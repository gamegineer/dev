/*
 * ComponentStrategyIdAsEquatableTest.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
 * Created on Aug 3, 2012 at 9:03:26 PM.
 */

package org.gamegineer.table.core;

import java.util.ArrayList;
import java.util.Collection;
import org.gamegineer.test.core.AbstractEquatableTestCase;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.core.ComponentStrategyId} class to ensure it does
 * not violate the contract of the equatable interface.
 */
public final class ComponentStrategyIdAsEquatableTest
    extends AbstractEquatableTestCase<ComponentStrategyId>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ComponentStrategyIdAsEquatableTest} class.
     */
    public ComponentStrategyIdAsEquatableTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.test.core.AbstractEquatableTestCase#createReferenceInstance()
     */
    @Override
    protected ComponentStrategyId createReferenceInstance()
    {
        return ComponentStrategyId.fromString( "id" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.test.core.AbstractEquatableTestCase#createUnequalInstances()
     */
    @Override
    protected Collection<ComponentStrategyId> createUnequalInstances()
    {
        final Collection<ComponentStrategyId> others = new ArrayList<>();
        others.add( ComponentStrategyId.fromString( "otherId" ) ); //$NON-NLS-1$
        return others;
    }
}
