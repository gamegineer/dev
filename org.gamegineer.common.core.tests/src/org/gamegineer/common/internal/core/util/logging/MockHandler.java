/*
 * MockHandler.java
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
 * Created on May 23, 2008 at 10:56:35 PM.
 */

package org.gamegineer.common.internal.core.util.logging;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import org.gamegineer.common.core.services.component.IComponentFactory;
import org.gamegineer.common.internal.core.services.logging.AbstractLoggingComponentFactory;

/**
 * Mock implementation of {@link java.util.logging.Handler}.
 */
public final class MockHandler
    extends Handler
{
    // ==================================================================
    // Fields
    // ==================================================================

    /** The component factory for this class. */
    public static final IComponentFactory FACTORY = new AbstractLoggingComponentFactory<MockHandler>( MockHandler.class )
    {
        // no overrides
    };


    // ==================================================================
    // Constructors
    // ==================================================================

    /**
     * Initializes a new instance of the {@code MockHandler} class.
     */
    public MockHandler()
    {
        super();
    }


    // ==================================================================
    // Methods
    // ==================================================================

    /*
     * @see java.util.logging.Handler#close()
     */
    @Override
    public void close()
        throws SecurityException
    {
        // Do nothing
    }

    /*
     * @see java.util.logging.Handler#flush()
     */
    @Override
    public void flush()
    {
        // Do nothing
    }

    /*
     * @see java.util.logging.Handler#publish(java.util.logging.LogRecord)
     */
    @Override
    public void publish(
        @SuppressWarnings( "unused" )
        final LogRecord record )
    {
        // Do nothing
    }
}
