/*
 * ComponentSurfaceDesignRegistryExtensionPointAdapterTest.java
 * Copyright 2008-2012 Gamegineer.org
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
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.core.IComponentSurfaceDesign;
import org.gamegineer.table.core.IComponentSurfaceDesignRegistry;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.ComponentSurfaceDesignRegistryExtensionPointAdapter}
 * class.
 */
public final class ComponentSurfaceDesignRegistryExtensionPointAdapterTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The component surface design registry extension point adapter under test
     * in the fixture.
     */
    private ComponentSurfaceDesignRegistryExtensionPointAdapter componentSurfaceDesignRegistryExtensionPointAdapter_;

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;


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

    /**
     * Sets up the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Before
    public void setUp()
        throws Exception
    {
        mocksControl_ = EasyMock.createControl();
        componentSurfaceDesignRegistryExtensionPointAdapter_ = new ComponentSurfaceDesignRegistryExtensionPointAdapter();
    }

    /**
     * Ensures the {@code added(IExtension)} method registers all component
     * surface designs associated with the specified extensions with the
     * component surface design registry.
     */
    @Test
    public void testAddedFromExtension()
    {
        final ComponentSurfaceDesignId expectedId = ComponentSurfaceDesignId.fromString( "expected-id" ); //$NON-NLS-1$
        final int expectedWidth = 2112;
        final int expectedHeight = 42;
        final IExtensionRegistry extensionRegistry = mocksControl_.createMock( IExtensionRegistry.class );
        final IExtension extension = mocksControl_.createMock( IExtension.class );
        final IConfigurationElement configurationElement = mocksControl_.createMock( IConfigurationElement.class );
        EasyMock.expect( configurationElement.getAttribute( "id" ) ).andReturn( expectedId.toString() ); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getAttribute( "width" ) ).andReturn( String.valueOf( expectedWidth ) ); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getAttribute( "height" ) ).andReturn( String.valueOf( expectedHeight ) ); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getDeclaringExtension() ).andReturn( extension );
        EasyMock.expect( extension.getNamespaceIdentifier() ).andReturn( "namespace" ); //$NON-NLS-1$
        EasyMock.expect( extension.getSimpleIdentifier() ).andReturn( "simple-id" ); //$NON-NLS-1$
        EasyMock.expect( extension.getConfigurationElements() ).andReturn( new IConfigurationElement[] {
            configurationElement
        } );
        final IComponentSurfaceDesignRegistry componentSurfaceDesignRegistry = mocksControl_.createMock( IComponentSurfaceDesignRegistry.class );
        final Capture<IComponentSurfaceDesign> componentSurfaceDesignCapture = new Capture<IComponentSurfaceDesign>();
        componentSurfaceDesignRegistry.registerComponentSurfaceDesign( EasyMock.capture( componentSurfaceDesignCapture ) );
        mocksControl_.replay();
        componentSurfaceDesignRegistryExtensionPointAdapter_.bindComponentSurfaceDesignRegistry( componentSurfaceDesignRegistry );
        componentSurfaceDesignRegistryExtensionPointAdapter_.bindExtensionRegistry( extensionRegistry );

        componentSurfaceDesignRegistryExtensionPointAdapter_.added( new IExtension[] {
            extension
        } );

        mocksControl_.verify();
        assertEquals( expectedId, componentSurfaceDesignCapture.getValue().getId() );
        assertEquals( expectedWidth, componentSurfaceDesignCapture.getValue().getSize().width );
        assertEquals( expectedHeight, componentSurfaceDesignCapture.getValue().getSize().height );
    }

    /**
     * Ensures the {@code bindComponentSurfaceDesignRegistry} method throws an
     * exception when the component surface design registry is already bound.
     */
    @Test( expected = IllegalStateException.class )
    public void testBindComponentSurfaceDesignRegistry_AlreadyBound()
    {
        componentSurfaceDesignRegistryExtensionPointAdapter_.bindComponentSurfaceDesignRegistry( mocksControl_.createMock( IComponentSurfaceDesignRegistry.class ) );

        componentSurfaceDesignRegistryExtensionPointAdapter_.bindComponentSurfaceDesignRegistry( mocksControl_.createMock( IComponentSurfaceDesignRegistry.class ) );
    }

    /**
     * Ensures the {@code bindComponentSurfaceDesignRegistry} method throws an
     * exception when passed a {@code null} component surface design registry.
     */
    @Test( expected = NullPointerException.class )
    public void testBindComponentSurfaceDesignRegistry_ComponentSurfaceDesignRegistry_Null()
    {
        componentSurfaceDesignRegistryExtensionPointAdapter_.bindComponentSurfaceDesignRegistry( null );
    }

    /**
     * Ensures the {@code bindExtensionRegistry} method throws an exception when
     * the extension registry is already bound.
     */
    @Test( expected = IllegalStateException.class )
    public void testBindExtensionRegistry_AlreadyBound()
    {
        componentSurfaceDesignRegistryExtensionPointAdapter_.bindExtensionRegistry( mocksControl_.createMock( IExtensionRegistry.class ) );

        componentSurfaceDesignRegistryExtensionPointAdapter_.bindExtensionRegistry( mocksControl_.createMock( IExtensionRegistry.class ) );
    }

    /**
     * Ensures the {@code bindExtensionRegistry} method throws an exception when
     * passed a {@code null} extension registry.
     */
    @Test( expected = NullPointerException.class )
    public void testBindExtensionRegistry_ExtensionRegistry_Null()
    {
        componentSurfaceDesignRegistryExtensionPointAdapter_.bindExtensionRegistry( null );
    }

    /**
     * Ensures the {@code removed(IExtension)} method unregisters all component
     * surface designs associated with the specified extensions from the
     * component surface design registry.
     */
    @Test
    public void testRemovedFromExtension()
    {
        final ComponentSurfaceDesignId expectedId = ComponentSurfaceDesignId.fromString( "expected-id" ); //$NON-NLS-1$
        final int expectedWidth = 2112;
        final int expectedHeight = 42;
        final IExtensionRegistry extensionRegistry = mocksControl_.createMock( IExtensionRegistry.class );
        final IExtension extension = mocksControl_.createMock( IExtension.class );
        final IConfigurationElement configurationElement = mocksControl_.createMock( IConfigurationElement.class );
        EasyMock.expect( configurationElement.getAttribute( "id" ) ).andReturn( expectedId.toString() ); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getAttribute( "width" ) ).andReturn( String.valueOf( expectedWidth ) ); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getAttribute( "height" ) ).andReturn( String.valueOf( expectedHeight ) ); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getDeclaringExtension() ).andReturn( extension );
        EasyMock.expect( extension.getNamespaceIdentifier() ).andReturn( "namespace" ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( extension.getSimpleIdentifier() ).andReturn( "simple-id" ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( extension.getConfigurationElements() ).andReturn( new IConfigurationElement[] {
            configurationElement
        } ).anyTimes();
        final IComponentSurfaceDesignRegistry componentSurfaceDesignRegistry = mocksControl_.createMock( IComponentSurfaceDesignRegistry.class );
        componentSurfaceDesignRegistry.registerComponentSurfaceDesign( EasyMock.notNull( IComponentSurfaceDesign.class ) );
        final Capture<IComponentSurfaceDesign> componentSurfaceDesignCapture = new Capture<IComponentSurfaceDesign>();
        componentSurfaceDesignRegistry.unregisterComponentSurfaceDesign( EasyMock.capture( componentSurfaceDesignCapture ) );
        mocksControl_.replay();
        componentSurfaceDesignRegistryExtensionPointAdapter_.bindComponentSurfaceDesignRegistry( componentSurfaceDesignRegistry );
        componentSurfaceDesignRegistryExtensionPointAdapter_.bindExtensionRegistry( extensionRegistry );
        componentSurfaceDesignRegistryExtensionPointAdapter_.added( new IExtension[] {
            extension
        } );

        componentSurfaceDesignRegistryExtensionPointAdapter_.removed( new IExtension[] {
            extension
        } );

        mocksControl_.verify();
        assertEquals( expectedId, componentSurfaceDesignCapture.getValue().getId() );
        assertEquals( expectedWidth, componentSurfaceDesignCapture.getValue().getSize().width );
        assertEquals( expectedHeight, componentSurfaceDesignCapture.getValue().getSize().height );
    }

    /**
     * Ensures the {@code unbindComponentSurfaceDesignRegistry} method throws an
     * exception when passed a component surface design registry that is not
     * bound.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnbindComponentSurfaceDesignRegistry_ComponentSurfaceDesignRegistry_NotBound()
    {
        componentSurfaceDesignRegistryExtensionPointAdapter_.bindComponentSurfaceDesignRegistry( mocksControl_.createMock( IComponentSurfaceDesignRegistry.class ) );

        componentSurfaceDesignRegistryExtensionPointAdapter_.unbindComponentSurfaceDesignRegistry( mocksControl_.createMock( IComponentSurfaceDesignRegistry.class ) );
    }

    /**
     * Ensures the {@code unbindComponentSurfaceDesignRegistry} method throws an
     * exception when passed a {@code null} component surface design registry.
     */
    @Test( expected = NullPointerException.class )
    public void testUnbindComponentSurfaceDesignRegistry_ComponentSurfaceDesignRegistry_Null()
    {
        componentSurfaceDesignRegistryExtensionPointAdapter_.unbindComponentSurfaceDesignRegistry( null );
    }

    /**
     * Ensures the {@code unbindExtensionRegistry} method throws an exception
     * when passed an extension registry that is not bound.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnbindExtensionRegistry_ExtensionRegistry_NotBound()
    {
        componentSurfaceDesignRegistryExtensionPointAdapter_.bindExtensionRegistry( mocksControl_.createMock( IExtensionRegistry.class ) );

        componentSurfaceDesignRegistryExtensionPointAdapter_.unbindExtensionRegistry( mocksControl_.createMock( IExtensionRegistry.class ) );
    }

    /**
     * Ensures the {@code unbindExtensionRegistry} method throws an exception
     * when passed a {@code null} extension registry.
     */
    @Test( expected = NullPointerException.class )
    public void testUnbindExtensionRegistry_ExtensionRegistry_Null()
    {
        componentSurfaceDesignRegistryExtensionPointAdapter_.unbindExtensionRegistry( null );
    }
}
