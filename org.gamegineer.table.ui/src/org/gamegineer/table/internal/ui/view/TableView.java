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
import java.util.concurrent.locks.Lock;
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
import org.gamegineer.common.core.util.IPredicate;
import org.gamegineer.common.ui.window.WindowConstants;
import org.gamegineer.common.ui.wizard.IWizard;
import org.gamegineer.common.ui.wizard.WizardDialog;
import org.gamegineer.table.core.ContainerLayoutId;
import org.gamegineer.table.core.ContainerLayoutRegistry;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.NoSuchContainerLayoutException;
import org.gamegineer.table.core.dnd.IDragContext;
import org.gamegineer.table.core.dnd.IDragSource;
import org.gamegineer.table.internal.ui.Activator;
import org.gamegineer.table.internal.ui.BundleImages;
import org.gamegineer.table.internal.ui.Loggers;
import org.gamegineer.table.internal.ui.action.ActionMediator;
import org.gamegineer.table.internal.ui.dialogs.selectremoteplayer.SelectRemotePlayerDialog;
import org.gamegineer.table.internal.ui.model.ComponentAxis;
import org.gamegineer.table.internal.ui.model.ComponentModel;
import org.gamegineer.table.internal.ui.model.ComponentModelVector;
import org.gamegineer.table.internal.ui.model.ContainerModel;
import org.gamegineer.table.internal.ui.model.ITableModelListener;
import org.gamegineer.table.internal.ui.model.TableModel;
import org.gamegineer.table.internal.ui.model.TableModelEvent;
import org.gamegineer.table.internal.ui.prototype.ComponentPrototypeUtils;
import org.gamegineer.table.internal.ui.wizards.hosttablenetwork.HostTableNetworkWizard;
import org.gamegineer.table.internal.ui.wizards.jointablenetwork.JoinTableNetworkWizard;
import org.gamegineer.table.net.IPlayer;
import org.gamegineer.table.net.PlayerRole;
import org.gamegineer.table.ui.prototype.ComponentPrototypeFactoryException;
import org.gamegineer.table.ui.prototype.IComponentPrototypeFactory;

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

    /**
     * The amount of padding between a component and any border drawn around it
     * in table dimensions.
     */
    private static final Dimension PADDING = new Dimension( 2, 2 );

    /** Serializable class version number. */
    private static final long serialVersionUID = 3574703230407179091L;

    /** The action mediator. */
    private final ActionMediator actionMediator_;

    /** The background paint. */
    private final Paint backgroundPaint_;

    /** The current input handler. */
    private AbstractInputHandler inputHandler_;

    /** The collection of input handler singletons. */
    private final Map<Class<? extends AbstractInputHandler>, AbstractInputHandler> inputHandlers_;

    /** The key listener for this view. */
    private final KeyListener keyListener_;

    /** The model associated with this view. */
    private final TableModel model_;

    /** The mouse input listener for this view. */
    private final MouseInputListener mouseInputListener_;

    /** The table model listener for this view. */
    private ITableModelListener tableModelListener_;

    /** The tabletop view. */
    private ContainerView tabletopView_;

    /**
     * Indicates the active input handler should update the focus in the Mouse
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
        inputHandlers_ = createInputHandlers();
        inputHandler_ = inputHandlers_.get( DefaultInputHandler.class );
        keyListener_ = createKeyListener();
        model_ = model;
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
            getTableEnvironmentLock().lock();
            try
            {
                final List<IComponent> components = componentPrototypeFactory.createComponentPrototype( model_.getTable().getTableEnvironment() );

                final IComponent focusedComponent = getFocusedComponent();
                if( focusedComponent != null )
                {
                    if( focusedComponent instanceof IContainer )
                    {
                        ((IContainer)focusedComponent).addComponents( components );
                    }
                    else
                    {
                        focusedComponent.getContainer().addComponents( components );
                    }
                }
                else
                {
                    final Point location = getMouseLocation();
                    convertPointFromTable( location );
                    final Dimension tableSize = getSize();
                    for( final IComponent component : components )
                    {
                        final Dimension componentSize = component.getSize();
                        if( location.x < 0 )
                        {
                            location.x = 0;
                        }
                        else if( location.x + componentSize.width > tableSize.width )
                        {
                            location.x = tableSize.width - componentSize.width;
                        }
                        if( location.y < 0 )
                        {
                            location.y = 0;
                        }
                        else if( location.y + componentSize.height > tableSize.height )
                        {
                            location.y = tableSize.height - componentSize.height;
                        }
                        convertPointToTable( location );
                        component.setLocation( location );
                    }

                    model_.getTable().getTabletop().addComponents( components );
                }
            }
            finally
            {
                getTableEnvironmentLock().unlock();
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
        final ActionListener setContainerLayoutActionListener = new ActionListener()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                final ActionEvent event )
            {
                setContainerLayout( ContainerLayoutId.fromString( event.getActionCommand() ) );
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
        actionMediator_.bindActionListener( Actions.getFlipComponentAction(), new ActionListener()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent event )
            {
                flipComponent();
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
        actionMediator_.bindActionListener( Actions.getRemoveAllComponentsAction(), new ActionListener()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent event )
            {
                removeAllComponents();
            }
        } );
        actionMediator_.bindActionListener( Actions.getRemoveComponentAction(), new ActionListener()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void actionPerformed(
                @SuppressWarnings( "unused" )
                final ActionEvent event )
            {
                removeComponent();
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
        actionMediator_.bindActionListener( Actions.getSetAccordianDownContainerLayoutAction(), setContainerLayoutActionListener );
        actionMediator_.bindActionListener( Actions.getSetAccordianLeftContainerLayoutAction(), setContainerLayoutActionListener );
        actionMediator_.bindActionListener( Actions.getSetAccordianRightContainerLayoutAction(), setContainerLayoutActionListener );
        actionMediator_.bindActionListener( Actions.getSetAccordianUpContainerLayoutAction(), setContainerLayoutActionListener );
        actionMediator_.bindActionListener( Actions.getSetStackedContainerLayoutAction(), setContainerLayoutActionListener );

        final IPredicate<Action> hasEditableFocusedComponentPredicate = new IPredicate<Action>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public boolean evaluate(
                @SuppressWarnings( "unused" )
                final Action obj )
            {
                if( getFocusedComponent() == null )
                {
                    return false;
                }

                return model_.isEditable();
            }
        };
        final IPredicate<Action> hasEditableFocusedContainerPredicate = new IPredicate<Action>()
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
        final IPredicate<Action> hasEditableFocusedNonEmptyContainerPredicate = new IPredicate<Action>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public boolean evaluate(
                @SuppressWarnings( "unused" )
                final Action obj )
            {
                final IContainer container;
                final IComponent component = getFocusedComponent();
                if( component != null )
                {
                    container = (component instanceof IContainer) ? (IContainer)component : null;
                }
                else
                {
                    container = model_.getTable().getTabletop();
                }

                if( (container == null) || (container.getComponentCount() == 0) )
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
        actionMediator_.bindShouldEnablePredicate( Actions.getFlipComponentAction(), hasEditableFocusedComponentPredicate );
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
        actionMediator_.bindShouldEnablePredicate( Actions.getRemoveAllComponentsAction(), hasEditableFocusedNonEmptyContainerPredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getRemoveComponentAction(), hasEditableFocusedComponentPredicate );
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
        actionMediator_.bindShouldEnablePredicate( Actions.getSetAccordianDownContainerLayoutAction(), hasEditableFocusedContainerPredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getSetAccordianLeftContainerLayoutAction(), hasEditableFocusedContainerPredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getSetAccordianRightContainerLayoutAction(), hasEditableFocusedContainerPredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getSetAccordianUpContainerLayoutAction(), hasEditableFocusedContainerPredicate );
        actionMediator_.bindShouldEnablePredicate( Actions.getSetStackedContainerLayoutAction(), hasEditableFocusedContainerPredicate );

        final IPredicate<Action> isContainerLayoutSelectedPredicate = new IPredicate<Action>()
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
        actionMediator_.bindShouldSelectPredicate( Actions.getSetAccordianDownContainerLayoutAction(), isContainerLayoutSelectedPredicate );
        actionMediator_.bindShouldSelectPredicate( Actions.getSetAccordianLeftContainerLayoutAction(), isContainerLayoutSelectedPredicate );
        actionMediator_.bindShouldSelectPredicate( Actions.getSetAccordianRightContainerLayoutAction(), isContainerLayoutSelectedPredicate );
        actionMediator_.bindShouldSelectPredicate( Actions.getSetAccordianUpContainerLayoutAction(), isContainerLayoutSelectedPredicate );
        actionMediator_.bindShouldSelectPredicate( Actions.getSetStackedContainerLayoutAction(), isContainerLayoutSelectedPredicate );
    }

    /**
     * Cancels a previous request to take control of the table network.
     */
    private void cancelTableNetworkControlRequest()
    {
        model_.getTableNetwork().cancelControlRequest();
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
     * Creates the collection of input handler singletons for the view.
     * 
     * @return The collection of input handler singletons for the view; never
     *         {@code null}.
     */
    /* @NonNull */
    private Map<Class<? extends AbstractInputHandler>, AbstractInputHandler> createInputHandlers()
    {
        final Map<Class<? extends AbstractInputHandler>, AbstractInputHandler> inputHandlers = new HashMap<Class<? extends AbstractInputHandler>, AbstractInputHandler>();
        inputHandlers.put( DefaultInputHandler.class, new DefaultInputHandler() );
        inputHandlers.put( DragPrimedInputHandler.class, new DragPrimedInputHandler() );
        inputHandlers.put( DraggingComponentInputHandler.class, new DraggingComponentInputHandler() );
        inputHandlers.put( PanningTableInputHandler.class, new PanningTableInputHandler() );
        inputHandlers.put( PopupMenuInputHandler.class, new PopupMenuInputHandler() );
        return inputHandlers;
    }

    /**
     * Creates the key listener for the view.
     * 
     * @return The key listener for the view; never {@code null}.
     */
    /* @NonNull */
    private KeyListener createKeyListener()
    {
        @SuppressWarnings( "synthetic-access" )
        final KeyListener keyListener = new KeyAdapter()
        {
            @Override
            public void keyPressed(
                final KeyEvent event )
            {
                inputHandler_.keyPressed( event );
            }

            @Override
            public void keyReleased(
                final KeyEvent event )
            {
                if( event.getKeyCode() == KeyEvent.VK_CONTEXT_MENU )
                {
                    setInputHandler( PopupMenuInputHandler.class, event );
                }

                inputHandler_.keyReleased( event );
            }

            @Override
            public void keyTyped(
                final KeyEvent event )
            {
                inputHandler_.keyTyped( event );
            }
        };
        return keyListener;
    }

    /**
     * Create the mouse input listener for the view.
     * 
     * @return The mouse input listener for the view; never {@code null}.
     */
    /* @NonNull */
    private MouseInputListener createMouseInputListener()
    {
        @SuppressWarnings( "synthetic-access" )
        final MouseInputListener mouseInputListener = new MouseInputAdapter()
        {
            @Override
            public void mouseDragged(
                final MouseEvent event )
            {
                inputHandler_.mouseDragged( event );
            }

            @Override
            public void mouseMoved(
                final MouseEvent event )
            {
                inputHandler_.mouseMoved( event );
            }

            @Override
            public void mousePressed(
                final MouseEvent event )
            {
                inputHandler_.mousePressed( event );
            }

            @Override
            public void mouseReleased(
                final MouseEvent event )
            {
                inputHandler_.mouseReleased( event );
            }
        };
        return mouseInputListener;
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
     * Flips the focused component.
     */
    private void flipComponent()
    {
        final IComponent component = getFocusedComponent();
        if( component != null )
        {
            getTableEnvironmentLock().lock();
            try
            {
                component.setOrientation( component.getOrientation().inverse() );
            }
            finally
            {
                getTableEnvironmentLock().unlock();
            }
        }
    }

    /**
     * Gets the focused component.
     * 
     * @return The focused component or {@code null} if no component has the
     *         focus.
     */
    /* @Nullable */
    private IComponent getFocusedComponent()
    {
        final ComponentModel componentModel = getFocusedComponentModel();
        return (componentModel != null) ? componentModel.getComponent() : null;
    }

    /**
     * Gets the model associated with the focused component.
     * 
     * @return The model associated with the focused component or {@code null}
     *         if no component has the focus.
     */
    /* @Nullable */
    private ComponentModel getFocusedComponentModel()
    {
        return model_.getFocusedComponentModel();
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
     * Gets the table environment lock.
     * 
     * @return The table environment lock; never {@code null}.
     */
    /* @NonNull */
    private Lock getTableEnvironmentLock()
    {
        return model_.getTable().getTableEnvironment().getLock();
    }

    /**
     * Gets the table environment model lock.
     * 
     * @return The table environment model lock; never {@code null}.
     */
    /* @NonNull */
    private Lock getTableEnvironmentModelLock()
    {
        return model_.getTableEnvironmentModel().getLock();
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
            if( clipBounds.intersects( tabletopView_.getComponentModel().getComponent().getBounds() ) )
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
        g.drawRect( componentBounds.x - PADDING.width, componentBounds.y - PADDING.height, componentBounds.width + (2 * PADDING.width) - 1, componentBounds.height + (2 * PADDING.height) - 1 );
        g.setColor( oldColor );
    }

    /**
     * Removes all components from the focused container or the tabletop if no
     * component has the focus.
     */
    private void removeAllComponents()
    {
        final IContainer container;
        final IComponent component = getFocusedComponent();
        if( component != null )
        {
            container = (component instanceof IContainer) ? (IContainer)component : null;
        }
        else
        {
            container = model_.getTable().getTabletop();
        }

        if( container != null )
        {
            container.removeAllComponents();
        }
    }

    /**
     * Removes the focused component.
     */
    private void removeComponent()
    {
        final IComponent component = getFocusedComponent();
        if( component != null )
        {
            getTableEnvironmentLock().lock();
            try
            {
                final IContainer container = component.getContainer();
                if( container != null )
                {
                    container.removeComponent( component );
                }
            }
            finally
            {
                getTableEnvironmentLock().unlock();
            }
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
        viewRegion.grow( PADDING.width, PADDING.height ); // ensure there is enough room to draw border if necessary
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
     * Sets the layout of the focused container to the specified value.
     * 
     * @param layoutId
     *        The identifier of the container layout; must not be {@code null}.
     */
    private void setContainerLayout(
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
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.TableView_setContainerLayout_error, e );
            }
        }
    }

    /**
     * Sets the current input handler
     * 
     * @param handlerClass
     *        The class of the input handler to activate; must not be
     *        {@code null}.
     * @param event
     *        The input event that triggered activation of the input handler;
     *        may be {@code null} if no input event triggered the activation.
     */
    private void setInputHandler(
        /* @NonNUll */
        final Class<? extends AbstractInputHandler> handlerClass,
        /* @Nullable */
        final InputEvent event )
    {
        assert handlerClass != null;

        inputHandler_.deactivate();
        inputHandler_ = inputHandlers_.get( handlerClass );
        assert inputHandler_ != null;
        inputHandler_.activate( event );
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
     * Superclass for all input handlers.
     * 
     * <p>
     * Instances of this class represent the State participant of the State
     * pattern.
     * </p>
     */
    @Immutable
    @SuppressWarnings( "synthetic-access" )
    private abstract class AbstractInputHandler
        extends MouseInputAdapter
        implements KeyListener
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code AbstractInputHandler} class.
         */
        protected AbstractInputHandler()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Activates this handler.
         * 
         * <p>
         * This implementation does nothing. Subclasses may override and are not
         * required to call the superclass implementation.
         * </p>
         * 
         * @param event
         *        The input event that triggered activation of this handler; may
         *        be {@code null} if no input event triggered the activation.
         */
        void activate(
            /* @Nullable */
            @SuppressWarnings( "unused" )
            final InputEvent event )
        {
            // do nothing
        }

        /**
         * Creates a search vector from the focused component model based on the
         * specified event state.
         * 
         * @param event
         *        The input event; may be {@code null} if no input event is
         *        available.
         * 
         * @return A search vector from the focused component model or
         *         {@code null} if no component model has the focus.
         */
        /* @Nullable */
        protected final ComponentModelVector createSearchVectorFromFocusedComponentModel(
            /* @Nullable */
            final InputEvent event )
        {
            final ComponentModel focusedComponentModel = model_.getFocusedComponentModel();
            if( focusedComponentModel == null )
            {
                return null;
            }

            final ComponentAxis searchAxis;
            if( event != null )
            {
                if( (event.getModifiersEx() & InputEvent.SHIFT_DOWN_MASK) == InputEvent.SHIFT_DOWN_MASK )
                {
                    searchAxis = ComponentAxis.FOLLOWING;
                }
                else
                {
                    searchAxis = ComponentAxis.PRECEDING;
                }
            }
            else
            {
                searchAxis = ComponentAxis.PRECEDING;
            }

            return new ComponentModelVector( focusedComponentModel, searchAxis );
        }

        /**
         * Deactivates this handler.
         * 
         * <p>
         * This implementation does nothing. Subclasses may override and are not
         * required to call the superclass implementation.
         * </p>
         */
        void deactivate()
        {
            // do nothing
        }

        /**
         * Gets the mouse location in table coordinates associated with the
         * specified input event or the current mouse location if the input
         * event does not include the mouse location or no input event is
         * available.
         * 
         * @param event
         *        The input event; may be {@code null} if no input event is
         *        available.
         * 
         * @return The mouse location in table coordinates; never {@code null}.
         */
        /* @NonNull */
        protected final Point getMouseLocation(
            /* @Nullable */
            final InputEvent event )
        {
            if( event instanceof MouseEvent )
            {
                final Point location = ((MouseEvent)event).getPoint();
                convertPointToTable( location );
                return location;
            }

            return TableView.this.getMouseLocation();
        }

        /**
         * This implementation does nothing. Subclasses may override and are not
         * required to call the superclass implementation.
         * 
         * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
         */
        @Override
        public void keyPressed(
            @SuppressWarnings( "unused" )
            final KeyEvent event )
        {
            // do nothing
        }

        /**
         * This implementation does nothing. Subclasses may override and are not
         * required to call the superclass implementation.
         * 
         * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
         */
        @Override
        public void keyReleased(
            @SuppressWarnings( "unused" )
            final KeyEvent event )
        {
            // do nothing
        }

        /**
         * This implementation does nothing. Subclasses may override and are not
         * required to call the superclass implementation.
         * 
         * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
         */
        @Override
        public void keyTyped(
            @SuppressWarnings( "unused" )
            final KeyEvent event )
        {
            // do nothing
        }

        /**
         * Subclasses may override but must call the superclass implementation.
         * 
         * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
         */
        @Override
        public void mouseReleased(
            @SuppressWarnings( "unused" )
            final MouseEvent event )
        {
            updateFocusOnMouseReleased_ = false;
        }
    }

    /**
     * The default input handler.
     */
    @Immutable
    @SuppressWarnings( "synthetic-access" )
    private final class DefaultInputHandler
        extends AbstractInputHandler
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code DefaultInputHandler} class.
         */
        DefaultInputHandler()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.internal.ui.view.TableView.AbstractInputHandler#activate(java.awt.event.InputEvent)
         */
        @Override
        void activate(
            final InputEvent event )
        {
            updateHover( event );
            updateCursor( event );
        }

        /*
         * @see org.gamegineer.table.internal.ui.view.TableView.AbstractInputHandler#keyPressed(java.awt.event.KeyEvent)
         */
        @Override
        public void keyPressed(
            final KeyEvent event )
        {
            updateHover( event );
        }

        /*
         * @see org.gamegineer.table.internal.ui.view.TableView.AbstractInputHandler#keyReleased(java.awt.event.KeyEvent)
         */
        @Override
        public void keyReleased(
            final KeyEvent event )
        {
            updateHover( event );
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
        public void mousePressed(
            final MouseEvent event )
        {
            updateFocus( event );

            if( event.isPopupTrigger() )
            {
                setInputHandler( PopupMenuInputHandler.class, event );
            }
            else if( SwingUtilities.isLeftMouseButton( event ) )
            {
                if( model_.getFocusedComponentModel() != null )
                {
                    setInputHandler( DragPrimedInputHandler.class, event );
                }
            }
            else if( SwingUtilities.isMiddleMouseButton( event ) )
            {
                setInputHandler( PanningTableInputHandler.class, event );
            }
        }

        /*
         * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
         */
        @Override
        public void mouseReleased(
            final MouseEvent event )
        {
            if( event.isPopupTrigger() )
            {
                setInputHandler( PopupMenuInputHandler.class, event );
            }

            super.mouseReleased( event );
        }

        /**
         * Updates the mouse cursor.
         * 
         * @param event
         *        The input event; may be {@code null} if no input event is
         *        available.
         */
        private void updateCursor(
            /* @Nullable */
            final InputEvent event )
        {
            final ComponentModel componentModel = model_.getFocusableComponentModel( getMouseLocation( event ) );
            final Cursor newCursor;
            if( componentModel != null )
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
        private void updateFocus(
            /* @NonNull */
            final MouseEvent event )
        {
            assert event != null;

            if( SwingUtilities.isLeftMouseButton( event ) || SwingUtilities.isRightMouseButton( event ) )
            {
                final Point mouseLocation = getMouseLocation( event );
                final ComponentModel focusedComponentModel = model_.getFocusedComponentModel();
                if( (focusedComponentModel != null) && focusedComponentModel.getComponent().getBounds().contains( mouseLocation ) )
                {
                    updateFocusOnMouseReleased_ = SwingUtilities.isLeftMouseButton( event );
                }
                else
                {
                    model_.setFocus( model_.getFocusableComponentModel( mouseLocation ) );
                }
            }
        }

        /**
         * Updates the hover.
         * 
         * @param event
         *        The input event; may be {@code null} if no input event is
         *        available.
         */
        private void updateHover(
            /* @Nullable */
            final InputEvent event )
        {
            getTableEnvironmentModelLock().lock();
            try
            {
                model_.setHover( model_.getFocusableComponentModel( getMouseLocation( event ), createSearchVectorFromFocusedComponentModel( event ) ) );
            }
            finally
            {
                getTableEnvironmentModelLock().unlock();
            }
        }
    }

    /**
     * The input handler that is active when a drag operation is primed but has
     * not yet begun.
     */
    @NotThreadSafe
    @SuppressWarnings( "synthetic-access" )
    private final class DragPrimedInputHandler
        extends AbstractInputHandler
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
         * Initializes a new instance of the {@code DragPrimedInputHandler}
         * class.
         */
        DragPrimedInputHandler()
        {
            originalLocation_ = new Point( 0, 0 );
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.internal.ui.view.TableView.AbstractInputHandler#activate(java.awt.event.InputEvent)
         */
        @Override
        void activate(
            final InputEvent event )
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
         * @see org.gamegineer.table.internal.ui.view.TableView.AbstractInputHandler#deactivate()
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
        public void mouseDragged(
            final MouseEvent event )
        {
            final Point location = getMouseLocation( event );
            if( canBeginDrag( location ) )
            {
                if( model_.getFocusableComponentModel( location ) != null )
                {
                    if( model_.isEditable() )
                    {
                        setInputHandler( DraggingComponentInputHandler.class, event );
                    }
                    else
                    {
                        setInputHandler( DefaultInputHandler.class, event );
                    }
                }
                else
                {
                    setInputHandler( DefaultInputHandler.class, event );
                }
            }
        }

        /*
         * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
         */
        @Override
        public void mouseReleased(
            final MouseEvent event )
        {
            if( updateFocusOnMouseReleased_ )
            {
                updateFocus( event );
            }

            if( SwingUtilities.isLeftMouseButton( event ) )
            {
                setInputHandler( DefaultInputHandler.class, null );
            }

            super.mouseReleased( event );
        }

        /**
         * Updates the focus.
         * 
         * @param event
         *        The mouse event; must not be {@code null}.
         */
        private void updateFocus(
            /* @NonNull */
            final MouseEvent event )
        {
            assert event != null;

            if( SwingUtilities.isLeftMouseButton( event ) )
            {
                getTableEnvironmentModelLock().lock();
                try
                {
                    model_.setFocus( model_.getFocusableComponentModel( getMouseLocation( event ), createSearchVectorFromFocusedComponentModel( event ) ) );
                }
                finally
                {
                    getTableEnvironmentModelLock().unlock();
                }
            }
        }
    }

    /**
     * The input handler that is active when a component is being dragged.
     */
    @NotThreadSafe
    @SuppressWarnings( "synthetic-access" )
    private final class DraggingComponentInputHandler
        extends AbstractInputHandler
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The drag context. */
        private IDragContext dragContext_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the
         * {@code DraggingComponentInputHandler} class.
         */
        DraggingComponentInputHandler()
        {
            dragContext_ = null;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.internal.ui.view.TableView.AbstractInputHandler#activate(java.awt.event.InputEvent)
         */
        @Override
        void activate(
            final InputEvent event )
        {
            final ComponentModel focusedComponentModel = model_.getFocusedComponentModel();
            if( focusedComponentModel != null )
            {
                final IDragSource dragSource = model_.getTable().getExtension( IDragSource.class );
                if( dragSource != null )
                {
                    dragContext_ = dragSource.beginDrag( getMouseLocation( event ), focusedComponentModel.getComponent() );
                    if( dragContext_ == null )
                    {
                        setInputHandler( DefaultInputHandler.class, null );
                    }
                }
                else
                {
                    Loggers.getDefaultLogger().severe( NonNlsMessages.TableView_draggingComponent_dragSourceNotAvailable );
                    setInputHandler( DefaultInputHandler.class, null );
                }
            }
            else
            {
                setInputHandler( DefaultInputHandler.class, null );
            }
        }

        /*
         * @see org.gamegineer.table.internal.ui.view.TableView.AbstractInputHandler#deactivate()
         */
        @Override
        void deactivate()
        {
            dragContext_ = null;
        }

        /*
         * @see java.awt.event.MouseAdapter#mouseDragged(java.awt.event.MouseEvent)
         */
        @Override
        public void mouseDragged(
            final MouseEvent event )
        {
            assert dragContext_ != null;

            dragContext_.drag( getMouseLocation( event ) );
        }

        /*
         * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
         */
        @Override
        public void mouseReleased(
            final MouseEvent event )
        {
            if( SwingUtilities.isLeftMouseButton( event ) )
            {
                assert dragContext_ != null;

                dragContext_.drop( getMouseLocation( event ) );
                setInputHandler( DefaultInputHandler.class, null );
            }

            super.mouseReleased( event );
        }
    }

    /**
     * The input handler that is active when the table is being panned.
     */
    @NotThreadSafe
    @SuppressWarnings( "synthetic-access" )
    private final class PanningTableInputHandler
        extends AbstractInputHandler
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
         * Initializes a new instance of the {@code PanningTableInputHandler}
         * class.
         */
        PanningTableInputHandler()
        {
            originalLocation_ = new Point( 0, 0 );
            originalOriginOffset_ = new Dimension( 0, 0 );
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.internal.ui.view.TableView.AbstractInputHandler#activate(java.awt.event.InputEvent)
         */
        @Override
        void activate(
            final InputEvent event )
        {
            final Point location = getMouseLocation( event );
            convertPointFromTable( location );
            originalLocation_.setLocation( location );
            originalOriginOffset_.setSize( model_.getOriginOffset() );

            setCursor( Cursors.getGrabCursor() );
        }

        /*
         * @see org.gamegineer.table.internal.ui.view.TableView.AbstractInputHandler#deactivate()
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
        public void mouseReleased(
            final MouseEvent event )
        {
            if( SwingUtilities.isMiddleMouseButton( event ) )
            {
                setInputHandler( DefaultInputHandler.class, null );
            }

            super.mouseReleased( event );
        }
    }

    /**
     * The input handler that is active when a popup menu is visible.
     */
    @Immutable
    @SuppressWarnings( "synthetic-access" )
    private final class PopupMenuInputHandler
        extends AbstractInputHandler
        implements PopupMenuListener
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code PopupMenuInputHandler}
         * class.
         */
        PopupMenuInputHandler()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.internal.ui.view.TableView.AbstractInputHandler#activate(java.awt.event.InputEvent)
         */
        @Override
        void activate(
            final InputEvent event )
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
        private JPopupMenu getPopupMenu(
            /* @NonNull */
            final Point location )
        {
            assert location != null;

            final ComponentModel componentModel = getFocusedComponentModel();
            if( componentModel != null )
            {
                if( componentModel instanceof ContainerModel )
                {
                    return new ContainerPopupMenu( (ContainerModel)componentModel );
                }

                return new ComponentPopupMenu( componentModel );
            }

            return new TablePopupMenu( model_ );
        }

        /*
         * @see javax.swing.event.PopupMenuListener#popupMenuCanceled(javax.swing.event.PopupMenuEvent)
         */
        @Override
        public void popupMenuCanceled(
            @SuppressWarnings( "unused" )
            final PopupMenuEvent event )
        {
            setInputHandler( DefaultInputHandler.class, null );
        }

        /*
         * @see javax.swing.event.PopupMenuListener#popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent)
         */
        @Override
        public void popupMenuWillBecomeInvisible(
            @SuppressWarnings( "unused" )
            final PopupMenuEvent event )
        {
            setInputHandler( DefaultInputHandler.class, null );
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
    @SuppressWarnings( "synthetic-access" )
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
                public void run()
                {
                    TableView.this.tableModelOriginOffsetChanged();
                }
            } );
        }
    }
}
