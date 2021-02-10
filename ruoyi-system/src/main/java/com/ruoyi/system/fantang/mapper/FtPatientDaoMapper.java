package com.ruoyi.system.fantang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.fantang.domain.FtPatientDao;
import com.ruoyi.system.fantang.vo.FtDepartVo;
import com.ruoyi.system.fantang.vo.FtPatientVo;
import com.ruoyi.system.fantang.vo.ftSyncConflictVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 病人管理Mapper接口
 *
 * @author ft
 * @date 2020-11-24
 */
@Repository
public interface FtPatientDaoMapper extends BaseMapper<FtPatientDao> {
    @Update("update ft_patient set sync_flag = 0 where off_flag = 0")
    public int initForSync();

    @Update("update ft_patient a, ft_sync b set a.name= b.name, a.bed_id = b.bed_id,  a.sync_flag = 1 where a.hospital_id = b.hospital_id and a.name = b.name and a.bed_id = b.bed_id and a.depart_id = b.depart_id")
    public int syncEqual();

    @Update("update ft_patient a, ft_sync b set a.name= b.name, a.bed_id = b.bed_id,  a.sync_flag = 1 where a.hospital_id = b.hospital_id and a.name = b.name")
    int syncEqualForHospitalAndName();

    @Update("update ft_patient a, ft_sync b set a.name= b.name, a.bed_id = b.bed_id,  a.sync_flag = 1 where a.hospital_id = b.hospital_id")
    public int syncEqualHospitalId();

    @Insert("Insert into ft_patient (hospital_id, name, depart_id, bed_id, sync_flag) select a.hospital_id, a.name, c.depart_id, a.bed_id,2 from ft_sync a LEFT JOIN ft_depart c on a.depart_name = c.depart_name  LEFT JOIN ft_patient b on a.`name` = b.`name` and c.depart_id = b.depart_id  where a.hospital_id not in (select hospital_id from ft_patient d)")
    public int syncNewHospitalId();

    @Update("update ft_patient set off_flag = 1 where sync_flag = 0")
    public int updateOffHospitalFlag();

    @Update("update ft_patient a inner join ft_sync b on a.hospital_id = b.hospital_id and a.sync_flag = 2 set a.depart_id = (select depart_id from ft_depart c where b.depart_name = c.depart_name )")
    public int updateDepartIDToNewPatient();

    @Update("update ft_sync, ft_depart set ft_sync.depart_id =  ft_depart.depart_id  where ft_sync.depart_name = ft_depart.depart_name")
    public int updateDepartIDToPatient();


    @Select("select a.hospital_id, a.name, a.depart_name, a.bed_id, b.depart_id, c.hospital_id as old_hospital_id, c.name as old_name, a.depart_name as old_depart_name, c.bed_id as old_bed_id, c.depart_id as old_depart_id, c.patient_id as patient_id from ft_sync a LEFT JOIN  ft_depart b on a.depart_name = b.depart_name LEFT JOIN ft_patient c on a.hospital_id = c.hospital_id where b.depart_id = c.depart_id and a.bed_id = c.bed_id and a.name != c.name")
    List<ftSyncConflictVo> syncConflictOnlyHospitalEqual();

    @Select("select a.hospital_id, a.name, a.depart_name, a.bed_id, b.depart_id, c.hospital_id as old_hospital_id, c.name as old_name, a.depart_name as old_depart_name, c.bed_id as old_bed_id, c.depart_id as old_depart_id, c.patient_id as patient_id from ft_sync a LEFT JOIN  ft_depart b on a.depart_name = b.depart_name LEFT JOIN ft_patient c on a.hospital_id = c.hospital_id where b.depart_id = c.depart_id and a.bed_id = c.bed_id and a.name = c.name and c.hospital_id!=a.hospital_id")
    List<ftSyncConflictVo> syncConflictOtherAllEqual();

    List<FtPatientVo> getReportMealsToday(@Param("createAt") String createAt, @Param("patientId") Long patientId);

    List<FtPatientVo> getReportMealsByDepart(@Param("departId") Long departId, @Param("createAt") String createAt);

    @Select("SELECT * from ft_patient where depart_id = #{departId} and patient_id not in (SELECT patient_id from ft_catering) ")
    List<FtPatientDao> selectNoCateringByDepartId(@Param("departId") Long departId);
}
