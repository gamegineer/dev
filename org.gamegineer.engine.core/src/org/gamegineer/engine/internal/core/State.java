/*
 * State.java
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
 * Created on Feb 21, 2008 at 9:32:16 PM.
 */

package org.gamegineer.engine.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.gamegineer.engine.core.AttributeName;
import org.gamegineer.engine.core.IState;
import org.gamegineer.engine.core.extensions.stateeventmediator.IAttributeChange;

/**
 * Implementation of {@link org.gamegineer.engine.core.IState}.
 * 
 * <p>
 * This class is not thread-safe.
 * </p>
 */
final class State
    implements IState
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The attribute collection. */
    private Map<AttributeName, Object> attributes_;

    /** The backup attribute collection stored during a transaction. */
    private Map<AttributeName, Object> backupAttributes_;

    /**
     * The collection of attribute names whose values <i>may</i> have been
     * changed during a transaction.
     */
    private final Set<AttributeName> changedAttributeNames_;

    /** The transaction state. */
    private TransactionState transactionState_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code State} class.
     */
    State()
    {
        attributes_ = new HashMap<AttributeName, Object>();
        backupAttributes_ = null;
        changedAttributeNames_ = new HashSet<AttributeName>();
        transactionState_ = TransactionState.INACTIVE;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * @throws java.lang.IllegalStateException
     *         If a writable transaction is not active.
     * 
     * @see org.gamegineer.engine.core.IState#addAttribute(org.gamegineer.engine.core.AttributeName,
     *      java.lang.Object)
     */
    public void addAttribute(
        final AttributeName name,
        final Object value )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$
        assertArgumentLegal( !attributes_.containsKey( name ), "name", Messages.State_attribute_present( name ) ); //$NON-NLS-1$
        assertStateLegal( transactionState_ == TransactionState.ACTIVE, Messages.State_transaction_notActiveWritable );

        changedAttributeNames_.add( name );
        attributes_.put( name, value );
    }

    /**
     * Begins a new transaction.
     * 
     * @throws java.lang.IllegalStateException
     *         If a transaction is active.
     */
    void beginTransaction()
    {
        assertStateLegal( !isTransactionActive(), Messages.State_transaction_active );
        assert changedAttributeNames_.isEmpty();

        backupAttributes_ = new HashMap<AttributeName, Object>( attributes_ );
        transactionState_ = TransactionState.ACTIVE;
    }

    /**
     * Commits the active transaction.
     * 
     * @throws java.lang.IllegalStateException
     *         If a transaction is not active.
     */
    void commitTransaction()
    {
        assertStateLegal( isTransactionActive(), Messages.State_transaction_notActive );

        backupAttributes_.clear();
        backupAttributes_ = null;
        changedAttributeNames_.clear();
        transactionState_ = TransactionState.INACTIVE;
    }

    /*
     * @see org.gamegineer.engine.core.IState#containsAttribute(org.gamegineer.engine.core.AttributeName)
     */
    public boolean containsAttribute(
        final AttributeName name )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$

        return attributes_.containsKey( name );
    }

    /*
     * @see org.gamegineer.engine.core.IState#getAttribute(org.gamegineer.engine.core.AttributeName)
     */
    public Object getAttribute(
        final AttributeName name )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$
        assertArgumentLegal( attributes_.containsKey( name ), "name", Messages.State_attribute_absent( name ) ); //$NON-NLS-1$

        return attributes_.get( name );
    }

    /**
     * Gets the change record for the specified attribute if it has changed in
     * some way (added, removed, or edited) during the current transaction.
     * 
     * <p>
     * This method must be called while a transaction is active.
     * </p>
     * 
     * @param name
     *        The name of the attribute; must not be {@code null}.
     * 
     * @return The attribute change record or {@code null} if the attribute has
     *         not changed.
     */
    /* @Nullable */
    private IAttributeChange getAttributeChange(
        /* @NonNull */
        final AttributeName name )
    {
        assert name != null;
        assert isTransactionActive();

        final boolean isPresentCurrent = attributes_.containsKey( name );
        final Object valueCurrent = attributes_.get( name );
        final boolean isPresentBackup = backupAttributes_.containsKey( name );
        final Object valueBackup = backupAttributes_.get( name );
        if( !isPresentCurrent && !isPresentBackup )
        {
            return null;
        }
        else if( isPresentCurrent && !isPresentBackup )
        {
            return AttributeChange.createAddedAttributeChange( name, valueCurrent );
        }
        else if( !isPresentCurrent && isPresentBackup )
        {
            return AttributeChange.createRemovedAttributeChange( name, valueBackup );
        }
        else if( valueCurrent == valueBackup )
        {
            return null;
        }
        else if( ((valueCurrent != null) && (valueBackup == null)) || ((valueCurrent == null) && (valueBackup != null)) )
        {
            return AttributeChange.createChangedAttributeChange( name, valueBackup, valueCurrent );
        }
        else
        {
            assert valueCurrent != null;
            if( valueCurrent.equals( valueBackup ) )
            {
                return null;
            }

            return AttributeChange.createChangedAttributeChange( name, valueBackup, valueCurrent );
        }
    }

    /**
     * Gets a map view of the attribute changes that have occurred during the
     * current transaction.
     * 
     * @return A map view of the attribute changes that have occurred during the
     *         current transaction; never {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If a transaction is not active.
     */
    /* @NonNull */
    Map<AttributeName, IAttributeChange> getAttributeChanges()
    {
        assertStateLegal( isTransactionActive(), Messages.State_transaction_notActive );

        final Map<AttributeName, IAttributeChange> attributeChanges = new HashMap<AttributeName, IAttributeChange>();
        for( final AttributeName name : changedAttributeNames_ )
        {
            final IAttributeChange change = getAttributeChange( name );
            if( change != null )
            {
                attributeChanges.put( name, change );
            }
        }

        return attributeChanges;
    }

    /*
     * @see org.gamegineer.engine.core.IState#getAttributeNames()
     */
    public Set<AttributeName> getAttributeNames()
    {
        return Collections.unmodifiableSet( attributes_.keySet() );
    }

    /**
     * Gets a set view of the attribute names in the specified scope contained
     * in this state.
     * 
     * @param scope
     *        The attribute scope; must not be {@code null}.
     * 
     * @return A set view of the attribute names in the specified scope
     *         contained in this state; never {@code null}.
     */
    /* @NonNull */
    Set<AttributeName> getAttributeNames(
        /* @NonNull */
        final Scope scope )
    {
        assert scope != null;

        final Set<AttributeName> names = new HashSet<AttributeName>();
        for( final AttributeName name : attributes_.keySet() )
        {
            if( name.getScope() == scope )
            {
                names.add( name );
            }
        }
        return names;
    }

    /**
     * Indicates a transaction is active.
     * 
     * @return {@code true} if there is an active transaction; otherwise {@code
     *         false}.
     */
    boolean isTransactionActive()
    {
        return transactionState_ != TransactionState.INACTIVE;
    }

    /**
     * Prepares the active transaction to be committed.
     * 
     * <p>
     * This method prevents any further changes to the state attributes. After
     * calling this method, clients can only examine the state attributes,
     * commit the transaction, or roll back the transaction.
     * </p>
     */
    void prepareToCommitTransaction()
    {
        assertStateLegal( transactionState_ == TransactionState.ACTIVE, Messages.State_transaction_notActiveWritable );

        transactionState_ = TransactionState.ACTIVE_READ_ONLY;
    }

    /**
     * Removes all attributes with the specified names from this state.
     * 
     * @param names
     *        The names of the attributes to be removed; must not be {@code
     *        null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If any of the specified attributes do not exist in this state.
     * @throws java.lang.IllegalStateException
     *         If a writable transaction is not active.
     */
    void removeAllAttributes(
        /* @NonNull */
        final Set<AttributeName> names )
    {
        assert names != null;

        for( final AttributeName name : names )
        {
            removeAttribute( name );
        }
    }

    /**
     * @throws java.lang.IllegalStateException
     *         If a writable transaction is not active.
     * 
     * @see org.gamegineer.engine.core.IState#removeAttribute(org.gamegineer.engine.core.AttributeName)
     */
    public void removeAttribute(
        final AttributeName name )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$
        assertArgumentLegal( attributes_.containsKey( name ), "name", Messages.State_attribute_absent( name ) ); //$NON-NLS-1$
        assertStateLegal( transactionState_ == TransactionState.ACTIVE, Messages.State_transaction_notActiveWritable );

        changedAttributeNames_.add( name );
        attributes_.remove( name );
    }

    /**
     * Rolls back the active transaction.
     * 
     * @throws java.lang.IllegalStateException
     *         If a transaction is not active.
     */
    void rollbackTransaction()
    {
        assertStateLegal( isTransactionActive(), Messages.State_transaction_notActive );

        attributes_ = backupAttributes_;
        backupAttributes_ = null;
        changedAttributeNames_.clear();
        transactionState_ = TransactionState.INACTIVE;
    }

    /**
     * @throws java.lang.IllegalStateException
     *         If a writable transaction is not active.
     * 
     * @see org.gamegineer.engine.core.IState#setAttribute(org.gamegineer.engine.core.AttributeName,
     *      java.lang.Object)
     */
    public void setAttribute(
        final AttributeName name,
        final Object value )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$
        assertArgumentLegal( attributes_.containsKey( name ), "name", Messages.State_attribute_absent( name ) ); //$NON-NLS-1$
        assertStateLegal( transactionState_ == TransactionState.ACTIVE, Messages.State_transaction_notActiveWritable );

        changedAttributeNames_.add( name );
        attributes_.put( name, value );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * Enumerates the possible states of a {@code State} transaction.
     */
    private enum TransactionState
    {
        // ==================================================================
        // Enum Constants
        // ==================================================================

        /** The transaction is active and read/write operations are permitted. */
        ACTIVE,

        /** The transaction is active but only read operations are permitted. */
        ACTIVE_READ_ONLY,

        /** The transaction is not active. */
        INACTIVE,
    }
}
