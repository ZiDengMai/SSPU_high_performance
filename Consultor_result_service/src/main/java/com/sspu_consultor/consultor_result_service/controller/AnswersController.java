package com.sspu_consultor.consultor_result_service.controller;

import entity.CommonResult;
import entity.answers.Answers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.AnswersService;

import java.util.List;

@RestController
@RequestMapping("/answers")
public class AnswersController {
    @Autowired
    AnswersService answersService;

    @PostMapping("/insertNeoAnswer")
    @ResponseBody
    public CommonResult<String> insertNeoAnswer(@RequestBody Answers answers){
        int ans=answersService.insertNeoAnswer(answers.getUser_id(),answers.getQ_id(),answers.getAns_content());
        if(ans==1){
           return new CommonResult<String>(200, "答案提交成功");
        }else{
            return  new CommonResult<String>(500, "答案提交异常");
        }
    }

    @PostMapping("/updateAnswer")
    @ResponseBody
    public CommonResult<String> updateAnswer(@RequestBody Answers answers){
        int ans=answersService.updateAnswer(answers.getUser_id(),answers.getQ_id(),answers.getAns_content());
        if(ans==1){
            return new CommonResult<String>(200, "答案更新成功");
        }else{
            return  new CommonResult<String>(500, "答案更新异常");
        }
    }

    @GetMapping("/findAllAnswersByQ_id")
    @ResponseBody
    public CommonResult<List<Answers>> findAllAnswersByQ_id(Long q_id){
        return new CommonResult<List<Answers>>(200,answersService.findAllAnswersByQ_id(q_id));
    }

    @GetMapping("/findAllAnswersByUser_id")
    @ResponseBody
    public CommonResult<List<Answers>> findAllAnswersByUser_id(Long user_id){
        return new CommonResult<List<Answers>>(200,answersService.findAllAnswersByQ_id(user_id));
    }
}
