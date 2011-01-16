/*
 * AbstractAcceptorTestCase.java
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
 * Created on Jan 8, 2011 at 9:48:17 PM.
 */

package org.gamegineer.table.internal.net.connection;

import org.gamegineer.table.net.INetworkTableConfiguration;
import org.gamegineer.table.net.NetworkTableConfigurationBuilder;
import org.gamegineer.table.net.NetworkTableConstants;
import org.gamegineer.table.net.NetworkTableException;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.internal.net.connection.IAcceptor} interface.
 * 
 * @param <H>
 *        The type of the transport handle.
 * @param <E>
 *        The type of the event.
 * @param <T>
 *        The type of the acceptor.
 */
public abstract class AbstractAcceptorTestCase<H, E, T extends IAcceptor<H, E>>
    extends AbstractEventHandlerTestCase<H, E, T>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractAcceptorTestCase} class.
     */
    protected AbstractAcceptorTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new network table configuration suitable for testing.
     * 
     * @return A new network table configuration suitable for testing; never
     *         {@code null}.
     */
    /* @NonNull */
    private static INetworkTableConfiguration createNetworkTableConfiguration()
    {
        final NetworkTableConfigurationBuilder builder = new NetworkTableConfigurationBuilder();
        builder.setLocalPlayerName( "playerName" ).setHostName( "localhost" ).setPort( NetworkTableConstants.DEFAULT_PORT ); //$NON-NLS-1$ //$NON-NLS-2$
        return builder.toNetworkTableConfiguration();
    }

    /**
     * Ensures the {@code bind} method throws an exception if the acceptor has
     * been closed.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalStateException.class )
    public void testBind_AfterClose()
        throws Exception
    {
        getEventHandler().close();

        getEventHandler().bind( createNetworkTableConfiguration() );
    }

    /**
     * Ensures the {@code bind} method throws an exception when passed a {@code
     * null} configuration.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testBind_Configuration_Null()
        throws Exception
    {
        getEventHandler().bind( null );
    }

    /**
     * Ensures the {@code bind} method throws an exception when attempting to
     * bind the transport handle more than once.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalStateException.class )
    public void testBind_MultipleInvocations()
        throws Exception
    {
        final INetworkTableConfiguration configuration = createNetworkTableConfiguration();
        try
        {
            getEventHandler().bind( configuration );
        }
        catch( final NetworkTableException e )
        {
            // ignore network errors
        }

        getEventHandler().bind( configuration );
    }
}
