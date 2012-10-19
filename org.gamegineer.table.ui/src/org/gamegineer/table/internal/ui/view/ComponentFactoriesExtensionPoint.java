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

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import javax.swing.JMenu;
import javax.swing.KeyStroke;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.gamegineer.table.internal.ui.Activator;
import org.gamegineer.table.internal.ui.BundleConstants;
import org.gamegineer.table.internal.ui.Loggers;
import org.gamegineer.table.ui.IComponentFactory;

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

    /**
     * The name of the category configuration element attribute that represents
     * the category identifier.
     */
    private static final String CATEGORY_ATTR_ID = "id"; //$NON-NLS-1$

    /**
     * The name of the category configuration element attribute that represents
     * the category mnemonic.
     */
    private static final String CATEGORY_ATTR_MNEMONIC = "mnemonic"; //$NON-NLS-1$

    /**
     * The name of the category configuration element attribute that represents
     * the category name.
     */
    private static final String CATEGORY_ATTR_NAME = "name"; //$NON-NLS-1$

    /**
     * The name of the category configuration element attribute that represents
     * the path of the parent category.
     */
    private static final String CATEGORY_ATTR_PARENT_CATEGORY = "parentCategory"; //$NON-NLS-1$

    /** The name of the category configuration element. */
    private static final String CATEGORY_ELEM_NAME = "category"; //$NON-NLS-1$

    /** The category path separator. */
    private static final String CATEGORY_PATH_SEPARATOR = "/"; //$NON-NLS-1$

    /**
     * The name of the component factory configuration element attribute that
     * represents the component factory category.
     */
    private static final String COMPONENT_FACTORY_ATTR_CATEGORY = "category"; //$NON-NLS-1$

    /**
     * The name of the component factory configuration element attribute that
     * represents the component factory class name.
     */
    private static final String COMPONENT_FACTORY_ATTR_CLASS_NAME = "className"; //$NON-NLS-1$

    /**
     * The name of the component factory configuration element attribute that
     * represents the component factory identifier.
     */
    private static final String COMPONENT_FACTORY_ATTR_ID = "id"; //$NON-NLS-1$

    /**
     * The name of the component factory configuration element attribute that
     * represents the component factory mnemonic.
     */
    private static final String COMPONENT_FACTORY_ATTR_MNEMONIC = "mnemonic"; //$NON-NLS-1$

    /**
     * The name of the component factory configuration element attribute that
     * represents the component factory name.
     */
    private static final String COMPONENT_FACTORY_ATTR_NAME = "name"; //$NON-NLS-1$

    /** The name of the component factory configuration element. */
    private static final String COMPONENT_FACTORY_ELEM_NAME = "componentFactory"; //$NON-NLS-1$


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
     * Creates a new component factory category from the specified component
     * factory category configuration element.
     * 
     * @param configurationElement
     *        The component factory category configuration element; must not be
     *        {@code null}.
     * 
     * @return A new component factory category; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code configurationElement} does not represent a legal
     *         component factory category.
     */
    /* @NonNull */
    private static ComponentFactoryCategory createCategory(
        /* @NonNull */
        final IConfigurationElement configurationElement )
    {
        assert configurationElement != null;

        final String id = configurationElement.getAttribute( CATEGORY_ATTR_ID );
        assertArgumentLegal( id != null, "configurationElement", NonNlsMessages.ComponentFactoriesExtensionPoint_createCategory_missingId ); //$NON-NLS-1$

        final String name = configurationElement.getAttribute( CATEGORY_ATTR_NAME );
        assertArgumentLegal( name != null, "configurationElement", NonNlsMessages.ComponentFactoriesExtensionPoint_createCategory_missingName ); //$NON-NLS-1$

        final String encodedMnemonic = configurationElement.getAttribute( CATEGORY_ATTR_MNEMONIC );
        assertArgumentLegal( encodedMnemonic != null, "configurationElement", NonNlsMessages.ComponentFactoriesExtensionPoint_createCategory_missingMnemonic ); //$NON-NLS-1$
        final int mnemonic = decodeMnemonic( encodedMnemonic );

        final String encodedParentCategoryPath = configurationElement.getAttribute( CATEGORY_ATTR_PARENT_CATEGORY );
        final List<String> parentCategoryPath = decodeCategoryPath( encodedParentCategoryPath );

        return new ComponentFactoryCategory( id, name, mnemonic, parentCategoryPath );
    }

    /**
     * Creates a new component factory from the specified component factory
     * configuration element.
     * 
     * @param configurationElement
     *        The component factory configuration element; must not be
     *        {@code null}.
     * 
     * @return A new component factory; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code configurationElement} does not represent a legal
     *         component factory.
     */
    /* @NonNull */
    private static ComponentFactory createComponentFactory(
        /* @NonNull */
        final IConfigurationElement configurationElement )
    {
        assert configurationElement != null;

        final String categoryId = configurationElement.getAttribute( COMPONENT_FACTORY_ATTR_CATEGORY );

        final String className = configurationElement.getAttribute( COMPONENT_FACTORY_ATTR_CLASS_NAME );
        assertArgumentLegal( className != null, "configurationElement", NonNlsMessages.ComponentFactoriesExtensionPoint_createComponentFactory_missingClassName ); //$NON-NLS-1$
        final IComponentFactory componentFactory = new ComponentFactoryProxy( configurationElement, COMPONENT_FACTORY_ATTR_CLASS_NAME );

        final String id = configurationElement.getAttribute( COMPONENT_FACTORY_ATTR_ID );
        assertArgumentLegal( id != null, "configurationElement", NonNlsMessages.ComponentFactoriesExtensionPoint_createComponentFactory_missingId ); //$NON-NLS-1$

        final String name = configurationElement.getAttribute( COMPONENT_FACTORY_ATTR_NAME );
        assertArgumentLegal( name != null, "configurationElement", NonNlsMessages.ComponentFactoriesExtensionPoint_createComponentFactory_missingName ); //$NON-NLS-1$

        final String encodedMnemonic = configurationElement.getAttribute( COMPONENT_FACTORY_ATTR_MNEMONIC );
        assertArgumentLegal( encodedMnemonic != null, "configurationElement", NonNlsMessages.ComponentFactoriesExtensionPoint_createComponentFactory_missingMnemonic ); //$NON-NLS-1$
        final int mnemonic = decodeMnemonic( encodedMnemonic );

        return new ComponentFactory( id, name, mnemonic, categoryId, componentFactory );
    }

    /**
     * Creates a new component factory menu using the extension registry.
     * 
     * @param rootMenuLabel
     *        The root menu label; must not be {@code null}.
     * @param rootMenuMnemonic
     *        The root menu mnemonic.
     * 
     * @return A new component factory menu; never {@code null}.
     */
    /* @NonNull */
    static JMenu createMenu(
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
                if( CATEGORY_ELEM_NAME.equals( configurationElement.getName() ) )
                {
                    try
                    {
                        menuBuilder.addCategory( createCategory( configurationElement ) );
                    }
                    catch( final IllegalArgumentException e )
                    {
                        Loggers.getDefaultLogger().log( Level.WARNING, NonNlsMessages.ComponentFactoriesExtensionPoint_createMenu_illegalCategoryConfigurationElement, e );
                    }
                }
                else if( COMPONENT_FACTORY_ELEM_NAME.equals( configurationElement.getName() ) )
                {
                    try
                    {
                        menuBuilder.addComponentFactory( createComponentFactory( configurationElement ) );
                    }
                    catch( final IllegalArgumentException e )
                    {
                        Loggers.getDefaultLogger().log( Level.WARNING, NonNlsMessages.ComponentFactoriesExtensionPoint_createMenu_illegalComponentFactoryConfigurationElement, e );
                    }
                }
            }
        }

        return menuBuilder.toMenu();
    }

    /**
     * Decodes the specified string as a category path.
     * 
     * @param source
     *        The string to decode; may be {@code null}.
     * 
     * @return The decoded category path; never {@code null}.
     */
    /* @NonNull */
    private static List<String> decodeCategoryPath(
        /* @Nullable */
        final String source )
    {
        if( source == null )
        {
            return Collections.emptyList();
        }

        return Arrays.asList( source.split( CATEGORY_PATH_SEPARATOR ) );
    }

    /**
     * Decodes the specified string as a mnemonic.
     * 
     * @param source
     *        The string to decode; may be {@code null}.
     * 
     * @return The decoded mnemonic; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code source} does not represent a legal mnemonic.
     */
    private static int decodeMnemonic(
        /* @NonNull */
        final String source )
    {
        assert source != null;

        final KeyStroke keyStroke = KeyStroke.getKeyStroke( source );
        if( keyStroke == null )
        {
            throw new IllegalArgumentException( NonNlsMessages.ComponentFactoriesExtensionPoint_decodeMnemonic_illegalSource );
        }

        return keyStroke.getKeyCode();
    }
}
