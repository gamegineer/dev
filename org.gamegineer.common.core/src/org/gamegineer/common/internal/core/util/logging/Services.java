/*
 * Services.java
 * Copyright 2008 Gamegineer.org
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
 * Created on May 22, 2008 at 11:47:45 PM.
 */

package org.gamegineer.common.internal.core.util.logging;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import org.gamegineer.common.core.services.component.IComponentFactory;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * Manages the OSGi services provided by this package.
 * 
 * <p>
 * The {@code close} method should be called before the bundle is stopped.
 * </p>
 */
public final class Services
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The singleton instance. */
    private static final Services c_instance = new Services();

    /** The ConsoleHandlerFactory registration token. */
    private ServiceRegistration m_consoleHandlerFactoryRegistration;

    /** The FrameworkLogHandlerFactory registration token. */
    private ServiceRegistration m_frameworkLogHandlerFactoryRegistration;

    /** The SimpleFormatterFactory registration token. */
    private ServiceRegistration m_simpleFormatterFactoryRegistration;

    /** The StandardOutputHandlerFactory registration token. */
    private ServiceRegistration m_standardOutputHandlerFactoryRegistration;

    /** The XmlFormatterFactory registration token. */
    private ServiceRegistration m_xmlFormatterFactoryRegistration;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Services} class.
     */
    private Services()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Closes the services managed by this object.
     */
    public void close()
    {
        if( m_xmlFormatterFactoryRegistration != null )
        {
            m_xmlFormatterFactoryRegistration.unregister();
            m_xmlFormatterFactoryRegistration = null;
        }
        if( m_standardOutputHandlerFactoryRegistration != null )
        {
            m_standardOutputHandlerFactoryRegistration.unregister();
            m_standardOutputHandlerFactoryRegistration = null;
        }
        if( m_simpleFormatterFactoryRegistration != null )
        {
            m_simpleFormatterFactoryRegistration.unregister();
            m_simpleFormatterFactoryRegistration = null;
        }
        if( m_frameworkLogHandlerFactoryRegistration != null )
        {
            m_frameworkLogHandlerFactoryRegistration.unregister();
            m_frameworkLogHandlerFactoryRegistration = null;
        }
        if( m_consoleHandlerFactoryRegistration != null )
        {
            m_consoleHandlerFactoryRegistration.unregister();
            m_consoleHandlerFactoryRegistration = null;
        }
    }

    /**
     * Gets the default instance of the {@code Services} class.
     * 
     * @return The default instance of the {@code Services} class; never
     *         {@code null}.
     */
    /* @NonNull */
    public static Services getDefault()
    {
        return c_instance;
    }

    /**
     * Opens the services managed by this object.
     * 
     * @param context
     *        The execution context of the bundle; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     */
    public void open(
        /* @NonNull */
        final BundleContext context )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        m_consoleHandlerFactoryRegistration = context.registerService( IComponentFactory.class.getName(), new ConsoleHandlerFactory(), null );
        m_frameworkLogHandlerFactoryRegistration = context.registerService( IComponentFactory.class.getName(), new FrameworkLogHandlerFactory(), null );
        m_simpleFormatterFactoryRegistration = context.registerService( IComponentFactory.class.getName(), new SimpleFormatterFactory(), null );
        m_standardOutputHandlerFactoryRegistration = context.registerService( IComponentFactory.class.getName(), new StandardOutputHandlerFactory(), null );
        m_xmlFormatterFactoryRegistration = context.registerService( IComponentFactory.class.getName(), new XmlFormatterFactory(), null );
    }
}
