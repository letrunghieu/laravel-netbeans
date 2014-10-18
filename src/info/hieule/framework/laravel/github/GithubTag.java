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

/**
 *
 * @author Hieu Le <letrunghieu.cse09@gmail.com>
 */
public class GithubTag {

    private final String _name;
    private final String _zipball_url;
    private final String _tarball_url;
    private final GithubTagCommit _commit;

    // Format
    //  {
    //    "name": "4.1.0",
    //    "zipball_url": "https://api.github.com/repos/laravel/laravel/zipball/4.1.0",
    //    "tarball_url": "https://api.github.com/repos/laravel/laravel/tarball/4.1.0",
    //    "commit": {
    //      "sha": "3053d486e64238b3698c14179f9881a55f9ac08b",
    //      "url": "https://api.github.com/repos/laravel/laravel/commits/3053d486e64238b3698c14179f9881a55f9ac08b"
    //    }
    //  }
    public GithubTag(String name, String zipball_url, String tarball_url, GithubTagCommit commit) {
        this._name = name;
        this._zipball_url = zipball_url;
        this._tarball_url = tarball_url;
        this._commit = commit;
    }

    public String getName() {
        return _name;
    }

    public String getZipballUrl() {
        return _zipball_url;
    }

    public String getTarballUrl() {
        return _tarball_url;
    }

    public GithubTagCommit getCommit() {
        return _commit;
    }

    @Override
    public String toString() {
        return _name;
    }
}
