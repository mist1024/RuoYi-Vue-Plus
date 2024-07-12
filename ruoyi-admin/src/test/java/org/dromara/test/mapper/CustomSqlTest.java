package org.dromara.test.mapper;

public class CustomSqlTest {
    /**
     * mapper 接口
     * @Mapper
     * public interface ILoanMapper extends BaseMapper<Loan> {
     *     @DataPermission({
     *         @DataColumn(key = DataColumn.CUSTOM_SQL, value = "(#{#trackerId} = #{#user.userId}  or #{#salespersonId} = #{#user.userId})"),
     *         @DataColumn(key = {"trackerId", "salespersonId"}, value = {"l.tracker_id", "l.salesperson_id"}),
     *     })
     *     Page<Loan> selectPageLoanList(@Param("page") Page<Loan> page, @Param(Constants.WRAPPER) Wrapper<Loan> queryWrapper);
     * }
     *
     * mapper 文件
     * <select id="selectPageLoanList" resultType="com.smart330.zr.loan.domain.entity.Loan">
     *         select
     *         <if test="ew.getSqlSelect != null">
     *             ${ew.getSqlSelect}
     *         </if>
     *         <if test="ew.getSqlSelect == null">
     *             *
     *         </if>
     *         from zr_loan l
     *             ${ew.getCustomSqlSegment}
     *     </select>
     *
     *  实体
     *  public class Loan extends TenantEntity {
     *      private Long id;
     *      //业务员
     *      private Long salespersonId;
     *      //跟单员
     *      private Long trackerId;
     *      .....
     *  }
     *
     * //结果： 业务员 与 跟单员都只能看自己的数据，但每条数据 既有跟单员也有业务员
     *  select * from zr_loan  where (l.tracker_id = 1  or l.salesperson_id = 1)
     */
}
