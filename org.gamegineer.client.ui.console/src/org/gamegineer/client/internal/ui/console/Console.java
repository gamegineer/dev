/*
 * Console.java
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
 * Created on Oct 5, 2008 at 10:05:18 PM.
 */

package org.gamegineer.client.internal.ui.console;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import net.jcip.annotations.ThreadSafe;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.gamegineer.client.core.GameClientConfigurationException;
import org.gamegineer.client.core.GameClientFactory;
import org.gamegineer.client.core.IGameClient;
import org.gamegineer.client.core.config.GameClientConfigurationBuilder;
import org.gamegineer.client.core.system.GameSystemUiSourceFactory;
import org.gamegineer.client.internal.ui.console.commandlet.CommandletExecutor;
import org.gamegineer.client.internal.ui.console.commandlet.CommandletParser;
import org.gamegineer.client.ui.console.ConsoleResult;
import org.gamegineer.client.ui.console.IConsole;
import org.gamegineer.client.ui.console.IConsoleAdvisor;
import org.gamegineer.client.ui.console.IDisplay;
import org.gamegineer.client.ui.console.commandlet.CommandletException;
import org.gamegineer.client.ui.console.commandlet.IStatelet;

/**
 * Implementation of {@link org.gamegineer.client.ui.console.IConsole}.
 * 
 * <p>
 * This class is thread-safe.
 * </p>
 */
@ThreadSafe
public final class Console
    implements IConsole
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The console advisor. */
    private final IConsoleAdvisor advisor_;

    /** The display associated with the console. */
    private final IDisplay display_;

    /** Indicates the console has been closed by user request. */
    private final AtomicBoolean isClosed_;

    /** The console state. */
    private final AtomicReference<State> state_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Console} class.
     * 
     * @param display
     *        The display associated with the console; must not be {@code null}.
     * @param advisor
     *        The console advisor; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code display} or {@code advisor} is {@code null}.
     */
    public Console(
        /* @NonNull */
        final IDisplay display,
        /* @NonNull */
        final IConsoleAdvisor advisor )
    {
        assertArgumentNotNull( display, "display" ); //$NON-NLS-1$
        assertArgumentNotNull( advisor, "advisor" ); //$NON-NLS-1$

        display_ = display;
        advisor_ = advisor;
        isClosed_ = new AtomicBoolean( false );
        state_ = new AtomicReference<State>( State.PRISTINE );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.client.ui.console.IConsole#close()
     */
    public void close()
    {
        isClosed_.set( true );
    }

    /**
     * Creates a new game client for this console.
     * 
     * @return A new game client; never {@code null}.
     * 
     * @throws org.gamegineer.client.core.GameClientConfigurationException
     *         If an error occurs creating the game client.
     */
    /* @NonNull */
    private static IGameClient createGameClient()
        throws GameClientConfigurationException
    {
        final GameClientConfigurationBuilder builder = new GameClientConfigurationBuilder();
        builder.setGameSystemUiSource( GameSystemUiSourceFactory.createRegistryGameSystemUiSource() );
        return GameClientFactory.createGameClient( builder.toGameClientConfiguration() );
    }

    /**
     * Creates a runner for this console.
     * 
     * @return A runner for this console; never {@code null}.
     */
    /* @NonNull */
    public Callable<ConsoleResult> createRunner()
    {
        return new Callable<ConsoleResult>()
        {
            @SuppressWarnings( "synthetic-access" )
            public ConsoleResult call()
                throws Exception
            {
                return Console.this.run();
            }
        };
    }

    /*
     * @see org.gamegineer.client.ui.console.IConsole#getDisplay()
     */
    public IDisplay getDisplay()
    {
        return display_;
    }

    /**
     * Gets the state of this console.
     * 
     * @return The state of this console; never {@code null}.
     */
    State getState()
    {
        return state_.get();
    }

    /**
     * Indicates this console is running.
     * 
     * @return {@code true} if this console is running; otherwise {@code false}.
     */
    public boolean isRunning()
    {
        return state_.get() == State.RUNNING;
    }

    /**
     * Processes the application command line arguments.
     * 
     * @return A non-{@code null} value that represents the console result if
     *         the console should exit immediately; otherwise {@code null} to
     *         indicate the console should continue running.
     */
    /* @Nullable */
    private ConsoleResult processCommandLineArguments()
    {
        try
        {
            final List<String> args = advisor_.getApplicationArguments();
            final CommandLineParser commandLineParser = new GnuParser();
            final Options options = CommandLineOptions.getOptions();
            final CommandLine commandLine = commandLineParser.parse( options, args.toArray( new String[ args.size() ] ) );

            if( commandLine.hasOption( CommandLineOptions.OPTION_HELP ) )
            {
                final HelpFormatter helpFormatter = new HelpFormatter();
                helpFormatter.printHelp( display_.getWriter(), HelpFormatter.DEFAULT_WIDTH, "gamegineer", null, options, HelpFormatter.DEFAULT_LEFT_PAD, HelpFormatter.DEFAULT_DESC_PAD, null ); //$NON-NLS-1$
                return ConsoleResult.OK;
            }
        }
        catch( final ParseException e )
        {
            display_.getWriter().println( e.getLocalizedMessage() );
            return ConsoleResult.FAIL;
        }

        return null;
    }

    /**
     * Runs this console.
     * 
     * @return The console result; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     * @throws java.lang.IllegalStateException
     *         If this console is running or has finished running.
     */
    /* @NonNull */
    private ConsoleResult run()
        throws Exception
    {
        assertStateLegal( state_.compareAndSet( State.PRISTINE, State.RUNNING ), Messages.Console_state_notPristine );

        try
        {
            final ConsoleResult processCommandLineResult = processCommandLineArguments();
            if( processCommandLineResult != null )
            {
                return processCommandLineResult;
            }

            display_.getWriter().println( Messages.Console_output_bannerMessage( advisor_.getApplicationVersion() ) );

            final IStatelet statelet = new Statelet();
            final IGameClient gameClient = createGameClient();

            final CommandletParser parser = new CommandletParser();
            while( !isClosed_.get() )
            {
                display_.getWriter().println();
                display_.format( "g8r> " ); //$NON-NLS-1$

                while( !isClosed_.get() && !display_.getReader().ready() )
                {
                    if( Thread.interrupted() )
                    {
                        throw new InterruptedException();
                    }

                    Thread.sleep( 10 );
                }

                if( isClosed_.get() )
                {
                    break;
                }

                final String line = display_.readLine();
                if( line == null )
                {
                    break;
                }

                final CommandletExecutor executor = parser.parse( line );
                try
                {
                    executor.execute( this, statelet, gameClient );
                }
                catch( final CommandletException e )
                {
                    Loggers.DEFAULT.log( Level.SEVERE, Messages.Console_run_commandletException, e );
                    display_.getWriter().println( e.getLocalizedMessage() );
                }
                catch( final Exception e )
                {
                    Loggers.DEFAULT.log( Level.SEVERE, Messages.Console_run_unexpectedCommandletException, e );
                    display_.getWriter().println( Messages.Console_output_unexpectedCommandletException );
                }
            }

            return ConsoleResult.OK;
        }
        finally
        {
            state_.set( State.FINISHED );
        }
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * The state of the console.
     */
    enum State
    {
        // ==================================================================
        // Enum Constants
        // ==================================================================

        /** The console has never been run. */
        PRISTINE,

        /** The console is currently running. */
        RUNNING,

        /** The console has finished running. */
        FINISHED;
    }
}
