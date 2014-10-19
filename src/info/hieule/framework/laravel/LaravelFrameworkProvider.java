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

import info.hieule.framework.laravel.commands.LaravelCommandSupport;
import info.hieule.framework.laravel.modules.LaravelModule;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.modules.php.api.phpmodule.PhpModule;
import org.netbeans.modules.php.api.phpmodule.PhpModuleProperties;
import org.netbeans.modules.php.api.util.FileUtils;
import org.netbeans.modules.php.spi.framework.PhpFrameworkProvider;
import org.netbeans.modules.php.spi.framework.PhpModuleActionsExtender;
import org.netbeans.modules.php.spi.framework.PhpModuleExtender;
import org.netbeans.modules.php.spi.framework.PhpModuleIgnoredFilesExtender;
import org.netbeans.modules.php.spi.framework.commands.FrameworkCommandSupport;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.NbBundle;

/**
 *
 * @author Hieu Le <letrunghieu.cse09@gmail.com>
 */
public class LaravelFrameworkProvider extends PhpFrameworkProvider {

    private static final LaravelFrameworkProvider _INSTANCE = new LaravelFrameworkProvider();
    private static final Logger _LOGGER = Logger.getLogger(LaravelFrameworkProvider.class.getName());
    private static final Comparator<File> _FILE_COMPARATOR = new Comparator<File>() {
        @Override
        public int compare(File o1, File o2) {
            return o1.getName().compareToIgnoreCase(o2.getName());
        }
    };

    private LaravelFrameworkProvider() {
        super("laravel",
                NbBundle.getMessage(LaravelFrameworkProvider.class, "LaravelFramework"),
                NbBundle.getMessage(LaravelFrameworkProvider.class, "LaravelFrameworkDescription"));
    }

    @PhpFrameworkProvider.Registration(position = 400)
    public static LaravelFrameworkProvider getInstance() {
        return _INSTANCE;
    }

    @Override
    public boolean isInPhpModule(PhpModule phpModule) {
        return LaravelPreferences.isEnabled(phpModule);
    }

    @Override
    public File[] getConfigurationFiles(PhpModule phpModule) {
        LaravelModule laravelModule = LaravelModule.forPhpModule(phpModule);
        if (laravelModule == null) {
            return new File[0];
        }
        List<File> configFiles = new LinkedList<File>();
        FileObject config = laravelModule.getConfigDirectory(LaravelModule.DIR_TYPE.APP);
        if (config == null) {
            _LOGGER.log(Level.WARNING, (phpModule.getDisplayName()));
            return new File[0];
        }
        if (config.isFolder()) {
            Enumeration<? extends FileObject> children = config.getChildren(false);
            while (children.hasMoreElements()) {
                FileObject child = children.nextElement();
                if (child.isData() && FileUtils.isPhpFile(child)) {
                    configFiles.add(FileUtil.toFile(child));
                }
            }
        }
        if (!configFiles.isEmpty()) {
            Collections.sort(configFiles, _FILE_COMPARATOR);
        }
        return configFiles.toArray(new File[configFiles.size()]);
    }

    @Override
    public PhpModuleExtender createPhpModuleExtender(PhpModule phpModule) {
        return new LaravelModuleExtender();
    }

    @Override
    public PhpModuleProperties getPhpModuleProperties(PhpModule phpModule) {
        PhpModuleProperties properties = new PhpModuleProperties();
        LaravelModule module = LaravelModule.forPhpModule(phpModule);
        if (module != null) {
            properties = module.getPhpModuleProperties(phpModule);
        }
        return properties;
    }

    @Override
    public PhpModuleActionsExtender getActionsExtender(PhpModule phpModule) {
        return new LaravelActionsExtender();
    }

    @Override
    public PhpModuleIgnoredFilesExtender getIgnoredFilesExtender(PhpModule phpModule) {
        return new LaravelIgnoredFilesExtender(phpModule);
    }

    @Override
    public FrameworkCommandSupport getFrameworkCommandSupport(PhpModule phpModule) {
        return new LaravelCommandSupport(phpModule);
    }

}
