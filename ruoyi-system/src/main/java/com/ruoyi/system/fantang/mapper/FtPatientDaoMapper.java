package com.ruoyi.system.fantang.mapper;

import com.ruoyi.system.fantang.domain.FtPatientDao;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

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

    @Update("update ft_patient a, ft_sync b set a.name= b.name, a.bed_id = b.bed_id,  a.sync_flag = 1 where a.hospital_id = b.hospital_id")
    public int syncEqualHospitalId();

    @Insert("Insert into ft_patient (name, bed_id, hospital_id, sync_flag) select name, bed_id, hospital_id, 2 from ft_sync where hospital_id not in (select hospital_id from ft_patient)")
    public int syncNewHospitalId();

    @Update("update ft_patient set off_flag = 1 where sync_flag = 0")
    public int updateOffHospitalFlag();

    @Update("update ft_patient a inner join ft_sync b on a.hospital_id = b.hospital_id and a.sync_flag = 2 set a.depart_id = (select depart_id from ft_depart c where b.depart_name = c.depart_name )")
    public int updateDepartIDToNewPatient();


}
