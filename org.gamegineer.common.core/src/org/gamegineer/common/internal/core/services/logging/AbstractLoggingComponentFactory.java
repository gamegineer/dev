/*
 * AbstractLoggingComponentFactory.java
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
 * Created on May 21, 2008 at 11:12:14 PM.
 */

package org.gamegineer.common.internal.core.services.logging;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.Map;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.runtime.Platform;
import org.gamegineer.common.core.services.component.AbstractComponentFactory;
import org.gamegineer.common.core.services.component.ComponentCreationContextBuilder;
import org.gamegineer.common.core.services.component.ComponentCreationException;
import org.gamegineer.common.core.services.component.ComponentException;
import org.gamegineer.common.core.services.component.IComponentCreationContext;
import org.gamegineer.common.core.services.component.IComponentSpecification;
import org.gamegineer.common.core.services.component.attributes.ClassNameAttribute;
import org.gamegineer.common.core.services.component.attributes.SupportedClassNamesAttribute;
import org.gamegineer.common.core.services.component.specs.ClassNameComponentSpecification;
import org.gamegineer.common.core.services.component.util.attribute.ComponentCreationContextAttributeAccessor;
import org.gamegineer.common.core.services.component.util.attribute.IAttributeAccessor;
import org.gamegineer.common.internal.core.services.logging.attributes.InstanceNameAttribute;
import org.gamegineer.common.internal.core.services.logging.attributes.LoggingPropertiesAttribute;

/**
 * Superclass for all factories that create logging components.
 * 
 * @param <T>
 *        The type of the logging component.
 */
@ThreadSafe
public abstract class AbstractLoggingComponentFactory<T>
    extends AbstractComponentFactory
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

        SupportedClassNamesAttribute.INSTANCE.setValue( this, type.getName() );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Configures an instance of the logging component associated with the
     * factory.
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
     * @param loggingProperties
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
        final Map<String, String> loggingProperties )
    {
        assertArgumentNotNull( component, "component" ); //$NON-NLS-1$
        assertArgumentNotNull( instanceName, "instanceName" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.common.core.services.component.IComponentFactory#containsAttribute(java.lang.String)
     */
    @Override
    public final boolean containsAttribute(
        final String name )
    {
        // NB: This method is declared final to prevent subclasses from modifying
        // factory attribute behavior so we don't have to test each concrete
        // subclass with respect to IComponentFactory.

        return super.containsAttribute( name );
    }

    /*
     * @see org.gamegineer.common.core.services.component.IComponentFactory#createComponent(org.gamegineer.common.core.services.component.IComponentCreationContext)
     */
    public final T createComponent(
        final IComponentCreationContext context )
        throws ComponentCreationException
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        final IAttributeAccessor accessor = new ComponentCreationContextAttributeAccessor( context );
        final String className = ClassNameAttribute.INSTANCE.getValue( accessor );
        final String instanceName = InstanceNameAttribute.INSTANCE.getValue( accessor );
        final Map<String, String> loggingProperties = LoggingPropertiesAttribute.INSTANCE.tryGetValue( accessor );

        if( !type_.getName().equals( className ) )
        {
            throw new IllegalArgumentException( Messages.AbstractLoggingComponentFactory_createComponent_unsupportedType( className ) );
        }

        final T component = createLoggingComponent( instanceName, loggingProperties );
        configureLoggingComponent( component, instanceName, loggingProperties );
        return component;
    }

    /**
     * Creates a new instance of the logging component associated with the
     * factory.
     * 
     * <p>
     * The default implementation creates a new instance of the component using
     * its default constructor. No attempt is made to configure the component
     * using the logging properties.
     * </p>
     * 
     * @param instanceName
     *        The instance name of the logging component; must not be {@code
     *        null}. This name is used to discover the component's properties in
     *        the specified logging properties.
     * @param loggingProperties
     *        The logging properties; may be {@code null}.
     * 
     * @return A new instance of the logging component; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code instanceName} is {@code null}.
     * @throws org.gamegineer.common.core.services.component.ComponentCreationException
     *         If an error occurred during component creation.
     */
    /* @NonNull */
    protected T createLoggingComponent(
        /* @NonNull */
        final String instanceName,
        /* @Nullable */
        final Map<String, String> loggingProperties )
        throws ComponentCreationException
    {
        assertArgumentNotNull( instanceName, "instanceName" ); //$NON-NLS-1$

        try
        {
            return type_.newInstance();
        }
        catch( final IllegalAccessException e )
        {
            throw new ComponentCreationException( Messages.AbstractLoggingComponentFactory_createLoggingComponent_failed( instanceName, type_.getName() ), e );
        }
        catch( final InstantiationException e )
        {
            throw new ComponentCreationException( Messages.AbstractLoggingComponentFactory_createLoggingComponent_failed( instanceName, type_.getName() ), e );
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
     * <i>{@literal <class-name>}</i>.<i>{@literal <instance-name>}</i>
     * </p>
     * 
     * <p>
     * where <i>class-name</i> is the fully-qualified component class name and
     * <i>instance-name</i> is the component instance name as it is identified
     * in the logging properties. The <i>instance-name</i> must not contain any
     * dots.
     * </p>
     * 
     * @param name
     *        The fully-qualified name of the component; must not be {@code
     *        null}.
     * @param loggingProperties
     *        The logging properties; may be {@code null}.
     * 
     * @return The logging component; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code name} is not a fully-qualified component name.
     * @throws java.lang.NullPointerException
     *         If {@code name} is {@code null}.
     * @throws org.gamegineer.common.core.services.component.ComponentException
     *         If the component could not be created.
     */
    /* @NonNull */
    public static final Object createNamedLoggingComponent(
        /* @NonNull */
        final String name,
        /* @Nullable */
        final Map<String, String> loggingProperties )
        throws ComponentException
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$

        final int index = name.lastIndexOf( '.' );
        assertArgumentLegal( index != -1, "name", Messages.AbstractLoggingComponentFactory_createNamedLoggingComponent_nameNoDots ); //$NON-NLS-1$
        final String className = name.substring( 0, index );
        final String instanceName = name.substring( index + 1 );

        final IComponentSpecification specification = new ClassNameComponentSpecification( className );
        final ComponentCreationContextBuilder builder = new ComponentCreationContextBuilder();
        ClassNameAttribute.INSTANCE.setValue( builder, className );
        InstanceNameAttribute.INSTANCE.setValue( builder, instanceName );
        LoggingPropertiesAttribute.INSTANCE.trySetValue( builder, loggingProperties );

        return Platform.getComponentService().createComponent( specification, builder.toComponentCreationContext() );
    }

    /*
     * @see org.gamegineer.common.core.services.component.AbstractComponentFactory#getAttribute(java.lang.String)
     */
    @Override
    public final Object getAttribute(
        final String name )
    {
        // NB: This method is declared final to prevent subclasses from modifying
        // factory attribute behavior so we don't have to test each concrete
        // subclass with respect to IComponentFactory.

        return super.getAttribute( name );
    }

    /**
     * Gets the specified logging property.
     * 
     * @param instanceName
     *        The instance name of the logging component; must not be {@code
     *        null}.
     * @param propertyName
     *        The property name; must not be {@code null}.
     * @param loggingProperties
     *        The logging properties; must not be {@code null}.
     * 
     * @return The requested logging property or {@code null} if the property
     *         does not exist.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code instanceName}, {@code propertyName}, or {@code
     *         loggingProperties} is {@code null}.
     */
    /* @Nullable */
    protected final String getLoggingProperty(
        /* @NonNull */
        final String instanceName,
        /* @NonNull */
        final String propertyName,
        /* @NonNull */
        final Map<String, String> loggingProperties )
    {
        assertArgumentNotNull( instanceName, "instanceName" ); //$NON-NLS-1$
        assertArgumentNotNull( propertyName, "propertyName" ); //$NON-NLS-1$
        assertArgumentNotNull( loggingProperties, "loggingProperties" ); //$NON-NLS-1$

        return loggingProperties.get( String.format( "%1$s.%2$s.%3$s", type_.getName(), instanceName, propertyName ) ); //$NON-NLS-1$
    }

    /**
     * Gets the type of the logging component created by the factory.
     * 
     * @return The type of the logging component created by the factory; never
     *         {@code null}.
     */
    /* @NonNull */
    public final Class<T> getType()
    {
        return type_;
    }

    /*
     * @see org.gamegineer.common.core.services.component.AbstractComponentFactory#setAttribute(java.lang.String, java.lang.Object)
     */
    @Override
    public final void setAttribute(
        final String name,
        final Object value )
    {
        // NB: This method is declared final to prevent subclasses from modifying
        // factory attribute behavior so we don't have to test each concrete
        // subclass with respect to IComponentFactory.

        super.setAttribute( name, value );
    }
}
