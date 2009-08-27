/*
 * ClassNameComponentSpecificationTest.java
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
 * Created on May 17, 2008 at 1:25:12 PM.
 */

package org.gamegineer.common.core.services.component.specs;

import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.core.services.component.specs.ClassNameComponentSpecification}
 * class.
 */
public final class ClassNameComponentSpecificationTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ClassNameComponentSpecificationTest} class.
     */
    public ClassNameComponentSpecificationTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * class name.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Name_Null()
    {
        new ClassNameComponentSpecification( null );
    }
}
