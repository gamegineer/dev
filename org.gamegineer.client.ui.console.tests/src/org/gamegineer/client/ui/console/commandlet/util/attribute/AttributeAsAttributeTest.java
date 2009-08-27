/*
 * AttributeAsAttributeTest.java
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
 * Created on May 18, 2009 at 10:21:33 PM.
 */

package org.gamegineer.client.ui.console.commandlet.util.attribute;

import java.util.Collection;
import java.util.Collections;

/**
 * A fixture for testing the
 * {@link org.gamegineer.client.ui.console.commandlet.util.attribute.Attribute}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.client.ui.console.commandlet.util.attribute.IAttribute}
 * interface.
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
     * @see org.gamegineer.client.ui.console.commandlet.util.attribute.AbstractAttributeTestCase#createAttribute()
     */
    @Override
    protected IAttribute<Object> createAttribute()
        throws Exception
    {
        return new Attribute<Object>( "name" ) //$NON-NLS-1$
        {
            @Override
            protected Object getDefaultValue()
            {
                return getLegalAttributeValue();
            }
        };
    }

    /*
     * @see org.gamegineer.client.ui.console.commandlet.util.attribute.AbstractAttributeTestCase#getIllegalAttributeValues()
     */
    @Override
    protected Collection<Object> getIllegalAttributeValues()
    {
        return Collections.singletonList( null );
    }

    /*
     * @see org.gamegineer.client.ui.console.commandlet.util.attribute.AbstractAttributeTestCase#getLegalAttributeValue()
     */
    @Override
    protected Object getLegalAttributeValue()
    {
        return new Object();
    }
}
