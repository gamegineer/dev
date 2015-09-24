/*
 * FrameworkLogHandlerFactoryTest.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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
 * Created on Jun 11, 2010 at 3:39:13 PM.
 */

package org.gamegineer.common.internal.core.impl.logging;

import java.util.Optional;
import org.easymock.EasyMock;
import org.eclipse.osgi.framework.log.FrameworkLog;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link FrameworkLogHandlerFactory} class.
 */
public final class FrameworkLogHandlerFactoryTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The framework log handler factory under test in the fixture. */
    private Optional<FrameworkLogHandlerFactory> factory_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FrameworkLogHandlerFactoryTest}
     * class.
     */
    public FrameworkLogHandlerFactoryTest()
    {
        factory_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the framework log handler factory under test in the fixture.
     * 
     * @return The framework log handler factory under test in the fixture.
     */
    private FrameworkLogHandlerFactory getFactory()
    {
        return factory_.get();
    }

    /**
     * Sets up the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Before
    public void setUp()
        throws Exception
    {
        factory_ = Optional.of( new FrameworkLogHandlerFactory() );
    }

    /**
     * Ensures the {@link FrameworkLogHandlerFactory#bindFrameworkLog} method
     * throws an exception when a framework log service is already bound.
     */
    @Test( expected = IllegalStateException.class )
    public void testBindFrameworkLog_ServiceAlreadyBound()
    {
        final FrameworkLogHandlerFactory factory = getFactory();
        factory.bindFrameworkLog( EasyMock.createMock( FrameworkLog.class ) );

        factory.bindFrameworkLog( EasyMock.createMock( FrameworkLog.class ) );
    }

    /**
     * Ensures the {@link FrameworkLogHandlerFactory#unbindFrameworkLog} method
     * throws an exception when a different framework log service is already
     * bound.
     */
    @Test( expected = IllegalStateException.class )
    public void testUnbindFrameworkLog_DifferentServiceBound()
    {
        final FrameworkLogHandlerFactory factory = getFactory();
        factory.bindFrameworkLog( EasyMock.createMock( FrameworkLog.class ) );

        factory.unbindFrameworkLog( EasyMock.createMock( FrameworkLog.class ) );
    }
}
