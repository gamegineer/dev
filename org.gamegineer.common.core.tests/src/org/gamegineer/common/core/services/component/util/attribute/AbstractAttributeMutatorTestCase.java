/*
 * AbstractAttributeMutatorTestCase.java
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
 * Created on May 19, 2008 at 12:48:44 AM.
 */

package org.gamegineer.common.core.services.component.util.attribute;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.common.core.services.component.util.attribute.IAttributeMutator}
 * interface.
 */
public abstract class AbstractAttributeMutatorTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The attribute mutator under test in the fixture. */
    private IAttributeMutator mutator_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractAttributeMutatorTestCase} class.
     */
    protected AbstractAttributeMutatorTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the attribute mutator to be tested.
     * 
     * @return The attribute mutator to be tested; never {@code null}.
     */
    /* @NonNull */
    protected abstract IAttributeMutator createAttributeMutator();

    /**
     * Gets an attribute accessor for the specified attribute mutator.
     * 
     * <p>
     * The default implementation simply tries to cast the mutator to an {@code
     * IAttributeAccessor} and will throw a {@code ClassCastException} if the
     * cast fails.
     * </p>
     * 
     * @param mutator
     *        The attribute mutator; must not be {@code null}.
     * 
     * @return An attribute accessor for the attribute mutator; never {@code
     *         null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code mutator} is {@code null}.
     */
    /* @NonNull */
    protected IAttributeAccessor getAttributeAccessor(
        /* @NonNull */
        final IAttributeMutator mutator )
    {
        assertArgumentNotNull( mutator, "mutator" ); //$NON-NLS-1$

        return (IAttributeAccessor)mutator;
    }

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
        mutator_ = createAttributeMutator();
        assertNotNull( mutator_ );
        mutator_.setAttribute( "name1", "value1" ); //$NON-NLS-1$ //$NON-NLS-2$
        mutator_.setAttribute( "name2", "value2" ); //$NON-NLS-1$ //$NON-NLS-2$
        mutator_.setAttribute( "name3", "value3" ); //$NON-NLS-1$ //$NON-NLS-2$
        mutator_.setAttribute( "name4", "value4" ); //$NON-NLS-1$ //$NON-NLS-2$
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
        mutator_ = null;
    }

    /**
     * Ensures the {@code setAttribute} method adds an attribute that does not
     * exist.
     */
    @Test
    public void testSetAttribute_Attribute_Absent()
    {
        final String name = "unknown_name"; //$NON-NLS-1$
        final Object value = new Object();
        mutator_.setAttribute( name, value );
        assertEquals( value, getAttributeAccessor( mutator_ ).getAttribute( name ) );
    }

    /**
     * Ensures the {@code setAttribute} method overwrites the value of an
     * attribute that exists.
     */
    @Test
    public void testSetAttribute_Attribute_Present()
    {
        final IAttributeAccessor accessor = getAttributeAccessor( mutator_ );
        final String name = "name2"; //$NON-NLS-1$
        final Object value = new Object();
        assertEquals( "value2", accessor.getAttribute( name ) ); //$NON-NLS-1$
        mutator_.setAttribute( name, value );
        assertEquals( value, accessor.getAttribute( name ) );
    }

    /**
     * Ensures the {@code setAttribute} method throws an exception when passed a
     * {@code null} name.
     */
    @Test( expected = NullPointerException.class )
    public void testSetAttribute_Name_Null()
    {
        mutator_.setAttribute( null, new Object() );
    }

    /**
     * Ensures the {@code setAttribute} method throws an exception when passed a
     * {@code null} value.
     */
    @Test( expected = NullPointerException.class )
    public void testSetAttribute_Value_Null()
    {
        mutator_.setAttribute( "name", null ); //$NON-NLS-1$
    }
}
