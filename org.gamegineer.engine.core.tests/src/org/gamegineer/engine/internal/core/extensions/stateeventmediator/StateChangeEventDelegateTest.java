/*
 * StateChangeEventDelegateTest.java
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
 * Created on Jun 2, 2008 at 9:30:21 PM.
 */

package org.gamegineer.engine.internal.core.extensions.stateeventmediator;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import java.util.Collections;
import org.gamegineer.engine.core.AttributeName;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.extensions.stateeventmediator.IAttributeChange;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.extensions.stateeventmediator.StateChangeEventDelegate}
 * class.
 */
public final class StateChangeEventDelegateTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code StateChangeEventDelegateTest}
     * class.
     */
    public StateChangeEventDelegateTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * attribute change collection.
     */
    @Test( expected = AssertionError.class )
    public void testConstructor_AttributeChanges_Null()
    {
        new StateChangeEventDelegate( createDummy( IEngineContext.class ), null );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * context.
     */
    @Test( expected = AssertionError.class )
    public void testConstructor_Context_Null()
    {
        new StateChangeEventDelegate( null, Collections.<AttributeName, IAttributeChange>emptyMap() );
    }
}
