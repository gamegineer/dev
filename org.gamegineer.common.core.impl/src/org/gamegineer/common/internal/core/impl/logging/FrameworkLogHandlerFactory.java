/*
 * FrameworkLogHandlerFactory.java
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
 * Created on Jun 11, 2010 at 3:20:36 PM.
 */

package org.gamegineer.common.internal.core.impl.logging;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.util.concurrent.atomic.AtomicReference;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.osgi.framework.log.FrameworkLog;
import org.gamegineer.common.core.logging.FrameworkLogHandler;
import org.osgi.service.component.ComponentException;

/**
 * A component factory for
 * {@link org.gamegineer.common.core.logging.FrameworkLogHandler}.
 */
@ThreadSafe
public final class FrameworkLogHandlerFactory
    extends AbstractHandlerFactory<FrameworkLogHandler>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The framework log service. */
    private final AtomicReference<FrameworkLog> frameworkLog_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FrameworkLogHandlerFactory}
     * class.
     */
    public FrameworkLogHandlerFactory()
    {
        super( FrameworkLogHandler.class );

        frameworkLog_ = new AtomicReference<>( null );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Binds the specified framework log service.
     * 
     * @param frameworkLog
     *        The framework log service to bind; must not be {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If a framework log service is already bound.
     * @throws java.lang.NullPointerException
     *         If {@code frameworkLog} is {@code null}.
     */
    public void bindFrameworkLog(
        /* @NonNull */
        final FrameworkLog frameworkLog )
    {
        assertArgumentNotNull( frameworkLog, "frameworkLog" ); //$NON-NLS-1$
        assertStateLegal( frameworkLog_.compareAndSet( null, frameworkLog ) );
    }

    /*
     * @see org.gamegineer.common.internal.core.logging.AbstractLoggingComponentFactory#createLoggingComponent(java.lang.String)
     */
    @Override
    protected FrameworkLogHandler createLoggingComponent(
        final String typeName )
    {
        assertArgumentNotNull( typeName, "typeName" ); //$NON-NLS-1$

        if( !typeName.equals( FrameworkLogHandler.class.getName() ) )
        {
            throw new ComponentException( NonNlsMessages.FrameworkLogHandlerFactory_createLoggingComponent_illegalTypeName );
        }

        final FrameworkLog frameworkLog = frameworkLog_.get();
        if( frameworkLog == null )
        {
            throw new ComponentException( NonNlsMessages.FrameworkLogHandlerFactory_createLoggingComponent_noFrameworkLogAvailable );
        }

        return new FrameworkLogHandler( frameworkLog );
    }

    /**
     * Unbinds the specified framework log service.
     * 
     * @param frameworkLog
     *        The framework log service to unbind; must not be {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If a different or no framework log service is currently bound.
     * @throws java.lang.NullPointerException
     *         If {@code frameworkLog} is {@code null}.
     */
    public void unbindFrameworkLog(
        /* @NonNull */
        final FrameworkLog frameworkLog )
    {
        assertArgumentNotNull( frameworkLog, "frameworkLog" ); //$NON-NLS-1$
        assertStateLegal( frameworkLog_.compareAndSet( frameworkLog, null ) );
    }
}
