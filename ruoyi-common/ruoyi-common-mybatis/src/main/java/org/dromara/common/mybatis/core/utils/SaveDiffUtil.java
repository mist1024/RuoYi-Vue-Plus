package org.dromara.common.mybatis.core.utils;

import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.dromara.common.core.utils.SpringUtils;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import org.dromara.common.mybatis.core.mapper.CommonMapper;

import java.util.*;

/**
 * 比对列表数据，用于判断差异并保存到数据库中
 * 全量保存
 */
public class SaveDiffUtil {
    // 获取mybatisplus 的ID生成器
    private final static IdentifierGenerator IDENTIFIER_GENERATOR = SpringUtils.getBean("idGenerator");

    public static Map<String, Set<Long>> saveFormDiff(List formList, List oriList, BaseMapper mapper) {
        return saveFormDiff(formList, oriList, mapper, null, null);
    }

    public static Map<String, Set<Long>> saveFormDiff(List formList, List oriList, BaseMapper mapper, Boolean exeUpdate) {
        return saveFormDiff(formList, oriList, mapper, exeUpdate, null);
    }

    /**
     * 需要处理手工修改的记录
     *
     * @param formList  表单提交的数据
     * @param oriList   数据库数据
     * @param mapper    处理实体的mapper
     * @param exeUpdate 是否执行更新
     * @param updateAll 更新操作是否全字段更新
     * @throws Exception
     */
    public static Map<String, Set<Long>> saveFormDiff(List formList, List oriList, BaseMapper mapper,
                                                      Boolean exeUpdate, Boolean updateAll) {
        if (exeUpdate == null) {
            exeUpdate = false;
        }
        if (updateAll == null) {
            updateAll = false;
        }


        if (formList == null) {
            formList = new ArrayList();
        }

        Map<String, Set<Long>> result = getFormDiff(formList, oriList);

        if (result != null) {
            Set<Long> addList = result.get(BeanUtil.ADD);
            Set<Long> removeList = result.get(BeanUtil.REMOVE);
            Set<Long> updateList = result.get(BeanUtil.NORMAL);

            // 添加的对象
            if (addList != null && addList.size() > 0) {
                List addObjList = new ArrayList();
                for (Long id : addList) {
                    Iterator it = formList.iterator();
                    while (it.hasNext()) {
                        Object obj = it.next();
                        Long objId = (Long) ReflectUtil.getFieldValue(obj, "id");
                        if (id.equals(objId)) {
                            addObjList.add(obj);
//                            it.remove(); 不可删除，需要返回
                            break;
                        }
                    }
                }
                if (addObjList != null && addObjList.size() > 0) {
//                    List list = cn.hutool.core.bean.BeanUtil.copyToList(addObjList, clz);
                    ((BaseMapperPlus) mapper).insertBatch(addObjList);
                }
            }

            if (removeList != null && removeList.size() > 0) {
                mapper.deleteBatchIds(removeList);
            }

            if (exeUpdate && updateList != null && updateList.size() > 0) {
                List updateObjList = new ArrayList();

                for (Long id : updateList) {
                    Object compareObj = findObj(formList, id);
                    Object currObj = findObj(oriList, id);

                    // 比对数据库字段是否有修改
                    boolean diff = BeanUtil.diffObject(compareObj, currObj);
                    if (!diff) {
                        updateObjList.add(compareObj);
                    }
                }

                if (updateObjList.size() > 0) {
                    for (Object obj : updateObjList) {
                        if (updateAll) {
                            // 全量更新
                            ((CommonMapper) mapper).alwaysUpdateSomeColumnById(obj);
                        } else {
                            // 更新不为空
                            mapper.updateById(obj);
                        }
                    }
                }
            }
        }

        return result;
    }

    public static Map<String, Set<Long>> getFormDiff(List formList, List oriList) {
        // 需要比较的
        List<Long> compareList = new ArrayList<>();
        // 原始数据
        List<Long> currList = new ArrayList<>();

        updateObject(formList, compareList);
        updateObject(oriList, currList);

        Map<String, Set<Long>> result = BeanUtil.diffList(compareList, currList);

        return result;
    }


    private static void updateObject(List objList, List targetList, String idFieldName) {
        if (idFieldName == null) {
            idFieldName = "id";
        }

        for (Object obj : objList) {
            Object val = ReflectUtil.getFieldValue(obj, idFieldName);
            if (val != null) {
                targetList.add(val);
            } else {
                Long uuid = IDENTIFIER_GENERATOR.nextId(obj).longValue();
                targetList.add(uuid);
                ReflectUtil.setFieldValue(obj, "id", uuid);
            }
        }
    }

    private static void updateObject(List objList, List targetList) {
        updateObject(objList, targetList, null);
    }

    /**
     * 根据查询的属性找对象的key并赋值到目标列表中
     *
     * @param oriList
     * @param targetList
     * @param searchFieldName
     * @param idFieldName
     */
    public static void updateObject(List oriList, List targetList, String searchFieldName, String idFieldName) {
        if (oriList == null) {
            return;
        }
        for (Object oriObj : oriList) {
            Object searchStr = ReflectUtil.getFieldValue(oriObj, searchFieldName);

            if (searchStr != null) {
                for (Object tarObj : targetList) {
                    Object tarStr = ReflectUtil.getFieldValue(tarObj, searchFieldName);
                    if (searchStr.equals(tarStr)) {
                        Object idStr = ReflectUtil.getFieldValue(oriObj, idFieldName);
                        if (idStr != null) {
                            ReflectUtil.setFieldValue(tarObj, idFieldName, idStr);
                        }
                        continue;
                    }
                }
            }

        }
    }

    /**
     * 根据属性及属性值，从列表中查找对象
     *
     * @param objList
     * @param idFieldName
     * @param keyValue
     * @return
     */
    private static Object findObj(List objList, String idFieldName, Object keyValue) {

        if (idFieldName == null) {
            idFieldName = "id";
        }

        Object result = null;
        for (Object obj : objList) {
            Object val = ReflectUtil.getFieldValue(obj, idFieldName);
            if (keyValue.equals(val)) {
                result = obj;
                break;
            }
        }

        return result;
    }

    private static Object findObj(List objList, Object keyValue) {
        return findObj(objList, null, keyValue);
    }

//    /**
//     * 修改关系记录（根据linkID+pkId添加和删除）
//     */
//    /**
//     * @param currentUser 当前用户
//     * @param formList    表单提交的数据
//     * @param oriList     数据库中的数据
//     * @param clz         要插入表中的对象对应的类
//     * @param findId      根据哪个属性查找传入的formList、oriList
//     * @param pkIdName    根据哪个属性删除（添加）关系记录
//     * @param linkIdName  外键字段名称
//     * @param linkId      外键字段值
//     * @param mapper
//     * @throws Exception
//     */
//    public static void saveRelaDiff(
//                                    List formList,
//                                    List oriList,
//                                    Class clz,
//                                    String findId,
//                                    String pkIdName,
//                                    String linkIdName,
//                                    String linkId,
//                                    BaseMapper mapper) throws Exception {
//        // 需要比较的
//        List<String> compareList = new ArrayList<>();
//        // 原始数据
//        List<String> currList = new ArrayList<>();
//
//        updateObject(formList, compareList, findId);
//        updateObject(oriList, currList, findId);
//
//        Map<String, Set<String>> result = BeanUtil.diffList(compareList, currList);
//
//        if (result != null) {
//            Set<String> addList = result.get(BeanUtil.ADD);
//            Set<String> removeList = result.get(BeanUtil.REMOVE);
//
//            //删除数据
//            if (removeList != null && removeList.size() > 0) {
//                for (String removeId : removeList) {
//                    Object obj = clz.newInstance();
//                    ReflectUtil.setFieldValue(obj, "delFlag", DelFlag.DELETE);
//
//                    Example remExample = new Example(clz);
//                    Example.Criteria remCriteria = remExample.createCriteria();
//                    //获取删除标记为正常的记录
//                    remCriteria.andEqualTo(linkIdName, linkId);
//                    remCriteria.andEqualTo(pkIdName, removeId);
//                    remCriteria.andNotEqualTo("delFlag", DelFlag.DELETE);
//                    mapper.updateByExampleSelective(obj, remExample);
//                }
//            }
//
//            //添加资源
//            if (addList != null && addList.size() > 0) {
//                List addObjList = new ArrayList();
//                for (String addId : addList) {
//                    Object obj = clz.newInstance();
//                    ReflectUtil.setFieldValue(obj, linkIdName, linkId);
//                    ReflectUtil.setFieldValue(obj, pkIdName, addId);
//
//                    BeanUtil.setCreateUser(currentUser, obj);
//                    BeanUtil.setUpdateUser(currentUser, obj);
//
//                    addObjList.add(obj);
//                }
//
//                if (addObjList.size() > 0) {
//                    mapper.batchInsert(addObjList);
//                }
//            }
//        }
//    }
}
