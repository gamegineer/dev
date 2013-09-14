/*
 * AbstractPersistenceDelegateTestCase.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
 * Created on Jul 1, 2008 at 10:59:32 PM.
 */

package org.gamegineer.common.persistence.serializable.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import org.gamegineer.common.persistence.serializable.IPersistenceDelegate;
import org.gamegineer.common.persistence.serializable.IPersistenceDelegateRegistry;
import org.gamegineer.common.persistence.serializable.ObjectInputStream;
import org.gamegineer.common.persistence.serializable.ObjectOutputStream;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link IPersistenceDelegate} interface.
 */
public abstract class AbstractPersistenceDelegateTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The persistence delegate under test in the fixture. */
    private IPersistenceDelegate persistenceDelegate_;

    /** The persistence delegate registry for use in the fixture. */
    private IPersistenceDelegateRegistry persistenceDelegateRegistry_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractPersistenceDelegateTestCase} class.
     */
    protected AbstractPersistenceDelegateTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Asserts that the specified subjects are equal.
     * 
     * <p>
     * This implementation compares the two objects using the {@code equals}
     * method.
     * </p>
     * 
     * @param expected
     *        The expected subject; must not be {@code null}.
     * @param actual
     *        The actual subject; may be {@code null}.
     * 
     * @throws java.lang.AssertionError
     *         If the two subjects are not equal.
     * @throws java.lang.NullPointerException
     *         If {@code expected} is {@code null}.
     */
    protected void assertSubjectEquals(
        /* @NonNull */
        final Object expected,
        /* @Nullable */
        final Object actual )
    {
        assertEquals( expected, actual );
    }

    /**
     * Creates a new empty object input stream.
     * 
     * @return A new empty object input stream; never {@code null}.
     * 
     * @throws java.io.IOException
     *         If an I/O error occurs.
     */
    /* @NonNull */
    private ObjectInputStream createEmptyObjectInputStream()
        throws IOException
    {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ObjectOutputStream oos = createObjectOutputStream( baos );
        oos.close();
        baos.close();
        return createObjectInputStream( new ByteArrayInputStream( baos.toByteArray() ) );
    }

    /**
     * Creates a new empty object output stream.
     * 
     * @return A new empty object output stream; never {@code null}.
     * 
     * @throws java.io.IOException
     *         If an I/O error occurs.
     */
    /* @NonNull */
    private ObjectOutputStream createEmptyObjectOutputStream()
        throws IOException
    {
        return createObjectOutputStream( new ByteArrayOutputStream() );
    }

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
     * Creates the persistence delegate under test in the fixture.
     * 
     * @return The persistence delegate under test in the fixture; never
     *         {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IPersistenceDelegate createPersistenceDelegate()
        throws Exception;

    /**
     * Creates the subject to be persisted.
     * 
     * @return The subject to be persisted; never {@code null}.
     */
    /* @NonNull */
    protected abstract Object createSubject();

    /**
     * Registers the persistence delegates required for the subject to be
     * persisted.
     * 
     * @param persistenceDelegateRegistry
     *        The persistence delegate registry for use in the fixture; must not
     *        be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code persistenceDelegateRegistry} is {@code null}.
     */
    protected abstract void registerPersistenceDelegates(
        /* @NonNull */
        IPersistenceDelegateRegistry persistenceDelegateRegistry );

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
        persistenceDelegate_ = createPersistenceDelegate();
        assertNotNull( persistenceDelegate_ );
        persistenceDelegateRegistry_ = new FakePersistenceDelegateRegistry();
        registerPersistenceDelegates( persistenceDelegateRegistry_ );
    }

    /**
     * Ensures the {@link IPersistenceDelegate#annotateClass} method throws an
     * exception when passed a {@code null} class.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testAnnotateClass_Class_Null()
        throws Exception
    {
        persistenceDelegate_.annotateClass( createEmptyObjectOutputStream(), null );
    }

    /**
     * Ensures the {@link IPersistenceDelegate#annotateClass} method throws an
     * exception when passed a {@code null} object stream.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testAnnotateClass_Stream_Null()
        throws Exception
    {
        persistenceDelegate_.annotateClass( null, String.class );
    }

    /**
     * Ensures the {@link IPersistenceDelegate#resolveClass} method throws an
     * exception when passed a {@code null} object stream class descriptor.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testResolveClass_Desc_Null()
        throws Exception
    {
        persistenceDelegate_.resolveClass( createEmptyObjectInputStream(), null );
    }

    /**
     * Ensures the {@link IPersistenceDelegate#resolveClass} method throws an
     * exception when passed a {@code null} object stream.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testResolveClass_Stream_Null()
        throws Exception
    {
        persistenceDelegate_.resolveClass( null, ObjectStreamClass.lookup( String.class ) );
    }

    /**
     * Ensures the persistence delegate can round-trip the subject.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testRoundTrip_Subject()
        throws Exception
    {
        final Object obj = createSubject();
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ObjectOutputStream oos = createObjectOutputStream( baos );
        oos.writeObject( obj );
        oos.close();

        final ObjectInputStream ois = createObjectInputStream( new ByteArrayInputStream( baos.toByteArray() ) );
        final Object deserializedObj = ois.readObject();
        ois.close();

        assertSubjectEquals( obj, deserializedObj );
    }
}
