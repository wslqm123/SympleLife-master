/*
 * Copyright (c) 2015 [1076559197@qq.com | tchen0707@gmail.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.symplelife.bean;

import java.util.List;


public class ResponseNewsListEntity {

    // 头条
    public List<NewsEntity> T1348647909107;
    // 娱乐
    public List<NewsEntity> T1348648517839;
    // 体育
    public List<NewsEntity> T1348649079062;
    // 财经
    public List<NewsEntity> T1348648756099;
    // 科技
    public List<NewsEntity> T1348649580692;
    // 手机
    public List<NewsEntity> T1348649654285;
    // 影视
    public List<NewsEntity> T1348648650048;
    // 轻松一刻
    public List<NewsEntity> T1350383429665;
    // 房产
    public List<NewsEntity> T1348654085632;
    // 汽车
    public List<NewsEntity> T1348654060988;
    // 时尚
    public List<NewsEntity> T1348650593803;
    // 历史
    public List<NewsEntity> T1368497029546;

    public List<NewsEntity> getList(int position){
        switch (position){
            case 0:
                return T1348647909107;
            case 1:
                return T1348648517839;
            case 2:
                return T1348649079062;
            case 3:
                return T1348648756099;
            case 4:
                return T1348649580692;
            case 5:
                return T1348649654285;
            case 6:
                return T1348648650048;
            case 7:
                return T1350383429665;
            case 8:
                return T1348654085632;
            case 9:
                return T1348654060988;
            case 10:
                return T1348650593803;
            case 11:
                return T1368497029546;
            default:
                return T1348647909107;
        }

    }

}
