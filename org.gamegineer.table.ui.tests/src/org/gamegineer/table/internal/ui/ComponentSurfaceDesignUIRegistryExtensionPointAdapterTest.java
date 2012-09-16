/*
 * ComponentSurfaceDesignUIRegistryExtensionPointAdapterTest.java
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
 * Created on Apr 23, 2012 at 8:23:21 PM.
 */

package org.gamegineer.table.internal.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.core.runtime.ContributorFactoryOSGi;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.internal.ui.util.swing.IconProxy;
import org.gamegineer.table.ui.ComponentSurfaceDesignUI;
import org.gamegineer.table.ui.IComponentSurfaceDesignUIRegistry;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.ComponentSurfaceDesignUIRegistryExtensionPointAdapter}
 * class.
 */
public final class ComponentSurfaceDesignUIRegistryExtensionPointAdapterTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The component surface design user interface registry extension point
     * adapter under test in the fixture.
     */
    private ComponentSurfaceDesignUIRegistryExtensionPointAdapter componentSurfaceDesignUIRegistryExtensionPointAdapter_;

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;


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
        componentSurfaceDesignUIRegistryExtensionPointAdapter_ = new ComponentSurfaceDesignUIRegistryExtensionPointAdapter();
    }

    /**
     * Ensures the
     * {@link ComponentSurfaceDesignUIRegistryExtensionPointAdapter#added(IExtension[])}
     * method registers all component surface design user interfaces associated
     * with the specified extensions with the component surface design user
     * interface registry.
     */
    @Test
    public void testAddedFromExtension()
    {
        final ComponentSurfaceDesignId expectedId = ComponentSurfaceDesignId.fromString( "id" ); //$NON-NLS-1$
        final String expectedName = "name"; //$NON-NLS-1$
        final String expectedIconPath = "icons/cardSurfaces/back-blue.png"; //$NON-NLS-1$
        final IExtensionRegistry extensionRegistry = mocksControl_.createMock( IExtensionRegistry.class );
        final IExtension extension = mocksControl_.createMock( IExtension.class );
        final IContributor contributor = ContributorFactoryOSGi.createContributor( Activator.getDefault().getBundleContext().getBundle() );
        final IConfigurationElement configurationElement = mocksControl_.createMock( IConfigurationElement.class );
        EasyMock.expect( configurationElement.getAttribute( "id" ) ).andReturn( expectedId.toString() ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getAttribute( "name" ) ).andReturn( expectedName ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getAttribute( "icon" ) ).andReturn( expectedIconPath ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getNamespaceIdentifier() ).andReturn( BundleConstants.SYMBOLIC_NAME ).anyTimes();
        EasyMock.expect( configurationElement.getDeclaringExtension() ).andReturn( extension ).anyTimes();
        EasyMock.expect( configurationElement.getContributor() ).andReturn( contributor ).anyTimes();
        EasyMock.expect( extension.getNamespaceIdentifier() ).andReturn( BundleConstants.SYMBOLIC_NAME ).anyTimes();
        EasyMock.expect( extension.getSimpleIdentifier() ).andReturn( "simple-id" ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( extension.getConfigurationElements() ).andReturn( new IConfigurationElement[] {
            configurationElement
        } ).anyTimes();
        final IComponentSurfaceDesignUIRegistry componentSurfaceDesignUIRegistry = mocksControl_.createMock( IComponentSurfaceDesignUIRegistry.class );
        final Capture<ComponentSurfaceDesignUI> componentSurfaceDesignUICapture = new Capture<ComponentSurfaceDesignUI>();
        componentSurfaceDesignUIRegistry.registerComponentSurfaceDesignUI( EasyMock.capture( componentSurfaceDesignUICapture ) );
        mocksControl_.replay();
        componentSurfaceDesignUIRegistryExtensionPointAdapter_.bindComponentSurfaceDesignUIRegistry( componentSurfaceDesignUIRegistry );
        componentSurfaceDesignUIRegistryExtensionPointAdapter_.bindExtensionRegistry( extensionRegistry );

        componentSurfaceDesignUIRegistryExtensionPointAdapter_.added( new IExtension[] {
            extension
        } );

        mocksControl_.verify();
        assertEquals( expectedId, componentSurfaceDesignUICapture.getValue().getId() );
        assertEquals( expectedName, componentSurfaceDesignUICapture.getValue().getName() );
        assertTrue( componentSurfaceDesignUICapture.getValue().getIcon() instanceof IconProxy );
    }

    /**
     * Ensures the
     * {@link ComponentSurfaceDesignUIRegistryExtensionPointAdapter#bindComponentSurfaceDesignUIRegistry}
     * method throws an exception when the component surface design user
     * interface registry is already bound.
     */
    @Test( expected = IllegalStateException.class )
    public void testBindComponentSurfaceDesignUIRegistry_AlreadyBound()
    {
        componentSurfaceDesignUIRegistryExtensionPointAdapter_.bindComponentSurfaceDesignUIRegistry( mocksControl_.createMock( IComponentSurfaceDesignUIRegistry.class ) );

        componentSurfaceDesignUIRegistryExtensionPointAdapter_.bindComponentSurfaceDesignUIRegistry( mocksControl_.createMock( IComponentSurfaceDesignUIRegistry.class ) );
    }

    /**
     * Ensures the
     * {@link ComponentSurfaceDesignUIRegistryExtensionPointAdapter#bindComponentSurfaceDesignUIRegistry}
     * method throws an exception when passed a {@code null} component surface
     * design user interface registry.
     */
    @Test( expected = NullPointerException.class )
    public void testBindComponentSurfaceDesignUIRegistry_ComponentSurfaceDesignUIRegistry_Null()
    {
        componentSurfaceDesignUIRegistryExtensionPointAdapter_.bindComponentSurfaceDesignUIRegistry( null );
    }

    /**
     * Ensures the
     * {@link ComponentSurfaceDesignUIRegistryExtensionPointAdapter#bindExtensionRegistry}
     * method throws an exception when the extension registry is already bound.
     */
    @Test( expected = IllegalStateException.class )
    public void testBindExtensionRegistry_AlreadyBound()
    {
        componentSurfaceDesignUIRegistryExtensionPointAdapter_.bindExtensionRegistry( mocksControl_.createMock( IExtensionRegistry.class ) );

        componentSurfaceDesignUIRegistryExtensionPointAdapter_.bindExtensionRegistry( mocksControl_.createMock( IExtensionRegistry.class ) );
    }

    /**
     * Ensures the
     * {@link ComponentSurfaceDesignUIRegistryExtensionPointAdapter#bindExtensionRegistry}
     * method throws an exception when passed a {@code null} extension registry.
     */
    @Test( expected = NullPointerException.class )
    public void testBindExtensionRegistry_ExtensionRegistry_Null()
    {
        componentSurfaceDesignUIRegistryExtensionPointAdapter_.bindExtensionRegistry( null );
    }

    /**
     * Ensures the
     * {@link ComponentSurfaceDesignUIRegistryExtensionPointAdapter#removed(IExtension[])}
     * method unregisters all component surface design user interfaces
     * associated with the specified extensions from the component surface
     * design user interface registry.
     */
    @Test
    public void testRemovedFromExtension()
    {
        final ComponentSurfaceDesignId expectedId = ComponentSurfaceDesignId.fromString( "id" ); //$NON-NLS-1$
        final String expectedName = "name"; //$NON-NLS-1$
        final String expectedIconPath = "icons/cardSurfaces/back-blue.png"; //$NON-NLS-1$
        final IExtensionRegistry extensionRegistry = mocksControl_.createMock( IExtensionRegistry.class );
        final IExtension extension = mocksControl_.createMock( IExtension.class );
        final IContributor contributor = ContributorFactoryOSGi.createContributor( Activator.getDefault().getBundleContext().getBundle() );
        final IConfigurationElement configurationElement = mocksControl_.createMock( IConfigurationElement.class );
        EasyMock.expect( configurationElement.getAttribute( "id" ) ).andReturn( expectedId.toString() ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getAttribute( "name" ) ).andReturn( expectedName ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getAttribute( "icon" ) ).andReturn( expectedIconPath ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getNamespaceIdentifier() ).andReturn( BundleConstants.SYMBOLIC_NAME ).anyTimes();
        EasyMock.expect( configurationElement.getDeclaringExtension() ).andReturn( extension ).anyTimes();
        EasyMock.expect( configurationElement.getContributor() ).andReturn( contributor ).anyTimes();
        EasyMock.expect( extension.getNamespaceIdentifier() ).andReturn( BundleConstants.SYMBOLIC_NAME ).anyTimes();
        EasyMock.expect( extension.getSimpleIdentifier() ).andReturn( "simple-id" ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( extension.getConfigurationElements() ).andReturn( new IConfigurationElement[] {
            configurationElement
        } ).anyTimes();
        final IComponentSurfaceDesignUIRegistry componentSurfaceDesignUIRegistry = mocksControl_.createMock( IComponentSurfaceDesignUIRegistry.class );
        componentSurfaceDesignUIRegistry.registerComponentSurfaceDesignUI( EasyMock.notNull( ComponentSurfaceDesignUI.class ) );
        final Capture<ComponentSurfaceDesignUI> componentSurfaceDesignUICapture = new Capture<ComponentSurfaceDesignUI>();
        componentSurfaceDesignUIRegistry.unregisterComponentSurfaceDesignUI( EasyMock.capture( componentSurfaceDesignUICapture ) );
        mocksControl_.replay();
        componentSurfaceDesignUIRegistryExtensionPointAdapter_.bindComponentSurfaceDesignUIRegistry( componentSurfaceDesignUIRegistry );
        componentSurfaceDesignUIRegistryExtensionPointAdapter_.bindExtensionRegistry( extensionRegistry );
        componentSurfaceDesignUIRegistryExtensionPointAdapter_.added( new IExtension[] {
            extension
        } );

        componentSurfaceDesignUIRegistryExtensionPointAdapter_.removed( new IExtension[] {
            extension
        } );

        mocksControl_.verify();
        assertEquals( expectedId, componentSurfaceDesignUICapture.getValue().getId() );
        assertEquals( expectedName, componentSurfaceDesignUICapture.getValue().getName() );
        assertTrue( componentSurfaceDesignUICapture.getValue().getIcon() instanceof IconProxy );
    }

    /**
     * Ensures the
     * {@link ComponentSurfaceDesignUIRegistryExtensionPointAdapter#unbindComponentSurfaceDesignUIRegistry}
     * method throws an exception when passed a component surface design user
     * interface registry that is not bound.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnbindComponentSurfaceDesignUIRegistry_ComponentSurfaceDesignUIRegistry_NotBound()
    {
        componentSurfaceDesignUIRegistryExtensionPointAdapter_.bindComponentSurfaceDesignUIRegistry( mocksControl_.createMock( IComponentSurfaceDesignUIRegistry.class ) );

        componentSurfaceDesignUIRegistryExtensionPointAdapter_.unbindComponentSurfaceDesignUIRegistry( mocksControl_.createMock( IComponentSurfaceDesignUIRegistry.class ) );
    }

    /**
     * Ensures the
     * {@link ComponentSurfaceDesignUIRegistryExtensionPointAdapter#unbindComponentSurfaceDesignUIRegistry}
     * method throws an exception when passed a {@code null} component surface
     * design user interface registry.
     */
    @Test( expected = NullPointerException.class )
    public void testUnbindComponentSurfaceDesignUIRegistry_ComponentSurfaceDesignUIRegistry_Null()
    {
        componentSurfaceDesignUIRegistryExtensionPointAdapter_.unbindComponentSurfaceDesignUIRegistry( null );
    }

    /**
     * Ensures the
     * {@link ComponentSurfaceDesignUIRegistryExtensionPointAdapter#unbindExtensionRegistry}
     * method throws an exception when passed an extension registry that is not
     * bound.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnbindExtensionRegistry_ExtensionRegistry_NotBound()
    {
        componentSurfaceDesignUIRegistryExtensionPointAdapter_.bindExtensionRegistry( mocksControl_.createMock( IExtensionRegistry.class ) );

        componentSurfaceDesignUIRegistryExtensionPointAdapter_.unbindExtensionRegistry( mocksControl_.createMock( IExtensionRegistry.class ) );
    }

    /**
     * Ensures the
     * {@link ComponentSurfaceDesignUIRegistryExtensionPointAdapter#unbindExtensionRegistry}
     * method throws an exception when passed a {@code null} extension registry.
     */
    @Test( expected = NullPointerException.class )
    public void testUnbindExtensionRegistry_ExtensionRegistry_Null()
    {
        componentSurfaceDesignUIRegistryExtensionPointAdapter_.unbindExtensionRegistry( null );
    }
}
