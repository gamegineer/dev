/*
 * CardSurfaceDesignUIs.java
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
 * Created on Nov 20, 2009 at 11:55:33 PM.
 */

package org.gamegineer.table.ui;

import static org.easymock.EasyMock.createMock;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import javax.swing.Icon;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.CardSurfaceDesigns;
import org.gamegineer.table.core.ICardSurfaceDesign;

/**
 * A factory for creating various types of card surface design user interface
 * types suitable for testing.
 */
@ThreadSafe
public final class CardSurfaceDesignUIs
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardSurfaceDesignUIs} class.
     */
    private CardSurfaceDesignUIs()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Clones the specified card surface design user interface.
     * 
     * @param cardSurfaceDesignUI
     *        The card surface design user interface to clone; must not be
     *        {@code null}.
     * 
     * @return A new card surface design user interface; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code cardSurfaceDesignUI} is {@code null}.
     */
    /* @NonNull */
    public static ICardSurfaceDesignUI cloneCardSurfaceDesignUI(
        /* @NonNull */
        final ICardSurfaceDesignUI cardSurfaceDesignUI )
    {
        assertArgumentNotNull( cardSurfaceDesignUI, "cardSurfaceDesignUI" ); //$NON-NLS-1$

        return CardSurfaceDesignUIFactory.createCardSurfaceDesignUI( cardSurfaceDesignUI.getId(), cardSurfaceDesignUI.getName(), cardSurfaceDesignUI.getIcon() );
    }

    /**
     * Creates a new card surface design user interface for the specified card
     * surface design.
     * 
     * @param cardSurfaceDesign
     *        The card surface design; must not be {@code null}.
     * 
     * @return A new card surface design user interface; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code cardSurfaceDesign} is {@code null}.
     */
    /* @NonNull */
    public static ICardSurfaceDesignUI createCardSurfaceDesignUI(
        /* @NonNull */
        final ICardSurfaceDesign cardSurfaceDesign )
    {
        assertArgumentNotNull( cardSurfaceDesign, "cardSurfaceDesign" ); //$NON-NLS-1$

        return CardSurfaceDesignUIFactory.createCardSurfaceDesignUI( cardSurfaceDesign.getId(), cardSurfaceDesign.getId().toString(), createMock( Icon.class ) );
    }

    /**
     * Creates a new card surface design user interface with a unique
     * identifier.
     * 
     * @return A new card surface design user interface; never {@code null}.
     */
    /* @NonNull */
    public static ICardSurfaceDesignUI createUniqueCardSurfaceDesignUI()
    {
        return createCardSurfaceDesignUI( CardSurfaceDesigns.createUniqueCardSurfaceDesign() );
    }
}
