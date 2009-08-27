/*
 * MockInvertibleCommand.java
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
 * Created on Jun 22, 2008 at 10:59:12 PM.
 */

package org.gamegineer.engine.core;

/**
 * Mock implementation of {@link org.gamegineer.engine.core.IInvertibleCommand}
 * that acquires the engine write lock before execution.
 * 
 * <p>
 * This class is intended to be extended by clients.
 * </p>
 * 
 * @param <T>
 *        The result type of the command.
 */
public class MockInvertibleCommand<T>
    extends MockWriteCommand<T>
    implements IInvertibleCommand<T>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MockInvertibleCommand} class.
     */
    public MockInvertibleCommand()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * The default implementation returns this instance.
     * 
     * @see org.gamegineer.engine.core.IInvertibleCommand#getInverseCommand()
     */
    public IInvertibleCommand<T> getInverseCommand()
    {
        return this;
    }
}
