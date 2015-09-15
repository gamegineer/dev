/*
 * SecureStringAsEquatableTest.java
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
 * Created on Oct 30, 2010 at 4:14:50 PM.
 */

package org.gamegineer.common.core.security;

import java.util.ArrayList;
import java.util.Collection;
import org.gamegineer.test.core.AbstractEquatableTestCase;

/**
 * A fixture for testing the {@link SecureString} class to ensure it does not
 * violate the contract of the equatable interface.
 */
public final class SecureStringAsEquatableTest
    extends AbstractEquatableTestCase<SecureString>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code SecureStringAsEquatableTest}
     * class.
     */
    public SecureStringAsEquatableTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.test.core.AbstractEquatableTestCase#createReferenceInstance()
     */
    @Override
    protected SecureString createReferenceInstance()
    {
        return new SecureString( "password".toCharArray() ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.test.core.AbstractEquatableTestCase#createUnequalInstances()
     */
    @Override
    protected Collection<SecureString> createUnequalInstances()
    {
        final Collection<SecureString> others = new ArrayList<>();
        others.add( new SecureString( "PASSWORD".toCharArray() ) ); //$NON-NLS-1$
        return others;
    }
}
