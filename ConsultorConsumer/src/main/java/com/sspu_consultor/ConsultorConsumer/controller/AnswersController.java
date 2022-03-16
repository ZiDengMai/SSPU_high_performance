package com.sspu_consultor.ConsultorConsumer.controller;

import entity.CommonResult;
import entity.answers.Answers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/answers")
public class AnswersController {

    @Autowired
    RestTemplate restTemplate;

    @PostMapping("/insertNeoAnswer")
    @ResponseBody
    public CommonResult<String> insertNeoAnswer(@RequestBody Answers answers){
       return restTemplate.postForObject("http://Providers/answers/insertNeoAnswer",answers,CommonResult.class);
    }

    @PostMapping("/updateAnswer")
    @ResponseBody
    public CommonResult<String> updateAnswer(@RequestBody Answers answers){
        System.err.println(answers.getUser_id()+" "+answers.getQ_id()+" "+answers.getAns_content());
        return restTemplate.postForObject("http://Providers/answers/updateAnswer",answers,CommonResult.class);
    }

    @GetMapping("/findAllAnswersByQ_id")
    @ResponseBody
    public CommonResult<List<Answers>> findAllAnswersByQ_id(Long q_id){
        return restTemplate.getForObject("http://Providers/answers/findAllAnswersByQ_id",CommonResult.class);
    }

    @GetMapping("/findAllAnswersByUser_id")
    @ResponseBody
    public CommonResult<List<Answers>> findAllAnswersByUser_id(Long user_id){
        return restTemplate.getForObject("http://Providers/answers/findAllAnswersByUser_id",CommonResult.class);
    }

    @GetMapping("findAllAnswersByQ_idUser_id")
    @ResponseBody
    public CommonResult<Answers> findAllAnswersByQ_idUser_id(Long q_id,Long user_id){
        System.err.println(q_id+" "+user_id);
        return restTemplate.getForObject("http://Providers/answers/findAllAnswersByQ_idUser_id?user_id="+user_id+"&q_id="+q_id,CommonResult.class);
    }
}
