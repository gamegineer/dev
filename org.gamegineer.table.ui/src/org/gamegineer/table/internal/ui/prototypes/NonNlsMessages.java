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
 * Created on Oct 24, 2012 at 8:00:45 PM.
 */

package org.gamegineer.table.internal.ui.prototypes;

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

    // --- CardPilePrototypeFactory -----------------------------------------

    /** Failed to create the component prototype. */
    public static String CardPilePrototypeFactory_createComponentPrototype_error;

    /** The specified parameter was not specified. */
    public static String CardPilePrototypeFactory_setInitializationData_parameterNotSpecified;

    /** The parameters collection was not specified. */
    public static String CardPilePrototypeFactory_setInitializationData_parametersNotSpecified;

    // --- CardPrototypeFactory ---------------------------------------------

    /** Failed to create the component prototype. */
    public static String CardPrototypeFactory_createComponentPrototype_error;

    /** The specified parameter was not specified. */
    public static String CardPrototypeFactory_setInitializationData_parameterNotSpecified;

    /** The parameters collection was not specified. */
    public static String CardPrototypeFactory_setInitializationData_parametersNotSpecified;

    // --- DeckPrototypeFactory ---------------------------------------------

    /** Failed to create the component prototype. */
    public static String DeckPrototypeFactory_createComponentPrototype_error;

    /** The specified parameter was not specified. */
    public static String DeckPrototypeFactory_setInitializationData_parameterNotSpecified;

    /** The parameters collection was not specified. */
    public static String DeckPrototypeFactory_setInitializationData_parametersNotSpecified;


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

    // --- CardPilePrototypeFactory -----------------------------------------

    /**
     * Gets the formatted message indicating the specified parameter was not
     * specified.
     * 
     * @param parameterName
     *        The parameter name; must not be {@code null}.
     * 
     * @return The formatted message indicating the specified parameter was not
     *         specified; never {@code null}.
     */
    /* @NonNull */
    static String CardPilePrototypeFactory_setInitializationData_parameterNotSpecified(
        /* @NonNull */
        final String parameterName )
    {
        return bind( CardPilePrototypeFactory_setInitializationData_parameterNotSpecified, parameterName );
    }

    // --- CardPrototypeFactory ---------------------------------------------

    /**
     * Gets the formatted message indicating the specified parameter was not
     * specified.
     * 
     * @param parameterName
     *        The parameter name; must not be {@code null}.
     * 
     * @return The formatted message indicating the specified parameter was not
     *         specified; never {@code null}.
     */
    /* @NonNull */
    static String CardPrototypeFactory_setInitializationData_parameterNotSpecified(
        /* @NonNull */
        final String parameterName )
    {
        return bind( CardPrototypeFactory_setInitializationData_parameterNotSpecified, parameterName );
    }

    // --- DeckPrototypeFactory ---------------------------------------------

    /**
     * Gets the formatted message indicating the specified parameter was not
     * specified.
     * 
     * @param parameterName
     *        The parameter name; must not be {@code null}.
     * 
     * @return The formatted message indicating the specified parameter was not
     *         specified; never {@code null}.
     */
    /* @NonNull */
    static String DeckPrototypeFactory_setInitializationData_parameterNotSpecified(
        /* @NonNull */
        final String parameterName )
    {
        return bind( DeckPrototypeFactory_setInitializationData_parameterNotSpecified, parameterName );
    }
}
