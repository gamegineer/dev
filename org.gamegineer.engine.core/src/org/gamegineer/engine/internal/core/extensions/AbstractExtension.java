/*
 * AbstractExtension.java
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
 * Created on Apr 6, 2009 at 10:20:45 PM.
 */

package org.gamegineer.engine.internal.core.extensions;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.util.concurrent.atomic.AtomicBoolean;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.engine.core.EngineException;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.IExtension;
import org.gamegineer.engine.core.contexts.extension.IExtensionContext;

/**
 * Superclass for implementations of
 * {@link org.gamegineer.engine.core.IExtension}.
 * 
 * <p>
 * This class is thread-safe.
 * </p>
 */
@ThreadSafe
public abstract class AbstractExtension
    implements IExtension
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * Indicates this extension has been started.
     * 
     * <p>
     * An atomic variable is used to ensure this mutable field is safely
     * published to all threads. It is assumed this field will only be modified
     * from a thread that currently has acquired the engine writer lock.
     * </p>
     */
    private final AtomicBoolean m_isStarted;

    /** The type of this extension. */
    private final Class<?> m_type;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes an instance of the {@code AbstractExtension} class.
     * 
     * @param type
     *        The type of the extension; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code type} is {@code null}.
     */
    protected AbstractExtension(
        /* @NonNull */
        final Class<?> type )
    {
        assertArgumentNotNull( type, "type" ); //$NON-NLS-1$

        m_isStarted = new AtomicBoolean( false );
        m_type = type;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Asserts the extension is started.
     * 
     * @throws java.lang.IllegalStateException
     *         If the extension is not started.
     */
    protected final void assertExtensionStarted()
    {
        assertStateLegal( m_isStarted.get(), Messages.AbstractExtension_extension_notStarted );
    }

    /**
     * Gets the extension context from the specified engine context.
     * 
     * @param context
     *        The engine context; must not be {@code null}.
     * 
     * @return The extension context from the specified engine context; never
     *         {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code context} does not contain an extension context.
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     */
    /* @NonNull */
    protected static final IExtensionContext getExtensionContext(
        /* @NonNull */
        final IEngineContext context )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        final IExtensionContext extensionContext = context.getContext( IExtensionContext.class );
        assertArgumentLegal( extensionContext != null, "context", Messages.AbstractExtension_extensionContext_unavailable ); //$NON-NLS-1$

        return extensionContext;
    }

    /*
     * @see org.gamegineer.engine.core.IExtension#getExtensionType()
     */
    public final Class<?> getExtensionType()
    {
        return m_type;
    }

    /*
     * @see org.gamegineer.engine.core.IExtension#start(org.gamegineer.engine.core.IEngineContext)
     */
    @SuppressWarnings( "unused" )
    public void start(
        final IEngineContext context )
        throws EngineException
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        assertStateLegal( m_isStarted.compareAndSet( false, true ), Messages.AbstractExtension_extension_alreadyStarted );
    }

    /*
     * @see org.gamegineer.engine.core.IExtension#stop(org.gamegineer.engine.core.IEngineContext)
     */
    @SuppressWarnings( "unused" )
    public void stop(
        final IEngineContext context )
        throws EngineException
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        assertStateLegal( m_isStarted.compareAndSet( true, false ), Messages.AbstractExtension_extension_notStarted );
    }
}
