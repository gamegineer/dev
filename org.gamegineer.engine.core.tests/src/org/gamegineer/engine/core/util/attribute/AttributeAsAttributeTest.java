/*
 * AttributeAsAttributeTest.java
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
 * Created on Sep 6, 2008 at 9:44:45 PM.
 */

package org.gamegineer.engine.core.util.attribute;

import java.util.Collection;
import java.util.Collections;
import org.gamegineer.engine.core.IState.Scope;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.core.util.attribute.Attribute} class to ensure
 * it does not violate the contract of the
 * {@link org.gamegineer.engine.core.util.attribute.IAttribute} interface.
 */
public final class AttributeAsAttributeTest
    extends AbstractAttributeTestCase<Object>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AttributeAsAttributeTest} class.
     */
    public AttributeAsAttributeTest()
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
    protected IAttribute<Object> createAttribute()
        throws Exception
    {
        return new Attribute<Object>( Scope.APPLICATION, "name" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.engine.core.util.AbstractAttributeTestCase#getIllegalAttributeValues()
     */
    @Override
    protected Collection<Object> getIllegalAttributeValues()
    {
        return Collections.singletonList( null );
    }

    /*
     * @see org.gamegineer.engine.core.util.AbstractAttributeTestCase#getLegalAttributeValue()
     */
    @Override
    protected Object getLegalAttributeValue()
    {
        return new Object();
    }
}
