/*
 * CardSurfaceDesignUIRegistry.java
 * Copyright 2008-2011 Gamegineer.org
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
 * Created on Nov 21, 2009 at 12:27:06 AM.
 */

package org.gamegineer.table.internal.ui;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.CardSurfaceDesignId;
import org.gamegineer.table.ui.ICardSurfaceDesignUI;
import org.gamegineer.table.ui.ICardSurfaceDesignUIRegistry;

/**
 * Implementation of
 * {@link org.gamegineer.table.ui.ICardSurfaceDesignUIRegistry}.
 */
@ThreadSafe
public final class CardSurfaceDesignUIRegistry
    implements ICardSurfaceDesignUIRegistry
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The collection of card surface design user interfaces directly managed by
     * this object.
     */
    private final ConcurrentMap<CardSurfaceDesignId, ICardSurfaceDesignUI> cardSurfaceDesignUIs_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardSurfaceDesignUIRegistry}
     * class.
     */
    public CardSurfaceDesignUIRegistry()
    {
        cardSurfaceDesignUIs_ = new ConcurrentHashMap<CardSurfaceDesignId, ICardSurfaceDesignUI>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.ui.cardsurfacedesignuiregistry.ICardSurfaceDesignUIRegistry#getCardSurfaceDesignUI(org.gamegineer.table.core.CardSurfaceDesignId)
     */
    @Override
    public ICardSurfaceDesignUI getCardSurfaceDesignUI(
        final CardSurfaceDesignId id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        return cardSurfaceDesignUIs_.get( id );
    }

    /*
     * @see org.gamegineer.table.ui.cardsurfacedesignuiregistry.ICardSurfaceDesignUIRegistry#getCardSurfaceDesignUIs()
     */
    @Override
    public Collection<ICardSurfaceDesignUI> getCardSurfaceDesignUIs()
    {
        return new ArrayList<ICardSurfaceDesignUI>( cardSurfaceDesignUIs_.values() );
    }

    /*
     * @see org.gamegineer.table.ui.cardsurfacedesignuiregistry.ICardSurfaceDesignUIRegistry#registerCardSurfaceDesignUI(org.gamegineer.table.ui.ICardSurfaceDesignUI)
     */
    @Override
    public void registerCardSurfaceDesignUI(
        final ICardSurfaceDesignUI cardSurfaceDesignUI )
    {
        assertArgumentNotNull( cardSurfaceDesignUI, "cardSurfaceDesignUI" ); //$NON-NLS-1$
        assertArgumentLegal( cardSurfaceDesignUIs_.putIfAbsent( cardSurfaceDesignUI.getId(), cardSurfaceDesignUI ) == null, "cardSurfaceDesignUI", Messages.CardSurfaceDesignUIRegistry_registerCardSurfaceDesignUI_cardSurfaceDesignUI_registered( cardSurfaceDesignUI.getId() ) ); //$NON-NLS-1$

        Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "Registered card surface design user interface '%1$s'", cardSurfaceDesignUI.getId() ) ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.ui.cardsurfacedesignuiregistry.ICardSurfaceDesignUIRegistry#unregisterCardSurfaceDesignUI(org.gamegineer.table.ui.ICardSurfaceDesignUI)
     */
    @Override
    public void unregisterCardSurfaceDesignUI(
        final ICardSurfaceDesignUI cardSurfaceDesignUI )
    {
        assertArgumentNotNull( cardSurfaceDesignUI, "cardSurfaceDesignUI" ); //$NON-NLS-1$
        assertArgumentLegal( cardSurfaceDesignUIs_.remove( cardSurfaceDesignUI.getId(), cardSurfaceDesignUI ), "cardSurfaceDesignUI", Messages.CardSurfaceDesignUIRegistry_unregisterCardSurfaceDesignUI_cardSurfaceDesignUI_unregistered( cardSurfaceDesignUI.getId() ) ); //$NON-NLS-1$

        Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "Unregistered card surface design user interface '%1$s'", cardSurfaceDesignUI.getId() ) ); //$NON-NLS-1$
    }
}
