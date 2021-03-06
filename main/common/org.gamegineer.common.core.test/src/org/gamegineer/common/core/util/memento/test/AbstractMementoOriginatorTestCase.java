/*
 * AbstractMementoOriginatorTestCase.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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

package org.gamegineer.common.core.util.memento.test;

import static org.junit.Assert.assertEquals;
import java.util.Optional;
import org.gamegineer.common.core.util.memento.IMementoOriginator;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link IMementoOriginator} interface.
 */
public abstract class AbstractMementoOriginatorTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The memento originator under test in the fixture. */
    private Optional<IMementoOriginator> mementoOriginator_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractMementoOriginatorTestCase} class.
     */
    protected AbstractMementoOriginatorTestCase()
    {
        mementoOriginator_ = Optional.empty();
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
     *        The expected memento originator.
     * @param actual
     *        The actual memento originator.
     * 
     * @throws java.lang.AssertionError
     *         If the two memento originators are not equal.
     */
    protected void assertMementoOriginatorEquals(
        final IMementoOriginator expected,
        final IMementoOriginator actual )
    {
        assertEquals( expected, actual );
    }

    /**
     * Creates the memento originator to be tested.
     * 
     * @return The memento originator to be tested.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    protected abstract IMementoOriginator createMementoOriginator()
        throws Exception;

    /**
     * Gets the memento originator under test in the fixture.
     * 
     * @return The memento originator under test in the fixture.
     */
    protected final IMementoOriginator getMementoOriginator()
    {
        return mementoOriginator_.get();
    }

    /**
     * Initializes the specified memento originator to a state other than that
     * in the value returned from {@link #createMementoOriginator()}.
     * 
     * @param mementoOriginator
     *        The memento originator.
     */
    protected abstract void initializeMementoOriginator(
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
        mementoOriginator_ = Optional.of( createMementoOriginator() );
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
        final IMementoOriginator mementoOriginator = getMementoOriginator();
        initializeMementoOriginator( mementoOriginator );
        final Object memento = mementoOriginator.createMemento();
        final IMementoOriginator otherMementoOriginator = createMementoOriginator();
        otherMementoOriginator.setMemento( memento );

        assertMementoOriginatorEquals( mementoOriginator, otherMementoOriginator );
    }
}
