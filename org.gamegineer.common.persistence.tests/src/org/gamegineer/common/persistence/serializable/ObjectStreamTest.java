/*
 * ObjectStreamTest.java
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
 * Created on Jun 21, 2008 at 9:41:21 PM.
 */

package org.gamegineer.common.persistence.serializable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the interaction between the
 * {@link org.gamegineer.common.persistence.serializable.ObjectInputStream} and
 * {@link org.gamegineer.common.persistence.serializable.ObjectOutputStream}
 * classes.
 */
public final class ObjectStreamTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The persistence delegate registry for use in the fixture. */
    private IPersistenceDelegateRegistry persistenceDelegateRegistry_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ObjectStreamTest} class.
     */
    public ObjectStreamTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new object input stream for the specified input stream.
     * 
     * @param is
     *        The input stream; must not be {@code null}.
     * 
     * @return A new object input stream for the specified input stream; never
     *         {@code null}.
     * 
     * @throws java.io.IOException
     *         If an I/O error occurs.
     */
    /* @NonNull */
    private ObjectInputStream createObjectInputStream(
        /* @NonNull */
        final InputStream is )
        throws IOException
    {
        assert is != null;

        return new ObjectInputStream( is, persistenceDelegateRegistry_ );
    }

    /**
     * Creates a new object output stream for the specified output stream.
     * 
     * @param os
     *        The output stream; must not be {@code null}.
     * 
     * @return A new object output stream for the specified output stream; never
     *         {@code null}.
     * 
     * @throws java.io.IOException
     *         If an I/O error occurs.
     */
    /* @NonNull */
    private ObjectOutputStream createObjectOutputStream(
        /* @NonNull */
        final OutputStream os )
        throws IOException
    {
        assert os != null;

        return new ObjectOutputStream( os, persistenceDelegateRegistry_ );
    }

    /**
     * Sets up the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Before
    public void setUp()
        throws Exception
    {
        persistenceDelegateRegistry_ = new FakePersistenceDelegateRegistry();
        persistenceDelegateRegistry_.registerPersistenceDelegate( FakeNonSerializableClass.class, new FakeNonSerializableClassPersistenceDelegate() );
    }

    /**
     * Ensures the object serialization streams can round-trip a
     * non-serializable object if a suitable persistence delegate is available.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testRoundTrip_NonSerializableObject()
        throws Exception
    {
        final FakeNonSerializableClass obj = new FakeNonSerializableClass( 2112, "42" ); //$NON-NLS-1$
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ObjectOutputStream oos = createObjectOutputStream( baos );
        oos.writeObject( obj );
        oos.close();

        final ObjectInputStream ois = createObjectInputStream( new ByteArrayInputStream( baos.toByteArray() ) );
        final FakeNonSerializableClass deserializedObj = (FakeNonSerializableClass)ois.readObject();
        ois.close();

        assertEquals( obj, deserializedObj );
    }

    /**
     * Ensures the object serialization streams can round-trip a {@code null}
     * object.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testRoundTrip_NullObject()
        throws Exception
    {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ObjectOutputStream oos = createObjectOutputStream( baos );
        oos.writeObject( null );
        oos.close();

        final ObjectInputStream ois = createObjectInputStream( new ByteArrayInputStream( baos.toByteArray() ) );
        final Object deserializedObj = ois.readObject();
        ois.close();

        assertNull( deserializedObj );
    }

    /**
     * Ensures the object serialization streams can round-trip a serializable
     * object without having an adaptable persistence delegate available.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testRoundTrip_SerializableObject()
        throws Exception
    {
        final Date obj = new Date();
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ObjectOutputStream oos = createObjectOutputStream( baos );
        oos.writeObject( obj );
        oos.close();

        final ObjectInputStream ois = createObjectInputStream( new ByteArrayInputStream( baos.toByteArray() ) );
        final Date deserializedObj = (Date)ois.readObject();
        ois.close();

        assertEquals( obj, deserializedObj );
    }
}
