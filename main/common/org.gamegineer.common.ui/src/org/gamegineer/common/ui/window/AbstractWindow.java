/*
 * AbstractWindow.java
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
 * Created on Sep 11, 2010 at 2:56:08 PM.
 */

package org.gamegineer.common.ui.window;

import static org.gamegineer.common.core.runtime.NullAnalysis.nonNull;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import javax.swing.JPanel;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.ui.util.Geometry;

/**
 * Superclass for all windows.
 * 
 * @param <T>
 *        The type of the window shell.
 */
@NotThreadSafe
public abstract class AbstractWindow<T extends Window>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The window content. */
    @Nullable
    private Component content_;

    /** The window parent shell. */
    @Nullable
    private Window parentShell_;

    /** The window return code. */
    private int returnCode_;

    /** The window shell. */
    @Nullable
    private T shell_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractWindow} class.
     * 
     * @param parentShell
     *        The parent shell or {@code null} to create a top-level shell.
     */
    protected AbstractWindow(
        @Nullable
        final Window parentShell )
    {
        content_ = null;
        parentShell_ = parentShell;
        returnCode_ = WindowConstants.RETURN_CODE_OK;
        shell_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Closes the window.
     * 
     * @return {@code true} if the window was closed; {@code false} if the
     *         window is still open.
     */
    public boolean close()
    {
        final @Nullable T shell = shell_;
        if( (shell == null) || !shell.isDisplayable() )
        {
            return true;
        }

        shell.dispose();
        shell_ = null;
        content_ = null;

        return true;
    }

    /**
     * Configures the specified shell before opening the window.
     * 
     * <p>
     * This implementation sets the layout of the shell.
     * </p>
     * 
     * @param shell
     *        The shell; must not be {@code null}.
     */
    protected void configureShell(
        final T shell )
    {
        final LayoutManager layout = getLayout();
        if( layout != null )
        {
            shell.setLayout( layout );
        }
    }

    /**
     * Constrains the shell size to be no larger than the display size.
     */
    private void constrainShellSize()
    {
        final @Nullable T shell = shell_;
        assert shell != null;
        final Rectangle bounds = nonNull( shell.getBounds() );
        final Rectangle constrainedBounds = getConstrainedBounds( bounds );
        if( !bounds.equals( constrainedBounds ) )
        {
            shell.setBounds( constrainedBounds );
        }
    }

    /**
     * Invoked after the window content has been created.
     * 
     * <p>
     * This implementation does nothing.
     * </p>
     */
    protected void contentCreated()
    {
        // do nothing
    }

    /**
     * Invoked after the window content has been realized.
     * 
     * <p>
     * This implementation does nothing.
     * </p>
     */
    protected void contentRealized()
    {
        // do nothing
    }

    /**
     * Creates the window.
     */
    public final void create()
    {
        final T shell = createShell( getParentShell() );
        shell_ = shell;
        configureShell( shell );

        content_ = createContent( shell );
        contentCreated();

        shell.pack();
        contentRealized();

        initializeBounds();
    }

    /**
     * Creates the window content.
     * 
     * <p>
     * This implementation creates a panel.
     * </p>
     * 
     * @param parent
     *        The parent container for all content; must not be {@code null}.
     * 
     * @return The window content; never {@code null}.
     */
    protected Component createContent(
        final Container parent )
    {
        final Component content = new JPanel();
        parent.add( content );
        return content;
    }

    /**
     * Creates the window shell.
     * 
     * @param parent
     *        The parent shell or {@code null} to create a top-level shell.
     * 
     * @return The window shell; never {@code null}.
     */
    protected abstract T createShell(
        @Nullable
        final Window parent );

    /**
     * Adjusts the specified preferred bounds so that they do not extend beyond
     * the display bounds.
     * 
     * @param preferredBounds
     *        The preferred bounds; must not be {@code null}.
     * 
     * @return The adjusted bounds that are as close to the preferred bounds as
     *         possible without extending beyond the display bounds; never
     *         {@code null}.
     */
    private static Rectangle getConstrainedBounds(
        final Rectangle preferredBounds )
    {
        final Rectangle bounds = new Rectangle( preferredBounds );
        final Rectangle displayBounds = new Rectangle( new Point( 0, 0 ), Toolkit.getDefaultToolkit().getScreenSize() );

        if( bounds.width > displayBounds.width )
        {
            bounds.width = displayBounds.width;
        }
        if( bounds.height > displayBounds.height )
        {
            bounds.height = displayBounds.height;
        }

        bounds.x = Math.max( displayBounds.x, Math.min( bounds.x, displayBounds.x + displayBounds.width - bounds.width ) );
        bounds.y = Math.max( displayBounds.y, Math.min( bounds.y, displayBounds.y + displayBounds.height - bounds.height ) );

        return bounds;
    }

    /**
     * Get the window content.
     * 
     * @return The window content or {@code null} if the window content has not
     *         yet been created.
     */
    @Nullable
    protected final Component getContent()
    {
        return content_;
    }

    /**
     * Gets the initial location of shell.
     * 
     * <p>
     * This implementation centers the shell horizontally (1/2 to the left and
     * 1/2 to the right) and vertically (1/3 above and 2/3 below) relative to
     * the parent shell or the display bounds if there is no parent shell.
     * </p>
     * 
     * @param initialSize
     *        The initial size of the shell; must not be {@code null}.
     * 
     * @return The initial location of the shell; never {@code null}.
     */
    protected Point getInitialLocation(
        final Dimension initialSize )
    {
        final @Nullable T shell = shell_;
        assert shell != null;
        final Container parent = shell.getParent();
        final Rectangle displayBounds = new Rectangle( new Point( 0, 0 ), Toolkit.getDefaultToolkit().getScreenSize() );
        final Point centerPoint = Geometry.calculateCenterPoint( (parent != null) ? nonNull( parent.getBounds() ) : displayBounds );
        return new Point( //
            centerPoint.x - (initialSize.width / 2), //
            Math.max( displayBounds.y, Math.min( centerPoint.y - (initialSize.height * 2 / 3), displayBounds.y + displayBounds.height - initialSize.height ) ) );
    }

    /**
     * Gets the initial size of the shell.
     * 
     * <p>
     * This implementation returns the preferred size of the shell.
     * </p>
     * 
     * @return The initial size of the shell; never {@code null}.
     */
    protected Dimension getInitialSize()
    {
        final @Nullable T shell = shell_;
        assert shell != null;
        return nonNull( shell.getPreferredSize() );
    }

    /**
     * Gets the layout for the shell.
     * 
     * <p>
     * This implementation returns an instance of {@link BorderLayout}.
     * </p>
     * 
     * @return The layout for the shell or {@code null} if no layout should be
     *         associated with the shell.
     */
    @Nullable
    protected LayoutManager getLayout()
    {
        return new BorderLayout();
    }

    /**
     * Gets the window parent shell.
     * 
     * @return The window parent shell or {@code null} if the window has no
     *         parent shell.
     */
    @Nullable
    protected final Window getParentShell()
    {
        return parentShell_;
    }

    /**
     * Gets the window return code.
     * 
     * @return The window return code.
     */
    public final int getReturnCode()
    {
        return returnCode_;
    }

    /**
     * Gets the window shell.
     * 
     * @return The window shell or {@code null} if the window shell has not yet
     *         been created.
     */
    @Nullable
    public final T getShell()
    {
        return shell_;
    }

    /**
     * Initializes the bounds of the window shell.
     */
    private void initializeBounds()
    {
        final Dimension size = getInitialSize();
        final Point location = getInitialLocation( size );
        final Rectangle bounds = getConstrainedBounds( new Rectangle( location, size ) );
        final @Nullable T shell = shell_;
        assert shell != null;
        shell.setBounds( bounds );
        shell.setPreferredSize( bounds.getSize() );
    }

    /**
     * Opens the window, creating it first if necessary.
     * 
     * @return The window return code.
     */
    public final int open()
    {
        @Nullable T shell = shell_;
        if( (shell == null) || !shell.isDisplayable() )
        {
            shell = shell_ = null;
            create();
            shell = shell_;
            assert shell != null;
        }

        constrainShellSize();

        shell.setVisible( true );

        return returnCode_;
    }

    /**
     * Sets the window return code.
     * 
     * @param returnCode
     *        The window return code.
     */
    protected final void setReturnCode(
        final int returnCode )
    {
        returnCode_ = returnCode;
    }
}
