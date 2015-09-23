/*
 * MainModelTest.java
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
 * Created on Oct 6, 2009 at 11:57:40 PM.
 */

package org.gamegineer.table.internal.ui.impl.model;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Optional;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.jdt.annotation.NonNull;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.SingleThreadedTableEnvironmentContext;
import org.gamegineer.table.core.test.TestTableEnvironments;
import org.gamegineer.table.net.test.TestTableNetworks;
import org.gamegineer.table.ui.test.TestComponents;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link MainModel} class.
 */
public final class MainModelTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The main model under test in the fixture. */
    private Optional<MainModel> mainModel_;

    /** The nice mocks control for use in the fixture. */
    private Optional<IMocksControl> niceMocksControl_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MainModelTest} class.
     */
    public MainModelTest()
    {
        mainModel_ = Optional.empty();
        niceMocksControl_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a temporary file.
     * 
     * @return A temporary file; never {@code null}.
     */
    private static File createTemporaryFile()
    {
        try
        {
            final File temporaryFile = File.createTempFile( MainModelTest.class.getName(), null );
            temporaryFile.deleteOnExit();
            return temporaryFile;
        }
        catch( final IOException e )
        {
            throw new AssertionError( "Failed to create temporary file.", e ); //$NON-NLS-1$
        }
    }

    /**
     * Fires a main model state changed event for the main model under test in
     * the fixture.
     */
    private void fireMainModelStateChangedEvent()
    {
        try
        {
            final Method method = MainModel.class.getDeclaredMethod( "fireMainModelStateChanged" ); //$NON-NLS-1$
            method.setAccessible( true );
            method.invoke( getMainModel() );
        }
        catch( final Exception e )
        {
            throw new AssertionError( e );
        }
    }

    /**
     * Gets the main model under test in the fixture.
     * 
     * @return The main model under test in the fixture; never {@code null}.
     */
    private MainModel getMainModel()
    {
        return mainModel_.get();
    }

    /**
     * Gets the fixture nice mocks control.
     * 
     * @return The fixture nice mocks control; never {@code null}.
     */
    private IMocksControl getNiceMocksControl()
    {
        return niceMocksControl_.get();
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
        niceMocksControl_ = Optional.of( EasyMock.createNiceControl() );
        final TableEnvironmentModel tableEnvironmentModel = new TableEnvironmentModel( TestTableEnvironments.createTableEnvironment( new SingleThreadedTableEnvironmentContext() ) );
        mainModel_ = Optional.of( new MainModel( new TableModel( tableEnvironmentModel, tableEnvironmentModel.getTableEnvironment().createTable(), TestTableNetworks.createTableNetwork() ) ) );
    }

    /**
     * Ensures the {@link MainModel#addMainModelListener} method adds a listener
     * that is absent from the main model listener collection.
     */
    @Test
    public void testAddMainModelListener_Listener_Absent()
    {
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final IMainModelListener listener = niceMocksControl.createMock( IMainModelListener.class );
        listener.mainModelStateChanged( EasyMock.<@NonNull MainModelEvent>notNull() );
        niceMocksControl.replay();

        getMainModel().addMainModelListener( listener );
        fireMainModelStateChangedEvent();

        niceMocksControl.verify();
    }

    /**
     * Ensures the {@link MainModel#addMainModelListener} method throws an
     * exception when passed a listener that is present in the main model
     * listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddMainModelListener_Listener_Present()
    {
        final MainModel mainModel = getMainModel();
        final IMainModelListener listener = EasyMock.createMock( IMainModelListener.class );
        mainModel.addMainModelListener( listener );

        mainModel.addMainModelListener( listener );
    }

    /**
     * Ensures the main model state changed event catches any exception thrown
     * by the {@link IMainModelListener#mainModelStateChanged} method of a main
     * model listener.
     */
    @Test
    public void testMainModelStateChanged_CatchesListenerException()
    {
        final MainModel mainModel = getMainModel();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final IMainModelListener listener1 = niceMocksControl.createMock( IMainModelListener.class );
        listener1.mainModelStateChanged( EasyMock.<@NonNull MainModelEvent>notNull() );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final IMainModelListener listener2 = niceMocksControl.createMock( IMainModelListener.class );
        listener2.mainModelStateChanged( EasyMock.<@NonNull MainModelEvent>notNull() );
        niceMocksControl.replay();
        mainModel.addMainModelListener( listener1 );
        mainModel.addMainModelListener( listener2 );

        fireMainModelStateChangedEvent();

        niceMocksControl.verify();
    }

    /**
     * Ensures the {@link MainModel#openTable()} method fires a main model state
     * changed event.
     */
    @Test
    public void testOpenTable_FiresMainModelStateChangedEvent()
    {
        final MainModel mainModel = getMainModel();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final IMainModelListener listener = niceMocksControl.createMock( IMainModelListener.class );
        listener.mainModelStateChanged( EasyMock.<@NonNull MainModelEvent>notNull() );
        niceMocksControl.replay();
        mainModel.addMainModelListener( listener );

        mainModel.openTable();

        niceMocksControl.verify();
    }

    /**
     * Ensures the {@link MainModel#openTable(File)} method fires a main model
     * state changed event.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testOpenTableFromFile_FiresMainModelStateChangedEvent()
        throws Exception
    {
        final MainModel mainModel = getMainModel();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final File file = createTemporaryFile();
        final IMainModelListener listener = niceMocksControl.createMock( IMainModelListener.class );
        listener.mainModelStateChanged( EasyMock.<@NonNull MainModelEvent>notNull() );
        niceMocksControl.replay();
        mainModel.saveTable( file );
        mainModel.addMainModelListener( listener );

        mainModel.openTable( file );

        niceMocksControl.verify();
    }

    /**
     * Ensures the {@link MainModel#removeMainModelListener} method throws an
     * exception when passed a listener that is absent from the main model
     * listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveMainModelListener_Listener_Absent()
    {
        getMainModel().removeMainModelListener( EasyMock.createMock( IMainModelListener.class ) );
    }

    /**
     * Ensures the {@link MainModel#removeMainModelListener} removes a listener
     * that is present in the main model listener collection.
     */
    @Test
    public void testRemoveMainModelListener_Listener_Present()
    {
        final MainModel mainModel = getMainModel();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final IMainModelListener listener = niceMocksControl.createMock( IMainModelListener.class );
        listener.mainModelStateChanged( EasyMock.<@NonNull MainModelEvent>notNull() );
        niceMocksControl.replay();
        mainModel.addMainModelListener( listener );
        fireMainModelStateChangedEvent();

        mainModel.removeMainModelListener( listener );
        fireMainModelStateChangedEvent();

        niceMocksControl.verify();
    }

    /**
     * Ensures the {@link MainModel#saveTable} method fires a main model state
     * changed event.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testSaveTable_FiresMainModelStateChangedEvent()
        throws Exception
    {
        final MainModel mainModel = getMainModel();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final File file = createTemporaryFile();
        final IMainModelListener listener = niceMocksControl.createMock( IMainModelListener.class );
        listener.mainModelStateChanged( EasyMock.<@NonNull MainModelEvent>notNull() );
        niceMocksControl.replay();
        mainModel.addMainModelListener( listener );

        mainModel.saveTable( file );

        niceMocksControl.verify();
    }

    /**
     * Ensures a change to a table model owned by the main model fires a main
     * model state changed event.
     */
    @Test
    public void testTableModel_StateChanged_FiresMainModelStateChangedEvent()
    {
        final MainModel mainModel = getMainModel();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        final IMainModelListener listener = niceMocksControl.createMock( IMainModelListener.class );
        listener.mainModelStateChanged( EasyMock.<@NonNull MainModelEvent>notNull() );
        niceMocksControl.replay();
        mainModel.addMainModelListener( listener );

        final ITable table = mainModel.getTableModel().getTable();
        table.getTabletop().addComponent( TestComponents.createUniqueComponent( table.getTableEnvironment() ) );

        niceMocksControl.verify();
    }
}
