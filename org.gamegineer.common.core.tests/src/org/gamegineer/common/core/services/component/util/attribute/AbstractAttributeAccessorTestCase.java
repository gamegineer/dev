/*
 * AbstractAttributeAccessorTestCase.java
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
 * Created on May 17, 2008 at 10:44:30 PM.
 */

package org.gamegineer.common.core.services.component.util.attribute;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.common.core.services.component.util.attribute.IAttributeAccessor}
 * interface.
 */
public abstract class AbstractAttributeAccessorTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The attribute accessor under test in the fixture. */
    private IAttributeAccessor m_accessor;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractAttributeAccessorTestCase} class.
     */
    protected AbstractAttributeAccessorTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the attribute accessor to be tested.
     * 
     * @param attributeMap
     *        The map of initialization attributes; must not be {@code null}.
     * 
     * @return The attribute accessor to be tested; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If any value in {@code attributeMap} is {@code null}.
     * @throws java.lang.NullPointerException
     *         If {@code attributeMap} is {@code null}.
     */
    /* @NonNull */
    protected abstract IAttributeAccessor createAttributeAccessor(
        /* @NonNull */
        Map<String, Object> attributeMap );

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
        final Map<String, Object> attributeMap = new HashMap<String, Object>();
        attributeMap.put( "name1", "value1" ); //$NON-NLS-1$ //$NON-NLS-2$
        attributeMap.put( "name2", "value2" ); //$NON-NLS-1$ //$NON-NLS-2$
        attributeMap.put( "name3", "value3" ); //$NON-NLS-1$ //$NON-NLS-2$
        attributeMap.put( "name4", "value4" ); //$NON-NLS-1$ //$NON-NLS-2$
        m_accessor = createAttributeAccessor( attributeMap );
        assertNotNull( m_accessor );
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
        m_accessor = null;
    }

    /**
     * Ensures the {@code containsAttribute} method correctly indicates an
     * absent attribute.
     */
    @Test
    public void testContainsAttribute_Attribute_Absent()
    {
        assertFalse( m_accessor.containsAttribute( "unknown_name" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code containsAttribute} method correctly indicates a
     * present attribute.
     */
    @Test
    public void testContainsAttribute_Attribute_Present()
    {
        assertTrue( m_accessor.containsAttribute( "name1" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code containsAttribute} method throws an exception when
     * passed a {@code null} name.
     */
    @Test( expected = NullPointerException.class )
    public void testContainsAttribute_Name_Null()
    {
        m_accessor.containsAttribute( null );
    }

    /**
     * Ensures the {@code getAttribute} method returns {@code null} when the
     * specified attribute does not exist.
     */
    @Test
    public void testGetAttribute_Attribute_Absent()
    {
        assertNull( m_accessor.getAttribute( "unknown_name" ) ); //$NON-NLS-1$ 
    }

    /**
     * Ensures the {@code getAttribute} method retrieves an attribute that
     * exists.
     */
    @Test
    public void testGetAttribute_Attribute_Present()
    {
        m_accessor.getAttribute( "name1" ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getAttribute} method throws an exception when passed a
     * {@code null} name.
     */
    @Test( expected = NullPointerException.class )
    public void testGetAttribute_Name_Null()
    {
        m_accessor.getAttribute( null );
    }
}
