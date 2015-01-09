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
package org.codehaus.griffon.runtime.lookandfeel.office;

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
@Named("office")
public class OfficeLookAndFeelHandler extends AbstractLookAndFeelHandler {
    private static final List<OfficeLookAndFeelDescriptor> SUPPORTED_LAFS = CollectionUtils.<OfficeLookAndFeelDescriptor>list()
        .e(new OfficeLookAndFeelDescriptor("Office 2003", "org.fife.plaf.Office2003.Office2003LookAndFeel"))
        .e(new OfficeLookAndFeelDescriptor("Office XP", "org.fife.plaf.OfficeXP.OfficeXPLookAndFeel"))
        .e(new OfficeLookAndFeelDescriptor("VisualStudio 2005", "org.fife.plaf.VisualStudio2005.VisualStudio2005LookAndFeel"));

    private final LookAndFeelDescriptor[] supportedDescriptors;

    public OfficeLookAndFeelHandler() {
        this.supportedDescriptors = SUPPORTED_LAFS.toArray(new OfficeLookAndFeelDescriptor[SUPPORTED_LAFS.size()]);
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
        return descriptor instanceof OfficeLookAndFeelDescriptor;
    }

    @Nonnull
    @Override
    public LookAndFeelDescriptor[] getSupportedLookAndFeelDescriptors() {
        return supportedDescriptors;
    }

    private static class OfficeLookAndFeelDescriptor extends DefaultLookAndFeelDescriptor {
        public OfficeLookAndFeelDescriptor(@Nonnull String displayName, @Nonnull String lookAndFeelClassName) {
            super(createIdentifier(displayName), displayName, lookAndFeelClassName);
        }

        private static String createIdentifier(String displayName) {
            return "office-" + displayName.toLowerCase();
        }
    }
}
