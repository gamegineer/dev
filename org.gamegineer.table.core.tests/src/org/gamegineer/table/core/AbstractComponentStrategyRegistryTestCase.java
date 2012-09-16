/*
 * AbstractComponentStrategyRegistryTestCase.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Aug 3, 2012 at 10:32:45 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.util.Collection;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.core.IComponentStrategyRegistry} interface.
 */
public abstract class AbstractComponentStrategyRegistryTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The component strategy registry under test in the fixture. */
    private IComponentStrategyRegistry componentStrategyRegistry_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractComponentStrategyRegistryTestCase} class.
     */
    protected AbstractComponentStrategyRegistryTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the component strategy registry to be tested.
     * 
     * @return The component strategy registry to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IComponentStrategyRegistry createComponentStrategyRegistry()
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
        componentStrategyRegistry_ = createComponentStrategyRegistry();
        assertNotNull( componentStrategyRegistry_ );
    }

    /**
     * Ensures the {@link IComponentStrategyRegistry#getComponentStrategies}
     * method returns a copy of the registered component strategy collection.
     */
    @Test
    public void testGetComponentStrategies_ReturnValue_Copy()
    {
        final Collection<IComponentStrategy> componentStrategies = componentStrategyRegistry_.getComponentStrategies();
        final int expectedComponentStrategiesSize = componentStrategies.size();

        componentStrategies.add( TestComponentStrategies.createUniqueComponentStrategy() );

        assertEquals( expectedComponentStrategiesSize, componentStrategyRegistry_.getComponentStrategies().size() );
    }

    /**
     * Ensures the {@link IComponentStrategyRegistry#getComponentStrategies}
     * method returns a snapshot of the registered component strategy
     * collection.
     */
    @Test
    public void testGetComponentStrategies_ReturnValue_Snapshot()
    {
        final Collection<IComponentStrategy> componentStrategies = componentStrategyRegistry_.getComponentStrategies();
        componentStrategyRegistry_.registerComponentStrategy( TestComponentStrategies.createUniqueComponentStrategy() );

        assertTrue( componentStrategies.size() != componentStrategyRegistry_.getComponentStrategies().size() );
    }

    /**
     * Ensures the {@link IComponentStrategyRegistry#getComponentStrategy}
     * method returns the correct value when passed an identifier that is
     * absent.
     */
    @Test
    public void testGetComponentStrategy_Id_Absent()
    {
        assertNull( componentStrategyRegistry_.getComponentStrategy( ComponentStrategyId.fromString( "unknownId" ) ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link IComponentStrategyRegistry#getComponentStrategy}
     * method throws an exception when passed a {@code null} identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testGetComponentStrategy_Id_Null()
    {
        componentStrategyRegistry_.getComponentStrategy( null );
    }

    /**
     * Ensures the {@link IComponentStrategyRegistry#getComponentStrategy}
     * method returns the correct value when passed an identifier that is
     * present.
     */
    @Test
    public void testGetComponentStrategy_Id_Present()
    {
        final IComponentStrategy expectedComponentStrategy = TestComponentStrategies.createUniqueComponentStrategy();
        componentStrategyRegistry_.registerComponentStrategy( expectedComponentStrategy );

        final IComponentStrategy actualComponentStrategy = componentStrategyRegistry_.getComponentStrategy( expectedComponentStrategy.getId() );

        assertSame( expectedComponentStrategy, actualComponentStrategy );
    }

    /**
     * Ensures the {@link IComponentStrategyRegistry#registerComponentStrategy}
     * method throws an exception when passed a {@code null} component strategy.
     */
    @Test( expected = NullPointerException.class )
    public void testRegisterComponentStrategy_ComponentStrategy_Null()
    {
        componentStrategyRegistry_.registerComponentStrategy( null );
    }

    /**
     * Ensures the {@link IComponentStrategyRegistry#registerComponentStrategy}
     * method throws an exception when a component strategy with the same
     * identifier is already registered.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRegisterComponentStrategy_ComponentStrategy_Registered()
    {
        final IComponentStrategy componentStrategy = TestComponentStrategies.createUniqueComponentStrategy();
        componentStrategyRegistry_.registerComponentStrategy( componentStrategy );

        componentStrategyRegistry_.registerComponentStrategy( TestComponentStrategies.cloneComponentStrategy( componentStrategy ) );
    }

    /**
     * Ensures the {@link IComponentStrategyRegistry#registerComponentStrategy}
     * method registers an unregistered component strategy.
     */
    @Test
    public void testRegisterComponentStrategy_ComponentStrategy_Unregistered()
    {
        final IComponentStrategy componentStrategy = TestComponentStrategies.createUniqueComponentStrategy();

        componentStrategyRegistry_.registerComponentStrategy( componentStrategy );

        assertTrue( componentStrategyRegistry_.getComponentStrategies().contains( componentStrategy ) );
    }

    /**
     * Ensures the
     * {@link IComponentStrategyRegistry#unregisterComponentStrategy} method
     * throws an exception when passed a {@code null} component strategy.
     */
    @Test( expected = NullPointerException.class )
    public void testUnregisterComponentStrategy_ComponentStrategy_Null()
    {
        componentStrategyRegistry_.unregisterComponentStrategy( null );
    }

    /**
     * Ensures the
     * {@link IComponentStrategyRegistry#unregisterComponentStrategy} method
     * throws an exception when passed a component strategy whose identifier was
     * previously registered but by a different component strategy instance.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnregisterComponentStrategy_ComponentStrategy_Registered_DifferentInstance()
    {
        final IComponentStrategy componentStrategy = TestComponentStrategies.createUniqueComponentStrategy();
        final int originalComponentStrategiesSize = componentStrategyRegistry_.getComponentStrategies().size();
        componentStrategyRegistry_.registerComponentStrategy( componentStrategy );
        assertEquals( originalComponentStrategiesSize + 1, componentStrategyRegistry_.getComponentStrategies().size() );

        componentStrategyRegistry_.unregisterComponentStrategy( TestComponentStrategies.cloneComponentStrategy( componentStrategy ) );
    }

    /**
     * Ensures the
     * {@link IComponentStrategyRegistry#unregisterComponentStrategy} method
     * unregisters a previously registered component strategy.
     */
    @Test
    public void testUnregisterComponentStrategy_ComponentStrategy_Registered_SameInstance()
    {
        final IComponentStrategy componentStrategy = TestComponentStrategies.createUniqueComponentStrategy();
        final int originalComponentStrategiesSize = componentStrategyRegistry_.getComponentStrategies().size();
        componentStrategyRegistry_.registerComponentStrategy( componentStrategy );
        assertEquals( originalComponentStrategiesSize + 1, componentStrategyRegistry_.getComponentStrategies().size() );

        componentStrategyRegistry_.unregisterComponentStrategy( componentStrategy );

        assertEquals( originalComponentStrategiesSize, componentStrategyRegistry_.getComponentStrategies().size() );
    }

    /**
     * Ensures the
     * {@link IComponentStrategyRegistry#unregisterComponentStrategy} method
     * throws an exception when passed a component strategy that was not
     * previously registered.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnregisterComponentStrategy_ComponentStrategy_Unregistered()
    {
        final IComponentStrategy componentStrategy = TestComponentStrategies.createUniqueComponentStrategy();

        componentStrategyRegistry_.unregisterComponentStrategy( componentStrategy );
    }
}
