package hao.blog.www.common;

import hao.blog.www.entity.UserContent;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public static String getString(String str,Long id){
            String[] arr = str.split( "," );
            String s = "";
            if(arr!=null && arr.length>0){
                for(int i = 0; i<arr.length;i++){
                    System.out.println(id.toString().equals( arr[i] ));
                    if(id.toString().equals( arr[i] )){
                        System.out.println(arr[i]);
                        continue;
                    }else {
                        if(i == arr.length - 1){
                            s = s + arr[i] ;
                        }else {
                            s = s + arr[i] + ",";
                        }
                    }


                }
            }
        if (s.endsWith( "," )){
                s = s.substring( 0,s.length()-1 );
        }
        return s;
    }
    public static String delHTMLTag(String htmlStr){
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式

        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
        Matcher m_style=p_style.matcher(htmlStr);
        htmlStr=m_style.replaceAll(""); //过滤style标签

        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
        Matcher m_html=p_html.matcher(htmlStr);
        htmlStr=m_html.replaceAll(""); //过滤html标签

        htmlStr=htmlStr.replace(" ","");
        htmlStr=htmlStr.replaceAll("\\s*|\t|\r|\n","");
        htmlStr=htmlStr.replace("“","");
        htmlStr=htmlStr.replace("”","");
        htmlStr=htmlStr.replaceAll("　","");
        htmlStr=htmlStr.replaceAll("/&nbsp;/ig","");

        return htmlStr.trim(); //返回文本字符串
    }
    public static PageHelper.Page<UserContent> cutCont(PageHelper.Page<UserContent> page){
        List<UserContent> list=page.getResult();
        for(int i=0;i<list.size();i++){
            String temp=list.get(i).getContent();
            temp=StringUtil.delHTMLTag(temp);
            if(temp.length()>=15){
                temp=temp.substring(0,15);
            }
            list.get(i).setContent(temp);
        }
        page.setResult(list);
        return page;
    }
    public static String decode(String text){
        text=text.replace(" ","+");
        return text;
    }
}
