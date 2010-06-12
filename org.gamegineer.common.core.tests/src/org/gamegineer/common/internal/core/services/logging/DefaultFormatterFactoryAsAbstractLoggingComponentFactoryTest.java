/*
 * DefaultFormatterFactoryAsAbstractLoggingComponentFactoryTest.java
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
 * Created on Jun 6, 2010 at 9:14:25 PM.
 */

package org.gamegineer.common.internal.core.services.logging;

import java.util.logging.Formatter;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.internal.core.services.logging.DefaultFormatterFactory}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.common.internal.core.services.logging.AbstractLoggingComponentFactory}
 * class.
 */
public final class DefaultFormatterFactoryAsAbstractLoggingComponentFactoryTest
    extends AbstractAbstractLoggingComponentFactoryTestCase<DefaultFormatterFactory, Formatter>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * DefaultFormatterFactoryAsAbstractLoggingComponentFactoryTest} class.
     */
    public DefaultFormatterFactoryAsAbstractLoggingComponentFactoryTest()
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
    protected DefaultFormatterFactory createLoggingComponentFactory()
    {
        return new DefaultFormatterFactory();
    }

    /*
     * @see org.gamegineer.common.internal.core.services.logging.AbstractAbstractLoggingComponentFactoryTestCase#getLoggingComponentType()
     */
    @Override
    protected Class<FakeFormatter> getLoggingComponentType()
    {
        return FakeFormatter.class;
    }
}
