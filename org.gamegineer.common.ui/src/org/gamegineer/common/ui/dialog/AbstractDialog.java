/*
 * AbstractDialog.java
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
 * Created on Sep 11, 2010 at 2:50:26 PM.
 */

package org.gamegineer.common.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Window;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.common.internal.ui.Debug;
import org.gamegineer.common.ui.window.AbstractWindow;

/**
 * Superclass for all dialogs.
 */
@NotThreadSafe
public abstract class AbstractDialog
    extends AbstractWindow
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The dialog button bar component. */
    private Component buttonBar_;

    /**
     * The collection of dialog buttons. The key is the button identifier. The
     * value is the button.
     */
    private final Map<Integer, JButton> buttons_;

    /** The dialog content area component. */
    private Component dialogArea_;

    /** The dialog font metrics. */
    private FontMetrics fontMetrics_;

    /** The dialog title. */
    private String title_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractDialog} class.
     * 
     * @param parentShell
     *        The parent shell or {@code null} to create a top-level shell.
     */
    protected AbstractDialog(
        /* @Nullable */
        final Window parentShell )
    {
        super( parentShell );

        buttonBar_ = null;
        buttons_ = new LinkedHashMap<Integer, JButton>();
        dialogArea_ = null;
        fontMetrics_ = null;
        title_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.ui.window.AbstractWindow#close()
     */
    @Override
    public boolean close()
    {
        final boolean isClosed = super.close();
        if( isClosed )
        {
            buttons_.clear();
            buttonBar_ = null;
            dialogArea_ = null;
        }

        return isClosed;
    }

    /**
     * This implementation extends the superclass implementation and sets the
     * dialog title.
     * 
     * @see org.gamegineer.common.ui.window.AbstractWindow#configureShell(java.awt.Window)
     */
    @Override
    protected void configureShell(
        final Window shell )
    {
        super.configureShell( shell );

        final JDialog dialog = (JDialog)shell;
        dialog.setTitle( title_ );
    }

    /**
     * Converts the specified height in characters to pixels based on the
     * current font.
     * 
     * @param chars
     *        The height in characters.
     * 
     * @return The height in pixels.
     */
    protected final int convertHeightInCharsToPixels(
        final int chars )
    {
        if( fontMetrics_ == null )
        {
            Debug.getDefault().trace( Debug.OPTION_DEFAULT, "Dialog font metrics not initialized" ); //$NON-NLS-1$
            return 0;
        }

        return DialogUtils.convertHeightInCharsToPixels( fontMetrics_, chars );
    }

    /**
     * Converts the specified height in dialog units to pixels based on the
     * current font.
     * 
     * @param dlus
     *        The height in dialog units.
     * 
     * @return The height in pixels.
     */
    protected final int convertHeightInDlusToPixels(
        final int dlus )
    {
        if( fontMetrics_ == null )
        {
            Debug.getDefault().trace( Debug.OPTION_DEFAULT, "Dialog font metrics not initialized" ); //$NON-NLS-1$
            return 0;
        }

        return DialogUtils.convertHeightInDlusToPixels( fontMetrics_, dlus );
    }

    /**
     * Converts the specified width in characters to pixels based on the current
     * font.
     * 
     * @param chars
     *        The width in characters.
     * 
     * @return The width in pixels.
     */
    protected final int convertWidthInCharsToPixels(
        final int chars )
    {
        if( fontMetrics_ == null )
        {
            Debug.getDefault().trace( Debug.OPTION_DEFAULT, "Dialog font metrics not initialized" ); //$NON-NLS-1$
            return 0;
        }

        return DialogUtils.convertWidthInCharsToPixels( fontMetrics_, chars );
    }

    /**
     * Converts the specified width in dialog units to pixels based on the
     * current font.
     * 
     * @param dlus
     *        The width in dialog units.
     * 
     * @return The width in pixels.
     */
    protected final int convertWidthInDlusToPixels(
        final int dlus )
    {
        if( fontMetrics_ == null )
        {
            Debug.getDefault().trace( Debug.OPTION_DEFAULT, "Dialog font metrics not initialized" ); //$NON-NLS-1$
            return 0;
        }

        return DialogUtils.convertWidthInDlusToPixels( fontMetrics_, dlus );
    }

    /**
     * Creates a new button with the specified properties.
     * 
     * @param parent
     *        The parent container for the button; must not be {@code null}.
     * @param id
     *        The button identifier.
     * @param label
     *        The button label; may be {@code null}.
     * @param defaultButton
     *        {@code true} if the button is the default dialog button; otherwise
     *        {@code false}.
     * 
     * @return The new button; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    protected JButton createButton(
        /* @NonNull */
        final Container parent,
        final int id,
        /* @Nullable */
        final String label,
        final boolean defaultButton )
    {
        final JButton button = new JButton();
        parent.add( button );
        button.setText( label );
        setButtonLayoutData( button );

        if( defaultButton )
        {
            ((JDialog)getShell()).getRootPane().setDefaultButton( button );
        }

        buttons_.put( id, button );

        return button;
    }

    /**
     * Creates the dialog button bar component.
     * 
     * <p>
     * The default implementation creates a panel and calls
     * {@link #createButtonsForButtonBar(Container)} to populate the button bar.
     * </p>
     * 
     * @param parent
     *        The parent container for the button bar; must not be {@code null}.
     * 
     * @return The dialog button bar component; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code parent} is {@code null}.
     */
    /* @NonNull */
    protected Component createButtonBar(
        /* @NonNull */
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

        createButtonsForButtonBar( composite );

        final GroupLayout.Group hGroup = layout.createSequentialGroup();
        hGroup.addGap( 0, 0, Integer.MAX_VALUE );
        final GroupLayout.Group vGroup = layout.createParallelGroup();
        final int hGap = convertWidthInDlusToPixels( DialogConstants.HORIZONTAL_SPACING );
        int index = 0;
        for( final JButton button : buttons_.values() )
        {
            hGroup.addComponent( button, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE );
            if( index++ != buttons_.size() - 1 )
            {
                hGroup.addGap( hGap );
            }
            vGroup.addComponent( button, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE );
        }
        layout.setHorizontalGroup( hGroup );
        layout.setVerticalGroup( vGroup );

        return composite;
    }

    /**
     * Creates the buttons for the dialog button bar.
     * 
     * <p>
     * The default implementation creates OK and Cancel buttons.
     * </p>
     * 
     * @param parent
     *        The parent container for the buttons; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code parent} is {@code null}.
     */
    protected void createButtonsForButtonBar(
        /* @NonNull */
        final Container parent )
    {
        createButton( parent, DialogConstants.OK_BUTTON_ID, DialogConstants.OK_BUTTON_LABEL, true );
        createButton( parent, DialogConstants.CANCEL_BUTTON_ID, DialogConstants.CANCEL_BUTTON_LABEL, false );
    }

    /*
     * @see org.gamegineer.common.ui.window.AbstractWindow#createContents(java.awt.Container)
     */
    @Override
    protected final Component createContent(
        final Container parent )
    {
        final Container composite = new JPanel();
        parent.add( composite );

        final BorderLayout layout = new BorderLayout();
        composite.setLayout( layout );

        initializeDialogUnits( composite );

        dialogArea_ = createDialogArea( composite );
        buttonBar_ = createButtonBar( composite );

        layout.addLayoutComponent( dialogArea_, BorderLayout.CENTER );
        layout.addLayoutComponent( buttonBar_, BorderLayout.SOUTH );

        return composite;
    }

    /**
     * Creates the dialog content area component (the area above the button
     * bar).
     * 
     * <p>
     * The default implementation creates a panel with standard dialog margins
     * and a layout manager of type {@link java.awt.BorderLayout}.
     * </p>
     * 
     * @param parent
     *        The parent container for the dialog content area; must not be
     *        {@code null}.
     * 
     * @return The dialog content area component; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code parent} is {@code null}.
     */
    /* @NonNull */
    protected Component createDialogArea(
        /* @NonNull */
        final Container parent )
    {
        final JComponent composite = new JPanel();
        parent.add( composite );

        final int marginHeight = convertHeightInDlusToPixels( DialogConstants.VERTICAL_MARGIN );
        final int marginWidth = convertWidthInDlusToPixels( DialogConstants.HORIZONTAL_MARGIN );
        composite.setBorder( BorderFactory.createEmptyBorder( marginHeight, marginWidth, marginHeight, marginWidth ) );

        composite.setLayout( new BorderLayout() );

        return composite;
    }

    /*
     * @see org.gamegineer.common.ui.window.AbstractWindow#createShell(java.awt.Window)
     */
    @Override
    protected final JDialog createShell(
        final Window parent )
    {
        return new JDialog( parent, Dialog.ModalityType.APPLICATION_MODAL );
    }

    /**
     * Gets the button with the specified identifier.
     * 
     * @param id
     *        The button identifier.
     * 
     * @return The button with the specified identifier or {@code null} if no
     *         such button exists.
     */
    /* @Nullable */
    @SuppressWarnings( "boxing" )
    protected final JButton getButton(
        final int id )
    {
        return buttons_.get( id );
    }

    /**
     * Gets the dialog button bar component.
     * 
     * @return The dialog button bar component or {@code null} if the button bar
     *         has not yet been created.
     */
    /* @Nullable */
    protected final Component getButtonBar()
    {
        return buttonBar_;
    }

    /**
     * Gets the dialog content area component.
     * 
     * @return The dialog content area component or {@code null} if the content
     *         area component has not yet been created.
     */
    /* @Nullable */
    protected final Component getDialogArea()
    {
        return dialogArea_;
    }

    /**
     * Initializes the computation of horizontal and vertical dialog units based
     * on the size of the current font.
     * 
     * <p>
     * This method must be called before any of the dialog unit conversion
     * methods are called.
     * </p>
     * 
     * @param component
     *        The component from which to obtain the current font; must not be
     *        {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code component} is {@code null}.
     */
    private void initializeDialogUnits(
        /* @NonNull */
        final Component component )
    {
        fontMetrics_ = component.getFontMetrics( component.getFont() );
    }

    /**
     * Sets the standard layout data for the specified button.
     * 
     * @param button
     *        The button; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code button} is {@code null}.
     */
    protected final void setButtonLayoutData(
        /* @NonNull */
        final JButton button )
    {
        final int defaultWidth = convertWidthInDlusToPixels( DialogConstants.BUTTON_WIDTH );
        final Dimension preferredSize = button.getPreferredSize();
        preferredSize.width = Math.max( defaultWidth, preferredSize.width );
        button.setPreferredSize( preferredSize );
    }

    /**
     * Sets the dialog title.
     * 
     * @param title
     *        The dialog title or {@code null} to clear the title.
     */
    protected final void setTitle(
        /* @Nullable */
        final String title )
    {
        title_ = title;

        final JDialog dialog = (JDialog)getShell();
        if( dialog != null )
        {
            dialog.setTitle( title_ );
        }
    }
}
