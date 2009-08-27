/*
 * CurrentRoundIdAttributeAsAttributeTest.java
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
 * Created on Sep 6, 2008 at 11:53:36 PM.
 */

package org.gamegineer.game.internal.core;

import java.util.Arrays;
import java.util.Collection;
import org.gamegineer.engine.core.util.attribute.AbstractAttributeTestCase;
import org.gamegineer.engine.core.util.attribute.IAttribute;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.internal.core.GameAttributes#CURRENT_ROUND_ID}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.engine.core.util.attribute.IAttribute} interface.
 */
public final class CurrentRoundIdAttributeAsAttributeTest
    extends AbstractAttributeTestCase<Integer>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code CurrentRoundIdAttributeAsAttributeTest} class.
     */
    public CurrentRoundIdAttributeAsAttributeTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.util.attribute.AbstractAttributeTestCase#createAttribute()
     */
    @Override
    protected IAttribute<Integer> createAttribute()
        throws Exception
    {
        return GameAttributes.CURRENT_ROUND_ID;
    }

    /*
     * @see org.gamegineer.engine.core.util.attribute.AbstractAttributeTestCase#getIllegalAttributeValues()
     */
    @Override
    @SuppressWarnings( "boxing" )
    protected Collection<Integer> getIllegalAttributeValues()
    {
        return Arrays.asList( 0, -1, Integer.MIN_VALUE );
    }

    /*
     * @see org.gamegineer.engine.core.util.attribute.AbstractAttributeTestCase#getLegalAttributeValue()
     */
    @Override
    @SuppressWarnings( "boxing" )
    protected Integer getLegalAttributeValue()
    {
        return 1 + (int)(Math.random() * 1000);
    }
}
