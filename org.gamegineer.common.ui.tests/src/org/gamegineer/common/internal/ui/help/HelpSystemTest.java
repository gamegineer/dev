/*
 * HelpSystemTest.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Jan 4, 2012 at 8:38:37 PM.
 */

package org.gamegineer.common.internal.ui.help;

import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.internal.ui.help.HelpSystem} class.
 */
public final class HelpSystemTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The help system under test in the fixture. */
    private HelpSystem helpSystem_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code HelpSystemTest} class.
     */
    public HelpSystemTest()
    {
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
        helpSystem_ = new HelpSystem();
    }

    /**
     * Ensures the {@link HelpSystem#registerHelpSetProvider} method throws an
     * exception when passed a {@code null} help set provider reference.
     */
    @Test( expected = NullPointerException.class )
    public void testRegisterHelpSetProviderFromServiceReference_HelpSetProviderReference_Null()
    {
        helpSystem_.registerHelpSetProvider( null );
    }

    /**
     * Ensures the {@link HelpSystem#unregisterHelpSetProvider} method throws an
     * exception when passed a {@code null} help set provider reference.
     */
    @Test( expected = NullPointerException.class )
    public void testUnregisterHelpSetProviderFromServiceReference_HelpSetProviderReference_Null()
    {
        helpSystem_.unregisterHelpSetProvider( null );
    }
}
