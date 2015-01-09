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
package org.codehaus.griffon.runtime.lookandfeel.jgoodies;

import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.PlasticTheme;
import com.jgoodies.looks.plastic.theme.BrownSugar;
import com.jgoodies.looks.plastic.theme.DarkStar;
import com.jgoodies.looks.plastic.theme.DesertBlue;
import com.jgoodies.looks.plastic.theme.DesertBluer;
import com.jgoodies.looks.plastic.theme.DesertGreen;
import com.jgoodies.looks.plastic.theme.DesertRed;
import com.jgoodies.looks.plastic.theme.DesertYellow;
import com.jgoodies.looks.plastic.theme.ExperienceBlue;
import com.jgoodies.looks.plastic.theme.ExperienceGreen;
import com.jgoodies.looks.plastic.theme.ExperienceRoyale;
import com.jgoodies.looks.plastic.theme.LightGray;
import com.jgoodies.looks.plastic.theme.Silver;
import com.jgoodies.looks.plastic.theme.SkyBlue;
import com.jgoodies.looks.plastic.theme.SkyBluer;
import com.jgoodies.looks.plastic.theme.SkyGreen;
import com.jgoodies.looks.plastic.theme.SkyKrupp;
import com.jgoodies.looks.plastic.theme.SkyPink;
import com.jgoodies.looks.plastic.theme.SkyRed;
import com.jgoodies.looks.plastic.theme.SkyYellow;
import griffon.plugins.lookandfeel.LookAndFeelDescriptor;
import org.codehaus.griffon.runtime.lookandfeel.AbstractLookAndFeelHandler;
import org.codehaus.griffon.runtime.lookandfeel.DefaultLookAndFeelDescriptor;

import javax.annotation.Nonnull;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import java.util.ArrayList;
import java.util.List;

import static com.jgoodies.looks.plastic.PlasticLookAndFeel.getPlasticTheme;
import static java.util.Objects.requireNonNull;

/**
 * @author Andres Almiray
 */
public abstract class AbstractJGoodiesLookAndFeelHandler extends AbstractLookAndFeelHandler {
    protected final LookAndFeel lookAndFeel;

    private final JGoodiesLookAndFeelDescriptor[] supportedDescriptors;

    public AbstractJGoodiesLookAndFeelHandler(@Nonnull String name, @Nonnull LookAndFeel lookAndFeel) {
        super("JGoodies - " + name);
        this.lookAndFeel = lookAndFeel;

        List<JGoodiesLookAndFeelDescriptor> lafs = new ArrayList<>();
        lafs.add(new JGoodiesLookAndFeelDescriptor("BrownSugar", new BrownSugar()));
        lafs.add(new JGoodiesLookAndFeelDescriptor("DarkStar", new DarkStar()));
        lafs.add(new JGoodiesLookAndFeelDescriptor("DesertBlue", new DesertBlue()));
        lafs.add(new JGoodiesLookAndFeelDescriptor("DesertBluer", new DesertBluer()));
        lafs.add(new JGoodiesLookAndFeelDescriptor("DesertGreen", new DesertGreen()));
        lafs.add(new JGoodiesLookAndFeelDescriptor("DesertRed", new DesertRed()));
        lafs.add(new JGoodiesLookAndFeelDescriptor("DesertYellow", new DesertYellow()));
        lafs.add(new JGoodiesLookAndFeelDescriptor("ExperienceBlue", new ExperienceBlue()));
        lafs.add(new JGoodiesLookAndFeelDescriptor("ExperienceGreen", new ExperienceGreen()));
        lafs.add(new JGoodiesLookAndFeelDescriptor("ExperienceRoyale", new ExperienceRoyale()));
        lafs.add(new JGoodiesLookAndFeelDescriptor("LightGray", new LightGray()));
        lafs.add(new JGoodiesLookAndFeelDescriptor("Silver", new Silver()));
        lafs.add(new JGoodiesLookAndFeelDescriptor("SkyBlue", new SkyBlue()));
        lafs.add(new JGoodiesLookAndFeelDescriptor("SkyBluer", new SkyBluer()));
        lafs.add(new JGoodiesLookAndFeelDescriptor("SkyGreen", new SkyGreen()));
        lafs.add(new JGoodiesLookAndFeelDescriptor("SkyKrupp", new SkyKrupp()));
        lafs.add(new JGoodiesLookAndFeelDescriptor("SkyPink", new SkyPink()));
        lafs.add(new JGoodiesLookAndFeelDescriptor("SkyRed", new SkyRed()));
        lafs.add(new JGoodiesLookAndFeelDescriptor("SkyYellow", new SkyYellow()));
        this.supportedDescriptors = lafs.toArray(new JGoodiesLookAndFeelDescriptor[lafs.size()]);
    }

    @Override
    public boolean handles(@Nonnull LookAndFeel laf) {
        requireNonNull(laf, "Argument 'laf' must not be null");
        for (JGoodiesLookAndFeelDescriptor descriptor : supportedDescriptors) {
            if (descriptor.getLookAndFeel().getClass().getName().equals(laf.getClass().getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean handles(@Nonnull LookAndFeelDescriptor descriptor) {
        requireNonNull(descriptor, "Argument 'descriptor' must not be null");
        return descriptor instanceof JGoodiesLookAndFeelDescriptor;
    }

    @Nonnull
    @Override
    public LookAndFeelDescriptor[] getSupportedLookAndFeelDescriptors() {
        return supportedDescriptors;
    }

    private class JGoodiesLookAndFeelDescriptor extends DefaultLookAndFeelDescriptor {
        private final PlasticTheme theme;

        public JGoodiesLookAndFeelDescriptor(@Nonnull String displayName, @Nonnull PlasticTheme theme) {
            super(createIdentifier(displayName), displayName, lookAndFeel);
            this.theme = requireNonNull(theme, "Argument 'theme' must not be null");
        }

        @Override
        public void install() {
            PlasticLookAndFeel.setPlasticTheme(theme);
            super.install();
        }

        @Override
        public boolean isCurrent() {
            LookAndFeel laf = UIManager.getLookAndFeel();
            return laf instanceof PlasticLookAndFeel && getPlasticTheme().getClass().getName().equals(theme.getClass().getName());
        }
    }

    private static String createIdentifier(String displayName) {
        return "jgoodies-" + displayName.toLowerCase();
    }
}
