/*
 * ContainerEvent.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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
 * Created on Mar 29, 2012 at 8:18:46 PM.
 */

package org.gamegineer.table.core;

import static org.gamegineer.common.core.runtime.NullAnalysis.nonNull;
import java.util.EventObject;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.Nullable;

/**
 * An event fired by a container.
 * 
 * @noextend This class is not intended to be subclassed by clients.
 */
@ThreadSafe
public class ContainerEvent
    extends EventObject
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = -3356087700212900227L;

    /**
     * The path of the container that fired the event at the time the event was
     * fired.
     */
    @Nullable
    private final ComponentPath containerPath_;

    /** The thread on which the event originated. */
    private final Thread thread_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ContainerEvent} class.
     * 
     * @param source
     *        The container that fired the event; must not be {@code null}.
     * @param containerPath
     *        The path of the container that fired the event at the time the
     *        event was fired; may be {@code null}.
     */
    public ContainerEvent(
        final IContainer source,
        @Nullable
        final ComponentPath containerPath )
    {
        super( source );

        containerPath_ = containerPath;
        thread_ = nonNull( Thread.currentThread() );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the container that fired the event.
     * 
     * @return The container that fired the event; never {@code null}.
     */
    public final IContainer getContainer()
    {
        return nonNull( (IContainer)getSource() );
    }

    /**
     * Gets the path of the container that fired the event at the time the event
     * was fired.
     * 
     * @return The path of the container that fired the event at the time the
     *         event was fired; may be {@code null}.
     */
    @Nullable
    public final ComponentPath getContainerPath()
    {
        return containerPath_;
    }

    /**
     * Gets the thread on which the event originated.
     * 
     * @return The thread on which the event originated; never {@code null}.
     */
    public final Thread getThread()
    {
        return thread_;
    }
}
