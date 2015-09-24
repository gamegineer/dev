/*
 * AbstractAbstractComponentStrategyTestCase.java
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
 * Created on Nov 29, 2012 at 11:33:21 PM.
 */

package org.gamegineer.table.core.test;

import java.util.Optional;
import org.gamegineer.table.core.AbstractComponentStrategy;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that extend the
 * {@link AbstractComponentStrategy} class.
 * 
 * @param <ComponentStrategyType>
 *        The type of the component strategy.
 */
public abstract class AbstractAbstractComponentStrategyTestCase<ComponentStrategyType extends AbstractComponentStrategy>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The component strategy under test in the fixture. */
    private Optional<ComponentStrategyType> componentStrategy_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractAbstractComponentStrategyTestCase} class.
     */
    protected AbstractAbstractComponentStrategyTestCase()
    {
        componentStrategy_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the component strategy to be tested.
     * 
     * @return The component strategy to be tested.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    protected abstract ComponentStrategyType createComponentStrategy()
        throws Exception;

    /**
     * Gets the component strategy under test in the fixture.
     * 
     * @return The component strategy under test in the fixture.
     */
    protected final ComponentStrategyType getComponentStrategy()
    {
        return componentStrategy_.get();
    }

    /**
     * Gets the identifier of the default component surface design for the
     * specified component strategy.
     * 
     * @param componentStrategy
     *        The component strategy.
     * 
     * @return The identifier of the default component surface design for the
     *         specified component strategy.
     */
    protected abstract ComponentSurfaceDesignId getDefaultSurfaceDesignId(
        ComponentStrategyType componentStrategy );

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
        componentStrategy_ = Optional.of( createComponentStrategy() );
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
