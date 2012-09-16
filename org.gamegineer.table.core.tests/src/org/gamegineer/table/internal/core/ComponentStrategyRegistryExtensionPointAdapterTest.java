/*
 * ComponentStrategyRegistryExtensionPointAdapterTest.java
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
 * Created on Aug 3, 2012 at 10:58:51 PM.
 */

package org.gamegineer.table.internal.core;

import static org.junit.Assert.assertEquals;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.gamegineer.table.core.IComponentStrategy;
import org.gamegineer.table.core.IComponentStrategyRegistry;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.ComponentStrategyRegistryExtensionPointAdapter}
 * class.
 */
public final class ComponentStrategyRegistryExtensionPointAdapterTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The component strategy registry extension point adapter under test in the
     * fixture.
     */
    private ComponentStrategyRegistryExtensionPointAdapter componentStrategyRegistryExtensionPointAdapter_;

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;


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
        componentStrategyRegistryExtensionPointAdapter_ = new ComponentStrategyRegistryExtensionPointAdapter();
    }

    /**
     * Ensures the
     * {@link ComponentStrategyRegistryExtensionPointAdapter#added(IExtension[])}
     * method registers all component strategies associated with the specified
     * extensions with the component strategy registry.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testAddedFromExtension()
        throws Exception
    {
        final IComponentStrategy expectedComponentStrategy = mocksControl_.createMock( IComponentStrategy.class );
        final IExtensionRegistry extensionRegistry = mocksControl_.createMock( IExtensionRegistry.class );
        final IExtension extension = mocksControl_.createMock( IExtension.class );
        final IConfigurationElement configurationElement = mocksControl_.createMock( IConfigurationElement.class );
        EasyMock.expect( configurationElement.getAttribute( "id" ) ).andReturn( "expected-id" ); //$NON-NLS-1$ //$NON-NLS-2$
        EasyMock.expect( configurationElement.createExecutableExtension( "className" ) ).andReturn( expectedComponentStrategy ); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getDeclaringExtension() ).andReturn( extension );
        EasyMock.expect( extension.getNamespaceIdentifier() ).andReturn( "namespace" ); //$NON-NLS-1$
        EasyMock.expect( extension.getSimpleIdentifier() ).andReturn( "simple-id" ); //$NON-NLS-1$
        EasyMock.expect( extension.getConfigurationElements() ).andReturn( new IConfigurationElement[] {
            configurationElement
        } );
        final IComponentStrategyRegistry componentStrategyRegistry = mocksControl_.createMock( IComponentStrategyRegistry.class );
        final Capture<IComponentStrategy> componentStrategyCapture = new Capture<IComponentStrategy>();
        componentStrategyRegistry.registerComponentStrategy( EasyMock.capture( componentStrategyCapture ) );
        mocksControl_.replay();
        componentStrategyRegistryExtensionPointAdapter_.bindComponentStrategyRegistry( componentStrategyRegistry );
        componentStrategyRegistryExtensionPointAdapter_.bindExtensionRegistry( extensionRegistry );

        componentStrategyRegistryExtensionPointAdapter_.added( new IExtension[] {
            extension
        } );

        mocksControl_.verify();
        assertEquals( expectedComponentStrategy, componentStrategyCapture.getValue() );
    }

    /**
     * Ensures the
     * {@link ComponentStrategyRegistryExtensionPointAdapter#bindComponentStrategyRegistry}
     * method throws an exception when the component strategy registry is
     * already bound.
     */
    @Test( expected = IllegalStateException.class )
    public void testBindComponentStrategyRegistry_AlreadyBound()
    {
        componentStrategyRegistryExtensionPointAdapter_.bindComponentStrategyRegistry( mocksControl_.createMock( IComponentStrategyRegistry.class ) );

        componentStrategyRegistryExtensionPointAdapter_.bindComponentStrategyRegistry( mocksControl_.createMock( IComponentStrategyRegistry.class ) );
    }

    /**
     * Ensures the
     * {@link ComponentStrategyRegistryExtensionPointAdapter#bindComponentStrategyRegistry}
     * method throws an exception when passed a {@code null} component strategy
     * registry.
     */
    @Test( expected = NullPointerException.class )
    public void testBindComponentStrategyRegistry_ComponentStrategyRegistry_Null()
    {
        componentStrategyRegistryExtensionPointAdapter_.bindComponentStrategyRegistry( null );
    }

    /**
     * Ensures the
     * {@link ComponentStrategyRegistryExtensionPointAdapter#bindExtensionRegistry}
     * method throws an exception when the extension registry is already bound.
     */
    @Test( expected = IllegalStateException.class )
    public void testBindExtensionRegistry_AlreadyBound()
    {
        componentStrategyRegistryExtensionPointAdapter_.bindExtensionRegistry( mocksControl_.createMock( IExtensionRegistry.class ) );

        componentStrategyRegistryExtensionPointAdapter_.bindExtensionRegistry( mocksControl_.createMock( IExtensionRegistry.class ) );
    }

    /**
     * Ensures the
     * {@link ComponentStrategyRegistryExtensionPointAdapter#bindExtensionRegistry}
     * method throws an exception when passed a {@code null} extension registry.
     */
    @Test( expected = NullPointerException.class )
    public void testBindExtensionRegistry_ExtensionRegistry_Null()
    {
        componentStrategyRegistryExtensionPointAdapter_.bindExtensionRegistry( null );
    }

    /**
     * Ensures the
     * {@link ComponentStrategyRegistryExtensionPointAdapter#removed(IExtension[])}
     * method unregisters all component strategies associated with the specified
     * extensions from the component strategy registry.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testRemovedFromExtension()
        throws Exception
    {
        final IComponentStrategy expectedComponentStrategy = mocksControl_.createMock( IComponentStrategy.class );
        final IExtensionRegistry extensionRegistry = mocksControl_.createMock( IExtensionRegistry.class );
        final IExtension extension = mocksControl_.createMock( IExtension.class );
        final IConfigurationElement configurationElement = mocksControl_.createMock( IConfigurationElement.class );
        EasyMock.expect( configurationElement.getAttribute( "id" ) ).andReturn( "expected-id" ); //$NON-NLS-1$ //$NON-NLS-2$
        EasyMock.expect( configurationElement.createExecutableExtension( "className" ) ).andReturn( expectedComponentStrategy ); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getDeclaringExtension() ).andReturn( extension );
        EasyMock.expect( extension.getNamespaceIdentifier() ).andReturn( "namespace" ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( extension.getSimpleIdentifier() ).andReturn( "simple-id" ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( extension.getConfigurationElements() ).andReturn( new IConfigurationElement[] {
            configurationElement
        } ).anyTimes();
        final IComponentStrategyRegistry componentStrategyRegistry = mocksControl_.createMock( IComponentStrategyRegistry.class );
        componentStrategyRegistry.registerComponentStrategy( EasyMock.notNull( IComponentStrategy.class ) );
        final Capture<IComponentStrategy> componentStrategyCapture = new Capture<IComponentStrategy>();
        componentStrategyRegistry.unregisterComponentStrategy( EasyMock.capture( componentStrategyCapture ) );
        mocksControl_.replay();
        componentStrategyRegistryExtensionPointAdapter_.bindComponentStrategyRegistry( componentStrategyRegistry );
        componentStrategyRegistryExtensionPointAdapter_.bindExtensionRegistry( extensionRegistry );
        componentStrategyRegistryExtensionPointAdapter_.added( new IExtension[] {
            extension
        } );

        componentStrategyRegistryExtensionPointAdapter_.removed( new IExtension[] {
            extension
        } );

        mocksControl_.verify();
        assertEquals( expectedComponentStrategy, componentStrategyCapture.getValue() );
    }

    /**
     * Ensures the
     * {@link ComponentStrategyRegistryExtensionPointAdapter#unbindComponentStrategyRegistry}
     * method throws an exception when passed a component strategy registry that
     * is not bound.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnbindComponentStrategyRegistry_ComponentStrategyRegistry_NotBound()
    {
        componentStrategyRegistryExtensionPointAdapter_.bindComponentStrategyRegistry( mocksControl_.createMock( IComponentStrategyRegistry.class ) );

        componentStrategyRegistryExtensionPointAdapter_.unbindComponentStrategyRegistry( mocksControl_.createMock( IComponentStrategyRegistry.class ) );
    }

    /**
     * Ensures the
     * {@link ComponentStrategyRegistryExtensionPointAdapter#unbindComponentStrategyRegistry}
     * method throws an exception when passed a {@code null} component strategy
     * registry.
     */
    @Test( expected = NullPointerException.class )
    public void testUnbindComponentStrategyRegistry_ComponentStrategyRegistry_Null()
    {
        componentStrategyRegistryExtensionPointAdapter_.unbindComponentStrategyRegistry( null );
    }

    /**
     * Ensures the
     * {@link ComponentStrategyRegistryExtensionPointAdapter#unbindExtensionRegistry}
     * method throws an exception when passed an extension registry that is not
     * bound.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnbindExtensionRegistry_ExtensionRegistry_NotBound()
    {
        componentStrategyRegistryExtensionPointAdapter_.bindExtensionRegistry( mocksControl_.createMock( IExtensionRegistry.class ) );

        componentStrategyRegistryExtensionPointAdapter_.unbindExtensionRegistry( mocksControl_.createMock( IExtensionRegistry.class ) );
    }

    /**
     * Ensures the
     * {@link ComponentStrategyRegistryExtensionPointAdapter#unbindExtensionRegistry}
     * method throws an exception when passed a {@code null} extension registry.
     */
    @Test( expected = NullPointerException.class )
    public void testUnbindExtensionRegistry_ExtensionRegistry_Null()
    {
        componentStrategyRegistryExtensionPointAdapter_.unbindExtensionRegistry( null );
    }
}
