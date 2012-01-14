/*
 * MainFrame.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Sep 18, 2009 at 10:09:51 PM.
 */

package org.gamegineer.table.internal.ui.view;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.common.core.util.IPredicate;
import org.gamegineer.common.ui.databinding.swing.SwingRealm;
import org.gamegineer.common.ui.help.IHelpSystem;
import org.gamegineer.table.internal.ui.Activator;
import org.gamegineer.table.internal.ui.BundleImages;
import org.gamegineer.table.internal.ui.Loggers;
import org.gamegineer.table.internal.ui.action.ActionMediator;
import org.gamegineer.table.internal.ui.model.FramePreferences;
import org.gamegineer.table.internal.ui.model.IMainModelListener;
import org.gamegineer.table.internal.ui.model.ITableModelListener;
import org.gamegineer.table.internal.ui.model.MainModel;
import org.gamegineer.table.internal.ui.model.MainModelEvent;
import org.gamegineer.table.internal.ui.model.ModelException;
import org.gamegineer.table.internal.ui.model.TableModelEvent;
import org.gamegineer.table.internal.ui.util.OptionDialogs;
import org.gamegineer.table.internal.ui.util.swing.JFileChooser;
import org.gamegineer.table.ui.ITableAdvisor;

/**
 * The top-level frame.
 */
@NotThreadSafe
public final class MainFrame
    extends JFrame
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 1087139002992381995L;

    /** The action mediator. */
    private final ActionMediator actionMediator_;

    /** The asynchronous completion token for the action updater task. */
    private Future<?> actionUpdaterTaskFuture_;

    /** Indicates an action update is required. */
    private final AtomicBoolean isActionUpdateRequired_;

    /** The main model listener for this view. */
    private IMainModelListener mainModelListener_;

    /** The main view. */
    private final MainView mainView_;

    /** The menu bar view. */
    private final MenuBarView menuBarView_;

    /** The model. */
    private final MainModel model_;

    /** The table model listener for this view. */
    private ITableModelListener tableModelListener_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MainFrame} class.
     * 
     * @param advisor
     *        The table advisor; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code advisor} is {@code null}.
     */
    public MainFrame(
        /* @NonNull */
        final ITableAdvisor advisor )
    {
        assertArgumentNotNull( advisor, "advisor" ); //$NON-NLS-1$

        actionMediator_ = new ActionMediator();
        actionUpdaterTaskFuture_ = null;
        isActionUpdateRequired_ = new AtomicBoolean( false );
        mainModelListener_ = null;
        model_ = new MainModel( advisor );
        mainView_ = new MainView( model_ );
        menuBarView_ = new MenuBarView( model_ );
        tableModelListener_ = null;

        initializeComponent();

        loadApplicationState();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see java.awt.Frame#addNotify()
     */
    @Override
    public void addNotify()
    {
        super.addNotify();

        SwingRealm.installSystemRealm();

        bindActions();
        startActionUpdater();

        mainModelListener_ = new MainModelListener();
        model_.addMainModelListener( mainModelListener_ );
        tableModelListener_ = new TableModelListener();
        model_.getTableModel().addTableModelListener( tableModelListener_ );
    }

    /**
     * Binds the action attachments for this component.
     */
    private void bindActions()
    {
        actionMediator_.bindActionListener( Actions.getDisplayHelpAction(), new ActionListener()
        {
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent event )
            {
                final IHelpSystem helpSystem = Activator.getDefault().getHelpSystem();
                if( helpSystem != null )
                {
                    helpSystem.displayHelp( MainFrame.this );
                }
            }
        } );
        actionMediator_.bindActionListener( Actions.getExitAction(), new ActionListener()
        {
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent event )
            {
                if( confirmSaveDirtyTable() )
                {
                    dispose();
                }
            }
        } );
        actionMediator_.bindActionListener( Actions.getOpenAboutDialogAction(), new ActionListener()
        {
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent event )
            {
                JOptionPane.showMessageDialog( MainFrame.this, NlsMessages.AboutDialog_message( model_.getVersion() ), NlsMessages.AboutDialog_title, JOptionPane.OK_OPTION | JOptionPane.INFORMATION_MESSAGE );
            }
        } );
        actionMediator_.bindActionListener( Actions.getOpenNewTableAction(), new ActionListener()
        {
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent event )
            {
                openTable();
            }
        } );
        actionMediator_.bindActionListener( Actions.getOpenTableAction(), new ActionListener()
        {
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                final ActionEvent event )
            {
                openTable( event.getActionCommand() );
            }
        } );
        actionMediator_.bindActionListener( Actions.getSaveTableAction(), new ActionListener()
        {
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent event )
            {
                saveTable( false );
            }
        } );
        actionMediator_.bindActionListener( Actions.getSaveTableAsAction(), new ActionListener()
        {
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent event )
            {
                saveTable( true );
            }
        } );

        final IPredicate<Action> isTableDirtyPredicate = new IPredicate<Action>()
        {
            @SuppressWarnings( "synthetic-access" )
            public boolean evaluate(
                @SuppressWarnings( "unused" )
                final Action obj )
            {
                return isTableDirty();
            }
        };
        final IPredicate<Action> isTableEditablePredicate = new IPredicate<Action>()
        {
            @SuppressWarnings( "synthetic-access" )
            public boolean evaluate(
                @SuppressWarnings( "unused" )
                final Action obj )
            {
                return isTableEditable();
            }
        };
        actionMediator_.bindShouldEnablePredicate( Actions.getOpenNewTableAction(), isTableEditablePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getOpenTableAction(), isTableEditablePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getSaveTableAction(), isTableDirtyPredicate );
    }

    /**
     * Prompts the user to save the active table if it is dirty.
     * 
     * <p>
     * If this method returns {@code false}, the caller should not proceed to
     * discard the current table.
     * </p>
     * 
     * @return {@code true} if the table is not dirty, the user chooses not to
     *         save the dirty table, or the dirty table is saved successfully;
     *         {@code false} if the user wishes to abort the current operation
     *         or the dirty table was not saved successfully.
     */
    private boolean confirmSaveDirtyTable()
    {
        if( !isTableDirty() )
        {
            return true;
        }

        final int result = OptionDialogs.showConfirmDialog( this, NlsMessages.MainFrame_confirmSaveDirtyTable_message( getTableName() ) );
        if( result == JOptionPane.YES_OPTION )
        {
            return saveTable( false );
        }
        else if( result == JOptionPane.NO_OPTION )
        {
            return true;
        }

        return false; // JOptionPane.CANCEL_OPTION
    }

    /*
     * @see java.awt.Window#dispose()
     */
    @Override
    public void dispose()
    {
        ViewUtils.disconnectTableNetwork( this, model_.getTableModel().getTableNetwork() );

        stopActionUpdater();

        super.dispose();
    }

    /**
     * Gets the collection of application icon images.
     * 
     * @return The collection of application icon images; never {@code null}.
     */
    /* @NonNull */
    private static List<Image> getApplicationIconImages()
    {
        final String[] imagePaths = new String[] { //
            BundleImages.APPLICATION_16, //
            BundleImages.APPLICATION_32, //
            BundleImages.APPLICATION_48, //
            BundleImages.APPLICATION_64
        };
        final BundleImages bundleImages = Activator.getDefault().getBundleImages();
        final List<Image> iconImages = new ArrayList<Image>();
        for( final String imagePath : imagePaths )
        {
            final Image iconImage = bundleImages.getImage( imagePath );
            if( iconImage != null )
            {
                iconImages.add( iconImage );
            }
        }

        return iconImages;
    }

    /**
     * Gets the file associated with the table.
     * 
     * @return The file associated with the table; never {@code null}.
     */
    /* @NonNull */
    private File getTableFile()
    {
        return model_.getTableModel().getFile();
    }

    /**
     * Gets the name of the table.
     * 
     * @return The name of the table; never {@code null}.
     */
    /* @NonNull */
    private String getTableName()
    {
        final File file = getTableFile();
        if( file == null )
        {
            return NlsMessages.MainFrame_untitledTable;
        }

        return file.getName();
    }

    /**
     * Initializes this component.
     */
    private void initializeComponent()
    {
        setJMenuBar( menuBarView_.getMenuBar() );

        setContentPane( mainView_ );

        setLocationByPlatform( true );
        setSize( 640, 480 );
        setIconImages( getApplicationIconImages() );
        updateTitle();
    }

    /**
     * Indicates the table is dirty.
     * 
     * @return {@code true} if the table is dirty; otherwise {@code false}.
     */
    private boolean isTableDirty()
    {
        return model_.getTableModel().isDirty();
    }

    /**
     * Indicates the table is editable.
     * 
     * @return {@code true} if the table is editable; otherwise {@code false}.
     */
    private boolean isTableEditable()
    {
        return model_.getTableModel().isEditable();
    }

    /**
     * Loads the global application state.
     */
    private void loadApplicationState()
    {
        model_.load();

        loadPreferences();
    }

    /**
     * Loads the preferences for this class.
     */
    @SuppressWarnings( "boxing" )
    private void loadPreferences()
    {
        final FramePreferences framePreferences = model_.getPreferencesModel().getFramePreferences();

        final Point location = framePreferences.getLocation();
        if( location != null )
        {
            setLocation( location );
        }

        final Dimension size = framePreferences.getSize();
        if( size != null )
        {
            setSize( size );
        }

        final Integer state = framePreferences.getState();
        if( state != null )
        {
            setExtendedState( state );
        }
    }

    /**
     * Opens a new empty table.
     */
    private void openTable()
    {
        if( confirmSaveDirtyTable() )
        {
            model_.openTable();
        }
    }

    /**
     * Opens the an existing table from the specified file.
     * 
     * @param fileName
     *        The name of the file from which the table will be opened; must not
     *        be {@code null}. Pass an empty string to prompt the user for a
     *        file name.
     */
    private void openTable(
        /* @NonNull */
        final String fileName )
    {
        assert fileName != null;

        if( !confirmSaveDirtyTable() )
        {
            return;
        }

        final File file;
        if( fileName.isEmpty() )
        {
            final JFileChooser fileChooser = FileChoosers.getTableFileChooser( getTableFile() );
            if( fileChooser.showOpenDialog( this ) == JFileChooser.CANCEL_OPTION )
            {
                return;
            }

            file = fileChooser.getSelectedFile();
        }
        else
        {
            file = new File( fileName );
        }

        try
        {
            model_.openTable( file );
        }
        catch( final ModelException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.MainFrame_openTable_error( file ), e );
            OptionDialogs.showErrorMessageDialog( this, NlsMessages.MainFrame_openTable_error );
        }
    }

    /*
     * @see javax.swing.JFrame#processWindowEvent(java.awt.event.WindowEvent)
     */
    @Override
    protected void processWindowEvent(
        final WindowEvent event )
    {
        switch( event.getID() )
        {
            case WindowEvent.WINDOW_CLOSED:
                saveApplicationState();
                break;

            case WindowEvent.WINDOW_CLOSING:
                if( !confirmSaveDirtyTable() )
                {
                    return;
                }
                break;
        }

        super.processWindowEvent( event );
    }

    /*
     * @see java.awt.Frame#removeNotify()
     */
    @Override
    public void removeNotify()
    {
        model_.getTableModel().removeTableModelListener( tableModelListener_ );
        tableModelListener_ = null;
        model_.removeMainModelListener( mainModelListener_ );
        mainModelListener_ = null;

        stopActionUpdater();
        actionMediator_.unbindAll();

        SwingRealm.uninstallSystemRealm();

        super.removeNotify();
    }

    /**
     * Saves the global application state.
     */
    private void saveApplicationState()
    {
        savePreferences();

        model_.save();
    }

    /**
     * Saves the preferences for this class.
     */
    @SuppressWarnings( "boxing" )
    private void savePreferences()
    {
        final FramePreferences framePreferences = model_.getPreferencesModel().getFramePreferences();
        framePreferences.setLocation( getLocation() );
        framePreferences.setSize( getSize() );
        framePreferences.setState( getExtendedState() );
    }

    /**
     * Saves the table state to a file.
     * 
     * @param forcePromptForFile
     *        Indicates the user should be forced to choose a file regardless of
     *        whether or not the table state is already associated with an
     *        existing file.
     * 
     * @return {@code true} if the table was saved; otherwise {@code false}.
     */
    private boolean saveTable(
        final boolean forcePromptForFile )
    {
        final File file;
        if( forcePromptForFile || (getTableFile() == null) )
        {
            final JFileChooser fileChooser = FileChoosers.getTableFileChooser( getTableFile() );
            if( fileChooser.showSaveDialog( this ) == JFileChooser.CANCEL_OPTION )
            {
                return false;
            }

            file = fileChooser.getSelectedFile();
        }
        else
        {
            file = getTableFile();
        }

        try
        {
            model_.saveTable( file );
        }
        catch( final ModelException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.MainFrame_saveTable_error( file ), e );
            OptionDialogs.showErrorMessageDialog( this, NlsMessages.MainFrame_saveTable_error );
            return false;
        }

        return true;
    }

    /**
     * Starts the action updater task.
     */
    private void startActionUpdater()
    {
        assert actionUpdaterTaskFuture_ == null;

        actionUpdaterTaskFuture_ = Activator.getDefault().getExecutorService().submit( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                try
                {
                    while( !Thread.interrupted() )
                    {
                        if( isActionUpdateRequired_.getAndSet( false ) )
                        {
                            try
                            {
                                SwingUtilities.invokeAndWait( new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        Actions.updateAll();
                                    }
                                } );
                            }
                            catch( final InvocationTargetException e )
                            {
                                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.MainFrame_actionUpdater_error, e );
                            }
                        }

                        Thread.sleep( 250L );
                    }
                }
                catch( final InterruptedException e )
                {
                    // cancelled
                }
            }
        } );
    }

    /**
     * Stops the action updater task.
     */
    private void stopActionUpdater()
    {
        if( actionUpdaterTaskFuture_ != null )
        {
            actionUpdaterTaskFuture_.cancel( true );
            actionUpdaterTaskFuture_ = null;
        }
    }

    /**
     * Updates the frame title based on the state of the model.
     */
    private void updateTitle()
    {
        setTitle( NlsMessages.MainFrame_title( getTableName() ) );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A main model listener for the main frame.
     */
    @Immutable
    private final class MainModelListener
        extends org.gamegineer.table.internal.ui.model.MainModelListener
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code MainModelListener} class.
         */
        MainModelListener()
        {
            super();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.internal.ui.model.MainModelListener#mainModelStateChanged(org.gamegineer.table.internal.ui.model.MainModelEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void mainModelStateChanged(
            final MainModelEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            isActionUpdateRequired_.set( true );
        }
    }

    /**
     * A table model listener for the main frame.
     */
    @Immutable
    private final class TableModelListener
        extends org.gamegineer.table.internal.ui.model.TableModelListener
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code TableModelListener} class.
         */
        TableModelListener()
        {
            super();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.internal.ui.model.TableModelListener#tableModelFileChanged(org.gamegineer.table.internal.ui.model.TableModelEvent)
         */
        @Override
        public void tableModelFileChanged(
            final TableModelEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            SwingUtilities.invokeLater( new Runnable()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public void run()
                {
                    updateTitle();
                }
            } );
        }
    }
}
