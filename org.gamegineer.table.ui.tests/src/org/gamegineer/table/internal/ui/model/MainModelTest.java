/*
 * MainModelTest.java
 * Copyright 2008-2010 Gamegineer.org
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

import static org.junit.Assert.assertNotNull;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.table.ui.TableAdvisor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.model.MainModel} class.
 */
public final class MainModelTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The main model under test in the fixture. */
    private MainModel model_;

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
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

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
        model_ = new MainModel( new TableAdvisor() );
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
        model_ = null;
        niceMocksControl_ = null;
    }

    /**
     * Ensures the {@code addMainModelListener} method throws an exception when
     * passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testAddMainModelListener_Listener_Null()
    {
        model_.addMainModelListener( null );
    }

    /**
     * Ensures the {@code addMainModelListener} method throws an exception when
     * passed a listener that is present in the main model listener collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddMainModelListener_Listener_Present()
    {
        final IMainModelListener listener = niceMocksControl_.createMock( IMainModelListener.class );
        model_.addMainModelListener( listener );

        model_.addMainModelListener( listener );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * table advisor.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Advisor_Null()
    {
        new MainModel( null );
    }

    /**
     * Ensures the {@code getVersion} method does not return {@code null}.
     */
    @Test
    public void testGetVersion_ReturnValue_NonNull()
    {
        assertNotNull( model_.getVersion() );
    }

    /**
     * Ensures the main model dirty flag changed event catches any exception
     * thrown by the {@code mainModelDirtyFlagChanged} method of a main model
     * listener.
     */
    @Test
    public void testMainModelDirtyFlagChanged_CatchesListenerException()
    {
        final IMainModelListener listener = niceMocksControl_.createMock( IMainModelListener.class );
        listener.mainModelDirtyFlagChanged( EasyMock.notNull( MainModelEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        niceMocksControl_.replay();
        model_.addMainModelListener( listener );

        model_.openTable();

        niceMocksControl_.verify();
    }

    /**
     * Ensures the main model file changed event catches any exception thrown by
     * the {@code mainModelFileChanged} method of a main model listener.
     */
    @Test
    public void testMainModelFileChanged_CatchesListenerException()
    {
        final IMainModelListener listener = niceMocksControl_.createMock( IMainModelListener.class );
        listener.mainModelFileChanged( EasyMock.notNull( MainModelEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        niceMocksControl_.replay();
        model_.addMainModelListener( listener );

        model_.openTable();

        niceMocksControl_.verify();
    }

    /**
     * Ensures the main model state changed event catches any exception thrown
     * by the {@code mainModelStateChanged} method of a main model listener.
     */
    @Test
    public void testMainModelStateChanged_CatchesListenerException()
    {
        final IMainModelListener listener = niceMocksControl_.createMock( IMainModelListener.class );
        listener.mainModelStateChanged( EasyMock.notNull( MainModelEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        niceMocksControl_.replay();
        model_.addMainModelListener( listener );

        model_.openTable();

        niceMocksControl_.verify();
    }

    /**
     * Ensures the {@code openTable()} method fires a main model dirty flag
     * changed event.
     */
    @Test
    public void testOpenTable_FiresMainModelDirtyFlagChangedEvent()
    {
        final IMainModelListener listener = niceMocksControl_.createMock( IMainModelListener.class );
        listener.mainModelDirtyFlagChanged( EasyMock.notNull( MainModelEvent.class ) );
        niceMocksControl_.replay();
        model_.openTable();
        model_.addMainModelListener( listener );

        model_.openTable();

        niceMocksControl_.verify();
    }

    /**
     * Ensures the {@code openTable()} method fires a main model file changed
     * event.
     */
    @Test
    public void testOpenTable_FiresMainModelFileChangedEvent()
    {
        final IMainModelListener listener = niceMocksControl_.createMock( IMainModelListener.class );
        listener.mainModelFileChanged( EasyMock.notNull( MainModelEvent.class ) );
        niceMocksControl_.replay();
        model_.openTable();
        model_.addMainModelListener( listener );

        model_.openTable();

        niceMocksControl_.verify();
    }

    /**
     * Ensures the {@code openTable()} method fires a main model state changed
     * event.
     */
    @Test
    public void testOpenTable_FiresMainModelStateChangedEvent()
    {
        final IMainModelListener listener = niceMocksControl_.createMock( IMainModelListener.class );
        listener.mainModelStateChanged( EasyMock.notNull( MainModelEvent.class ) );
        niceMocksControl_.replay();
        model_.addMainModelListener( listener );

        model_.openTable();

        niceMocksControl_.verify();
    }

    /**
     * Ensures the {@code openTable()} method fires a table closed event.
     */
    @Test
    public void testOpenTable_FiresTableClosedEvent()
    {
        final IMainModelListener listener = niceMocksControl_.createMock( IMainModelListener.class );
        listener.tableClosed( EasyMock.notNull( MainModelContentChangedEvent.class ) );
        niceMocksControl_.replay();
        model_.openTable();
        model_.addMainModelListener( listener );

        model_.openTable();

        niceMocksControl_.verify();
    }

    /**
     * Ensures the {@code openTable()} method fires a table opened event.
     */
    @Test
    public void testOpenTable_FiresTableOpenedEvent()
    {
        final IMainModelListener listener = niceMocksControl_.createMock( IMainModelListener.class );
        listener.tableOpened( EasyMock.notNull( MainModelContentChangedEvent.class ) );
        niceMocksControl_.replay();
        model_.addMainModelListener( listener );

        model_.openTable();

        niceMocksControl_.verify();
    }

    /**
     * Ensures the {@code openTable(File)} method throws an exception when
     * passed a {@code null} file.
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
     * Ensures the {@code openTable(File)} method fires a main model dirty flag
     * changed event.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testOpenTableFromFile_FiresMainModelDirtyFlagChangedEvent()
        throws Exception
    {
        final File file = createTemporaryFile();
        final IMainModelListener listener = niceMocksControl_.createMock( IMainModelListener.class );
        listener.mainModelDirtyFlagChanged( EasyMock.notNull( MainModelEvent.class ) );
        niceMocksControl_.replay();
        model_.openTable();
        model_.saveTable( file );
        model_.addMainModelListener( listener );

        model_.openTable( file );

        niceMocksControl_.verify();
    }

    /**
     * Ensures the {@code openTable(File)} method fires a main model file
     * changed event.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testOpenTableFromFile_FiresMainModelFileChangedEvent()
        throws Exception
    {
        final File file = createTemporaryFile();
        final IMainModelListener listener = niceMocksControl_.createMock( IMainModelListener.class );
        listener.mainModelFileChanged( EasyMock.notNull( MainModelEvent.class ) );
        niceMocksControl_.replay();
        model_.openTable();
        model_.saveTable( file );
        model_.addMainModelListener( listener );

        model_.openTable( file );

        niceMocksControl_.verify();
    }

    /**
     * Ensures the {@code openTable(File)} method fires a main model state
     * changed event.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testOpenTableFromFile_FiresMainModelStateChangedEvent()
        throws Exception
    {
        final File file = createTemporaryFile();
        final IMainModelListener listener = niceMocksControl_.createMock( IMainModelListener.class );
        listener.mainModelStateChanged( EasyMock.notNull( MainModelEvent.class ) );
        niceMocksControl_.replay();
        model_.openTable();
        model_.saveTable( file );
        model_.addMainModelListener( listener );

        model_.openTable( file );

        niceMocksControl_.verify();
    }

    /**
     * Ensures the {@code openTable(File)} method fires a table closed event.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testOpenTableFromFile_FiresTableClosedEvent()
        throws Exception
    {
        final File file = createTemporaryFile();
        final IMainModelListener listener = niceMocksControl_.createMock( IMainModelListener.class );
        listener.tableClosed( EasyMock.notNull( MainModelContentChangedEvent.class ) );
        niceMocksControl_.replay();
        model_.openTable();
        model_.saveTable( file );
        model_.addMainModelListener( listener );

        model_.openTable( file );

        niceMocksControl_.verify();
    }

    /**
     * Ensures the {@code openTable(File)} method fires a table opened event.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testOpenTableFromFile_FiresTableOpenedEvent()
        throws Exception
    {
        final File file = createTemporaryFile();
        final IMainModelListener listener = niceMocksControl_.createMock( IMainModelListener.class );
        listener.tableOpened( EasyMock.notNull( MainModelContentChangedEvent.class ) );
        niceMocksControl_.replay();
        model_.openTable();
        model_.saveTable( file );
        model_.addMainModelListener( listener );

        model_.openTable( file );

        niceMocksControl_.verify();
    }

    /**
     * Ensures the {@code removeMainModelListener} method throws an exception
     * when passed a listener that is absent from the main model listener
     * collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveMainModelListener_Listener_Absent()
    {
        model_.removeMainModelListener( niceMocksControl_.createMock( IMainModelListener.class ) );
    }

    /**
     * Ensures the {@code removeMainModelListener} method throws an exception
     * when passed a {@code null} listener.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveMainModelListener_Listener_Null()
    {
        model_.removeMainModelListener( null );
    }

    /**
     * Ensures the {@code removeMainModelListener} removes a listener that is
     * present in the main model listener collection.
     */
    @Test
    public void testRemoveMainModelListener_Listener_Present()
    {
        final IMainModelListener listener = niceMocksControl_.createMock( IMainModelListener.class );
        listener.tableOpened( EasyMock.notNull( MainModelContentChangedEvent.class ) );
        niceMocksControl_.replay();
        model_.addMainModelListener( listener );
        model_.openTable();

        model_.removeMainModelListener( listener );
        model_.openTable();

        niceMocksControl_.verify();
    }

    /**
     * Ensures the {@code saveTable} method throws an exception when passed a
     * {@code null} file.
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
     * Ensures the {@code saveTable} method fires a main model dirty flag
     * changed event.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testSaveTable_FiresMainModelDirtyFlagChangedEvent()
        throws Exception
    {
        final File file = createTemporaryFile();
        final IMainModelListener listener = niceMocksControl_.createMock( IMainModelListener.class );
        listener.mainModelDirtyFlagChanged( EasyMock.notNull( MainModelEvent.class ) );
        niceMocksControl_.replay();
        model_.openTable();
        model_.addMainModelListener( listener );

        model_.saveTable( file );

        niceMocksControl_.verify();
    }

    /**
     * Ensures the {@code saveTable} method fires a main model file changed
     * event.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testSaveTable_FiresMainModelFileChangedEvent()
        throws Exception
    {
        final File file = createTemporaryFile();
        final IMainModelListener listener = niceMocksControl_.createMock( IMainModelListener.class );
        listener.mainModelFileChanged( EasyMock.notNull( MainModelEvent.class ) );
        niceMocksControl_.replay();
        model_.openTable();
        model_.addMainModelListener( listener );

        model_.saveTable( file );

        niceMocksControl_.verify();
    }

    /**
     * Ensures the {@code saveTable} method fires a main model state changed
     * event.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testSaveTable_FiresMainModelStateChangedEvent()
        throws Exception
    {
        final File file = createTemporaryFile();
        final IMainModelListener listener = niceMocksControl_.createMock( IMainModelListener.class );
        listener.mainModelStateChanged( EasyMock.notNull( MainModelEvent.class ) );
        niceMocksControl_.replay();
        model_.openTable();
        model_.addMainModelListener( listener );

        model_.saveTable( file );

        niceMocksControl_.verify();
    }

    /**
     * Ensures the table closed event catches any exception thrown by the
     * {@code tableClosed} method of a main model listener.
     */
    @Test
    public void testTableClosed_CatchesListenerException()
    {
        final IMainModelListener listener = niceMocksControl_.createMock( IMainModelListener.class );
        listener.tableClosed( EasyMock.notNull( MainModelContentChangedEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        niceMocksControl_.replay();
        model_.openTable();
        model_.addMainModelListener( listener );

        model_.openTable();

        niceMocksControl_.verify();
    }

    /**
     * Ensures a change to a table model owned by the main model fires a main
     * model dirty flag changed event.
     */
    @Test
    public void testTableModel_StateChanged_FiresMainModelDirtyFlagChangedEvent()
    {
        model_.openTable();
        final IMainModelListener listener = niceMocksControl_.createMock( IMainModelListener.class );
        listener.mainModelDirtyFlagChanged( EasyMock.notNull( MainModelEvent.class ) );
        niceMocksControl_.replay();
        model_.addMainModelListener( listener );

        model_.getTableModel().setOriginOffset( new Dimension( 100, 200 ) );

        niceMocksControl_.verify();
    }

    /**
     * Ensures a change to a table model owned by the main model fires a main
     * model state changed event.
     */
    @Test
    public void testTableModel_StateChanged_FiresMainModelStateChangedEvent()
    {
        model_.openTable();
        final IMainModelListener listener = niceMocksControl_.createMock( IMainModelListener.class );
        listener.mainModelStateChanged( EasyMock.notNull( MainModelEvent.class ) );
        niceMocksControl_.replay();
        model_.addMainModelListener( listener );

        model_.getTableModel().setOriginOffset( new Dimension( 100, 200 ) );

        niceMocksControl_.verify();
    }

    /**
     * Ensures the table opened event catches any exception thrown by the
     * {@code tableOpened} method of a main model listener.
     */
    @Test
    public void testTableOpened_CatchesListenerException()
    {
        final IMainModelListener listener = niceMocksControl_.createMock( IMainModelListener.class );
        listener.tableOpened( EasyMock.notNull( MainModelContentChangedEvent.class ) );
        EasyMock.expectLastCall().andThrow( new RuntimeException() );
        niceMocksControl_.replay();
        model_.addMainModelListener( listener );

        model_.openTable();

        niceMocksControl_.verify();
    }
}
