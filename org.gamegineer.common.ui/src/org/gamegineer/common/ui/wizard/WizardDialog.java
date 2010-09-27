/*
 * WizardDialog.java
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
 * Created on Sep 24, 2010 at 10:10:10 PM.
 */

package org.gamegineer.common.ui.wizard;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Component;
import java.awt.Container;
import java.awt.Window;
import java.util.concurrent.ExecutionException;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.common.ui.dialog.AbstractTitleAreaDialog;
import org.gamegineer.common.ui.dialog.DialogConstants;
import org.gamegineer.common.ui.dialog.Message;
import org.gamegineer.common.ui.operation.IRunnableWithProgress;
import org.gamegineer.common.ui.window.WindowConstants;

/**
 * A dialog that hosts a wizard.
 */
@NotThreadSafe
public final class WizardDialog
    extends AbstractTitleAreaDialog
    implements IWizardContainer
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The identifier for the Back button. */
    private static final String BACK_BUTTON_ID = "back"; //$NON-NLS-1$

    /** The identifier for the Finish button. */
    private static final String FINISH_BUTTON_ID = "finish"; //$NON-NLS-1$

    /** The identifier for the Next button. */
    private static final String NEXT_BUTTON_ID = "next"; //$NON-NLS-1$

    /** The active wizard page. */
    private IWizardPage activePage_;

    /** Indicates the wizard is in the process of moving to the previous page. */
    private boolean isMovingToPreviousPage_;

    /** The container for all page content. */
    private Container pageContainer_;

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
        isMovingToPreviousPage_ = false;
        pageContainer_ = null;
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

    /*
     * @see org.gamegineer.common.ui.dialog.AbstractDialog#buttonPressed(java.lang.String)
     */
    @Override
    protected void buttonPressed(
        final String id )
    {
        if( id.equals( BACK_BUTTON_ID ) )
        {
            backPressed();
        }
        else if( id.equals( NEXT_BUTTON_ID ) )
        {
            nextPressed();
        }
        else if( id.equals( FINISH_BUTTON_ID ) )
        {
            finishPressed();
        }
        else if( id.equals( DialogConstants.CANCEL_BUTTON_ID ) )
        {
            cancelPressed();
        }
    }

    /*
     * @see org.gamegineer.common.ui.dialog.AbstractDialog#cancelPressed()
     */
    @Override
    protected void cancelPressed()
    {
        if( wizard_.performCancel() )
        {
            setReturnCode( WindowConstants.RETURN_CODE_CANCEL );
            close();
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
        final JComponent composite = new JPanel();
        parent.add( composite );

        final GroupLayout layout = new GroupLayout( composite );
        composite.setLayout( layout );

        final int marginWidth = convertWidthInDlusToPixels( DialogConstants.HORIZONTAL_MARGIN );
        final int marginHeight = convertHeightInDlusToPixels( DialogConstants.VERTICAL_MARGIN );
        composite.setBorder( BorderFactory.createEmptyBorder( marginHeight, marginWidth, marginHeight, marginWidth ) );

        composite.setFont( parent.getFont() );

        final GroupLayout.Group hGroup = layout.createSequentialGroup();
        final GroupLayout.Group vGroup = layout.createParallelGroup();
        final int hGap = convertWidthInDlusToPixels( DialogConstants.HORIZONTAL_SPACING );

        hGroup.addGap( 0, 0, Integer.MAX_VALUE );

        if( wizard_.needsPreviousAndNextButtons() )
        {
            final JButton backButton = createButton( parent, BACK_BUTTON_ID, Messages.WizardDialog_backButton_label, false );
            backButton.setMnemonic( Messages.WizardDialog_backButton_mnemonic.charAt( 0 ) );
            hGroup.addComponent( backButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE );
            vGroup.addComponent( backButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE );

            hGroup.addGap( convertWidthInDlusToPixels( 1 ) );

            final JButton nextButton = createButton( parent, NEXT_BUTTON_ID, Messages.WizardDialog_nextButton_label, false );
            nextButton.setMnemonic( Messages.WizardDialog_nextButton_mnemonic.charAt( 0 ) );
            hGroup.addComponent( nextButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE );
            vGroup.addComponent( nextButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE );

            hGroup.addGap( hGap );
        }

        final JButton finishButton = createButton( parent, FINISH_BUTTON_ID, Messages.WizardDialog_finishButton_label, true );
        finishButton.setMnemonic( Messages.WizardDialog_finishButton_mnemonic.charAt( 0 ) );
        hGroup.addComponent( finishButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE );
        vGroup.addComponent( finishButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE );

        hGroup.addGap( hGap );

        final JButton cancelButton = createButton( parent, DialogConstants.CANCEL_BUTTON_ID, DialogConstants.CANCEL_BUTTON_LABEL, false );
        hGroup.addComponent( cancelButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE );
        vGroup.addComponent( cancelButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE );

        layout.setHorizontalGroup( hGroup );
        layout.setVerticalGroup( vGroup );

        return composite;
    }

    /*
     * @see org.gamegineer.common.ui.dialog.AbstractTitleAreaDialog#createContentArea(java.awt.Container)
     */
    @Override
    protected Component createContentArea(
        final Container parent )
    {
        wizard_.addPages();

        pageContainer_ = (Container)super.createContentArea( parent );

        wizard_.create( pageContainer_ );
        for( final IWizardPage page : wizard_.getPages() )
        {
            page.setVisible( false );
        }

        return pageContainer_;
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

    /*
     * @see org.gamegineer.common.ui.operation.IRunnableContext#run(org.gamegineer.common.ui.operation.IRunnableWithProgress, boolean, boolean)
     */
    @Override
    @SuppressWarnings( "unused" )
    public void run(
        final IRunnableWithProgress runnable,
        final boolean isCancellable,
        final boolean fork )
        throws InterruptedException, ExecutionException
    {
        // TODO Auto-generated method stub
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
        updateTitleArea();
        updateButtons();
    }

    /*
     * @see org.gamegineer.common.ui.wizard.IWizardContainer#updateButtons()
     */
    @Override
    public void updateButtons()
    {
        boolean canMoveToNextPage = false;
        final boolean canFinish = wizard_.canFinish();

        final JButton backButton = getButton( BACK_BUTTON_ID );
        if( backButton != null )
        {
            backButton.setEnabled( activePage_.getPreviousPage() != null );
        }

        final JButton nextButton = getButton( NEXT_BUTTON_ID );
        if( nextButton != null )
        {
            canMoveToNextPage = activePage_.canMoveToNextPage();
            nextButton.setEnabled( canMoveToNextPage );
        }

        final JButton finishButton = getButton( FINISH_BUTTON_ID );
        assert finishButton != null;
        finishButton.setEnabled( canFinish );

        getShell().getRootPane().setDefaultButton( (canMoveToNextPage && !canFinish) ? nextButton : finishButton );
    }

    /*
     * @see org.gamegineer.common.ui.wizard.IWizardContainer#updateMessage()
     */
    @Override
    public void updateMessage()
    {
        final String description;
        final Message message;
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

    /*
     * @see org.gamegineer.common.ui.wizard.IWizardContainer#updateTitleArea()
     */
    @Override
    public void updateTitleArea()
    {
        setTitleAreaTitle( (activePage_ != null) ? activePage_.getTitle() : null );

        updateMessage();
    }
}
