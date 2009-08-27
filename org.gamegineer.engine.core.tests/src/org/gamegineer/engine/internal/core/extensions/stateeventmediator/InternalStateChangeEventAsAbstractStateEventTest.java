/*
 * InternalStateChangeEventAsAbstractStateEventTest.java
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
 * Created on Jun 12, 2008 at 10:11:13 PM.
 */

package org.gamegineer.engine.internal.core.extensions.stateeventmediator;

import java.util.Collections;
import org.gamegineer.engine.core.AttributeName;
import org.gamegineer.engine.core.FakeEngineContext;
import org.gamegineer.engine.core.extensions.stateeventmediator.AbstractAbstractStateEventTestCase;
import org.gamegineer.engine.core.extensions.stateeventmediator.IAttributeChange;
import org.gamegineer.engine.core.extensions.stateeventmediator.StateEvent;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.extensions.stateeventmediator.InternalStateChangeEvent}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.engine.core.extensions.stateeventmediator.StateEvent}
 * class.
 */
public final class InternalStateChangeEventAsAbstractStateEventTest
    extends AbstractAbstractStateEventTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code InternalStateChangeEventAsAbstractStateEventTest} class.
     */
    public InternalStateChangeEventAsAbstractStateEventTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.extensions.stateeventmediator.AbstractAbstractStateEventTestCase#createStateEvent()
     */
    @Override
    protected StateEvent createStateEvent()
    {
        return InternalStateChangeEvent.createStateChangeEvent( new FakeEngineContext(), Collections.<AttributeName, IAttributeChange>emptyMap() );
    }
}
