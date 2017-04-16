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
package com.yanzhenjie.searchview.sample.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.yanzhenjie.searchview.sample.App;
import com.yanzhenjie.searchview.sample.greendao.GreenDaoFactory;
import com.yanzhenjie.searchview.sample.greendao.UserInfoDao;

/**
 * <p>User search provider.</p>
 * Created by Yan Zhenjie on 2016/10/13.
 */
public class UserSearchProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int CODE_ITEM = 1;
    private static final int CODE_ALL = 2;

    static {
        sUriMatcher.addURI(ProviderInfo.AUTHOR, "search/#", CODE_ITEM);
        sUriMatcher.addURI(ProviderInfo.AUTHOR, "search", CODE_ALL);
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowId = GreenDaoFactory.getInstance().getWritableDatabase().insert(UserInfoDao.TABLENAME, "", values);
        if (rowId > 0) {
            Uri rowUri = ContentUris.withAppendedId(ProviderInfo.CONTENT_URI, rowId);
            App.get().getContentResolver().notifyChange(rowUri, null);
            return rowUri;
        }
        return null;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(UserInfoDao.TABLENAME);
        switch (sUriMatcher.match(uri)) {
            case CODE_ITEM:
                long id = ContentUris.parseId(uri);
                qb.appendWhere(ProviderInfo.ID + "=" + id);
                break;
            case CODE_ALL:
                break;
        }

        SQLiteDatabase db = GreenDaoFactory.getInstance().getWritableDatabase();
        Cursor cursor = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder, "15");
        cursor.setNotificationUri(App.get().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        int code = sUriMatcher.match(uri);
        switch (code) {
            case CODE_ITEM:
                return ProviderInfo.CONTENT_TYPE_ITEM;
            case CODE_ALL:
                return ProviderInfo.CONTENT_TYPE;
            default:
                throw new IllegalArgumentException("Not support.");
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = GreenDaoFactory.getInstance().getWritableDatabase().delete(
                UserInfoDao.TABLENAME,
                selection,
                selectionArgs);
        App.get().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = GreenDaoFactory.getInstance().getWritableDatabase().update(
                UserInfoDao.TABLENAME,
                values,
                selection,
                selectionArgs);
        App.get().getContentResolver().notifyChange(uri, null);
        return count;
    }

    public static class ProviderInfo {

        public static final String AUTHOR = "com.yanzhenjie.searchview.sample.provider.UserSearchProvider";

        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHOR + "/search");

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/search";
        public static final String CONTENT_TYPE_ITEM = "vnd.android.cursor.item/search";

        public static final String TABLE_NAME = UserInfoDao.TABLENAME;

        public static final String ID = UserInfoDao.Properties.Id.columnName;
        public static final String USER_NAME = UserInfoDao.Properties.UserName.columnName;
        public static final String USER_AGE = UserInfoDao.Properties.UserAge.columnName;
        public static final String USER_DES = UserInfoDao.Properties.UserDes.columnName;

    }
}
