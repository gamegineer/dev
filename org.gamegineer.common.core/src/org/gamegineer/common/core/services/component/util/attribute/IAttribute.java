/*
 * IAttribute.java
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
 * Created on May 18, 2008 at 9:44:29 PM.
 */

package org.gamegineer.common.core.services.component.util.attribute;

/**
 * A component service participant attribute.
 * 
 * <p>
 * Instances of this interface allow clients to manipulate an attribute of a
 * component service participant that exposes a generic attribute collection in
 * a type-safe manner.
 * </p>
 * 
 * <p>
 * The only thread-safety guarantee an implementation must make is that each
 * method will execute atomically with respect to the underlying attribute
 * collection.
 * </p>
 * 
 * <p>
 * This interface is intended to be implemented but not extended by clients.
 * </p>
 * 
 * @param <T>
 *        The type of the attribute value. Implementors may choose to support
 *        conversion between this type and other types if desired.
 */
public interface IAttribute<T>
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the name of this attribute.
     * 
     * @return The name of this attribute; never {@code null}.
     */
    /* @NonNull */
    public String getName();

    /**
     * Gets the value of this attribute from the specified object.
     * 
     * <p>
     * This method should be used to read the attribute value from the
     * underlying attribute collection when the presence of the attribute is
     * required in the caller's context.
     * </p>
     * 
     * @param accessor
     *        The accessor used to read the attribute from the object; must not
     *        be {@code null}.
     * 
     * @return The attribute value; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If this attribute is not present in the specified object or the
     *         attribute value is illegal.
     * @throws java.lang.NullPointerException
     *         If {@code accessor} is {@code null}.
     */
    /* @NonNull */
    public T getValue(
        /* @NonNull */
        IAttributeAccessor accessor );

    /**
     * Indicates this attribute is present in the specified object.
     * 
     * @param accessor
     *        The accessor used to query the attribute from the object; must not
     *        be {@code null}.
     * 
     * @return {@code true} if this attribute is present in the specified
     *         object; otherwise {@code false}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code accessor} is {@code null}.
     */
    public boolean isPresent(
        /* @NonNull */
        IAttributeAccessor accessor );

    /**
     * Sets the value of this attribute in the specified object.
     * 
     * <p>
     * This method should be used to write the attribute value to the underlying
     * attribute collection when the presence of the attribute is required in
     * the caller's context.
     * </p>
     * 
     * @param mutator
     *        The mutator used to write the attribute to the object; must not be
     *        {@code null}.
     * @param value
     *        The attribute value; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the attribute value is illegal.
     * @throws java.lang.NullPointerException
     *         If {@code mutator} or {@code value} is {@code null}.
     */
    public void setValue(
        /* @NonNull */
        IAttributeMutator mutator,
        /* @NonNull */
        T value );

    /**
     * Gets the value of this attribute from the specified object if it is
     * present.
     * 
     * <p>
     * This method should be used to read the attribute value from the
     * underlying attribute collection when the presence of the attribute is
     * optional in the caller's context.
     * </p>
     * 
     * @param accessor
     *        The accessor used to read the attribute from the object; must not
     *        be {@code null}.
     * 
     * @return The attribute value or {@code null} if the attribute is not
     *         present.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the attribute value is illegal.
     * @throws java.lang.NullPointerException
     *         If {@code accessor} is {@code null}.
     */
    /* @Nullable */
    public T tryGetValue(
        /* @NonNull */
        IAttributeAccessor accessor );

    /**
     * Sets the value of this attribute on the specified object if one is
     * specified.
     * 
     * <p>
     * This method should be used to write the attribute value to the underlying
     * attribute collection when the presence of the attribute is optional in
     * the caller's context.
     * </p>
     * 
     * <p>
     * This method is functionally equivalent to the following code:
     * </p>
     * 
     * <pre>
     * if( value != null )
     * {
     *     attribute.setValue( mutator, value );
     * }
     * </pre>
     * 
     * @param mutator
     *        The mutator used to write the attribute to the object; must not be
     *        {@code null}.
     * @param value
     *        The attribute value; may be {@code null} if no attribute value is
     *        to be written.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the attribute value is illegal.
     * @throws java.lang.NullPointerException
     *         If {@code mutator} is {@code null}.
     */
    public void trySetValue(
        /* @NonNull */
        IAttributeMutator mutator,
        /* @Nullable */
        T value );
}
