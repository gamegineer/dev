/*
 * ComponentPathAsComparableTest.java
 * Copyright 2008-2013 Gamegineer.org
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
 * Created on Jun 10, 2012 at 5:45:51 PM.
 */

package org.gamegineer.table.core;

import java.util.ArrayList;
import java.util.Collection;
import org.gamegineer.test.core.AbstractComparableTestCase;

/**
 * A fixture for testing the {@link org.gamegineer.table.core.ComponentPath}
 * class to ensure it does not violate the contract of the
 * {@link java.lang.Comparable} interface.
 */
public final class ComponentPathAsComparableTest
    extends AbstractComparableTestCase<ComponentPath>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentPathAsComparableTest}
     * class.
     */
    public ComponentPathAsComparableTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.test.core.AbstractComparableTestCase#createGreaterThanInstances()
     */
    @Override
    protected Collection<ComponentPath> createGreaterThanInstances()
    {
        final Collection<ComponentPath> others = new ArrayList<ComponentPath>();
        others.add( new ComponentPath( new ComponentPath( new ComponentPath( null, 1 ), 1 ), 0 ) );
        others.add( new ComponentPath( new ComponentPath( null, 1 ), 2 ) );
        others.add( new ComponentPath( new ComponentPath( null, 2 ), 1 ) );
        return others;
    }

    /*
     * @see org.gamegineer.test.core.AbstractComparableTestCase#createLessThanInstances()
     */
    @Override
    protected Collection<ComponentPath> createLessThanInstances()
    {
        final Collection<ComponentPath> others = new ArrayList<ComponentPath>();
        others.add( new ComponentPath( null, 1 ) );
        others.add( new ComponentPath( new ComponentPath( null, 1 ), 0 ) );
        others.add( new ComponentPath( new ComponentPath( null, 0 ), 1 ) );
        return others;
    }

    /*
     * @see org.gamegineer.test.core.AbstractEquatableTestCase#createReferenceInstance()
     */
    @Override
    protected ComponentPath createReferenceInstance()
    {
        return new ComponentPath( new ComponentPath( null, 1 ), 1 );
    }

    /*
     * @see org.gamegineer.test.core.AbstractEquatableTestCase#createUnequalInstances()
     */
    @Override
    protected Collection<ComponentPath> createUnequalInstances()
    {
        final Collection<ComponentPath> others = new ArrayList<ComponentPath>();
        others.add( new ComponentPath( null, 1 ) );
        others.add( new ComponentPath( new ComponentPath( null, 0 ), 1 ) );
        others.add( new ComponentPath( new ComponentPath( null, 1 ), 0 ) );
        others.add( new ComponentPath( new ComponentPath( new ComponentPath( null, 1 ), 1 ), 1 ) );
        return others;
    }
}
