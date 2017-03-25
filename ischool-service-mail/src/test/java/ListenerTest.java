import com.google.gson.Gson;
import com.imis.jxufe.facade.SenderMailServiceFacade;
import com.imis.jxufe.param.MailParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author zhongping
 * @date 2017/3/24
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-context.xml")
public class ListenerTest {


    private static Logger logger = LoggerFactory.getLogger(ListenerTest.class);
    private static Gson gson = new Gson();

    @Resource(name = "SenderMailService")
    private SenderMailServiceFacade senderMailService;


    @Test
    public void sendMailTest() throws InterruptedException {
        MailParam mailParam = new MailParam();
       /* mailParam.setTo("penn2011@126.com");
        mailParam.setSubject("ischool账号激活通知");
        mailParam.setContent(StringUtils.replace(Constant.MAIL_CONTENT, Constant.URL_PLACEHOLDER, "http://baidu.com"));

        logger.debug("===========================mail:" + gson.toJson(mailParam));

        senderMailService.send(mailParam);
        logger.debug("============================ok!:" + gson.toJson(mailParam));



        Thread.sleep(40000L);*/

    }
}
