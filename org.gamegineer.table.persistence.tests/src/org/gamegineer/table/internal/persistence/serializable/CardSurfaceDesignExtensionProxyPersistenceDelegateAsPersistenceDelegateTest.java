/*
 * CardSurfaceDesignExtensionProxyPersistenceDelegateAsPersistenceDelegateTest.java
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
 * Created on Aug 18, 2010 at 11:37:34 PM.
 */

package org.gamegineer.table.internal.persistence.serializable;

import static org.junit.Assert.assertEquals;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.core.runtime.IExtension;
import org.gamegineer.common.persistence.serializable.AbstractPersistenceDelegateTestCase;
import org.gamegineer.common.persistence.serializable.IPersistenceDelegate;
import org.gamegineer.common.persistence.serializable.IPersistenceDelegateRegistry;
import org.gamegineer.table.core.CardSurfaceDesigns;
import org.gamegineer.table.core.ICardSurfaceDesign;
import org.gamegineer.table.internal.core.CardSurfaceDesign;
import org.gamegineer.table.internal.core.CardSurfaceDesignExtensionProxy;
import org.gamegineer.table.internal.persistence.serializable.CardSurfaceDesignExtensionProxyPersistenceDelegate;
import org.gamegineer.table.internal.persistence.serializable.CardSurfaceDesignPersistenceDelegate;
import org.gamegineer.table.internal.persistence.serializable.CardSurfaceDesignProxy;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.persistence.serializable.CardSurfaceDesignExtensionProxyPersistenceDelegate}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.common.persistence.serializable.IPersistenceDelegate}
 * interface.
 */
public final class CardSurfaceDesignExtensionProxyPersistenceDelegateAsPersistenceDelegateTest
    extends AbstractPersistenceDelegateTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code 
     * CardSurfaceDesignExtensionProxyPersistenceDelegateAsPersistenceDelegateTest
     * } class.
     */
    public CardSurfaceDesignExtensionProxyPersistenceDelegateAsPersistenceDelegateTest()
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
        final ICardSurfaceDesign expectedCardSurfaceDesign = (ICardSurfaceDesign)expected;
        final ICardSurfaceDesign actualCardSurfaceDesign = (ICardSurfaceDesign)actual;
        assertEquals( expectedCardSurfaceDesign.getId(), actualCardSurfaceDesign.getId() );
        assertEquals( expectedCardSurfaceDesign.getSize(), actualCardSurfaceDesign.getSize() );
    }

    /*
     * @see org.gamegineer.common.persistence.serializable.AbstractPersistenceDelegateTestCase#createPersistenceDelegate()
     */
    @Override
    protected IPersistenceDelegate createPersistenceDelegate()
    {
        return new CardSurfaceDesignExtensionProxyPersistenceDelegate();
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
        final ICardSurfaceDesign cardSurfaceDesign = CardSurfaceDesigns.createUniqueCardSurfaceDesign();
        return new CardSurfaceDesignExtensionProxy( extension, cardSurfaceDesign.getId(), cardSurfaceDesign.getSize().width, cardSurfaceDesign.getSize().height );
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
        persistenceDelegateRegistry.registerPersistenceDelegate( CardSurfaceDesignExtensionProxy.class, new CardSurfaceDesignExtensionProxyPersistenceDelegate() );
    }
}
