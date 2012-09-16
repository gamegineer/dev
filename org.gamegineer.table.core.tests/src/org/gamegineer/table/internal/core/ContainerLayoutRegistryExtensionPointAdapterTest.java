/*
 * ContainerLayoutRegistryExtensionPointAdapterTest.java
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
 * Created on Aug 9, 2012 at 9:03:56 PM.
 */

package org.gamegineer.table.internal.core;

import static org.junit.Assert.assertEquals;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.gamegineer.table.core.IContainerLayout;
import org.gamegineer.table.core.IContainerLayoutRegistry;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.ContainerLayoutRegistryExtensionPointAdapter}
 * class.
 */
public final class ContainerLayoutRegistryExtensionPointAdapterTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The container layout registry extension point adapter under test in the
     * fixture.
     */
    private ContainerLayoutRegistryExtensionPointAdapter containerLayoutRegistryExtensionPointAdapter_;

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;


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
        containerLayoutRegistryExtensionPointAdapter_ = new ContainerLayoutRegistryExtensionPointAdapter();
    }

    /**
     * Ensures the
     * {@link ContainerLayoutRegistryExtensionPointAdapter#added(IExtension[])}
     * method registers all container layouts associated with the specified
     * extensions with the container layout registry.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testAddedFromExtension()
        throws Exception
    {
        final IContainerLayout expectedContainerLayout = mocksControl_.createMock( IContainerLayout.class );
        final IExtensionRegistry extensionRegistry = mocksControl_.createMock( IExtensionRegistry.class );
        final IExtension extension = mocksControl_.createMock( IExtension.class );
        final IConfigurationElement configurationElement = mocksControl_.createMock( IConfigurationElement.class );
        EasyMock.expect( configurationElement.getAttribute( "id" ) ).andReturn( "expected-id" ); //$NON-NLS-1$ //$NON-NLS-2$
        EasyMock.expect( configurationElement.createExecutableExtension( "className" ) ).andReturn( expectedContainerLayout ); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getDeclaringExtension() ).andReturn( extension );
        EasyMock.expect( extension.getNamespaceIdentifier() ).andReturn( "namespace" ); //$NON-NLS-1$
        EasyMock.expect( extension.getSimpleIdentifier() ).andReturn( "simple-id" ); //$NON-NLS-1$
        EasyMock.expect( extension.getConfigurationElements() ).andReturn( new IConfigurationElement[] {
            configurationElement
        } );
        final IContainerLayoutRegistry containerLayoutRegistry = mocksControl_.createMock( IContainerLayoutRegistry.class );
        final Capture<IContainerLayout> containerLayoutCapture = new Capture<IContainerLayout>();
        containerLayoutRegistry.registerContainerLayout( EasyMock.capture( containerLayoutCapture ) );
        mocksControl_.replay();
        containerLayoutRegistryExtensionPointAdapter_.bindContainerLayoutRegistry( containerLayoutRegistry );
        containerLayoutRegistryExtensionPointAdapter_.bindExtensionRegistry( extensionRegistry );

        containerLayoutRegistryExtensionPointAdapter_.added( new IExtension[] {
            extension
        } );

        mocksControl_.verify();
        assertEquals( expectedContainerLayout, containerLayoutCapture.getValue() );
    }

    /**
     * Ensures the
     * {@link ContainerLayoutRegistryExtensionPointAdapter#bindContainerLayoutRegistry}
     * method throws an exception when the container layout registry is already
     * bound.
     */
    @Test( expected = IllegalStateException.class )
    public void testBindContainerLayoutRegistry_AlreadyBound()
    {
        containerLayoutRegistryExtensionPointAdapter_.bindContainerLayoutRegistry( mocksControl_.createMock( IContainerLayoutRegistry.class ) );

        containerLayoutRegistryExtensionPointAdapter_.bindContainerLayoutRegistry( mocksControl_.createMock( IContainerLayoutRegistry.class ) );
    }

    /**
     * Ensures the
     * {@link ContainerLayoutRegistryExtensionPointAdapter#bindContainerLayoutRegistry}
     * method throws an exception when passed a {@code null} container layout
     * registry.
     */
    @Test( expected = NullPointerException.class )
    public void testBindContainerLayoutRegistry_ContainerLayoutRegistry_Null()
    {
        containerLayoutRegistryExtensionPointAdapter_.bindContainerLayoutRegistry( null );
    }

    /**
     * Ensures the
     * {@link ContainerLayoutRegistryExtensionPointAdapter#bindExtensionRegistry}
     * method throws an exception when the extension registry is already bound.
     */
    @Test( expected = IllegalStateException.class )
    public void testBindExtensionRegistry_AlreadyBound()
    {
        containerLayoutRegistryExtensionPointAdapter_.bindExtensionRegistry( mocksControl_.createMock( IExtensionRegistry.class ) );

        containerLayoutRegistryExtensionPointAdapter_.bindExtensionRegistry( mocksControl_.createMock( IExtensionRegistry.class ) );
    }

    /**
     * Ensures the
     * {@link ContainerLayoutRegistryExtensionPointAdapter#bindExtensionRegistry}
     * method throws an exception when passed a {@code null} extension registry.
     */
    @Test( expected = NullPointerException.class )
    public void testBindExtensionRegistry_ExtensionRegistry_Null()
    {
        containerLayoutRegistryExtensionPointAdapter_.bindExtensionRegistry( null );
    }

    /**
     * Ensures the
     * {@link ContainerLayoutRegistryExtensionPointAdapter#removed(IExtension[])}
     * method unregisters all container layouts associated with the specified
     * extensions from the container layout registry.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testRemovedFromExtension()
        throws Exception
    {
        final IContainerLayout expectedContainerLayout = mocksControl_.createMock( IContainerLayout.class );
        final IExtensionRegistry extensionRegistry = mocksControl_.createMock( IExtensionRegistry.class );
        final IExtension extension = mocksControl_.createMock( IExtension.class );
        final IConfigurationElement configurationElement = mocksControl_.createMock( IConfigurationElement.class );
        EasyMock.expect( configurationElement.getAttribute( "id" ) ).andReturn( "expected-id" ); //$NON-NLS-1$ //$NON-NLS-2$
        EasyMock.expect( configurationElement.createExecutableExtension( "className" ) ).andReturn( expectedContainerLayout ); //$NON-NLS-1$
        EasyMock.expect( configurationElement.getDeclaringExtension() ).andReturn( extension );
        EasyMock.expect( extension.getNamespaceIdentifier() ).andReturn( "namespace" ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( extension.getSimpleIdentifier() ).andReturn( "simple-id" ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( extension.getConfigurationElements() ).andReturn( new IConfigurationElement[] {
            configurationElement
        } ).anyTimes();
        final IContainerLayoutRegistry containerLayoutRegistry = mocksControl_.createMock( IContainerLayoutRegistry.class );
        containerLayoutRegistry.registerContainerLayout( EasyMock.notNull( IContainerLayout.class ) );
        final Capture<IContainerLayout> containerLayoutCapture = new Capture<IContainerLayout>();
        containerLayoutRegistry.unregisterContainerLayout( EasyMock.capture( containerLayoutCapture ) );
        mocksControl_.replay();
        containerLayoutRegistryExtensionPointAdapter_.bindContainerLayoutRegistry( containerLayoutRegistry );
        containerLayoutRegistryExtensionPointAdapter_.bindExtensionRegistry( extensionRegistry );
        containerLayoutRegistryExtensionPointAdapter_.added( new IExtension[] {
            extension
        } );

        containerLayoutRegistryExtensionPointAdapter_.removed( new IExtension[] {
            extension
        } );

        mocksControl_.verify();
        assertEquals( expectedContainerLayout, containerLayoutCapture.getValue() );
    }

    /**
     * Ensures the
     * {@link ContainerLayoutRegistryExtensionPointAdapter#unbindContainerLayoutRegistry}
     * method throws an exception when passed a container layout registry that
     * is not bound.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnbindContainerLayoutRegistry_ContainerLayoutRegistry_NotBound()
    {
        containerLayoutRegistryExtensionPointAdapter_.bindContainerLayoutRegistry( mocksControl_.createMock( IContainerLayoutRegistry.class ) );

        containerLayoutRegistryExtensionPointAdapter_.unbindContainerLayoutRegistry( mocksControl_.createMock( IContainerLayoutRegistry.class ) );
    }

    /**
     * Ensures the
     * {@link ContainerLayoutRegistryExtensionPointAdapter#unbindContainerLayoutRegistry}
     * method throws an exception when passed a {@code null} container layout
     * registry.
     */
    @Test( expected = NullPointerException.class )
    public void testUnbindContainerLayoutRegistry_ContainerLayoutRegistry_Null()
    {
        containerLayoutRegistryExtensionPointAdapter_.unbindContainerLayoutRegistry( null );
    }

    /**
     * Ensures the
     * {@link ContainerLayoutRegistryExtensionPointAdapter#unbindExtensionRegistry}
     * method throws an exception when passed an extension registry that is not
     * bound.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnbindExtensionRegistry_ExtensionRegistry_NotBound()
    {
        containerLayoutRegistryExtensionPointAdapter_.bindExtensionRegistry( mocksControl_.createMock( IExtensionRegistry.class ) );

        containerLayoutRegistryExtensionPointAdapter_.unbindExtensionRegistry( mocksControl_.createMock( IExtensionRegistry.class ) );
    }

    /**
     * Ensures the
     * {@link ContainerLayoutRegistryExtensionPointAdapter#unbindExtensionRegistry}
     * method throws an exception when passed a {@code null} extension registry.
     */
    @Test( expected = NullPointerException.class )
    public void testUnbindExtensionRegistry_ExtensionRegistry_Null()
    {
        containerLayoutRegistryExtensionPointAdapter_.unbindExtensionRegistry( null );
    }
}
