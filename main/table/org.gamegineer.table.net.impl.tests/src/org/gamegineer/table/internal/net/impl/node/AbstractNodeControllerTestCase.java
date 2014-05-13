/*
 * AbstractNodeControllerTestCase.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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

package org.gamegineer.table.internal.net.impl.node;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import org.easymock.EasyMock;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.MultiThreadedTableEnvironmentContext;
import org.gamegineer.table.core.test.TestTableEnvironments;
import org.gamegineer.table.internal.net.impl.TableNetworkConfigurations;
import org.gamegineer.table.net.IPlayer;
import org.gamegineer.table.net.TableNetworkConfiguration;
import org.gamegineer.table.net.TableNetworkException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link INodeController} interface.
 * 
 * @param <T>
 *        The type of the table network node controller.
 */
@NonNullByDefault( false )
public abstract class AbstractNodeControllerTestCase<T extends INodeController>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table network node controller under test in the fixture. */
    private volatile T nodeController_;

    /** The node layer runner for use in the fixture. */
    private NodeLayerRunner nodeLayerRunner_;

    /** The table for use in the fixture. */
    private ITable table_;

    /** The table environment context for use in the fixture. */
    private MultiThreadedTableEnvironmentContext tableEnvironmentContext_;


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
    @NonNull
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
     */
    @NonNull
    protected abstract NodeLayerRunner createNodeLayerRunner(
        @NonNull
        T nodeController );

    /**
     * Creates a new table network configuration.
     * 
     * @return A new table network configuration; never {@code null}.
     */
    @NonNull
    protected final TableNetworkConfiguration createTableNetworkConfiguration()
    {
        assertNotNull( table_ );
        return TableNetworkConfigurations.createDefaultTableNetworkConfiguration( table_ );
    }

    /**
     * Gets the table network node controller under test in the fixture.
     * 
     * @return The table network node controller under test in the fixture;
     *         never {@code null}.
     */
    @NonNull
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
        final MultiThreadedTableEnvironmentContext tableEnvironmentContext = tableEnvironmentContext_ = new MultiThreadedTableEnvironmentContext();
        table_ = TestTableEnvironments.createTableEnvironment( tableEnvironmentContext ).createTable();

        nodeController_ = createNodeController();
        assertNotNull( nodeController_ );
        nodeLayerRunner_ = createNodeLayerRunner( nodeController_ );
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
        tableEnvironmentContext_.dispose();
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
        final TableNetworkConfiguration configuration = createTableNetworkConfiguration();
        final Future<Void> connectFuture1 = nodeLayerRunner_.run( new Callable<Future<Void>>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public Future<Void> call()
            {
                return nodeController_.beginConnect( configuration );
            }
        } );
        assertNotNull( connectFuture1 );
        nodeController_.endConnect( connectFuture1 );

        final Future<Void> connectFuture2 = nodeLayerRunner_.run( new Callable<Future<Void>>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public Future<Void> call()
            {
                return nodeController_.beginConnect( configuration );
            }
        } );
        assertNotNull( connectFuture2 );
        nodeController_.endConnect( connectFuture2 );
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
        final Future<Void> disconnectFuture = nodeLayerRunner_.run( new Callable<Future<Void>>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public Future<Void> call()
            {
                return nodeController_.beginDisconnect();
            }
        } );
        assertNotNull( disconnectFuture );
        nodeController_.endDisconnect( disconnectFuture );
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
        final Future<Void> connectFuture = nodeLayerRunner_.run( new Callable<Future<Void>>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public Future<Void> call()
                throws Exception
            {
                return nodeController_.beginConnect( createTableNetworkConfiguration() );
            }
        } );
        assertNotNull( connectFuture );
        nodeController_.endConnect( connectFuture );

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
}
