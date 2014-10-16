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
package info.hieule.framework.laravel;

import java.io.File;
import org.netbeans.modules.php.api.phpmodule.PhpModule;
import org.netbeans.modules.php.api.phpmodule.PhpModuleProperties;
import org.netbeans.modules.php.spi.framework.PhpFrameworkProvider;
import org.netbeans.modules.php.spi.framework.PhpModuleActionsExtender;
import org.netbeans.modules.php.spi.framework.PhpModuleExtender;
import org.netbeans.modules.php.spi.framework.PhpModuleIgnoredFilesExtender;
import org.netbeans.modules.php.spi.framework.commands.FrameworkCommandSupport;
import org.openide.util.NbBundle;

/**
 *
 * @author Hieu Le <letrunghieu.cse09@gmail.com>
 */
public class LaravelFrameworkProvider extends PhpFrameworkProvider{

    private LaravelFrameworkProvider()
    {
        super("laravel", 
                NbBundle.getMessage(LaravelFrameworkProvider.class, "LaravelFramework"), 
                NbBundle.getMessage(LaravelFrameworkProvider.class, "LaravelFrameworkDescription"));
    }
    
    @Override
    public boolean isInPhpModule(PhpModule phpModule) {
        return LaravelPreferences.isEnabled(phpModule);
    }

    @Override
    public File[] getConfigurationFiles(PhpModule phpModule) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PhpModuleExtender createPhpModuleExtender(PhpModule phpModule) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PhpModuleProperties getPhpModuleProperties(PhpModule phpModule) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PhpModuleActionsExtender getActionsExtender(PhpModule phpModule) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PhpModuleIgnoredFilesExtender getIgnoredFilesExtender(PhpModule phpModule) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FrameworkCommandSupport getFrameworkCommandSupport(PhpModule phpModule) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
