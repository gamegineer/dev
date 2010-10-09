/*
 * AbstractDialogPage.java
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
 * Created on Sep 21, 2010 at 10:32:36 PM.
 */

package org.gamegineer.common.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FontMetrics;
import javax.swing.JButton;
import javax.swing.JPanel;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.common.internal.ui.Debug;

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

    /** The dialog page font metrics. */
    private FontMetrics fontMetrics_;

    /** The dialog page message. */
    private DialogMessage message_;

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
        fontMetrics_ = null;
        message_ = null;
        title_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

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

    /*
     * @see org.gamegineer.common.ui.dialog.IDialogPage#create(java.awt.Container)
     */
    @Override
    public final void create(
        final Container parent )
    {
        initializeDialogUnits( parent );

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
     * @see org.gamegineer.common.ui.dialog.IDialogPage#getTitle()
     */
    @Override
    public final String getTitle()
    {
        return title_;
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
     * Sets the dialog page message.
     * 
     * <p>
     * This implementation updates the dialog page message field.
     * </p>
     * 
     * @param message
     *        The dialog page message or {@code null} to clear the message.
     */
    protected void setMessage(
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
