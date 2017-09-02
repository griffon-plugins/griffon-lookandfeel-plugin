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
package org.codehaus.griffon.runtime.lookandfeel.substance

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
class SubstanceLookAndFeelSpec extends Specification {
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
        handler     | theme
        'Substance' | 'Autum'
        'Substance' | 'BlackSteel'
        'Substance' | 'BlueSteel'
        'Substance' | 'Business'
        'Substance' | 'Cerulean'
        'Substance' | 'Challenger'
        'Substance' | 'CremeCoffee'
        'Substance' | 'Creme'
        'Substance' | 'DustCoffee'
        'Substance' | 'Dust'
        'Substance' | 'EmeralDusk'
        'Substance' | 'Gemini'
        'Substance' | 'GraphiteAqua'
        'Substance' | 'GraphiteGlass'
        'Substance' | 'Graphite'
        'Substance' | 'Magellan'
        'Substance' | 'Mariner'
        'Substance' | 'MistAqua'
        'Substance' | 'MistSilver'
        'Substance' | 'Moderate'
        'Substance' | 'NebulaBrickWall'
        'Substance' | 'Nebula'
        'Substance' | 'OfficeBlack2007'
        'Substance' | 'OfficeBlue2007'
        'Substance' | 'OfficeSilver2007'
        'Substance' | 'Raven'
        'Substance' | 'Sahara'
        'Substance' | 'Twilight'
    }
}
