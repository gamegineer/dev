/*
 * NonNlsMessages.java
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
 * Created on Oct 8, 2009 at 11:37:48 PM.
 */

package org.gamegineer.table.internal.ui.view;

import java.io.File;
import java.util.Collection;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.osgi.util.NLS;
import org.gamegineer.table.core.ComponentSurfaceDesignId;

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

    // --- ComponentFactoryProxy --------------------------------------------

    /** An error occurred while creating the component factory. */
    public static String ComponentFactoryProxy_getDelegate_createError;

    // --- ComponentPrototypesExtensionPoint --------------------------------

    /** The component prototype class name is missing. */
    public static String ComponentPrototypesExtensionPoint_createComponentPrototype_missingClassName;

    /** The component prototype identifier is missing. */
    public static String ComponentPrototypesExtensionPoint_createComponentPrototype_missingId;

    /** The component prototype mnemonic is missing. */
    public static String ComponentPrototypesExtensionPoint_createComponentPrototype_missingMnemonic;

    /** The component prototype name is missing. */
    public static String ComponentPrototypesExtensionPoint_createComponentPrototype_missingName;

    /** The component prototype category identifier is missing. */
    public static String ComponentPrototypesExtensionPoint_createComponentPrototypeCategory_missingId;

    /** The component prototype category mnemonic is missing. */
    public static String ComponentPrototypesExtensionPoint_createComponentPrototypeCategory_missingMnemonic;

    /** The component prototype category name is missing. */
    public static String ComponentPrototypesExtensionPoint_createComponentPrototypeCategory_missingName;

    /** The component prototype category configuration element is illegal. */
    public static String ComponentPrototypesExtensionPoint_createMenu_illegalComponentPrototypeCategoryConfigurationElement;

    /** The component prototype configuration element is illegal. */
    public static String ComponentPrototypesExtensionPoint_createMenu_illegalComponentPrototypeConfigurationElement;

    /** The source does not represent a legal mnemonic. */
    public static String ComponentPrototypesExtensionPoint_decodeMnemonic_illegalSource;

    // --- ComponentPrototypeMenuBuilder ------------------------------------

    /** The component prototype categories are orphans. */
    public static String ComponentPrototypeMenuBuilder_toMenu_orphanedCategories;

    // --- ComponentView ----------------------------------------------------

    /** The component surface design user interface is not available. */
    public static String ComponentView_getActiveComponentSurfaceDesignUI_notAvailable;

    // --- Cursors ----------------------------------------------------------

    /** Failed to create the system invalid cursor. */
    public static String Cursors_createInvalidCursor_failed;

    /** The name of the grab cursor. */
    public static String Cursors_grab_name;

    /** The name of the hand cursor. */
    public static String Cursors_hand_name;

    // --- MainFrame --------------------------------------------------------

    /** An error occurred within the action updater task. */
    public static String MainFrame_actionUpdater_error;

    /** An error occurred while opening the table. */
    public static String MainFrame_openTable_error;

    /** An error occurred while saving the table. */
    public static String MainFrame_saveTable_error;

    // --- TableView --------------------------------------------------------

    /** An error occurred while adding a new card to the table. */
    public static String TableView_addCard_error;

    /** An error occurred while adding a new card pile to the table. */
    public static String TableView_addCardPile_error;

    /** An error occurred while dragging cards. */
    public static String TableView_draggingCards_error;

    /** An error occurred while setting the card pile layout. */
    public static String TableView_setCardPileLayout_error;

    // --- ViewUtils --------------------------------------------------------

    /** Interrupted while waiting for the table network to disconnect. */
    public static String ViewUtils_disconnectTableNetwork_interrupted;

    /** Timed out while waiting for the table network to disconnect. */
    public static String ViewUtils_disconnectTableNetwork_timedOut;


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

    // --- ComponentFactoryProxy --------------------------------------------

    /**
     * Gets the formatted message indicating an error occurred while creating
     * the component factory.
     * 
     * @param className
     *        The component factory class name; must not be {@code null}.
     * 
     * @return The formatted message indicating an error occurred while creating
     *         the component factory; never {@code null}.
     */
    /* @NonNull */
    static String ComponentFactoryProxy_getDelegate_createError(
        /* @NonNull */
        final String className )
    {
        return bind( ComponentFactoryProxy_getDelegate_createError, className );
    }

    // --- ComponentPrototypeMenuBuilder ------------------------------------

    /**
     * Gets the formatted message indicating the component prototype categories
     * are orphans.
     * 
     * @param componentPrototypeCategoryIds
     *        The collection of component prototype category identifiers; must
     *        not be {@code null}.
     * 
     * @return The formatted message indicating the component prototype
     *         categories are orphans; never {@code null}.
     */
    /* @NonNull */
    static String ComponentPrototypeMenuBuilder_toMenu_orphanedCategories(
        /* @NonNull */
        final Collection<String> componentPrototypeCategoryIds )
    {
        return bind( ComponentPrototypeMenuBuilder_toMenu_orphanedCategories, componentPrototypeCategoryIds );
    }

    // --- ComponentView ----------------------------------------------------

    /**
     * Gets the formatted message indicating the component surface design user
     * interface is not available.
     * 
     * @param componentSurfaceDesignId
     *        The component surface design identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating the component surface design
     *         user interface is not available; never {@code null}.
     */
    /* @NonNull */
    static String ComponentView_getActiveComponentSurfaceDesignUI_notAvailable(
        /* @NonNull */
        final ComponentSurfaceDesignId componentSurfaceDesignId )
    {
        return bind( ComponentView_getActiveComponentSurfaceDesignUI_notAvailable, componentSurfaceDesignId );
    }

    // --- MainFrame --------------------------------------------------------

    /**
     * Gets the formatted message that indicates an error occurred while opening
     * the table.
     * 
     * @param file
     *        The table file; must not be {@code null}.
     * 
     * @return The formatted message that indicates an error occurred while
     *         opening the table; never {@code null}.
     */
    /* @NonNull */
    static String MainFrame_openTable_error(
        /* @NonNull */
        final File file )
    {
        return bind( MainFrame_openTable_error, file.getAbsolutePath() );
    }

    /**
     * Gets the formatted message that indicates an error occurred while saving
     * the table.
     * 
     * @param file
     *        The table file; must not be {@code null}.
     * 
     * @return The formatted message that indicates an error occurred while
     *         saving the table; never {@code null}.
     */
    /* @NonNull */
    static String MainFrame_saveTable_error(
        /* @NonNull */
        final File file )
    {
        return bind( MainFrame_saveTable_error, file.getAbsolutePath() );
    }
}
