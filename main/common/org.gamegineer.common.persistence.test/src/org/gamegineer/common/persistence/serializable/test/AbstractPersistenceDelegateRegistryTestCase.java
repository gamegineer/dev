/*
 * AbstractPersistenceDelegateRegistryTestCase.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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

import static org.gamegineer.common.core.runtime.NullAnalysis.nonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.util.Set;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.gamegineer.common.persistence.serializable.IPersistenceDelegate;
import org.gamegineer.common.persistence.serializable.IPersistenceDelegateRegistry;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link IPersistenceDelegateRegistry} interface.
 */
@NonNullByDefault( false )
public abstract class AbstractPersistenceDelegateRegistryTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The persistence delegate registry under test in the fixture. */
    private IPersistenceDelegateRegistry persistenceDelegateRegistry_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractPersistenceDelegateRegistryTestCase} class.
     */
    protected AbstractPersistenceDelegateRegistryTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the persistence delegate registry to be tested.
     * 
     * @return The persistence delegate registry to be tested; never
     *         {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @NonNull
    protected abstract IPersistenceDelegateRegistry createPersistenceDelegateRegistry()
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
        persistenceDelegateRegistry_ = createPersistenceDelegateRegistry();
        assertNotNull( persistenceDelegateRegistry_ );
    }

    /**
     * Ensures the
     * {@link IPersistenceDelegateRegistry#getPersistenceDelegate(Class)} method
     * returns the correct value when passed a type that is absent.
     */
    @Test
    public void testGetPersistenceDelegateForType_Type_Absent()
    {
        assertNull( persistenceDelegateRegistry_.getPersistenceDelegate( Object.class ) );
    }

    /**
     * Ensures the
     * {@link IPersistenceDelegateRegistry#getPersistenceDelegate(Class)} method
     * returns the correct value when passed a type that is present.
     */
    @Test
    public void testGetPersistenceDelegateForType_Type_Present()
    {
        final Class<?> expectedType = Object.class;
        final IPersistenceDelegate expectedPersistenceDelegate = new FakePersistenceDelegate();
        persistenceDelegateRegistry_.registerPersistenceDelegate( expectedType, expectedPersistenceDelegate );

        final IPersistenceDelegate actualPersistenceDelegate = persistenceDelegateRegistry_.getPersistenceDelegate( expectedType );

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
        assertNull( persistenceDelegateRegistry_.getPersistenceDelegate( "unknownTypeName" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the
     * {@link IPersistenceDelegateRegistry#getPersistenceDelegate(String)}
     * method returns the correct value when passed a type name that is present.
     */
    @Test
    public void testGetPersistenceDelegateForTypeName_TypeName_Present()
    {
        final Class<?> expectedType = Object.class;
        final IPersistenceDelegate expectedPersistenceDelegate = new FakePersistenceDelegate();
        persistenceDelegateRegistry_.registerPersistenceDelegate( expectedType, expectedPersistenceDelegate );

        final IPersistenceDelegate actualPersistenceDelegate = persistenceDelegateRegistry_.getPersistenceDelegate( nonNull( expectedType.getName() ) );

        assertSame( expectedPersistenceDelegate, actualPersistenceDelegate );
    }

    /**
     * Ensures the {@link IPersistenceDelegateRegistry#getTypeNames} method
     * returns a copy of the registered type name collection.
     */
    @Test
    public void testGetTypeNames_ReturnValue_Copy()
    {
        final Set<String> typeNames = persistenceDelegateRegistry_.getTypeNames();
        final int expectedTypeNamesSize = typeNames.size();

        typeNames.add( Object.class.getName() );

        assertEquals( expectedTypeNamesSize, persistenceDelegateRegistry_.getTypeNames().size() );
    }

    /**
     * Ensures the {@link IPersistenceDelegateRegistry#getTypeNames} method
     * returns a snapshot of the registered type name collection.
     */
    @Test
    public void testGetTypeNames_ReturnValue_Snapshot()
    {
        final Set<String> typeNames = persistenceDelegateRegistry_.getTypeNames();
        persistenceDelegateRegistry_.registerPersistenceDelegate( Object.class, new FakePersistenceDelegate() );

        assertTrue( typeNames.size() != persistenceDelegateRegistry_.getTypeNames().size() );
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
        final Class<?> type = Object.class;
        persistenceDelegateRegistry_.registerPersistenceDelegate( type, new FakePersistenceDelegate() );

        persistenceDelegateRegistry_.registerPersistenceDelegate( type, new FakePersistenceDelegate() );
    }

    /**
     * Ensures the
     * {@link IPersistenceDelegateRegistry#registerPersistenceDelegate} method
     * registers a persistence delegate whose type is unregistered.
     */
    @Test
    public void testRegisterPersistenceDelegate_Type_Unregistered()
    {
        final Class<?> type = Object.class;
        final IPersistenceDelegate expectedPersistenceDelegate = new FakePersistenceDelegate();

        persistenceDelegateRegistry_.registerPersistenceDelegate( type, expectedPersistenceDelegate );

        assertSame( expectedPersistenceDelegate, persistenceDelegateRegistry_.getPersistenceDelegate( type ) );
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
        final Class<?> type = Object.class;
        persistenceDelegateRegistry_.registerPersistenceDelegate( type, new FakePersistenceDelegate() );

        persistenceDelegateRegistry_.unregisterPersistenceDelegate( type, new FakePersistenceDelegate() );
    }

    /**
     * Ensures the
     * {@link IPersistenceDelegateRegistry#unregisterPersistenceDelegate} method
     * unregisters a persistence delegate for a previously registered type.
     */
    @Test
    public void testUnregisterPersistenceDelegate_Type_Registered_SameInstance()
    {
        final Class<?> type = Object.class;
        final IPersistenceDelegate persistenceDelegate = new FakePersistenceDelegate();
        final int originalTypeNamesSize = persistenceDelegateRegistry_.getTypeNames().size();
        persistenceDelegateRegistry_.registerPersistenceDelegate( type, persistenceDelegate );
        assertEquals( originalTypeNamesSize + 1, persistenceDelegateRegistry_.getTypeNames().size() );

        persistenceDelegateRegistry_.unregisterPersistenceDelegate( type, persistenceDelegate );

        assertNull( persistenceDelegateRegistry_.getPersistenceDelegate( type ) );
        assertEquals( originalTypeNamesSize, persistenceDelegateRegistry_.getTypeNames().size() );
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

        persistenceDelegateRegistry_.unregisterPersistenceDelegate( type, new FakePersistenceDelegate() );
    }
}
