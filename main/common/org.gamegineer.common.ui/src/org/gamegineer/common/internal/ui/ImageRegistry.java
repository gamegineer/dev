/*
 * ImageRegistry.java
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
 * Created on Sep 18, 2010 at 10:41:18 PM.
 */

package org.gamegineer.common.internal.ui;

import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.Nullable;

/**
 * A registry of images used by the bundle.
 */
@ThreadSafe
public final class ImageRegistry
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The path to the dialog default title image. */
    public static final String DIALOG_DEFAULT_TITLE_PATH = "/icons/default-title-image.png"; //$NON-NLS-1$

    /** The path to the dialog error message image. */
    public static final String DIALOG_ERROR_MESSAGE_PATH = "/icons/message-error.png"; //$NON-NLS-1$

    /** The path to the dialog information message image. */
    public static final String DIALOG_INFORMATION_MESSAGE_PATH = "/icons/message-information.png"; //$NON-NLS-1$

    /** The path to the dialog warning message image. */
    public static final String DIALOG_WARNING_MESSAGE_PATH = "/icons/message-warning.png"; //$NON-NLS-1$

    /**
     * The collection of images in the registry. The key is the image path. The
     * value is the image.
     */
    @GuardedBy( "lock_" )
    private final Map<String, Image> images_;

    /** The instance lock. */
    private final Object lock_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ImageRegistry} class.
     */
    ImageRegistry()
    {
        images_ = new HashMap<>();
        lock_ = new Object();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the image associated with the specified bundle-relative path.
     * 
     * @param path
     *        The bundle-relative path of the image.
     * 
     * @return The image associated with the specified bundle-relative path or
     *         {@code null} if no such image exists.
     */
    public @Nullable Image getImage(
        final String path )
    {
        Image image = null;
        synchronized( lock_ )
        {
            image = images_.get( path );
            if( image == null )
            {
                try
                {
                    image = ImageIO.read( Activator.getDefault().getBundleContext().getBundle().getEntry( path ) );
                    if( image != null )
                    {
                        images_.put( path, image );
                    }
                }
                catch( final IOException e )
                {
                    Loggers.getDefaultLogger().log( Level.WARNING, NonNlsMessages.ImageRegistry_loadImage_error( path ), e );
                }
            }
        }

        return image;
    }

    /**
     * Disposes of the resources used by this object.
     */
    void dispose()
    {
        synchronized( lock_ )
        {
            for( final Image image : images_.values() )
            {
                image.flush();
            }

            images_.clear();
        }
    }
}
