/*
 * WizardDialog.java
 * Copyright 2008-2011 Gamegineer contributors and others.
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
 * Created on Sep 24, 2010 at 10:10:10 PM.
 */

package org.gamegineer.common.ui.wizard;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Window;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.common.ui.dialog.AbstractBannerDialog;
import org.gamegineer.common.ui.dialog.ComponentEnableState;
import org.gamegineer.common.ui.dialog.DialogConstants;
import org.gamegineer.common.ui.dialog.DialogMessage;
import org.gamegineer.common.ui.operation.RunnableTask;
import org.gamegineer.common.ui.window.WindowConstants;

/**
 * A dialog that hosts a wizard.
 */
@NotThreadSafe
public final class WizardDialog
    extends AbstractBannerDialog
    implements IWizardContainer
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The minimum dialog height in dialog units. */
    private static final int MINIMUM_DIALOG_HEIGHT = 300;

    /** The minimum dialog width in dialog units. */
    private static final int MINIMUM_DIALOG_WIDTH = 350;

    /** The active wizard page. */
    private IWizardPage activePage_;

    /** The component enable state before executing the active wizard task. */
    private ComponentEnableState componentEnableState_;

    /** Indicates the wizard is in the process of moving to the previous page. */
    private boolean isMovingToPreviousPage_;

    /** The container for all page content. */
    private Container pageContainer_;

    /** The wizard progress monitor component. */
    private ProgressMonitorComponent progressMonitorComponent_;

    /** The active task. */
    private RunnableTask<?, ?> task_;

    /** The wizard hosted in the dialog. */
    private final IWizard wizard_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code WizardDialog} class.
     * 
     * @param parentShell
     *        The parent shell or {@code null} to create a top-level shell.
     * @param wizard
     *        The wizard hosted in the dialog; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code wizard} is {@code null}.
     */
    public WizardDialog(
        /* @Nullable */
        final Window parentShell,
        /* @NonNull */
        final IWizard wizard )
    {
        super( parentShell );

        wizard.setContainer( this );

        activePage_ = null;
        componentEnableState_ = null;
        isMovingToPreviousPage_ = false;
        pageContainer_ = null;
        progressMonitorComponent_ = null;
        task_ = null;
        wizard_ = wizard;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.ui.wizard.IWizardContainer#activatePage(org.gamegineer.common.ui.wizard.IWizardPage)
     */
    @Override
    public void activatePage(
        final IWizardPage page )
    {
        assertArgumentNotNull( page, "page" ); //$NON-NLS-1$

        if( page == activePage_ )
        {
            return;
        }

        if( isMovingToPreviousPage_ )
        {
            isMovingToPreviousPage_ = false;
        }
        else
        {
            page.setPreviousPage( activePage_ );
        }

        if( page.getContent() == null )
        {
            page.create( pageContainer_ );
            assert page.getContent() != null;
        }

        if( activePage_ != null )
        {
            activePage_.setVisible( false );
        }
        activePage_ = page;
        activePage_.setVisible( true );

        update();
    }

    /*
     * @see org.gamegineer.common.ui.wizard.IWizardContainer#back()
     */
    @Override
    public void back()
    {
        pressButton( WizardConstants.BACK_BUTTON_ID );
    }

    /**
     * Invoked when the Back button is pressed.
     */
    private void backPressed()
    {
        final IWizardPage previousPage = activePage_.getPreviousPage();
        if( previousPage != null )
        {
            isMovingToPreviousPage_ = true;
            activatePage( previousPage );
        }
    }

    /**
     * Begins the execution of the specified task.
     * 
     * @param task
     *        The task; must not be {@code null}.
     */
    private void beginExecuteTask(
        /* @NonNull */
        final RunnableTask<?, ?> task )
    {
        assert task != null;

        assert task_ == null;
        task_ = task;

        componentEnableState_ = ComponentEnableState.disable( pageContainer_ );
        updateButtons();

        getShell().setCursor( Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR ) );
        if( task.isCancellable() )
        {
            final JButton cancelButton = getButton( DialogConstants.CANCEL_BUTTON_ID );
            assert cancelButton != null;
            cancelButton.setCursor( Cursor.getDefaultCursor() );
        }

        if( wizard_.needsProgressMonitor() )
        {
            progressMonitorComponent_.setVisible( true );
            task.addPropertyChangeListener( progressMonitorComponent_ );
        }
    }

    /*
     * @see org.gamegineer.common.ui.dialog.AbstractDialog#buttonPressed(java.lang.String)
     */
    @Override
    protected void buttonPressed(
        final String id )
    {
        if( id.equals( WizardConstants.BACK_BUTTON_ID ) )
        {
            backPressed();
        }
        else if( id.equals( WizardConstants.NEXT_BUTTON_ID ) )
        {
            nextPressed();
        }
        else if( id.equals( WizardConstants.FINISH_BUTTON_ID ) )
        {
            finishPressed();
        }
        else if( id.equals( DialogConstants.CANCEL_BUTTON_ID ) )
        {
            cancelPressed();
        }
    }

    /*
     * @see org.gamegineer.common.ui.wizard.IWizardContainer#cancel()
     */
    @Override
    public void cancel()
    {
        pressButton( DialogConstants.CANCEL_BUTTON_ID );
    }

    /*
     * @see org.gamegineer.common.ui.dialog.AbstractDialog#cancelPressed()
     */
    @Override
    protected void cancelPressed()
    {
        if( task_ != null )
        {
            if( task_.isCancellable() )
            {
                final JButton cancelButton = getButton( DialogConstants.CANCEL_BUTTON_ID );
                cancelButton.setEnabled( false );
                cancelButton.setCursor( Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR ) );
                task_.cancel( task_.isInterruptible() );
            }
        }
        else
        {
            if( wizard_.performCancel() )
            {
                setReturnCode( WindowConstants.RETURN_CODE_CANCEL );
                close();
            }
        }
    }

    /*
     * @see org.gamegineer.common.ui.dialog.AbstractDialog#close()
     */
    @Override
    public boolean close()
    {
        wizard_.dispose();
        wizard_.setContainer( null );

        return super.close();
    }

    /*
     * @see org.gamegineer.common.ui.window.AbstractWindow#contentCreated()
     */
    @Override
    protected void contentCreated()
    {
        super.contentCreated();

        showFirstPage();
    }

    /*
     * @see org.gamegineer.common.ui.dialog.AbstractDialog#createButtonBar(java.awt.Container)
     */
    @Override
    protected Component createButtonBar(
        final Container parent )
    {
        final JPanel container = new JPanel();
        parent.add( container );
        container.setLayout( new BorderLayout() );

        container.add( new JSeparator(), BorderLayout.NORTH );

        final JPanel buttonPanel = new JPanel();
        container.add( buttonPanel, BorderLayout.CENTER );
        final GroupLayout layout = new GroupLayout( buttonPanel );
        buttonPanel.setLayout( layout );

        final int marginWidth = convertWidthInDlusToPixels( DialogConstants.HORIZONTAL_MARGIN );
        final int marginHeight = convertHeightInDlusToPixels( DialogConstants.VERTICAL_MARGIN );
        buttonPanel.setBorder( BorderFactory.createEmptyBorder( marginHeight, marginWidth, marginHeight, marginWidth ) );

        final GroupLayout.Group hGroup = layout.createSequentialGroup();
        final GroupLayout.Group vGroup = layout.createParallelGroup();
        final int hGap = convertWidthInDlusToPixels( DialogConstants.HORIZONTAL_SPACING );

        hGroup.addGap( 0, 0, Integer.MAX_VALUE );

        if( wizard_.needsPreviousAndNextButtons() )
        {
            final JButton backButton = createButton( parent, WizardConstants.BACK_BUTTON_ID, NlsMessages.WizardDialog_backButton_label, false );
            backButton.setMnemonic( NlsMessages.WizardDialog_backButton_mnemonic.charAt( 0 ) );
            hGroup.addComponent( backButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE );
            vGroup.addComponent( backButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE );

            hGroup.addGap( convertWidthInDlusToPixels( 1 ) );

            final JButton nextButton = createButton( parent, WizardConstants.NEXT_BUTTON_ID, NlsMessages.WizardDialog_nextButton_label, false );
            nextButton.setMnemonic( NlsMessages.WizardDialog_nextButton_mnemonic.charAt( 0 ) );
            hGroup.addComponent( nextButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE );
            vGroup.addComponent( nextButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE );

            hGroup.addGap( hGap );
        }

        final JButton finishButton = createButton( parent, WizardConstants.FINISH_BUTTON_ID, NlsMessages.WizardDialog_finishButton_label, true );
        finishButton.setMnemonic( NlsMessages.WizardDialog_finishButton_mnemonic.charAt( 0 ) );
        hGroup.addComponent( finishButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE );
        vGroup.addComponent( finishButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE );

        hGroup.addGap( hGap );

        final JButton cancelButton = createButton( parent, DialogConstants.CANCEL_BUTTON_ID, DialogConstants.CANCEL_BUTTON_LABEL, false );
        hGroup.addComponent( cancelButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE );
        vGroup.addComponent( cancelButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE );

        layout.setHorizontalGroup( hGroup );
        layout.setVerticalGroup( vGroup );

        return container;
    }

    /*
     * @see org.gamegineer.common.ui.dialog.AbstractBannerDialog#createContentArea(java.awt.Container)
     */
    @Override
    protected Component createContentArea(
        final Container parent )
    {
        wizard_.addPages();

        final Container container = (Container)super.createContentArea( parent );
        final GroupLayout layout = new GroupLayout( container );
        container.setLayout( layout );

        pageContainer_ = new JPanel();
        pageContainer_.setLayout( new BorderLayout() );
        container.add( pageContainer_ );

        progressMonitorComponent_ = new ProgressMonitorComponent();
        progressMonitorComponent_.setBorder( BorderFactory.createEmptyBorder( convertHeightInDlusToPixels( DialogConstants.VERTICAL_MARGIN ), 0, 0, 0 ) );
        progressMonitorComponent_.setVisible( false );
        container.add( progressMonitorComponent_ );

        final GroupLayout.Group hGroup = layout.createParallelGroup();
        hGroup.addComponent( pageContainer_ );
        hGroup.addComponent( progressMonitorComponent_ );
        final GroupLayout.Group vGroup = layout.createSequentialGroup();
        vGroup.addComponent( pageContainer_ );
        vGroup.addComponent( progressMonitorComponent_, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE );
        layout.setHorizontalGroup( hGroup );
        layout.setVerticalGroup( vGroup );
        layout.setHonorsVisibility( progressMonitorComponent_, Boolean.FALSE );

        wizard_.create( pageContainer_ );
        for( final IWizardPage page : wizard_.getPages() )
        {
            page.setVisible( false );
        }

        return container;
    }

    /**
     * Ends the execution of the active task.
     */
    private void endExecuteTask()
    {
        assert SwingUtilities.isEventDispatchThread();
        assert task_ != null;

        final RunnableTask<?, ?> task = task_;
        task_ = null;

        if( wizard_.needsProgressMonitor() )
        {
            task.removePropertyChangeListener( progressMonitorComponent_ );
            progressMonitorComponent_.setVisible( false );
        }

        componentEnableState_.restore();
        componentEnableState_ = null;
        updateButtons();

        getShell().setCursor( null );
        if( task.isCancellable() )
        {
            final JButton cancelButton = getButton( DialogConstants.CANCEL_BUTTON_ID );
            assert cancelButton != null;
            cancelButton.setCursor( null );
        }
    }

    /*
     * @see org.gamegineer.common.ui.operation.IRunnableContext#executeTask(org.gamegineer.common.ui.operation.RunnableTask)
     */
    @Override
    public void executeTask(
        final RunnableTask<?, ?> task )
    {
        assertArgumentNotNull( task, "task" ); //$NON-NLS-1$

        beginExecuteTask( task );

        task.addPropertyChangeListener( new PropertyChangeListener()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void propertyChange(
                final PropertyChangeEvent event )
            {
                if( RunnableTask.STATE_PROPERTY_NAME.equals( event.getPropertyName() ) && (event.getNewValue() == SwingWorker.StateValue.DONE) )
                {
                    endExecuteTask();
                }
            }
        } );
        task.execute();
    }

    /*
     * @see org.gamegineer.common.ui.wizard.IWizardContainer#finish()
     */
    @Override
    public void finish()
    {
        pressButton( WizardConstants.FINISH_BUTTON_ID );
    }

    /**
     * Invoked when the Finish button is pressed.
     */
    private void finishPressed()
    {
        if( wizard_.performFinish() )
        {
            setReturnCode( WindowConstants.RETURN_CODE_OK );
            close();
        }
    }

    /*
     * @see org.gamegineer.common.ui.wizard.IWizardContainer#getActivePage()
     */
    @Override
    public IWizardPage getActivePage()
    {
        return activePage_;
    }

    /*
     * @see org.gamegineer.common.ui.window.AbstractWindow#getInitialSize()
     */
    @Override
    protected Dimension getInitialSize()
    {
        final Dimension initialSize = super.getInitialSize();
        initialSize.width = Math.max( convertWidthInDlusToPixels( MINIMUM_DIALOG_WIDTH ), initialSize.width );
        initialSize.height = Math.max( convertHeightInDlusToPixels( MINIMUM_DIALOG_HEIGHT ), initialSize.height );
        return initialSize;
    }

    /*
     * @see org.gamegineer.common.ui.wizard.IWizardContainer#next()
     */
    @Override
    public void next()
    {
        pressButton( WizardConstants.NEXT_BUTTON_ID );
    }

    /**
     * Invoked when the Next button is pressed.
     */
    private void nextPressed()
    {
        final IWizardPage nextPage = activePage_.getNextPage();
        if( nextPage != null )
        {
            activatePage( nextPage );
        }
    }

    /**
     * Simulates pressing the specified button.
     * 
     * <p>
     * This method does nothing if the specified button does not exist or is not
     * enabled. The button press is submitted to the window's event queue and
     * will be processed as soon as possible.
     * </p>
     * 
     * @param id
     *        The button identifier; must not be {@code null}.
     */
    private void pressButton(
        /* @NonNull */
        final String id )
    {
        assert id != null;

        SwingUtilities.invokeLater( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                final JButton button = getButton( id );
                if( (button != null) && button.isEnabled() )
                {
                    buttonPressed( id );
                }
            }
        } );
    }

    /**
     * Shows the first page of the wizard.
     */
    private void showFirstPage()
    {
        activePage_ = wizard_.getFirstPage();
        if( activePage_ == null )
        {
            return;
        }

        if( activePage_.getContent() == null )
        {
            activePage_.create( pageContainer_ );
            assert activePage_.getContent() != null;
        }

        activePage_.setVisible( true );
        update();
    }

    /**
     * Updates the dialog state based on the active page.
     */
    private void update()
    {
        updateShell();
        updateBanner();
        updateButtons();
    }

    /*
     * @see org.gamegineer.common.ui.wizard.IWizardContainer#updateBanner()
     */
    @Override
    public void updateBanner()
    {
        setBannerTitle( (activePage_ != null) ? activePage_.getTitle() : null );

        updateMessage();
    }

    /*
     * @see org.gamegineer.common.ui.wizard.IWizardContainer#updateButtons()
     */
    @Override
    public void updateButtons()
    {
        boolean canMoveToNextPage = false;
        final boolean canFinish = wizard_.canFinish();
        final boolean isTaskIdle = (task_ == null);

        final JButton backButton = getButton( WizardConstants.BACK_BUTTON_ID );
        if( backButton != null )
        {
            backButton.setEnabled( isTaskIdle && (activePage_.getPreviousPage() != null) );
        }

        final JButton nextButton = getButton( WizardConstants.NEXT_BUTTON_ID );
        if( nextButton != null )
        {
            canMoveToNextPage = activePage_.canMoveToNextPage();
            nextButton.setEnabled( isTaskIdle && canMoveToNextPage );
        }

        final JButton finishButton = getButton( WizardConstants.FINISH_BUTTON_ID );
        assert finishButton != null;
        finishButton.setEnabled( isTaskIdle && canFinish );

        final JButton cancelButton = getButton( DialogConstants.CANCEL_BUTTON_ID );
        assert cancelButton != null;
        cancelButton.setEnabled( isTaskIdle || task_.isCancellable() );

        getShell().getRootPane().setDefaultButton( (canMoveToNextPage && !canFinish) ? nextButton : finishButton );
    }

    /*
     * @see org.gamegineer.common.ui.wizard.IWizardContainer#updateMessage()
     */
    @Override
    public void updateMessage()
    {
        final String description;
        final DialogMessage message;
        if( activePage_ != null )
        {
            description = activePage_.getDescription();
            message = activePage_.getMessage();
        }
        else
        {
            description = null;
            message = null;
        }

        setDescription( description );
        setMessage( message );
    }

    /*
     * @see org.gamegineer.common.ui.wizard.IWizardContainer#updateShell()
     */
    @Override
    public void updateShell()
    {
        setTitle( wizard_.getTitle() );
    }
}
