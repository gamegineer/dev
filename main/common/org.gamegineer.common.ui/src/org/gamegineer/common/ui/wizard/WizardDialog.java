/*
 * WizardDialog.java
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
 * Created on Sep 24, 2010 at 10:10:10 PM.
 */

package org.gamegineer.common.ui.wizard;

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
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.jdt.annotation.Nullable;
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
    private @Nullable IWizardPage activePage_;

    /** The component enable state before executing the active wizard task. */
    private @Nullable ComponentEnableState componentEnableState_;

    /**
     * Indicates the wizard is in the process of moving to the previous page.
     */
    private boolean isMovingToPreviousPage_;

    /** The container for all page content. */
    private @Nullable Container pageContainer_;

    /** The wizard progress monitor component. */
    private @Nullable ProgressMonitorComponent progressMonitorComponent_;

    /** The active task. */
    private @Nullable RunnableTask<?, ?> task_;

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
     *        The wizard hosted in the dialog.
     */
    public WizardDialog(
        final @Nullable Window parentShell,
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
            assert pageContainer_ != null;
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
        assert activePage_ != null;

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
     *        The task.
     */
    private void beginExecuteTask(
        final RunnableTask<?, ?> task )
    {
        assert task_ == null;
        task_ = task;

        assert pageContainer_ != null;
        componentEnableState_ = ComponentEnableState.disable( pageContainer_ );
        updateButtons();

        final JDialog shell = getShell();
        assert shell != null;
        shell.setCursor( Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR ) );
        if( task.isCancellable() )
        {
            final JButton cancelButton = getButton( DialogConstants.CANCEL_BUTTON_ID );
            assert cancelButton != null;
            cancelButton.setCursor( Cursor.getDefaultCursor() );
        }

        if( wizard_.needsProgressMonitor() )
        {
            final ProgressMonitorComponent progressMonitorComponent = progressMonitorComponent_;
            assert progressMonitorComponent != null;
            progressMonitorComponent.setVisible( true );
            task.addPropertyChangeListener( progressMonitorComponent );
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
        final RunnableTask<?, ?> task = task_;
        if( task != null )
        {
            if( task.isCancellable() )
            {
                final JButton cancelButton = getButton( DialogConstants.CANCEL_BUTTON_ID );
                assert cancelButton != null;
                cancelButton.setEnabled( false );
                cancelButton.setCursor( Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR ) );
                task.cancel( task.isInterruptible() );
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

        final Container pageContainer = pageContainer_ = new JPanel();
        pageContainer.setLayout( new BorderLayout() );
        container.add( pageContainer );

        final ProgressMonitorComponent progressMonitorComponent = progressMonitorComponent_ = new ProgressMonitorComponent();
        progressMonitorComponent.setBorder( BorderFactory.createEmptyBorder( convertHeightInDlusToPixels( DialogConstants.VERTICAL_MARGIN ), 0, 0, 0 ) );
        progressMonitorComponent.setVisible( false );
        container.add( progressMonitorComponent );

        final GroupLayout.Group hGroup = layout.createParallelGroup();
        hGroup.addComponent( pageContainer );
        hGroup.addComponent( progressMonitorComponent );
        final GroupLayout.Group vGroup = layout.createSequentialGroup();
        vGroup.addComponent( pageContainer );
        vGroup.addComponent( progressMonitorComponent, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE );
        layout.setHorizontalGroup( hGroup );
        layout.setVerticalGroup( vGroup );
        layout.setHonorsVisibility( progressMonitorComponent, Boolean.FALSE );

        wizard_.create( pageContainer );
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
            final ProgressMonitorComponent progressMonitorComponent = progressMonitorComponent_;
            assert progressMonitorComponent != null;
            task.removePropertyChangeListener( progressMonitorComponent );
            progressMonitorComponent.setVisible( false );
        }

        assert componentEnableState_ != null;
        componentEnableState_.restore();
        componentEnableState_ = null;
        updateButtons();

        final JDialog shell = getShell();
        assert shell != null;
        shell.setCursor( null );
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
        beginExecuteTask( task );

        task.addPropertyChangeListener( new PropertyChangeListener()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void propertyChange(
                final @Nullable PropertyChangeEvent event )
            {
                assert event != null;

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
    public @Nullable IWizardPage getActivePage()
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
        assert activePage_ != null;
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
     *        The button identifier.
     */
    private void pressButton(
        final String id )
    {
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
        final IWizardPage activePage = activePage_ = wizard_.getFirstPage();
        if( activePage == null )
        {
            return;
        }

        if( activePage.getContent() == null )
        {
            assert pageContainer_ != null;
            activePage.create( pageContainer_ );
            assert activePage.getContent() != null;
        }

        activePage.setVisible( true );
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
        final boolean isTaskCancellable = (task_ != null) ? task_.isCancellable() : false;

        final IWizardPage activePage = activePage_;
        assert activePage != null;

        final JButton backButton = getButton( WizardConstants.BACK_BUTTON_ID );
        if( backButton != null )
        {
            backButton.setEnabled( isTaskIdle && (activePage.getPreviousPage() != null) );
        }

        final JButton nextButton = getButton( WizardConstants.NEXT_BUTTON_ID );
        if( nextButton != null )
        {
            canMoveToNextPage = activePage.canMoveToNextPage();
            nextButton.setEnabled( isTaskIdle && canMoveToNextPage );
        }

        final JButton finishButton = getButton( WizardConstants.FINISH_BUTTON_ID );
        assert finishButton != null;
        finishButton.setEnabled( isTaskIdle && canFinish );

        final JButton cancelButton = getButton( DialogConstants.CANCEL_BUTTON_ID );
        assert cancelButton != null;
        cancelButton.setEnabled( isTaskIdle || isTaskCancellable );

        final JDialog shell = getShell();
        assert shell != null;
        shell.getRootPane().setDefaultButton( (canMoveToNextPage && !canFinish) ? nextButton : finishButton );
    }

    /*
     * @see org.gamegineer.common.ui.wizard.IWizardContainer#updateMessage()
     */
    @Override
    public void updateMessage()
    {
        final String description;
        final DialogMessage message;
        final IWizardPage activePage = activePage_;
        if( activePage != null )
        {
            description = activePage.getDescription();
            message = activePage.getMessage();
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
