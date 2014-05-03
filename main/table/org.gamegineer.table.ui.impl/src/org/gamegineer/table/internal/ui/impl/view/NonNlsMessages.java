/*
 * NonNlsMessages.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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

package org.gamegineer.table.internal.ui.impl.view;

import static org.gamegineer.common.core.runtime.NullAnalysis.nonNull;
import java.io.File;
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

    // --- ComponentView ----------------------------------------------------

    /** The component surface design user interface is not available. */
    public static String ComponentView_getComponentSurfaceDesignUI_notAvailable = ""; //$NON-NLS-1$

    // --- Cursors ----------------------------------------------------------

    /** Failed to create the system invalid cursor. */
    public static String Cursors_createInvalidCursor_failed = ""; //$NON-NLS-1$

    /** The name of the grab cursor. */
    public static String Cursors_grab_name = ""; //$NON-NLS-1$

    /** The name of the hand cursor. */
    public static String Cursors_hand_name = ""; //$NON-NLS-1$

    // --- MainFrame --------------------------------------------------------

    /** An error occurred within the action updater task. */
    public static String MainFrame_actionUpdater_error = ""; //$NON-NLS-1$

    /** An error occurred while opening the table. */
    public static String MainFrame_openTable_error = ""; //$NON-NLS-1$

    /** An error occurred while saving the table. */
    public static String MainFrame_saveTable_error = ""; //$NON-NLS-1$

    // --- TableView --------------------------------------------------------

    /** An error occurred while adding a new component to the table. */
    public static String TableView_addComponent_error = ""; //$NON-NLS-1$

    /** The drag source extension is not available. */
    public static String TableView_draggingComponent_dragSourceNotAvailable = ""; //$NON-NLS-1$

    /** An error occurred while setting the container layout. */
    public static String TableView_setContainerLayout_error = ""; //$NON-NLS-1$

    // --- ViewUtils --------------------------------------------------------

    /** Interrupted while waiting for the table network to disconnect. */
    public static String ViewUtils_disconnectTableNetwork_interrupted = ""; //$NON-NLS-1$

    /** Timed out while waiting for the table network to disconnect. */
    public static String ViewUtils_disconnectTableNetwork_timedOut = ""; //$NON-NLS-1$


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
    static String ComponentView_getComponentSurfaceDesignUI_notAvailable(
        final ComponentSurfaceDesignId componentSurfaceDesignId )
    {
        return nonNull( bind( ComponentView_getComponentSurfaceDesignUI_notAvailable, componentSurfaceDesignId ) );
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
    static String MainFrame_openTable_error(
        final File file )
    {
        return nonNull( bind( MainFrame_openTable_error, file.getAbsolutePath() ) );
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
    static String MainFrame_saveTable_error(
        final File file )
    {
        return nonNull( bind( MainFrame_saveTable_error, file.getAbsolutePath() ) );
    }
}
