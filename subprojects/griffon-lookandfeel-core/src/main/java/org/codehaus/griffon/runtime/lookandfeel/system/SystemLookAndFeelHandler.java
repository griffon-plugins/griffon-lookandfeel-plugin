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
package org.codehaus.griffon.runtime.lookandfeel.system;

import griffon.plugins.lookandfeel.LookAndFeelDescriptor;
import griffon.util.CollectionUtils;
import griffon.util.GriffonApplicationUtils;
import org.codehaus.griffon.runtime.lookandfeel.AbstractLookAndFeelHandler;
import org.codehaus.griffon.runtime.lookandfeel.DefaultLookAndFeelDescriptor;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Objects.requireNonNull;

/**
 * @author Andres Almiray
 */
@Named("system")
public class SystemLookAndFeelHandler extends AbstractLookAndFeelHandler {
    private static final List<SystemLookAndFeelDescriptor> SUPPORTED_LAFS = CollectionUtils.<SystemLookAndFeelDescriptor>list()
        .e(new SystemLookAndFeelDescriptor("Nimbus", getNimbusLAFName()))
        .e(new SystemLookAndFeelDescriptor("Metal", "javax.swing.plaf.metal.MetalLookAndFeel"))
        .e(new SystemLookAndFeelDescriptor("Motif", "com.sun.java.swing.plaf.motif.MotifLookAndFeel"))
        .e(new SystemLookAndFeelDescriptor("System", UIManager.getSystemLookAndFeelClassName()))
        .e(new SystemLookAndFeelDescriptor("CrossPlatform", UIManager.getCrossPlatformLookAndFeelClassName()));

    static {
        if (GriffonApplicationUtils.isWindows()) {
            SUPPORTED_LAFS.add(new SystemLookAndFeelDescriptor("Windows", "com.sun.java.swing.plaf.windows.WindowsLookAndFeel"));
            SUPPORTED_LAFS.add(new SystemLookAndFeelDescriptor("Windows Classic", "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel"));
        }
        Collections.sort(SUPPORTED_LAFS);
    }

    private final LookAndFeelDescriptor[] supportedDescriptors;

    public SystemLookAndFeelHandler() {
        this.supportedDescriptors = SUPPORTED_LAFS.toArray(new SystemLookAndFeelDescriptor[SUPPORTED_LAFS.size()]);
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
        return descriptor instanceof SystemLookAndFeelDescriptor;
    }

    @Nonnull
    @Override
    public LookAndFeelDescriptor[] getSupportedLookAndFeelDescriptors() {
        return supportedDescriptors;
    }

    private static String getNimbusLAFName() {
        for (String klass : asList(
            "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel",
            "sun.swing.plaf.nimbus.NimbusLookAndFeel",
            "org.jdesktop.swingx.plaf.nimbus.NimbusLookAndFeel")) {
            try {
                return Class.forName(klass, true, SystemLookAndFeelHandler.class.getClassLoader()).getName();
            } catch (Throwable t) {
                // ignore it, try the next on the list
            }
        }
        return null;
    }

    private static class SystemLookAndFeelDescriptor extends DefaultLookAndFeelDescriptor {
        public SystemLookAndFeelDescriptor(@Nonnull String displayName, @Nonnull LookAndFeel lookAndFeel) {
            super(createIdentifier(displayName), displayName, lookAndFeel);
        }

        public SystemLookAndFeelDescriptor(@Nonnull String displayName, @Nonnull String lookAndFeelClassName) {
            super(createIdentifier(displayName), displayName, lookAndFeelClassName);
        }

        public SystemLookAndFeelDescriptor(@Nonnull String displayName, @Nonnull Class<? extends LookAndFeel> lookAndFeel) {
            super(createIdentifier(displayName), displayName, lookAndFeel);
        }

        private static String createIdentifier(String displayName) {
            return "system-" + displayName.toLowerCase();
        }
    }
}
