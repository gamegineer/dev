/*
 * StateTest.java
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
 * Created on Apr 22, 2008 at 9:32:07 PM.
 */

package org.gamegineer.engine.internal.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.HashSet;
import java.util.Set;
import org.gamegineer.engine.core.AttributeName;
import org.gamegineer.engine.core.IState.Scope;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link org.gamegineer.engine.internal.core.State}
 * class.
 */
public final class StateTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The state under test in the fixture. */
    private State m_state;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code StateTest} class.
     */
    public StateTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Sets up the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Before
    public void setUp()
        throws Exception
    {
        m_state = new State();
    }

    /**
     * Tears down the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @After
    public void tearDown()
        throws Exception
    {
        m_state = null;
    }

    /**
     * Ensures the {@code addAttribute} method throws an exception when called
     * with an active read-only transaction.
     */
    @Test( expected = IllegalStateException.class )
    public void testAddAttribute_Transaction_ActiveReadOnly()
    {
        m_state.beginTransaction();
        m_state.prepareToCommitTransaction();
        m_state.addAttribute( new AttributeName( Scope.APPLICATION, "name" ), "value" ); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Ensures the {@code addAttribute} method throws an exception when called
     * without an active transaction.
     */
    @Test( expected = IllegalStateException.class )
    public void testAddAttribute_Transaction_Inactive()
    {
        m_state.addAttribute( new AttributeName( Scope.APPLICATION, "name" ), "value" ); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Ensures the {@code beginTransaction} method throws an exception when
     * called during an active transaction.
     */
    @Test( expected = IllegalStateException.class )
    public void testBeginTransaction_Transaction_Active()
    {
        m_state.beginTransaction();
        m_state.beginTransaction();
    }

    /**
     * Ensures the {@code beginTransaction} method throws an exception when
     * called during an active read-only transaction.
     */
    @Test( expected = IllegalStateException.class )
    public void testBeginTransaction_Transaction_ActiveReadOnly()
    {
        m_state.beginTransaction();
        m_state.prepareToCommitTransaction();
        m_state.beginTransaction();
    }

    /**
     * Ensures the {@code beginTransaction} method activates the transaction
     * when called without an active transaction.
     */
    @Test
    public void testBeginTransaction_Transaction_Inactive()
    {
        m_state.beginTransaction();
        assertTrue( m_state.isTransactionActive() );
    }

    /**
     * Ensures the {@code commitTransaction} deactivates the transaction and
     * retains all state changes when called during an active transaction.
     */
    @Test
    public void testCommitTransaction_Transaction_Active()
    {
        final AttributeName name = new AttributeName( Scope.APPLICATION, "name" ); //$NON-NLS-1$
        m_state.beginTransaction();
        m_state.addAttribute( name, "value1" ); //$NON-NLS-1$
        m_state.commitTransaction();

        m_state.beginTransaction();
        m_state.setAttribute( name, "value2" ); //$NON-NLS-1$
        m_state.commitTransaction();
        assertFalse( m_state.isTransactionActive() );
        assertEquals( "value2", m_state.getAttribute( name ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code commitTransaction} deactivates the transaction and
     * retains all state changes when called during an active read-only
     * transaction.
     */
    @Test
    public void testCommitTransaction_Transaction_ActiveReadOnly()
    {
        final AttributeName name = new AttributeName( Scope.APPLICATION, "name" ); //$NON-NLS-1$
        m_state.beginTransaction();
        m_state.addAttribute( name, "value1" ); //$NON-NLS-1$
        m_state.commitTransaction();

        m_state.beginTransaction();
        m_state.setAttribute( name, "value2" ); //$NON-NLS-1$
        m_state.prepareToCommitTransaction();
        m_state.commitTransaction();
        assertFalse( m_state.isTransactionActive() );
        assertEquals( "value2", m_state.getAttribute( name ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code commitTransaction} method throws an exception when
     * called without an active transaction.
     */
    @Test( expected = IllegalStateException.class )
    public void testCommitTransaction_Transaction_Inactive()
    {
        m_state.commitTransaction();
    }

    /**
     * Ensures the {@code containsAttribute} method does not throw an exception
     * when called without an active transaction.
     */
    @Test
    public void testContainsAttribute_Transaction_Inactive()
    {
        m_state.containsAttribute( new AttributeName( Scope.APPLICATION, "name" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getAttribute} method does not throw an exception when
     * called without an active transaction.
     */
    @Test
    public void testGetAttribute_Transaction_Inactive()
    {
        final AttributeName name = new AttributeName( Scope.APPLICATION, "name" ); //$NON-NLS-1$
        m_state.beginTransaction();
        m_state.addAttribute( name, "value" ); //$NON-NLS-1$
        m_state.commitTransaction();

        m_state.getAttribute( name );
    }

    /**
     * Ensures the {@code getAttributeChanges} method throws an exception when
     * called without an active transaction.
     */
    @Test( expected = IllegalStateException.class )
    public void testGetAttributeChanges_Transaction_Inactive()
    {
        m_state.getAttributeChanges();
    }

    /**
     * Ensures the {@code getAttributeNames()} method does not throw an
     * exception when called without an active transaction.
     */
    @Test
    public void testGetAttributeNames_Transaction_Inactive()
    {
        m_state.getAttributeNames();
    }

    /**
     * Ensures the {@code getAttributeNames(Scope)} method returns all the
     * attribute names in the state when the specified scope contains at least
     * one attribute.
     */
    @Test
    public void testGetAttributeNamesWithScope()
    {
        final AttributeName name1 = new AttributeName( Scope.APPLICATION, "name1" ); //$NON-NLS-1$
        final AttributeName name2 = new AttributeName( Scope.ENGINE_CONTROL, "name2" ); //$NON-NLS-1$
        m_state.beginTransaction();
        m_state.addAttribute( name1, "value1" ); //$NON-NLS-1$
        m_state.addAttribute( name2, "value2" ); //$NON-NLS-1$
        m_state.commitTransaction();

        final Set<AttributeName> nameSet = m_state.getAttributeNames( Scope.APPLICATION );
        assertTrue( nameSet.size() == 1 );
        assertTrue( nameSet.contains( name1 ) );
    }

    /**
     * Ensures the {@code getAttributeNames(Scope)} method returns an empty set
     * when the state contains no attributes in the specified scope.
     */
    @Test
    public void testGetAttributeNamesWithScope_Empty()
    {
        final Set<AttributeName> nameSet = m_state.getAttributeNames( Scope.APPLICATION );
        assertNotNull( nameSet );
        assertTrue( nameSet.size() == 0 );
    }

    /**
     * Ensures the {@code getAttributeNames(Scope)} method throws an exception
     * when passed a {@code null} scope.
     */
    @Test( expected = AssertionError.class )
    public void testGetAttributeNamesWithScope_Scope_Null()
    {
        m_state.getAttributeNames( null );
    }

    /**
     * Ensures the {@code getAttributeNames(Scope)} method does not throw an
     * exception when called without an active transaction.
     */
    @Test
    public void testGetAttributeNamesWithScope_Transaction_Inactive()
    {
        m_state.getAttributeNames( Scope.APPLICATION );
    }

    /**
     * Ensures the {@code prepareToCommitTransaction} method throws an exception
     * when called with an active read-only transaction.
     */
    @Test( expected = IllegalStateException.class )
    public void testPrepareToCommitTransaction_Transaction_ActiveReadOnly()
    {
        m_state.beginTransaction();
        m_state.prepareToCommitTransaction();
        m_state.prepareToCommitTransaction();
    }

    /**
     * Ensures the {@code prepareToCommitTransaction} method throws an exception
     * when called without an active transaction.
     */
    @Test( expected = IllegalStateException.class )
    public void testPrepareToCommitTransaction_Transaction_Inactive()
    {
        m_state.prepareToCommitTransaction();
    }

    /**
     * Ensures the {@code removeAllAttributes} throws an exception when at least
     * one of the specified attributes do not exist.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveAllAttributes_Attribute_Absent()
    {
        final Set<AttributeName> names = new HashSet<AttributeName>();
        names.add( new AttributeName( Scope.APPLICATION, "name" ) ); //$NON-NLS-1$
        m_state.beginTransaction();
        m_state.removeAllAttributes( names );
        m_state.commitTransaction();
    }

    /**
     * Ensures the {@code removeAllAttributes} method behaves correctly when all
     * of the specified attributes do exist.
     */
    @Test
    public void testRemoveAllAttributes_Attribute_Present()
    {
        final AttributeName name = new AttributeName( Scope.APPLICATION, "name" ); //$NON-NLS-1$
        m_state.beginTransaction();
        m_state.addAttribute( name, "value" ); //$NON-NLS-1$
        m_state.commitTransaction();
        assertTrue( m_state.containsAttribute( name ) );

        final Set<AttributeName> names = new HashSet<AttributeName>();
        names.add( name );
        m_state.beginTransaction();
        m_state.removeAllAttributes( names );
        m_state.commitTransaction();
        assertFalse( m_state.containsAttribute( name ) );
    }

    /**
     * Ensures the {@code removeAllAttributes} method throws an exception when
     * passed a {@code null} name set.
     */
    @Test( expected = AssertionError.class )
    public void testRemoveAllAttributes_Names_Null()
    {
        m_state.removeAllAttributes( null );
    }

    /**
     * Ensures the {@code removeAllAttributes} method throws an exception when
     * called with an active read-only transaction.
     */
    @Test( expected = IllegalStateException.class )
    public void testRemoveAllAttributes_Transaction_ActiveReadOnly()
    {
        final AttributeName name = new AttributeName( Scope.APPLICATION, "name" ); //$NON-NLS-1$
        m_state.beginTransaction();
        m_state.addAttribute( name, "value" ); //$NON-NLS-1$
        m_state.commitTransaction();

        final Set<AttributeName> names = new HashSet<AttributeName>();
        names.add( name );
        m_state.beginTransaction();
        m_state.prepareToCommitTransaction();
        m_state.removeAllAttributes( names );
    }

    /**
     * Ensures the {@code removeAllAttributes} method throws an exception when
     * called without an active transaction.
     */
    @Test( expected = IllegalStateException.class )
    public void testRemoveAllAttributes_Transaction_Inactive()
    {
        final AttributeName name = new AttributeName( Scope.APPLICATION, "name" ); //$NON-NLS-1$
        m_state.beginTransaction();
        m_state.addAttribute( name, "value" ); //$NON-NLS-1$
        m_state.commitTransaction();

        final Set<AttributeName> names = new HashSet<AttributeName>();
        names.add( name );
        m_state.removeAllAttributes( names );
    }

    /**
     * Ensures the {@code removeAttribute} method throws an exception when
     * called with an active read-only transaction.
     */
    @Test( expected = IllegalStateException.class )
    public void testRemoveAttribute_Transaction_ActiveReadOnly()
    {
        final AttributeName name = new AttributeName( Scope.APPLICATION, "name" ); //$NON-NLS-1$
        m_state.beginTransaction();
        m_state.addAttribute( name, "value" ); //$NON-NLS-1$
        m_state.commitTransaction();

        m_state.beginTransaction();
        m_state.prepareToCommitTransaction();
        m_state.removeAttribute( name );
    }

    /**
     * Ensures the {@code removeAttribute} method throws an exception when
     * called without an active transaction.
     */
    @Test( expected = IllegalStateException.class )
    public void testRemoveAttribute_Transaction_Inactive()
    {
        final AttributeName name = new AttributeName( Scope.APPLICATION, "name" ); //$NON-NLS-1$
        m_state.beginTransaction();
        m_state.addAttribute( name, "value" ); //$NON-NLS-1$
        m_state.commitTransaction();

        m_state.removeAttribute( name );
    }

    /**
     * Ensures the {@code rollbackTransaction} deactivates the transaction and
     * discards all state changes when called during an active transaction.
     */
    @Test
    public void testRollbackTransaction_Transaction_Active()
    {
        final AttributeName name = new AttributeName( Scope.APPLICATION, "name" ); //$NON-NLS-1$
        m_state.beginTransaction();
        m_state.addAttribute( name, "value1" ); //$NON-NLS-1$
        m_state.commitTransaction();

        m_state.beginTransaction();
        m_state.setAttribute( name, "value2" ); //$NON-NLS-1$
        m_state.rollbackTransaction();
        assertFalse( m_state.isTransactionActive() );
        assertEquals( "value1", m_state.getAttribute( name ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code rollbackTransaction} deactivates the transaction and
     * discards all state changes when called during an active read-only
     * transaction.
     */
    @Test
    public void testRollbackTransaction_Transaction_ActiveReadOnly()
    {
        final AttributeName name = new AttributeName( Scope.APPLICATION, "name" ); //$NON-NLS-1$
        m_state.beginTransaction();
        m_state.addAttribute( name, "value1" ); //$NON-NLS-1$
        m_state.commitTransaction();

        m_state.beginTransaction();
        m_state.setAttribute( name, "value2" ); //$NON-NLS-1$
        m_state.prepareToCommitTransaction();
        m_state.rollbackTransaction();
        assertFalse( m_state.isTransactionActive() );
        assertEquals( "value1", m_state.getAttribute( name ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code rollbackTransaction} method throws an exception when
     * called without an active transaction.
     */
    @Test( expected = IllegalStateException.class )
    public void testRollbackTransaction_Transaction_Inactive()
    {
        m_state.rollbackTransaction();
    }

    /**
     * Ensures the {@code setAttribute} method throws an exception when called
     * with an active read-only transaction.
     */
    @Test( expected = IllegalStateException.class )
    public void testSetAttribute_Transaction_ActiveReadOnly()
    {
        final AttributeName name = new AttributeName( Scope.APPLICATION, "name" ); //$NON-NLS-1$
        m_state.beginTransaction();
        m_state.addAttribute( name, "value" ); //$NON-NLS-1$
        m_state.commitTransaction();

        m_state.beginTransaction();
        m_state.prepareToCommitTransaction();
        m_state.setAttribute( name, "value" ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code setAttribute} method throws an exception when called
     * without an active transaction.
     */
    @Test( expected = IllegalStateException.class )
    public void testSetAttribute_Transaction_Inactive()
    {
        final AttributeName name = new AttributeName( Scope.APPLICATION, "name" ); //$NON-NLS-1$
        m_state.beginTransaction();
        m_state.addAttribute( name, "value" ); //$NON-NLS-1$
        m_state.commitTransaction();

        m_state.setAttribute( name, "value" ); //$NON-NLS-1$
    }
}
