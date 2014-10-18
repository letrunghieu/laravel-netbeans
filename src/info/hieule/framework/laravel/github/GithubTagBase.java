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
package info.hieule.framework.laravel.github;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.netbeans.api.annotations.common.NonNull;
import org.openide.util.Exceptions;

/**
 *
 * @author Hieu Le <letrunghieu.cse09@gmail.com>
 */
public abstract class GithubTagBase {

    private ArrayList<GithubTag> tags;
    private ArrayList<String> names;
    private boolean isNetworkError = true;

    @NonNull
    public abstract String getUrl();

    public abstract Filter getFilter();

    private void init() {
        isNetworkError = false;
        tags = new ArrayList<GithubTag>();
        try {
            // JSON -> Object
            Gson gson = new Gson();
            URL tagsJson = new URL(getUrl());
            BufferedReader reader = new BufferedReader(new InputStreamReader(tagsJson.openStream(), "UTF-8")); // NOI18N
            try {
                JsonReader jsonReader = new JsonReader(reader);
                Type type = new TypeToken<ArrayList<GithubTag>>() {
                }.getType();
                tags = gson.fromJson(jsonReader, type);
            } finally {
                reader.close();
            }
        } catch (MalformedURLException ex) {
            Exceptions.printStackTrace(ex);
        } catch (UnsupportedEncodingException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException ex) {
            isNetworkError = true;
        }

        names = new ArrayList<String>(tags.size());
        if (isNetworkError) {
            return;
        }

        Filter filter = getFilter();
        if (filter == null) {
            filter = new DefaultFilter();
        }
        for (GithubTag tag : tags) {
            String name = tag.getName();
            if (filter.accept(name)) {
                names.add(name);
            }
        }
    }

    public void reload() {
        init();
    }

    public List<GithubTag> getTags() {
        if (isNetworkError) {
            reload();
        }
        if (tags == null) {
            return Collections.emptyList();
        }
        return tags;
    }

    public String getZipUrl(String name) {
        for (GithubTag tag : tags) {
            if (tag.getName().equals(name)) {
                return tag.getZipballUrl();
            }
        }
        return null;
    }

    public String[] getNames() {
        if (isNetworkError) {
            reload();
        }
        return names.toArray(new String[0]);
    }

    public String getLatestStableVersion() {
        if (isNetworkError) {
            reload();
        }
        for (String name : names) {
            // not stable version
            if (name.contains("-")) { // NOI18N
                continue;
            }
            return name;
        }
        return null;
    }

    public boolean isNetworkError() {
        return isNetworkError;
    }

    //~ Inner classes
    public interface Filter {

        public boolean accept(String name);
    }

    public class DefaultFilter implements Filter {

        @Override
        public boolean accept(String name) {
            return true;
        }

    }

}
