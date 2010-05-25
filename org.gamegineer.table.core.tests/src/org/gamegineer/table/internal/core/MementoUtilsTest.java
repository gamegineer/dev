/*
 * MementoUtilsTest.java
 * Copyright 2008-2010 Gamegineer.org
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

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.gamegineer.common.persistence.memento.IMemento;
import org.gamegineer.common.persistence.memento.MalformedMementoException;
import org.gamegineer.common.persistence.memento.MementoBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.MementoUtilsTest} class.
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
    private IMemento memento_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MementoUtilsTest} class.
     */
    public MementoUtilsTest()
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
        final MementoBuilder mementoBuilder = new MementoBuilder();
        mementoBuilder.addAttribute( NULL_ATTRIBUTE_NAME, null );
        mementoBuilder.addAttribute( PRESENT_ATTRIBUTE_NAME, PRESENT_ATTRIBUTE_VALUE );
        memento_ = mementoBuilder.toMemento();
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
        memento_ = null;
    }

    /**
     * Ensures the {@code getOptionalAttribute} method returns {@code null} when
     * the attribute is absent.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testGetOptionalAttribute_Attribute_Absent()
        throws Exception
    {
        final String actualValue = MementoUtils.getOptionalAttribute( memento_, ABSENT_ATTRIBUTE_NAME, String.class );

        assertNull( actualValue );
    }

    /**
     * Ensures the {@code getOptionalAttribute} method returns the attribute
     * value when the attribute is present.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testGetOptionalAttribute_Attribute_Present()
        throws Exception
    {
        final String actualValue = MementoUtils.getOptionalAttribute( memento_, PRESENT_ATTRIBUTE_NAME, PRESENT_ATTRIBUTE_VALUE.getClass() );

        assertEquals( PRESENT_ATTRIBUTE_VALUE, actualValue );
    }

    /**
     * Ensures the {@code getOptionalAttribute} method throws an exception when
     * the attribute is present but is {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = MalformedMementoException.class )
    public void testGetOptionalAttribute_Attribute_Present_Null()
        throws Exception
    {
        MementoUtils.getOptionalAttribute( memento_, NULL_ATTRIBUTE_NAME, String.class );
    }

    /**
     * Ensures the {@code getOptionalAttribute} method throws an exception when
     * the attribute is present but is of the wrong type.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = MalformedMementoException.class )
    public void testGetOptionalAttribute_Attribute_Present_WrongType()
        throws Exception
    {
        MementoUtils.getOptionalAttribute( memento_, PRESENT_ATTRIBUTE_NAME, Integer.class );
    }

    /**
     * Ensures the {@code getOptionalAttribute} method throws an exception when
     * passed a {@code null} memento.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = AssertionError.class )
    public void testGetOptionalAttribute_Memento_Null()
        throws Exception
    {
        MementoUtils.getOptionalAttribute( null, "name", Object.class ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getOptionalAttribute} method throws an exception when
     * passed a {@code null} name.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = AssertionError.class )
    public void testGetOptionalAttribute_Name_Null()
        throws Exception
    {
        MementoUtils.getOptionalAttribute( createMock( IMemento.class ), null, Object.class );
    }

    /**
     * Ensures the {@code getOptionalAttribute} method throws an exception when
     * passed a {@code null} type.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = AssertionError.class )
    public void testGetOptionalAttribute_Type_Null()
        throws Exception
    {
        MementoUtils.getOptionalAttribute( createMock( IMemento.class ), "name", null ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getRequiredAttribute} method throws an exception when
     * the attribute is absent.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = MalformedMementoException.class )
    public void testGetRequiredAttribute_Attribute_Absent()
        throws Exception
    {
        MementoUtils.getRequiredAttribute( memento_, ABSENT_ATTRIBUTE_NAME, String.class );
    }

    /**
     * Ensures the {@code getRequiredAttribute} method returns the attribute
     * value when the attribute is present.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testGetRequiredAttribute_Attribute_Present()
        throws Exception
    {
        final String actualValue = MementoUtils.getRequiredAttribute( memento_, PRESENT_ATTRIBUTE_NAME, PRESENT_ATTRIBUTE_VALUE.getClass() );

        assertEquals( PRESENT_ATTRIBUTE_VALUE, actualValue );
    }

    /**
     * Ensures the {@code getRequiredAttribute} method throws an exception when
     * the attribute is present but is {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = MalformedMementoException.class )
    public void testGetRequiredAttribute_Attribute_Present_Null()
        throws Exception
    {
        MementoUtils.getRequiredAttribute( memento_, NULL_ATTRIBUTE_NAME, String.class );
    }

    /**
     * Ensures the {@code getRequiredAttribute} method throws an exception when
     * the attribute is present but is of the wrong type.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = MalformedMementoException.class )
    public void testGetRequiredAttribute_Attribute_Present_WrongType()
        throws Exception
    {
        MementoUtils.getRequiredAttribute( memento_, PRESENT_ATTRIBUTE_NAME, Integer.class );
    }

    /**
     * Ensures the {@code getRequiredAttribute} method throws an exception when
     * passed a {@code null} memento.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = AssertionError.class )
    public void testGetRequiredAttribute_Memento_Null()
        throws Exception
    {
        MementoUtils.getRequiredAttribute( null, "name", Object.class ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getRequiredAttribute} method throws an exception when
     * passed a {@code null} name.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = AssertionError.class )
    public void testGetRequiredAttribute_Name_Null()
        throws Exception
    {
        MementoUtils.getRequiredAttribute( createMock( IMemento.class ), null, Object.class );
    }

    /**
     * Ensures the {@code getRequiredAttribute} method throws an exception when
     * passed a {@code null} type.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = AssertionError.class )
    public void testGetRequiredAttribute_Type_Null()
        throws Exception
    {
        MementoUtils.getRequiredAttribute( createMock( IMemento.class ), "name", null ); //$NON-NLS-1$
    }
}
