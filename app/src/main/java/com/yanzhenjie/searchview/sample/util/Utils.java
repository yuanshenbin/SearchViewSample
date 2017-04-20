/*
 * Copyright © Yan Zhenjie. All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yanzhenjie.searchview.sample.util;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * <p></p>
 * Created by Yan Zhenjie on 2017/4/20.
 */
public class Utils {

    public static void closeInputMethod(Activity context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        View focusView = context.getCurrentFocus();
        if (inputMethodManager != null && focusView != null)
            inputMethodManager.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
    }

    public static void setSearchViewCursorColor(SearchView mSearchView, @DrawableRes int drawable) {
        TextView textView = (TextView) mSearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        try {
            Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            mCursorDrawableRes.setAccessible(true);
            mCursorDrawableRes.set(textView, drawable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
