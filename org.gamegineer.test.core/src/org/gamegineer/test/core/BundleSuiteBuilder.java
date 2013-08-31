/*
 * BundleSuiteBuilder.java
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
 * Created on Apr 1, 2008 at 11:05:10 PM.
 */

package org.gamegineer.test.core;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
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
import net.jcip.annotations.ThreadSafe;
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
@ThreadSafe
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

        final String classpath = bundle.getHeaders().get( Constants.BUNDLE_CLASSPATH );
        if( classpath == null )
        {
            return Collections.singletonList( "." ); //$NON-NLS-1$
        }

        final Collection<String> classpathEntries = new ArrayList<>();
        for( final String classpathEntry : classpath.split( "\\s*,\\s*" ) ) //$NON-NLS-1$
        {
            classpathEntries.add( classpathEntry );
        }

        normalizeClasspathEntries( classpathEntries );
        return Collections.unmodifiableCollection( classpathEntries );
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

        final Collection<String> classpathEntries = new ArrayList<>();
        try( final InputStream is = url.openStream() )
        {
            final InputSource inputSource = new InputSource( is );
            final XPath xpath = XPathFactory.newInstance().newXPath();
            final String expression = "/classpath/classpathentry[@kind='output' or @kind='src']"; //$NON-NLS-1$
            final NodeList nodes = (NodeList)xpath.evaluate( expression, inputSource, XPathConstants.NODESET );
            for( int index = 0; index < nodes.getLength(); ++index )
            {
                final Element element = (Element)nodes.item( index );

                final String classpathEntryAttributeName;
                final String kind = element.getAttribute( "kind" ); //$NON-NLS-1$
                if( "output".equals( kind ) ) //$NON-NLS-1$
                {
                    classpathEntryAttributeName = "path"; //$NON-NLS-1$
                }
                else if( "src".equals( kind ) ) //$NON-NLS-1$
                {
                    classpathEntryAttributeName = "output"; //$NON-NLS-1$
                }
                else
                {
                    classpathEntryAttributeName = null;
                }

                if( classpathEntryAttributeName != null )
                {
                    final String classpathEntry = element.getAttribute( classpathEntryAttributeName );
                    if( classpathEntry != null )
                    {
                        classpathEntries.add( classpathEntry );
                    }
                }
            }
        }

        normalizeClasspathEntries( classpathEntries );
        return Collections.unmodifiableCollection( classpathEntries );
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

        final Collection<String> classNames = new ArrayList<>();

        // First assume the test is being run from the IDE and use the bundle's project information
        // to determine the classpath on which to search for test classes.
        for( final String classpathEntry : getClasspathEntriesFromProject( bundle ) )
        {
            classNames.addAll( getTestClassNamesFromEntry( bundle, classpathEntry ) );
        }
        if( !classNames.isEmpty() )
        {
            return classNames;
        }

        // Otherwise assume the test is being run from a deployment and use the bundle's manifest to
        // determine the classpath on which to search for test classes.
        for( final String classpathEntry : getClasspathEntriesFromManifest( bundle ) )
        {
            classNames.addAll( getTestClassNamesFromEntry( bundle, classpathEntry ) );
        }
        if( !classNames.isEmpty() )
        {
            return classNames;
        }

        return Collections.emptyList();
    }

    /**
     * Gets the fully-qualified name of each test class found in the specified
     * bundle entry.
     * 
     * @param bundle
     *        The bundle in which to search; must not be {@code null}.
     * @param path
     *        The path to the bundle entry in which to search; must not be
     *        {@code null}.
     * 
     * @return A read-only collection of test class names found; never
     *         {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    private static Collection<String> getTestClassNamesFromEntry(
        /* @NonNull */
        final Bundle bundle,
        /* @NonNull */
        final String path )
        throws Exception
    {
        assert bundle != null;
        assert path != null;

        return path.endsWith( ".jar" ) //$NON-NLS-1$
            ? getTestClassNamesFromJar( bundle, path ) //
            : getTestClassNamesFromPath( bundle, path );
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

        final Collection<String> classNames = new ArrayList<>();
        final Pattern CLASS_NAME_PATTERN = Pattern.compile( "^(.*Test)\\.class$" ); //$NON-NLS-1$
        try( final JarInputStream is = new JarInputStream( url.openStream() ) )
        {
            JarEntry entry = null;
            while( (entry = is.getNextJarEntry()) != null )
            {
                final Matcher matcher = CLASS_NAME_PATTERN.matcher( entry.getName() );
                if( matcher.matches() )
                {
                    classNames.add( matcher.group( 1 ).replace( '/', '.' ) );
                }
            }
        }

        return Collections.unmodifiableCollection( classNames );
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

        final Collection<String> classNames = new ArrayList<>();
        final Pattern CLASS_NAME_PATTERN = Pattern.compile( String.format( "^/%1$s/(.+)\\.class$", path ) ); //$NON-NLS-1$
        final String FILE_PATTERN = "*Test.class"; //$NON-NLS-1$
        final Enumeration<?> entries = bundle.findEntries( path, FILE_PATTERN, true );
        if( entries != null )
        {
            while( entries.hasMoreElements() )
            {
                final URL url = (URL)entries.nextElement();
                final Matcher matcher = CLASS_NAME_PATTERN.matcher( url.getPath() );
                if( !matcher.matches() )
                {
                    throw new RuntimeException( "unexpected bundle entry URL format" ); //$NON-NLS-1$
                }
                classNames.add( matcher.group( 1 ).replace( '/', '.' ) );
            }
        }

        return Collections.unmodifiableCollection( classNames );
    }

    /**
     * Normalizes the specified collection of classpath entries such that if any
     * path overlaps another, only the most specific path will be retained.
     * 
     * @param classpathEntries
     *        The collection of classpath entries; must not be {@code null}.
     */
    private static void normalizeClasspathEntries(
        /* @NonNull */
        final Collection<String> classpathEntries )
    {
        assert classpathEntries != null;

        for( final Iterator<String> iter = classpathEntries.iterator(); iter.hasNext(); )
        {
            final String classpathEntry = iter.next();
            for( final String otherClasspathEntry : classpathEntries )
            {
                if( otherClasspathEntry.startsWith( classpathEntry ) && !otherClasspathEntry.equals( classpathEntry ) )
                {
                    iter.remove();
                    break;
                }
            }
        }
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
     *        The symbolic name of the bundle fragment; must not be {@code null}
     *        .
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

        final Bundle fragmentBundle = TestCases.getFragment( hostBundle, fragmentName );
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

        final Bundle targetBundle = (fragmentBundle != null) ? fragmentBundle : hostBundle;
        final TestSuite suite = new TestSuite( targetBundle.getSymbolicName() );
        for( final String className : getTestClassNames( targetBundle ) )
        {
            suite.addTest( new JUnit4TestAdapter( hostBundle.loadClass( className ) ) );
        }

        return suite;
    }
}
