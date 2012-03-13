/*
 * FakeRealm.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Oct 16, 2010 at 9:37:58 PM.
 */

package org.gamegineer.common.ui.databinding;

import net.jcip.annotations.NotThreadSafe;
import org.eclipse.core.databinding.observable.Realm;

/**
 * Fake implementation of {@link org.eclipse.core.databinding.observable.Realm}.
 */
@NotThreadSafe
public class FakeRealm
    extends Realm
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FakeRealm} class.
     */
    public FakeRealm()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.eclipse.core.databinding.observable.Realm#isCurrent()
     */
    @Override
    public boolean isCurrent()
    {
        return this.equals( Realm.getDefault() );
    }
}
