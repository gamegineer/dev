/*
 * AbstractComponentCreationContextTestCase.java
 * Copyright 2008-2009 Gamegineer.org
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
 * Created on Apr 10, 2008 at 10:57:28 PM.
 */

package org.gamegineer.common.core.services.component;

import static org.junit.Assert.assertEquals;
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
 * {@link org.gamegineer.common.core.services.component.IComponentCreationContext}
 * interface.
 */
public abstract class AbstractComponentCreationContextTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The component creation context under test in the fixture. */
    private IComponentCreationContext context_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractComponentCreationContextTestCase} class.
     */
    protected AbstractComponentCreationContextTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the component creation context to be tested.
     * 
     * @param attributes
     *        The collection of initialization attributes; must not be {@code
     *        null}.
     * 
     * @return The component creation context to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     * @throws java.lang.IllegalArgumentException
     *         If any value in {@code attributes} is {@code null}.
     * @throws java.lang.NullPointerException
     *         If {@code attributes} is {@code null}.
     */
    /* @NonNull */
    protected abstract IComponentCreationContext createComponentCreationContext(
        /* @NonNull */
        Map<String, Object> attributes )
        throws Exception;

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
        final Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put( "name1", "value1" ); //$NON-NLS-1$ //$NON-NLS-2$
        attributes.put( "name2", "value2" ); //$NON-NLS-1$ //$NON-NLS-2$
        attributes.put( "name3", "value3" ); //$NON-NLS-1$ //$NON-NLS-2$
        attributes.put( "name4", "value4" ); //$NON-NLS-1$ //$NON-NLS-2$
        context_ = createComponentCreationContext( attributes );
        assertNotNull( context_ );
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
        context_ = null;
    }

    /**
     * Ensures the {@code containsAttribute} method correctly indicates an
     * absent attribute.
     */
    @Test
    public void testContainsAttribute_Attribute_Absent()
    {
        assertFalse( context_.containsAttribute( "unknown_name" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code containsAttribute} method correctly indicates a
     * present attribute.
     */
    @Test
    public void testContainsAttribute_Attribute_Present()
    {
        assertTrue( context_.containsAttribute( "name1" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code containsAttribute} method throws an exception when
     * passed a {@code null} name.
     */
    @Test( expected = NullPointerException.class )
    public void testContainsAttribute_Name_Null()
    {
        context_.containsAttribute( null );
    }

    /**
     * Ensures the {@code getAttribute} method returns {@code null} when the
     * specified attribute does not exist.
     */
    @Test
    public void testGetAttribute_Attribute_Absent()
    {
        assertNull( context_.getAttribute( "unknown_name" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getAttribute} method retrieves an attribute that
     * exists.
     */
    @Test
    public void testGetAttribute_Attribute_Present()
    {
        assertEquals( "value1", context_.getAttribute( "name1" ) ); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Ensures the {@code getAttribute} method throws an exception when passed a
     * {@code null} name.
     */
    @Test( expected = NullPointerException.class )
    public void testGetAttribute_Name_Null()
    {
        context_.getAttribute( null );
    }
}
