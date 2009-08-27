/*
 * ComponentFactoryAttributeAccessorAsAttributeAccessorTest.java
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
 * Created on May 17, 2008 at 10:59:58 PM.
 */

package org.gamegineer.common.core.services.component.util.attribute;

import java.util.Map;
import org.gamegineer.common.core.services.component.MockComponentFactory;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.core.services.component.util.attribute.ComponentFactoryAttributeAccessor}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.common.core.services.component.util.attribute.IAttributeAccessor}
 * interface.
 */
public final class ComponentFactoryAttributeAccessorAsAttributeAccessorTest
    extends AbstractAttributeAccessorTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ComponentFactoryAttributeAccessorAsAttributeAccessorTest} class.
     */
    public ComponentFactoryAttributeAccessorAsAttributeAccessorTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.services.component.util.attribute.AbstractAttributeAccessorTestCase#createAttributeAccessor(java.util.Map)
     */
    @Override
    protected IAttributeAccessor createAttributeAccessor(
        final Map<String, Object> attributeMap )
    {
        final MockComponentFactory factory = new MockComponentFactory();
        for( final Map.Entry<String, Object> entry : attributeMap.entrySet() )
        {
            factory.setAttribute( entry.getKey(), entry.getValue() );
        }
        return new ComponentFactoryAttributeAccessor( factory );
    }
}
