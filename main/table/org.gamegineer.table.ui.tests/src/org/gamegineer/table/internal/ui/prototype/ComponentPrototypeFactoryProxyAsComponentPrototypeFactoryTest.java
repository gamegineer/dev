/*
 * ComponentPrototypeFactoryProxyAsComponentPrototypeFactoryTest.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
 * Created on Oct 18, 2012 at 11:24:08 PM.
 */

package org.gamegineer.table.internal.ui.prototype;

import org.easymock.EasyMock;
import org.eclipse.core.runtime.IConfigurationElement;
import org.gamegineer.table.ui.prototype.AbstractComponentPrototypeFactoryTestCase;
import org.gamegineer.table.ui.prototype.IComponentPrototypeFactory;

/**
 * A fixture for testing the {@link ComponentPrototypeFactoryProxy} class to
 * ensure it does not violate the contract of the
 * {@link IComponentPrototypeFactory} interface.
 */
public final class ComponentPrototypeFactoryProxyAsComponentPrototypeFactoryTest
    extends AbstractComponentPrototypeFactoryTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ComponentPrototypeFactoryProxyAsComponentPrototypeFactoryTest}
     * class.
     */
    public ComponentPrototypeFactoryProxyAsComponentPrototypeFactoryTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.ui.prototype.AbstractComponentPrototypeFactoryTestCase#createComponentPrototypeFactory()
     */
    @Override
    protected IComponentPrototypeFactory createComponentPrototypeFactory()
    {
        return new ComponentPrototypeFactoryProxy( EasyMock.createMock( IConfigurationElement.class ), "className" ); //$NON-NLS-1$
    }
}