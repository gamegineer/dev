/*
 * StageVersion.java
 * Copyright 2008-2009 Gamegineer.org
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
 * Created on Aug 29, 2008 at 10:14:17 PM.
 */

package org.gamegineer.game.internal.core;

import java.util.UUID;
import net.jcip.annotations.Immutable;

/**
 * A stage version.
 * 
 * <p>
 * A stage version consists of two components:
 * </p>
 * 
 * <ol>
 * <li><b>A stage instance identifier.</b> Each time a stage is activated, a new
 * instance identifier is generated to uniquely identify that particular
 * activation.</li>
 * <li><b>A stage modification count.</b> During a stage activation, every
 * modification made to the stage causes its modification count to be
 * incremented.</li>
 * </ol>
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class StageVersion
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The stage instance identifier. */
    private final String instanceId_;

    /** The stage modification count. */
    private final int modificationCount_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code StageVersion} class with a
     * unique instance identifier and a modification count of zero.
     */
    public StageVersion()
    {
        this( UUID.randomUUID().toString(), 0 );
    }

    /**
     * Initializes a new instance of the {@code StageVersion} class with the
     * specified instance identifier and modification count.
     * 
     * @param instanceId
     *        The stage instance identifier; must not be {@code null}.
     * @param modificationCount
     *        The stage modification count.
     */
    private StageVersion(
        /* @NonNull */
        final String instanceId,
        final int modificationCount )
    {
        assert instanceId != null;

        instanceId_ = instanceId;
        modificationCount_ = modificationCount;
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
        if( obj == this )
        {
            return true;
        }

        if( !(obj instanceof StageVersion) )
        {
            return false;
        }

        final StageVersion other = (StageVersion)obj;
        return instanceId_.equals( other.instanceId_ ) && (modificationCount_ == other.modificationCount_);
    }

    /**
     * Gets the stage instance identifier.
     * 
     * @return The stage instance identifier; never {@code null}.
     */
    String getInstanceId()
    {
        return instanceId_;
    }

    /**
     * Gets the stage modification count.
     * 
     * @return The stage modification count.
     */
    int getModificationCount()
    {
        return modificationCount_;
    }

    /*
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        int result = 17;
        result = result * 31 + instanceId_.hashCode();
        result = result * 31 + modificationCount_;
        return result;
    }

    /**
     * Increments the stage version modification count.
     * 
     * @return A new stage version with an incremented modification count; never
     *         {@code null}.
     */
    /* @NonNull */
    StageVersion increment()
    {
        return new StageVersion( instanceId_, modificationCount_ + 1 );
    }

    /*
     * @see java.lang.Object#toString()
     */
    @Override
    @SuppressWarnings( "boxing" )
    public String toString()
    {
        return String.format( "StageVersion[instanceId_='%1$s', modificationCount_='%2$d']", instanceId_, modificationCount_ ); //$NON-NLS-1$
    }
}
