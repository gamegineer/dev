/*
 * DefaultHandlerFactoryAsAbstractHandlerFactoryTest.java
 * Copyright 2008-2017 Gamegineer contributors and others.
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
 * Created on Jun 6, 2010 at 9:43:31 PM.
 */

package org.gamegineer.common.internal.core.impl.logging;

import java.util.logging.Handler;

/**
 * A fixture for testing the {@link DefaultHandlerFactory} class to ensure it
 * does not violate the contract of the {@link AbstractHandlerFactory} class.
 */
public final class DefaultHandlerFactoryAsAbstractHandlerFactoryTest
    extends AbstractAbstractLoggingComponentFactoryTestCase<DefaultHandlerFactory, Handler>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code DefaultHandlerFactoryAsAbstractHandlerFactoryTest} class.
     */
    public DefaultHandlerFactoryAsAbstractHandlerFactoryTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.internal.core.logging.AbstractAbstractLoggingComponentFactoryTestCase#createLoggingComponentFactory()
     */
    @Override
    protected DefaultHandlerFactory createLoggingComponentFactory()
    {
        return new DefaultHandlerFactory();
    }

    /*
     * @see org.gamegineer.common.internal.core.logging.AbstractAbstractLoggingComponentFactoryTestCase#getLoggingComponentType()
     */
    @Override
    protected Class<? extends Handler> getLoggingComponentType()
    {
        return FakeHandler.class;
    }
}
