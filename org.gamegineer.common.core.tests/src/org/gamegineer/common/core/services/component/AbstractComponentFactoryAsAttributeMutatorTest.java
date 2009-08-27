/*
 * AbstractComponentFactoryAsAttributeMutatorTest.java
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
 * Created on May 21, 2008 at 9:59:00 PM.
 */

package org.gamegineer.common.core.services.component;

import org.gamegineer.common.core.services.component.util.attribute.AbstractAttributeMutatorTestCase;
import org.gamegineer.common.core.services.component.util.attribute.IAttributeMutator;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.core.services.component.AbstractComponentFactory}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.common.core.services.component.util.attribute.IAttributeMutator}
 * interface.
 */
public final class AbstractComponentFactoryAsAttributeMutatorTest
    extends AbstractAttributeMutatorTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractComponentFactoryAsAttributeMutatorTest} class.
     */
    public AbstractComponentFactoryAsAttributeMutatorTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.services.component.util.attribute.AbstractAttributeMutatorTestCase#createAttributeMutator()
     */
    @Override
    protected IAttributeMutator createAttributeMutator()
    {
        return new AbstractComponentFactory()
        {
            public Object createComponent(
                @SuppressWarnings( "unused" )
                final IComponentCreationContext context )
            {
                throw new UnsupportedOperationException();
            }
        };
    }
}
