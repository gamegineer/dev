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
import java.beans.PersistenceDelegate;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import org.gamegineer.common.internal.persistence.Services;
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

    /**
     * The persistence delegate for the non-serializable class used in the
     * fixture.
     */
    private PersistenceDelegate persistenceDelegate_;

    /** The persistence delegate registry. */
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
     * Sets up the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Before
    public void setUp()
        throws Exception
    {
        persistenceDelegateRegistry_ = Services.getDefault().getBeansPersistenceDelegateRegistry();
        persistenceDelegate_ = new MockNonPersistableClassPersistenceDelegate();
        persistenceDelegateRegistry_.registerPersistenceDelegate( MockNonPersistableClass.class.getName(), persistenceDelegate_ );
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
        persistenceDelegateRegistry_.unregisterPersistenceDelegate( MockNonPersistableClass.class.getName(), persistenceDelegate_ );
        persistenceDelegate_ = null;
        persistenceDelegateRegistry_ = null;
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
