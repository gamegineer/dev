/*
 * FakeHandler.java
 * Copyright 2008-2013 Gamegineer.org
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
 * Created on Jun 5, 2010 at 10:56:42 PM.
 */

package org.gamegineer.common.internal.core.impl.logging;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.common.internal.core.impl.Activator;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentConstants;
import org.osgi.service.component.ComponentException;
import org.osgi.service.component.ComponentFactory;

/**
 * Fake implementation of {@link java.util.logging.Handler}.
 */
@NotThreadSafe
public final class FakeHandler
    extends Handler
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FakeHandler} class.
     */
    public FakeHandler()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see java.util.logging.Handler#close()
     */
    @Override
    public void close()
        throws SecurityException
    {
        // do nothing
    }

    /*
     * @see java.util.logging.Handler#flush()
     */
    @Override
    public void flush()
    {
        // do nothing
    }

    /*
     * @see java.util.logging.Handler#publish(java.util.logging.LogRecord)
     */
    @Override
    public void publish(
        @SuppressWarnings( "unused" )
        final LogRecord record )
    {
        // do nothing
    }

    /**
     * Registers a component factory for the {@code FakeHandler} class.
     * 
     * @return The service registration token associated with the component
     *         factory; never {@code null}.
     */
    /* @NonNull */
    public static ServiceRegistration<ComponentFactory> registerComponentFactory()
    {
        final ComponentFactory componentFactory = new AbstractHandlerFactory<FakeHandler>( FakeHandler.class )
        {
            // no overrides
        };
        final Dictionary<String, Object> properties = new Hashtable<>();
        properties.put( ComponentConstants.COMPONENT_FACTORY, FakeHandler.class.getName() );
        return Activator.getDefault().getBundleContext().registerService( ComponentFactory.class, componentFactory, properties );
    }

    /**
     * Registers a component factory for the {@code FakeHandler} class that will
     * always fail to create the component.
     * 
     * @return The service registration token associated with the component
     *         factory; never {@code null}.
     */
    /* @NonNull */
    public static ServiceRegistration<ComponentFactory> registerFailingComponentFactory()
    {
        final ComponentFactory componentFactory = new AbstractHandlerFactory<FakeHandler>( FakeHandler.class )
        {
            @Override
            protected FakeHandler createLoggingComponent(
                @SuppressWarnings( "unused" )
                final String typeName )
            {
                throw new ComponentException( "failed to create FakeHandler" ); //$NON-NLS-1$
            }
        };
        final Dictionary<String, Object> properties = new Hashtable<>();
        properties.put( ComponentConstants.COMPONENT_FACTORY, FakeHandler.class.getName() );
        return Activator.getDefault().getBundleContext().registerService( ComponentFactory.class, componentFactory, properties );
    }
}
