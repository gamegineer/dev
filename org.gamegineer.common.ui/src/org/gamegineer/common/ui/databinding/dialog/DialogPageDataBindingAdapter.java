/*
 * DialogPageDataBindingAdapter.java
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
 * Created on Oct 16, 2010 at 9:14:50 PM.
 */

package org.gamegineer.common.ui.databinding.dialog;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
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
import org.gamegineer.common.ui.dialog.DialogMessage;
import org.gamegineer.common.ui.dialog.IDialogPage;

/**
 * Adapts the validation result from a data binding context to a dialog page.
 */
@NotThreadSafe
public class DialogPageDataBindingAdapter
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The aggregate validation status. */
    private IObservableValue aggregateValidationStatus_;

    /** The current validation status. */
    private IStatus currentValidationStatus_;

    /** The data binding context. */
    private DataBindingContext dataBindingContext_;

    /** The dialog page. */
    private IDialogPage dialogPage_;

    /** Indicates the UI has changed. */
    private boolean hasUIChanged_;

    /** Indicates the current validation status is stale. */
    private boolean isCurrentValidationStatusStale_;

    /** The UI change listener. */
    private IChangeListener uiChangeListener_;

    /** The validation status provider targets list change listener. */
    private IListChangeListener validationStatusProviderTargetsListChangeListener_;

    /** The validation status providers list change listener. */
    private IListChangeListener validationStatusProvidersListChangeListener_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code DialogPageDataBindingAdapter}
     * class.
     * 
     * @param dialogPage
     *        The dialog page; must not be {@code null}.
     * @param dataBindingContext
     *        The data binding context; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code dialogPage} or {@code dataBindingContext} is {@code
     *         null}.
     */
    public DialogPageDataBindingAdapter(
        /* @NonNull */
        final IDialogPage dialogPage,
        /* @NonNull */
        final DataBindingContext dataBindingContext )
    {
        assertArgumentNotNull( dialogPage, "dialogPage" ); //$NON-NLS-1$
        assertArgumentNotNull( dataBindingContext, "dataBindingContext" ); //$NON-NLS-1$

        aggregateValidationStatus_ = null;
        currentValidationStatus_ = null;
        dataBindingContext_ = dataBindingContext;
        dialogPage_ = dialogPage;
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
    /* @NonNull */
    private IChangeListener createUIChangeListener()
    {
        return new IChangeListener()
        {
            @Override
            public void handleChange(
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
    /* @NonNull */
    private IListChangeListener createValidationStatusProviderTargetsListChangeListener()
    {
        return new IListChangeListener()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void handleListChange(
                final ListChangeEvent event )
            {
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
    /* @NonNull */
    private IListChangeListener createValidationStatusProvidersListChangeListener()
    {
        return new IListChangeListener()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void handleListChange(
                final ListChangeEvent event )
            {
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
        if( aggregateValidationStatus_ != null )
        {
            aggregateValidationStatus_.dispose();
        }

        if( (dataBindingContext_ != null) && !hasUIChanged_ )
        {
            dataBindingContext_.getValidationStatusProviders().removeListChangeListener( validationStatusProvidersListChangeListener_ );
            for( final Iterator<?> validationStatusProviderIterator = dataBindingContext_.getValidationStatusProviders().iterator(); validationStatusProviderIterator.hasNext(); )
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
        dialogPage_ = null;
        dataBindingContext_ = null;
        aggregateValidationStatus_ = null;
    }

    /**
     * Gets the current validation status.
     * 
     * @return The current validation status; never {@code null}.
     */
    /* @NonNull */
    protected final IStatus getCurrentValidationStatus()
    {
        return currentValidationStatus_;
    }

    /**
     * Gets the dialog page.
     * 
     * @return The dialog page; never {@code null}.
     */
    /* @NonNull */
    protected final IDialogPage getDialogPage()
    {
        return dialogPage_;
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

        dataBindingContext_.getValidationStatusProviders().removeListChangeListener( validationStatusProvidersListChangeListener_ );
        for( final Iterator<?> validationStatusProviderListener = dataBindingContext_.getValidationStatusProviders().iterator(); validationStatusProviderListener.hasNext(); )
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
        if( (currentValidationStatus_ != null) && hasUIChanged_ && !currentValidationStatus_.isOK() )
        {
            message = new DialogMessage( currentValidationStatus_.getMessage(), DataBindingUtils.getDialogMessageType( currentValidationStatus_ ) );
        }
        else
        {
            message = null;
        }

        dialogPage_.setMessage( message );
    }

    /**
     * Initializes all listeners.
     */
    private void initializeListeners()
    {
        ObservableTracker.runAndIgnore( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                aggregateValidationStatus_ = new AggregateValidationStatus( dataBindingContext_.getValidationStatusProviders(), AggregateValidationStatus.MAX_SEVERITY );
            }
        } );
        aggregateValidationStatus_.addValueChangeListener( new IValueChangeListener()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void handleValueChange(
                final ValueChangeEvent event )
            {
                currentValidationStatus_ = (IStatus)event.diff.getNewValue();
                isCurrentValidationStatusStale_ = aggregateValidationStatus_.isStale();
                handleValidationStatusChanged();
            }
        } );
        aggregateValidationStatus_.addStaleListener( new IStaleListener()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void handleStale(
                @SuppressWarnings( "unused" )
                final StaleEvent event )
            {
                isCurrentValidationStatusStale_ = true;
                handleValidationStatusChanged();
            }
        } );
        currentValidationStatus_ = (IStatus)aggregateValidationStatus_.getValue();
        isCurrentValidationStatusStale_ = aggregateValidationStatus_.isStale();
        handleValidationStatusChanged();

        dataBindingContext_.getValidationStatusProviders().addListChangeListener( validationStatusProvidersListChangeListener_ );
        for( final Iterator<?> validationStatusProviderIterator = dataBindingContext_.getValidationStatusProviders().iterator(); validationStatusProviderIterator.hasNext(); )
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
