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

    /**
     * A reference to the asynchronous completion token associated with the
     * running table.
     */
    private final AtomicReference<Future<TableResult>> futureRef_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Application} class.
     */
    public Application()
    {
        futureRef_ = new AtomicReference<Future<TableResult>>();
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

        final List<String> applicationArguments = parseApplicationArguments( (String[])context.getArguments().get( IApplicationContext.APPLICATION_ARGS ) );
        final Version applicationVersion = parseApplicationVersion( (String)context.getBrandingBundle().getHeaders().get( org.osgi.framework.Constants.BUNDLE_VERSION ) );
        return new TableAdvisor( applicationArguments, applicationVersion );
    }

    /**
     * Parses the specified application arguments array.
     * 
     * @param applicationArgumentsArray
     *        The application arguments array; may be {@code null}.
     * 
     * @return The collection of application arguments; never {@code null}.
     */
    /* @NonNull */
    private static List<String> parseApplicationArguments(
        /* @Nullable */
        final String[] applicationArgumentsArray )
    {
        if( (applicationArgumentsArray == null) || (applicationArgumentsArray.length == 0) )
        {
            return Collections.emptyList();
        }

        return Arrays.asList( applicationArgumentsArray );
    }

    /**
     * Parses the specified application version string.
     * 
     * @param applicationVersionString
     *        The application version string; may be {@code null}.
     * 
     * @return The application version; never {@code null}.
     */
    /* @NonNull */
    private static Version parseApplicationVersion(
        /* @Nullable */
        final String applicationVersionString )
    {
        try
        {
            return Version.parseVersion( applicationVersionString );
        }
        catch( final IllegalArgumentException e )
        {
            Loggers.getDefaultLogger().log( Level.WARNING, NonNlsMessages.Application_parseApplicationVersion_error( applicationVersionString ), e );
        }

        return Version.emptyVersion;
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

        final Future<TableResult> future = Activator.getDefault().getExecutorService().submit( TableUIFactory.createTableRunner( advisor ) );
        futureRef_.set( future );
        try
        {
            TableResult result;
            try
            {
                result = future.get();
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
            futureRef_.set( null );
        }
    }

    /*
     * @see org.eclipse.equinox.app.IApplication#stop()
     */
    @Override
    public void stop()
    {
        final Future<?> future = futureRef_.get();
        if( future != null )
        {
            Loggers.getDefaultLogger().info( NonNlsMessages.Application_stop_stopping );
            if( !future.cancel( true ) )
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
