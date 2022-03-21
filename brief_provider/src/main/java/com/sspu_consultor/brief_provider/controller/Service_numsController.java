package com.sspu_consultor.brief_provider.controller;

import com.alibaba.fastjson.JSON;
import entity.CommonResult;
import entity.service_nums.Service_nums;
import entity.service_nums.Service_nums_pack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.Service_numsService;

import java.util.List;

@RestController
@RequestMapping("/service_nums")
@CrossOrigin
public class Service_numsController {
    @Autowired
    private Service_numsService service_numsService;


    @GetMapping("/selectService_numsByServcie_id")
    @ResponseBody
    public CommonResult<List<Service_nums>> selectService_numsByServcie_id(Long service_id){
        List<Service_nums> ans=service_numsService.selectService_numsByServcie_id(service_id);
        return new CommonResult<List<Service_nums>>(200,ans);
    };

    @GetMapping("/selectAllService_nums")
    @ResponseBody
    public CommonResult<String> selectAllService_nums(){
        List<Service_nums> ans=service_numsService.selectAllService_nums();

        return new CommonResult<String>(200, JSON.toJSONString(ans));
    };

    @PostMapping("/updateService_numsByService_id")
    @ResponseBody
    public CommonResult<String> updateService_numsByService_id(@RequestBody Service_nums_pack service_nums_pack){
        if(service_numsService.updateService_numsByService_id(service_nums_pack.getService_id(),service_nums_pack.getUser_id(),service_nums_pack.getQ_id(),service_nums_pack.getStart(),service_nums_pack.getEnds())>0){
            return new CommonResult<String>(200,"更新成功");
        }else{
            return new CommonResult<String>(444,"更新失败");
        }
    }
}
