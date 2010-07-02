/*
 * XMLPersistenceTest.java
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
 * Created on Jun 21, 2008 at 11:25:54 PM.
 */

package org.gamegineer.common.persistence.schemes.beans;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import org.gamegineer.common.persistence.schemes.beans.services.persistencedelegateregistry.FakePersistenceDelegateRegistry;
import org.gamegineer.common.persistence.schemes.beans.services.persistencedelegateregistry.IPersistenceDelegateRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the interaction between the
 * {@link org.gamegineer.common.persistence.schemes.beans.XMLDecoder} and
 * {@link org.gamegineer.common.persistence.schemes.beans.XMLEncoder} classes.
 */
public final class XMLPersistenceTest
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
     * Initializes a new instance of the {@code XMLPersistenceTest} class.
     */
    public XMLPersistenceTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new XML decoder for the specified input stream.
     * 
     * @param is
     *        The input stream; must not be {@code null}.
     * 
     * @return A new XML decoder for the specified input stream; never {@code
     *         null}.
     */
    /* @NonNull */
    private XMLDecoder createXMLDecoder(
        /* @NonNull */
        final InputStream is )
    {
        assert is != null;

        return new XMLDecoder( is, persistenceDelegateRegistry_ );
    }

    /**
     * Creates a new XML encoder for the specified output stream.
     * 
     * @param os
     *        The output stream; must not be {@code null}.
     * 
     * @return A new XML encoder for the specified output stream; never {@code
     *         null}.
     */
    /* @NonNull */
    private XMLEncoder createXMLEncoder(
        /* @NonNull */
        final OutputStream os )
    {
        assert os != null;

        return new XMLEncoder( os, persistenceDelegateRegistry_ );
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
        persistenceDelegateRegistry_.registerPersistenceDelegate( FakeNonPersistableClass.class, new FakeNonPersistableClassPersistenceDelegate() );
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
        persistenceDelegateRegistry_ = null;
    }

    /**
     * Ensures the XML persistence coders can round-trip a non-persistable
     * object if a suitable persistence delegate is available.
     */
    @Test
    public void testRoundTrip_NonPersistableObject()
    {
        final FakeNonPersistableClass obj = new FakeNonPersistableClass( 2112, "42" ); //$NON-NLS-1$
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final XMLEncoder encoder = createXMLEncoder( baos );
        encoder.writeObject( obj );
        encoder.close();

        final XMLDecoder decoder = createXMLDecoder( new ByteArrayInputStream( baos.toByteArray() ) );
        final FakeNonPersistableClass deserializedObj = (FakeNonPersistableClass)decoder.readObject();
        decoder.close();

        assertEquals( obj, deserializedObj );
    }

    /**
     * Ensures the XML persistence coders can round-trip a {@code null} object.
     */
    @Test
    public void testRoundTrip_NullObject()
    {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final XMLEncoder encoder = createXMLEncoder( baos );
        encoder.writeObject( null );
        encoder.close();

        final XMLDecoder decoder = createXMLDecoder( new ByteArrayInputStream( baos.toByteArray() ) );
        final Object deserializedObj = decoder.readObject();
        decoder.close();

        assertNull( deserializedObj );
    }

    /**
     * Ensures the XML persistence coders can round-trip a persistable object
     * without having an adaptable persistence delegate available.
     */
    @Test
    public void testRoundTrip_PersistableObject()
    {
        final Date obj = new Date();
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final XMLEncoder encoder = createXMLEncoder( baos );
        encoder.writeObject( obj );
        encoder.close();

        final XMLDecoder decoder = createXMLDecoder( new ByteArrayInputStream( baos.toByteArray() ) );
        final Date deserializedObj = (Date)decoder.readObject();
        decoder.close();

        assertEquals( obj, deserializedObj );
    }
}
