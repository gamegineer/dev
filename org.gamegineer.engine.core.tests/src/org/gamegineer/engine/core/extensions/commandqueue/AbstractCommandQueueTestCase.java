/*
 * AbstractCommandQueueTestCase.java
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
 * Created on Jun 9, 2008 at 9:00:44 PM.
 */

package org.gamegineer.engine.core.extensions.commandqueue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.concurrent.ExecutionException;
import org.gamegineer.common.core.util.concurrent.TaskUtils;
import org.gamegineer.engine.core.EngineException;
import org.gamegineer.engine.core.FakeEngineContext;
import org.gamegineer.engine.core.ICommand;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.MockCommand;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.engine.core.extensions.commandqueue.ICommandQueue}
 * interface.
 */
public abstract class AbstractCommandQueueTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The command queue under test in the fixture. */
    private ICommandQueue m_commandQueue;

    /** The engine context for use in the fixture. */
    private IEngineContext m_context;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractCommandQueueTestCase}
     * class.
     */
    protected AbstractCommandQueueTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the command queue to be tested.
     * 
     * @param context
     *        The engine context; must not be {@code null}.
     * 
     * @return The command queue to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     */
    /* @NonNull */
    protected abstract ICommandQueue createCommandQueue(
        /* @NonNull */
        IEngineContext context )
        throws Exception;

    /**
     * Creates an engine context suitable for testing the command queue.
     * 
     * <p>
     * The default implementation returns an uninitialized fake engine context.
     * </p>
     * 
     * @return An engine context suitable for testing the command queue; never
     *         {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected IEngineContext createEngineContext()
        throws Exception
    {
        return new FakeEngineContext();
    }

    /**
     * Sets up the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Before
    public void setUp()
        throws Exception
    {
        m_context = createEngineContext();
        m_commandQueue = createCommandQueue( m_context );
        assertNotNull( m_commandQueue );
    }

    /**
     * Shuts down the specified command queue.
     * 
     * @param commandQueue
     *        The command queue; must not be {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     * @throws java.lang.NullPointerException
     *         If {@code commandQueue} is {@code null}.
     */
    protected abstract void shutdownCommandQueue(
        /* @NonNull */
        ICommandQueue commandQueue )
        throws Exception;

    /**
     * Tears down the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @After
    public void tearDown()
        throws Exception
    {
        m_commandQueue = null;
        m_context = null;
    }

    /**
     * Ensures the {@code submitCommand} method propagates a checked exception
     * thrown by the command.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = EngineException.class )
    public void testSubmitCommand_Command_CheckedException()
        throws Exception
    {
        final ICommand<Void> command = new MockCommand<Void>()
        {
            @Override
            public Void execute(
                @SuppressWarnings( "unused" )
                final IEngineContext context )
                throws EngineException
            {
                throw new EngineException();
            }
        };

        try
        {
            m_commandQueue.submitCommand( m_context, command ).get();
        }
        catch( final ExecutionException e )
        {
            if( e.getCause() instanceof EngineException )
            {
                throw (EngineException)e.getCause();
            }

            throw e;
        }
    }

    /**
     * Ensures the {@code submitCommand} method throws an exception when passed
     * a {@code null} command.
     */
    @Test( expected = NullPointerException.class )
    public void testSubmitCommand_Command_Null()
    {
        m_commandQueue.submitCommand( m_context, null );
    }

    /**
     * Ensures the {@code submitCommand} method propagates an unchecked
     * exception thrown by the command.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = UnsupportedOperationException.class )
    public void testSubmitCommand_Command_UncheckedException()
        throws Exception
    {
        final ICommand<Void> command = new MockCommand<Void>()
        {
            @Override
            public Void execute(
                @SuppressWarnings( "unused" )
                final IEngineContext context )
            {
                throw new UnsupportedOperationException();
            }
        };

        try
        {
            m_commandQueue.submitCommand( m_context, command ).get();
        }
        catch( final ExecutionException e )
        {
            final Throwable cause = e.getCause();
            if( cause instanceof EngineException )
            {
                throw (EngineException)cause;
            }

            throw TaskUtils.launderThrowable( cause );
        }
    }

    /**
     * Ensures the {@code submitCommand} method throws an exception when passed
     * a {@code null} context.
     */
    @Test( expected = NullPointerException.class )
    public void testSubmitCommand_Context_Null()
    {
        m_commandQueue.submitCommand( null, new MockCommand<Void>() );
    }

    /**
     * Ensures the {@code submitCommand} returns the expected command result.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testSubmitCommand_ReturnValue_CommandResult()
        throws Exception
    {
        final String expectedResult = "this is the result"; //$NON-NLS-1$
        final ICommand<String> command = new MockCommand<String>()
        {
            @Override
            public String execute(
                @SuppressWarnings( "unused" )
                final IEngineContext context )
            {
                return expectedResult;
            }
        };

        final String actualResult = m_commandQueue.submitCommand( m_context, command ).get();

        assertEquals( expectedResult, actualResult );
    }

    /**
     * Ensures the {@code submitCommand} method throws an exception when the
     * engine has been shut down.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalStateException.class )
    public void testSubmitCommand_Shutdown()
        throws Exception
    {
        final ICommand<Void> command = new MockCommand<Void>();
        shutdownCommandQueue( m_commandQueue );

        m_commandQueue.submitCommand( m_context, command );
    }
}
