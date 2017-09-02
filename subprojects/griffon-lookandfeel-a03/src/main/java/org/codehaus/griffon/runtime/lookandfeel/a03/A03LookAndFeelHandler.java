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
package org.codehaus.griffon.runtime.lookandfeel.a03;

import a03.swing.plaf.A03LookAndFeel;
import griffon.plugins.lookandfeel.LookAndFeelDescriptor;
import org.codehaus.griffon.runtime.lookandfeel.AbstractLookAndFeelHandler;
import org.codehaus.griffon.runtime.lookandfeel.DefaultLookAndFeelDescriptor;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.swing.LookAndFeel;

import static java.util.Objects.requireNonNull;

/**
 * @author Andres Almiray
 */
@Named("a03")
public class A03LookAndFeelHandler extends AbstractLookAndFeelHandler {
    private final LookAndFeelDescriptor[] supportedDescriptors;

    public A03LookAndFeelHandler() {
        this.supportedDescriptors = new LookAndFeelDescriptor[]{
            new A03LookAndFeelDescriptor()
        };
    }

    @Override
    public boolean handles(@Nonnull LookAndFeel laf) {
        requireNonNull(laf, "Argument 'laf' must not be null");
        for (LookAndFeelDescriptor descriptor : supportedDescriptors) {
            if (descriptor.getLookAndFeel().getClass().getName().equals(laf.getClass().getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean handles(@Nonnull LookAndFeelDescriptor descriptor) {
        requireNonNull(descriptor, "Argument 'descriptor' must not be null");
        return descriptor instanceof A03LookAndFeelDescriptor;
    }

    @Nonnull
    @Override
    public LookAndFeelDescriptor[] getSupportedLookAndFeelDescriptors() {
        return supportedDescriptors;
    }

    private static class A03LookAndFeelDescriptor extends DefaultLookAndFeelDescriptor {
        public A03LookAndFeelDescriptor() {
            super("a03-a03", "A03", new A03LookAndFeel());
        }
    }
}
