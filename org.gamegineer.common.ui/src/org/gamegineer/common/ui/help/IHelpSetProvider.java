/*
 * IHelpSetProvider.java
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
 * Created on Jan 4, 2012 at 8:17:21 PM.
 */

package org.gamegineer.common.ui.help;

import javax.help.HelpSet;
import javax.help.HelpSetException;

/**
 * Provides a help set to the platform help system.
 * 
 * <p>
 * Clients provide an implementation of this interface in order to provide a
 * help set for inclusion in the platform help system.
 * </p>
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IHelpSetProvider
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the help set offered by this provider.
     * 
     * @return The help set offered by this provider; never {@code null}.
     * 
     * @throws javax.help.HelpSetException
     *         If the help set is not available.
     */
    /* @NonNull */
    public HelpSet getHelpSet()
        throws HelpSetException;
}
