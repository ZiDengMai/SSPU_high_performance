package com.sspu_consultor.ConsultorConsumer.controller;

import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import entity.CommonResult;
import entity.basic_result.StarsAndEnds;
import entity.course.*;
import entity.question.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/question")
@DefaultProperties(defaultFallback = "defaultError")
//@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600,allowedHeaders = "*",allowCredentials="true")
public class QuestionController {

    @Autowired
    RestTemplate restTemplate;


    //当前服务时长超市出错时自动调用备选方案，服务降级（专属定制版）
    @GetMapping("/insertNeoQuestion")
    @HystrixCommand(fallbackMethod = "timeout",commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="1500")
    })
    public CommonResult<Long> insertNeoQuestion(Long user_id){
        return restTemplate.getForObject("http://Providers/question/insertNeoQuestion?user_id="+user_id,CommonResult.class);
    }

    @PostMapping(value="/updateQuestion")
    @ResponseBody
    public CommonResult<String> updateQuestion(@RequestBody Question question){
        //return restTemplate.getForObject("http://Providers/question/updateQuestion?user_id="+user_id+"&q_id="+q_id+"&q_content="+q_content,CommonResult.class);

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("user_id",question.getUser_id());
        map.put("q_id",question.getQ_id());
        map.put("q_content",question.getQ_content());
        map.put("q_name",question.getQ_name());
        map.put("q_script",question.getQ_script());
        System.out.println(question.getUser_id()+" "+question.getQ_id()+" "+question.getQ_content());
        return restTemplate.postForObject("http://Providers/question/updateQuestion",map,CommonResult.class);
    }

    @GetMapping("/selectQuestionByQ_id")
    @ResponseBody
    public CommonResult<List<Question>> selectQuestionByQ_id(Long q_id){
        return restTemplate.getForObject("http://Providers/question/selectQuestionByQ_id?q_id="+q_id,CommonResult.class);
    }


    @GetMapping("/selectQuestionByUser_id")
    @ResponseBody
    public CommonResult<List<Question>> selectQuestionByUser_id(Long user_id){
        CommonResult<String> commonResult=restTemplate.getForObject("http://Providers/question/selectQuestionByUser_id?user_id="+user_id,CommonResult.class);
        List<Question> ans=JSON.parseArray(commonResult.getData(),Question.class);
        return new CommonResult<List<Question>>(200,ans);
    }

    @PostMapping("/updateCoursesByQ_id")
    @ResponseBody
    public CommonResult<String> updateCoursesByQ_id(@RequestBody Q_idAndCourses q_idAndCourses){

       /* HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("q_id",q_idAndCourses.getQ_id());
        map.put("courses",q_idAndCourses.getCourses());*/
        return restTemplate.postForObject("http://Providers/question/updateCoursesByQ_id",q_idAndCourses,CommonResult.class);
        //return new CommonResult<Integer>(1,1);
    }

    @PostMapping("/updateC_relationsByQ_id")
    @ResponseBody
    public CommonResult<Integer> updateC_relationsByQ_id(@RequestBody NodesAndEdges nodesAndEdges){
        return restTemplate.postForObject("http://Providers/question/updateC_relationsByQ_id",nodesAndEdges,CommonResult.class);
    }

    @GetMapping("/selectCoursesByQ_id")
    @ResponseBody
    public CommonResult<List<Course>> selectCoursesByQ_id(Long q_id){
       CommonResult<String> commonResult=restTemplate.getForObject("http://Providers/question/selectCoursesByQ_id?q_id="+q_id,CommonResult.class);
       String ans=(String)commonResult.getData();
       System.err.println(ans);
       List<Course> courses_list=JSON.parseArray(ans,Course.class);
       return new CommonResult<List<Course>>(200,courses_list);
    }

    @GetMapping("/updateQ_situationByQ_id")
    @ResponseBody
    public CommonResult<String> updateQ_situationByQ_id(Long q_id){
        System.err.println(q_id);
        return restTemplate.getForObject("http://Providers/question/updateQ_situationByQ_id?q_id="+q_id,CommonResult.class);
    }

    @PostMapping("/updateStartsAndEndsByQ_id")
    @ResponseBody
    public CommonResult<String> updateStartsAndEndsByQ_id(@RequestBody StarsAndEnds starsAndEnds){
        return restTemplate.postForObject("http://Providers/question/updateStartsAndEndsByQ_id",starsAndEnds,CommonResult.class);
    }

    @GetMapping("/selectC_relationsByQ_id")
    @ResponseBody
    public CommonResult<String> selectC_relationsByQ_id(Long q_id){
        return restTemplate.getForObject("http://Providers/question/selectC_relationsByQ_id?q_id="+q_id,CommonResult.class);
    }

    @GetMapping("/selectQuestionsByQ_idOrQ_Name")
    @ResponseBody
    public CommonResult<List<Question>> selectQuestionsByQ_idOrQ_Name(String key){
        CommonResult<String> ans= restTemplate.getForObject("http://Providers/question/selectQuestionsByQ_idOrQ_Name?key="+key,CommonResult.class);
        return new CommonResult<List<Question>>(200,JSON.parseArray(ans.getData(),Question.class));
    }

    @GetMapping("/selectQuestionByQ_idWithCache")
    @ResponseBody
    public CommonResult<List<Question>> selectQuestionByQ_idWithCache(Long q_id,Long user_id) {
        return restTemplate.getForObject("http://Providers/question/selectQuestionByQ_idWithCache?q_id="+q_id+"&user_id="+user_id,CommonResult.class);
    }

    @GetMapping("/getMyQuestionCache")
    public CommonResult<List<Question>> getMyQuestionCache(Long user_id){
        CommonResult<String> ans=restTemplate.getForObject("http://Providers/question/getMyQuestionCache?user_id="+user_id,CommonResult.class);
        return new CommonResult<List<Question>>(200,JSON.parseArray(ans.getData(),Question.class));
    }
    //insertNeoQuestion的降级方案
    public CommonResult<Long> timeout(Long user_id){
        return new CommonResult(500, (long) 0);
    }

    public CommonResult defaultError(){
        return new CommonResult(500,"全局性降级方法");
    }
}
