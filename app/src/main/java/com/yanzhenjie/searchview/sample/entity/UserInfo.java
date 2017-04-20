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
package com.yanzhenjie.searchview.sample.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * <p>UserInfo.</p>
 * Created by Yan Zhenjie on 2017/4/16.
 */
@Entity(
        nameInDb = "user_info_search",
        indexes = {@Index(name = "index_unique_user", value = "userName", unique = true)}
)
public class UserInfo {

    /**
     * Key.
     */
    @Id(autoincrement = true)
    private Long id;

    /**
     * User name.
     */
    @NotNull
    private String userName;

    /**
     * User describe.
     */
    private String userDes;

    @Keep
    public UserInfo(Long id, @NotNull String userName, String userDes) {
        this.id = id;
        this.userName = userName;
        this.userDes = userDes;
    }

    @Keep
    public UserInfo(String userName, String userDes) {
        this.userName = userName;
        this.userDes = userDes;
    }

    @Keep
    public UserInfo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserDes() {
        return userDes;
    }

    public void setUserDes(String userDes) {
        this.userDes = userDes;
    }
}
