/*
 * ContainerLayoutRegistryExtensionPointAdapter.java
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
 * Created on Aug 9, 2012 at 8:30:54 PM.
 */

package org.gamegineer.table.internal.core.impl;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.gamegineer.common.core.util.registry.AbstractRegistryExtensionPointAdapter;
import org.gamegineer.table.core.ContainerLayoutId;
import org.gamegineer.table.core.ContainerLayoutsExtensionPoint;
import org.gamegineer.table.core.IContainerLayout;

/**
 * A component that adapts container layouts published via the
 * {@code org.gamegineer.table.core.containerLayouts} extension point to the
 * container layout registry.
 */
@ThreadSafe
public final class ContainerLayoutRegistryExtensionPointAdapter
    extends AbstractRegistryExtensionPointAdapter<ContainerLayoutId, IContainerLayout>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The configuration element attribute specifying the container layout class
     * name.
     */
    private static final String ATTR_CLASS_NAME = "className"; //$NON-NLS-1$

    /**
     * The configuration element attribute specifying the container layout
     * identifier.
     */
    private static final String ATTR_ID = "id"; //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ContainerLayoutRegistryExtensionPointAdapter} class.
     */
    public ContainerLayoutRegistryExtensionPointAdapter()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.util.registry.AbstractRegistryExtensionPointAdapter#createObject(org.eclipse.core.runtime.IConfigurationElement)
     */
    @Override
    protected IContainerLayout createObject(
        final IConfigurationElement configurationElement )
    {
        assertArgumentNotNull( configurationElement, "configurationElement" ); //$NON-NLS-1$

        final String idString = configurationElement.getAttribute( ATTR_ID );
        if( idString == null )
        {
            throw new IllegalArgumentException( NonNlsMessages.ContainerLayoutRegistryExtensionPointAdapter_createObject_missingId );
        }
        @SuppressWarnings( "unused" )
        final ContainerLayoutId id = ContainerLayoutId.fromString( idString );

        // TODO: should be using a proxy here so we don't eagerly load the associated plug-in

        final IContainerLayout containerLayout;
        try
        {
            containerLayout = (IContainerLayout)configurationElement.createExecutableExtension( ATTR_CLASS_NAME );
        }
        catch( final CoreException e )
        {
            throw new IllegalArgumentException( NonNlsMessages.ContainerLayoutRegistryExtensionPointAdapter_createObject_createContainerLayoutError, e );
        }

        return containerLayout;
    }

    /*
     * @see org.gamegineer.common.core.util.registry.AbstractRegistryExtensionPointAdapter#getExtensionPointId()
     */
    @Override
    protected String getExtensionPointId()
    {
        return ContainerLayoutsExtensionPoint.UNIQUE_ID;
    }
}
