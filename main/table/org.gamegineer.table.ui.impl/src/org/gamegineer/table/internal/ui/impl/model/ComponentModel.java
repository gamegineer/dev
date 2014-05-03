/*
 * ComponentModel.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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
 * Created on Dec 25, 2009 at 9:30:44 PM.
 */

package org.gamegineer.table.internal.ui.impl.model;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import java.awt.Point;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.table.core.ComponentEvent;
import org.gamegineer.table.core.ComponentPath;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IComponentListener;
import org.gamegineer.table.core.IComponentStrategy;
import org.gamegineer.table.internal.ui.impl.Loggers;
import org.gamegineer.table.internal.ui.impl.prototype.IEvaluationContextProvider;
import org.gamegineer.table.internal.ui.impl.strategies.DefaultComponentStrategyUIFactory;
import org.gamegineer.table.ui.ComponentStrategyUIRegistry;
import org.gamegineer.table.ui.IComponentStrategyUI;
import org.gamegineer.table.ui.NoSuchComponentStrategyUIException;

/**
 * The component model.
 */
@ThreadSafe
public class ComponentModel
    implements IEvaluationContextProvider
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The component associated with this model. */
    private final IComponent component_;

    /** The component listener for this model. */
    private final IComponentListener componentListener_;

    /** The component strategy user interface associated with this model. */
    private final IComponentStrategyUI componentStrategyUI_;

    /** Indicates the associated component has the focus. */
    @GuardedBy( "getLock()" )
    private boolean isFocused_;

    /** Indicates the associated component has the hover. */
    @GuardedBy( "getLock()" )
    private boolean isHovered_;

    /** The collection of component model listeners. */
    private final CopyOnWriteArrayList<IComponentModelListener> listeners_;

    /** The model parent or {@code null} if this model has no parent. */
    @GuardedBy( "getLock()" )
    @Nullable
    private IComponentModelParent parent_;

    /** The table environment model associated with this model. */
    private final TableEnvironmentModel tableEnvironmentModel_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentModel} class.
     * 
     * @param tableEnvironmentModel
     *        The table environment model associated with this model; must not
     *        be {@code null}.
     * @param component
     *        The component associated with this model; must not be {@code null}
     *        .
     */
    ComponentModel(
        final TableEnvironmentModel tableEnvironmentModel,
        final IComponent component )
    {
        assert tableEnvironmentModel.getTableEnvironment().equals( component.getTableEnvironment() );

        component_ = component;
        componentListener_ = new ComponentListener();
        componentStrategyUI_ = getComponentStrategyUI( component.getStrategy() );
        isFocused_ = false;
        isHovered_ = false;
        listeners_ = new CopyOnWriteArrayList<>();
        parent_ = null;
        tableEnvironmentModel_ = tableEnvironmentModel;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds the specified component model listener to this component model.
     * 
     * @param listener
     *        The component model listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is already a registered component model
     *         listener.
     */
    public final void addComponentModelListener(
        final IComponentModelListener listener )
    {
        assertArgumentLegal( listeners_.addIfAbsent( listener ), "listener", NonNlsMessages.ComponentModel_addComponentModelListener_listener_registered ); //$NON-NLS-1$
    }

    /**
     * Fires a component bounds changed event.
     */
    final void fireComponentBoundsChanged()
    {
        assert !getLock().isHeldByCurrentThread();

        final ComponentModelEvent event = new ComponentModelEvent( this );
        for( final IComponentModelListener listener : listeners_ )
        {
            try
            {
                listener.componentBoundsChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.ComponentModel_componentBoundsChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a component changed event.
     */
    final void fireComponentChanged()
    {
        assert !getLock().isHeldByCurrentThread();

        final ComponentModelEvent event = new ComponentModelEvent( this );
        for( final IComponentModelListener listener : listeners_ )
        {
            try
            {
                listener.componentChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.ComponentModel_componentChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a component model focus changed event.
     */
    final void fireComponentModelFocusChanged()
    {
        assert !getLock().isHeldByCurrentThread();

        final ComponentModelEvent event = new ComponentModelEvent( this );
        for( final IComponentModelListener listener : listeners_ )
        {
            try
            {
                listener.componentModelFocusChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.ComponentModel_componentModelFocusChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a component model hover changed event.
     */
    final void fireComponentModelHoverChanged()
    {
        assert !getLock().isHeldByCurrentThread();

        final ComponentModelEvent event = new ComponentModelEvent( this );
        for( final IComponentModelListener listener : listeners_ )
        {
            try
            {
                listener.componentModelHoverChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.ComponentModel_componentModelHoverChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a component orientation changed event.
     */
    final void fireComponentOrientationChanged()
    {
        assert !getLock().isHeldByCurrentThread();

        final ComponentModelEvent event = new ComponentModelEvent( this );
        for( final IComponentModelListener listener : listeners_ )
        {
            try
            {
                listener.componentOrientationChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.ComponentModel_componentOrientationChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a component surface design changed event.
     */
    final void fireComponentSurfaceDesignChanged()
    {
        assert !getLock().isHeldByCurrentThread();

        final ComponentModelEvent event = new ComponentModelEvent( this );
        for( final IComponentModelListener listener : listeners_ )
        {
            try
            {
                listener.componentSurfaceDesignChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.ComponentModel_componentSurfaceDesignChanged_unexpectedException, e );
            }
        }
    }

    /**
     * Fires the specified event notification.
     * 
     * @param eventNotification
     *        The event notification; must not be {@code null}.
     */
    final void fireEventNotification(
        final Runnable eventNotification )
    {
        tableEnvironmentModel_.fireEventNotification( eventNotification );
    }

    /**
     * Gets the component associated with this model.
     * 
     * <p>
     * Subclasses are not required to call the superclass method.
     * </p>
     * 
     * @return The component associated with this model; never {@code null}.
     */
    public IComponent getComponent()
    {
        return component_;
    }

    /**
     * Gets the component strategy user interface for the specified component
     * strategy.
     * 
     * @param componentStrategy
     *        The component strategy; must not be {@code null}.
     * 
     * @return The component strategy user interface for the specified component
     *         strategy; never {@code null}. A default component strategy user
     *         interface is returned if a component strategy user interface is
     *         not registered for the specified component strategy.
     */
    private static IComponentStrategyUI getComponentStrategyUI(
        final IComponentStrategy componentStrategy )
    {
        try
        {
            return ComponentStrategyUIRegistry.getComponentStrategyUI( componentStrategy.getId() );
        }
        catch( final NoSuchComponentStrategyUIException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.ComponentModel_getComponentStrategyUI_unknownComponentStrategyId, e );
            return DefaultComponentStrategyUIFactory.createDefaultComponentStrategyUI( componentStrategy );
        }
    }

    /**
     * Subclasses may override and must call the superclass implementation.
     * 
     * @see org.gamegineer.table.internal.ui.impl.prototype.IEvaluationContextProvider#getEvaluationContext()
     */
    @Override
    public EvaluationContext getEvaluationContext()
    {
        final IComponentModelParent parent = getParent();
        final EvaluationContext parentEvaluationContext = (parent != null) ? parent.getEvaluationContext() : null;
        return new EvaluationContext( parentEvaluationContext, EvaluationContext.UNDEFINED_VARIABLE );
    }

    /**
     * Gets the table environment model lock.
     * 
     * @return The table environment model lock; never {@code null}.
     */
    final ITableEnvironmentModelLock getLock()
    {
        return tableEnvironmentModel_.getLock();
    }

    /**
     * Gets the model parent.
     * 
     * @return The model parent or {@code null} if this model has no parent.
     */
    @Nullable
    public final IComponentModelParent getParent()
    {
        getLock().lock();
        try
        {
            return parent_;
        }
        finally
        {
            getLock().unlock();
        }
    }

    /**
     * Gets the path to this component model from its associated table model.
     * 
     * @return The path to this component model from its associated table model
     *         or {@code null} if the component model is not associated with a
     *         table model.
     */
    @Nullable
    public final ComponentPath getPath()
    {
        getLock().lock();
        try
        {
            return (parent_ != null) ? parent_.getChildPath( this ) : null;
        }
        finally
        {
            getLock().unlock();
        }
    }

    /**
     * Gets the table environment model associated with this model.
     * 
     * @return The table environment model associated with this model; never
     *         {@code null}.
     */
    public final TableEnvironmentModel getTableEnvironmentModel()
    {
        return tableEnvironmentModel_;
    }

    /**
     * Hit tests this component model against the specified location.
     * 
     * <p>
     * This implementation adds this component model to the specified collection
     * if it occupies the specified location. Subclasses may override and must
     * call the superclass implementation.
     * </p>
     * 
     * @param location
     *        The location in table coordinates; must not be {@code null}.
     * @param componentModels
     *        The collection that receives the component models that occupy the
     *        specified location; must not be {@code null}.
     * 
     * @return {@code true} if this component model occupies the specified
     *         location; otherwise {@code false}.
     */
    @GuardedBy( "getLock()" )
    boolean hitTest(
        final Point location,
        final List<ComponentModel> componentModels )
    {
        assert getLock().isHeldByCurrentThread();

        if( component_.getBounds().contains( location ) )
        {
            componentModels.add( this );
            return true;
        }

        return false;
    }

    /**
     * Initializes this model.
     * 
     * <p>
     * This method must only be called when the model is uninitialized.
     * Subclasses may override and must call the superclass implementation while
     * holding the instance lock.
     * </p>
     * 
     * @param parent
     *        The model parent; must not be {@code null}.
     */
    void initialize(
        final IComponentModelParent parent )
    {
        getLock().lock();
        try
        {
            assert parent_ == null;

            parent_ = parent;
            component_.addComponentListener( componentListener_ );
        }
        finally
        {
            getLock().unlock();
        }
    }

    /**
     * Indicates the component associated with this model can receive the focus.
     * 
     * @return {@code true} if the component associated with this model can
     *         receive the focus; otherwise {@code false}.
     */
    public final boolean isFocusable()
    {
        return componentStrategyUI_.isFocusable();
    }

    /**
     * Indicates the associated component has the focus.
     * 
     * @return {@code true} if the associated component has the focus; otherwise
     *         {@code false}.
     */
    public final boolean isFocused()
    {
        getLock().lock();
        try
        {
            return isFocused_;
        }
        finally
        {
            getLock().unlock();
        }
    }

    /**
     * Indicates the associated component has the hover.
     * 
     * @return {@code true} if the associated component has the hover; otherwise
     *         {@code false}.
     */
    public final boolean isHovered()
    {
        getLock().lock();
        try
        {
            return isHovered_;
        }
        finally
        {
            getLock().unlock();
        }
    }

    /**
     * Indicates this component model is the same as or a descendant of the
     * specified component model.
     * 
     * @param componentModel
     *        The component model to test if it is the same as or an ancestor of
     *        this component model; must not be {@code null}.
     * 
     * @return {@code true} if this component model is the same as or a
     *         descendant of the specified component model; otherwise
     *         {@code false}.
     */
    @GuardedBy( "getLock()" )
    final boolean isSameOrDescendantOf(
        final ComponentModel componentModel )
    {
        assert getLock().isHeldByCurrentThread();

        if( this == componentModel )
        {
            return true;
        }

        for( IComponentModelParent ancestor = getParent(); ancestor != null; ancestor = ancestor.getParent() )
        {
            if( ancestor == componentModel )
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Removes the specified component model listener from this component model.
     * 
     * @param listener
     *        The component model listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is not a registered component model listener.
     */
    public final void removeComponentModelListener(
        final IComponentModelListener listener )
    {
        assertArgumentLegal( listeners_.remove( listener ), "listener", NonNlsMessages.ComponentModel_removeComponentModelListener_listener_notRegistered ); //$NON-NLS-1$
    }

    /**
     * Sets or removes the focus from the associated component.
     * 
     * @param isFocused
     *        {@code true} if the associated component has the focus; otherwise
     *        {@code false}.
     */
    @GuardedBy( "getLock()" )
    final void setFocused(
        final boolean isFocused )
    {
        assert getLock().isHeldByCurrentThread();

        isFocused_ = isFocused;

        fireEventNotification( new Runnable()
        {
            @Override
            public void run()
            {
                fireComponentModelFocusChanged();
            }
        } );
    }

    /**
     * Sets or removes the hover from the associated component.
     * 
     * @param isHovered
     *        {@code true} if the associated component has the hover; otherwise
     *        {@code false}.
     */
    @GuardedBy( "getLock()" )
    final void setHover(
        final boolean isHovered )
    {
        assert getLock().isHeldByCurrentThread();

        isHovered_ = isHovered;

        fireEventNotification( new Runnable()
        {
            @Override
            public void run()
            {
                fireComponentModelHoverChanged();
            }
        } );
    }

    /**
     * Uninitializes this model.
     * 
     * <p>
     * This method must only be called after the model is initialized.
     * Subclasses may override and must call the superclass implementation while
     * the instance lock is held.
     * </p>
     */
    void uninitialize()
    {
        getLock().lock();
        try
        {
            assert parent_ != null;

            component_.removeComponentListener( componentListener_ );
            parent_ = null;
        }
        finally
        {
            getLock().unlock();
        }
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A component listener for the component model.
     */
    @Immutable
    private final class ComponentListener
        extends org.gamegineer.table.core.ComponentListener
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code ComponentListener} class.
         */
        ComponentListener()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.core.ComponentListener#componentBoundsChanged(org.gamegineer.table.core.ComponentEvent)
         */
        @Override
        public void componentBoundsChanged(
            @SuppressWarnings( "unused" )
            final ComponentEvent event )
        {
            fireEventNotification( new Runnable()
            {
                @Override
                public void run()
                {
                    fireComponentBoundsChanged();
                    fireComponentChanged();
                }
            } );
        }

        /*
         * @see org.gamegineer.table.core.ComponentListener#componentOrientationChanged(org.gamegineer.table.core.ComponentEvent)
         */
        @Override
        public void componentOrientationChanged(
            @SuppressWarnings( "unused" )
            final ComponentEvent event )
        {
            fireEventNotification( new Runnable()
            {
                @Override
                public void run()
                {
                    fireComponentOrientationChanged();
                    fireComponentChanged();
                }
            } );
        }

        /*
         * @see org.gamegineer.table.core.ComponentListener#componentSurfaceDesignChanged(org.gamegineer.table.core.ComponentEvent)
         */
        @Override
        public void componentSurfaceDesignChanged(
            @SuppressWarnings( "unused" )
            final ComponentEvent event )
        {
            fireEventNotification( new Runnable()
            {
                @Override
                public void run()
                {
                    fireComponentSurfaceDesignChanged();
                    fireComponentChanged();
                }
            } );
        }
    }
}
