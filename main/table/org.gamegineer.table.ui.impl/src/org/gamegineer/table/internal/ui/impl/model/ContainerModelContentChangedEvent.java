/*
 * ContainerModelContentChangedEvent.java
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
 * Created on Sep 14, 2012 at 9:03:00 PM.
 */

package org.gamegineer.table.internal.ui.impl.model;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;

/**
 * An event used to notify listeners that the content of a container model has
 * changed.
 */
public final class ContainerModelContentChangedEvent
    extends ContainerModelEvent
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = -3873154550401904628L;

    /** The component model associated with the event. */
    private final ComponentModel componentModel_;

    /** The index of the component model associated with the event. */
    private final int componentModelIndex_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ContainerModelContentChangedEvent} class.
     * 
     * @param source
     *        The container model that fired the event; must not be {@code null}
     *        .
     * @param componentModel
     *        The component model associated with the event; must not be
     *        {@code null}.
     * @param componentModelIndex
     *        The index of the component model associated with the event.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code source} is {@code null} or if
     *         {@code componentModelIndex} is negative.
     * @throws java.lang.NullPointerException
     *         If {@code componentModel} is {@code null}.
     */
    public ContainerModelContentChangedEvent(
        /* @NonNull */
        final ContainerModel source,
        /* @NonNull */
        final ComponentModel componentModel,
        final int componentModelIndex )
    {
        super( source );

        assertArgumentNotNull( componentModel, "componentModel" ); //$NON-NLS-1$
        assertArgumentLegal( componentModelIndex >= 0, "componentModelIndex", NonNlsMessages.ContainerModelContentChangedEvent_ctor_componentModelIndex_negative ); //$NON-NLS-1$

        componentModel_ = componentModel;
        componentModelIndex_ = componentModelIndex;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the component model associated with the event.
     * 
     * @return The component model associated with the event; never {@code null}
     *         .
     */
    /* @NonNull */
    public ComponentModel getComponentModel()
    {
        return componentModel_;
    }

    /**
     * Gets the index of the component model associated with the event.
     * 
     * @return The index of the component model associated with the event.
     */
    public int getComponentModelIndex()
    {
        return componentModelIndex_;
    }
}
