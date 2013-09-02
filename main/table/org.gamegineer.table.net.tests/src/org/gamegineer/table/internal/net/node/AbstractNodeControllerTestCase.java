/*
 * AbstractNodeControllerTestCase.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
import static org.junit.Assert.assertNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import org.easymock.EasyMock;
import org.gamegineer.table.internal.net.TableNetworkConfigurations;
import org.gamegineer.table.net.IPlayer;
import org.gamegineer.table.net.ITableNetworkConfiguration;
import org.gamegineer.table.net.TableNetworkException;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link INodeController} interface.
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
    private volatile T nodeController_;

    /** The node layer runner for use in the fixture. */
    private NodeLayerRunner nodeLayerRunner_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractNodeControllerTestCase}
     * class.
     */
    protected AbstractNodeControllerTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the table network node controller to be tested.
     * 
     * @return The table network node controller to be tested; never
     *         {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract T createNodeController()
        throws Exception;

    /**
     * Creates a node layer runner for the specified table network node
     * controller.
     * 
     * @param nodeController
     *        The table network node controller to associate with the node layer
     *        runner; must not be {@code null}.
     * 
     * @return The new node layer runner for the specified table network node
     *         controller; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code nodeController} is {@code null}.
     */
    /* @NonNull */
    protected abstract NodeLayerRunner createNodeLayerRunner(
        /* @NonNull */
        T nodeController );

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
        nodeLayerRunner_ = createNodeLayerRunner( nodeController_ );
    }

    /**
     * Ensures the {@link INodeController#beginConnect} method throws an
     * exception when passed a {@code null} table network configuration.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testBeginConnect_Configuration_Null()
        throws Exception
    {
        nodeLayerRunner_.run( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                nodeController_.beginConnect( null );
            }
        } );
    }

    /**
     * Ensures the connect operation throws an exception when the table network
     * node is connected.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = TableNetworkException.class )
    public void testConnect_Connected_ThrowsException()
        throws Exception
    {
        final ITableNetworkConfiguration configuration = TableNetworkConfigurations.createDefaultTableNetworkConfiguration();
        nodeController_.endConnect( nodeLayerRunner_.run( new Callable<Future<Void>>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public Future<Void> call()
            {
                return nodeController_.beginConnect( configuration );
            }
        } ) );

        nodeController_.endConnect( nodeLayerRunner_.run( new Callable<Future<Void>>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public Future<Void> call()
            {
                return nodeController_.beginConnect( configuration );
            }
        } ) );
    }

    /**
     * Ensures the disconnect operation does nothing when the table network node
     * is disconnected.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testDisconnect_Disconnected_DoesNothing()
        throws Exception
    {
        nodeController_.endDisconnect( nodeLayerRunner_.run( new Callable<Future<Void>>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public Future<Void> call()
            {
                return nodeController_.beginDisconnect();
            }
        } ) );
    }

    /**
     * Ensures the {@link INodeController#endConnect} method throws an exception
     * when passed a {@code null} asynchronous completion token.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testEndConnect_Future_Null()
        throws Exception
    {
        nodeLayerRunner_.run( new Callable<Void>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public Void call()
                throws Exception
            {
                nodeController_.endConnect( null );

                return null;
            }
        } );
    }

    /**
     * Ensures the {@link INodeController#endDisconnect} method throws an
     * exception when passed a {@code null} asynchronous completion token.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testEndDisconnect_Future_Null()
        throws Exception
    {
        nodeLayerRunner_.run( new Callable<Void>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public Void call()
                throws Exception
            {
                nodeController_.endDisconnect( null );

                return null;
            }
        } );
    }

    /**
     * Ensures the {@link INodeController#getPlayer} method does not return
     * {@code null} when the table network is connected.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testGetPlayer_Connected()
        throws Exception
    {
        nodeController_.endConnect( nodeLayerRunner_.run( new Callable<Future<Void>>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public Future<Void> call()
                throws Exception
            {
                return nodeController_.beginConnect( TableNetworkConfigurations.createDefaultTableNetworkConfiguration() );
            }
        } ) );

        nodeLayerRunner_.run( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                assertNotNull( nodeController_.getPlayer() );
            }
        } );
    }

    /**
     * Ensures the {@link INodeController#getPlayer} method returns {@code null}
     * when the table network is disconnected.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testGetPlayer_Disconnected()
        throws Exception
    {
        nodeLayerRunner_.run( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                assertNull( nodeController_.getPlayer() );
            }
        } );
    }

    /**
     * Ensures the {@link INodeController#getPlayers} method returns a copy of
     * the player collection.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testGetPlayers_ReturnValue_Copy()
        throws Exception
    {
        nodeLayerRunner_.run( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                final Collection<IPlayer> players = nodeController_.getPlayers();
                final Collection<IPlayer> expectedValue = new ArrayList<>( players );

                players.add( EasyMock.createMock( IPlayer.class ) );
                final Collection<IPlayer> actualValue = nodeController_.getPlayers();

                assertEquals( expectedValue, actualValue );
            }
        } );
    }

    /**
     * Ensures the {@link INodeController#getPlayers} method does not return
     * {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testGetPlayers_ReturnValue_NonNull()
        throws Exception
    {
        nodeLayerRunner_.run( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                assertNotNull( nodeController_.getPlayers() );
            }
        } );
    }

    /**
     * Ensures the {@link INodeController#giveControl} method throws an
     * exception when passed a {@code null} player name.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testGiveControl_PlayerName_Null()
        throws Exception
    {
        nodeLayerRunner_.run( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                nodeController_.giveControl( null );
            }
        } );
    }
}
