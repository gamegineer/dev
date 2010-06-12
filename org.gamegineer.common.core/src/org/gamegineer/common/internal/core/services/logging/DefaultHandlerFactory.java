/*
 * DefaultHandlerFactory.java
 * Copyright 2008-2010 Gamegineer.org
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
 * Created on May 23, 2008 at 12:03:31 AM.
 */

package org.gamegineer.common.internal.core.services.logging;

import java.util.logging.Handler;
import net.jcip.annotations.ThreadSafe;

/**
 * A component factory for creating instances of
 * {@link java.util.logging.Handler} whose concrete class is present on the
 * bundle classpath.
 */
@ThreadSafe
public final class DefaultHandlerFactory
    extends AbstractHandlerFactory<Handler>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code DefaultHandlerFactory} class.
     */
    public DefaultHandlerFactory()
    {
        super( Handler.class );
    }
}
