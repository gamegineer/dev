/*
 * SwingRealm.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
 * Created on Oct 18, 2010 at 8:40:52 PM.
 */

package org.gamegineer.common.ui.databinding.swing;

import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import javax.swing.SwingUtilities;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.core.databinding.observable.Realm;
import org.gamegineer.common.internal.ui.Loggers;

/**
 * A data binding realm for a Swing event dispatch thread.
 */
@ThreadSafe
public final class SwingRealm
    extends Realm
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code SwingRealm} class.
     */
    private SwingRealm()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.eclipse.core.databinding.observable.Realm#asyncExec(java.lang.Runnable)
     */
    @Override
    public void asyncExec(
        final Runnable runnable )
    {
        final Runnable safeRunnable = new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                safeRun( runnable );
            }
        };
        SwingUtilities.invokeLater( safeRunnable );
    }

    /**
     * Executes the specified runnable from within the Swing system realm.
     * 
     * <p>
     * If this method is called from within the Swing system realm, the runnable
     * is executed directly; otherwise it executes the runnable from within the
     * Swing system realm and waits for it to complete.
     * </p>
     * 
     * @param runnable
     *        The runnable to execute; must not be {@code null}.
     */
    private static void execSystem(
        /* @NonNull */
        final Runnable runnable )
    {
        assert runnable != null;

        if( SwingUtilities.isEventDispatchThread() )
        {
            safeRun( runnable );
        }
        else
        {
            safeInvokeAndWait( runnable );
        }
    }

    /**
     * Gets the Swing realm for the system event dispatch thread.
     * 
     * @return The Swing realm for the system event dispatch thread or
     *         {@code null} if no system event dispatch thread is running.
     */
    /* @Nullable */
    public static SwingRealm getSystemRealm()
    {
        final AtomicReference<SwingRealm> systemRealm = new AtomicReference<>();
        execSystem( new Runnable()
        {
            @Override
            public void run()
            {
                systemRealm.set( (SwingRealm)Realm.getDefault() );
            }
        } );
        return systemRealm.get();
    }

    /**
     * Installs a Swing realm on the system event dispatch thread.
     * 
     * <p>
     * This method does nothing if a realm has already been installed on the
     * system event dispatch thread.
     * </p>
     */
    public static void installSystemRealm()
    {
        execSystem( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                if( Realm.getDefault() == null )
                {
                    Realm.setDefault( new SwingRealm() );
                }
            }
        } );
    }

    /*
     * @see org.eclipse.core.databinding.observable.Realm#isCurrent()
     */
    @Override
    public boolean isCurrent()
    {
        return SwingUtilities.isEventDispatchThread();
    }

    /**
     * Executes the specified runnable on the system event dispatch thread and
     * waits for it to complete.
     * 
     * <p>
     * Any exception thrown from the runnable is logged and not re-thrown.
     * </p>
     * 
     * @param runnable
     *        The runnable to execute; must not be {@code null}.
     */
    private static void safeInvokeAndWait(
        /* @NonNull */
        final Runnable runnable )
    {
        assert runnable != null;

        try
        {
            SwingUtilities.invokeAndWait( runnable );
        }
        catch( final Exception e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.SwingRealm_safeInvokeAndWait_error, e );
        }
    }

    /**
     * Uninstalls the Swing realm from the system event dispatch thread.
     * 
     * <p>
     * This method does nothing if no realm is installed on the system event
     * dispatch thread.
     * </p>
     */
    public static void uninstallSystemRealm()
    {
        execSystem( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                Realm.setDefault( null );
            }
        } );
    }
}
