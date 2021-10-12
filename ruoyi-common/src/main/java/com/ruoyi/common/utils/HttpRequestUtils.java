package com.ruoyi.common.utils;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class HttpRequestUtils {
	public static List<Object[]> testHutoolGet(String url, String param) {
		String getResult = HttpUtil
			.createGet(url + param)
			.execute()
			.charset("utf-8")
			.body();

		JSONObject jsonObject = JSONObject.parseObject(getResult);
		JSONObject data = jsonObject.getJSONObject("data");
		JSONArray avp = data.getJSONArray("avp");
		log.info("data" + data);
		log.info("jsonarr" + avp);
		List<Object[]> list = new ArrayList<>();
		for (int i = 0; i < avp.size(); i++) {
			JSONArray subArr = avp.getJSONArray(i);
			list.add(subArr.toArray());
		}
		//Object[] array = avp.stream().toArray();
		//List<Object> list = avp.stream().collect(Collectors.toList());
		return list;
	}

	public static void main(String[] args) {

		List<Object[]> list = testHutoolGet("https://api.ownthink.com/kg/knowledge?entity=", "周杰伦");
		for (Object[] objects : list) {
			for (Object object : objects) {
				System.out.println(object);
			}
		}

	}

}
