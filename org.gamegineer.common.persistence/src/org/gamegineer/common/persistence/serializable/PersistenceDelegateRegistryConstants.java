/*
 * PersistenceDelegateRegistryConstants.java
 * Copyright 2008-2011 Gamegineer.org
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
 * Created on Jul 5, 2010 at 9:22:52 PM.
 */

package org.gamegineer.common.persistence.serializable;

import net.jcip.annotations.ThreadSafe;

/**
 * Defines useful constants for use by the persistence delegate registry and its
 * clients.
 */
@ThreadSafe
public final class PersistenceDelegateRegistryConstants
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The name of the delegators property.
     * 
     * <p>
     * The value of this property is either a string or a string array
     * representing the type name of the class that delegates its persistence
     * behavior to the persistence delegate.
     * </p>
     */
    public static final String PROPERTY_DELEGATORS = "delegators"; //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * PersistenceDelegateRegistryConstants} class.
     */
    private PersistenceDelegateRegistryConstants()
    {
        super();
    }
}
