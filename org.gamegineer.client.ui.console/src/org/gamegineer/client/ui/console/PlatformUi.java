/*
 * PlatformUi.java
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
 * Created on Oct 7, 2008 at 10:51:47 PM.
 */

package org.gamegineer.client.ui.console;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.client.internal.ui.console.Console;
import org.gamegineer.client.internal.ui.console.displays.DefaultDisplay;
import org.gamegineer.client.internal.ui.console.displays.SystemDisplay;
import org.gamegineer.common.core.util.concurrent.TaskUtils;

/**
 * Provides central access to the Gamegineer platform console user interface.
 * 
 * <p>
 * This class is thread-safe.
 * </p>
 * 
 * <p>
 * This class is not intended to be extended by clients.
 * </p>
 */
@ThreadSafe
public final class PlatformUi
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The platform console. */
    private static final AtomicReference<Console> console_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code PlatformUi} class.
     */
    static
    {
        console_ = new AtomicReference<Console>();
    }

    /**
     * Initializes a new instance of the {@code PlatformUi} class.
     */
    private PlatformUi()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the platform console, associates it with the specified display,
     * and runs it.
     * 
     * <p>
     * This method is intended to be called by the Equinox application's {@code
     * start} method. It will fail if the platform console has already been
     * created.
     * </p>
     * 
     * @param display
     *        The display to associate with the platform console; must not be
     *        {@code null}.
     * @param advisor
     *        The console advisor; must not be {@code null}.
     * 
     * @return The console result; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     * @throws java.lang.IllegalStateException
     *         If the platform console has already been created.
     * @throws java.lang.NullPointerException
     *         If {@code display} or {@code advisor} is {@code null}.
     */
    /* @NonNull */
    public static ConsoleResult createAndRunConsole(
        /* @NonNull */
        final IDisplay display,
        /* @NonNull */
        final IConsoleAdvisor advisor )
        throws Exception
    {
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        try
        {
            return executor.submit( createConsoleRunner( display, advisor ) ).get();
        }
        catch( final ExecutionException e )
        {
            final Throwable cause = e.getCause();
            if( cause instanceof Exception )
            {
                throw (Exception)cause;
            }

            throw TaskUtils.launderThrowable( cause );
        }
        finally
        {
            executor.shutdown();
        }
    }

    /**
     * Creates the platform console, associates it with the specified display,
     * and returns an object capable of running it.
     * 
     * @param display
     *        The display to associate with the platform console; must not be
     *        {@code null}.
     * @param advisor
     *        The console advisor; must not be {@code null}.
     * 
     * @return An object capable of running the console; never {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the platform console has already been created.
     * @throws java.lang.NullPointerException
     *         If {@code display} or {@code advisor} is {@code null}.
     */
    /* @NonNull */
    public static Callable<ConsoleResult> createConsoleRunner(
        /* @NonNull */
        final IDisplay display,
        /* @NonNull */
        final IConsoleAdvisor advisor )
    {
        assertArgumentNotNull( display, "display" ); //$NON-NLS-1$
        assertArgumentNotNull( advisor, "advisor" ); //$NON-NLS-1$

        final Console console = new Console( display, advisor );
        assertStateLegal( console_.compareAndSet( null, console ), Messages.PlatformUi_console_alreadyCreated );

        return new Callable<ConsoleResult>()
        {
            public ConsoleResult call()
                throws Exception
            {
                return console.createRunner().call();
            }
        };
    }

    /**
     * Creates the display to be used by the console.
     * 
     * @return The display to be used by the console; never {@code null}.
     */
    /* @NonNull */
    public static IDisplay createDisplay()
    {
        final IDisplay systemDisplay = SystemDisplay.createSystemDisplay();
        if( systemDisplay != null )
        {
            return systemDisplay;
        }

        return new DefaultDisplay();
    }

    /**
     * Gets the platform console.
     * 
     * @return The platform console; never {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the platform console has not yet been created.
     */
    /* @NonNull */
    public static IConsole getConsole()
    {
        final Console console = console_.get();
        assertStateLegal( console != null, Messages.PlatformUi_console_notCreated );
        return console;
    }

    /**
     * Indicates the platform console is running.
     * 
     * @return {@code true} if the platform console is running; otherwise
     *         {@code false}.
     */
    public static boolean isConsoleRunning()
    {
        final Console console = console_.get();
        return (console != null) && console.isRunning();
    }
}
