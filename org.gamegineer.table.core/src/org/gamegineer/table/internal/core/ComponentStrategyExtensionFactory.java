/*
 * ComponentStrategyExtensionFactory.java
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
 * Created on Aug 4, 2012 at 9:44:14 PM.
 */

package org.gamegineer.table.internal.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IExecutableExtensionFactory;
import org.eclipse.core.runtime.Status;
import org.gamegineer.table.core.ComponentStrategyId;
import org.gamegineer.table.core.IComponentStrategy;

/**
 * A factory for creating component strategies located in this bundle from the
 * extension registry.
 */
@NotThreadSafe
public final class ComponentStrategyExtensionFactory
    implements IExecutableExtension, IExecutableExtensionFactory
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The collection of component strategies supported by this factory. */
    private static final Map<ComponentStrategyId, IComponentStrategy> COMPONENT_STRATEGIES;

    /** The identifier of the component strategy to create. */
    private ComponentStrategyId componentStrategyId_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code ComponentStrategyExtensionFactory} class.
     */
    static
    {
        final Map<ComponentStrategyId, IComponentStrategy> componentStrategies = new HashMap<ComponentStrategyId, IComponentStrategy>();
        componentStrategies.put( CardStrategy.INSTANCE.getId(), CardStrategy.INSTANCE );
        componentStrategies.put( CardPileStrategy.INSTANCE.getId(), CardPileStrategy.INSTANCE );
        componentStrategies.put( TabletopStrategy.INSTANCE.getId(), TabletopStrategy.INSTANCE );
        COMPONENT_STRATEGIES = Collections.unmodifiableMap( componentStrategies );
    }

    /**
     * Initializes a new instance of the
     * {@code ComponentStrategyExtensionFactory} class.
     */
    public ComponentStrategyExtensionFactory()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.eclipse.core.runtime.IExecutableExtensionFactory#create()
     */
    @Override
    public Object create()
        throws CoreException
    {
        final IComponentStrategy componentStrategy = COMPONENT_STRATEGIES.get( componentStrategyId_ );
        if( componentStrategy == null )
        {
            throw new CoreException( new Status( Status.ERROR, BundleConstants.SYMBOLIC_NAME, NonNlsMessages.ComponentStrategyExtensionFactory_create_unknownId( componentStrategyId_ ) ) );
        }

        return componentStrategy;
    }

    /*
     * @see org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org.eclipse.core.runtime.IConfigurationElement, java.lang.String, java.lang.Object)
     */
    @Override
    public void setInitializationData(
        @SuppressWarnings( "unused" )
        final IConfigurationElement config,
        @SuppressWarnings( "unused" )
        final String propertyName,
        final Object data )
        throws CoreException
    {
        if( !(data instanceof String) )
        {
            throw new CoreException( new Status( Status.ERROR, BundleConstants.SYMBOLIC_NAME, NonNlsMessages.ComponentStrategyExtensionFactory_setInitializationData_unexpectedData ) );
        }

        componentStrategyId_ = ComponentStrategyId.fromString( (String)data );
    }
}
