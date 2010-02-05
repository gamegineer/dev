/*
 * Activator.java
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
 * Created on Sep 16, 2009 at 10:40:53 PM.
 */

package org.gamegineer.table.internal.ui;

import net.jcip.annotations.ThreadSafe;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * The bundle activator for the org.gamegineer.table.ui bundle.
 */
@ThreadSafe
public final class Activator
    implements BundleActivator
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card pile base design user interfaces extension point identifier. */
    public static final String EXTENSION_CARD_PILE_BASE_DESIGN_UIS = "cardPileBaseDesignUIs"; //$NON-NLS-1$

    /** The card surface design user interfaces extension point identifier. */
    public static final String EXTENSION_CARD_SURFACE_DESIGN_UIS = "cardSurfaceDesignUIs"; //$NON-NLS-1$

    /** The symbolic name of the bundle. */
    public static final String SYMBOLIC_NAME = "org.gamegineer.table.ui"; //$NON-NLS-1$

    /** The singleton instance of the bundle activator. */
    private static Activator instance_;

    /** The bundle context. */
    private BundleContext context_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Activator} class.
     */
    public Activator()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the bundle context.
     * 
     * @return The bundle context; never {@code null}.
     */
    /* @NonNull */
    public BundleContext getBundleContext()
    {
        assert context_ != null;
        return context_;
    }

    /**
     * Gets the default instance of the bundle activator.
     * 
     * @return The default instance of the bundle activator; never {@code null}.
     */
    /* @NonNull */
    public static Activator getDefault()
    {
        assert instance_ != null;
        return instance_;
    }

    /*
     * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
     */
    public void start(
        final BundleContext context )
        throws Exception
    {
        assert context_ == null;
        context_ = context;
        assert instance_ == null;
        instance_ = this;

        Services.getDefault().open( context );
    }

    /*
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    public void stop(
        @SuppressWarnings( "unused" )
        final BundleContext context )
        throws Exception
    {
        Services.getDefault().close();

        instance_ = null;
        context_ = null;
    }
}
