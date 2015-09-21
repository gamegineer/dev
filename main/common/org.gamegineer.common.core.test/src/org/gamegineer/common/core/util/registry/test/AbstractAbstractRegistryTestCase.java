/*
 * AbstractAbstractRegistryTestCase.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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

package org.gamegineer.common.core.util.registry.test;

import static org.junit.Assert.assertNotNull;
import org.eclipse.jdt.annotation.DefaultLocation;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.gamegineer.common.core.util.registry.AbstractRegistry;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that extend the
 * {@link AbstractRegistry} class.
 * 
 * @param <RegistryType>
 *        The type of the registry.
 * @param <ObjectIdType>
 *        The type of object used to identify an object managed by the registry.
 * @param <ObjectType>
 *        The type of object managed by the registry.
 */
@NonNullByDefault( { DefaultLocation.PARAMETER, DefaultLocation.RETURN_TYPE, DefaultLocation.TYPE_BOUND, DefaultLocation.TYPE_ARGUMENT } )
public abstract class AbstractAbstractRegistryTestCase<RegistryType extends AbstractRegistry<ObjectIdType, ObjectType>, @NonNull ObjectIdType, @NonNull ObjectType>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The registry under test in the fixture. */
    private RegistryType registry_;


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
     * Invokes the {@link AbstractRegistry#getObjectId} method on the specified
     * registry.
     * 
     * @param registry
     *        The registry; must not be {@code null}.
     * @param object
     *        The object; must not be {@code null}.
     * 
     * @return The identifier of the specified object; never {@code null}.
     */
    protected abstract ObjectIdType getObjectId(
        RegistryType registry,
        ObjectType object );

    /**
     * Creates the registry to be tested.
     * 
     * @return The registry to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    protected abstract RegistryType createRegistry()
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
     * Placeholder for future interface tests.
     */
    @Test
    public void testDummy()
    {
        // do nothing
    }
}
