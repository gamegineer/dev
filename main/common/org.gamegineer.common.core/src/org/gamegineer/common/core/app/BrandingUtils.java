/*
 * BrandingUtils.java
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
 * Created on Jan 26, 2012 at 8:33:52 PM.
 */

package org.gamegineer.common.core.app;

import java.util.logging.Level;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.internal.core.Loggers;
import org.osgi.framework.Bundle;
import org.osgi.framework.Version;

/**
 * A collection of useful methods for applying information from an application
 * branding.
 */
@ThreadSafe
public final class BrandingUtils
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code BrandingUtils} class.
     */
    private BrandingUtils()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the version associated with the specified application branding.
     * 
     * @param branding
     *        The application branding; must not be {@code null}.
     * 
     * @return The version associated with the specified application branding;
     *         never {@code null}.
     */
    public static Version getVersion(
        final IBranding branding )
    {
        final Bundle brandingBundle = branding.getBundle();
        if( brandingBundle != null )
        {
            final String versionString = brandingBundle.getHeaders().get( org.osgi.framework.Constants.BUNDLE_VERSION );
            if( versionString != null )
            {
                try
                {
                    return Version.parseVersion( versionString );
                }
                catch( final IllegalArgumentException e )
                {
                    Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.BrandingUtils_getVersion_parseError( versionString ), e );
                }
            }
        }

        return Version.emptyVersion;
    }
}
