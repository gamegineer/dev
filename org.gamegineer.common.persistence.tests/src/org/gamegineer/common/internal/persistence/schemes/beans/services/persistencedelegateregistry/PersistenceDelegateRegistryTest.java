/*
 * PersistenceDelegateRegistryTest.java
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
 * Created on Jul 9, 2010 at 11:10:08 PM.
 */

package org.gamegineer.common.internal.persistence.schemes.beans.services.persistencedelegateregistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.internal.persistence.schemes.beans.services.persistencedelegateregistry.PersistenceDelegateRegistry}
 * class.
 */
public final class PersistenceDelegateRegistryTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The persistence delegate registry under test in the fixture. */
    private PersistenceDelegateRegistry persistenceDelegateRegistry_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code PersistenceDelegateRegistryTest}
     * class.
     */
    public PersistenceDelegateRegistryTest()
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
        persistenceDelegateRegistry_ = new PersistenceDelegateRegistry();
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
     * Ensures the {@code registerPersistenceDelegate(ServiceReference)} method
     * throws an exception when passed a {@code null} persistence delegate
     * reference.
     */
    @Test( expected = NullPointerException.class )
    public void testRegisterPersistenceDelegateFromServiceReference_PersistenceDelegateReference_Null()
    {
        persistenceDelegateRegistry_.registerPersistenceDelegate( null );
    }

    /**
     * Ensures the {@code unregisterPersistenceDelegate(ServiceReference)}
     * method throws an exception when passed a {@code null} persistence
     * delegate reference.
     */
    @Test( expected = NullPointerException.class )
    public void testUnregisterPersistenceDelegateFromServiceReference_PersistenceDelegateReference_Null()
    {
        persistenceDelegateRegistry_.unregisterPersistenceDelegate( null );
    }
}
