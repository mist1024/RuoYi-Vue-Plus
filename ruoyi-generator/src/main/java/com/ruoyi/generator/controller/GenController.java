package com.ruoyi.generator.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.map.MapUtil;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.generator.domain.GenTable;
import com.ruoyi.generator.domain.GenTableColumn;
import com.ruoyi.generator.service.IGenTableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 代码生成 操作处理
 *
 * @author Lion Li
 */
@Validated
@Api(value = "代码生成", tags = {"代码生成管理"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/tool/gen")
public class GenController extends BaseController {

    private final IGenTableService genTableService;

    /**
     * 查询代码生成列表
     */
    @ApiOperation("查询代码生成列表")
    @SaCheckPermission("tool:gen:list")
    @GetMapping("/list")
    public TableDataInfo<GenTable> genList(GenTable genTable, PageQuery pageQuery) {
        return genTableService.selectPageGenTableList(genTable, pageQuery);
    }

    /**
     * 修改代码生成业务
     */
    @ApiOperation("修改代码生成业务")
    @SaCheckPermission("tool:gen:query")
    @GetMapping(value = "/{tableId}")
    public R<Map<String, Object>> getInfo(@PathVariable Long tableId) {
        GenTable table = genTableService.selectGenTableById(tableId);
        List<GenTable> tables = genTableService.selectGenTableAll();
        List<GenTableColumn> list = genTableService.selectGenTableColumnListByTableId(tableId);
        Map<String, Object> map = MapUtil.newHashMap(3);
        map.put("info", table);
        map.put("rows", list);
        map.put("tables", tables);
        return R.ok(map);
    }

    /**
     * 查询数据库列表
     */
    @ApiOperation("查询数据库列表")
    @SaCheckPermission("tool:gen:list")
    @GetMapping("/db/list")
    public TableDataInfo<GenTable> dataList(GenTable genTable, PageQuery pageQuery) {
        return genTableService.selectPageDbTableList(genTable, pageQuery);
    }

    /**
     * 查询数据表字段列表
     */
    @ApiOperation("查询数据表字段列表")
    @SaCheckPermission("tool:gen:list")
    @GetMapping(value = "/column/{tableId}")
    public TableDataInfo<GenTableColumn> columnList(Long tableId) {
        TableDataInfo<GenTableColumn> dataInfo = new TableDataInfo<>();
        List<GenTableColumn> list = genTableService.selectGenTableColumnListByTableId(tableId);
        dataInfo.setRows(list);
        dataInfo.setTotal(list.size());
        return dataInfo;
    }

    /**
     * 导入表结构（保存）
     */
    @ApiOperation("导入表结构（保存）")
    @SaCheckPermission("tool:gen:import")
    @Log(title = "代码生成", businessType = BusinessType.IMPORT)
    @PostMapping("/importTable")
    public R<Void> importTableSave(String tables) {
        String[] tableNames = Convert.toStrArray(tables);
        // 查询表信息
        List<GenTable> tableList = genTableService.selectDbTableListByNames(tableNames);
        genTableService.importGenTable(tableList);
        return R.ok();
    }

    /**
     * 修改保存代码生成业务
     */
    @ApiOperation("修改保存代码生成业务")
    @SaCheckPermission("tool:gen:edit")
    @Log(title = "代码生成", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Void> editSave(@Validated @RequestBody GenTable genTable) {
        genTableService.validateEdit(genTable);
        genTableService.updateGenTable(genTable);
        return R.ok();
    }

    /**
     * 删除代码生成
     */
    @ApiOperation("删除代码生成")
    @SaCheckPermission("tool:gen:remove")
    @Log(title = "代码生成", businessType = BusinessType.DELETE)
    @DeleteMapping("/{tableIds}")
    public R<Void> remove(@PathVariable Long[] tableIds) {
        genTableService.deleteGenTableByIds(tableIds);
        return R.ok();
    }

    /**
     * 预览代码
     */
    @ApiOperation("预览代码")
    @SaCheckPermission("tool:gen:preview")
    @GetMapping("/preview/{tableId}")
    public R<Map<String, String>> preview(@PathVariable("tableId") Long tableId) throws IOException {
        Map<String, String> dataMap = genTableService.previewCode(tableId);
        return R.ok(dataMap);
    }

    /**
     * 生成代码（下载方式）
     */
    @ApiOperation("生成代码（下载方式）")
    @SaCheckPermission("tool:gen:code")
    @Log(title = "代码生成", businessType = BusinessType.GENCODE)
    @GetMapping("/download/{tableName}")
    public void download(HttpServletResponse response, @PathVariable("tableName") String tableName) throws IOException {
        byte[] data = genTableService.downloadCode(tableName);
        genCode(response, data);
    }

    /**
     * 生成代码（自定义路径）
     */
    @ApiOperation("生成代码（自定义路径）")
    @SaCheckPermission("tool:gen:code")
    @Log(title = "代码生成", businessType = BusinessType.GENCODE)
    @GetMapping("/genCode/{tableName}")
    public R<Void> genCode(@PathVariable("tableName") String tableName) {
        genTableService.generatorCode(tableName);
        return R.ok();
    }

    /**
     * 同步数据库
     */
    @ApiOperation("同步数据库")
    @SaCheckPermission("tool:gen:edit")
    @Log(title = "代码生成", businessType = BusinessType.UPDATE)
    @GetMapping("/synchDb/{tableName}")
    public R<Void> synchDb(@PathVariable("tableName") String tableName) {
        genTableService.synchDb(tableName);
        return R.ok();
    }

    /**
     * 批量生成代码
     */
    @ApiOperation("批量生成代码")
    @SaCheckPermission("tool:gen:code")
    @Log(title = "代码生成", businessType = BusinessType.GENCODE)
    @GetMapping("/batchGenCode")
    public void batchGenCode(HttpServletResponse response, String tables) throws IOException {
        String[] tableNames = Convert.toStrArray(tables);
        byte[] data = genTableService.downloadCode(tableNames);
        genCode(response, data);
    }

    /**
     * 生成zip文件
     */
    private void genCode(HttpServletResponse response, byte[] data) throws IOException {
        response.reset();
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-Disposition", "attachment; filename=\"ruoyi.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IoUtil.write(response.getOutputStream(), false, data);
    }
}
