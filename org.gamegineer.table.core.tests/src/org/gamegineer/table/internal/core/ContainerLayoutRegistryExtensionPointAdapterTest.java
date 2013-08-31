/*
 * ContainerLayoutRegistryExtensionPointAdapterTest.java
 * Copyright 2008-2013 Gamegineer.org
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
 * Created on Aug 9, 2012 at 9:03:56 PM.
 */

package org.gamegineer.table.internal.core;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.core.runtime.IConfigurationElement;
import org.gamegineer.common.core.test.util.registry.AbstractAbstractRegistryExtensionPointAdapterTestCase;
import org.gamegineer.table.core.ContainerLayoutId;
import org.gamegineer.table.core.IContainerLayout;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.ContainerLayoutRegistryExtensionPointAdapter}
 * class.
 */
public final class ContainerLayoutRegistryExtensionPointAdapterTest
    extends AbstractAbstractRegistryExtensionPointAdapterTestCase<ContainerLayoutRegistryExtensionPointAdapter, ContainerLayoutId, IContainerLayout>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ContainerLayoutRegistryExtensionPointAdapterTest} class.
     */
    public ContainerLayoutRegistryExtensionPointAdapterTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.test.util.registry.AbstractAbstractRegistryExtensionPointAdapterTestCase#configureConfigurationElement(org.eclipse.core.runtime.IConfigurationElement, org.easymock.IMocksControl)
     */
    @Override
    protected IContainerLayout configureConfigurationElement(
        final IConfigurationElement configurationElement,
        final IMocksControl mocksControl )
        throws Exception
    {
        final IContainerLayout expectedObject = mocksControl.createMock( IContainerLayout.class );
        EasyMock.expect( configurationElement.getAttribute( "id" ) ).andReturn( "expected-id" ); //$NON-NLS-1$ //$NON-NLS-2$
        EasyMock.expect( configurationElement.createExecutableExtension( "className" ) ).andReturn( expectedObject ); //$NON-NLS-1$
        return expectedObject;
    }

    /*
     * @see org.gamegineer.common.core.test.util.registry.AbstractAbstractRegistryExtensionPointAdapterTestCase#createObject(org.gamegineer.common.core.util.registry.AbstractRegistryExtensionPointAdapter, org.eclipse.core.runtime.IConfigurationElement)
     */
    @Override
    protected IContainerLayout createObject(
        final ContainerLayoutRegistryExtensionPointAdapter registryExtensionPointAdapter,
        final IConfigurationElement configurationElement )
    {
        return registryExtensionPointAdapter.createObject( configurationElement );
    }

    /*
     * @see org.gamegineer.common.core.test.util.registry.AbstractAbstractRegistryExtensionPointAdapterTestCase#createRegistryExtensionPointAdapter()
     */
    @Override
    protected ContainerLayoutRegistryExtensionPointAdapter createRegistryExtensionPointAdapter()
    {
        return new ContainerLayoutRegistryExtensionPointAdapter();
    }

    /*
     * @see org.gamegineer.common.core.test.util.registry.AbstractAbstractRegistryExtensionPointAdapterTestCase#getExtensionPointId(org.gamegineer.common.core.util.registry.AbstractRegistryExtensionPointAdapter)
     */
    @Override
    protected String getExtensionPointId(
        final ContainerLayoutRegistryExtensionPointAdapter registryExtensionPointAdapter )
    {
        return registryExtensionPointAdapter.getExtensionPointId();
    }
}
