/*
 * CardSurfaceDesignExtensionProxyPersistenceDelegateAsPersistenceDelegateTest.java
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
 * Created on Aug 18, 2010 at 11:37:34 PM.
 */

package org.gamegineer.table.internal.core.services.cardsurfacedesignregistry;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.core.runtime.IExtension;
import org.gamegineer.common.persistence.schemes.serializable.AbstractPersistenceDelegateTestCase;
import org.gamegineer.common.persistence.schemes.serializable.services.persistencedelegateregistry.IPersistenceDelegateRegistry;
import org.gamegineer.table.core.CardSurfaceDesigns;
import org.gamegineer.table.core.ICardSurfaceDesign;
import org.gamegineer.table.internal.core.CardSurfaceDesign;
import org.gamegineer.table.internal.core.CardSurfaceDesignPersistenceDelegate;
import org.gamegineer.table.internal.core.CardSurfaceDesignProxy;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.services.cardsurfacedesignregistry.CardSurfaceDesignExtensionProxyPersistenceDelegate}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.common.persistence.schemes.serializable.IPersistenceDelegate}
 * interface.
 */
public final class CardSurfaceDesignExtensionProxyPersistenceDelegateAsPersistenceDelegateTest
    extends AbstractPersistenceDelegateTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code 
     * CardSurfaceDesignExtensionProxyPersistenceDelegateAsPersistenceDelegateTest
     * * } class.
     */
    public CardSurfaceDesignExtensionProxyPersistenceDelegateAsPersistenceDelegateTest()
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
        final IMocksControl mocksControl = EasyMock.createNiceControl();
        final IExtension extension = mocksControl.createMock( IExtension.class );
        EasyMock.expect( extension.getNamespaceIdentifier() ).andReturn( "namespace-id" ); //$NON-NLS-1$
        EasyMock.expect( extension.getSimpleIdentifier() ).andReturn( "simple-id" ); //$NON-NLS-1$
        mocksControl.replay();
        final ICardSurfaceDesign cardSurfaceDesign = CardSurfaceDesigns.createUniqueCardSurfaceDesign();
        return new CardSurfaceDesignExtensionProxy( extension, cardSurfaceDesign.getId(), cardSurfaceDesign.getSize().width, cardSurfaceDesign.getSize().height );
    }

    /*
     * @see org.gamegineer.common.persistence.schemes.serializable.AbstractPersistenceDelegateTestCase#isEqual(java.lang.Object, java.lang.Object)
     */
    @Override
    protected boolean isEqual(
        final Object originalObj,
        final Object deserializedObj )
    {
        final ICardSurfaceDesign originalCardSurfaceDesign = (ICardSurfaceDesign)originalObj;
        final ICardSurfaceDesign deserializedCardSurfaceDesign = (ICardSurfaceDesign)deserializedObj;
        return originalCardSurfaceDesign.getId().equals( deserializedCardSurfaceDesign.getId() ) && originalCardSurfaceDesign.getSize().equals( deserializedCardSurfaceDesign.getSize() );
    }

    /*
     * @see org.gamegineer.common.persistence.schemes.serializable.AbstractPersistenceDelegateTestCase#registerPersistenceDelegates(org.gamegineer.common.persistence.schemes.serializable.services.persistencedelegateregistry.IPersistenceDelegateRegistry)
     */
    @Override
    protected void registerPersistenceDelegates(
        final IPersistenceDelegateRegistry persistenceDelegateRegistry )
    {
        persistenceDelegateRegistry.registerPersistenceDelegate( CardSurfaceDesign.class, new CardSurfaceDesignPersistenceDelegate() );
        persistenceDelegateRegistry.registerPersistenceDelegate( CardSurfaceDesignProxy.class, new CardSurfaceDesignPersistenceDelegate() );
        persistenceDelegateRegistry.registerPersistenceDelegate( CardSurfaceDesignExtensionProxy.class, new CardSurfaceDesignExtensionProxyPersistenceDelegate() );
    }
}
