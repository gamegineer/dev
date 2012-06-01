/*
 * ViewUtils.java
 * Copyright 2008-2012 Gamegineer.org
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

package org.gamegineer.table.internal.ui.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import javax.swing.ImageIcon;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.common.core.util.concurrent.TaskUtils;
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.internal.ui.Activator;
import org.gamegineer.table.internal.ui.BundleImages;
import org.gamegineer.table.internal.ui.Loggers;
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

    /** The maximum number of missing images to cache. */
    private static final int MAX_MISSING_IMAGES = 32;

    /**
     * The missing images cache. The key is the image size. The value is the
     * missing image.
     */
    private static final Map<Dimension, Image> missingImages_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code ViewUtils} class.
     */
    static
    {
        missingImages_ = new LinkedHashMap<Dimension, Image>()
        {
            private static final long serialVersionUID = 1L;

            @Override
            protected boolean removeEldestEntry(
                @SuppressWarnings( "unused" )
                final Entry<Dimension, Image> eldest )
            {
                return size() > MAX_MISSING_IMAGES;
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
    /* @NonNull */
    static ComponentSurfaceDesignUI createDefaultComponentSurfaceDesignUI(
        /* @NonNull */
        final ComponentSurfaceDesign componentSurfaceDesign )
    {
        assert componentSurfaceDesign != null;

        return new ComponentSurfaceDesignUI( //
            componentSurfaceDesign.getId(), //
            NlsMessages.ViewUtils_defaultComponentSurfaceDesignUI_name, //
            new ImageIcon( getMissingImage( componentSurfaceDesign.getSize() ) ) //
        );
    }

    /**
     * Creates the missing image for the specified size.
     * 
     * @param size
     *        The image size; must not be {@code null}.
     * 
     * @return A new missing image for the specified size; never {@code null}.
     */
    /* @NonNull */
    private static Image createMissingImage(
        /* @NonNull */
        final Dimension size )
    {
        assert size != null;

        final BufferedImage image = new BufferedImage( size.width, size.height, BufferedImage.TYPE_INT_ARGB_PRE );

        final Graphics2D g = image.createGraphics();
        try
        {
            g.setColor( Color.WHITE );
            g.fillRect( 0, 0, size.width, size.height );
            g.setColor( Color.BLACK );
            g.drawRect( 0, 0, size.width - 1, size.height - 1 );

            final ImageIcon icon = Activator.getDefault().getBundleImages().getIcon( BundleImages.OBJECT_MISSING_IMAGE );
            assert icon != null;
            g.drawImage( icon.getImage(), (size.width - icon.getIconWidth()) / 2, (size.height - icon.getIconHeight()) / 2, null );
        }
        finally
        {
            g.dispose();
        }

        return image;
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
        /* @NonNull */
        final Component component,
        /* @NonNull */
        final ITableNetwork tableNetwork )
    {
        assert component != null;
        assert tableNetwork != null;

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
                    catch( final InterruptedException e )
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
     * Gets the missing image for the specified size.
     * 
     * @param size
     *        The image size; must not be {@code null}.
     * 
     * @return The missing image for the specified size; never {@code null}.
     */
    /* @NonNull */
    private static Image getMissingImage(
        /* @NonNull */
        final Dimension size )
    {
        assert size != null;

        Image missingImage = missingImages_.get( size );
        if( missingImage == null )
        {
            missingImage = createMissingImage( size );
            missingImages_.put( size, missingImage );
        }

        return missingImage;
    }
}
