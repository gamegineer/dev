/*
 * CommandEventDelegateAsCommandEventTest.java
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
 * Created on Jun 2, 2008 at 11:33:35 PM.
 */

package org.gamegineer.engine.internal.core.extensions.commandeventmediator;

import org.gamegineer.engine.core.FakeEngineContext;
import org.gamegineer.engine.core.MockCommand;
import org.gamegineer.engine.core.extensions.commandeventmediator.AbstractCommandEventTestCase;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.extensions.commandeventmediator.CommandEventDelegate}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.engine.core.extensions.commandeventmediator.ICommandEvent}
 * interface.
 */
public final class CommandEventDelegateAsCommandEventTest
    extends AbstractCommandEventTestCase<CommandEventDelegate>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code CommandEventDelegateAsCommandEventTest} class.
     */
    public CommandEventDelegateAsCommandEventTest()
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
    protected CommandEventDelegate createCommandEvent()
    {
        return new CommandEventDelegate( new FakeEngineContext(), new MockCommand<Void>() );
    }
}
