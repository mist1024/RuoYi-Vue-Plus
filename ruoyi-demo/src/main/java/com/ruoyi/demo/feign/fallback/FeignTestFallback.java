package com.jiajiakang.demo.feign.fallback;


import com.jiajiakang.demo.feign.FeignTestService;

public class FeignTestFallback implements FeignTestService {

    @Override
    public String search(String wd) {
        return null;
    }
}
