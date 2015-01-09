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
package griffon.plugins.lookandfeel;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.LookAndFeel;
import java.awt.Component;

/**
 * @author Andres Almiray
 */
public interface LookAndFeelHandler extends Comparable<LookAndFeelHandler> {
    @Nonnull
    String getName();

    boolean handles(@Nonnull LookAndFeel laf);

    boolean handles(@Nonnull LookAndFeelDescriptor descriptor);

    @Nonnull
    LookAndFeelDescriptor[] getSupportedLookAndFeelDescriptors();

    void preview(@Nonnull LookAndFeelDescriptor descriptor, @Nonnull Component target);

    @Nullable
    LookAndFeelDescriptor getLookAndFeelDescriptor(@Nonnull String descriptorName);
}
