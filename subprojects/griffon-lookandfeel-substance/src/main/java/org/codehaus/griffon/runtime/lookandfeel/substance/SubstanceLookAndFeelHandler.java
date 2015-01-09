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
package org.codehaus.griffon.runtime.lookandfeel.substance;

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
@Named("substance")
public class SubstanceLookAndFeelHandler extends AbstractLookAndFeelHandler {
    private static final List<SubstanceLookAndFeelDescriptor> SUPPORTED_LAFS = CollectionUtils.<SubstanceLookAndFeelDescriptor>list()
        .e(new SubstanceLookAndFeelDescriptor("Autum", "org.pushingpixels.substance.api.skin.SubstanceAutumnLookAndFeel"))
        .e(new SubstanceLookAndFeelDescriptor("BlackSteel", "org.pushingpixels.substance.api.skin.SubstanceBusinessBlackSteelLookAndFeel"))
        .e(new SubstanceLookAndFeelDescriptor("BlueSteel", "org.pushingpixels.substance.api.skin.SubstanceBusinessBlueSteelLookAndFeel"))
        .e(new SubstanceLookAndFeelDescriptor("Business", "org.pushingpixels.substance.api.skin.SubstanceBusinessLookAndFeel"))
        .e(new SubstanceLookAndFeelDescriptor("Cerulean", "org.pushingpixels.substance.api.skin.SubstanceCeruleanLookAndFeel"))
        .e(new SubstanceLookAndFeelDescriptor("Challenger", "org.pushingpixels.substance.api.skin.SubstanceChallengerDeepLookAndFeel"))
        .e(new SubstanceLookAndFeelDescriptor("CremeCoffee", "org.pushingpixels.substance.api.skin.SubstanceCremeCoffeeLookAndFeel"))
        .e(new SubstanceLookAndFeelDescriptor("Creme", "org.pushingpixels.substance.api.skin.SubstanceCremeLookAndFeel"))
        .e(new SubstanceLookAndFeelDescriptor("DustCoffee", "org.pushingpixels.substance.api.skin.SubstanceDustCoffeeLookAndFeel"))
        .e(new SubstanceLookAndFeelDescriptor("Dust", "org.pushingpixels.substance.api.skin.SubstanceDustLookAndFeel"))
        .e(new SubstanceLookAndFeelDescriptor("EmeralDusk", "org.pushingpixels.substance.api.skin.SubstanceEmeraldDuskLookAndFeel"))
        .e(new SubstanceLookAndFeelDescriptor("Gemini", "org.pushingpixels.substance.api.skin.SubstanceGeminiLookAndFeel"))
        .e(new SubstanceLookAndFeelDescriptor("GraphiteAqua", "org.pushingpixels.substance.api.skin.SubstanceGraphiteAquaLookAndFeel"))
        .e(new SubstanceLookAndFeelDescriptor("GraphiteGlass", "org.pushingpixels.substance.api.skin.SubstanceGraphiteGlassLookAndFeel"))
        .e(new SubstanceLookAndFeelDescriptor("Graphite", "org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel"))
        .e(new SubstanceLookAndFeelDescriptor("Magellan", "org.pushingpixels.substance.api.skin.SubstanceMagellanLookAndFeel"))
        .e(new SubstanceLookAndFeelDescriptor("Mariner", "org.pushingpixels.substance.api.skin.SubstanceMarinerLookAndFeel"))
        .e(new SubstanceLookAndFeelDescriptor("MistAqua", "org.pushingpixels.substance.api.skin.SubstanceMistAquaLookAndFeel"))
        .e(new SubstanceLookAndFeelDescriptor("MistSilver", "org.pushingpixels.substance.api.skin.SubstanceMistSilverLookAndFeel"))
        .e(new SubstanceLookAndFeelDescriptor("Moderate", "org.pushingpixels.substance.api.skin.SubstanceModerateLookAndFeel"))
        .e(new SubstanceLookAndFeelDescriptor("NebulaBrickWall", "org.pushingpixels.substance.api.skin.SubstanceNebulaBrickWallLookAndFeel"))
        .e(new SubstanceLookAndFeelDescriptor("Nebula", "org.pushingpixels.substance.api.skin.SubstanceNebulaLookAndFeel"))
        .e(new SubstanceLookAndFeelDescriptor("OfficeBlack2007", "org.pushingpixels.substance.api.skin.SubstanceOfficeBlack2007LookAndFeel"))
        .e(new SubstanceLookAndFeelDescriptor("OfficeBlue2007", "org.pushingpixels.substance.api.skin.SubstanceOfficeBlue2007LookAndFeel"))
        .e(new SubstanceLookAndFeelDescriptor("OfficeSilver2007", "org.pushingpixels.substance.api.skin.SubstanceOfficeSilver2007LookAndFeel"))
        .e(new SubstanceLookAndFeelDescriptor("Raven", "org.pushingpixels.substance.api.skin.SubstanceRavenLookAndFeel"))
        .e(new SubstanceLookAndFeelDescriptor("Sahara", "org.pushingpixels.substance.api.skin.SubstanceSaharaLookAndFeel"))
        .e(new SubstanceLookAndFeelDescriptor("Twilight", "org.pushingpixels.substance.api.skin.SubstanceTwilightLookAndFeel"));

    private final LookAndFeelDescriptor[] supportedDescriptors;

    public SubstanceLookAndFeelHandler() {
        super("Substance");
        this.supportedDescriptors = SUPPORTED_LAFS.toArray(new SubstanceLookAndFeelDescriptor[SUPPORTED_LAFS.size()]);
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
        return descriptor instanceof SubstanceLookAndFeelDescriptor;
    }

    @Nonnull
    @Override
    public LookAndFeelDescriptor[] getSupportedLookAndFeelDescriptors() {
        return supportedDescriptors;
    }

    private static class SubstanceLookAndFeelDescriptor extends DefaultLookAndFeelDescriptor {
        public SubstanceLookAndFeelDescriptor(@Nonnull String displayName, @Nonnull String lookAndFeelClassName) {
            super(createIdentifier(displayName), displayName, lookAndFeelClassName);
        }

        private static String createIdentifier(String displayName) {
            return "substance-" + displayName.toLowerCase();
        }
    }
}
