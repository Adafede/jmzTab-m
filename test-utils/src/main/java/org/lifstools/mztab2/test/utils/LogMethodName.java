/*
 * Copyright 2018 Nils Hoffmann <nils.hoffmann@isas.de>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.lifstools.mztab2.test.utils;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * JUnit 5 extension that logs the executed test method name before each test.
 *
 * @author nilshoffmann
 */
public class LogMethodName implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 80; i++) {
            sb.append("#");
        }
        sb.append("\n")
          .append("# ")
          .append(context.getDisplayName())
          .append("\n");
        for (int i = 0; i < 80; i++) {
            sb.append("#");
        }
        System.out.println(sb.toString());
    }
}
