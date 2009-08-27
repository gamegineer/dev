/*
 * InternalCommandExecutingEventAsAbstractCommandEventTest.java
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
 * Created on Jun 11, 2008 at 11:25:50 PM.
 */

package org.gamegineer.engine.internal.core.extensions.commandeventmediator;

import org.gamegineer.engine.core.FakeEngineContext;
import org.gamegineer.engine.core.MockCommand;
import org.gamegineer.engine.core.extensions.commandeventmediator.AbstractAbstractCommandEventTestCase;
import org.gamegineer.engine.core.extensions.commandeventmediator.CommandEvent;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.extensions.commandeventmediator.InternalCommandExecutingEvent}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.engine.core.extensions.commandeventmediator.CommandEvent}
 * class.
 */
public final class InternalCommandExecutingEventAsAbstractCommandEventTest
    extends AbstractAbstractCommandEventTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code InternalCommandExecutingEventAsAbstractCommandEventTest} class.
     */
    public InternalCommandExecutingEventAsAbstractCommandEventTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.extensions.commandeventmediator.AbstractAbstractCommandEventTestCase#createCommandEvent()
     */
    @Override
    protected CommandEvent createCommandEvent()
    {
        return InternalCommandExecutingEvent.createCommandExecutingEvent( new FakeEngineContext(), new MockCommand<Void>() );
    }
}
