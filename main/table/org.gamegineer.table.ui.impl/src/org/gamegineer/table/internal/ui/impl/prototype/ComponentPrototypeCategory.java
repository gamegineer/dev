/*
 * ComponentPrototypeCategory.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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
     *        The component prototype category identifier.
     * @param name
     *        The component prototype category name.
     * @param mnemonic
     *        The component prototype category mnemonic.
     * @param parentPath
     *        The path of the parent component prototype category. The first
     *        element is the identifier of the furthest ancestor. The last
     *        element is the identifier of the nearest ancestor.
     */
    ComponentPrototypeCategory(
        final String id,
        final String name,
        final int mnemonic,
        final List<String> parentPath )
    {
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
     *        The path of the parent component prototype category.
     * @param id
     *        The component prototype category identifier.
     * 
     * @return An immutable view of the specified component prototype category
     *         path.
     */
    private static List<String> createPath(
        final List<String> parentPath,
        final String id )
    {
        final List<String> path = new ArrayList<>( parentPath.size() + 1 );
        path.addAll( parentPath );
        path.add( id );
        return Collections.unmodifiableList( path );
    }

    /**
     * Gets the component prototype category identifier.
     * 
     * @return The component prototype category identifier.
     */
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
     * @return The component prototype category name.
     */
    String getName()
    {
        return name_;
    }

    /**
     * Gets an immutable view of the component prototype category path.
     * 
     * @return An immutable view of the component prototype category path.
     */
    List<String> getPath()
    {
        return path_;
    }
}
