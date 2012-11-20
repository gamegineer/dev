/*
 * ComponentStrategyRegistryExtensionPointAdapter.java
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
 * Created on Aug 3, 2012 at 9:30:23 PM.
 */

package org.gamegineer.table.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.gamegineer.common.core.util.registry.AbstractRegistryExtensionPointAdapter;
import org.gamegineer.table.core.ComponentStrategyId;
import org.gamegineer.table.core.IComponentStrategy;

/**
 * A component that adapts component strategies published via the
 * {@code org.gamegineer.table.core.componentStrategies} extension point to the
 * component strategy registry.
 */
@ThreadSafe
public final class ComponentStrategyRegistryExtensionPointAdapter
    extends AbstractRegistryExtensionPointAdapter<ComponentStrategyId, IComponentStrategy>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The configuration element attribute specifying the component strategy
     * class name.
     */
    private static final String ATTR_CLASS_NAME = "className"; //$NON-NLS-1$

    /**
     * The configuration element attribute specifying the component strategy
     * identifier.
     */
    private static final String ATTR_ID = "id"; //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ComponentStrategyRegistryExtensionPointAdapter} class.
     */
    public ComponentStrategyRegistryExtensionPointAdapter()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.util.registry.AbstractRegistryExtensionPointAdapter#createObject(org.eclipse.core.runtime.IConfigurationElement)
     */
    @Override
    protected IComponentStrategy createObject(
        final IConfigurationElement configurationElement )
    {
        assertArgumentNotNull( configurationElement, "configurationElement" ); //$NON-NLS-1$

        final String idString = configurationElement.getAttribute( ATTR_ID );
        if( idString == null )
        {
            throw new IllegalArgumentException( NonNlsMessages.ComponentStrategyRegistryExtensionPointAdapter_createObject_missingId );
        }
        @SuppressWarnings( "unused" )
        final ComponentStrategyId id = ComponentStrategyId.fromString( idString );

        // TODO: should be using a proxy here so we don't eagerly load the associated plug-in

        final IComponentStrategy componentStrategy;
        try
        {
            componentStrategy = (IComponentStrategy)configurationElement.createExecutableExtension( ATTR_CLASS_NAME );
        }
        catch( final CoreException e )
        {
            throw new IllegalArgumentException( NonNlsMessages.ComponentStrategyRegistryExtensionPointAdapter_createObject_createComponentStrategyError, e );
        }

        return componentStrategy;
    }

    /*
     * @see org.gamegineer.common.core.util.registry.AbstractRegistryExtensionPointAdapter#getExtensionPointId()
     */
    @Override
    protected String getExtensionPointId()
    {
        return BundleConstants.COMPONENT_STRATEGIES_EXTENSION_POINT_UNIQUE_ID;
    }
}
