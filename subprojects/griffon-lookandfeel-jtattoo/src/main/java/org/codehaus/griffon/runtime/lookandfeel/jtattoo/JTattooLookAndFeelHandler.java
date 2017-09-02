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
package org.codehaus.griffon.runtime.lookandfeel.jtattoo;

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
@Named("jtattoo")
public class JTattooLookAndFeelHandler extends AbstractLookAndFeelHandler {
    private static final List<JTattooLookAndFeelDescriptor> SUPPORTED_LAFS = CollectionUtils.<JTattooLookAndFeelDescriptor>list()
        .e(new JTattooLookAndFeelDescriptor("Acrylic", "com.jtattoo.plaf.acryl.AcrylLookAndFeel"))
        .e(new JTattooLookAndFeelDescriptor("Aero", "com.jtattoo.plaf.aero.AeroLookAndFeel"))
        .e(new JTattooLookAndFeelDescriptor("Aluminium", "com.jtattoo.plaf.aluminium.AluminiumLookAndFeel"))
        .e(new JTattooLookAndFeelDescriptor("Bernstein", "com.jtattoo.plaf.bernstein.BernsteinLookAndFeel"))
        .e(new JTattooLookAndFeelDescriptor("Fast", "com.jtattoo.plaf.fast.FastLookAndFeel"))
        .e(new JTattooLookAndFeelDescriptor("Graphite", "com.jtattoo.plaf.graphite.GraphiteLookAndFeel"))
        .e(new JTattooLookAndFeelDescriptor("HiFi", "com.jtattoo.plaf.hifi.HiFiLookAndFeel"))
        .e(new JTattooLookAndFeelDescriptor("Luna", "com.jtattoo.plaf.luna.LunaLookAndFeel"))
        .e(new JTattooLookAndFeelDescriptor("McWin", "com.jtattoo.plaf.mcwin.McWinLookAndFeel"))
        .e(new JTattooLookAndFeelDescriptor("Mint", "com.jtattoo.plaf.mint.MintLookAndFeel"))
        .e(new JTattooLookAndFeelDescriptor("Noire", "com.jtattoo.plaf.noire.NoireLookAndFeel"))
        .e(new JTattooLookAndFeelDescriptor("Smart", "com.jtattoo.plaf.smart.SmartLookAndFeel"))
        .e(new JTattooLookAndFeelDescriptor("Texture", "com.jtattoo.plaf.texture.TextureLookAndFeel"));

    private final LookAndFeelDescriptor[] supportedDescriptors;

    public JTattooLookAndFeelHandler() {
        super("JTattoo");
        this.supportedDescriptors = SUPPORTED_LAFS.toArray(new JTattooLookAndFeelDescriptor[SUPPORTED_LAFS.size()]);
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
        return descriptor instanceof JTattooLookAndFeelDescriptor;
    }

    @Nonnull
    @Override
    public LookAndFeelDescriptor[] getSupportedLookAndFeelDescriptors() {
        return supportedDescriptors;
    }

    private static class JTattooLookAndFeelDescriptor extends DefaultLookAndFeelDescriptor {
        public JTattooLookAndFeelDescriptor(@Nonnull String displayName, @Nonnull String lookAndFeelClassName) {
            super(createIdentifier(displayName), displayName, lookAndFeelClassName);
        }

        private static String createIdentifier(String displayName) {
            return "jtattoo-" + displayName.toLowerCase();
        }
    }
}
