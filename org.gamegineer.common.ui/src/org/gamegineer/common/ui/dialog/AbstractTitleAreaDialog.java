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
    private Message message_;

    /** The title area image. */
    private Image titleAreaImage_;

    /** The title area image label. */
    private JLabel titleAreaImageLabel_;

    /** The title area title. */
    private String titleAreaTitle_;

    /** The title area title label. */
    private JLabel titleAreaTitleLabel_;


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
        descriptionImageLabel_ = null;
        descriptionLabel_ = null;
        description_ = ""; //$NON-NLS-1$
        titleAreaImage_ = null;
        titleAreaImageLabel_ = null;
        titleAreaTitle_ = ""; //$NON-NLS-1$
        titleAreaTitleLabel_ = null;
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

        titleAreaImageLabel_ = new JLabel();
        titleAreaImageLabel_.setVerticalAlignment( SwingConstants.BOTTOM );
        composite.add( titleAreaImageLabel_ );
        layout.putConstraint( SpringLayout.EAST, titleAreaImageLabel_, 0, SpringLayout.EAST, composite );
        layout.putConstraint( SpringLayout.NORTH, titleAreaImageLabel_, 0, SpringLayout.NORTH, composite );

        titleAreaTitleLabel_ = new JLabel();
        titleAreaTitleLabel_.setFont( titleAreaTitleLabel_.getFont().deriveFont( Font.BOLD ).deriveFont( titleAreaTitleLabel_.getFont().getSize() * 1.2F ) );
        composite.add( titleAreaTitleLabel_ );
        layout.putConstraint( SpringLayout.WEST, titleAreaTitleLabel_, horizontalMargin, SpringLayout.WEST, composite );
        layout.putConstraint( SpringLayout.NORTH, titleAreaTitleLabel_, verticalMargin, SpringLayout.NORTH, composite );
        layout.putConstraint( SpringLayout.EAST, titleAreaTitleLabel_, 0, SpringLayout.WEST, titleAreaImageLabel_ );

        descriptionImageLabel_ = new JLabel();
        composite.add( descriptionImageLabel_ );
        layout.putConstraint( SpringLayout.WEST, descriptionImageLabel_, 0, SpringLayout.WEST, titleAreaTitleLabel_ );
        layout.putConstraint( SpringLayout.NORTH, descriptionImageLabel_, verticalSpacing, SpringLayout.SOUTH, titleAreaTitleLabel_ );

        descriptionLabel_ = new JTextArea();
        descriptionLabel_.setFont( composite.getFont() );
        descriptionLabel_.setEditable( false );
        descriptionLabel_.setFocusable( false );
        descriptionLabel_.setLineWrap( true );
        descriptionLabel_.setOpaque( false );
        descriptionLabel_.setWrapStyleWord( true );
        composite.add( descriptionLabel_ );
        layout.putConstraint( SpringLayout.WEST, descriptionLabel_, horizontalSpacing, SpringLayout.EAST, descriptionImageLabel_ );
        layout.putConstraint( SpringLayout.NORTH, descriptionLabel_, verticalSpacing, SpringLayout.SOUTH, titleAreaTitleLabel_ );
        layout.putConstraint( SpringLayout.EAST, descriptionLabel_, 0, SpringLayout.WEST, titleAreaImageLabel_ );
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
                layout.getConstraint( SpringLayout.SOUTH, titleAreaImageLabel_ ) ) );

        layout.putConstraint( SpringLayout.SOUTH, composite, 0, SpringLayout.SOUTH, separator );

        if( titleAreaImage_ == null )
        {
            titleAreaImage_ = Activator.getDefault().getImageRegistry().getImage( ImageRegistry.DIALOG_DEFAULT_TITLE_PATH );
        }

        setTitleAreaTitle( titleAreaTitle_ );
        setTitleAreaImage( titleAreaImage_ );
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
     */
    protected final void setMessage(
        /* @Nullable */
        final Message message )
    {
        message_ = message;

        updateDescription();
    }

    /**
     * Sets the dialog title area image.
     * 
     * @param image
     *        The dialog title area image or {@code null} to clear the title
     *        area image.
     */
    protected final void setTitleAreaImage(
        /* @Nullable */
        final Image image )
    {
        titleAreaImage_ = image;

        if( titleAreaImageLabel_ != null )
        {
            titleAreaImageLabel_.setIcon( (titleAreaImage_ != null) ? new ImageIcon( titleAreaImage_ ) : null );
        }
    }

    /**
     * Sets the title to be displayed in the dialog title area.
     * 
     * @param title
     *        The dialog title area title or {@code null} to clear the title
     *        area title.
     */
    protected final void setTitleAreaTitle(
        /* @Nullable */
        final String title )
    {
        titleAreaTitle_ = (title != null) ? title : ""; //$NON-NLS-1$

        if( titleAreaTitleLabel_ != null )
        {
            titleAreaTitleLabel_.setText( title );
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
            descriptionLabel_.setText( (message_ != null) ? message_.getText() : description_ );
        }

        if( descriptionImageLabel_ != null )
        {
            String imagePath = null;
            if( message_ != null )
            {
                switch( message_.getType() )
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
