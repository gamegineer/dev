/*
 * ComponentPath.java
 * Copyright 2008-2017 Gamegineer contributors and others.
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
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import net.jcip.annotations.Immutable;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.core.util.ComparableUtils;

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
    implements Comparable<ComponentPath>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The path to the root component of a component hierarchy */
    public static final ComponentPath ROOT = new ComponentPath( Optional.empty(), -1 );

    /**
     * The component index relative to its parent or -1 if the component has no
     * parent.
     */
    private final int index_;

    /** The parent path or empty if the component has no parent. */
    private final Optional<ComponentPath> parentPath_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentPath} class.
     * 
     * @param parentPath
     *        The parent path or empty if the component has no parent.
     * @param index
     *        component index relative to its parent or -1 if the component has
     *        no parent.
     */
    private ComponentPath(
        final Optional<ComponentPath> parentPath,
        final int index )
    {
        assert index >= -1;

        index_ = index;
        parentPath_ = parentPath;
    }

    /**
     * Initializes a new instance of the {@code ComponentPath} class.
     * 
     * @param parentPath
     *        The parent path.
     * @param index
     *        The component index relative to its parent.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code index} is negative.
     */
    public ComponentPath(
        final ComponentPath parentPath,
        final int index )
    {
        this( Optional.of( parentPath ), index );

        assertArgumentLegal( index >= 0, "index", NonNlsMessages.ComponentPath_ctor_index_negative ); //$NON-NLS-1$
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(
        final @Nullable ComponentPath other )
    {
        if( other == null )
        {
            throw new NullPointerException( "other" ); //$NON-NLS-1$
        }

        final @Nullable ComponentPath parentPath = parentPath_.isPresent() ? parentPath_.get() : null;
        final @Nullable ComponentPath otherParentPath = other.parentPath_.isPresent() ? other.parentPath_.get() : null;
        final int parentPathCompareResult = ComparableUtils.compareTo( parentPath, otherParentPath );
        if( parentPathCompareResult != 0 )
        {
            return parentPathCompareResult;
        }

        return index_ - other.index_;
    }

    /*
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(
        final @Nullable Object obj )
    {
        if( this == obj )
        {
            return true;
        }

        if( !(obj instanceof ComponentPath) )
        {
            return false;
        }

        return compareTo( (ComponentPath)obj ) == 0;
    }

    /**
     * Gets the component index relative to its parent.
     * 
     * @return The component index relative to its parent or -1 if the component
     *         has no parent.
     */
    public int getIndex()
    {
        return index_;
    }

    /**
     * Gets the parent path.
     * 
     * @return The parent path or empty if the component has no parent.
     */
    public Optional<ComponentPath> getParentPath()
    {
        return parentPath_;
    }

    /*
     * @see java.lang.Object#hashCode()
     */
    @Override
    @SuppressWarnings( "boxing" )
    public int hashCode()
    {
        return Objects.hash( parentPath_, index_ );
    }

    /**
     * Converts this component path to a list of its constituent component paths
     * ordered from the root-most component to the leaf-most component.
     * 
     * @return A list of component paths ordered from the root-most component to
     *         the leaf-most component. The returned collection is guaranteed to
     *         have at least one element.
     */
    public List<ComponentPath> toList()
    {
        final List<ComponentPath> componentPaths = new ArrayList<>();

        Optional<ComponentPath> componentPath = Optional.of( this );
        while( componentPath.isPresent() )
        {
            final ComponentPath value = componentPath.get();
            componentPaths.add( value );
            componentPath = value.getParentPath();
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
        sb.append( toList().stream() //
            .map( ComponentPath::getIndex ) //
            .map( Object::toString ) //
            .collect( Collectors.joining( "." ) ) ); //$NON-NLS-1$
        sb.append( "]" ); //$NON-NLS-1$
        return sb.toString();
    }
}
