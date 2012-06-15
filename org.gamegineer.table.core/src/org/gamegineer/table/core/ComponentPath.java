/*
 * ComponentPath.java
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
 * Created on Jun 10, 2012 at 4:34:52 PM.
 */

package org.gamegineer.table.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.jcip.annotations.Immutable;
import org.gamegineer.common.core.util.ObjectUtils;

/**
 * The path to a component from its associated table.
 * 
 * <p>
 * A component path represents the sequence of container indexes that must be
 * followed, beginning at the table, to the component in question.
 * </p>
 * 
 * <p>
 * An instance of this class represents a snapshot of the component path.
 * Because a component's path may change as the table is modified, clients must
 * be careful to not use a component path that has been effectively invalidated
 * by a table modification.
 * </p>
 */
@Immutable
public final class ComponentPath
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The component index relative to its parent. */
    private final int index_;

    /** The parent path or {@code null} if the component has no parent. */
    private final ComponentPath parentPath_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentPath} class.
     * 
     * @param parentPath
     *        The parent path or {@code null} if the component has no parent.
     * @param index
     *        The component index relative to its parent.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code index} is negative.
     */
    public ComponentPath(
        /* @Nullable */
        final ComponentPath parentPath,
        final int index )
    {
        assertArgumentLegal( index >= 0, "index", NonNlsMessages.ComponentPath_ctor_index_negative ); //$NON-NLS-1$

        index_ = index;
        parentPath_ = parentPath;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(
        final Object obj )
    {
        if( this == obj )
        {
            return true;
        }

        if( !(obj instanceof ComponentPath) )
        {
            return false;
        }

        final ComponentPath other = (ComponentPath)obj;
        return ObjectUtils.equals( parentPath_, other.parentPath_ ) && (index_ == other.index_);
    }

    /**
     * Gets the component index relative to its parent.
     * 
     * @return The component index relative to its parent.
     */
    public int getIndex()
    {
        return index_;
    }

    /**
     * Gets the parent path.
     * 
     * @return The parent path or {@code null} if the component has no parent.
     */
    /* @Nullable */
    public ComponentPath getParentPath()
    {
        return parentPath_;
    }

    /*
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        int hashCode = 17;
        hashCode = 31 * hashCode + ObjectUtils.hashCode( parentPath_ );
        hashCode = 31 * hashCode + index_;
        return hashCode;
    }

    /**
     * Converts this component path to a list of its constituent component paths
     * ordered from the root-most component to the leaf-most component.
     * 
     * @return A list of component paths ordered from the root-most component to
     *         the leaf-most component; never {@code null}. The returned
     *         collection is guaranteed to have at least one element.
     */
    /* @NonNull */
    public List<ComponentPath> toList()
    {
        final List<ComponentPath> componentPaths = new ArrayList<ComponentPath>();

        ComponentPath componentPath = this;
        while( componentPath != null )
        {
            componentPaths.add( componentPath );
            componentPath = componentPath.getParentPath();
        }

        Collections.reverse( componentPaths );
        return componentPaths;
    }

    /*
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append( "ComponentPath[" ); //$NON-NLS-1$

        final List<ComponentPath> componentPaths = toList();
        for( int index = 0, size = componentPaths.size(); index < size; ++index )
        {
            sb.append( componentPaths.get( index ).getIndex() );
            if( index < (size - 1) )
            {
                sb.append( '.' );
            }
        }

        sb.append( "]" ); //$NON-NLS-1$
        return sb.toString();
    }
}
