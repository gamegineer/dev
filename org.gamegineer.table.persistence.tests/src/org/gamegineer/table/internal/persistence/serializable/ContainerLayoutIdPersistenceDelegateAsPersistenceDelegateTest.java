/*
 * ContainerLayoutIdPersistenceDelegateAsPersistenceDelegateTest.java
 * Copyright 2008-2012 Gamegineer contributors and others.
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
 * Created on Aug 11, 2012 at 8:27:30 PM.
 */

package org.gamegineer.table.internal.persistence.serializable;

import org.gamegineer.common.persistence.serializable.AbstractPersistenceDelegateTestCase;
import org.gamegineer.common.persistence.serializable.IPersistenceDelegate;
import org.gamegineer.common.persistence.serializable.IPersistenceDelegateRegistry;
import org.gamegineer.table.core.ContainerLayoutId;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.persistence.serializable.ContainerLayoutIdPersistenceDelegate}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.common.persistence.serializable.IPersistenceDelegate}
 * interface.
 */
public final class ContainerLayoutIdPersistenceDelegateAsPersistenceDelegateTest
    extends AbstractPersistenceDelegateTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ContainerLayoutIdPersistenceDelegateAsPersistenceDelegateTest}
     * class.
     */
    public ContainerLayoutIdPersistenceDelegateAsPersistenceDelegateTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.persistence.serializable.AbstractPersistenceDelegateTestCase#createPersistenceDelegate()
     */
    @Override
    protected IPersistenceDelegate createPersistenceDelegate()
    {
        return new ContainerLayoutIdPersistenceDelegate();
    }

    /*
     * @see org.gamegineer.common.persistence.serializable.AbstractPersistenceDelegateTestCase#createSubject()
     */
    @Override
    protected Object createSubject()
    {
        return ContainerLayoutId.fromString( "id" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.common.persistence.serializable.AbstractPersistenceDelegateTestCase#registerPersistenceDelegates(org.gamegineer.common.persistence.serializable.persistencedelegateregistry.IPersistenceDelegateRegistry)
     */
    @Override
    protected void registerPersistenceDelegates(
        final IPersistenceDelegateRegistry persistenceDelegateRegistry )
    {
        persistenceDelegateRegistry.registerPersistenceDelegate( ContainerLayoutId.class, new ContainerLayoutIdPersistenceDelegate() );
        persistenceDelegateRegistry.registerPersistenceDelegate( ContainerLayoutIdProxy.class, new ContainerLayoutIdPersistenceDelegate() );
    }
}
