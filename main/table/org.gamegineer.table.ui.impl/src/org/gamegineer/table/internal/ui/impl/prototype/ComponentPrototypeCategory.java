/*
 * ComponentPrototypeCategory.java
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
 * Created on Oct 4, 2012 at 10:04:46 PM.
 */

package org.gamegineer.table.internal.ui.impl.prototype;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.jcip.annotations.Immutable;

/**
 * A component prototype category.
 */
@Immutable
final class ComponentPrototypeCategory
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The component prototype category identifier. */
    private final String id_;

    /** The component prototype category mnemonic. */
    private final int mnemonic_;

    /** The component prototype category name. */
    private final String name_;

    /**
     * The component prototype category path. The first element is the
     * identifier of the furthest ancestor. The last element is the identifier
     * of this category.
     */
    private final List<String> path_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentPrototypeCategory}
     * class.
     * 
     * @param id
     *        The component prototype category identifier; must not be
     *        {@code null}.
     * @param name
     *        The component prototype category name; must not be {@code null}.
     * @param mnemonic
     *        The component prototype category mnemonic.
     * @param parentPath
     *        The path of the parent component prototype category; must not be
     *        {@code null}. The first element is the identifier of the furthest
     *        ancestor. The last element is the identifier of the nearest
     *        ancestor.
     */
    ComponentPrototypeCategory(
        /* @NonNull */
        final String id,
        /* @NonNull */
        final String name,
        final int mnemonic,
        /* @NonNull */
        final List<String> parentPath )
    {
        assert id != null;
        assert name != null;
        assert parentPath != null;

        id_ = id;
        mnemonic_ = mnemonic;
        name_ = name;
        path_ = createPath( parentPath, id );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates an immutable view of the specified component prototype category
     * path.
     * 
     * @param parentPath
     *        The path of the parent component prototype category; must not be
     *        {@code null}.
     * @param id
     *        The component prototype category identifier; must not be
     *        {@code null}.
     * 
     * @return An immutable view of the specified component prototype category
     *         path; never {@code null}.
     */
    /* @NonNull */
    private static List<String> createPath(
        /* @NonNull */
        final List<String> parentPath,
        /* @NonNull */
        final String id )
    {
        assert parentPath != null;
        assert id != null;

        final List<String> path = new ArrayList<>( parentPath.size() + 1 );
        path.addAll( parentPath );
        path.add( id );
        return Collections.unmodifiableList( path );
    }

    /**
     * Gets the component prototype category identifier.
     * 
     * @return The component prototype category identifier; never {@code null}.
     */
    /* @NonNull */
    String getId()
    {
        return id_;
    }

    /**
     * Gets the component prototype category mnemonic.
     * 
     * @return The component prototype category mnemonic.
     */
    int getMnemonic()
    {
        return mnemonic_;
    }

    /**
     * Gets the component prototype category name.
     * 
     * @return The component prototype category name; never {@code null}.
     */
    /* @NonNull */
    String getName()
    {
        return name_;
    }

    /**
     * Gets an immutable view of the component prototype category path.
     * 
     * @return An immutable view of the component prototype category path; never
     *         {@code null}.
     */
    /* @NonNull */
    List<String> getPath()
    {
        return path_;
    }
}
