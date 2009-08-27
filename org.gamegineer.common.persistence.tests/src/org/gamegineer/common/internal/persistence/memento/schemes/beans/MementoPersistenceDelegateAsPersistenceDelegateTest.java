/*
 * MementoPersistenceDelegateAsPersistenceDelegateTest.java
 * Copyright 2008 Gamegineer.org
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
 * Created on Jul 2, 2008 at 9:26:40 PM.
 */

package org.gamegineer.common.internal.persistence.memento.schemes.beans;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.gamegineer.common.internal.persistence.memento.Memento;
import org.gamegineer.common.persistence.schemes.beans.AbstractPersistenceDelegateTestCase;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.internal.persistence.memento.schemes.beans.MementoPersistenceDelegate}
 * class to ensure its subject class can be persisted successfully.
 */
public final class MementoPersistenceDelegateAsPersistenceDelegateTest
    extends AbstractPersistenceDelegateTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code MementoPersistenceDelegateAsPersistenceDelegateTest} class.
     */
    public MementoPersistenceDelegateAsPersistenceDelegateTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.persistence.schemes.beans.AbstractPersistenceDelegateTestCase#createSubject()
     */
    @Override
    protected Object createSubject()
    {
        final Map<String, Object> attributeMap = new HashMap<String, Object>();
        attributeMap.put( "nullName", null ); //$NON-NLS-1$
        attributeMap.put( "stringName", "string" ); //$NON-NLS-1$ //$NON-NLS-2$
        attributeMap.put( "integerName", new Integer( 2112 ) ); //$NON-NLS-1$
        attributeMap.put( "doubleName", new Double( -42.0 ) ); //$NON-NLS-1$
        attributeMap.put( "longListName", Collections.singletonList( new Long( 1001001L ) ) ); //$NON-NLS-1$
        return new Memento( attributeMap );
    }

    /*
     * @see org.gamegineer.common.persistence.schemes.beans.AbstractPersistenceDelegateTestCase#isEqual(java.lang.Object, java.lang.Object)
     */
    @Override
    protected boolean isEqual(
        final Object originalObj,
        final Object deserializedObj )
    {
        final Memento originalMemento = (Memento)originalObj;
        final Memento deserializedMemento = (Memento)deserializedObj;
        return originalMemento.getAttributes().equals( deserializedMemento.getAttributes() );
    }
}
