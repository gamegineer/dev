/*
 * FakeStringBundle.java
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
 * Created on Feb 28, 2009 at 9:27:00 PM.
 */

package org.gamegineer.game.ui.system.bindings.xml;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import net.jcip.annotations.Immutable;

/**
 * A fake implementation of
 * {@link org.gamegineer.game.ui.system.bindings.xml.IStringBundle}.
 * 
 * <p>
 * This class is immutable.
 * </p>
 * 
 * <p>
 * This class is intended to be extended by clients.
 * </p>
 */
@Immutable
public class FakeStringBundle
    implements IStringBundle
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The string bundle entries. */
    private final Map<String, String> entries_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FakeStringBundle} class with no
     * entries.
     */
    public FakeStringBundle()
    {
        this( Collections.<String, String>emptyMap() );
    }

    /**
     * Initializes a new instance of the {@code FakeStringBundle} class with the
     * specified entries.
     * 
     * @param entries
     *        The string bundle entries; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code entries} is {@code null}.
     */
    public FakeStringBundle(
        /* @NonNull */
        final Map<String, String> entries )
    {
        assertArgumentNotNull( entries, "entries" ); //$NON-NLS-1$

        entries_ = Collections.unmodifiableMap( new HashMap<String, String>( entries ) );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.game.ui.system.xml.IStringBundle#containsKey(java.lang.String)
     */
    public boolean containsKey(
        final String key )
    {
        assertArgumentNotNull( key, "key" ); //$NON-NLS-1$

        return entries_.containsKey( key );
    }

    /*
     * @see org.gamegineer.game.ui.system.xml.IStringBundle#getKeys()
     */
    public Set<String> getKeys()
    {
        return entries_.keySet();
    }

    /*
     * @see org.gamegineer.game.ui.system.xml.IStringBundle#getString(java.lang.String)
     */
    public String getString(
        final String key )
    {
        assertArgumentNotNull( key, "key" ); //$NON-NLS-1$

        return entries_.get( key );
    }
}
