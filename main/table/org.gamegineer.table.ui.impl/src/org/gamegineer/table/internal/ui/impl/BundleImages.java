/*
 * BundleImages.java
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
 * Created on Sep 22, 2011 at 10:41:35 PM.
 */

package org.gamegineer.table.internal.ui.impl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.Nullable;
import org.osgi.framework.BundleContext;

/**
 * Defines shared images for use in the bundle.
 */
@ThreadSafe
public final class BundleImages
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The bundle-relative path to the root icons folder. */
    private static final String FOLDER_ICONS = "/icons"; //$NON-NLS-1$

    /** The bundle-relative path to the background images folder. */
    private static final String FOLDER_BACKGROUNDS = FOLDER_ICONS + "/backgrounds"; //$NON-NLS-1$

    /** The bundle-relative path to the cursor icons folder. */
    private static final String FOLDER_CURSORS = FOLDER_ICONS + "/cursors"; //$NON-NLS-1$

    /** The bundle-relative path to the object icons folder. */
    private static final String FOLDER_OBJECTS = FOLDER_ICONS + "/objects"; //$NON-NLS-1$

    /** The bundle-relative path to the role icons folder. */
    private static final String FOLDER_ROLES = FOLDER_ICONS + "/roles"; //$NON-NLS-1$

    /** The bundle-relative path to the green felt background image. */
    public static final String BACKGROUND_GREEN_FELT = FOLDER_BACKGROUNDS + "/green-felt.png"; //$NON-NLS-1$

    /** The bundle-relative path to the grab cursor icon. */
    public static final String CURSOR_GRAB = FOLDER_CURSORS + "/grab.png"; //$NON-NLS-1$

    /** The bundle-relative path to the hand cursor icon. */
    public static final String CURSOR_HAND = FOLDER_CURSORS + "/hand.png"; //$NON-NLS-1$

    /** The bundle-relative path to the missing image object icon. */
    public static final String OBJECT_MISSING_IMAGE = FOLDER_OBJECTS + "/missing-image.png"; //$NON-NLS-1$

    /** The bundle-relative path to the editor role icon. */
    public static final String ROLE_EDITOR = FOLDER_ROLES + "/editor.png"; //$NON-NLS-1$

    /** The bundle-relative path to the editor requester role icon. */
    public static final String ROLE_EDITOR_REQUESTER = FOLDER_ROLES + "/editor-requester.png"; //$NON-NLS-1$

    /** The bundle-relative path to the no roles icon. */
    public static final String ROLE_NONE = FOLDER_ROLES + "/none.png"; //$NON-NLS-1$

    /** The bundle context. */
    private final BundleContext bundleContext_;

    /**
     * The collection of cached images. The key is the bundle-relative path of
     * the image. The value is the image.
     */
    @GuardedBy( "lock_" )
    private final Map<String, BufferedImage> images_;

    /** The instance lock. */
    private final Object lock_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code BundleImages} class.
     * 
     * @param bundleContext
     *        The bundle context.
     */
    BundleImages(
        final BundleContext bundleContext )
    {
        bundleContext_ = bundleContext;
        images_ = new HashMap<>();
        lock_ = new Object();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Disposes of the resources managed by this object.
     */
    void dispose()
    {
        synchronized( lock_ )
        {
            images_.clear();
        }
    }

    /**
     * Gets the icon with the specified bundle-relative path.
     * 
     * @param path
     *        The bundle-relative path of the icon.
     * 
     * @return The icon or {@code null} if no such icon exists.
     */
    public @Nullable ImageIcon getIcon(
        final String path )
    {
        final BufferedImage image = getImage( path );
        return (image != null) ? new ImageIcon( image ) : null;
    }

    /**
     * Gets the image with the specified bundle-relative path.
     * 
     * @param path
     *        The bundle-relative path of the image.
     * 
     * @return The image or {@code null} if no such image exists.
     */
    public @Nullable BufferedImage getImage(
        final String path )
    {
        synchronized( lock_ )
        {
            BufferedImage image = images_.get( path );
            if( image == null )
            {
                final URL imageUrl = bundleContext_.getBundle().getEntry( path );
                if( imageUrl != null )
                {
                    try
                    {
                        image = ImageIO.read( imageUrl );
                        if( image == null )
                        {
                            throw new IOException( NonNlsMessages.BundleImages_getImage_unsupportedFormat );
                        }

                        images_.put( path, image );
                    }
                    catch( final IOException e )
                    {
                        Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.BundleImages_getImage_readError( imageUrl ), e );
                    }
                }
            }

            return image;
        }
    }
}
