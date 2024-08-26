package org.dromara.system.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import org.dromara.common.core.constant.TenantConstants;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.tenant.helper.TenantHelper;
import org.dromara.common.web.core.BaseController;
import org.dromara.system.domain.bo.SysDictDataBo;
import org.dromara.system.domain.bo.SysDictTypeBo;
import org.dromara.system.domain.bo.SysTenantBo;
import org.dromara.system.domain.vo.SysDictDataVo;
import org.dromara.system.domain.vo.SysDictTypeVo;
import org.dromara.system.domain.vo.SysTenantVo;
import org.dromara.system.service.ISysDictDataService;
import org.dromara.system.service.ISysDictTypeService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.dromara.system.service.ISysTenantService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据字典信息
 *
 * @author Lion Li
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/dict/type")
public class SysDictTypeController extends BaseController {

    private final ISysDictTypeService dictTypeService;
    private final ISysDictDataService dictDataService;
    private final ISysTenantService tenantService;

    /**
     * 查询字典类型列表
     */
    @SaCheckPermission("system:dict:list")
    @GetMapping("/list")
    public TableDataInfo<SysDictTypeVo> list(SysDictTypeBo dictType, PageQuery pageQuery) {
        return dictTypeService.selectPageDictTypeList(dictType, pageQuery);
    }

    /**
     * 导出字典类型列表
     */
    @Log(title = "字典类型", businessType = BusinessType.EXPORT)
    @SaCheckPermission("system:dict:export")
    @PostMapping("/export")
    public void export(SysDictTypeBo dictType, HttpServletResponse response) {
        List<SysDictTypeVo> list = dictTypeService.selectDictTypeList(dictType);
        ExcelUtil.exportExcel(list, "字典类型", SysDictTypeVo.class, response);
    }

    /**
     * 查询字典类型详细
     *
     * @param dictId 字典ID
     */
    @SaCheckPermission("system:dict:query")
    @GetMapping(value = "/{dictId}")
    public R<SysDictTypeVo> getInfo(@PathVariable Long dictId) {
        return R.ok(dictTypeService.selectDictTypeById(dictId));
    }

    /**
     * 新增字典类型
     */
    @SaCheckPermission("system:dict:add")
    @Log(title = "字典类型", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Void> add(@Validated @RequestBody SysDictTypeBo dict) {
        if (!dictTypeService.checkDictTypeUnique(dict)) {
            return R.fail("新增字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        dictTypeService.insertDictType(dict);
        return R.ok();
    }

    /**
     * 修改字典类型
     */
    @SaCheckPermission("system:dict:edit")
    @Log(title = "字典类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Void> edit(@Validated @RequestBody SysDictTypeBo dict) {
        if (!dictTypeService.checkDictTypeUnique(dict)) {
            return R.fail("修改字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        dictTypeService.updateDictType(dict);
        return R.ok();
    }

    /**
     * 删除字典类型
     *
     * @param dictIds 字典ID串
     */
    @SaCheckPermission("system:dict:remove")
    @Log(title = "字典类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{dictIds}")
    public R<Void> remove(@PathVariable Long[] dictIds) {
        dictTypeService.deleteDictTypeByIds(dictIds);
        return R.ok();
    }

    /**
     * 刷新字典缓存
     */
    @SaCheckPermission("system:dict:remove")
    @Log(title = "字典类型", businessType = BusinessType.CLEAN)
    @DeleteMapping("/refreshCache")
    public R<Void> refreshCache() {
        dictTypeService.resetDictCache();
        return R.ok();
    }

    /**
     * 获取字典选择框列表
     */
    @GetMapping("/optionselect")
    public R<List<SysDictTypeVo>> optionselect() {
        List<SysDictTypeVo> dictTypes = dictTypeService.selectDictTypeAll();
        return R.ok(dictTypes);
    }

    /**
     * 同步租户字典
     */
    @GetMapping("syncTenantDict")
    @Log(title = "同步租户字典", businessType = BusinessType.INSERT)
    @Transactional(rollbackFor = Exception.class)
    @SaCheckRole(TenantConstants.SUPER_ADMIN_ROLE_KEY)
    public R<Void> syncTenantDict(String tenantId) {
        if (!TenantHelper.isEnable()) {
            return R.fail("当前未开启租户模式");
        }
        if (TenantConstants.DEFAULT_TENANT_ID.equals(tenantId)) {
            return R.fail("超级管理员无须同步字典");
        }
        //查询超管所有字典类型
        List<SysDictTypeVo> typeVos = TenantHelper.dynamic(TenantConstants.DEFAULT_TENANT_ID, dictTypeService::selectDictTypeAll);
        //查询超管所有字典data
        List<SysDictDataVo> dictDataVos = TenantHelper.dynamic(TenantConstants.DEFAULT_TENANT_ID, () -> dictDataService.selectDictDataList(new SysDictDataBo()));
        SysTenantBo sysTenantBo = new SysTenantBo();
        sysTenantBo.setTenantId(tenantId);
        List<SysTenantVo> sysTenantVos = tenantService.queryList(sysTenantBo);
        for (SysTenantVo sysTenantVo : sysTenantVos) {
            for (SysDictTypeVo typeVo : typeVos) {
                TenantHelper.dynamic(sysTenantVo.getTenantId(), () -> {
                    //如果不存在则插入
                    dictTypeService.insertIfNotExist(typeVo, dictDataVos);
                });
            }
        }
        return R.ok("同步租户字典成功");
    }
}
