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

import org.gradle.api.tasks.Optional


class WalkmodPatchTask extends WalkmodAbstractTask {

	@Optional
	protected Boolean isPatchPerFile = true

	@Optional
	protected Boolean isPatchPerChange = false

	@Optional
	protected String patchFormat = "raw";

	@Override
	void buildDynamicParams(){
		super.buildDynamicParams()

		dynamicParams.put("patchPerFile", Boolean.toString(isPatchPerFile()))
		dynamicParams.put("patchPerChange", Boolean.toString(ispatchPerChange()))
		dynamicParams.put("patchFormat", getPatchFormat())
	}
	@Override
	void executeTask(String... chains) {
		walkmodProxy.patch(chains)
	}

	boolean isPatchPerFile() {
		extension.isPatchPerFile?: isPatchPerFile
	}

	boolean ispatchPerChange() {
		extension.isPatchPerChange ?: isPatchPerChange
	}

	String getPatchFormat(){
		extension.patchFormat ?: patchFormat
	}
}
