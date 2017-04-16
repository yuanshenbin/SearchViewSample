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
package com.yanzhenjie.searchview.sample.greendao;

import android.database.sqlite.SQLiteDatabase;

import com.yanzhenjie.searchview.sample.App;

/**
 * Created by Yan Zhenjie on 2016/10/9.
 */
public class GreenDaoFactory {

    private static final String DB_NAME = "SEARCHVIEW_SAMPLE_DB";

    private static GreenDaoFactory instance;

    public static GreenDaoFactory getInstance() {
        if (instance == null)
            synchronized (GreenDaoFactory.class) {
                if (instance == null)
                    instance = new GreenDaoFactory();
            }
        return instance;
    }

    private DaoMaster.DevOpenHelper mHelper;

    private SQLiteDatabase mReadableSQLiteDatabase;

    private SQLiteDatabase mWritableSQLiteDatabase;

    private DaoSession mReadableDaoSession;

    private DaoSession mWritableDaoSession;

    /**
     * Create DevOpenHelper.
     */
    private GreenDaoFactory() {
        mHelper = new DaoMaster.DevOpenHelper(App.get(), DB_NAME);
    }


    public SQLiteDatabase getReadableDatabase() {
        if (mReadableSQLiteDatabase == null)
            mReadableSQLiteDatabase = mHelper.getReadableDatabase();
        return mReadableSQLiteDatabase;
    }

    public SQLiteDatabase getWritableDatabase() {
        if (mWritableSQLiteDatabase == null)
            mWritableSQLiteDatabase = mHelper.getWritableDatabase();
        return mWritableSQLiteDatabase;
    }

    public DaoSession getReadableSession() {
        if (mReadableDaoSession == null)
            mReadableDaoSession = new DaoMaster(getReadableDatabase()).newSession();
        return mReadableDaoSession;
    }

    public DaoSession getWritableSession() {
        if (mWritableDaoSession == null)
            mWritableDaoSession = new DaoMaster(getWritableDatabase()).newSession();
        return mWritableDaoSession;
    }

}
