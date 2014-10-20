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

import info.hieule.framework.laravel.modules.LaravelModule;
import info.hieule.framework.laravel.ui.customizer.LaravelModuleCustomizerPanel;
import java.beans.PropertyChangeEvent;
import java.util.EnumSet;
import javax.swing.JComponent;
import javax.swing.event.ChangeListener;
import org.netbeans.modules.php.api.phpmodule.PhpModule;
import org.netbeans.modules.php.spi.framework.PhpModuleCustomizerExtender;
import org.openide.util.ChangeSupport;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;

/**
 *
 * @author Hieu Le <letrunghieu.cse09@gmail.com>
 */
public class LaravelModuleCustomizerExtender extends PhpModuleCustomizerExtender {

    private LaravelModuleCustomizerPanel _panel;
    private final PhpModule _phpModule;
    private final boolean _isEnabled;
    private final ChangeSupport _changeSupport = new ChangeSupport(this);

    public LaravelModuleCustomizerExtender(PhpModule phpModule) {
        this._phpModule = phpModule;
        this._isEnabled = LaravelPreferences.isEnabled(phpModule);
    }

    @Override
    public String getDisplayName() {
        return NbBundle.getMessage(LaravelModuleCustomizerExtender.class, "LaravelFramework");
    }

    @Override
    public void addChangeListener(ChangeListener listener) {
        if (listener instanceof LaravelModule) {
            _changeSupport.addChangeListener(listener);
        }
        _getPanel().addChangeListener(listener);
    }

    @Override
    public void removeChangeListener(ChangeListener listener) {
        if (listener instanceof LaravelModule) {
            _changeSupport.addChangeListener(listener);
        }
        _getPanel().removeChangeListener(listener);
    }

    @Override
    public JComponent getComponent() {
        return _getPanel();
    }

    @Override
    public HelpCtx getHelp() {
        return null;
    }

    @Override
    public boolean isValid() {
        //validate
        return true;
    }

    @Override
    public String getErrorMessage() {
        //validate
        return "";
    }

    @Override
    public EnumSet<Change> save(PhpModule phpModule) {
        EnumSet<Change> enumset = EnumSet.of(Change.FRAMEWORK_CHANGE);
        if (_isEnabled != _getPanel().isLaravelEnabled()) {
            LaravelPreferences.setEnabled(phpModule, !_isEnabled);
        }
        LaravelModule module = LaravelModule.forPhpModule(phpModule);
        if (module != null) {
            module.notifyPropertyChanged(new PropertyChangeEvent(this, LaravelModule.PROPERTY_CHANGE_LARAVEL, null, null));
        }
        return enumset;
    }

    void fireChange() {
        _changeSupport.fireChange();
    }

    private LaravelModuleCustomizerPanel _getPanel() {
        if (_panel == null) {
            _panel = new LaravelModuleCustomizerPanel();
            _panel.setLaravelEnabled(_isEnabled);
        }
        return _panel;
    }

}
