/*
 * MementoAsMementoTest.java
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
 * Created on Jun 30, 2008 at 11:28:42 PM.
 */

package org.gamegineer.common.internal.persistence.memento;

import java.util.Map;
import org.gamegineer.common.persistence.memento.AbstractMementoTestCase;
import org.gamegineer.common.persistence.memento.IMemento;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.internal.persistence.memento.Memento} class to
 * ensure it does not violate the contract of the
 * {@link org.gamegineer.common.persistence.memento.IMemento} interface.
 */
public final class MementoAsMementoTest
    extends AbstractMementoTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MementoAsMementoTest} class.
     */
    public MementoAsMementoTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.persistence.memento.AbstractMementoTestCase#createMemento(java.util.Map)
     */
    @Override
    protected IMemento createMemento(
        final Map<String, Object> attributes )
    {
        return new Memento( attributes );
    }
}
