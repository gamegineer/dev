/*
 * CommandLineOptions.java
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
 * Created on Apr 3, 2010 at 10:36:54 PM.
 */

package org.gamegineer.table.internal.ui.impl;

import net.jcip.annotations.Immutable;
import org.apache.commons.cli.Options;

/**
 * The application command line options.
 */
@Immutable
final class CommandLineOptions
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The help option. */
    static final String OPTION_HELP = "help"; //$NON-NLS-1$

    /** The collection of application command line options. */
    private static final Options OPTIONS = createOptions();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CommandLineOptions} class.
     */
    private CommandLineOptions()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the collection of application command line options.
     * 
     * @return The collection of application command line options; never
     *         {@code null}.
     */
    /* @NonNull */
    private static Options createOptions()
    {
        final Options options = new Options();

        options.addOption( OPTION_HELP, false, NlsMessages.CommandLineOptions_help_description );

        return options;
    }

    /**
     * Gets the collection of application command line options.
     * 
     * @return The collection of application command line options; never
     *         {@code null}.
     */
    /* @NonNull */
    static Options getOptions()
    {
        return OPTIONS;
    }
}
