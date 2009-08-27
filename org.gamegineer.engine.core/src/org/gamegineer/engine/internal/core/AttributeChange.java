/*
 * AttributeChange.java
 * Copyright 2008 Gamegineer.org
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
 * Created on Jun 14, 2008 at 11:10:38 AM.
 */

package org.gamegineer.engine.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import net.jcip.annotations.Immutable;
import org.gamegineer.engine.core.AttributeName;
import org.gamegineer.engine.core.extensions.stateeventmediator.IAttributeChange;

/**
 * Implementation of
 * {@link org.gamegineer.engine.core.extensions.stateeventmediator.IAttributeChange}.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
final class AttributeChange
    implements IAttributeChange
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Indicates the attribute has a new value. */
    private final boolean m_hasNewValue;

    /** Indicates the attribute has an old value. */
    private final boolean m_hasOldValue;

    /** The attribute name. */
    private final AttributeName m_name;

    /** The new attribute value. */
    private final Object m_newValue;

    /** The old attribute value. */
    private final Object m_oldValue;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AttributeChange} class.
     * 
     * @param name
     *        The attribute name; must not be {@code null}.
     * @param hasOldValue
     *        {@code true} if the attribute has an old value; {@code false} if
     *        the attribute was added. If {@code false}, {@code hasNewValue}
     *        must be {@code true}.
     * @param oldValue
     *        The old attribute value; may be {@code null}.
     * @param hasNewValue
     *        {@code true} if the attribute has a new value; {@code false} if
     *        the attribute was removed. If {@code false}, {@code hasOldValue}
     *        must be {@code true}.
     * @param newValue
     *        The new attribute value; may be {@code null}.
     */
    private AttributeChange(
        /* @NonNull */
        final AttributeName name,
        final boolean hasOldValue,
        /* @Nullable */
        final Object oldValue,
        final boolean hasNewValue,
        /* @Nullable */
        final Object newValue )
    {
        assert name != null;
        assert hasOldValue || hasNewValue;

        m_name = name;
        m_hasOldValue = hasOldValue;
        m_oldValue = oldValue;
        m_hasNewValue = hasNewValue;
        m_newValue = newValue;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new instance of the {@code AttributeChange} class that
     * represents an added attribute.
     * 
     * @param name
     *        The attribute name; must not be {@code null}.
     * @param newValue
     *        The new attribute value; may be {@code null}.
     * 
     * @return A new instance of the {@code AttributeChange} class.
     */
    /* @NonNull */
    static AttributeChange createAddedAttributeChange(
        /* @NonNull */
        final AttributeName name,
        /* @Nullable */
        final Object newValue )
    {
        return new AttributeChange( name, false, null, true, newValue );
    }

    /**
     * Creates a new instance of the {@code AttributeChange} class that
     * represents a changed attribute.
     * 
     * @param name
     *        The attribute name; must not be {@code null}.
     * @param oldValue
     *        The old attribute value; may be {@code null}.
     * @param newValue
     *        The new attribute value; may be {@code null}.
     * 
     * @return A new instance of the {@code AttributeChange} class.
     */
    /* @NonNull */
    static AttributeChange createChangedAttributeChange(
        /* @NonNull */
        final AttributeName name,
        /* @Nullable */
        final Object oldValue,
        /* @Nullable */
        final Object newValue )
    {
        return new AttributeChange( name, true, oldValue, true, newValue );
    }

    /**
     * Creates a new instance of the {@code AttributeChange} class that
     * represents a removed attribute.
     * 
     * @param name
     *        The attribute name; must not be {@code null}.
     * @param oldValue
     *        The old attribute value; may be {@code null}.
     * 
     * @return A new instance of the {@code AttributeChange} class.
     */
    /* @NonNull */
    static AttributeChange createRemovedAttributeChange(
        /* @NonNull */
        final AttributeName name,
        /* @Nullable */
        final Object oldValue )
    {
        return new AttributeChange( name, true, oldValue, false, null );
    }

    /*
     * @see org.gamegineer.engine.core.extensions.stateeventmediator.IAttributeChange#getName()
     */
    public AttributeName getName()
    {
        return m_name;
    }

    /*
     * @see org.gamegineer.engine.core.extensions.stateeventmediator.IAttributeChange#getNewValue()
     */
    public Object getNewValue()
    {
        assertStateLegal( m_hasNewValue, Messages.AttributeChange_newValue_absent );

        return m_newValue;
    }

    /*
     * @see org.gamegineer.engine.core.extensions.stateeventmediator.IAttributeChange#getOldValue()
     */
    public Object getOldValue()
    {
        assertStateLegal( m_hasOldValue, Messages.AttributeChange_oldValue_absent );

        return m_oldValue;
    }

    /*
     * @see org.gamegineer.engine.core.extensions.stateeventmediator.IAttributeChange#hasNewValue()
     */
    public boolean hasNewValue()
    {
        return m_hasNewValue;
    }

    /*
     * @see org.gamegineer.engine.core.extensions.stateeventmediator.IAttributeChange#hasOldValue()
     */
    public boolean hasOldValue()
    {
        return m_hasOldValue;
    }
}
