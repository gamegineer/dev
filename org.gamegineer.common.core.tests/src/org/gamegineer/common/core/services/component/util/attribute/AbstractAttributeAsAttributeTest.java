/*
 * AbstractAttributeAsAttributeTest.java
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
 * Created on May 27, 2008 at 10:51:35 PM.
 */

package org.gamegineer.common.core.services.component.util.attribute;

import java.util.Collections;
import java.util.List;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.core.services.component.util.attribute.AbstractAttribute}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.common.core.services.component.util.attribute.IAttribute}
 * interface.
 */
public final class AbstractAttributeAsAttributeTest
    extends AbstractAttributeTestCase<Object>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractAttributeAsAttributeTest} class.
     */
    public AbstractAttributeAsAttributeTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.services.component.util.attribute.AbstractAttributeTestCase#createAttribute()
     */
    @Override
    protected IAttribute<Object> createAttribute()
    {
        return new AbstractAttribute<Object>( "name", Object.class ) //$NON-NLS-1$
        {
            // no overrides
        };
    }

    /*
     * @see org.gamegineer.common.core.services.component.util.attribute.AbstractAttributeTestCase#getLegalValues()
     */
    @Override
    protected List<Object> getLegalValues()
    {
        return Collections.singletonList( new Object() );
    }
}
