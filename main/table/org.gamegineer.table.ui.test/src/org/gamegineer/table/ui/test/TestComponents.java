/*
 * TestComponents.java
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
 * Created on Dec 8, 2012 at 7:46:18 PM.
 */

package org.gamegineer.table.ui.test;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.ITableEnvironment;
import org.gamegineer.table.ui.IComponentStrategyUI;
import org.gamegineer.table.ui.IContainerStrategyUI;

/**
 * A factory for creating various types of components suitable for user
 * interface testing.
 * 
 * <p>
 * The methods of this class should be used in lieu of the methods of
 * {@link org.gamegineer.table.core.test.TestComponents} because they ensure
 * that a corresponding user interface object is registered for each table
 * domain object created.
 * </p>
 */
@ThreadSafe
public final class TestComponents
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TestComponents} class.
     */
    private TestComponents()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new component with unique surface designs for the specified
     * table environment and registers a default component strategy user
     * interface for it.
     * 
     * @param tableEnvironment
     *        The table environment associated with the new component; must not
     *        be {@code null}.
     * 
     * @return A new component; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code tableEnvironment} is {@code null}.
     */
    /* @NonNull */
    public static IComponent createUniqueComponent(
        /* @NonNull */
        final ITableEnvironment tableEnvironment )
    {
        return createUniqueComponent( tableEnvironment, TestComponentStrategyUIs.NULL_COMPONENT_STRATEGY_UI );
    }

    /**
     * Creates a new component with unique surface designs for the specified
     * table environment and registers the specified component strategy user
     * interface for it.
     * 
     * @param tableEnvironment
     *        The table environment associated with the new component; must not
     *        be {@code null}.
     * @param componentStrategyUI
     *        The component strategy user interface; must not be {@code null}.
     * 
     * @return A new component; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code tableEnvironment} or {@code componentStrategyUI} is
     *         {@code null}.
     */
    /* @NonNull */
    public static IComponent createUniqueComponent(
        /* @NonNull */
        final ITableEnvironment tableEnvironment,
        /* @NonNull */
        final IComponentStrategyUI componentStrategyUI )
    {
        final IComponent component = org.gamegineer.table.core.test.TestComponents.createUniqueComponent( tableEnvironment );
        @SuppressWarnings( "unused" )
        final IComponentStrategyUI decoratedComponentStrategyUI = TestComponentStrategyUIs.createComponentStrategyUI( component.getStrategy().getId(), componentStrategyUI );
        return component;
    }

    /**
     * Creates a new container with unique surface designs for the specified
     * table environment and registers a default container strategy user
     * interface for it.
     * 
     * @param tableEnvironment
     *        The table environment associated with the new container; must not
     *        be {@code null}.
     * 
     * @return A new container; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code tableEnvironment} is {@code null}.
     */
    /* @NonNull */
    public static IContainer createUniqueContainer(
        /* @NonNull */
        final ITableEnvironment tableEnvironment )
    {
        return createUniqueContainer( tableEnvironment, TestComponentStrategyUIs.NULL_CONTAINER_STRATEGY_UI );
    }

    /**
     * Creates a new container with unique surface designs for the specified
     * table environment and registers the specified container strategy user
     * interface for it.
     * 
     * @param tableEnvironment
     *        The table environment associated with the new container; must not
     *        be {@code null}.
     * @param containerStrategyUI
     *        The container strategy user interface; must not be {@code null}.
     * 
     * @return A new container; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code tableEnvironment} or {@code containerStrategyUI} is
     *         {@code null}.
     */
    /* @NonNull */
    public static IContainer createUniqueContainer(
        /* @NonNull */
        final ITableEnvironment tableEnvironment,
        /* @NonNull */
        final IContainerStrategyUI containerStrategyUI )
    {
        final IContainer container = org.gamegineer.table.core.test.TestComponents.createUniqueContainer( tableEnvironment );
        @SuppressWarnings( "unused" )
        final IContainerStrategyUI decoratedContainerStrategyUI = TestComponentStrategyUIs.createContainerStrategyUI( container.getStrategy().getId(), containerStrategyUI );
        return container;
    }
}
