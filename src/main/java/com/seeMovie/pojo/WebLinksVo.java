package com.seeMovie.pojo;

import java.io.Serializable;
import java.util.Date;

public class WebLinksVo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String webId;

    private String webName;

    private String webLink;

    private String crawlFlag;

    private Date insertDate;

    private Date updateDate;

    public String getWebId() {
        return webId;
    }

    public void setWebId(String webId) {
        this.webId = webId == null ? null : webId.trim();
    }

    public String getWebName() {
        return webName;
    }

    public void setWebName(String webName) {
        this.webName = webName == null ? null : webName.trim();
    }

    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink == null ? null : webLink.trim();
    }

    public String getCrawlFlag() {
        return crawlFlag;
    }

    public void setCrawlFlag(String crawlFlag) {
        this.crawlFlag = crawlFlag == null ? null : crawlFlag.trim();
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}