/*
 * MockCommands.java
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
 * Created on Jun 12, 2008 at 10:28:32 PM.
 */

package org.gamegineer.engine.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;

/**
 * A factory for creating commands useful for testing an engine.
 */
public final class MockCommands
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MockCommands} class.
     */
    private MockCommands()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a command that adds the named attribute to the engine state.
     * 
     * @param attributeName
     *        The attribute name; must not be {@code null}.
     * @param attributeValue
     *        The attribute value; may be {@code null}.
     * 
     * @return A command that adds the named attribute to the engine state;
     *         never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code attributeName} is {@code null}.
     */
    /* @NonNull */
    public static ICommand<Void> createAddAttributeCommand(
        /* @NonNull */
        final AttributeName attributeName,
        /* @Nullable */
        final Object attributeValue )
    {
        assertArgumentNotNull( attributeName, "attributeName" ); //$NON-NLS-1$

        return new AddAttributeCommand( attributeName, attributeValue );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A command that adds an attribute to the engine state.
     */
    @CommandBehavior( writeLockRequired = true )
    private static final class AddAttributeCommand
        extends AbstractInvertibleCommand<Void>
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The attribute name. */
        private final AttributeName attributeName_;

        /** The attribute value. */
        private final Object attributeValue_;

        /**
         * Indicates the attribute should be added; otherwise it should be
         * removed.
         */
        private final boolean isAddCommand_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code AddAttributeCommand} class.
         * 
         * @param attributeName
         *        The attribute name; must not be {@code null}.
         * @param attributeValue
         *        The attribute value; may be {@code null}.
         */
        AddAttributeCommand(
            /* @NonNull */
            final AttributeName attributeName,
            /* @Nullable */
            final Object attributeValue )
        {
            this( attributeName, attributeValue, true );
        }

        /**
         * Initializes a new instance of the {@code AddAttributeCommand} class.
         * 
         * @param attributeName
         *        The attribute name; must not be {@code null}.
         * @param attributeValue
         *        The attribute value; may be {@code null}.
         * @param addAttribute
         *        {@code true} if the attribute should be added; {@code false}
         *        if the attribute should be removed.
         */
        private AddAttributeCommand(
            /* @NonNull */
            final AttributeName attributeName,
            /* @Nullable */
            final Object attributeValue,
            final boolean addAttribute )
        {
            assert attributeName != null;

            attributeName_ = attributeName;
            attributeValue_ = attributeValue;
            isAddCommand_ = addAttribute;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.engine.core.ICommand#execute(org.gamegineer.engine.core.IEngineContext)
         */
        public Void execute(
            final IEngineContext context )
        {
            if( isAddCommand_ )
            {
                context.getState().addAttribute( attributeName_, attributeValue_ );
            }
            else
            {
                context.getState().removeAttribute( attributeName_ );
            }

            return null;
        }

        /*
         * @see org.gamegineer.engine.core.IInvertibleCommand#getInverseCommand()
         */
        public IInvertibleCommand<Void> getInverseCommand()
        {
            return new AddAttributeCommand( attributeName_, attributeValue_, !isAddCommand_ );
        }
    }
}
