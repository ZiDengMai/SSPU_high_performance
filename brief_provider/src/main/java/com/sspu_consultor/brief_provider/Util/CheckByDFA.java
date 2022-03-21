package com.sspu_consultor.brief_provider.Util;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class CheckByDFA {

    DfaSensitiveWords dfaSensitiveWords=new DfaSensitiveWords();

    @Bean
    public void init() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("/Users/maikabi/Downloads/sensitive_words-imain/baidu_filter.txt");
        //FileInputStream fileInputStream = new FileInputStream("/usr/bin/sspu/baidu_filter.txt");
        int  len=0;
        StringBuilder sBuffer=new StringBuilder();
        Set<String> container = new HashSet<String>();
        byte[] buf = new byte[1024];
        while ((len=fileInputStream.read(buf)) != -1)  //当n不等于-1,则代表未到末尾
        {
           // System.err.println(new String(buf,0,len));
            sBuffer.append(new String(buf,0,len));
        }
        String ans=sBuffer.toString();
        String strs[]=ans.split("\n");
        for(int i=0;i<strs.length;i++){
            container.add(strs[i]);
        }
        dfaSensitiveWords.init(container);
        //System.out.println(sBuffer.toString());
    }

   /* @Bean
    public void test(){
         Set<String> set=dfaSensitiveWords.all("她有一双豪乳，好想发伦功");
         Iterator<String> it= set.iterator();
         System.err.println("非法词汇如下:");
         while(it.hasNext()){
             System.err.println(it.next());
         }
    }*/

    public Set<String> checkIllegalWords(String text){
        return dfaSensitiveWords.all(text);
    }
}
