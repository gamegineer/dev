/*
 * TestComponentStrategyUIs.java
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
 * Created on Sep 25, 2012 at 8:14:18 PM.
 */

package org.gamegineer.table.ui;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.concurrent.atomic.AtomicLong;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.ComponentStrategyId;
import org.gamegineer.table.core.TestComponentStrategies;
import org.gamegineer.table.internal.ui.Activator;

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
    private static final IComponentStrategyUI NULL_COMPONENT_STRATEGY_UI = new NullComponentStrategyUI();

    /** The null container strategy user interface. */
    private static final IContainerStrategyUI NULL_CONTAINER_STRATEGY_UI = new NullContainerStrategyUI();

    /** The next unique component strategy identifier. */
    private static final AtomicLong nextComponentStrategyId_ = new AtomicLong();


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
     *        The component strategy user interface to clone; must not be
     *        {@code null}.
     * 
     * @return A new component strategy user interface; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code componentStrategyUI} is {@code null}.
     */
    /* @NonNull */
    public static IComponentStrategyUI cloneComponentStrategyUI(
        /* @NonNull */
        final IComponentStrategyUI componentStrategyUI )
    {
        assertArgumentNotNull( componentStrategyUI, "componentStrategyUI" ); //$NON-NLS-1$

        return (componentStrategyUI instanceof IContainerStrategyUI) //
            ? createContainerStrategyUIDecorator( (IContainerStrategyUI)componentStrategyUI, componentStrategyUI.getId() ) //
            : createComponentStrategyUIDecorator( componentStrategyUI, componentStrategyUI.getId() );
    }

    /**
     * Creates a decorator for the specified component strategy user interface.
     * 
     * @param componentStrategyUI
     *        The component strategy user interface to be decorated; must not be
     *        {@code null}.
     * @param componentStrategyId
     *        The component strategy identifier for the decorator; must not be
     *        {@code null}.
     * 
     * @return A decorator for the specified component strategy user interface;
     *         never {@code null}.
     */
    /* @NonNull */
    private static IComponentStrategyUI createComponentStrategyUIDecorator(
        /* @NonNull */
        final IComponentStrategyUI componentStrategyUI,
        /* @NonNull */
        final ComponentStrategyId componentStrategyId )
    {
        assert componentStrategyUI != null;
        assert componentStrategyId != null;

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
     * Creates a decorator for the specified container strategy user interface.
     * 
     * @param containerStrategyUI
     *        The container strategy user interface to be decorated; must not be
     *        {@code null}.
     * @param componentStrategyId
     *        The component strategy identifier for the decorator; must not be
     *        {@code null}.
     * 
     * @return A decorator for the specified container strategy user interface;
     *         never {@code null}.
     */
    /* @NonNull */
    private static IContainerStrategyUI createContainerStrategyUIDecorator(
        /* @NonNull */
        final IContainerStrategyUI containerStrategyUI,
        /* @NonNull */
        final ComponentStrategyId componentStrategyId )
    {
        assert containerStrategyUI != null;
        assert componentStrategyId != null;

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
     * Creates a new component strategy user interface with a unique identifier.
     * 
     * @return A new component strategy user interface; never {@code null}.
     */
    /* @NonNull */
    public static IComponentStrategyUI createUniqueComponentStrategyUI()
    {
        return registerComponentStrategyUI( createComponentStrategyUIDecorator( NULL_COMPONENT_STRATEGY_UI, getUniqueComponentStrategyId() ) );
    }

    /**
     * Creates a new container strategy user interface with a unique identifier.
     * 
     * @return A new container strategy user interface; never {@code null}.
     */
    /* @NonNull */
    public static IContainerStrategyUI createUniqueContainerStrategyUI()
    {
        return registerComponentStrategyUI( createContainerStrategyUIDecorator( NULL_CONTAINER_STRATEGY_UI, getUniqueComponentStrategyId() ) );
    }

    /**
     * Gets a unique component strategy identifier.
     * 
     * @return A unique component strategy identifier; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static ComponentStrategyId getUniqueComponentStrategyId()
    {
        return ComponentStrategyId.fromString( String.format( "component-strategy-%1$d", nextComponentStrategyId_.incrementAndGet() ) ); //$NON-NLS-1$
    }

    /**
     * Registers the specified component strategy user interface with the
     * component strategy user interface registry.
     * 
     * @param <T>
     *        The type of the component strategy user interface.
     * 
     * @param componentStrategyUI
     *        The component strategy user interface; must not be {@code null}.
     * 
     * @return The registered component strategy user interface; never
     *         {@code null}.
     */
    /* @NonNull */
    private static <T extends IComponentStrategyUI> T registerComponentStrategyUI(
        /* @NonNull */
        final T componentStrategyUI )
    {
        assert componentStrategyUI != null;

        final IComponentStrategyUIRegistry componentStrategyUIRegistry = Activator.getDefault().getComponentStrategyUIRegistry();
        assert componentStrategyUIRegistry != null;
        componentStrategyUIRegistry.registerComponentStrategyUI( componentStrategyUI );
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
            return TestComponentStrategies.NULL_COMPONENT_STRATEGY_ID;
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
            return TestComponentStrategies.NULL_CONTAINER_STRATEGY_ID;
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
