/*
 * PlayerListAttributeAsAttributeTest.java
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
 * Created on Sep 6, 2008 at 11:31:54 PM.
 */

package org.gamegineer.game.internal.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.gamegineer.engine.core.util.attribute.AbstractAttributeTestCase;
import org.gamegineer.engine.core.util.attribute.IAttribute;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.internal.core.GameAttributes#PLAYER_LIST} class to
 * ensure it does not violate the contract of the
 * {@link org.gamegineer.engine.core.util.attribute.IAttribute} interface.
 */
public final class PlayerListAttributeAsAttributeTest
    extends AbstractAttributeTestCase<List<Player>>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code PlayerListAttributeAsAttributeTest} class.
     */
    public PlayerListAttributeAsAttributeTest()
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
    protected IAttribute<List<Player>> createAttribute()
        throws Exception
    {
        return GameAttributes.PLAYER_LIST;
    }

    /*
     * @see org.gamegineer.engine.core.util.attribute.AbstractAttributeTestCase#getIllegalAttributeValues()
     */
    @Override
    protected Collection<List<Player>> getIllegalAttributeValues()
    {
        return Collections.singletonList( null );
    }

    /*
     * @see org.gamegineer.engine.core.util.attribute.AbstractAttributeTestCase#getLegalAttributeValue()
     */
    @Override
    protected List<Player> getLegalAttributeValue()
    {
        return new ArrayList<Player>();
    }
}
