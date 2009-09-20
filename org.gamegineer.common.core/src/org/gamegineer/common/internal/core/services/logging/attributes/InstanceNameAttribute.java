/*
 * InstanceNameAttribute.java
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
 * Created on May 18, 2008 at 9:37:59 PM.
 */

package org.gamegineer.common.internal.core.services.logging.attributes;

import net.jcip.annotations.Immutable;
import org.gamegineer.common.core.services.component.util.attribute.AbstractAttribute;

/**
 * An attribute used to specify the name of an instance of a logging service
 * entity such as a handler, formatter, or a filter.
 */
@Immutable
public final class InstanceNameAttribute
    extends AbstractAttribute<String>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The singleton attribute instance. */
    public static final InstanceNameAttribute INSTANCE = new InstanceNameAttribute();

    /** The attribute name. */
    private static final String NAME = "org.gamegineer.common.internal.core.services.logging.attributes.instanceName"; //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code InstanceNameAttribute} class.
     */
    private InstanceNameAttribute()
    {
        super( NAME, String.class );
    }
}
