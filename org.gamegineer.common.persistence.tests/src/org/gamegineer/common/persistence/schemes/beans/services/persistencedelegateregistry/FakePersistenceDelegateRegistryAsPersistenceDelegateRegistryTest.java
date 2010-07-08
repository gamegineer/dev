/*
 * FakePersistenceDelegateRegistryAsPersistenceDelegateRegistryTest.java
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
 * Created on Jun 29, 2010 at 11:10:51 PM.
 */

package org.gamegineer.common.persistence.schemes.beans.services.persistencedelegateregistry;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.persistence.schemes.beans.services.persistencedelegateregistry.FakePersistenceDelegateRegistry}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.common.persistence.schemes.beans.services.persistencedelegateregistry.IPersistenceDelegateRegistry}
 * interface.
 */
public final class FakePersistenceDelegateRegistryAsPersistenceDelegateRegistryTest
    extends AbstractPersistenceDelegateRegistryTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * FakePersistenceDelegateRegistryAsPersistenceDelegateRegistryTest} class.
     */
    public FakePersistenceDelegateRegistryAsPersistenceDelegateRegistryTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.persistence.schemes.beans.services.persistencedelegateregistry.AbstractPersistenceDelegateRegistryTestCase#createPersistenceDelegateRegistry()
     */
    @Override
    protected IPersistenceDelegateRegistry createPersistenceDelegateRegistry()
    {
        return new FakePersistenceDelegateRegistry();
    }
}