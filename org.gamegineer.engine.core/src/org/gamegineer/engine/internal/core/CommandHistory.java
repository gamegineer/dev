/*
 * CommandHistory.java
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
 * Created on Apr 27, 2008 at 10:03:35 PM.
 */

package org.gamegineer.engine.internal.core;

import java.util.ArrayList;
import java.util.List;
import net.jcip.annotations.Immutable;
import org.gamegineer.engine.core.IInvertibleCommand;
import org.gamegineer.engine.core.contexts.command.ICommandContext;

/**
 * Represents the command history for an engine as well as the location of the
 * command history undo/redo pointer.
 * 
 * <p>
 * This class is not thread-safe.
 * </p>
 */
final class CommandHistory
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The list of command history entries where each entry represents a command
     * executed by the engine.
     * 
     * <p>
     * Entries with an index less than {@code nextEntryIndex_} represent
     * commands that can be undone. Entries with an index greater than or equal
     * to {@code nextEntryIndex_} represent commands that have been undone and
     * can be redone.
     * </p>
     */
    private final List<Entry> entries_;

    /**
     * The index in the entry list representing the location where the next
     * command to be executed will be inserted.
     * 
     * <p>
     * Typically, this index will be equal to the list size indicating the next
     * command will be added to the end of the list. However, when there is a
     * non-empty undo history, this index will point somewhere within the list.
     * </p>
     */
    private int nextEntryIndex_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CommandHistory} class.
     */
    CommandHistory()
    {
        entries_ = new ArrayList<Entry>();
        nextEntryIndex_ = 0;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds a new entry to the history and clears the undo history.
     * 
     * @param command
     *        The command; must not be {@code null}.
     * @param commandContext
     *        The command context; must not be {@code null}.
     */
    void add(
        /* @NonNull */
        final IInvertibleCommand<?> command,
        /* @NonNull */
        final ICommandContext commandContext )
    {
        assert command != null;
        assert commandContext != null;

        add( new Entry( command, commandContext ) );
    }

    /**
     * Adds a new entry to the history and clears the undo history.
     * 
     * @param entry
     *        The entry; must not be {@code null}.
     */
    void add(
        /* @NonNull */
        final Entry entry )
    {
        assert entry != null;

        entries_.subList( nextEntryIndex_, entries_.size() ).clear();
        entries_.add( entry );
        ++nextEntryIndex_;
    }

    /**
     * Indicates redo is available.
     * 
     * @return {@code true} if redo is available; otherwise {@code false}.
     */
    boolean canRedo()
    {
        return nextEntryIndex_ < entries_.size();
    }

    /**
     * Indicates undo is available.
     * 
     * @return {@code true} if undo is available; otherwise {@code false}.
     */
    boolean canUndo()
    {
        return nextEntryIndex_ > 0;
    }

    /*
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(
        final Object obj )
    {
        if( this == obj )
        {
            return true;
        }

        if( !(obj instanceof CommandHistory) )
        {
            return false;
        }

        final CommandHistory other = (CommandHistory)obj;
        return (nextEntryIndex_ == other.nextEntryIndex_) && entries_.equals( other.entries_ );
    }

    /**
     * Gets an ordered collection of the entries in this command history.
     * 
     * <p>
     * The returned collection does not include the undo history.
     * </p>
     * 
     * @return An ordered collection of the entries in this command history;
     *         never {@code null}. The first command in the list represents the
     *         earliest command executed by the engine. The last command in the
     *         list represents the latest command executed by the engine.
     */
    /* @NonNull */
    List<Entry> getEntries()
    {
        return new ArrayList<Entry>( entries_.subList( 0, nextEntryIndex_ ) );
    }

    /**
     * Gets the entry used to execute a redo request.
     * 
     * <p>
     * The command associated with the returned entry must be executed with its
     * associated context to satisfy the redo request.
     * </p>
     * 
     * <p>
     * Must not be called when redo is unavailable.
     * </p>
     * 
     * @return The entry used to execute a redo request; never {@code null}.
     */
    /* @NonNull */
    Entry getRedoEntry()
    {
        assert canRedo();

        return entries_.get( nextEntryIndex_ );
    }

    /**
     * Gets the entry used to execute an undo request.
     * 
     * <p>
     * The inverse of the command associated with the returned entry must be
     * executed with its associated context to satisfy the undo request.
     * </p>
     * 
     * <p>
     * Must not be called when undo is unavailable.
     * </p>
     * 
     * @return The entry used to execute an undo request; never {@code null}.
     */
    /* @NonNull */
    Entry getUndoEntry()
    {
        assert canUndo();

        return entries_.get( nextEntryIndex_ - 1 );
    }

    /*
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        int result = 17;
        result = 37 * result + entries_.hashCode();
        result = 37 * result + nextEntryIndex_;
        return result;
    }

    /**
     * Moves the most recent command in the undo history to the end of the
     * command history.
     * 
     * <p>
     * Must not be called when redo is unavailable.
     * </p>
     */
    void redo()
    {
        assert canRedo();

        ++nextEntryIndex_;
    }

    /**
     * Resets the command history using the specified list of command history
     * entries.
     * 
     * @param entries
     *        The list of command history entries; must not be {@code null}.
     */
    void reset(
        /* @NonNull */
        final List<Entry> entries )
    {
        assert entries != null;

        entries_.clear();
        entries_.addAll( entries );
        nextEntryIndex_ = entries_.size();
    }

    /**
     * Moves the most recent command in the command history to the front of the
     * undo history.
     * 
     * <p>
     * Must not be called when undo is unavailable.
     * </p>
     */
    void undo()
    {
        assert canUndo();

        --nextEntryIndex_;
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A command history entry.
     * 
     * <p>
     * A command history entry encapsulates all the information need to
     * re-execute a command if necessary. In addition to the command itself, it
     * stores a collection of attributes that represent the command context at
     * the time of its execution.
     * </p>
     * 
     * <p>
     * This class is immutable.
     * </p>
     */
    @Immutable
    static final class Entry
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The command. */
        private final IInvertibleCommand<?> command_;

        /** The command context. */
        private final ICommandContext commandContext_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code Entry} class.
         * 
         * @param command
         *        The command; must not be {@code null}.
         * @param commandContext
         *        The command context; must not be {@code null}.
         */
        Entry(
            /* @NonNull */
            final IInvertibleCommand<?> command,
            /* @NonNull */
            final ICommandContext commandContext )
        {
            assert command != null;
            assert commandContext != null;

            command_ = command;
            commandContext_ = commandContext;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(
            final Object obj )
        {
            if( this == obj )
            {
                return true;
            }

            if( !(obj instanceof Entry) )
            {
                return false;
            }

            final Entry other = (Entry)obj;
            return command_.equals( other.command_ ) && commandContext_.equals( other.commandContext_ );
        }

        /**
         * Gets the command.
         * 
         * @return The command; never {@code null}.
         */
        /* @NonNull */
        IInvertibleCommand<?> getCommand()
        {
            return command_;
        }

        /**
         * Gets the command context.
         * 
         * @return The command context; never {@code null}.
         */
        /* @NonNull */
        ICommandContext getCommandContext()
        {
            return commandContext_;
        }

        /*
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode()
        {
            int result = 17;
            result = 37 * result + command_.hashCode();
            result = 37 * result + commandContext_.hashCode();
            return result;
        }
    }
}
