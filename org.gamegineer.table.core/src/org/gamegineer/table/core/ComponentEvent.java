/*
 * ComponentEvent.java
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
 * Created on Mar 27, 2012 at 8:44:53 PM.
 */

package org.gamegineer.table.core;

import java.util.EventObject;
import net.jcip.annotations.ThreadSafe;

/**
 * An event fired by a component.
 * 
 * @noextend This class is not intended to be subclassed by clients.
 */
@ThreadSafe
public class ComponentEvent
    extends EventObject
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 2992004706733067598L;

    /**
     * The path of the component that fired the event at the time the event was
     * fired.
     */
    private final ComponentPath componentPath_;

    /** The thread on which the event originated. */
    private final Thread thread_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentEvent} class.
     * 
     * @param source
     *        The component that fired the event; must not be {@code null}.
     * @param componentPath
     *        The path of the component that fired the event at the time the
     *        event was fired; may be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code source} is {@code null}.
     */
    public ComponentEvent(
        /* @NonNull */
        @SuppressWarnings( "hiding" )
        final IComponent source,
        /* @Nullable */
        final ComponentPath componentPath )
    {
        super( source );

        componentPath_ = componentPath;
        thread_ = Thread.currentThread();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the component that fired the event.
     * 
     * @return The component that fired the event; never {@code null}.
     */
    /* @NonNull */
    public final IComponent getComponent()
    {
        return (IComponent)getSource();
    }

    /**
     * Gets the path of the component that fired the event at the time the event
     * was fired.
     * 
     * @return The path of the component that fired the event at the time the
     *         event was fired; may be {@code null}.
     */
    /* @Nullable */
    public final ComponentPath getComponentPath()
    {
        return componentPath_;
    }

    /**
     * Gets the thread on which the event originated.
     * 
     * @return The thread on which the event originated; never {@code null}.
     */
    /* @NonNull */
    public final Thread getThread()
    {
        return thread_;
    }
}
