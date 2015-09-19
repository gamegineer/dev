/*
 * ComponentStrategyExtensionFactory.java
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
 * Created on Aug 4, 2012 at 9:44:14 PM.
 */

package org.gamegineer.table.internal.core.impl.strategies;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IExecutableExtensionFactory;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.table.core.ComponentStrategyId;
import org.gamegineer.table.core.IComponentStrategy;
import org.gamegineer.table.internal.core.impl.BundleConstants;

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

    /**
     * The configuration element attribute specifying the component strategy
     * identifier.
     */
    private static final String ATTR_ID = "id"; //$NON-NLS-1$

    /** The collection of component strategies supported by this factory. */
    private static final Map<ComponentStrategyId, IComponentStrategy> COMPONENT_STRATEGIES;

    /** The identifier of the component strategy to create. */
    private @Nullable ComponentStrategyId componentStrategyId_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code ComponentStrategyExtensionFactory} class.
     */
    static
    {
        final Map<ComponentStrategyId, IComponentStrategy> componentStrategies = new HashMap<>();
        componentStrategies.put( InternalComponentStrategies.TABLETOP.getId(), InternalComponentStrategies.TABLETOP );
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
        final @Nullable IConfigurationElement config,
        final @Nullable String propertyName,
        final @Nullable Object data )
        throws CoreException
    {
        assert config != null;

        final String idString = config.getAttribute( ATTR_ID );
        if( idString == null )
        {
            throw new CoreException( new Status( Status.ERROR, BundleConstants.SYMBOLIC_NAME, NonNlsMessages.ComponentStrategyExtensionFactory_setInitializationData_missingId ) );
        }

        componentStrategyId_ = ComponentStrategyId.fromString( idString );
    }
}
