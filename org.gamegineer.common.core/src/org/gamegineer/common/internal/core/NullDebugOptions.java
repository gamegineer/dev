/*
 * NullDebugOptions.java
 * Copyright 2008 Gamegineer.org
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
 * Created on Sep 16, 2008 at 10:41:47 PM.
 */

package org.gamegineer.common.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;
import org.eclipse.osgi.service.debug.DebugOptions;

/**
 * A null object implementation of the
 * {@link org.eclipse.osgi.service.debug.DebugOptions} interface.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
final class NullDebugOptions
    implements DebugOptions
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NullDebugOptions} class.
     */
    NullDebugOptions()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.eclipse.osgi.service.debug.DebugOptions#getBooleanOption(java.lang.String, boolean)
     */
    public boolean getBooleanOption(
        final String option,
        final boolean defaultValue )
    {
        assertArgumentNotNull( option, "option" ); //$NON-NLS-1$

        return defaultValue;
    }

    /*
     * @see org.eclipse.osgi.service.debug.DebugOptions#getIntegerOption(java.lang.String, int)
     */
    public int getIntegerOption(
        final String option,
        final int defaultValue )
    {
        assertArgumentNotNull( option, "option" ); //$NON-NLS-1$

        return defaultValue;
    }

    /*
     * @see org.eclipse.osgi.service.debug.DebugOptions#getOption(java.lang.String)
     */
    public String getOption(
        final String option )
    {
        assertArgumentNotNull( option, "option" ); //$NON-NLS-1$

        return null;
    }

    /*
     * @see org.eclipse.osgi.service.debug.DebugOptions#getOption(java.lang.String, java.lang.String)
     */
    public String getOption(
        final String option,
        final String defaultValue )
    {
        assertArgumentNotNull( option, "option" ); //$NON-NLS-1$
        assertArgumentNotNull( defaultValue, "defaultValue" ); //$NON-NLS-1$

        return defaultValue;
    }

    /*
     * @see org.eclipse.osgi.service.debug.DebugOptions#setOption(java.lang.String, java.lang.String)
     */
    public void setOption(
        @SuppressWarnings( "unused" )
        final String option,
        @SuppressWarnings( "unused" )
        final String value )
    {
        throw new UnsupportedOperationException();
    }
}
