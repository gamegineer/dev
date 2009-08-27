/*
 * Messages.java
 * Copyright 2008-2009 Gamegineer.org
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
 * Created on May 11, 2008 at 9:59:41 PM.
 */

package org.gamegineer.engine.internal.core;

import org.eclipse.osgi.util.NLS;
import org.gamegineer.engine.core.AttributeName;

/**
 * A utility class to manage localized messages for the package.
 */
final class Messages
    extends NLS
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The name of the associated resource bundle. */
    private static final String BUNDLE_NAME = "org.gamegineer.engine.internal.core.Messages"; //$NON-NLS-1$

    // --- AttributeChange --------------------------------------------------

    /** New attribute value is absent. */
    public static String AttributeChange_newValue_absent;

    /** Old attribute value is absent. */
    public static String AttributeChange_oldValue_absent;

    // --- Engine -----------------------------------------------------------

    /** Unexpected exception thrown from IExtension.preSubmitCommand(). */
    public static String Engine_createCommandContext_unexpectedException;

    /** Command execution was interrupted. */
    public static String Engine_executeCommand_executionInterrupted;

    /** Redo is not available. */
    public static String Engine_redo_notAvailable;

    /** The attribute is transient and should not be present in a memento. */
    public static String Engine_setMemento_attribute_transient;

    /** The attribute name has an illegal format. */
    public static String Engine_setMemento_attributeName_illegalFormat;

    /** The command history attribute is not present in the memento. */
    public static String Engine_setMemento_commandHistory_absent;

    /** The engine context does not contain a legal command context. */
    public static String Engine_submitCommand_illegalContext;

    /** The command cannot be submitted because the engine is shutdown. */
    public static String Engine_submitCommand_shutdown;

    /** Undo is not available. */
    public static String Engine_undo_notAvailable;

    // --- Engine.SafeFuture ------------------------------------------------

    /** Attempt to wait for command completion from an engine worker thread. */
    public static String Engine_SafeFuture_waitFromWorkerThread;

    // --- EngineFactory ----------------------------------------------------

    /** The requested class name is unsupported. */
    public static String EngineFactory_createComponent_unsupportedType;

    // --- State ------------------------------------------------------------

    /** The attribute does not exist. */
    public static String State_attribute_absent;

    /** The attribute already exists. */
    public static String State_attribute_present;

    /** A transaction is already active. */
    public static String State_transaction_active;

    /** A transaction is not active. */
    public static String State_transaction_notActive;

    /** A writable transaction is not active. */
    public static String State_transaction_notActiveWritable;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code Messages} class.
     */
    static
    {
        NLS.initializeMessages( BUNDLE_NAME, Messages.class );
    }

    /**
     * Initializes a new instance of the {@code Messages} class.
     */
    private Messages()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    // --- Engine -----------------------------------------------------------

    /**
     * Gets the formatted message indicating the attribute is transient and
     * should not be present in a memento.
     * 
     * @param attributeName
     *        The attribute name; must not be {@code null}.
     * 
     * @return The formatted message indicating the attribute is transient and
     *         should not be present in a memento; never {@code null}.
     */
    /* @NonNull */
    static String Engine_setMemento_attribute_transient(
        /* @NonNull */
        final AttributeName attributeName )
    {
        return bind( Engine_setMemento_attribute_transient, attributeName );
    }

    /**
     * Gets the formatted message indicating the attribute name has an illegal
     * format.
     * 
     * @param attributeName
     *        The attribute name; must not be {@code null}.
     * 
     * @return The formatted message indicating the attribute name has an
     *         illegal format; never {@code null}.
     */
    /* @NonNull */
    static String Engine_setMemento_attributeName_illegalFormat(
        /* @NonNull */
        final String attributeName )
    {
        return bind( Engine_setMemento_attributeName_illegalFormat, attributeName );
    }

    // --- EngineFactory ----------------------------------------------------

    /**
     * Gets the formatted message indicating the requested class name is
     * unsupported.
     * 
     * @param className
     *        The class name; must not be {@code null}.
     * 
     * @return The formatted message indicating the requested class name is
     *         unsupported; never {@code null}.
     */
    /* @NonNull */
    static String EngineFactory_createComponent_unsupportedType(
        /* @NonNull */
        final String className )
    {
        return bind( EngineFactory_createComponent_unsupportedType, className );
    }

    // --- State ------------------------------------------------------------

    /**
     * Gets the formatted message indicating the attribute does not exist.
     * 
     * @param attributeName
     *        The attribute name; must not be {@code null}.
     * 
     * @return The formatted message indicating the attribute does not exist;
     *         never {@code null}.
     */
    /* @NonNull */
    static String State_attribute_absent(
        /* @NonNull */
        final AttributeName attributeName )
    {
        return bind( State_attribute_absent, attributeName );
    }

    /**
     * Gets the formatted message indicating the attribute already exists.
     * 
     * @param attributeName
     *        The attribute name; must not be {@code null}.
     * 
     * @return The formatted message indicating the attribute already exists;
     *         never {@code null}.
     */
    /* @NonNull */
    static String State_attribute_present(
        /* @NonNull */
        final AttributeName attributeName )
    {
        return bind( State_attribute_present, attributeName );
    }
}
