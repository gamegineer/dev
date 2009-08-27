/*
 * StageBuilder.java
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
 * Created on Nov 15, 2008 at 9:14:33 PM.
 */

package org.gamegineer.game.core.system;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.util.ArrayList;
import java.util.List;
import org.gamegineer.game.internal.core.system.Stage;

/**
 * A factory for building instances of
 * {@link org.gamegineer.game.core.system.IStage}.
 * 
 * <p>
 * Each stage built by an instance of this class is immutable and thus
 * guaranteed to be thread-safe.
 * </p>
 * 
 * <p>
 * This class is not thread-safe.
 * </p>
 */
public final class StageBuilder
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The stage cardinality. */
    private Integer m_cardinality;

    /** The stage identifier. */
    private String m_id;

    /** The child stage list. */
    private final List<IStage> m_stages;

    /** The stage strategy. */
    private IStageStrategy m_strategy;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code StageBuilder} class with an
     * empty stage.
     */
    public StageBuilder()
    {
        m_id = null;
        m_strategy = null;
        m_cardinality = null;
        m_stages = new ArrayList<IStage>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds a child stage.
     * 
     * @param stage
     *        The child stage; must not be {@code null}.
     * 
     * @return A reference to this builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code stage} is {@code null}.
     */
    /* @NonNull */
    public StageBuilder addStage(
        /* @NonNull */
        final IStage stage )
    {
        assertArgumentNotNull( stage, "stage" ); //$NON-NLS-1$

        m_stages.add( stage );

        return this;
    }

    /**
     * Adds a collection of child stages.
     * 
     * @param stages
     *        The stage list; must not be {@code null}.
     * 
     * @return A reference to this builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code stages} is {@code null}.
     */
    /* @NonNull */
    public StageBuilder addStages(
        /* @NonNull */
        final List<IStage> stages )
    {
        assertArgumentNotNull( stages, "stages" ); //$NON-NLS-1$

        m_stages.addAll( stages );

        return this;
    }

    /**
     * Gets the stage identifier.
     * 
     * @return The stage identifier; may be {@code null}.
     */
    /* @Nullable */
    String getId()
    {
        return m_id;
    }

    /**
     * Sets the stage cardinality.
     * 
     * @param cardinality
     *        The stage cardinality; must not be negative.
     * 
     * @return A reference to this builder; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    public StageBuilder setCardinality(
        final int cardinality )
    {
        m_cardinality = cardinality;

        return this;
    }

    /**
     * Sets the stage identifier.
     * 
     * @param id
     *        The stage identifier; must not be {@code null}.
     * 
     * @return A reference to this builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    /* @NonNull */
    public StageBuilder setId(
        /* @NonNull */
        final String id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        m_id = id;

        return this;
    }

    /**
     * Sets the stage strategy.
     * 
     * @param strategy
     *        The stage strategy; must not be {@code null}.
     * 
     * @return A reference to this builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code strategy} is {@code null}.
     */
    /* @NonNull */
    public StageBuilder setStrategy(
        /* @NonNull */
        final IStageStrategy strategy )
    {
        assertArgumentNotNull( strategy, "strategy" ); //$NON-NLS-1$

        m_strategy = strategy;

        return this;
    }

    /**
     * Creates a new stage based on the current state of this builder.
     * 
     * @return A new stage; never {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the current state of this builder does not represent a legal
     *         stage.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    public IStage toStage()
    {
        assertStateLegal( m_id != null, Messages.StageBuilder_id_notSet );
        assertStateLegal( m_strategy != null, Messages.StageBuilder_strategy_notSet );
        assertStateLegal( m_cardinality != null, Messages.StageBuilder_cardinality_notSet );

        try
        {
            return Stage.createStage( m_id, m_strategy, m_cardinality, m_stages );
        }
        catch( final IllegalArgumentException e )
        {
            throw new IllegalStateException( Messages.StageBuilder_state_illegal, e );
        }
    }
}
