/*
 * AbstractPersistenceDelegateTestCase.java
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
 * Created on Jul 2, 2008 at 9:26:56 PM.
 */

package org.gamegineer.common.persistence.schemes.beans;

import static org.junit.Assert.assertTrue;
import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.junit.Test;

/**
 * A fixture for testing the classes that extend the
 * {@link java.beans.PersistenceDelegate} class to ensure their subject class
 * can be persisted successfully.
 */
public abstract class AbstractPersistenceDelegateTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractPersistenceDelegateTestCase} class.
     */
    protected AbstractPersistenceDelegateTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the subject to be persisted.
     * 
     * @return The subject to be persisted; never {@code null}.
     */
    /* @NonNull */
    protected abstract Object createSubject();

    /**
     * Indicates the specified objects are equal.
     * 
     * <p>
     * The default implementation compares the two objects using the {@code
     * equals} method.
     * </p>
     * 
     * @param originalObj
     *        The original object; must not be {@code null}.
     * @param deserializedObj
     *        The deserialized object; may be {@code null}.
     * 
     * @return {@code true} if the two objects are equal; otherwise {@code
     *         false}.
     * 
     * @throws java.lang.ClassCastException
     *         If {@code deserializedObj} is not of the subject type.
     * @throws java.lang.NullPointerException
     *         If {@code originalObj} is {@code null}.
     */
    protected boolean isEqual(
        /* @NonNull */
        final Object originalObj,
        /* @Nullable */
        final Object deserializedObj )
    {
        return originalObj.equals( deserializedObj );
    }

    /**
     * Ensures the persistence delegate can round-trip the subject.
     */
    @Test
    public void testRoundTrip_Subject()
    {
        final Object obj = createSubject();
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final XMLEncoder encoder = new XMLEncoder( baos );
        encoder.writeObject( obj );
        encoder.close();

        final XMLDecoder decoder = new XMLDecoder( new ByteArrayInputStream( baos.toByteArray() ) );
        final Object deserializedObj = decoder.readObject();
        decoder.close();

        assertTrue( isEqual( obj, deserializedObj ) );
    }
}
