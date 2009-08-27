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
    private IComponentService m_service;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractComponentServiceTestCase} class.
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
        m_service = createComponentService();
        assertNotNull( m_service );
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
        m_service = null;
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
        m_service.createComponent( new MockComponentSpecification(), null );
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
        m_service.createComponent( null, ComponentCreationContextBuilder.emptyComponentCreationContext() );
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
        m_service.registerComponentFactory( factory );
        assertNotNull( m_service.createComponent( specification, ComponentCreationContextBuilder.emptyComponentCreationContext() ) );
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
            assertNotNull( m_service.createComponent( specification, ComponentCreationContextBuilder.emptyComponentCreationContext() ) );
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
        m_service.createComponent( new MockComponentSpecification(), ComponentCreationContextBuilder.emptyComponentCreationContext() );
    }

    /**
     * Ensures the {@code getComponentFactories} method returns an immutable
     * collection.
     */
    @Test
    public void testGetComponentFactories_ImmutableCollectionReturned()
    {
        assertImmutableCollection( m_service.getComponentFactories() );
    }

    /**
     * Ensures the {@code getComponentFactories} method returns a collection
     * that is a snapshot of the service state at the time of the call.
     */
    @Test
    public void testGetComponentFactories_SnapshotCollectionReturned()
    {
        final Collection<IComponentFactory> factories = m_service.getComponentFactories();
        m_service.registerComponentFactory( new MockComponentFactory() );
        assertTrue( factories.size() != m_service.getComponentFactories().size() );
    }

    /**
     * Ensures the {@code getComponentFactories(IComponentSpecification)} method
     * returns a collection of factories that match the specified specification.
     */
    @Test
    public void testGetComponentFactoriesWithSpecification_ExpectedCollectionReturned()
    {
        final IComponentFactory factory = new MockComponentFactory();
        m_service.registerComponentFactory( factory );
        assertTrue( m_service.getComponentFactories( new MockComponentSpecification( factory ) ).contains( factory ) );
    }

    /**
     * Ensures the {@code getComponentFactories(IComponentSpecification)} method
     * returns an immutable collection.
     */
    @Test
    public void testGetComponentFactoriesWithSpecification_ImmutableCollectionReturned()
    {
        assertImmutableCollection( m_service.getComponentFactories( new MockComponentSpecification() ) );
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
        final Collection<IComponentFactory> factories = m_service.getComponentFactories( specification );
        m_service.registerComponentFactory( factory );
        assertTrue( factories.size() != m_service.getComponentFactories( specification ).size() );
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
        m_service.getComponentFactories( null );
    }

    /**
     * Ensures the {@code registerComponentFactory} method throws an exception
     * when passed a {@code null} factory.
     */
    @Test( expected = NullPointerException.class )
    public void testRegisterComponentFactory_Factory_Null()
    {
        m_service.registerComponentFactory( null );
    }

    /**
     * Ensures the {@code registerComponentFactory} method properly ignores a
     * factory that has already been registered.
     */
    @Test
    public void testRegisterComponentFactory_Factory_Registered()
    {
        final IComponentFactory factory = new MockComponentFactory();
        final int originalFactoryCount = m_service.getComponentFactories().size();
        m_service.registerComponentFactory( factory );
        m_service.registerComponentFactory( factory );
        assertTrue( m_service.getComponentFactories().contains( factory ) );
        assertTrue( (originalFactoryCount + 1) == m_service.getComponentFactories().size() );
    }

    /**
     * Ensures the {@code registerComponentFactory} method registers a
     * previously unregistered factory.
     */
    @Test
    public void testRegisterComponentFactory_Factory_Unregistered()
    {
        final IComponentFactory factory = new MockComponentFactory();
        m_service.registerComponentFactory( factory );
        assertTrue( m_service.getComponentFactories().contains( factory ) );
    }

    /**
     * Ensures the {@code unregisterComponentFactory} method throws an exception
     * when passed a {@code null} factory.
     */
    @Test( expected = NullPointerException.class )
    public void testUnregisterComponentFactory_Factory_Null()
    {
        m_service.unregisterComponentFactory( null );
    }

    /**
     * Ensures the {@code unregisterComponentFactory} method unregisters a
     * previously registered factory.
     */
    @Test
    public void testUnregisterComponentFactory_Factory_Registered()
    {
        final IComponentFactory factory = new MockComponentFactory();
        final int originalFactoryCount = m_service.getComponentFactories().size();
        m_service.registerComponentFactory( factory );
        assertTrue( (originalFactoryCount + 1) == m_service.getComponentFactories().size() );
        m_service.unregisterComponentFactory( factory );
        assertTrue( originalFactoryCount == m_service.getComponentFactories().size() );
    }

    /**
     * Ensures the {@code unregisterComponentFactory} properly ignores a factory
     * that was not previously registered.
     */
    @Test
    public void testUnregisterComponentFactory_Factory_Unregistered()
    {
        final IComponentFactory factory = new MockComponentFactory();
        final int originalFactoryCount = m_service.getComponentFactories().size();
        m_service.unregisterComponentFactory( factory );
        assertTrue( originalFactoryCount == m_service.getComponentFactories().size() );
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
        private final IComponentFactory m_factory;


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
            m_factory = factory;
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
            return factory.equals( m_factory );
        }
    }
}
