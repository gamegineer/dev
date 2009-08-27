/*
 * StringBundle.java
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
 * Created on Feb 26, 2009 at 11:41:44 PM.
 */

package org.gamegineer.game.internal.ui.system.bindings.xml;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import net.jcip.annotations.Immutable;
import org.gamegineer.game.ui.system.bindings.xml.IStringBundle;

/**
 * Implementation of
 * {@link org.gamegineer.game.ui.system.bindings.xml.IStringBundle}.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
final class StringBundle
    implements IStringBundle
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The string bundle entries. */
    private final Map<String, String> m_entries;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code StringBundle} class.
     * 
     * @param entries
     *        The string bundle entries; must not be {@code null}. Note that a
     *        deep copy of this parameter is not made.
     */
    StringBundle(
        /* @NonNull */
        final Map<String, String> entries )
    {
        assert entries != null;

        m_entries = Collections.unmodifiableMap( entries );
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

        return m_entries.containsKey( key );
    }

    /*
     * @see org.gamegineer.game.ui.system.xml.IStringBundle#getKeys()
     */
    public Set<String> getKeys()
    {
        return m_entries.keySet();
    }

    /*
     * @see org.gamegineer.game.ui.system.xml.IStringBundle#getString(java.lang.String)
     */
    public String getString(
        final String key )
    {
        assertArgumentNotNull( key, "key" ); //$NON-NLS-1$

        return m_entries.get( key );
    }
}
