/*
 * CommandQueueExtensionAsCommandQueueTest.java
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
 * Created on Jun 9, 2008 at 9:16:36 PM.
 */

package org.gamegineer.engine.internal.core.extensions.commandqueue;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import org.gamegineer.engine.core.ICommand;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.extensions.commandqueue.AbstractCommandQueueTestCase;
import org.gamegineer.engine.core.extensions.commandqueue.ICommandQueue;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.extensions.commandqueue.CommandQueueExtension}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.engine.core.extensions.commandqueue.ICommandQueue}
 * interface.
 */
public final class CommandQueueExtensionAsCommandQueueTest
    extends AbstractCommandQueueTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * CommandQueueExtensionAsCommandQueueTest} class.
     */
    public CommandQueueExtensionAsCommandQueueTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.extensions.commandqueue.AbstractCommandQueueTestCase#createCommandQueue(org.gamegineer.engine.core.IEngineContext)
     */
    @Override
    protected ICommandQueue createCommandQueue(
        final IEngineContext context )
        throws Exception
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        final CommandQueueExtension extension = new CommandQueueExtension( new FakeCommandQueue() );
        extension.start( context );
        return extension;
    }

    /*
     * @see org.gamegineer.engine.core.extensions.commandqueue.AbstractCommandQueueTestCase#shutdownCommandQueue(org.gamegineer.engine.core.extensions.commandqueue.ICommandQueue)
     */
    @Override
    protected void shutdownCommandQueue(
        final ICommandQueue commandQueue )
        throws Exception
    {
        final CommandQueueExtension extension = (CommandQueueExtension)commandQueue;
        final FakeCommandQueue delegate = (FakeCommandQueue)extension.getDelegate();
        delegate.shutdown();
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A fake implementation of {@code ICommandQueue} to test the implementation
     * of {@code CommandQueueExtension}.
     */
    private static final class FakeCommandQueue
        implements ICommandQueue
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /**
         * The executor service used to execute commands submitted to this
         * queue.
         */
        private final ExecutorService executorService_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code FakeCommandQueue} class.
         */
        FakeCommandQueue()
        {
            executorService_ = Executors.newSingleThreadExecutor();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Shuts down this command queue.
         */
        void shutdown()
        {
            executorService_.shutdown();
        }

        /*
         * @see org.gamegineer.engine.core.extensions.commandqueue.ICommandQueue#submitCommand(org.gamegineer.engine.core.IEngineContext, org.gamegineer.engine.core.ICommand)
         */
        public <T> Future<T> submitCommand(
            final IEngineContext context,
            final ICommand<T> command )
        {
            assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
            assertArgumentNotNull( command, "command" ); //$NON-NLS-1$

            final Callable<T> callable = new Callable<T>()
            {
                public T call()
                    throws Exception
                {
                    return command.execute( context );
                }
            };

            try
            {
                return executorService_.submit( callable );
            }
            catch( final RejectedExecutionException e )
            {
                throw new IllegalStateException( e );
            }
        }
    }
}
