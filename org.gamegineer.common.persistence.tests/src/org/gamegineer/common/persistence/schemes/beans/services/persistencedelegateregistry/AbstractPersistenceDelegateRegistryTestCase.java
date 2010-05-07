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
     * Ensures the {@code getClassNames} method returns a copy of the registered
     * class name collection.
     */
    @Test
    public void testGetClassNames_ReturnValue_Copy()
    {
        final Set<String> classNames = persistenceDelegateRegistry_.getClassNames();
        final int expectedClassNamesSize = classNames.size();

        classNames.add( "className" ); //$NON-NLS-1$

        assertEquals( expectedClassNamesSize, persistenceDelegateRegistry_.getClassNames().size() );
    }

    /**
     * Ensures the {@code getClassNames} method returns a snapshot of the
     * registered class name collection.
     */
    @Test
    public void testGetClassNames_ReturnValue_Snapshot()
    {
        final Set<String> classNames = persistenceDelegateRegistry_.getClassNames();
        persistenceDelegateRegistry_.registerPersistenceDelegate( "className", new DefaultPersistenceDelegate() ); //$NON-NLS-1$

        assertTrue( classNames.size() != persistenceDelegateRegistry_.getClassNames().size() );
    }

    /**
     * Ensures the {@code getPersistenceDelegate} method returns the correct
     * value when passed a class name that is absent.
     */
    @Test
    public void testGetPersistenceDelegate_ClassName_Absent()
    {
        assertNull( persistenceDelegateRegistry_.getPersistenceDelegate( "unknownClassName" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getPersistenceDelegate} method throws an exception
     * when passed a {@code null} class name.
     */
    @Test( expected = NullPointerException.class )
    public void testGetPersistenceDelegate_ClassName_Null()
    {
        persistenceDelegateRegistry_.getPersistenceDelegate( null );
    }

    /**
     * Ensures the {@code getPersistenceDelegate} method returns the correct
     * value when passed a class name that is present.
     */
    @Test
    public void testGetPersistenceDelegate_ClassName_Present()
    {
        final String expectedClassName = "className"; //$NON-NLS-1$
        final PersistenceDelegate expectedPersistenceDelegate = new DefaultPersistenceDelegate();
        persistenceDelegateRegistry_.registerPersistenceDelegate( expectedClassName, expectedPersistenceDelegate );

        final PersistenceDelegate actualPersistenceDelegate = persistenceDelegateRegistry_.getPersistenceDelegate( expectedClassName );

        assertSame( expectedPersistenceDelegate, actualPersistenceDelegate );
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
        persistenceDelegateRegistry_.registerPersistenceDelegate( "className", null ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code registerPersistenceDelegate} method properly ignores a
     * persistence delegate whose class name has already been registered for a
     * different persistence delegate.
     */
    @Test
    public void testRegisterPersistenceDelegate_ClassName_Registered_DifferentInstance()
    {
        final String className = "className"; //$NON-NLS-1$
        final PersistenceDelegate expectedPersistenceDelegate = new DefaultPersistenceDelegate();
        final int originalClassNamesSize = persistenceDelegateRegistry_.getClassNames().size();
        persistenceDelegateRegistry_.registerPersistenceDelegate( className, expectedPersistenceDelegate );

        persistenceDelegateRegistry_.registerPersistenceDelegate( className, new DefaultPersistenceDelegate() );

        assertSame( expectedPersistenceDelegate, persistenceDelegateRegistry_.getPersistenceDelegate( className ) );
        assertEquals( originalClassNamesSize + 1, persistenceDelegateRegistry_.getClassNames().size() );
    }

    /**
     * Ensures the {@code registerPersistenceDelegate} method properly ignores a
     * persistence delegate whose class name has already been registered for the
     * same persistence delegate.
     */
    @Test
    public void testRegisterPersistenceDelegate_ClassName_Registered_SameInstance()
    {
        final String className = "className"; //$NON-NLS-1$
        final PersistenceDelegate expectedPersistenceDelegate = new DefaultPersistenceDelegate();
        final int originalClassNamesSize = persistenceDelegateRegistry_.getClassNames().size();
        persistenceDelegateRegistry_.registerPersistenceDelegate( className, expectedPersistenceDelegate );

        persistenceDelegateRegistry_.registerPersistenceDelegate( className, expectedPersistenceDelegate );

        assertSame( expectedPersistenceDelegate, persistenceDelegateRegistry_.getPersistenceDelegate( className ) );
        assertEquals( originalClassNamesSize + 1, persistenceDelegateRegistry_.getClassNames().size() );
    }

    /**
     * Ensures the {@code registerPersistenceDelegate} method registers a
     * persistence delegate whose class name is unregistered.
     */
    @Test
    public void testRegisterPersistenceDelegate_ClassName_Unregistered()
    {
        final String className = "className"; //$NON-NLS-1$
        final PersistenceDelegate expectedPersistenceDelegate = new DefaultPersistenceDelegate();

        persistenceDelegateRegistry_.registerPersistenceDelegate( className, expectedPersistenceDelegate );

        assertSame( expectedPersistenceDelegate, persistenceDelegateRegistry_.getPersistenceDelegate( className ) );
    }

    /**
     * Ensures the {@code unregisterPersistenceDelegate} method throws an
     * exception when passed a {@code null} class name.
     */
    @Test( expected = NullPointerException.class )
    public void testUnregisterPersistenceDelegate_ClassName_Null()
    {
        persistenceDelegateRegistry_.unregisterPersistenceDelegate( null, new DefaultPersistenceDelegate() );
    }

    /**
     * Ensures the {@code unregisterPersistenceDelegate} method throws an
     * exception when passed a {@code null} persistence delegate.
     */
    @Test( expected = NullPointerException.class )
    public void testUnregisterPersistenceDelegate_PersistenceDelegate_Null()
    {
        persistenceDelegateRegistry_.unregisterPersistenceDelegate( "className", null ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code unregisterPersistenceDelegate} method ignores a class
     * name that was previously registered but with a different persistence
     * delegate instance.
     */
    @Test
    public void testUnregisterPersistenceDelegate_ClassName_Registered_DifferentInstance()
    {
        final String className = "className"; //$NON-NLS-1$
        final PersistenceDelegate expectedPersistenceDelegate = new DefaultPersistenceDelegate();
        final int originalClassNamesSize = persistenceDelegateRegistry_.getClassNames().size();
        persistenceDelegateRegistry_.registerPersistenceDelegate( className, expectedPersistenceDelegate );
        assertEquals( originalClassNamesSize + 1, persistenceDelegateRegistry_.getClassNames().size() );

        persistenceDelegateRegistry_.unregisterPersistenceDelegate( className, new DefaultPersistenceDelegate() );

        assertSame( expectedPersistenceDelegate, persistenceDelegateRegistry_.getPersistenceDelegate( className ) );
        assertEquals( originalClassNamesSize + 1, persistenceDelegateRegistry_.getClassNames().size() );
    }

    /**
     * Ensures the {@code unregisterPersistenceDelegate} method unregisters a
     * persistence delegate for a previously registered class name.
     */
    @Test
    public void testUnregisterPersistenceDelegate_ClassName_Registered_SameInstance()
    {
        final String className = "className"; //$NON-NLS-1$
        final PersistenceDelegate persistenceDelegate = new DefaultPersistenceDelegate();
        final int originalClassNamesSize = persistenceDelegateRegistry_.getClassNames().size();
        persistenceDelegateRegistry_.registerPersistenceDelegate( className, persistenceDelegate );
        assertEquals( originalClassNamesSize + 1, persistenceDelegateRegistry_.getClassNames().size() );

        persistenceDelegateRegistry_.unregisterPersistenceDelegate( className, persistenceDelegate );

        assertNull( persistenceDelegateRegistry_.getPersistenceDelegate( className ) );
        assertEquals( originalClassNamesSize, persistenceDelegateRegistry_.getClassNames().size() );
    }

    /**
     * Ensures the {@code unregisterPersistenceDelegate} method properly ignores
     * a class name that was not previously registered.
     */
    @Test
    public void testUnregisterPersistenceDelegate_ClassName_Unregistered()
    {
        final String className = "className"; //$NON-NLS-1$
        final int originalClassNamesSize = persistenceDelegateRegistry_.getClassNames().size();

        persistenceDelegateRegistry_.unregisterPersistenceDelegate( className, new DefaultPersistenceDelegate() );

        assertEquals( originalClassNamesSize, persistenceDelegateRegistry_.getClassNames().size() );
    }
}
