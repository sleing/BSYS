package com.mt.common.system.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Mapper
@Component(value = "foreignKeyExcuteDao")
public interface ForeignKeyExcuteDao {
    public List<HashMap<String, String>> findAllForeignKeys(HashMap<String, String> hashMap);

}
