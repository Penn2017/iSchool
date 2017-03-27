package com.imis.jxufe.model;

import java.io.Serializable;

/**
 * @author zhongping
 * @date 2017/3/25
 */
public class Constant  implements Serializable {

    private Constant() {
        /***
         * empty private constant.
         */
    }

    //所有已经注册人的邮箱
    public static final String ALL_MAIL_SET ="all_mail_set" ;
    public static final String USERNAME_OR_PASSWD_ERRO = "username_or_passwd_erro";
    public static String USER_IS_EXIST="user_is_exist";


    public static String URL_PLACEHOLDER = "urlpalceholder9988";
    public static String MAIL_CONTENT = "欢迎您注册ischool，请在2天内点击链接激活账号。 \n \n"+URL_PLACEHOLDER+"\n \n【Join class anytime ,anywhere | ISchool.】";

    /**
     * 用户预注册时候key存活时间
     */
    public static Long USER_KEY_LIVE_TIME=60 * 60 * 24 * 2L;

}
