/*
 * PersistenceDelegateProxy.java
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
 * Created on Jul 8, 2010 at 10:51:55 PM.
 */

package org.gamegineer.common.internal.persistence.schemes.beans.services.persistencedelegateregistry;

import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.concurrent.TaskUtils;
import org.gamegineer.common.internal.persistence.Activator;
import org.gamegineer.common.persistence.schemes.beans.IAdvancedPersistenceDelegate;
import org.osgi.framework.ServiceReference;

/**
 * A proxy for lazily loading persistence delegates published via the service
 * registry.
 */
@ThreadSafe
final class PersistenceDelegateProxy
    extends PersistenceDelegate
    implements IAdvancedPersistenceDelegate
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Indicates this instance has been disposed. */
    @GuardedBy( "lock_" )
    private boolean isDisposed_;

    /** The instance lock. */
    private final Object lock_;

    /** The actual persistence delegate. */
    @GuardedBy( "lock_" )
    private PersistenceDelegate persistenceDelegate_;

    /** The service registry reference to the persistence delegate. */
    private final ServiceReference persistenceDelegateReference_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code PersistenceDelegateProxy} class.
     * 
     * @param persistenceDelegateReference
     *        The service registry reference to the persistence delegate; must
     *        not be {@code null}.
     */
    PersistenceDelegateProxy(
        /* @NonNull */
        final ServiceReference persistenceDelegateReference )
    {
        assert persistenceDelegateReference != null;

        lock_ = new Object();
        persistenceDelegateReference_ = persistenceDelegateReference;
        isDisposed_ = false;
        persistenceDelegate_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Disposes of the resources used by this instance.
     */
    void dispose()
    {
        synchronized( lock_ )
        {
            assert !isDisposed_;

            if( persistenceDelegate_ != null )
            {
                isDisposed_ = true;
                persistenceDelegate_ = null;
                Activator.getDefault().getBundleContext().ungetService( persistenceDelegateReference_ );
            }
        }
    }

    /**
     * Gets the actual persistence delegate associated with this proxy.
     * 
     * @return The actual persistence delegate associated with this proxy; never
     *         {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the actual persistence delegate cannot be obtained.
     */
    /* @NonNull */
    private PersistenceDelegate getActualPersistenceDelegate()
    {
        synchronized( lock_ )
        {
            assertStateLegal( !isDisposed_, Messages.PersistenceDelegateProxy_getActualPersistenceDelegate_proxyDisposed );

            if( persistenceDelegate_ == null )
            {
                persistenceDelegate_ = (PersistenceDelegate)Activator.getDefault().getBundleContext().getService( persistenceDelegateReference_ );
            }

            assertStateLegal( persistenceDelegate_ != null, Messages.PersistenceDelegateProxy_getActualPersistenceDelegate_actualObjectNotAvailable );
            return persistenceDelegate_;
        }
    }

    /*
     * @see org.gamegineer.common.persistence.schemes.beans.IAdvancedPersistenceDelegate#getContextClassLoader()
     */
    @Override
    public ClassLoader getContextClassLoader()
    {
        final PersistenceDelegate actualPersistenceDelegate = getActualPersistenceDelegate();
        if( actualPersistenceDelegate instanceof IAdvancedPersistenceDelegate )
        {
            final ClassLoader contextClassLoader = ((IAdvancedPersistenceDelegate)actualPersistenceDelegate).getContextClassLoader();
            if( contextClassLoader != null )
            {
                return contextClassLoader;
            }
        }

        return actualPersistenceDelegate.getClass().getClassLoader();
    }

    /*
     * @see java.beans.PersistenceDelegate#initialize(java.lang.Class, java.lang.Object, java.lang.Object, java.beans.Encoder)
     */
    @Override
    protected void initialize(
        final Class<?> type,
        final Object oldInstance,
        final Object newInstance,
        final Encoder out )
    {
        try
        {
            final Method method = PersistenceDelegate.class.getDeclaredMethod( "initialize", Class.class, Object.class, Object.class, Encoder.class ); //$NON-NLS-1$
            method.setAccessible( true );
            method.invoke( getActualPersistenceDelegate(), type, oldInstance, newInstance, out );
        }
        catch( final NoSuchMethodException e )
        {
            throw new AssertionError( e );
        }
        catch( final IllegalAccessException e )
        {
            throw new AssertionError( e );
        }
        catch( final InvocationTargetException e )
        {
            throw TaskUtils.launderThrowable( e.getCause() );
        }
    }

    /*
     * @see java.beans.PersistenceDelegate#instantiate(java.lang.Object, java.beans.Encoder)
     */
    @Override
    protected Expression instantiate(
        final Object oldInstance,
        final Encoder out )
    {
        try
        {
            final Method method = PersistenceDelegate.class.getDeclaredMethod( "instantiate", Object.class, Encoder.class ); //$NON-NLS-1$
            method.setAccessible( true );
            return (Expression)method.invoke( getActualPersistenceDelegate(), oldInstance, out );
        }
        catch( final NoSuchMethodException e )
        {
            throw new AssertionError( e );
        }
        catch( final IllegalAccessException e )
        {
            throw new AssertionError( e );
        }
        catch( final InvocationTargetException e )
        {
            throw TaskUtils.launderThrowable( e.getCause() );
        }
    }

    /*
     * @see java.beans.PersistenceDelegate#mutatesTo(java.lang.Object, java.lang.Object)
     */
    @Override
    @SuppressWarnings( "boxing" )
    protected boolean mutatesTo(
        final Object oldInstance,
        final Object newInstance )
    {
        try
        {
            final Method method = PersistenceDelegate.class.getDeclaredMethod( "mutatesTo", Object.class, Object.class ); //$NON-NLS-1$
            method.setAccessible( true );
            return (Boolean)method.invoke( getActualPersistenceDelegate(), oldInstance, newInstance );
        }
        catch( final NoSuchMethodException e )
        {
            throw new AssertionError( e );
        }
        catch( final IllegalAccessException e )
        {
            throw new AssertionError( e );
        }
        catch( final InvocationTargetException e )
        {
            throw TaskUtils.launderThrowable( e.getCause() );
        }
    }

    /*
     * @see org.gamegineer.common.persistence.schemes.beans.IAdvancedPersistenceDelegate#replaceObject(java.lang.Object)
     */
    @Override
    public Object replaceObject(
        final Object obj )
    {
        final PersistenceDelegate actualPersistenceDelegate = getActualPersistenceDelegate();
        if( actualPersistenceDelegate instanceof IAdvancedPersistenceDelegate )
        {
            return ((IAdvancedPersistenceDelegate)actualPersistenceDelegate).replaceObject( obj );
        }


        return obj;
    }

    /*
     * @see org.gamegineer.common.persistence.schemes.beans.IAdvancedPersistenceDelegate#resolveObject(java.lang.Object)
     */
    @Override
    public Object resolveObject(
        final Object obj )
    {
        final PersistenceDelegate actualPersistenceDelegate = getActualPersistenceDelegate();
        if( actualPersistenceDelegate instanceof IAdvancedPersistenceDelegate )
        {
            return ((IAdvancedPersistenceDelegate)actualPersistenceDelegate).resolveObject( obj );
        }

        return obj;
    }
}
