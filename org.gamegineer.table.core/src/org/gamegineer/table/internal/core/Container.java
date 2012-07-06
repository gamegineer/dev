/*
 * Container.java
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
 * Created on Jul 4, 2012 at 8:47:42 PM.
 */

package org.gamegineer.table.internal.core;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.IContainer;

/**
 * Implementation of {@link org.gamegineer.table.core.IContainer}.
 */
@ThreadSafe
abstract class Container
    extends Component
    implements IContainer
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Container} class.
     * 
     * @param tableEnvironment
     *        The table environment associated with the container; must not be
     *        {@code null}.
     */
    Container(
        /* @NonNull */
        final TableEnvironment tableEnvironment )
    {
        super( tableEnvironment );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the index of the specified component in this container.
     * 
     * @param component
     *        The component; must not be {@code null}.
     * 
     * @return The index of the specified component in this container.
     */
    @GuardedBy( "getLock()" )
    abstract int getComponentIndex(
        /* @NonNull */
        Component component );
}
