/*
 * PhantomCommand.java
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
 * Created on Apr 25, 2008 at 9:42:53 PM.
 */

package org.gamegineer.engine.internal.core;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates an engine phantom command.
 * 
 * <p>
 * An instance of {@link org.gamegineer.engine.core.ICommand} annotated with
 * {@code @PhantomCommand} indicates to the engine that the command is actually
 * a phantom command. A phantom command is typically used to execute normal
 * commands within its body. For example, a command that undoes a previously
 * executed command would be considered a phantom command. Because the phantom
 * command itself does not actually affect the engine state, a phantom command
 * is not added to the command history and no engine events associated with the
 * phantom command are ever fired.
 * </p>
 * 
 * <p>
 * This annotation is an implementation detail of the engine and is not intended
 * to be a part of the public API. Clients outside the org.gamegineer.engine.*
 * projects should not use it.
 * </p>
 */
@Documented
@Inherited
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.TYPE )
public @interface PhantomCommand
{
    // No elements
}
