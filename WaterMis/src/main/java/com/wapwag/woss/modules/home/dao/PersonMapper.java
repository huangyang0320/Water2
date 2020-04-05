package com.wapwag.woss.modules.home.dao;

import com.wapwag.woss.common.persistence.annotation.MyBatisDao;
import com.wapwag.woss.modules.home.entity.PersonPositionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * guoln
 */
@MyBatisDao("com.wapwag.woss.modules.home.dao.PersonMapper")
public interface PersonMapper {

    List<PersonPositionVO> getPersonNewPosition(@Param("keyword") String keyword);

    PersonPositionVO getUserInfoByMobile(String mobile);
}
