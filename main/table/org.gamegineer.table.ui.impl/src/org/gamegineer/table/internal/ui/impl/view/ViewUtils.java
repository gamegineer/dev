/*
 * ViewUtils.java
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
 * Created on Nov 24, 2011 at 10:38:04 PM.
 */

package org.gamegineer.table.internal.ui.impl.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.core.util.concurrent.TaskUtils;
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.internal.ui.impl.Activator;
import org.gamegineer.table.internal.ui.impl.BundleImages;
import org.gamegineer.table.internal.ui.impl.Loggers;
import org.gamegineer.table.net.ITableNetwork;
import org.gamegineer.table.ui.ComponentSurfaceDesignUI;

/**
 * A collection of useful methods for views.
 * 
 * <p>
 * The methods of this class are not thread safe and must only be called on the
 * Swing event dispatch thread.
 * </p>
 */
@NotThreadSafe
final class ViewUtils
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The maximum number of missing icons to cache. */
    private static final int MAX_MISSING_ICONS = 32;

    /**
     * The missing icons cache. The key is the icon size. The value is the
     * missing icon.
     */
    private static final Map<Dimension, Icon> missingIcons_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code ViewUtils} class.
     */
    static
    {
        missingIcons_ = new LinkedHashMap<Dimension, Icon>()
        {
            private static final long serialVersionUID = 1L;

            @Override
            protected boolean removeEldestEntry(
                @SuppressWarnings( "unused" )
                final @Nullable Entry<Dimension, Icon> eldest )
            {
                return size() > MAX_MISSING_ICONS;
            }
        };
    }

    /**
     * Initializes a new instance of the {@code ViewUtils} class.
     */
    private ViewUtils()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a default component surface design user interface for the
     * specified component surface design.
     * 
     * @param componentSurfaceDesign
     *        The component surface design; must not be {@code null}.
     * 
     * @return A default component surface design user interface for the
     *         specified component surface design; never {@code null}.
     */
    static ComponentSurfaceDesignUI createDefaultComponentSurfaceDesignUI(
        final ComponentSurfaceDesign componentSurfaceDesign )
    {
        return new ComponentSurfaceDesignUI( //
            componentSurfaceDesign.getId(), //
            NlsMessages.ViewUtils_defaultComponentSurfaceDesignUI_name, //
            getMissingIcon( componentSurfaceDesign.getSize() ) //
        );
    }

    /**
     * Creates the missing icon for the specified size.
     * 
     * @param size
     *        The icon size; must not be {@code null}.
     * 
     * @return A new missing icon for the specified size; never {@code null}.
     */
    private static Icon createMissingIcon(
        final Dimension size )
    {
        final int height = size.height;
        final int width = size.width;
        return new Icon()
        {
            @Override
            public int getIconHeight()
            {
                return height;
            }

            @Override
            public int getIconWidth()
            {
                return width;
            }

            @Override
            public void paintIcon(
                @SuppressWarnings( "unused" )
                final @Nullable Component c,
                final @Nullable Graphics g,
                final int x,
                final int y )
            {
                assert g != null;

                g.setColor( Color.WHITE );
                g.fillRect( x, y, width, height );
                g.setColor( Color.BLACK );
                g.drawRect( x, y, width - 1, height - 1 );

                final ImageIcon icon = Activator.getDefault().getBundleImages().getIcon( BundleImages.OBJECT_MISSING_IMAGE );
                assert icon != null;
                g.drawImage( icon.getImage(), x + (width - icon.getIconWidth()) / 2, y + (height - icon.getIconHeight()) / 2, null );
            }
        };
    }

    /**
     * Synchronously disconnects the specified table network associated with the
     * specified view component.
     * 
     * <p>
     * This method waits for a fixed amount of time for the specified table
     * network to disconnect.
     * </p>
     * 
     * @param component
     *        The view component associated with the table network; must not be
     *        {@code null}.
     * @param tableNetwork
     *        The table network to be disconnected; must not be {@code null}.
     */
    static void disconnectTableNetwork(
        final Component component,
        final ITableNetwork tableNetwork )
    {
        final Cursor oldCursor = component.getCursor();
        component.setCursor( Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR ) );
        try
        {
            final Future<?> future = Activator.getDefault().getExecutorService().submit( new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        tableNetwork.disconnect();
                    }
                    catch( @SuppressWarnings( "unused" ) final InterruptedException e )
                    {
                        Thread.currentThread().interrupt();
                    }
                }
            } );

            try
            {
                future.get( 10L, TimeUnit.SECONDS );
            }
            catch( final TimeoutException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.ViewUtils_disconnectTableNetwork_timedOut, e );
            }
            catch( final ExecutionException e )
            {
                throw TaskUtils.launderThrowable( e.getCause() );
            }
            catch( final InterruptedException e )
            {
                Thread.currentThread().interrupt();
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.ViewUtils_disconnectTableNetwork_interrupted, e );
            }
        }
        finally
        {
            component.setCursor( oldCursor );
        }
    }

    /**
     * Gets the missing icon for the specified size.
     * 
     * @param size
     *        The icon size; must not be {@code null}.
     * 
     * @return The missing icon for the specified size; never {@code null}.
     */
    private static Icon getMissingIcon(
        final Dimension size )
    {
        Icon missingIcon = missingIcons_.get( size );
        if( missingIcon == null )
        {
            missingIcon = createMissingIcon( size );
            missingIcons_.put( size, missingIcon );
        }

        return missingIcon;
    }
}
