/*
 * FrameworkLogHandlerFactoryTest.java
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
 * Created on Jun 11, 2010 at 3:39:13 PM.
 */

package org.gamegineer.common.internal.core.logging;

import org.easymock.EasyMock;
import org.eclipse.osgi.framework.log.FrameworkLog;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.internal.core.logging.FrameworkLogHandlerFactory}
 * class.
 */
public final class FrameworkLogHandlerFactoryTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The framework log handler factory under test in the fixture. */
    private FrameworkLogHandlerFactory factory_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FrameworkLogHandlerFactoryTest}
     * class.
     */
    public FrameworkLogHandlerFactoryTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

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
        factory_ = new FrameworkLogHandlerFactory();
    }

    /**
     * Tears down the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @After
    public void tearDown()
        throws Exception
    {
        factory_ = null;
    }

    /**
     * Ensures the {@code bindFrameworkLog} method throws an exception when
     * passed a {@code null} framework log service.
     */
    @Test( expected = NullPointerException.class )
    public void testBindFrameworkLog_FrameworkLog_Null()
    {
        factory_.bindFrameworkLog( null );
    }

    /**
     * Ensures the {@code bindFrameworkLog} method throws an exception when a
     * framework log service is already bound.
     */
    @Test( expected = IllegalStateException.class )
    public void testBindFrameworkLog_ServiceAlreadyBound()
    {
        factory_.bindFrameworkLog( EasyMock.createMock( FrameworkLog.class ) );

        factory_.bindFrameworkLog( EasyMock.createMock( FrameworkLog.class ) );
    }

    /**
     * Ensures the {@code unbindFrameworkLog} method throws an exception when
     * passed a {@code null} framework log service.
     */
    @Test( expected = NullPointerException.class )
    public void testUnbindFrameworkLog_FrameworkLog_Null()
    {
        factory_.unbindFrameworkLog( null );
    }

    /**
     * Ensures the {@code unbindFrameworkLog} method throws an exception when a
     * different framework log service is already bound.
     */
    @Test( expected = IllegalStateException.class )
    public void testUnbindFrameworkLog_DifferentServiceBound()
    {
        factory_.bindFrameworkLog( EasyMock.createMock( FrameworkLog.class ) );

        factory_.unbindFrameworkLog( EasyMock.createMock( FrameworkLog.class ) );
    }
}
