/*
 * CardPileLayoutPersistenceDelegateAsPersistenceDelegateTest.java
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
 * Created on May 9, 2010 at 9:30:34 PM.
 */

package org.gamegineer.table.internal.core.persistence.schemes.serializable;

import org.gamegineer.common.persistence.schemes.serializable.AbstractPersistenceDelegateTestCase;
import org.gamegineer.table.core.CardPileLayout;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.persistence.schemes.serializable.CardPileLayoutPersistenceDelegate}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.common.persistence.schemes.serializable.IPersistenceDelegate}
 * interface.
 */
public final class CardPileLayoutPersistenceDelegateAsPersistenceDelegateTest
    extends AbstractPersistenceDelegateTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * CardPileLayoutPersistenceDelegateAsPersistenceDelegateTest} class.
     */
    public CardPileLayoutPersistenceDelegateAsPersistenceDelegateTest()
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
        return CardPileLayout.STACKED;
    }
}
