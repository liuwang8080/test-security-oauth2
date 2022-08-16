package com.macro.cloud.mapper;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface TSysconfigMapper {

    @Update("UPDATE T_SysConfig SET ConfValue=ConfValue+1 WHERE KindValue = #{KindValue}")
    void updateByPrimaryKey(@Param("KindValue") Long KindValue);

    @Update("UPDATE T_SysConfig SET ConfValue=#{ConfValue} WHERE KindValue = #{KindValue}")
    void updateConfValue(@Param("KindValue") Long KindValue, @Param("ConfValue") Long ConfValue);

    @Select("SELECT ConfValue FROM  T_SysConfig  WHERE KindValue = #{KindValue}")
    Long selectValue(@Param("KindValue") Long KindValue);


    void updateByID(@Param("KindValue") Long KindValue);

    List<Map> selectALL();


}