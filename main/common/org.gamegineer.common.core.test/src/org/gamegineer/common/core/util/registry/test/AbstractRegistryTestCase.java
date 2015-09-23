/*
 * AbstractRegistryTestCase.java
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
 * Created on Nov 15, 2012 at 10:46:52 PM.
 */

package org.gamegineer.common.core.util.registry.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.util.Collection;
import java.util.Optional;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.core.util.registry.IRegistry;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link IRegistry} interface.
 * 
 * @param <ObjectIdType>
 *        The type of object used to identify an object managed by the registry.
 * @param <ObjectType>
 *        The type of object managed by the registry.
 */
public abstract class AbstractRegistryTestCase<@NonNull ObjectIdType, @NonNull ObjectType>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The registry under test in the fixture. */
    private Optional<IRegistry<ObjectIdType, ObjectType>> registry_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractRegistryTestCase} class.
     */
    protected AbstractRegistryTestCase()
    {
        registry_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Clones the specified object.
     * 
     * @param object
     *        The object to clone; must not be {@code null}.
     * 
     * @return A new object; never {@code null}.
     */
    protected abstract ObjectType cloneObject(
        ObjectType object );

    /**
     * Creates the registry to be tested.
     * 
     * @return The registry to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    protected abstract IRegistry<ObjectIdType, ObjectType> createRegistry()
        throws Exception;

    /**
     * Creates a new object with a unique identifier.
     * 
     * @return A new object; never {@code null}.
     */
    protected abstract ObjectType createUniqueObject();

    /**
     * Gets the identifier of the specified object.
     * 
     * @param object
     *        The object; must not be {@code null}.
     * 
     * @return The identifier of the specified object; never {@code null}.
     */
    protected abstract ObjectIdType getObjectId(
        ObjectType object );

    /**
     * Gets the registry under test in the fixture.
     * 
     * @return The registry under test in the fixture; never {@code null}.
     */
    protected final IRegistry<ObjectIdType, ObjectType> getRegistry()
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
     * Ensures the {@link IRegistry#getObject} method returns the correct value
     * when passed an identifier that is absent.
     */
    @Test
    public void testGetObject_Id_Absent()
    {
        assertNull( getRegistry().getObject( getObjectId( createUniqueObject() ) ) );
    }

    /**
     * Ensures the {@link IRegistry#getObject} method returns the correct value
     * when passed an identifier that is present.
     */
    @Test
    public void testGetObject_Id_Present()
    {
        final IRegistry<ObjectIdType, ObjectType> registry = getRegistry();
        final ObjectType expectedObject = createUniqueObject();
        registry.registerObject( expectedObject );

        final @Nullable ObjectType actualObject = registry.getObject( getObjectId( expectedObject ) );

        assertSame( expectedObject, actualObject );
    }

    /**
     * Ensures the {@link IRegistry#getObjects} method returns a copy of the
     * registered object collection.
     */
    @Test
    public void testGetObjects_ReturnValue_Copy()
    {
        final IRegistry<ObjectIdType, ObjectType> registry = getRegistry();
        final Collection<ObjectType> objects = registry.getObjects();
        final int expectedObjectsSize = objects.size();

        objects.add( createUniqueObject() );

        assertEquals( expectedObjectsSize, registry.getObjects().size() );
    }

    /**
     * Ensures the {@link IRegistry#getObjects} method returns a snapshot of the
     * registered object collection.
     */
    @Test
    public void testGetObjects_ReturnValue_Snapshot()
    {
        final IRegistry<ObjectIdType, ObjectType> registry = getRegistry();
        final Collection<ObjectType> objects = registry.getObjects();
        registry.registerObject( createUniqueObject() );

        assertTrue( objects.size() != registry.getObjects().size() );
    }

    /**
     * Ensures the {@link IRegistry#registerObject} method throws an exception
     * when an object with the same identifier is already registered.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRegisterObject_Object_Registered()
    {
        final IRegistry<ObjectIdType, ObjectType> registry = getRegistry();
        final ObjectType object = createUniqueObject();
        registry.registerObject( object );

        registry.registerObject( cloneObject( object ) );
    }

    /**
     * Ensures the {@link IRegistry#registerObject} method registers an
     * unregistered object.
     */
    @Test
    public void testRegisterObject_Object_Unregistered()
    {
        final IRegistry<ObjectIdType, ObjectType> registry = getRegistry();
        final ObjectType object = createUniqueObject();

        registry.registerObject( object );

        assertTrue( registry.getObjects().contains( object ) );
    }

    /**
     * Ensures the {@link IRegistry#unregisterObject} method throws an exception
     * when passed an object whose identifier was previously registered but by a
     * different object instance.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnregisterObject_Object_Registered_DifferentInstance()
    {
        final IRegistry<ObjectIdType, ObjectType> registry = getRegistry();
        final ObjectType object = createUniqueObject();
        final int originalObjectsSize = registry.getObjects().size();
        registry.registerObject( object );
        assertEquals( originalObjectsSize + 1, registry.getObjects().size() );

        registry.unregisterObject( cloneObject( object ) );
    }

    /**
     * Ensures the {@link IRegistry#unregisterObject} method unregisters a
     * previously registered object.
     */
    @Test
    public void testUnregisterObject_Object_Registered_SameInstance()
    {
        final IRegistry<ObjectIdType, ObjectType> registry = getRegistry();
        final ObjectType object = createUniqueObject();
        final int originalObjectsSize = registry.getObjects().size();
        registry.registerObject( object );
        assertEquals( originalObjectsSize + 1, registry.getObjects().size() );

        registry.unregisterObject( object );

        assertEquals( originalObjectsSize, registry.getObjects().size() );
    }

    /**
     * Ensures the {@link IRegistry#unregisterObject} method throws an exception
     * when passed an object that was not previously registered.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnregisterObject_Object_Unregistered()
    {
        final ObjectType object = createUniqueObject();

        getRegistry().unregisterObject( object );
    }
}
