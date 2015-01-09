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
package org.codehaus.griffon.runtime.lookandfeel.pagosoft;

import com.pagosoft.plaf.PgsLookAndFeel;
import com.pagosoft.plaf.PlafOptions;
import com.pagosoft.plaf.themes.ElegantGrayTheme;
import com.pagosoft.plaf.themes.JGoodiesThemes;
import com.pagosoft.plaf.themes.NativeColorTheme;
import com.pagosoft.plaf.themes.SilverTheme;
import com.pagosoft.plaf.themes.VistaTheme;
import griffon.plugins.lookandfeel.LookAndFeelDescriptor;
import griffon.util.CollectionUtils;
import org.codehaus.griffon.runtime.lookandfeel.AbstractLookAndFeelHandler;
import org.codehaus.griffon.runtime.lookandfeel.DefaultLookAndFeelDescriptor;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.swing.LookAndFeel;
import javax.swing.plaf.metal.MetalTheme;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * @author Andres Almiray
 */
@Named("pagosoft")
public class PagosoftLookAndFeelHandler extends AbstractLookAndFeelHandler {
    private static final List<PagosoftLookAndFeelDescriptor> SUPPORTED_LAFS = CollectionUtils.<PagosoftLookAndFeelDescriptor>list()
        .e(new PagosoftLookAndFeelDescriptor("ElegantGray", ElegantGrayTheme.getInstance()))
        .e(new PagosoftLookAndFeelDescriptor("JGoodies - BrownSugar", JGoodiesThemes.getBrownSugar()))
        .e(new PagosoftLookAndFeelDescriptor("JGoodies - DarkStar", JGoodiesThemes.getDarkStar()))
        .e(new PagosoftLookAndFeelDescriptor("JGoodies - DesertBlue", JGoodiesThemes.getDesertBlue()))
        .e(new PagosoftLookAndFeelDescriptor("NativeColor", new NativeColorTheme()))
        .e(new PagosoftLookAndFeelDescriptor("Silver", new SilverTheme()))
        .e(new PagosoftLookAndFeelDescriptor("Vista", new VistaTheme()));

    private final LookAndFeelDescriptor[] supportedDescriptors;

    public PagosoftLookAndFeelHandler() {
        super("Pagosoft");
        this.supportedDescriptors = SUPPORTED_LAFS.toArray(new PagosoftLookAndFeelDescriptor[SUPPORTED_LAFS.size()]);
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
        return descriptor instanceof PagosoftLookAndFeelDescriptor;
    }

    @Nonnull
    @Override
    public LookAndFeelDescriptor[] getSupportedLookAndFeelDescriptors() {
        return supportedDescriptors;
    }

    private static class PagosoftLookAndFeelDescriptor extends DefaultLookAndFeelDescriptor {
        private final MetalTheme theme;

        public PagosoftLookAndFeelDescriptor(@Nonnull String displayName, @Nonnull MetalTheme theme) {
            super(createIdentifier(displayName), displayName, new PgsLookAndFeel());
            this.theme = theme;
        }

        @Override
        public void install() {
            PlafOptions.setCurrentTheme(theme);
            PlafOptions.setAsLookAndFeel();
        }

        @Override
        public boolean isCurrent() {
            return super.isCurrent() && PgsLookAndFeel.getCurrentTheme().equals(theme);
        }

        private static String createIdentifier(String displayName) {
            return "pagosoft-" + displayName.toLowerCase();
        }
    }
}
