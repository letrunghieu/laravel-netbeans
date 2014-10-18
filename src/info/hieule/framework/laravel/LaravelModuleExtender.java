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

import info.hieule.framework.laravel.wizards.ConfigurationInnerPanel;
import info.hieule.framework.laravel.wizards.NewProjectConfigurationPanel;
import java.awt.Container;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.event.ChangeListener;
import org.netbeans.modules.php.api.phpmodule.PhpModule;
import org.netbeans.modules.php.spi.framework.PhpModuleExtender;
import org.openide.filesystems.FileObject;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author Hieu Le <letrunghieu.cse09@gmail.com>
 */
public class LaravelModuleExtender extends PhpModuleExtender{

    static final Logger LOGGER = Logger.getLogger(LaravelModuleExtender.class.getName());
    private NewProjectConfigurationPanel panel = null;
    
    @Override
    public void addChangeListener(ChangeListener listener) {
        getPanel().addChangeListener(listener);
    }

    @Override
    public void removeChangeListener(ChangeListener listener) {
        getPanel().removeChangeListener(listener);
    }

    @Override
    public JComponent getComponent() {
        return getPanel();
    }

    @Override
    public HelpCtx getHelp() {
        return null;
    }

    @Override
    public boolean isValid() {
        boolean isValid = getErrorMessage() == null;
        Container parent = getPanel().getParent();
//        if (parent != null) {
//            parent = parent.getParent();
//        }
//        if (parent != null) {
//            setComponentsEnabled(parent, isValid);
//            getPanel().setRadioButtonsEnabled(true);
//        }
        return isValid;
    }

    @Override
    public String getErrorMessage() {
        return getPanel().getErrorMessage();
    }

    @Override
    public String getWarningMessage() {
        return null;
    }

    @Override
    public Set<FileObject> extend(PhpModule phpModule) throws ExtendingException {
        return new HashSet<FileObject>();
    }
    
    public NewProjectConfigurationPanel getPanel() {
        if (panel == null) {
            panel = new NewProjectConfigurationPanel();
//            Lookup lookup = Lookups.forPath(ConfigurationInnerPanel.CONFIGURATION_INNER_PANELS_PATH);
//            Collection<? extends ConfigurationInnerPanel> configurationPanels = lookup.lookupAll(ConfigurationInnerPanel.class);
//            for (ConfigurationInnerPanel configurationPanel : configurationPanels) {
//                panel.addPanel(configurationPanel);
//            }
//            panel.changePanel();
        }
//        panel.setError();
        return panel;
    }
    
}
