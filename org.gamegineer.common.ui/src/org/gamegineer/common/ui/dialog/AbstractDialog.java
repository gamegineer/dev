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

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FontMetrics;
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
import org.gamegineer.common.internal.ui.Debug;
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
    private Component buttonBar_;

    /**
     * The collection of dialog buttons. The key is the button identifier. The
     * value is the button.
     */
    private final Map<String, JButton> buttons_;

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
        buttons_ = new LinkedHashMap<String, JButton>();
        dialogArea_ = null;
        fontMetrics_ = null;
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
     *        The identifier of the button that was pressed; must not be {@code
     *        null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    protected void buttonPressed(
        /* @NonNull */
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
                @SuppressWarnings( "unused" )
                final WindowEvent event )
            {
                cancelPressed();
            }
        } );
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
     *        The button identifier; must not be {@code null}.
     * @param label
     *        The button label; may be {@code null}.
     * @param defaultButton
     *        {@code true} if the button is the default dialog button; otherwise
     *        {@code false}.
     * 
     * @return The new button; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code parent} or {@code id} is {@code null}.
     */
    /* @NonNull */
    protected final JButton createButton(
        /* @NonNull */
        final Container parent,
        /* @NonNull */
        final String id,
        /* @Nullable */
        final String label,
        final boolean defaultButton )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        final JButton button = new JButton();
        parent.add( button );
        button.setActionCommand( id );
        button.setText( label );
        button.addActionListener( new ActionListener()
        {
            @Override
            public void actionPerformed(
                final ActionEvent event )
            {
                buttonPressed( event.getActionCommand() );
            }
        } );
        setButtonLayoutData( button );

        if( defaultButton )
        {
            getShell().getRootPane().setDefaultButton( button );
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
     * 
     * @throws java.lang.NullPointerException
     *         If {@code parent} is {@code null}.
     */
    /* @NonNull */
    protected Component createButtonBar(
        /* @NonNull */
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
        final Container container = new JPanel();
        parent.add( container );

        final BorderLayout layout = new BorderLayout();
        container.setLayout( layout );

        initializeDialogUnits( container );

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
     * layout manager of type {@link java.awt.BorderLayout}.
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
        final Window parent )
    {
        final ActionListener escapeKeyListener = new ActionListener()
        {
            @Override
            public void actionPerformed(
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
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    /* @Nullable */
    protected final JButton getButton(
        /* @NonNull */
        final String id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

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

        final JDialog shell = getShell();
        if( shell != null )
        {
            shell.setTitle( title_ );
        }
    }
}
