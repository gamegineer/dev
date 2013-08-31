/*
 * AbstractMementoOriginatorTestCase.java
 * Copyright 2008-2013 Gamegineer.org
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
 * Created on Jun 6, 2011 at 8:01:30 PM.
 */

package org.gamegineer.common.core.test.util.memento;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.gamegineer.common.core.util.memento.IMementoOriginator;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.common.core.util.memento.IMementoOriginator} interface.
 */
public abstract class AbstractMementoOriginatorTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The memento originator under test in the fixture. */
    private IMementoOriginator mementoOriginator_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractMementoOriginatorTestCase} class.
     */
    protected AbstractMementoOriginatorTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Asserts that the specified memento originators are equal.
     * 
     * <p>
     * This implementation compares the two objects using the {@code equals}
     * method.
     * </p>
     * 
     * @param expected
     *        The expected memento originator; must not be {@code null}.
     * @param actual
     *        The actual memento originator; must not be {@code null}.
     * 
     * @throws java.lang.AssertionError
     *         If the two memento originators are not equal.
     * @throws java.lang.NullPointerException
     *         If {@code expected} or {@code actual} is {@code null}.
     */
    protected void assertMementoOriginatorEquals(
        /* @NonNull */
        final IMementoOriginator expected,
        /* @NonNull */
        final IMementoOriginator actual )
    {
        assertArgumentNotNull( expected, "expected" ); //$NON-NLS-1$
        assertArgumentNotNull( actual, "actual" ); //$NON-NLS-1$

        assertEquals( expected, actual );
    }

    /**
     * Creates the memento originator to be tested.
     * 
     * @return The memento originator to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IMementoOriginator createMementoOriginator()
        throws Exception;

    /**
     * Initializes the specified memento originator to a state other than that
     * in the value returned from {@link #createMementoOriginator()}.
     * 
     * @param mementoOriginator
     *        The memento originator; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code mementoOriginator} is {@code null}.
     */
    protected abstract void initializeMementoOriginator(
        /* @NonNull */
        IMementoOriginator mementoOriginator );

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
        mementoOriginator_ = createMementoOriginator();
        assertNotNull( mementoOriginator_ );
    }

    /**
     * Ensures the {@link IMementoOriginator#createMemento} method does not
     * return {@code null}.
     */
    @Test
    public void testCreateMemento_ReturnValue_NonNull()
    {
        assertNotNull( mementoOriginator_.createMemento() );
    }

    /**
     * Ensures the {@link IMementoOriginator#createMemento} and
     * {@link IMementoOriginator#setMemento} methods can save and restore the
     * state of the memento originator.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testSaveAndRestoreMementoOriginatorState()
        throws Exception
    {
        initializeMementoOriginator( mementoOriginator_ );
        final Object memento = mementoOriginator_.createMemento();
        final IMementoOriginator otherMementoOriginator = createMementoOriginator();
        otherMementoOriginator.setMemento( memento );

        assertMementoOriginatorEquals( mementoOriginator_, otherMementoOriginator );
    }

    /**
     * Ensures the {@link IMementoOriginator#setMemento} method throws an
     * exception when passed a {@code null} memento.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testSetMemento_Memento_Null()
        throws Exception
    {
        mementoOriginator_.setMemento( null );
    }
}
