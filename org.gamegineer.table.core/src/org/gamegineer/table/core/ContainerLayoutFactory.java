/*
 * ContainerLayoutFactory.java
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
 * Created on Aug 7, 2012 at 8:13:57 PM.
 */

package org.gamegineer.table.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.lang.reflect.Field;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.core.AbsoluteLayout;
import org.gamegineer.table.internal.core.AccordianLayout;
import org.gamegineer.table.internal.core.StackedLayout;

/**
 * A factory for creating container layouts.
 */
@ThreadSafe
public final class ContainerLayoutFactory
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * A layout in which the container is laid out with all components at their
     * absolute position in table coordinates.
     */
    private static final IContainerLayout ABSOLUTE = new AbsoluteLayout( ContainerLayoutId.fromString( "org.gamegineer.containerLayouts.absolute" ) ); //$NON-NLS-1$

    /**
     * A layout in which the container is laid out as an accordian. Beginning
     * with the component at the bottom of the container, each successive
     * component is offset immediately below it.
     */
    private static final IContainerLayout ACCORDIAN_DOWN = new AccordianLayout( ContainerLayoutId.fromString( "org.gamegineer.containerLayouts.accordianDown" ), 0, 18 ); //$NON-NLS-1$

    /**
     * A layout in which the container is laid out as an accordian. Beginning
     * with the component at the bottom of the container, each successive
     * component is offset immediately to the left of it.
     */
    private static final IContainerLayout ACCORDIAN_LEFT = new AccordianLayout( ContainerLayoutId.fromString( "org.gamegineer.containerLayouts.accordianLeft" ), -16, 0 ); //$NON-NLS-1$

    /**
     * A layout in which the container is laid out as an accordian. Beginning
     * with the component at the bottom of the container, each successive
     * component is offset immediately to the right of it.
     */
    private static final IContainerLayout ACCORDIAN_RIGHT = new AccordianLayout( ContainerLayoutId.fromString( "org.gamegineer.containerLayouts.accordianRight" ), 16, 0 ); //$NON-NLS-1$

    /**
     * A layout in which the container is laid out as an accordian. Beginning
     * with the component at the bottom of the container, each successive
     * component is offset immediately above it.
     */
    private static final IContainerLayout ACCORDIAN_UP = new AccordianLayout( ContainerLayoutId.fromString( "org.gamegineer.containerLayouts.accordianUp" ), 0, -18 ); //$NON-NLS-1$

    /**
     * A layout in which the container is laid out with one component placed on
     * top of the other with no offset.
     */
    private static final IContainerLayout STACKED = new StackedLayout( ContainerLayoutId.fromString( "org.gamegineer.containerLayouts.stacked" ), 10, 2, 1 ); //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ContainerLayoutFactory} class.
     */
    private ContainerLayoutFactory()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a layout in which the container is laid out with all components
     * at their absolute position in table coordinates.
     * 
     * @return An absolute layout; never {@code null}.
     */
    /* @NonNull */
    public static IContainerLayout createAbsoluteLayout()
    {
        return ABSOLUTE;
    }

    /**
     * Creates a layout in which the container is laid out as an accordian.
     * Beginning with the component at the bottom of the container, each
     * successive component is offset immediately below it.
     * 
     * @return An accordian down layout; never {@code null}.
     */
    /* @NonNull */
    public static IContainerLayout createAccordianDownLayout()
    {
        return ACCORDIAN_DOWN;
    }

    /**
     * Creates a layout in which the container is laid out as an accordian.
     * Beginning with the component at the bottom of the container, each
     * successive component is offset immediately to the left of it.
     * 
     * @return An accordian left layout; never {@code null}.
     */
    /* @NonNull */
    public static IContainerLayout createAccordianLeftLayout()
    {
        return ACCORDIAN_LEFT;
    }

    /**
     * Creates a layout in which the container is laid out as an accordian.
     * Beginning with the component at the bottom of the container, each
     * successive component is offset immediately to the right of it.
     * 
     * @return An accordian right layout; never {@code null}.
     */
    /* @NonNull */
    public static IContainerLayout createAccordianRightLayout()
    {
        return ACCORDIAN_RIGHT;
    }

    /**
     * Creates a layout in which the container is laid out as an accordian.
     * Beginning with the component at the bottom of the container, each
     * successive component is offset immediately above it.
     * 
     * @return An accordian up layout; never {@code null}.
     */
    /* @NonNull */
    public static IContainerLayout createAccordianUpLayout()
    {
        return ACCORDIAN_UP;
    }

    /**
     * Creates a layout in which the container is laid out with one component
     * placed on top of the other with no offset.
     * 
     * @return A stacked layout; never {@code null}.
     */
    /* @NonNull */
    public static IContainerLayout createStackedLayout()
    {
        return STACKED;
    }

    /**
     * Gets the container layout associated with the specified identifier.
     * 
     * @param id
     *        The container layout identifier; must not be {@code null}.
     * 
     * @return The container layout associated with the specified identifier;
     *         never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code id} is not associated with a container layout created
     *         by this factory.
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    /* @NonNull */
    public static IContainerLayout fromId(
        /* @NonNull */
        final String id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        try
        {
            return (IContainerLayout)ContainerLayoutFactory.class.getDeclaredField( id ).get( null );
        }
        catch( final NoSuchFieldException e )
        {
            throw new IllegalArgumentException( "id", e ); //$NON-NLS-1$
        }
        catch( final IllegalAccessException e )
        {
            throw new IllegalArgumentException( "id", e ); //$NON-NLS-1$
        }
    }

    /**
     * Gets the identifier of the specified container layout.
     * 
     * @param layout
     *        The container layout; must not be {@code null}.
     * 
     * @return The identifier of the specified container layout; never
     *         {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code layout} is not a container layout created by this
     *         factory.
     * @throws java.lang.NullPointerException
     *         If {@code layout} is {@code null}.
     */
    /* @NonNull */
    public static String getId(
        /* @NonNull */
        final IContainerLayout layout )
    {
        assertArgumentNotNull( layout, "layout" ); //$NON-NLS-1$

        try
        {
            for( final Field field : ContainerLayoutFactory.class.getDeclaredFields() )
            {
                if( layout == field.get( null ) )
                {
                    return field.getName();
                }
            }
        }
        catch( final IllegalAccessException e )
        {
            throw new AssertionError( e );
        }

        throw new IllegalArgumentException( "layout" ); //$NON-NLS-1$
    }
}
