/*
 * MainModelTest.java
 * Copyright 2008-2013 Gamegineer.org
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

package org.gamegineer.table.internal.ui.model;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.ui.TestComponents;
import org.gamegineer.test.core.MocksSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.model.MainModel} class.
 */
public final class MainModelTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The default test timeout. */
    @Rule
    public final Timeout DEFAULT_TIMEOUT = new Timeout( 1000 );

    /** The main model under test in the fixture. */
    private MainModel model_;

    /** The mocks support for use in the fixture. */
    private MocksSupport mocksSupport_;

    /** The nice mocks control for use in the fixture. */
    private IMocksControl niceMocksControl_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MainModelTest} class.
     */
    public MainModelTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds the specified main model listener to the fixture main model.
     * 
     * <p>
     * This method ensures all pending table environment events have fired
     * before adding the listener.
     * </p>
     * 
     * @param listener
     *        The main model listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is already a registered main model listener.
     * @throws java.lang.InterruptedException
     *         If this thread is interrupted.
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    private void addMainModelListener(
        /* @NonNull */
        final IMainModelListener listener )
        throws InterruptedException
    {
        awaitPendingTableEnvironmentEvents();
        model_.addMainModelListener( listener );
    }

    /**
     * Awaits all pending events from the fixture table environment.
     * 
     * @throws java.lang.InterruptedException
     *         If this thread is interrupted.
     */
    private void awaitPendingTableEnvironmentEvents()
        throws InterruptedException
    {
        model_.getTableModel().getTable().getTableEnvironment().awaitPendingEvents();
    }

    /**
     * Creates a temporary file.
     * 
     * @return A temporary file; never {@code null}.
     */
    /* @NonNull */
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
            throw new AssertionError( "Failed to create temporary file." ); //$NON-NLS-1$
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
            method.invoke( model_ );
        }
        catch( final Exception e )
        {
            throw new AssertionError( e );
        }
    }

    /**
     * Switches the fixture mocks control from record mode to replay mode.
     */
    private void replayMocks()
    {
        niceMocksControl_.replay();
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
        niceMocksControl_ = EasyMock.createNiceControl();
        mocksSupport_ = new MocksSupport();
        model_ = new MainModel();
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
        model_.dispose();
    }

    /**
     * Ensures the {@link MainModel#addMainModelListener} method adds a listener
     * that is absent from the main model listener collection.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testAddMainModelListener_Listener_Absent()
        throws Exception
    {
        final IMainModelListener listener = niceMocksControl_.createMock( IMainModelListener.class );
        listener.mainModelStateChanged( EasyMock.notNull( MainModelEvent.class ) );
        replayMocks();

        addMainModelListener( listener );
        fireMainModelStateChangedEvent();

        verifyMocks();
    }

    /**
     * Ensures the {@link MainModel#addMainModelListener} method throws an
     * exception when passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testAddMainModelListener_Listener_Null()
    {
        model_.addMainModelListener( null );
    }

    /**
     * Ensures the {@link MainModel#addMainModelListener} method throws an
     * exception when passed a listener that is present in the main model
     * listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddMainModelListener_Listener_Present()
    {
        final IMainModelListener listener = EasyMock.createMock( IMainModelListener.class );
        model_.addMainModelListener( listener );

        model_.addMainModelListener( listener );
    }

    /**
     * Ensures the main model state changed event catches any exception thrown
     * by the {@link IMainModelListener#mainModelStateChanged} method of a main
     * model listener.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testMainModelStateChanged_CatchesListenerException()
        throws Exception
    {
        final IMainModelListener listener1 = niceMocksControl_.createMock( IMainModelListener.class );
        listener1.mainModelStateChanged( EasyMock.notNull( MainModelEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        final IMainModelListener listener2 = niceMocksControl_.createMock( IMainModelListener.class );
        listener2.mainModelStateChanged( EasyMock.notNull( MainModelEvent.class ) );
        replayMocks();

        addMainModelListener( listener1 );
        addMainModelListener( listener2 );

        fireMainModelStateChangedEvent();

        verifyMocks();
    }

    /**
     * Ensures the {@link MainModel#openTable()} method fires a main model state
     * changed event.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testOpenTable_FiresMainModelStateChangedEvent()
        throws Exception
    {
        final IMainModelListener listener = niceMocksControl_.createMock( IMainModelListener.class );
        listener.mainModelStateChanged( EasyMock.notNull( MainModelEvent.class ) );
        replayMocks();

        addMainModelListener( listener );

        model_.openTable();

        verifyMocks();
    }

    /**
     * Ensures the {@link MainModel#openTable(File)} method throws an exception
     * when passed a {@code null} file.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testOpenTableFromFile_File_Null()
        throws Exception
    {
        model_.openTable( null );
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
        final IMainModelListener listener = niceMocksControl_.createMock( IMainModelListener.class );
        listener.mainModelStateChanged( EasyMock.notNull( MainModelEvent.class ) );
        replayMocks();

        final File file = createTemporaryFile();
        model_.saveTable( file );
        addMainModelListener( listener );

        model_.openTable( file );

        verifyMocks();
    }

    /**
     * Ensures the {@link MainModel#removeMainModelListener} method throws an
     * exception when passed a listener that is absent from the main model
     * listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveMainModelListener_Listener_Absent()
    {
        model_.removeMainModelListener( EasyMock.createMock( IMainModelListener.class ) );
    }

    /**
     * Ensures the {@link MainModel#removeMainModelListener} method throws an
     * exception when passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveMainModelListener_Listener_Null()
    {
        model_.removeMainModelListener( null );
    }

    /**
     * Ensures the {@link MainModel#removeMainModelListener} removes a listener
     * that is present in the main model listener collection.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testRemoveMainModelListener_Listener_Present()
        throws Exception
    {
        final IMainModelListener listener = niceMocksControl_.createMock( IMainModelListener.class );
        listener.mainModelStateChanged( EasyMock.notNull( MainModelEvent.class ) );
        replayMocks();

        addMainModelListener( listener );
        fireMainModelStateChangedEvent();

        model_.removeMainModelListener( listener );
        fireMainModelStateChangedEvent();

        verifyMocks();
    }

    /**
     * Ensures the {@link MainModel#saveTable} method throws an exception when
     * passed a {@code null} file.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testSaveTable_File_Null()
        throws Exception
    {
        model_.saveTable( null );
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
        final IMainModelListener listener = niceMocksControl_.createMock( IMainModelListener.class );
        listener.mainModelStateChanged( EasyMock.notNull( MainModelEvent.class ) );
        replayMocks();

        final File file = createTemporaryFile();
        addMainModelListener( listener );

        model_.saveTable( file );

        verifyMocks();
    }

    /**
     * Ensures a change to a table model owned by the main model fires a main
     * model state changed event.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testTableModel_StateChanged_FiresMainModelStateChangedEvent()
        throws Exception
    {
        final IMainModelListener listener = niceMocksControl_.createMock( IMainModelListener.class );
        listener.mainModelStateChanged( EasyMock.notNull( MainModelEvent.class ) );
        EasyMock.expectLastCall().andAnswer( mocksSupport_.asyncAnswer() );
        replayMocks();

        addMainModelListener( listener );

        final ITable table = model_.getTableModel().getTable();
        table.getTabletop().addComponent( TestComponents.createUniqueComponent( table.getTableEnvironment() ) );

        verifyMocks();
    }

    /**
     * Verifies that all expectations were met in the fixture mocks control.
     * 
     * <p>
     * This method waits for all asynchronous answers registered with the
     * fixture mocks support to complete before verifying expectations.
     * </p>
     * 
     * @throws java.lang.InterruptedException
     *         If this thread is interrupted.
     */
    private void verifyMocks()
        throws InterruptedException
    {
        mocksSupport_.awaitAsyncAnswers();
        niceMocksControl_.verify();
    }
}
