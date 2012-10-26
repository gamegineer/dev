/*
 * ComponentPrototype.java
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

package org.gamegineer.table.internal.ui.prototype;

import net.jcip.annotations.Immutable;
import org.gamegineer.table.core.IComponentFactory;

/**
 * A component prototype.
 */
@Immutable
final class ComponentPrototype
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The component prototype category identifier or {@code null} if the
     * component prototype has no category.
     */
    private final String categoryId_;

    /** The component factory used to create the prototype. */
    private final IComponentFactory componentFactory_;

    /** The component prototype mnemonic. */
    private final int mnemonic_;

    /** The component prototype name. */
    private final String name_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentPrototype} class.
     * 
     * @param name
     *        The component prototype name; must not be {@code null}.
     * @param mnemonic
     *        The component prototype mnemonic.
     * @param categoryId
     *        The component prototype category identifier or {@code null} if the
     *        component prototype has no category.
     * @param componentFactory
     *        The component factory used to create the prototype; must not be
     *        {@code null}.
     */
    ComponentPrototype(
        /* @NonNull */
        final String name,
        final int mnemonic,
        /* @Nullable */
        final String categoryId,
        /* @NonNull */
        final IComponentFactory componentFactory )
    {
        assert name != null;
        assert componentFactory != null;

        categoryId_ = categoryId;
        componentFactory_ = componentFactory;
        mnemonic_ = mnemonic;
        name_ = name;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the component prototype category identifier.
     * 
     * @return The component prototype category identifier or {@code null} if
     *         the component prototype has no category.
     */
    /* @Nullable */
    String getCategoryId()
    {
        return categoryId_;
    }

    /**
     * Gets the component factory used to create the prototype.
     * 
     * @return The component factory used to create the prototype; never
     *         {@code null}.
     */
    /* @NonNull */
    IComponentFactory getComponentFactory()
    {
        return componentFactory_;
    }

    /**
     * Gets the component prototype mnemonic.
     * 
     * @return The component prototype mnemonic.
     */
    int getMnemonic()
    {
        return mnemonic_;
    }

    /**
     * Gets the component prototype name.
     * 
     * @return The component prototype name; never {@code null}.
     */
    /* @NonNull */
    String getName()
    {
        return name_;
    }
}
