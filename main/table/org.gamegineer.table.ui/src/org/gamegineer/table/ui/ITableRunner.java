/*
 * ITableRunner.java
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
 * Created on Oct 3, 2009 at 7:58:21 PM.
 */

package org.gamegineer.table.ui;

import java.util.concurrent.Callable;
import org.eclipse.jdt.annotation.NonNull;

/**
 * An object capable of running a table user interface.
 * 
 * <p>
 * Table runners are only intended to be run once.
 * </p>
 * 
 * <p>
 * A table runner may be cancelled by interrupting the thread on which it is
 * running. Implementations must handle thread interruption gracefully and
 * attempt to shut themselves down and return as quickly as possible.
 * </p>
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ITableRunner
    extends Callable<@NonNull TableResult>
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * @throws java.lang.IllegalStateException
     *         If the runner has already been run.
     * 
     * @see java.util.concurrent.Callable#call()
     */
    @Override
    public TableResult call()
        throws Exception;
}
