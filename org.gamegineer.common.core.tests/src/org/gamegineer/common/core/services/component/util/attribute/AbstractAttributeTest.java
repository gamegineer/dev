/*
 * AbstractAttributeTest.java
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
 * Created on May 18, 2008 at 9:55:54 PM.
 */

package org.gamegineer.common.core.services.component.util.attribute;

import org.gamegineer.common.core.services.component.MockComponentFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.core.services.component.util.attribute.AbstractAttribute}
 * class.
 */
public final class AbstractAttributeTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The attribute under test in the fixture. */
    private AbstractAttribute<String> m_attribute;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractAttributeTest} class.
     */
    public AbstractAttributeTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Sets up the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Before
    public void setUp()
        throws Exception
    {
        m_attribute = new AbstractAttribute<String>( "name", String.class ) //$NON-NLS-1$
        {
            // no overrides
        };
    }

    /**
     * Tears down the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @After
    public void tearDown()
        throws Exception
    {
        m_attribute = null;
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * name.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Name_Null()
    {
        new AbstractAttribute<Object>( null, Object.class )
        {
            // no overrides
        };
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * type.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Type_Null()
    {
        new AbstractAttribute<Object>( "name", null ) //$NON-NLS-1$
        {
            // no overrides
        };
    }

    /**
     * Ensures the {@code getValue} method throws an exception when an illegal
     * value of the wrong type is read from the accessor.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testGetValue_Value_Illegal_WrongType()
    {
        final MockComponentFactory factory = new MockComponentFactory();
        factory.setAttribute( m_attribute.getName(), new Object() );
        m_attribute.getValue( factory );
    }

    /**
     * Ensures the {@code tryGetValue} method throws an exception when an
     * illegal value of the wrong type is read from the accessor.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testTryGetValue_Value_Illegal_WrongType()
    {
        final MockComponentFactory factory = new MockComponentFactory();
        factory.setAttribute( m_attribute.getName(), new Object() );
        m_attribute.tryGetValue( factory );
    }
}
