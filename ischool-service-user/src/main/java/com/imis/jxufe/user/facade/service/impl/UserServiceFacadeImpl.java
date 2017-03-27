package com.imis.jxufe.user.facade.service.impl;

import com.google.gson.Gson;
import com.imis.jxufe.model.Constant;
import com.imis.jxufe.model.IschoolUser;
import com.imis.jxufe.redis.facade.RedisServiceFacade;
import com.imis.jxufe.user.facade.UserServiceFacade;
import com.imis.jxufe.user.mapper.UserMapper;
import com.imis.jxufe.utils.IdWorker;
import com.imis.jxufe.utils.NickImageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * 用户服务接口实现
 *
 * @author zhongping
 * @date 2017/3/21
 */
@Component("userService")
public class UserServiceFacadeImpl implements UserServiceFacade {


    @Resource(name = "redisServiceFacade")
    private RedisServiceFacade redisServiceFacade;

    private Gson gson = new Gson();

    /**
     * reids服务
     */

    @Autowired
    private UserMapper userMapper;

    /**
     * id生成器
     */
    private IdWorker idWorker = new IdWorker(1, 0);
    private static Logger logger = LoggerFactory.getLogger(UserServiceFacadeImpl.class);


    @Override
    @Transactional(readOnly = true)
    public boolean userEmailIsExist(String email) {
        //redis里面寻找
        Set emailSet = redisServiceFacade.getObject(Constant.ALL_MAIL_SET, HashSet.class);
        if (emailSet != null && !emailSet.isEmpty()) {
            if (emailSet.contains(email)) {
                return true;
            }
        }
        //数据库中寻找
        int count = userMapper.selectCount(new IschoolUser(email));
        return count > 0;
    }

    public String preCreateUser(String email, String name, String passwd, Integer type) {
        Map<String, Object> resutl = new HashMap();

        //验证邮箱
        boolean userEmailIsExist = userEmailIsExist(email);
        if (userEmailIsExist) {
            return Constant.USER_IS_EXIST;
        }
        //密码加密
        String encodingPasswd = null;

        logger.debug("===================> passwd:" + passwd);
        encodingPasswd=encodMD5(passwd);
        logger.debug("===================>encoding passwd:" + encodingPasswd);
        Date now = new Date();
        //创建user
        IschoolUser ischoolUser = new IschoolUser(email, name, encodingPasswd, now, now);
        //设置用户类型
        ischoolUser.setType(type);
        //TODO:头像问题
        //设置随机头像
        ischoolUser.setImage(NickImageUtils.getRandomNickPic());
        //设置账户未激活
        ischoolUser.setState(0);
        //生成激活key
        String enableKey = String.valueOf(idWorker.nextId());

        //存入redis，等待激活,保留两天的时间
        redisServiceFacade.setObjectExpire(enableKey, ischoolUser, Constant.USER_KEY_LIVE_TIME.intValue());

        return enableKey;
    }



    @Override
    @Transactional
    public Map<String, Object> createUser(IschoolUser user) {
        Map<String, Object> resutl = new HashMap();
        //记录到数据库
        int success = userMapper.insert(user);
        return resutl;
    }

    @Override
    @Transactional(readOnly = true)
    public String login(String email, String passwd) {
        //密码加密
        String encodingPasswd = new String(encodMD5(passwd));

        IschoolUser user = new IschoolUser();
        user.setEmail(email);
        user.setPasswd(encodingPasswd);

        user = userMapper.selectOne(user);
        if (user != null) {
            String userKey = "LOGIN_USER_" + idWorker.nextId();
            //序列化,
            user.setPasswd(null);
            //存入redis
            //30分钟有效期
            redisServiceFacade.setexpire(userKey, gson.toJson(user), Constant.USER_LOGIN_VALIDTE_TIME);
            return userKey;
        }
        return Constant.USERNAME_OR_PASSWD_ERRO;
    }

    /**
     * 密码加密
     * @param passwd
     * @return
     */
    private String encodMD5(String passwd) {
        String encodingPasswd=null;
        //确定计算方法
        MessageDigest md5= null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            logger.debug(e.getMessage());
        }
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        try {
            encodingPasswd=base64en.encode(md5.digest(passwd.getBytes("utf-8")));
        } catch (UnsupportedEncodingException e) {
            logger.debug(e.getMessage());
        }
        return encodingPasswd;
    }
}
