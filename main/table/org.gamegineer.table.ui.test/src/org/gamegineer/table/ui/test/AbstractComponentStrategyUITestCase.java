/*
 * AbstractComponentStrategyUITestCase.java
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
 * Created on Sep 25, 2012 at 7:51:49 PM.
 */

package org.gamegineer.table.ui.test;

import static org.junit.Assert.assertNotNull;
import org.eclipse.jdt.annotation.DefaultLocation;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.gamegineer.table.ui.IComponentStrategyUI;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link IComponentStrategyUI} interface.
 * 
 * @param <ComponentStrategyUIType>
 *        The type of the component strategy user interface.
 */
@NonNullByDefault( { DefaultLocation.PARAMETER, DefaultLocation.RETURN_TYPE, DefaultLocation.TYPE_BOUND, DefaultLocation.TYPE_ARGUMENT } )
public abstract class AbstractComponentStrategyUITestCase<ComponentStrategyUIType extends IComponentStrategyUI>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The component strategy user interface under test in the fixture. */
    private ComponentStrategyUIType componentStrategyUI_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractComponentStrategyUITestCase} class.
     */
    protected AbstractComponentStrategyUITestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the component strategy user interface to be tested.
     * 
     * @return The component strategy user interface to be tested; never
     *         {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    protected abstract ComponentStrategyUIType createComponentStrategyUI()
        throws Exception;

    /**
     * Gets the component strategy user interface under test in the fixture.
     * 
     * @return The component strategy user interface under test in the fixture;
     *         never {@code null}.
     */
    protected final ComponentStrategyUIType getComponentStrategyUI()
    {
        assertNotNull( componentStrategyUI_ );
        return componentStrategyUI_;
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
        componentStrategyUI_ = createComponentStrategyUI();
        assertNotNull( componentStrategyUI_ );
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
