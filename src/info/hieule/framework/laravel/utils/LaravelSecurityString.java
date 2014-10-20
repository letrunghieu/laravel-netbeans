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
package info.hieule.framework.laravel.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;
import org.apache.commons.lang.RandomStringUtils;
import org.openide.filesystems.FileObject;

/**
 *
 * @author Hieu Le <letrunghieu.cse09@gmail.com>
 */
public class LaravelSecurityString {

    private static final String _SECURITY_STRING_LINE = "'key' => 'YourSecretKey!!!'";
    private static final String _SECURITY_STRING_FORMAT = "\t'key' => '%s',";

    public static void updateSecurityString(FileObject configFile) throws IOException {
        List<String> lines = configFile.asLines();
        String newKey = RandomStringUtils.randomAlphanumeric(32);
        OutputStream outputStream = configFile.getOutputStream();
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true); // NOI18N
        try {
            for (String line : lines) {
                if (line.contains(_SECURITY_STRING_LINE)) {
                    line = String.format(_SECURITY_STRING_FORMAT, newKey);
                }
                pw.println(line);
            }
        } finally {
            outputStream.close();
            pw.close();
        }
    }
}
