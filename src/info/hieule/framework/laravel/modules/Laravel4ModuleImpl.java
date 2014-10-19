/*
 * The MIT License
 *
 * Copyright 2014 Hieu Le <letrunghieu.cse09@gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package info.hieule.framework.laravel.modules;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.modules.php.api.phpmodule.PhpModule;
import org.openide.filesystems.FileObject;

/**
 *
 * @author Hieu Le <letrunghieu.cse09@gmail.com>
 */
public class Laravel4ModuleImpl extends LaravelModuleImpl {

    private static final Logger LOGGER = Logger.getLogger(Laravel4ModuleImpl.class.getName());

    public Laravel4ModuleImpl(PhpModule phpModule) {
        super(phpModule);
    }

    @Override
    public FileObject getDirectory(LaravelModule.DIR_TYPE type, LaravelModule.FILE_TYPE fileType, String packageName) {
        if (packageName != null && packageName.isEmpty()) {
            packageName = null;
        }
        if (type == null && packageName == null) {
            return null;
        }

        if (fileType == null && packageName == null) {
            return getDirectory(type);
        }

        if (packageName != null) {
            // TODO package dir
        }

        FileObject directory = null;
        StringBuilder sb = new StringBuilder();
        switch (type) {
            case APP:
                if (fileType == null) {
                    return null;
                }
                switch (fileType) {
                    case COMMANDS:
                    case CONFIG:
                    case CONTROLLERS:
                    case LANG:
                    case MODELS:
                    case TESTS:
                    case VIEWS:
                        sb.append(fileType.name().toLowerCase());
                        break;
                    case MIGRATIONS:
                    case SEEDS:
                        sb.append("database/");
                        sb.append(fileType.name().toLowerCase());
                        break;
                    default:
                        return getDirectory(type);

                }
                break;
            case BOOTSTRAP:
            case PUBLIC:
                if (fileType == null || fileType == LaravelModule.FILE_TYPE.NONE) {
                    return getDirectory(type);
                } else {
                    return null;
                }
            case VENDOR:
                if (fileType == null) {
                    return null;
                }
                switch (fileType) {
                    case CONFIG:
                    case LANG:
                    case VIEWS:
                    case MIGRATIONS:
                        sb.append("src/");
                        sb.append(fileType.name().toLowerCase());
                        break;
                    case TESTS:
                    case PUBLIC:
                        sb.append(fileType.name().toLowerCase());
                        break;
                    default:
                        return getDirectory(type);

                }
                break;
            default:
                return null;
        }
        directory = getDirectory(type);
        if (directory == null) {
            return null;
        }
        return directory.getFileObject(sb.toString());
    }

    @Override
    public FileObject getDirectory(LaravelModule.DIR_TYPE type) {
        if (type == null) {
            return null;
        }
        FileObject sourceDirectory = getLaravelDirectory();
        if (sourceDirectory == null) {
            LOGGER.log(Level.WARNING, "Not found source directory");
            return null;
        }
        String path;
        switch (type) {
            case NONE:
                return null;
            default:
                path = type.name().toLowerCase();
                break;
        }
        return sourceDirectory.getFileObject(path);
    }

}
