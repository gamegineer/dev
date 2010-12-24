/*
 * IPersistenceDelegate.java
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
 * Created on Jun 21, 2008 at 8:46:44 PM.
 */

package org.gamegineer.common.persistence.serializable;

import java.io.IOException;
import java.io.ObjectStreamClass;

/**
 * Represents an object that takes responsibility for persisting objects of a
 * specific class within the Java object serialization framework.
 * 
 * <p>
 * The Gamegineer serialization streams use registered persistence delegates to
 * assist with serializing and deserializing objects, especially those that are
 * not naturally serializable (i.e. they do not implement {@code Serializable}).
 * A persistence delegate registered for a specific class will always be given
 * the first opportunity to serialize or deserialize a given object.
 * </p>
 * 
 * <p>
 * To contribute a persistence delegate for a specific class, register it with
 * the platform's {@code IPersistenceDelegateRegistry}.
 * </p>
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IPersistenceDelegate
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Allows the persistence delegate to annotate the specified class with
     * whatever data it deems necessary.
     * 
     * @param stream
     *        The object output stream; must not be {@code null}.
     * @param cl
     *        The class to be annotated; must not be {@code null}.
     * 
     * @throws java.io.IOException
     *         If an I/O error occurs.
     * @throws java.lang.NullPointerException
     *         If {@code stream} or {@code cl} is {@code null}.
     */
    public void annotateClass(
        /* @NonNull */
        ObjectOutputStream stream,
        /* @NonNull */
        Class<?> cl )
        throws IOException;

    /**
     * Allows the persistence delegate to replace the specified object being
     * serialized with an equivalent serializable object.
     * 
     * @param obj
     *        The object being serialized; may be {@code null}.
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
     * Allows the persistence delegate to resolve the class associated with the
     * specified object stream class description.
     * 
     * @param stream
     *        The object input stream; must not be {@code null}.
     * @param desc
     *        An object stream class description; must not be {@code null}.
     * 
     * @return The class associated with the specified object stream class
     *         descriptor or {@code null} if the persistence delegate cannot
     *         resolve the class, in which case the caller is responsible for
     *         resolving the class.
     * 
     * @throws java.io.IOException
     *         If an I/O error occurs.
     * @throws java.lang.NullPointerException
     *         If {@code stream} or {@code desc} is {@code null}.
     */
    /* @Nullable */
    public Class<?> resolveClass(
        /* @NonNull */
        ObjectInputStream stream,
        /* @NonNull */
        ObjectStreamClass desc )
        throws IOException;

    /**
     * Allows the persistence delegate to resolve the specified serializable
     * object being deserialized with an equivalent object.
     * 
     * @param obj
     *        The object being deserialized; may be {@code null}.
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