package com.sspu_consultor.brief_provider.controller;

import com.alibaba.fastjson.JSON;


import com.sspu_consultor.brief_provider.Util.CheckByDFA;
import entity.CommonResult;
import entity.basic_result.StarsAndEnds;
import entity.course.*;
import entity.question.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.QuestionService;

import java.io.UnsupportedEncodingException;
import java.util.*;

@RestController
@RequestMapping("/question")
@CrossOrigin
public class QustionController {
    @Autowired
    QuestionService questionService;

    @Autowired
    private CheckByDFA checkByDFA;

    @GetMapping("/insertNeoQuestion")
    @ResponseBody
    public CommonResult<Long> insertNeoQustion(Long user_id) throws InterruptedException {

         Long q_id=questionService.insertNeoQuestion(user_id);

         if(q_id!=0){
             return new CommonResult<Long>(200, q_id);
         }else {
             return new CommonResult<Long>(500, (long)0);
         }
    }

    @PostMapping("/updateQuestion")
    @ResponseBody
    public CommonResult<String> updateQuestion(@RequestBody Question question){
        Set<String> filter=checkByDFA.checkIllegalWords(question.getQ_content()+question.getQ_name()+question.getQ_script());
        if(!filter.isEmpty()){
            Iterator<String> it=filter.iterator();
            StringBuilder stringBuilder=new StringBuilder();
            while(it.hasNext()){
                stringBuilder.append(it.next()+" ");
            }
            return new CommonResult<String>( 300,stringBuilder.toString());
        }
        if(questionService.updateQuestion(question.getUser_id(),question.getQ_id(),question.getQ_content(),question.getQ_name(),question.getQ_script())>=1){
            return new CommonResult<String>(200,"成功了");
        }else {
            return new CommonResult<String>(500, "更新出错 ");
        }
    }

    @GetMapping("/selectQuestionByQ_id")
    @ResponseBody
    public CommonResult<List<Question>> selectQuestionByQ_id(Long q_id) throws UnsupportedEncodingException {
        List<Question> ans=questionService.selectQuestionByQ_id(q_id);
        for(int i=0;i<ans.size();i++) {
            System.err.println(ans.get(i));
        }
        return new CommonResult<List<Question>>(200, ans);
    }

    @GetMapping("/selectQuestionByUser_id")
    @ResponseBody
    public CommonResult<String> selectQuestionByUser_id(Long user_id){
        List<Question> ans=questionService.selectQuestionByUser_id(user_id);
        System.out.println("!!!!!!!!!");
        return new CommonResult<String>(200, JSON.toJSONString(ans));
    }

    @PostMapping("/updateCoursesByQ_id")
    @ResponseBody
    public CommonResult<String> updateCoursesByQ_id(@RequestBody Q_idAndCourses q_idAndCourses){
        Set<String> filter=checkByDFA.checkIllegalWords(JSON.toJSONString(q_idAndCourses.getCourses()));
        if(!filter.isEmpty()){
            Iterator<String> it=filter.iterator();
            StringBuilder stringBuilder=new StringBuilder();
            while(it.hasNext()){
                stringBuilder.append(it.next()+" ");
            }
            return new CommonResult<String>( 300,stringBuilder.toString());
        }
        if(questionService.updateCoursesByQ_id(q_idAndCourses.getQ_id(), JSON.toJSONString(q_idAndCourses.getCourses()))>0){
            return new CommonResult<String>(200, "更新失败");
        }else{
            return new CommonResult<String>(200, "更新成功");
        }
    }

    @PostMapping("/updateC_relationsByQ_id")
    @ResponseBody
    public CommonResult<Integer> updateC_relationsByQ_id(@RequestBody NodesAndEdges nodesAndEdges){
        Long q_id=nodesAndEdges.getQ_id();
        List<Node> nodes=nodesAndEdges.getNodes();
        List<Edge> edges=nodesAndEdges.getEdges();
        int n=nodes.size();
        int map[][]=new int[n+1][n+1];
        for(int i=0;i<nodes.size();i++){
            System.err.print(nodes.get(i).getLabel()+" ");
        }
        System.out.println();
        Map<String,Integer> n_idTOc_id=new HashMap<String,Integer>();
        for(int i=0;i<nodes.size();i++){
            Node node=nodes.get(i);
            Course course=JSON.parseObject(node.getLabel(), Course.class);
            int c_id=course.getC_id();
            n_idTOc_id.put(node.getId(),c_id);
        }
        if(edges!=null){
            for(int i=0;i<edges.size();i++) {
                String f=edges.get(i).getFrom();
                String t=edges.get(i).getTo();
                int from=n_idTOc_id.get(f);
                int to=n_idTOc_id.get(t);
                map[from][to]=1;
                System.err.print(edges.get(i).getFrom() + " " + edges.get(i).getTo()+" ");
                System.out.println();
            }
        }
        String c_relations= JSON.toJSONString(map);
        if(questionService.updateC_relationsByQ_id(q_id,c_relations)>0){
            return new CommonResult<Integer>(200, 1);
        }else{
            return new CommonResult<Integer>(200, 0);
        }
    }

    @GetMapping("/selectCoursesByQ_id")
    public CommonResult<String> selectCoursesByQ_id(Long q_id){
        String ans= questionService.selectCoursesByQ_id(q_id);
        return new CommonResult<String>(200, ans);
    }

    @GetMapping("/selectC_relationsByQ_id")
    public CommonResult<String> selectC_relationsByQ_id(Long q_id){
        String ans= questionService.selectC_relationsByQ_id(q_id);
        return new CommonResult<String>(200, ans);
    }

    @GetMapping("/updateQ_situationByQ_id")
    @ResponseBody
    public CommonResult<String> updateQ_situationByQ_id(Long q_id){
        int ans=questionService.updateQ_situationByQ_id(q_id);
        if(ans==1){
            return new CommonResult<String>(200, "发布成功");
        }else{
            return new CommonResult<String>(400, "操作错误发布不成功");
        }
    }

    @PostMapping("/updateStartsAndEndsByQ_id")
    @ResponseBody
    public CommonResult<String> updateStartsAndEndsByQ_id(@RequestBody StarsAndEnds starsAndEnds){
           int ans=questionService.updateStartsAndEndsByQ_id(starsAndEnds.getQ_id(),starsAndEnds.getStart(),starsAndEnds.getEnds());
           if(ans==1){
               return new CommonResult<String>(200, "上传成功");
           }else{
               return new CommonResult<String>(400, "上传失败");
           }
    }

    public CommonResult<Long> timeout(Long user_id){
        return new CommonResult(500,"超时了，当前服务出现卡顿");
    }
}
