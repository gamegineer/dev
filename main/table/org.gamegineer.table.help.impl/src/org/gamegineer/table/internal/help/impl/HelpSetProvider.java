/*
 * HelpSetProvider.java
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
 * Created on Jan 13, 2012 at 8:46:02 PM.
 */

package org.gamegineer.table.internal.help.impl;

import java.net.URL;
import javax.help.HelpSet;
import javax.help.HelpSetException;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.ui.help.IHelpSetProvider;

/**
 * Provides the table help set.
 */
@ThreadSafe
public final class HelpSetProvider
    implements IHelpSetProvider
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The help set. */
    @GuardedBy( "lock_" )
    private HelpSet helpSet_;

    /** The instance lock. */
    private final Object lock_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code HelpSetProvider} class.
     */
    public HelpSetProvider()
    {
        helpSet_ = null;
        lock_ = new Object();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.ui.help.IHelpSetProvider#getHelpSet()
     */
    @Override
    public HelpSet getHelpSet()
        throws HelpSetException
    {
        synchronized( lock_ )
        {
            if( helpSet_ == null )
            {
                final URL helpSetUrl = Activator.getDefault().getBundleContext().getBundle().getEntry( "/help/table.hs" ); //$NON-NLS-1$
                if( helpSetUrl == null )
                {
                    throw new HelpSetException( NonNlsMessages.HelpSetProvider_getHelpSet_helpSetNotAvailable );
                }

                helpSet_ = new HelpSet( null, helpSetUrl );
            }

            return helpSet_;
        }
    }
}
