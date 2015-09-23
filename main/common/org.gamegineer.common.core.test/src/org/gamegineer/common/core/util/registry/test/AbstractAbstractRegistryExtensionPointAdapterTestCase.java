/*
 * AbstractAbstractRegistryExtensionPointAdapterTestCase.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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

package org.gamegineer.common.core.util.registry.test;

import static org.junit.Assert.assertEquals;
import java.util.Optional;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.core.util.registry.AbstractRegistryExtensionPointAdapter;
import org.gamegineer.common.core.util.registry.IRegistry;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that extend the
 * {@link AbstractRegistryExtensionPointAdapter} class.
 * 
 * @param <RegistryExtensionPointAdapterType>
 *        The type of the registry extension point adapter.
 * @param <ObjectIdType>
 *        The type of object used to identify an object managed by the registry.
 * @param <ObjectType>
 *        The type of object managed by the registry.
 */
public abstract class AbstractAbstractRegistryExtensionPointAdapterTestCase<RegistryExtensionPointAdapterType extends AbstractRegistryExtensionPointAdapter<ObjectIdType, ObjectType>, @NonNull ObjectIdType, @NonNull ObjectType>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The mocks control for use in the fixture. */
    private Optional<IMocksControl> mocksControl_;

    /** The registry extension point adapter under test in the fixture. */
    private Optional<RegistryExtensionPointAdapterType> registryExtensionPointAdapter_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractAbstractRegistryExtensionPointAdapterTestCase} class.
     */
    protected AbstractAbstractRegistryExtensionPointAdapterTestCase()
    {
        mocksControl_ = Optional.empty();
        registryExtensionPointAdapter_ = Optional.empty();
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
        final @Nullable ObjectType expected,
        final @Nullable ObjectType actual )
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
     */
    protected abstract ObjectType configureConfigurationElement(
        IConfigurationElement configurationElement,
        IMocksControl mocksControl )
        throws Exception;

    /**
     * Creates a mock registry.
     * 
     * @return A new mock registry; never {@code null}.
     */
    @SuppressWarnings( "unchecked" )
    private IRegistry<ObjectIdType, ObjectType> createMockRegistry()
    {
        return getMocksControl().createMock( IRegistry.class );
    }

    /**
     * Invokes the {@link AbstractRegistryExtensionPointAdapter#createObject}
     * method on the specified registry extension point adapter.
     * 
     * @param registryExtensionPointAdapter
     *        The registry extension point adapter; must not be {@code null}.
     * @param configurationElement
     *        The configuration element; must not be {@code null}.
     * 
     * @return A new object; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the configuration element represents an illegal object.
     */
    protected abstract ObjectType createObject(
        RegistryExtensionPointAdapterType registryExtensionPointAdapter,
        IConfigurationElement configurationElement );

    /**
     * Creates the registry extension point adapter to be tested.
     * 
     * @return The registry extension point adapter to be tested; never
     *         {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    protected abstract RegistryExtensionPointAdapterType createRegistryExtensionPointAdapter()
        throws Exception;

    /**
     * Invokes the
     * {@link AbstractRegistryExtensionPointAdapter#getExtensionPointId} method
     * on the specified registry extension point adapter.
     * 
     * @param registryExtensionPointAdapter
     *        The registry extension point adapter; must not be {@code null}.
     * 
     * @return The identifier of the extension point associated with this
     *         adapter; never {@code null}.
     */
    protected abstract String getExtensionPointId(
        RegistryExtensionPointAdapterType registryExtensionPointAdapter );

    /**
     * Gets the fixture mocks control.
     * 
     * @return The fixture mocks control; never {@code null}.
     */
    private IMocksControl getMocksControl()
    {
        return mocksControl_.get();
    }

    /**
     * Gets the registry extension point adapter under test in the fixture.
     * 
     * @return The registry extension point adapter under test in the fixture;
     *         never {@code null}.
     */
    protected final RegistryExtensionPointAdapterType getRegistryExtensionPointAdapter()
    {
        return registryExtensionPointAdapter_.get();
    }

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
        mocksControl_ = Optional.of( EasyMock.createControl() );
        registryExtensionPointAdapter_ = Optional.of( createRegistryExtensionPointAdapter() );
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
        final RegistryExtensionPointAdapterType registryExtensionPointAdapter = getRegistryExtensionPointAdapter();
        final IMocksControl mocksControl = getMocksControl();
        final IExtensionRegistry extensionRegistry = mocksControl.createMock( IExtensionRegistry.class );
        final IExtension extension = mocksControl.createMock( IExtension.class );
        final IConfigurationElement configurationElement = mocksControl.createMock( IConfigurationElement.class );
        final ObjectType expectedObject = configureConfigurationElement( configurationElement, mocksControl );
        EasyMock.expect( configurationElement.getDeclaringExtension() ).andReturn( extension );
        EasyMock.expect( extension.getNamespaceIdentifier() ).andReturn( "namespace" ); //$NON-NLS-1$
        EasyMock.expect( extension.getSimpleIdentifier() ).andReturn( "simple-id" ); //$NON-NLS-1$
        EasyMock.expect( extension.getConfigurationElements() ).andReturn( new @NonNull IConfigurationElement[] {
            configurationElement
        } );
        final IRegistry<ObjectIdType, ObjectType> objectRegistry = createMockRegistry();
        final Capture<ObjectType> objectCapture = new Capture<>();
        objectRegistry.registerObject( EasyMock.capture( objectCapture ) );
        mocksControl.replay();
        registryExtensionPointAdapter.bindObjectRegistry( objectRegistry );
        registryExtensionPointAdapter.bindExtensionRegistry( extensionRegistry );

        registryExtensionPointAdapter.added( new IExtension[] {
            extension
        } );

        mocksControl.verify();
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
        final RegistryExtensionPointAdapterType registryExtensionPointAdapter = getRegistryExtensionPointAdapter();
        final IMocksControl mocksControl = getMocksControl();
        registryExtensionPointAdapter.bindExtensionRegistry( mocksControl.createMock( IExtensionRegistry.class ) );

        registryExtensionPointAdapter.bindExtensionRegistry( mocksControl.createMock( IExtensionRegistry.class ) );
    }

    /**
     * Ensures the
     * {@link AbstractRegistryExtensionPointAdapter#bindObjectRegistry} method
     * throws an exception when the object registry is already bound.
     */
    @Test( expected = IllegalStateException.class )
    public void testBindObjectRegistry_AlreadyBound()
    {
        final RegistryExtensionPointAdapterType registryExtensionPointAdapter = getRegistryExtensionPointAdapter();
        registryExtensionPointAdapter.bindObjectRegistry( createMockRegistry() );

        registryExtensionPointAdapter.bindObjectRegistry( createMockRegistry() );
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
        final RegistryExtensionPointAdapterType registryExtensionPointAdapter = getRegistryExtensionPointAdapter();
        final IMocksControl mocksControl = getMocksControl();
        final IExtensionRegistry extensionRegistry = mocksControl.createMock( IExtensionRegistry.class );
        final IExtension extension = mocksControl.createMock( IExtension.class );
        final IConfigurationElement configurationElement = mocksControl.createMock( IConfigurationElement.class );
        final ObjectType expectedObject = configureConfigurationElement( configurationElement, mocksControl );
        EasyMock.expect( configurationElement.getDeclaringExtension() ).andReturn( extension );
        EasyMock.expect( extension.getNamespaceIdentifier() ).andReturn( "namespace" ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( extension.getSimpleIdentifier() ).andReturn( "simple-id" ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( extension.getConfigurationElements() ).andReturn( new @NonNull IConfigurationElement[] {
            configurationElement
        } ).anyTimes();
        final IRegistry<ObjectIdType, ObjectType> objectRegistry = createMockRegistry();
        objectRegistry.registerObject( EasyMock.<ObjectType>notNull() );
        final Capture<ObjectType> objectCapture = new Capture<>();
        objectRegistry.unregisterObject( EasyMock.capture( objectCapture ) );
        mocksControl.replay();
        registryExtensionPointAdapter.bindObjectRegistry( objectRegistry );
        registryExtensionPointAdapter.bindExtensionRegistry( extensionRegistry );
        registryExtensionPointAdapter.added( new IExtension[] {
            extension
        } );

        registryExtensionPointAdapter.removed( new IExtension[] {
            extension
        } );

        mocksControl.verify();
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
        final RegistryExtensionPointAdapterType registryExtensionPointAdapter = getRegistryExtensionPointAdapter();
        final IMocksControl mocksControl = getMocksControl();
        registryExtensionPointAdapter.bindExtensionRegistry( mocksControl.createMock( IExtensionRegistry.class ) );

        registryExtensionPointAdapter.unbindExtensionRegistry( mocksControl.createMock( IExtensionRegistry.class ) );
    }

    /**
     * Ensures the
     * {@link AbstractRegistryExtensionPointAdapter#unbindObjectRegistry} method
     * throws an exception when passed an object registry that is not bound.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnbindObjectRegistry_ObjectRegistry_NotBound()
    {
        final RegistryExtensionPointAdapterType registryExtensionPointAdapter = getRegistryExtensionPointAdapter();
        registryExtensionPointAdapter.bindObjectRegistry( createMockRegistry() );

        registryExtensionPointAdapter.unbindObjectRegistry( createMockRegistry() );
    }
}
