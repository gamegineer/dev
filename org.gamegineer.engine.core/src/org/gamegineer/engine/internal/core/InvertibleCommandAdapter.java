/*
 * InvertibleCommandAdapter.java
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
 * Created on Aug 13, 2008 at 10:17:17 PM.
 */

package org.gamegineer.engine.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.gamegineer.engine.core.AbstractInvertibleCommand;
import org.gamegineer.engine.core.AttributeName;
import org.gamegineer.engine.core.CommandBehavior;
import org.gamegineer.engine.core.EngineException;
import org.gamegineer.engine.core.ICommand;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.IInvertibleCommand;
import org.gamegineer.engine.core.IState;
import org.gamegineer.engine.core.IState.Scope;
import org.gamegineer.engine.core.extensions.stateeventmediator.IAttributeChange;

/**
 * An engine command that adapts a non-invertible command as an invertible
 * command.
 * 
 * <p>
 * This class records all application attribute changes that occur during the
 * execution of the non-invertible command to provide an implementation of the
 * inverse command.
 * </p>
 * 
 * <p>
 * This class is not thread-safe.
 * </p>
 * 
 * <p>
 * This class is not intended to be extended by clients.
 * </p>
 * 
 * @param <T>
 *        The result type of the command.
 */
@CommandBehavior( writeLockRequired = true )
public final class InvertibleCommandAdapter<T>
    implements IInvertibleCommand<T>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The collection of attributes added by the command; {@code null} if no
     * attributes were added.
     */
    private Set<AttributeName> addedAttributes_;

    /**
     * The collection of attributes changed by the command; {@code null} if no
     * attributes were changed. The attribute value at the time it was changed
     * is recorded.
     */
    private Map<AttributeName, Object> changedAttributes_;

    /** The non-invertible command. */
    private final ICommand<T> command_;

    /**
     * The collection of attributes removed by the command; {@code null} if no
     * attributes were removed. The attribute value at the time it was removed
     * is recorded.
     */
    private Map<AttributeName, Object> removedAttributes_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code InvertibleCommandAdapter} class.
     * 
     * @param command
     *        The non-invertible command; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code command} is {@code null}.
     */
    public InvertibleCommandAdapter(
        /* @NonNull */
        final ICommand<T> command )
    {
        assertArgumentNotNull( command, "command" ); //$NON-NLS-1$

        command_ = command;
        addedAttributes_ = null;
        changedAttributes_ = null;
        removedAttributes_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.ICommand#execute(org.gamegineer.engine.core.IEngineContext)
     */
    public T execute(
        final IEngineContext context )
        throws EngineException
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        final T result = command_.execute( context );
        storeAttributeChanges( context );
        return result;
    }

    /*
     * @see org.gamegineer.engine.core.IInvertibleCommand#getInverseCommand()
     */
    public IInvertibleCommand<T> getInverseCommand()
    {
        @CommandBehavior( writeLockRequired = true )
        class InverseCommand
            extends AbstractInvertibleCommand<T>
        {
            @SuppressWarnings( "synthetic-access" )
            public T execute(
                final IEngineContext context )
            {
                assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

                revertAttributeChanges( context );
                return null;
            }

            public IInvertibleCommand<T> getInverseCommand()
            {
                return InvertibleCommandAdapter.this;
            }
        }
        return new InverseCommand();
    }

    /*
     * @see org.gamegineer.engine.core.ICommand#getType()
     */
    public String getType()
    {
        return command_.getType();
    }

    /**
     * Reverts the attribute changes made by the command.
     * 
     * @param context
     *        The engine context; must not be {@code null}.
     */
    private void revertAttributeChanges(
        /* @NonNull */
        final IEngineContext context )
    {
        assert context != null;

        final IState state = context.getState();
        if( addedAttributes_ != null )
        {
            for( final AttributeName name : addedAttributes_ )
            {
                state.removeAttribute( name );
            }
        }
        if( changedAttributes_ != null )
        {
            for( final Map.Entry<AttributeName, Object> entry : changedAttributes_.entrySet() )
            {
                state.setAttribute( entry.getKey(), entry.getValue() );
            }
        }
        if( removedAttributes_ != null )
        {
            for( final Map.Entry<AttributeName, Object> entry : removedAttributes_.entrySet() )
            {
                state.addAttribute( entry.getKey(), entry.getValue() );
            }
        }
    }

    /**
     * Stores the attribute changes made by the command.
     * 
     * @param context
     *        The engine context; must not be {@code null}.
     */
    private void storeAttributeChanges(
        /* @NonNull */
        final IEngineContext context )
    {
        assert context != null;

        final Engine engine = ((EngineContext)context).getEngine();
        final Set<AttributeName> addedAttributes = new HashSet<AttributeName>();
        final Map<AttributeName, Object> changedAttributes = new HashMap<AttributeName, Object>();
        final Map<AttributeName, Object> removedAttributes = new HashMap<AttributeName, Object>();
        for( final IAttributeChange change : engine.getState().getAttributeChanges().values() )
        {
            final AttributeName name = change.getName();
            if( name.getScope() == Scope.APPLICATION )
            {
                final boolean hasOldValue = change.hasOldValue();
                final boolean hasNewValue = change.hasNewValue();
                if( hasOldValue && hasNewValue )
                {
                    changedAttributes.put( name, change.getOldValue() );
                }
                else if( hasOldValue && !hasNewValue )
                {
                    removedAttributes.put( name, change.getOldValue() );
                }
                else if( !hasOldValue && hasNewValue )
                {
                    addedAttributes.add( name );
                }
            }
        }

        addedAttributes_ = addedAttributes.isEmpty() ? null : addedAttributes;
        changedAttributes_ = changedAttributes.isEmpty() ? null : changedAttributes;
        removedAttributes_ = removedAttributes.isEmpty() ? null : removedAttributes;
    }
}
