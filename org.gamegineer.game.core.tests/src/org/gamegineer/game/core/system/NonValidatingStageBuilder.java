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
    private Integer m_cardinality;

    /** The stage identifier. */
    private String m_id;

    /** The child stage list. */
    private List<IStage> m_stages;

    /** The stage strategy. */
    private IStageStrategy m_strategy;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NonValidatingStageBuilder} class
     * with an uninitialized stage.
     */
    public NonValidatingStageBuilder()
    {
        m_id = null;
        m_strategy = null;
        m_cardinality = null;
        m_stages = null;
    }

    /**
     * Initializes a new instance of the {@code NonValidatingStageBuilder} class
     * using the specified stage.
     * 
     * @param stage
     *        The stage used to initialize this builder; must not be
     *        {@code null}.
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

        m_id = stage.getId();
        m_strategy = stage.getStrategy();
        m_cardinality = stage.getCardinality();
        m_stages = stage.getStages();
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
        if( m_stages == null )
        {
            m_stages = new ArrayList<IStage>();
        }

        m_stages.add( stage );

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
        m_cardinality = cardinality;

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
        m_id = id;

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
        m_strategy = strategy;

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
        return new NonValidatingStage( m_id, m_strategy, m_cardinality, m_stages );
    }
}
