/*
 * LocalizedStringReplacer.java
 * Copyright 2008-2009 Gamegineer.org
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
 * Created on Feb 26, 2009 at 11:23:06 PM.
 */

package org.gamegineer.game.internal.ui.system.bindings.xml;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import javax.xml.bind.Unmarshaller;
import net.jcip.annotations.Immutable;
import org.gamegineer.game.internal.ui.Loggers;
import org.gamegineer.game.ui.system.bindings.xml.IStringBundle;
import org.gamegineer.game.ui.system.bindings.xml.LocaleNeutralKey;

/**
 * Replaces locale-neutral keys with localized strings as JAXB objects are
 * unmarshalled.
 * 
 * <p>
 * When an instance of this class is attached to a JAXB Unmarshaller, it will
 * inspect each unmarshalled object looking for fields of type {@code String}
 * that have been annotated with the {@code Localizable} annotation. If the
 * field value represents a decorated locale-neutral key (according to the
 * {@link LocaleNeutralKey#isDecoratedKey(String)} method), it will be used to
 * look up the corresponding localized string in the string bundle passed to the
 * replacer. If a localized string exists within the string bundle, it will
 * replace the field value.
 * </p>
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class LocalizedStringReplacer
    extends Unmarshaller.Listener
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The string bundle containing the localized strings. */
    private final IStringBundle bundle_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code LocalizedStringReplacer} class.
     * 
     * @param bundle
     *        The string bundle containing the localized strings; must not be
     *        {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code bundle} is {@code null}.
     */
    public LocalizedStringReplacer(
        /* @NonNull */
        final IStringBundle bundle )
    {
        assertArgumentNotNull( bundle, "bundle" ); //$NON-NLS-1$

        bundle_ = bundle;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see javax.xml.bind.Unmarshaller.Listener#afterUnmarshal(java.lang.Object, java.lang.Object)
     */
    @Override
    public void afterUnmarshal(
        final Object target,
        @SuppressWarnings( "unused" )
        final Object parent )
    {
        assertArgumentNotNull( target, "target" ); //$NON-NLS-1$

        for( final Field field : getLocalizableStringFields( target.getClass() ) )
        {
            field.setAccessible( true );
            final String localeNeutralKey = getLocaleNeutralKey( target, field );
            if( localeNeutralKey != null )
            {
                final String localizedString = bundle_.getString( localeNeutralKey );
                if( localizedString != null )
                {
                    setLocalizedString( target, field, localizedString );
                }
            }
        }
    }

    /**
     * Gets the locale-neutral key from the specified field if applicable.
     * 
     * @param obj
     *        The object; must not be {@code null}.
     * @param field
     *        The field; must not be {@code null}.
     * 
     * @return The locale-neutral key read from the specified field or {@code
     *         null} if the field does not contain a locale-neutral key.
     */
    /* @Nullable */
    private static String getLocaleNeutralKey(
        /* @NonNull */
        final Object obj,
        /* @NonNull */
        final Field field )
    {
        assert obj != null;
        assert field != null;

        try
        {
            final String value = (String)field.get( obj );
            if( (value != null) && LocaleNeutralKey.isDecoratedKey( value ) )
            {
                return LocaleNeutralKey.undecorateKey( value );
            }
        }
        catch( final IllegalAccessException e )
        {
            Loggers.SYSTEM.log( Level.SEVERE, Messages.LocalizedStringReplacer_fieldAccess_accessError, e );
        }

        return null;
    }

    /**
     * Gets the localizable string fields for the specified class.
     * 
     * @param type
     *        The class; must not be {@code null}.
     * 
     * @return The localizable string fields for the specified class; never
     *         {@code null}.
     */
    /* @NonNull */
    private static Collection<Field> getLocalizableStringFields(
        /* @NonNull */
        final Class<?> type )
    {
        assert type != null;

        final Collection<Field> fields = new ArrayList<Field>();
        for( final Field field : type.getDeclaredFields() )
        {
            if( field.isAnnotationPresent( Localizable.class ) && (field.getType() == String.class) )
            {
                fields.add( field );
            }
        }
        return fields;
    }

    /**
     * Sets the specified field to the specified localized string.
     * 
     * @param obj
     *        The object; must not be {@code null}.
     * @param field
     *        The field; must not be {@code null}.
     * @param localizedString
     *        The localized string; must not be {@code null}.
     */
    private static void setLocalizedString(
        /* @NonNull */
        final Object obj,
        /* @NonNull */
        final Field field,
        /* @NonNull */
        final String localizedString )
    {
        assert obj != null;
        assert field != null;
        assert localizedString != null;

        try
        {
            field.set( obj, localizedString );
        }
        catch( final IllegalAccessException e )
        {
            Loggers.SYSTEM.log( Level.SEVERE, Messages.LocalizedStringReplacer_fieldAccess_accessError, e );
        }
    }
}
