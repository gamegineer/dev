/*
 * CardPileOrientationPersistenceDelegateAsPersistenceDelegateTest.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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
 * Created on Apr 12, 2012 at 9:00:57 PM.
 */

package org.gamegineer.cards.internal.persistence.serializable;

import org.gamegineer.cards.core.CardPileOrientation;
import org.gamegineer.common.persistence.serializable.IPersistenceDelegate;
import org.gamegineer.common.persistence.serializable.IPersistenceDelegateRegistry;
import org.gamegineer.common.persistence.serializable.test.AbstractPersistenceDelegateTestCase;

/**
 * A fixture for testing the {@link CardPileOrientationPersistenceDelegate}
 * class to ensure it does not violate the contract of the
 * {@link IPersistenceDelegate} interface.
 */
public final class CardPileOrientationPersistenceDelegateAsPersistenceDelegateTest
    extends AbstractPersistenceDelegateTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code CardPileOrientationPersistenceDelegateAsPersistenceDelegateTest}
     * class.
     */
    public CardPileOrientationPersistenceDelegateAsPersistenceDelegateTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.persistence.serializable.test.AbstractPersistenceDelegateTestCase#createSubject()
     */
    @Override
    protected Object createSubject()
    {
        return CardPileOrientation.BASE;
    }

    /*
     * @see org.gamegineer.common.persistence.serializable.test.AbstractPersistenceDelegateTestCase#registerPersistenceDelegates(org.gamegineer.common.persistence.serializable.IPersistenceDelegateRegistry)
     */
    @Override
    protected void registerPersistenceDelegates(
        final IPersistenceDelegateRegistry persistenceDelegateRegistry )
    {
        persistenceDelegateRegistry.registerPersistenceDelegate( CardPileOrientation.class, new CardPileOrientationPersistenceDelegate() );
    }
}
