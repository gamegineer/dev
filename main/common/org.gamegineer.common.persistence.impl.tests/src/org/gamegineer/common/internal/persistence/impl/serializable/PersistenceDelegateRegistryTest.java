/*
 * PersistenceDelegateRegistryTest.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
 * Created on Jul 5, 2010 at 9:31:43 PM.
 */

package org.gamegineer.common.internal.persistence.impl.serializable;

import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link PersistenceDelegateRegistry} class.
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
     * Ensures the
     * {@link PersistenceDelegateRegistry#registerPersistenceDelegate(org.osgi.framework.ServiceReference)}
     * method throws an exception when passed a {@code null} persistence
     * delegate reference.
     */
    @Test( expected = NullPointerException.class )
    public void testRegisterPersistenceDelegateFromServiceReference_PersistenceDelegateReference_Null()
    {
        persistenceDelegateRegistry_.registerPersistenceDelegate( null );
    }

    /**
     * Ensures the
     * {@link PersistenceDelegateRegistry#unregisterPersistenceDelegate(org.osgi.framework.ServiceReference)}
     * method throws an exception when passed a {@code null} persistence
     * delegate reference.
     */
    @Test( expected = NullPointerException.class )
    public void testUnregisterPersistenceDelegateFromServiceReference_PersistenceDelegateReference_Null()
    {
        persistenceDelegateRegistry_.unregisterPersistenceDelegate( null );
    }
}