/*
 * AbstractLoggingComponentFactory.java
 * Copyright 2008-2011 Gamegineer.org
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

package org.gamegineer.common.internal.core.logging;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.logging.LoggingServiceConstants;
import org.gamegineer.common.internal.core.Activator;
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
public abstract class AbstractLoggingComponentFactory<T>
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
     *        The type of the logging component; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code type} is {@code null}.
     */
    protected AbstractLoggingComponentFactory(
        /* @NonNull */
        final Class<T> type )
    {
        assertArgumentNotNull( type, "type" ); //$NON-NLS-1$

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
     *        The logging component; must not be {@code null}.
     * @param instanceName
     *        The instance name of the logging component; must not be {@code
     *        null}. This name is used to discover the component's properties in
     *        the specified logging properties.
     * @param properties
     *        The logging properties; may be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code component} or {@code instanceName} is {@code null}.
     */
    protected void configureLoggingComponent(
        /* @NonNull */
        final T component,
        /* @NonNull */
        final String instanceName,
        /* @Nullable */
        @SuppressWarnings( "unused" )
        final Map<String, String> properties )
    {
        assertArgumentNotNull( component, "component" ); //$NON-NLS-1$
        assertArgumentNotNull( instanceName, "instanceName" ); //$NON-NLS-1$
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
     *        The type name of the logging component; must not be {@code null}.
     * 
     * @return A new instance of the logging component; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code typeName} is {@code null}.
     * @throws org.osgi.service.component.ComponentException
     *         If an error occurs while creating the component.
     */
    /* @NonNull */
    protected T createLoggingComponent(
        /* @NonNull */
        final String typeName )
    {
        assertArgumentNotNull( typeName, "typeName" ); //$NON-NLS-1$

        try
        {
            final Class<?> type = Class.forName( typeName );
            return type_.cast( type.newInstance() );
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
     *        The type of the logging component; must not be {@code null}.
     * @param name
     *        The fully-qualified name of the component; must not be {@code
     *        null}.
     * @param properties
     *        The logging properties; may be {@code null}.
     * 
     * @return The logging component; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code name} is not a fully-qualified component name.
     * @throws org.osgi.service.component.ComponentException
     *         If the component could not be created.
     */
    /* @NonNull */
    static final <T> T createNamedLoggingComponent(
        /* @NonNull */
        final Class<T> type,
        /* @NonNull */
        final String name,
        /* @Nullable */
        final Map<String, String> properties )
    {
        assert type != null;
        assert name != null;

        final int index = name.lastIndexOf( '.' );
        assertArgumentLegal( index != -1, "name", NonNlsMessages.AbstractLoggingComponentFactory_createNamedLoggingComponent_nameNoDots ); //$NON-NLS-1$
        final String typeName = name.substring( 0, index );
        final String instanceName = name.substring( index + 1 );

        final ServiceReference serviceReference = findComponentFactory( typeName, type );
        final ComponentFactory factory = (ComponentFactory)Activator.getDefault().getBundleContext().getService( serviceReference );
        if( factory == null )
        {
            throw new ComponentException( NonNlsMessages.AbstractLoggingComponentFactory_createNamedLoggingComponent_noComponentFactoryAvailable );
        }

        try
        {
            final Dictionary<String, Object> componentProperties = new Hashtable<String, Object>();
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
     *        The component type name; must not be {@code null}.
     * @param type
     *        The component type or one of its super types; must not be {@code
     *        null}.
     * 
     * @return A reference to the most appropriate component factory for the
     *         specified component type information; never {@code null}.
     */
    /* @NonNull */
    private static ServiceReference findComponentFactory(
        /* @NonNull */
        final String typeName,
        /* @NonNull */
        final Class<?> type )
    {
        assert typeName != null;
        assert type != null;

        ServiceReference serviceReference = getComponentFactory( typeName );
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
     *        The type name of the component created by the factory; must not be
     *        {@code null}.
     * 
     * @return A reference to the component factory for the specified type name
     *         or {@code null} if no component factory is available.
     * 
     * @throws org.osgi.service.component.ComponentException
     *         If an error occurs.
     */
    /* @Nullable */
    private static ServiceReference getComponentFactory(
        /* @NonNull */
        final String typeName )
    {
        assert typeName != null;

        try
        {
            final String filter = String.format( "(%1$s=%2$s)", ComponentConstants.COMPONENT_FACTORY, typeName ); //$NON-NLS-1$
            final ServiceReference[] serviceReferences = Activator.getDefault().getBundleContext().getServiceReferences( ComponentFactory.class.getName(), filter );
            if( (serviceReferences == null) || (serviceReferences.length == 0) )
            {
                return null;
            }

            return serviceReferences[ 0 ];
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
     *        The collection of component properties; must not be {@code null}.
     * @param name
     *        The property name; must not be {@code null}.
     * @param type
     *        The property value type; must not be {@code null}.
     * @param isNullable
     *        Indicates the property value is nullable.
     * 
     * @return The property value; may be {@code null} if {@code isNullable} is
     *         {@code true}.
     * 
     * @throws org.osgi.service.component.ComponentException
     *         If the property value is not nullable and does not exist in the
     *         component properties collection or the property value is the
     *         wrong type.
     */
    /* @Nullable */
    private static <T> T getComponentProperty(
        /* @NonNull */
        final Dictionary<?, ?> componentProperties,
        /* @NonNull */
        final String name,
        /* @NonNull */
        final Class<T> type,
        final boolean isNullable )
    {
        assert componentProperties != null;
        assert name != null;
        assert type != null;

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
        @SuppressWarnings( "unchecked" )
        final Dictionary componentProperties )
    {
        if( componentProperties == null )
        {
            throw new ComponentException( NonNlsMessages.AbstractLoggingComponentFactory_newInstance_noComponentProperties );
        }

        final String typeName = getComponentProperty( componentProperties, LoggingServiceConstants.PROPERTY_COMPONENT_FACTORY_TYPE_NAME, String.class, false );
        final String instanceName = getComponentProperty( componentProperties, LoggingServiceConstants.PROPERTY_COMPONENT_FACTORY_INSTANCE_NAME, String.class, false );
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
