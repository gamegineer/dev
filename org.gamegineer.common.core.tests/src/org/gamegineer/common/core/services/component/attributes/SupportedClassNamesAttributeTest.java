/*
 * SupportedClassNamesAttributeTest.java
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
 * Created on May 18, 2008 at 11:11:08 PM.
 */

package org.gamegineer.common.core.services.component.attributes;

import org.gamegineer.common.core.services.component.MockComponentFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.core.services.component.attributes.SupportedClassNamesAttribute}
 * class.
 */
public final class SupportedClassNamesAttributeTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The attribute under test in the fixture. */
    private SupportedClassNamesAttribute m_attribute;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code SupportedClassNamesAttributeTest} class.
     */
    public SupportedClassNamesAttributeTest()
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
        m_attribute = SupportedClassNamesAttribute.INSTANCE;
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
     * Ensures the {@code setValue(IAttributeMutator,String)} method throws an
     * exception when passed a {@code null} class name.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testSetValueString_ClassName_Null()
    {
        final String className = null;
        m_attribute.setValue( new MockComponentFactory(), className );
    }

    /**
     * Ensures the {@code setValue(IAttributeMutator,String)} method throws an
     * exception when passed a {@code null} mutator.
     */
    @Test( expected = NullPointerException.class )
    public void testSetValueString_Mutator_Null()
    {
        m_attribute.setValue( null, Object.class.getName() );
    }
}
