/*
 * AbstractPersistenceDelegateRegistryTestCase.java
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
 * Created on Apr 30, 2010 at 12:05:25 AM.
 */

package org.gamegineer.common.persistence.serializable.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.util.Optional;
import java.util.Set;
import org.gamegineer.common.persistence.serializable.IPersistenceDelegate;
import org.gamegineer.common.persistence.serializable.IPersistenceDelegateRegistry;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link IPersistenceDelegateRegistry} interface.
 */
public abstract class AbstractPersistenceDelegateRegistryTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The persistence delegate registry under test in the fixture. */
    private Optional<IPersistenceDelegateRegistry> persistenceDelegateRegistry_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractPersistenceDelegateRegistryTestCase} class.
     */
    protected AbstractPersistenceDelegateRegistryTestCase()
    {
        persistenceDelegateRegistry_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the persistence delegate registry to be tested.
     * 
     * @return The persistence delegate registry to be tested.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    protected abstract IPersistenceDelegateRegistry createPersistenceDelegateRegistry()
        throws Exception;

    /**
     * Gets the persistence delegate registry under test in the fixture.
     * 
     * @return The persistence delegate registry under test in the fixture.
     */
    protected final IPersistenceDelegateRegistry getPersistenceDelegateRegistry()
    {
        return persistenceDelegateRegistry_.get();
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
        persistenceDelegateRegistry_ = Optional.of( createPersistenceDelegateRegistry() );
    }

    /**
     * Ensures the
     * {@link IPersistenceDelegateRegistry#getPersistenceDelegate(Class)} method
     * returns the correct value when passed a type that is absent.
     */
    @Test
    public void testGetPersistenceDelegateForType_Type_Absent()
    {
        assertNull( getPersistenceDelegateRegistry().getPersistenceDelegate( Object.class ) );
    }

    /**
     * Ensures the
     * {@link IPersistenceDelegateRegistry#getPersistenceDelegate(Class)} method
     * returns the correct value when passed a type that is present.
     */
    @Test
    public void testGetPersistenceDelegateForType_Type_Present()
    {
        final IPersistenceDelegateRegistry persistenceDelegateRegistry = getPersistenceDelegateRegistry();
        final Class<?> expectedType = Object.class;
        final IPersistenceDelegate expectedPersistenceDelegate = new FakePersistenceDelegate();
        persistenceDelegateRegistry.registerPersistenceDelegate( expectedType, expectedPersistenceDelegate );

        final IPersistenceDelegate actualPersistenceDelegate = persistenceDelegateRegistry.getPersistenceDelegate( expectedType );

        assertSame( expectedPersistenceDelegate, actualPersistenceDelegate );
    }

    /**
     * Ensures the
     * {@link IPersistenceDelegateRegistry#getPersistenceDelegate(String)}
     * method returns the correct value when passed a type name that is absent.
     */
    @Test
    public void testGetPersistenceDelegateForTypeName_TypeName_Absent()
    {
        assertNull( getPersistenceDelegateRegistry().getPersistenceDelegate( "unknownTypeName" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the
     * {@link IPersistenceDelegateRegistry#getPersistenceDelegate(String)}
     * method returns the correct value when passed a type name that is present.
     */
    @Test
    public void testGetPersistenceDelegateForTypeName_TypeName_Present()
    {
        final IPersistenceDelegateRegistry persistenceDelegateRegistry = getPersistenceDelegateRegistry();
        final Class<?> expectedType = Object.class;
        final IPersistenceDelegate expectedPersistenceDelegate = new FakePersistenceDelegate();
        persistenceDelegateRegistry.registerPersistenceDelegate( expectedType, expectedPersistenceDelegate );

        final IPersistenceDelegate actualPersistenceDelegate = persistenceDelegateRegistry.getPersistenceDelegate( expectedType.getName() );

        assertSame( expectedPersistenceDelegate, actualPersistenceDelegate );
    }

    /**
     * Ensures the {@link IPersistenceDelegateRegistry#getTypeNames} method
     * returns a copy of the registered type name collection.
     */
    @Test
    public void testGetTypeNames_ReturnValue_Copy()
    {
        final IPersistenceDelegateRegistry persistenceDelegateRegistry = getPersistenceDelegateRegistry();
        final Set<String> typeNames = persistenceDelegateRegistry.getTypeNames();
        final int expectedTypeNamesSize = typeNames.size();

        typeNames.add( Object.class.getName() );

        assertEquals( expectedTypeNamesSize, persistenceDelegateRegistry.getTypeNames().size() );
    }

    /**
     * Ensures the {@link IPersistenceDelegateRegistry#getTypeNames} method
     * returns a snapshot of the registered type name collection.
     */
    @Test
    public void testGetTypeNames_ReturnValue_Snapshot()
    {
        final IPersistenceDelegateRegistry persistenceDelegateRegistry = getPersistenceDelegateRegistry();
        final Set<String> typeNames = persistenceDelegateRegistry.getTypeNames();
        persistenceDelegateRegistry.registerPersistenceDelegate( Object.class, new FakePersistenceDelegate() );

        assertTrue( typeNames.size() != persistenceDelegateRegistry.getTypeNames().size() );
    }

    /**
     * Ensures the
     * {@link IPersistenceDelegateRegistry#registerPersistenceDelegate} method
     * throws an exception when a persistence delegate is already registered for
     * the specified type.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRegisterPersistenceDelegate_Type_Registered()
    {
        final IPersistenceDelegateRegistry persistenceDelegateRegistry = getPersistenceDelegateRegistry();
        final Class<?> type = Object.class;
        persistenceDelegateRegistry.registerPersistenceDelegate( type, new FakePersistenceDelegate() );

        persistenceDelegateRegistry.registerPersistenceDelegate( type, new FakePersistenceDelegate() );
    }

    /**
     * Ensures the
     * {@link IPersistenceDelegateRegistry#registerPersistenceDelegate} method
     * registers a persistence delegate whose type is unregistered.
     */
    @Test
    public void testRegisterPersistenceDelegate_Type_Unregistered()
    {
        final IPersistenceDelegateRegistry persistenceDelegateRegistry = getPersistenceDelegateRegistry();
        final Class<?> type = Object.class;
        final IPersistenceDelegate expectedPersistenceDelegate = new FakePersistenceDelegate();

        persistenceDelegateRegistry.registerPersistenceDelegate( type, expectedPersistenceDelegate );

        assertSame( expectedPersistenceDelegate, persistenceDelegateRegistry.getPersistenceDelegate( type ) );
    }

    /**
     * Ensures the
     * {@link IPersistenceDelegateRegistry#unregisterPersistenceDelegate} method
     * throws an exception when passed a type that was previously registered but
     * with a different persistence delegate instance.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnregisterPersistenceDelegate_Type_Registered_DifferentInstance()
    {
        final IPersistenceDelegateRegistry persistenceDelegateRegistry = getPersistenceDelegateRegistry();
        final Class<?> type = Object.class;
        persistenceDelegateRegistry.registerPersistenceDelegate( type, new FakePersistenceDelegate() );

        persistenceDelegateRegistry.unregisterPersistenceDelegate( type, new FakePersistenceDelegate() );
    }

    /**
     * Ensures the
     * {@link IPersistenceDelegateRegistry#unregisterPersistenceDelegate} method
     * unregisters a persistence delegate for a previously registered type.
     */
    @Test
    public void testUnregisterPersistenceDelegate_Type_Registered_SameInstance()
    {
        final IPersistenceDelegateRegistry persistenceDelegateRegistry = getPersistenceDelegateRegistry();
        final Class<?> type = Object.class;
        final IPersistenceDelegate persistenceDelegate = new FakePersistenceDelegate();
        final int originalTypeNamesSize = persistenceDelegateRegistry.getTypeNames().size();
        persistenceDelegateRegistry.registerPersistenceDelegate( type, persistenceDelegate );
        assertEquals( originalTypeNamesSize + 1, persistenceDelegateRegistry.getTypeNames().size() );

        persistenceDelegateRegistry.unregisterPersistenceDelegate( type, persistenceDelegate );

        assertNull( persistenceDelegateRegistry.getPersistenceDelegate( type ) );
        assertEquals( originalTypeNamesSize, persistenceDelegateRegistry.getTypeNames().size() );
    }

    /**
     * Ensures the
     * {@link IPersistenceDelegateRegistry#unregisterPersistenceDelegate} method
     * throws an exception when passed a type that was not previously
     * registered.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnregisterPersistenceDelegate_Type_Unregistered()
    {
        final Class<?> type = Object.class;

        getPersistenceDelegateRegistry().unregisterPersistenceDelegate( type, new FakePersistenceDelegate() );
    }
}
