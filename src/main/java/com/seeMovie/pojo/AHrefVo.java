package com.seeMovie.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author mym
 * @date   2018年7月7日下午1:05:06
 * 保存电影信息的实体
 */
public class AHrefVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 电影主键  32位
	 */
	private String movieId;
	/**
	 * a标签连接
	 */
	private String aHref;
	/**
	 * 电影名字
	 */
	private String  movieName;
	/**
	 * 数据来源
	 */
	private String source;
	/**
	 * 电影图片路径
	 */
	private String imgUrl;
	/**
	 * 电影描述
	 */
	private String describes;
	/**
	 * 接入日期
	 */
	private Date insertDate;
	/**
	 * 备注
	 */
	private String remarks;
	public String getMovieId() {
		return movieId;
	}
	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}
	public String getaHref() {
		return aHref;
	}
	public void setaHref(String aHref) {
		this.aHref = aHref;
	}
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getDescribes() {
		return describes;
	}
	public void setDescribes(String describes) {
		this.describes = describes;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Date getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

}
