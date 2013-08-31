/*
 * FakeFormatter.java
 * Copyright 2008-2013 Gamegineer.org
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
 * Created on Jun 5, 2010 at 11:06:04 PM.
 */

package org.gamegineer.common.internal.core.impl.logging;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import net.jcip.annotations.ThreadSafe;

/**
 * Fake implementation of {@link java.util.logging.Formatter}.
 */
@ThreadSafe
public final class FakeFormatter
    extends Formatter
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FakeFormatter} class.
     */
    public FakeFormatter()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see java.util.logging.Formatter#format(java.util.logging.LogRecord)
     */
    @Override
    public String format(
        @SuppressWarnings( "unused" )
        final LogRecord record )
    {
        return ""; //$NON-NLS-1$
    }
}
