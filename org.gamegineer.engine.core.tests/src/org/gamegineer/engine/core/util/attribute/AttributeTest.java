/*
 * AttributeTest.java
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
 * Created on Sep 6, 2008 at 9:49:25 PM.
 */

package org.gamegineer.engine.core.util.attribute;

import org.gamegineer.engine.core.IState.Scope;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.core.util.attribute.Attribute} class.
 */
public final class AttributeTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AttributeTest} class.
     */
    public AttributeTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the primary constructor throws an exception when passed a
     * {@code null} attribute name.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Primary_AttributeName_Null()
    {
        new Attribute<Object>( null );
    }

    /**
     * Ensures the secondary constructor throws an exception when passed an
     * empty local name.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_Secondary_LocalName_Empty()
    {
        new Attribute<Object>( Scope.APPLICATION, "" ); //$NON-NLS-1$
    }

    /**
     * Ensures the secondary constructor throws an exception when passed a
     * {@code null} local name.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Secondary_LocalName_Null()
    {
        new Attribute<Object>( Scope.APPLICATION, null );
    }

    /**
     * Ensures the secondary constructor throws an exception when passed a
     * {@code null} scope.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Secondary_Scope_Null()
    {
        new Attribute<Object>( null, "name" ); //$NON-NLS-1$
    }
}
