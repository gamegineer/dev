/*
 * FileNameHistoryPreferencesTest.java
 * Copyright 2008-2010 Gamegineer.org
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
 * Created on Aug 14, 2010 at 10:38:49 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.model.FileNameHistoryPreferences}
 * class.
 */
public final class FileNameHistoryPreferencesTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The file name history preferences under test in the fixture. */
    private FileNameHistoryPreferences fileNameHistoryPreferences_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FileNameHistoryPreferencesTest}
     * class.
     */
    public FileNameHistoryPreferencesTest()
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
        fileNameHistoryPreferences_ = new FileNameHistoryPreferences();
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
        fileNameHistoryPreferences_ = null;
    }

    /**
     * Ensures the {@code getFileNames} method returns a copy of the file names
     * collection.
     */
    @Test
    public void testGetFileNames_ReturnValue_Copy()
    {
        final List<String> fileNames = fileNameHistoryPreferences_.getFileNames();
        final List<String> expectedFileNames = new ArrayList<String>( fileNames );
        fileNames.add( "fileName" ); //$NON-NLS-1$

        final List<String> actualFileNames = fileNameHistoryPreferences_.getFileNames();

        assertEquals( expectedFileNames, actualFileNames );
    }
}
