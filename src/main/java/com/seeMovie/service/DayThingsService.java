package com.seeMovie.service;

import com.seeMovie.pojo.DayThingsVo;

/**
 * @param :
 * @author :     mym
 * @version :    V1.0
 * @date :       2019/06/13 17:55
 * @describe :   获取历史上的今天的数据数据接口
 * @return :
 */
public interface DayThingsService {
    void insertDayThings(DayThingsVo dayThingsVo);
    //检查是否有当天的数据
    boolean checkDays(String today);
}
