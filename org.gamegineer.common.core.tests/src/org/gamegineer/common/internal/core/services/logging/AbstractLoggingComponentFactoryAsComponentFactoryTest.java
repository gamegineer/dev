/*
 * AbstractLoggingComponentFactoryAsComponentFactoryTest.java
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
 * Created on May 24, 2008 at 10:29:22 PM.
 */

package org.gamegineer.common.internal.core.services.logging;

import java.util.Map;
import org.gamegineer.common.core.services.component.AbstractComponentFactoryTestCase;
import org.gamegineer.common.core.services.component.IComponentFactory;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.internal.core.services.logging.AbstractLoggingComponentFactory}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.common.core.services.component.IComponentFactory}
 * interface.
 */
public final class AbstractLoggingComponentFactoryAsComponentFactoryTest
    extends AbstractComponentFactoryTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractLoggingComponentFactoryAsComponentFactoryTest} class.
     */
    public AbstractLoggingComponentFactoryAsComponentFactoryTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.services.component.AbstractComponentFactoryTestCase#createComponentFactory(java.util.Map)
     */
    @Override
    protected IComponentFactory createComponentFactory(
        final Map<String, Object> attributes )
    {
        final AbstractLoggingComponentFactory<MockLoggingComponent> factory = new AbstractLoggingComponentFactory<MockLoggingComponent>( MockLoggingComponent.class )
        {
            // no overrides
        };
        for( final Map.Entry<String, Object> entry : attributes.entrySet() )
        {
            factory.setAttribute( entry.getKey(), entry.getValue() );
        }
        return factory;
    }
}
