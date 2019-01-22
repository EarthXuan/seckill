package com.ex.seckill.dao;

import com.ex.seckill.domain.SecGoods;
import com.ex.seckill.domain.SecGoodsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SecGoodsMapper {
    int countByExample(SecGoodsExample example);

    int deleteByExample(SecGoodsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SecGoods record);

    int insertSelective(SecGoods record);

    List<SecGoods> selectByExample(SecGoodsExample example);

    SecGoods selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SecGoods record, @Param("example") SecGoodsExample example);

    int updateByExample(@Param("record") SecGoods record, @Param("example") SecGoodsExample example);

    int updateByPrimaryKeySelective(SecGoods record);

    int updateByPrimaryKey(SecGoods record);

    int reduceSecGoodsStock(SecGoods secGoods);
}