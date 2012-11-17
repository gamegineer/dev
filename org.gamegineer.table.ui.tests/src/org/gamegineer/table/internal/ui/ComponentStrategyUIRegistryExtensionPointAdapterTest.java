/*
 * ComponentStrategyUIRegistryExtensionPointAdapterTest.java
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
 * Created on Sep 27, 2012 at 8:59:53 PM.
 */

package org.gamegineer.table.internal.ui;

import static org.junit.Assert.assertEquals;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.gamegineer.table.ui.IComponentStrategyUI;
import org.gamegineer.table.ui.IComponentStrategyUIRegistry;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.ComponentStrategyUIRegistryExtensionPointAdapter}
 * class.
 */
public final class ComponentStrategyUIRegistryExtensionPointAdapterTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The component strategy user interface registry extension point adapter
     * under test in the fixture.
     */
    private ComponentStrategyUIRegistryExtensionPointAdapter componentStrategyUIRegistryExtensionPointAdapter_;

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;


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
        componentStrategyUIRegistryExtensionPointAdapter_ = new ComponentStrategyUIRegistryExtensionPointAdapter();
    }

    /**
     * Ensures the
     * {@link ComponentStrategyUIRegistryExtensionPointAdapter#added(IExtension[])}
     * method registers all component strategy user interfaces associated with
     * the specified extensions with the component strategy user interface
     * registry.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testAddedFromExtension()
        throws Exception
    {
        final IComponentStrategyUI expectedComponentStrategyUI = mocksControl_.createMock( IComponentStrategyUI.class );
        final IExtensionRegistry extensionRegistry = mocksControl_.createMock( IExtensionRegistry.class );
        final IExtension extension = mocksControl_.createMock( IExtension.class );
        final IConfigurationElement configurationElement = mocksControl_.createMock( IConfigurationElement.class );
        EasyMock.expect( configurationElement.getAttribute( "id" ) ).andReturn( "expected-id" ); //$NON-NLS-1$ //$NON-NLS-2$
        EasyMock.expect( configurationElement.createExecutableExtension( "className" ) ).andReturn( expectedComponentStrategyUI ); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getDeclaringExtension() ).andReturn( extension ).anyTimes();
        EasyMock.expect( extension.getNamespaceIdentifier() ).andReturn( BundleConstants.SYMBOLIC_NAME ).anyTimes();
        EasyMock.expect( extension.getSimpleIdentifier() ).andReturn( "simple-id" ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( extension.getConfigurationElements() ).andReturn( new IConfigurationElement[] {
            configurationElement
        } ).anyTimes();
        final IComponentStrategyUIRegistry componentStrategyUIRegistry = mocksControl_.createMock( IComponentStrategyUIRegistry.class );
        final Capture<IComponentStrategyUI> componentStrategyUICapture = new Capture<IComponentStrategyUI>();
        componentStrategyUIRegistry.register( EasyMock.capture( componentStrategyUICapture ) );
        mocksControl_.replay();
        componentStrategyUIRegistryExtensionPointAdapter_.bindComponentStrategyUIRegistry( componentStrategyUIRegistry );
        componentStrategyUIRegistryExtensionPointAdapter_.bindExtensionRegistry( extensionRegistry );

        componentStrategyUIRegistryExtensionPointAdapter_.added( new IExtension[] {
            extension
        } );

        mocksControl_.verify();
        assertEquals( expectedComponentStrategyUI, componentStrategyUICapture.getValue() );
    }

    /**
     * Ensures the
     * {@link ComponentStrategyUIRegistryExtensionPointAdapter#bindComponentStrategyUIRegistry}
     * method throws an exception when the component strategy user interface
     * registry is already bound.
     */
    @Test( expected = IllegalStateException.class )
    public void testBindComponentStrategyUIRegistry_AlreadyBound()
    {
        componentStrategyUIRegistryExtensionPointAdapter_.bindComponentStrategyUIRegistry( mocksControl_.createMock( IComponentStrategyUIRegistry.class ) );

        componentStrategyUIRegistryExtensionPointAdapter_.bindComponentStrategyUIRegistry( mocksControl_.createMock( IComponentStrategyUIRegistry.class ) );
    }

    /**
     * Ensures the
     * {@link ComponentStrategyUIRegistryExtensionPointAdapter#bindComponentStrategyUIRegistry}
     * method throws an exception when passed a {@code null} component strategy
     * user interface registry.
     */
    @Test( expected = NullPointerException.class )
    public void testBindComponentStrategyUIRegistry_ComponentStrategyUIRegistry_Null()
    {
        componentStrategyUIRegistryExtensionPointAdapter_.bindComponentStrategyUIRegistry( null );
    }

    /**
     * Ensures the
     * {@link ComponentStrategyUIRegistryExtensionPointAdapter#bindExtensionRegistry}
     * method throws an exception when the extension registry is already bound.
     */
    @Test( expected = IllegalStateException.class )
    public void testBindExtensionRegistry_AlreadyBound()
    {
        componentStrategyUIRegistryExtensionPointAdapter_.bindExtensionRegistry( mocksControl_.createMock( IExtensionRegistry.class ) );

        componentStrategyUIRegistryExtensionPointAdapter_.bindExtensionRegistry( mocksControl_.createMock( IExtensionRegistry.class ) );
    }

    /**
     * Ensures the
     * {@link ComponentStrategyUIRegistryExtensionPointAdapter#bindExtensionRegistry}
     * method throws an exception when passed a {@code null} extension registry.
     */
    @Test( expected = NullPointerException.class )
    public void testBindExtensionRegistry_ExtensionRegistry_Null()
    {
        componentStrategyUIRegistryExtensionPointAdapter_.bindExtensionRegistry( null );
    }

    /**
     * Ensures the
     * {@link ComponentStrategyUIRegistryExtensionPointAdapter#removed(IExtension[])}
     * method unregisters all component strategy user interfaces associated with
     * the specified extensions from the component strategy user interface
     * registry.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testRemovedFromExtension()
        throws Exception
    {
        final IComponentStrategyUI expectedComponentStrategyUI = mocksControl_.createMock( IComponentStrategyUI.class );
        final IExtensionRegistry extensionRegistry = mocksControl_.createMock( IExtensionRegistry.class );
        final IExtension extension = mocksControl_.createMock( IExtension.class );
        final IConfigurationElement configurationElement = mocksControl_.createMock( IConfigurationElement.class );
        EasyMock.expect( configurationElement.getAttribute( "id" ) ).andReturn( "expected-id" ); //$NON-NLS-1$ //$NON-NLS-2$
        EasyMock.expect( configurationElement.createExecutableExtension( "className" ) ).andReturn( expectedComponentStrategyUI ); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getDeclaringExtension() ).andReturn( extension ).anyTimes();
        EasyMock.expect( extension.getNamespaceIdentifier() ).andReturn( BundleConstants.SYMBOLIC_NAME ).anyTimes();
        EasyMock.expect( extension.getSimpleIdentifier() ).andReturn( "simple-id" ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( extension.getConfigurationElements() ).andReturn( new IConfigurationElement[] {
            configurationElement
        } ).anyTimes();
        final IComponentStrategyUIRegistry componentStrategyUIRegistry = mocksControl_.createMock( IComponentStrategyUIRegistry.class );
        componentStrategyUIRegistry.register( EasyMock.notNull( IComponentStrategyUI.class ) );
        final Capture<IComponentStrategyUI> componentStrategyUICapture = new Capture<IComponentStrategyUI>();
        componentStrategyUIRegistry.unregister( EasyMock.capture( componentStrategyUICapture ) );
        mocksControl_.replay();
        componentStrategyUIRegistryExtensionPointAdapter_.bindComponentStrategyUIRegistry( componentStrategyUIRegistry );
        componentStrategyUIRegistryExtensionPointAdapter_.bindExtensionRegistry( extensionRegistry );
        componentStrategyUIRegistryExtensionPointAdapter_.added( new IExtension[] {
            extension
        } );

        componentStrategyUIRegistryExtensionPointAdapter_.removed( new IExtension[] {
            extension
        } );

        mocksControl_.verify();
        assertEquals( expectedComponentStrategyUI, componentStrategyUICapture.getValue() );
    }

    /**
     * Ensures the
     * {@link ComponentStrategyUIRegistryExtensionPointAdapter#unbindComponentStrategyUIRegistry}
     * method throws an exception when passed a component strategy user
     * interface registry that is not bound.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnbindComponentStrategyUIRegistry_ComponentStrategyUIRegistry_NotBound()
    {
        componentStrategyUIRegistryExtensionPointAdapter_.bindComponentStrategyUIRegistry( mocksControl_.createMock( IComponentStrategyUIRegistry.class ) );

        componentStrategyUIRegistryExtensionPointAdapter_.unbindComponentStrategyUIRegistry( mocksControl_.createMock( IComponentStrategyUIRegistry.class ) );
    }

    /**
     * Ensures the
     * {@link ComponentStrategyUIRegistryExtensionPointAdapter#unbindComponentStrategyUIRegistry}
     * method throws an exception when passed a {@code null} component strategy
     * user interface registry.
     */
    @Test( expected = NullPointerException.class )
    public void testUnbindComponentStrategyUIRegistry_ComponentStrategyUIRegistry_Null()
    {
        componentStrategyUIRegistryExtensionPointAdapter_.unbindComponentStrategyUIRegistry( null );
    }

    /**
     * Ensures the
     * {@link ComponentStrategyUIRegistryExtensionPointAdapter#unbindExtensionRegistry}
     * method throws an exception when passed an extension registry that is not
     * bound.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnbindExtensionRegistry_ExtensionRegistry_NotBound()
    {
        componentStrategyUIRegistryExtensionPointAdapter_.bindExtensionRegistry( mocksControl_.createMock( IExtensionRegistry.class ) );

        componentStrategyUIRegistryExtensionPointAdapter_.unbindExtensionRegistry( mocksControl_.createMock( IExtensionRegistry.class ) );
    }

    /**
     * Ensures the
     * {@link ComponentStrategyUIRegistryExtensionPointAdapter#unbindExtensionRegistry}
     * method throws an exception when passed a {@code null} extension registry.
     */
    @Test( expected = NullPointerException.class )
    public void testUnbindExtensionRegistry_ExtensionRegistry_Null()
    {
        componentStrategyUIRegistryExtensionPointAdapter_.unbindExtensionRegistry( null );
    }
}
