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
package org.codehaus.griffon.runtime.lookandfeel.seaglass;

import com.seaglasslookandfeel.SeaGlassLookAndFeel;
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
@Named("seaglass")
public class SeaglassLookAndFeelHandler extends AbstractLookAndFeelHandler {
    private final LookAndFeelDescriptor[] supportedDescriptors;

    public SeaglassLookAndFeelHandler() {
        this.supportedDescriptors = new LookAndFeelDescriptor[]{
            new SeaglassLookAndFeelDescriptor()
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
        return descriptor instanceof SeaglassLookAndFeelDescriptor;
    }

    @Nonnull
    @Override
    public LookAndFeelDescriptor[] getSupportedLookAndFeelDescriptors() {
        return supportedDescriptors;
    }

    private static class SeaglassLookAndFeelDescriptor extends DefaultLookAndFeelDescriptor {
        public SeaglassLookAndFeelDescriptor() {
            super("seaglass-seaglass", "Seaglass", new SeaGlassLookAndFeel());
        }
    }
}
