/*
 * DebugUtils.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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

package org.gamegineer.table.internal.ui.impl.util;

import static org.gamegineer.common.core.runtime.NullAnalysis.nonNull;
import java.util.List;
import net.jcip.annotations.NotThreadSafe;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.ComponentPath;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.internal.ui.impl.Debug;
import org.gamegineer.table.internal.ui.impl.model.ComponentModel;
import org.gamegineer.table.internal.ui.impl.model.ContainerModel;
import org.gamegineer.table.internal.ui.impl.model.TableModel;

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
     */
    public static void trace(
        final ITable table )
    {
        final TableTracer tableTracer = new TableTracer();
        tableTracer.trace( table );
    }

    /**
     * Traces the structure of the specified table model.
     * 
     * @param tableModel
     *        The table model; must not be {@code null}.
     */
    public static void trace(
        final TableModel tableModel )
    {
        final TableModelTracer tableModelTracer = new TableModelTracer();
        tableModelTracer.trace( tableModel );

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
        private String format(
            final IComponent component )
        {
            stringBuilder_.setLength( 0 );

            for( int index = 0; index < indentLevel_; ++index )
            {
                stringBuilder_.append( INDENT );
            }

            final ComponentPath componentPath = component.getPath();
            assert componentPath != null;
            final List<ComponentPath> componentPaths = componentPath.toList();
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

            return nonNull( stringBuilder_.toString() );
        }

        /**
         * Traces the structure of the specified table.
         * 
         * @param table
         *        The table; must not be {@code null}.
         */
        void trace(
            final ITable table )
        {
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
            final IComponent component )
        {
            Debug.getDefault().trace( Debug.OPTION_DEFAULT, format( component ) );

            if( component instanceof IContainer )
            {
                ++indentLevel_;

                for( final IComponent childComponent : ((IContainer)component).getComponents() )
                {
                    assert childComponent != null;
                    trace( childComponent );
                }

                --indentLevel_;
            }
        }
    }

    /**
     * Traces the structure of a table model.
     */
    @NotThreadSafe
    private static final class TableModelTracer
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
         * Initializes a new instance of the {@code TableModelTracer} class.
         */
        TableModelTracer()
        {
            indentLevel_ = 0;
            stringBuilder_ = new StringBuilder();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Formats the specified component model.
         * 
         * @param componentModel
         *        The component model; must not be {@code null}.
         * 
         * @return The formatted component model; never {@code null}.
         */
        private String format(
            final ComponentModel componentModel )
        {
            stringBuilder_.setLength( 0 );

            for( int index = 0; index < indentLevel_; ++index )
            {
                stringBuilder_.append( INDENT );
            }

            final ComponentPath componentPath = componentModel.getPath();
            assert componentPath != null;
            final List<ComponentPath> componentPaths = componentPath.toList();
            for( int index = 0, size = componentPaths.size(); index < size; ++index )
            {
                stringBuilder_.append( componentPaths.get( index ).getIndex() );
                if( index < (size - 1) )
                {
                    stringBuilder_.append( '.' );
                }
            }

            stringBuilder_.append( " : " ); //$NON-NLS-1$
            stringBuilder_.append( componentModel.getComponent().getSurfaceDesign( componentModel.getComponent().getOrientation() ).getId() );

            return nonNull( stringBuilder_.toString() );
        }

        /**
         * Traces the structure of the specified table model.
         * 
         * @param tableModel
         *        The table model; must not be {@code null}.
         */
        void trace(
            final TableModel tableModel )
        {
            tableModel.getTableEnvironmentModel().getLock().lock();
            try
            {
                trace( tableModel.getTabletopModel() );
            }
            finally
            {
                tableModel.getTableEnvironmentModel().getLock().unlock();
            }
        }

        /**
         * Traces the structure of the specified component model.
         * 
         * @param componentModel
         *        The component model; must not be {@code null}.
         */
        private void trace(
            final ComponentModel componentModel )
        {
            Debug.getDefault().trace( Debug.OPTION_DEFAULT, format( componentModel ) );

            if( componentModel instanceof ContainerModel )
            {
                ++indentLevel_;

                for( final ComponentModel childComponentModel : ((ContainerModel)componentModel).getComponentModels() )
                {
                    assert childComponentModel != null;
                    trace( childComponentModel );
                }

                --indentLevel_;
            }
        }
    }
}
