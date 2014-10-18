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

import info.hieule.framework.laravel.wizards.NewProjectConfigurationPanel;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.netbeans.modules.php.api.executable.InvalidPhpExecutableException;
import org.netbeans.modules.php.api.phpmodule.PhpModule;
import org.netbeans.modules.php.composer.api.Composer;
import org.netbeans.modules.php.spi.framework.PhpModuleExtender;
import org.netbeans.modules.php.spi.framework.PhpModuleExtender.ExtendingException;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;

/**
 *
 * @author Hieu Le <letrunghieu.cse09@gmail.com>
 */
public class LaravelExtenderVerion4 implements LaravelExtender {

    private NewProjectConfigurationPanel _panel;

    public LaravelExtenderVerion4(NewProjectConfigurationPanel panel) {
        this._panel = panel;
    }

    @Override
    public Set<FileObject> extend(PhpModule phpModule) throws PhpModuleExtender.ExtendingException {
        FileObject targetDirectory = phpModule.getSourceDirectory();
        if (targetDirectory == null) {
            // broken project
            throw new PhpModuleExtender.ExtendingException(Bundle.LaravelModuleExtender_extending_exception(phpModule.getName()));
        }
        _installLaravel(phpModule, targetDirectory);
        return Collections.emptySet();
    }

    private void _installLaravel(PhpModule phpModule, FileObject targetDirectory) throws ExtendingException {
        if (_panel.isUnzipGitub()) {

        } else if (_panel.isUnzipLocal()) {

        }
    }

    private void _installByComposer(PhpModule phpModule, FileObject targetDirectory) throws ExtendingException {
        LaravelPreferences.setEnabled(phpModule, Boolean.FALSE);
        if (!_composerInstall(targetDirectory, phpModule)) {
            return;
        }
    }

    @NbBundle.Messages({
        "# {0} - name",
        "LaravelModuleExtender.extending.exception.composer.install=failed installing composer: {0}"
    })
    private boolean _composerInstall(FileObject targetDirectory, final PhpModule phpModule) throws ExtendingException {
        boolean isSuccess = true;
        try {
            Composer composer = Composer.getDefault();
            composer.setWorkDir(FileUtil.toFile(targetDirectory));
            Future<Integer> result = composer.install(phpModule);
            if (result != null) {
                try {
                    result.get();
                } catch (InterruptedException ex) {
                    Exceptions.printStackTrace(ex);
                } catch (ExecutionException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        } catch (InvalidPhpExecutableException ex) {
            isSuccess = false;
            throw new ExtendingException(Bundle.LaravelModuleExtender_extending_exception_composer_install(phpModule.getName()));
        }
        return isSuccess;
    }

}
