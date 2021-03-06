/*
 * NonNlsMessages.java
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
 * Created on Oct 20, 2012 at 10:54:35 PM.
 */

package org.gamegineer.table.internal.ui.impl.prototype;

import java.util.Collection;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.osgi.util.NLS;

/**
 * A utility class to manage non-localized messages for the package.
 */
@ThreadSafe
final class NonNlsMessages
    extends NLS
{
    // ======================================================================
    // Fields
    // ======================================================================

    // --- ComponentPrototypeFactoryProxy -----------------------------------

    /** An error occurred while creating the component prototype factory. */
    public static String ComponentPrototypeFactoryProxy_getDelegate_createError = ""; //$NON-NLS-1$

    // --- ComponentPrototypeMenuBuilder ------------------------------------

    /** The component prototype categories are orphans. */
    public static String ComponentPrototypeMenuBuilder_buildMenu_orphanedCategories = ""; //$NON-NLS-1$

    // --- ComponentPrototypesExtensionPoint --------------------------------

    /** The component prototype category configuration element is illegal. */
    public static String ComponentPrototypesExtensionPoint_buildMenu_illegalComponentPrototypeCategoryConfigurationElement = ""; //$NON-NLS-1$

    /** The component prototype configuration element is illegal. */
    public static String ComponentPrototypesExtensionPoint_buildMenu_illegalComponentPrototypeConfigurationElement = ""; //$NON-NLS-1$

    /** The component prototype factory is missing. */
    public static String ComponentPrototypesExtensionPoint_createComponentPrototype_missingFactory = ""; //$NON-NLS-1$

    /** The component prototype mnemonic is missing. */
    public static String ComponentPrototypesExtensionPoint_createComponentPrototype_missingMnemonic = ""; //$NON-NLS-1$

    /** The component prototype name is missing. */
    public static String ComponentPrototypesExtensionPoint_createComponentPrototype_missingName = ""; //$NON-NLS-1$

    /** The component prototype category identifier is missing. */
    public static String ComponentPrototypesExtensionPoint_createComponentPrototypeCategory_missingId = ""; //$NON-NLS-1$

    /** The component prototype category mnemonic is missing. */
    public static String ComponentPrototypesExtensionPoint_createComponentPrototypeCategory_missingMnemonic = ""; //$NON-NLS-1$

    /** The component prototype category name is missing. */
    public static String ComponentPrototypesExtensionPoint_createComponentPrototypeCategory_missingName = ""; //$NON-NLS-1$

    /** The source does not represent a legal mnemonic. */
    public static String ComponentPrototypesExtensionPoint_decodeMnemonic_illegalSource = ""; //$NON-NLS-1$

    /**
     * An error occurred while evaluating the configuration element enablement
     * expression.
     */
    public static String ComponentPrototypesExtensionPoint_isConfigurationElementEnabled_error = ""; //$NON-NLS-1$

    /**
     * The configuration element enablement expression could not be converted.
     */
    public static String ComponentPrototypesExtensionPoint_isConfigurationElementEnabled_unconvertable = ""; //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code NonNlsMessages} class.
     */
    static
    {
        NLS.initializeMessages( NonNlsMessages.class.getName(), NonNlsMessages.class );
    }

    /**
     * Initializes a new instance of the {@code NonNlsMessages} class.
     */
    private NonNlsMessages()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    // --- ComponentPrototypeFactoryProxy -----------------------------------

    /**
     * Gets the formatted message indicating an error occurred while creating
     * the component prototype factory.
     * 
     * @param configurationElement
     *        The component prototype factory configuration element.
     * 
     * @return The formatted message indicating an error occurred while creating
     *         the component prototype factory.
     */
    static String ComponentPrototypeFactoryProxy_getDelegate_createError(
        final IConfigurationElement configurationElement )
    {
        return bind( ComponentPrototypeFactoryProxy_getDelegate_createError, configurationElement.getNamespaceIdentifier(), configurationElement.getName() );
    }

    // --- ComponentPrototypeMenuBuilder ------------------------------------

    /**
     * Gets the formatted message indicating the component prototype categories
     * are orphans.
     * 
     * @param componentPrototypeCategoryIds
     *        The collection of component prototype category identifiers.
     * 
     * @return The formatted message indicating the component prototype
     *         categories are orphans.
     */
    static String ComponentPrototypeMenuBuilder_buildMenu_orphanedCategories(
        final Collection<@Nullable String> componentPrototypeCategoryIds )
    {
        return bind( ComponentPrototypeMenuBuilder_buildMenu_orphanedCategories, componentPrototypeCategoryIds );
    }
}
