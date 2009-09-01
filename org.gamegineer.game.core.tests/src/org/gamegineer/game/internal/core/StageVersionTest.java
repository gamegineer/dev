/*
 * StageVersionTest.java
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
 * Created on Aug 29, 2008 at 10:30:57 PM.
 */

package org.gamegineer.game.internal.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;
import java.lang.reflect.Constructor;
import java.util.UUID;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.internal.core.StageVersion} class.
 */
public final class StageVersionTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code StageVersionTest} class.
     */
    public StageVersionTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new stage version with a unique instance identifier and a
     * modification count of zero.
     * 
     * @return A new stage version; never {@code null}.
     */
    /* @NonNull */
    private static StageVersion createStageVersion()
    {
        return new StageVersion();
    }

    /**
     * Creates a new stage version with the specified instance identifier and
     * modification count.
     * 
     * @param instanceId
     *        The stage instance identifier; must not be {@code null}.
     * @param modificationCount
     *        The stage modification count.
     * 
     * @return A new stage version; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static StageVersion createStageVersion(
        /* @NonNull */
        final String instanceId,
        final int modificationCount )
    {
        try
        {
            final Constructor<StageVersion> ctor = StageVersion.class.getDeclaredConstructor( String.class, Integer.TYPE );
            ctor.setAccessible( true );
            return ctor.newInstance( instanceId, modificationCount );
        }
        catch( final Exception e )
        {
            fail( String.format( "failed to create StageVersion instance (%1$s)", e.getClass().getName() ) ); //$NON-NLS-1$
            return null; // never reached
        }
    }

    /**
     * Ensures the {@code equals} method correctly indicates two equal but
     * different objects are equal.
     */
    @Test
    public void testEquals_Equal_NotSame()
    {
        final StageVersion version1 = createStageVersion();
        final StageVersion version2 = createStageVersion( version1.getInstanceId(), version1.getModificationCount() );

        assertNotSame( version1, version2 );
        assertEquals( version1, version2 );
        assertEquals( version2, version1 ); // symmetric
    }

    /**
     * Ensures the {@code equals} method correctly handles a {@code null}
     * object.
     */
    @Test
    public void testEquals_Equal_Null()
    {
        final StageVersion version = createStageVersion();

        assertFalse( version.equals( null ) );
    }

    /**
     * Ensures the {@code equals} method correctly indicates the same object is
     * equal to itself.
     */
    @Test
    public void testEquals_Equal_Same()
    {
        final StageVersion version = createStageVersion();

        assertEquals( version, version ); // reflexive
    }

    /**
     * Ensures the {@code equals} method correctly indicates two objects whose
     * instance identifiers differ but whose modification counts are equal are
     * unequal.
     */
    @Test
    public void testEquals_Unequal_InstanceId()
    {
        final StageVersion version1 = createStageVersion();
        final StageVersion version2 = createStageVersion( UUID.randomUUID().toString(), version1.getModificationCount() );

        assertFalse( version1.equals( version2 ) );
    }

    /**
     * Ensures the {@code equals} method correctly indicates two objects whose
     * instance identifiers are equal but whose modification counts differ are
     * unequal.
     */
    @Test
    public void testEquals_Unequal_ModificationCount()
    {
        final StageVersion version1 = createStageVersion();
        final StageVersion version2 = createStageVersion( version1.getInstanceId(), version1.getModificationCount() + 1 );

        assertFalse( version1.equals( version2 ) );
    }

    /**
     * Ensures the {@code hashCode} method returns the same hash code for equal
     * objects.
     */
    @Test
    public void testHashCode_Equal()
    {
        final StageVersion version1 = createStageVersion();
        final StageVersion version2 = createStageVersion( version1.getInstanceId(), version1.getModificationCount() );

        assertNotSame( version1, version2 );
        assertEquals( version1.hashCode(), version2.hashCode() );
    }

    /**
     * Ensures the {@code increment} method returns a new stage version with the
     * same instance identifier but an incremented modification count.
     */
    @Test
    public void testIncrement()
    {
        final StageVersion version1 = createStageVersion();
        final StageVersion version2 = version1.increment();

        assertEquals( version1.getInstanceId(), version2.getInstanceId() );
        assertEquals( version1.getModificationCount() + 1, version2.getModificationCount() );
    }
}
