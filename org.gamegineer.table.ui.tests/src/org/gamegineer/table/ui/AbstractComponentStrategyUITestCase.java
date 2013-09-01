/*
 * AbstractComponentStrategyUITestCase.java
 * Copyright 2008-2012 Gamegineer contributors and others.
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

package org.gamegineer.table.ui;

import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.ui.IComponentStrategyUI} interface.
 * 
 * @param <ComponentStrategyUIType>
 *        The type of the component strategy user interface.
 */
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
    /* @NonNull */
    protected abstract ComponentStrategyUIType createComponentStrategyUI()
        throws Exception;

    /**
     * Gets the component strategy user interface under test in the fixture.
     * 
     * @return The component strategy user interface under test in the fixture;
     *         never {@code null}.
     */
    /* @NonNull */
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
     * Ensures the {@link IComponentStrategyUI#getId} method does not return
     * {@code null}.
     */
    @Test
    public void testGetId_ReturnValue_NonNull()
    {
        assertNotNull( componentStrategyUI_.getId() );
    }
}
