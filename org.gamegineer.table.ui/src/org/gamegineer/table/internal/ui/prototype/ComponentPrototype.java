/*
 * ComponentPrototype.java
 * Copyright 2008-2012 Gamegineer contributors and others.
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
import org.gamegineer.table.ui.prototype.IComponentPrototypeFactory;

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

    /** The component prototype factory. */
    private final IComponentPrototypeFactory componentPrototypeFactory_;

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
     * @param componentPrototypeFactory
     *        The component prototype factory; must not be {@code null}.
     */
    ComponentPrototype(
        /* @NonNull */
        final String name,
        final int mnemonic,
        /* @Nullable */
        final String categoryId,
        /* @NonNull */
        final IComponentPrototypeFactory componentPrototypeFactory )
    {
        assert name != null;
        assert componentPrototypeFactory != null;

        categoryId_ = categoryId;
        componentPrototypeFactory_ = componentPrototypeFactory;
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
     * Gets the component prototype factory.
     * 
     * @return The component prototype factory; never {@code null}.
     */
    /* @NonNull */
    IComponentPrototypeFactory getFactory()
    {
        return componentPrototypeFactory_;
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
