/*
 * ComponentSurfaceDesignUIRegistryExtensionPointAdapter.java
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
 * Created on Apr 23, 2012 at 8:22:36 PM.
 */

package org.gamegineer.table.internal.ui.impl;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.net.URL;
import javax.swing.Icon;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.core.runtime.ContributorFactoryOSGi;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Path;
import org.gamegineer.common.core.util.registry.AbstractRegistryExtensionPointAdapter;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.internal.ui.impl.util.swing.IconProxy;
import org.gamegineer.table.ui.ComponentSurfaceDesignUI;
import org.gamegineer.table.ui.ComponentSurfaceDesignUIsExtensionPoint;
import org.osgi.framework.Bundle;

/**
 * A component that adapts component surface design user interfaces published
 * via the {@code org.gamegineer.table.ui.componentSurfaceDesignUIs} extension
 * point to the component surface design user interface registry.
 */
@ThreadSafe
public final class ComponentSurfaceDesignUIRegistryExtensionPointAdapter
    extends AbstractRegistryExtensionPointAdapter<ComponentSurfaceDesignId, ComponentSurfaceDesignUI>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The configuration element attribute specifying the component surface
     * design icon.
     */
    private static final String ATTR_ICON = "icon"; //$NON-NLS-1$

    /**
     * The configuration element attribute specifying the component surface
     * design identifier.
     */
    private static final String ATTR_ID = "id"; //$NON-NLS-1$

    /**
     * The configuration element attribute specifying the component surface
     * design name.
     */
    private static final String ATTR_NAME = "name"; //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ComponentSurfaceDesignUIRegistryExtensionPointAdapter} class.
     */
    public ComponentSurfaceDesignUIRegistryExtensionPointAdapter()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.util.registry.AbstractRegistryExtensionPointAdapter#createObject(org.eclipse.core.runtime.IConfigurationElement)
     */
    @Override
    protected ComponentSurfaceDesignUI createObject(
        final IConfigurationElement configurationElement )
    {
        assertArgumentNotNull( configurationElement, "configurationElement" ); //$NON-NLS-1$

        final String idString = configurationElement.getAttribute( ATTR_ID );
        if( idString == null )
        {
            throw new IllegalArgumentException( NonNlsMessages.ComponentSurfaceDesignUIRegistryExtensionPointAdapter_createObject_missingId );
        }
        final ComponentSurfaceDesignId id = ComponentSurfaceDesignId.fromString( idString );

        final String name = configurationElement.getAttribute( ATTR_NAME );
        if( name == null )
        {
            throw new IllegalArgumentException( NonNlsMessages.ComponentSurfaceDesignUIRegistryExtensionPointAdapter_createObject_missingName );
        }

        final String iconPath = configurationElement.getAttribute( ATTR_ICON );
        if( iconPath == null )
        {
            throw new IllegalArgumentException( NonNlsMessages.ComponentSurfaceDesignUIRegistryExtensionPointAdapter_createObject_missingIconPath );
        }
        final Bundle bundle = ContributorFactoryOSGi.resolve( configurationElement.getContributor() );
        if( bundle == null )
        {
            throw new IllegalArgumentException( NonNlsMessages.ComponentSurfaceDesignUIRegistryExtensionPointAdapter_createObject_iconBundleNotFound( configurationElement.getNamespaceIdentifier() ) );
        }
        final URL iconUrl = FileLocator.find( bundle, new Path( iconPath ), null );
        if( iconUrl == null )
        {
            throw new IllegalArgumentException( NonNlsMessages.ComponentSurfaceDesignUIRegistryExtensionPointAdapter_createObject_iconFileNotFound( bundle, iconPath ) );
        }
        final Icon icon = new IconProxy( iconUrl );

        return new ComponentSurfaceDesignUI( id, name, icon );
    }

    /*
     * @see org.gamegineer.common.core.util.registry.AbstractRegistryExtensionPointAdapter#getExtensionPointId()
     */
    @Override
    protected String getExtensionPointId()
    {
        return ComponentSurfaceDesignUIsExtensionPoint.UNIQUE_ID;
    }
}
