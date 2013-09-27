/*
 * ComponentFactory.java
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
 * Created on Jul 27, 2012 at 10:43:08 PM.
 */

package org.gamegineer.table.internal.core;

import java.util.HashMap;
import java.util.Map;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.memento.MementoException;
import org.gamegineer.table.core.ComponentStrategyId;
import org.gamegineer.table.core.ComponentStrategyRegistry;
import org.gamegineer.table.core.IComponentStrategy;
import org.gamegineer.table.core.IContainerStrategy;
import org.gamegineer.table.core.NoSuchComponentStrategyException;

/**
 * A factory for creating table components.
 */
@ThreadSafe
final class ComponentFactory
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The name of the memento attribute that stores the component class name. */
    private static final String CLASS_NAME_MEMENTO_ATTRIBUTE_NAME = "componentFactory.className"; //$NON-NLS-1$

    /**
     * The name of the memento attribute that stores the component strategy
     * identifier.
     */
    private static final String STRATEGY_ID_MEMENTO_ATTRIBUTE_NAME = "componentFactory.strategyId"; //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentFactory} class.
     */
    private ComponentFactory()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a component for the specified table environment from the
     * specified memento.
     * 
     * @param tableEnvironment
     *        The table environment; must not be {@code null}.
     * @param memento
     *        The component memento; must not be {@code null}.
     * 
     * @return A new component; never {@code null}.
     * 
     * @throws org.gamegineer.common.core.util.memento.MementoException
     *         If {@code memento} is malformed.
     */
    /* @NonNull */
    static Component createComponent(
        /* @NonNull */
        final TableEnvironment tableEnvironment,
        /* @NonNull */
        final Object memento )
        throws MementoException
    {
        assert tableEnvironment != null;
        assert memento != null;

        final IComponentStrategy strategy = getComponentStrategy( MementoUtils.getAttribute( memento, STRATEGY_ID_MEMENTO_ATTRIBUTE_NAME, ComponentStrategyId.class ) );
        final Component component = createComponent( MementoUtils.getAttribute( memento, CLASS_NAME_MEMENTO_ATTRIBUTE_NAME, String.class ), tableEnvironment, strategy );
        component.setMemento( memento );
        return component;
    }

    /**
     * Creates a component of the specified class for the specified table
     * environment using the specified component strategy.
     * 
     * @param className
     *        The component class name; must not be {@code null}.
     * @param tableEnvironment
     *        The table environment; must not be {@code null}.
     * @param strategy
     *        The component strategy; must not be {@code null}.
     * 
     * @return A new component; never {@code null}.
     * 
     * @throws org.gamegineer.common.core.util.memento.MementoException
     *         If the component cannot be created.
     */
    /* @NonNull */
    private static Component createComponent(
        /* @NonNull */
        final String className,
        /* @NonNull */
        final TableEnvironment tableEnvironment,
        /* @NonNull */
        final IComponentStrategy strategy )
        throws MementoException
    {
        assert className != null;
        assert tableEnvironment != null;
        assert strategy != null;

        if( className.equals( Component.class.getName() ) )
        {
            return new Component( tableEnvironment, strategy );
        }
        else if( className.equals( Container.class.getName() ) )
        {
            if( strategy instanceof IContainerStrategy )
            {
                return new Container( tableEnvironment, (IContainerStrategy)strategy );
            }

            throw new MementoException( NonNlsMessages.ComponentFactory_createComponent_illegalComponentStrategy );
        }

        throw new MementoException( NonNlsMessages.ComponentFactory_createComponent_unknownComponentType );
    }

    /**
     * Creates a new empty memento for the specified component.
     * 
     * @param component
     *        The component; must not be {@code null}.
     * 
     * @return A new empty memento for the specified component; never
     *         {@code null}.
     */
    /* @NonNull */
    static Map<String, Object> createMemento(
        /* @NonNull */
        final Component component )
    {
        assert component != null;

        final Map<String, Object> memento = new HashMap<>();
        memento.put( CLASS_NAME_MEMENTO_ATTRIBUTE_NAME, component.getClass().getName() );
        memento.put( STRATEGY_ID_MEMENTO_ATTRIBUTE_NAME, component.getStrategy().getId() );
        return memento;
    }

    /**
     * Gets the component strategy associated with the specified identifier.
     * 
     * @param id
     *        The component strategy identifier; must not be {@code null}.
     * 
     * @return The component strategy associated with the specified identifier;
     *         never {@code null}.
     * 
     * @throws org.gamegineer.common.core.util.memento.MementoException
     *         If the component strategy is unknown.
     */
    /* @NonNull */
    private static IComponentStrategy getComponentStrategy(
        /* @NonNull */
        final ComponentStrategyId id )
        throws MementoException
    {
        assert id != null;

        try
        {
            return ComponentStrategyRegistry.getComponentStrategy( id );
        }
        catch( final NoSuchComponentStrategyException e )
        {
            throw new MementoException( e );
        }
    }
}