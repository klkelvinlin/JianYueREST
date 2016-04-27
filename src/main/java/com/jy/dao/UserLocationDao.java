package com.jy.dao;

import org.apache.ibatis.annotations.Param;

import com.jy.dataaccess.dao.GenericDao;
import com.jy.domain.UserLocation;

public interface UserLocationDao extends GenericDao<UserLocation>{
	UserLocation getUserLocation(@Param("userId") Long userId);
}