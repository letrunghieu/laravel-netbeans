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
public class LaravelGithubTags extends GithubTagBase {

    private static final String _GITHUB_API_REPOS_TAGS = "https://api.github.com/repos/laravel/laravel/tags";
    private static final LaravelGithubTags _INSTANCE = new LaravelGithubTags();

    public static LaravelGithubTags getInstance() {
        return _INSTANCE;
    }

    @Override
    public String getUrl() {
        return _GITHUB_API_REPOS_TAGS;
    }

    @Override
    public Filter getFilter() {
        return new Laravel4Filter();
    }
    
    private static class Laravel4Filter implements Filter {

        @Override
        public boolean accept(String name) {
            return name.matches("^v4\\.\\d+\\.\\d+.*");
        }
        
    }
}