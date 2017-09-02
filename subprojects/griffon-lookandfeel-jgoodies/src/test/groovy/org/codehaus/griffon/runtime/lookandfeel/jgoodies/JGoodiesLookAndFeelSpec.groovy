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
package org.codehaus.griffon.runtime.lookandfeel.jgoodies

import griffon.core.test.GriffonUnitRule
import griffon.plugins.lookandfeel.LookAndFeelManager
import org.junit.Rule
import spock.lang.Specification
import spock.lang.Unroll

import javax.inject.Inject

import static org.codehaus.griffon.runtime.lookandfeel.LookAndFeelTestSupport.resetLookAndFeel
import static org.codehaus.griffon.runtime.lookandfeel.LookAndFeelTestSupport.setAndTestLookAndFeel

/**
 * @author Andres Almiray
 */
@Unroll
class JGoodiesLookAndFeelSpec extends Specification {
    @Inject
    private LookAndFeelManager lookAndFeelManager

    @Rule
    public final GriffonUnitRule griffon = new GriffonUnitRule()

    void "Verify handler '#handler' with theme '#theme'"() {
        setup:
        resetLookAndFeel()

        expect:
        setAndTestLookAndFeel(handler, theme, lookAndFeelManager)

        where:
        handler << [
            ['JGoodies - Plastic'] * THEME_NAMES.size(),
            ['JGoodies - Plastic3D'] * THEME_NAMES.size(),
            ['JGoodies - PlasticXP'] * THEME_NAMES.size()
        ].flatten()
        theme << [
            THEME_NAMES * 3
        ].flatten()
    }

    private static final List<String> THEME_NAMES = [
        'BrownSugar',
        'DarkStar',
        'DesertBlue',
        'DesertBluer',
        'DesertGreen',
        'DesertRed',
        'DesertYellow',
        'ExperienceBlue',
        'ExperienceGreen',
        'ExperienceRoyale',
        'LightGray',
        'Silver',
        'SkyBlue',
        'SkyBluer',
        'SkyGreen',
        'SkyKrupp',
        'SkyPink',
        'SkyRed',
        'SkyYellow'
    ]
}
