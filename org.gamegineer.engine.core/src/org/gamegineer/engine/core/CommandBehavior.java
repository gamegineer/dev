/*
 * CommandBehavior.java
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
 * Created on Aug 13, 2008 at 9:26:52 PM.
 */

package org.gamegineer.engine.core;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies the execution behavior of an engine command.
 * 
 * <p>
 * This annotation is intended to be applied to classes that implement the
 * {@code ICommand} interface.
 * </p>
 */
@Documented
@Inherited
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.TYPE )
public @interface CommandBehavior
{
    // ======================================================================
    // Elements
    // ======================================================================

    /**
     * Indicates the command must acquire the engine write lock before
     * execution.
     * 
     * @return {@code true} if the command must acquire the engine write lock
     *         before execution; otherwise {@code false}.
     */
    boolean writeLockRequired() default false;
}
