/*
 * InternalCommandExecutedEventAsCommandExecutedEventTest.java
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
 * Created on May 9, 2008 at 9:12:32 PM.
 */

package org.gamegineer.engine.internal.core.extensions.commandeventmediator;

import org.gamegineer.engine.core.FakeEngineContext;
import org.gamegineer.engine.core.ICommand;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.MockCommand;
import org.gamegineer.engine.core.extensions.commandeventmediator.AbstractCommandExecutedEventTestCase;
import org.gamegineer.engine.core.extensions.commandeventmediator.ICommandExecutedEvent;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.extensions.commandeventmediator.InternalCommandExecutedEvent}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.engine.core.extensions.commandeventmediator.ICommandExecutedEvent}
 * interface.
 */
public final class InternalCommandExecutedEventAsCommandExecutedEventTest
    extends AbstractCommandExecutedEventTestCase<InternalCommandExecutedEvent>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code InternalCommandExecutedEventAsCommandExecutedEventTest} class.
     */
    public InternalCommandExecutedEventAsCommandExecutedEventTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.extensions.commandeventmediator.AbstractCommandEventTestCase#createCommandEvent()
     */
    @Override
    protected InternalCommandExecutedEvent createCommandEvent()
    {
        return InternalCommandExecutedEvent.createSuccessfulCommandExecutedEvent( new FakeEngineContext(), new MockCommand<Void>(), new Object() );
    }

    /*
     * @see org.gamegineer.engine.core.extensions.commandeventmediator.AbstractCommandExecutedEventTestCase#createFailedCommandExecutedEvent(org.gamegineer.engine.core.IEngineContext, org.gamegineer.engine.core.ICommand, java.lang.Exception)
     */
    @Override
    protected ICommandExecutedEvent createFailedCommandExecutedEvent(
        final IEngineContext context,
        final ICommand<?> command,
        final Exception exception )
    {
        return InternalCommandExecutedEvent.createFailedCommandExecutedEvent( context, command, exception );
    }

    /*
     * @see org.gamegineer.engine.core.extensions.commandeventmediator.AbstractCommandExecutedEventTestCase#createSuccessfulCommandExecutedEvent(org.gamegineer.engine.core.IEngineContext, org.gamegineer.engine.core.ICommand, java.lang.Object)
     */
    @Override
    protected ICommandExecutedEvent createSuccessfulCommandExecutedEvent(
        final IEngineContext context,
        final ICommand<?> command,
        final Object result )
    {
        return InternalCommandExecutedEvent.createSuccessfulCommandExecutedEvent( context, command, result );
    }
}
