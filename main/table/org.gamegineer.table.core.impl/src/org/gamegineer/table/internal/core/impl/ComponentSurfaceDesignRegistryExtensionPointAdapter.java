/*
 * ComponentSurfaceDesignRegistryExtensionPointAdapter.java
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
 * Created on Apr 7, 2012 at 9:37:50 PM.
 */

package org.gamegineer.table.internal.core.impl;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jdt.annotation.NonNull;
import org.gamegineer.common.core.util.registry.AbstractRegistryExtensionPointAdapter;
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.core.ComponentSurfaceDesignsExtensionPoint;

/**
 * A component that adapts component surface designs published via the
 * {@code org.gamegineer.table.core.componentSurfaceDesigns} extension point to
 * the component surface design registry.
 */
@ThreadSafe
public final class ComponentSurfaceDesignRegistryExtensionPointAdapter
    extends AbstractRegistryExtensionPointAdapter<@NonNull ComponentSurfaceDesignId, @NonNull ComponentSurfaceDesign>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The configuration element attribute specifying the component surface
     * design height.
     */
    private static final String ATTR_HEIGHT = "height"; //$NON-NLS-1$

    /**
     * The configuration element attribute specifying the component surface
     * design identifier.
     */
    private static final String ATTR_ID = "id"; //$NON-NLS-1$

    /**
     * The configuration element attribute specifying the component surface
     * design width.
     */
    private static final String ATTR_WIDTH = "width"; //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ComponentSurfaceDesignRegistryExtensionPointAdapter} class.
     */
    public ComponentSurfaceDesignRegistryExtensionPointAdapter()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.util.registry.AbstractRegistryExtensionPointAdapter#createObject(org.eclipse.core.runtime.IConfigurationElement)
     */
    @Override
    protected ComponentSurfaceDesign createObject(
        final IConfigurationElement configurationElement )
    {
        final String idString = configurationElement.getAttribute( ATTR_ID );
        if( idString == null )
        {
            throw new IllegalArgumentException( NonNlsMessages.ComponentSurfaceDesignRegistryExtensionPointAdapter_createObject_missingId );
        }
        final ComponentSurfaceDesignId id = ComponentSurfaceDesignId.fromString( idString );

        final int width;
        try
        {
            width = Integer.parseInt( configurationElement.getAttribute( ATTR_WIDTH ) );
        }
        catch( final NumberFormatException e )
        {
            throw new IllegalArgumentException( NonNlsMessages.ComponentSurfaceDesignRegistryExtensionPointAdapter_createObject_parseWidthError, e );
        }

        final int height;
        try
        {
            height = Integer.parseInt( configurationElement.getAttribute( ATTR_HEIGHT ) );
        }
        catch( final NumberFormatException e )
        {
            throw new IllegalArgumentException( NonNlsMessages.ComponentSurfaceDesignRegistryExtensionPointAdapter_createObject_parseHeightError, e );
        }

        return new ComponentSurfaceDesign( id, width, height );
    }

    /*
     * @see org.gamegineer.common.core.util.registry.AbstractRegistryExtensionPointAdapter#getExtensionPointId()
     */
    @Override
    protected String getExtensionPointId()
    {
        return ComponentSurfaceDesignsExtensionPoint.UNIQUE_ID;
    }
}
