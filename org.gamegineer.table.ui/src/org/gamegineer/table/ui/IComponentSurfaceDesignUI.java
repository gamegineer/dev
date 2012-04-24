/*
 * IComponentSurfaceDesignUI.java
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
 * Created on Apr 23, 2012 at 7:39:14 PM.
 */

package org.gamegineer.table.ui;

import javax.swing.Icon;
import org.gamegineer.table.core.ComponentSurfaceDesignId;

/**
 * A component surface design user interface.
 * 
 * @noextend This interface is not intended to be extended by clients.
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface IComponentSurfaceDesignUI
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the component surface design icon.
     * 
     * @return The component surface design icon; never {@code null}.
     */
    /* @NonNull */
    public Icon getIcon();

    /**
     * Gets the component surface design identifier.
     * 
     * @return The component surface design identifier; never {@code null}.
     */
    /* @NonNull */
    public ComponentSurfaceDesignId getId();

    /**
     * Gets the component surface design name.
     * 
     * @return The component surface design name; never {@code null}.
     */
    /* @NonNull */
    public String getName();
}
