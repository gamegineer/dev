/*
 * AbstractComponentServiceTestCase.java
 * Copyright 2008-2009 Gamegineer.org
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
 * Created on Apr 11, 2008 at 11:49:58 PM.
 */

package org.gamegineer.common.core.services.component;

import static org.gamegineer.test.core.Assert.assertImmutableCollection;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.Collection;
import org.gamegineer.common.internal.core.Activator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.common.core.services.component.IComponentService}
 * interface.
 */
public abstract class AbstractComponentServiceTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The component service under test in the fixture. */
    private IComponentService service_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractComponentServiceTestCase} class.
     */
    protected AbstractComponentServiceTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the component service to be tested.
     * 
     * @return The component service to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IComponentService createComponentService()
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
        service_ = createComponentService();
        assertNotNull( service_ );
    }

    /**
     * Tears down the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @After
    public void tearDown()
        throws Exception
    {
        service_ = null;
    }

    /**
     * Ensures the {@code createComponent} method throws an exception when
     * passed a {@code null} context.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateComponent_Context_Null()
        throws Exception
    {
        service_.createComponent( new MockComponentSpecification(), null );
    }

    /**
     * Ensures the {@code createComponent} method throws an exception when
     * passed a {@code null} specification.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateComponent_Specification_Null()
        throws Exception
    {
        service_.createComponent( null, ComponentCreationContextBuilder.emptyComponentCreationContext() );
    }

    /**
     * Ensures the {@code createComponent} method creates a component when
     * passed a specification supported by a factory registered explicitly.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testCreateComponent_Specification_Supported_Explicit()
        throws Exception
    {
        final IComponentFactory factory = new MockComponentFactory();
        final IComponentSpecification specification = new MockComponentSpecification( factory );
        service_.registerComponentFactory( factory );
        assertNotNull( service_.createComponent( specification, ComponentCreationContextBuilder.emptyComponentCreationContext() ) );
    }

    /**
     * Ensures the {@code createComponent} method creates a component when
     * passed a specification supported by a factory registered through OSGi.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testCreateComponent_Specification_Supported_OSGi()
        throws Exception
    {
        final IComponentFactory factory = new MockComponentFactory();
        final IComponentSpecification specification = new MockComponentSpecification( factory );
        final BundleContext bundleContext = Activator.getDefault().getBundleContext();
        final ServiceRegistration serviceReg = bundleContext.registerService( IComponentFactory.class.getName(), factory, null );
        try
        {
            assertNotNull( service_.createComponent( specification, ComponentCreationContextBuilder.emptyComponentCreationContext() ) );
        }
        finally
        {
            serviceReg.unregister();
        }
    }

    /**
     * Ensures the {@code createComponent} method throws an exception when
     * passed an unsupported specification.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = UnsupportedComponentSpecificationException.class )
    public void testCreateComponent_Specification_Unsupported()
        throws Exception
    {
        service_.createComponent( new MockComponentSpecification(), ComponentCreationContextBuilder.emptyComponentCreationContext() );
    }

    /**
     * Ensures the {@code getComponentFactories} method returns an immutable
     * collection.
     */
    @Test
    public void testGetComponentFactories_ImmutableCollectionReturned()
    {
        assertImmutableCollection( service_.getComponentFactories() );
    }

    /**
     * Ensures the {@code getComponentFactories} method returns a collection
     * that is a snapshot of the service state at the time of the call.
     */
    @Test
    public void testGetComponentFactories_SnapshotCollectionReturned()
    {
        final Collection<IComponentFactory> factories = service_.getComponentFactories();
        service_.registerComponentFactory( new MockComponentFactory() );
        assertTrue( factories.size() != service_.getComponentFactories().size() );
    }

    /**
     * Ensures the {@code getComponentFactories(IComponentSpecification)} method
     * returns a collection of factories that match the specified specification.
     */
    @Test
    public void testGetComponentFactoriesWithSpecification_ExpectedCollectionReturned()
    {
        final IComponentFactory factory = new MockComponentFactory();
        service_.registerComponentFactory( factory );
        assertTrue( service_.getComponentFactories( new MockComponentSpecification( factory ) ).contains( factory ) );
    }

    /**
     * Ensures the {@code getComponentFactories(IComponentSpecification)} method
     * returns an immutable collection.
     */
    @Test
    public void testGetComponentFactoriesWithSpecification_ImmutableCollectionReturned()
    {
        assertImmutableCollection( service_.getComponentFactories( new MockComponentSpecification() ) );
    }

    /**
     * Ensures the {@code getComponentFactories(IComponentSpecification)} method
     * returns a collection that is a snapshot of the service state at the time
     * of the call.
     */
    @Test
    public void testGetComponentFactoriesWithSpecification_SnapshotCollectionReturned()
    {
        final IComponentFactory factory = new MockComponentFactory();
        final IComponentSpecification specification = new MockComponentSpecification( factory );
        final Collection<IComponentFactory> factories = service_.getComponentFactories( specification );
        service_.registerComponentFactory( factory );
        assertTrue( factories.size() != service_.getComponentFactories( specification ).size() );
    }

    /**
     * Ensures the {@code getComponentFactories(IComponentSpecification)} method
     * throws an exception when passed a {@code null} specification.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testGetComponentFactoriesWithSpecification_Specification_Null()
        throws Exception
    {
        service_.getComponentFactories( null );
    }

    /**
     * Ensures the {@code registerComponentFactory} method throws an exception
     * when passed a {@code null} factory.
     */
    @Test( expected = NullPointerException.class )
    public void testRegisterComponentFactory_Factory_Null()
    {
        service_.registerComponentFactory( null );
    }

    /**
     * Ensures the {@code registerComponentFactory} method properly ignores a
     * factory that has already been registered.
     */
    @Test
    public void testRegisterComponentFactory_Factory_Registered()
    {
        final IComponentFactory factory = new MockComponentFactory();
        final int originalFactoryCount = service_.getComponentFactories().size();
        service_.registerComponentFactory( factory );
        service_.registerComponentFactory( factory );
        assertTrue( service_.getComponentFactories().contains( factory ) );
        assertTrue( (originalFactoryCount + 1) == service_.getComponentFactories().size() );
    }

    /**
     * Ensures the {@code registerComponentFactory} method registers a
     * previously unregistered factory.
     */
    @Test
    public void testRegisterComponentFactory_Factory_Unregistered()
    {
        final IComponentFactory factory = new MockComponentFactory();
        service_.registerComponentFactory( factory );
        assertTrue( service_.getComponentFactories().contains( factory ) );
    }

    /**
     * Ensures the {@code unregisterComponentFactory} method throws an exception
     * when passed a {@code null} factory.
     */
    @Test( expected = NullPointerException.class )
    public void testUnregisterComponentFactory_Factory_Null()
    {
        service_.unregisterComponentFactory( null );
    }

    /**
     * Ensures the {@code unregisterComponentFactory} method unregisters a
     * previously registered factory.
     */
    @Test
    public void testUnregisterComponentFactory_Factory_Registered()
    {
        final IComponentFactory factory = new MockComponentFactory();
        final int originalFactoryCount = service_.getComponentFactories().size();
        service_.registerComponentFactory( factory );
        assertTrue( (originalFactoryCount + 1) == service_.getComponentFactories().size() );
        service_.unregisterComponentFactory( factory );
        assertTrue( originalFactoryCount == service_.getComponentFactories().size() );
    }

    /**
     * Ensures the {@code unregisterComponentFactory} properly ignores a factory
     * that was not previously registered.
     */
    @Test
    public void testUnregisterComponentFactory_Factory_Unregistered()
    {
        final IComponentFactory factory = new MockComponentFactory();
        final int originalFactoryCount = service_.getComponentFactories().size();
        service_.unregisterComponentFactory( factory );
        assertTrue( originalFactoryCount == service_.getComponentFactories().size() );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A mock component factory implementation.
     */
    private static final class MockComponentFactory
        implements IComponentFactory
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code MockComponentFactory} class.
         */
        MockComponentFactory()
        {
            super();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.common.core.services.component.IComponentFactory#containsAttribute(java.lang.String)
         */
        public boolean containsAttribute(
            @SuppressWarnings( "unused" )
            final String name )
        {
            return false;
        }

        /*
         * @see org.gamegineer.common.core.services.component.IComponentFactory#createComponent(org.gamegineer.common.core.services.component.IComponentCreationContext)
         */
        public Object createComponent(
            @SuppressWarnings( "unused" )
            final IComponentCreationContext context )
        {
            return new Object();
        }

        /*
         * @see org.gamegineer.common.core.services.component.IComponentFactory#getAttribute(java.lang.String)
         */
        public Object getAttribute(
            @SuppressWarnings( "unused" )
            final String name )
        {
            throw new IllegalArgumentException();
        }
    }

    /**
     * A mock component specification implementation.
     */
    private static final class MockComponentSpecification
        implements IComponentSpecification
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The component factory matched by this specification. */
        private final IComponentFactory factory_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code MockComponentSpecification}
         * class that matches no component factories.
         */
        MockComponentSpecification()
        {
            this( null );
        }

        /**
         * Initializes a new instance of the {@code MockComponentSpecification}
         * class that matches the specified component factory.
         * 
         * @param factory
         *        The component factory matched by this specification; may be
         *        {@code null}.
         */
        MockComponentSpecification(
            /* @Nullable */
            final IComponentFactory factory )
        {
            factory_ = factory;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.common.core.services.component.IComponentSpecification#matches(org.gamegineer.common.core.services.component.IComponentFactory)
         */
        public boolean matches(
            final IComponentFactory factory )
        {
            return factory.equals( factory_ );
        }
    }
}
