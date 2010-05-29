/*
 * MementoAsEquatableTest.java
 * Copyright 2008-2010 Gamegineer.org
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
 * Created on May 27, 2010 at 11:10:18 PM.
 */

package org.gamegineer.common.internal.persistence.memento;

import java.util.ArrayList;
import java.util.Collection;
import org.gamegineer.test.core.AbstractEquatableTestCase;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.internal.persistence.memento.Memento} class to
 * ensure it does not violate the contract of the equatable interface.
 */
public final class MementoAsEquatableTest
    extends AbstractEquatableTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MementoAsEquatableTest} class.
     */
    public MementoAsEquatableTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.test.core.AbstractEquatableTestCase#createReferenceInstance()
     */
    @Override
    protected Object createReferenceInstance()
    {
        return Mementos.createMemento( 4, 1 );
    }

    /*
     * @see org.gamegineer.test.core.AbstractEquatableTestCase#createUnequalInstances()
     */
    @Override
    protected Collection<Object> createUnequalInstances()
    {
        final Collection<Object> others = new ArrayList<Object>();
        others.add( Mementos.createMemento( 3, 1 ) );
        others.add( Mementos.createMemento( 4, 2 ) );
        return others;
    }
}
