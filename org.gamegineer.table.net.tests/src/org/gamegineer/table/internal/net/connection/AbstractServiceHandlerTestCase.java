/*
 * AbstractServiceHandlerTestCase.java
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
 * Created on Jan 13, 2011 at 11:26:20 PM.
 */

package org.gamegineer.table.internal.net.connection;

import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.internal.net.connection.IServiceHandler}
 * interface.
 * 
 * @param <H>
 *        The type of the transport handle.
 * @param <E>
 *        The type of the event.
 * @param <T>
 *        The type of the service handler.
 */
public abstract class AbstractServiceHandlerTestCase<H, E, T extends IServiceHandler<H, E>>
    extends AbstractEventHandlerTestCase<H, E, T>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractServiceHandlerTestCase}
     * class.
     */
    protected AbstractServiceHandlerTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code open} method throws an exception if the service
     * handler has been closed.
     */
    @Test( expected = IllegalStateException.class )
    public void testOpen_AfterClose()
    {
        getEventHandler().close();

        getEventHandler().open( createMockTransportHandle() );
    }

    /**
     * Ensures the {@code open} method throws an exception when passed a {@code
     * null} transport handle.
     */
    @Test( expected = NullPointerException.class )
    public void testOpen_Handle_Null()
    {
        getEventHandler().open( null );
    }

    /**
     * Ensures the {@code open} method throws an exception when attempting to
     * open the service handler more than once.
     */
    @Test( expected = IllegalStateException.class )
    public void testOpen_MultipleInvocations()
    {
        final H handle = createMockTransportHandle();
        getEventHandler().open( handle );

        getEventHandler().open( handle );
    }
}
