/*
 * FakeConsole.java
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
 * Created on Oct 20, 2008 at 10:39:43 PM.
 */

package org.gamegineer.client.ui.console;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;

/**
 * Fake implementation of {@link org.gamegineer.client.ui.console.IConsole}.
 * 
 * <p>
 * This class is immutable.
 * </p>
 * 
 * <p>
 * This class is intended to be extended by clients.
 * </p>
 */
@Immutable
public class FakeConsole
    implements IConsole
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The display associated with the console. */
    private final IDisplay m_display;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FakeConsole} class.
     * 
     * @param display
     *        The display associated with the console; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code display} is {@code null}.
     */
    public FakeConsole(
        /* @NonNull */
        final IDisplay display )
    {
        assertArgumentNotNull( display, "display" ); //$NON-NLS-1$

        m_display = display;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.client.ui.console.IConsole#close()
     */
    public void close()
    {
        // Do nothing
    }

    /*
     * @see org.gamegineer.client.ui.console.IConsole#getDisplay()
     */
    public IDisplay getDisplay()
    {
        return m_display;
    }
}
