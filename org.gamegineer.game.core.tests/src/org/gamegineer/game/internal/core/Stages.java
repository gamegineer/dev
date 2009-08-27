/*
 * Stages.java
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
 * Created on Sep 11, 2008 at 11:05:00 PM.
 */

package org.gamegineer.game.internal.core;

import org.gamegineer.game.core.system.GameSystems;

/**
 * A factory for creating various types of game stage objects suitable for
 * testing.
 */
public final class Stages
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Stages} class.
     */
    private Stages()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new stage with a unique identifier.
     * 
     * @return A new stage; never {@code null}.
     */
    /* @NonNull */
    public static Stage createUniqueStage()
    {
        return new Stage( GameSystems.createUniqueStage() );
    }

    /**
     * Creates a new stage with a unique identifier and at least one child
     * stage.
     * 
     * @return A new stage; never {@code null}.
     */
    /* @NonNull */
    public static Stage createUniqueStageWithChildren()
    {
        return new Stage( GameSystems.createUniqueStageList( 1 ).get( 0 ) );
    }
}
