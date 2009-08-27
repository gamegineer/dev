/*
 * ComponentCreationContextAsComponentCreationContextTest.java
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
 * Created on Apr 21, 2008 at 9:26:10 PM.
 */

package org.gamegineer.common.internal.core.services.component;

import java.util.Map;
import org.gamegineer.common.core.services.component.AbstractComponentCreationContextTestCase;
import org.gamegineer.common.core.services.component.IComponentCreationContext;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.internal.core.services.component.ComponentCreationContext}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.common.core.services.component.IComponentCreationContext}
 * interface.
 */
public final class ComponentCreationContextAsComponentCreationContextTest
    extends AbstractComponentCreationContextTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ComponentCreationContextAsComponentCreationContextTest} class.
     */
    public ComponentCreationContextAsComponentCreationContextTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.services.component.AbstractComponentCreationContextTestCase#createComponentCreationContext(java.util.Map)
     */
    @Override
    protected IComponentCreationContext createComponentCreationContext(
        final Map<String, Object> attributeMap )
    {
        return new ComponentCreationContext( attributeMap );
    }
}
