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
package org.codehaus.griffon.runtime.lookandfeel;

import griffon.plugins.lookandfeel.LookAndFeelDescriptor;
import griffon.plugins.lookandfeel.LookAndFeelHandler;
import griffon.plugins.lookandfeel.LookAndFeelManager;

import javax.annotation.Nonnull;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static griffon.util.GriffonNameUtils.requireNonBlank;
import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Andres Almiray
 */
public final class LookAndFeelTestSupport {
    private static boolean headless;

    static {
        try {
            Class jframe = LookAndFeelTestSupport.class.getClassLoader().loadClass("javax.swing.JFrame");
            final Constructor constructor = jframe.getConstructor(new Class[]{String.class});
            constructor.newInstance("test");
            headless = false;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            headless = true;
        }
    }

    private LookAndFeelTestSupport() {

    }

    public static boolean isHeadless() {
        return headless;
    }

    public static void runInsideUISync(@Nonnull Runnable runnable) {
        if (isHeadless()) return;
        requireNonNull(runnable, "runnable");
        try {
            SwingUtilities.invokeAndWait(runnable);
        } catch (InterruptedException | InvocationTargetException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void resetLookAndFeel() {
        runInsideUISync(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                    throw new IllegalStateException(e);
                }
            }
        });
    }

    public static void setAndTestLookAndFeel(@Nonnull final String handler, @Nonnull final String theme, @Nonnull final LookAndFeelManager lafManager) {
        runInsideUISync(new Runnable() {
            @Override
            public void run() {
                setLookAndFeel(handler, theme, lafManager);
                assertCurrentLookAndFeelIs(handler, theme, lafManager);
            }
        });
    }

    public static void assertCurrentLookAndFeelIs(@Nonnull String handler, @Nonnull String theme, @Nonnull LookAndFeelManager lafManager) {
        requireNonBlank(handler, "handler");
        requireNonBlank(theme, "theme");
        requireNonNull(lafManager);

        LookAndFeelHandler lafHandler = lafManager.getLookAndFeelHandler(handler);
        assertNotNull("LookAndFeelHandler named '" + handler + "' was not found", lafHandler);
        assertEquals(lafManager.getCurrentLookAndFeelHandler(), lafHandler);

        LookAndFeelDescriptor descriptor = lafHandler.getLookAndFeelDescriptor(theme);
        assertNotNull("LookAndFeelDescriptor named '" + theme + "' was not found", descriptor);
        assertEquals(lafManager.getCurrentLookAndFeelDescriptor(), descriptor);
    }

    public static void setLookAndFeel(@Nonnull String handler, @Nonnull String theme, @Nonnull LookAndFeelManager lafManager) {
        requireNonBlank(handler, "handler");
        requireNonBlank(theme, "theme");
        requireNonNull(lafManager);

        LookAndFeelHandler lafHandler = lafManager.getLookAndFeelHandler(handler);
        assertNotNull("LookAndFeelHandler named '" + handler + "' was not found", lafHandler);
        LookAndFeelDescriptor descriptor = lafHandler.getLookAndFeelDescriptor(theme);
        assertNotNull("LookAndFeelDescriptor named '" + theme + "' was not found", descriptor);
        descriptor.install();
    }
}
