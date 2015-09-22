/*
 * AbstractTableAdvisorTestCase.java
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
 * Created on Sep 18, 2009 at 9:29:40 PM.
 */

package org.gamegineer.table.ui.test;

import static org.gamegineer.test.core.Assert.assertImmutableCollection;
import java.util.Optional;
import org.gamegineer.table.ui.ITableAdvisor;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link ITableAdvisor} interface.
 */
public abstract class AbstractTableAdvisorTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table advisor under test in the fixture. */
    private Optional<ITableAdvisor> tableAdvisor_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractTableAdvisorTestCase}
     * class.
     */
    protected AbstractTableAdvisorTestCase()
    {
        tableAdvisor_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the table advisor to be tested.
     * 
     * @return The table advisor to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    protected abstract ITableAdvisor createTableAdvisor()
        throws Exception;

    /**
     * Gets the table advisor under test in the fixture.
     * 
     * @return The table advisor under test in the fixture; never {@code null}.
     */
    protected final ITableAdvisor getTableAdvisor()
    {
        return tableAdvisor_.get();
    }

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
        tableAdvisor_ = Optional.of( createTableAdvisor() );
    }

    /**
     * Ensures the {@link ITableAdvisor#getApplicationArguments} method returns
     * an immutable collection.
     */
    @Test
    public void testGetApplicationArguments_ReturnValue_Immutable()
    {
        assertImmutableCollection( getTableAdvisor().getApplicationArguments(), "" ); //$NON-NLS-1$
    }
}
