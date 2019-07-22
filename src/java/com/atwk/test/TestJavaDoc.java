package com.atwk.test;

import com.sun.deploy.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *  TestJavaDoc
 *  TODO
 * @Author wk
 * @Date 2019/6/27 23:55
 * @Version 1.0
 */
public class TestJavaDoc {

    public static void main(String[] args) {

        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(StringUtils.join(Arrays.asList("1","2","3"),","));
        sb.append(")");
        System.out.println(sb.toString());

        StringJoiner sj = new StringJoiner(",", "(", ")");
        Arrays.asList("1", "2", "3")
                .stream()
                .filter(s -> s.equals("1"))
                .collect(Collectors.toList())
                .forEach(s -> sj.add(s));

        System.out.println(sj.toString());

        System.out.println(formatList(Arrays.asList("a","b","c","d")));


    }

    /**
     * 拼接list元素
     *
     * @param paramList 参数list
     * @return java.lang.String
     */
    private static String formatList(List<String> paramList) {

            // StringJoiner用于拼接字符串，三个参数（delimiter:分割符，prefix:前缀，suffix:后缀）
            StringJoiner sj = new StringJoiner(",", "(", ")");

            //paramList.forEach(s -> sj.add(s));
            paramList.forEach(sj::add);

        return sj.toString();
    }
}
