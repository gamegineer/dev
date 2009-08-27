/*
 * Assert.java
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
 * Created on Nov 14, 2008 at 11:17:42 PM.
 */

package org.gamegineer.game.core.system;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.List;

/**
 * A collection of custom assertion methods for testing game system types.
 */
public final class Assert
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Assert} class.
     */
    private Assert()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Asserts that two game systems are equal.
     * 
     * <p>
     * If the two game systems are not equal, an {@code AssertionError} without
     * a message is thrown. If {@code expected} and {@code actual} are
     * {@code null}, they are considered equal.
     * </p>
     * 
     * @param expected
     *        The expected game system; may be {@code null}.
     * @param actual
     *        The actual game system; may be {@code null}.
     */
    @SuppressWarnings( "boxing" )
    public static void assertGameSystemEquals(
        /* @Nullable */
        final IGameSystem expected,
        /* @Nullable */
        final IGameSystem actual )
    {
        if( expected == actual )
        {
            return;
        }
        assertNotNull( expected );
        assertNotNull( actual );

        assertEquals( expected.getId(), actual.getId() );

        final List<IRole> expectedRoles = expected.getRoles();
        final List<IRole> actualRoles = actual.getRoles();
        assertEquals( expectedRoles.size(), actualRoles.size() );
        for( int index = 0, size = expectedRoles.size(); index < size; ++index )
        {
            assertRoleEquals( expectedRoles.get( index ), actualRoles.get( index ) );
        }

        final List<IStage> expectedStages = expected.getStages();
        final List<IStage> actualStages = actual.getStages();
        assertEquals( expectedStages.size(), actualStages.size() );
        for( int index = 0, size = expectedStages.size(); index < size; ++index )
        {
            assertStageEquals( expectedStages.get( index ), actualStages.get( index ) );
        }
    }

    /**
     * Asserts that two roles are equal.
     * 
     * <p>
     * If the two roles are not equal, an {@code AssertionError} without a
     * message is thrown. If {@code expected} and {@code actual} are
     * {@code null}, they are considered equal.
     * </p>
     * 
     * @param expected
     *        The expected role; may be {@code null}.
     * @param actual
     *        The actual role; may be {@code null}.
     */
    public static void assertRoleEquals(
        /* @Nullable */
        final IRole expected,
        /* @Nullable */
        final IRole actual )
    {
        if( expected == actual )
        {
            return;
        }
        assertNotNull( expected );
        assertNotNull( actual );

        assertEquals( expected.getId(), actual.getId() );
    }

    /**
     * Asserts that two stages are equal.
     * 
     * <p>
     * If the two stages are not equal, an {@code AssertionError} without a
     * message is thrown. If {@code expected} and {@code actual} are
     * {@code null}, they are considered equal.
     * </p>
     * 
     * @param expected
     *        The expected stage; may be {@code null}.
     * @param actual
     *        The actual stage; may be {@code null}.
     */
    @SuppressWarnings( "boxing" )
    public static void assertStageEquals(
        /* @Nullable */
        final IStage expected,
        /* @Nullable */
        final IStage actual )
    {
        if( expected == actual )
        {
            return;
        }
        assertNotNull( expected );
        assertNotNull( actual );

        assertEquals( expected.getId(), actual.getId() );
        assertEquals( expected.getStrategy().getClass().getName(), actual.getStrategy().getClass().getName() );
        assertEquals( expected.getCardinality(), actual.getCardinality() );

        final List<IStage> expectedStages = expected.getStages();
        final List<IStage> actualStages = actual.getStages();
        assertEquals( expectedStages.size(), actualStages.size() );
        for( int index = 0, size = expectedStages.size(); index < size; ++index )
        {
            assertStageEquals( expectedStages.get( index ), actualStages.get( index ) );
        }
    }
}
