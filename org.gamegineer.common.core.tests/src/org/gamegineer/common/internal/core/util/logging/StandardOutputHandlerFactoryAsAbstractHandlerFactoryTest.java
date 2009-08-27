/*
 * StandardOutputHandlerFactoryAsAbstractHandlerFactoryTest.java
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
 * Created on May 25, 2008 at 9:56:46 PM.
 */

package org.gamegineer.common.internal.core.util.logging;

import org.gamegineer.common.core.util.logging.StandardOutputHandler;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.internal.core.util.logging.StandardOutputHandlerFactory}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.common.internal.core.util.logging.AbstractHandlerFactory}
 * class.
 */
public final class StandardOutputHandlerFactoryAsAbstractHandlerFactoryTest
    extends AbstractAbstractHandlerFactoryTestCase<StandardOutputHandlerFactory, StandardOutputHandler>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code StandardOutputHandlerFactoryAsAbstractHandlerFactoryTest} class.
     */
    public StandardOutputHandlerFactoryAsAbstractHandlerFactoryTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.internal.core.services.logging.AbstractAbstractLoggingComponentFactoryTestCase#createLoggingComponentFactory()
     */
    @Override
    protected StandardOutputHandlerFactory createLoggingComponentFactory()
    {
        return new StandardOutputHandlerFactory();
    }
}
