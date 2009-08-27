/*
 * XMLPersistenceTest.java
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
 * Created on Jun 21, 2008 at 11:25:54 PM.
 */

package org.gamegineer.common.persistence.schemes.beans;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.IAdapterManager;
import org.gamegineer.common.internal.persistence.Services;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the interaction between the
 * {@link java.beans.XMLDecoder} and
 * {@link org.gamegineer.common.persistence.schemes.beans.XMLEncoder} classes.
 */
public final class XMLPersistenceTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The persistence delegate adapter factory for the non-persistable class
     * used in the fixture.
     */
    private IAdapterFactory m_adapterFactory;

    /** The platform adapter manager. */
    private IAdapterManager m_adapterManager;


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
     * Sets up the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Before
    public void setUp()
        throws Exception
    {
        m_adapterManager = Services.getDefault().getAdapterManager();
        m_adapterFactory = new MockNonPersistableClassPersistenceDelegate.AdapterFactory();
        m_adapterManager.registerAdapters( m_adapterFactory, MockNonPersistableClass.class );
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
        m_adapterManager.unregisterAdapters( m_adapterFactory );
        m_adapterFactory = null;
        m_adapterManager = null;
    }

    /**
     * Ensures the XML persistence coders can round-trip a non-persistable
     * object if a suitable persistence delegate is available.
     */
    @Test
    public void testRoundTrip_NonPersistableObject()
    {
        final MockNonPersistableClass obj = new MockNonPersistableClass( 2112, "42" ); //$NON-NLS-1$
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final XMLEncoder encoder = new XMLEncoder( baos );
        encoder.writeObject( obj );
        encoder.close();

        final XMLDecoder decoder = new XMLDecoder( new ByteArrayInputStream( baos.toByteArray() ) );
        final MockNonPersistableClass deserializedObj = (MockNonPersistableClass)decoder.readObject();
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
        final XMLEncoder encoder = new XMLEncoder( baos );
        encoder.writeObject( null );
        encoder.close();

        final XMLDecoder decoder = new XMLDecoder( new ByteArrayInputStream( baos.toByteArray() ) );
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
        final XMLEncoder encoder = new XMLEncoder( baos );
        encoder.writeObject( obj );
        encoder.close();

        final XMLDecoder decoder = new XMLDecoder( new ByteArrayInputStream( baos.toByteArray() ) );
        final Date deserializedObj = (Date)decoder.readObject();
        decoder.close();

        assertEquals( obj, deserializedObj );
    }
}
