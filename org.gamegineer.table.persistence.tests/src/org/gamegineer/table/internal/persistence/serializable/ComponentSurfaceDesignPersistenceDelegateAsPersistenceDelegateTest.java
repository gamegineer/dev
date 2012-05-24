/*
 * ComponentSurfaceDesignPersistenceDelegateAsPersistenceDelegateTest.java
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
 * Created on Apr 26, 2012 at 9:58:12 PM.
 */

package org.gamegineer.table.internal.persistence.serializable;

import static org.junit.Assert.assertEquals;
import org.gamegineer.common.persistence.serializable.AbstractPersistenceDelegateTestCase;
import org.gamegineer.common.persistence.serializable.IPersistenceDelegate;
import org.gamegineer.common.persistence.serializable.IPersistenceDelegateRegistry;
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.core.ComponentSurfaceDesigns;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.persistence.serializable.ComponentSurfaceDesignPersistenceDelegate}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.common.persistence.serializable.IPersistenceDelegate}
 * interface.
 */
public final class ComponentSurfaceDesignPersistenceDelegateAsPersistenceDelegateTest
    extends AbstractPersistenceDelegateTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ComponentSurfaceDesignPersistenceDelegateAsPersistenceDelegateTest}
     * class.
     */
    public ComponentSurfaceDesignPersistenceDelegateAsPersistenceDelegateTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.persistence.serializable.AbstractPersistenceDelegateTestCase#assertSubjectEquals(java.lang.Object, java.lang.Object)
     */
    @Override
    protected void assertSubjectEquals(
        final Object expected,
        final Object actual )
    {
        final ComponentSurfaceDesign expectedComponentSurfaceDesign = (ComponentSurfaceDesign)expected;
        final ComponentSurfaceDesign actualComponentSurfaceDesign = (ComponentSurfaceDesign)actual;
        assertEquals( expectedComponentSurfaceDesign.getId(), actualComponentSurfaceDesign.getId() );
        assertEquals( expectedComponentSurfaceDesign.getSize(), actualComponentSurfaceDesign.getSize() );
    }

    /*
     * @see org.gamegineer.common.persistence.serializable.AbstractPersistenceDelegateTestCase#createPersistenceDelegate()
     */
    @Override
    protected IPersistenceDelegate createPersistenceDelegate()
    {
        return new ComponentSurfaceDesignPersistenceDelegate();
    }

    /*
     * @see org.gamegineer.common.persistence.serializable.AbstractPersistenceDelegateTestCase#createSubject()
     */
    @Override
    protected Object createSubject()
    {
        return ComponentSurfaceDesigns.createUniqueComponentSurfaceDesign();
    }

    /*
     * @see org.gamegineer.common.persistence.serializable.AbstractPersistenceDelegateTestCase#registerPersistenceDelegates(org.gamegineer.common.persistence.serializable.persistencedelegateregistry.IPersistenceDelegateRegistry)
     */
    @Override
    protected void registerPersistenceDelegates(
        final IPersistenceDelegateRegistry persistenceDelegateRegistry )
    {
        persistenceDelegateRegistry.registerPersistenceDelegate( ComponentSurfaceDesign.class, new ComponentSurfaceDesignPersistenceDelegate() );
        persistenceDelegateRegistry.registerPersistenceDelegate( ComponentSurfaceDesignProxy.class, new ComponentSurfaceDesignPersistenceDelegate() );
    }
}
