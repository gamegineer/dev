/*
 * HelpSetProviderProxy.java
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
 * Created on Jan 6, 2012 at 10:25:58 PM.
 */

package org.gamegineer.common.internal.ui.help;

import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import javax.help.HelpSet;
import javax.help.HelpSetException;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.internal.ui.Activator;
import org.gamegineer.common.ui.help.IHelpSetProvider;
import org.osgi.framework.ServiceReference;

/**
 * A proxy for lazily loading help set providers published via the service
 * registry.
 */
@ThreadSafe
final class HelpSetProviderProxy
    implements IHelpSetProvider
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The actual help set provider. */
    @GuardedBy( "lock_" )
    private IHelpSetProvider helpSetProvider_;

    /** The service registry reference to the help set provider. */
    private final ServiceReference helpSetProviderReference_;

    /** Indicates this instance has been disposed. */
    @GuardedBy( "lock_" )
    private boolean isDisposed_;

    /** The instance lock. */
    private final Object lock_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code HelpSetProviderProxy} class.
     * 
     * @param helpSetProviderReference
     *        The service registry reference to the help set provider; must not
     *        be {@code null}.
     */
    HelpSetProviderProxy(
        /* @NonNull */
        final ServiceReference helpSetProviderReference )
    {
        assert helpSetProviderReference != null;

        helpSetProvider_ = null;
        helpSetProviderReference_ = helpSetProviderReference;
        lock_ = new Object();
        isDisposed_ = false;
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

            if( helpSetProvider_ != null )
            {
                isDisposed_ = true;
                helpSetProvider_ = null;
                Activator.getDefault().getBundleContext().ungetService( helpSetProviderReference_ );
            }
        }
    }

    /**
     * Gets the actual help set provider associated with this proxy.
     * 
     * @return The actual help set provider associated with this proxy; never
     *         {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the actual help set provider cannot be obtained.
     */
    /* @NonNull */
    private IHelpSetProvider getActualHelpSetProvider()
    {
        synchronized( lock_ )
        {
            assertStateLegal( !isDisposed_, NonNlsMessages.HelpSetProviderProxy_getActualHelpSetProvider_proxyDisposed );

            if( helpSetProvider_ == null )
            {
                helpSetProvider_ = (IHelpSetProvider)Activator.getDefault().getBundleContext().getService( helpSetProviderReference_ );
            }

            assertStateLegal( helpSetProvider_ != null, NonNlsMessages.HelpSetProviderProxy_getActualHelpSetProvider_actualObjectNotAvailable );
            return helpSetProvider_;
        }
    }

    /*
     * @see org.gamegineer.common.ui.help.IHelpSetProvider#getHelpSet()
     */
    @Override
    public HelpSet getHelpSet()
        throws HelpSetException
    {
        return getActualHelpSetProvider().getHelpSet();
    }
}
