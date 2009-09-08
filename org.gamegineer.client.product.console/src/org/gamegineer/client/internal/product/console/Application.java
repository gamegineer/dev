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
 * Created on Sep 19, 2008 at 11:11:49 PM.
 */

package org.gamegineer.client.internal.product.console;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.gamegineer.client.ui.console.ConsoleAdvisor;
import org.gamegineer.client.ui.console.ConsoleResult;
import org.gamegineer.client.ui.console.IConsoleAdvisor;
import org.gamegineer.client.ui.console.IDisplay;
import org.gamegineer.client.ui.console.PlatformUi;
import org.osgi.framework.Version;

/**
 * The entry point of the Gamegineer console client.
 */
public final class Application
    implements IApplication
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The task associated with the running console. */
    private final AtomicReference<Future<ConsoleResult>> task_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Application} class.
     */
    public Application()
    {
        task_ = new AtomicReference<Future<ConsoleResult>>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a console advisor based on the specified application context.
     * 
     * @param context
     *        The application context; must not be {@code null}.
     * 
     * @return A console advisor; never {@code null}.
     */
    /* @NonNull */
    private static IConsoleAdvisor createConsoleAdvisor(
        /* @NonNull */
        final IApplicationContext context )
    {
        assert context != null;

        final String[] args = (String[])context.getArguments().get( IApplicationContext.APPLICATION_ARGS );
        final List<String> argList;
        if( (args != null) && (args.length != 0) )
        {
            argList = Arrays.asList( args );
        }
        else
        {
            argList = Collections.emptyList();
        }

        final String versionString = (String)context.getBrandingBundle().getHeaders().get( org.osgi.framework.Constants.BUNDLE_VERSION );
        Version version = Version.emptyVersion;
        try
        {
            version = Version.parseVersion( versionString );
        }
        catch( final IllegalArgumentException e )
        {
            Loggers.DEFAULT.log( Level.WARNING, Messages.Application_createConsoleAdvisor_parseVersionError( versionString ), e );
        }

        return new ConsoleAdvisor( argList, version );
    }

    /*
     * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
     */
    public Object start(
        final IApplicationContext context )
        throws Exception
    {
        final IDisplay display = PlatformUi.createDisplay();
        final IConsoleAdvisor advisor = createConsoleAdvisor( context );

        final ExecutorService executor = Executors.newSingleThreadExecutor();
        try
        {
            final Future<ConsoleResult> task = executor.submit( PlatformUi.createConsoleRunner( display, advisor ) );
            task_.set( task );
            return toApplicationExitObject( task.get() );
        }
        finally
        {
            task_.set( null );
            executor.shutdown();
            executor.awaitTermination( 10, TimeUnit.SECONDS );
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
            task.cancel( true );
        }
    }

    /**
     * Converts the specified console result to an appropriate Equinox
     * application exit object.
     * 
     * @param result
     *        The console result; must not be {@code null}.
     * 
     * @return The Equinox application exit object corresponding to the
     *         specified console result; never {@code null}.
     */
    /* @NonNull */
    private static Object toApplicationExitObject(
        /* @NonNull */
        final ConsoleResult result )
    {
        assert result != null;

        @SuppressWarnings( "boxing" )
        final Integer exitCode = result.getExitCode();
        return exitCode;
    }
}
