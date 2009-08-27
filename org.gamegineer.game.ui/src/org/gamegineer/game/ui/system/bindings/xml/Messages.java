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
 * Created on Feb 26, 2009 at 11:28:37 PM.
 */

package org.gamegineer.game.ui.system.bindings.xml;

import java.io.File;
import java.net.URL;
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
    private static final String BUNDLE_NAME = "org.gamegineer.game.ui.system.bindings.xml.Messages"; //$NON-NLS-1$

    // --- XmlGameSystemUiExtensionFactory ----------------------------------

    /** An error occurred while creating the game system user interface. */
    public static String XmlGameSystemUiExtensionFactory_create_creationError;

    /** The game system user interface URL is not set. */
    public static String XmlGameSystemUiExtensionFactory_create_urlNotSet;

    /** The bundle hosting the game system user interface file was not found. */
    public static String XmlGameSystemUiExtensionFactory_setInitializationData_bundleNotFound;

    /** The game system user interface file was not found. */
    public static String XmlGameSystemUiExtensionFactory_setInitializationData_fileNotFound;

    /** The game system user interface file path is not set. */
    public static String XmlGameSystemUiExtensionFactory_setInitializationData_pathNotSet;

    // --- XmlGameSystemUiFactory -------------------------------------------

    /** An error occurred while creating the game system user interface. */
    public static String XmlGameSystemUiFactory_createGameSystemUiFromReader_creationError;

    /** The game system user interface URL has a query string. */
    public static String XmlGameSystemUiFactory_createGameSystemUiFromUrl_url_hasQueryString;

    /** The game system user interface URL has a reference. */
    public static String XmlGameSystemUiFactory_createGameSystemUiFromUrl_url_hasReference;

    /** The game system user interface file was not found. */
    public static String XmlGameSystemUiFactory_createReaderFromFile_fileNotFound;

    /** The game system user interface URL was not found. */
    public static String XmlGameSystemUiFactory_createReaderFromUrl_urlNotFound;

    /** An error occurred while closing the string bundle file. */
    public static String XmlGameSystemUiFactory_createStringBundleFromFile_closeFileError;

    /** The string bundle file was not found. */
    public static String XmlGameSystemUiFactory_createStringBundleFromFile_fileNotFound;

    /** An error occurred while reading the string bundle file. */
    public static String XmlGameSystemUiFactory_createStringBundleFromFile_readFileError;

    /** An error occurred while closing the string bundle stream. */
    public static String XmlGameSystemUiFactory_createStringBundleFromUrl_closeStreamError;

    /** The string bundle URL is malformed. */
    public static String XmlGameSystemUiFactory_createStringBundleFromUrl_malformedUrl;

    /** An error occurred while reading the string bundle stream. */
    public static String XmlGameSystemUiFactory_createStringBundleFromUrl_readStreamError;

    /** The string bundle URL was not found. */
    public static String XmlGameSystemUiFactory_createStringBundleFromUrl_urlNotFound;

    /** An error occurred while generating the schema. */
    public static String XmlGameSystemUiFactory_createUnmarshaller_generateSchemaError;

    /** An error occurred while parsing the schema. */
    public static String XmlGameSystemUiFactory_createUnmarshaller_parseSchemaError;


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

    // --- XmlGameSystemUiExtensionFactory ----------------------------------

    /**
     * Gets the formatted message indicating the bundle hosting the game system
     * user interface file was not found.
     * 
     * @param name
     *        The bundle name; must not be {@code null}.
     * 
     * @return The formatted message indicating the bundle hosting the game
     *         system user interface file was not found; never {@code null}.
     */
    /* @NonNull */
    static String XmlGameSystemUiExtensionFactory_setInitializationData_bundleNotFound(
        /* @NonNull */
        final String name )
    {
        return bind( XmlGameSystemUiExtensionFactory_setInitializationData_bundleNotFound, name );
    }

    /**
     * Gets the formatted message indicating the game system user interface file
     * was not found.
     * 
     * @param bundle
     *        The bundle hosting the game system user interface file; must not
     *        be {@code null}.
     * @param path
     *        The bundle path of the game system user interface file; must not
     *        be {@code null}.
     * 
     * @return The formatted message indicating the game system user interface
     *         file was not found; never {@code null}.
     */
    /* @NonNull */
    static String XmlGameSystemUiExtensionFactory_setInitializationData_fileNotFound(
        /* @NonNull */
        final Bundle bundle,
        /* @NonNull */
        final String path )
    {
        return bind( XmlGameSystemUiExtensionFactory_setInitializationData_fileNotFound, bundle.getSymbolicName(), path );
    }

    // --- XmlGameSystemUiFactory -------------------------------------------

    /**
     * Gets the formatted message indicating the string bundle file was not
     * found.
     * 
     * @param file
     *        The string bundle file; must not be {@code null}.
     * 
     * @return The formatted message indicating the string bundle file was not
     *         found; never {@code null}.
     */
    /* @NonNull */
    static String XmlGameSystemUiFactory_createStringBundleFromFile_fileNotFound(
        /* @NonNull */
        final File file )
    {
        return bind( XmlGameSystemUiFactory_createStringBundleFromFile_fileNotFound, file.getAbsolutePath() );
    }

    /**
     * Gets the formatted message indicating the string bundle URL was not
     * found.
     * 
     * @param url
     *        The string bundle URL; must not be {@code null}.
     * 
     * @return The formatted message indicating the string bundle URL was not
     *         found; never {@code null}.
     */
    /* @NonNull */
    static String XmlGameSystemUiFactory_createStringBundleFromUrl_urlNotFound(
        /* @NonNull */
        final URL url )
    {
        return bind( XmlGameSystemUiFactory_createStringBundleFromUrl_urlNotFound, url.toString() );
    }
}
