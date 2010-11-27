/*
 * IAdvancedPersistenceDelegate.java
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
 * Created on Nov 26, 2010 at 10:11:04 PM.
 */

package org.gamegineer.common.persistence.schemes.beans;

/**
 * Provides optional behavior for JavaBeans persistence delegates useful in
 * advanced scenarios.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IAdvancedPersistenceDelegate
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the class loader to use in the context of reading or writing objects
     * associated with the persistence delegate.
     * 
     * @return The class loader to use in the context of reading or writing
     *         objects associated with the persistence delegate or {@code null}
     *         to use the class loader of the persistence delegate itself.
     */
    /* @Nullable */
    public ClassLoader getContextClassLoader();

    /**
     * Allows the persistence delegate to replace the specified object being
     * written with an equivalent object.
     * 
     * @param obj
     *        The object being written; may be {@code null}.
     * 
     * @return The alternate object that replaces the specified object; may be
     *         {@code null}. The original object may be returned if no
     *         replacement should occur.
     */
    /* @Nullable */
    public Object replaceObject(
        /* @Nullable */
        Object obj );

    /**
     * Allows the persistence delegate to resolve the specified object being
     * read with an equivalent object.
     * 
     * @param obj
     *        The object being read; may be {@code null}.
     * 
     * @return The alternate object that resolves the specified object; may be
     *         {@code null}. The original object may be returned if no
     *         resolution should occur.
     */
    /* @Nullable */
    public Object resolveObject(
        /* @Nullable */
        Object obj );
}
