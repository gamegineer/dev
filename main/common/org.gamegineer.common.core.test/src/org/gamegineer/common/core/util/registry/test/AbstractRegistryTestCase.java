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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.util.Collection;
import org.eclipse.jdt.annotation.DefaultLocation;
import org.eclipse.jdt.annotation.NonNullByDefault;
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
@NonNullByDefault( { DefaultLocation.PARAMETER, DefaultLocation.RETURN_TYPE, DefaultLocation.TYPE_BOUND, DefaultLocation.TYPE_ARGUMENT } )
public abstract class AbstractRegistryTestCase<ObjectIdType, ObjectType>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The registry under test in the fixture. */
    private IRegistry<ObjectIdType, ObjectType> registry_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractRegistryTestCase} class.
     */
    protected AbstractRegistryTestCase()
    {
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
     * Ensures the {@link IRegistry#getObject} method returns the correct value
     * when passed an identifier that is absent.
     */
    @Test
    public void testGetObject_Id_Absent()
    {
        assertNull( registry_.getObject( getObjectId( createUniqueObject() ) ) );
    }

    /**
     * Ensures the {@link IRegistry#getObject} method returns the correct value
     * when passed an identifier that is present.
     */
    @Test
    public void testGetObject_Id_Present()
    {
        final ObjectType expectedObject = createUniqueObject();
        registry_.registerObject( expectedObject );

        final @Nullable ObjectType actualObject = registry_.getObject( getObjectId( expectedObject ) );

        assertSame( expectedObject, actualObject );
    }

    /**
     * Ensures the {@link IRegistry#getObjects} method returns a copy of the
     * registered object collection.
     */
    @Test
    public void testGetObjects_ReturnValue_Copy()
    {
        final Collection<ObjectType> objects = registry_.getObjects();
        final int expectedObjectsSize = objects.size();

        objects.add( createUniqueObject() );

        assertEquals( expectedObjectsSize, registry_.getObjects().size() );
    }

    /**
     * Ensures the {@link IRegistry#getObjects} method returns a snapshot of the
     * registered object collection.
     */
    @Test
    public void testGetObjects_ReturnValue_Snapshot()
    {
        final Collection<ObjectType> objects = registry_.getObjects();
        registry_.registerObject( createUniqueObject() );

        assertTrue( objects.size() != registry_.getObjects().size() );
    }

    /**
     * Ensures the {@link IRegistry#registerObject} method throws an exception
     * when an object with the same identifier is already registered.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRegisterObject_Object_Registered()
    {
        final ObjectType object = createUniqueObject();
        registry_.registerObject( object );

        registry_.registerObject( cloneObject( object ) );
    }

    /**
     * Ensures the {@link IRegistry#registerObject} method registers an
     * unregistered object.
     */
    @Test
    public void testRegisterObject_Object_Unregistered()
    {
        final ObjectType object = createUniqueObject();

        registry_.registerObject( object );

        assertTrue( registry_.getObjects().contains( object ) );
    }

    /**
     * Ensures the {@link IRegistry#unregisterObject} method throws an exception
     * when passed an object whose identifier was previously registered but by a
     * different object instance.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnregisterObject_Object_Registered_DifferentInstance()
    {
        final ObjectType object = createUniqueObject();
        final int originalObjectsSize = registry_.getObjects().size();
        registry_.registerObject( object );
        assertEquals( originalObjectsSize + 1, registry_.getObjects().size() );

        registry_.unregisterObject( cloneObject( object ) );
    }

    /**
     * Ensures the {@link IRegistry#unregisterObject} method unregisters a
     * previously registered object.
     */
    @Test
    public void testUnregisterObject_Object_Registered_SameInstance()
    {
        final ObjectType object = createUniqueObject();
        final int originalObjectsSize = registry_.getObjects().size();
        registry_.registerObject( object );
        assertEquals( originalObjectsSize + 1, registry_.getObjects().size() );

        registry_.unregisterObject( object );

        assertEquals( originalObjectsSize, registry_.getObjects().size() );
    }

    /**
     * Ensures the {@link IRegistry#unregisterObject} method throws an exception
     * when passed an object that was not previously registered.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnregisterObject_Object_Unregistered()
    {
        final ObjectType object = createUniqueObject();

        registry_.unregisterObject( object );
    }
}
