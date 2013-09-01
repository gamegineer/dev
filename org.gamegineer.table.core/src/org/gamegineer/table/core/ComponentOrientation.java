/*
 * ComponentOrientation.java
 * Copyright 2008-2012 Gamegineer contributors and others.
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
 * Created on Apr 3, 2012 at 7:42:29 PM.
 */

package org.gamegineer.table.core;

import net.jcip.annotations.Immutable;
import org.gamegineer.common.core.util.ExtensibleEnum;

/**
 * Superclass for all component orientation enum constants.
 */
@Immutable
public abstract class ComponentOrientation
    extends ExtensibleEnum
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 1323430882724950747L;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentOrientation} class.
     * 
     * @param name
     *        The name of the enum constant; must not be {@code null}.
     * @param ordinal
     *        The ordinal of the enum constant.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code ordinal} is negative.
     * @throws java.lang.NullPointerException
     *         If {@code name} is {@code null}.
     */
    protected ComponentOrientation(
        /* @NonNull */
        final String name,
        final int ordinal )
    {
        super( name, ordinal );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the inverse of this component orientation.
     * 
     * <p>
     * If the component orientation has no inverse, it should return a reference
     * to itself.
     * </p>
     * 
     * @return The inverse of this component orientation; never {@code null}.
     */
    /* @NonNull */
    public abstract ComponentOrientation inverse();
}
