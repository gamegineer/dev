/*
 * StateListenersAttributeTest.java
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
 * Created on Sep 7, 2008 at 11:56:47 PM.
 */

package org.gamegineer.engine.internal.core.extensions.stateeventmediator;

import static org.gamegineer.test.core.Assert.assertImmutableCollection;
import java.util.ArrayList;
import java.util.List;
import org.gamegineer.engine.core.FakeState;
import org.gamegineer.engine.core.IState;
import org.gamegineer.engine.core.extensions.stateeventmediator.IStateListener;
import org.gamegineer.engine.core.util.attribute.IAttribute;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.extensions.stateeventmediator.StateEventMediatorExtension#STATE_LISTENERS_ATTRIBUTE}
 * class.
 */
public final class StateListenersAttributeTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The attribute under test in the fixture. */
    private IAttribute<List<IStateListener>> m_attribute;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code StateListenersAttributeTest}
     * class.
     */
    public StateListenersAttributeTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

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
        m_attribute = StateEventMediatorExtensionFacade.STATE_LISTENERS_ATTRIBUTE();
    }

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
        m_attribute = null;
    }

    /**
     * Ensures the {@code add} method adds the attribute value to the engine
     * state as an immutable list.
     */
    @Test
    public void testAdd_Value_Immutable()
    {
        final IState state = new FakeState();

        m_attribute.add( state, new ArrayList<IStateListener>() );

        assertImmutableCollection( m_attribute.getValue( state ) );
    }

    /**
     * Ensures the {@code setValue} method adds the attribute value to the
     * engine state as an immutable map.
     */
    @Test
    public void testSetValue_Value_Immutable()
    {
        final IState state = new FakeState();
        m_attribute.add( state, new ArrayList<IStateListener>() );

        m_attribute.setValue( state, new ArrayList<IStateListener>() );

        assertImmutableCollection( m_attribute.getValue( state ) );
    }
}
