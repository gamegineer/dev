/*
 * Tabletop.java
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
 * Created on Jun 28, 2012 at 8:07:30 PM.
 */

package org.gamegineer.table.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.memento.MementoException;
import org.gamegineer.table.core.ComponentOrientation;
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainerLayout;
import org.gamegineer.table.core.TabletopLayouts;
import org.gamegineer.table.core.TabletopOrientation;

/**
 * A tabletop.
 */
@ThreadSafe
final class Tabletop
    extends Container
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The name of the memento attribute that stores the collection of
     * components on the tabletop.
     */
    private static final String COMPONENTS_MEMENTO_ATTRIBUTE_NAME = "components"; //$NON-NLS-1$

    /** The name of the memento attribute that stores the tabletop layout. */
    private static final String LAYOUT_MEMENTO_ATTRIBUTE_NAME = "layout"; //$NON-NLS-1$

    /** The name of the memento attribute that stores the tabletop location. */
    private static final String LOCATION_MEMENTO_ATTRIBUTE_NAME = "location"; //$NON-NLS-1$

    /** The collection of supported component orientations. */
    private static final Collection<ComponentOrientation> SUPPORTED_ORIENTATIONS = Collections.unmodifiableCollection( Arrays.<ComponentOrientation>asList( TabletopOrientation.values( TabletopOrientation.class ) ) );

    /**
     * The name of the memento attribute that stores the component surface
     * design.
     */
    private static final String SURFACE_DESIGN_MEMENTO_ATTRIBUTE_NAME = "surfaceDesign"; //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Tabletop} class.
     * 
     * @param tableEnvironment
     *        The table environment associated with the tabletop; must not be
     *        {@code null}.
     */
    Tabletop(
        /* @NonNull */
        final TableEnvironment tableEnvironment )
    {
        super( tableEnvironment );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.util.memento.IMementoOriginator#createMemento()
     */
    @Override
    public Object createMemento()
    {
        final Map<String, Object> memento = new HashMap<String, Object>();

        getLock().lock();
        try
        {
            memento.put( LAYOUT_MEMENTO_ATTRIBUTE_NAME, getLayout() );
            memento.put( LOCATION_MEMENTO_ATTRIBUTE_NAME, getLocation() );
            memento.put( SURFACE_DESIGN_MEMENTO_ATTRIBUTE_NAME, getSurfaceDesign( TabletopOrientation.DEFAULT ) );

            final List<Object> componentMementos = new ArrayList<Object>();
            for( final IComponent component : getComponents() )
            {
                componentMementos.add( component.createMemento() );
            }
            memento.put( COMPONENTS_MEMENTO_ATTRIBUTE_NAME, Collections.unmodifiableList( componentMementos ) );
        }
        finally
        {
            getLock().unlock();
        }

        return Collections.unmodifiableMap( memento );
    }

    /**
     * Creates a new instance of the {@code Tabletop} class from the specified
     * memento.
     * 
     * @param tableEnvironment
     *        The table environment associated with the new tabletop; must not
     *        be {@code null}.
     * @param memento
     *        The memento representing the initial tabletop state; must not be
     *        {@code null}.
     * 
     * @return A new instance of the {@code Tabletop} class; never {@code null}.
     * 
     * @throws org.gamegineer.common.core.util.memento.MementoException
     *         If {@code memento} is malformed.
     */
    /* @NonNull */
    static Tabletop fromMemento(
        /* @NonNull */
        final TableEnvironment tableEnvironment,
        /* @NonNull */
        final Object memento )
        throws MementoException
    {
        assert tableEnvironment != null;
        assert memento != null;

        final Tabletop tabletop = new Tabletop( tableEnvironment );

        final IContainerLayout layout = MementoUtils.getAttribute( memento, LAYOUT_MEMENTO_ATTRIBUTE_NAME, IContainerLayout.class );
        tabletop.setLayout( layout );

        final Point location = MementoUtils.getAttribute( memento, LOCATION_MEMENTO_ATTRIBUTE_NAME, Point.class );
        tabletop.setLocation( location );

        final ComponentSurfaceDesign surfaceDesign = MementoUtils.getAttribute( memento, SURFACE_DESIGN_MEMENTO_ATTRIBUTE_NAME, ComponentSurfaceDesign.class );
        tabletop.setSurfaceDesign( TabletopOrientation.DEFAULT, surfaceDesign );

        @SuppressWarnings( "unchecked" )
        final List<Object> componentMementos = MementoUtils.getAttribute( memento, COMPONENTS_MEMENTO_ATTRIBUTE_NAME, List.class );
        for( final Object componentMemento : componentMementos )
        {
            tabletop.addComponent( Component.fromMemento( tableEnvironment, componentMemento ) );
        }

        return tabletop;
    }

    /*
     * @see org.gamegineer.table.internal.core.Container#getDefaultLayout()
     */
    @Override
    IContainerLayout getDefaultLayout()
    {
        return TabletopLayouts.ABSOLUTE;
    }

    /*
     * @see org.gamegineer.table.internal.core.Component#getDefaultLocation()
     */
    @Override
    Point getDefaultLocation()
    {
        return new Point( Short.MIN_VALUE / 2, Short.MIN_VALUE / 2 );
    }

    /*
     * @see org.gamegineer.table.internal.core.Component#getDefaultOrientation()
     */
    @Override
    ComponentOrientation getDefaultOrientation()
    {
        return TabletopOrientation.DEFAULT;
    }

    /*
     * @see org.gamegineer.table.internal.core.Component#getDefaultOrigin()
     */
    @Override
    Point getDefaultOrigin()
    {
        return getDefaultLocation();
    }

    /*
     * @see org.gamegineer.table.internal.core.Component#getDefaultSurfaceDesigns()
     */
    @Override
    Map<ComponentOrientation, ComponentSurfaceDesign> getDefaultSurfaceDesigns()
    {
        return Collections.<ComponentOrientation, ComponentSurfaceDesign>singletonMap( //
            TabletopOrientation.DEFAULT, //
            new ComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "org.gamegineer.table.internal.core.Tabletop.DEFAULT_SURFACE_DESIGN" ), Short.MAX_VALUE, Short.MAX_VALUE ) ); //$NON-NLS-1$ );
    }

    /*
     * @see org.gamegineer.table.core.IComponent#getSupportedOrientations()
     */
    @Override
    public Collection<ComponentOrientation> getSupportedOrientations()
    {
        return SUPPORTED_ORIENTATIONS;
    }

    /*
     * @see org.gamegineer.common.core.util.memento.IMementoOriginator#setMemento(java.lang.Object)
     */
    @Override
    public void setMemento(
        final Object memento )
        throws MementoException
    {
        assertArgumentNotNull( memento, "memento" ); //$NON-NLS-1$

        getLock().lock();
        try
        {
            final Tabletop tabletop = fromMemento( getTableEnvironment(), memento );

            setLayout( tabletop.getLayout() );
            setLocation( tabletop.getLocation() );
            setSurfaceDesign( TabletopOrientation.DEFAULT, tabletop.getSurfaceDesign( TabletopOrientation.DEFAULT ) );

            removeComponents();
            addComponents( tabletop.removeComponents() );
        }
        finally
        {
            getLock().unlock();
        }
    }
}
