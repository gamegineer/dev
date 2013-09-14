/*
 * PersistenceDelegateRegistryAsPersistenceDelegateRegistryTest.java
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
 * Created on May 1, 2010 at 10:20:11 PM.
 */

package org.gamegineer.common.internal.persistence.impl.serializable;

import org.gamegineer.common.persistence.serializable.IPersistenceDelegateRegistry;
import org.gamegineer.common.persistence.serializable.test.AbstractPersistenceDelegateRegistryTestCase;

/**
 * A fixture for testing the {@link PersistenceDelegateRegistry} class to ensure
 * it does not violate the contract of the {@link IPersistenceDelegateRegistry}
 * interface.
 */
public final class PersistenceDelegateRegistryAsPersistenceDelegateRegistryTest
    extends AbstractPersistenceDelegateRegistryTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code PersistenceDelegateRegistryAsPersistenceDelegateRegistryTest}
     * class.
     */
    public PersistenceDelegateRegistryAsPersistenceDelegateRegistryTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.persistence.serializable.test.AbstractPersistenceDelegateRegistryTestCase#createPersistenceDelegateRegistry()
     */
    @Override
    protected IPersistenceDelegateRegistry createPersistenceDelegateRegistry()
    {
        return new PersistenceDelegateRegistry();
    }
}
