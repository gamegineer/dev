/*
 * NullDebugOptions.java
 * Copyright 2008-2010 Gamegineer.org
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
import java.io.File;
import net.jcip.annotations.Immutable;
import org.eclipse.osgi.service.debug.DebugOptions;
import org.eclipse.osgi.service.debug.DebugTrace;

/**
 * A null object implementation of the
 * {@link org.eclipse.osgi.service.debug.DebugOptions} interface.
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
    @Override
    public boolean getBooleanOption(
        final String option,
        final boolean defaultValue )
    {
        assertArgumentNotNull( option, "option" ); //$NON-NLS-1$

        return defaultValue;
    }

    /*
     * @see org.eclipse.osgi.service.debug.DebugOptions#getFile()
     */
    @Override
    public File getFile()
    {
        return null;
    }

    /*
     * @see org.eclipse.osgi.service.debug.DebugOptions#getIntegerOption(java.lang.String, int)
     */
    @Override
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
    @Override
    public String getOption(
        final String option )
    {
        assertArgumentNotNull( option, "option" ); //$NON-NLS-1$

        return null;
    }

    /*
     * @see org.eclipse.osgi.service.debug.DebugOptions#getOption(java.lang.String, java.lang.String)
     */
    @Override
    public String getOption(
        final String option,
        final String defaultValue )
    {
        assertArgumentNotNull( option, "option" ); //$NON-NLS-1$
        assertArgumentNotNull( defaultValue, "defaultValue" ); //$NON-NLS-1$

        return defaultValue;
    }

    /*
     * @see org.eclipse.osgi.service.debug.DebugOptions#isDebugEnabled()
     */
    @Override
    public boolean isDebugEnabled()
    {
        return false;
    }

    /*
     * @see org.eclipse.osgi.service.debug.DebugOptions#newDebugTrace(java.lang.String)
     */
    @Override
    public DebugTrace newDebugTrace(
        final String bundleSymbolicName )
    {
        assertArgumentNotNull( bundleSymbolicName, "bundleSymbolicName" ); //$NON-NLS-1$

        throw new UnsupportedOperationException();
    }

    /*
     * @see org.eclipse.osgi.service.debug.DebugOptions#newDebugTrace(java.lang.String, java.lang.Class)
     */
    @Override
    @SuppressWarnings( "unchecked" )
    public DebugTrace newDebugTrace(
        final String bundleSymbolicName,
        final Class traceEntryClass )
    {
        assertArgumentNotNull( bundleSymbolicName, "bundleSymbolicName" ); //$NON-NLS-1$
        assertArgumentNotNull( traceEntryClass, "traceEntryClass" ); //$NON-NLS-1$

        throw new UnsupportedOperationException();
    }

    /*
     * @see org.eclipse.osgi.service.debug.DebugOptions#removeOption(java.lang.String)
     */
    @Override
    public void removeOption(
        final String option )
    {
        assertArgumentNotNull( option, "option" ); //$NON-NLS-1$

        throw new UnsupportedOperationException();
    }

    /*
     * @see org.eclipse.osgi.service.debug.DebugOptions#setDebugEnabled(boolean)
     */
    @Override
    public void setDebugEnabled(
        @SuppressWarnings( "unused" )
        final boolean value )
    {
        throw new UnsupportedOperationException();
    }

    /*
     * @see org.eclipse.osgi.service.debug.DebugOptions#setFile(java.io.File)
     */
    @Override
    public void setFile(
        @SuppressWarnings( "unused" )
        final File newFile )
    {
        throw new UnsupportedOperationException();
    }

    /*
     * @see org.eclipse.osgi.service.debug.DebugOptions#setOption(java.lang.String, java.lang.String)
     */
    @Override
    public void setOption(
        final String option,
        @SuppressWarnings( "unused" )
        final String value )
    {
        assertArgumentNotNull( option, "option" ); //$NON-NLS-1$

        throw new UnsupportedOperationException();
    }
}
