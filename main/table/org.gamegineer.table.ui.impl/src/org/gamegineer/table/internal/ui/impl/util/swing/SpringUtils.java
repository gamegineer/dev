/*
 * SpringUtils.java
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
 * Created on Oct 8, 2010 at 11:03:02 PM.
 */

package org.gamegineer.table.internal.ui.impl.util.swing;

import static org.gamegineer.common.core.runtime.NullAnalysis.nonNull;
import java.awt.Component;
import java.awt.Container;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import net.jcip.annotations.ThreadSafe;

/**
 * A collection of useful methods for working with {@code SpringLayout}.
 * 
 * <p>
 * This code is based on the {@code SpringUtilities} class provided as part of
 * <i>The JFC Swing Tutorial, Second Edition</i>.
 * </p>
 */
@ThreadSafe
public final class SpringUtils
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code SpringUtils} class.
     */
    private SpringUtils()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Builds a compact grid layout for the components of the specified
     * container.
     * 
     * <p>
     * Aligns the first {@code rows} * {@code columns} components of
     * {@code parent} in a grid. Each component in a column is as wide as the
     * maximum preferred width of the components in that column. The height is
     * similarly determined for each row. The parent is made just big enough to
     * fit them all.
     * </p>
     * 
     * @param parent
     *        The parent container; must not be {@code null}.
     * @param rows
     *        The number of rows in the grid.
     * @param columns
     *        The number of columns in the grid.
     * @param xStart
     *        The x location of the first grid cell.
     * @param yStart
     *        The y location of the first grid cell.
     * @param horizontalSpacing
     *        The horizontal spacing between grid cells.
     * @param verticalSpacing
     *        The vertical spacing between grid cells.
     */
    public static void buildCompactGrid(
        final Container parent,
        final int rows,
        final int columns,
        final int xStart,
        final int yStart,
        final int horizontalSpacing,
        final int verticalSpacing )
    {
        final SpringLayout layout = (SpringLayout)parent.getLayout();

        // Align all cells in each column and make them the same width
        Spring x = Spring.constant( xStart );
        for( int column = 0; column < columns; ++column )
        {
            Spring width = Spring.constant( 0 );
            for( int row = 0; row < rows; ++row )
            {
                width = Spring.max( width, getGridCellConstraints( parent, row, column, columns ).getWidth() );
            }

            for( int row = 0; row < rows; ++row )
            {
                final SpringLayout.Constraints constraints = getGridCellConstraints( parent, row, column, columns );
                constraints.setX( x );
                constraints.setWidth( width );
            }

            x = Spring.sum( x, Spring.sum( width, Spring.constant( (column < (columns - 1)) ? horizontalSpacing : 0 ) ) );
        }

        // Align all cells in each row and make them the same height
        Spring y = Spring.constant( yStart );
        for( int row = 0; row < rows; ++row )
        {
            Spring height = Spring.constant( 0 );
            for( int column = 0; column < columns; ++column )
            {
                height = Spring.max( height, getGridCellConstraints( parent, row, column, columns ).getHeight() );
            }

            for( int column = 0; column < columns; ++column )
            {
                final SpringLayout.Constraints constraints = getGridCellConstraints( parent, row, column, columns );
                constraints.setY( y );
                constraints.setHeight( height );
            }

            y = Spring.sum( y, Spring.sum( height, Spring.constant( (row < (rows - 1)) ? verticalSpacing : 0 ) ) );
        }

        // Set the parent's size
        final SpringLayout.Constraints parentConstraints = layout.getConstraints( parent );
        parentConstraints.setConstraint( SpringLayout.SOUTH, y );
        parentConstraints.setConstraint( SpringLayout.EAST, x );
    }

    /**
     * Builds a grid layout for the components of the specified container.
     * 
     * <p>
     * Aligns the first {@code rows} * {@code columns} components of
     * {@code parent} in a grid. Each component is as big as the maximum
     * preferred width and height of the components. The parent is made just big
     * enough to fit them all.
     * </p>
     * 
     * @param parent
     *        The parent container; must not be {@code null}.
     * @param rows
     *        The number of rows in the grid.
     * @param columns
     *        The number of columns in the grid.
     * @param xStart
     *        The x location of the first grid cell.
     * @param yStart
     *        The y location of the first grid cell.
     * @param horizontalSpacing
     *        The horizontal spacing between grid cells.
     * @param verticalSpacing
     *        The vertical spacing between grid cells.
     */
    public static void buildGrid(
        final Container parent,
        final int rows,
        final int columns,
        final int xStart,
        final int yStart,
        final int horizontalSpacing,
        final int verticalSpacing )
    {
        final SpringLayout layout = (SpringLayout)parent.getLayout();
        final Spring horizontalSpacingSpring = Spring.constant( horizontalSpacing );
        final Spring verticalSpacingSpring = Spring.constant( verticalSpacing );
        final Spring xStartSpring = Spring.constant( xStart );
        final Spring yStartSpring = Spring.constant( yStart );
        final int cellCount = rows * columns;

        // Calculate Springs that are the max of the width/height so that all
        // cells have the same size.
        Spring maxWidthSpring = layout.getConstraints( parent.getComponent( 0 ) ).getWidth();
        Spring maxHeightSpring = layout.getConstraints( parent.getComponent( 0 ) ).getWidth();
        for( int index = 1; index < cellCount; ++index )
        {
            final SpringLayout.Constraints constraints = layout.getConstraints( parent.getComponent( index ) );
            maxWidthSpring = Spring.max( maxWidthSpring, constraints.getWidth() );
            maxHeightSpring = Spring.max( maxHeightSpring, constraints.getHeight() );
        }

        // Apply the new width/height Spring. This forces all the
        // components to have the same size.
        for( int index = 0; index < cellCount; ++index )
        {
            final SpringLayout.Constraints constraints = layout.getConstraints( parent.getComponent( index ) );
            constraints.setWidth( maxWidthSpring );
            constraints.setHeight( maxHeightSpring );
        }

        // Then adjust the x/y constraints of all the cells so that they
        // are aligned in a grid.
        SpringLayout.Constraints lastConstraints = null;
        SpringLayout.Constraints lastRowConstraints = null;
        for( int index = 0; index < cellCount; ++index )
        {
            final SpringLayout.Constraints constraints = layout.getConstraints( parent.getComponent( index ) );
            if( (index % columns) == 0 )
            {
                // start of new row
                lastRowConstraints = lastConstraints;
                constraints.setX( xStartSpring );
            }
            else
            {
                // x position depends on previous component
                assert lastConstraints != null;
                constraints.setX( Spring.sum( lastConstraints.getConstraint( SpringLayout.EAST ), horizontalSpacingSpring ) );
            }

            if( (index / columns) == 0 )
            {
                // first row
                constraints.setY( yStartSpring );
            }
            else
            {
                // y position depends on previous row
                assert lastRowConstraints != null;
                constraints.setY( Spring.sum( lastRowConstraints.getConstraint( SpringLayout.SOUTH ), verticalSpacingSpring ) );
            }

            lastConstraints = constraints;
        }

        // Set the parent's size
        assert lastConstraints != null;
        final SpringLayout.Constraints parentConstraints = layout.getConstraints( parent );
        parentConstraints.setConstraint( SpringLayout.SOUTH, Spring.sum( Spring.constant( verticalSpacing ), lastConstraints.getConstraint( SpringLayout.SOUTH ) ) );
        parentConstraints.setConstraint( SpringLayout.EAST, Spring.sum( Spring.constant( horizontalSpacing ), lastConstraints.getConstraint( SpringLayout.EAST ) ) );
    }

    /**
     * Gets the layout constraints for the specified grid cell.
     * 
     * @param parent
     *        The parent container; must not be {@code null}.
     * @param row
     *        The grid cell row.
     * @param column
     *        The grid cell column.
     * @param columns
     *        The number of columns in the grid.
     * 
     * @return The layout constraints for the specified grid cell; never
     *         {@code null}.
     */
    private static SpringLayout.Constraints getGridCellConstraints(
        final Container parent,
        final int row,
        final int column,
        final int columns )
    {
        final SpringLayout layout = (SpringLayout)parent.getLayout();
        final Component component = parent.getComponent( row * columns + column );
        return nonNull( layout.getConstraints( component ) );
    }
}
