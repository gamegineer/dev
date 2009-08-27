/*
 * ClassNameAttribute.java
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
 * Created on May 17, 2008 at 9:57:05 PM.
 */

package org.gamegineer.common.core.services.component.attributes;

import org.gamegineer.common.core.services.component.util.attribute.AbstractAttribute;

/**
 * An attribute used to specify the name of a class for which a client requests
 * an instance to be created.
 */
public final class ClassNameAttribute
    extends AbstractAttribute<String>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The singleton attribute instance. */
    public static final ClassNameAttribute INSTANCE = new ClassNameAttribute();

    /** The attribute name. */
    private static final String NAME = "org.gamegineer.common.core.services.component.attributes.className"; //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ClassNameAttribute} class.
     */
    private ClassNameAttribute()
    {
        super( NAME, String.class );
    }
}
