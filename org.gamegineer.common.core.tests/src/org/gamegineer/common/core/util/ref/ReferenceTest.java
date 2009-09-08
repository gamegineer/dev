/*
 * ReferenceTest.java
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
 * Created on May 26, 2008 at 11:13:02 PM.
 */

package org.gamegineer.common.core.util.ref;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of the
 * {@link org.gamegineer.common.core.util.ref.Reference} class.
 */
public final class ReferenceTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The reference under test in the fixture. */
    private Reference<Object> ref_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ReferenceTest} class.
     */
    public ReferenceTest()
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
        ref_ = new Reference<Object>();
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
        ref_ = null;
    }

    /**
     * Ensures the default constructor initializes the referent to {@code null}.
     */
    @Test
    public void testConstructor_Default_SetsNullReferent()
    {
        final Reference<Object> ref = new Reference<Object>();
        assertNull( ref.get() );
    }

    /**
     * Ensures the primary constructor does not throw an exception when passed a
     * {@code null} referent.
     */
    @Test
    public void testConstructor_Primary_Referent_Null()
    {
        new Reference<Object>( null );
    }

    /**
     * Ensures the primary constructor initializes the referent to the specified
     * value.
     */
    @Test
    public void testConstructor_Primary_SetsInitialReferent()
    {
        final Object obj = new Object();
        final Reference<Object> ref = new Reference<Object>( obj );
        assertSame( obj, ref.get() );
    }

    /**
     * Ensures the {@code set} method correctly modifies the referent.
     */
    @Test
    public void testSet()
    {
        final Object obj = new Object();
        ref_.set( obj );
        assertSame( obj, ref_.get() );
    }

    /**
     * Ensures the {@code set} method does not throw an exception when passed a
     * {@code null} referent.
     */
    @Test
    public void testSet_Referent_Null()
    {
        ref_.set( null );
    }
}
