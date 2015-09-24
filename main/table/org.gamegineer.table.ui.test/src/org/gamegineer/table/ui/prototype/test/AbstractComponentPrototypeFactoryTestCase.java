/*
 * AbstractComponentPrototypeFactoryTestCase.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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

package org.gamegineer.table.ui.prototype.test;

import java.util.Optional;
import org.gamegineer.table.ui.prototype.IComponentPrototypeFactory;
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
    private Optional<IComponentPrototypeFactory> componentPrototypeFactory_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractComponentPrototypeFactoryTestCase} class.
     */
    protected AbstractComponentPrototypeFactoryTestCase()
    {
        componentPrototypeFactory_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the component prototype factory to be tested.
     * 
     * @return The component prototype factory to be tested.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    protected abstract IComponentPrototypeFactory createComponentPrototypeFactory()
        throws Exception;

    /**
     * Gets the component prototype factory under test in the fixture.
     * 
     * @return The component prototype factory under test in the fixture.
     */
    protected final IComponentPrototypeFactory getComponentPrototypeFactory()
    {
        return componentPrototypeFactory_.get();
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
        componentPrototypeFactory_ = Optional.of( createComponentPrototypeFactory() );
    }

    /**
     * Placeholder for future interface tests.
     */
    @Test
    public void testDummy()
    {
        // do nothing
    }
}
