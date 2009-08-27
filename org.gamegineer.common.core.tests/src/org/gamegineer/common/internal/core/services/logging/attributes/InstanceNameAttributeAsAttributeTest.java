/*
 * InstanceNameAttributeAsAttributeTest.java
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
 * Created on May 19, 2008 at 12:53:48 AM.
 */

package org.gamegineer.common.internal.core.services.logging.attributes;

import java.util.Arrays;
import java.util.List;
import org.gamegineer.common.core.services.component.util.attribute.AbstractAttributeTestCase;
import org.gamegineer.common.core.services.component.util.attribute.IAttribute;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.internal.core.services.logging.attributes.InstanceNameAttribute}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.common.core.services.component.util.attribute.IAttribute}
 * interface.
 */
public final class InstanceNameAttributeAsAttributeTest
    extends AbstractAttributeTestCase<String>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code InstanceNameAttributeAsAttributeTest} class.
     */
    public InstanceNameAttributeAsAttributeTest()
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
    protected IAttribute<String> createAttribute()
    {
        return InstanceNameAttribute.INSTANCE;
    }

    /*
     * @see org.gamegineer.common.core.services.component.util.attribute.AbstractAttributeTestCase#getLegalValues()
     */
    @Override
    protected List<String> getLegalValues()
    {
        return Arrays.asList( "", "name", "compoundName" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }
}
