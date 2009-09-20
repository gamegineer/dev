/*
 * StandardOutputHandlerFactory.java
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
 * Created on May 25, 2008 at 9:53:26 PM.
 */

package org.gamegineer.common.internal.core.util.logging;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.logging.StandardOutputHandler;

/**
 * A component factory for creating instances of
 * {@link org.gamegineer.common.core.util.logging.StandardOutputHandler}.
 */
@ThreadSafe
final class StandardOutputHandlerFactory
    extends AbstractHandlerFactory<StandardOutputHandler>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code StandardOutputHandlerFactory}
     * class.
     */
    StandardOutputHandlerFactory()
    {
        super( StandardOutputHandler.class );
    }
}
