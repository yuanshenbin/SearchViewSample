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
package com.yanzhenjie.searchview.sample.greendao.service;

import com.yanzhenjie.searchview.sample.entity.UserInfo;
import com.yanzhenjie.searchview.sample.greendao.GreenDaoFactory;
import com.yanzhenjie.searchview.sample.greendao.UserInfoDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * <p>UserInfo db service.</p>
 * Created by Yan Zhenjie on 2017/4/16.
 */
public class UserInfoService {

    private static UserInfoService instance;

    public static UserInfoService getInstance() {
        if (instance == null)
            synchronized (UserInfoService.class) {
                if (instance == null)
                    instance = new UserInfoService();
            }
        return instance;
    }

    private UserInfoService() {
    }

    private UserInfoDao mReadableDao;

    private UserInfoDao mWritableDao;

    private UserInfoDao getReadableDao() {
        if (mReadableDao == null)
            mReadableDao = GreenDaoFactory.getInstance().getReadableSession().getUserInfoDao();
        return mReadableDao;
    }

    private UserInfoDao getWritableDao() {
        if (mWritableDao == null)
            mWritableDao = GreenDaoFactory.getInstance().getWritableSession().getUserInfoDao();
        return mWritableDao;
    }

    /**
     * Add or update.
     *
     * @param userInfo {@link UserInfo}.
     */
    public void replace(UserInfo userInfo) {
        getWritableDao().insertOrReplace(userInfo);
    }

    /**
     * Delete.
     *
     * @param userInfo {@link UserInfo}.
     */
    public void delete(UserInfo userInfo) {
        getWritableDao().delete(userInfo);
    }

    /**
     * Get all users.
     *
     * @return List.
     */
    public List<UserInfo> getAll(int limit) {
        QueryBuilder<UserInfo> queryBuilder = getReadableDao().queryBuilder();
        return queryBuilder.orderDesc(UserInfoDao.Properties.Id).limit(limit).list();
    }

    /**
     * Search records based on keywords.
     *
     * @param userName user's name.
     * @param limit    limit count.
     * @return user list.
     */
    public List<UserInfo> searchByKey(String userName, int limit) {
        QueryBuilder<UserInfo> queryBuilder = getReadableDao().queryBuilder();
        return queryBuilder.where(UserInfoDao.Properties.UserName.like(userName)).limit(limit)
                .orderDesc(UserInfoDao.Properties.Id).list();
    }

    /**
     * Delete All.
     */
    public void clean() {
        getWritableDao().detachAll();
    }
}
