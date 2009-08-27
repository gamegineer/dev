/*
 * EngineFactoryAsComponentFactoryTest.java
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
 * Created on Jun 28, 2008 at 11:33:23 PM.
 */

package org.gamegineer.engine.internal.core;

import java.util.Map;
import org.gamegineer.common.core.services.component.AbstractComponentFactoryTestCase;
import org.gamegineer.common.core.services.component.IComponentFactory;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.EngineFactory} class to ensure it
 * does not violate the contract of the
 * {@link org.gamegineer.common.core.services.component.IComponentFactory}
 * interface.
 */
public final class EngineFactoryAsComponentFactoryTest
    extends AbstractComponentFactoryTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code EngineFactoryAsComponentFactoryTest} class.
     */
    public EngineFactoryAsComponentFactoryTest()
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
        final Map<String, Object> attributeMap )
    {
        final EngineFactory factory = new EngineFactory();
        for( final Map.Entry<String, Object> entry : attributeMap.entrySet() )
        {
            factory.setAttribute( entry.getKey(), entry.getValue() );
        }
        return factory;
    }
}
