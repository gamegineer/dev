/*
 * AbstractPrincipalTestCase.java
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
 * Created on Apr 10, 2009 at 9:29:19 PM.
 */

package org.gamegineer.engine.internal.core.extensions.securitymanager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import java.security.Principal;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link java.security.Principal} interface.
 */
public abstract class AbstractPrincipalTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The default principal name. */
    private static final String DEFAULT_NAME = "name"; //$NON-NLS-1$

    /** The principal under test in the fixture. */
    private Principal m_principal;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractPrincipalTestCase}
     * class.
     */
    protected AbstractPrincipalTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the principal to be tested.
     * 
     * @param name
     *        The principal name; must not be {@code null}.
     * 
     * @return The principal to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     * @throws java.lang.NullPointerException
     *         If {@code name} is {@code null}.
     */
    /* @NonNull */
    protected abstract Principal createPrincipal(
        /* @NonNull */
        String name )
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
        m_principal = createPrincipal( DEFAULT_NAME );
        assertNotNull( m_principal );
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
        m_principal = null;
    }

    /**
     * Ensures the {@code equals} method correctly indicates two equal but
     * different principals are equal.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testEquals_Equal_NotSame()
        throws Exception
    {
        final Principal principal1 = m_principal;
        final Principal principal2 = createPrincipal( m_principal.getName() );

        assertNotSame( principal1, principal2 );
        assertEquals( principal1, principal2 );
        assertEquals( principal2, principal1 ); // symmetric
    }

    /**
     * Ensures the {@code equals} method correctly handles a {@code null}
     * principal.
     */
    @Test
    public void testEquals_Equal_Null()
    {
        assertFalse( m_principal.equals( null ) );
    }

    /**
     * Ensures the {@code equals} method correctly indicates the same principal
     * is equal to itself.
     */
    @Test
    public void testEquals_Equal_Same()
    {
        assertEquals( m_principal, m_principal ); // reflexive
    }

    /**
     * Ensures the {@code equals} method correctly indicates two principals
     * whose names differ are unequal.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testEquals_Unequal_Names()
        throws Exception
    {
        final Principal principal1 = m_principal;
        final Principal principal2 = createPrincipal( "other-name" ); //$NON-NLS-1$

        assertFalse( principal1.equals( principal2 ) );
    }

    /**
     * Ensures the {@code equals} method correctly indicates two principals
     * whose names are the same but whose types differ are unequal.
     */
    @Test
    public void testEquals_Unequal_Types()
    {
        final Principal principal1 = m_principal;
        final Principal principal2 = new Principal()
        {
            @SuppressWarnings( "synthetic-access" )
            public String getName()
            {
                return m_principal.getName();
            }
        };

        assertFalse( principal1.equals( principal2 ) );
    }

    /**
     * Ensures the {@code getName} method does not return {@code null}.
     */
    @Test
    public void testGetName_ReturnValue_NonNull()
    {
        assertNotNull( m_principal.getName() );
    }

    /**
     * Ensures the {@code hashCode} method returns the same hash code for equal
     * principals.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testHashCode_Equal()
        throws Exception
    {
        final Principal principal1 = m_principal;
        final Principal principal2 = createPrincipal( m_principal.getName() );

        assertNotSame( principal1, principal2 );
        assertEquals( principal1.hashCode(), principal2.hashCode() );
    }
}
