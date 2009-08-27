/*
 * AttributeChangeAsAttributeChangeTest.java
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
 * Created on Jun 14, 2008 at 11:37:47 AM.
 */

package org.gamegineer.engine.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import org.gamegineer.engine.core.AttributeName;
import org.gamegineer.engine.core.extensions.stateeventmediator.AbstractAttributeChangeTestCase;
import org.gamegineer.engine.core.extensions.stateeventmediator.IAttributeChange;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.AttributeChange} class to ensure
 * it does not violate the contract of the
 * {@link org.gamegineer.engine.core.extensions.stateeventmediator.IAttributeChange}
 * interface.
 */
public final class AttributeChangeAsAttributeChangeTest
    extends AbstractAttributeChangeTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AttributeChangeAsAttributeChangeTest} class.
     */
    public AttributeChangeAsAttributeChangeTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.extensions.stateeventmediator.AbstractAttributeChangeTestCase#createAttributeChange(org.gamegineer.engine.core.AttributeName, boolean, java.lang.Object, boolean, java.lang.Object)
     */
    @Override
    protected IAttributeChange createAttributeChange(
        final AttributeName name,
        final boolean hasOldValue,
        final Object oldValue,
        final boolean hasNewValue,
        final Object newValue )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$
        assertArgumentLegal( hasOldValue || hasNewValue, "hasNewValue", "attribute change must have either an old value or a new value" ); //$NON-NLS-1$ //$NON-NLS-2$

        if( !hasOldValue )
        {
            return AttributeChange.createAddedAttributeChange( name, newValue );
        }
        else if( !hasNewValue )
        {
            return AttributeChange.createRemovedAttributeChange( name, oldValue );
        }
        else
        {
            return AttributeChange.createChangedAttributeChange( name, oldValue, newValue );
        }
    }
}
