package com.sspu_consultor.ConsultorProvider.controller;

import entity.CommonResult;
import entity.answers.Answers;
import entity.question.Question;
import org.checkerframework.checker.units.qual.C;
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
        System.err.println(answers.getUser_id()+" "+answers.getQ_id()+" "+answers.getAns_content());
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

    @GetMapping("/findAllAnswersByQ_idUser_id")
    @ResponseBody
    public CommonResult<Answers> findAllAnswersByQ_idUser_id(Long user_id,Long q_id){
        Answers answers=answersService.findAllAnswersByQ_idUser_id(user_id,q_id);
        System.err.println(q_id+" "+user_id);
        //System.err.println(answers.getUser_id());
        if(answers==null){
            return new CommonResult<Answers>(500, null);
        }else{
            return new CommonResult<Answers>(200,answers);
        }
    }
}
