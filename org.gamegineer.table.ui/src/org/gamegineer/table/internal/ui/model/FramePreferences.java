/*
 * FramePreferences.java
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
 * Created on Aug 14, 2010 at 9:20:33 PM.
 */

package org.gamegineer.table.internal.ui.model;

import java.awt.Dimension;
import java.awt.Point;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.osgi.service.prefs.Preferences;

/**
 * The preferences for the main frame window.
 */
@ThreadSafe
public final class FramePreferences
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The preference key for the frame height. */
    private static final String KEY_HEIGHT = "height"; //$NON-NLS-1$

    /** The preference key for the frame state. */
    private static final String KEY_STATE = "state"; //$NON-NLS-1$

    /** The preference key for the frame width. */
    private static final String KEY_WIDTH = "width"; //$NON-NLS-1$

    /** The preference key for the frame x location. */
    private static final String KEY_X = "x"; //$NON-NLS-1$

    /** The preference key for the frame y location. */
    private static final String KEY_Y = "y"; //$NON-NLS-1$

    /** The frame location or {@code null} if not defined. */
    @GuardedBy( "lock_" )
    private Point location_;

    /** The instance lock. */
    private final Object lock_;

    /** The frame size or {@code null} if not defined. */
    @GuardedBy( "lock_" )
    private Dimension size_;

    /** The frame state or {@code null} if not defined. */
    @GuardedBy( "lock_" )
    private Integer state_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FramePreferences} class.
     */
    FramePreferences()
    {
        lock_ = new Object();
        location_ = null;
        size_ = null;
        state_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the frame location.
     * 
     * @return The frame location or {@code null} if the frame location is not
     *         defined.
     */
    /* @Nullable */
    public Point getLocation()
    {
        synchronized( lock_ )
        {
            return (location_ != null) ? new Point( location_ ) : null;
        }
    }

    /**
     * Gets the frame size.
     * 
     * @return The frame size or {@code null} if the frame size is not defined.
     */
    /* @Nullable */
    public Dimension getSize()
    {
        synchronized( lock_ )
        {
            return (size_ != null) ? new Dimension( size_ ) : null;
        }
    }

    /**
     * Gets the frame state.
     * 
     * @return The frame state or {@code null} if the frame state is not
     *         defined.
     */
    /* @Nullable */
    public Integer getState()
    {
        synchronized( lock_ )
        {
            return state_;
        }
    }

    /**
     * Loads the frame preferences from the specified preference node.
     * 
     * @param preferences
     *        The preferences node; must not be {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    void load(
        /* @NonNull */
        final Preferences preferences )
    {
        assert preferences != null;

        synchronized( lock_ )
        {
            final int x = preferences.getInt( KEY_X, Integer.MIN_VALUE );
            final int y = preferences.getInt( KEY_Y, Integer.MIN_VALUE );
            if( (x == Integer.MIN_VALUE) && (y == Integer.MIN_VALUE) )
            {
                location_ = null;
            }
            else
            {
                location_ = new Point( x, y );
            }

            final int width = preferences.getInt( KEY_WIDTH, Integer.MIN_VALUE );
            final int height = preferences.getInt( KEY_HEIGHT, Integer.MIN_VALUE );
            if( (width == Integer.MIN_VALUE) && (height == Integer.MIN_VALUE) )
            {
                size_ = null;
            }
            else
            {
                size_ = new Dimension( width, height );
            }

            final int statePrimitive = preferences.getInt( KEY_STATE, Integer.MIN_VALUE );
            state_ = (statePrimitive == Integer.MIN_VALUE) ? null : statePrimitive;
        }
    }

    /**
     * Stores the frame preferences in the specified preference node.
     * 
     * @param preferences
     *        The preferences node; must not be {@code null}.
     */
    @SuppressWarnings( "boxing" )
    void save(
        /* @NonNull */
        final Preferences preferences )
    {
        assert preferences != null;

        synchronized( lock_ )
        {
            if( location_ != null )
            {
                preferences.putInt( KEY_X, location_.x );
                preferences.putInt( KEY_Y, location_.y );
            }
            else
            {
                preferences.remove( KEY_X );
                preferences.remove( KEY_Y );
            }

            if( size_ != null )
            {
                preferences.putInt( KEY_WIDTH, size_.width );
                preferences.putInt( KEY_HEIGHT, size_.height );
            }
            else
            {
                preferences.remove( KEY_WIDTH );
                preferences.remove( KEY_HEIGHT );
            }

            if( state_ != null )
            {
                preferences.putInt( KEY_STATE, state_ );
            }
            else
            {
                preferences.remove( KEY_STATE );
            }
        }
    }

    /**
     * Sets the frame location.
     * 
     * @param location
     *        The frame location or {@code null} if the frame location is not
     *        defined.
     */
    public void setLocation(
        /* @Nullable */
        final Point location )
    {
        synchronized( lock_ )
        {
            location_ = (location != null) ? new Point( location ) : null;
        }
    }

    /**
     * Sets the frame size.
     * 
     * @param size
     *        The frame size or {@code null} if the frame size is not defined.
     */
    public void setSize(
        /* @Nullable */
        final Dimension size )
    {
        synchronized( lock_ )
        {
            size_ = (size != null) ? new Dimension( size ) : null;
        }
    }

    /**
     * Sets the frame state.
     * 
     * @param state
     *        The frame state or {@code null} if the frame state is not defined.
     */
    public void setState(
        /* @Nullable */
        final Integer state )
    {
        synchronized( lock_ )
        {
            state_ = state;
        }
    }
}
