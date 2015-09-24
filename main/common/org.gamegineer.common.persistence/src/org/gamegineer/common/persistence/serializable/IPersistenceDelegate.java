/*
 * IPersistenceDelegate.java
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
 * Created on Jun 21, 2008 at 8:46:44 PM.
 */

package org.gamegineer.common.persistence.serializable;

import java.io.IOException;
import java.io.ObjectStreamClass;
import org.eclipse.jdt.annotation.Nullable;

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
     *        The object output stream.
     * @param cl
     *        The class to be annotated.
     * 
     * @throws java.io.IOException
     *         If an I/O error occurs.
     */
    public void annotateClass(
        ObjectOutputStream stream,
        Class<?> cl )
        throws IOException;

    /**
     * Allows the persistence delegate to replace the specified object being
     * serialized with an equivalent serializable object.
     * 
     * @param obj
     *        The object being serialized.
     * 
     * @return The alternate object that replaces the specified object. The
     *         original object may be returned if no replacement should occur.
     * 
     * @throws java.io.IOException
     *         If an I/O error occurs.
     */
    public @Nullable Object replaceObject(
        @Nullable Object obj )
        throws IOException;

    /**
     * Allows the persistence delegate to resolve the class associated with the
     * specified object stream class description.
     * 
     * @param stream
     *        The object input stream.
     * @param desc
     *        An object stream class description.
     * 
     * @return The class associated with the specified object stream class
     *         descriptor.
     * 
     * @throws java.io.IOException
     *         If an I/O error occurs.
     * @throws java.lang.ClassNotFoundException
     *         If the class cannot be found.
     */
    public Class<?> resolveClass(
        ObjectInputStream stream,
        ObjectStreamClass desc )
        throws IOException, ClassNotFoundException;

    /**
     * Allows the persistence delegate to resolve the specified serializable
     * object being deserialized with an equivalent object.
     * 
     * @param obj
     *        The object being deserialized.
     * 
     * @return The alternate object that resolves the specified object. The
     *         original object may be returned if no resolution should occur.
     * 
     * @throws java.io.IOException
     *         If an I/O error occurs.
     */
    public @Nullable Object resolveObject(
        @Nullable Object obj )
        throws IOException;
}
