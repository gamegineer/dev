/*
 * AbsoluteLayoutPersistenceDelegateAsPersistenceDelegateTest.java
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
 * Created on Aug 5, 2012 at 7:02:42 PM.
 */

package org.gamegineer.table.internal.persistence.serializable;

import static org.junit.Assert.assertEquals;
import org.gamegineer.common.persistence.serializable.AbstractPersistenceDelegateTestCase;
import org.gamegineer.common.persistence.serializable.IPersistenceDelegate;
import org.gamegineer.common.persistence.serializable.IPersistenceDelegateRegistry;
import org.gamegineer.table.internal.core.AbsoluteLayout;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.persistence.serializable.AbsoluteLayoutPersistenceDelegate}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.common.persistence.serializable.IPersistenceDelegate}
 * interface.
 */
public final class AbsoluteLayoutPersistenceDelegateAsPersistenceDelegateTest
    extends AbstractPersistenceDelegateTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbsoluteLayoutPersistenceDelegateAsPersistenceDelegateTest} class.
     */
    public AbsoluteLayoutPersistenceDelegateAsPersistenceDelegateTest()
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
        final AbsoluteLayout expectedContainerLayout = (AbsoluteLayout)expected;
        final AbsoluteLayout actualContainerLayout = (AbsoluteLayout)actual;
        assertEquals( expectedContainerLayout.getClass(), actualContainerLayout.getClass() );
    }

    /*
     * @see org.gamegineer.common.persistence.serializable.AbstractPersistenceDelegateTestCase#createPersistenceDelegate()
     */
    @Override
    protected IPersistenceDelegate createPersistenceDelegate()
    {
        return new AbsoluteLayoutPersistenceDelegate();
    }

    /*
     * @see org.gamegineer.common.persistence.serializable.AbstractPersistenceDelegateTestCase#createSubject()
     */
    @Override
    protected Object createSubject()
    {
        return new AbsoluteLayout();
    }

    /*
     * @see org.gamegineer.common.persistence.serializable.AbstractPersistenceDelegateTestCase#registerPersistenceDelegates(org.gamegineer.common.persistence.serializable.IPersistenceDelegateRegistry)
     */
    @Override
    protected void registerPersistenceDelegates(
        final IPersistenceDelegateRegistry persistenceDelegateRegistry )
    {
        persistenceDelegateRegistry.registerPersistenceDelegate( AbsoluteLayout.class, new AbsoluteLayoutPersistenceDelegate() );
        persistenceDelegateRegistry.registerPersistenceDelegate( AbsoluteLayoutProxy.class, new AbsoluteLayoutPersistenceDelegate() );
    }
}
