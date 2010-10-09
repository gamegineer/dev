/*
 * AbstractBannerDialog.java
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
 * Superclass for all dialogs that have a banner for displaying a title and an
 * image, as well as a common area for displaying a description or a temporary
 * message.
 */
@NotThreadSafe
public abstract class AbstractBannerDialog
    extends AbstractDialog
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The minimum dialog height in dialog units. */
    private static final int MINIMUM_DIALOG_HEIGHT = 150;

    /** The minimum dialog width in dialog units. */
    private static final int MINIMUM_DIALOG_WIDTH = 350;

    /** The banner image. */
    private Image bannerImage_;

    /** The banner image label. */
    private JLabel bannerImageLabel_;

    /** The banner title. */
    private String bannerTitle_;

    /** The banner title label. */
    private JLabel bannerTitleLabel_;

    /** The dialog content area component. */
    private Component contentArea_;

    /** The description. */
    private String description_;

    /** The description image label. */
    private JLabel descriptionImageLabel_;

    /** The description label. */
    private JTextArea descriptionLabel_;

    /** The active message or {@code null} if no active message. */
    private DialogMessage message_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractBannerDialog} class.
     * 
     * @param parentShell
     *        The parent shell or {@code null} to create a top-level shell.
     */
    protected AbstractBannerDialog(
        /* @Nullable */
        final Window parentShell )
    {
        super( parentShell );

        bannerImage_ = null;
        bannerImageLabel_ = null;
        bannerTitle_ = ""; //$NON-NLS-1$
        bannerTitleLabel_ = null;
        contentArea_ = null;
        message_ = null;
        descriptionImageLabel_ = null;
        descriptionLabel_ = null;
        description_ = ""; //$NON-NLS-1$
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the dialog banner component.
     * 
     * @param parent
     *        The parent container for the dialog banner; must not be {@code
     *        null}.
     * 
     * @return The dialog banner component; never {@code null}.
     */
    /* @NonNull */
    private Component createBanner(
        /* @NonNull */
        final Container parent )
    {
        final JPanel container = new JPanel();
        parent.add( container );

        final SpringLayout layout = new SpringLayout();
        container.setLayout( layout );
        container.setBackground( Color.WHITE );

        final int horizontalMargin = convertWidthInDlusToPixels( DialogConstants.HORIZONTAL_MARGIN );
        final int verticalMargin = convertHeightInDlusToPixels( DialogConstants.VERTICAL_MARGIN );
        final int horizontalSpacing = convertWidthInDlusToPixels( DialogConstants.HORIZONTAL_SPACING );
        final int verticalSpacing = convertHeightInDlusToPixels( DialogConstants.VERTICAL_SPACING );

        bannerImageLabel_ = new JLabel();
        bannerImageLabel_.setVerticalAlignment( SwingConstants.BOTTOM );
        container.add( bannerImageLabel_ );
        layout.putConstraint( SpringLayout.EAST, bannerImageLabel_, 0, SpringLayout.EAST, container );
        layout.putConstraint( SpringLayout.NORTH, bannerImageLabel_, 0, SpringLayout.NORTH, container );

        bannerTitleLabel_ = new JLabel();
        bannerTitleLabel_.setFont( bannerTitleLabel_.getFont().deriveFont( Font.BOLD ).deriveFont( bannerTitleLabel_.getFont().getSize() * 1.2F ) );
        container.add( bannerTitleLabel_ );
        layout.putConstraint( SpringLayout.WEST, bannerTitleLabel_, horizontalMargin, SpringLayout.WEST, container );
        layout.putConstraint( SpringLayout.NORTH, bannerTitleLabel_, verticalMargin, SpringLayout.NORTH, container );
        layout.putConstraint( SpringLayout.EAST, bannerTitleLabel_, 0, SpringLayout.WEST, bannerImageLabel_ );

        descriptionImageLabel_ = new JLabel();
        container.add( descriptionImageLabel_ );
        layout.putConstraint( SpringLayout.WEST, descriptionImageLabel_, 0, SpringLayout.WEST, bannerTitleLabel_ );
        layout.putConstraint( SpringLayout.NORTH, descriptionImageLabel_, verticalSpacing, SpringLayout.SOUTH, bannerTitleLabel_ );

        descriptionLabel_ = new JTextArea();
        descriptionLabel_.setFont( container.getFont() );
        descriptionLabel_.setEditable( false );
        descriptionLabel_.setFocusable( false );
        descriptionLabel_.setLineWrap( true );
        descriptionLabel_.setOpaque( false );
        descriptionLabel_.setWrapStyleWord( true );
        container.add( descriptionLabel_ );
        layout.putConstraint( SpringLayout.WEST, descriptionLabel_, horizontalSpacing, SpringLayout.EAST, descriptionImageLabel_ );
        layout.putConstraint( SpringLayout.NORTH, descriptionLabel_, verticalSpacing, SpringLayout.SOUTH, bannerTitleLabel_ );
        layout.putConstraint( SpringLayout.EAST, descriptionLabel_, 0, SpringLayout.WEST, bannerImageLabel_ );
        layout.getConstraints( descriptionLabel_ ).setHeight( Spring.constant( convertHeightInCharsToPixels( 2 ) ) );

        final JSeparator separator = new JSeparator();
        container.add( separator );
        layout.putConstraint( SpringLayout.WEST, separator, 0, SpringLayout.WEST, container );
        layout.putConstraint( SpringLayout.EAST, separator, 0, SpringLayout.EAST, container );
        layout.getConstraints( separator ).setConstraint( SpringLayout.NORTH, //
            Spring.max( //
                Spring.sum( //
                    layout.getConstraint( SpringLayout.SOUTH, descriptionLabel_ ), //
                    Spring.constant( verticalSpacing ) ), //
                layout.getConstraint( SpringLayout.SOUTH, bannerImageLabel_ ) ) );

        layout.putConstraint( SpringLayout.SOUTH, container, 0, SpringLayout.SOUTH, separator );

        if( bannerImage_ == null )
        {
            bannerImage_ = Activator.getDefault().getImageRegistry().getImage( ImageRegistry.DIALOG_DEFAULT_TITLE_PATH );
        }

        setBannerTitle( bannerTitle_ );
        setBannerImage( bannerImage_ );
        setDescription( description_ );

        return container;
    }

    /**
     * Creates the dialog content area component (the area above the button bar
     * and below the banner).
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
        final JPanel container = new JPanel();
        parent.add( container );

        final int marginHeight = convertHeightInDlusToPixels( DialogConstants.VERTICAL_MARGIN );
        final int marginWidth = convertWidthInDlusToPixels( DialogConstants.HORIZONTAL_MARGIN );
        container.setBorder( BorderFactory.createEmptyBorder( marginHeight, marginWidth, marginHeight, marginWidth ) );

        container.setLayout( new BorderLayout() );

        return container;
    }

    /*
     * @see org.gamegineer.common.ui.dialog.AbstractDialog#createDialogArea(java.awt.Container)
     */
    @Override
    protected final Component createDialogArea(
        final Container parent )
    {
        final Container container = new JPanel();
        parent.add( container );

        final BorderLayout layout = new BorderLayout();
        container.setLayout( layout );

        final Component banner = createBanner( container );
        contentArea_ = createContentArea( container );

        layout.addLayoutComponent( banner, BorderLayout.NORTH );
        layout.addLayoutComponent( contentArea_, BorderLayout.CENTER );

        return container;
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
     * Sets the dialog banner image.
     * 
     * @param image
     *        The dialog banner image or {@code null} to clear the banner image.
     */
    protected final void setBannerImage(
        /* @Nullable */
        final Image image )
    {
        bannerImage_ = image;

        if( bannerImageLabel_ != null )
        {
            bannerImageLabel_.setIcon( (bannerImage_ != null) ? new ImageIcon( bannerImage_ ) : null );
        }
    }

    /**
     * Sets the title to be displayed in the dialog banner.
     * 
     * @param title
     *        The dialog banner title or {@code null} to clear the banner title.
     */
    protected final void setBannerTitle(
        /* @Nullable */
        final String title )
    {
        bannerTitle_ = (title != null) ? title : ""; //$NON-NLS-1$

        if( bannerTitleLabel_ != null )
        {
            bannerTitleLabel_.setText( title );
        }
    }

    /**
     * Sets the description to be displayed in the dialog banner.
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
        final DialogMessage message )
    {
        message_ = message;

        updateDescription();
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
