/*
 * XMLEncoderTest.java
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
 * Created on Jun 21, 2008 at 11:14:20 PM.
 */

package org.gamegineer.common.persistence.beans;

import java.io.ByteArrayOutputStream;
import org.easymock.EasyMock;
import org.gamegineer.common.persistence.beans.services.persistencedelegateregistry.IPersistenceDelegateRegistry;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.persistence.beans.XMLEncoder} class.
 */
public final class XMLEncoderTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code XMLEncoderTest} class.
     */
    public XMLEncoderTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * persistence delegate registry.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_PersistenceDelegateRegistry_Null()
    {
        new XMLEncoder( new ByteArrayOutputStream(), null );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * stream.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Stream_Null()
    {
        new XMLEncoder( null, EasyMock.createMock( IPersistenceDelegateRegistry.class ) );
    }
}
