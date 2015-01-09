/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.codehaus.griffon.runtime.lookandfeel;

import javax.annotation.Nonnull;
import javax.swing.LookAndFeel;

import static griffon.util.GriffonNameUtils.requireNonBlank;
import static java.util.Objects.requireNonNull;

/**
 * @author Andres Almiray
 */
public abstract class DefaultLookAndFeelDescriptor extends AbstractLookAndFeelDescriptor {
    private final LookAndFeel lookAndFeel;

    public DefaultLookAndFeelDescriptor(@Nonnull String identifier, @Nonnull String displayName, @Nonnull LookAndFeel lookAndFeel) {
        super(identifier, displayName);
        this.lookAndFeel = requireNonNull(lookAndFeel, "Argument 'lookAndFeel' must not be null");
    }

    public DefaultLookAndFeelDescriptor(@Nonnull String identifier, @Nonnull String displayName, @Nonnull String lookAndFeelClassName) {
        super(identifier, displayName);
        requireNonBlank(lookAndFeelClassName, "Argument 'lookAndFeelClassName' must not be blank");
        try {
            this.lookAndFeel = (LookAndFeel) Class.forName(lookAndFeelClassName, true, getClass().getClassLoader()).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public DefaultLookAndFeelDescriptor(@Nonnull String identifier, @Nonnull String displayName, @Nonnull Class<? extends LookAndFeel> lookAndFeel) {
        super(identifier, displayName);
        requireNonNull(lookAndFeel, "Argument 'lookAndFeel' must not be null");
        try {
            this.lookAndFeel = lookAndFeel.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Nonnull
    @Override
    public final LookAndFeel getLookAndFeel() {
        return lookAndFeel;
    }
}
