/*
 * AbstractPersistenceDelegateTestCase.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;
import org.eclipse.jdt.annotation.Nullable;
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

    /** The persistence delegate registry for use in the fixture. */
    private Optional<IPersistenceDelegateRegistry> persistenceDelegateRegistry_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractPersistenceDelegateTestCase} class.
     */
    protected AbstractPersistenceDelegateTestCase()
    {
        persistenceDelegateRegistry_ = Optional.empty();
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
     *        The expected subject.
     * @param actual
     *        The actual subject.
     * 
     * @throws java.lang.AssertionError
     *         If the two subjects are not equal.
     */
    protected void assertSubjectEquals(
        final Object expected,
        final @Nullable Object actual )
    {
        assertEquals( expected, actual );
    }

    /**
     * Creates a new object input stream for the specified input stream.
     * 
     * @param is
     *        The input stream.
     * 
     * @return A new object input stream for the specified input stream.
     * 
     * @throws java.io.IOException
     *         If an I/O error occurs.
     */
    private ObjectInputStream createObjectInputStream(
        final InputStream is )
        throws IOException
    {
        return new ObjectInputStream( is, getPersistenceDelegateRegistry() );
    }

    /**
     * Creates a new object output stream for the specified output stream.
     * 
     * @param os
     *        The output stream.
     * 
     * @return A new object output stream for the specified output stream.
     * 
     * @throws java.io.IOException
     *         If an I/O error occurs.
     */
    private ObjectOutputStream createObjectOutputStream(
        final OutputStream os )
        throws IOException
    {
        return new ObjectOutputStream( os, getPersistenceDelegateRegistry() );
    }

    /**
     * Creates the subject to be persisted.
     * 
     * @return The subject to be persisted.
     */
    protected abstract Object createSubject();

    /**
     * Gets the fixture persistence delegate registry.
     * 
     * @return The fixture persistence delegate registry.
     */
    private IPersistenceDelegateRegistry getPersistenceDelegateRegistry()
    {
        return persistenceDelegateRegistry_.get();
    }

    /**
     * Registers the persistence delegates required for the subject to be
     * persisted.
     * 
     * @param persistenceDelegateRegistry
     *        The persistence delegate registry for use in the fixture.
     */
    protected abstract void registerPersistenceDelegates(
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
        final IPersistenceDelegateRegistry persistenceDelegateRegistry = new FakePersistenceDelegateRegistry();
        persistenceDelegateRegistry_ = Optional.of( persistenceDelegateRegistry );
        registerPersistenceDelegates( persistenceDelegateRegistry );
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

        try( final ObjectOutputStream oos = createObjectOutputStream( baos ) )
        {
            oos.writeObject( obj );
        }

        final Object deserializedObj;
        try( final ObjectInputStream ois = createObjectInputStream( new ByteArrayInputStream( baos.toByteArray() ) ) )
        {
            deserializedObj = ois.readObject();
        }

        assertSubjectEquals( obj, deserializedObj );
    }
}
