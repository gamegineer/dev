/*
 * ContainerContentChangedEvent.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
 * Created on Mar 29, 2012 at 8:21:16 PM.
 */

package org.gamegineer.table.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.ThreadSafe;

/**
 * An event used to notify listeners that the content of a container has
 * changed.
 * 
 * @noextend This class is not intended to be subclassed by clients.
 */
@ThreadSafe
public class ContainerContentChangedEvent
    extends ContainerEvent
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 3818840808151909032L;

    /** The component associated with the event. */
    private final IComponent component_;

    /**
     * The index of the component associated with the event at the time the
     * event was fired.
     */
    private final int componentIndex_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ContainerContentChangedEvent}
     * class.
     * 
     * @param source
     *        The container that fired the event; must not be {@code null}.
     * @param containerPath
     *        The path of the container that fired the event at the time the
     *        event was fired; may be {@code null}.
     * @param component
     *        The component associated with the event; must not be {@code null}.
     * @param componentIndex
     *        The index of the component associated with the event at the time
     *        the event was fired.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code source} is {@code null} or if {@code componentIndex} is
     *         negative.
     * @throws java.lang.NullPointerException
     *         If {@code component} is {@code null}.
     */
    public ContainerContentChangedEvent(
        /* @NonNull */
        @SuppressWarnings( "hiding" )
        final IContainer source,
        /* @NonNull */
        final ComponentPath containerPath,
        /* @NonNull */
        final IComponent component,
        final int componentIndex )
    {
        super( source, containerPath );

        assertArgumentNotNull( component, "component" ); //$NON-NLS-1$
        assertArgumentLegal( componentIndex >= 0, "componentIndex", NonNlsMessages.ContainerContentChangedEvent_ctor_componentIndex_negative ); //$NON-NLS-1$

        component_ = component;
        componentIndex_ = componentIndex;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the component associated with the event.
     * 
     * @return The component associated with the event; never {@code null}.
     */
    /* @NonNull */
    public final IComponent getComponent()
    {
        return component_;
    }

    /**
     * Gets the index of the component associated with the event at the time the
     * event was fired.
     * 
     * @return The index of the component associated with the event at the time
     *         the event was fired.
     */
    public final int getComponentIndex()
    {
        return componentIndex_;
    }
}
