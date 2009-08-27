/*
 * AbstractCommandExecutingEventTestCase.java
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
 * Created on Jun 2, 2008 at 11:02:15 PM.
 */

package org.gamegineer.engine.core.extensions.commandeventmediator;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.engine.core.extensions.commandeventmediator.ICommandExecutingEvent}
 * interface.
 * 
 * @param <T>
 *        The type of the command executing event.
 */
public abstract class AbstractCommandExecutingEventTestCase<T extends ICommandExecutingEvent>
    extends AbstractCommandEventTestCase<T>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractCommandExecutingEventTestCase} class.
     */
    protected AbstractCommandExecutingEventTestCase()
    {
        super();
    }
}
