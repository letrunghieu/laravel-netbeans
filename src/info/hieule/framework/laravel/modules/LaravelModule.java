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

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.netbeans.api.annotations.common.CheckForNull;
import org.netbeans.modules.php.api.phpmodule.PhpModule;
import org.netbeans.modules.php.api.phpmodule.PhpModuleProperties;
import org.openide.filesystems.FileObject;

/**
 *
 * @author Hieu Le <letrunghieu.cse09@gmail.com>
 */
public class LaravelModule implements ChangeListener {

    public enum DIR_TYPE {

        NONE,
        APP,
        BOOTSTRAP,
        PUBLIC,
        VENDOR;
    }

    public enum FILE_TYPE {

        NONE,
        COMMANDS,
        CONFIG,
        CONTROLLERS,
        MIGRATIONS,
        SEEDS,
        LANG,
        MODELS,
        OTHER,
        TESTS,
        VIEWS,
        PUBLIC,

    }
    private final PhpModule _phpModule;
    private final LaravelModuleImpl _impl;

    public LaravelModule(PhpModule phpModule, LaravelModuleImpl impl) {
        this._phpModule = phpModule;
        this._impl = impl;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @CheckForNull
    public static FileObject geLaravelDirectory(PhpModule phpModule) {
        if (phpModule == null) {
            return null;
        }
        FileObject sourceDirectory = phpModule.getSourceDirectory();
        return sourceDirectory;
    }

    public FileObject getConfigDirectory(DIR_TYPE type) {
        return _impl.getConfigDirectory(type);
    }
    
    public FileObject getConfigFile() {
        return _impl.getConfigFile();
    }

    public PhpModuleProperties getPhpModuleProperties(PhpModule phpModule) {
        return _impl.getPhpModuleProperties(phpModule);
    }

    public static LaravelModule forPhpModule(PhpModule phpModule) {
        if (phpModule == null) {
            phpModule = PhpModule.Factory.inferPhpModule();
        }
        if (phpModule == null) {
            return null;
        }
        LaravelModuleFactory factory = LaravelModuleFactory.getInstance();
        return factory.create(phpModule);
    }

}
