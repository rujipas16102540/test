package project.isns.controller;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import project.isns.SmtpMailSender;
import project.isns.model.bean.APIResponse;
import project.isns.model.service.NewsRepository;
import project.isns.model.service.QueueRepository;
import project.isns.model.service.UserRepository;
import project.isns.model.table.NewsTable;
import project.isns.model.table.QueueTable;
import project.isns.model.table.UserTable;

import javax.mail.MessagingException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/news")
public class NewsAPIController {
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private SmtpMailSender smtpMailSenders;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QueueRepository queueRepository;

    @PostMapping("/send_to_email")
    public Object saveNews(NewsTable news) throws MessagingException {
        APIResponse res = new APIResponse();
        List<String> email = userRepository.findEmailByUserType();
        try {
            for (int i = 0; i < email.size(); i++) {
                smtpMailSenders.send(email.get(i), news.getHeader(), news.getBody());
                System.out.println("--------" + email);
            }
            newsRepository.save(news);
            res.setStatus(1);
            res.setMessage("Success");
            res.setData(news);


        } catch (Exception err) {
            res.setStatus(0);
            res.setMessage("ERROR: " + err.toString());
        }
        return res;
    }

    @PostMapping("/news_to_line")
    public Object sendToLine(NewsTable news) throws MessagingException {
        APIResponse res = new APIResponse();
        List<UserTable> lstUser = userRepository.listUser();
        System.out.println(lstUser);
        System.out.println(news.getBody());
        try {
            for (int i = 0; i < lstUser.size(); i++) {
                if (lstUser.get(i).getLine_uid() != null) {
//                    System.out.println("ในIf " + lstUser.get(i).getLine_uid() + "ชื่อ" + lstUser.get(i).getFirst_name());
                    final LineMessagingClient client = LineMessagingClient
                            .builder("vfzFSzKN6OVit3qSsxSpPXXw93J5H/x7NnlHF170FqZV1lVEril/pt+1MBaKoctMi3DbSIFC2yZPNEHZ7MPVz/zIv/CKpAlP8HdyLns7VFLq/LAQlHlAFq4BOP96LKg2j2diKWZMkzlX8xlxArgbuAdB04t89/1O/w1cDnyilFU=")
                            .build();
                    final TextMessage textMessage = new TextMessage(news.getBody().toString());
                    final PushMessage pushMessage = new PushMessage(lstUser.get(i).getLine_uid().toString(), textMessage);
                    final BotApiResponse botApiResponse;
                    try {
                        botApiResponse = client.pushMessage(pushMessage).get();
                        System.out.println(botApiResponse);

                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
            newsRepository.save(news);
            res.setStatus(1);
            res.setMessage("Success");
            res.setData(news);
        } catch (Exception err) {
            res.setStatus(0);
            res.setMessage("ERROR: " + err.toString());
        }
        return res;

    }

    @PostMapping("/email_noti")
    public Object sendToEmailByNoti(@RequestParam String topic, @RequestParam String header, @RequestParam String body) throws MessagingException {
        APIResponse res = new APIResponse();
        List<String> lstQueueByheader = queueRepository.findEmailByHeader(topic);

        try {

            for (int i = 0; i < lstQueueByheader.size(); i++) {

                smtpMailSenders.send(lstQueueByheader.get(i), header, body);
                Thread.sleep(5000);

            }
            res.setStatus(1);
            res.setMessage("Success");
        } catch (Exception err) {
            res.setStatus(0);
            res.setMessage("ERROR: " + err.toString());
        }
        System.out.print("res : "+res);
        return res;
    }

    @PostMapping("/line_noti")
    public Object sendToLineByNoti(@RequestParam String topic, @RequestParam String body) throws MessagingException {
        APIResponse res = new APIResponse();
        System.out.println("--------" + body);
        System.out.println("--------" + topic);
        List<String> lstLineByheader = queueRepository.findLineByHeader(topic);
        try{
        for (int i = 0; i < lstLineByheader.size(); i++) {
            if (lstLineByheader.get(i) != null) {
                final LineMessagingClient client = LineMessagingClient
                        .builder("vfzFSzKN6OVit3qSsxSpPXXw93J5H/x7NnlHF170FqZV1lVEril/pt+1MBaKoctMi3DbSIFC2yZPNEHZ7MPVz/zIv/CKpAlP8HdyLns7VFLq/LAQlHlAFq4BOP96LKg2j2diKWZMkzlX8xlxArgbuAdB04t89/1O/w1cDnyilFU=")
                        .build();
                final TextMessage textMessage = new TextMessage(body.toString());
                final PushMessage pushMessage = new PushMessage(lstLineByheader.get(i).toString(), textMessage);
                final BotApiResponse botApiResponse;
                try {
                    botApiResponse = client.pushMessage(pushMessage).get();
                    System.out.println(botApiResponse);

                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
        res.setStatus(1);
        res.setMessage("Success");
    } catch (Exception err) {
        res.setStatus(0);
        res.setMessage("ERROR: " + err.toString());
    }
        return res;
    }

    @PostMapping("/list_news")
    public Object listNews() {
        APIResponse res = new APIResponse();
        try {
            List<NewsTable> dbNews = newsRepository.findAll();
            res.setStatus(1);
            res.setMessage("Success");
            res.setData(dbNews);
        } catch (Exception err) {
            res.setStatus(0);
            res.setMessage("ERROR: " + err.toString());
        }
        return res;
    }
}
