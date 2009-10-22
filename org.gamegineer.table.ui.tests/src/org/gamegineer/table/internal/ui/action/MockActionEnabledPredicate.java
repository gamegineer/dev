/*
 * MockActionEnabledPredicate.java
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
 * Created on Oct 21, 2009 at 11:04:44 PM.
 */

package org.gamegineer.table.internal.ui.action;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.Action;
import net.jcip.annotations.ThreadSafe;

/**
 * Mock implementation of
 * {@link org.gamegineer.table.internal.ui.action.IActionEnabledPredicate}.
 */
@ThreadSafe
final class MockActionEnabledPredicate
    implements IActionEnabledPredicate
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The count of calls made to the {@code isActionEnabled} method. */
    private final AtomicInteger isActionEnabledCallCount_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MockActionEnabledPredicate}
     * class.
     */
    MockActionEnabledPredicate()
    {
        isActionEnabledCallCount_ = new AtomicInteger( 0 );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the count of calls made to the {@code isActionEnabled} method.
     * 
     * @return The count of calls made to the {@code isActionEnabled} method.
     */
    int getIsActionEnabledCallCount()
    {
        return isActionEnabledCallCount_.get();
    }

    /*
     * @see org.gamegineer.table.internal.ui.action.IActionEnabledPredicate#isActionEnabled(javax.swing.Action)
     */
    public boolean isActionEnabled(
        final Action action )
    {
        assertArgumentNotNull( action, "action" ); //$NON-NLS-1$

        isActionEnabledCallCount_.incrementAndGet();

        return false;
    }
}
