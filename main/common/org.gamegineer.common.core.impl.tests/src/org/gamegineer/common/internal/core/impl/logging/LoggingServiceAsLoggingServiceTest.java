/*
 * LoggingServiceAsLoggingServiceTest.java
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
 * Created on May 14, 2008 at 11:02:12 PM.
 */

package org.gamegineer.common.internal.core.impl.logging;

import org.gamegineer.common.core.logging.ILoggingService;
import org.gamegineer.common.core.logging.test.AbstractLoggingServiceTestCase;

/**
 * A fixture for testing the {@link LoggingService} class to ensure it does not
 * violate the contract of the {@link ILoggingService} interface.
 */
public final class LoggingServiceAsLoggingServiceTest
    extends AbstractLoggingServiceTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code LoggingServiceAsLoggingServiceTest} class.
     */
    public LoggingServiceAsLoggingServiceTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.logging.test.AbstractLoggingServiceTestCase#createLoggingService()
     */
    @Override
    protected ILoggingService createLoggingService()
    {
        return new LoggingService();
    }
}
