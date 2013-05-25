/*
 * AllFragmentTests.java
 * Copyright 2008-2013 Gamegineer.org
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
 * Created on May 24, 2013 at 9:50:04 PM.
 */

package org.gamegineer.test.core;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import junit.framework.Test;
import net.jcip.annotations.NotThreadSafe;
import org.junit.internal.runners.JUnit38ClassRunner;
import org.junit.runners.model.InitializationError;
import org.osgi.framework.Bundle;

/**
 * A test runner that runs all tests in an OSGi bundle fragment.
 * 
 * <p>
 * The test class must define the following methods:
 * </p>
 * 
 * <pre>
 * public static Bundle getHostBundle();
 * 
 * public static String getFragmentName();
 * </pre>
 */
@NotThreadSafe
@SuppressWarnings( "restriction" )
public final class AllFragmentTests
    extends JUnit38ClassRunner
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AllFragmentTests} class.
     * 
     * @param testClass
     *        The test class; must not be {@code null}.
     * 
     * @throws org.junit.runners.model.InitializationError
     *         If the test runner cannot be initialized.
     */
    public AllFragmentTests(
        /* @NonNull */
        final Class<?> testClass )
        throws InitializationError
    {
        super( buildTest( testClass ) );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Builds a test suite for the specified test class.
     * 
     * @param testClass
     *        The test class; must not be {@code null}.
     * 
     * @return A test suite; never {@code null}.
     * 
     * @throws org.junit.runners.model.InitializationError
     *         If an error occurs.
     */
    /* @NonNull */
    private static Test buildTest(
        /* @NonNull */
        final Class<?> testClass )
        throws InitializationError
    {
        assert testClass != null;

        final Bundle hostBundle = invokeStaticNoArgMethod( testClass, "getHostBundle", Bundle.class ); //$NON-NLS-1$
        final String fragmentName = invokeStaticNoArgMethod( testClass, "getFragmentName", String.class ); //$NON-NLS-1$
        return BundleSuiteBuilder.suite( hostBundle, fragmentName );
    }

    /**
     * Invokes the specified static no-argument method.
     * 
     * @param <T>
     *        The method return type.
     * 
     * @param type
     *        The type that declares the method; must not be {@code null}.
     * @param methodName
     *        The method name; must not be {@code null}.
     * @param returnType
     *        The method return type; must not be {@code null}.
     * 
     * @return The method return value; may be {@code null}.
     * 
     * @throws org.junit.runners.model.InitializationError
     *         If an error occurs.
     */
    /* @Nullable */
    private static <T> T invokeStaticNoArgMethod(
        /* @NonNull */
        final Class<?> type,
        /* @NonNull */
        final String methodName,
        /* @NonNull */
        final Class<T> returnType )
        throws InitializationError
    {
        try
        {
            final Method method = type.getMethod( methodName );
            if( !Modifier.isStatic( method.getModifiers() ) )
            {
                throw new Exception( String.format( "%s.%s() must be static", type.getName(), methodName ) ); //$NON-NLS-1$
            }

            return returnType.cast( method.invoke( null ) );
        }
        catch( final Exception e )
        {
            throw new InitializationError( e );
        }
    }
}
