/*
 * Application.java
 * Copyright 2008-2009 Gamegineer.org
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
 * Created on Sep 16, 2009 at 11:38:32 PM.
 */

package org.gamegineer.table.internal.product;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.gamegineer.table.ui.ITableAdvisor;
import org.gamegineer.table.ui.TableAdvisor;
import org.gamegineer.table.ui.TableResult;
import org.gamegineer.table.ui.TableUI;
import org.osgi.framework.Version;

/**
 * The entry point of the Gamegineer table application.
 */
@ThreadSafe
public final class Application
    implements IApplication
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The task associated with the running table. */
    private final AtomicReference<Future<TableResult>> task_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Application} class.
     */
    public Application()
    {
        task_ = new AtomicReference<Future<TableResult>>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a table advisor based on the specified application context.
     * 
     * @param context
     *        The application context; must not be {@code null}.
     * 
     * @return A table advisor; never {@code null}.
     */
    /* @NonNull */
    private static ITableAdvisor createTableAdvisor(
        /* @NonNull */
        final IApplicationContext context )
    {
        assert context != null;

        final String[] argsArray = (String[])context.getArguments().get( IApplicationContext.APPLICATION_ARGS );
        final List<String> args;
        if( (argsArray != null) && (argsArray.length != 0) )
        {
            args = Arrays.asList( argsArray );
        }
        else
        {
            args = Collections.emptyList();
        }

        final String versionString = (String)context.getBrandingBundle().getHeaders().get( org.osgi.framework.Constants.BUNDLE_VERSION );
        Version version;
        try
        {
            version = Version.parseVersion( versionString );
        }
        catch( final IllegalArgumentException e )
        {
            Loggers.DEFAULT.log( Level.WARNING, Messages.Application_createTableAdvisor_parseVersionError( versionString ), e );
            version = Version.emptyVersion;
        }

        return new TableAdvisor( args, version );
    }

    /*
     * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
     */
    public Object start(
        final IApplicationContext context )
        throws Exception
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        final ITableAdvisor advisor = createTableAdvisor( context );
        Loggers.DEFAULT.info( Messages.Application_start_starting( advisor.getApplicationVersion() ) );

        final ExecutorService executor = Executors.newSingleThreadExecutor();
        try
        {
            final Future<TableResult> task = executor.submit( TableUI.createTableRunner( advisor ) );
            task_.set( task );

            TableResult result;
            try
            {
                result = task.get();
            }
            catch( final CancellationException e )
            {
                Loggers.DEFAULT.log( Level.WARNING, Messages.Application_start_cancelled, e );
                result = TableResult.OK;
            }

            Loggers.DEFAULT.info( Messages.Application_start_stopped( result ) );
            return toApplicationExitObject( result );
        }
        finally
        {
            task_.set( null );
            executor.shutdown();
            if( !executor.awaitTermination( 10, TimeUnit.SECONDS ) )
            {
                Loggers.DEFAULT.severe( Messages.Application_start_stopFailed );
            }
        }
    }

    /*
     * @see org.eclipse.equinox.app.IApplication#stop()
     */
    public void stop()
    {
        final Future<?> task = task_.get();
        if( task != null )
        {
            Loggers.DEFAULT.info( Messages.Application_stop_stopping );
            if( !task.cancel( true ) )
            {
                Loggers.DEFAULT.severe( Messages.Application_stop_cancelFailed );
            }
        }
    }

    /**
     * Converts the specified table result to an appropriate Equinox application
     * exit object.
     * 
     * @param result
     *        The table result; must not be {@code null}.
     * 
     * @return The Equinox application exit object corresponding to the
     *         specified table result; never {@code null}.
     */
    /* @NonNull */
    private static Object toApplicationExitObject(
        /* @NonNull */
        final TableResult result )
    {
        assert result != null;

        @SuppressWarnings( "boxing" )
        final Integer exitCode = result.getExitCode();
        return exitCode;
    }
}
