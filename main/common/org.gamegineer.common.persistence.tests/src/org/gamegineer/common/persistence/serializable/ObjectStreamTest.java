/*
 * ObjectStreamTest.java
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
import java.util.Optional;
import org.easymock.EasyMock;
import org.easymock.IAnswer;
import org.easymock.IMocksControl;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the interaction between the {@link ObjectInputStream}
 * and {@link ObjectOutputStream} classes.
 */
public final class ObjectStreamTest
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
     * Initializes a new instance of the {@code ObjectStreamTest} class.
     */
    public ObjectStreamTest()
    {
        persistenceDelegateRegistry_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

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
     * Gets the persistence delegate registry under test in the fixture.
     * 
     * @return The persistence delegate registry under test in the fixture.
     */
    private IPersistenceDelegateRegistry getPersistenceDelegateRegistry()
    {
        return persistenceDelegateRegistry_.get();
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
        final IMocksControl mocksControl = EasyMock.createControl();
        final IPersistenceDelegateRegistry persistenceDelegateRegistry = mocksControl.createMock( IPersistenceDelegateRegistry.class );
        persistenceDelegateRegistry_ = Optional.of( persistenceDelegateRegistry );
        EasyMock.expect( persistenceDelegateRegistry.getPersistenceDelegate( EasyMock.<@NonNull String>notNull() ) ).andAnswer( new IAnswer<@Nullable IPersistenceDelegate>()
        {
            @Override
            public @Nullable IPersistenceDelegate answer()
            {
                final String typeName = (String)EasyMock.getCurrentArguments()[ 0 ];
                if( typeName.equals( FakeNonSerializableClass.class.getName() ) )
                {
                    return new FakeNonSerializableClassPersistenceDelegate();
                }

                return null;
            }
        } ).anyTimes();
        mocksControl.replay();
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
        try( final ObjectOutputStream oos = createObjectOutputStream( baos ) )
        {
            oos.writeObject( obj );
        }

        final FakeNonSerializableClass deserializedObj;
        try( final ObjectInputStream ois = createObjectInputStream( new ByteArrayInputStream( baos.toByteArray() ) ) )
        {
            deserializedObj = (FakeNonSerializableClass)ois.readObject();
        }

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
        try( final ObjectOutputStream oos = createObjectOutputStream( baos ) )
        {
            oos.writeObject( null );
        }

        final Object deserializedObj;
        try( final ObjectInputStream ois = createObjectInputStream( new ByteArrayInputStream( baos.toByteArray() ) ) )
        {
            deserializedObj = ois.readObject();
        }

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
        try( final ObjectOutputStream oos = createObjectOutputStream( baos ) )
        {
            oos.writeObject( obj );
        }

        final Date deserializedObj;
        try( final ObjectInputStream ois = createObjectInputStream( new ByteArrayInputStream( baos.toByteArray() ) ) )
        {
            deserializedObj = (Date)ois.readObject();
        }

        assertEquals( obj, deserializedObj );
    }
}
