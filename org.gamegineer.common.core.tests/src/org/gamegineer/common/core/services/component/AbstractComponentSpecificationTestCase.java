/*
 * AbstractComponentSpecificationTestCase.java
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
 * Created on May 17, 2008 at 1:30:05 PM.
 */

package org.gamegineer.common.core.services.component;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.common.core.services.component.IComponentSpecification}
 * interface.
 */
public abstract class AbstractComponentSpecificationTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The component specification under test in the fixture. */
    private IComponentSpecification m_spec;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractComponentSpecificationTestCase} class.
     */
    protected AbstractComponentSpecificationTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the component specification to be tested.
     * 
     * @return The component specification to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IComponentSpecification createComponentSpecification()
        throws Exception;

    /**
     * Creates a component factory that will match the specification returned by
     * {@code createComponentSpecification}.
     * 
     * @return A matching component factory; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IComponentFactory createMatchingComponentFactory()
        throws Exception;

    /**
     * Creates a component factory that will not match the specification
     * returned by {@code createComponentSpecification}.
     * 
     * @return A non-matching component factory; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IComponentFactory createNonMatchingComponentFactory()
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
        m_spec = createComponentSpecification();
        assertNotNull( m_spec );
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
        m_spec = null;
    }

    /**
     * Ensures the {@code matches} method returns {@code true} when passed a
     * matching factory.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testMatches_Factory_Match()
        throws Exception
    {
        assertTrue( m_spec.matches( createMatchingComponentFactory() ) );
    }

    /**
     * Ensures the {@code matches} method returns {@code false} when passed a
     * non-matching factory.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testMatches_Factory_NoMatch()
        throws Exception
    {
        assertFalse( m_spec.matches( createNonMatchingComponentFactory() ) );
    }

    /**
     * Ensures the {@code matches} method throws an exception when passed a
     * {@code null} factory.
     */
    @Test( expected = NullPointerException.class )
    public void testMatches_Factory_Null()
    {
        m_spec.matches( null );
    }
}
