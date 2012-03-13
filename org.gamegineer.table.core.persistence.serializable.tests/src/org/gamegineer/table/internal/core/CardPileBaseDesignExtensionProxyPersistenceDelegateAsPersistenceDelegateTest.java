/*
 * CardPileBaseDesignExtensionProxyPersistenceDelegateAsPersistenceDelegateTest.java
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
 * Created on Aug 18, 2010 at 10:50:52 PM.
 */

package org.gamegineer.table.internal.core;

import static org.junit.Assert.assertEquals;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.core.runtime.IExtension;
import org.gamegineer.common.persistence.serializable.AbstractPersistenceDelegateTestCase;
import org.gamegineer.common.persistence.serializable.IPersistenceDelegate;
import org.gamegineer.common.persistence.serializable.IPersistenceDelegateRegistry;
import org.gamegineer.table.core.CardPileBaseDesigns;
import org.gamegineer.table.core.ICardPileBaseDesign;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.CardPileBaseDesignExtensionProxyPersistenceDelegate}
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
     * Initializes a new instance of the
     * {@code 
     * CardPileBaseDesignExtensionProxyPersistenceDelegateAsPersistenceDelegateTest
     * } class.
     */
    public CardPileBaseDesignExtensionProxyPersistenceDelegateAsPersistenceDelegateTest()
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
        final ICardPileBaseDesign expectedCardPileBaseDesign = (ICardPileBaseDesign)expected;
        final ICardPileBaseDesign actualCardPileBaseDesign = (ICardPileBaseDesign)actual;
        assertEquals( expectedCardPileBaseDesign.getId(), actualCardPileBaseDesign.getId() );
        assertEquals( expectedCardPileBaseDesign.getSize(), actualCardPileBaseDesign.getSize() );
    }

    /*
     * @see org.gamegineer.common.persistence.serializable.AbstractPersistenceDelegateTestCase#createPersistenceDelegate()
     */
    @Override
    protected IPersistenceDelegate createPersistenceDelegate()
    {
        return new CardPileBaseDesignExtensionProxyPersistenceDelegate();
    }

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
     * @see org.gamegineer.common.persistence.serializable.AbstractPersistenceDelegateTestCase#registerPersistenceDelegates(org.gamegineer.common.persistence.serializable.persistencedelegateregistry.IPersistenceDelegateRegistry)
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
