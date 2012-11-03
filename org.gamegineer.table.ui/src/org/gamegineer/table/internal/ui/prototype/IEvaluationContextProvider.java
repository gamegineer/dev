/*
 * IEvaluationContextProvider.java
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
 * Created on Nov 3, 2012 at 9:42:42 AM.
 */

package org.gamegineer.table.internal.ui.prototype;

import org.eclipse.core.expressions.EvaluationContext;

/**
 * An evaluation context provider.
 */
public interface IEvaluationContextProvider
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets an evaluation context.
     * 
     * <p>
     * The evaluation context should reflect the current state of the provider.
     * </p>
     * 
     * @return An evaluation context; never {@code null}.
     */
    /* @NonNull */
    public EvaluationContext getEvaluationContext();
}
