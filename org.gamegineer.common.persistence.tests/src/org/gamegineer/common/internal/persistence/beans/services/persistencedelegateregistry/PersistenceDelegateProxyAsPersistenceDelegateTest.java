/*
 * PersistenceDelegateProxyAsPersistenceDelegateTest.java
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
 * Created on Jul 9, 2010 at 11:42:17 PM.
 */

package org.gamegineer.common.internal.persistence.beans.services.persistencedelegateregistry;

import java.beans.PersistenceDelegate;
import java.util.Dictionary;
import java.util.Hashtable;
import org.gamegineer.common.internal.persistence.Activator;
import org.gamegineer.common.persistence.beans.AbstractPersistenceDelegateTestCase;
import org.gamegineer.common.persistence.beans.FakeNonPersistableClass;
import org.gamegineer.common.persistence.beans.FakeNonPersistableClassPersistenceDelegate;
import org.gamegineer.common.persistence.beans.services.persistencedelegateregistry.IPersistenceDelegateRegistry;
import org.gamegineer.common.persistence.beans.services.persistencedelegateregistry.PersistenceDelegateRegistryConstants;
import org.junit.After;
import org.junit.Before;
import org.osgi.framework.ServiceRegistration;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.internal.persistence.beans.services.persistencedelegateregistry.PersistenceDelegateProxy}
 * class to ensure it correctly delegates its behavior to its actual persistence
 * delegate.
 */
public final class PersistenceDelegateProxyAsPersistenceDelegateTest
    extends AbstractPersistenceDelegateTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The registration token for the persistence delegate service used in the
     * fixture.
     */
    private ServiceRegistration serviceRegistration_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * PersistenceDelegateProxyAsPersistenceDelegateTest} class.
     */
    public PersistenceDelegateProxyAsPersistenceDelegateTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.persistence.beans.AbstractPersistenceDelegateTestCase#createSubject()
     */
    @Override
    protected Object createSubject()
    {
        return new FakeNonPersistableClass( 2112, "42" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.common.persistence.beans.AbstractPersistenceDelegateTestCase#registerPersistenceDelegates(org.gamegineer.common.persistence.beans.services.persistencedelegateregistry.IPersistenceDelegateRegistry)
     */
    @Override
    protected void registerPersistenceDelegates(
        final IPersistenceDelegateRegistry persistenceDelegateRegistry )
    {
        final PersistenceDelegate persistenceDelegate = Activator.getDefault().getBeansPersistenceDelegateRegistry().getPersistenceDelegate( FakeNonPersistableClass.class );
        persistenceDelegateRegistry.registerPersistenceDelegate( FakeNonPersistableClass.class, persistenceDelegate );
    }

    /*
     * @see org.gamegineer.common.persistence.beans.AbstractPersistenceDelegateTestCase#setUp()
     */
    @Before
    @Override
    public void setUp()
        throws Exception
    {
        final Dictionary<String, Object> properties = new Hashtable<String, Object>();
        properties.put( PersistenceDelegateRegistryConstants.PROPERTY_DELEGATORS, FakeNonPersistableClass.class.getName() );
        serviceRegistration_ = Activator.getDefault().getBundleContext().registerService( PersistenceDelegate.class.getName(), new FakeNonPersistableClassPersistenceDelegate(), properties );

        super.setUp();
    }

    /*
     * @see org.gamegineer.common.persistence.beans.AbstractPersistenceDelegateTestCase#tearDown()
     */
    @After
    @Override
    public void tearDown()
        throws Exception
    {
        super.tearDown();

        serviceRegistration_.unregister();
    }
}
