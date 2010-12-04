/*
 * XMLDecoderTest.java
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
 * Created on Jul 1, 2010 at 11:15:53 PM.
 */

package org.gamegineer.common.persistence.beans;

import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.persistence.beans.XMLDecoder} class.
 */
public final class XMLDecoderTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code XMLDecoderTest} class.
     */
    public XMLDecoderTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the primary constructor throws an exception when passed a {@code
     * null} persistence delegate registry.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructorPrimary_PersistenceDelegateRegistry_Null()
    {
        new XMLDecoder( null, null, null, null, null );
    }
}
