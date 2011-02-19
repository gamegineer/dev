/*
 * CardSurfaceDesignPersistenceDelegateAsPersistenceDelegateTest.java
 * Copyright 2008-2011 Gamegineer.org
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
 * Created on May 8, 2010 at 9:14:35 PM.
 */

package org.gamegineer.table.internal.core;

import static org.junit.Assert.assertEquals;
import org.gamegineer.common.persistence.serializable.AbstractPersistenceDelegateTestCase;
import org.gamegineer.common.persistence.serializable.IPersistenceDelegateRegistry;
import org.gamegineer.table.core.CardSurfaceDesigns;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.CardSurfaceDesignPersistenceDelegate}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.common.persistence.serializable.IPersistenceDelegate}
 * interface.
 */
public final class CardSurfaceDesignPersistenceDelegateAsPersistenceDelegateTest
    extends AbstractPersistenceDelegateTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * CardSurfaceDesignPersistenceDelegateAsPersistenceDelegateTest} class.
     */
    public CardSurfaceDesignPersistenceDelegateAsPersistenceDelegateTest()
    {
        super();
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
        final CardSurfaceDesign expectedCardSurfaceDesign = (CardSurfaceDesign)expected;
        final CardSurfaceDesign actualCardSurfaceDesign = (CardSurfaceDesign)actual;
        assertEquals( expectedCardSurfaceDesign.getId(), actualCardSurfaceDesign.getId() );
        assertEquals( expectedCardSurfaceDesign.getSize(), actualCardSurfaceDesign.getSize() );
    }

    /*
     * @see org.gamegineer.common.persistence.serializable.AbstractPersistenceDelegateTestCase#createSubject()
     */
    @Override
    protected Object createSubject()
    {
        return CardSurfaceDesigns.createUniqueCardSurfaceDesign();
    }

    /*
     * @see org.gamegineer.common.persistence.serializable.AbstractPersistenceDelegateTestCase#registerPersistenceDelegates(org.gamegineer.common.persistence.serializable.persistencedelegateregistry.IPersistenceDelegateRegistry)
     */
    @Override
    protected void registerPersistenceDelegates(
        final IPersistenceDelegateRegistry persistenceDelegateRegistry )
    {
        persistenceDelegateRegistry.registerPersistenceDelegate( CardSurfaceDesign.class, new CardSurfaceDesignPersistenceDelegate() );
        persistenceDelegateRegistry.registerPersistenceDelegate( CardSurfaceDesignProxy.class, new CardSurfaceDesignPersistenceDelegate() );
    }
}
