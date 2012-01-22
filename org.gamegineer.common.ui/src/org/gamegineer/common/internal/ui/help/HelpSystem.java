/*
 * HelpSystem.java
 * Copyright 2008-2012 Gamegineer.org
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import javax.help.CSH;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.help.HelpSetException;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.app.IBranding;
import org.gamegineer.common.internal.ui.Activator;
import org.gamegineer.common.internal.ui.Debug;
import org.gamegineer.common.internal.ui.Loggers;
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
    private final Map<ServiceReference, HelpSetProviderProxy> helpSetProviderProxies_;

    /** The instance lock. */
    private final Object lock_;

    /** The master help broker. */
    @GuardedBy( "lock_" )
    private HelpBroker masterHelpBroker_;

    /** The master help set. */
    @GuardedBy( "lock_" )
    private HelpSet masterHelpSet_;

    // FIXME: need to figure out how we're going to build help indexes automatically
    // do we do it at runtime or at build time?  if at build time, it's probably something
    // that has to be done in the build script, which means search index will not be
    // available during development.  or we build a static empty search index that will
    // be available during development, but build the real thing during build.


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code HelpSystem} class.
     */
    public HelpSystem()
    {
        branding_ = null;
        helpSetProviderProxies_ = new HashMap<ServiceReference, HelpSetProviderProxy>();
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
        final URL helpSetUrl = Activator.getDefault().getBundleContext().getBundle().getEntry( "/help/master.hs" ); //$NON-NLS-1$
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
                // TODO: Need to be able to override help icon, as well.
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
                if( masterHelpSet_ != null )
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
        final ServiceReference helpSetProviderReference )
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
                if( masterHelpSet_ != null )
                {
                    // FIXME: this causes an annoying beep when exiting the application.
                    //
                    // caused by javax.help.plaf.basic.BasicSearchNavigatorUI:746
                    //
                    // when the help set is removed, the search is re-run, and for some reason,
                    // BasicSearchNavigator emits a beep when the search is finished.
                    //
                    // we need to figure out how to disable this, at least when the user is
                    // exiting the application (maybe add a new method to IHelpSystem, shutdown(),
                    // that will destroy the master help set so we don't attempt to remove
                    // contributed help sets during shutdown?)
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
        final ServiceReference helpSetProviderReference )
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
