/*
 * Stage.java
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
 * Created on Nov 15, 2008 at 10:01:39 PM.
 */

package org.gamegineer.game.internal.core.system;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.List;
import net.jcip.annotations.Immutable;
import org.gamegineer.game.core.system.IStage;
import org.gamegineer.game.core.system.IStageStrategy;

/**
 * Implementation of {@link org.gamegineer.game.core.system.IStage}.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class Stage
    implements IStage
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The stage cardinality. */
    private final int cardinality_;

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
     * Initializes a new instance of the {@code Stage} class.
     * 
     * @param id
     *        The stage identifier; must not be {@code null}.
     * @param strategy
     *        The stage strategy; must not be {@code null}.
     * @param cardinality
     *        The stage cardinality.
     * @param stages
     *        The child stage list; must not be {@code null}.
     */
    private Stage(
        /* @NonNull */
        final String id,
        /* @NonNull */
        final IStageStrategy strategy,
        final int cardinality,
        /* @NonNull */
        final List<IStage> stages )
    {
        assert id != null;
        assert strategy != null;
        assert stages != null;

        id_ = id;
        strategy_ = strategy;
        cardinality_ = cardinality;
        stages_ = new ArrayList<IStage>( stages );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new instance of the {@code Stage} class.
     * 
     * @param id
     *        The stage identifier; must not be {@code null}.
     * @param strategy
     *        The stage strategy; must not be {@code null}.
     * @param cardinality
     *        The stage cardinality.
     * @param stages
     *        The child stage list; must not be {@code null}.
     * 
     * @return A new stage; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If any argument will result in an illegal stage.
     * @throws java.lang.NullPointerException
     *         If {@code id}, {@code strategy}, or {@code stages} is {@code
     *         null}.
     */
    /* @NonNull */
    public static Stage createStage(
        /* @NonNull */
        final String id,
        /* @NonNull */
        final IStageStrategy strategy,
        final int cardinality,
        /* @NonNull */
        final List<IStage> stages )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$
        assertArgumentNotNull( strategy, "strategy" ); //$NON-NLS-1$
        assertArgumentNotNull( stages, "stages" ); //$NON-NLS-1$

        final Stage stage = new Stage( id, strategy, cardinality, stages );
        GameSystemUtils.assertStageLegal( stage );
        return stage;
    }

    /*
     * @see org.gamegineer.game.core.system.IStage#getCardinality()
     */
    public int getCardinality()
    {
        return cardinality_;
    }

    /*
     * @see org.gamegineer.game.core.system.IStage#getId()
     */
    public String getId()
    {
        return id_;
    }

    /*
     * @see org.gamegineer.game.core.system.IStage#getStages()
     */
    public List<IStage> getStages()
    {
        return new ArrayList<IStage>( stages_ );
    }

    /*
     * @see org.gamegineer.game.core.system.IStage#getStrategy()
     */
    public IStageStrategy getStrategy()
    {
        return strategy_;
    }
}
