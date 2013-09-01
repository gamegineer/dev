/*
 * PixelConverterTest.java
 * Copyright 2008-2012 Gamegineer contributors and others.
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
 * Created on Nov 4, 2011 at 9:23:18 PM.
 */

package org.gamegineer.common.ui.layout;

import java.awt.Component;
import java.awt.FontMetrics;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.ui.layout.PixelConverter} class.
 */
public final class PixelConverterTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code PixelConverterTest} class.
     */
    public PixelConverterTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@link PixelConverter#PixelConverter(Component)} constructor
     * throws an exception when passed a {@code null} component.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructorFromComponent_Component_Null()
    {
        new PixelConverter( (Component)null );
    }

    /**
     * Ensures the {@link PixelConverter#PixelConverter(FontMetrics)}
     * constructor throws an exception when passed a {@code null} font metrics.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructorFromFontMetrics_FontMetrics_Null()
    {
        new PixelConverter( (FontMetrics)null );
    }
}
