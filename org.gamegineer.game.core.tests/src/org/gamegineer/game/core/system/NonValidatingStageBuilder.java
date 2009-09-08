/*
 * NonValidatingStageBuilder.java
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
 * Created on Nov 20, 2008 at 11:04:39 PM.
 */

package org.gamegineer.game.core.system;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * A factory for building instances of
 * {@link org.gamegineer.game.core.system.NonValidatingStage}.
 * 
 * <p>
 * Each stage built by an instance of this class is immutable and thus
 * guaranteed to be thread-safe. This class does not validate its attributes and
 * thus allows the construction of malformed stages that may violate the
 * contract of {@code IStage}. It is only intended to be used during testing.
 * </p>
 * 
 * <p>
 * This class is not thread-safe.
 * </p>
 */
public final class NonValidatingStageBuilder
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The stage cardinality. */
    private Integer cardinality_;

    /** The stage identifier. */
    private String id_;

    /** The child stage list. */
    private List<IStage> stages_;

    /** The stage strategy. */
    private IStageStrategy strategy_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NonValidatingStageBuilder} class
     * with an uninitialized stage.
     */
    public NonValidatingStageBuilder()
    {
        id_ = null;
        strategy_ = null;
        cardinality_ = null;
        stages_ = null;
    }

    /**
     * Initializes a new instance of the {@code NonValidatingStageBuilder} class
     * using the specified stage.
     * 
     * @param stage
     *        The stage used to initialize this builder; must not be {@code
     *        null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code stage} is {@code null}.
     */
    @SuppressWarnings( "boxing" )
    public NonValidatingStageBuilder(
        /* @NonNull */
        final IStage stage )
    {
        assertArgumentNotNull( stage, "stage" ); //$NON-NLS-1$

        id_ = stage.getId();
        strategy_ = stage.getStrategy();
        cardinality_ = stage.getCardinality();
        stages_ = stage.getStages();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds a child stage to the stage being built.
     * 
     * @param stage
     *        The child stage; may be {@code null}.
     * 
     * @return A reference to this builder; never {@code null}.
     */
    /* @NonNull */
    public NonValidatingStageBuilder addStage(
        /* @Nullable */
        final IStage stage )
    {
        if( stages_ == null )
        {
            stages_ = new ArrayList<IStage>();
        }

        stages_.add( stage );

        return this;
    }

    /**
     * Sets the stage cardinality.
     * 
     * @param cardinality
     *        The stage cardinality; may be {@code null}.
     * 
     * @return A reference to this builder; never {@code null}.
     */
    /* @NonNull */
    public NonValidatingStageBuilder setCardinality(
        final Integer cardinality )
    {
        cardinality_ = cardinality;

        return this;
    }

    /**
     * Sets the stage identifier.
     * 
     * @param id
     *        The stage identifier; may be {@code null}.
     * 
     * @return A reference to this builder; never {@code null}.
     */
    /* @NonNull */
    public NonValidatingStageBuilder setId(
        /* @Nullable */
        final String id )
    {
        id_ = id;

        return this;
    }

    /**
     * Sets the stage strategy.
     * 
     * @param strategy
     *        The stage strategy; may be {@code null}.
     * 
     * @return A reference to this builder; never {@code null}.
     */
    /* @NonNull */
    public NonValidatingStageBuilder setStrategy(
        /* @Nullable */
        final IStageStrategy strategy )
    {
        strategy_ = strategy;

        return this;
    }

    /**
     * Creates a new stage based on the current state of this builder.
     * 
     * @return A new stage; never {@code null}.
     */
    /* @NonNull */
    public IStage toStage()
    {
        return new NonValidatingStage( id_, strategy_, cardinality_, stages_ );
    }
}
