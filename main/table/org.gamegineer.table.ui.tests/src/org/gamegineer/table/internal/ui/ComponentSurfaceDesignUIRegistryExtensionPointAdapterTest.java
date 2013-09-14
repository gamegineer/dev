/*
 * ComponentSurfaceDesignUIRegistryExtensionPointAdapterTest.java
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
 * Created on Apr 23, 2012 at 8:23:21 PM.
 */

package org.gamegineer.table.internal.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import javax.swing.Icon;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.core.runtime.ContributorFactoryOSGi;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IContributor;
import org.gamegineer.common.core.util.registry.test.AbstractAbstractRegistryExtensionPointAdapterTestCase;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.internal.ui.util.swing.IconProxy;
import org.gamegineer.table.ui.ComponentSurfaceDesignUI;

/**
 * A fixture for testing the
 * {@link ComponentSurfaceDesignUIRegistryExtensionPointAdapter} class.
 */
public final class ComponentSurfaceDesignUIRegistryExtensionPointAdapterTest
    extends AbstractAbstractRegistryExtensionPointAdapterTestCase<ComponentSurfaceDesignUIRegistryExtensionPointAdapter, ComponentSurfaceDesignId, ComponentSurfaceDesignUI>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ComponentSurfaceDesignUIRegistryExtensionPointAdapterTest} class.
     */
    public ComponentSurfaceDesignUIRegistryExtensionPointAdapterTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.util.registry.test.AbstractAbstractRegistryExtensionPointAdapterTestCase#assertObjectEquals(java.lang.Object, java.lang.Object)
     */
    @Override
    protected void assertObjectEquals(
        final ComponentSurfaceDesignUI expected,
        final ComponentSurfaceDesignUI actual )
    {
        if( expected == null )
        {
            assertNull( actual );
        }
        else if( actual == null )
        {
            assertNull( expected );
        }
        else
        {
            assertEquals( expected.getId(), actual.getId() );
            assertEquals( expected.getName(), actual.getName() );
            assertTrue( actual.getIcon() instanceof IconProxy );
        }
    }

    /*
     * @see org.gamegineer.common.core.util.registry.test.AbstractAbstractRegistryExtensionPointAdapterTestCase#configureConfigurationElement(org.eclipse.core.runtime.IConfigurationElement, org.easymock.IMocksControl)
     */
    @Override
    protected ComponentSurfaceDesignUI configureConfigurationElement(
        final IConfigurationElement configurationElement,
        final IMocksControl mocksControl )
    {
        final ComponentSurfaceDesignId id = ComponentSurfaceDesignId.fromString( "id" ); //$NON-NLS-1$
        final String name = "name"; //$NON-NLS-1$
        final String iconPath = "icons/components/null.png"; //$NON-NLS-1$
        final IContributor contributor = ContributorFactoryOSGi.createContributor( Activator.getDefault().getBundleContext().getBundle() );
        EasyMock.expect( configurationElement.getAttribute( "id" ) ).andReturn( id.toString() ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getAttribute( "name" ) ).andReturn( name ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getAttribute( "icon" ) ).andReturn( iconPath ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getContributor() ).andReturn( contributor ).anyTimes();
        return new ComponentSurfaceDesignUI( id, name, mocksControl.createMock( Icon.class ) );
    }

    /*
     * @see org.gamegineer.common.core.util.registry.test.AbstractAbstractRegistryExtensionPointAdapterTestCase#createObject(org.gamegineer.common.core.util.registry.AbstractRegistryExtensionPointAdapter, org.eclipse.core.runtime.IConfigurationElement)
     */
    @Override
    protected ComponentSurfaceDesignUI createObject(
        final ComponentSurfaceDesignUIRegistryExtensionPointAdapter registryExtensionPointAdapter,
        final IConfigurationElement configurationElement )
    {
        return registryExtensionPointAdapter.createObject( configurationElement );
    }

    /*
     * @see org.gamegineer.common.core.util.registry.test.AbstractAbstractRegistryExtensionPointAdapterTestCase#createRegistryExtensionPointAdapter()
     */
    @Override
    protected ComponentSurfaceDesignUIRegistryExtensionPointAdapter createRegistryExtensionPointAdapter()
    {
        return new ComponentSurfaceDesignUIRegistryExtensionPointAdapter();
    }

    /*
     * @see org.gamegineer.common.core.util.registry.test.AbstractAbstractRegistryExtensionPointAdapterTestCase#getExtensionPointId(org.gamegineer.common.core.util.registry.AbstractRegistryExtensionPointAdapter)
     */
    @Override
    protected String getExtensionPointId(
        final ComponentSurfaceDesignUIRegistryExtensionPointAdapter registryExtensionPointAdapter )
    {
        return registryExtensionPointAdapter.getExtensionPointId();
    }
}
