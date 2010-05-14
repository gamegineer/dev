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
 * Created on Jul 1, 2008 at 10:59:32 PM.
 */

package org.gamegineer.common.persistence.schemes.serializable;

import static org.junit.Assert.assertTrue;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectStreamClass;
import org.gamegineer.common.internal.persistence.Services;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.common.persistence.schemes.serializable.IPersistenceDelegate}
 * interface.
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
     * Ensures the {@code annotateClass} method throws an exception when passed
     * a {@code null} class.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testAnnotateClass_Class_Null()
        throws Exception
    {
        final Object obj = createSubject();
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream( baos );
        final IPersistenceDelegate persistenceDelegate = Services.getDefault().getSerializablePersistenceDelegateRegistry().getPersistenceDelegate( obj.getClass().getName() );

        persistenceDelegate.annotateClass( oos, null );
    }

    /**
     * Ensures the {@code annotateClass} method throws an exception when passed
     * a {@code null} object stream.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testAnnotateClass_Stream_Null()
        throws Exception
    {
        final Object obj = createSubject();
        final IPersistenceDelegate persistenceDelegate = Services.getDefault().getSerializablePersistenceDelegateRegistry().getPersistenceDelegate( obj.getClass().getName() );

        persistenceDelegate.annotateClass( null, String.class );
    }

    /**
     * Ensures the {@code resolveClass} method throws an exception when passed a
     * {@code null} object stream class descriptor.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testResolveClass_Desc_Null()
        throws Exception
    {
        final Object obj = createSubject();
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject( obj );
        oos.close();
        final ObjectInputStream ois = new ObjectInputStream( new ByteArrayInputStream( baos.toByteArray() ) );
        final IPersistenceDelegate persistenceDelegate = Services.getDefault().getSerializablePersistenceDelegateRegistry().getPersistenceDelegate( obj.getClass().getName() );

        persistenceDelegate.resolveClass( ois, null );
    }

    /**
     * Ensures the {@code resolveClass} method throws an exception when passed a
     * {@code null} object stream.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testResolveClass_Stream_Null()
        throws Exception
    {
        final Object obj = createSubject();
        final IPersistenceDelegate persistenceDelegate = Services.getDefault().getSerializablePersistenceDelegateRegistry().getPersistenceDelegate( obj.getClass().getName() );

        persistenceDelegate.resolveClass( null, ObjectStreamClass.lookup( String.class ) );
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
        final ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject( obj );
        oos.close();

        final ObjectInputStream ois = new ObjectInputStream( new ByteArrayInputStream( baos.toByteArray() ) );
        final Object deserializedObj = ois.readObject();
        ois.close();

        assertTrue( isEqual( obj, deserializedObj ) );
    }
}
