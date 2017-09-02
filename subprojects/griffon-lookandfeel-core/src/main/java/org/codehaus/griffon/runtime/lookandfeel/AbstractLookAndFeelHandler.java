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
import griffon.plugins.lookandfeel.LookAndFeelHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.Component;

import static griffon.util.AnnotationUtils.nameFor;
import static griffon.util.GriffonNameUtils.capitalize;
import static griffon.util.GriffonNameUtils.requireNonBlank;
import static java.util.Objects.requireNonNull;

/**
 * @author Andres Almiray
 */
public abstract class AbstractLookAndFeelHandler implements LookAndFeelHandler {
    private static final String ERROR_DESCRIPTOR_NULL = "Argument 'descriptor' must not be null";
    private final String name;

    public AbstractLookAndFeelHandler() {
        this.name = capitalize(nameFor(getClass()));
    }

    public AbstractLookAndFeelHandler(String name) {
        requireNonBlank(name, "Argument 'name' must not be blank");
        this.name = name;
    }

    @Nonnull
    public final String getName() {
        return name;
    }

    public String toString() {
        return name;
    }

    public final int hashCode() {
        return name.hashCode();
    }

    public final boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (!(obj instanceof LookAndFeelHandler)) { return false; }

        LookAndFeelHandler other = (LookAndFeelHandler) obj;
        return name.equals(other.getName());
    }

    public final int compareTo(LookAndFeelHandler obj) {
        if (obj == null) { return -1; }
        if (obj == this) { return 0; }

        return name.compareTo(obj.getName());
    }

    @Override
    public void preview(@Nonnull LookAndFeelDescriptor descriptor, @Nonnull Component target) {
        requireNonNull(descriptor, ERROR_DESCRIPTOR_NULL);
        if (!handles(descriptor)) { return; }
        descriptor.preview(target);
    }

    @Nullable
    @Override
    public LookAndFeelDescriptor getLookAndFeelDescriptor(@Nonnull String descriptorName) {
        requireNonBlank(descriptorName, "Argument 'descriptorName' must not be null");
        for (LookAndFeelDescriptor descriptor : getSupportedLookAndFeelDescriptors()) {
            if (descriptor.getDisplayName().equals(descriptorName)) {
                return descriptor;
            }
        }
        return null;
    }
}
