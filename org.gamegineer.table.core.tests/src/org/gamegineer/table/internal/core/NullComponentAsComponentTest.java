/*
 * NullComponentAsComponentTest.java
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
 * Created on Jun 28, 2012 at 8:08:19 PM.
 */

package org.gamegineer.table.internal.core;

import java.lang.reflect.Method;
import org.gamegineer.table.core.AbstractComponentTestCase;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.NullComponent} class to ensure it
 * does not violate the contract of the
 * {@link org.gamegineer.table.core.IComponent} interface.
 */
public final class NullComponentAsComponentTest
    extends AbstractComponentTestCase<TableEnvironment, NullComponent>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NullComponentAsComponentTest}
     * class.
     */
    public NullComponentAsComponentTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#createComponent(org.gamegineer.table.core.ITableEnvironment)
     */
    @Override
    protected NullComponent createComponent(
        final TableEnvironment tableEnvironment )
    {
        return new NullComponent( tableEnvironment );
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#createTableEnvironment()
     */
    @Override
    protected TableEnvironment createTableEnvironment()
    {
        return new TableEnvironment();
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#fireComponentBoundsChanged(org.gamegineer.table.core.IComponent)
     */
    @Override
    protected void fireComponentBoundsChanged(
        final NullComponent component )
    {
        fireEvent( component, "fireComponentBoundsChanged" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#fireComponentOrientationChanged(org.gamegineer.table.core.IComponent)
     */
    @Override
    protected void fireComponentOrientationChanged(
        final NullComponent component )
    {
        fireEvent( component, "fireComponentOrientationChanged" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#fireComponentSurfaceDesignChanged(org.gamegineer.table.core.IComponent)
     */
    @Override
    protected void fireComponentSurfaceDesignChanged(
        final NullComponent component )
    {
        fireEvent( component, "fireComponentSurfaceDesignChanged" ); //$NON-NLS-1$
    }

    /**
     * Fires the event associated with the specified component method.
     * 
     * @param component
     *        The component; must not be {@code null}.
     * @param methodName
     *        The name of the method associated with the event; must not be
     *        {@code null}.
     */
    private static void fireEvent(
        /* @NonNull */
        final NullComponent component,
        /* @NonNull */
        final String methodName )
    {
        assert component != null;
        assert methodName != null;

        try
        {
            final Method method = NullComponent.class.getDeclaredMethod( methodName );
            method.setAccessible( true );
            method.invoke( component );
        }
        catch( final Exception e )
        {
            throw new AssertionError( e );
        }
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#testGetBounds_MatchesCurrentOrientationSurface()
     */
    @Override
    public void testGetBounds_MatchesCurrentOrientationSurface()
    {
        // FIXME: not supported
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#testGetBounds_Translate()
     */
    @Override
    public void testGetBounds_Translate()
    {
        // FIXME: not supported
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#testGetLocation_Translate()
     */
    @Override
    public void testGetLocation_Translate()
    {
        // FIXME: not supported
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#testGetOrigin_Translate()
     */
    @Override
    public void testGetOrigin_Translate()
    {
        // FIXME: not supported
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#testGetSize_MatchesCurrentOrientationSurface()
     */
    @Override
    public void testGetSize_MatchesCurrentOrientationSurface()
    {
        // FIXME: not supported
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#testGetSize_Translate()
     */
    @Override
    public void testGetSize_Translate()
    {
        // FIXME: not supported
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#testSetLocation_FiresComponentBoundsChangedEvent()
     */
    @Override
    public void testSetLocation_FiresComponentBoundsChangedEvent()
    {
        // FIXME: not supported
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#testSetLocation_Location_Copy()
     */
    @Override
    public void testSetLocation_Location_Copy()
    {
        // FIXME: not supported
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#testSetOrientation_FiresComponentOrientationChangedEvent()
     */
    @Override
    public void testSetOrientation_FiresComponentOrientationChangedEvent()
    {
        // FIXME: not supported
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#testSetOrigin_FiresComponentBoundsChangedEvent()
     */
    @Override
    public void testSetOrigin_FiresComponentBoundsChangedEvent()
    {
        // FIXME: not supported
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#testSetOrigin_Origin_Copy()
     */
    @Override
    public void testSetOrigin_Origin_Copy()
    {
        // FIXME: not supported
    }

    /*
     * @see org.gamegineer.table.core.AbstractComponentTestCase#testSetSurfaceDesign_FiresComponentSurfaceDesignChangedEvent()
     */
    @Override
    public void testSetSurfaceDesign_FiresComponentSurfaceDesignChangedEvent()
    {
        // FIXME: not supported
    }
}
