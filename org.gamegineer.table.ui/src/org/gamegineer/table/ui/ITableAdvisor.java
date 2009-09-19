/*
 * ITableAdvisor.java
 * Copyright 2008-2009 Gamegineer.org
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
 * Created on Sep 18, 2009 at 9:25:44 PM.
 */

package org.gamegineer.table.ui;

import java.util.List;
import org.osgi.framework.Version;

/**
 * An advisor responsible for configuring the table based on application
 * settings.
 * 
 * <p>
 * This interface is intended to be implemented but not extended by clients.
 * </p>
 */
public interface ITableAdvisor
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets an immutable view of the application argument collection.
     * 
     * @return An immutable view of the application argument collection; never
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