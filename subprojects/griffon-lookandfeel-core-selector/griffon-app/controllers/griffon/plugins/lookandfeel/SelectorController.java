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

import griffon.core.artifact.GriffonController;
import griffon.metadata.ArtifactProviderFor;
import griffon.transform.Threading;
import org.codehaus.griffon.runtime.core.artifact.AbstractGriffonController;

import javax.inject.Inject;

/**
 * @author Andres Almiray
 */
@Threading(Threading.Policy.INSIDE_UITHREAD_ASYNC)
@ArtifactProviderFor(GriffonController.class)
public class SelectorController extends AbstractGriffonController {
    private SelectorModel model;
    private SelectorView view;

    @Inject
    private LookAndFeelManager lookAndFeelManager;

    public void setModel(SelectorModel model) {
        this.model = model;
    }

    public void setView(SelectorView view) {
        this.view = view;
    }

    public void previewAction() {
        if (model.getSelectedTheme() == null) return;
        model.setReset(true);
        lookAndFeelManager.preview(model.getSelectedTheme(), view.getDesktop());
    }

    public void resetAction() {
        LookAndFeelHandler systemHandler = lookAndFeelManager.getLookAndFeelHandler("System");
        LookAndFeelDescriptor systemTheme = systemHandler.getLookAndFeelDescriptor("System");
        lookAndFeelManager.install(systemTheme);
        model.setSelectedHandler(systemHandler);
        model.setSelectedTheme(systemTheme);
    }

    public void showAction() {
        LookAndFeelDescriptor currentTheme = lookAndFeelManager.getCurrentLookAndFeelDescriptor();
        LookAndFeelHandler systemHandler = lookAndFeelManager.getLookAndFeelHandler("System");
        LookAndFeelDescriptor systemTheme = systemHandler.getLookAndFeelDescriptor("System");

        model.setSelectedHandler(lookAndFeelManager.getCurrentLookAndFeelHandler());
        model.setSelectedTheme(lookAndFeelManager.getCurrentLookAndFeelDescriptor());

        if (view.show()) {
            applyLookAndFeel(model.getSelectedTheme());
        } else if (currentTheme != null) {
            applyLookAndFeel(currentTheme);
        } else {
            applyLookAndFeel(systemTheme);
        }
    }

    private void applyLookAndFeel(final LookAndFeelDescriptor theme) {
        runInsideUIAsync(new Runnable() {
            @Override
            public void run() {
                lookAndFeelManager.install(theme);
            }
        });
    }
}