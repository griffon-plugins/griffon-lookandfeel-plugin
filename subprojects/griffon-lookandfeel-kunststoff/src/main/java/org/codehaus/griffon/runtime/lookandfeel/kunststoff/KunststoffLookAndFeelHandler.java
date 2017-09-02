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
package org.codehaus.griffon.runtime.lookandfeel.kunststoff;

import com.incors.plaf.kunststoff.KunststoffLookAndFeel;
import com.incors.plaf.kunststoff.KunststoffTheme;
import com.incors.plaf.kunststoff.themes.KunststoffDesktopTheme;
import com.incors.plaf.kunststoff.themes.KunststoffNotebookTheme;
import com.incors.plaf.kunststoff.themes.KunststoffPresentationTheme;
import griffon.plugins.lookandfeel.LookAndFeelDescriptor;
import griffon.util.CollectionUtils;
import org.codehaus.griffon.runtime.lookandfeel.AbstractLookAndFeelHandler;
import org.codehaus.griffon.runtime.lookandfeel.DefaultLookAndFeelDescriptor;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.swing.LookAndFeel;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * @author Andres Almiray
 */
@Named("kunststoff")
public class KunststoffLookAndFeelHandler extends AbstractLookAndFeelHandler {
    private static final List<KunststoffLookAndFeelDescriptor> SUPPORTED_LAFS = CollectionUtils.<KunststoffLookAndFeelDescriptor>list()
        .e(new KunststoffLookAndFeelDescriptor("Desktop", new KunststoffDesktopTheme()))
        .e(new KunststoffLookAndFeelDescriptor("Notebook", new KunststoffNotebookTheme()))
        .e(new KunststoffLookAndFeelDescriptor("Presentation", new KunststoffPresentationTheme()));

    private final LookAndFeelDescriptor[] supportedDescriptors;

    public KunststoffLookAndFeelHandler() {
        super("Kunststoff");
        this.supportedDescriptors = SUPPORTED_LAFS.toArray(new KunststoffLookAndFeelDescriptor[SUPPORTED_LAFS.size()]);
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
        return descriptor instanceof KunststoffLookAndFeelDescriptor;
    }

    @Nonnull
    @Override
    public LookAndFeelDescriptor[] getSupportedLookAndFeelDescriptors() {
        return supportedDescriptors;
    }

    private static class KunststoffLookAndFeelDescriptor extends DefaultLookAndFeelDescriptor {
        private final KunststoffTheme theme;

        public KunststoffLookAndFeelDescriptor(@Nonnull String displayName, @Nonnull KunststoffTheme theme) {
            super(createIdentifier(displayName), displayName, new KunststoffLookAndFeel());
            this.theme = theme;
        }

        @Override
        public void install() {
            KunststoffLookAndFeel.setCurrentTheme(theme);
            super.install();
        }

        @Override
        public boolean isCurrent() {
            return super.isCurrent() && KunststoffLookAndFeel.getCurrentTheme().equals(theme);
        }

        private static String createIdentifier(String displayName) {
            return "kunststoff-" + displayName.toLowerCase();
        }
    }
}
