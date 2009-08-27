/*
 * Localizable.java
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
 * Created on Feb 26, 2009 at 11:22:17 PM.
 */

package org.gamegineer.game.internal.ui.system.bindings.xml;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies that a {@code String} field is localizable.
 * 
 * <p>
 * When a JAXB object is unmarshalled, all of its {@code String} fields that
 * declare this annotation will be examined to determine if they contain a
 * decorated locale-neutral key (according to the algorithm used by the
 * {@link org.gamegineer.game.ui.system.bindings.xml.LocaleNeutralKey} class).
 * All such fields will have their values replaced with the corresponding
 * localized string using the string bundle associated with the unmarshaller.
 * </p>
 */
@Documented
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.FIELD )
public @interface Localizable
{
    // No elements
}
