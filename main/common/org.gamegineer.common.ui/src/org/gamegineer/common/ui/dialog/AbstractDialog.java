/*
 * AbstractDialog.java
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
 * Created on Sep 11, 2010 at 2:50:26 PM.
 */

package org.gamegineer.common.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.internal.ui.Debug;
import org.gamegineer.common.ui.layout.PixelConverter;
import org.gamegineer.common.ui.window.AbstractWindow;
import org.gamegineer.common.ui.window.WindowConstants;

/**
 * Superclass for all dialogs.
 */
@NotThreadSafe
public abstract class AbstractDialog
    extends AbstractWindow<JDialog>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The dialog button bar component. */
    @Nullable
    private Component buttonBar_;

    /**
     * The collection of dialog buttons. The key is the button identifier. The
     * value is the button.
     */
    private final Map<String, JButton> buttons_;

    /** The dialog content area component. */
    @Nullable
    private Component dialogArea_;

    /** The dialog pixel converter. */
    @Nullable
    private PixelConverter pixelConverter_;

    /** The dialog title. */
    @Nullable
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
        @Nullable
        final Window parentShell )
    {
        super( parentShell );

        buttonBar_ = null;
        buttons_ = new LinkedHashMap<>();
        dialogArea_ = null;
        pixelConverter_ = null;
        title_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Invoked when a dialog button is pressed.
     * 
     * <p>
     * This implementation invokes the {@link #okPressed()} method if the OK
     * button is pressed or the {@link #cancelPressed()} method if the Cancel
     * button is pressed.
     * </p>
     * 
     * @param id
     *        The identifier of the button that was pressed; must not be
     *        {@code null}.
     */
    protected void buttonPressed(
        final String id )
    {
        if( id.equals( DialogConstants.OK_BUTTON_ID ) )
        {
            okPressed();
        }
        else if( id.equals( DialogConstants.CANCEL_BUTTON_ID ) )
        {
            cancelPressed();
        }
    }

    /**
     * Invoked when the Cancel button is pressed.
     * 
     * <p>
     * This implementation sets the dialog return code to
     * {@link WindowConstants#RETURN_CODE_CANCEL} and closes the dialog.
     * </p>
     */
    protected void cancelPressed()
    {
        setReturnCode( WindowConstants.RETURN_CODE_CANCEL );
        close();
    }

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
        final JDialog shell )
    {
        super.configureShell( shell );

        shell.setTitle( title_ );

        shell.setDefaultCloseOperation( JDialog.DO_NOTHING_ON_CLOSE );
        shell.addWindowListener( new WindowAdapter()
        {
            @Override
            public void windowClosing(
                @Nullable
                @SuppressWarnings( "unused" )
                final WindowEvent event )
            {
                cancelPressed();
            }
        } );
    }

    /**
     * Converts the specified height in characters to pixels.
     * 
     * @param chars
     *        The height in characters.
     * 
     * @return The height in pixels.
     */
    protected final int convertHeightInCharsToPixels(
        final int chars )
    {
        final PixelConverter pixelConverter = pixelConverter_;
        if( pixelConverter == null )
        {
            Debug.getDefault().trace( Debug.OPTION_DEFAULT, "Dialog pixel converter not initialized" ); //$NON-NLS-1$
            return 0;
        }

        return pixelConverter.convertHeightInCharsToPixels( chars );
    }

    /**
     * Converts the specified height in dialog units to pixels.
     * 
     * @param dlus
     *        The height in dialog units.
     * 
     * @return The height in pixels.
     */
    protected final int convertHeightInDlusToPixels(
        final int dlus )
    {
        final PixelConverter pixelConverter = pixelConverter_;
        if( pixelConverter == null )
        {
            Debug.getDefault().trace( Debug.OPTION_DEFAULT, "Dialog pixel converter not initialized" ); //$NON-NLS-1$
            return 0;
        }

        return pixelConverter.convertHeightInDlusToPixels( dlus );
    }

    /**
     * Converts the specified width in characters to pixels.
     * 
     * @param chars
     *        The width in characters.
     * 
     * @return The width in pixels.
     */
    protected final int convertWidthInCharsToPixels(
        final int chars )
    {
        final PixelConverter pixelConverter = pixelConverter_;
        if( pixelConverter == null )
        {
            Debug.getDefault().trace( Debug.OPTION_DEFAULT, "Dialog pixel converter not initialized" ); //$NON-NLS-1$
            return 0;
        }

        return pixelConverter.convertWidthInCharsToPixels( chars );
    }

    /**
     * Converts the specified width in dialog units to pixels.
     * 
     * @param dlus
     *        The width in dialog units.
     * 
     * @return The width in pixels.
     */
    protected final int convertWidthInDlusToPixels(
        final int dlus )
    {
        final PixelConverter pixelConverter = pixelConverter_;
        if( pixelConverter == null )
        {
            Debug.getDefault().trace( Debug.OPTION_DEFAULT, "Dialog pixel converter not initialized" ); //$NON-NLS-1$
            return 0;
        }

        return pixelConverter.convertWidthInDlusToPixels( dlus );
    }

    /**
     * Creates a new button with the specified properties.
     * 
     * @param parent
     *        The parent container for the button; must not be {@code null}.
     * @param id
     *        The button identifier; must not be {@code null}.
     * @param label
     *        The button label; may be {@code null}.
     * @param defaultButton
     *        {@code true} if the button is the default dialog button; otherwise
     *        {@code false}.
     * 
     * @return The new button; never {@code null}.
     */
    protected final JButton createButton(
        final Container parent,
        final String id,
        @Nullable
        final String label,
        final boolean defaultButton )
    {
        final JButton button = new JButton();
        parent.add( button );
        button.setActionCommand( id );
        button.setText( label );
        button.addActionListener( new ActionListener()
        {
            @Override
            public void actionPerformed(
                @Nullable
                final ActionEvent event )
            {
                assert event != null;

                final String buttonId = event.getActionCommand();
                if( buttonId != null )
                {
                    buttonPressed( buttonId );
                }
            }
        } );
        setButtonLayoutData( button );

        if( defaultButton )
        {
            final JDialog shell = getShell();
            assert shell != null;
            shell.getRootPane().setDefaultButton( button );
        }

        buttons_.put( id, button );

        return button;
    }

    /**
     * Creates the dialog button bar component.
     * 
     * <p>
     * This implementation creates a panel and calls
     * {@link #createButtonsForButtonBar(Container)} to populate the button bar.
     * </p>
     * 
     * @param parent
     *        The parent container for the button bar; must not be {@code null}.
     * 
     * @return The dialog button bar component; never {@code null}.
     */
    protected Component createButtonBar(
        final Container parent )
    {
        final JPanel container = new JPanel();
        parent.add( container );

        final GroupLayout layout = new GroupLayout( container );
        container.setLayout( layout );

        final int marginWidth = convertWidthInDlusToPixels( DialogConstants.HORIZONTAL_MARGIN );
        final int marginHeight = convertHeightInDlusToPixels( DialogConstants.VERTICAL_MARGIN );
        container.setBorder( BorderFactory.createEmptyBorder( marginHeight, marginWidth, marginHeight, marginWidth ) );

        container.setFont( parent.getFont() );

        createButtonsForButtonBar( container );

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

        return container;
    }

    /**
     * Creates the buttons for the dialog button bar.
     * 
     * <p>
     * This implementation creates OK and Cancel buttons.
     * </p>
     * 
     * @param parent
     *        The parent container for the buttons; must not be {@code null}.
     */
    protected void createButtonsForButtonBar(
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
        @Nullable
        final Container parent )
    {
        assert parent != null;

        final Container container = new JPanel();
        parent.add( container );

        final BorderLayout layout = new BorderLayout();
        container.setLayout( layout );

        pixelConverter_ = new PixelConverter( container );

        dialogArea_ = createDialogArea( container );
        buttonBar_ = createButtonBar( container );

        layout.addLayoutComponent( dialogArea_, BorderLayout.CENTER );
        layout.addLayoutComponent( buttonBar_, BorderLayout.SOUTH );

        return container;
    }

    /**
     * Creates the dialog content area component (the area above the button
     * bar).
     * 
     * <p>
     * This implementation creates a panel with standard dialog margins and a
     * layout manager of type {@link BorderLayout}.
     * </p>
     * 
     * @param parent
     *        The parent container for the dialog content area; must not be
     *        {@code null}.
     * 
     * @return The dialog content area component; never {@code null}.
     */
    protected Component createDialogArea(
        final Container parent )
    {
        final JPanel container = new JPanel();
        parent.add( container );

        final int marginHeight = convertHeightInDlusToPixels( DialogConstants.VERTICAL_MARGIN );
        final int marginWidth = convertWidthInDlusToPixels( DialogConstants.HORIZONTAL_MARGIN );
        container.setBorder( BorderFactory.createEmptyBorder( marginHeight, marginWidth, marginHeight, marginWidth ) );

        container.setLayout( new BorderLayout() );

        return container;
    }

    /*
     * @see org.gamegineer.common.ui.window.AbstractWindow#createShell(java.awt.Window)
     */
    @Override
    protected final JDialog createShell(
        @Nullable
        final Window parent )
    {
        assert parent != null;

        final ActionListener escapeKeyListener = new ActionListener()
        {
            @Override
            public void actionPerformed(
                @Nullable
                @SuppressWarnings( "unused" )
                final ActionEvent event )
            {
                cancelPressed();
            }
        };

        return new JDialog( parent, Dialog.ModalityType.APPLICATION_MODAL )
        {
            private static final long serialVersionUID = -1569267715967524723L;

            @Override
            protected JRootPane createRootPane()
            {
                @SuppressWarnings( "hiding" )
                final JRootPane rootPane = super.createRootPane();
                rootPane.registerKeyboardAction( escapeKeyListener, KeyStroke.getKeyStroke( KeyEvent.VK_ESCAPE, 0 ), JComponent.WHEN_IN_FOCUSED_WINDOW );
                return rootPane;
            }
        };
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
    @Nullable
    protected final JButton getButton(
        final String id )
    {
        return buttons_.get( id );
    }

    /**
     * Gets the dialog button bar component.
     * 
     * @return The dialog button bar component or {@code null} if the button bar
     *         has not yet been created.
     */
    @Nullable
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
    @Nullable
    protected final Component getDialogArea()
    {
        return dialogArea_;
    }

    /**
     * Invoked when the OK button is pressed.
     * 
     * <p>
     * This implementation sets the dialog return code to
     * {@link WindowConstants#RETURN_CODE_OK} and closes the dialog.
     * </p>
     */
    protected void okPressed()
    {
        setReturnCode( WindowConstants.RETURN_CODE_OK );
        close();
    }

    /**
     * Sets the standard layout data for the specified button.
     * 
     * @param button
     *        The button; must not be {@code null}.
     */
    protected final void setButtonLayoutData(
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
        @Nullable
        final String title )
    {
        title_ = title;

        final JDialog shell = getShell();
        if( shell != null )
        {
            shell.setTitle( title_ );
        }
    }
}
