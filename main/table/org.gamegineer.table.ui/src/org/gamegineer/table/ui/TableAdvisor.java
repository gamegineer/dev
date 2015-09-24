/*
 * TableAdvisor.java
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
 * Created on Sep 18, 2009 at 9:28:24 PM.
 */

package org.gamegineer.table.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.jcip.annotations.Immutable;

/**
 * Implementation of {@link ITableAdvisor}.
 */
@Immutable
public final class TableAdvisor
    implements ITableAdvisor
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The application argument collection. */
    private final List<String> appArgs_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableAdvisor} class.
     * 
     * @param appArgs
     *        The application argument collection.
     */
    public TableAdvisor(
        final List<String> appArgs )
    {
        appArgs_ = Collections.unmodifiableList( new ArrayList<>( appArgs ) );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.ui.ITableAdvisor#getApplicationArguments()
     */
    @Override
    public List<String> getApplicationArguments()
    {
        return appArgs_;
    }
}
