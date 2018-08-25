package com.seeMovie.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author 		mym
 * @date   		2018年7月7日下午1:05:06
 * @describe 	保存电影信息的实体
 * 
 */
public class MovieVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 电影主键  32位
	 */
	private String movieId;
	/**
	 * 下载连接
	 */
	private String downLoadHref;
	/**
	 * 影片类型(teleplay电视剧   除了电视剧都是电影）
	 */
	private String category;
	/**
	 * 电影名字
	 */
	private String movieName;
	/**
	 * 数据来源
	 */
	private String source;
	/**
	 * 豆瓣评分
	 */
	private BigDecimal doubanScore;
	/**
	 * 电影上映年份
	 */
	private String produceYear;
	/**
	 * 电影产地
	 */
	private String produceCountry;
	/**
	 * 电影图片路径
	 */
	private String imgUrl;
	/**
	 * 电影图片路径1
	 */
	private String imgUrl2;
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
	/**
	 * 影片类型(电影/电视剧)同步标志(Y/N)
	 */
	private String synchronousFlag;
	/**
	 * 电影搜索指数
	 */
	private Integer searchScore;
	/**
	 * 电影查看详情(或者下载)指数
	 */
    private Integer seeOrdownLoadScore;
	/**
	 * 更新日期
	 */
	private Date updateDate;
	/**
	 * 影片类型(电影/电视剧)同步标志(Y/N)
	 */
	private String synchronousImgUrlFlage;		
	public Integer getSearchScore() {
		return searchScore;
	}
	public void setSearchScore(Integer searchScore) {
		this.searchScore = searchScore;
	}
	public String getProduceCountry() {
		return produceCountry;
	}
	public void setProduceCountry(String produceCountry) {
		this.produceCountry = produceCountry;
	}
	public BigDecimal getDoubanScore() {
		return doubanScore;
	}
	public void setDoubanScore(BigDecimal doubanScore) {
		this.doubanScore = doubanScore;
	}
	public String getProduceYear() {
		return produceYear;
	}
	public void setProduceYear(String produceYear) {
		this.produceYear = produceYear;
	}
	public String getSynchronousImgUrlFlage() {
		return synchronousImgUrlFlage;
	}
	public void setSynchronousImgUrlFlage(String synchronousImgUrlFlage) {
		this.synchronousImgUrlFlage = synchronousImgUrlFlage;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getSynchronousFlag() {
		return synchronousFlag;
	}
	public void setSynchronousFlag(String synchronousFlag) {
		this.synchronousFlag = synchronousFlag;
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getMovieId() {
		return movieId;
	}
	public void setMovieId(String movieId) {
		this.movieId = movieId;
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
	public String getImgUrl2() {
		return imgUrl2;
	}
	public void setImgUrl2(String imgUrl2) {
		this.imgUrl2 = imgUrl2;
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
	public String getDownLoadHref() {
		return downLoadHref;
	}
	public void setDownLoadHref(String downLoadHref) {
		this.downLoadHref = downLoadHref;
	}
	public Integer getSeeOrdownLoadScore() {
		return seeOrdownLoadScore;
	}
	public void setSeeOrdownLoadScore(Integer seeOrdownLoadScore) {
		this.seeOrdownLoadScore = seeOrdownLoadScore;
	}

}
