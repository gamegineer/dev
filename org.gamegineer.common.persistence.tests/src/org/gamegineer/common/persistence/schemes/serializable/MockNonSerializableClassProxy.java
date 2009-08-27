/*
 * MockNonSerializableClassProxy.java
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
 * Created on Jun 21, 2008 at 10:00:48 PM.
 */

package org.gamegineer.common.persistence.schemes.serializable;

import java.io.Serializable;

/**
 * A serializable proxy for the {@code MockNonSerializableClass} class.
 * 
 * <p>
 * This class is not intended to be extended by clients.
 * </p>
 */
public final class MockNonSerializableClassProxy
    implements Serializable
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 996961268400195642L;

    /**
     * The integer field.
     * 
     * @serial
     */
    private int m_intField;

    /**
     * The string field.
     * 
     * @serial
     */
    private String m_stringField;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MockNonSerializableClassProxy}
     * class.
     */
    @SuppressWarnings( "unused" )
    private MockNonSerializableClassProxy()
    {
        m_intField = 0;
        m_stringField = null;
    }

    /**
     * Initializes a new instance of the {@code MockNonSerializableClassProxy}
     * class from the specified {@code MockNonSerializableClass} instance.
     * 
     * @param subject
     *        The {@code MockNonSerializableClass} instance; must not be
     *        {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code subject} is {@code null}.
     */
    public MockNonSerializableClassProxy(
        /* @NonNull */
        final MockNonSerializableClass subject )
    {
        m_intField = subject.getIntField();
        m_stringField = subject.getStringField();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets a replacement object for this instance after it has been
     * deserialized.
     * 
     * @return A replacement object for this instance after it has been
     *         deserialized; never {@code null}.
     */
    /* @NonNull */
    private Object readResolve()
    {
        return new MockNonSerializableClass( m_intField, m_stringField );
    }
}
