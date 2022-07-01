package project.isns.controller;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import project.isns.SmtpMailSender;
import project.isns.model.bean.APIResponse;
//import project.isns.model.service.MessageRepository;
import project.isns.model.service.QueueRepository;
import project.isns.model.service.UserRepository;
//import project.isns.model.table.MessageTable;
import project.isns.model.table.QueueTable;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Configuration
@EnableScheduling
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/message")
public class MessageAPIController {
    //    @Autowired
//    private MessageRepository messageRepository;
    @Autowired
    private SmtpMailSender smtpMailSenders;
    @Autowired
    private QueueRepository queueRepository;
    @Autowired
    private UserRepository userRepository;

    @Scheduled(fixedDelay = 5000)
    public void sendMessage() throws MessagingException {
        LocalDateTime currentTime = LocalDateTime.now(ZoneId.of("UTC+07:00"));
        List<QueueTable> lstQueue = queueRepository.listQueue();
        for (int i = 0; i < lstQueue.size(); i++) {
//            System.out.println(lstQueue);

            LocalDateTime endTime = LocalDateTime.parse(lstQueue.get(i).getDate()); //ระยะห่างของเวลา
            if (!lstQueue.get(i).getStatus().equals("0")) {
                if (lstQueue.get(i).getStatus().equals("2") && ChronoUnit.DAYS.between(currentTime, endTime) == 1) {
//                        ///ส่งไปที่ Email///
                    smtpMailSenders.send(lstQueue.get(i).getEmail().toString(), "แจ้งเตือนการจองคิว", "การจองติวของ คุณ" + lstQueue.get(i).getFirst_name().toString() + " " + lstQueue.get(i).getLast_name().toString() + " อีก 1 วันถึงกำหนดการ");
//                        ///ส่งไปที่ line///
                    if (lstQueue.get(i).getLine_uid() != null) {
                        final LineMessagingClient client = LineMessagingClient
                                .builder("vfzFSzKN6OVit3qSsxSpPXXw93J5H/x7NnlHF170FqZV1lVEril/pt+1MBaKoctMi3DbSIFC2yZPNEHZ7MPVz/zIv/CKpAlP8HdyLns7VFLq/LAQlHlAFq4BOP96LKg2j2diKWZMkzlX8xlxArgbuAdB04t89/1O/w1cDnyilFU=")
                                .build();
                        final TextMessage textMessage = new TextMessage("การจองติวของ คุณ " + lstQueue.get(i).getFirst_name().toString() + " " + lstQueue.get(i).getLast_name().toString() + " อีก 1 วันถึงกำหนดการ");
                        final PushMessage pushMessage = new PushMessage(lstQueue.get(i).getLine_uid().toString(), textMessage);
                        final BotApiResponse botApiResponse;
                        try {
                            botApiResponse = client.pushMessage(pushMessage).get();
                            System.out.println(botApiResponse);

                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                    queueRepository.updateStatus("1", lstQueue.get(i).getQueue_id());
                    System.out.println("ส่่งข้อความก่อน 1 วันและเปลี่ยน status เป็น 1");

                } else if (!lstQueue.get(i).getStatus().equals("2") && ChronoUnit.DAYS.between(currentTime, endTime) <= 0) {
                    queueRepository.updateStatus("0", lstQueue.get(i).getQueue_id());
                    System.out.println("status เป็น 0 หลังจากระยะห่างวัน เป็น 0 หรือน้อยกว้า");

                }
            }
        }
    }
}
