package com.qijiabin.dailyList.email;

import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qijiabin.dailyList.util.Constants;
import com.sun.mail.util.MailSSLSocketFactory;

/**
 * ========================================================
 * 日 期：2016年12月27日 下午2:38:39
 * 版 本：1.0.0
 * 类说明：
 * TODO
 * ========================================================
 * 修订日期     修订人    描述
 */
public class SendEmail {
	
	private static final Logger log = LoggerFactory.getLogger(SendEmail.class);
	
	/**
	 * 发送
	 * @param args
	 * @throws Exception
	 */
	public static void send(String content) {
		log.info("**************邮件发送开始**************");
        try {
			Properties prop = new Properties();
			// 开启debug调试，以便在控制台查看
			prop.setProperty("mail.debug", "true");
			// 设置邮件服务器主机名
			prop.setProperty("mail.host", "smtp.qq.com");
			// 发送服务器需要身份验证
			prop.setProperty("mail.smtp.auth", "true");
			// 发送邮件协议名称
			prop.setProperty("mail.transport.protocol", "smtp");

			// 开启SSL加密，否则会失败
			MailSSLSocketFactory sf = new MailSSLSocketFactory();
			sf.setTrustAllHosts(true);
			prop.put("mail.smtp.ssl.enable", "true");
			prop.put("mail.smtp.ssl.socketFactory", sf);

			// 创建session
			Session session = Session.getInstance(prop);

			// 通过session得到transport对象
			Transport ts = session.getTransport();
			// 连接邮件服务器：邮箱类型，帐号，授权码代替密码（更安全）
			ts.connect("smtp.qq.com", Constants.EMAIL_QQ, "gedjtjjwtoiqbeaf");

			// 创建邮件
			Message message = createSimpleMail("dailyList", content, session);
			// 发送邮件
			ts.sendMessage(message, message.getAllRecipients());
			ts.close();
		} catch (NoSuchProviderException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} catch (MessagingException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
        log.info("***********邮件发送结束********************");
    }

    /**
     * @Method: createSimpleMail
     * @Description: 创建一封只包含文本的邮件
     */
    public static MimeMessage createSimpleMail(String subject, String content, Session session) throws Exception {
        // 创建邮件对象
        MimeMessage message = new MimeMessage(session);
        // 指明邮件的发件人
        message.setFrom(new InternetAddress(Constants.EMAIL_FROM));
        // 指明邮件的收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(Constants.EMAIL_TO));
        // 邮件的标题
        message.setSubject(subject);
        // 邮件的文本内容
        message.setContent(content, "text/html;charset=UTF-8");
        // 返回创建好的邮件对象
        return message;
    }

}
