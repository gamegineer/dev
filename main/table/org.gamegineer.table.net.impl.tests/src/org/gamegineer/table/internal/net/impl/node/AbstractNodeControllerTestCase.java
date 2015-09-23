/*
 * AbstractNodeControllerTestCase.java
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
 * Created on Apr 10, 2011 at 6:14:52 PM.
 */

package org.gamegineer.table.internal.net.impl.node;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import org.easymock.EasyMock;
import org.eclipse.jdt.annotation.Nullable;
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
public abstract class AbstractNodeControllerTestCase<T extends INodeController>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table network node controller under test in the fixture. */
    private Optional<T> nodeController_;

    /** The node layer runner for use in the fixture. */
    private Optional<NodeLayerRunner> nodeLayerRunner_;

    /** The table for use in the fixture. */
    private Optional<ITable> table_;

    /** The table environment context for use in the fixture. */
    private Optional<MultiThreadedTableEnvironmentContext> tableEnvironmentContext_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractNodeControllerTestCase}
     * class.
     */
    protected AbstractNodeControllerTestCase()
    {
        nodeController_ = Optional.empty();
        nodeLayerRunner_ = Optional.empty();
        table_ = Optional.empty();
        tableEnvironmentContext_ = Optional.empty();
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
    protected abstract NodeLayerRunner createNodeLayerRunner(
        T nodeController );

    /**
     * Creates a new table network configuration.
     * 
     * @return A new table network configuration; never {@code null}.
     */
    protected final TableNetworkConfiguration createTableNetworkConfiguration()
    {
        return TableNetworkConfigurations.createDefaultTableNetworkConfiguration( getTable() );
    }

    /**
     * Gets the table network node controller under test in the fixture.
     * 
     * @return The table network node controller under test in the fixture;
     *         never {@code null}.
     */
    protected final T getNodeController()
    {
        return nodeController_.get();
    }

    /**
     * Gets the fixture node layer runner.
     * 
     * @return The fixture node layer runner; never {@code null}.
     */
    private NodeLayerRunner getNodeLayerRunner()
    {
        return nodeLayerRunner_.get();
    }

    /**
     * Gets the fixture table.
     * 
     * @return The fixture table; never {@code null}.
     */
    private ITable getTable()
    {
        return table_.get();
    }

    /**
     * Gets the fixture table environment context.
     * 
     * @return The fixture table environment context; never {@code null}.
     */
    private MultiThreadedTableEnvironmentContext getTableEnvironmentContext()
    {
        return tableEnvironmentContext_.get();
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
        final MultiThreadedTableEnvironmentContext tableEnvironmentContext = new MultiThreadedTableEnvironmentContext();
        tableEnvironmentContext_ = Optional.of( tableEnvironmentContext );
        table_ = Optional.of( TestTableEnvironments.createTableEnvironment( tableEnvironmentContext ).createTable() );

        final T nodeController = createNodeController();
        nodeController_ = Optional.of( nodeController );
        nodeLayerRunner_ = Optional.of( createNodeLayerRunner( nodeController ) );
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
        getTableEnvironmentContext().dispose();
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
        final T nodeController = getNodeController();
        final TableNetworkConfiguration configuration = createTableNetworkConfiguration();
        final Future<@Nullable Void> connectFuture1 = getNodeLayerRunner().run( new Callable<Future<@Nullable Void>>()
        {
            @Override
            public Future<@Nullable Void> call()
            {
                return nodeController.beginConnect( configuration );
            }
        } );
        assertNotNull( connectFuture1 );
        nodeController.endConnect( connectFuture1 );

        final Future<@Nullable Void> connectFuture2 = getNodeLayerRunner().run( new Callable<Future<@Nullable Void>>()
        {
            @Override
            public Future<@Nullable Void> call()
            {
                return nodeController.beginConnect( configuration );
            }
        } );
        assertNotNull( connectFuture2 );
        nodeController.endConnect( connectFuture2 );
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
        final T nodeController = getNodeController();
        final Future<@Nullable Void> disconnectFuture = getNodeLayerRunner().run( new Callable<Future<@Nullable Void>>()
        {
            @Override
            public Future<@Nullable Void> call()
            {
                return nodeController.beginDisconnect();
            }
        } );
        assertNotNull( disconnectFuture );
        nodeController.endDisconnect( disconnectFuture );
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
        final T nodeController = getNodeController();
        final Future<@Nullable Void> connectFuture = getNodeLayerRunner().run( new Callable<Future<@Nullable Void>>()
        {
            @Override
            public Future<@Nullable Void> call()
                throws Exception
            {
                return nodeController.beginConnect( createTableNetworkConfiguration() );
            }
        } );
        assertNotNull( connectFuture );
        nodeController.endConnect( connectFuture );

        getNodeLayerRunner().run( new Runnable()
        {
            @Override
            public void run()
            {
                assertNotNull( nodeController.getPlayer() );
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
        final T nodeController = getNodeController();
        getNodeLayerRunner().run( new Runnable()
        {
            @Override
            public void run()
            {
                assertNull( nodeController.getPlayer() );
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
        final T nodeController = getNodeController();
        getNodeLayerRunner().run( new Runnable()
        {
            @Override
            public void run()
            {
                final Collection<IPlayer> players = nodeController.getPlayers();
                final Collection<IPlayer> expectedValue = new ArrayList<>( players );

                players.add( EasyMock.createMock( IPlayer.class ) );
                final Collection<IPlayer> actualValue = nodeController.getPlayers();

                assertEquals( expectedValue, actualValue );
            }
        } );
    }
}
