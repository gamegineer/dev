/*
 * ObjectInputStreamTest.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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
 * Created on Jun 21, 2008 at 9:35:07 PM.
 */

package org.gamegineer.common.persistence.serializable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.easymock.EasyMock;
import org.junit.Test;

/**
 * A fixture for testing the {@link ObjectInputStream} class.
 */
public final class ObjectInputStreamTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ObjectInputStreamTest} class.
     */
    public ObjectInputStreamTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@link ObjectInputStream#ObjectInputStream} constructor
     * throws an exception when passed a {@code null} persistence delegate
     * registry.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_PersistenceDelegateRegistry_Null()
        throws Exception
    {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try( final ObjectOutputStream oos = new ObjectOutputStream( baos, EasyMock.createMock( IPersistenceDelegateRegistry.class ) ) )
        {
            oos.writeObject( null );
        }

        try( final ObjectInputStream inputStream = new ObjectInputStream( new ByteArrayInputStream( baos.toByteArray() ), null ) )
        {
            // do nothing
        }
    }

    /**
     * Ensures the {@link ObjectInputStream#ObjectInputStream} constructor
     * throws an exception when passed a {@code null} stream.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Stream_Null()
        throws Exception
    {
        try( final ObjectInputStream inputStream = new ObjectInputStream( null, EasyMock.createMock( IPersistenceDelegateRegistry.class ) ) )
        {
            // do nothing
        }
    }
}
