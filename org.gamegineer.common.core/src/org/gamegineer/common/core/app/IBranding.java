/*
 * IBranding.java
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
 * Created on Jan 21, 2012 at 9:08:37 PM.
 */

package org.gamegineer.common.core.app;

import org.osgi.framework.Bundle;

// TODO: add a getVersion() method or a BrandingUtils class that has a getVersion(IBranding) method

/**
 * A service that provides branding information for the running application.
 */
public interface IBranding
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the identifier of the application associated with the branding.
     * 
     * @return The identifier of the application associated with the branding or
     *         {@code null} if none.
     */
    /* @Nullable */
    public String getApplication();

    /**
     * Gets the bundle responsible for the branding.
     * 
     * @return The bundle responsible for the branding or {@code null} if none.
     */
    /* @Nullable */
    public Bundle getBundle();

    /**
     * Gets the branding description.
     * 
     * @return The branding description or {@code null} if none.
     */
    /* @Nullable */
    public String getDescription();

    /**
     * Gets the branding identifier.
     * 
     * @return The branding identifier; never {@code null}.
     */
    /* @NonNull */
    public String getId();

    /**
     * Gets the branding name.
     * 
     * @return The branding name or {@code null} if none.
     */
    /* @Nullable */
    public String getName();

    /**
     * Gets the value of the branding property with the specified name.
     * 
     * @param name
     *        The branding name; must not be {@code null}.
     * 
     * @return The value of the branding property with the specified name or
     *         {@code null} if the property does not exist.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code name} is {@code null}.
     */
    /* @Nullable */
    public String getProperty(
        /* @NonNull */
        String name );
}
