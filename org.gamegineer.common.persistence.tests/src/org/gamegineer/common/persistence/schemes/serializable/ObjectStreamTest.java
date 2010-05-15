/*
 * ObjectStreamTest.java
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
 * Created on Jun 21, 2008 at 9:41:21 PM.
 */

package org.gamegineer.common.persistence.schemes.serializable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import org.gamegineer.common.internal.persistence.Services;
import org.gamegineer.common.persistence.schemes.serializable.services.persistencedelegateregistry.IPersistenceDelegateRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the interaction between the
 * {@link org.gamegineer.common.persistence.schemes.serializable.ObjectInputStream}
 * and
 * {@link org.gamegineer.common.persistence.schemes.serializable.ObjectOutputStream}
 * classes.
 */
public final class ObjectStreamTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The persistence delegate for the non-serializable class used in the
     * fixture.
     */
    private IPersistenceDelegate persistenceDelegate_;

    /** The persistence delegate registry. */
    private IPersistenceDelegateRegistry persistenceDelegateRegistry_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ObjectStreamTest} class.
     */
    public ObjectStreamTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

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
        persistenceDelegateRegistry_ = Services.getDefault().getSerializablePersistenceDelegateRegistry();
        persistenceDelegate_ = new MockNonSerializableClassPersistenceDelegate();
        persistenceDelegateRegistry_.registerPersistenceDelegate( MockNonSerializableClass.class, persistenceDelegate_ );
    }

    /**
     * Tears down the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @After
    public void tearDown()
        throws Exception
    {
        persistenceDelegateRegistry_.unregisterPersistenceDelegate( MockNonSerializableClass.class, persistenceDelegate_ );
        persistenceDelegate_ = null;
        persistenceDelegateRegistry_ = null;
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
        final MockNonSerializableClass obj = new MockNonSerializableClass( 2112, "42" ); //$NON-NLS-1$
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject( obj );
        oos.close();

        final ObjectInputStream ois = new ObjectInputStream( new ByteArrayInputStream( baos.toByteArray() ) );
        final MockNonSerializableClass deserializedObj = (MockNonSerializableClass)ois.readObject();
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
        final ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject( null );
        oos.close();

        final ObjectInputStream ois = new ObjectInputStream( new ByteArrayInputStream( baos.toByteArray() ) );
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
        final ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject( obj );
        oos.close();

        final ObjectInputStream ois = new ObjectInputStream( new ByteArrayInputStream( baos.toByteArray() ) );
        final Date deserializedObj = (Date)ois.readObject();
        ois.close();

        assertEquals( obj, deserializedObj );
    }
}
