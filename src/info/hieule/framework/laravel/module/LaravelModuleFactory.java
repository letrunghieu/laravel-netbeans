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

import java.util.HashMap;
import java.util.Map;
import org.netbeans.modules.php.api.phpmodule.PhpModule;

/**
 *
 * @author Hieu Le <letrunghieu.cse09@gmail.com>
 */
public class LaravelModuleFactory {

    private final Map<PhpModule, LaravelModule> _modules = new HashMap<PhpModule, LaravelModule>();
    private static final LaravelModuleFactory _INSTANCE = new LaravelModuleFactory();
    private boolean _isCreating = false;

    public static LaravelModuleFactory getInstance() {
        return _INSTANCE;
    }

    public synchronized LaravelModule create(PhpModule phpModule) {
        assert !_isCreating;
        LaravelModule module = _modules.get(phpModule);
        if (module == null) {
            try {
                start();
                LaravelModuleImpl impl = getLaravelModuleImpl(phpModule);
                if (phpModule == null) {
                    return new LaravelModule(phpModule, impl);
                }
                module = new LaravelModule(phpModule, impl);
                _modules.put(phpModule, module);
            } finally {
                finish();
            }
        }
        return module;
    }

    private LaravelModuleImpl getLaravelModuleImpl(PhpModule phpModule) {
        LaravelModuleImpl impl;
        impl = new Laravel4ModuleImpl(phpModule);
        return impl;
    }

    private void start() {
        _isCreating = true;
    }

    private void finish() {
        _isCreating = false;
    }
}
