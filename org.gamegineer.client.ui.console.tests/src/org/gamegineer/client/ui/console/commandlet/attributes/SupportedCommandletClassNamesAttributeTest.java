/*
 * SupportedCommandletClassNamesAttributeTest.java
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
 * Created on Oct 21, 2008 at 10:43:06 PM.
 */

package org.gamegineer.client.ui.console.commandlet.attributes;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import org.gamegineer.common.core.services.component.util.attribute.IAttributeMutator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.client.ui.console.commandlet.attributes.SupportedCommandletClassNamesAttribute}
 * class.
 */
public final class SupportedCommandletClassNamesAttributeTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The attribute under test in the fixture. */
    private SupportedCommandletClassNamesAttribute m_attribute;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code SupportedCommandletClassNamesAttributeTest} class.
     */
    public SupportedCommandletClassNamesAttributeTest()
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
        m_attribute = SupportedCommandletClassNamesAttribute.INSTANCE;
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
        m_attribute.setValue( createDummy( IAttributeMutator.class ), className );
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
