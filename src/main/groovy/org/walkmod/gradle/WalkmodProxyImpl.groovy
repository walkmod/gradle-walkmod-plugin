/*
 * Copyright 2017 walkmod.
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
package org.walkmod.gradle

import org.walkmod.WalkModFacade


/**
 * @author abelsromero
 */
class WalkmodProxyImpl implements WalkmodProxy {

    WalkModFacade delegate

    @Override
    void check(String... chains) {
        delegate.check(chains)
    }

    @Override
    void apply(String... chains) {
        delegate.apply(chains)
    }

    @Override
    void patch(String... chains) {
        delegate.patch(chains)
    }

    @Override
    void install() {
        delegate.install()
    }
}
