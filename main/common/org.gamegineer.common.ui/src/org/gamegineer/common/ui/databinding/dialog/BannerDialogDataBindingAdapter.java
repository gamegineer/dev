/*
 * BannerDialogDataBindingAdapter.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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
 * Created on Sep 17, 2011 at 11:11:57 PM.
 */

package org.gamegineer.common.ui.databinding.dialog;

import java.util.Iterator;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.core.databinding.AggregateValidationStatus;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.ValidationStatusProvider;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.IStaleListener;
import org.eclipse.core.databinding.observable.ObservableTracker;
import org.eclipse.core.databinding.observable.StaleEvent;
import org.eclipse.core.databinding.observable.list.IListChangeListener;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.ListChangeEvent;
import org.eclipse.core.databinding.observable.list.ListDiffEntry;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.ui.dialog.AbstractBannerDialog;
import org.gamegineer.common.ui.dialog.DialogMessage;

/**
 * Adapts the validation result from a data binding context to a banner dialog.
 */
@NotThreadSafe
public class BannerDialogDataBindingAdapter
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The aggregate validation status. */
    @Nullable
    private IObservableValue aggregateValidationStatus_;

    /** The banner dialog. */
    @Nullable
    private AbstractBannerDialog bannerDialog_;

    /** The current validation status. */
    @Nullable
    private IStatus currentValidationStatus_;

    /** The data binding context. */
    @Nullable
    private DataBindingContext dataBindingContext_;

    /** Indicates the UI has changed. */
    private boolean hasUIChanged_;

    /** Indicates the current validation status is stale. */
    private boolean isCurrentValidationStatusStale_;

    /** The UI change listener. */
    @Nullable
    private IChangeListener uiChangeListener_;

    /** The validation status provider targets list change listener. */
    @Nullable
    private IListChangeListener validationStatusProviderTargetsListChangeListener_;

    /** The validation status providers list change listener. */
    @Nullable
    private IListChangeListener validationStatusProvidersListChangeListener_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code BannerDialogDataBindingAdapter}
     * class.
     * 
     * @param bannerDialog
     *        The banner dialog; must not be {@code null}.
     * @param dataBindingContext
     *        The data binding context; must not be {@code null}.
     */
    public BannerDialogDataBindingAdapter(
        final AbstractBannerDialog bannerDialog,
        final DataBindingContext dataBindingContext )
    {
        aggregateValidationStatus_ = null;
        bannerDialog_ = bannerDialog;
        currentValidationStatus_ = null;
        dataBindingContext_ = dataBindingContext;
        hasUIChanged_ = false;
        isCurrentValidationStatusStale_ = false;
        uiChangeListener_ = createUIChangeListener();
        validationStatusProviderTargetsListChangeListener_ = createValidationStatusProviderTargetsListChangeListener();
        validationStatusProvidersListChangeListener_ = createValidationStatusProvidersListChangeListener();

        initializeListeners();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the UI change listener.
     * 
     * @return The UI change listener; never {@code null}.
     */
    private IChangeListener createUIChangeListener()
    {
        return new IChangeListener()
        {
            @Override
            public void handleChange(
                @Nullable
                @SuppressWarnings( "unused" )
                final ChangeEvent event )
            {
                handleUIChanged();
            }
        };
    }

    /**
     * Creates the validation status provider targets list change listener.
     * 
     * @return The validation status provider targets list change listener;
     *         never {@code null}.
     */
    private IListChangeListener createValidationStatusProviderTargetsListChangeListener()
    {
        return new IListChangeListener()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void handleListChange(
                @Nullable
                final ListChangeEvent event )
            {
                assert event != null;

                for( final ListDiffEntry difference : event.diff.getDifferences() )
                {
                    final IObservable target = (IObservable)difference.getElement();
                    if( difference.isAddition() )
                    {
                        target.addChangeListener( uiChangeListener_ );
                    }
                    else
                    {
                        target.removeChangeListener( uiChangeListener_ );
                    }
                }
            }
        };
    }

    /**
     * Creates the validation status providers list change listener.
     * 
     * @return The validation status providers list change listener; never
     *         {@code null}.
     */
    private IListChangeListener createValidationStatusProvidersListChangeListener()
    {
        return new IListChangeListener()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void handleListChange(
                @Nullable
                final ListChangeEvent event )
            {
                assert event != null;

                for( final ListDiffEntry difference : event.diff.getDifferences() )
                {
                    final ValidationStatusProvider validationStatusProvider = (ValidationStatusProvider)difference.getElement();
                    final IObservableList targets = validationStatusProvider.getTargets();
                    if( difference.isAddition() )
                    {
                        targets.addListChangeListener( validationStatusProviderTargetsListChangeListener_ );
                        for( final Iterator<?> iterator = targets.iterator(); iterator.hasNext(); )
                        {
                            ((IObservable)iterator.next()).addChangeListener( uiChangeListener_ );
                        }
                    }
                    else
                    {
                        targets.removeListChangeListener( validationStatusProviderTargetsListChangeListener_ );
                        for( final Iterator<?> iterator = targets.iterator(); iterator.hasNext(); )
                        {
                            ((IObservable)iterator.next()).removeChangeListener( uiChangeListener_ );
                        }
                    }
                }
            }
        };
    }

    /**
     * Disposes of the resources managed by this object.
     */
    public void dispose()
    {
        final IObservableValue aggregateValidationStatus = aggregateValidationStatus_;
        if( aggregateValidationStatus != null )
        {
            aggregateValidationStatus.dispose();
        }

        final DataBindingContext dataBindingContext = dataBindingContext_;
        if( (dataBindingContext != null) && !hasUIChanged_ )
        {
            dataBindingContext.getValidationStatusProviders().removeListChangeListener( validationStatusProvidersListChangeListener_ );
            for( final Iterator<?> validationStatusProviderIterator = dataBindingContext.getValidationStatusProviders().iterator(); validationStatusProviderIterator.hasNext(); )
            {
                final ValidationStatusProvider validationStatusProvider = (ValidationStatusProvider)validationStatusProviderIterator.next();
                final IObservableList targets = validationStatusProvider.getTargets();
                targets.removeListChangeListener( validationStatusProviderTargetsListChangeListener_ );
                for( final Iterator<?> targetIterator = targets.iterator(); targetIterator.hasNext(); )
                {
                    ((IObservable)targetIterator.next()).removeChangeListener( uiChangeListener_ );
                }
            }
        }

        validationStatusProvidersListChangeListener_ = null;
        validationStatusProviderTargetsListChangeListener_ = null;
        uiChangeListener_ = null;
        bannerDialog_ = null;
        dataBindingContext_ = null;
        aggregateValidationStatus_ = null;
    }

    /**
     * Gets the banner dialog.
     * 
     * @return The banner dialog; never {@code null}.
     */
    protected final AbstractBannerDialog getBannerDialog()
    {
        assert bannerDialog_ != null;
        return bannerDialog_;
    }

    /**
     * Gets the current validation status.
     * 
     * @return The current validation status or {@code null} if there is no
     *         current validation status.
     */
    @Nullable
    protected final IStatus getCurrentValidationStatus()
    {
        return currentValidationStatus_;
    }

    /**
     * Invoked when the UI has changed.
     */
    protected void handleUIChanged()
    {
        hasUIChanged_ = true;

        if( currentValidationStatus_ != null )
        {
            handleValidationStatusChanged();
        }

        final DataBindingContext dataBindingContext = dataBindingContext_;
        assert dataBindingContext != null;
        dataBindingContext.getValidationStatusProviders().removeListChangeListener( validationStatusProvidersListChangeListener_ );
        for( final Iterator<?> validationStatusProviderListener = dataBindingContext.getValidationStatusProviders().iterator(); validationStatusProviderListener.hasNext(); )
        {
            final ValidationStatusProvider validationStatusProvider = (ValidationStatusProvider)validationStatusProviderListener.next();
            final IObservableList targets = validationStatusProvider.getTargets();
            targets.removeListChangeListener( validationStatusProviderTargetsListChangeListener_ );
            for( final Iterator<?> targetIterator = targets.iterator(); targetIterator.hasNext(); )
            {
                ((IObservable)targetIterator.next()).removeChangeListener( uiChangeListener_ );
            }
        }
    }

    /**
     * Invoked when the validation status has changed.
     */
    protected void handleValidationStatusChanged()
    {
        final DialogMessage message;
        final IStatus currentValidationStatus = currentValidationStatus_;
        if( (currentValidationStatus != null) && hasUIChanged_ && !currentValidationStatus.isOK() )
        {
            message = new DialogMessage( currentValidationStatus.getMessage(), DataBindingUtils.getDialogMessageType( currentValidationStatus ) );
        }
        else
        {
            message = null;
        }

        getBannerDialog().setMessage( message );
    }

    /**
     * Initializes all listeners.
     */
    private void initializeListeners()
    {
        final DataBindingContext dataBindingContext = dataBindingContext_;
        assert dataBindingContext != null;

        ObservableTracker.runAndIgnore( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                aggregateValidationStatus_ = new AggregateValidationStatus( dataBindingContext.getValidationStatusProviders(), AggregateValidationStatus.MAX_SEVERITY );
            }
        } );

        final IObservableValue aggregateValidationStatus = aggregateValidationStatus_;
        assert aggregateValidationStatus != null;
        aggregateValidationStatus.addValueChangeListener( new IValueChangeListener()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void handleValueChange(
                @Nullable
                final ValueChangeEvent event )
            {
                assert event != null;

                currentValidationStatus_ = (IStatus)event.diff.getNewValue();
                isCurrentValidationStatusStale_ = aggregateValidationStatus.isStale();
                handleValidationStatusChanged();
            }
        } );
        aggregateValidationStatus.addStaleListener( new IStaleListener()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void handleStale(
                @Nullable
                @SuppressWarnings( "unused" )
                final StaleEvent event )
            {
                isCurrentValidationStatusStale_ = true;
                handleValidationStatusChanged();
            }
        } );
        currentValidationStatus_ = (IStatus)aggregateValidationStatus.getValue();
        isCurrentValidationStatusStale_ = aggregateValidationStatus.isStale();
        handleValidationStatusChanged();

        dataBindingContext.getValidationStatusProviders().addListChangeListener( validationStatusProvidersListChangeListener_ );
        for( final Iterator<?> validationStatusProviderIterator = dataBindingContext.getValidationStatusProviders().iterator(); validationStatusProviderIterator.hasNext(); )
        {
            final ValidationStatusProvider validationStatusProvider = (ValidationStatusProvider)validationStatusProviderIterator.next();
            final IObservableList targets = validationStatusProvider.getTargets();
            targets.addListChangeListener( validationStatusProviderTargetsListChangeListener_ );
            for( final Iterator<?> targetIterator = targets.iterator(); targetIterator.hasNext(); )
            {
                ((IObservable)targetIterator.next()).addChangeListener( uiChangeListener_ );
            }
        }
    }

    /**
     * Indicates the current validation status is fatal.
     * 
     * @return {@code true} if the current validation status is fatal; otherwise
     *         {@code false}.
     */
    protected final boolean isCurrentValidationStatusFatal()
    {
        return DataBindingUtils.isValidationStatusFatal( currentValidationStatus_, isCurrentValidationStatusStale_ );
    }

    /**
     * Indicates the current validation status is stale.
     * 
     * @return {@code true} if the current validation status is stale; otherwise
     *         {@code false}.
     */
    protected final boolean isCurrentValidationStatusStale()
    {
        return isCurrentValidationStatusStale_;
    }
}
