/*
 * AbstractAbstractRegistryTestCase.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Nov 16, 2012 at 9:31:28 PM.
 */

package org.gamegineer.common.core.util.registry;

import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that extend the
 * {@link org.gamegineer.common.core.util.registry.AbstractRegistry} class.
 * 
 * @param <ObjectIdType>
 *        The type of object used to identify an object managed by the registry.
 * @param <ObjectType>
 *        The type of object managed by the registry.
 */
public abstract class AbstractAbstractRegistryTestCase<ObjectIdType, ObjectType>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The registry under test in the fixture. */
    private AbstractRegistry<ObjectIdType, ObjectType> registry_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractAbstractRegistryTestCase} class.
     */
    protected AbstractAbstractRegistryTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the registry to be tested.
     * 
     * @return The registry to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract AbstractRegistry<ObjectIdType, ObjectType> createRegistry()
        throws Exception;

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
        registry_ = createRegistry();
        assertNotNull( registry_ );
    }

    /**
     * Ensures the {@link AbstractRegistry#getId} method throws an exception
     * when passed a {@code null} object.
     */
    @Test( expected = NullPointerException.class )
    public void testGetId_Object_Null()
    {
        registry_.getId( null );
    }
}
