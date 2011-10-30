/*
 * Application.java
 * Copyright 2008-2011 Gamegineer.org
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
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.gamegineer.table.ui.ITableAdvisor;
import org.gamegineer.table.ui.TableAdvisor;
import org.gamegineer.table.ui.TableResult;
import org.gamegineer.table.ui.TableUIFactory;
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
            Loggers.getDefaultLogger().log( Level.WARNING, NonNlsMessages.Application_createTableAdvisor_parseVersionError( versionString ), e );
            version = Version.emptyVersion;
        }

        return new TableAdvisor( args, version );
    }

    /*
     * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
     */
    @Override
    public Object start(
        final IApplicationContext context )
        throws Exception
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        final ITableAdvisor advisor = createTableAdvisor( context );
        Loggers.getDefaultLogger().info( NonNlsMessages.Application_start_starting( advisor.getApplicationVersion() ) );

        final ExecutorService executorService = Activator.getDefault().getExecutorService();
        if( executorService == null )
        {
            throw new IllegalStateException( NonNlsMessages.Application_start_executorServiceNotAvailable );
        }

        final Future<TableResult> task = executorService.submit( TableUIFactory.createTableRunner( advisor ) );
        task_.set( task );
        try
        {
            TableResult result;
            try
            {
                result = task.get();
            }
            catch( final CancellationException e )
            {
                Loggers.getDefaultLogger().log( Level.WARNING, NonNlsMessages.Application_start_cancelled, e );
                result = TableResult.OK;
            }

            Loggers.getDefaultLogger().info( NonNlsMessages.Application_start_stopped( result ) );
            return toApplicationExitObject( result );
        }
        finally
        {
            task_.set( null );
        }
    }

    /*
     * @see org.eclipse.equinox.app.IApplication#stop()
     */
    @Override
    public void stop()
    {
        final Future<?> task = task_.get();
        if( task != null )
        {
            Loggers.getDefaultLogger().info( NonNlsMessages.Application_stop_stopping );
            if( !task.cancel( true ) )
            {
                Loggers.getDefaultLogger().severe( NonNlsMessages.Application_stop_cancelFailed );
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
