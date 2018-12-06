package com.yumee.search.solr.dao;


import com.yumee.search.solr.entity.JdProSpu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper     //声明是一个Mapper,与springbootApplication中的@MapperScan二选一写上即可
@Repository
public interface JdProSpuMapper {

    List<JdProSpu> selectJdProSpuList(@Param("startOfPage") Integer startOfPage, @Param("numberOfPage") Integer numberOfPage);

}
