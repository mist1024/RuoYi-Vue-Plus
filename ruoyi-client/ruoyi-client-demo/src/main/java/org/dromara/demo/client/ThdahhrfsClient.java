package org.dromara.demo.client;

import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.JSONBody;
import com.dtflys.forest.annotation.Post;
import com.dtflys.forest.http.ForestResponse;

/**
 * demo第三方请求服务
 */
@BaseRequest(baseURL = "{thdahhrfs.ip}/fcj_zlf_caps_api",
    contentType = "application/json",
    charset = "UTF-8",
    interceptor = ThdahhrfsIntercepto.class
)
public interface ThdahhrfsClient {

    /**
     * 企业信息验证接口
     *
     * @param companyName      企业名称
     * @param organizationCode 社会统一信用代码
     * @return 返回报文
     */
    @Post(url = "/bank/ValidateEnterpriseInfo")
    ForestResponse<String> validateEnterpriselnfo(@JSONBody("CompanyName") String companyName,
                                                  @JSONBody("OrganizationCode") String organizationCode);

}
