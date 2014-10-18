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
package info.hieule.framework.laravel.module;

import info.hieule.framework.laravel.module.LaravelModule.DIR_TYPE;
import info.hieule.framework.laravel.module.LaravelModule.FILE_TYPE;
import org.netbeans.modules.php.api.phpmodule.PhpModule;
import org.netbeans.modules.php.api.phpmodule.PhpModuleProperties;
import org.openide.filesystems.FileObject;

/**
 *
 * @author Hieu Le <letrunghieu.cse09@gmail.com>
 */
public abstract class LaravelModuleImpl {

    protected PhpModule phpModule;
    protected static final String PHP_EXT = "php";
    protected static final String BLADE_EXT = "blade.php";

    private FileObject _appDirectory;

    public LaravelModuleImpl(PhpModule phpModule) {
        this.phpModule = phpModule;
    }

    public PhpModuleProperties getPhpModuleProperties(PhpModule phpModule) {
        PhpModuleProperties properties = new PhpModuleProperties();
        FileObject webroot = getWebrootDirectory(DIR_TYPE.APP);
        if (webroot != null) {
            properties.setWebRoot(webroot);
        }
        FileObject test = getTestDirectory(DIR_TYPE.APP);
        if (test != null) {
            properties.setTests(test);
        }
        return properties;
    }

    public FileObject getConfigDirectory(DIR_TYPE type) {
        return getDirectory(type, FILE_TYPE.CONFIG);
    }

    public FileObject getConfigDirectory(DIR_TYPE type, String packageName) {
        return getDirectory(type, FILE_TYPE.CONFIG, packageName);
    }

    public FileObject getTestDirectory(DIR_TYPE type) {
        return getDirectory(type, FILE_TYPE.TEST);
    }

    public FileObject getTestDirectory(DIR_TYPE type, String packageName) {
        return getDirectory(type, FILE_TYPE.TEST, packageName);
    }

    public FileObject getWebrootDirectory(DIR_TYPE type) {
        PhpModuleProperties properties = phpModule.getLookup().lookup(PhpModuleProperties.Factory.class).getProperties();
        FileObject webroot = properties.getWebRoot();
        if (webroot == phpModule.getSourceDirectory()) {
            return getDirectory(type, FILE_TYPE.WEBROOT);
        }
        return webroot != null ? webroot : getDirectory(type, FILE_TYPE.WEBROOT);
    }

    public FileObject getWebrootDirectory(DIR_TYPE type, String pluginName) {
        return getDirectory(type, FILE_TYPE.WEBROOT, pluginName);
    }

    protected FileObject getDirectory(DIR_TYPE type, FILE_TYPE fileType) {
        return getDirectory(type, fileType, null);
    }

    public abstract FileObject getDirectory(DIR_TYPE type, FILE_TYPE fileType, String packageName);

}
