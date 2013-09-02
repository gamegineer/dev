/*
 * AbstractComponentPrototypeFactoryTestCase.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
 * Created on Oct 26, 2012 at 9:04:09 PM.
 */

package org.gamegineer.table.ui.prototype;

import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link IComponentPrototypeFactory} interface.
 */
public abstract class AbstractComponentPrototypeFactoryTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The component prototype factory under test in the fixture. */
    private IComponentPrototypeFactory componentPrototypeFactory_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractComponentPrototypeFactoryTestCase} class.
     */
    protected AbstractComponentPrototypeFactoryTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the component prototype factory to be tested.
     * 
     * @return The component prototype factory to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IComponentPrototypeFactory createComponentPrototypeFactory()
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
        componentPrototypeFactory_ = createComponentPrototypeFactory();
        assertNotNull( componentPrototypeFactory_ );
    }

    /**
     * Ensures the {@link IComponentPrototypeFactory#createComponentPrototype}
     * method throws an exception when passed a {@code null} table environment.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateComponentPrototype_TableEnvironment_Null()
        throws Exception
    {
        componentPrototypeFactory_.createComponentPrototype( null );
    }
}
