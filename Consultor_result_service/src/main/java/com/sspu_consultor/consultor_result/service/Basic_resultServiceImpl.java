package com.sspu_consultor.consultor_result.service;


import com.alibaba.fastjson.JSON;
import com.sspu_consultor.consultor_result.mapper.AnswersMapper;
import com.sspu_consultor.consultor_result.mapper.Basic_resultMapper;
import com.sspu_consultor.consultor_result.mapper.QuestionMapper;
import entity.QuestionResult;
import entity.answers.SingleAnswer;
import entity.course.Course;
import entity.question.SingleQuestion;
import entity.answers.Answers;
import entity.basic_result.Basic_result;
import entity.question.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.Basic_resultService;

import java.util.*;

@Service
public class Basic_resultServiceImpl implements Basic_resultService {


    @Autowired
    Basic_resultMapper basic_resultMapper;

    @Autowired
    AnswersMapper answersMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Override
    public int res_basicByUser_idQ_id(Long user_id, Long q_id, String res_basic) {
          return basic_resultMapper.res_basicByUser_idQ_id(user_id,q_id,res_basic);
    }

    @Override
    public List<Basic_result> find_res_basicByUser_idQ_id(Long user_id, Long q_id) {
        return basic_resultMapper.find_res_basicByUser_idQ_id(user_id,q_id);
    }

    @Override
    public List<Basic_result> find_res_basicByUser_id(Long user_id) {
        return basic_resultMapper.find_res_basicByUser_id(user_id);
    }

    @Override
    public List<Basic_result> find_res_basicByQ_id(Long q_id) {
        return basic_resultMapper.find_res_basicByQ_id(q_id);
    }

    @Override
    public int insertRes_basicByUser_idQ_id(Long user_id, Long q_id, String res_basic) {
        return basic_resultMapper.insertRes_basicByUser_idQ_id(user_id,q_id,res_basic);
    }

    @Override
    @Transactional
    public int countAllanswersSelection(Long q_id,Long user_id) {
       //查找所有作答记录
       List<Answers> ansList = answersMapper.findAllAnswersByQ_id(q_id);
       //查找问卷
       List<Question> queList= questionMapper.selectQuestionByQ_id(q_id);
       //查找问卷的问题列表
       List<SingleQuestion> que_single_questions = JSON.parseArray(queList.get(0).getQ_content(),SingleQuestion.class);

       //表示 第几题，第几个选项的人数
       int arr[][]=new int[200][20];
       int ques_num=que_single_questions.size();

       //外循环，所有答卷
        // 表示该问卷作答者每题的作答情况，统计到数组
       for(int user_index=0;user_index<ansList.size();user_index++) {
           List<SingleAnswer> ans_singleAnswers = JSON.parseArray(ansList.get(user_index).getAns_content(),SingleAnswer.class);
           for(int question_index=0;question_index<ans_singleAnswers.size();question_index++){
              //arr[question_index][Integer.parseInt(ans_singleAnswers.get(question_index).getAnswer())]++;
               //遍历每一个选项
               List<String> allans=ans_singleAnswers.get(question_index).getAnswer();
               for(int i=0;i<allans.size();i++){
                   arr[question_index][Integer.parseInt(allans.get(i))]++;
               }
           }
       }
       List<QuestionResult> res_for_basic_result=new LinkedList<QuestionResult>();

       //外循环表示问卷的问题数
        //统计每个选项的人数，并序列化
       for(int i=0;i<ques_num;i++){
           QuestionResult questionResult=new QuestionResult();
           questionResult.setTitle(que_single_questions.get(i).getTitle());
           questionResult.setType(que_single_questions.get(i).getType());
           questionResult.setSelections(que_single_questions.get(i).getSelections());
           questionResult.setAnswers(new LinkedList<String>());
           for(int j=0;j<que_single_questions.get(i).getSelections().size();j++){
               questionResult.getAnswers().add(String.valueOf(arr[i][j]));
           }
           res_for_basic_result.add(questionResult);
       }

       for(int i=0;i<res_for_basic_result.size();i++){
           QuestionResult q=res_for_basic_result.get(i);
           System.out.println(q.getTitle()+" "+q.getType());
           for(int j=0;j<q.getSelections().size();j++){
               System.out.print(q.getSelections().get(j)+" ");
           }
           System.out.println();
       }
       String res_basic=JSON.toJSONString(res_for_basic_result);
       if(basic_resultMapper.find_res_basicByQ_id(q_id).size()==0) {
           return basic_resultMapper.insertRes_basicByUser_idQ_id(user_id,q_id,res_basic);
       }else{
           return basic_resultMapper.res_basicByUser_idQ_id(user_id,q_id,res_basic);
       }
    }

    @Override
    public int insertBasic_relationByUser_idQ_id(Long user_id, Long q_id, String basic_relation,int start,List<Integer> ends) {
        return 0;
    }

    @Override
    public int updateBasic_relationByUser_idQ_id(Long user_id, Long q_id,int start,List<Integer> ends) {
        List<Answers> ansList = answersMapper.findAllAnswersByQ_id(q_id);
        //查找问卷
        List<Question> queList= questionMapper.selectQuestionByQ_id(q_id);
        //查找问卷的问题列表
        List<SingleQuestion> que_single_questions = JSON.parseArray(queList.get(0).getQ_content(), SingleQuestion.class);

        //表示 第几题，第几个选项的人数
        int arr[][]=new int[200][20];
        int ques_num=que_single_questions.size();

        //外循环，所有答卷
        // 表示该问卷作答者每题的作答情况，统计到数组
        for(int user_index=0;user_index<ansList.size();user_index++) {
            List<SingleAnswer> ans_singleAnswers = JSON.parseArray(ansList.get(user_index).getAns_content(),SingleAnswer.class);
            for(int question_index=0;question_index<ans_singleAnswers.size();question_index++){
                //System.err.println(ans_singleAnswers.get(question_index).getAnswer());
                //arr[question_index][Integer.parseInt(ans_singleAnswers.get(question_index).getAnswer())]++;
                List<String> allans=ans_singleAnswers.get(question_index).getAnswer();
                for(int i=0;i<allans.size();i++){
                    arr[question_index][Integer.parseInt(allans.get(i))]++;
                }
            }
        }
        List<QuestionResult> res_for_basic_result=new LinkedList<QuestionResult>();

        //外循环表示问卷的问题数
        //统计每个选项的人数，并序列化
        for(int i=0;i<ques_num;i++){
            QuestionResult questionResult=new QuestionResult();
            questionResult.setTitle(que_single_questions.get(i).getTitle());
            questionResult.setType(que_single_questions.get(i).getType());
            questionResult.setSelections(que_single_questions.get(i).getSelections());
            questionResult.setAnswers(new LinkedList<String>());
            for(int j=0;j<que_single_questions.get(i).getSelections().size();j++){
                questionResult.getAnswers().add(String.valueOf(arr[i][j]));
            }
            res_for_basic_result.add(questionResult);
        }
        /*for(int i=0;i<res_for_basic_result.size();i++){
            QuestionResult q=res_for_basic_result.get(i);
            System.out.println(q.getTitle()+" "+q.getType());
            for(int j=0;j<q.getSelections().size();j++){
                System.out.print(q.getSelections().get(j)+" ");
            }
            System.out.println();
        }*/


        //获取关系图
        String s_map=queList.get(0).getC_relations();
        List<int[]> map=JSON.parseArray(s_map,int[].class);
        /*for(int i=0;i<map.size();i++){
            int array[]=map.get(i);
           for(int j=0;j<array.length;j++){
               System.out.print(array[j]+" ");
           }
           System.out.println();
        }*/
        //获取课程列表
        String s_course=queList.get(0).getCourses();
        List<Course> courses=JSON.parseArray(s_course,Course.class);
        //统计课程列表中的权值
        for(int i=0;i<courses.size();i++){
            for(int j=0;j<map.size();j++){
                int array[]=map.get(j);
                if(array[i+1]!=0){
                    array[i+1]=courses.get(i).getWeight();
                    array[i+1]*=10;
                }
            }
        }

        //统计所有问卷-》统计每张问卷每题的作答情况-》统计相关的课程

        for(int user_index=0;user_index<ansList.size();user_index++) {
            List<SingleAnswer> ans_singleAnswers = JSON.parseArray(ansList.get(user_index).getAns_content(),SingleAnswer.class);
            for(int question_index=0;question_index<ans_singleAnswers.size();question_index++){
                SingleAnswer singleAnswer=ans_singleAnswers.get(question_index);
                List<Course> course_list=singleAnswer.getRelations();
                for(int i=0;i<course_list.size();i++){
                    for(int j=0;j<map.size();j++){
                        int array[]=map.get(j);
                        int arr_index=course_list.get(i).getC_id();
                        if(array[arr_index]!=0){
                            array[arr_index]+=course_list.get(i).getWeight();
                        }
                    }
                }
            }
        }
        //String ans=JSON.toJSONString(res_for_basic_result);
        for(int i=0;i<map.size();i++){
            int array[]=map.get(i);
            for(int j=0;j<array.length;j++){
                System.out.print(array[j]+" ");
            }
            System.out.println();
        }
        List<List<Integer>> res=new LinkedList<List<Integer>>();
        for(int i=0;i<ends.size();i++){
            res.add(dijaskra(start,ends.get(i),map));
        }
        String ans=JSON.toJSONString(res);
        return basic_resultMapper.updateBasic_relationByUser_idQ_id(user_id,q_id,ans);
    }

    public List<Integer> dijaskra(int start,int ends,List<int[]> map){
        //算法主体
        int from=start;
        int n=map.size();
        boolean hasbeen[]=new boolean[n];
        int path[]=new int[n];
        Arrays.fill(path,-1);
        List<Integer> ans=new LinkedList<Integer>();
        int tmp[]=new int[n];
        for(int i=0;i<n;i++){
            tmp[i]=map.get(start)[i];
        }
        for(int i=1;i<=n;i++){
            hasbeen[from]=true;
            int min=100000;
            int index=0;
            for(int to=1;to<n;to++){
                if(map.get(from)[to]>0 && !hasbeen[to]){
                   if(tmp[to] < min){
                       min=tmp[to];
                       index=to;
                   }
                }
            }
            int to=index;
            if(to==0 || to==ends){
                break;
            }
            for(int j=1;j<n;j++) {
                if (!hasbeen[j] && map.get(to)[j] != 0 && tmp[to] + map.get(to)[j] < tmp[j]) {
                    tmp[j] = tmp[to] + map.get(to)[j];
                    path[j] = to;
                } else if (!hasbeen[j] && map.get(to)[j] != 0 && tmp[j] == 0) {
                    tmp[j] = tmp[to] + map.get(to)[j];
                    path[j] = to;
                }
            }
            from=to;
        }

        //生成路径
        Stack<Integer> stack=new Stack<Integer>();
        int node=ends;
        stack.push(ends);
        while(true){
            node=path[node];
            if(node==-1){
                break;
            }
            System.out.println(node);
            stack.push(node);
        }
        ans.add(start);
        while(!stack.isEmpty()){
            ans.add(stack.pop());
        }
        return ans;
    }
}
