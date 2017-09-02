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
import griffon.exceptions.GriffonException;
import griffon.plugins.lookandfeel.LookAndFeelDescriptor;
import griffon.plugins.lookandfeel.LookAndFeelHandler;
import griffon.plugins.lookandfeel.LookAndFeelManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.inject.Inject;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Component;
import java.awt.Window;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static griffon.util.GriffonNameUtils.requireNonBlank;
import static java.util.Arrays.asList;
import static java.util.Objects.requireNonNull;

/**
 * @author Andres Almiray
 */
public class DefaultLookAndFeelManager implements LookAndFeelManager {
    private static final String ERROR_DESCRIPTOR_NULL = "Argument 'descriptor' must not be null";
    private final Object handlerLock = new Object[0];

    @GuardedBy("handler-lock")
    private LookAndFeelHandler lookAndFeelHandler;

    @Inject
    private GriffonApplication application;

    @Nonnull
    @Override
    public LookAndFeelHandler[] getLookAndFeelHandlers() {
        List<LookAndFeelHandler> handlers = new ArrayList<>(application.getInjector().getInstances(LookAndFeelHandler.class));
        Collections.sort(handlers);
        return handlers.toArray(new LookAndFeelHandler[handlers.size()]);
    }

    @Nonnull
    @Override
    public LookAndFeelHandler getCurrentLookAndFeelHandler() {
        synchronized (handlerLock) {
            if (lookAndFeelHandler == null) {
                for (LookAndFeelHandler handler : getLookAndFeelHandlers()) {
                    if (handler.handles(UIManager.getLookAndFeel())) {
                        lookAndFeelHandler = handler;
                        break;
                    }
                }
            }
            return lookAndFeelHandler;
        }
    }

    @Nonnull
    @Override
    public LookAndFeelDescriptor getCurrentLookAndFeelDescriptor() {
        LookAndFeelHandler handler = getCurrentLookAndFeelHandler();
        for (LookAndFeelDescriptor descriptor : handler.getSupportedLookAndFeelDescriptors()) {
            if (descriptor.isCurrent()) {
                return descriptor;
            }
        }
        return null;
    }

    @Override
    public void preview(@Nonnull LookAndFeelDescriptor descriptor, @Nonnull Component component) {
        requireNonNull(descriptor, ERROR_DESCRIPTOR_NULL);
        descriptor.preview(component);
    }

    @Override
    public void install(@Nonnull final LookAndFeelDescriptor descriptor) {
        requireNonNull(descriptor, ERROR_DESCRIPTOR_NULL);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LookAndFeel lookAndFeel = descriptor.getLookAndFeel();
                application.getEventRouter().publishEvent("LookAndFeelConfig", asList(
                    descriptor.getDisplayName(), lookAndFeel));

                try {
                    UIManager.setLookAndFeel(lookAndFeel);
                    synchronized (handlerLock) {
                        lookAndFeelHandler = null;
                    }
                } catch (UnsupportedLookAndFeelException e) {
                    throw new GriffonException("Cannot update LookAndFeel to " + descriptor.getLookAndFeel(), e);
                }

                for (Window window : Window.getWindows()) {
                    SwingUtilities.updateComponentTreeUI(window);
                }

                application.getEventRouter().publishEvent("LookAndFeelChanged", asList(
                    getCurrentLookAndFeelHandler(), descriptor, lookAndFeel));
            }
        });
    }

    @Nullable
    @Override
    public LookAndFeelHandler getLookAndFeelHandler(@Nonnull String handlerName) {
        requireNonBlank(handlerName, "Argument 'handlerName' must not be blank");
        for (LookAndFeelHandler handler : getLookAndFeelHandlers()) {
            if (handler.getName().equals(handlerName)) {
                return handler;
            }
        }
        return null;
    }
}
