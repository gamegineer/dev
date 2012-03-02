/*
 * TestCases.java
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
 * Created on Dec 2, 2008 at 10:48:23 PM.
 */

package org.gamegineer.test.core;

import java.io.File;
import net.jcip.annotations.ThreadSafe;
import org.osgi.framework.Bundle;
import org.osgi.framework.wiring.BundleRevision;
import org.osgi.framework.wiring.BundleWire;
import org.osgi.framework.wiring.BundleWiring;

/**
 * A collection of useful methods for writing test cases in an OSGi environment.
 */
@ThreadSafe
public final class TestCases
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TestCases} class.
     */
    private TestCases()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the named fragment in the specified host bundle.
     * 
     * @param hostBundle
     *        The host bundle; must not be {@code null}.
     * @param fragmentName
     *        The symbolic name of the fragment; must not be {@code null}.
     * 
     * @return The fragment or {@code null} if no such fragment exists within
     *         the host bundle.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code hostBundle} or {@code fragmentName} is {@code null}.
     */
    /* @Nullable */
    public static Bundle getFragment(
        /* @NonNull */
        final Bundle hostBundle,
        /* @NonNull */
        final String fragmentName )
    {
        if( hostBundle == null )
        {
            throw new NullPointerException( "hostBundle" ); //$NON-NLS-1$
        }
        if( fragmentName == null )
        {
            throw new NullPointerException( "fragmentName" ); //$NON-NLS-1$
        }

        final BundleWiring bundleWiring = hostBundle.adapt( BundleWiring.class );
        if( bundleWiring == null )
        {
            System.err.println( "bundle wiring not available" ); //$NON-NLS-1$
            return null;
        }

        for( final BundleWire bundleWire : bundleWiring.getProvidedWires( BundleRevision.HOST_NAMESPACE ) )
        {
            final Bundle fragment = bundleWire.getRequirerWiring().getBundle();
            if( fragmentName.equals( fragment.getSymbolicName() ) )
            {
                return fragment;
            }
        }

        return null;
    }

    /**
     * Gets the temporary directory for the specified test case.
     * 
     * <p>
     * The temporary directory can be used by the test case for persistent
     * storage during test execution. This directory and its contents will not
     * be automatically cleaned up due to any action taken by this class.
     * However, because the temporary directory is located within the bundle's
     * persistent storage area, it is likely the test execution environment will
     * delete this directory before the first test method is executed.
     * </p>
     * 
     * @param bundle
     *        The bundle that hosts the test case; must not be {@code null}.
     * @param testCaseClass
     *        The test case class; must not be {@code null}.
     * 
     * @return The temporary directory for the specified test case; never
     *         {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code bundle} or {@code testCaseClass} is {@code null}.
     * @throws java.lang.RuntimeException
     *         If an error occurs while obtaining the temporary directory.
     */
    /* @NonNull */
    public static File getTemporaryDirectory(
        /* @NonNull */
        final Bundle bundle,
        /* @NonNull */
        final Class<?> testCaseClass )
    {
        if( bundle == null )
        {
            throw new NullPointerException( "bundle" ); //$NON-NLS-1$
        }
        if( testCaseClass == null )
        {
            throw new NullPointerException( "testCaseClass" ); //$NON-NLS-1$
        }

        final File bundleDirectory = bundle.getBundleContext().getDataFile( "" ); //$NON-NLS-1$
        if( bundleDirectory == null )
        {
            throw new RuntimeException( "the OSGi platform does not have file system support" ); //$NON-NLS-1$
        }

        final String childPath = testCaseClass.getName().replace( '.', File.separatorChar );
        final File temporaryDirectory = new File( bundleDirectory, childPath );
        if( !temporaryDirectory.exists() )
        {
            if( !temporaryDirectory.mkdirs() )
            {
                throw new RuntimeException( "unable to create test case temporary directory" ); //$NON-NLS-1$
            }
        }

        return temporaryDirectory;
    }
}
