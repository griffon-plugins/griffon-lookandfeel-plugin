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
package griffon.plugins.lookandfeel;

import griffon.core.artifact.GriffonModel;
import griffon.metadata.ArtifactProviderFor;
import org.codehaus.griffon.runtime.core.artifact.AbstractGriffonModel;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.swing.DefaultComboBoxModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

/**
 * @author Andres Almiray
 */
@ArtifactProviderFor(GriffonModel.class)
public class SelectorModel extends AbstractGriffonModel {
    public static final String SELECTED_HANDLER = "selectedHandler";
    public static final String SELECTED_THEME = "selectedTheme";

    private final DefaultComboBoxModel handlers = new DefaultComboBoxModel();
    private final DefaultComboBoxModel themes = new DefaultComboBoxModel();
    private LookAndFeelHandler selectedHandler;
    private LookAndFeelDescriptor selectedTheme;
    private boolean reset;

    public SelectorModel() {
        addPropertyChangeListener(SELECTED_HANDLER, new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                LookAndFeelHandler newHandler = (LookAndFeelHandler) evt.getNewValue();
                if (newHandler == null) return;

                // sync comboModel
                LookAndFeelHandler sh = (LookAndFeelHandler) handlers.getSelectedItem();
                if (sh != newHandler) {
                    handlers.setSelectedItem(newHandler);
                }

                themes.removeAllElements();
                setSelectedTheme(null);
                for (LookAndFeelDescriptor theme : newHandler.getSupportedLookAndFeelDescriptors()) {
                    themes.addElement(theme);
                }
            }
        });
        addPropertyChangeListener(SELECTED_THEME, new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                LookAndFeelDescriptor newTheme = (LookAndFeelDescriptor) evt.getNewValue();
                if (newTheme == null) return;

                // sync comboModel
                LookAndFeelDescriptor st = (LookAndFeelDescriptor) themes.getSelectedItem();
                if (st != newTheme) {
                    themes.setSelectedItem(newTheme);
                }
            }
        });
    }

    @Override
    public void mvcGroupInit(Map<String, Object> args) {
        setSelectedHandler(lookAndFeelManager.getCurrentLookAndFeelHandler());
        setSelectedTheme(lookAndFeelManager.getCurrentLookAndFeelDescriptor());
    }

    public DefaultComboBoxModel getHandlers() {
        return handlers;
    }

    public DefaultComboBoxModel getThemes() {
        return themes;
    }

    public LookAndFeelHandler getSelectedHandler() {
        return selectedHandler;
    }

    public void setSelectedHandler(LookAndFeelHandler selectedHandler) {
        firePropertyChange(SELECTED_HANDLER, this.selectedHandler, this.selectedHandler = selectedHandler);
    }

    public LookAndFeelDescriptor getSelectedTheme() {
        return selectedTheme;
    }

    public void setSelectedTheme(LookAndFeelDescriptor selectedTheme) {
        firePropertyChange(SELECTED_THEME, this.selectedTheme, this.selectedTheme = selectedTheme);
    }

    public boolean isReset() {
        return reset;
    }

    public void setReset(boolean reset) {
        firePropertyChange("reset", this.reset, this.reset = reset);
    }

    @Inject
    private LookAndFeelManager lookAndFeelManager;

    @PostConstruct
    private void setupHandlers() {
        for (LookAndFeelHandler handler : lookAndFeelManager.getLookAndFeelHandlers()) {
            handlers.addElement(handler);
        }
    }
}