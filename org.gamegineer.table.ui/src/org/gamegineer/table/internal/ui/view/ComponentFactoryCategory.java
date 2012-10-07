/*
 * ComponentFactoryCategory.java
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
 * Created on Oct 4, 2012 at 10:04:46 PM.
 */

package org.gamegineer.table.internal.ui.view;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.KeyStroke;
import net.jcip.annotations.Immutable;
import org.eclipse.core.runtime.IConfigurationElement;

/**
 * A component factory category.
 */
@Immutable
final class ComponentFactoryCategory
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The name of the configuration element attribute that represents the
     * category identifier.
     */
    private static final String ATTR_ID = "id"; //$NON-NLS-1$

    /**
     * The name of the configuration element attribute that represents the
     * category mnemonic.
     */
    private static final String ATTR_MNEMONIC = "mnemonic"; //$NON-NLS-1$

    /**
     * The name of the configuration element attribute that represents the
     * category name.
     */
    private static final String ATTR_NAME = "name"; //$NON-NLS-1$

    /**
     * The name of the configuration element attribute that represents the path
     * of the parent category.
     */
    private static final String ATTR_PARENT_CATEGORY = "parentCategory"; //$NON-NLS-1$

    /** The category path separator. */
    private static final String PATH_SEPARATOR = "/"; //$NON-NLS-1$

    /** The category identifier. */
    private final String id_;

    /** The category mnemonic. */
    private final int mnemonic_;

    /** The category name. */
    private final String name_;

    /**
     * The category path. The first element is the identifier of the furthest
     * ancestor. The last element is the identifier of this category.
     */
    private final List<String> path_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentFactoryCategory} class.
     * 
     * @param id
     *        The category identifier; must not be {@code null}.
     * @param name
     *        The category name; must not be {@code null}.
     * @param mnemonic
     *        The category mnemonic.
     * @param parentPath
     *        The path of the parent category; must not be {@code null}.The
     *        first element is the identifier of the furthest ancestor. The last
     *        element is the identifier of the nearest ancestor.
     */
    ComponentFactoryCategory(
        /* @NonNull */
        final String id,
        /* @NonNull */
        final String name,
        final int mnemonic,
        /* @NonNull */
        final List<String> parentPath )
    {
        assert id != null;
        assert name != null;
        assert parentPath != null;

        id_ = id;
        mnemonic_ = mnemonic;
        name_ = name;
        path_ = createPath( parentPath, id );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates an immutable view of the specified category path.
     * 
     * @param parentPath
     *        The path of the parent category; must not be {@code null}.
     * @param id
     *        The category identifier; must not be {@code null}.
     * 
     * @return An immutable view of the specified category path; never
     *         {@code null}.
     */
    /* @NonNull */
    private static List<String> createPath(
        /* @NonNull */
        final List<String> parentPath,
        /* @NonNull */
        final String id )
    {
        assert parentPath != null;
        assert id != null;

        final List<String> path = new ArrayList<String>( parentPath.size() + 1 );
        path.addAll( parentPath );
        path.add( id );
        return Collections.unmodifiableList( path );
    }

    /**
     * Decodes the specified string as a mnemonic.
     * 
     * @param source
     *        The string to decode; may be {@code null}.
     * 
     * @return The decoded mnemonic; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code source} does not represent a legal mnemonic.
     */
    private static int decodeMnemonic(
        /* @NonNull */
        final String source )
    {
        assert source != null;

        final KeyStroke keyStroke = KeyStroke.getKeyStroke( source );
        if( keyStroke == null )
        {
            throw new IllegalArgumentException( NonNlsMessages.ComponentFactoryCategory_decodeMnemonic_illegalSource );
        }

        return keyStroke.getKeyCode();
    }

    /**
     * Decodes the specified string as a parent category path.
     * 
     * @param source
     *        The string to decode; may be {@code null}.
     * 
     * @return The decoded parent category path; never {@code null}.
     */
    /* @NonNull */
    private static List<String> decodeParentCategoryPath(
        /* @Nullable */
        final String source )
    {
        if( source == null )
        {
            return Collections.emptyList();
        }

        return Arrays.asList( source.split( PATH_SEPARATOR ) );
    }

    /**
     * Gets the category identifier.
     * 
     * @return The category identifier; never {@code null}.
     */
    /* @NonNull */
    String getId()
    {
        return id_;
    }

    /**
     * Gets the category mnemonic.
     * 
     * @return The category mnemonic.
     */
    int getMnemonic()
    {
        return mnemonic_;
    }

    /**
     * Gets the category name.
     * 
     * @return The category name; never {@code null}.
     */
    /* @NonNull */
    String getName()
    {
        return name_;
    }

    /**
     * Gets an immutable view of the category path.
     * 
     * @return An immutable view of the category path; never {@code null}.
     */
    /* @NonNull */
    List<String> getPath()
    {
        return path_;
    }

    /**
     * Creates a new instance of the {@code ComponentFactoryCategory} class from
     * the specified component factory category configuration element.
     * 
     * @param configurationElement
     *        The component factory category configuration element; must not be
     *        {@code null}.
     * 
     * @return A new instance of the {@code ComponentFactoryCategory} class;
     *         never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code configurationElement} does not represent a legal
     *         component factory category.
     */
    /* @NonNull */
    static ComponentFactoryCategory fromConfigurationElement(
        /* @NonNull */
        final IConfigurationElement configurationElement )
    {
        assert configurationElement != null;

        final String id = configurationElement.getAttribute( ATTR_ID );
        assertArgumentLegal( id != null, "configurationElement", NonNlsMessages.ComponentFactoryCategory_fromConfigurationElement_missingId ); //$NON-NLS-1$

        final String name = configurationElement.getAttribute( ATTR_NAME );
        assertArgumentLegal( name != null, "configurationElement", NonNlsMessages.ComponentFactoryCategory_fromConfigurationElement_missingName ); //$NON-NLS-1$

        final String encodedMnemonic = configurationElement.getAttribute( ATTR_MNEMONIC );
        assertArgumentLegal( encodedMnemonic != null, "configurationElement", NonNlsMessages.ComponentFactoryCategory_fromConfigurationElement_missingMnemonic ); //$NON-NLS-1$
        final int mnemonic = decodeMnemonic( encodedMnemonic );

        final List<String> parentCategoryPath = decodeParentCategoryPath( configurationElement.getAttribute( ATTR_PARENT_CATEGORY ) );

        return new ComponentFactoryCategory( id, name, mnemonic, parentCategoryPath );
    }
}
