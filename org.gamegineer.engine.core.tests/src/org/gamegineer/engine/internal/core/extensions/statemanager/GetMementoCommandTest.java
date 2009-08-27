/*
 * GetMementoCommandTest.java
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
 * Created on Jul 2, 2008 at 11:29:52 PM.
 */

package org.gamegineer.engine.internal.core.extensions.statemanager;

import static org.junit.Assert.assertNotNull;
import org.gamegineer.common.persistence.memento.IMemento;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.extensions.statemanager.GetMementoCommand}
 * class.
 */
public final class GetMementoCommandTest
    extends AbstractStateManagerCommandTestCase<GetMementoCommand, IMemento>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GetMementoCommandTest} class.
     */
    public GetMementoCommandTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.internal.core.extensions.statemanager.AbstractStateManagerCommandTestCase#createCommand()
     */
    @Override
    protected GetMementoCommand createCommand()
    {
        return new GetMementoCommand();
    }

    /**
     * Ensures the {@code execute} method does not return {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testExecute_ReturnValue_NonNull()
        throws Exception
    {
        assertNotNull( getEngine().executeCommand( getCommand() ) );
    }
}
