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

import java.util.Optional;
import org.eclipse.jdt.annotation.NonNull;
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
public abstract class AbstractAbstractRegistryTestCase<RegistryType extends AbstractRegistry<ObjectIdType, ObjectType>, @NonNull ObjectIdType, @NonNull ObjectType>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The registry under test in the fixture. */
    private Optional<RegistryType> registry_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractAbstractRegistryTestCase} class.
     */
    protected AbstractAbstractRegistryTestCase()
    {
        registry_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Invokes the {@link AbstractRegistry#getObjectId} method on the specified
     * registry.
     * 
     * @param registry
     *        The registry.
     * @param object
     *        The object.
     * 
     * @return The identifier of the specified object.
     */
    protected abstract ObjectIdType getObjectId(
        RegistryType registry,
        ObjectType object );

    /**
     * Creates the registry to be tested.
     * 
     * @return The registry to be tested.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    protected abstract RegistryType createRegistry()
        throws Exception;

    /**
     * Gets the registry under test in the fixture.
     * 
     * @return The registry under test in the fixture.
     */
    protected final RegistryType getRegistry()
    {
        return registry_.get();
    }

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
        registry_ = Optional.of( createRegistry() );
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
