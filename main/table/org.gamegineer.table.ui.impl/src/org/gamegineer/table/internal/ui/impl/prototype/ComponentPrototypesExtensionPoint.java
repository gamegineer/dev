/*
 * ComponentPrototypesExtensionPoint.java
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
 * Created on Oct 9, 2012 at 7:45:04 PM.
 */

package org.gamegineer.table.internal.ui.impl.prototype;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.NullAnalysis.nonNull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.KeyStroke;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.ExpressionConverter;
import org.eclipse.core.expressions.ExpressionTagNames;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.table.internal.ui.impl.Activator;
import org.gamegineer.table.internal.ui.impl.Loggers;
import org.gamegineer.table.ui.prototype.IComponentPrototypeFactory;

/**
 * A facade for working with the
 * {@code org.gamegineer.table.ui.componentPrototypes} extension point.
 */
@ThreadSafe
public final class ComponentPrototypesExtensionPoint
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The category path separator. */
    private static final String CATEGORY_PATH_SEPARATOR = "/"; //$NON-NLS-1$

    /**
     * The name of the component prototype configuration element attribute that
     * represents the component prototype category.
     */
    private static final String COMPONENT_PROTOTYPE_ATTR_CATEGORY = "category"; //$NON-NLS-1$

    /**
     * The name of the component prototype configuration element attribute that
     * represents the component prototype mnemonic.
     */
    private static final String COMPONENT_PROTOTYPE_ATTR_MNEMONIC = "mnemonic"; //$NON-NLS-1$

    /**
     * The name of the component prototype configuration element attribute that
     * represents the component prototype name.
     */
    private static final String COMPONENT_PROTOTYPE_ATTR_NAME = "name"; //$NON-NLS-1$

    /**
     * The name of the component prototype category configuration element
     * attribute that represents the category identifier.
     */
    private static final String COMPONENT_PROTOTYPE_CATEGORY_ATTR_ID = "id"; //$NON-NLS-1$

    /**
     * The name of the component prototype category configuration element
     * attribute that represents the category mnemonic.
     */
    private static final String COMPONENT_PROTOTYPE_CATEGORY_ATTR_MNEMONIC = "mnemonic"; //$NON-NLS-1$

    /**
     * The name of the component prototype category configuration element
     * attribute that represents the category name.
     */
    private static final String COMPONENT_PROTOTYPE_CATEGORY_ATTR_NAME = "name"; //$NON-NLS-1$

    /**
     * The name of the component prototype category configuration element
     * attribute that represents the path of the parent category.
     */
    private static final String COMPONENT_PROTOTYPE_CATEGORY_ATTR_PARENT_CATEGORY = "parentCategory"; //$NON-NLS-1$

    /** The name of the component prototype category configuration element. */
    private static final String COMPONENT_PROTOTYPE_CATEGORY_ELEM_NAME = "category"; //$NON-NLS-1$

    /** The name of the component prototype configuration element. */
    private static final String COMPONENT_PROTOTYPE_ELEM_NAME = "componentPrototype"; //$NON-NLS-1$

    /** The name of the component prototype factory configuration element. */
    private static final String COMPONENT_PROTOTYPE_FACTORY_ELEM_NAME = "factory"; //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ComponentPrototypesExtensionPoint} class.
     */
    private ComponentPrototypesExtensionPoint()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Builds the component prototype menu using the extension registry.
     * 
     * @param rootMenu
     *        The root menu; must not be {@code null}.
     * @param menuItemAction
     *        The action used for all menu items; must not be {@code null}.
     * @param evaluationContextProvider
     *        The evaluation context provider; must not be {@code null}.
     */
    public static void buildMenu(
        final JMenu rootMenu,
        final Action menuItemAction,
        final IEvaluationContextProvider evaluationContextProvider )
    {
        rootMenu.addMenuListener( new MenuListener()
        {
            @Override
            public void menuCanceled(
                @Nullable
                @SuppressWarnings( "unused" )
                final MenuEvent event )
            {
                // do nothing
            }

            @Override
            public void menuDeselected(
                @Nullable
                @SuppressWarnings( "unused" )
                final MenuEvent event )
            {
                // do nothing
            }

            @Override
            @SuppressWarnings( "synthetic-access" )
            public void menuSelected(
                @Nullable
                final MenuEvent event )
            {
                assert event != null;

                buildMenuInternal( nonNull( (JMenu)event.getSource() ), menuItemAction, evaluationContextProvider );
            }
        } );
    }

    /**
     * Builds the component prototype menu using the extension registry.
     * 
     * @param rootMenu
     *        The root menu; must not be {@code null}.
     * @param menuItemAction
     *        The action used for all menu items; must not be {@code null}.
     * @param evaluationContextProvider
     *        The evaluation context provider; must not be {@code null}.
     */
    private static void buildMenuInternal(
        final JMenu rootMenu,
        final Action menuItemAction,
        final IEvaluationContextProvider evaluationContextProvider )
    {
        final ComponentPrototypeMenuBuilder menuBuilder = new ComponentPrototypeMenuBuilder( menuItemAction );

        final IExtensionRegistry extensionRegistry = Activator.getDefault().getExtensionRegistry();
        if( extensionRegistry != null )
        {
            for( final IConfigurationElement configurationElement : extensionRegistry.getConfigurationElementsFor( org.gamegineer.table.ui.prototype.ComponentPrototypesExtensionPoint.UNIQUE_ID ) )
            {
                if( COMPONENT_PROTOTYPE_ELEM_NAME.equals( configurationElement.getName() ) )
                {
                    if( isConfigurationElementEnabled( configurationElement, evaluationContextProvider ) )
                    {
                        try
                        {
                            menuBuilder.addComponentPrototype( createComponentPrototype( configurationElement ) );
                        }
                        catch( final IllegalArgumentException e )
                        {
                            Loggers.getDefaultLogger().log( Level.WARNING, NonNlsMessages.ComponentPrototypesExtensionPoint_buildMenu_illegalComponentPrototypeConfigurationElement, e );
                        }
                    }
                }
                else if( COMPONENT_PROTOTYPE_CATEGORY_ELEM_NAME.equals( configurationElement.getName() ) )
                {
                    try
                    {
                        menuBuilder.addComponentPrototypeCategory( createComponentPrototypeCategory( configurationElement ) );
                    }
                    catch( final IllegalArgumentException e )
                    {
                        Loggers.getDefaultLogger().log( Level.WARNING, NonNlsMessages.ComponentPrototypesExtensionPoint_buildMenu_illegalComponentPrototypeCategoryConfigurationElement, e );
                    }
                }
            }
        }

        menuBuilder.buildMenu( rootMenu );
    }

    /**
     * Creates a new component prototype from the specified component prototype
     * configuration element.
     * 
     * @param configurationElement
     *        The component prototype configuration element; must not be
     *        {@code null}.
     * 
     * @return A new component prototype; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code configurationElement} does not represent a legal
     *         component prototype.
     */
    private static ComponentPrototype createComponentPrototype(
        final IConfigurationElement configurationElement )
    {
        final String categoryId = configurationElement.getAttribute( COMPONENT_PROTOTYPE_ATTR_CATEGORY );

        final String name = configurationElement.getAttribute( COMPONENT_PROTOTYPE_ATTR_NAME );
        assertArgumentLegal( name != null, "configurationElement", NonNlsMessages.ComponentPrototypesExtensionPoint_createComponentPrototype_missingName ); //$NON-NLS-1$
        assert name != null;

        final String encodedMnemonic = configurationElement.getAttribute( COMPONENT_PROTOTYPE_ATTR_MNEMONIC );
        assertArgumentLegal( encodedMnemonic != null, "configurationElement", NonNlsMessages.ComponentPrototypesExtensionPoint_createComponentPrototype_missingMnemonic ); //$NON-NLS-1$
        assert encodedMnemonic != null;
        final int mnemonic = decodeMnemonic( encodedMnemonic );

        final IConfigurationElement[] factoryConfigurationElements = configurationElement.getChildren( COMPONENT_PROTOTYPE_FACTORY_ELEM_NAME );
        assertArgumentLegal( factoryConfigurationElements.length == 1, "configurationElement", NonNlsMessages.ComponentPrototypesExtensionPoint_createComponentPrototype_missingFactory ); //$NON-NLS-1$
        final IComponentPrototypeFactory componentPrototypeFactory = new ComponentPrototypeFactoryProxy( configurationElement, COMPONENT_PROTOTYPE_FACTORY_ELEM_NAME );

        return new ComponentPrototype( name, mnemonic, categoryId, componentPrototypeFactory );
    }

    /**
     * Creates a new component prototype category from the specified component
     * prototype category configuration element.
     * 
     * @param configurationElement
     *        The component prototype category configuration element; must not
     *        be {@code null}.
     * 
     * @return A new component prototype category; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code configurationElement} does not represent a legal
     *         component prototype category.
     */
    private static ComponentPrototypeCategory createComponentPrototypeCategory(
        final IConfigurationElement configurationElement )
    {
        final String id = configurationElement.getAttribute( COMPONENT_PROTOTYPE_CATEGORY_ATTR_ID );
        assertArgumentLegal( id != null, "configurationElement", NonNlsMessages.ComponentPrototypesExtensionPoint_createComponentPrototypeCategory_missingId ); //$NON-NLS-1$
        assert id != null;

        final String name = configurationElement.getAttribute( COMPONENT_PROTOTYPE_CATEGORY_ATTR_NAME );
        assertArgumentLegal( name != null, "configurationElement", NonNlsMessages.ComponentPrototypesExtensionPoint_createComponentPrototypeCategory_missingName ); //$NON-NLS-1$
        assert name != null;

        final String encodedMnemonic = configurationElement.getAttribute( COMPONENT_PROTOTYPE_CATEGORY_ATTR_MNEMONIC );
        assertArgumentLegal( encodedMnemonic != null, "configurationElement", NonNlsMessages.ComponentPrototypesExtensionPoint_createComponentPrototypeCategory_missingMnemonic ); //$NON-NLS-1$
        assert encodedMnemonic != null;
        final int mnemonic = decodeMnemonic( encodedMnemonic );

        final String encodedParentCategoryPath = configurationElement.getAttribute( COMPONENT_PROTOTYPE_CATEGORY_ATTR_PARENT_CATEGORY );
        final List<String> parentCategoryPath = decodeCategoryPath( encodedParentCategoryPath );

        return new ComponentPrototypeCategory( id, name, mnemonic, parentCategoryPath );
    }

    /**
     * Decodes the specified string as a category path.
     * 
     * @param source
     *        The string to decode; may be {@code null}.
     * 
     * @return The decoded category path; never {@code null}.
     */
    private static List<String> decodeCategoryPath(
        @Nullable
        final String source )
    {
        if( source == null )
        {
            return Collections.<@NonNull String>emptyList();
        }

        return nonNull( Arrays.asList( source.split( CATEGORY_PATH_SEPARATOR ) ) );
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
        final String source )
    {
        final KeyStroke keyStroke = KeyStroke.getKeyStroke( source );
        if( keyStroke == null )
        {
            throw new IllegalArgumentException( NonNlsMessages.ComponentPrototypesExtensionPoint_decodeMnemonic_illegalSource );
        }

        return keyStroke.getKeyCode();
    }

    /**
     * Indicates the specified configuration element is enabled.
     * 
     * @param configurationElement
     *        The configuration element; must not be {@code null}.
     * @param evaluationContextProvider
     *        The evaluation context provider; must not be {@code null}.
     * 
     * @return {@code true} if the specified configuration element is enabled;
     *         otherwise {@code false}.
     */
    private static boolean isConfigurationElementEnabled(
        final IConfigurationElement configurationElement,
        final IEvaluationContextProvider evaluationContextProvider )
    {
        try
        {
            final IConfigurationElement[] enablementConfigurationElements = configurationElement.getChildren( ExpressionTagNames.ENABLEMENT );
            if( enablementConfigurationElements.length == 0 )
            {
                return true;
            }

            final ExpressionConverter expressionConverter = ExpressionConverter.getDefault();
            final Expression expression = expressionConverter.perform( enablementConfigurationElements[ 0 ] );
            if( expression == null )
            {
                Loggers.getDefaultLogger().warning( NonNlsMessages.ComponentPrototypesExtensionPoint_isConfigurationElementEnabled_unconvertable );
                return false;
            }

            return expression.evaluate( evaluationContextProvider.getEvaluationContext() ) == EvaluationResult.TRUE;
        }
        catch( final CoreException e )
        {
            Loggers.getDefaultLogger().log( Level.WARNING, NonNlsMessages.ComponentPrototypesExtensionPoint_isConfigurationElementEnabled_error, e );
            return false;
        }
    }
}
