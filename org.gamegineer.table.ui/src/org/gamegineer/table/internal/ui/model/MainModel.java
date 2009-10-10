/*
 * MainModel.java
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
 * Created on Oct 6, 2009 at 11:17:00 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.TableFactory;
import org.gamegineer.table.ui.ITableAdvisor;
import org.osgi.framework.Version;

/**
 * The top-level model.
 */
@ThreadSafe
public final class MainModel
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table advisor. */
    private final ITableAdvisor advisor_;

    /** The table. */
    private final ITable table_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MainModel} class.
     * 
     * @param advisor
     *        The table advisor; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code advisor} is {@code null}.
     */
    public MainModel(
        /* @NonNull */
        final ITableAdvisor advisor )
    {
        assertArgumentNotNull( advisor, "advisor" ); //$NON-NLS-1$

        advisor_ = advisor;
        table_ = TableFactory.createTable();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the table.
     * 
     * @return The table; never {@code null}.
     */
    /* @NonNull */
    public ITable getTable()
    {
        return table_;
    }

    /**
     * Gets the model version.
     * 
     * @return The model version.
     */
    /* @NonNull */
    public Version getVersion()
    {
        return advisor_.getApplicationVersion();
    }
}
