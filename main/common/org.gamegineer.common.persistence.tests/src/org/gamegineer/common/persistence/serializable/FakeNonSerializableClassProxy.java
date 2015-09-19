/*
 * FakeNonSerializableClassProxy.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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

package org.gamegineer.common.persistence.serializable;

import java.io.Serializable;
import net.jcip.annotations.Immutable;
import org.eclipse.jdt.annotation.Nullable;

/**
 * A serializable proxy for the {@code FakeNonSerializableClass} class.
 */
@Immutable
public final class FakeNonSerializableClassProxy
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
    private int intField_;

    /**
     * The string field.
     * 
     * @serial
     */
    private @Nullable String stringField_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FakeNonSerializableClassProxy}
     * class.
     */
    @SuppressWarnings( "unused" )
    private FakeNonSerializableClassProxy()
    {
        intField_ = 0;
        stringField_ = null;
    }

    /**
     * Initializes a new instance of the {@code FakeNonSerializableClassProxy}
     * class from the specified {@code FakeNonSerializableClass} instance.
     * 
     * @param subject
     *        The {@code FakeNonSerializableClass} instance; must not be
     *        {@code null}.
     */
    public FakeNonSerializableClassProxy(
        final FakeNonSerializableClass subject )
    {
        intField_ = subject.getIntField();
        stringField_ = subject.getStringField();
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
    private Object readResolve()
    {
        return new FakeNonSerializableClass( intField_, stringField_ );
    }
}
