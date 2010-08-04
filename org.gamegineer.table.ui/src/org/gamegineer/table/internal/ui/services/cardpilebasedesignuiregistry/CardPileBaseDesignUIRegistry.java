/*
 * CardPileBaseDesignUIRegistry.java
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
 * Created on Jan 23, 2010 at 9:37:47 PM.
 */

package org.gamegineer.table.internal.ui.services.cardpilebasedesignuiregistry;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.CardPileBaseDesignId;
import org.gamegineer.table.internal.ui.Debug;
import org.gamegineer.table.ui.ICardPileBaseDesignUI;
import org.gamegineer.table.ui.services.cardpilebasedesignuiregistry.ICardPileBaseDesignUIRegistry;

/**
 * Implementation of
 * {@link org.gamegineer.table.ui.services.cardpilebasedesignuiregistry.ICardPileBaseDesignUIRegistry}
 * .
 */
@ThreadSafe
public final class CardPileBaseDesignUIRegistry
    implements ICardPileBaseDesignUIRegistry
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The collection of card pile base design user interfaces directly managed
     * by this object.
     */
    private final ConcurrentMap<CardPileBaseDesignId, ICardPileBaseDesignUI> cardPileBaseDesignUIs_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileBaseDesignUIRegistry}
     * class.
     */
    public CardPileBaseDesignUIRegistry()
    {
        cardPileBaseDesignUIs_ = new ConcurrentHashMap<CardPileBaseDesignId, ICardPileBaseDesignUI>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.ui.services.cardpilebasedesignuiregistry.ICardPileBaseDesignUIRegistry#getCardPileBaseDesignUI(org.gamegineer.table.core.CardPileBaseDesignId)
     */
    @Override
    public ICardPileBaseDesignUI getCardPileBaseDesignUI(
        final CardPileBaseDesignId id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        return cardPileBaseDesignUIs_.get( id );
    }

    /*
     * @see org.gamegineer.table.ui.services.cardpilebasedesignuiregistry.ICardPileBaseDesignUIRegistry#getCardPileBaseDesignUIs()
     */
    @Override
    public Collection<ICardPileBaseDesignUI> getCardPileBaseDesignUIs()
    {
        return new ArrayList<ICardPileBaseDesignUI>( cardPileBaseDesignUIs_.values() );
    }

    /*
     * @see org.gamegineer.table.ui.services.cardpilebasedesignuiregistry.ICardPileBaseDesignUIRegistry#registerCardPileBaseDesignUI(org.gamegineer.table.ui.ICardPileBaseDesignUI)
     */
    @Override
    public void registerCardPileBaseDesignUI(
        final ICardPileBaseDesignUI cardPileBaseDesignUI )
    {
        assertArgumentNotNull( cardPileBaseDesignUI, "cardPileBaseDesignUI" ); //$NON-NLS-1$
        assertArgumentLegal( cardPileBaseDesignUIs_.putIfAbsent( cardPileBaseDesignUI.getId(), cardPileBaseDesignUI ) == null, "cardPileBaseDesignUI", Messages.CardPileBaseDesignUIRegistry_registerCardPileBaseDesignUI_cardPileBaseDesignUI_registered( cardPileBaseDesignUI.getId() ) ); //$NON-NLS-1$

        Debug.getDefault().trace( Debug.OPTION_SERVICES_CARD_PILE_BASE_DESIGN_UI_REGISTRY, String.format( "Registered card pile base design user interface '%1$s'", cardPileBaseDesignUI.getId() ) ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.ui.services.cardpilebasedesignuiregistry.ICardPileBaseDesignUIRegistry#unregisterCardPileBaseDesignUI(org.gamegineer.table.ui.ICardPileBaseDesignUI)
     */
    @Override
    public void unregisterCardPileBaseDesignUI(
        final ICardPileBaseDesignUI cardPileBaseDesignUI )
    {
        assertArgumentNotNull( cardPileBaseDesignUI, "cardPileBaseDesignUI" ); //$NON-NLS-1$
        assertArgumentLegal( cardPileBaseDesignUIs_.remove( cardPileBaseDesignUI.getId(), cardPileBaseDesignUI ), "cardPileBaseDesignUI", Messages.CardPileBaseDesignUIRegistry_unregisterCardPileBaseDesignUI_cardPileBaseDesignUI_unregistered( cardPileBaseDesignUI.getId() ) ); //$NON-NLS-1$

        Debug.getDefault().trace( Debug.OPTION_SERVICES_CARD_PILE_BASE_DESIGN_UI_REGISTRY, String.format( "Unregistered card pile base design user interface '%1$s'", cardPileBaseDesignUI.getId() ) ); //$NON-NLS-1$
    }
}
