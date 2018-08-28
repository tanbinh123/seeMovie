package com.seeMovie.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author      mym
 * @date        2018/8/28 0028 22:03
 * @describe    后端首页左侧加载菜单实体
 * @version     V1.0
 * @param       后端首页左侧加载菜单实体
 * @return      
*/
public class MenuQueryVo extends MenuVo implements Serializable {
    private List<MenuQueryVo> menuQueryVoList;

    public List<MenuQueryVo> getMenuQueryVoList() {
        return menuQueryVoList;
    }

    public void setMenuQueryVoList(List<MenuQueryVo> menuQueryVoList) {
        this.menuQueryVoList = menuQueryVoList;
    }
}