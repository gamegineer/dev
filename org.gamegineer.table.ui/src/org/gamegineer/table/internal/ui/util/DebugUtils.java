/*
 * DebugUtils.java
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
 * Created on Jun 17, 2013 at 7:57:15 PM.
 */

package org.gamegineer.table.internal.ui.util;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.List;
import net.jcip.annotations.NotThreadSafe;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.ComponentPath;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.internal.ui.Debug;

/**
 * A collection of useful methods for debugging.
 */
@ThreadSafe
public final class DebugUtils
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code DebugUtils} class.
     */
    private DebugUtils()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Traces the structure of the specified table.
     * 
     * @param table
     *        The table; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code table} is {@code null}.
     */
    public static void trace(
        /* @NonNull */
        final ITable table )
    {
        assertArgumentNotNull( table, "table" ); //$NON-NLS-1$

        final TableTracer tableTracer = new TableTracer();
        tableTracer.trace( table );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * Traces the structure of a table.
     */
    @NotThreadSafe
    private static final class TableTracer
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The string used to indent a trace. */
        private static final String INDENT = "  "; //$NON-NLS-1$

        /** The current indent level. */
        private int indentLevel_;

        /** The string builder. */
        private final StringBuilder stringBuilder_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code TableTracer} class.
         */
        TableTracer()
        {
            indentLevel_ = 0;
            stringBuilder_ = new StringBuilder();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Formats the specified component.
         * 
         * @param component
         *        The component; must not be {@code null}.
         * 
         * @return The formatted component; never {@code null}.
         */
        /* @NonNull */
        private String format(
            /* @NonNull */
            final IComponent component )
        {
            assert component != null;

            stringBuilder_.setLength( 0 );

            for( int index = 0; index < indentLevel_; ++index )
            {
                stringBuilder_.append( INDENT );
            }

            final List<ComponentPath> componentPaths = component.getPath().toList();
            for( int index = 0, size = componentPaths.size(); index < size; ++index )
            {
                stringBuilder_.append( componentPaths.get( index ).getIndex() );
                if( index < (size - 1) )
                {
                    stringBuilder_.append( '.' );
                }
            }

            stringBuilder_.append( " : " ); //$NON-NLS-1$
            stringBuilder_.append( component.getSurfaceDesign( component.getOrientation() ).getId() );

            return stringBuilder_.toString();
        }

        /**
         * Traces the structure of the specified table.
         * 
         * @param table
         *        The table; must not be {@code null}.
         */
        void trace(
            /* @NonNull */
            final ITable table )
        {
            assert table != null;

            table.getTableEnvironment().getLock().lock();
            try
            {
                trace( table.getTabletop() );
            }
            finally
            {
                table.getTableEnvironment().getLock().unlock();
            }
        }

        /**
         * Traces the structure of the specified component.
         * 
         * @param component
         *        The component; must not be {@code null}.
         */
        private void trace(
            /* @NonNull */
            final IComponent component )
        {
            assert component != null;

            Debug.getDefault().trace( Debug.OPTION_DEFAULT, format( component ) );

            if( component instanceof IContainer )
            {
                ++indentLevel_;

                for( final IComponent childComponent : ((IContainer)component).getComponents() )
                {
                    trace( childComponent );
                }

                --indentLevel_;
            }
        }
    }
}
