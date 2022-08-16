package com.macro.cloud.controller;

import com.macro.cloud.mapper.TSysconfigMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by macro on 2019/9/11.
 */
@RestController
@RefreshScope
public class ConfigClientController {

    @Resource
    TSysconfigMapper tSysconfigMapper;

    @Value("${config.info}")
    private String configInfo;

    @Value("${data.info}")
    private String dataInfo;

    @GetMapping("/configInfo")
    public String getConfigInfo() {
        return configInfo;
    }

    @GetMapping("/dataInfo")
    public String getdataInfo() {
        return dataInfo;
    }

    @GetMapping("/update")
    public String update(Long id) {
        tSysconfigMapper.updateByID(id);
        return dataInfo;
    }

    @GetMapping("/selectALL")
    @ResponseBody
    public List<Map> selectALL(Long id) {
        return tSysconfigMapper.selectALL();
    }
}
