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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * JUnit 5 extension that extracts classpath resource files into a temporary
 * directory before all tests in a class and cleans up afterwards.
 *
 * @author nilshoffmann
 */
public class ExtractClassPathFiles implements BeforeAllCallback, AfterAllCallback {

    private final ClassPathFile[] classPathFiles;
    private final List<File> files = new LinkedList<>();
    private File baseFolder;
    private File tempDir;

    /**
     * @param classPathFiles an array of {@link ClassPathFile} objects to extract.
     */
    public ExtractClassPathFiles(ClassPathFile... classPathFiles) {
        this.classPathFiles = classPathFiles;
    }

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        tempDir = Files.createTempDirectory("mztabm-test-").toFile();
        baseFolder = tempDir;
        for (ClassPathFile resource : classPathFiles) {
            File file = ZipResourceExtractor.extract(resource.resourcePath(), baseFolder);
            files.add(file);
        }
    }

    @Override
    public void afterAll(ExtensionContext context) {
        for (File f : files) {
            if (f != null && f.exists()) {
                try {
                    Files.deleteIfExists(f.toPath());
                } catch (IOException ioex) {
                    Logger.getLogger(ExtractClassPathFiles.class.getName()).log(
                        Level.SEVERE,
                        "Caught an IOException while trying to delete file " + f.getAbsolutePath(),
                        ioex);
                }
            }
        }
        if (tempDir != null && tempDir.exists()) {
            tempDir.delete();
        }
    }

    /**
     * @return a {@link java.util.List} of extracted files.
     */
    public List<File> getFiles() {
        return this.files;
    }

    /**
     * @return the base directory where files were extracted.
     */
    public File getBaseDir() {
        return baseFolder;
    }
}
