/*
 * AbstractPersistenceDelegateRegistryTestCase.java
 * Copyright 2008-2010 Gamegineer.org
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
 * Created on May 6, 2010 at 11:39:22 PM.
 */

package org.gamegineer.common.persistence.schemes.beans.services.persistencedelegateregistry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.beans.DefaultPersistenceDelegate;
import java.beans.PersistenceDelegate;
import java.util.Set;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.common.persistence.schemes.beans.services.persistencedelegateregistry.IPersistenceDelegateRegistry}
 * interface.
 */
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
     * Initializes a new instance of the {@code
     * AbstractPersistenceDelegateRegistryTestCase} class.
     */
    protected AbstractPersistenceDelegateRegistryTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the persistence delegate registry to be tested.
     * 
     * @return The persistence delegate registry to be tested; never {@code
     *         null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
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
     * Tears down the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @After
    public void tearDown()
        throws Exception
    {
        persistenceDelegateRegistry_ = null;
    }

    /**
     * Ensures the {@code getPersistenceDelegate(Class<?>)} method returns the
     * correct value when passed a type that is absent.
     */
    @Test
    public void testGetPersistenceDelegateForType_Type_Absent()
    {
        assertNull( persistenceDelegateRegistry_.getPersistenceDelegate( Object.class ) );
    }

    /**
     * Ensures the {@code getPersistenceDelegate(Class<?>)} method throws an
     * exception when passed a {@code null} type.
     */
    @Test( expected = NullPointerException.class )
    public void testGetPersistenceDelegateForType_Type_Null()
    {
        persistenceDelegateRegistry_.getPersistenceDelegate( (Class<?>)null );
    }

    /**
     * Ensures the {@code getPersistenceDelegate(Class<?>)} method returns the
     * correct value when passed a type that is present.
     */
    @Test
    public void testGetPersistenceDelegateForType_Type_Present()
    {
        final Class<?> expectedType = Object.class;
        final PersistenceDelegate expectedPersistenceDelegate = new DefaultPersistenceDelegate();
        persistenceDelegateRegistry_.registerPersistenceDelegate( expectedType, expectedPersistenceDelegate );

        final PersistenceDelegate actualPersistenceDelegate = persistenceDelegateRegistry_.getPersistenceDelegate( expectedType );

        assertSame( expectedPersistenceDelegate, actualPersistenceDelegate );
    }

    /**
     * Ensures the {@code getPersistenceDelegate(String)} method returns the
     * correct value when passed a type name that is absent.
     */
    @Test
    public void testGetPersistenceDelegateForTypeName_TypeName_Absent()
    {
        assertNull( persistenceDelegateRegistry_.getPersistenceDelegate( "unknownClassName" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getPersistenceDelegate(String)} method throws an
     * exception when passed a {@code null} type name.
     */
    @Test( expected = NullPointerException.class )
    public void testGetPersistenceDelegateForTypeName_TypeName_Null()
    {
        persistenceDelegateRegistry_.getPersistenceDelegate( (String)null );
    }

    /**
     * Ensures the {@code getPersistenceDelegate(String)} method returns the
     * correct value when passed a type name that is present.
     */
    @Test
    public void testGetPersistenceDelegateForTypeName_TypeName_Present()
    {
        final Class<?> expectedType = Object.class;
        final PersistenceDelegate expectedPersistenceDelegate = new DefaultPersistenceDelegate();
        persistenceDelegateRegistry_.registerPersistenceDelegate( expectedType, expectedPersistenceDelegate );

        final PersistenceDelegate actualPersistenceDelegate = persistenceDelegateRegistry_.getPersistenceDelegate( expectedType.getName() );

        assertSame( expectedPersistenceDelegate, actualPersistenceDelegate );
    }

    /**
     * Ensures the {@code getTypeNames} method returns a copy of the registered
     * type name collection.
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
     * Ensures the {@code getTypeNames} method returns a snapshot of the
     * registered type name collection.
     */
    @Test
    public void testGetTypeNames_ReturnValue_Snapshot()
    {
        final Set<String> typeNames = persistenceDelegateRegistry_.getTypeNames();
        persistenceDelegateRegistry_.registerPersistenceDelegate( Object.class, new DefaultPersistenceDelegate() );

        assertTrue( typeNames.size() != persistenceDelegateRegistry_.getTypeNames().size() );
    }

    /**
     * Ensures the {@code registerPersistenceDelegate} method throws an
     * exception when passed a {@code null} class name.
     */
    @Test( expected = NullPointerException.class )
    public void testRegisterPersistenceDelegate_ClassName_Null()
    {
        persistenceDelegateRegistry_.registerPersistenceDelegate( null, new DefaultPersistenceDelegate() );
    }

    /**
     * Ensures the {@code registerPersistenceDelegate} method throws an
     * exception when passed a {@code null} persistence delegate.
     */
    @Test( expected = NullPointerException.class )
    public void testRegisterPersistenceDelegate_PersistenceDelegate_Null()
    {
        persistenceDelegateRegistry_.registerPersistenceDelegate( Object.class, null );
    }

    /**
     * Ensures the {@code registerPersistenceDelegate} method properly ignores a
     * persistence delegate whose type has already been registered for a
     * different persistence delegate.
     */
    @Test
    public void testRegisterPersistenceDelegate_Type_Registered_DifferentInstance()
    {
        final Class<?> type = Object.class;
        final PersistenceDelegate expectedPersistenceDelegate = new DefaultPersistenceDelegate();
        final int originalTypeNamesSize = persistenceDelegateRegistry_.getTypeNames().size();
        persistenceDelegateRegistry_.registerPersistenceDelegate( type, expectedPersistenceDelegate );

        persistenceDelegateRegistry_.registerPersistenceDelegate( type, new DefaultPersistenceDelegate() );

        assertSame( expectedPersistenceDelegate, persistenceDelegateRegistry_.getPersistenceDelegate( type ) );
        assertEquals( originalTypeNamesSize + 1, persistenceDelegateRegistry_.getTypeNames().size() );
    }

    /**
     * Ensures the {@code registerPersistenceDelegate} method properly ignores a
     * persistence delegate whose type has already been registered for the same
     * persistence delegate.
     */
    @Test
    public void testRegisterPersistenceDelegate_Type_Registered_SameInstance()
    {
        final Class<?> type = Object.class;
        final PersistenceDelegate expectedPersistenceDelegate = new DefaultPersistenceDelegate();
        final int originalTypeNamesSize = persistenceDelegateRegistry_.getTypeNames().size();
        persistenceDelegateRegistry_.registerPersistenceDelegate( type, expectedPersistenceDelegate );

        persistenceDelegateRegistry_.registerPersistenceDelegate( type, expectedPersistenceDelegate );

        assertSame( expectedPersistenceDelegate, persistenceDelegateRegistry_.getPersistenceDelegate( type ) );
        assertEquals( originalTypeNamesSize + 1, persistenceDelegateRegistry_.getTypeNames().size() );
    }

    /**
     * Ensures the {@code registerPersistenceDelegate} method registers a
     * persistence delegate whose type is unregistered.
     */
    @Test
    public void testRegisterPersistenceDelegate_Type_Unregistered()
    {
        final Class<?> type = Object.class;
        final PersistenceDelegate expectedPersistenceDelegate = new DefaultPersistenceDelegate();

        persistenceDelegateRegistry_.registerPersistenceDelegate( type, expectedPersistenceDelegate );

        assertSame( expectedPersistenceDelegate, persistenceDelegateRegistry_.getPersistenceDelegate( type ) );
    }

    /**
     * Ensures the {@code unregisterPersistenceDelegate} method throws an
     * exception when passed a {@code null} persistence delegate.
     */
    @Test( expected = NullPointerException.class )
    public void testUnregisterPersistenceDelegate_PersistenceDelegate_Null()
    {
        persistenceDelegateRegistry_.unregisterPersistenceDelegate( Object.class, null );
    }

    /**
     * Ensures the {@code unregisterPersistenceDelegate} method throws an
     * exception when passed a {@code null} type.
     */
    @Test( expected = NullPointerException.class )
    public void testUnregisterPersistenceDelegate_Type_Null()
    {
        persistenceDelegateRegistry_.unregisterPersistenceDelegate( null, new DefaultPersistenceDelegate() );
    }

    /**
     * Ensures the {@code unregisterPersistenceDelegate} method ignores a type
     * that was previously registered but with a different persistence delegate
     * instance.
     */
    @Test
    public void testUnregisterPersistenceDelegate_Type_Registered_DifferentInstance()
    {
        final Class<?> type = Object.class;
        final PersistenceDelegate expectedPersistenceDelegate = new DefaultPersistenceDelegate();
        final int originalTypeNamesSize = persistenceDelegateRegistry_.getTypeNames().size();
        persistenceDelegateRegistry_.registerPersistenceDelegate( type, expectedPersistenceDelegate );
        assertEquals( originalTypeNamesSize + 1, persistenceDelegateRegistry_.getTypeNames().size() );

        persistenceDelegateRegistry_.unregisterPersistenceDelegate( type, new DefaultPersistenceDelegate() );

        assertSame( expectedPersistenceDelegate, persistenceDelegateRegistry_.getPersistenceDelegate( type ) );
        assertEquals( originalTypeNamesSize + 1, persistenceDelegateRegistry_.getTypeNames().size() );
    }

    /**
     * Ensures the {@code unregisterPersistenceDelegate} method unregisters a
     * persistence delegate for a previously registered type.
     */
    @Test
    public void testUnregisterPersistenceDelegate_Type_Registered_SameInstance()
    {
        final Class<?> type = Object.class;
        final PersistenceDelegate persistenceDelegate = new DefaultPersistenceDelegate();
        final int originalTypeNamesSize = persistenceDelegateRegistry_.getTypeNames().size();
        persistenceDelegateRegistry_.registerPersistenceDelegate( type, persistenceDelegate );
        assertEquals( originalTypeNamesSize + 1, persistenceDelegateRegistry_.getTypeNames().size() );

        persistenceDelegateRegistry_.unregisterPersistenceDelegate( type, persistenceDelegate );

        assertNull( persistenceDelegateRegistry_.getPersistenceDelegate( type ) );
        assertEquals( originalTypeNamesSize, persistenceDelegateRegistry_.getTypeNames().size() );
    }

    /**
     * Ensures the {@code unregisterPersistenceDelegate} method properly ignores
     * a type that was not previously registered.
     */
    @Test
    public void testUnregisterPersistenceDelegate_Type_Unregistered()
    {
        final Class<?> type = Object.class;
        final int originalTypeNamesSize = persistenceDelegateRegistry_.getTypeNames().size();

        persistenceDelegateRegistry_.unregisterPersistenceDelegate( type, new DefaultPersistenceDelegate() );

        assertEquals( originalTypeNamesSize, persistenceDelegateRegistry_.getTypeNames().size() );
    }
}
