/*
 * AttributeNameTest.java
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
 * Created on Jun 25, 2008 at 11:17:23 PM.
 */

package org.gamegineer.engine.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import org.gamegineer.engine.core.IState.Scope;
import org.junit.Test;

/**
 * A fixture for testing the {@link org.gamegineer.engine.core.AttributeName}
 * class.
 */
public final class AttributeNameTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AttributeNameTest} class.
     */
    public AttributeNameTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the constructor throws an exception when passed an empty local
     * name.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_LocalName_Empty()
    {
        new AttributeName( Scope.APPLICATION, "" ); //$NON-NLS-1$
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * local name.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_LocalName_Null()
    {
        new AttributeName( Scope.APPLICATION, null );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * scope.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Scope_Null()
    {
        new AttributeName( null, "name" ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code equals} method correctly indicates two equal but
     * different attribute names are equal.
     */
    @Test
    public void testEquals_Equal_NotSame()
    {
        final Scope SCOPE = Scope.APPLICATION;
        final String LOCAL_NAME = "name"; //$NON-NLS-1$
        final AttributeName name1 = new AttributeName( SCOPE, LOCAL_NAME );
        final AttributeName name2 = new AttributeName( SCOPE, LOCAL_NAME );
        assertNotSame( name1, name2 );
        assertEquals( name1, name2 );
        assertEquals( name2, name1 ); // symmetric
    }

    /**
     * Ensures the {@code equals} method correctly handles a {@code null}
     * attribute name.
     */
    @Test
    public void testEquals_Equal_Null()
    {
        final AttributeName name = new AttributeName( Scope.APPLICATION, "name" ); //$NON-NLS-1$
        assertFalse( name.equals( null ) );
    }

    /**
     * Ensures the {@code equals} method correctly indicates the same attribute
     * name is equal to itself.
     */
    @Test
    public void testEquals_Equal_Same()
    {
        final AttributeName name = new AttributeName( Scope.APPLICATION, "name" ); //$NON-NLS-1$
        assertEquals( name, name ); // reflexive
    }

    /**
     * Ensures the {@code equals} method correctly indicates two attribute names
     * whose local names differ are unequal.
     */
    @Test
    public void testEquals_Unequal_LocalName()
    {
        final Scope SCOPE = Scope.APPLICATION;
        final AttributeName name1 = new AttributeName( SCOPE, "name1" ); //$NON-NLS-1$
        final AttributeName name2 = new AttributeName( SCOPE, "name2" ); //$NON-NLS-1$
        assertFalse( name1.equals( name2 ) );
    }

    /**
     * Ensures the {@code equals} method correctly indicates two attribute names
     * whose scopes differ are unequal.
     */
    @Test
    public void testEquals_Unequal_Scope()
    {
        final String LOCAL_NAME = "name"; //$NON-NLS-1$
        final AttributeName name1 = new AttributeName( Scope.APPLICATION, LOCAL_NAME );
        final AttributeName name2 = new AttributeName( Scope.ENGINE_CONTROL, LOCAL_NAME );
        assertFalse( name1.equals( name2 ) );
    }

    /**
     * Ensures the {@code fromString} method throws an exception when passed a
     * name with a bad format.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testFromString_Name_Illegal_BadFormat()
    {
        AttributeName.fromString( "name" ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code fromString} method throws an exception when passed a
     * name with an empty local name.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testFromString_Name_Illegal_EmptyLocalName()
    {
        AttributeName.fromString( "{" + Scope.APPLICATION.toString() + "}" ); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Ensures the {@code fromString} method throws an exception when passed a
     * name with an empty scope.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testFromString_Name_Illegal_EmptyScope()
    {
        AttributeName.fromString( "{}name" ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code fromString} method throws an exception when passed a
     * name with an unknown format.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testFromString_Name_Illegal_UnknownScope()
    {
        AttributeName.fromString( "{UNKNOWN_SCOPE}name" ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code fromString} method correctly parses legal attribute
     * names.
     */
    @Test
    public void testFromString_Name_Legal()
    {
        for( final Scope scope : Scope.values() )
        {
            final AttributeName name = new AttributeName( scope, "name" ); //$NON-NLS-1$
            assertEquals( name, AttributeName.fromString( name.toString() ) );
        }
    }

    /**
     * Ensures the {@code fromString} method throws an exception when passed a
     * {@code null} name.
     */
    @Test( expected = NullPointerException.class )
    public void testFromString_Name_Null()
    {
        AttributeName.fromString( null );
    }

    /**
     * Ensures the {@code hashCode} method returns the same hash code for equal
     * attribute names.
     */
    @Test
    public void testHashCode_Equal()
    {
        final Scope SCOPE = Scope.APPLICATION;
        final String LOCAL_NAME = "name"; //$NON-NLS-1$
        final AttributeName name1 = new AttributeName( SCOPE, LOCAL_NAME );
        final AttributeName name2 = new AttributeName( SCOPE, LOCAL_NAME );
        assertNotSame( name1, name2 );
        assertEquals( name1.hashCode(), name2.hashCode() );
    }
}
