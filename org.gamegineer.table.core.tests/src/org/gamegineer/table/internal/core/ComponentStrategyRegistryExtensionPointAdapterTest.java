/*
 * ComponentStrategyRegistryExtensionPointAdapterTest.java
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
 * Created on Aug 3, 2012 at 10:58:51 PM.
 */

package org.gamegineer.table.internal.core;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.core.runtime.IConfigurationElement;
import org.gamegineer.common.core.util.registry.test.AbstractAbstractRegistryExtensionPointAdapterTestCase;
import org.gamegineer.table.core.ComponentStrategyId;
import org.gamegineer.table.core.IComponentStrategy;

/**
 * A fixture for testing the
 * {@link ComponentStrategyRegistryExtensionPointAdapter} class.
 */
public final class ComponentStrategyRegistryExtensionPointAdapterTest
    extends AbstractAbstractRegistryExtensionPointAdapterTestCase<ComponentStrategyRegistryExtensionPointAdapter, ComponentStrategyId, IComponentStrategy>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ComponentStrategyRegistryExtensionPointAdapterTest} class.
     */
    public ComponentStrategyRegistryExtensionPointAdapterTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.test.util.registry.AbstractAbstractRegistryExtensionPointAdapterTestCase#configureConfigurationElement(org.eclipse.core.runtime.IConfigurationElement, org.easymock.IMocksControl)
     */
    @Override
    protected IComponentStrategy configureConfigurationElement(
        final IConfigurationElement configurationElement,
        final IMocksControl mocksControl )
        throws Exception
    {
        final IComponentStrategy expectedObject = mocksControl.createMock( IComponentStrategy.class );
        EasyMock.expect( configurationElement.getAttribute( "id" ) ).andReturn( "expected-id" ); //$NON-NLS-1$ //$NON-NLS-2$
        EasyMock.expect( configurationElement.createExecutableExtension( "className" ) ).andReturn( expectedObject ); //$NON-NLS-1$
        return expectedObject;
    }

    /*
     * @see org.gamegineer.common.core.test.util.registry.AbstractAbstractRegistryExtensionPointAdapterTestCase#createObject(org.gamegineer.common.core.util.registry.AbstractRegistryExtensionPointAdapter, org.eclipse.core.runtime.IConfigurationElement)
     */
    @Override
    protected IComponentStrategy createObject(
        final ComponentStrategyRegistryExtensionPointAdapter registryExtensionPointAdapter,
        final IConfigurationElement configurationElement )
    {
        return registryExtensionPointAdapter.createObject( configurationElement );
    }

    /*
     * @see org.gamegineer.common.core.test.util.registry.AbstractAbstractRegistryExtensionPointAdapterTestCase#createRegistryExtensionPointAdapter()
     */
    @Override
    protected ComponentStrategyRegistryExtensionPointAdapter createRegistryExtensionPointAdapter()
    {
        return new ComponentStrategyRegistryExtensionPointAdapter();
    }

    /*
     * @see org.gamegineer.common.core.test.util.registry.AbstractAbstractRegistryExtensionPointAdapterTestCase#getExtensionPointId(org.gamegineer.common.core.util.registry.AbstractRegistryExtensionPointAdapter)
     */
    @Override
    protected String getExtensionPointId(
        final ComponentStrategyRegistryExtensionPointAdapter registryExtensionPointAdapter )
    {
        return registryExtensionPointAdapter.getExtensionPointId();
    }
}
