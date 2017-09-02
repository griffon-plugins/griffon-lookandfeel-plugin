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

import griffon.core.addon.GriffonAddon;
import griffon.core.injection.Module;
import griffon.inject.DependsOn;
import griffon.plugins.lookandfeel.LookAndFeelHandler;
import griffon.plugins.lookandfeel.LookAndFeelManager;
import org.codehaus.griffon.runtime.core.injection.AbstractModule;
import org.codehaus.griffon.runtime.lookandfeel.system.SystemLookAndFeelHandler;
import org.kordamp.jipsy.ServiceProviderFor;

import javax.inject.Named;

/**
 * @author Andres Almiray
 */
@DependsOn("swing")
@Named("lookandfeel")
@ServiceProviderFor(Module.class)
public class LookAndFeelModule extends AbstractModule {
    @Override
    protected void doConfigure() {
        // tag::bindings[]
        bind(LookAndFeelManager.class)
            .to(DefaultLookAndFeelManager.class)
            .asSingleton();

        bind(LookAndFeelHandler.class)
            .to(SystemLookAndFeelHandler.class)
            .asSingleton();

        bind(GriffonAddon.class)
            .to(LookAndFeelAddon.class)
            .asSingleton();
        // end::bindings[]
    }
}
