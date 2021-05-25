package com.ruoyi.generator.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.constant.GenConstants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.generator.domain.GenTable;
import com.ruoyi.generator.domain.GenTableColumn;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 模板处理工具类
 *
 * @author ruoyi
 */
public class VelocityUtils
{
    /** 项目空间路径 */
    private static final String PROJECT_PATH = "main/java";

    /** mybatis空间路径 */
    private static final String MYBATIS_PATH = "main/resources/mapper";

    /** 默认上级菜单，系统工具 */
    private static final String DEFAULT_PARENT_MENU_ID = "3";

    /**
     * 设置模板变量信息
     *
     * @return 模板列表
     */
    public static VelocityContext prepareContext(GenTable genTable)
    {
        String moduleName = genTable.getModuleName();
        String businessName = genTable.getBusinessName();
        String packageName = genTable.getPackageName();
        String tplCategory = genTable.getTplCategory();
        String functionName = genTable.getFunctionName();

        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("tplCategory", genTable.getTplCategory());
        velocityContext.put("tableName", genTable.getTableName());
        velocityContext.put("functionName", StrUtil.isNotEmpty(functionName) ? functionName : "【请填写功能名称】");
        velocityContext.put("ClassName", genTable.getClassName());
        velocityContext.put("className", StrUtil.lowerFirst(genTable.getClassName()));
        velocityContext.put("moduleName", genTable.getModuleName());
        velocityContext.put("BusinessName", StrUtil.upperFirst(genTable.getBusinessName()));
        velocityContext.put("businessName", genTable.getBusinessName());
        velocityContext.put("basePackage", getPackagePrefix(packageName));
        velocityContext.put("packageName", packageName);
        velocityContext.put("author", genTable.getFunctionAuthor());
        velocityContext.put("datetime", DateUtils.getDate());
        velocityContext.put("pkColumn", genTable.getPkColumn());
        velocityContext.put("importList", getImportList(genTable));
        velocityContext.put("permissionPrefix", getPermissionPrefix(moduleName, businessName));
        velocityContext.put("columns", genTable.getColumns());
        velocityContext.put("table", genTable);
        setMenuVelocityContext(velocityContext, genTable);
        if (GenConstants.TPL_TREE.equals(tplCategory))
        {
            setTreeVelocityContext(velocityContext, genTable);
        }
        if (GenConstants.TPL_SUB.equals(tplCategory))
        {
            setSubVelocityContext(velocityContext, genTable);
        }
        if (GenConstants.TPL_JOIN.equals(tplCategory))
        {
            setJoinVelocityContext(velocityContext, genTable);
        }
        return velocityContext;
    }

    public static void setMenuVelocityContext(VelocityContext context, GenTable genTable)
    {
        String options = genTable.getOptions();
        JSONObject paramsObj = JSONObject.parseObject(options);
        String parentMenuId = getParentMenuId(paramsObj);
        context.put("parentMenuId", parentMenuId);
    }

    public static void setTreeVelocityContext(VelocityContext context, GenTable genTable)
    {
        String options = genTable.getOptions();
        JSONObject paramsObj = JSONObject.parseObject(options);
        String treeCode = getTreecode(paramsObj);
        String treeParentCode = getTreeParentCode(paramsObj);
        String treeName = getTreeName(paramsObj);

        context.put("treeCode", treeCode);
        context.put("treeParentCode", treeParentCode);
        context.put("treeName", treeName);
        context.put("expandColumn", getExpandColumn(genTable));
        if (paramsObj.containsKey(GenConstants.TREE_PARENT_CODE))
        {
            context.put("tree_parent_code", paramsObj.getString(GenConstants.TREE_PARENT_CODE));
        }
        if (paramsObj.containsKey(GenConstants.TREE_NAME))
        {
            context.put("tree_name", paramsObj.getString(GenConstants.TREE_NAME));
        }
    }

    public static void setSubVelocityContext(VelocityContext context, GenTable genTable)
    {
        GenTable subTable = genTable.getSubTable();
        String subTableName = genTable.getSubTableName();
        String subTableFkName = genTable.getSubTableFkName();
        String subClassName = genTable.getSubTable().getClassName();
        String subTableFkClassName = StrUtil.toCamelCase(subTableFkName);

        context.put("subTable", subTable);
        context.put("subTableName", subTableName);
        context.put("subTableFkName", subTableFkName);
        context.put("subTableFkClassName", subTableFkClassName);
        context.put("subTableFkclassName", StrUtil.lowerFirst(subTableFkClassName));
        context.put("subClassName", subClassName);
        context.put("subclassName", StrUtil.lowerFirst(subClassName));
        context.put("subImportList", getImportList(genTable.getSubTable()));
    }

	/**
	 * 关联表类型的相关参数封装
	 * @param context
	 * @param genTable
	 */
	public static void setJoinVelocityContext(VelocityContext context, GenTable genTable) {
		List<GenTable.JoinInfo> joinInfos = genTable.getJoinInfos();
		Map<String, GenTable> joinTableMap = genTable.getJoinTableMap();
		List<GenTableColumn> genTableColumns = genTable.getColumns();
		Map<String, GenTableColumn> genTableColumnMap = CollUtil.fieldValueMap(genTableColumns, "columnName");
		List<Map<String, Object>> joinInfoList = new ArrayList<>();
		context.put("joinInfos", joinInfoList);
		if (!CollectionUtils.isEmpty(joinInfos)) {
			for (GenTable.JoinInfo joinInfo : joinInfos) {
				Map<String, Object> joinInfoMap = new HashMap<>();
				joinInfoList.add(joinInfoMap);

				String joinTable = joinInfo.getJoinTable();
				String joinTableClassName = StrUtil.toCamelCase(joinTable);
				String joinTableLowerClassName = StrUtil.lowerFirst(joinTableClassName);
				String joinTableUpperClassName = StrUtil.upperFirst(joinTableClassName);

				joinInfoMap.put("joinGenTable", joinTableMap.get(joinTable));
				joinInfoMap.put("joinGenTableBusinessName", StrUtil.upperFirst(joinTableMap.get(joinTable).getBusinessName()));
				joinInfoMap.put("joinGenTableModuleName", StrUtil.upperFirst(joinTableMap.get(joinTable).getModuleName()));
				joinInfoMap.put("joinTable", joinTable);
				joinInfoMap.put("joinTableClassName", joinTableClassName);
				joinInfoMap.put("joinTableLowerClassName", joinTableLowerClassName);
				joinInfoMap.put("joinTableUpperClassName", joinTableUpperClassName);

				String tableFkName = joinInfo.getTableFkName();
				joinInfoMap.put("tableFkName", tableFkName);
				GenTableColumn genTableColumn = genTableColumnMap.get(tableFkName);
				// 主表关联的字段(tableFkName)，如果本身就在列表中展示，就会在各个vm中生成拼接，如果不需要在列表中展示，关联查询却仍需要这个字段，为避免重复生成增加该flag进行判断
				joinInfoMap.put("tableFkGenerateFlag", !genTableColumn.isList());
				joinInfoMap.put("tableFkColumn", genTableColumn);

				String joinField = joinInfo.getJoinField();
				joinInfoMap.put("joinField", joinField);


				LinkedHashSet<String> showFields = joinInfo.getShowFields();
				List<Map<String, String>> showFieldList = new ArrayList<>();
				joinInfoMap.put("showFields", showFieldList);
				if (!CollectionUtils.isEmpty(showFields)) {
					GenTable joinGenTable = joinTableMap.get(joinTable);
					List<GenTableColumn> columns = joinGenTable.getColumns();
					Map<String, GenTableColumn> columnMap = CollUtil.fieldValueMap(columns, "columnName");

					for (String showField : showFields) {
						Map<String, String> showFieldMap = new HashMap<>();
						showFieldList.add(showFieldMap);
						String showFiledName = StrUtil.toCamelCase(showField);
						String showFiledUpperName = StrUtil.upperFirst(showFiledName);
						showFieldMap.put("showField", showField);
						showFieldMap.put("showFiledName", showFiledName);
						showFieldMap.put("showFiledUpperName", showFiledUpperName);
						GenTableColumn column = columnMap.get(showField);
						showFieldMap.put("showFieldJavaType", column.getJavaType());
						showFieldMap.put("showFieldDictType", column.getDictType());
						if (StringUtils.isNotBlank(column.getColumnComment())) {
							showFieldMap.put("showFieldComment", column.getColumnComment());
						} else {
							showFieldMap.put("showFieldComment", joinTableLowerClassName + showFiledUpperName);
						}
					}
				}
				LinkedHashSet<String> queryFields = joinInfo.getQueryFields();
				List<Map<String, String>> queryFieldList = new ArrayList<>();
				joinInfoMap.put("queryFields", queryFieldList);
				if (!CollectionUtils.isEmpty(queryFields)) {
					GenTable joinGenTable = joinTableMap.get(joinTable);
					List<GenTableColumn> columns = joinGenTable.getColumns();
					Map<String, GenTableColumn> columnMap = CollUtil.fieldValueMap(columns, "columnName");
					for (String queryField : queryFields) {
						Map<String, String> queryFieldMap = new HashMap<>();
						queryFieldList.add(queryFieldMap);
						String queryFiledName = StrUtil.toCamelCase(queryField);
						String queryFiledUpperName = StrUtil.upperFirst(queryFiledName);
						queryFieldMap.put("queryField", queryField);
						queryFieldMap.put("queryFiledName", queryFiledName);
						queryFieldMap.put("queryFiledUpperName", queryFiledUpperName);

						GenTableColumn column = columnMap.get(queryField);
						if (StringUtils.isNotBlank(column.getColumnComment())) {
							queryFieldMap.put("queryFieldComment", column.getColumnComment());
						} else {
							queryFieldMap.put("queryFieldComment", joinTableLowerClassName + queryFiledUpperName);
						}
					}
				}
			}
		}
	}

    /**
     * 获取模板信息
     *
     * @return 模板列表
     */
    public static List<String> getTemplateList(String tplCategory)
    {
        List<String> templates = new ArrayList<String>();
        templates.add("vm/java/domain.java.vm");
        templates.add("vm/java/vo.java.vm");
        templates.add("vm/java/queryBo.java.vm");
        templates.add("vm/java/addBo.java.vm");
        templates.add("vm/java/editBo.java.vm");
        templates.add("vm/java/mapper.java.vm");
        templates.add("vm/java/service.java.vm");
        templates.add("vm/java/serviceImpl.java.vm");
        templates.add("vm/java/controller.java.vm");
        templates.add("vm/xml/mapper.xml.vm");
        templates.add("vm/sql/sql.vm");
        templates.add("vm/js/api.js.vm");
        if (GenConstants.TPL_CRUD.equals(tplCategory) || GenConstants.TPL_JOIN.equals(tplCategory))
        {
            templates.add("vm/vue/index.vue.vm");
        }
        else if (GenConstants.TPL_TREE.equals(tplCategory))
        {
            templates.add("vm/vue/index-tree.vue.vm");
        }
        else if (GenConstants.TPL_SUB.equals(tplCategory))
        {
            templates.add("vm/vue/index.vue.vm");
            templates.add("vm/java/sub-domain.java.vm");
        }
        return templates;
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, GenTable genTable)
    {
        // 文件名称
        String fileName = "";
        // 包路径
        String packageName = genTable.getPackageName();
        // 模块名
        String moduleName = genTable.getModuleName();
        // 大写类名
        String className = genTable.getClassName();
        // 业务名称
        String businessName = genTable.getBusinessName();

        String javaPath = PROJECT_PATH + "/" + StrUtil.replace(packageName, ".", "/");
        String mybatisPath = MYBATIS_PATH + "/" + moduleName;
        String vuePath = "vue";

        if (template.contains("domain.java.vm"))
        {
            fileName = StrUtil.format("{}/domain/{}.java", javaPath, className);
        }
        if (template.contains("vo.java.vm"))
        {
            fileName = StrUtil.format("{}/vo/{}Vo.java", javaPath, className);
        }
        if (template.contains("queryBo.java.vm"))
        {
            fileName = StrUtil.format("{}/bo/{}QueryBo.java", javaPath, className);
        }
        if (template.contains("addBo.java.vm"))
        {
            fileName = StrUtil.format("{}/bo/{}AddBo.java", javaPath, className);
        }
        if (template.contains("editBo.java.vm"))
        {
            fileName = StrUtil.format("{}/bo/{}EditBo.java", javaPath, className);
        }
        if (template.contains("sub-domain.java.vm") && StrUtil.equals(GenConstants.TPL_SUB, genTable.getTplCategory()))
        {
            fileName = StrUtil.format("{}/domain/{}.java", javaPath, genTable.getSubTable().getClassName());
        }
        else if (template.contains("mapper.java.vm"))
        {
            fileName = StrUtil.format("{}/mapper/{}Mapper.java", javaPath, className);
        }
        else if (template.contains("service.java.vm"))
        {
            fileName = StrUtil.format("{}/service/I{}Service.java", javaPath, className);
        }
        else if (template.contains("serviceImpl.java.vm"))
        {
            fileName = StrUtil.format("{}/service/impl/{}ServiceImpl.java", javaPath, className);
        }
        else if (template.contains("controller.java.vm"))
        {
            fileName = StrUtil.format("{}/controller/{}Controller.java", javaPath, className);
        }
        else if (template.contains("mapper.xml.vm"))
        {
            fileName = StrUtil.format("{}/{}Mapper.xml", mybatisPath, className);
        }
        else if (template.contains("sql.vm"))
        {
            fileName = businessName + "Menu.sql";
        }
        else if (template.contains("api.js.vm"))
        {
            fileName = StrUtil.format("{}/api/{}/{}.js", vuePath, moduleName, businessName);
        }
        else if (template.contains("index.vue.vm"))
        {
            fileName = StrUtil.format("{}/views/{}/{}/index.vue", vuePath, moduleName, businessName);
        }
        else if (template.contains("index-tree.vue.vm"))
        {
            fileName = StrUtil.format("{}/views/{}/{}/index.vue", vuePath, moduleName, businessName);
        }
        return fileName;
    }

    /**
     * 获取包前缀
     *
     * @param packageName 包名称
     * @return 包前缀名称
     */
    public static String getPackagePrefix(String packageName)
    {
        int lastIndex = packageName.lastIndexOf(".");
        String basePackage = StrUtil.sub(packageName, 0, lastIndex);
        return basePackage;
    }

    /**
     * 根据列类型获取导入包
     *
     * @param genTable 业务表对象
     * @return 返回需要导入的包列表
     */
    public static HashSet<String> getImportList(GenTable genTable)
    {
        List<GenTableColumn> columns = genTable.getColumns();
        GenTable subGenTable = genTable.getSubTable();
        HashSet<String> importList = new HashSet<String>();
        if (Validator.isNotNull(subGenTable))
        {
            importList.add("java.util.List");
        }
        for (GenTableColumn column : columns)
        {
            if (!column.isSuperColumn() && GenConstants.TYPE_DATE.equals(column.getJavaType()))
            {
                importList.add("java.util.Date");
                importList.add("com.fasterxml.jackson.annotation.JsonFormat");
            }
            else if (!column.isSuperColumn() && GenConstants.TYPE_BIGDECIMAL.equals(column.getJavaType()))
            {
                importList.add("java.math.BigDecimal");
            }
        }
        return importList;
    }

    /**
     * 获取权限前缀
     *
     * @param moduleName 模块名称
     * @param businessName 业务名称
     * @return 返回权限前缀
     */
    public static String getPermissionPrefix(String moduleName, String businessName)
    {
        return StrUtil.format("{}:{}", moduleName, businessName);
    }

    /**
     * 获取上级菜单ID字段
     *
     * @param paramsObj 生成其他选项
     * @return 上级菜单ID字段
     */
    public static String getParentMenuId(JSONObject paramsObj)
    {
        if (Validator.isNotEmpty(paramsObj) && paramsObj.containsKey(GenConstants.PARENT_MENU_ID))
        {
            return paramsObj.getString(GenConstants.PARENT_MENU_ID);
        }
        return DEFAULT_PARENT_MENU_ID;
    }

    /**
     * 获取树编码
     *
     * @param paramsObj 生成其他选项
     * @return 树编码
     */
    public static String getTreecode(JSONObject paramsObj)
    {
        if (paramsObj.containsKey(GenConstants.TREE_CODE))
        {
            return StrUtil.toCamelCase(paramsObj.getString(GenConstants.TREE_CODE));
        }
        return StrUtil.EMPTY;
    }

    /**
     * 获取树父编码
     *
     * @param paramsObj 生成其他选项
     * @return 树父编码
     */
    public static String getTreeParentCode(JSONObject paramsObj)
    {
        if (paramsObj.containsKey(GenConstants.TREE_PARENT_CODE))
        {
            return StrUtil.toCamelCase(paramsObj.getString(GenConstants.TREE_PARENT_CODE));
        }
        return StrUtil.EMPTY;
    }

    /**
     * 获取树名称
     *
     * @param paramsObj 生成其他选项
     * @return 树名称
     */
    public static String getTreeName(JSONObject paramsObj)
    {
        if (paramsObj.containsKey(GenConstants.TREE_NAME))
        {
            return StrUtil.toCamelCase(paramsObj.getString(GenConstants.TREE_NAME));
        }
        return StrUtil.EMPTY;
    }

    /**
     * 获取需要在哪一列上面显示展开按钮
     *
     * @param genTable 业务表对象
     * @return 展开按钮列序号
     */
    public static int getExpandColumn(GenTable genTable)
    {
        String options = genTable.getOptions();
        JSONObject paramsObj = JSONObject.parseObject(options);
        String treeName = paramsObj.getString(GenConstants.TREE_NAME);
        int num = 0;
        for (GenTableColumn column : genTable.getColumns())
        {
            if (column.isList())
            {
                num++;
                String columnName = column.getColumnName();
                if (columnName.equals(treeName))
                {
                    break;
                }
            }
        }
        return num;
    }
}
