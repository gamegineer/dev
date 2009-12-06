/*
 * Actions.java
 * Copyright 2008-2009 Gamegineer.org
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
 * Created on Oct 9, 2009 at 10:26:28 PM.
 */

package org.gamegineer.table.internal.ui.view;

import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.internal.ui.action.BasicAction;

/**
 * The collection of actions available to all views.
 */
@NotThreadSafe
final class Actions
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The action used to add an Ace of Clubs card to the table. */
    private static final BasicAction addAceOfClubsCardAction_;

    /** The action used to add an Ace of Diamonds card to the table. */
    private static final BasicAction addAceOfDiamondsCardAction_;

    /** The action used to add an Ace of Hearts card to the table. */
    private static final BasicAction addAceOfHeartsCardAction_;

    /** The action used to add an Ace of Spades card to the table. */
    private static final BasicAction addAceOfSpadesCardAction_;

    /** The action used to add an Eight of Clubs card to the table. */
    private static final BasicAction addEightOfClubsCardAction_;

    /** The action used to add an Eight of Diamonds card to the table. */
    private static final BasicAction addEightOfDiamondsCardAction_;

    /** The action used to add an Eight of Hearts card to the table. */
    private static final BasicAction addEightOfHeartsCardAction_;

    /** The action used to add an Eight of Spades card to the table. */
    private static final BasicAction addEightOfSpadesCardAction_;

    /** The action used to add a Five of Clubs card to the table. */
    private static final BasicAction addFiveOfClubsCardAction_;

    /** The action used to add a Five of Diamonds card to the table. */
    private static final BasicAction addFiveOfDiamondsCardAction_;

    /** The action used to add a Five of Hearts card to the table. */
    private static final BasicAction addFiveOfHeartsCardAction_;

    /** The action used to add a Five of Spades card to the table. */
    private static final BasicAction addFiveOfSpadesCardAction_;

    /** The action used to add a Four of Clubs card to the table. */
    private static final BasicAction addFourOfClubsCardAction_;

    /** The action used to add a Four of Diamonds card to the table. */
    private static final BasicAction addFourOfDiamondsCardAction_;

    /** The action used to add a Four of Hearts card to the table. */
    private static final BasicAction addFourOfHeartsCardAction_;

    /** The action used to add a Four of Spades card to the table. */
    private static final BasicAction addFourOfSpadesCardAction_;

    /** The action used to add a Jack of Clubs card to the table. */
    private static final BasicAction addJackOfClubsCardAction_;

    /** The action used to add a Jack of Diamonds card to the table. */
    private static final BasicAction addJackOfDiamondsCardAction_;

    /** The action used to add a Jack of Hearts card to the table. */
    private static final BasicAction addJackOfHeartsCardAction_;

    /** The action used to add a Jack of Spades card to the table. */
    private static final BasicAction addJackOfSpadesCardAction_;

    /** The action used to add a Joker card to the table. */
    private static final BasicAction addJokerCardAction_;

    /** The action used to add a King of Clubs card to the table. */
    private static final BasicAction addKingOfClubsCardAction_;

    /** The action used to add a King of Diamonds card to the table. */
    private static final BasicAction addKingOfDiamondsCardAction_;

    /** The action used to add a King of Hearts card to the table. */
    private static final BasicAction addKingOfHeartsCardAction_;

    /** The action used to add a King of Spades card to the table. */
    private static final BasicAction addKingOfSpadesCardAction_;

    /** The action used to add a Nine of Clubs card to the table. */
    private static final BasicAction addNineOfClubsCardAction_;

    /** The action used to add a Nine of Diamonds card to the table. */
    private static final BasicAction addNineOfDiamondsCardAction_;

    /** The action used to add a Nine of Hearts card to the table. */
    private static final BasicAction addNineOfHeartsCardAction_;

    /** The action used to add a Nine of Spades card to the table. */
    private static final BasicAction addNineOfSpadesCardAction_;

    /** The action used to add a Queen of Clubs card to the table. */
    private static final BasicAction addQueenOfClubsCardAction_;

    /** The action used to add a Queen of Diamonds card to the table. */
    private static final BasicAction addQueenOfDiamondsCardAction_;

    /** The action used to add a Queen of Hearts card to the table. */
    private static final BasicAction addQueenOfHeartsCardAction_;

    /** The action used to add a Queen of Spades card to the table. */
    private static final BasicAction addQueenOfSpadesCardAction_;

    /** The action used to add a Seven of Clubs card to the table. */
    private static final BasicAction addSevenOfClubsCardAction_;

    /** The action used to add a Seven of Diamonds card to the table. */
    private static final BasicAction addSevenOfDiamondsCardAction_;

    /** The action used to add a Seven of Hearts card to the table. */
    private static final BasicAction addSevenOfHeartsCardAction_;

    /** The action used to add a Seven of Spades card to the table. */
    private static final BasicAction addSevenOfSpadesCardAction_;

    /** The action used to add a Six of Clubs card to the table. */
    private static final BasicAction addSixOfClubsCardAction_;

    /** The action used to add a Six of Diamonds card to the table. */
    private static final BasicAction addSixOfDiamondsCardAction_;

    /** The action used to add a Six of Hearts card to the table. */
    private static final BasicAction addSixOfHeartsCardAction_;

    /** The action used to add a Six of Spades card to the table. */
    private static final BasicAction addSixOfSpadesCardAction_;

    /** The action used to add a Ten of Clubs card to the table. */
    private static final BasicAction addTenOfClubsCardAction_;

    /** The action used to add a Ten of Diamonds card to the table. */
    private static final BasicAction addTenOfDiamondsCardAction_;

    /** The action used to add a Ten of Hearts card to the table. */
    private static final BasicAction addTenOfHeartsCardAction_;

    /** The action used to add a Ten of Spades card to the table. */
    private static final BasicAction addTenOfSpadesCardAction_;

    /** The action used to add a Three of Clubs card to the table. */
    private static final BasicAction addThreeOfClubsCardAction_;

    /** The action used to add a Three of Diamonds card to the table. */
    private static final BasicAction addThreeOfDiamondsCardAction_;

    /** The action used to add a Three of Hearts card to the table. */
    private static final BasicAction addThreeOfHeartsCardAction_;

    /** The action used to add a Three of Spades card to the table. */
    private static final BasicAction addThreeOfSpadesCardAction_;

    /** The action used to add a Two of Clubs card to the table. */
    private static final BasicAction addTwoOfClubsCardAction_;

    /** The action used to add a Two of Diamonds card to the table. */
    private static final BasicAction addTwoOfDiamondsCardAction_;

    /** The action used to add a Two of Hearts card to the table. */
    private static final BasicAction addTwoOfHeartsCardAction_;

    /** The action used to add a Two of Spades card to the table. */
    private static final BasicAction addTwoOfSpadesCardAction_;

    /** The action used to exit the application. */
    private static final BasicAction exitAction_;

    /** The action used to flip a card on the table. */
    private static final BasicAction flipCardAction_;

    /** The action used to open the about dialog. */
    private static final BasicAction openAboutDialogAction_;

    /** The action used to remove a card from the table. */
    private static final BasicAction removeCardAction_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code Actions} class.
     */
    static
    {
        addAceOfClubsCardAction_ = createAddAceOfClubsCardAction();
        addAceOfDiamondsCardAction_ = createAddAceOfDiamondsCardAction();
        addAceOfHeartsCardAction_ = createAddAceOfHeartsCardAction();
        addAceOfSpadesCardAction_ = createAddAceOfSpadesCardAction();
        addEightOfClubsCardAction_ = createAddEightOfClubsCardAction();
        addEightOfDiamondsCardAction_ = createAddEightOfDiamondsCardAction();
        addEightOfHeartsCardAction_ = createAddEightOfHeartsCardAction();
        addEightOfSpadesCardAction_ = createAddEightOfSpadesCardAction();
        addFiveOfClubsCardAction_ = createAddFiveOfClubsCardAction();
        addFiveOfDiamondsCardAction_ = createAddFiveOfDiamondsCardAction();
        addFiveOfHeartsCardAction_ = createAddFiveOfHeartsCardAction();
        addFiveOfSpadesCardAction_ = createAddFiveOfSpadesCardAction();
        addFourOfClubsCardAction_ = createAddFourOfClubsCardAction();
        addFourOfDiamondsCardAction_ = createAddFourOfDiamondsCardAction();
        addFourOfHeartsCardAction_ = createAddFourOfHeartsCardAction();
        addFourOfSpadesCardAction_ = createAddFourOfSpadesCardAction();
        addJackOfClubsCardAction_ = createAddJackOfClubsCardAction();
        addJackOfDiamondsCardAction_ = createAddJackOfDiamondsCardAction();
        addJackOfHeartsCardAction_ = createAddJackOfHeartsCardAction();
        addJackOfSpadesCardAction_ = createAddJackOfSpadesCardAction();
        addJokerCardAction_ = createAddJokerCardAction();
        addKingOfClubsCardAction_ = createAddKingOfClubsCardAction();
        addKingOfDiamondsCardAction_ = createAddKingOfDiamondsCardAction();
        addKingOfHeartsCardAction_ = createAddKingOfHeartsCardAction();
        addKingOfSpadesCardAction_ = createAddKingOfSpadesCardAction();
        addNineOfClubsCardAction_ = createAddNineOfClubsCardAction();
        addNineOfDiamondsCardAction_ = createAddNineOfDiamondsCardAction();
        addNineOfHeartsCardAction_ = createAddNineOfHeartsCardAction();
        addNineOfSpadesCardAction_ = createAddNineOfSpadesCardAction();
        addQueenOfClubsCardAction_ = createAddQueenOfClubsCardAction();
        addQueenOfDiamondsCardAction_ = createAddQueenOfDiamondsCardAction();
        addQueenOfHeartsCardAction_ = createAddQueenOfHeartsCardAction();
        addQueenOfSpadesCardAction_ = createAddQueenOfSpadesCardAction();
        addSevenOfClubsCardAction_ = createAddSevenOfClubsCardAction();
        addSevenOfDiamondsCardAction_ = createAddSevenOfDiamondsCardAction();
        addSevenOfHeartsCardAction_ = createAddSevenOfHeartsCardAction();
        addSevenOfSpadesCardAction_ = createAddSevenOfSpadesCardAction();
        addSixOfClubsCardAction_ = createAddSixOfClubsCardAction();
        addSixOfDiamondsCardAction_ = createAddSixOfDiamondsCardAction();
        addSixOfHeartsCardAction_ = createAddSixOfHeartsCardAction();
        addSixOfSpadesCardAction_ = createAddSixOfSpadesCardAction();
        addTenOfClubsCardAction_ = createAddTenOfClubsCardAction();
        addTenOfDiamondsCardAction_ = createAddTenOfDiamondsCardAction();
        addTenOfHeartsCardAction_ = createAddTenOfHeartsCardAction();
        addTenOfSpadesCardAction_ = createAddTenOfSpadesCardAction();
        addThreeOfClubsCardAction_ = createAddThreeOfClubsCardAction();
        addThreeOfDiamondsCardAction_ = createAddThreeOfDiamondsCardAction();
        addThreeOfHeartsCardAction_ = createAddThreeOfHeartsCardAction();
        addThreeOfSpadesCardAction_ = createAddThreeOfSpadesCardAction();
        addTwoOfClubsCardAction_ = createAddTwoOfClubsCardAction();
        addTwoOfDiamondsCardAction_ = createAddTwoOfDiamondsCardAction();
        addTwoOfHeartsCardAction_ = createAddTwoOfHeartsCardAction();
        addTwoOfSpadesCardAction_ = createAddTwoOfSpadesCardAction();
        exitAction_ = createExitAction();
        flipCardAction_ = createFlipCardAction();
        openAboutDialogAction_ = createOpenAboutDialogAction();
        removeCardAction_ = createRemoveCardAction();
    }

    /**
     * Initializes a new instance of the {@code Actions} class.
     */
    private Actions()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the add Ace of Clubs card action.
     * 
     * @return The add Ace of Clubs card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddAceOfClubsCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.clubs.ace" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddAceOfClubsCardAction_mnemonic ) );
                putValue( NAME, Messages.AddAceOfClubsCardAction_text );
            }
        };
    }

    /**
     * Creates the add Ace of Diamonds card action.
     * 
     * @return The add Ace of Diamonds card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddAceOfDiamondsCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.diamonds.ace" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddAceOfDiamondsCardAction_mnemonic ) );
                putValue( NAME, Messages.AddAceOfDiamondsCardAction_text );
            }
        };
    }

    /**
     * Creates the add Ace of Hearts card action.
     * 
     * @return The add Ace of Hearts card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddAceOfHeartsCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.hearts.ace" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddAceOfHeartsCardAction_mnemonic ) );
                putValue( NAME, Messages.AddAceOfHeartsCardAction_text );
            }
        };
    }

    /**
     * Creates the add Ace of Spades card action.
     * 
     * @return The add Ace of Spades card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddAceOfSpadesCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.spades.ace" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddAceOfSpadesCardAction_mnemonic ) );
                putValue( NAME, Messages.AddAceOfSpadesCardAction_text );
            }
        };
    }

    /**
     * Creates the add Eight of Clubs card action.
     * 
     * @return The add Eight of Clubs card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddEightOfClubsCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.clubs.eight" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddEightOfClubsCardAction_mnemonic ) );
                putValue( NAME, Messages.AddEightOfClubsCardAction_text );
            }
        };
    }

    /**
     * Creates the add Eight of Diamonds card action.
     * 
     * @return The add Eight of Diamonds card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddEightOfDiamondsCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.diamonds.eight" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddEightOfDiamondsCardAction_mnemonic ) );
                putValue( NAME, Messages.AddEightOfDiamondsCardAction_text );
            }
        };
    }

    /**
     * Creates the add Eight of Hearts card action.
     * 
     * @return The add Eight of Hearts card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddEightOfHeartsCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.hearts.eight" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddEightOfHeartsCardAction_mnemonic ) );
                putValue( NAME, Messages.AddEightOfHeartsCardAction_text );
            }
        };
    }

    /**
     * Creates the add Eight of Spades card action.
     * 
     * @return The add Eight of Spades card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddEightOfSpadesCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.spades.eight" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddEightOfSpadesCardAction_mnemonic ) );
                putValue( NAME, Messages.AddEightOfSpadesCardAction_text );
            }
        };
    }

    /**
     * Creates the add Five of Clubs card action.
     * 
     * @return The add Five of Clubs card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddFiveOfClubsCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.clubs.five" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddFiveOfClubsCardAction_mnemonic ) );
                putValue( NAME, Messages.AddFiveOfClubsCardAction_text );
            }
        };
    }

    /**
     * Creates the add Five of Diamonds card action.
     * 
     * @return The add Five of Diamonds card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddFiveOfDiamondsCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.diamonds.five" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddFiveOfDiamondsCardAction_mnemonic ) );
                putValue( NAME, Messages.AddFiveOfDiamondsCardAction_text );
            }
        };
    }

    /**
     * Creates the add Five of Hearts card action.
     * 
     * @return The add Five of Hearts card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddFiveOfHeartsCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.hearts.five" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddFiveOfHeartsCardAction_mnemonic ) );
                putValue( NAME, Messages.AddFiveOfHeartsCardAction_text );
            }
        };
    }

    /**
     * Creates the add Five of Spades card action.
     * 
     * @return The add Five of Spades card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddFiveOfSpadesCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.spades.five" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddFiveOfSpadesCardAction_mnemonic ) );
                putValue( NAME, Messages.AddFiveOfSpadesCardAction_text );
            }
        };
    }

    /**
     * Creates the add Four of Clubs card action.
     * 
     * @return The add Four of Clubs card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddFourOfClubsCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.clubs.four" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddFourOfClubsCardAction_mnemonic ) );
                putValue( NAME, Messages.AddFourOfClubsCardAction_text );
            }
        };
    }

    /**
     * Creates the add Four of Diamonds card action.
     * 
     * @return The add Four of Diamonds card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddFourOfDiamondsCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.diamonds.four" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddFourOfDiamondsCardAction_mnemonic ) );
                putValue( NAME, Messages.AddFourOfDiamondsCardAction_text );
            }
        };
    }

    /**
     * Creates the add Four of Hearts card action.
     * 
     * @return The add Four of Hearts card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddFourOfHeartsCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.hearts.four" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddFourOfHeartsCardAction_mnemonic ) );
                putValue( NAME, Messages.AddFourOfHeartsCardAction_text );
            }
        };
    }

    /**
     * Creates the add Four of Spades card action.
     * 
     * @return The add Four of Spades card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddFourOfSpadesCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.spades.four" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddFourOfSpadesCardAction_mnemonic ) );
                putValue( NAME, Messages.AddFourOfSpadesCardAction_text );
            }
        };
    }

    /**
     * Creates the add Jack of Clubs card action.
     * 
     * @return The add Jack of Clubs card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddJackOfClubsCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.clubs.jack" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddJackOfClubsCardAction_mnemonic ) );
                putValue( NAME, Messages.AddJackOfClubsCardAction_text );
            }
        };
    }

    /**
     * Creates the add Jack of Diamonds card action.
     * 
     * @return The add Jack of Diamonds card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddJackOfDiamondsCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.diamonds.jack" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddJackOfDiamondsCardAction_mnemonic ) );
                putValue( NAME, Messages.AddJackOfDiamondsCardAction_text );
            }
        };
    }

    /**
     * Creates the add Jack of Hearts card action.
     * 
     * @return The add Jack of Hearts card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddJackOfHeartsCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.hearts.jack" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddJackOfHeartsCardAction_mnemonic ) );
                putValue( NAME, Messages.AddJackOfHeartsCardAction_text );
            }
        };
    }

    /**
     * Creates the add Jack of Spades card action.
     * 
     * @return The add Jack of Spades card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddJackOfSpadesCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.spades.jack" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddJackOfSpadesCardAction_mnemonic ) );
                putValue( NAME, Messages.AddJackOfSpadesCardAction_text );
            }
        };
    }

    /**
     * Creates the add Joker card action.
     * 
     * @return The add Joker card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddJokerCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.special.joker" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddJokerCardAction_mnemonic ) );
                putValue( NAME, Messages.AddJokerCardAction_text );
            }
        };
    }

    /**
     * Creates the add King of Clubs card action.
     * 
     * @return The add King of Clubs card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddKingOfClubsCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.clubs.king" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddKingOfClubsCardAction_mnemonic ) );
                putValue( NAME, Messages.AddKingOfClubsCardAction_text );
            }
        };
    }

    /**
     * Creates the add King of Diamonds card action.
     * 
     * @return The add King of Diamonds card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddKingOfDiamondsCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.diamonds.king" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddKingOfDiamondsCardAction_mnemonic ) );
                putValue( NAME, Messages.AddKingOfDiamondsCardAction_text );
            }
        };
    }

    /**
     * Creates the add King of Hearts card action.
     * 
     * @return The add King of Hearts card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddKingOfHeartsCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.hearts.king" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddKingOfHeartsCardAction_mnemonic ) );
                putValue( NAME, Messages.AddKingOfHeartsCardAction_text );
            }
        };
    }

    /**
     * Creates the add King of Spades card action.
     * 
     * @return The add King of Spades card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddKingOfSpadesCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.spades.king" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddKingOfSpadesCardAction_mnemonic ) );
                putValue( NAME, Messages.AddKingOfSpadesCardAction_text );
            }
        };
    }

    /**
     * Creates the add Nine of Clubs card action.
     * 
     * @return The add Nine of Clubs card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddNineOfClubsCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.clubs.nine" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddNineOfClubsCardAction_mnemonic ) );
                putValue( NAME, Messages.AddNineOfClubsCardAction_text );
            }
        };
    }

    /**
     * Creates the add Nine of Diamonds card action.
     * 
     * @return The add Nine of Diamonds card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddNineOfDiamondsCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.diamonds.nine" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddNineOfDiamondsCardAction_mnemonic ) );
                putValue( NAME, Messages.AddNineOfDiamondsCardAction_text );
            }
        };
    }

    /**
     * Creates the add Nine of Hearts card action.
     * 
     * @return The add Nine of Hearts card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddNineOfHeartsCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.hearts.nine" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddNineOfHeartsCardAction_mnemonic ) );
                putValue( NAME, Messages.AddNineOfHeartsCardAction_text );
            }
        };
    }

    /**
     * Creates the add Nine of Spades card action.
     * 
     * @return The add Nine of Spades card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddNineOfSpadesCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.spades.nine" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddNineOfSpadesCardAction_mnemonic ) );
                putValue( NAME, Messages.AddNineOfSpadesCardAction_text );
            }
        };
    }

    /**
     * Creates the add Queen of Clubs card action.
     * 
     * @return The add Queen of Clubs card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddQueenOfClubsCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.clubs.queen" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddQueenOfClubsCardAction_mnemonic ) );
                putValue( NAME, Messages.AddQueenOfClubsCardAction_text );
            }
        };
    }

    /**
     * Creates the add Queen of Diamonds card action.
     * 
     * @return The add Queen of Diamonds card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddQueenOfDiamondsCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.diamonds.queen" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddQueenOfDiamondsCardAction_mnemonic ) );
                putValue( NAME, Messages.AddQueenOfDiamondsCardAction_text );
            }
        };
    }

    /**
     * Creates the add Queen of Hearts card action.
     * 
     * @return The add Queen of Hearts card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddQueenOfHeartsCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.hearts.queen" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddQueenOfHeartsCardAction_mnemonic ) );
                putValue( NAME, Messages.AddQueenOfHeartsCardAction_text );
            }
        };
    }

    /**
     * Creates the add Queen of Spades card action.
     * 
     * @return The add Queen of Spades card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddQueenOfSpadesCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.spades.queen" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddQueenOfSpadesCardAction_mnemonic ) );
                putValue( NAME, Messages.AddQueenOfSpadesCardAction_text );
            }
        };
    }

    /**
     * Creates the add Seven of Clubs card action.
     * 
     * @return The add Seven of Clubs card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddSevenOfClubsCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.clubs.seven" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddSevenOfClubsCardAction_mnemonic ) );
                putValue( NAME, Messages.AddSevenOfClubsCardAction_text );
            }
        };
    }

    /**
     * Creates the add Seven of Diamonds card action.
     * 
     * @return The add Seven of Diamonds card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddSevenOfDiamondsCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.diamonds.seven" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddSevenOfDiamondsCardAction_mnemonic ) );
                putValue( NAME, Messages.AddSevenOfDiamondsCardAction_text );
            }
        };
    }

    /**
     * Creates the add Seven of Hearts card action.
     * 
     * @return The add Seven of Hearts card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddSevenOfHeartsCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.hearts.seven" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddSevenOfHeartsCardAction_mnemonic ) );
                putValue( NAME, Messages.AddSevenOfHeartsCardAction_text );
            }
        };
    }

    /**
     * Creates the add Seven of Spades card action.
     * 
     * @return The add Seven of Spades card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddSevenOfSpadesCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.spades.seven" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddSevenOfSpadesCardAction_mnemonic ) );
                putValue( NAME, Messages.AddSevenOfSpadesCardAction_text );
            }
        };
    }

    /**
     * Creates the add Six of Clubs card action.
     * 
     * @return The add Six of Clubs card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddSixOfClubsCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.clubs.six" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddSixOfClubsCardAction_mnemonic ) );
                putValue( NAME, Messages.AddSixOfClubsCardAction_text );
            }
        };
    }

    /**
     * Creates the add Six of Diamonds card action.
     * 
     * @return The add Six of Diamonds card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddSixOfDiamondsCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.diamonds.six" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddSixOfDiamondsCardAction_mnemonic ) );
                putValue( NAME, Messages.AddSixOfDiamondsCardAction_text );
            }
        };
    }

    /**
     * Creates the add Six of Hearts card action.
     * 
     * @return The add Six of Hearts card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddSixOfHeartsCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.hearts.six" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddSixOfHeartsCardAction_mnemonic ) );
                putValue( NAME, Messages.AddSixOfHeartsCardAction_text );
            }
        };
    }

    /**
     * Creates the add Six of Spades card action.
     * 
     * @return The add Six of Spades card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddSixOfSpadesCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.spades.six" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddSixOfSpadesCardAction_mnemonic ) );
                putValue( NAME, Messages.AddSixOfSpadesCardAction_text );
            }
        };
    }

    /**
     * Creates the add Ten of Clubs card action.
     * 
     * @return The add Ten of Clubs card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddTenOfClubsCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.clubs.ten" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddTenOfClubsCardAction_mnemonic ) );
                putValue( NAME, Messages.AddTenOfClubsCardAction_text );
            }
        };
    }

    /**
     * Creates the add Ten of Diamonds card action.
     * 
     * @return The add Ten of Diamonds card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddTenOfDiamondsCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.diamonds.ten" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddTenOfDiamondsCardAction_mnemonic ) );
                putValue( NAME, Messages.AddTenOfDiamondsCardAction_text );
            }
        };
    }

    /**
     * Creates the add Ten of Hearts card action.
     * 
     * @return The add Ten of Hearts card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddTenOfHeartsCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.hearts.ten" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddTenOfHeartsCardAction_mnemonic ) );
                putValue( NAME, Messages.AddTenOfHeartsCardAction_text );
            }
        };
    }

    /**
     * Creates the add Ten of Spades card action.
     * 
     * @return The add Ten of Spades card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddTenOfSpadesCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.spades.ten" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddTenOfSpadesCardAction_mnemonic ) );
                putValue( NAME, Messages.AddTenOfSpadesCardAction_text );
            }
        };
    }

    /**
     * Creates the add Three of Clubs card action.
     * 
     * @return The add Three of Clubs card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddThreeOfClubsCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.clubs.three" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddThreeOfClubsCardAction_mnemonic ) );
                putValue( NAME, Messages.AddThreeOfClubsCardAction_text );
            }
        };
    }

    /**
     * Creates the add Three of Diamonds card action.
     * 
     * @return The add Three of Diamonds card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddThreeOfDiamondsCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.diamonds.three" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddThreeOfDiamondsCardAction_mnemonic ) );
                putValue( NAME, Messages.AddThreeOfDiamondsCardAction_text );
            }
        };
    }

    /**
     * Creates the add Three of Hearts card action.
     * 
     * @return The add Three of Hearts card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddThreeOfHeartsCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.hearts.three" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddThreeOfHeartsCardAction_mnemonic ) );
                putValue( NAME, Messages.AddThreeOfHeartsCardAction_text );
            }
        };
    }

    /**
     * Creates the add Three of Spades card action.
     * 
     * @return The add Three of Spades card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddThreeOfSpadesCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.spades.three" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddThreeOfSpadesCardAction_mnemonic ) );
                putValue( NAME, Messages.AddThreeOfSpadesCardAction_text );
            }
        };
    }

    /**
     * Creates the add Two of Clubs card action.
     * 
     * @return The add Two of Clubs card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddTwoOfClubsCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.clubs.two" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddTwoOfClubsCardAction_mnemonic ) );
                putValue( NAME, Messages.AddTwoOfClubsCardAction_text );
            }
        };
    }

    /**
     * Creates the add Two of Diamonds card action.
     * 
     * @return The add Two of Diamonds card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddTwoOfDiamondsCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.diamonds.two" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddTwoOfDiamondsCardAction_mnemonic ) );
                putValue( NAME, Messages.AddTwoOfDiamondsCardAction_text );
            }
        };
    }

    /**
     * Creates the add Two of Hearts card action.
     * 
     * @return The add Two of Hearts card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddTwoOfHeartsCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.hearts.two" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddTwoOfHeartsCardAction_mnemonic ) );
                putValue( NAME, Messages.AddTwoOfHeartsCardAction_text );
            }
        };
    }

    /**
     * Creates the add Two of Spades card action.
     * 
     * @return The add Two of Spades card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createAddTwoOfSpadesCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( ACTION_COMMAND_KEY, "org.gamegineer.cards.spades.two" ); //$NON-NLS-1$
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.AddTwoOfSpadesCardAction_mnemonic ) );
                putValue( NAME, Messages.AddTwoOfSpadesCardAction_text );
            }
        };
    }

    /**
     * Creates the exit action.
     * 
     * @return The exit action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createExitAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.ExitAction_mnemonic ) );
                putValue( NAME, Messages.ExitAction_text );
            }
        };
    }

    /**
     * Creates the flip card action.
     * 
     * @return The flip card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createFlipCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.FlipCardAction_mnemonic ) );
                putValue( NAME, Messages.FlipCardAction_text );
            }
        };
    }

    /**
     * Creates the open about dialog action.
     * 
     * @return The open about dialog action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createOpenAboutDialogAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.OpenAboutDialogAction_mnemonic ) );
                putValue( NAME, Messages.OpenAboutDialogAction_text );
            }
        };
    }

    /**
     * Creates the remove card action.
     * 
     * @return The remove card action; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static BasicAction createRemoveCardAction()
    {
        return new BasicAction()
        {
            private static final long serialVersionUID = 1L;

            {
                putValue( MNEMONIC_KEY, Messages.toMnemonic( Messages.RemoveCardAction_mnemonic ) );
                putValue( NAME, Messages.RemoveCardAction_text );
            }
        };
    }

    /**
     * Gets the add Ace of Clubs card action.
     * 
     * @return The add Ace of Clubs card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddAceOfClubsCardAction()
    {
        return addAceOfClubsCardAction_;
    }

    /**
     * Gets the add Ace of Diamonds card action.
     * 
     * @return The add Ace of Diamonds card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddAceOfDiamondsCardAction()
    {
        return addAceOfDiamondsCardAction_;
    }

    /**
     * Gets the add Ace of Hearts card action.
     * 
     * @return The add Ace of Hearts card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddAceOfHeartsCardAction()
    {
        return addAceOfHeartsCardAction_;
    }

    /**
     * Gets the add Ace of Spades card action.
     * 
     * @return The add Ace of Spades card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddAceOfSpadesCardAction()
    {
        return addAceOfSpadesCardAction_;
    }

    /**
     * Gets the add Eight of Clubs card action.
     * 
     * @return The add Eight of Clubs card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddEightOfClubsCardAction()
    {
        return addEightOfClubsCardAction_;
    }

    /**
     * Gets the add Eight of Diamonds card action.
     * 
     * @return The add Eight of Diamonds card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddEightOfDiamondsCardAction()
    {
        return addEightOfDiamondsCardAction_;
    }

    /**
     * Gets the add Eight of Hearts card action.
     * 
     * @return The add Eight of Hearts card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddEightOfHeartsCardAction()
    {
        return addEightOfHeartsCardAction_;
    }

    /**
     * Gets the add Eight of Spades card action.
     * 
     * @return The add Eight of Spades card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddEightOfSpadesCardAction()
    {
        return addEightOfSpadesCardAction_;
    }

    /**
     * Gets the add Five of Clubs card action.
     * 
     * @return The add Five of Clubs card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddFiveOfClubsCardAction()
    {
        return addFiveOfClubsCardAction_;
    }

    /**
     * Gets the add Five of Diamonds card action.
     * 
     * @return The add Five of Diamonds card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddFiveOfDiamondsCardAction()
    {
        return addFiveOfDiamondsCardAction_;
    }

    /**
     * Gets the add Five of Hearts card action.
     * 
     * @return The add Five of Hearts card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddFiveOfHeartsCardAction()
    {
        return addFiveOfHeartsCardAction_;
    }

    /**
     * Gets the add Five of Spades card action.
     * 
     * @return The add Five of Spades card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddFiveOfSpadesCardAction()
    {
        return addFiveOfSpadesCardAction_;
    }

    /**
     * Gets the add Four of Clubs card action.
     * 
     * @return The add Four of Clubs card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddFourOfClubsCardAction()
    {
        return addFourOfClubsCardAction_;
    }

    /**
     * Gets the add Four of Diamonds card action.
     * 
     * @return The add Four of Diamonds card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddFourOfDiamondsCardAction()
    {
        return addFourOfDiamondsCardAction_;
    }

    /**
     * Gets the add Four of Hearts card action.
     * 
     * @return The add Four of Hearts card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddFourOfHeartsCardAction()
    {
        return addFourOfHeartsCardAction_;
    }

    /**
     * Gets the add Four of Spades card action.
     * 
     * @return The add Four of Spades card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddFourOfSpadesCardAction()
    {
        return addFourOfSpadesCardAction_;
    }

    /**
     * Gets the add Jack of Clubs card action.
     * 
     * @return The add Jack of Clubs card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddJackOfClubsCardAction()
    {
        return addJackOfClubsCardAction_;
    }

    /**
     * Gets the add Jack of Diamonds card action.
     * 
     * @return The add Jack of Diamonds card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddJackOfDiamondsCardAction()
    {
        return addJackOfDiamondsCardAction_;
    }

    /**
     * Gets the add Jack of Hearts card action.
     * 
     * @return The add Jack of Hearts card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddJackOfHeartsCardAction()
    {
        return addJackOfHeartsCardAction_;
    }

    /**
     * Gets the add Jack of Spades card action.
     * 
     * @return The add Jack of Spades card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddJackOfSpadesCardAction()
    {
        return addJackOfSpadesCardAction_;
    }

    /**
     * Gets the add Joker card action.
     * 
     * @return The add Joker card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddJokerCardAction()
    {
        return addJokerCardAction_;
    }

    /**
     * Gets the add King of Clubs card action.
     * 
     * @return The add King of Clubs card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddKingOfClubsCardAction()
    {
        return addKingOfClubsCardAction_;
    }

    /**
     * Gets the add King of Diamonds card action.
     * 
     * @return The add King of Diamonds card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddKingOfDiamondsCardAction()
    {
        return addKingOfDiamondsCardAction_;
    }

    /**
     * Gets the add King of Hearts card action.
     * 
     * @return The add King of Hearts card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddKingOfHeartsCardAction()
    {
        return addKingOfHeartsCardAction_;
    }

    /**
     * Gets the add King of Spades card action.
     * 
     * @return The add King of Spades card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddKingOfSpadesCardAction()
    {
        return addKingOfSpadesCardAction_;
    }

    /**
     * Gets the add Nine of Clubs card action.
     * 
     * @return The add Nine of Clubs card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddNineOfClubsCardAction()
    {
        return addNineOfClubsCardAction_;
    }

    /**
     * Gets the add Nine of Diamonds card action.
     * 
     * @return The add Nine of Diamonds card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddNineOfDiamondsCardAction()
    {
        return addNineOfDiamondsCardAction_;
    }

    /**
     * Gets the add Nine of Hearts card action.
     * 
     * @return The add Nine of Hearts card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddNineOfHeartsCardAction()
    {
        return addNineOfHeartsCardAction_;
    }

    /**
     * Gets the add Nine of Spades card action.
     * 
     * @return The add Nine of Spades card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddNineOfSpadesCardAction()
    {
        return addNineOfSpadesCardAction_;
    }

    /**
     * Gets the add Queen of Clubs card action.
     * 
     * @return The add Queen of Clubs card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddQueenOfClubsCardAction()
    {
        return addQueenOfClubsCardAction_;
    }

    /**
     * Gets the add Queen of Diamonds card action.
     * 
     * @return The add Queen of Diamonds card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddQueenOfDiamondsCardAction()
    {
        return addQueenOfDiamondsCardAction_;
    }

    /**
     * Gets the add Queen of Hearts card action.
     * 
     * @return The add Queen of Hearts card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddQueenOfHeartsCardAction()
    {
        return addQueenOfHeartsCardAction_;
    }

    /**
     * Gets the add Queen of Spades card action.
     * 
     * @return The add Queen of Spades card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddQueenOfSpadesCardAction()
    {
        return addQueenOfSpadesCardAction_;
    }

    /**
     * Gets the add Seven of Clubs card action.
     * 
     * @return The add Seven of Clubs card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddSevenOfClubsCardAction()
    {
        return addSevenOfClubsCardAction_;
    }

    /**
     * Gets the add Seven of Diamonds card action.
     * 
     * @return The add Seven of Diamonds card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddSevenOfDiamondsCardAction()
    {
        return addSevenOfDiamondsCardAction_;
    }

    /**
     * Gets the add Seven of Hearts card action.
     * 
     * @return The add Seven of Hearts card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddSevenOfHeartsCardAction()
    {
        return addSevenOfHeartsCardAction_;
    }

    /**
     * Gets the add Seven of Spades card action.
     * 
     * @return The add Seven of Spades card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddSevenOfSpadesCardAction()
    {
        return addSevenOfSpadesCardAction_;
    }

    /**
     * Gets the add Six of Clubs card action.
     * 
     * @return The add Six of Clubs card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddSixOfClubsCardAction()
    {
        return addSixOfClubsCardAction_;
    }

    /**
     * Gets the add Six of Diamonds card action.
     * 
     * @return The add Six of Diamonds card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddSixOfDiamondsCardAction()
    {
        return addSixOfDiamondsCardAction_;
    }

    /**
     * Gets the add Six of Hearts card action.
     * 
     * @return The add Six of Hearts card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddSixOfHeartsCardAction()
    {
        return addSixOfHeartsCardAction_;
    }

    /**
     * Gets the add Six of Spades card action.
     * 
     * @return The add Six of Spades card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddSixOfSpadesCardAction()
    {
        return addSixOfSpadesCardAction_;
    }

    /**
     * Gets the add Ten of Clubs card action.
     * 
     * @return The add Ten of Clubs card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddTenOfClubsCardAction()
    {
        return addTenOfClubsCardAction_;
    }

    /**
     * Gets the add Ten of Diamonds card action.
     * 
     * @return The add Ten of Diamonds card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddTenOfDiamondsCardAction()
    {
        return addTenOfDiamondsCardAction_;
    }

    /**
     * Gets the add Ten of Hearts card action.
     * 
     * @return The add Ten of Hearts card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddTenOfHeartsCardAction()
    {
        return addTenOfHeartsCardAction_;
    }

    /**
     * Gets the add Ten of Spades card action.
     * 
     * @return The add Ten of Spades card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddTenOfSpadesCardAction()
    {
        return addTenOfSpadesCardAction_;
    }

    /**
     * Gets the add Three of Clubs card action.
     * 
     * @return The add Three of Clubs card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddThreeOfClubsCardAction()
    {
        return addThreeOfClubsCardAction_;
    }

    /**
     * Gets the add Three of Diamonds card action.
     * 
     * @return The add Three of Diamonds card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddThreeOfDiamondsCardAction()
    {
        return addThreeOfDiamondsCardAction_;
    }

    /**
     * Gets the add Three of Hearts card action.
     * 
     * @return The add Three of Hearts card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddThreeOfHeartsCardAction()
    {
        return addThreeOfHeartsCardAction_;
    }

    /**
     * Gets the add Three of Spades card action.
     * 
     * @return The add Three of Spades card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddThreeOfSpadesCardAction()
    {
        return addThreeOfSpadesCardAction_;
    }

    /**
     * Gets the add Two of Clubs card action.
     * 
     * @return The add Two of Clubs card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddTwoOfClubsCardAction()
    {
        return addTwoOfClubsCardAction_;
    }

    /**
     * Gets the add Two of Diamonds card action.
     * 
     * @return The add Two of Diamonds card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddTwoOfDiamondsCardAction()
    {
        return addTwoOfDiamondsCardAction_;
    }

    /**
     * Gets the add Two of Hearts card action.
     * 
     * @return The add Two of Hearts card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddTwoOfHeartsCardAction()
    {
        return addTwoOfHeartsCardAction_;
    }

    /**
     * Gets the add Two of Spades card action.
     * 
     * @return The add Two of Spades card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getAddTwoOfSpadesCardAction()
    {
        return addTwoOfSpadesCardAction_;
    }

    /**
     * Gets the exit action.
     * 
     * @return The exit action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getExitAction()
    {
        return exitAction_;
    }

    /**
     * Gets the flip card action.
     * 
     * @return The flip card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getFlipCardAction()
    {
        return flipCardAction_;
    }

    /**
     * Gets the open about dialog action.
     * 
     * @return The open about dialog action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getOpenAboutDialogAction()
    {
        return openAboutDialogAction_;
    }

    /**
     * Gets the remove card action.
     * 
     * @return The remove card action; never {@code null}.
     */
    /* @NonNull */
    static BasicAction getRemoveCardAction()
    {
        return removeCardAction_;
    }
}
