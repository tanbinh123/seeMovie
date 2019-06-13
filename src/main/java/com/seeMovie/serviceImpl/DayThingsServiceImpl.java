package com.seeMovie.serviceImpl;

import com.seeMovie.mapper.DayThingsVoMapper;
import com.seeMovie.pojo.DayThingsVo;
import com.seeMovie.service.DayThingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @param :
 * @author :     mym
 * @version :    V1.0
 * @date :       2019/06/13 17:55
 * @describe :   获取历史上的今天的数据
 * @return :
 */
@Service
@Transactional
public class DayThingsServiceImpl implements DayThingsService {
    @Autowired
    DayThingsVoMapper dayThingsVoMapper;
    @Override
    public void insertDayThings(DayThingsVo dayThingsVo) {
        dayThingsVoMapper.insert(dayThingsVo);
    }

    @Override
    public boolean checkDays(String today) {
        boolean returnData = false;
        try{
           if(dayThingsVoMapper.selectDaysByDayId(today) == 0){
               returnData = true;
           }
        }catch (Exception e){
            e.printStackTrace();
       }
        return returnData;
    }
}
