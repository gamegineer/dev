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
 * Created on Oct 18, 2010 at 9:43:08 PM.
 */

package org.gamegineer.common.ui.databinding.swing;

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

    // --- ComponentProperties ----------------------------------------------

    /** The password property was requested for an unsupported component. */
    public static String ComponentProperties_password_unsupportedComponent;

    /**
     * The single selection value property was requested for an unsupported
     * component.
     */
    public static String ComponentProperties_singleSelectionValue_unsupportedComponent;

    /** The text property was requested for an unsupported component. */
    public static String ComponentProperties_text_unsupportedComponent;

    // --- SwingRealm -------------------------------------------------------

    /** An error occurred while executing the runnable. */
    public static String SwingRealm_safeInvokeAndWait_error;


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

    // --- ComponentProperties ----------------------------------------------

    /**
     * Gets the formatted message indicating the password property was requested
     * for an unsupported component.
     * 
     * @param source
     *        The property source; must not be {@code null}.
     * 
     * @return The formatted message indicating the password property was
     *         requested for an unsupported component; never {@code null}.
     */
    /* @NonNull */
    static String ComponentProperties_password_unsupportedComponent(
        /* @NonNull */
        final Object source )
    {
        return bind( ComponentProperties_password_unsupportedComponent, source );
    }

    /**
     * Gets the formatted message indicating the single selection value property
     * was requested for an unsupported component.
     * 
     * @param source
     *        The property source; must not be {@code null}.
     * 
     * @return The formatted message indicating the single selection value
     *         property was requested for an unsupported component; never
     *         {@code null}.
     */
    /* @NonNull */
    static String ComponentProperties_singleSelectionValue_unsupportedComponent(
        /* @NonNull */
        final Object source )
    {
        return bind( ComponentProperties_singleSelectionValue_unsupportedComponent, source );
    }

    /**
     * Gets the formatted message indicating the text property was requested for
     * an unsupported component.
     * 
     * @param source
     *        The property source; must not be {@code null}.
     * 
     * @return The formatted message indicating the text property was requested
     *         for an unsupported component; never {@code null}.
     */
    /* @NonNull */
    static String ComponentProperties_text_unsupportedComponent(
        /* @NonNull */
        final Object source )
    {
        return bind( ComponentProperties_text_unsupportedComponent, source );
    }
}
