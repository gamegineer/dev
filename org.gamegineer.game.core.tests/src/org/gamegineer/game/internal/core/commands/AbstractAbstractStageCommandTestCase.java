/*
 * AbstractAbstractStageCommandTestCase.java
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
 * Created on Aug 30, 2008 at 11:17:50 PM.
 */

package org.gamegineer.game.internal.core.commands;

import org.gamegineer.engine.core.AbstractCommandTestCase;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that extend the
 * {@link org.gamegineer.game.internal.core.commands.AbstractStageCommand}
 * class.
 * 
 * @param <C>
 *        The type of the command.
 */
public abstract class AbstractAbstractStageCommandTestCase<C extends AbstractStageCommand>
    extends AbstractCommandTestCase<C, Void>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractAbstractStageCommandTestCase} class.
     */
    protected AbstractAbstractStageCommandTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code executeInternal} method throws an exception when
     * passed a {@code null} context.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testExecuteInternal_Context_Null()
        throws Exception
    {
        getCommand().executeInternal( null );
    }
}
