/*
 * AbstractDialogPage.java
 * Copyright 2008-2012 Gamegineer contributors and others.
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
 * Created on Sep 21, 2010 at 10:32:36 PM.
 */

package org.gamegineer.common.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Window;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.common.internal.ui.Debug;
import org.gamegineer.common.ui.layout.PixelConverter;

/**
 * Superclass for all implementations of
 * {@link org.gamegineer.common.ui.dialog.IDialogPage}.
 */
@NotThreadSafe
public abstract class AbstractDialogPage
    implements IDialogPage
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The dialog page content. */
    private Component content_;

    /** The dialog page description. */
    private String description_;

    /** The dialog page message. */
    private DialogMessage message_;

    /** The dialog page pixel converter. */
    private PixelConverter pixelConverter_;

    /** The dialog page title. */
    private String title_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractDialogPage} class.
     */
    protected AbstractDialogPage()
    {
        content_ = null;
        description_ = null;
        message_ = null;
        pixelConverter_ = null;
        title_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

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
        if( pixelConverter_ == null )
        {
            Debug.getDefault().trace( Debug.OPTION_DEFAULT, "Dialog page pixel converter not initialized" ); //$NON-NLS-1$
            return 0;
        }

        return pixelConverter_.convertHeightInCharsToPixels( chars );
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
        if( pixelConverter_ == null )
        {
            Debug.getDefault().trace( Debug.OPTION_DEFAULT, "Dialog page pixel converter not initialized" ); //$NON-NLS-1$
            return 0;
        }

        return pixelConverter_.convertHeightInDlusToPixels( dlus );
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
        if( pixelConverter_ == null )
        {
            Debug.getDefault().trace( Debug.OPTION_DEFAULT, "Dialog page pixel converter not initialized" ); //$NON-NLS-1$
            return 0;
        }

        return pixelConverter_.convertWidthInCharsToPixels( chars );
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
        if( pixelConverter_ == null )
        {
            Debug.getDefault().trace( Debug.OPTION_DEFAULT, "Dialog page pixel converter not initialized" ); //$NON-NLS-1$
            return 0;
        }

        return pixelConverter_.convertWidthInDlusToPixels( dlus );
    }

    /*
     * @see org.gamegineer.common.ui.dialog.IDialogPage#create(java.awt.Container)
     */
    @Override
    public final void create(
        final Container parent )
    {
        pixelConverter_ = new PixelConverter( parent );

        content_ = createContent( parent );
    }

    /**
     * Creates the dialog page content area component.
     * 
     * <p>
     * The default implementation creates a panel with no margins and a layout
     * manager of type {@link java.awt.BorderLayout}.
     * </p>
     * 
     * @param parent
     *        The parent container for the dialog page content area; must not be
     *        {@code null}.
     * 
     * @return The dialog page content area component; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code parent} is {@code null}.
     */
    /* @NonNull */
    protected Component createContent(
        /* @NonNull */
        final Container parent )
    {
        final JPanel container = new JPanel();
        parent.add( container );

        container.setLayout( new BorderLayout() );

        return container;
    }

    /*
     * @see org.gamegineer.common.ui.dialog.IDialogPage#dispose()
     */
    @Override
    public void dispose()
    {
        // do nothing
    }

    /*
     * @see org.gamegineer.common.ui.dialog.IDialogPage#getContent()
     */
    @Override
    public final Component getContent()
    {
        return content_;
    }

    /*
     * @see org.gamegineer.common.ui.dialog.IDialogPage#getDescription()
     */
    @Override
    public final String getDescription()
    {
        return description_;
    }

    /*
     * @see org.gamegineer.common.ui.dialog.IDialogPage#getMessage()
     */
    @Override
    public final DialogMessage getMessage()
    {
        return message_;
    }

    /*
     * @see org.gamegineer.common.ui.dialog.IDialogPage#getShell()
     */
    @Override
    public final Window getShell()
    {
        if( content_ == null )
        {
            return null;
        }

        return SwingUtilities.getWindowAncestor( content_ );
    }

    /*
     * @see org.gamegineer.common.ui.dialog.IDialogPage#getTitle()
     */
    @Override
    public final String getTitle()
    {
        return title_;
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
     * Sets the dialog page description.
     * 
     * <p>
     * This implementation updates the dialog page description field.
     * </p>
     * 
     * @param description
     *        The dialog page description or {@code null} to clear it.
     */
    protected void setDescription(
        /* @Nullable */
        final String description )
    {
        description_ = description;
    }

    /**
     * This implementation updates the dialog page message field.
     * 
     * @see org.gamegineer.common.ui.dialog.IDialogPage#setMessage(org.gamegineer.common.ui.dialog.DialogMessage)
     */
    @Override
    public void setMessage(
        /* @Nullable */
        final DialogMessage message )
    {
        message_ = message;
    }

    /**
     * Sets the dialog page title.
     * 
     * <p>
     * This implementation updates the dialog page title field.
     * </p>
     * 
     * @param title
     *        The dialog page title or {@code null} to clear it.
     */
    protected void setTitle(
        /* @Nullable */
        final String title )
    {
        title_ = title;
    }

    /*
     * @see org.gamegineer.common.ui.dialog.IDialogPage#setVisible(boolean)
     */
    @Override
    public final void setVisible(
        final boolean isVisible )
    {
        if( content_ != null )
        {
            content_.setVisible( isVisible );
        }
    }
}
