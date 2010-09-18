/*
 * TitleAreaDialog.java
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
import java.awt.Window;
import java.net.URL;
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

/**
 * A dialog that has a title area for displaying a title and an image, as well
 * as a common area for displaying a description, a message, or an error
 * message.
 */
@NotThreadSafe
public class TitleAreaDialog
    extends AbstractDialog
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The minimum dialog height in dialog units. */
    private static final int MINIMUM_DIALOG_HEIGHT = 150;

    /** The minimum dialog width in dialog units. */
    private static final int MINIMUM_DIALOG_WIDTH = 350;

    /** The image label. */
    private JLabel imageLabel_;

    /** The message image label. */
    private JLabel messageImageLabel_;

    /** The message label. */
    private JTextArea messageLabel_;

    /** The title label. */
    private JLabel titleLabel_;

    /** The dialog work area component. */
    private Component workArea_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TitleAreaDialog} class.
     * 
     * @param parentShell
     *        The parent shell or {@code null} to create a top-level shell.
     */
    public TitleAreaDialog(
        /* @Nullable */
        final Window parentShell )
    {
        super( parentShell );

        imageLabel_ = null;
        messageImageLabel_ = null;
        messageLabel_ = null;
        titleLabel_ = null;
        workArea_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

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
        workArea_ = createWorkArea( composite );

        layout.addLayoutComponent( titleArea, BorderLayout.NORTH );
        layout.addLayoutComponent( workArea_, BorderLayout.CENTER );

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

        titleLabel_ = new JLabel( "title" );
        titleLabel_.setFont( titleLabel_.getFont().deriveFont( Font.BOLD ).deriveFont( titleLabel_.getFont().getSize() * 1.2F ) );
        composite.add( titleLabel_ );
        layout.putConstraint( SpringLayout.WEST, titleLabel_, horizontalMargin, SpringLayout.WEST, composite );
        layout.putConstraint( SpringLayout.NORTH, titleLabel_, verticalMargin, SpringLayout.NORTH, composite );

        imageLabel_ = new JLabel();
        final URL url = Activator.getDefault().getBundleContext().getBundle().getEntry( "/icons/default-title-image.png" ); //$NON-NLS-1$
        imageLabel_.setIcon( new ImageIcon( url ) );
        imageLabel_.setVerticalAlignment( SwingConstants.BOTTOM );
        composite.add( imageLabel_ );
        layout.putConstraint( SpringLayout.EAST, imageLabel_, 0, SpringLayout.EAST, composite );
        layout.putConstraint( SpringLayout.NORTH, imageLabel_, 0, SpringLayout.NORTH, composite );

        messageImageLabel_ = new JLabel();
        final URL url2 = Activator.getDefault().getBundleContext().getBundle().getEntry( "/icons/message-error.png" ); //$NON-NLS-1$
        messageImageLabel_.setIcon( new ImageIcon( url2 ) );
        composite.add( messageImageLabel_ );
        layout.putConstraint( SpringLayout.WEST, messageImageLabel_, 0, SpringLayout.WEST, titleLabel_ );
        layout.putConstraint( SpringLayout.NORTH, messageImageLabel_, verticalSpacing, SpringLayout.SOUTH, titleLabel_ );

        messageLabel_ = new JTextArea( "Message line 1\nMessage line 2" );
        messageLabel_.setFont( composite.getFont() );
        messageLabel_.setEditable( false );
        messageLabel_.setFocusable( false );
        messageLabel_.setLineWrap( true );
        messageLabel_.setOpaque( false );
        messageLabel_.setWrapStyleWord( true );
        composite.add( messageLabel_ );
        layout.putConstraint( SpringLayout.WEST, messageLabel_, horizontalSpacing, SpringLayout.EAST, messageImageLabel_ );
        layout.putConstraint( SpringLayout.NORTH, messageLabel_, verticalSpacing, SpringLayout.SOUTH, titleLabel_ );
        layout.putConstraint( SpringLayout.EAST, messageLabel_, 0, SpringLayout.WEST, imageLabel_ );

        final JSeparator separator = new JSeparator();
        composite.add( separator );
        layout.putConstraint( SpringLayout.WEST, separator, 0, SpringLayout.WEST, composite );
        layout.putConstraint( SpringLayout.EAST, separator, 0, SpringLayout.EAST, composite );
        layout.getConstraints( separator ).setConstraint( SpringLayout.NORTH, //
            Spring.max( //
                Spring.sum( //
                    layout.getConstraint( SpringLayout.SOUTH, messageLabel_ ), //
                    Spring.constant( verticalSpacing ) ), //
                layout.getConstraint( SpringLayout.SOUTH, imageLabel_ ) ) );

        layout.putConstraint( SpringLayout.SOUTH, composite, 0, SpringLayout.SOUTH, separator );

        return composite;
    }

    /**
     * Creates the dialog work area component (the area above the button bar and
     * below the title area).
     * 
     * <p>
     * The default implementation creates a panel with standard dialog margins
     * and a layout manager of type {@link java.awt.BorderLayout}.
     * </p>
     * 
     * @param parent
     *        The parent container for the dialog work area; must not be {@code
     *        null}.
     * 
     * @return The dialog work area component; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code parent} is {@code null}.
     */
    /* @NonNull */
    protected Component createWorkArea(
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
}
