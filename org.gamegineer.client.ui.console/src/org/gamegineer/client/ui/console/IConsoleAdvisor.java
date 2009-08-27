/*
 * IConsoleAdvisor.java
 * Copyright 2008 Gamegineer.org
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
 * Created on Oct 10, 2008 at 11:27:35 PM.
 */

package org.gamegineer.client.ui.console;

import java.util.List;
import org.osgi.framework.Version;

/**
 * An advisor responsible for configuring the platform console based on
 * application settings.
 * 
 * <p>
 * This interface is intended to be implemented but not extended by clients.
 * </p>
 */
public interface IConsoleAdvisor
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets an immutable view of the application argument list.
     * 
     * @return An immutable view of the application argument list; never
     *         {@code null}.
     */
    /* @NonNull */
    public List<String> getApplicationArguments();

    /**
     * Gets the application version.
     * 
     * @return The application version; never {@code null}.
     */
    /* @NonNull */
    public Version getApplicationVersion();
}
