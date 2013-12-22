/*
 * IconProxyTest.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
 * Created on Nov 22, 2009 at 10:10:36 PM.
 */

package org.gamegineer.table.internal.ui.impl.util.swing;

import org.junit.Test;

/**
 * A fixture for testing the {@link IconProxy} class.
 */
public final class IconProxyTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code IconProxy} class.
     */
    public IconProxyTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@link IconProxy#IconProxy} constructor throws an exception
     * when passed a {@code null} URL.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Url_Null()
    {
        new IconProxy( null );
    }
}