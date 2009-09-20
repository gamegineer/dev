/*
 * MockFormatter.java
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
 * Created on May 24, 2008 at 10:03:53 PM.
 */

package org.gamegineer.common.internal.core.util.logging;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.services.component.IComponentFactory;
import org.gamegineer.common.internal.core.services.logging.AbstractLoggingComponentFactory;

/**
 * Mock implementation of {@link java.util.logging.Formatter}.
 */
@ThreadSafe
public final class MockFormatter
    extends Formatter
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The component factory for this class. */
    public static final IComponentFactory FACTORY = new AbstractLoggingComponentFactory<MockFormatter>( MockFormatter.class )
    {
        // no overrides
    };


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MockFormatter} class.
     */
    public MockFormatter()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see java.util.logging.Formatter#format(java.util.logging.LogRecord)
     */
    @Override
    public String format(
        final LogRecord record )
    {
        return record.toString();
    }
}
