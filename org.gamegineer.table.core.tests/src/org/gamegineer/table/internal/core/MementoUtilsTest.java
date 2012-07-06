/*
 * MementoUtilsTest.java
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
 * Created on Apr 9, 2010 at 10:40:06 PM.
 */

package org.gamegineer.table.internal.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.HashMap;
import java.util.Map;
import org.gamegineer.common.core.util.memento.MementoException;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.MementoUtils} class.
 */
public final class MementoUtilsTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The name of an attribute guaranteed to be absent from the memento. */
    private static final String ABSENT_ATTRIBUTE_NAME = "absent"; //$NON-NLS-1$

    /**
     * The name of an attribute guaranteed to be present and in the memento with
     * a {@code null} value.
     */
    private static final String NULL_ATTRIBUTE_NAME = "null"; //$NON-NLS-1$

    /** The name of an attribute guaranteed to be present in the memento. */
    private static final String PRESENT_ATTRIBUTE_NAME = "present"; //$NON-NLS-1$

    /** The value of an attribute guaranteed to be present in the memento. */
    private static final String PRESENT_ATTRIBUTE_VALUE = "present-Value"; //$NON-NLS-1$

    /** A memento for use in the fixture. */
    private Map<String, Object> memento_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MementoUtilsTest} class.
     */
    public MementoUtilsTest()
    {
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
        memento_ = new HashMap<String, Object>();
        memento_.put( NULL_ATTRIBUTE_NAME, null );
        memento_.put( PRESENT_ATTRIBUTE_NAME, PRESENT_ATTRIBUTE_VALUE );
    }

    /**
     * Ensures the {@code getAttribute} method throws an exception when the
     * attribute is absent.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = MementoException.class )
    public void testGetAttribute_Attribute_Absent()
        throws Exception
    {
        MementoUtils.getAttribute( memento_, ABSENT_ATTRIBUTE_NAME, String.class );
    }

    /**
     * Ensures the {@code getAttribute} method returns the attribute value when
     * the attribute is present.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testGetAttribute_Attribute_Present()
        throws Exception
    {
        final String actualValue = MementoUtils.getAttribute( memento_, PRESENT_ATTRIBUTE_NAME, PRESENT_ATTRIBUTE_VALUE.getClass() );

        assertEquals( PRESENT_ATTRIBUTE_VALUE, actualValue );
    }

    /**
     * Ensures the {@code getAttribute} method throws an exception when the
     * attribute is present but is {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = MementoException.class )
    public void testGetAttribute_Attribute_Present_Null()
        throws Exception
    {
        MementoUtils.getAttribute( memento_, NULL_ATTRIBUTE_NAME, String.class );
    }

    /**
     * Ensures the {@code getAttribute} method throws an exception when the
     * attribute is present but is of the wrong type.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = MementoException.class )
    public void testGetAttribute_Attribute_Present_WrongType()
        throws Exception
    {
        MementoUtils.getAttribute( memento_, PRESENT_ATTRIBUTE_NAME, Integer.class );
    }

    /**
     * Ensures the {@code hasAttribute} method returns {@code false} when the
     * attribute is absent.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testHasAttribute_Attribute_Absent()
        throws Exception
    {
        assertFalse( MementoUtils.hasAttribute( memento_, ABSENT_ATTRIBUTE_NAME ) );
    }

    /**
     * Ensures the {@code hasAttribute} method returns {@code true} when the
     * attribute is present.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testHasAttribute_Attribute_Present()
        throws Exception
    {
        assertTrue( MementoUtils.hasAttribute( memento_, PRESENT_ATTRIBUTE_NAME ) );
    }
}
