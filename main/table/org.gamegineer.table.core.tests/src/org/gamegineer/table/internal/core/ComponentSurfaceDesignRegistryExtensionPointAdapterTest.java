/*
 * ComponentSurfaceDesignRegistryExtensionPointAdapterTest.java
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
 * Created on Apr 7, 2012 at 9:48:00 PM.
 */

package org.gamegineer.table.internal.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.core.runtime.IConfigurationElement;
import org.gamegineer.common.core.util.registry.test.AbstractAbstractRegistryExtensionPointAdapterTestCase;
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.core.ComponentSurfaceDesignId;

/**
 * A fixture for testing the
 * {@link ComponentSurfaceDesignRegistryExtensionPointAdapter} class.
 */
public final class ComponentSurfaceDesignRegistryExtensionPointAdapterTest
    extends AbstractAbstractRegistryExtensionPointAdapterTestCase<ComponentSurfaceDesignRegistryExtensionPointAdapter, ComponentSurfaceDesignId, ComponentSurfaceDesign>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ComponentSurfaceDesignRegistryExtensionPointAdapterTest} class.
     */
    public ComponentSurfaceDesignRegistryExtensionPointAdapterTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.test.util.registry.AbstractAbstractRegistryExtensionPointAdapterTestCase#assertObjectEquals(java.lang.Object, java.lang.Object)
     */
    @Override
    protected void assertObjectEquals(
        final ComponentSurfaceDesign expected,
        final ComponentSurfaceDesign actual )
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
            assertEquals( expected.getSize(), actual.getSize() );
        }
    }

    /*
     * @see org.gamegineer.common.core.test.util.registry.AbstractAbstractRegistryExtensionPointAdapterTestCase#configureConfigurationElement(org.eclipse.core.runtime.IConfigurationElement, org.easymock.IMocksControl)
     */
    @Override
    protected ComponentSurfaceDesign configureConfigurationElement(
        final IConfigurationElement configurationElement,
        @SuppressWarnings( "unused" )
        final IMocksControl mocksControl )
    {
        final ComponentSurfaceDesignId id = ComponentSurfaceDesignId.fromString( "expected-id" ); //$NON-NLS-1$
        final int width = 2112;
        final int height = 42;
        EasyMock.expect( configurationElement.getAttribute( "id" ) ).andReturn( id.toString() ); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getAttribute( "width" ) ).andReturn( String.valueOf( width ) ); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getAttribute( "height" ) ).andReturn( String.valueOf( height ) ); //$NON-NLS-1$
        return new ComponentSurfaceDesign( id, width, height );
    }

    /*
     * @see org.gamegineer.common.core.test.util.registry.AbstractAbstractRegistryExtensionPointAdapterTestCase#createObject(org.gamegineer.common.core.util.registry.AbstractRegistryExtensionPointAdapter, org.eclipse.core.runtime.IConfigurationElement)
     */
    @Override
    protected ComponentSurfaceDesign createObject(
        final ComponentSurfaceDesignRegistryExtensionPointAdapter registryExtensionPointAdapter,
        final IConfigurationElement configurationElement )
    {
        return registryExtensionPointAdapter.createObject( configurationElement );
    }

    /*
     * @see org.gamegineer.common.core.test.util.registry.AbstractAbstractRegistryExtensionPointAdapterTestCase#createRegistryExtensionPointAdapter()
     */
    @Override
    protected ComponentSurfaceDesignRegistryExtensionPointAdapter createRegistryExtensionPointAdapter()
    {
        return new ComponentSurfaceDesignRegistryExtensionPointAdapter();
    }

    /*
     * @see org.gamegineer.common.core.test.util.registry.AbstractAbstractRegistryExtensionPointAdapterTestCase#getExtensionPointId(org.gamegineer.common.core.util.registry.AbstractRegistryExtensionPointAdapter)
     */
    @Override
    protected String getExtensionPointId(
        final ComponentSurfaceDesignRegistryExtensionPointAdapter registryExtensionPointAdapter )
    {
        return registryExtensionPointAdapter.getExtensionPointId();
    }
}
