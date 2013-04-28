/*
 * IComponentModelParent.java
 * Copyright 2008-2013 Gamegineer.org
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
 * Created on Nov 3, 2012 at 10:19:00 AM.
 */

package org.gamegineer.table.internal.ui.model;

import org.eclipse.core.expressions.EvaluationContext;

/**
 * A component model parent.
 */
interface IComponentModelParent
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets an evaluation context.
     * 
     * <p>
     * The evaluation context should reflect the current state of the component
     * model and all of its ancestors.
     * </p>
     * 
     * @return An evaluation context; never {@code null}.
     */
    /* @NonNull */
    EvaluationContext getEvaluationContext();

    /**
     * Gets the parent of this component model parent.
     * 
     * @return The parent of this component model parent or {@code null} if this
     *         component model parent has no parent.
     */
    /* @Nullable */
    IComponentModelParent getParent();

    /**
     * Gets the table model associated with this component model parent.
     * 
     * @return The table model associated with the component model parent or
     *         {@code null} if this component model parent is not associated
     *         with a table model.
     */
    /* @Nullable */
    TableModel getTableModel();
}
