/*
 * TestComponentStrategyUIs.java
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
 * Created on Sep 25, 2012 at 8:14:18 PM.
 */

package org.gamegineer.table.ui.test;

import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.ComponentStrategies;
import org.gamegineer.table.core.ComponentStrategyId;
import org.gamegineer.table.internal.ui.test.Activator;
import org.gamegineer.table.ui.IComponentStrategyUI;
import org.gamegineer.table.ui.IComponentStrategyUIRegistry;
import org.gamegineer.table.ui.IContainerStrategyUI;

/**
 * A factory for creating various types of component strategy user interfaces
 * suitable for testing.
 */
@ThreadSafe
public final class TestComponentStrategyUIs
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The null component strategy user interface. */
    static final IComponentStrategyUI NULL_COMPONENT_STRATEGY_UI = new NullComponentStrategyUI();

    /** The null container strategy user interface. */
    static final IContainerStrategyUI NULL_CONTAINER_STRATEGY_UI = new NullContainerStrategyUI();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TestComponentStrategyUIs} class.
     */
    private TestComponentStrategyUIs()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Clones the specified component strategy user interface.
     * 
     * @param componentStrategyUI
     *        The component strategy user interface to clone.
     * 
     * @return A new component strategy user interface.
     */
    public static IComponentStrategyUI cloneComponentStrategyUI(
        final IComponentStrategyUI componentStrategyUI )
    {
        return (componentStrategyUI instanceof IContainerStrategyUI) //
            ? createContainerStrategyUIDecorator( (IContainerStrategyUI)componentStrategyUI, componentStrategyUI.getId() ) //
            : createComponentStrategyUIDecorator( componentStrategyUI, componentStrategyUI.getId() );
    }

    /**
     * Creates a new component strategy user interface with the specified
     * identifier.
     * 
     * @param id
     *        The component strategy identifier.
     * 
     * @return A new component strategy user interface.
     */
    public static IComponentStrategyUI createComponentStrategyUI(
        final ComponentStrategyId id )
    {
        return createComponentStrategyUI( id, NULL_COMPONENT_STRATEGY_UI );
    }

    /**
     * Creates a new component strategy user interface with the specified
     * identifier that delegates to the specified component strategy user
     * interface.
     * 
     * @param id
     *        The component strategy identifier.
     * @param componentStrategyUI
     *        The component strategy user interface to which all operations will
     *        be delegated.
     * 
     * @return A new component strategy user interface.
     */
    public static IComponentStrategyUI createComponentStrategyUI(
        final ComponentStrategyId id,
        final IComponentStrategyUI componentStrategyUI )
    {
        return registerComponentStrategyUI( createComponentStrategyUIDecorator( componentStrategyUI, id ) );
    }

    /**
     * Creates a decorator for the specified component strategy user interface.
     * 
     * @param componentStrategyUI
     *        The component strategy user interface to be decorated.
     * @param componentStrategyId
     *        The component strategy identifier for the decorator.
     * 
     * @return A decorator for the specified component strategy user interface.
     */
    private static IComponentStrategyUI createComponentStrategyUIDecorator(
        final IComponentStrategyUI componentStrategyUI,
        final ComponentStrategyId componentStrategyId )
    {
        return new IComponentStrategyUI()
        {
            @Override
            public ComponentStrategyId getId()
            {
                return componentStrategyId;
            }

            @Override
            public boolean isFocusable()
            {
                return componentStrategyUI.isFocusable();
            }
        };
    }

    /**
     * Creates a new container strategy user interface with the specified
     * identifier.
     * 
     * @param id
     *        The component strategy identifier.
     * 
     * @return A new container strategy user interface.
     */
    public static IContainerStrategyUI createContainerStrategyUI(
        final ComponentStrategyId id )
    {
        return createContainerStrategyUI( id, NULL_CONTAINER_STRATEGY_UI );
    }

    /**
     * Creates a new container strategy user interface with the specified
     * identifier that delegates to the specified container strategy user
     * interface.
     * 
     * @param id
     *        The component strategy identifier.
     * @param containerStrategyUI
     *        The container strategy user interface to which all operations will
     *        be delegated.
     * 
     * @return A new container strategy user interface.
     */
    public static IContainerStrategyUI createContainerStrategyUI(
        final ComponentStrategyId id,
        final IContainerStrategyUI containerStrategyUI )
    {
        return registerComponentStrategyUI( createContainerStrategyUIDecorator( containerStrategyUI, id ) );
    }

    /**
     * Creates a decorator for the specified container strategy user interface.
     * 
     * @param containerStrategyUI
     *        The container strategy user interface to be decorated.
     * @param componentStrategyId
     *        The component strategy identifier for the decorator.
     * 
     * @return A decorator for the specified container strategy user interface.
     */
    private static IContainerStrategyUI createContainerStrategyUIDecorator(
        final IContainerStrategyUI containerStrategyUI,
        final ComponentStrategyId componentStrategyId )
    {
        return new IContainerStrategyUI()
        {
            @Override
            public ComponentStrategyId getId()
            {
                return componentStrategyId;
            }

            @Override
            public boolean isFocusable()
            {
                return containerStrategyUI.isFocusable();
            }
        };
    }

    /**
     * Registers the specified component strategy user interface with the
     * component strategy user interface registry.
     * 
     * @param <T>
     *        The type of the component strategy user interface.
     * 
     * @param componentStrategyUI
     *        The component strategy user interface.
     * 
     * @return The registered component strategy user interface.
     */
    private static <T extends IComponentStrategyUI> T registerComponentStrategyUI(
        final T componentStrategyUI )
    {
        final IComponentStrategyUIRegistry componentStrategyUIRegistry = Activator.getDefault().getComponentStrategyUIRegistry();
        assert componentStrategyUIRegistry != null;
        componentStrategyUIRegistry.registerObject( componentStrategyUI );
        return componentStrategyUI;
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A null component strategy user interface.
     */
    @Immutable
    private static class NullComponentStrategyUI
        implements IComponentStrategyUI
    {
        // ======================================================================
        // Constructors
        // ======================================================================

        /**
         * Initializes a new instance of the {@code NullComponentStrategyUI}
         * class.
         */
        NullComponentStrategyUI()
        {
        }


        // ======================================================================
        // Methods
        // ======================================================================

        /*
         * @see org.gamegineer.table.ui.IComponentStrategyUI#getId()
         */
        @Override
        public ComponentStrategyId getId()
        {
            return ComponentStrategies.NULL_COMPONENT.getId();
        }

        /*
         * @see org.gamegineer.table.ui.IComponentStrategyUI#isFocusable()
         */
        @Override
        public boolean isFocusable()
        {
            return false;
        }
    }

    /**
     * A null container strategy user interface.
     */
    @Immutable
    private static final class NullContainerStrategyUI
        extends NullComponentStrategyUI
        implements IContainerStrategyUI
    {
        // ======================================================================
        // Constructors
        // ======================================================================

        /**
         * Initializes a new instance of the {@code NullContainerStrategyUI}
         * class.
         */
        NullContainerStrategyUI()
        {
        }


        // ======================================================================
        // Methods
        // ======================================================================

        /*
         * @see org.gamegineer.table.ui.IComponentStrategyUI#getId()
         */
        @Override
        public ComponentStrategyId getId()
        {
            return ComponentStrategies.NULL_CONTAINER.getId();
        }

        /*
         * @see org.gamegineer.table.ui.IComponentStrategyUI#isFocusable()
         */
        @Override
        public boolean isFocusable()
        {
            return true;
        }
    }
}
