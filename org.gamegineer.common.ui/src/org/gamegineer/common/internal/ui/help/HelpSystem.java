/*
 * HelpSystem.java
 * Copyright 2008-2013 Gamegineer.org
 * All rights reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Created on Jan 4, 2012 at 8:23:37 PM.
 */

package org.gamegineer.common.internal.ui.help;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.help.CSH;
import javax.help.DefaultHelpBroker;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.help.HelpSetException;
import javax.help.WindowPresentation;
import javax.swing.JFrame;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.app.IBranding;
import org.gamegineer.common.internal.ui.Debug;
import org.gamegineer.common.internal.ui.Loggers;
import org.gamegineer.common.ui.app.BrandingUIUtils;
import org.gamegineer.common.ui.help.IHelpSetProvider;
import org.gamegineer.common.ui.help.IHelpSystem;
import org.osgi.framework.ServiceReference;

/**
 * Implementation of {@link IHelpSystem}.
 */
@ThreadSafe
public final class HelpSystem
    implements IHelpSystem
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The branding for the running application. */
    @GuardedBy( "lock_" )
    private IBranding branding_;

    /**
     * The collection of help set providers registered from the service
     * registry. The key is the help set provider service reference; the value
     * is the help set provider proxy.
     */
    @GuardedBy( "lock_" )
    private final Map<ServiceReference<IHelpSetProvider>, HelpSetProviderProxy> helpSetProviderProxies_;

    /** Indicates the help system has been shutdown. */
    @GuardedBy( "lock_" )
    private boolean isShutdown_;

    /** The instance lock. */
    private final Object lock_;

    /** The master help broker. */
    @GuardedBy( "lock_" )
    private HelpBroker masterHelpBroker_;

    /** The master help set. */
    @GuardedBy( "lock_" )
    private HelpSet masterHelpSet_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code HelpSystem} class.
     */
    public HelpSystem()
    {
        branding_ = null;
        helpSetProviderProxies_ = new HashMap<>();
        isShutdown_ = false;
        lock_ = new Object();
        masterHelpBroker_ = null;
        masterHelpSet_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Activates this component.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    public void activate()
        throws Exception
    {
        final URL helpSetUrl = HelpSet.findHelpSet( getClass().getClassLoader(), "help/master.hs" ); //$NON-NLS-1$
        if( helpSetUrl == null )
        {
            throw new HelpSetException( NonNlsMessages.HelpSystem_activate_masterHelpSetMissing );
        }

        final HelpSet masterHelpSet = new HelpSet( null, helpSetUrl );
        final HelpBroker masterHelpBroker = masterHelpSet.createHelpBroker();

        synchronized( lock_ )
        {
            assert masterHelpSet_ == null;
            assert masterHelpBroker_ == null;

            masterHelpSet_ = masterHelpSet;
            masterHelpBroker_ = masterHelpBroker;

            if( branding_ != null )
            {
                masterHelpSet_.setTitle( NlsMessages.HelpSystem_masterHelpSet_title( branding_.getName() ) );
            }

            for( final HelpSetProviderProxy helpSetProviderProxy : helpSetProviderProxies_.values() )
            {
                addHelpSet( helpSetProviderProxy );
            }
        }
    }

    /**
     * Adds the help set associated with the specified provider to the help
     * system.
     * 
     * @param helpSetProvider
     *        The help set provider; must not be {@code null}.
     */
    private void addHelpSet(
        /* @NonNull */
        final IHelpSetProvider helpSetProvider )
    {
        assert helpSetProvider != null;

        try
        {
            synchronized( lock_ )
            {
                if( (masterHelpSet_ != null) && !isShutdown_ )
                {
                    masterHelpSet_.add( helpSetProvider.getHelpSet() );
                }
            }
        }
        catch( final HelpSetException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.HelpSystem_addHelpSet_failed, e );
        }
    }

    /**
     * Binds the application branding to this component.
     * 
     * @param branding
     *        The application branding; must not be {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the application branding is already bound.
     * @throws java.lang.NullPointerException
     *         If {@code branding} is {@code null}.
     */
    public void bindBranding(
        /* @NonNull */
        final IBranding branding )
    {
        assertArgumentNotNull( branding, "branding" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            assertStateLegal( branding_ == null, NonNlsMessages.HelpSystem_bindBranding_bound );
            branding_ = branding;
        }
    }

    /**
     * Deactivates this component.
     */
    public void deactivate()
    {
        synchronized( lock_ )
        {
            masterHelpBroker_ = null;

            if( masterHelpSet_ != null )
            {
                for( final HelpSetProviderProxy helpSetProviderProxy : helpSetProviderProxies_.values() )
                {
                    removeHelpSet( helpSetProviderProxy );
                }

                masterHelpSet_ = null;
            }
        }
    }

    /*
     * @see org.gamegineer.common.ui.help.IHelpSystem#displayHelp(java.lang.Object)
     */
    @Override
    public void displayHelp(
        final Object activationObject )
    {
        assertArgumentNotNull( activationObject, "activationObject" ); //$NON-NLS-1$

        final ActionListener listener;
        synchronized( lock_ )
        {
            if( isShutdown_ )
            {
                return;
            }

            if( masterHelpBroker_ != null )
            {
                listener = new CSH.DisplayHelpFromSource( masterHelpBroker_ );
            }
            else
            {
                Loggers.getDefaultLogger().severe( NonNlsMessages.HelpSystem_displayHelp_helpBrokerNotAvailable );
                return;
            }
        }

        listener.actionPerformed( new ActionEvent( activationObject, ActionEvent.ACTION_PERFORMED, null ) );
        setMainWindowIcons();
    }

    /**
     * Registers the help set provider associated with the specified service
     * reference.
     * 
     * @param helpSetProviderReference
     *        The help set provider service reference; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code helpSetProviderReference} is {@code null}.
     */
    public void registerHelpSetProvider(
        /* @NonNull */
        final ServiceReference<IHelpSetProvider> helpSetProviderReference )
    {
        assertArgumentNotNull( helpSetProviderReference, "helpSetProviderReference" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            final HelpSetProviderProxy helpSetProviderProxy = new HelpSetProviderProxy( helpSetProviderReference );
            helpSetProviderProxies_.put( helpSetProviderReference, helpSetProviderProxy );
            addHelpSet( helpSetProviderProxy );
            Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "Registered help set provider '%s'", helpSetProviderProxy ) ); //$NON-NLS-1$
        }
    }

    /**
     * Removes the help set associated with the specified provider from the help
     * system.
     * 
     * @param helpSetProvider
     *        The help set provider; must not be {@code null}.
     */
    private void removeHelpSet(
        /* @NonNull */
        final IHelpSetProvider helpSetProvider )
    {
        assert helpSetProvider != null;

        try
        {
            synchronized( lock_ )
            {
                if( (masterHelpSet_ != null) && !isShutdown_ )
                {
                    masterHelpSet_.remove( helpSetProvider.getHelpSet() );
                }
            }
        }
        catch( final HelpSetException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.HelpSystem_removeHelpSet_failed, e );
        }
    }

    /**
     * Sets the icons for the main help window.
     */
    private void setMainWindowIcons()
    {
        // HACK: This implementation is a total hack, but the JavaHelp API really provides
        // no good way to set the main help window icons dynamically.  In addition, even
        // t he support to declaratively set an icon doesn't provide support for multiple
        // image resolutions, which has become more and more important on modern OSes that
        // want icons larger than 32x32.

        final IBranding branding;
        final HelpBroker masterHelpBroker;
        synchronized( lock_ )
        {
            branding = branding_;
            masterHelpBroker = masterHelpBroker_;
        }

        if( (branding != null) && (masterHelpBroker != null) )
        {
            final List<Image> windowImages = BrandingUIUtils.getWindowImages( branding );
            if( !windowImages.isEmpty() )
            {
                try
                {
                    final WindowPresentation windowPresentation = ((DefaultHelpBroker)masterHelpBroker).getWindowPresentation();
                    if( windowPresentation != null )
                    {
                        final Field frameField = WindowPresentation.class.getDeclaredField( "frame" ); //$NON-NLS-1$
                        frameField.setAccessible( true );
                        final JFrame frame = (JFrame)frameField.get( windowPresentation );
                        if( frame != null )
                        {
                            frame.setIconImages( windowImages );
                        }
                    }
                }
                catch( final NoSuchFieldException e )
                {
                    Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.HelpSystem_setMainWindowIcons_failed, e );
                }
                catch( final IllegalAccessException e )
                {
                    Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.HelpSystem_setMainWindowIcons_failed, e );
                }
            }
        }
    }

    /*
     * @see org.gamegineer.common.ui.help.IHelpSystem#shutdown()
     */
    @Override
    public void shutdown()
    {
        synchronized( lock_ )
        {
            isShutdown_ = true;
        }
    }

    /**
     * Unbinds the application branding from this component.
     * 
     * @param branding
     *        The application branding; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code branding} is not currently bound.
     * @throws java.lang.NullPointerException
     *         If {@code branding} is {@code null}.
     */
    public void unbindBranding(
        /* @NonNull */
        final IBranding branding )
    {
        assertArgumentNotNull( branding, "branding" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            assertArgumentLegal( branding_ == branding, "branding", NonNlsMessages.HelpSystem_unbindBranding_notBound ); //$NON-NLS-1$
            branding_ = null;
        }
    }

    /**
     * Unregisters the help set provider associated with the specified service
     * reference.
     * 
     * @param helpSetProviderReference
     *        The help set provider service reference; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code helpSetProviderReference} is {@code null}.
     */
    public void unregisterHelpSetProvider(
        /* @NonNull */
        final ServiceReference<IHelpSetProvider> helpSetProviderReference )
    {
        assertArgumentNotNull( helpSetProviderReference, "helpSetProviderReference" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            final HelpSetProviderProxy helpSetProviderProxy = helpSetProviderProxies_.remove( helpSetProviderReference );
            if( helpSetProviderProxy != null )
            {
                removeHelpSet( helpSetProviderProxy );
                helpSetProviderProxy.dispose();
                Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "Unregistered help set provider '%s'", helpSetProviderProxy ) ); //$NON-NLS-1$
            }
        }
    }
}
