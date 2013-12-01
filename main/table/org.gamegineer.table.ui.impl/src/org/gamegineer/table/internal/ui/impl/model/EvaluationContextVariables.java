/*
 * EvaluationContextVariables.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
 * Created on Nov 3, 2012 at 11:30:18 AM.
 */

package org.gamegineer.table.internal.ui.impl.model;

import net.jcip.annotations.ThreadSafe;

/**
 * A collection of evaluation context variables used by table user interface
 * models.
 */
@ThreadSafe
final class EvaluationContextVariables
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The prefix for all variable names defined in this class. */
    private static final String PREFIX = "table."; //$NON-NLS-1$

    /** The name of the variable that stores the focused table component. */
    static final String FOCUSED_COMPONENT = PREFIX + "focusedComponent"; //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code EvaluationContextVariables}
     * class.
     */
    private EvaluationContextVariables()
    {
    }
}
