/*
 * ICardPile.java
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
 * Created on Jan 9, 2010 at 10:59:42 PM.
 */

package org.gamegineer.table.core;

// TODO: we're going to add this type in stages.
//
// first we'll work out the interface details and get everything working with card piles by themselves
//
// then we need to decide how we're going to introduce them into the card table.  i suggest we first
// modify the table to only handle instances of ICardPile like we did with the previous card table
// implementations (C++ and C#).  if we try to move into table components/containers at this time, we
// may waste a lot of time for something that's not needed until we move on to the "grand plan"
//
// then again, it may be time well spent to work out the details of table components and containers now.
//
// however, i'm of the opinion that implementing the simple case first and then moving on to the more
// complex case will be more helpful in the long run.
//
// the only issue with having card piles be the sole child of the table is that we can no longer move
// cards around by themselves.  again, that may be fine or it may not be.  but given that we WILL
// eventually allow it, maybe it's something we shouldn't worry about at this point.

/**
 * TODO
 * 
 */
public interface ICardPile
{
    // ======================================================================
    // Methods
    // ======================================================================

    // TODO: milestone 1 says we will only support stacked piles for now.
    //
    // which means we only need to be able to add/remove cards from the top of the pile?

    // METHODS:
    //      - add card (to top of pile)
    //      - remove card (from top of pile)
    //      - get card (at top of pile)

    public void addCard(
        /* @NonNull */
        ICard card );

    public void addCardPileListener(
        /* @NonNull */
        ICardPileListener listener );

    // TODO: see discussion below in removeCard() about whether this method should
    // return null or fail if the card pile is empty.
    /* @Nullable */
    public ICard getCard(); // XXX: getTopCard ?

    // TODO: do we need a method to get a card count? or at least an isEmpty method?
    // --> will add an isEmpty method for now because it will be used by the view to
    // determine which commands are available for the card pile
    public boolean isEmpty();

    // TODO: should this method return null if the card pile is empty or should it fail?
    // i think simply returning null is better because it is thread-safe.  if we force the
    // caller to check isEmpty before calling removeCard, we introduce a TOCTOU condition.
    //
    // in both C++ and C# versions of Card Table, we threw an exception if the pile was empty!
    /* @Nullable */
    public ICard removeCard();

    public void removeCardPileListener(
        /* @NonNull */
        ICardPileListener listener );
}
