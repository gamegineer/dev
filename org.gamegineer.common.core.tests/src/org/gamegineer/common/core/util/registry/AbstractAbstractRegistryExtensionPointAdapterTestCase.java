/*
 * AbstractAbstractRegistryExtensionPointAdapterTestCase.java
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
 * Created on Nov 17, 2012 at 9:45:23 PM.
 */

package org.gamegineer.common.core.util.registry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that extend the
 * {@link org.gamegineer.common.core.util.registry.AbstractRegistryExtensionPointAdapter}
 * class.
 * 
 * @param <ObjectIdType>
 *        The type of object used to identify an object managed by the registry.
 * @param <ObjectType>
 *        The type of object managed by the registry.
 */
public abstract class AbstractAbstractRegistryExtensionPointAdapterTestCase<ObjectIdType, ObjectType>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;

    /** The registry extension point adapter under test in the fixture. */
    private AbstractRegistryExtensionPointAdapter<ObjectIdType, ObjectType> registryExtensionPointAdapter_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractAbstractRegistryExtensionPointAdapterTestCase} class.
     */
    protected AbstractAbstractRegistryExtensionPointAdapterTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Asserts that the specified objects are equal.
     * 
     * <p>
     * This implementation compares the two objects using the {@code equals}
     * method.
     * </p>
     * 
     * @param expected
     *        The expected object; may be {@code null}.
     * @param actual
     *        The actual object; may be {@code null}.
     * 
     * @throws java.lang.AssertionError
     *         If the two objects are not equal.
     */
    protected void assertObjectEquals(
        /* @Nullable */
        final ObjectType expected,
        /* @Nullable */
        final ObjectType actual )
    {
        assertEquals( expected, actual );
    }

    /**
     * Configures the specified configuration element appropriately for the
     * extension point associated with the adapter.
     * 
     * @param configurationElement
     *        The configuration element; must not be {@code null}.
     * @param mocksControl
     *        The fixture mocks control; must not be {@code null}.
     * 
     * @return The object that will be created when
     *         {@link IConfigurationElement#createExecutableExtension} is called
     *         on the specified configuration element; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     * @throws java.lang.NullPointerException
     *         If {@code configurationElement} or {@code mocksControl} is
     *         {@code null}.
     */
    /* @NonNull */
    protected abstract ObjectType configureConfigurationElement(
        /* @NonNull */
        IConfigurationElement configurationElement,
        /* @NonNull */
        IMocksControl mocksControl )
        throws Exception;

    /**
     * Creates the registry extension point adapter to be tested.
     * 
     * @return The registry extension point adapter to be tested; never
     *         {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract AbstractRegistryExtensionPointAdapter<ObjectIdType, ObjectType> createRegistryExtensionPointAdapter()
        throws Exception;

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
        registryExtensionPointAdapter_ = createRegistryExtensionPointAdapter();
    }

    /**
     * Ensures the
     * {@link AbstractRegistryExtensionPointAdapter#added(IExtension[])} method
     * registers all objects associated with the specified extensions with the
     * object registry.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testAddedFromExtension()
        throws Exception
    {
        final IExtensionRegistry extensionRegistry = mocksControl_.createMock( IExtensionRegistry.class );
        final IExtension extension = mocksControl_.createMock( IExtension.class );
        final IConfigurationElement configurationElement = mocksControl_.createMock( IConfigurationElement.class );
        final ObjectType expectedObject = configureConfigurationElement( configurationElement, mocksControl_ );
        EasyMock.expect( configurationElement.getDeclaringExtension() ).andReturn( extension );
        EasyMock.expect( extension.getNamespaceIdentifier() ).andReturn( "namespace" ); //$NON-NLS-1$
        EasyMock.expect( extension.getSimpleIdentifier() ).andReturn( "simple-id" ); //$NON-NLS-1$
        EasyMock.expect( extension.getConfigurationElements() ).andReturn( new IConfigurationElement[] {
            configurationElement
        } );
        final IRegistry<ObjectIdType, ObjectType> objectRegistry = mocksControl_.createMock( IRegistry.class );
        final Capture<ObjectType> objectCapture = new Capture<ObjectType>();
        objectRegistry.registerObject( EasyMock.capture( objectCapture ) );
        mocksControl_.replay();
        registryExtensionPointAdapter_.bindObjectRegistry( objectRegistry );
        registryExtensionPointAdapter_.bindExtensionRegistry( extensionRegistry );

        registryExtensionPointAdapter_.added( new IExtension[] {
            extension
        } );

        mocksControl_.verify();
        assertObjectEquals( expectedObject, objectCapture.getValue() );
    }

    /**
     * Ensures the
     * {@link AbstractRegistryExtensionPointAdapter#bindExtensionRegistry}
     * method throws an exception when the extension registry is already bound.
     */
    @Test( expected = IllegalStateException.class )
    public void testBindExtensionRegistry_AlreadyBound()
    {
        registryExtensionPointAdapter_.bindExtensionRegistry( mocksControl_.createMock( IExtensionRegistry.class ) );

        registryExtensionPointAdapter_.bindExtensionRegistry( mocksControl_.createMock( IExtensionRegistry.class ) );
    }

    /**
     * Ensures the
     * {@link AbstractRegistryExtensionPointAdapter#bindExtensionRegistry}
     * method throws an exception when passed a {@code null} extension registry.
     */
    @Test( expected = NullPointerException.class )
    public void testBindExtensionRegistry_ExtensionRegistry_Null()
    {
        registryExtensionPointAdapter_.bindExtensionRegistry( null );
    }

    /**
     * Ensures the
     * {@link AbstractRegistryExtensionPointAdapter#bindObjectRegistry} method
     * throws an exception when the object registry is already bound.
     */
    @Test( expected = IllegalStateException.class )
    public void testBindObjectRegistry_AlreadyBound()
    {
        registryExtensionPointAdapter_.bindObjectRegistry( mocksControl_.createMock( IRegistry.class ) );

        registryExtensionPointAdapter_.bindObjectRegistry( mocksControl_.createMock( IRegistry.class ) );
    }

    /**
     * Ensures the
     * {@link AbstractRegistryExtensionPointAdapter#bindObjectRegistry} method
     * throws an exception when passed a {@code null} object registry.
     */
    @Test( expected = NullPointerException.class )
    public void testBindObjectRegistry_ObjectRegistry_Null()
    {
        registryExtensionPointAdapter_.bindObjectRegistry( null );
    }

    /**
     * Ensures the {@link AbstractRegistryExtensionPointAdapter#createObject}
     * method throws an exception when passed a {@code null} configuration
     * element.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateObject_ConfigurationElement_Null()
    {
        registryExtensionPointAdapter_.createObject( null );
    }

    /**
     * Ensures the
     * {@link AbstractRegistryExtensionPointAdapter#getExtensionPointId} method
     * does not return {@code null}.
     */
    @Test
    public void testGetExtensionPointId_ReturnValue_NonNull()
    {
        assertNotNull( registryExtensionPointAdapter_.getExtensionPointId() );
    }

    /**
     * Ensures the
     * {@link AbstractRegistryExtensionPointAdapter#removed(IExtension[])}
     * method unregisters all objects associated with the specified extensions
     * from the object registry.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testRemovedFromExtension()
        throws Exception
    {
        final IExtensionRegistry extensionRegistry = mocksControl_.createMock( IExtensionRegistry.class );
        final IExtension extension = mocksControl_.createMock( IExtension.class );
        final IConfigurationElement configurationElement = mocksControl_.createMock( IConfigurationElement.class );
        final ObjectType expectedObject = configureConfigurationElement( configurationElement, mocksControl_ );
        EasyMock.expect( configurationElement.getDeclaringExtension() ).andReturn( extension );
        EasyMock.expect( extension.getNamespaceIdentifier() ).andReturn( "namespace" ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( extension.getSimpleIdentifier() ).andReturn( "simple-id" ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( extension.getConfigurationElements() ).andReturn( new IConfigurationElement[] {
            configurationElement
        } ).anyTimes();
        final IRegistry<ObjectIdType, ObjectType> objectRegistry = mocksControl_.createMock( IRegistry.class );
        objectRegistry.registerObject( EasyMock.<ObjectType>notNull() );
        final Capture<ObjectType> objectCapture = new Capture<ObjectType>();
        objectRegistry.unregisterObject( EasyMock.capture( objectCapture ) );
        mocksControl_.replay();
        registryExtensionPointAdapter_.bindObjectRegistry( objectRegistry );
        registryExtensionPointAdapter_.bindExtensionRegistry( extensionRegistry );
        registryExtensionPointAdapter_.added( new IExtension[] {
            extension
        } );

        registryExtensionPointAdapter_.removed( new IExtension[] {
            extension
        } );

        mocksControl_.verify();
        assertObjectEquals( expectedObject, objectCapture.getValue() );
    }

    /**
     * Ensures the
     * {@link AbstractRegistryExtensionPointAdapter#unbindExtensionRegistry}
     * method throws an exception when passed an extension registry that is not
     * bound.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnbindExtensionRegistry_ExtensionRegistry_NotBound()
    {
        registryExtensionPointAdapter_.bindExtensionRegistry( mocksControl_.createMock( IExtensionRegistry.class ) );

        registryExtensionPointAdapter_.unbindExtensionRegistry( mocksControl_.createMock( IExtensionRegistry.class ) );
    }

    /**
     * Ensures the
     * {@link AbstractRegistryExtensionPointAdapter#unbindExtensionRegistry}
     * method throws an exception when passed a {@code null} extension registry.
     */
    @Test( expected = NullPointerException.class )
    public void testUnbindExtensionRegistry_ExtensionRegistry_Null()
    {
        registryExtensionPointAdapter_.unbindExtensionRegistry( null );
    }

    /**
     * Ensures the
     * {@link AbstractRegistryExtensionPointAdapter#unbindObjectRegistry} method
     * throws an exception when passed an object registry that is not bound.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnbindObjectRegistry_ObjectRegistry_NotBound()
    {
        registryExtensionPointAdapter_.bindObjectRegistry( mocksControl_.createMock( IRegistry.class ) );

        registryExtensionPointAdapter_.unbindObjectRegistry( mocksControl_.createMock( IRegistry.class ) );
    }

    /**
     * Ensures the
     * {@link AbstractRegistryExtensionPointAdapter#unbindObjectRegistry} method
     * throws an exception when passed a {@code null} object registry.
     */
    @Test( expected = NullPointerException.class )
    public void testUnbindObjectRegistry_ObjectRegistry_Null()
    {
        registryExtensionPointAdapter_.unbindObjectRegistry( null );
    }
}
