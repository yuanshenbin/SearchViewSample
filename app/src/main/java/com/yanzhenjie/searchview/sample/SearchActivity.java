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
package com.yanzhenjie.searchview.sample;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.widget.TextView;

import com.yanzhenjie.searchview.sample.entity.UserInfo;
import com.yanzhenjie.searchview.sample.greendao.service.UserInfoService;
import com.yanzhenjie.searchview.sample.provider.UserSearchProvider;
import com.yanzhenjie.searchview.sample.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <p>Main UI.</p>
 * Created by Yan Zhenjie on 2017/4/16.
 */
public class SearchActivity extends AppCompatActivity {

    private static final int CURSOR_LOADER_ID = 99;

    /**
     * SearchView.
     */
    private SearchView mSearchView;

    /**
     * Suggestions adapter.
     */
    private SimpleCursorAdapter mCursorAdapter;

    /**
     * Create age.
     */
    private Random ageRandom = new Random();

    /**
     * SearchResult.
     */
    private TextView mTvMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTvMessage = (TextView) findViewById(R.id.tv_message);

        List<UserInfo> infoList = new ArrayList<>();
        UserInfo userInfo = new UserInfo("007", "5 years old.");
        infoList.add(userInfo);

        userInfo = new UserInfo("345", "1 years old.");
        infoList.add(userInfo);

        userInfo = new UserInfo("789", "10 years old.");
        infoList.add(userInfo);

        userInfo = new UserInfo("abc", "50 years old.");
        infoList.add(userInfo);

        userInfo = new UserInfo("jkh", "89 years old.");
        infoList.add(userInfo);

        userInfo = new UserInfo("lop", "66 years old.");
        infoList.add(userInfo);

        for (UserInfo info : infoList) {
            UserInfoService.getInstance().replace(info);
        }

        // Or lambda:
        // infoList.forEach(info -> UserInfoService.getInstance().replace(info));
    }

    /**
     * Search for data from the server, displayed in the main area.
     *
     * @param key key.
     */
    private void searchAndShow(String key) {
        // TODO Search for data from the server, displayed in the main area.
        // Request request = new Request(url);
        // request.addParameter("key", key);
        // http.request(request, new Callback<String>() {
        //      @Override
        //      public void callback(String result) {
        //          TODO Show data at the interface.
        mTvMessage.setText("Result: \"" + key.toUpperCase() + "\" data is found on the server.");
        //      }
        // });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mSearchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.menu_action_user_search));
        Utils.setSearchViewCursorColor(mSearchView, R.drawable.text_cursor_white);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint(getText(R.string.title_search_user_hint));
        mSearchView.setOnQueryTextListener(mOnQueryTextListener);

        // Can not exit to icon.
        // mSearchView.setOnCloseListener(() -> TextUtils.isEmpty(mSearchView.getQuery()));

        // Automatic expansion.
//        mSearchView.setIconified(false);
//        mSearchView.setIconifiedByDefault(true);

        mSearchView.setOnSuggestionListener(mSuggestionListener);
        SearchableInfo searchableInfo = ((SearchManager) getSystemService(Context.SEARCH_SERVICE))
                .getSearchableInfo(getComponentName());
        mSearchView.setSearchableInfo(searchableInfo);

        mCursorAdapter = new SimpleCursorAdapter(this,
                R.layout.item_text_list_popup,
                null,
                new String[]{UserSearchProvider.ProviderInfo.USER_NAME, UserSearchProvider.ProviderInfo.USER_DES},
                new int[]{R.id.tv_item_list, R.id.tv_item_list_des},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        mSearchView.setSuggestionsAdapter(mCursorAdapter);

        getSupportLoaderManager().initLoader(CURSOR_LOADER_ID, null, mLoaderCallbacks);
        return true;
    }

    /**
     * SearchView's data is submitted, whether it is a keyboard or a button.
     */
    private SearchView.OnQueryTextListener mOnQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            UserInfo userInfo = new UserInfo(query, ageRandom.nextInt(99) + " years old.");

            UserInfoService.getInstance().replace(userInfo);
            searchAndShow(query);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            if (!TextUtils.isEmpty(newText)) {
                Bundle args = new Bundle();
                args.putString("key", newText);
                getSupportLoaderManager().restartLoader(CURSOR_LOADER_ID, args, mLoaderCallbacks);
            }
            return true;
        }
    };

    /**
     * Drop-down box item listener of SearchView.
     */
    private SearchView.OnSuggestionListener mSuggestionListener = new SearchView.OnSuggestionListener() {
        @Override
        public boolean onSuggestionSelect(int position) {
            return onSuggestionClick(position);
        }

        @Override
        public boolean onSuggestionClick(int position) {
            Cursor cursor = mCursorAdapter.getCursor();
            cursor.moveToPosition(position);
            String searchKey = cursor.getString(cursor.getColumnIndex(UserSearchProvider.ProviderInfo.USER_NAME));
            mSearchView.setQuery(searchKey, false);
            searchAndShow(searchKey);
            return true;
        }
    };

    /**
     * Loader load data listener.
     */
    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            String[] projection = new String[]{
                    UserSearchProvider.ProviderInfo.ID,
                    UserSearchProvider.ProviderInfo.USER_NAME,
                    UserSearchProvider.ProviderInfo.USER_DES
            };
            String selection = null;
            String[] selectionArgs = null;
            String sortOrder = UserSearchProvider.ProviderInfo.ID + " desc";
            if (args != null) {
                selection = UserSearchProvider.ProviderInfo.USER_NAME + " like ?";
                selectionArgs = new String[]{"%" + args.getString("key") + "%"};
            }
            return new CursorLoader(
                    SearchActivity.this,
                    UserSearchProvider.ProviderInfo.CONTENT_URI,
                    projection,
                    selection,
                    selectionArgs,
                    sortOrder);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            mCursorAdapter.changeCursor(data);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            mCursorAdapter.changeCursor(null);
        }
    };

    @Override
    protected void onDestroy() {
        getSupportLoaderManager().destroyLoader(CURSOR_LOADER_ID);
        super.onDestroy();
    }
}
