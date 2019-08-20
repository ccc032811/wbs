package com.neefull.fsp.generator.servie;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.neefull.fsp.common.entity.QueryRequest;
import com.neefull.fsp.generator.entity.Column;
import com.neefull.fsp.generator.entity.Table;

import java.util.List;

/**
 * @author pei.wang
 */
public interface IGeneratorService {

    List<String> getDatabases(String databaseType);

    IPage<Table> getTables(String tableName, QueryRequest request, String databaseType, String schemaName);

    List<Column> getColumns(String databaseType, String schemaName, String tableName);
}
