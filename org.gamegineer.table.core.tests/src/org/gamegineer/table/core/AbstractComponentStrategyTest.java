/*
 * AbstractComponentStrategyTest.java
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
 * Created on Nov 29, 2012 at 11:25:43 PM.
 */

package org.gamegineer.table.core;

import java.util.Collection;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.core.AbstractComponentStrategy} class.
 */
public final class AbstractComponentStrategyTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractComponentStrategyTest}
     * class.
     */
    public AbstractComponentStrategyTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Id_Null()
    {
        new AbstractComponentStrategy( null )
        {
            @Override
            public ComponentOrientation getDefaultOrientation()
            {
                return null;
            }

            @Override
            protected ComponentSurfaceDesignId getDefaultSurfaceDesignId()
            {
                return null;
            }

            @Override
            public Collection<ComponentOrientation> getSupportedOrientations()
            {
                return null;
            }
        };
    }
}
