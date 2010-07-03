/*
 * CardPileBaseDesignPersistenceDelegateAsPersistenceDelegateTest.java
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
 * Created on Apr 28, 2010 at 10:32:09 PM.
 */

package org.gamegineer.table.internal.core.persistence.schemes.serializable;

import org.gamegineer.common.persistence.schemes.serializable.AbstractPersistenceDelegateTestCase;
import org.gamegineer.common.persistence.schemes.serializable.services.persistencedelegateregistry.IPersistenceDelegateRegistry;
import org.gamegineer.table.core.CardPileBaseDesigns;
import org.gamegineer.table.internal.core.CardPileBaseDesign;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.persistence.schemes.serializable.CardPileBaseDesignPersistenceDelegate}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.common.persistence.schemes.serializable.IPersistenceDelegate}
 * interface.
 */
public final class CardPileBaseDesignPersistenceDelegateAsPersistenceDelegateTest
    extends AbstractPersistenceDelegateTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * CardPileBaseDesignPersistenceDelegateAsPersistenceDelegateTest} class.
     */
    public CardPileBaseDesignPersistenceDelegateAsPersistenceDelegateTest()
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
        return CardPileBaseDesigns.createUniqueCardPileBaseDesign();
    }

    /*
     * @see org.gamegineer.common.persistence.schemes.serializable.AbstractPersistenceDelegateTestCase#isEqual(java.lang.Object, java.lang.Object)
     */
    @Override
    protected boolean isEqual(
        final Object originalObj,
        final Object deserializedObj )
    {
        final CardPileBaseDesign originalCardPileBaseDesign = (CardPileBaseDesign)originalObj;
        final CardPileBaseDesign deserializedCardPileBaseDesign = (CardPileBaseDesign)deserializedObj;
        return originalCardPileBaseDesign.getId().equals( deserializedCardPileBaseDesign.getId() ) && originalCardPileBaseDesign.getSize().equals( deserializedCardPileBaseDesign.getSize() );
    }

    /*
     * @see org.gamegineer.common.persistence.schemes.serializable.AbstractPersistenceDelegateTestCase#registerPersistenceDelegates(org.gamegineer.common.persistence.schemes.serializable.services.persistencedelegateregistry.IPersistenceDelegateRegistry)
     */
    @Override
    protected void registerPersistenceDelegates(
        final IPersistenceDelegateRegistry persistenceDelegateRegistry )
    {
        persistenceDelegateRegistry.registerPersistenceDelegate( CardPileBaseDesign.class, new CardPileBaseDesignPersistenceDelegate() );
        persistenceDelegateRegistry.registerPersistenceDelegate( CardPileBaseDesignProxy.class, new CardPileBaseDesignPersistenceDelegate() );
    }
}
