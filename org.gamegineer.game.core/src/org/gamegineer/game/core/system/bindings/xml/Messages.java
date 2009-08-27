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
 * Created on Dec 4, 2008 at 11:12:58 PM.
 */

package org.gamegineer.game.core.system.bindings.xml;

import org.eclipse.osgi.util.NLS;
import org.osgi.framework.Bundle;

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
    private static final String BUNDLE_NAME = "org.gamegineer.game.core.system.bindings.xml.Messages"; //$NON-NLS-1$

    // --- XmlGameSystemExtensionFactory ------------------------------------

    /** An error occurred while creating the game system. */
    public static String XmlGameSystemExtensionFactory_create_creationError;

    /** An error occurred opening the game system file. */
    public static String XmlGameSystemExtensionFactory_create_openFileError;

    /** The game system URL is not set. */
    public static String XmlGameSystemExtensionFactory_create_urlNotSet;

    /** The bundle hosting the game system file was not found. */
    public static String XmlGameSystemExtensionFactory_setInitializationData_bundleNotFound;

    /** The game system file was not found. */
    public static String XmlGameSystemExtensionFactory_setInitializationData_fileNotFound;

    /** The game system file path is not set. */
    public static String XmlGameSystemExtensionFactory_setInitializationData_pathNotSet;

    // --- XmlGameSystemFactory ---------------------------------------------

    /** An error occurred while creating the game system. */
    public static String XmlGameSystemFactory_createGameSystem_creationError;

    /** The game system file was not found. */
    public static String XmlGameSystemFactory_createReader_fileNotFound;

    /** An error occurred while generating the schema. */
    public static String XmlGameSystemFactory_createUnmarshaller_generateSchemaError;

    /** An error occurred while parsing the schema. */
    public static String XmlGameSystemFactory_createUnmarshaller_parseSchemaError;


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

    // --- XmlGameSystemExtensionFactory ------------------------------------

    /**
     * Gets the formatted message indicating the bundle hosting the game system
     * file was not found.
     * 
     * @param name
     *        The bundle name; must not be {@code null}.
     * 
     * @return The formatted message indicating the bundle hosting the game
     *         system file was not found; never {@code null}.
     */
    /* @NonNull */
    static String XmlGameSystemExtensionFactory_setInitializationData_bundleNotFound(
        /* @NonNull */
        final String name )
    {
        return bind( XmlGameSystemExtensionFactory_setInitializationData_bundleNotFound, name );
    }

    /**
     * Gets the formatted message indicating the game system file was not found.
     * 
     * @param bundle
     *        The bundle hosting the game system file; must not be {@code null}.
     * @param path
     *        The bundle path of the game system file; must not be {@code null}.
     * 
     * @return The formatted message indicating the game system file was not
     *         found; never {@code null}.
     */
    /* @NonNull */
    static String XmlGameSystemExtensionFactory_setInitializationData_fileNotFound(
        /* @NonNull */
        final Bundle bundle,
        /* @NonNull */
        final String path )
    {
        return bind( XmlGameSystemExtensionFactory_setInitializationData_fileNotFound, bundle.getSymbolicName(), path );
    }
}
