/*
 * LoggingPropertiesAttribute.java
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
 * Created on May 19, 2008 at 8:47:11 PM.
 */

package org.gamegineer.common.internal.core.services.logging.attributes;

import java.util.Map;
import org.gamegineer.common.core.services.component.util.attribute.AbstractAttribute;

/**
 * An attribute used to specify a collection of optional logging properties.
 */
public final class LoggingPropertiesAttribute
    extends AbstractAttribute<Map<String, String>>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The singleton attribute instance. */
    public static final LoggingPropertiesAttribute INSTANCE = new LoggingPropertiesAttribute();

    /** The attribute name. */
    private static final String NAME = "org.gamegineer.common.internal.core.services.logging.attributes.loggingProperties"; //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code LoggingPropertiesAttribute}
     * class.
     */
    private LoggingPropertiesAttribute()
    {
        super( NAME, Map.class );
    }
}
