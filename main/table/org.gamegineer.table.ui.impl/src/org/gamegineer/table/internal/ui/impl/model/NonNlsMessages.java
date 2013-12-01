/*
 * NonNlsMessages.java
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
 * Created on Dec 26, 2009 at 8:59:34 PM.
 */

package org.gamegineer.table.internal.ui.impl.model;

import java.io.File;
import net.jcip.annotations.ThreadSafe;
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

    // --- ComponentModel ---------------------------------------------------

    /** The component model listener is already registered. */
    public static String ComponentModel_addComponentModelListener_listener_registered;

    /**
     * An unexpected exception was thrown from
     * IComponentModelListener.componentBoundsChanged().
     */
    public static String ComponentModel_componentBoundsChanged_unexpectedException;

    /**
     * An unexpected exception was thrown from
     * IComponentModelListener.componentChanged().
     */
    public static String ComponentModel_componentChanged_unexpectedException;

    /**
     * An unexpected exception was thrown from
     * IComponentModelListener.componentModelFocusChanged().
     */
    public static String ComponentModel_componentModelFocusChanged_unexpectedException;

    /**
     * An unexpected exception was thrown from
     * IComponentModelListener.componentModelHoverChanged().
     */
    public static String ComponentModel_componentModelHoverChanged_unexpectedException;

    /**
     * An unexpected exception was thrown from
     * IComponentModelListener.componentOrientationChanged().
     */
    public static String ComponentModel_componentOrientationChanged_unexpectedException;

    /**
     * An unexpected exception was thrown from
     * IComponentModelListener.componentSurfaceDesignChanged().
     */
    public static String ComponentModel_componentSurfaceDesignChanged_unexpectedException;

    /** The component strategy identifier is not registered. */
    public static String ComponentModel_getComponentStrategyUI_unknownComponentStrategyId;

    /** The component model listener is not registered. */
    public static String ComponentModel_removeComponentModelListener_listener_notRegistered;

    // --- ContainerModel ---------------------------------------------------

    /** The container model listener is already registered. */
    public static String ContainerModel_addContainerModelListener_listener_registered;

    /**
     * An unexpected exception was thrown from
     * IContainerModelListener.componentModelAdded().
     */
    public static String ContainerModel_componentModelAdded_unexpectedException;

    /**
     * An unexpected exception was thrown from
     * IContainerModelListener.componentModelRemoved().
     */
    public static String ContainerModel_componentModelRemoved_unexpectedException;

    /**
     * An unexpected exception was thrown from
     * IContainerModelListener.containerLayoutChanged().
     */
    public static String ContainerModel_containerLayoutChanged_unexpectedException;

    /** The container model listener is not registered. */
    public static String ContainerModel_removeContainerModelListener_listener_notRegistered;

    // --- ContainerModelContentChangedEvent --------------------------------

    /** The component model index is negative. */
    public static String ContainerModelContentChangedEvent_ctor_componentModelIndex_negative;

    // --- MainModel --------------------------------------------------------

    /** The main model listener is already registered. */
    public static String MainModel_addMainModelListener_listener_registered;

    /**
     * An unexpected exception was thrown from
     * IMainModelListener.mainModelStateChanged().
     */
    public static String MainModel_mainModelStateChanged_unexpectedException;

    /** The main model listener is not registered. */
    public static String MainModel_removeMainModelListener_listener_notRegistered;

    // --- PreferencesModel -------------------------------------------------

    /** The user preferences are not available. */
    public static String PreferencesModel_userPreferences_notAvailable;

    // --- TableEnvironmentModel --------------------------------------------

    /** The component was created by a different table environment. */
    public static String TableEnvironmentModel_createComponentModel_componentCreatedByDifferentTableEnvironment;

    /** The container was created by a different table environment. */
    public static String TableEnvironmentModel_createContainerModel_containerCreatedByDifferentTableEnvironment;

    /** The table was created by a different table environment. */
    public static String TableEnvironmentModel_createTableModel_tableCreatedByDifferentTableEnvironment;

    // --- TableModel -------------------------------------------------------

    /** The table model listener is already registered. */
    public static String TableModel_addTableModelListener_listener_registered;

    /** The version control extension is not available. */
    public static String TableModel_getTableRevisionNumber_versionControlNotAvailable;

    /** An error occurred while reading the table memento. */
    public static String TableModel_readTableMemento_error;

    /** The table model listener is not registered. */
    public static String TableModel_removeTableModelListener_listener_notRegistered;

    /** An error occurred while setting the table memento. */
    public static String TableModel_setTableMemento_error;

    /**
     * An unexpected exception was thrown from
     * ITableModelListener.tableChanged().
     */
    public static String TableModel_tableChanged_unexpectedException;

    /**
     * An unexpected exception was thrown from
     * ITableModelListener.tableModelDirtyFlagChanged().
     */
    public static String TableModel_tableModelDirtyFlagChanged_unexpectedException;

    /**
     * An unexpected exception was thrown from
     * ITableModelListener.tableModelFileChanged().
     */
    public static String TableModel_tableModelFileChanged_unexpectedException;

    /**
     * An unexpected exception was thrown from
     * ITableModelListener.tableModelFocusChanged().
     */
    public static String TableModel_tableModelFocusChanged_unexpectedException;

    /**
     * An unexpected exception was thrown from
     * ITableModelListener.tableModelHoverChanged().
     */
    public static String TableModel_tableModelHoverChanged_unexpectedException;

    /**
     * An unexpected exception was thrown from
     * ITableModelListener.tableModelOriginOffsetChanged().
     */
    public static String TableModel_tableModelOriginOffsetChanged_unexpectedException;

    /** An error occurred while writing the table memento. */
    public static String TableModel_writeTableMemento_error;


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

    // --- TableModel -------------------------------------------------------

    /**
     * Gets the formatted message indicating an error occurred while reading the
     * table memento.
     * 
     * @param file
     *        The file from which the table memento is read; must not be
     *        {@code null}.
     * 
     * @return The formatted message indicating an error occurred while reading
     *         the table memento; never {@code null}.
     */
    /* @NonNull */
    static String TableModel_readTableMemento_error(
        /* @NonNull */
        final File file )
    {
        return bind( TableModel_readTableMemento_error, file.getAbsolutePath() );
    }

    /**
     * Gets the formatted message indicating an error occurred while writing the
     * table memento.
     * 
     * @param file
     *        The file to which the table memento is written; must not be
     *        {@code null}.
     * 
     * @return The formatted message indicating an error occurred while writing
     *         the table memento; never {@code null}.
     */
    /* @NonNull */
    static String TableModel_writeTableMemento_error(
        /* @NonNull */
        final File file )
    {
        return bind( TableModel_writeTableMemento_error, file.getAbsolutePath() );
    }
}
