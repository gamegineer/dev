/*
 * Services.java
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
 * Created on May 22, 2008 at 11:47:45 PM.
 */

package org.gamegineer.common.internal.core.util.logging;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.ThreadSafe;
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
@ThreadSafe
public final class Services
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The singleton instance. */
    private static final Services instance_ = new Services();

    /** The ConsoleHandlerFactory registration token. */
    private ServiceRegistration consoleHandlerFactoryRegistration_;

    /** The FrameworkLogHandlerFactory registration token. */
    private ServiceRegistration frameworkLogHandlerFactoryRegistration_;

    /** The SimpleFormatterFactory registration token. */
    private ServiceRegistration simpleFormatterFactoryRegistration_;

    /** The StandardOutputHandlerFactory registration token. */
    private ServiceRegistration standardOutputHandlerFactoryRegistration_;

    /** The XmlFormatterFactory registration token. */
    private ServiceRegistration xmlFormatterFactoryRegistration_;


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
        if( xmlFormatterFactoryRegistration_ != null )
        {
            xmlFormatterFactoryRegistration_.unregister();
            xmlFormatterFactoryRegistration_ = null;
        }
        if( standardOutputHandlerFactoryRegistration_ != null )
        {
            standardOutputHandlerFactoryRegistration_.unregister();
            standardOutputHandlerFactoryRegistration_ = null;
        }
        if( simpleFormatterFactoryRegistration_ != null )
        {
            simpleFormatterFactoryRegistration_.unregister();
            simpleFormatterFactoryRegistration_ = null;
        }
        if( frameworkLogHandlerFactoryRegistration_ != null )
        {
            frameworkLogHandlerFactoryRegistration_.unregister();
            frameworkLogHandlerFactoryRegistration_ = null;
        }
        if( consoleHandlerFactoryRegistration_ != null )
        {
            consoleHandlerFactoryRegistration_.unregister();
            consoleHandlerFactoryRegistration_ = null;
        }
    }

    /**
     * Gets the default instance of the {@code Services} class.
     * 
     * @return The default instance of the {@code Services} class; never {@code
     *         null}.
     */
    /* @NonNull */
    public static Services getDefault()
    {
        return instance_;
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

        consoleHandlerFactoryRegistration_ = context.registerService( IComponentFactory.class.getName(), new ConsoleHandlerFactory(), null );
        frameworkLogHandlerFactoryRegistration_ = context.registerService( IComponentFactory.class.getName(), new FrameworkLogHandlerFactory(), null );
        simpleFormatterFactoryRegistration_ = context.registerService( IComponentFactory.class.getName(), new SimpleFormatterFactory(), null );
        standardOutputHandlerFactoryRegistration_ = context.registerService( IComponentFactory.class.getName(), new StandardOutputHandlerFactory(), null );
        xmlFormatterFactoryRegistration_ = context.registerService( IComponentFactory.class.getName(), new XmlFormatterFactory(), null );
    }
}
