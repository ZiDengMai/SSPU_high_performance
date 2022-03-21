package com.sspu_consultor.brief_provider.Util;

import java.util.*;

/**
 * 敏感词处理 -- DFA算法
 *
 */

public class DfaSensitiveWords implements ISensitiveWords {

    private final static String END_MARK_KEY = "end";

    private final static int MIN_MATCH_FLAG = 2;

    private HashMap wordsMap;

    @Override
    public void init(Collection<String> keywords) {
        if (keywords == null || keywords.isEmpty()) {
            throw new IllegalArgumentException("The param of keywords cannot be empty");
        }
        wordsMap= new HashMap(keywords.size());
        for (String keyword : keywords) {
            if (keyword == null) {
                continue;
            }
            Map nowMap = wordsMap;
            char[] chars = keyword.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                char keyChar = chars[i];
                Object wordMap = nowMap.get(keyChar);

                if (wordMap != null) {
                    //如果存在该key，直接赋值
                    nowMap = (Map) wordMap;
                } else {
                    //不存在则，则构建一个map，同时将end设置为false，因为他不是最后一个
                    Map<String, Object> newWorMap = new HashMap<>(4);
                    nowMap.put(keyChar, newWorMap);
                    nowMap = newWorMap;
                }

                if (i == chars.length - 1) {
                    //最后一个
                    nowMap.put(END_MARK_KEY, true);
                }
            }
        }
    }

    @Override
    public void add(String keyword) {
        if (wordsMap== null) {
            throw new IllegalArgumentException("Please initialize first");
        }
    }

    @Override
    public boolean contain(String words) {
        for (int i = 0; i < words.length(); i++) {
            int matchFlag = this.check(words, i);
            if (matchFlag > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Optional<String> first(String words) {
        for (int i = 0; i < words.length(); i++) {
            int length = this.check(words, i);
            if (length > 0) {
                return Optional.of(words.substring(i, i + length));
            }
        }
        return Optional.empty();
    }

    @Override
    public Set<String> all(String words) {
        Set<String> sensitiveWordList = new HashSet<>();

        for (int i = 0; i < words.length(); i++) {
            int length = check(words, i);
            if (length > 0) {
                sensitiveWordList.add(words.substring(i, i + length));
                i = i + length - 1;

            }
        }

        return sensitiveWordList;
    }

    /**
     * 检查文字中是否包含敏感字符
     *
     * @param words      文本
     * @param beginIndex 开始位置
     * @return 如果存在，则返回敏感词字符的长度，不存在返回0
     */
    private int check(String words, int beginIndex) {
        boolean flag = false;
        int matchFlag = 0;
        Map nowMap = wordsMap;
        for (int i = beginIndex; i < words.length(); i++) {
            char word = words.charAt(i);
            nowMap = (Map) nowMap.get(word);
            if (nowMap == null) {
                break;
            }
            matchFlag++;
            flag = Boolean.TRUE.equals(nowMap.get(END_MARK_KEY));
        }
        return matchFlag >= MIN_MATCH_FLAG && flag ? matchFlag : 0;

    }
}




