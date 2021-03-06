/*
 * IMementoOriginator.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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
 * Created on Jun 4, 2011 at 11:33:53 PM.
 */

package org.gamegineer.common.core.util.memento;

/**
 * A memento originator.
 */
public interface IMementoOriginator
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a memento that represents the state of the originator.
     * 
     * @return A memento that represents the state of the originator.
     */
    public Object createMemento();

    /**
     * Sets the state of the originator using the specified memento.
     * 
     * @param memento
     *        The memento that represents the new state of the originator.
     * 
     * @throws org.gamegineer.common.core.util.memento.MementoException
     *         If the memento does not represent a valid state for the
     *         originator.
     */
    public void setMemento(
        Object memento )
        throws MementoException;
}
