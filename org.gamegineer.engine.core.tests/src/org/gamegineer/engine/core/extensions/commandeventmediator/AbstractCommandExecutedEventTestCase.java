/*
 * AbstractCommandExecutedEventTestCase.java
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
 * Created on May 9, 2008 at 8:49:53 PM.
 */

package org.gamegineer.engine.core.extensions.commandeventmediator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.gamegineer.engine.core.FakeEngineContext;
import org.gamegineer.engine.core.ICommand;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.MockCommand;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.engine.core.extensions.commandeventmediator.ICommandExecutedEvent}
 * interface.
 * 
 * @param <T>
 *        The type of the command executed event.
 */
public abstract class AbstractCommandExecutedEventTestCase<T extends ICommandExecutedEvent>
    extends AbstractCommandEventTestCase<T>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractCommandExecutedEventTestCase} class.
     */
    protected AbstractCommandExecutedEventTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a command executed event that represents failed execution of the
     * specified command with the specified exception.
     * 
     * @param context
     *        The engine context; must not be {@code null}.
     * @param command
     *        The executed command; must not be {@code null}.
     * @param exception
     *        The exception thrown during command execution; must not be
     *        {@code null}.
     * 
     * @return A command executed event that represents failed execution of the
     *         specified command with the specified exception; never
     *         {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context}, {@code command}, or {@code exception} is
     *         {@code null}.
     */
    /* @NonNull */
    protected abstract ICommandExecutedEvent createFailedCommandExecutedEvent(
        /* @NonNull */
        IEngineContext context,
        /* @NonNull */
        ICommand<?> command,
        /* @NonNull */
        Exception exception );

    /**
     * Creates a command executed event that represents successful execution of
     * the specified command with the specified result.
     * 
     * @param context
     *        The engine context; must not be {@code null}.
     * @param command
     *        The executed command; must not be {@code null}.
     * @param result
     *        The result of the command execution; may be {@code null}.
     * 
     * @return A command executed event that represents successful execution of
     *         the specified command with the specified result; never
     *         {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context} or {@code command} is {@code null}.
     */
    /* @NonNull */
    protected abstract ICommandExecutedEvent createSuccessfulCommandExecutedEvent(
        /* @NonNull */
        IEngineContext context,
        /* @NonNull */
        ICommand<?> command,
        /* @Nullable */
        Object result );

    /**
     * Ensures the {@code getException} method does not throw an exception when
     * an exception is present in the event.
     */
    @Test
    public void testGetException_Command_Failed()
    {
        final ICommandExecutedEvent event = createFailedCommandExecutedEvent( new FakeEngineContext(), new MockCommand<Void>(), new Exception() );
        assertTrue( event.hasException() );
        assertNotNull( event.getException() );
    }

    /**
     * Ensures the {@code getException} method throws an exception when no
     * exception is present in the event.
     */
    @Test( expected = IllegalStateException.class )
    public void testGetException_Command_Succeeded()
    {
        final ICommandExecutedEvent event = createSuccessfulCommandExecutedEvent( new FakeEngineContext(), new MockCommand<Void>(), new Object() );
        assertFalse( event.hasException() );
        event.getException();
    }

    /**
     * Ensures the {@code getResult} method throws an exception when an
     * exception is present in the event.
     */
    @Test( expected = IllegalStateException.class )
    public void testGetResult_Command_Failed()
    {
        final ICommandExecutedEvent event = createFailedCommandExecutedEvent( new FakeEngineContext(), new MockCommand<Void>(), new Exception() );
        assertTrue( event.hasException() );
        event.getResult();
    }

    /**
     * Ensures the {@code getResult} method does not throw an exception when no
     * exception is present in the event.
     */
    @Test
    public void testGetResult_Command_Succeeded()
    {
        final ICommandExecutedEvent event = createSuccessfulCommandExecutedEvent( new FakeEngineContext(), new MockCommand<Void>(), new Object() );
        assertFalse( event.hasException() );
        assertNotNull( event.getResult() );
    }
}
