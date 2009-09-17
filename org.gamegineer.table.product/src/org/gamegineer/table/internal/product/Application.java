/*
 * Application.java
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
 * Created on Sep 16, 2009 at 11:38:32 PM.
 */

package org.gamegineer.table.internal.product;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

/**
 * The entry point of the Gamegineer application.
 */
public final class Application
    implements IApplication
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Application} class.
     */
    public Application()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
     */
    public Object start(
        final IApplicationContext context )
        throws Exception
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        System.out.println( "[gamegineer]: start" ); //$NON-NLS-1$

        return EXIT_OK;
    }

    /*
     * @see org.eclipse.equinox.app.IApplication#stop()
     */
    public void stop()
    {
        System.out.println( "[gamegineer]: stop" ); //$NON-NLS-1$
    }
}
