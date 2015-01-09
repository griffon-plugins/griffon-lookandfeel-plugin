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
package org.codehaus.griffon.runtime.lookandfeel.synthetica;

import griffon.plugins.lookandfeel.LookAndFeelDescriptor;
import org.codehaus.griffon.runtime.lookandfeel.AbstractLookAndFeelHandler;
import org.codehaus.griffon.runtime.lookandfeel.DefaultLookAndFeelDescriptor;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.swing.LookAndFeel;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * @author Andres Almiray
 */
@Named("synthetica")
public class SyntheticaLookAndFeelHandler extends AbstractLookAndFeelHandler {
    private static final List<SyntheticaLookAndFeelDescriptor> SUPPORTED_LAFS = new ArrayList<>();

    private final LookAndFeelDescriptor[] supportedDescriptors;

    static {
        addSyntheticaLookAndFeelDescriptor("Synthetica", "de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel");
        addSyntheticaLookAndFeelDescriptor("Alu Oxide", "de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel");
        addSyntheticaLookAndFeelDescriptor("Black Eye", "de.javasoft.plaf.synthetica.SyntheticaBlackEyeLookAndFeel");
        addSyntheticaLookAndFeelDescriptor("Black Moon", "de.javasoft.plaf.synthetica.SyntheticaBlackMoonLookAndFeel");
        addSyntheticaLookAndFeelDescriptor("Black Star", "de.javasoft.plaf.synthetica.SyntheticaBlackStarLookAndFeel");
        addSyntheticaLookAndFeelDescriptor("Blue Ice", "de.javasoft.plaf.synthetica.SyntheticaBlueIceLookAndFeel");
        addSyntheticaLookAndFeelDescriptor("Blue Light", "de.javasoft.plaf.synthetica.SyntheticaBlueLightLookAndFeel");
        addSyntheticaLookAndFeelDescriptor("Blue Moon", "de.javasoft.plaf.synthetica.SyntheticaBlueMoonLookAndFeel");
        addSyntheticaLookAndFeelDescriptor("Blue Steel", "de.javasoft.plaf.synthetica.SyntheticaBlueSteelLookAndFeel");
        addSyntheticaLookAndFeelDescriptor("Classy", "de.javasoft.plaf.synthetica.SyntheticaClassyLookAndFeel");
        addSyntheticaLookAndFeelDescriptor("Green Dream", "de.javasoft.plaf.synthetica.SyntheticaGreenDreamLookAndFeel");
        addSyntheticaLookAndFeelDescriptor("Mauve Metallic", "de.javasoft.plaf.synthetica.SyntheticaMauveMetallicLookAndFeel");
        addSyntheticaLookAndFeelDescriptor("Orange Metallic", "de.javasoft.plaf.synthetica.SyntheticaOrangeMetallicLookAndFeel");
        addSyntheticaLookAndFeelDescriptor("Silver Moon", "de.javasoft.plaf.synthetica.SyntheticaSilverMoonLookAndFeel");
        addSyntheticaLookAndFeelDescriptor("Simple2D", "de.javasoft.plaf.synthetica.SyntheticaSimple2DLookAndFeel");
        addSyntheticaLookAndFeelDescriptor("Sky Metallic", "de.javasoft.plaf.synthetica.SyntheticaSkyMetallicLookAndFeel");
        addSyntheticaLookAndFeelDescriptor("White Vision", "de.javasoft.plaf.synthetica.SyntheticaWhiteVisionLookAndFeel");
    }

    private static void addSyntheticaLookAndFeelDescriptor(@Nonnull String displayName, @Nonnull String lookAndFeelClassName) {
        try {
            SUPPORTED_LAFS.add(new SyntheticaLookAndFeelDescriptor(displayName, lookAndFeelClassName));
        } catch (IllegalArgumentException e) {
            // ignore
        }
    }

    public SyntheticaLookAndFeelHandler() {
        System.out.println(SUPPORTED_LAFS);
        this.supportedDescriptors = SUPPORTED_LAFS.toArray(new SyntheticaLookAndFeelDescriptor[SUPPORTED_LAFS.size()]);
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
        return descriptor instanceof SyntheticaLookAndFeelDescriptor;
    }

    @Nonnull
    @Override
    public LookAndFeelDescriptor[] getSupportedLookAndFeelDescriptors() {
        return supportedDescriptors;
    }

    private static class SyntheticaLookAndFeelDescriptor extends DefaultLookAndFeelDescriptor {
        public SyntheticaLookAndFeelDescriptor(@Nonnull String displayName, @Nonnull String lookAndFeelClassName) {
            super("synthetica-" + displayName.toLowerCase(), displayName, lookAndFeelClassName);
        }
    }
}
