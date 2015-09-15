/*
 * Branding.java
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
 * Created on Jan 23, 2012 at 8:34:39 PM.
 */

package org.gamegineer.table.internal.ui.impl;

import static org.gamegineer.common.core.runtime.NullAnalysis.nonNull;
import java.awt.Image;
import java.util.Collections;
import java.util.List;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.core.app.BrandingUtils;
import org.gamegineer.common.core.app.IBranding;
import org.gamegineer.common.ui.app.BrandingUIUtils;
import org.osgi.framework.Version;

/**
 * A facade for simplified access to the application branding service.
 */
@ThreadSafe
public final class Branding
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Branding} class.
     */
    private Branding()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the default application branding service.
     * 
     * @return The default application branding service or {@code null} if no
     *         application branding service is available.
     */
    @Nullable
    public static IBranding getDefault()
    {
        return Activator.getDefault().getBranding();
    }

    /**
     * Gets the branding name.
     * 
     * @return The branding name; never {@code null}.
     */
    public static String getName()
    {
        final IBranding branding = getDefault();
        if( branding != null )
        {
            final String name = branding.getName();
            if( name != null )
            {
                return name;
            }
        }

        return NlsMessages.Branding_name_default;
    }

    /**
     * Gets the branding version.
     * 
     * @return The branding version; never {@code null}.
     */
    public static Version getVersion()
    {
        final IBranding branding = getDefault();
        if( branding != null )
        {
            return BrandingUtils.getVersion( branding );
        }

        return nonNull( Version.emptyVersion );
    }

    /**
     * Gets the collection of window images associated with the branding.
     * 
     * @return The collection of window images associated with the branding;
     *         never {@code null}.
     */
    public static List<Image> getWindowImages()
    {
        final IBranding branding = getDefault();
        if( branding != null )
        {
            return BrandingUIUtils.getWindowImages( branding );
        }

        return Collections.<@NonNull Image>emptyList();
    }
}
