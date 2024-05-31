package org.dromara.demo.util;

import com.dtflys.forest.Forest;
import com.dtflys.forest.http.ForestResponse;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.forest.exception.ForestException;
import org.dromara.common.json.utils.JsonUtils;
import org.dromara.demo.client.ThdahhrfsClient;
import org.dromara.demo.domain.ThdahhrfsResult;

/**
 * demo第三方请求服务请求工具
 */
public class ThdahhrfsUtil {
    private static ThdahhrfsClient CLIENT = Forest.client(ThdahhrfsClient.class);

    /**
     * 企业信息验证接口
     *
     * @param companyName      企业名称
     * @param organizationCode 社会统一信用代码
     * @return 返回报文
     */
    public static ThdahhrfsResult validateEnterpriselnfo(String companyName, String organizationCode) {
        ForestResponse<String> response = CLIENT.validateEnterpriselnfo(companyName, organizationCode);
        if (!response.isSuccess()) {
            throw new ForestException("请求第三方异常：" + response.getException());
        }
        String result = response.getResult();
        if (StringUtils.isBlank(result)) {
            return null;
        }
        return JsonUtils.parseObject(result, ThdahhrfsResult.class);
    }

}
