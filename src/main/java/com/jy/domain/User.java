package com.jy.domain;

import java.util.Date;

import com.jy.dataaccess.dao.AbstractDomainObject;
import com.jy.utils.DateUtils;

public class User extends AbstractDomainObject{
    private Long id;

    private String name;
    
    private String deviceToken;

    private String gender;
    
    private String interestIn;

    private Integer likes;

    private Boolean enabled;

    private Date lastlogintime;

    private Date createtime;

    private String pic;
    
    private Integer picWidth;
    
    private Integer picHeight;

    private Integer ageId;

    private String uuid;

    private Integer cityId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken == null ? null : deviceToken.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Date getLastlogintime() {
        return lastlogintime;
    }

    public void setLastlogintime(Date lastlogintime) {
        this.lastlogintime = lastlogintime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    public Integer getAgeId() {
        return ageId;
    }

    public void setAgeId(Integer ageId) {
        this.ageId = ageId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }
    
    public String getInterestIn() {
		return interestIn;
	}

	public void setInterestIn(String interestIn) {
		this.interestIn = interestIn;
	}
	
	public Integer getPicWidth() {
		return picWidth;
	}

	public void setPicWidth(Integer picWidth) {
		this.picWidth = picWidth;
	}

	public Integer getPicHeight() {
		return picHeight;
	}

	public void setPicHeight(Integer picHeight) {
		this.picHeight = picHeight;
	}

	public String getCreatetimeForUI(){
    	return DateUtils.switchToUsTime(this.createtime);
    }
    
    public String getLastlogintimeForUI(){
    	return DateUtils.switchToUsTime(this.lastlogintime);
    }
    
}