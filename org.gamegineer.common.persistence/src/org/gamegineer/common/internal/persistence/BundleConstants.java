/*
 * BundleConstants.java
 * Copyright 2008-2010 Gamegineer.org
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
 * Created on Jun 13, 2010 at 8:30:27 PM.
 */

package org.gamegineer.common.internal.persistence;

import net.jcip.annotations.ThreadSafe;

/**
 * Defines useful constants for use by the bundle.
 */
@ThreadSafe
public final class BundleConstants
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The JavaBeans persistence delegate extension point identifier. */
    public static final String EXTENSION_BEANS_PERSISTENCE_DELEGATES = "beansPersistenceDelegates"; //$NON-NLS-1$

    /** The Serializable persistence delegate extension point identifier. */
    public static final String EXTENSION_SERIALIZABLE_PERSISTENCE_DELEGATES = "serializablePersistenceDelegates"; //$NON-NLS-1$

    /** The symbolic name of the bundle. */
    public static final String SYMBOLIC_NAME = "org.gamegineer.common.persistence"; //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code BundleConstants} class.
     */
    private BundleConstants()
    {
        super();
    }
}