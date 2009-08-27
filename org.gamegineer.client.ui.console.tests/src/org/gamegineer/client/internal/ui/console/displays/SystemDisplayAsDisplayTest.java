/*
 * SystemDisplayAsDisplayTest.java
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
 * Created on Sep 28, 2008 at 11:27:41 PM.
 */

package org.gamegineer.client.internal.ui.console.displays;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import org.gamegineer.client.ui.console.AbstractDisplayTestCase;
import org.gamegineer.client.ui.console.IDisplay;
import org.junit.Ignore;

/**
 * A fixture for testing the
 * {@link org.gamegineer.client.internal.ui.console.displays.SystemDisplay}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.client.ui.console.IDisplay} interface.
 */
@Ignore( "There does not seem to be a way to test the system console because it is too tightly coupled to the JVM and not substitutable." )
public final class SystemDisplayAsDisplayTest
    extends AbstractDisplayTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code SystemDisplayAsDisplayTest}
     * class.
     */
    public SystemDisplayAsDisplayTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.client.ui.console.AbstractDisplayTestCase#createDisplay()
     */
    @Override
    protected IDisplay createDisplay()
        throws Exception
    {
        return SystemDisplay.createSystemDisplay();
    }

    /*
     * @see org.gamegineer.client.ui.console.AbstractDisplayTestCase#readDisplayOutput(org.gamegineer.client.ui.console.IDisplay)
     */
    @Override
    protected String readDisplayOutput(
        final IDisplay display )
    {
        assertArgumentNotNull( display, "display" ); //$NON-NLS-1$

        throw new AssertionError( "not supported" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.client.ui.console.AbstractDisplayTestCase#writeDisplayInput(org.gamegineer.client.ui.console.IDisplay, java.lang.String)
     */
    @Override
    protected void writeDisplayInput(
        final IDisplay display,
        final String s )
    {
        assertArgumentNotNull( display, "display" ); //$NON-NLS-1$
        assertArgumentNotNull( s, "s" ); //$NON-NLS-1$

        throw new AssertionError( "not supported" ); //$NON-NLS-1$
    }
}
