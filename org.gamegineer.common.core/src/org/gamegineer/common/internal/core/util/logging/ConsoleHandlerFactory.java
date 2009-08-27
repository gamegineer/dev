/*
 * ConsoleHandlerFactory.java
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
 * Created on May 20, 2008 at 12:00:07 AM.
 */

package org.gamegineer.common.internal.core.util.logging;

import java.util.logging.ConsoleHandler;

/**
 * A component factory for creating instances of
 * {@link java.util.logging.ConsoleHandler}.
 */
final class ConsoleHandlerFactory
    extends AbstractHandlerFactory<ConsoleHandler>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ConsoleHandlerFactory} class.
     */
    ConsoleHandlerFactory()
    {
        super( ConsoleHandler.class );
    }
}
