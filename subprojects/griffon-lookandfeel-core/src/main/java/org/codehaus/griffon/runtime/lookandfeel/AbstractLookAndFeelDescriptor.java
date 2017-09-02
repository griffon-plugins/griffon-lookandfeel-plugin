/*
 * Copyright 2014-2017 the original author or authors.
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

import griffon.plugins.lookandfeel.LookAndFeelDescriptor;

import javax.annotation.Nonnull;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.Component;

import static griffon.util.GriffonNameUtils.requireNonBlank;
import static java.util.Objects.requireNonNull;

/**
 * @author Andres Almiray
 */
public abstract class AbstractLookAndFeelDescriptor implements LookAndFeelDescriptor {
    private final String identifier;
    private final String displayName;

    public AbstractLookAndFeelDescriptor(@Nonnull String identifier, @Nonnull String displayName) {
        this.identifier = requireNonBlank(identifier, "Argument 'identifier' must not be blank");
        this.displayName = requireNonBlank(displayName, "Argument 'displayName' must not be blank");
    }

    @Nonnull
    public final String getIdentifier() {
        return identifier;
    }

    @Nonnull
    public final String getDisplayName() {
        return displayName;
    }

    public String toString() {
        return displayName;
    }

    public final int hashCode() {
        return identifier.hashCode();
    }

    public final boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof LookAndFeelDescriptor)) return false;

        LookAndFeelDescriptor other = (LookAndFeelDescriptor) obj;
        return identifier.equals(other.getIdentifier());
    }

    public final int compareTo(LookAndFeelDescriptor obj) {
        if (obj == null) return -1;
        if (obj == this) return 0;

        return identifier.compareTo(obj.getIdentifier());
    }

    public void preview(@Nonnull Component component) {
        requireNonNull(component, "Argument 'component' must not be null");
        install();
        SwingUtilities.updateComponentTreeUI(component);
    }

    public void install() {
        try {
            UIManager.setLookAndFeel(getLookAndFeel());
        } catch (Exception e) {
            // ignore
        }
    }

    public boolean isCurrent() {
        LookAndFeel currentLookAndFeel = UIManager.getLookAndFeel();
        return currentLookAndFeel != null && getLookAndFeel().getClass().getName().equals(currentLookAndFeel.getClass().getName());
    }
}
