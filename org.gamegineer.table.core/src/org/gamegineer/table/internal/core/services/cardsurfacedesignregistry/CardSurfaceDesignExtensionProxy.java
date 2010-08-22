/*
 * CardSurfaceDesignExtensionProxy.java
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
 * Created on Aug 18, 2010 at 11:30:51 PM.
 */

package org.gamegineer.table.internal.core.services.cardsurfacedesignregistry;

import java.awt.Dimension;
import net.jcip.annotations.Immutable;
import org.eclipse.core.runtime.IExtension;
import org.gamegineer.table.core.CardSurfaceDesignId;
import org.gamegineer.table.core.ICardSurfaceDesign;
import org.gamegineer.table.core.TableFactory;

/**
 * Implementation of {@link org.gamegineer.table.core.ICardSurfaceDesign} that
 * acts as a proxy for a card surface design created from an extension.
 */
@Immutable
public final class CardSurfaceDesignExtensionProxy
    implements ICardSurfaceDesign
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card surface design to which all behavior is delegated. */
    private final ICardSurfaceDesign delegate_;

    /** The namespace identifier of the contributing extension. */
    private final String extensionNamespaceId_;

    /** The simple identifier of the contributing extension. */
    private final String extensionSimpleId_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardSurfaceDesignExtensionProxy}
     * class.
     * 
     * @param extension
     *        The extension that contributed this card surface design; must not
     *        be {@code null}.
     * @param id
     *        The card surface design identifier; must not be {@code null}.
     * @param width
     *        The card surface design width in table coordinates; must not be
     *        negative.
     * @param height
     *        The card surface design height in table coordinates; must not be
     *        negative.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code width} or {@code height} is negative.
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    CardSurfaceDesignExtensionProxy(
        /* @NonNull */
        final IExtension extension,
        /* @NonNull */
        final CardSurfaceDesignId id,
        final int width,
        final int height )
    {
        assert extension != null;

        extensionNamespaceId_ = extension.getNamespaceIdentifier();
        extensionSimpleId_ = extension.getSimpleIdentifier();
        delegate_ = TableFactory.createCardSurfaceDesign( id, width, height );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the card surface design to which all behavior is delegated.
     * 
     * @return The card surface design to which all behavior is delegated; never
     *         {@code null}.
     */
    /* @NonNull */
    ICardSurfaceDesign getDelegate()
    {
        return delegate_;
    }

    /**
     * Gets the namespace identifier of the contributing extension.
     * 
     * @return The namespace identifier of the contributing extension; never
     *         {@code null}.
     */
    /* @NonNull */
    String getExtensionNamespaceId()
    {
        return extensionNamespaceId_;
    }

    /**
     * Gets the simple identifier of the contributing extension.
     * 
     * @return The simple identifier of the contributing extension; may be
     *         {@code null}.
     */
    /* @Nullable */
    String getExtensionSimpleId()
    {
        return extensionSimpleId_;
    }

    /*
     * @see org.gamegineer.table.core.ICardSurfaceDesign#getId()
     */
    @Override
    public CardSurfaceDesignId getId()
    {
        return delegate_.getId();
    }

    /*
     * @see org.gamegineer.table.core.ICardSurfaceDesign#getSize()
     */
    @Override
    public Dimension getSize()
    {
        return delegate_.getSize();
    }
}