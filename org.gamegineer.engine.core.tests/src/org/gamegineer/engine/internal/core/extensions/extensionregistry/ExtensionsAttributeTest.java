/*
 * ExtensionsAttributeTest.java
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
 * Created on Sep 7, 2008 at 10:48:00 PM.
 */

package org.gamegineer.engine.internal.core.extensions.extensionregistry;

import static org.gamegineer.test.core.Assert.assertImmutableMap;
import java.util.HashMap;
import java.util.Map;
import org.gamegineer.engine.core.FakeState;
import org.gamegineer.engine.core.IExtension;
import org.gamegineer.engine.core.IState;
import org.gamegineer.engine.core.util.attribute.IAttribute;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.extensions.extensionregistry.ExtensionRegistryExtension#EXTENSIONS_ATTRIBUTE}
 * class.
 */
public final class ExtensionsAttributeTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The attribute under test in the fixture. */
    private IAttribute<Map<Class<?>, IExtension>> attribute_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ExtensionsAttributeTest} class.
     */
    public ExtensionsAttributeTest()
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
        attribute_ = ExtensionRegistryExtensionFacade.EXTENSIONS_ATTRIBUTE();
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
        attribute_ = null;
    }

    /**
     * Ensures the {@code add} method adds the attribute value to the engine
     * state as an immutable map.
     */
    @Test
    public void testAdd_Value_Immutable()
    {
        final IState state = new FakeState();

        attribute_.add( state, new HashMap<Class<?>, IExtension>() );

        assertImmutableMap( attribute_.getValue( state ) );
    }

    /**
     * Ensures the {@code setValue} method adds the attribute value to the
     * engine state as an immutable map.
     */
    @Test
    public void testSetValue_Value_Immutable()
    {
        final IState state = new FakeState();
        attribute_.add( state, new HashMap<Class<?>, IExtension>() );

        attribute_.setValue( state, new HashMap<Class<?>, IExtension>() );

        assertImmutableMap( attribute_.getValue( state ) );
    }
}
