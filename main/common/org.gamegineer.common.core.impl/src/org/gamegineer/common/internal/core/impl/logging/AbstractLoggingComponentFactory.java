/*
 * AbstractLoggingComponentFactory.java
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
 * Created on May 21, 2008 at 11:12:14 PM.
 */

package org.gamegineer.common.internal.core.impl.logging;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.NullAnalysis.nonNull;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.core.logging.LoggingServiceConstants;
import org.gamegineer.common.core.util.osgi.BundleContextUtils;
import org.gamegineer.common.internal.core.impl.Activator;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentConstants;
import org.osgi.service.component.ComponentException;
import org.osgi.service.component.ComponentFactory;
import org.osgi.service.component.ComponentInstance;

/**
 * Superclass for all factories that create logging components.
 * 
 * @param <T>
 *        The type of the logging component.
 */
@ThreadSafe
public abstract class AbstractLoggingComponentFactory<@NonNull T>
    implements ComponentFactory
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The type of the logging component. */
    private final Class<T> type_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractLoggingComponentFactory}
     * class.
     * 
     * @param type
     *        The type of the logging component.
     */
    protected AbstractLoggingComponentFactory(
        final Class<T> type )
    {
        type_ = type;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Configures an instance of the logging component associated with the
     * factory.
     * 
     * <p>
     * Implementations should use an appropriate default if a particular
     * property of the logging component cannot be configured successfully. The
     * failure should be noted using the platform's debug tracing facility.
     * </p>
     * 
     * <p>
     * The default implementation does nothing.
     * </p>
     * 
     * @param component
     *        The logging component.
     * @param instanceName
     *        The instance name of the logging component. This name is used to
     *        discover the component's properties in the specified logging
     *        properties.
     * @param properties
     *        The logging properties.
     */
    protected void configureLoggingComponent(
        @SuppressWarnings( "unused" )
        final T component,
        @SuppressWarnings( "unused" )
        final String instanceName,
        @SuppressWarnings( "unused" )
        final @Nullable Map<String, String> properties )
    {
        // do nothing
    }

    /**
     * Creates a new instance of the logging component associated with the
     * factory.
     * 
     * <p>
     * The default implementation creates a new instance of the component using
     * its default constructor
     * </p>
     * 
     * @param typeName
     *        The type name of the logging component.
     * 
     * @return A new instance of the logging component.
     * 
     * @throws org.osgi.service.component.ComponentException
     *         If an error occurs while creating the component.
     */
    protected T createLoggingComponent(
        final String typeName )
    {
        try
        {
            final Class<?> type = Class.forName( typeName );
            return nonNull( type_, type.newInstance() );
        }
        catch( final ClassNotFoundException e )
        {
            throw new ComponentException( NonNlsMessages.AbstractLoggingComponentFactory_createLoggingComponent_failed( typeName ), e );
        }
        catch( final IllegalAccessException e )
        {
            throw new ComponentException( NonNlsMessages.AbstractLoggingComponentFactory_createLoggingComponent_failed( typeName ), e );
        }
        catch( final InstantiationException e )
        {
            throw new ComponentException( NonNlsMessages.AbstractLoggingComponentFactory_createLoggingComponent_failed( typeName ), e );
        }
        catch( final ClassCastException e )
        {
            throw new ComponentException( NonNlsMessages.AbstractLoggingComponentFactory_createLoggingComponent_failed( typeName ), e );
        }
    }

    /**
     * Creates the named logging component.
     * 
     * <p>
     * A fully-qualified name is of the form:
     * </p>
     * 
     * <p>
     * <i>{@literal <type-name>}</i>.<i>{@literal <instance-name>}</i>
     * </p>
     * 
     * <p>
     * where <i>type-name</i> is the fully-qualified component type name and
     * <i>instance-name</i> is the component instance name as it is identified
     * in the logging properties. The <i>instance-name</i> must not contain any
     * dots.
     * </p>
     * 
     * @param <T>
     *        The type of the logging component.
     * 
     * @param type
     *        The type of the logging component.
     * @param name
     *        The fully-qualified name of the component.
     * @param properties
     *        The logging properties.
     * 
     * @return The logging component.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code name} is not a fully-qualified component name.
     * @throws org.osgi.service.component.ComponentException
     *         If the component could not be created.
     */
    static final <T> T createNamedLoggingComponent(
        final Class<T> type,
        final String name,
        final @Nullable Map<String, String> properties )
    {
        final int index = name.lastIndexOf( '.' );
        assertArgumentLegal( index != -1, "name", NonNlsMessages.AbstractLoggingComponentFactory_createNamedLoggingComponent_nameNoDots ); //$NON-NLS-1$
        final String typeName = name.substring( 0, index );
        final String instanceName = name.substring( index + 1 );

        final ServiceReference<ComponentFactory> serviceReference = findComponentFactory( typeName, type );
        final ComponentFactory factory = BundleContextUtils.getService( Activator.getDefault().getBundleContext(), serviceReference );
        if( factory == null )
        {
            throw new ComponentException( NonNlsMessages.AbstractLoggingComponentFactory_createNamedLoggingComponent_noComponentFactoryAvailable );
        }

        try
        {
            final Dictionary<String, Object> componentProperties = new Hashtable<>();
            componentProperties.put( LoggingServiceConstants.PROPERTY_COMPONENT_FACTORY_TYPE_NAME, typeName );
            componentProperties.put( LoggingServiceConstants.PROPERTY_COMPONENT_FACTORY_INSTANCE_NAME, instanceName );
            if( properties != null )
            {
                componentProperties.put( LoggingServiceConstants.PROPERTY_COMPONENT_FACTORY_LOGGING_PROPERTIES, properties );
            }
            return type.cast( factory.newInstance( componentProperties ).getInstance() );
        }
        finally
        {
            Activator.getDefault().getBundleContext().ungetService( serviceReference );
        }
    }

    /**
     * Finds the most appropriate component factory for the specified component
     * type information.
     * 
     * <p>
     * This method will attempt to find a component factory registered for the
     * concrete component type first then fall back to searching for a component
     * factory registered for its super type.
     * </p>
     * 
     * @param typeName
     *        The component type name.
     * @param type
     *        The component type or one of its super types.
     * 
     * @return A reference to the most appropriate component factory for the
     *         specified component type information.
     * 
     * @throws org.osgi.service.component.ComponentException
     *         If a component factory is not available for the specified
     *         component type information.
     */
    private static ServiceReference<ComponentFactory> findComponentFactory(
        final String typeName,
        final Class<?> type )
    {
        ServiceReference<ComponentFactory> serviceReference = getComponentFactory( typeName );
        if( serviceReference != null )
        {
            return serviceReference;
        }

        serviceReference = getComponentFactory( type.getName() );
        if( serviceReference != null )
        {
            return serviceReference;
        }

        throw new ComponentException( NonNlsMessages.AbstractLoggingComponentFactory_findComponentFactory_noComponentFactoryAvailable( typeName ) );
    }

    /**
     * Gets the component factory for the specified type name.
     * 
     * @param typeName
     *        The type name of the component created by the factory.
     * 
     * @return A reference to the component factory for the specified type name
     *         or {@code null} if no component factory is available.
     * 
     * @throws org.osgi.service.component.ComponentException
     *         If an error occurs.
     */
    private static @Nullable ServiceReference<ComponentFactory> getComponentFactory(
        final String typeName )
    {
        try
        {
            final String filter = String.format( "(%1$s=%2$s)", ComponentConstants.COMPONENT_FACTORY, typeName ); //$NON-NLS-1$
            @SuppressWarnings( "null" )
            final Collection<ServiceReference<ComponentFactory>> serviceReferences = (Collection<ServiceReference<ComponentFactory>>)nonNull( Activator.getDefault().getBundleContext().<@NonNull ComponentFactory>getServiceReferences( nonNull( ComponentFactory.class ), filter ) );
            if( serviceReferences.isEmpty() )
            {
                return null;
            }

            return serviceReferences.iterator().next();
        }
        catch( final InvalidSyntaxException e )
        {
            throw new ComponentException( NonNlsMessages.AbstractLoggingComponentFactory_getComponentFactory_invalidFilterSyntax, e );
        }
    }

    /**
     * Gets the specified component property.
     * 
     * @param <T>
     *        The property value type.
     * 
     * @param componentProperties
     *        The collection of component properties.
     * @param name
     *        The property name.
     * @param type
     *        The property value type.
     * @param isNullable
     *        Indicates the property value is nullable.
     * 
     * @return The property value.
     * 
     * @throws org.osgi.service.component.ComponentException
     *         If the property value is not nullable and does not exist in the
     *         component properties collection or the property value is the
     *         wrong type.
     */
    private static <T> @Nullable T getComponentProperty(
        final Dictionary<?, ?> componentProperties,
        final String name,
        final Class<T> type,
        final boolean isNullable )
    {
        final Object value = componentProperties.get( name );
        if( (value == null) && !isNullable )
        {
            throw new ComponentException( NonNlsMessages.AbstractLoggingComponentFactory_getComponentProperty_illegalPropertyValue( name ) );
        }

        try
        {
            return type.cast( value );
        }
        catch( final ClassCastException e )
        {
            throw new ComponentException( NonNlsMessages.AbstractLoggingComponentFactory_getComponentProperty_illegalPropertyValue( name ), e );
        }
    }

    /**
     * @throws org.osgi.service.component.ComponentException
     *         If {@code componentProperties} is {@code null}.
     * 
     * @see org.osgi.service.component.ComponentFactory#newInstance(java.util.Dictionary)
     */
    @Override
    public final ComponentInstance newInstance(
        @SuppressWarnings( "rawtypes" )
        final @Nullable Dictionary componentProperties )
    {
        if( componentProperties == null )
        {
            throw new ComponentException( NonNlsMessages.AbstractLoggingComponentFactory_newInstance_noComponentProperties );
        }

        final String typeName = getComponentProperty( componentProperties, LoggingServiceConstants.PROPERTY_COMPONENT_FACTORY_TYPE_NAME, String.class, false );
        assert typeName != null;
        final String instanceName = getComponentProperty( componentProperties, LoggingServiceConstants.PROPERTY_COMPONENT_FACTORY_INSTANCE_NAME, String.class, false );
        assert instanceName != null;
        @SuppressWarnings( "unchecked" )
        final Map<String, String> loggingProperties = getComponentProperty( componentProperties, LoggingServiceConstants.PROPERTY_COMPONENT_FACTORY_LOGGING_PROPERTIES, Map.class, true );

        final T component = createLoggingComponent( typeName );
        configureLoggingComponent( component, instanceName, loggingProperties );
        return new ComponentInstance()
        {
            @Override
            public void dispose()
            {
                // do nothing
            }

            @Override
            public Object getInstance()
            {
                return component;
            }
        };
    }
}
