/*
 * ComponentStrategyUIRegistryExtensionPointAdapterTest.java
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
 * Created on Sep 27, 2012 at 8:59:53 PM.
 */

package org.gamegineer.table.internal.ui;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.core.runtime.IConfigurationElement;
import org.gamegineer.common.core.util.registry.test.AbstractAbstractRegistryExtensionPointAdapterTestCase;
import org.gamegineer.table.core.ComponentStrategyId;
import org.gamegineer.table.ui.IComponentStrategyUI;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.ComponentStrategyUIRegistryExtensionPointAdapter}
 * class.
 */
public final class ComponentStrategyUIRegistryExtensionPointAdapterTest
    extends AbstractAbstractRegistryExtensionPointAdapterTestCase<ComponentStrategyUIRegistryExtensionPointAdapter, ComponentStrategyId, IComponentStrategyUI>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ComponentStrategyUIRegistryExtensionPointAdapterTest} class.
     */
    public ComponentStrategyUIRegistryExtensionPointAdapterTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.test.util.registry.AbstractAbstractRegistryExtensionPointAdapterTestCase#configureConfigurationElement(org.eclipse.core.runtime.IConfigurationElement, org.easymock.IMocksControl)
     */
    @Override
    protected IComponentStrategyUI configureConfigurationElement(
        final IConfigurationElement configurationElement,
        final IMocksControl mocksControl )
        throws Exception
    {
        final IComponentStrategyUI expectedObject = mocksControl.createMock( IComponentStrategyUI.class );
        EasyMock.expect( configurationElement.getAttribute( "id" ) ).andReturn( "expected-id" ); //$NON-NLS-1$ //$NON-NLS-2$
        EasyMock.expect( configurationElement.createExecutableExtension( "className" ) ).andReturn( expectedObject ); //$NON-NLS-1$
        return expectedObject;
    }

    /*
     * @see org.gamegineer.common.core.test.util.registry.AbstractAbstractRegistryExtensionPointAdapterTestCase#createObject(org.gamegineer.common.core.util.registry.AbstractRegistryExtensionPointAdapter, org.eclipse.core.runtime.IConfigurationElement)
     */
    @Override
    protected IComponentStrategyUI createObject(
        final ComponentStrategyUIRegistryExtensionPointAdapter registryExtensionPointAdapter,
        final IConfigurationElement configurationElement )
    {
        return registryExtensionPointAdapter.createObject( configurationElement );
    }

    /*
     * @see org.gamegineer.common.core.test.util.registry.AbstractAbstractRegistryExtensionPointAdapterTestCase#createRegistryExtensionPointAdapter()
     */
    @Override
    protected ComponentStrategyUIRegistryExtensionPointAdapter createRegistryExtensionPointAdapter()
    {
        return new ComponentStrategyUIRegistryExtensionPointAdapter();
    }

    /*
     * @see org.gamegineer.common.core.test.util.registry.AbstractAbstractRegistryExtensionPointAdapterTestCase#getExtensionPointId(org.gamegineer.common.core.util.registry.AbstractRegistryExtensionPointAdapter)
     */
    @Override
    protected String getExtensionPointId(
        final ComponentStrategyUIRegistryExtensionPointAdapter registryExtensionPointAdapter )
    {
        return registryExtensionPointAdapter.getExtensionPointId();
    }
}
