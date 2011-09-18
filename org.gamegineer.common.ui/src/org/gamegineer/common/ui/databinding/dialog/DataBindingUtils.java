/*
 * DataBindingUtils.java
 * Copyright 2008-2011 Gamegineer.org
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
 * Created on Oct 16, 2010 at 10:47:04 PM.
 */

package org.gamegineer.common.ui.databinding.dialog;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.core.runtime.IStatus;
import org.gamegineer.common.ui.dialog.DialogMessageType;

/**
 * A collection of useful methods for supporting data binding in dialogs.
 */
@ThreadSafe
final class DataBindingUtils
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code DataBindingUtils} class.
     */
    private DataBindingUtils()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the appropriate dialog message type for the severity of the
     * specified validation status.
     * 
     * @param status
     *        The validation status; must not be {@code null}.
     * 
     * @return The appropriate dialog message type for the severity of the
     *         specified validation status; never {@code null}.
     */
    /* @NonNull */
    static DialogMessageType getDialogMessageType(
        /* @NonNull */
        final IStatus status )
    {
        assert status != null;

        switch( status.getSeverity() )
        {
            case IStatus.OK:
            case IStatus.CANCEL:
                return DialogMessageType.NORMAL;

            case IStatus.INFO:
                return DialogMessageType.INFORMATION;

            case IStatus.WARNING:
                return DialogMessageType.WARNING;

            case IStatus.ERROR:
                return DialogMessageType.ERROR;

            default:
                throw new AssertionError( "unknown IStatus severity" ); //$NON-NLS-1$
        }
    }

    /**
     * Indicates the specified validation status is fatal.
     * 
     * @param status
     *        The validation status to test or {@code null} if the status is not
     *        available.
     * @param isStatusStale
     *        {@code true} if the validation status is stale; otherwise {@code
     *        false}.
     * 
     * @return {@code true} if the specified validation status is fatal;
     *         otherwise {@code false}.
     */
    static boolean isValidationStatusFatal(
        /* @Nullable */
        final IStatus status,
        final boolean isStatusStale )
    {
        if( isStatusStale )
        {
            return true;
        }

        return (status != null) ? status.matches( IStatus.CANCEL | IStatus.ERROR ) : false;
    }
}
