/*
 * ObjectStreamsTest.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Apr 20, 2011 at 7:42:19 PM.
 */

package org.gamegineer.common.persistence.serializable;

import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.persistence.serializable.ObjectStreams} class.
 */
public final class ObjectStreamsTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ObjectStreamsTest} class.
     */
    public ObjectStreamsTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@link ObjectStreams#createPlatformObjectInputStream} method
     * throws an exception when passed a {@code null} input stream.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testCreatePlatformObjectInputStream_Stream_Null()
        throws Exception
    {
        ObjectStreams.createPlatformObjectInputStream( null );
    }

    /**
     * Ensures the {@link ObjectStreams#createPlatformObjectOutputStream} method
     * throws an exception when passed a {@code null} output stream.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testCreatePlatformObjectOutputStream_Stream_Null()
        throws Exception
    {
        ObjectStreams.createPlatformObjectOutputStream( null );
    }
}
