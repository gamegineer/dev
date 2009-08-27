/*
 * LoggingPropertiesAttributeAsAttributeTest.java
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
 * Created on May 19, 2008 at 8:49:54 PM.
 */

package org.gamegineer.common.internal.core.services.logging.attributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.gamegineer.common.core.services.component.util.attribute.AbstractAttributeTestCase;
import org.gamegineer.common.core.services.component.util.attribute.IAttribute;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.internal.core.services.logging.attributes.LoggingPropertiesAttribute}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.common.core.services.component.util.attribute.IAttribute}
 * interface.
 */
public final class LoggingPropertiesAttributeAsAttributeTest
    extends AbstractAttributeTestCase<Map<String, String>>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code LoggingPropertiesAttributeAsAttributeTest} class.
     */
    public LoggingPropertiesAttributeAsAttributeTest()
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
    protected IAttribute<Map<String, String>> createAttribute()
    {
        return LoggingPropertiesAttribute.INSTANCE;
    }

    /*
     * @see org.gamegineer.common.core.services.component.util.attribute.AbstractAttributeTestCase#getLegalValues()
     */
    @Override
    protected List<Map<String, String>> getLegalValues()
    {
        final List<Map<String, String>> values = new ArrayList<Map<String, String>>();
        values.add( Collections.<String, String>emptyMap() );
        values.add( Collections.singletonMap( "name", "value" ) ); //$NON-NLS-1$ //$NON-NLS-2$
        final Map<String, String> multiElementMap = new HashMap<String, String>();
        multiElementMap.put( "name1", "value1" ); //$NON-NLS-1$ //$NON-NLS-2$
        multiElementMap.put( "name2", "value2" ); //$NON-NLS-1$ //$NON-NLS-2$
        values.add( multiElementMap );
        return values;
    }
}
