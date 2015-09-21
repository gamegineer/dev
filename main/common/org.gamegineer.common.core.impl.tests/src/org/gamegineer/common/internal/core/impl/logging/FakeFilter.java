/*
 * FakeFilter.java
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
 * Created on Jun 5, 2010 at 11:02:18 PM.
 */

package org.gamegineer.common.internal.core.impl.logging;

import static org.gamegineer.common.core.runtime.NullAnalysis.nonNull;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Filter;
import java.util.logging.LogRecord;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.internal.core.impl.Activator;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentConstants;
import org.osgi.service.component.ComponentFactory;

/**
 * Fake implementation of {@link Filter}.
 * 
 * <p>
 * This filter supports the following fake properties:
 * </p>
 * 
 * <ul>
 * <li>{@code fakeBooleanProperty} specifies a {@code Boolean} value (defaults
 * to {@code true}).</li>
 * </ul>
 */
@NotThreadSafe
public final class FakeFilter
    implements Filter
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The name of the fake Boolean property. */
    public static final String PROPERTY_FAKE_BOOLEAN_PROPERTY = "fakeBooleanProperty"; //$NON-NLS-1$

    /** The fake Boolean property. */
    private boolean fakeBooleanProperty_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FakeFilter} class.
     */
    public FakeFilter()
    {
        fakeBooleanProperty_ = true;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the value of the fake Boolean property.
     * 
     * @return The value of the fake Boolean property.
     */
    public boolean getFakeBooleanProperty()
    {
        return fakeBooleanProperty_;
    }

    /*
     * @see java.util.logging.Filter#isLoggable(java.util.logging.LogRecord)
     */
    @Override
    public boolean isLoggable(
        final @Nullable LogRecord record )
    {
        return true;
    }

    /**
     * Registers a component factory for the {@code FakeFilter} class.
     * 
     * @return The service registration token associated with the component
     *         factory; never {@code null}.
     */
    public static ServiceRegistration<ComponentFactory> registerComponentFactory()
    {
        final ComponentFactory componentFactory = new AbstractLoggingComponentFactory<FakeFilter>( nonNull( FakeFilter.class ) )
        {
            @Override
            protected void configureLoggingComponent(
                final FakeFilter component,
                final String instanceName,
                final @Nullable Map<String, String> properties )
            {
                super.configureLoggingComponent( component, instanceName, properties );

                if( properties == null )
                {
                    return;
                }

                final String value = LoggingProperties.getProperty( properties, nonNull( component.getClass() ), instanceName, PROPERTY_FAKE_BOOLEAN_PROPERTY );
                if( value != null )
                {
                    component.setFakeBooleanProperty( Boolean.parseBoolean( value ) );
                }
            }
        };
        final Dictionary<String, Object> properties = new Hashtable<>();
        properties.put( ComponentConstants.COMPONENT_FACTORY, FakeFilter.class.getName() );
        return nonNull( Activator.getDefault().getBundleContext().<@NonNull ComponentFactory>registerService( nonNull( ComponentFactory.class ), componentFactory, properties ) );
    }

    /**
     * Sets the value of the fake Boolean property.
     * 
     * @param fakeBooleanProperty
     *        The value of the fake Boolean property.
     */
    public void setFakeBooleanProperty(
        final boolean fakeBooleanProperty )
    {
        fakeBooleanProperty_ = fakeBooleanProperty;
    }
}
