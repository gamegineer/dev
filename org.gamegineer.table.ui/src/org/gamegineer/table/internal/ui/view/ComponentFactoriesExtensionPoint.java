/*
 * ComponentFactoriesExtensionPoint.java
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
 * Created on Oct 9, 2012 at 7:45:04 PM.
 */

package org.gamegineer.table.internal.ui.view;

import java.util.logging.Level;
import javax.swing.JMenu;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.gamegineer.table.internal.ui.Activator;
import org.gamegineer.table.internal.ui.BundleConstants;
import org.gamegineer.table.internal.ui.Loggers;

/**
 * A facade for working with the
 * {@code org.gamegineer.table.ui.componentFactories} extension point.
 */
@ThreadSafe
final class ComponentFactoriesExtensionPoint
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The name of the category element. */
    private static final String ELEM_CATEGORY = "category"; //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ComponentFactoriesExtensionPoint} class.
     */
    private ComponentFactoriesExtensionPoint()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new component factory menu from the extension registry.
     * 
     * @param rootMenuLabel
     *        The root menu label; must not be {@code null}.
     * @param rootMenuMnemonic
     *        The root menu mnemonic.
     * 
     * @return A new component factory menu; never {@code null}.
     */
    /* @NonNull */
    static JMenu createComponentFactoryMenu(
        /* @NonNull */
        final String rootMenuLabel,
        final int rootMenuMnemonic )
    {
        assert rootMenuLabel != null;

        final ComponentFactoryMenuBuilder menuBuilder = new ComponentFactoryMenuBuilder( rootMenuLabel, rootMenuMnemonic );

        final IExtensionRegistry extensionRegistry = Activator.getDefault().getExtensionRegistry();
        if( extensionRegistry != null )
        {
            for( final IConfigurationElement configurationElement : extensionRegistry.getConfigurationElementsFor( BundleConstants.COMPONENT_FACTORIES_EXTENSION_POINT_UNIQUE_ID ) )
            {
                if( ELEM_CATEGORY.equals( configurationElement.getName() ) )
                {
                    try
                    {
                        menuBuilder.addCategory( ComponentFactoryCategory.fromConfigurationElement( configurationElement ) );
                    }
                    catch( final IllegalArgumentException e )
                    {
                        Loggers.getDefaultLogger().log( Level.WARNING, NonNlsMessages.ComponentFactoriesExtensionPoint_createComponentFactoryMenu_illegalCategoryConfigurationElement, e );
                    }
                }
            }
        }

        return menuBuilder.toMenu();
    }
}
