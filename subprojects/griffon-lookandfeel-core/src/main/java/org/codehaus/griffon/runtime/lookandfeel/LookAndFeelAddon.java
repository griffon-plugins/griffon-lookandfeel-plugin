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
package org.codehaus.griffon.runtime.lookandfeel;

import griffon.core.GriffonApplication;
import griffon.inject.DependsOn;
import griffon.plugins.lookandfeel.LookAndFeelDescriptor;
import griffon.plugins.lookandfeel.LookAndFeelHandler;
import griffon.plugins.lookandfeel.LookAndFeelManager;
import org.codehaus.griffon.runtime.core.addon.AbstractGriffonAddon;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import javax.swing.UIManager;
import java.util.Collections;
import java.util.Map;

import static griffon.util.GriffonApplicationUtils.isMacOSX;

/**
 * @author Andres Almiray
 */
@DependsOn("swing")
@Named("lookandfeel")
public class LookAndFeelAddon extends AbstractGriffonAddon {
    @Inject
    private LookAndFeelManager lookAndFeelManager;

    public void onStartupStart(@Nonnull GriffonApplication application) {
        UIManager.put("ClassLoader", application.getApplicationClassLoader().get());

        String handlerName = application.getConfiguration().getAsString("lookandfeel.handler", "System");
        String themeName = application.getConfiguration().getAsString("lookandfeel.theme", isMacOSX() ? "System" : "Nimbus");
        Map<String, Object> props = application.getConfiguration().get("lookandfeel.properties", Collections.<String, Object>emptyMap());

        getLog().trace("Requested LookAndFeel settings {}:{}", handlerName, themeName);

        for (Map.Entry<String, Object> e : props.entrySet()) {
            UIManager.put(e.getKey(), e.getValue());
        }

        LookAndFeelHandler handler = lookAndFeelManager.getLookAndFeelHandler(handlerName);
        if (handler == null) {
            handler = lookAndFeelManager.getLookAndFeelHandler("System");
        }

        LookAndFeelDescriptor descriptor = handler.getLookAndFeelDescriptor(themeName);

        if (descriptor == null) {
            handler = lookAndFeelManager.getLookAndFeelHandler("System");
            descriptor = handler.getLookAndFeelDescriptor("System");
        }
        getLog().trace("Resolved LookAndFeel settings {}:{}", handler.getName(), descriptor.getDisplayName());

        lookAndFeelManager.install(descriptor);
    }
}
