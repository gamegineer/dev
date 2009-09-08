/*
 * NonValidatingStage.java
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
 * Created on Nov 20, 2008 at 10:43:41 PM.
 */

package org.gamegineer.game.core.system;

import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.util.ArrayList;
import java.util.List;
import net.jcip.annotations.Immutable;

/**
 * An implementation of {@link org.gamegineer.game.core.system.IStage} that does
 * not validate its attributes.
 * 
 * <p>
 * This implementation allows malformed stages and thus may violate the contract
 * of {@code IStage}. It is only intended to be used during testing.
 * </p>
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class NonValidatingStage
    implements IStage
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The stage cardinality. */
    private final Integer cardinality_;

    /** The stage identifier. */
    private final String id_;

    /** The child stage list. */
    private final List<IStage> stages_;

    /** The stage strategy. */
    private final IStageStrategy strategy_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NonValidatingStage} class.
     * 
     * @param id
     *        The stage identifier; may be {@code null}.
     * @param strategy
     *        The stage strategy; may be {@code null}.
     * @param cardinality
     *        The stage cardinality; negative to indicate no cardinality.
     * @param stages
     *        The child stage list; may be {@code null}.
     */
    public NonValidatingStage(
        /* @Nullable */
        final String id,
        /* @Nullable */
        final IStageStrategy strategy,
        /* @Nullable */
        final Integer cardinality,
        /* @Nullable */
        final List<IStage> stages )
    {
        id_ = id;
        strategy_ = strategy;
        cardinality_ = cardinality;
        stages_ = (stages != null) ? new ArrayList<IStage>( stages ) : null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * @throws java.lang.IllegalStateException
     *         If this stage has no value specified for its cardinality.
     * 
     * @see org.gamegineer.game.core.system.IStage#getCardinality()
     */
    @SuppressWarnings( "boxing" )
    public int getCardinality()
    {
        assertStateLegal( cardinality_ != null, "cardinality_ is null" ); //$NON-NLS-1$

        return cardinality_;
    }

    /**
     * @return The stage identifier; may be {@code null}.
     * 
     * @see org.gamegineer.game.core.system.IStage#getId()
     */
    /* @Nullable */
    public String getId()
    {
        return id_;
    }

    /**
     * @return A list view of the stages which are children of this stage; may
     *         be {@code null}.
     * 
     * @see org.gamegineer.game.core.system.IStage#getStages()
     */
    /* @Nullable */
    public List<IStage> getStages()
    {
        return (stages_ != null) ? new ArrayList<IStage>( stages_ ) : null;
    }

    /**
     * @return The strategy which implements the game logic for this stage; may
     *         be {@code null}.
     * 
     * @see org.gamegineer.game.core.system.IStage#getStrategy()
     */
    /* @Nullable */
    public IStageStrategy getStrategy()
    {
        return strategy_;
    }

    /**
     * Indicates this stage has a value specified for its cardinality.
     * 
     * @return {@code true} if this stage has a value specified for its
     *         cardinality; otherwise {@code false}.
     */
    public boolean hasCardinality()
    {
        return cardinality_ != null;
    }
}
