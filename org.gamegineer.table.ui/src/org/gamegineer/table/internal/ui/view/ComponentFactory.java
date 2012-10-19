/*
 * ComponentFactory.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Oct 12, 2012 at 9:35:33 PM.
 */

package org.gamegineer.table.internal.ui.view;

import net.jcip.annotations.Immutable;
import org.gamegineer.table.ui.IComponentFactory;

/**
 * A component factory.
 */
@Immutable
final class ComponentFactory
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The component factory category identifier or {@code null} if the
     * component factory has no category.
     */
    private final String categoryId_;

    /** The underlying component factory. */
    private final IComponentFactory componentFactory_;

    /** The component factory identifier. */
    private final String id_;

    /** The component factory mnemonic. */
    private final int mnemonic_;

    /** The component factory name. */
    private final String name_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentFactory} class.
     * 
     * @param id
     *        The component factory identifier; must not be {@code null}.
     * @param name
     *        The component factory name; must not be {@code null}.
     * @param mnemonic
     *        The component factory mnemonic.
     * @param categoryId
     *        The component factory category identifier or {@code null} if the
     *        component factory has no category.
     * @param componentFactory
     *        The underlying component factory; must not be {@code null}.
     */
    ComponentFactory(
        /* @NonNull */
        final String id,
        /* @NonNull */
        final String name,
        final int mnemonic,
        /* @Nullable */
        final String categoryId,
        /* @NonNull */
        final IComponentFactory componentFactory )
    {
        assert id != null;
        assert name != null;
        assert componentFactory != null;

        categoryId_ = categoryId;
        componentFactory_ = componentFactory;
        id_ = id;
        mnemonic_ = mnemonic;
        name_ = name;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the component factory category identifier.
     * 
     * @return The component factory category identifier or {@code null} if the
     *         component factory has no category.
     */
    /* @Nullable */
    String getCategoryId()
    {
        return categoryId_;
    }

    /**
     * Gets the underlying component factory.
     * 
     * @return The underlying component factory; never {@code null}.
     */
    /* @NonNull */
    IComponentFactory getComponentFactory()
    {
        return componentFactory_;
    }

    /**
     * Gets the component factory identifier.
     * 
     * @return The component factory identifier; never {@code null}.
     */
    /* @NonNull */
    String getId()
    {
        return id_;
    }

    /**
     * Gets the component factory mnemonic.
     * 
     * @return The component factory mnemonic.
     */
    int getMnemonic()
    {
        return mnemonic_;
    }

    /**
     * Gets the component factory name.
     * 
     * @return The component factory name; never {@code null}.
     */
    /* @NonNull */
    String getName()
    {
        return name_;
    }
}
