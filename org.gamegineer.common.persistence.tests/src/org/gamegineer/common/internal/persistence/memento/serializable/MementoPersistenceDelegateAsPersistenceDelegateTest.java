/*
 * MementoPersistenceDelegateAsPersistenceDelegateTest.java
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
 * Created on Jul 1, 2008 at 11:14:02 PM.
 */

package org.gamegineer.common.internal.persistence.memento.serializable;

import static org.junit.Assert.assertEquals;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.gamegineer.common.internal.persistence.memento.Memento;
import org.gamegineer.common.persistence.serializable.AbstractPersistenceDelegateTestCase;
import org.gamegineer.common.persistence.serializable.IPersistenceDelegate;
import org.gamegineer.common.persistence.serializable.IPersistenceDelegateRegistry;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.internal.persistence.memento.serializable.MementoPersistenceDelegate}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.common.persistence.serializable.IPersistenceDelegate}
 * interface.
 */
public final class MementoPersistenceDelegateAsPersistenceDelegateTest
    extends AbstractPersistenceDelegateTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * MementoPersistenceDelegateAsPersistenceDelegateTest} class.
     */
    public MementoPersistenceDelegateAsPersistenceDelegateTest()
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
        final Memento expectedMemento = (Memento)expected;
        final Memento actualMemento = (Memento)actual;
        assertEquals( expectedMemento.getAttributes(), actualMemento.getAttributes() );
    }

    /*
     * @see org.gamegineer.common.persistence.serializable.AbstractPersistenceDelegateTestCase#createPersistenceDelegate()
     */
    @Override
    protected IPersistenceDelegate createPersistenceDelegate()
    {
        return new MementoPersistenceDelegate();
    }

    /*
     * @see org.gamegineer.common.persistence.serializable.AbstractPersistenceDelegateTestCase#createSubject()
     */
    @Override
    protected Object createSubject()
    {
        final Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put( "nullName", null ); //$NON-NLS-1$
        attributes.put( "stringName", "string" ); //$NON-NLS-1$ //$NON-NLS-2$
        attributes.put( "integerName", new Integer( 2112 ) ); //$NON-NLS-1$
        attributes.put( "doubleName", new Double( -42.0 ) ); //$NON-NLS-1$
        attributes.put( "longListName", Collections.singletonList( new Long( 1001001L ) ) ); //$NON-NLS-1$
        return new Memento( attributes );
    }

    /*
     * @see org.gamegineer.common.persistence.serializable.AbstractPersistenceDelegateTestCase#registerPersistenceDelegates(org.gamegineer.common.persistence.serializable.persistencedelegateregistry.IPersistenceDelegateRegistry)
     */
    @Override
    protected void registerPersistenceDelegates(
        final IPersistenceDelegateRegistry persistenceDelegateRegistry )
    {
        persistenceDelegateRegistry.registerPersistenceDelegate( Memento.class, new MementoPersistenceDelegate() );
        persistenceDelegateRegistry.registerPersistenceDelegate( MementoProxy.class, new MementoPersistenceDelegate() );
    }
}
