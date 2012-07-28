/*
 * Card.java
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
 * Created on Oct 11, 2009 at 9:53:36 PM.
 */

package org.gamegineer.table.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Point;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.memento.MementoException;
import org.gamegineer.table.core.CardOrientation;
import org.gamegineer.table.core.ComponentOrientation;
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.core.ComponentSurfaceDesignId;
import org.gamegineer.table.core.ICard;

/**
 * Implementation of {@link org.gamegineer.table.core.ICard}.
 */
@ThreadSafe
final class Card
    extends Component
    implements ICard
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The name of the memento attribute that stores the design on the back of
     * the card.
     */
    private static final String BACK_DESIGN_MEMENTO_ATTRIBUTE_NAME = "backDesign"; //$NON-NLS-1$

    /**
     * The name of the memento attribute that stores the design on the face of
     * the card.
     */
    private static final String FACE_DESIGN_MEMENTO_ATTRIBUTE_NAME = "faceDesign"; //$NON-NLS-1$

    /** The name of the memento attribute that stores the card location. */
    private static final String LOCATION_MEMENTO_ATTRIBUTE_NAME = "location"; //$NON-NLS-1$

    /** The name of the memento attribute that stores the card orientation. */
    private static final String ORIENTATION_MEMENTO_ATTRIBUTE_NAME = "orientation"; //$NON-NLS-1$

    /** The collection of supported card orientations. */
    private static final Collection<ComponentOrientation> SUPPORTED_ORIENTATIONS = Collections.unmodifiableCollection( Arrays.<ComponentOrientation>asList( CardOrientation.values( CardOrientation.class ) ) );


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Card} class.
     * 
     * @param tableEnvironment
     *        The table environment associated with the card; must not be
     *        {@code null}.
     */
    Card(
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
            memento.put( BACK_DESIGN_MEMENTO_ATTRIBUTE_NAME, getSurfaceDesign( CardOrientation.BACK ) );
            memento.put( FACE_DESIGN_MEMENTO_ATTRIBUTE_NAME, getSurfaceDesign( CardOrientation.FACE ) );
            memento.put( LOCATION_MEMENTO_ATTRIBUTE_NAME, getLocation() );
            memento.put( ORIENTATION_MEMENTO_ATTRIBUTE_NAME, getOrientation() );
        }
        finally
        {
            getLock().unlock();
        }

        return Collections.unmodifiableMap( memento );
    }

    /**
     * Creates a new instance of the {@code Card} class from the specified
     * memento.
     * 
     * @param tableEnvironment
     *        The table environment associated with the new card; must not be
     *        {@code null}.
     * @param memento
     *        The memento representing the initial card state; must not be
     *        {@code null}.
     * 
     * @return A new instance of the {@code Card} class; never {@code null}.
     * 
     * @throws org.gamegineer.common.core.util.memento.MementoException
     *         If {@code memento} is malformed.
     */
    /* @NonNull */
    static Card fromMemento(
        /* @NonNull */
        final TableEnvironment tableEnvironment,
        /* @NonNull */
        final Object memento )
        throws MementoException
    {
        assert tableEnvironment != null;
        assert memento != null;

        final Card card = new Card( tableEnvironment );

        final ComponentSurfaceDesign backDesign = MementoUtils.getAttribute( memento, BACK_DESIGN_MEMENTO_ATTRIBUTE_NAME, ComponentSurfaceDesign.class );
        card.setSurfaceDesign( CardOrientation.BACK, backDesign );

        final ComponentSurfaceDesign faceDesign = MementoUtils.getAttribute( memento, FACE_DESIGN_MEMENTO_ATTRIBUTE_NAME, ComponentSurfaceDesign.class );
        card.setSurfaceDesign( CardOrientation.FACE, faceDesign );

        final Point location = MementoUtils.getAttribute( memento, LOCATION_MEMENTO_ATTRIBUTE_NAME, Point.class );
        card.setLocation( location );

        final CardOrientation orientation = MementoUtils.getAttribute( memento, ORIENTATION_MEMENTO_ATTRIBUTE_NAME, CardOrientation.class );
        card.setOrientation( orientation );

        return card;
    }

    /*
     * @see org.gamegineer.table.internal.core.Component#getDefaultLocation()
     */
    @Override
    Point getDefaultLocation()
    {
        return new Point( 0, 0 );
    }

    /*
     * @see org.gamegineer.table.internal.core.Component#getDefaultOrientation()
     */
    @Override
    ComponentOrientation getDefaultOrientation()
    {
        return CardOrientation.FACE;
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
        final ComponentSurfaceDesign defaultSurfaceDesign = new ComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "org.gamegineer.table.internal.core.Card.DEFAULT_SURFACE_DESIGN" ), 0, 0 ); //$NON-NLS-1$
        final Map<ComponentOrientation, ComponentSurfaceDesign> surfaceDesigns = new IdentityHashMap<ComponentOrientation, ComponentSurfaceDesign>();
        surfaceDesigns.put( CardOrientation.BACK, defaultSurfaceDesign );
        surfaceDesigns.put( CardOrientation.FACE, defaultSurfaceDesign );
        return surfaceDesigns;
    }

    /*
     * @see org.gamegineer.table.core.IComponent#getSupportedOrientations()
     */
    @Override
    public Collection<ComponentOrientation> getSupportedOrientations()
    {
        return SUPPORTED_ORIENTATIONS;
    }

    /**
     * Indicates the specified memento is a {@code Card} memento.
     * 
     * @param memento
     *        The memento; must not be {@code null}.
     * 
     * @return {@code true} if the specified memento is a {@code Card} memento;
     *         otherwise {@code false}.
     * 
     * @throws org.gamegineer.common.core.util.memento.MementoException
     *         If {@code memento} is malformed.
     */
    static boolean isMemento(
        /* @NonNull */
        final Object memento )
        throws MementoException
    {
        assert memento != null;

        return MementoUtils.hasAttribute( memento, BACK_DESIGN_MEMENTO_ATTRIBUTE_NAME ) //
            && MementoUtils.hasAttribute( memento, FACE_DESIGN_MEMENTO_ATTRIBUTE_NAME ) //
            && MementoUtils.hasAttribute( memento, LOCATION_MEMENTO_ATTRIBUTE_NAME ) //
            && MementoUtils.hasAttribute( memento, ORIENTATION_MEMENTO_ATTRIBUTE_NAME );
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
            final Card card = fromMemento( getTableEnvironment(), memento );

            setSurfaceDesign( CardOrientation.BACK, card.getSurfaceDesign( CardOrientation.BACK ) );
            setSurfaceDesign( CardOrientation.FACE, card.getSurfaceDesign( CardOrientation.FACE ) );
            setLocation( card.getLocation() );
            setOrientation( card.getOrientation() );
        }
        finally
        {
            getLock().unlock();
        }
    }
}
