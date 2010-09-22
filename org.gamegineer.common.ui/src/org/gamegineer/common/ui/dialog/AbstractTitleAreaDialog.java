/*
 * AbstractTitleAreaDialog.java
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
 * Created on Sep 11, 2010 at 1:36:06 PM.
 */

package org.gamegineer.common.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Window;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.common.internal.ui.Activator;
import org.gamegineer.common.internal.ui.ImageRegistry;

/**
 * Superclass for all dialogs that have a title area for displaying a title and
 * an image, as well as a common area for displaying a description or a
 * temporary message.
 */
@NotThreadSafe
public abstract class AbstractTitleAreaDialog
    extends AbstractDialog
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The minimum dialog height in dialog units. */
    private static final int MINIMUM_DIALOG_HEIGHT = 150;

    /** The minimum dialog width in dialog units. */
    private static final int MINIMUM_DIALOG_WIDTH = 350;

    /** The dialog content area component. */
    private Component contentArea_;

    /** The description. */
    private String description_;

    /** The description image label. */
    private JLabel descriptionImageLabel_;

    /** The description label. */
    private JTextArea descriptionLabel_;

    /** The active message or {@code null} if no active message. */
    private String message_;

    /** The active message type or {@code null} if no active message. */
    private MessageType messageType_;

    /** The title. */
    private String title_;

    /** The title image. */
    private Image titleImage_;

    /** The title image label. */
    private JLabel titleImageLabel_;

    /** The title label. */
    private JLabel titleLabel_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractTitleAreaDialog} class.
     * 
     * @param parentShell
     *        The parent shell or {@code null} to create a top-level shell.
     */
    protected AbstractTitleAreaDialog(
        /* @Nullable */
        final Window parentShell )
    {
        super( parentShell );

        contentArea_ = null;
        message_ = null;
        messageType_ = null;
        descriptionImageLabel_ = null;
        descriptionLabel_ = null;
        description_ = ""; //$NON-NLS-1$
        title_ = ""; //$NON-NLS-1$
        titleImage_ = null;
        titleImageLabel_ = null;
        titleLabel_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the dialog content area component (the area above the button bar
     * and below the title area).
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
    protected Component createContentArea(
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
     * @see org.gamegineer.common.ui.dialog.AbstractDialog#createDialogArea(java.awt.Container)
     */
    @Override
    protected final Component createDialogArea(
        final Container parent )
    {
        final Container composite = new JPanel();
        parent.add( composite );

        final BorderLayout layout = new BorderLayout();
        composite.setLayout( layout );

        final Component titleArea = createTitleArea( composite );
        contentArea_ = createContentArea( composite );

        layout.addLayoutComponent( titleArea, BorderLayout.NORTH );
        layout.addLayoutComponent( contentArea_, BorderLayout.CENTER );

        return composite;
    }

    /**
     * Creates the dialog title area component.
     * 
     * @param parent
     *        The parent container for the dialog title area; must not be
     *        {@code null}.
     * 
     * @return The dialog title area component; never {@code null}.
     */
    /* @NonNull */
    private Component createTitleArea(
        /* @NonNull */
        final Container parent )
    {
        final JPanel composite = new JPanel();
        parent.add( composite );

        final SpringLayout layout = new SpringLayout();
        composite.setLayout( layout );
        composite.setBackground( Color.WHITE );

        final int horizontalMargin = convertWidthInDlusToPixels( DialogConstants.HORIZONTAL_MARGIN );
        final int verticalMargin = convertHeightInDlusToPixels( DialogConstants.VERTICAL_MARGIN );
        final int horizontalSpacing = convertWidthInDlusToPixels( DialogConstants.HORIZONTAL_SPACING );
        final int verticalSpacing = convertHeightInDlusToPixels( DialogConstants.VERTICAL_SPACING );

        titleImageLabel_ = new JLabel();
        titleImageLabel_.setVerticalAlignment( SwingConstants.BOTTOM );
        composite.add( titleImageLabel_ );
        layout.putConstraint( SpringLayout.EAST, titleImageLabel_, 0, SpringLayout.EAST, composite );
        layout.putConstraint( SpringLayout.NORTH, titleImageLabel_, 0, SpringLayout.NORTH, composite );

        titleLabel_ = new JLabel();
        titleLabel_.setFont( titleLabel_.getFont().deriveFont( Font.BOLD ).deriveFont( titleLabel_.getFont().getSize() * 1.2F ) );
        composite.add( titleLabel_ );
        layout.putConstraint( SpringLayout.WEST, titleLabel_, horizontalMargin, SpringLayout.WEST, composite );
        layout.putConstraint( SpringLayout.NORTH, titleLabel_, verticalMargin, SpringLayout.NORTH, composite );
        layout.putConstraint( SpringLayout.EAST, titleLabel_, 0, SpringLayout.WEST, titleImageLabel_ );

        descriptionImageLabel_ = new JLabel();
        composite.add( descriptionImageLabel_ );
        layout.putConstraint( SpringLayout.WEST, descriptionImageLabel_, 0, SpringLayout.WEST, titleLabel_ );
        layout.putConstraint( SpringLayout.NORTH, descriptionImageLabel_, verticalSpacing, SpringLayout.SOUTH, titleLabel_ );

        descriptionLabel_ = new JTextArea();
        descriptionLabel_.setFont( composite.getFont() );
        descriptionLabel_.setEditable( false );
        descriptionLabel_.setFocusable( false );
        descriptionLabel_.setLineWrap( true );
        descriptionLabel_.setOpaque( false );
        descriptionLabel_.setWrapStyleWord( true );
        composite.add( descriptionLabel_ );
        layout.putConstraint( SpringLayout.WEST, descriptionLabel_, horizontalSpacing, SpringLayout.EAST, descriptionImageLabel_ );
        layout.putConstraint( SpringLayout.NORTH, descriptionLabel_, verticalSpacing, SpringLayout.SOUTH, titleLabel_ );
        layout.putConstraint( SpringLayout.EAST, descriptionLabel_, 0, SpringLayout.WEST, titleImageLabel_ );
        layout.getConstraints( descriptionLabel_ ).setHeight( Spring.constant( convertHeightInCharsToPixels( 2 ) ) );

        final JSeparator separator = new JSeparator();
        composite.add( separator );
        layout.putConstraint( SpringLayout.WEST, separator, 0, SpringLayout.WEST, composite );
        layout.putConstraint( SpringLayout.EAST, separator, 0, SpringLayout.EAST, composite );
        layout.getConstraints( separator ).setConstraint( SpringLayout.NORTH, //
            Spring.max( //
                Spring.sum( //
                    layout.getConstraint( SpringLayout.SOUTH, descriptionLabel_ ), //
                    Spring.constant( verticalSpacing ) ), //
                layout.getConstraint( SpringLayout.SOUTH, titleImageLabel_ ) ) );

        layout.putConstraint( SpringLayout.SOUTH, composite, 0, SpringLayout.SOUTH, separator );

        if( titleImage_ == null )
        {
            titleImage_ = Activator.getDefault().getImageRegistry().getImage( ImageRegistry.DIALOG_DEFAULT_TITLE_PATH );
        }

        setTitle( title_ );
        setTitleImage( titleImage_ );
        setDescription( description_ );

        return composite;
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

    /**
     * Sets the description to be displayed in the dialog title area.
     * 
     * @param description
     *        The dialog description or {@code null} to clear the description.
     */
    protected final void setDescription(
        /* @Nullable */
        final String description )
    {
        description_ = (description != null) ? description : ""; //$NON-NLS-1$

        updateDescription();
    }

    /**
     * Sets the dialog message.
     * 
     * <p>
     * The dialog message temporarily replaces the dialog description. The
     * dialog description is restored automatically when the dialog message is
     * cleared.
     * </p>
     * 
     * @param message
     *        The dialog message or {@code null} to clear the message.
     * @param messageType
     *        The dialog message type or {@code null} to clear the message.
     */
    protected final void setMessage(
        /* @Nullable */
        final String message,
        /* @Nullable */
        final MessageType messageType )
    {
        if( (message != null) && (messageType != null) )
        {
            message_ = message;
            messageType_ = messageType;
        }
        else
        {
            message_ = null;
            messageType_ = null;
        }

        updateDescription();
    }

    /**
     * Sets the title to be displayed in the dialog title area.
     * 
     * @param title
     *        The dialog title or {@code null} to clear the title.
     */
    protected final void setTitle(
        /* @Nullable */
        final String title )
    {
        title_ = (title != null) ? title : ""; //$NON-NLS-1$

        if( titleLabel_ != null )
        {
            titleLabel_.setText( title );
        }
    }

    /**
     * Sets the dialog title image.
     * 
     * @param titleImage
     *        The dialog title image or {@code null} to clear the title image.
     */
    protected final void setTitleImage(
        /* @Nullable */
        final Image titleImage )
    {
        titleImage_ = titleImage;

        if( titleImageLabel_ != null )
        {
            titleImageLabel_.setIcon( (titleImage_ != null) ? new ImageIcon( titleImage_ ) : null );
        }
    }

    /**
     * Updates the dialog description.
     */
    @SuppressWarnings( "incomplete-switch" )
    private void updateDescription()
    {
        if( descriptionLabel_ != null )
        {
            descriptionLabel_.setText( (message_ != null) ? message_ : description_ );
        }

        if( descriptionImageLabel_ != null )
        {
            String imagePath = null;
            if( messageType_ != null )
            {
                switch( messageType_ )
                {
                    case INFORMATION:
                        imagePath = ImageRegistry.DIALOG_INFORMATION_MESSAGE_PATH;
                        break;

                    case WARNING:
                        imagePath = ImageRegistry.DIALOG_WARNING_MESSAGE_PATH;
                        break;

                    case ERROR:
                        imagePath = ImageRegistry.DIALOG_ERROR_MESSAGE_PATH;
                        break;
                }
            }

            final Image image = (imagePath != null) ? Activator.getDefault().getImageRegistry().getImage( imagePath ) : null;
            descriptionImageLabel_.setIcon( (image != null) ? new ImageIcon( image ) : null );
        }
    }
}
