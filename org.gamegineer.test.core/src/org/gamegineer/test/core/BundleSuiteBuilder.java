/*
 * BundleSuiteBuilder.java
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
 * Created on Apr 1, 2008 at 11:05:10 PM.
 */

package org.gamegineer.test.core;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.osgi.framework.Bundle;
import org.osgi.framework.Constants;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * A JUnit test suite builder for OSGi bundles.
 * 
 * <p>
 * This class builds a JUnit test suite for all tests contained within an OSGi
 * bundle. It dynamically determines the bundle classpath on which to search for
 * tests. It properly handles both the case where the bundle is deployed in
 * source form (such as when run from an IDE) and when it is deployed in binary
 * form (such as when run from an installed product).
 * </p>
 */
public final class BundleSuiteBuilder
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code BundleSuiteBuilder} class.
     */
    private BundleSuiteBuilder()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the classpath entries from the specified bundle's manifest.
     * 
     * @param bundle
     *        The bundle whose classpath entries are desired; must not be
     *        {@code null}.
     * 
     * @return A read-only collection of classpath entries for the specified
     *         bundle; never {@code null}.
     */
    /* @NonNull */
    private static Collection<String> getClasspathEntriesFromManifest(
        /* @NonNull */
        final Bundle bundle )
    {
        assert bundle != null;

        final String classpath = (String)bundle.getHeaders().get( Constants.BUNDLE_CLASSPATH );
        if( classpath == null )
        {
            return Collections.emptyList();
        }

        final List<String> classpathEntryList = new ArrayList<String>();
        for( final String classpathEntry : classpath.split( "\\s*,\\s*" ) ) //$NON-NLS-1$
        {
            classpathEntryList.add( classpathEntry );
        }

        return Collections.unmodifiableList( classpathEntryList );
    }

    /**
     * Gets the classpath entries from the specified bundle's project
     * information.
     * 
     * @param bundle
     *        The bundle whose classpath entries are desired; must not be
     *        {@code null}.
     * 
     * @return A read-only collection of classpath entries for the specified
     *         bundle; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    private static Collection<String> getClasspathEntriesFromProject(
        /* @NonNull */
        final Bundle bundle )
        throws Exception
    {
        assert bundle != null;

        final URL url = bundle.getEntry( ".classpath" ); //$NON-NLS-1$
        if( url == null )
        {
            return Collections.emptyList();
        }

        final List<String> classpathEntryList = new ArrayList<String>();
        final InputStream is = url.openStream();
        try
        {
            final InputSource inputSource = new InputSource( is );
            final XPath xpath = XPathFactory.newInstance().newXPath();
            final String expression = "/classpath/classpathentry[@kind='output']"; //$NON-NLS-1$
            final NodeList nodes = (NodeList)xpath.evaluate( expression, inputSource, XPathConstants.NODESET );
            for( int index = 0; index < nodes.getLength(); ++index )
            {
                final Element element = (Element)nodes.item( index );
                final String classpathEntry = element.getAttribute( "path" ); //$NON-NLS-1$
                if( classpathEntry != null )
                {
                    classpathEntryList.add( classpathEntry );
                }
            }
        }
        finally
        {
            is.close();
        }

        return Collections.unmodifiableList( classpathEntryList );
    }

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
     */
    /* @Nullable */
    private static Bundle getFragment(
        /* @NonNull */
        final Bundle hostBundle,
        /* @NonNull */
        final String fragmentName )
    {
        assert hostBundle != null;
        assert fragmentName != null;

        for( final Enumeration<?> entries = hostBundle.findEntries( "META-INF", "MANIFEST.MF", false ); entries.hasMoreElements(); ) //$NON-NLS-1$ //$NON-NLS-2$
        {
            // OSGi bundle URLs are of the form "bundleentry://<bundle-id>/<entry-path>"
            final URL url = (URL)entries.nextElement();
            final Bundle bundle = hostBundle.getBundleContext().getBundle( Long.parseLong( url.getHost() ) );
            if( fragmentName.equals( bundle.getSymbolicName() ) )
            {
                return bundle;
            }
        }

        return null;
    }

    /**
     * Gets the fully-qualified name of each test class found in the specified
     * bundle.
     * 
     * <p>
     * This method searches various locations in the bundle for the presence of
     * test classes.
     * </p>
     * 
     * @param bundle
     *        The bundle in which to search; must not be {@code null}.
     * 
     * @return A read-only collection of test class names found; never
     *         {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    private static Collection<String> getTestClassNames(
        /* @NonNull */
        final Bundle bundle )
        throws Exception
    {
        assert bundle != null;

        final Collection<String> classNames = new ArrayList<String>();

        // First assume the test is being run from a deployment and use the bundle's manifest to
        // determine the classpath on which to search for test classes.
        for( final String classpathEntry : getClasspathEntriesFromManifest( bundle ) )
        {
            classNames.addAll( getTestClassNamesFromJar( bundle, classpathEntry ) );
        }
        if( !classNames.isEmpty() )
        {
            return classNames;
        }

        // Otherwise assume the test is being run from the IDE and use the bundle's project information
        // to determine the classpath on which to search for test classes.
        for( final String classpathEntry : getClasspathEntriesFromProject( bundle ) )
        {
            classNames.addAll( getTestClassNamesFromPath( bundle, classpathEntry ) );
        }
        if( !classNames.isEmpty() )
        {
            return classNames;
        }

        return Collections.emptyList();
    }

    /**
     * Gets the fully-qualified name of each test class found in the specified
     * JAR file.
     * 
     * @param bundle
     *        The bundle in which to search; must not be {@code null}.
     * @param path
     *        The path to the JAR file in which to search; must not be
     *        {@code null}.
     * 
     * @return A read-only collection of test class names found; never
     *         {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    private static Collection<String> getTestClassNamesFromJar(
        /* @NonNull */
        final Bundle bundle,
        /* @NonNull */
        final String path )
        throws Exception
    {
        assert bundle != null;
        assert path != null;

        final URL url = bundle.getEntry( path );
        if( url == null )
        {
            return Collections.emptyList();
        }

        final JarInputStream is = new JarInputStream( url.openStream() );
        final List<String> classNameList = new ArrayList<String>();
        final Pattern CLASS_NAME_PATTERN = Pattern.compile( "^(.*Test)\\.class$" ); //$NON-NLS-1$
        try
        {
            JarEntry entry = null;
            while( (entry = is.getNextJarEntry()) != null )
            {
                final Matcher matcher = CLASS_NAME_PATTERN.matcher( entry.getName() );
                if( matcher.matches() )
                {
                    classNameList.add( matcher.group( 1 ).replace( '/', '.' ) );
                }
            }
        }
        finally
        {
            is.close();
        }

        return Collections.unmodifiableList( classNameList );
    }

    /**
     * Gets the fully-qualified name of each test class found in the specified
     * bundle directory and all subdirectories.
     * 
     * @param bundle
     *        The bundle in which to search; must not be {@code null}.
     * @param path
     *        The bundle directory in which to search; must not be {@code null}.
     * 
     * @return A read-only collection of test class names found; never
     *         {@code null}.
     */
    /* @NonNull */
    private static Collection<String> getTestClassNamesFromPath(
        /* @NonNull */
        final Bundle bundle,
        /* @NonNull */
        final String path )
    {
        assert bundle != null;
        assert path != null;
        assert path.charAt( 0 ) != '/';

        final List<String> classNameList = new ArrayList<String>();
        final Pattern CLASS_NAME_PATTERN = Pattern.compile( String.format( "^/%1$s/(.+)\\.class$", path ) ); //$NON-NLS-1$
        final String FILE_PATTERN = "*Test.class"; //$NON-NLS-1$
        for( final Enumeration<?> entries = bundle.findEntries( path, FILE_PATTERN, true ); entries.hasMoreElements(); )
        {
            final URL url = (URL)entries.nextElement();
            final Matcher matcher = CLASS_NAME_PATTERN.matcher( url.getPath() );
            if( !matcher.matches() )
            {
                throw new RuntimeException( "unexpected bundle entry URL format" ); //$NON-NLS-1$
            }
            classNameList.add( matcher.group( 1 ).replace( '/', '.' ) );
        }

        return Collections.unmodifiableList( classNameList );
    }

    /**
     * Builds a test suite for the specified bundle.
     * 
     * @param bundle
     *        A bundle; must not be {@code null}. This must not be a bundle
     *        fragment.
     * 
     * @return A test suite containing all tests in the specified bundle; never
     *         {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code bundle} is {@code null}.
     * @throws java.lang.RuntimeException
     *         If an error occurs while building the test suite.
     */
    /* @NonNull */
    public static Test suite(
        /* @NonNull */
        final Bundle bundle )
    {
        if( bundle == null )
        {
            throw new NullPointerException( "bundle" ); //$NON-NLS-1$
        }

        try
        {
            return suite( bundle, (Bundle)null );
        }
        catch( final Exception e )
        {
            throw new RuntimeException( "an error occurred while building the test suite", e ); //$NON-NLS-1$
        }
    }

    /**
     * Builds a test suite for the specified bundle fragment.
     * 
     * @param hostBundle
     *        The bundle hosting the fragment; must not be {@code null}. This
     *        must not be a bundle fragment.
     * @param fragmentName
     *        The symbolic name of the bundle fragment; must not be {@code null}.
     * 
     * @return A test suite containing all tests in the specified bundle
     *         fragment; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the bundle fragment does not exist in the host bundle.
     * @throws java.lang.NullPointerException
     *         If {@code hostBundle} or {@code fragmentName} is {@code null}.
     * @throws java.lang.RuntimeException
     *         If an error occurs while building the test suite.
     */
    /* @NonNull */
    public static Test suite(
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

        final Bundle fragmentBundle = getFragment( hostBundle, fragmentName );
        if( fragmentBundle == null )
        {
            throw new IllegalArgumentException( String.format( "no such fragment '%1$s' in bundle '%2$s'", fragmentName, hostBundle.getSymbolicName() ) ); //$NON-NLS-1$
        }

        try
        {
            return suite( hostBundle, fragmentBundle );
        }
        catch( final Exception e )
        {
            throw new RuntimeException( "an error occurred while building the test suite", e ); //$NON-NLS-1$
        }
    }

    /**
     * Builds a test suite for an entire bundle or a bundle fragment.
     * 
     * @param hostBundle
     *        The host bundle; must not be {@code null}.
     * @param fragmentBundle
     *        The bundle fragment; may be {@code null}. If {@code null}, all
     *        tests in the host bundle are included in the test suite; otherwise
     *        only tests in the bundle fragment are included in the test suite.
     * 
     * @return A test suite containing an appropriate set of tests depending on
     *         the parameters passed to this method; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    private static Test suite(
        /* @NonNull */
        final Bundle hostBundle,
        /* @Nullable */
        final Bundle fragmentBundle )
        throws Exception
    {
        assert hostBundle != null;

        final TestSuite suite = new TestSuite();
        final Bundle targetBundle = (fragmentBundle != null) ? fragmentBundle : hostBundle;
        for( final String className : getTestClassNames( targetBundle ) )
        {
            suite.addTest( new JUnit4TestAdapter( hostBundle.loadClass( className ) ) );
        }

        return suite;
    }
}