/*
 * CardPileBaseDesignExtensionProxyPersistenceDelegateAsPersistenceDelegateTest.java
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
 * Created on Aug 18, 2010 at 10:50:52 PM.
 */

package org.gamegineer.table.internal.core.services.cardpilebasedesignregistry;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.core.runtime.IExtension;
import org.gamegineer.common.persistence.serializable.AbstractPersistenceDelegateTestCase;
import org.gamegineer.common.persistence.serializable.services.persistencedelegateregistry.IPersistenceDelegateRegistry;
import org.gamegineer.table.core.CardPileBaseDesigns;
import org.gamegineer.table.core.ICardPileBaseDesign;
import org.gamegineer.table.internal.core.CardPileBaseDesign;
import org.gamegineer.table.internal.core.CardPileBaseDesignPersistenceDelegate;
import org.gamegineer.table.internal.core.CardPileBaseDesignProxy;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.services.cardpilebasedesignregistry.CardPileBaseDesignExtensionProxyPersistenceDelegate}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.common.persistence.serializable.IPersistenceDelegate}
 * interface.
 */
public final class CardPileBaseDesignExtensionProxyPersistenceDelegateAsPersistenceDelegateTest
    extends AbstractPersistenceDelegateTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code 
     * CardPileBaseDesignExtensionProxyPersistenceDelegateAsPersistenceDelegateTest
     * * } class.
     */
    public CardPileBaseDesignExtensionProxyPersistenceDelegateAsPersistenceDelegateTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.persistence.serializable.AbstractPersistenceDelegateTestCase#createSubject()
     */
    @Override
    protected Object createSubject()
    {
        final IMocksControl mocksControl = EasyMock.createNiceControl();
        final IExtension extension = mocksControl.createMock( IExtension.class );
        EasyMock.expect( extension.getNamespaceIdentifier() ).andReturn( "namespace-id" ); //$NON-NLS-1$
        EasyMock.expect( extension.getSimpleIdentifier() ).andReturn( "simple-id" ); //$NON-NLS-1$
        mocksControl.replay();
        final ICardPileBaseDesign cardPileBaseDesign = CardPileBaseDesigns.createUniqueCardPileBaseDesign();
        return new CardPileBaseDesignExtensionProxy( extension, cardPileBaseDesign.getId(), cardPileBaseDesign.getSize().width, cardPileBaseDesign.getSize().height );
    }

    /*
     * @see org.gamegineer.common.persistence.serializable.AbstractPersistenceDelegateTestCase#isEqual(java.lang.Object, java.lang.Object)
     */
    @Override
    protected boolean isEqual(
        final Object originalObj,
        final Object deserializedObj )
    {
        final ICardPileBaseDesign originalCardPileBaseDesign = (ICardPileBaseDesign)originalObj;
        final ICardPileBaseDesign deserializedCardPileBaseDesign = (ICardPileBaseDesign)deserializedObj;
        return originalCardPileBaseDesign.getId().equals( deserializedCardPileBaseDesign.getId() ) && originalCardPileBaseDesign.getSize().equals( deserializedCardPileBaseDesign.getSize() );
    }

    /*
     * @see org.gamegineer.common.persistence.serializable.AbstractPersistenceDelegateTestCase#registerPersistenceDelegates(org.gamegineer.common.persistence.serializable.services.persistencedelegateregistry.IPersistenceDelegateRegistry)
     */
    @Override
    protected void registerPersistenceDelegates(
        final IPersistenceDelegateRegistry persistenceDelegateRegistry )
    {
        persistenceDelegateRegistry.registerPersistenceDelegate( CardPileBaseDesign.class, new CardPileBaseDesignPersistenceDelegate() );
        persistenceDelegateRegistry.registerPersistenceDelegate( CardPileBaseDesignProxy.class, new CardPileBaseDesignPersistenceDelegate() );
        persistenceDelegateRegistry.registerPersistenceDelegate( CardPileBaseDesignExtensionProxy.class, new CardPileBaseDesignExtensionProxyPersistenceDelegate() );
    }
}
