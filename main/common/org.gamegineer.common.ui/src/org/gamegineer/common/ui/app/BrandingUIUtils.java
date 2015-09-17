/*
 * BrandingUIUtils.java
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
 * Created on Jan 21, 2012 at 10:42:53 PM.
 */

package org.gamegineer.common.ui.app;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.core.app.IBranding;
import org.gamegineer.common.internal.ui.Loggers;
import org.osgi.framework.Bundle;

/**
 * A collection of useful methods for applying information from an application
 * branding to a user interface.
 */
@ThreadSafe
public final class BrandingUIUtils
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code BrandingUIUtils} class.
     */
    private BrandingUIUtils()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the image with the specified bundle-relative path.
     * 
     * @param bundle
     *        The bundle that contains the image; must not be {@code null}.
     * @param path
     *        The bundle-relative path of the image; must not be {@code null}.
     * 
     * @return The image or {@code null} if no such image exists.
     */
    @Nullable
    private static BufferedImage getImage(
        final Bundle bundle,
        final String path )
    {
        final URL imageUrl = bundle.getEntry( path );
        if( imageUrl != null )
        {
            try
            {
                return ImageIO.read( imageUrl );
            }
            catch( final IOException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.BrandingUIUtils_getImage_readError( imageUrl ), e );
            }
        }

        return null;
    }

    /**
     * Gets the collection of window images associated with the specified
     * application branding.
     * 
     * @param branding
     *        The application branding; must not be {@code null}.
     * 
     * @return The collection of window images associated with the specified
     *         application branding; never {@code null}.
     */
    public static List<Image> getWindowImages(
        final IBranding branding )
    {
        final Bundle brandingBundle = branding.getBundle();
        if( brandingBundle == null )
        {
            return Collections.<@NonNull Image>emptyList();
        }

        final String windowImagePaths = branding.getProperty( BrandingUIConstants.WINDOW_IMAGES );
        if( windowImagePaths == null )
        {
            return Collections.<@NonNull Image>emptyList();
        }

        final List<Image> windowImages = new ArrayList<>();
        for( final String windowImagePath : windowImagePaths.split( "," ) ) //$NON-NLS-1$
        {
            final Image windowImage = getImage( brandingBundle, windowImagePath );
            if( windowImage != null )
            {
                windowImages.add( windowImage );
            }
        }

        return windowImages;
    }
}
