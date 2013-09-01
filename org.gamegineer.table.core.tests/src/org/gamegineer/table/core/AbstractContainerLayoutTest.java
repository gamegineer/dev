/*
 * AbstractContainerLayoutTest.java
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
 * Created on Nov 30, 2012 at 9:23:35 PM.
 */

package org.gamegineer.table.core;

import java.awt.Dimension;
import org.junit.Test;

/**
 * A fixture for testing the {@link AbstractContainerLayout} class.
 */
public final class AbstractContainerLayoutTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractContainerLayoutTest}
     * class.
     */
    public AbstractContainerLayoutTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@link AbstractContainerLayout#AbstractContainerLayout}
     * constructor throws an exception when passed a {@code null} identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Id_Null()
    {
        new AbstractContainerLayout( null )
        {
            @Override
            @SuppressWarnings( "unused" )
            protected Dimension getComponentOffsetAt(
                final IContainer container,
                final int index )
            {
                return null;
            }
        };
    }
}
