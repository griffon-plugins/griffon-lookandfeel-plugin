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
package org.codehaus.griffon.runtime.lookandfeel.skin;

import com.l2fprod.gui.plaf.skin.Skin;
import com.l2fprod.gui.plaf.skin.SkinLookAndFeel;
import griffon.plugins.lookandfeel.LookAndFeelDescriptor;
import griffon.util.CollectionUtils;
import org.codehaus.griffon.runtime.lookandfeel.AbstractLookAndFeelHandler;
import org.codehaus.griffon.runtime.lookandfeel.DefaultLookAndFeelDescriptor;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.swing.LookAndFeel;
import java.net.URL;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * @author Andres Almiray
 */
@Named("skin")
public class SkinLookAndFeelHandler extends AbstractLookAndFeelHandler {
    private static final List<SkinLookAndFeelDescriptor> SUPPORTED_LAFS = CollectionUtils.<SkinLookAndFeelDescriptor>list()
        .e(new SkinLookAndFeelDescriptor("Amarach", loadSkin("amarach")))
        .e(new SkinLookAndFeelDescriptor("Architect Blue", loadSkin("architectBlue")))
        .e(new SkinLookAndFeelDescriptor("Architect Olive", loadSkin("architectOlive")))
        .e(new SkinLookAndFeelDescriptor("b0sumi Ergo", loadSkin("b0sumiErgo")))
        .e(new SkinLookAndFeelDescriptor("b0sumi", loadSkin("b0sumi")))
        .e(new SkinLookAndFeelDescriptor("BeOS", loadSkin("BeOS")))
        .e(new SkinLookAndFeelDescriptor("Blue Metal", loadSkin("blueMetal")))
        .e(new SkinLookAndFeelDescriptor("Blue Turquesa", loadSkin("blueTurquesa")))
        .e(new SkinLookAndFeelDescriptor("ChaNinja Blue", loadSkin("chaNinja-Blue")))
        .e(new SkinLookAndFeelDescriptor("CoronaH", loadSkin("coronaH")))
        .e(new SkinLookAndFeelDescriptor("Cougar", loadSkin("cougar")))
        .e(new SkinLookAndFeelDescriptor("Crystal2", loadSkin("crystal2")))
        .e(new SkinLookAndFeelDescriptor("Default", loadSkin("default")))
        .e(new SkinLookAndFeelDescriptor("FatalE", loadSkin("fatalE")))
        .e(new SkinLookAndFeelDescriptor("Gfx Oasis", loadSkin("gfxOasis")))
        .e(new SkinLookAndFeelDescriptor("Gorilla", loadSkin("gorilla")))
        .e(new SkinLookAndFeelDescriptor("Hmm XP Blue", loadSkin("hmmXPBlue")))
        .e(new SkinLookAndFeelDescriptor("Hmm XP Mono Blue", loadSkin("hmmXPMonoBlue")))
        .e(new SkinLookAndFeelDescriptor("iBar", loadSkin("iBar")))
        .e(new SkinLookAndFeelDescriptor("Midnight", loadSkin("midnight")))
        .e(new SkinLookAndFeelDescriptor("MakkiX and MagraX", loadSkin("mmMagra-X")))
        .e(new SkinLookAndFeelDescriptor("Olive Green Luna XP", loadSkin("oliveGreenLunaXP")))
        .e(new SkinLookAndFeelDescriptor("Opus Luna Silver", loadSkin("opusLunaSilver")))
        .e(new SkinLookAndFeelDescriptor("Opus OS Blue", loadSkin("opusOSBlue")))
        .e(new SkinLookAndFeelDescriptor("Opus OS Deep", loadSkin("opusOSDeep")))
        .e(new SkinLookAndFeelDescriptor("Opus OS Olive", loadSkin("opusOSOlive")))
        .e(new SkinLookAndFeelDescriptor("QuickSilverR", loadSkin("quickSilverR")))
        .e(new SkinLookAndFeelDescriptor("Roue Blue", loadSkin("roueBlue")))
        .e(new SkinLookAndFeelDescriptor("Roue Brown", loadSkin("roueBrown")))
        .e(new SkinLookAndFeelDescriptor("Roue Green", loadSkin("roueGreen")))
        .e(new SkinLookAndFeelDescriptor("Royal Inspirat", loadSkin("royalInspirat")))
        .e(new SkinLookAndFeelDescriptor("Silver Luna XP", loadSkin("silverLunaXP")))
        .e(new SkinLookAndFeelDescriptor("SolunaR", loadSkin("solunaR")))
        .e(new SkinLookAndFeelDescriptor("Tiger Graphite", loadSkin("tigerGraphite")))
        .e(new SkinLookAndFeelDescriptor("Tiger", loadSkin("tiger")))
        .e(new SkinLookAndFeelDescriptor("Underling", loadSkin("underling")));

    private static Skin loadSkin(String name) {
        try {
            URL url = SkinLookAndFeelHandler.class.getResource("/skinlnf/themepacks/" + name + ".themepack");
            return SkinLookAndFeel.loadThemePack(url);
        } catch (Exception e) {
            throw new IllegalStateException("Cannot load skin theme " + name);
        }
    }

    private final LookAndFeelDescriptor[] supportedDescriptors;

    public SkinLookAndFeelHandler() {
        this.supportedDescriptors = SUPPORTED_LAFS.toArray(new SkinLookAndFeelDescriptor[SUPPORTED_LAFS.size()]);
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
        return descriptor instanceof SkinLookAndFeelDescriptor;
    }

    @Nonnull
    @Override
    public LookAndFeelDescriptor[] getSupportedLookAndFeelDescriptors() {
        return supportedDescriptors;
    }

    private static class SkinLookAndFeelDescriptor extends DefaultLookAndFeelDescriptor {
        private final Skin skin;

        public SkinLookAndFeelDescriptor(@Nonnull String displayName, @Nonnull Skin skin) {
            super(createIdentifier(displayName), displayName, new SkinLookAndFeel());
            this.skin = skin;
            SkinLookAndFeel.setSkin(skin);
        }

        @Override
        public void install() {
            SkinLookAndFeel.setSkin(skin);
            super.install();
        }

        @Override
        public boolean isCurrent() {
            return super.isCurrent() && SkinLookAndFeel.getSkin().equals(skin);
        }

        private static String createIdentifier(String displayName) {
            return "skin-" + displayName.toLowerCase();
        }
    }
}
