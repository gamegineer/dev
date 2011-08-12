/*
 * AbstractNodeControllerTestCase.java
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
 * Created on Apr 10, 2011 at 6:14:52 PM.
 */

package org.gamegineer.table.internal.net.node;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.ArrayList;
import java.util.Collection;
import org.easymock.EasyMock;
import org.gamegineer.table.internal.net.TableNetworkConfigurations;
import org.gamegineer.table.net.IPlayer;
import org.gamegineer.table.net.ITableNetworkConfiguration;
import org.gamegineer.table.net.TableNetworkException;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.internal.net.node.INodeController} interface.
 * 
 * @param <T>
 *        The type of the table network node controller.
 */
public abstract class AbstractNodeControllerTestCase<T extends INodeController>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table network node controller under test in the fixture. */
    private T nodeController_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractNodeControllerTestCase}
     * class.
     */
    protected AbstractNodeControllerTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the table network node controller to be tested.
     * 
     * @return The table network node controller to be tested; never {@code
     *         null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract T createNodeController()
        throws Exception;

    /**
     * Gets the table network node controller under test in the fixture.
     * 
     * @return The table network node controller under test in the fixture;
     *         never {@code null}.
     */
    /* @NonNull */
    protected final T getNodeController()
    {
        assertNotNull( nodeController_ );
        return nodeController_;
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
        nodeController_ = createNodeController();
        assertNotNull( nodeController_ );
    }

    /**
     * Ensures the {@code connect} method throws an exception when passed a
     * {@code null} table network configuration.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testConnect_Configuration_Null()
        throws Exception
    {
        nodeController_.connect( null );
    }

    /**
     * Ensures the {@code connect} method throws an exception when the table
     * network node is connected.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = TableNetworkException.class )
    public void testConnect_Connected_ThrowsException()
        throws Exception
    {
        final ITableNetworkConfiguration configuration = TableNetworkConfigurations.createDefaultTableNetworkConfiguration();
        nodeController_.connect( configuration );

        nodeController_.connect( configuration );
    }

    /**
     * Ensures the {@code disconnect} method does nothing when the table network
     * node is disconnected.
     */
    @Test
    public void testDisconnect_Disconnected_DoesNothing()
    {
        nodeController_.disconnect();
    }

    /**
     * Ensures the {@code getPlayers} method returns a copy of the player
     * collection.
     */
    @Test
    public void testGetPlayers_ReturnValue_Copy()
    {
        final Collection<IPlayer> players = nodeController_.getPlayers();
        final Collection<IPlayer> expectedValue = new ArrayList<IPlayer>( players );

        players.add( EasyMock.createMock( IPlayer.class ) );
        final Collection<IPlayer> actualValue = nodeController_.getPlayers();

        assertEquals( expectedValue, actualValue );
    }

    /**
     * Ensures the {@code getPlayers} method does not return {@code null}.
     */
    @Test
    public void testGetPlayers_ReturnValue_NonNull()
    {
        assertNotNull( nodeController_.getPlayers() );
    }
}
