/*
 * LoggingServiceAsLoggingServiceTest.java
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
 * Created on May 14, 2008 at 11:02:12 PM.
 */

package org.gamegineer.common.internal.core.services.logging;

import org.gamegineer.common.core.services.logging.AbstractLoggingServiceTestCase;
import org.gamegineer.common.core.services.logging.ILoggingService;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.internal.core.services.logging.LoggingService}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.common.core.services.logging.ILoggingService}
 * interface.
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
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.services.logging.AbstractLoggingServiceTestCase#createLogService()
     */
    @Override
    protected ILoggingService createLogService()
    {
        return new LoggingService();
    }
}
