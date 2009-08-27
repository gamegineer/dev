/*
 * CommandLineOptions.java
 * Copyright 2008 Gamegineer.org
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
 * Created on Oct 1, 2008 at 10:43:38 PM.
 */

package org.gamegineer.client.internal.ui.console;

import net.jcip.annotations.Immutable;
import org.apache.commons.cli.Options;

/**
 * The application command line options.
 * 
 * <p>
 * This class is immutable.
 * </p>
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
    private static final Options OPTIONS;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code CommandLineOptions} class.
     */
    static
    {
        OPTIONS = new Options();
        OPTIONS.addOption( OPTION_HELP, false, Messages.CommandLineOptions_help_description );
    }

    /**
     * Initializes a new instance of the {@code CommandLineOptions} class.
     */
    private CommandLineOptions()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

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
