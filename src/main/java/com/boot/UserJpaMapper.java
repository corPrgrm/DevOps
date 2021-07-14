package com.boot;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserJpaMapper {
     @Select("select * from UserJpa")
     List findAll();
}
