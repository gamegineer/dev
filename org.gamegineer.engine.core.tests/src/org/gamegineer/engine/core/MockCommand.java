/*
 * MockCommand.java
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
 * Created on Apr 7, 2008 at 8:43:37 PM.
 */

package org.gamegineer.engine.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;

/**
 * Mock implementation of {@link org.gamegineer.engine.core.ICommand}.
 * 
 * <p>
 * Instances of this command do not acquire the engine write lock and thus can
 * not modify the engine state.
 * </p>
 * 
 * <p>
 * This class is intended to be extended by clients.
 * </p>
 * 
 * @param <T>
 *        The result type of the command.
 */
public class MockCommand<T>
    implements ICommand<T>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MockCommand} class.
     */
    public MockCommand()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * The default implementation does nothing and returns {@code null}.
     * 
     * @see org.gamegineer.engine.core.ICommand#execute(org.gamegineer.engine.core.IEngineContext)
     */
    @SuppressWarnings( "unused" )
    public T execute(
        final IEngineContext context )
        throws EngineException
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        return null;
    }

    /**
     * The default implementation returns the fully-qualified name of the
     * {@code Class} of this command.
     * 
     * @see org.gamegineer.engine.core.ICommand#getType()
     */
    public String getType()
    {
        return getClass().getName();
    }
}
