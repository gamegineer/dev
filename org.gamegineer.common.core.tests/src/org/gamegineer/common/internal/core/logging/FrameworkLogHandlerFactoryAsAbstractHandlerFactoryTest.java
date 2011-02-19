/*
 * FrameworkLogHandlerFactoryAsAbstractHandlerFactoryTest.java
 * Copyright 2008-2011 Gamegineer.org
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
 * Created on Jun 11, 2010 at 3:20:56 PM.
 */

package org.gamegineer.common.internal.core.logging;

import org.easymock.EasyMock;
import org.eclipse.osgi.framework.log.FrameworkLog;
import org.gamegineer.common.core.logging.FrameworkLogHandler;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.internal.core.logging.FrameworkLogHandlerFactory}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.common.internal.core.logging.AbstractHandlerFactory}
 * class.
 */
public final class FrameworkLogHandlerFactoryAsAbstractHandlerFactoryTest
    extends AbstractAbstractHandlerFactoryTestCase<FrameworkLogHandlerFactory, FrameworkLogHandler>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * FrameworkLogHandlerFactoryAsAbstractHandlerFactoryTest} class.
     */
    public FrameworkLogHandlerFactoryAsAbstractHandlerFactoryTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.internal.core.logging.AbstractAbstractLoggingComponentFactoryTestCase#createLoggingComponentFactory()
     */
    @Override
    protected FrameworkLogHandlerFactory createLoggingComponentFactory()
    {
        final FrameworkLogHandlerFactory factory = new FrameworkLogHandlerFactory();
        factory.bindFrameworkLog( EasyMock.createMock( FrameworkLog.class ) );
        return factory;
    }

    /*
     * @see org.gamegineer.common.internal.core.logging.AbstractAbstractLoggingComponentFactoryTestCase#getLoggingComponentType()
     */
    @Override
    protected Class<? extends FrameworkLogHandler> getLoggingComponentType()
    {
        return FrameworkLogHandler.class;
    }
}
