/*
 * ViewUtils.java
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
 * Created on Nov 24, 2011 at 10:38:04 PM.
 */

package org.gamegineer.table.internal.ui.view;

import java.awt.Component;
import java.awt.Cursor;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.concurrent.TaskUtils;
import org.gamegineer.table.internal.ui.Activator;
import org.gamegineer.table.internal.ui.Loggers;
import org.gamegineer.table.net.ITableNetwork;

/**
 * A collection of useful methods for views.
 */
@ThreadSafe
final class ViewUtils
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ViewUtils} class.
     */
    private ViewUtils()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Synchronously disconnects the specified table network associated with the
     * specified view component.
     * 
     * <p>
     * This method waits for a fixed amount of time for the specified table
     * network to disconnect.
     * </p>
     * 
     * @param component
     *        The view component associated with the table network; must not be
     *        {@code null}.
     * @param tableNetwork
     *        The table network to be disconnected; must not be {@code null}.
     */
    static void disconnectTableNetwork(
        /* @NonNull */
        final Component component,
        /* @NonNull */
        final ITableNetwork tableNetwork )
    {
        assert component != null;
        assert tableNetwork != null;

        final Cursor oldCursor = component.getCursor();
        component.setCursor( Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR ) );
        try
        {
            final Future<?> future = Activator.getDefault().getExecutorService().submit( new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        tableNetwork.disconnect();
                    }
                    catch( final InterruptedException e )
                    {
                        Thread.currentThread().interrupt();
                    }
                }
            } );

            try
            {
                future.get( 10L, TimeUnit.SECONDS );
            }
            catch( final TimeoutException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.ViewUtils_disconnectTableNetwork_timedOut, e );
            }
            catch( final ExecutionException e )
            {
                throw TaskUtils.launderThrowable( e.getCause() );
            }
            catch( final InterruptedException e )
            {
                Thread.currentThread().interrupt();
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.ViewUtils_disconnectTableNetwork_interrupted, e );
            }
        }
        finally
        {
            component.setCursor( oldCursor );
        }
    }
}
