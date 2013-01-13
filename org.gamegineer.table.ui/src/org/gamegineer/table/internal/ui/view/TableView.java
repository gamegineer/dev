/*
 * TableView.java
 * Copyright 2008-2013 Gamegineer.org
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
 * Created on Oct 6, 2009 at 11:16:52 PM.
 */

package org.gamegineer.table.internal.ui.view;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.dnd.DragSource;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.cards.core.CardPileOrientation;
import org.gamegineer.cards.core.CardsComponentStrategyIds;
import org.gamegineer.common.core.util.IPredicate;
import org.gamegineer.common.ui.window.WindowConstants;
import org.gamegineer.common.ui.wizard.IWizard;
import org.gamegineer.common.ui.wizard.WizardDialog;
import org.gamegineer.table.core.ComponentStrategyRegistry;
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.core.ContainerLayoutId;
import org.gamegineer.table.core.ContainerLayoutRegistry;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.NoSuchComponentStrategyException;
import org.gamegineer.table.core.NoSuchContainerLayoutException;
import org.gamegineer.table.internal.ui.Activator;
import org.gamegineer.table.internal.ui.BundleImages;
import org.gamegineer.table.internal.ui.Loggers;
import org.gamegineer.table.internal.ui.action.ActionMediator;
import org.gamegineer.table.internal.ui.dialogs.selectremoteplayer.SelectRemotePlayerDialog;
import org.gamegineer.table.internal.ui.model.ComponentModel;
import org.gamegineer.table.internal.ui.model.ContainerModel;
import org.gamegineer.table.internal.ui.model.ITableModelListener;
import org.gamegineer.table.internal.ui.model.ModelException;
import org.gamegineer.table.internal.ui.model.TableModel;
import org.gamegineer.table.internal.ui.model.TableModelEvent;
import org.gamegineer.table.internal.ui.prototype.ComponentPrototypeUtils;
import org.gamegineer.table.internal.ui.wizards.hosttablenetwork.HostTableNetworkWizard;
import org.gamegineer.table.internal.ui.wizards.jointablenetwork.JoinTableNetworkWizard;
import org.gamegineer.table.net.IPlayer;
import org.gamegineer.table.net.PlayerRole;
import org.gamegineer.table.ui.prototype.ComponentPrototypeFactoryException;
import org.gamegineer.table.ui.prototype.IComponentPrototypeFactory;

// TODO: Remove all references to "card" and "card pile".

/**
 * A view of the table.
 */
@NotThreadSafe
final class TableView
    extends JPanel
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 3574703230407179091L;

    /** The action mediator. */
    private final ActionMediator actionMediator_;

    /** The background paint. */
    private final Paint backgroundPaint_;

    /** The key listener for this view. */
    private final KeyListener keyListener_;

    /** The model associated with this view. */
    private final TableModel model_;

    /** The current mouse input handler. */
    private AbstractMouseInputHandler mouseInputHandler_;

    /** The collection of mouse input handler singletons. */
    private final Map<Class<? extends AbstractMouseInputHandler>, AbstractMouseInputHandler> mouseInputHandlers_;

    /** The mouse input listener for this view. */
    private final MouseInputListener mouseInputListener_;

    /** The table model listener for this view. */
    private ITableModelListener tableModelListener_;

    /** The tabletop view. */
    private ContainerView tabletopView_;

    /**
     * Indicates the active mouse handler should update the focus in the Mouse
     * Released handler (as opposed to the Mouse Pressed handler).
     */
    private boolean updateFocusOnMouseReleased_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableView} class.
     * 
     * @param model
     *        The model associated with this view; must not be {@code null}.
     */
    TableView(
        /* @NonNull */
        final TableModel model )
    {
        assert model != null;

        actionMediator_ = new ActionMediator();
        backgroundPaint_ = createBackgroundPaint();
        keyListener_ = createKeyListener();
        model_ = model;
        mouseInputHandlers_ = createMouseInputHandlers();
        mouseInputHandler_ = mouseInputHandlers_.get( DefaultMouseInputHandler.class );
        mouseInputListener_ = createMouseInputListener();
        tableModelListener_ = null;
        tabletopView_ = null;
        updateFocusOnMouseReleased_ = false;

        initializeComponent();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds a new component to the table.
     * 
     * @param componentPrototypeFactory
     *        The component prototype factory used to create the new component;
     *        must not be {@code null}.
     */
    private void addComponent(
        /* @NonNull */
        final IComponentPrototypeFactory componentPrototypeFactory )
    {
        assert componentPrototypeFactory != null;

        try
        {
            final List<IComponent> components = componentPrototypeFactory.createComponentPrototype( model_.getTable().getTableEnvironment() );

            final IContainer focusedContainer = getFocusedContainer();
            if( focusedContainer != null )
            {
                focusedContainer.addComponents( components );
            }
            else
            {
                final Point location = getMouseLocation();
                convertPointFromTable( location );
                final Dimension tableSize = getSize();
                for( final IComponent component : components )
                {
                    final Dimension cardPileSize = component.getSize();
                    if( location.x < 0 )
                    {
                        location.x = 0;
                    }
                    else if( location.x + cardPileSize.width > tableSize.width )
                    {
                        location.x = tableSize.width - cardPileSize.width;
                    }
                    if( location.y < 0 )
                    {
                        location.y = 0;
                    }
                    else if( location.y + cardPileSize.height > tableSize.height )
                    {
                        location.y = tableSize.height - cardPileSize.height;
                    }
                    convertPointToTable( location );
                    component.setLocation( location );
                }

                model_.getTable().getTabletop().addComponents( components );
            }
        }
        catch( final ComponentPrototypeFactoryException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.TableView_addComponent_error, e );
        }
    }

    /*
     * @see javax.swing.JComponent#addNotify()
     */
    @Override
    public void addNotify()
    {
        super.addNotify();

        bindActions();
        tableModelListener_ = new TableModelListener();
        model_.addTableModelListener( tableModelListener_ );
        addKeyListener( keyListener_ );
        addMouseListener( mouseInputListener_ );
        addMouseMotionListener( mouseInputListener_ );

        createTabletopView();
    }

    /**
     * Binds the action attachments for this component.
     */
    private void bindActions()
    {
        final ActionListener setCardPileLayoutActionListener = new ActionListener()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                final ActionEvent event )
            {
                setCardPileLayout( ContainerLayoutId.fromString( event.getActionCommand() ) );
            }
        };
        actionMediator_.bindActionListener( Actions.getAddComponentAction(), new ActionListener()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                final ActionEvent event )
            {
                final IComponentPrototypeFactory componentPrototypeFactory = ComponentPrototypeUtils.getComponentPrototypeFactory( event );
                if( componentPrototypeFactory != null )
                {
                    addComponent( componentPrototypeFactory );
                }
            }
        } );
        actionMediator_.bindActionListener( Actions.getCancelTableNetworkControlRequestAction(), new ActionListener()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent event )
            {
                cancelTableNetworkControlRequest();
            }
        } );
        actionMediator_.bindActionListener( Actions.getDisconnectTableNetworkAction(), new ActionListener()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent event )
            {
                disconnectTableNetwork();
            }
        } );
        actionMediator_.bindActionListener( Actions.getFlipCardAction(), new ActionListener()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent event )
            {
                flipTopCard();
            }
        } );
        actionMediator_.bindActionListener( Actions.getGiveTableNetworkControlAction(), new ActionListener()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent event )
            {
                giveTableNetworkControl();
            }
        } );
        actionMediator_.bindActionListener( Actions.getHostTableNetworkAction(), new ActionListener()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent event )
            {
                hostTableNetwork();
            }
        } );
        actionMediator_.bindActionListener( Actions.getJoinTableNetworkAction(), new ActionListener()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent event )
            {
                joinTableNetwork();
            }
        } );
        actionMediator_.bindActionListener( Actions.getRemoveAllCardPilesAction(), new ActionListener()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent event )
            {
                removeAllCardPiles();
            }
        } );
        actionMediator_.bindActionListener( Actions.getRemoveAllCardsAction(), new ActionListener()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent event )
            {
                removeAllCards();
            }
        } );
        actionMediator_.bindActionListener( Actions.getRemoveCardAction(), new ActionListener()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent event )
            {
                removeTopCard();
            }
        } );
        actionMediator_.bindActionListener( Actions.getRemoveCardPileAction(), new ActionListener()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent event )
            {
                removeFocusedCardPile();
            }
        } );
        actionMediator_.bindActionListener( Actions.getRequestTableNetworkControlAction(), new ActionListener()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent event )
            {
                requestTableNetworkControl();
            }
        } );
        actionMediator_.bindActionListener( Actions.getResetTableOriginAction(), new ActionListener()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent event )
            {
                resetTableOrigin();
            }
        } );
        actionMediator_.bindActionListener( Actions.getSetAccordianDownCardPileLayoutAction(), setCardPileLayoutActionListener );
        actionMediator_.bindActionListener( Actions.getSetAccordianLeftCardPileLayoutAction(), setCardPileLayoutActionListener );
        actionMediator_.bindActionListener( Actions.getSetAccordianRightCardPileLayoutAction(), setCardPileLayoutActionListener );
        actionMediator_.bindActionListener( Actions.getSetAccordianUpCardPileLayoutAction(), setCardPileLayoutActionListener );
        actionMediator_.bindActionListener( Actions.getSetStackedCardPileLayoutAction(), setCardPileLayoutActionListener );

        final IPredicate<Action> hasEditableCardPilePredicate = new IPredicate<Action>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public boolean evaluate(
                @SuppressWarnings( "unused" )
                final Action obj )
            {
                if( model_.getTable().getTabletop().getComponentCount() == 0 )
                {
                    return false;
                }

                return model_.isEditable();
            }
        };
        final IPredicate<Action> hasEditableCardPredicate = new IPredicate<Action>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public boolean evaluate(
                @SuppressWarnings( "unused" )
                final Action obj )
            {
                final IContainer container = getFocusedContainer();
                if( container == null )
                {
                    return false;
                }

                if( container.getComponentCount() == 0 )
                {
                    return false;
                }

                return model_.isEditable();
            }
        };
        final IPredicate<Action> hasEditableFocusedCardPilePredicate = new IPredicate<Action>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public boolean evaluate(
                @SuppressWarnings( "unused" )
                final Action obj )
            {
                if( getFocusedContainer() == null )
                {
                    return false;
                }

                return model_.isEditable();
            }
        };
        final IPredicate<Action> isTableEditablePredicate = new IPredicate<Action>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public boolean evaluate(
                @SuppressWarnings( "unused" )
                final Action obj )
            {
                return model_.isEditable();
            }
        };
        final IPredicate<Action> isNetworkConnectedPredicate = new IPredicate<Action>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public boolean evaluate(
                @SuppressWarnings( "unused" )
                final Action obj )
            {
                return model_.getTableNetwork().isConnected();
            }
        };
        final IPredicate<Action> isNetworkDisconnectedPredicate = new IPredicate<Action>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public boolean evaluate(
                @SuppressWarnings( "unused" )
                final Action obj )
            {
                return !model_.getTableNetwork().isConnected();
            }
        };
        actionMediator_.bindShouldEnablePredicate( Actions.getAddComponentAction(), isTableEditablePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getCancelTableNetworkControlRequestAction(), new IPredicate<Action>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public boolean evaluate(
                @SuppressWarnings( "unused" )
                final Action obj )
            {
                final IPlayer localPlayer = model_.getTableNetwork().getLocalPlayer();
                return (localPlayer != null) && localPlayer.hasRole( PlayerRole.EDITOR_REQUESTER );
            }
        } );
        actionMediator_.bindShouldEnablePredicate( Actions.getDisconnectTableNetworkAction(), isNetworkConnectedPredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getFlipCardAction(), hasEditableCardPredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getGiveTableNetworkControlAction(), new IPredicate<Action>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public boolean evaluate(
                @SuppressWarnings( "unused" )
                final Action obj )
            {
                final IPlayer localPlayer = model_.getTableNetwork().getLocalPlayer();
                return (localPlayer != null) && localPlayer.hasRole( PlayerRole.EDITOR );
            }
        } );
        actionMediator_.bindShouldEnablePredicate( Actions.getHostTableNetworkAction(), isNetworkDisconnectedPredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getJoinTableNetworkAction(), isNetworkDisconnectedPredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getRemoveAllCardPilesAction(), hasEditableCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getRemoveAllCardsAction(), hasEditableCardPredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getRemoveCardAction(), hasEditableCardPredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getRemoveCardPileAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getRequestTableNetworkControlAction(), new IPredicate<Action>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public boolean evaluate(
                @SuppressWarnings( "unused" )
                final Action obj )
            {
                final IPlayer localPlayer = model_.getTableNetwork().getLocalPlayer();
                return (localPlayer != null) && !localPlayer.hasRole( PlayerRole.EDITOR ) && !localPlayer.hasRole( PlayerRole.EDITOR_REQUESTER );
            }
        } );
        actionMediator_.bindShouldEnablePredicate( Actions.getSetAccordianDownCardPileLayoutAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getSetAccordianLeftCardPileLayoutAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getSetAccordianRightCardPileLayoutAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getSetAccordianUpCardPileLayoutAction(), hasEditableFocusedCardPilePredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getSetStackedCardPileLayoutAction(), hasEditableFocusedCardPilePredicate );

        final IPredicate<Action> isCardPileLayoutSelectedPredicate = new IPredicate<Action>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public boolean evaluate(
                final Action obj )
            {
                final IContainer container = getFocusedContainer();
                if( container == null )
                {
                    return false;
                }

                return container.getLayout().getId().equals( ContainerLayoutId.fromString( (String)obj.getValue( Action.ACTION_COMMAND_KEY ) ) );
            }

        };
        actionMediator_.bindShouldSelectPredicate( Actions.getSetAccordianDownCardPileLayoutAction(), isCardPileLayoutSelectedPredicate );
        actionMediator_.bindShouldSelectPredicate( Actions.getSetAccordianLeftCardPileLayoutAction(), isCardPileLayoutSelectedPredicate );
        actionMediator_.bindShouldSelectPredicate( Actions.getSetAccordianRightCardPileLayoutAction(), isCardPileLayoutSelectedPredicate );
        actionMediator_.bindShouldSelectPredicate( Actions.getSetAccordianUpCardPileLayoutAction(), isCardPileLayoutSelectedPredicate );
        actionMediator_.bindShouldSelectPredicate( Actions.getSetStackedCardPileLayoutAction(), isCardPileLayoutSelectedPredicate );
    }

    /**
     * Cancels a previous request to take control of the table network.
     */
    private void cancelTableNetworkControlRequest()
    {
        model_.getTableNetwork().cancelControlRequest();
    }

    /**
     * Creates the background paint.
     * 
     * @return The background paint; never {@code null}.
     */
    /* @NonNull */
    private static Paint createBackgroundPaint()
    {
        final BufferedImage image = Activator.getDefault().getBundleImages().getImage( BundleImages.BACKGROUND_GREEN_FELT );
        if( image != null )
        {
            return new TexturePaint( image, new Rectangle( 0, 0, image.getWidth(), image.getHeight() ) );
        }

        return new Color( 0, 128, 0 );
    }

    /**
     * Creates a new card pile with the specified surface design.
     * 
     * @param baseDesign
     *        The card pile base surface design; must not be {@code null}.
     * 
     * @return A new card pile; never {@code null}.
     * 
     * @throws org.gamegineer.table.internal.ui.model.ModelException
     *         If an error occurs creating the card pile.
     */
    /* @NonNull */
    private IContainer createCardPile(
        /* @NonNull */
        final ComponentSurfaceDesign baseDesign )
        throws ModelException
    {
        assert baseDesign != null;

        try
        {
            final IContainer cardPile = model_.getTable().getTableEnvironment().createContainer( ComponentStrategyRegistry.getContainerStrategy( CardsComponentStrategyIds.CARD_PILE ) );
            cardPile.setSurfaceDesign( CardPileOrientation.BASE, baseDesign );
            return cardPile;
        }
        catch( final NoSuchComponentStrategyException e )
        {
            throw new ModelException( e );
        }
    }

    /**
     * Converts the specified point from the table coordinate system to the view
     * coordinate system.
     * 
     * @param point
     *        The point; must not be {@code null}.
     */
    private void convertPointFromTable(
        /* @NonNull */
        final Point point )
    {
        assert point != null;

        final Dimension originOffset = model_.getOriginOffset();
        point.translate( originOffset.width, originOffset.height );
    }

    /**
     * Converts the specified point from the view coordinate system to the table
     * coordinate system.
     * 
     * @param point
     *        The point; must not be {@code null}.
     */
    private void convertPointToTable(
        /* @NonNull */
        final Point point )
    {
        assert point != null;

        final Dimension originOffset = model_.getOriginOffset();
        point.translate( -originOffset.width, -originOffset.height );
    }

    /**
     * Converts the specified rectangle from the table coordinate system to the
     * view coordinate system.
     * 
     * @param rect
     *        The rectangle; must not be {@code null}.
     */
    private void convertRectangleFromTable(
        /* @NonNull */
        final Rectangle rect )
    {
        assert rect != null;

        final Dimension originOffset = model_.getOriginOffset();
        rect.translate( originOffset.width, originOffset.height );
    }

    /**
     * Creates a view for the tabletop and adds it to the table view.
     */
    private void createTabletopView()
    {
        assert tabletopView_ == null;

        tabletopView_ = new ContainerView( model_.getTabletopModel() );
        tabletopView_.initialize( this );
    }

    /**
     * Creates the key listener for the view.
     * 
     * @return The key listener for the view; never {@code null}.
     */
    /* @NonNull */
    private KeyListener createKeyListener()
    {
        return new KeyAdapter()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void keyReleased(
                final KeyEvent event )
            {
                if( event.getKeyCode() == KeyEvent.VK_CONTEXT_MENU )
                {
                    setMouseInputHandler( PopupMenuMouseInputHandler.class, null );
                }
            }
        };
    }

    /**
     * Creates the collection of mouse input handler singletons for the view.
     * 
     * @return The collection of mouse input handler singletons for the view;
     *         never {@code null}.
     */
    /* @NonNull */
    private Map<Class<? extends AbstractMouseInputHandler>, AbstractMouseInputHandler> createMouseInputHandlers()
    {
        final Map<Class<? extends AbstractMouseInputHandler>, AbstractMouseInputHandler> mouseInputHandlers = new HashMap<Class<? extends AbstractMouseInputHandler>, AbstractMouseInputHandler>();
        mouseInputHandlers.put( DefaultMouseInputHandler.class, new DefaultMouseInputHandler() );
        mouseInputHandlers.put( DragPrimedMouseInputHandler.class, new DragPrimedMouseInputHandler() );
        mouseInputHandlers.put( DraggingCardsMouseInputHandler.class, new DraggingCardsMouseInputHandler() );
        mouseInputHandlers.put( DraggingCardPileMouseInputHandler.class, new DraggingCardPileMouseInputHandler() );
        mouseInputHandlers.put( PanningTableMouseInputHandler.class, new PanningTableMouseInputHandler() );
        mouseInputHandlers.put( PopupMenuMouseInputHandler.class, new PopupMenuMouseInputHandler() );
        return mouseInputHandlers;
    }

    /**
     * Create the mouse input listener for the view.
     * 
     * @return The mouse input listener for the view; never {@code null}.
     */
    /* @NonNull */
    private MouseInputListener createMouseInputListener()
    {
        return new MouseInputAdapter()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void mouseDragged(
                final MouseEvent event )
            {
                mouseInputHandler_.mouseDragged( event );
            }

            @Override
            @SuppressWarnings( "synthetic-access" )
            public void mouseMoved(
                final MouseEvent event )
            {
                mouseInputHandler_.mouseMoved( event );
            }

            @Override
            @SuppressWarnings( "synthetic-access" )
            public void mousePressed(
                final MouseEvent event )
            {
                mouseInputHandler_.mousePressed( event );
            }

            @Override
            @SuppressWarnings( "synthetic-access" )
            public void mouseReleased(
                final MouseEvent event )
            {
                mouseInputHandler_.mouseReleased( event );
            }
        };
    }

    /**
     * Deletes the view associated with the tabletop and removes it from the
     * table view.
     */
    private void deleteTabletopView()
    {
        if( tabletopView_ != null )
        {
            tabletopView_.uninitialize();
            tabletopView_ = null;
        }
    }

    /**
     * Disconnects the table network.
     */
    private void disconnectTableNetwork()
    {
        ViewUtils.disconnectTableNetwork( this, model_.getTableNetwork() );
    }

    /**
     * Flips the card at the top of the focused card pile.
     */
    private void flipTopCard()
    {
        final IContainer container = getFocusedContainer();
        if( container != null )
        {
            final List<IComponent> components = container.getComponents();
            if( !components.isEmpty() )
            {
                final IComponent component = components.get( components.size() - 1 );
                component.setOrientation( component.getOrientation().inverse() );
            }
        }
    }

    /**
     * Gets the focused container.
     * 
     * @return The focused container or {@code null} if no container has the
     *         focus.
     */
    /* @Nullable */
    private IContainer getFocusedContainer()
    {
        final ContainerModel containerModel = getFocusedContainerModel();
        return (containerModel != null) ? containerModel.getComponent() : null;
    }

    /**
     * Gets the model associated with the focused container.
     * 
     * @return The model associated with the focused container or {@code null}
     *         if no container has the focus.
     */
    /* @Nullable */
    private ContainerModel getFocusedContainerModel()
    {
        final ComponentModel componentModel = model_.getFocusedComponentModel();
        return (componentModel instanceof ContainerModel) ? (ContainerModel)componentModel : null;
    }

    /**
     * Gets the mouse location in table coordinates.
     * 
     * @return The mouse location in table coordinates; never {@code null}.
     */
    /* @NonNull */
    private Point getMouseLocation()
    {
        final Point location = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen( location, this );
        convertPointToTable( location );
        return location;
    }

    /**
     * Gives control of the table network to another player.
     */
    private void giveTableNetworkControl()
    {
        final SelectRemotePlayerDialog dialog = new SelectRemotePlayerDialog( JOptionPane.getFrameForComponent( this ), model_ );
        if( dialog.open() == WindowConstants.RETURN_CODE_OK )
        {
            final IPlayer player = dialog.getSelectedPlayer();
            if( player != null )
            {
                model_.getTableNetwork().giveControl( player );
            }
        }
    }

    /**
     * Hosts a new table network.
     */
    private void hostTableNetwork()
    {
        final IWizard wizard = new HostTableNetworkWizard( model_ );
        final WizardDialog dialog = new WizardDialog( JOptionPane.getFrameForComponent( this ), wizard );
        dialog.open();
    }

    /**
     * Initializes this component.
     */
    private void initializeComponent()
    {
        setLayout( null );
        setOpaque( true );
    }

    /*
     * @see java.awt.Component#isFocusable()
     */
    @Override
    public boolean isFocusable()
    {
        return true;
    }

    /**
     * Joins an existing table network.
     */
    private void joinTableNetwork()
    {
        final IWizard wizard = new JoinTableNetworkWizard( model_ );
        final WizardDialog dialog = new WizardDialog( JOptionPane.getFrameForComponent( this ), wizard );
        dialog.open();
    }

    /*
     * @see javax.swing.JComponent#paintChildren(java.awt.Graphics)
     */
    @Override
    protected void paintChildren(
        final Graphics g )
    {
        super.paintChildren( g );

        final Graphics2D g2d = (Graphics2D)g;

        final Dimension originOffset = model_.getOriginOffset();
        g.translate( originOffset.width, originOffset.height );

        final Rectangle clipBounds = g.getClipBounds();

        g2d.setPaint( backgroundPaint_ );
        g2d.fillRect( -originOffset.width, -originOffset.height, getWidth(), getHeight() );

        if( tabletopView_ != null )
        {
            if( clipBounds.intersects( tabletopView_.getBounds() ) )
            {
                tabletopView_.paint( this, g );
            }
        }

        final ComponentModel hoveredComponentModel = model_.getHoveredComponentModel();
        final ComponentModel focusedComponentModel = model_.getFocusedComponentModel();
        if( (hoveredComponentModel != null) && (hoveredComponentModel != focusedComponentModel) )
        {
            paintComponentBorder( g, hoveredComponentModel, Color.YELLOW );
        }
        if( focusedComponentModel != null )
        {
            paintComponentBorder( g, focusedComponentModel, Color.GREEN );
        }
    }

    /**
     * Paints a border around the specified component.
     * 
     * @param g
     *        The graphics context in which to paint; must not be {@code null}.
     * @param componentModel
     *        The component model; must not be {@code null}.
     * @param color
     *        The border color; must not be {@code null}.
     */
    private static void paintComponentBorder(
        /* @NonNull */
        final Graphics g,
        /* @NonNull */
        final ComponentModel componentModel,
        /* @NonNull */
        final Color color )
    {
        assert g != null;
        assert componentModel != null;
        assert color != null;

        final Rectangle componentBounds = componentModel.getComponent().getBounds();
        final Color oldColor = g.getColor();
        g.setColor( color );
        g.drawRect( //
            componentBounds.x - ComponentView.HORIZONTAL_PADDING, //
            componentBounds.y - ComponentView.VERTICAL_PADDING, //
            componentBounds.width + (2 * ComponentView.HORIZONTAL_PADDING) - 1, //
            componentBounds.height + (2 * ComponentView.VERTICAL_PADDING) - 1 );
        g.setColor( oldColor );
    }

    /**
     * Removes all card piles from the table.
     */
    private void removeAllCardPiles()
    {
        model_.getTable().getTabletop().removeAllComponents();
    }

    /**
     * Removes all cards from the focused card pile.
     */
    private void removeAllCards()
    {
        final IContainer container = getFocusedContainer();
        if( container != null )
        {
            container.removeAllComponents();
        }
    }

    /**
     * Removes the focused card pile from the table.
     */
    private void removeFocusedCardPile()
    {
        final IContainer container = getFocusedContainer();
        if( container != null )
        {
            model_.getTable().getTabletop().removeComponent( container );
        }
    }

    /**
     * Removes the card at the top of the focused card pile.
     */
    private void removeTopCard()
    {
        final IContainer container = getFocusedContainer();
        if( container != null )
        {
            container.removeTopComponent();
        }
    }

    /*
     * @see javax.swing.JComponent#removeNotify()
     */
    @Override
    public void removeNotify()
    {
        deleteTabletopView();

        removeMouseMotionListener( mouseInputListener_ );
        removeMouseListener( mouseInputListener_ );
        removeKeyListener( keyListener_ );
        model_.removeTableModelListener( tableModelListener_ );
        tableModelListener_ = null;
        actionMediator_.unbindAll();

        super.removeNotify();
    }

    /**
     * Repaints the specified region of the table.
     * 
     * @param region
     *        The region of the table to repaint in table coordinates.
     */
    void repaintTable(
        /* @NonNull */
        final Rectangle region )
    {
        assert region != null;

        final Rectangle viewRegion = new Rectangle( region );
        convertRectangleFromTable( viewRegion );
        repaint( viewRegion );
    }

    /**
     * Requests control of the table network.
     */
    private void requestTableNetworkControl()
    {
        model_.getTableNetwork().requestControl();
    }

    /**
     * Resets the table origin to the view origin.
     */
    private void resetTableOrigin()
    {
        model_.setOriginOffset( new Dimension( 0, 0 ) );
    }

    /**
     * Sets the layout of the focused card pile to the specified value.
     * 
     * @param layoutId
     *        The identifier of the card pile layout; must not be {@code null}.
     */
    private void setCardPileLayout(
        /* @NonNull */
        final ContainerLayoutId layoutId )
    {
        assert layoutId != null;

        final IContainer container = getFocusedContainer();
        if( container != null )
        {
            try
            {
                container.setLayout( ContainerLayoutRegistry.getContainerLayout( layoutId ) );
            }
            catch( final NoSuchContainerLayoutException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.TableView_setCardPileLayout_error, e );
            }
        }
    }

    /**
     * Sets the current mouse input handler
     * 
     * @param handlerClass
     *        The class of the mouse input handler to activate; must not be
     *        {@code null}.
     * @param event
     *        The mouse event that triggered activation of the mouse input
     *        handler; may be {@code null} if no mouse event triggered the
     *        activation.
     */
    private void setMouseInputHandler(
        /* @NonNUll */
        final Class<? extends AbstractMouseInputHandler> handlerClass,
        /* @Nullable */
        final MouseEvent event )
    {
        assert handlerClass != null;

        mouseInputHandler_.deactivate();
        mouseInputHandler_ = mouseInputHandlers_.get( handlerClass );
        assert mouseInputHandler_ != null;
        mouseInputHandler_.activate( event );
    }

    /**
     * Invoked when the table model origin offset has changed.
     */
    private void tableModelOriginOffsetChanged()
    {
        repaint();
    }


    // ======================================================================
    // Nested Classes
    // ======================================================================

    /**
     * Superclass for all mouse input handlers.
     * 
     * <p>
     * Instances of this class represent the State participant of the State
     * pattern.
     * </p>
     */
    @Immutable
    private abstract class AbstractMouseInputHandler
        extends MouseInputAdapter
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code AbstractMouseInputHandler}
         * class.
         */
        protected AbstractMouseInputHandler()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Activates this handler.
         * 
         * @param event
         *        The mouse event that triggered activation of this handler; may
         *        be {@code null} if no mouse event triggered the activation.
         */
        void activate(
            /* @NonNull */
            @SuppressWarnings( "unused" )
            final MouseEvent event )
        {
            // default implementation does nothing
        }

        /**
         * Deactivates this handler.
         */
        void deactivate()
        {
            // default implementation does nothing
        }

        /**
         * Gets the mouse location in table coordinates associated with the
         * specified mouse event or the current mouse location if no mouse event
         * is available.
         * 
         * @param event
         *        The mouse event; may be {@code null} if no mouse event is
         *        available.
         * 
         * @return The mouse location in table coordinates; never {@code null}.
         */
        /* @NonNull */
        @SuppressWarnings( "synthetic-access" )
        protected final Point getMouseLocation(
            /* @Nullable */
            final MouseEvent event )
        {
            if( event != null )
            {
                final Point location = event.getPoint();
                convertPointToTable( location );
                return location;
            }

            return TableView.this.getMouseLocation();
        }

        /**
         * Subclasses may override but must call the superclass implementation.
         * 
         * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void mouseReleased(
            @SuppressWarnings( "unused" )
            final MouseEvent event )
        {
            updateFocusOnMouseReleased_ = false;
        }
    }

    /**
     * The default mouse input handler.
     */
    @Immutable
    private final class DefaultMouseInputHandler
        extends AbstractMouseInputHandler
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code DefaultMouseInputHandler}
         * class.
         */
        DefaultMouseInputHandler()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.internal.ui.view.TableView.AbstractMouseInputHandler#activate(java.awt.event.MouseEvent)
         */
        @Override
        void activate(
            final MouseEvent event )
        {
            updateHover( event );
            updateCursor( event );
        }

        /*
         * @see java.awt.event.MouseAdapter#mouseMoved(java.awt.event.MouseEvent)
         */
        @Override
        public void mouseMoved(
            final MouseEvent event )
        {
            updateHover( event );
            updateCursor( event );
        }

        /*
         * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void mousePressed(
            final MouseEvent event )
        {
            updateFocus( event );

            if( event.isPopupTrigger() )
            {
                setMouseInputHandler( PopupMenuMouseInputHandler.class, event );
            }
            else if( SwingUtilities.isLeftMouseButton( event ) )
            {
                if( model_.getFocusedComponent() != null )
                {
                    setMouseInputHandler( DragPrimedMouseInputHandler.class, event );
                }
            }
            else if( SwingUtilities.isMiddleMouseButton( event ) )
            {
                setMouseInputHandler( PanningTableMouseInputHandler.class, event );
            }
        }

        /*
         * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void mouseReleased(
            final MouseEvent event )
        {
            if( event.isPopupTrigger() )
            {
                setMouseInputHandler( PopupMenuMouseInputHandler.class, event );
            }

            super.mouseReleased( event );
        }

        /**
         * Updates the mouse cursor.
         * 
         * @param event
         *        The mouse event; may be {@code null} if no mouse event is
         *        available.
         */
        @SuppressWarnings( "synthetic-access" )
        private void updateCursor(
            /* @Nullable */
            final MouseEvent event )
        {
            final IComponent component = model_.getFocusableComponent( getMouseLocation( event ) );
            final Cursor newCursor;
            if( component != null )
            {
                newCursor = model_.isEditable() ? Cursors.getDefaultCursor() : Cursors.getInvalidCursor();
            }
            else
            {
                newCursor = Cursors.getDefaultCursor();
            }

            if( newCursor != getCursor() )
            {
                setCursor( newCursor );
            }
        }

        /**
         * Updates the focus.
         * 
         * @param event
         *        The mouse event; must not be {@code null}.
         */
        @SuppressWarnings( "synthetic-access" )
        private void updateFocus(
            /* @NonNull */
            final MouseEvent event )
        {
            assert event != null;

            if( SwingUtilities.isLeftMouseButton( event ) || SwingUtilities.isRightMouseButton( event ) )
            {
                final Point mouseLocation = getMouseLocation( event );
                final IComponent focusedComponent = model_.getFocusedComponent();
                if( (focusedComponent != null) && focusedComponent.getBounds().contains( mouseLocation ) )
                {
                    updateFocusOnMouseReleased_ = SwingUtilities.isLeftMouseButton( event );
                }
                else
                {
                    model_.setFocus( model_.getFocusableComponent( mouseLocation ) );
                }
            }
        }

        /**
         * Updates the hover.
         * 
         * @param event
         *        The mouse event; may be {@code null} if no mouse event is
         *        available.
         */
        @SuppressWarnings( "synthetic-access" )
        private void updateHover(
            /* @Nullable */
            final MouseEvent event )
        {
            model_.setHover( model_.getFocusableComponent( getMouseLocation( event ), model_.getFocusedComponent() ) );
        }
    }

    /**
     * The mouse input handler that is active when a drag operation is primed
     * but has not yet begun.
     */
    @NotThreadSafe
    private final class DragPrimedMouseInputHandler
        extends AbstractMouseInputHandler
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /**
         * The number of pixels the mouse can move in any direction before a
         * drag operation begins.
         */
        private final int DRAG_THRESHOLD = DragSource.getDragThreshold();

        /**
         * The mouse location when this handler was activated in table
         * coordinates.
         */
        private final Point originalLocation_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code DragPrimedMouseInputHandler}
         * class.
         */
        DragPrimedMouseInputHandler()
        {
            originalLocation_ = new Point( 0, 0 );
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.internal.ui.view.TableView.AbstractMouseInputHandler#activate(java.awt.event.MouseEvent)
         */
        @Override
        void activate(
            final MouseEvent event )
        {
            originalLocation_.setLocation( getMouseLocation( event ) );
        }

        /**
         * Indicates the specified mouse location is sufficiently distant from
         * the original mouse location to begin a drag operation.
         * 
         * @param location
         *        The mouse location; must not be {@code null}.
         * 
         * @return {@code true} if the drag operation can begin; otherwise
         *         {@code false}.
         */
        private boolean canBeginDrag(
            /* @NonNUll */
            final Point location )
        {
            assert location != null;

            final Rectangle rect = new Rectangle( originalLocation_ );
            rect.grow( DRAG_THRESHOLD, DRAG_THRESHOLD );
            return !rect.contains( location );
        }

        /*
         * @see org.gamegineer.table.internal.ui.view.TableView.AbstractMouseInputHandler#deactivate()
         */
        @Override
        void deactivate()
        {
            originalLocation_.setLocation( 0, 0 );
        }

        /*
         * @see java.awt.event.MouseAdapter#mouseDragged(java.awt.event.MouseEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void mouseDragged(
            final MouseEvent event )
        {
            final Point location = getMouseLocation( event );
            if( canBeginDrag( location ) )
            {
                if( model_.getFocusableComponent( location ) != null )
                {
                    if( model_.isEditable() )
                    {
                        if( (event.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) == InputEvent.CTRL_DOWN_MASK )
                        {
                            setMouseInputHandler( DraggingCardPileMouseInputHandler.class, event );
                        }
                        else
                        {
                            setMouseInputHandler( DraggingCardsMouseInputHandler.class, event );
                        }
                    }
                    else
                    {
                        setMouseInputHandler( DefaultMouseInputHandler.class, event );
                    }
                }
                else
                {
                    setMouseInputHandler( DefaultMouseInputHandler.class, event );
                }
            }
        }

        /*
         * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void mouseReleased(
            final MouseEvent event )
        {
            if( updateFocusOnMouseReleased_ )
            {
                updateFocus( event );
            }

            if( SwingUtilities.isLeftMouseButton( event ) )
            {
                setMouseInputHandler( DefaultMouseInputHandler.class, null );
            }

            super.mouseReleased( event );
        }

        /**
         * Updates the focus.
         * 
         * @param event
         *        The mouse event; must not be {@code null}.
         */
        @SuppressWarnings( "synthetic-access" )
        private void updateFocus(
            /* @NonNull */
            final MouseEvent event )
        {
            assert event != null;

            if( SwingUtilities.isLeftMouseButton( event ) )
            {
                model_.setFocus( model_.getFocusableComponent( getMouseLocation( event ), model_.getFocusedComponent() ) );
            }
        }
    }

    /**
     * The mouse input handler that is active when a collection of cards are
     * being dragged.
     */
    @NotThreadSafe
    private final class DraggingCardsMouseInputHandler
        extends AbstractMouseInputHandler
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The card pile that temporarily holds the cards being dragged. */
        private IContainer mobileCardPile_;

        /**
         * The offset between the mouse pointer and the mobile card pile origin.
         */
        private final Dimension mobileCardPileOriginOffset_;

        /** The card pile that is the source of the cards being dragged. */
        private IContainer sourceCardPile_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the
         * {@code DraggingCardsMouseInputHandler} class.
         */
        DraggingCardsMouseInputHandler()
        {
            mobileCardPileOriginOffset_ = new Dimension( 0, 0 );
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.internal.ui.view.TableView.AbstractMouseInputHandler#activate(java.awt.event.MouseEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        void activate(
            final MouseEvent event )
        {
            sourceCardPile_ = getFocusedContainer();
            if( sourceCardPile_ != null )
            {
                final Point mouseLocation = getMouseLocation( event );
                final List<IComponent> draggedComponents = sourceCardPile_.removeComponents( mouseLocation );
                if( !draggedComponents.isEmpty() )
                {
                    try
                    {
                        final Point draggedCardsOrigin = draggedComponents.get( 0 ).getLocation();
                        mobileCardPileOriginOffset_.setSize( draggedCardsOrigin.x - mouseLocation.x, draggedCardsOrigin.y - mouseLocation.y );
                        mobileCardPile_ = createCardPile( sourceCardPile_.getSurfaceDesign( CardPileOrientation.BASE ) );
                        mobileCardPile_.setOrigin( draggedCardsOrigin );
                        mobileCardPile_.setLayout( sourceCardPile_.getLayout() );
                        model_.getTable().getTabletop().addComponent( mobileCardPile_ );
                        mobileCardPile_.addComponents( draggedComponents );
                        model_.setHover( mobileCardPile_ );
                        model_.setFocus( mobileCardPile_ );
                    }
                    catch( final ModelException e )
                    {
                        Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.TableView_draggingCards_error, e );
                    }
                }
                else
                {
                    setMouseInputHandler( DefaultMouseInputHandler.class, null );
                }
            }
            else
            {
                setMouseInputHandler( DefaultMouseInputHandler.class, null );
            }
        }

        /*
         * @see org.gamegineer.table.internal.ui.view.TableView.AbstractMouseInputHandler#deactivate()
         */
        @Override
        void deactivate()
        {
            mobileCardPile_ = null;
            mobileCardPileOriginOffset_.setSize( 0, 0 );
            sourceCardPile_ = null;
        }

        /*
         * @see java.awt.event.MouseAdapter#mouseDragged(java.awt.event.MouseEvent)
         */
        @Override
        public void mouseDragged(
            final MouseEvent event )
        {
            final Point location = getMouseLocation( event );
            location.translate( mobileCardPileOriginOffset_.width, mobileCardPileOriginOffset_.height );
            mobileCardPile_.setOrigin( location );
        }

        /*
         * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void mouseReleased(
            final MouseEvent event )
        {
            if( SwingUtilities.isLeftMouseButton( event ) )
            {
                model_.getTable().getTabletop().removeComponent( mobileCardPile_ );

                final Point mouseLocation = getMouseLocation( event );
                final IContainer cardPile = model_.getFocusableContainer( mouseLocation );
                final IContainer targetCardPile = (cardPile != null) ? cardPile : sourceCardPile_;
                targetCardPile.addComponents( mobileCardPile_.removeAllComponents() );
                model_.setHover( targetCardPile );
                model_.setFocus( targetCardPile );

                setMouseInputHandler( DefaultMouseInputHandler.class, null );
            }

            super.mouseReleased( event );
        }
    }

    /**
     * The mouse input handler that is active when a card pile is being dragged.
     */
    @NotThreadSafe
    private final class DraggingCardPileMouseInputHandler
        extends AbstractMouseInputHandler
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The card pile being dragged. */
        private IContainer draggedCardPile_;

        /**
         * The offset between the mouse pointer and the dragged card pile
         * location.
         */
        private final Dimension draggedCardPileLocationOffset_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the
         * {@code DraggingCardPileMouseInputHandler} class.
         */
        DraggingCardPileMouseInputHandler()
        {
            draggedCardPileLocationOffset_ = new Dimension( 0, 0 );
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.internal.ui.view.TableView.AbstractMouseInputHandler#activate(java.awt.event.MouseEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        void activate(
            final MouseEvent event )
        {
            draggedCardPile_ = getFocusedContainer();
            if( draggedCardPile_ != null )
            {
                final Point cardPileLocation = draggedCardPile_.getLocation();
                final Point mouseLocation = getMouseLocation( event );
                draggedCardPileLocationOffset_.setSize( cardPileLocation.x - mouseLocation.x, cardPileLocation.y - mouseLocation.y );
            }
            else
            {
                setMouseInputHandler( DefaultMouseInputHandler.class, null );
            }
        }

        /*
         * @see org.gamegineer.table.internal.ui.view.TableView.AbstractMouseInputHandler#deactivate()
         */
        @Override
        void deactivate()
        {
            draggedCardPile_ = null;
            draggedCardPileLocationOffset_.setSize( 0, 0 );
        }

        /*
         * @see java.awt.event.MouseAdapter#mouseDragged(java.awt.event.MouseEvent)
         */
        @Override
        public void mouseDragged(
            final MouseEvent event )
        {
            final Point location = getMouseLocation( event );
            location.translate( draggedCardPileLocationOffset_.width, draggedCardPileLocationOffset_.height );
            draggedCardPile_.setLocation( location );
        }

        /*
         * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void mouseReleased(
            final MouseEvent event )
        {
            if( SwingUtilities.isLeftMouseButton( event ) )
            {
                setMouseInputHandler( DefaultMouseInputHandler.class, null );
            }

            super.mouseReleased( event );
        }
    }

    /**
     * The mouse input handler that is active when the table is being panned.
     */
    @NotThreadSafe
    private final class PanningTableMouseInputHandler
        extends AbstractMouseInputHandler
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /**
         * The mouse location when this handler was activated in view
         * coordinates.
         */
        private final Point originalLocation_;

        /** The original origin offset. */
        private final Dimension originalOriginOffset_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the
         * {@code PanningTableMouseInputHandler} class.
         */
        PanningTableMouseInputHandler()
        {
            originalLocation_ = new Point( 0, 0 );
            originalOriginOffset_ = new Dimension( 0, 0 );
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.internal.ui.view.TableView.AbstractMouseInputHandler#activate(java.awt.event.MouseEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        void activate(
            final MouseEvent event )
        {
            final Point location = getMouseLocation( event );
            convertPointFromTable( location );
            originalLocation_.setLocation( location );
            originalOriginOffset_.setSize( model_.getOriginOffset() );

            setCursor( Cursors.getGrabCursor() );
        }

        /*
         * @see org.gamegineer.table.internal.ui.view.TableView.AbstractMouseInputHandler#deactivate()
         */
        @Override
        void deactivate()
        {
            originalLocation_.setLocation( 0, 0 );
            originalOriginOffset_.setSize( 0, 0 );
        }

        /*
         * @see java.awt.event.MouseAdapter#mouseDragged(java.awt.event.MouseEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void mouseDragged(
            final MouseEvent event )
        {
            final Point location = getMouseLocation( event );
            convertPointFromTable( location );
            final Dimension originOffset = new Dimension( originalOriginOffset_.width + (location.x - originalLocation_.x), originalOriginOffset_.height + (location.y - originalLocation_.y) );
            model_.setOriginOffset( originOffset );
        }

        /*
         * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void mouseReleased(
            final MouseEvent event )
        {
            if( SwingUtilities.isMiddleMouseButton( event ) )
            {
                setMouseInputHandler( DefaultMouseInputHandler.class, null );
            }

            super.mouseReleased( event );
        }
    }

    /**
     * The mouse input handler that is active when a popup menu is visible.
     */
    @Immutable
    private final class PopupMenuMouseInputHandler
        extends AbstractMouseInputHandler
        implements PopupMenuListener
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code PopupMenuMouseInputHandler}
         * class.
         */
        PopupMenuMouseInputHandler()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.internal.ui.view.TableView.AbstractMouseInputHandler#activate(java.awt.event.MouseEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        void activate(
            final MouseEvent event )
        {
            final Point location = getMouseLocation( event );
            final JPopupMenu menu = getPopupMenu( location );
            menu.addPopupMenuListener( this );
            convertPointFromTable( location );
            menu.show( TableView.this, location.x, location.y );
        }

        /**
         * Gets the popup menu associated with the specified location.
         * 
         * @param location
         *        The location; must not be {@code null}.
         * 
         * @return The popup menu associated with the specified location; never
         *         {@code null}.
         */
        /* @NonNull */
        @SuppressWarnings( "synthetic-access" )
        private JPopupMenu getPopupMenu(
            /* @NonNull */
            final Point location )
        {
            assert location != null;

            final ContainerModel containerModel = getFocusedContainerModel();
            if( containerModel != null )
            {
                return new CardPilePopupMenu( containerModel );
            }

            return new TablePopupMenu( model_ );
        }

        /*
         * @see javax.swing.event.PopupMenuListener#popupMenuCanceled(javax.swing.event.PopupMenuEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void popupMenuCanceled(
            @SuppressWarnings( "unused" )
            final PopupMenuEvent event )
        {
            setMouseInputHandler( DefaultMouseInputHandler.class, null );
        }

        /*
         * @see javax.swing.event.PopupMenuListener#popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void popupMenuWillBecomeInvisible(
            @SuppressWarnings( "unused" )
            final PopupMenuEvent event )
        {
            setMouseInputHandler( DefaultMouseInputHandler.class, null );
        }

        /*
         * @see javax.swing.event.PopupMenuListener#popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent)
         */
        @Override
        public void popupMenuWillBecomeVisible(
            @SuppressWarnings( "unused" )
            final PopupMenuEvent event )
        {
            // do nothing
        }
    }

    /**
     * A table model listener for the table view.
     */
    @Immutable
    private final class TableModelListener
        extends org.gamegineer.table.internal.ui.model.TableModelListener
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code TableModelListener} class.
         */
        TableModelListener()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.internal.ui.model.TableModelListener#tableModelOriginOffsetChanged(org.gamegineer.table.internal.ui.model.TableModelEvent)
         */
        @Override
        public void tableModelOriginOffsetChanged(
            final TableModelEvent event )
        {
            assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

            SwingUtilities.invokeLater( new Runnable()
            {
                @Override
                @SuppressWarnings( "synthetic-access" )
                public void run()
                {
                    TableView.this.tableModelOriginOffsetChanged();
                }
            } );
        }
    }
}
