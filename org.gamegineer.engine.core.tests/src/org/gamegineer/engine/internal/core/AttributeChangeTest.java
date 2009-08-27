/*
 * AttributeChangeTest.java
 * Copyright 2008 Gamegineer.org
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
 * Created on Jun 14, 2008 at 11:40:42 AM.
 */

package org.gamegineer.engine.internal.core;

import org.gamegineer.engine.core.AttributeName;
import org.gamegineer.engine.core.IState.Scope;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.AttributeChange} class.
 */
public final class AttributeChangeTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The attribute name for the fixture. */
    private static final AttributeName ATTR_NAME = new AttributeName( Scope.APPLICATION, "name" ); //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AttributeChangeTest} class.
     */
    public AttributeChangeTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code createAddedAttributeChange} method throws an exception
     * if passed a {@code null} name.
     */
    @Test( expected = AssertionError.class )
    public void testCreateAddedAttribute_Name_Null()
    {
        AttributeChange.createAddedAttributeChange( null, new Object() );
    }

    /**
     * Ensures the {@code createAddedAttributeChange} method does not throw an
     * exception if passed a {@code null} new value.
     */
    @Test
    public void testCreateAddedAttribute_NewValue_Null()
    {
        AttributeChange.createAddedAttributeChange( ATTR_NAME, null );
    }

    /**
     * Ensures the {@code createChangedAttributeChange} method throws an
     * exception if passed a {@code null} name.
     */
    @Test( expected = AssertionError.class )
    public void testCreateChangedAttribute_Name_Null()
    {
        AttributeChange.createChangedAttributeChange( null, new Object(), new Object() );
    }

    /**
     * Ensures the {@code createChangedAttributeChange} method does not throw an
     * exception if passed a {@code null} new value.
     */
    @Test
    public void testCreateChangedAttribute_NewValue_Null_OldValue_NonNull()
    {
        AttributeChange.createChangedAttributeChange( ATTR_NAME, new Object(), null );
    }

    /**
     * Ensures the {@code createChangedAttributeChange} method does not throw an
     * exception if passed a {@code null} old value.
     */
    @Test
    public void testCreateChangedAttribute_NewValue_NonNull_OldValue_Null()
    {
        AttributeChange.createChangedAttributeChange( ATTR_NAME, null, new Object() );
    }

    /**
     * Ensures the {@code createRemovedAttributeChange} method throws an
     * exception if passed a {@code null} name.
     */
    @Test( expected = AssertionError.class )
    public void testCreateRemovedAttribute_Name_Null()
    {
        AttributeChange.createRemovedAttributeChange( null, new Object() );
    }

    /**
     * Ensures the {@code createRemovedAttributeChange} method does not throw an
     * exception if passed a {@code null} old value.
     */
    @Test
    public void testCreateRemovedAttribute_OldValue_Null()
    {
        AttributeChange.createRemovedAttributeChange( ATTR_NAME, null );
    }
}
