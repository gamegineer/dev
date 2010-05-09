/*
 * CardSurfaceDesignPersistenceDelegateAsPersistenceDelegateTest.java
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
 * Created on May 8, 2010 at 9:14:35 PM.
 */

package org.gamegineer.table.internal.core.persistence.schemes.serializable;

import org.gamegineer.common.persistence.schemes.serializable.AbstractPersistenceDelegateTestCase;
import org.gamegineer.table.core.CardSurfaceDesigns;
import org.gamegineer.table.internal.core.CardSurfaceDesign;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.persistence.schemes.serializable.CardSurfaceDesignPersistenceDelegate}
 * class to ensure its subject class can be persisted successfully.
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
     * @see org.gamegineer.common.persistence.schemes.serializable.AbstractPersistenceDelegateTestCase#createSubject()
     */
    @Override
    protected Object createSubject()
    {
        return CardSurfaceDesigns.createUniqueCardSurfaceDesign();
    }

    /*
     * @see org.gamegineer.common.persistence.schemes.serializable.AbstractPersistenceDelegateTestCase#isEqual(java.lang.Object, java.lang.Object)
     */
    @Override
    protected boolean isEqual(
        final Object originalObj,
        final Object deserializedObj )
    {
        final CardSurfaceDesign originalCardSurfaceDesign = (CardSurfaceDesign)originalObj;
        final CardSurfaceDesign deserializedCardSurfaceDesign = (CardSurfaceDesign)deserializedObj;
        return originalCardSurfaceDesign.getId().equals( deserializedCardSurfaceDesign.getId() ) && originalCardSurfaceDesign.getSize().equals( deserializedCardSurfaceDesign.getSize() );
    }
}
