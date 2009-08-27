/*
 * TestCases.java
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
 * Created on Dec 2, 2008 at 10:48:23 PM.
 */

package org.gamegineer.test.core;

import java.io.File;
import org.osgi.framework.Bundle;

/**
 * A collection of useful methods for writing test cases in an OSGi environment.
 */
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
