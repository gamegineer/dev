/*
 * IMementoOriginator.java
 * Copyright 2008-2011 Gamegineer.org
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
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IMementoOriginator
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a memento that represents the state of the originator.
     * 
     * @return A memento that represents the state of the originator; never
     *         {@code null}.
     * 
     * @throws org.gamegineer.common.core.util.memento.MementoException
     *         If the memento cannot be created.
     */
    /* @NonNull */
    public Object createMemento()
        throws MementoException;

    /**
     * Sets the state of the originator using the specified memento.
     * 
     * @param memento
     *        The memento that represents the new state of the originator; must
     *        not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code memento} is {@code null}.
     * @throws org.gamegineer.common.core.util.memento.MementoException
     *         If the memento does not represent a valid state for the
     *         originator.
     */
    public void setMemento(
        /* @NonNull */
        Object memento )
        throws MementoException;
}
