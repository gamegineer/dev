/*
 * ExtensionsAttributeAsAttributeTest.java
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
 * Created on Sep 7, 2008 at 10:45:17 PM.
 */

package org.gamegineer.engine.internal.core.extensions.extensionregistry;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.gamegineer.engine.core.IExtension;
import org.gamegineer.engine.core.util.attribute.AbstractAttributeTestCase;
import org.gamegineer.engine.core.util.attribute.IAttribute;

/**
 * A fixture for testing the
 * {@link org.gamegineer.engine.internal.core.extensions.extensionregistry.ExtensionRegistryExtension#EXTENSIONS_ATTRIBUTE}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.engine.core.util.attribute.IAttribute} interface.
 */
public final class ExtensionsAttributeAsAttributeTest
    extends AbstractAttributeTestCase<Map<Class<?>, IExtension>>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ExtensionsAttributeAsAttributeTest} class.
     */
    public ExtensionsAttributeAsAttributeTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.util.attribute.AbstractAttributeTestCase#createAttribute()
     */
    @Override
    protected IAttribute<Map<Class<?>, IExtension>> createAttribute()
        throws Exception
    {
        return ExtensionRegistryExtensionFacade.EXTENSIONS_ATTRIBUTE();
    }

    /*
     * @see org.gamegineer.engine.core.util.attribute.AbstractAttributeTestCase#getIllegalAttributeValues()
     */
    @Override
    protected Collection<Map<Class<?>, IExtension>> getIllegalAttributeValues()
    {
        return Collections.singletonList( null );
    }

    /*
     * @see org.gamegineer.engine.core.util.attribute.AbstractAttributeTestCase#getLegalAttributeValue()
     */
    @Override
    protected Map<Class<?>, IExtension> getLegalAttributeValue()
    {
        return new HashMap<Class<?>, IExtension>();
    }
}
