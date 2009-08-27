/*
 * SupportedCommandletClassNamesAttributeAsAttributeTest.java
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
 * Created on Oct 21, 2008 at 10:40:22 PM.
 */

package org.gamegineer.client.ui.console.commandlet.attributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.gamegineer.common.core.services.component.util.attribute.AbstractAttributeTestCase;
import org.gamegineer.common.core.services.component.util.attribute.IAttribute;

/**
 * A fixture for testing the
 * {@link org.gamegineer.client.ui.console.commandlet.attributes.SupportedCommandletClassNamesAttribute}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.common.core.services.component.util.attribute.IAttribute}
 * interface.
 */
public final class SupportedCommandletClassNamesAttributeAsAttributeTest
    extends AbstractAttributeTestCase<Iterable<String>>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code SupportedCommandletClassNamesAttributeAsAttributeTest} class.
     */
    public SupportedCommandletClassNamesAttributeAsAttributeTest()
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
    protected IAttribute<Iterable<String>> createAttribute()
    {
        return SupportedCommandletClassNamesAttribute.INSTANCE;
    }

    /*
     * @see org.gamegineer.common.core.services.component.util.attribute.AbstractAttributeTestCase#getLegalValues()
     */
    @Override
    protected List<Iterable<String>> getLegalValues()
    {
        final List<Iterable<String>> values = new ArrayList<Iterable<String>>();
        values.add( Collections.<String>emptyList() );
        values.add( Collections.singletonList( Object.class.getName() ) );
        values.add( Arrays.asList( Object.class.getName(), String.class.getName() ) );
        return values;
    }
}
