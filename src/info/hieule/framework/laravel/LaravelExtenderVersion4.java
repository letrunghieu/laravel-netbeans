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
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import javax.swing.JComponent;
import javax.swing.JLabel;
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
public class LaravelExtenderVersion4 implements LaravelExtender {

    private NewProjectConfigurationPanel _panel;

    public LaravelExtenderVersion4(NewProjectConfigurationPanel panel) {
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
        _installByGithubTag(_panel.getSelectedGithubTagUrl(), targetDirectory, _panel.getProgressTextComp());
        _composerInstall(targetDirectory, phpModule);
    }

    private void _installByGithubTag(String sourceUrl, FileObject target, JLabel progressText) {
        try {
            File targetDirectory = FileUtil.toFile(target);
            if (targetDirectory == null) {
                return;
            }

            URL zipUrl = new URL(sourceUrl);
            // Download zip file
            InputStream in = new BufferedInputStream(zipUrl.openStream(), 1024);
            File zip = File.createTempFile("laravel", ".zip", targetDirectory);
            OutputStream out = new BufferedOutputStream(new FileOutputStream(zip));
            copyInputStream(in, out);
            out.close();
            // Unzip downloaded file
            ZipFile zipFile = new ZipFile(zip);
            for (Enumeration entries = zipFile.entries(); entries.hasMoreElements();) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                int position = entry.getName().indexOf("/");
                if (position < 0) {
                    continue;
                }
                File file = new File(targetDirectory, File.separator + entry.getName().substring(position + 1));
                if (!buildDirectory(file.getParentFile())) {
                    throw new IOException("Could not create directory: " + file.getParentFile());
                }
                if (!entry.isDirectory()) {
                    copyInputStream(zipFile.getInputStream(entry), new BufferedOutputStream(new FileOutputStream(file)));
                } else {
                    if (!buildDirectory(file)) {
                        throw new IOException("Could not create directory: " + file);
                    }
                }
            }
            zipFile.close();
            zip.delete();
        } catch (MalformedURLException ex) {
            System.out.println(sourceUrl + " is not a valid URL");
        } catch (IOException ex) {
            System.out.println("Cannot open " + sourceUrl + " as stream");
        }
    }

    public static void copyInputStream(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len = in.read(buffer);
        while (len >= 0) {
            out.write(buffer, 0, len);
            len = in.read(buffer);
        }
        in.close();
        out.close();
    }

    public static boolean buildDirectory(File file) {
        return file.exists() || file.mkdirs();
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
