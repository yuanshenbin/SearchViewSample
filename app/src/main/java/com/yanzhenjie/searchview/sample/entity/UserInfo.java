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
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * <p>UserInfo.</p>
 * Created by Yan Zhenjie on 2017/4/16.
 */
@Entity(
        nameInDb = "user_info",
        indexes = {@Index(value = "userName", unique = true)}
)
public class UserInfo {

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String userName;

    private String userAge;
    private String userDes;
@Generated(hash = 2002035437)
public UserInfo(Long id, @NotNull String userName, String userAge,
        String userDes) {
    this.id = id;
    this.userName = userName;
    this.userAge = userAge;
    this.userDes = userDes;
}
@Generated(hash = 1279772520)
public UserInfo() {
}
public Long getId() {
    return this.id;
}
public void setId(Long id) {
    this.id = id;
}
public String getUserName() {
    return this.userName;
}
public void setUserName(String userName) {
    this.userName = userName;
}
public String getUserAge() {
    return this.userAge;
}
public void setUserAge(String userAge) {
    this.userAge = userAge;
}
public String getUserDes() {
    return this.userDes;
}
public void setUserDes(String userDes) {
    this.userDes = userDes;
}

}
