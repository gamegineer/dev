/*
 * StackedLayoutPersistenceDelegateAsPersistenceDelegateTest.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on May 12, 2012 at 10:58:41 PM.
 */

package org.gamegineer.table.internal.persistence.serializable;

import static org.junit.Assert.assertEquals;
import org.gamegineer.common.persistence.serializable.AbstractPersistenceDelegateTestCase;
import org.gamegineer.common.persistence.serializable.IPersistenceDelegate;
import org.gamegineer.common.persistence.serializable.IPersistenceDelegateRegistry;
import org.gamegineer.table.internal.core.StackedLayout;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.persistence.serializable.StackedLayoutPersistenceDelegate}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.common.persistence.serializable.IPersistenceDelegate}
 * interface.
 */
public final class StackedLayoutPersistenceDelegateAsPersistenceDelegateTest
    extends AbstractPersistenceDelegateTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code StackedLayoutPersistenceDelegateAsPersistenceDelegateTest} class.
     */
    public StackedLayoutPersistenceDelegateAsPersistenceDelegateTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.persistence.serializable.AbstractPersistenceDelegateTestCase#assertSubjectEquals(java.lang.Object, java.lang.Object)
     */
    @Override
    protected void assertSubjectEquals(
        final Object expected,
        final Object actual )
    {
        final StackedLayout expectedContainerLayout = (StackedLayout)expected;
        final StackedLayout actualContainerLayout = (StackedLayout)actual;
        assertEquals( expectedContainerLayout.getComponentsPerStackLevel(), actualContainerLayout.getComponentsPerStackLevel() );
        assertEquals( expectedContainerLayout.getStackLevelOffset(), actualContainerLayout.getStackLevelOffset() );
    }

    /*
     * @see org.gamegineer.common.persistence.serializable.AbstractPersistenceDelegateTestCase#createPersistenceDelegate()
     */
    @Override
    protected IPersistenceDelegate createPersistenceDelegate()
    {
        return new StackedLayoutPersistenceDelegate();
    }

    /*
     * @see org.gamegineer.common.persistence.serializable.AbstractPersistenceDelegateTestCase#createSubject()
     */
    @Override
    protected Object createSubject()
    {
        return new StackedLayout( 1, 1, 1 );
    }

    /*
     * @see org.gamegineer.common.persistence.serializable.AbstractPersistenceDelegateTestCase#registerPersistenceDelegates(org.gamegineer.common.persistence.serializable.IPersistenceDelegateRegistry)
     */
    @Override
    protected void registerPersistenceDelegates(
        final IPersistenceDelegateRegistry persistenceDelegateRegistry )
    {
        persistenceDelegateRegistry.registerPersistenceDelegate( StackedLayout.class, new StackedLayoutPersistenceDelegate() );
        persistenceDelegateRegistry.registerPersistenceDelegate( StackedLayoutProxy.class, new StackedLayoutPersistenceDelegate() );
    }
}
