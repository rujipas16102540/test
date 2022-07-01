package project.isns.controller;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import project.isns.SmtpMailSender;
import project.isns.model.bean.APIResponse;
import project.isns.model.service.QueueRepository;
import project.isns.model.service.SettingNotiRepository;
import project.isns.model.service.UserRepository;
import project.isns.model.table.QueueTable;
import project.isns.model.table.SettingNotiTable;
import project.isns.model.table.UserTable;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.ExecutionException;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/setting_noti")
public class SettingNotiAPIController {
    @Autowired
    private SettingNotiRepository settingNotiRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SmtpMailSender smtpMailSenders;


    @PostMapping("/save_setting_noti")
    public Object saveSettingNoti(SettingNotiTable settingNotiTable) {
        APIResponse res = new APIResponse();

        try {
            settingNotiRepository.save(settingNotiTable);
            res.setStatus(1);
            res.setMessage("Success");
            res.setData(settingNotiTable);

        } catch (Exception err) {
            res.setStatus(-1);
            res.setMessage("ERROR: " + err.toString());
        }
        return res;
    }

    @GetMapping("/lst_setting_noti")
    public Object ListSettingNoti() {
        APIResponse res = new APIResponse();
        try {
            List settingNoti = settingNotiRepository.findAll();
            res.setStatus(1);
            res.setMessage("Success");
            res.setData(settingNoti);
        } catch (Exception err) {
            res.setStatus(0);
            res.setMessage("ERROR: " + err.toString());
        }
        return res;
    }

    @PostMapping("/cancel_setting_noti")
    public Object cancelSettingNoti(SettingNotiTable settingNotiTable) {
        APIResponse res = new APIResponse();
        try {
            settingNotiRepository.deleteById(settingNotiTable.getSetting_noti_id());
            res.setStatus(1);
            res.setMessage("Success");
            res.setData(settingNotiTable);
        } catch (Exception err) {
            res.setStatus(0);
            res.setMessage("ERROR: " + err.toString());
        }
        return res;
    }

    @PostMapping("/del_by_header")
    public String delSettingNotiByHeader(SettingNotiTable settingNotiTable) {
        System.out.println(settingNotiTable.getHeader());
        try {
            settingNotiRepository.deleteSettingNotiByHeader(settingNotiTable.getHeader());
            return "Success";
        } catch (Exception err) {
            return err.toString();
        }
    }
    @Scheduled(fixedDelay = 5000)
    public void settingMessage() throws MessagingException {
        LocalDateTime currentTime = LocalDateTime.now(ZoneId.of("UTC+07:00"));
        List<SettingNotiTable> listSettingNoti = settingNotiRepository.listSettingNoti();
        for (int i = 0; i < listSettingNoti.size(); i++) {
            LocalDateTime inputTime = LocalDateTime.parse(listSettingNoti.get(i).getDate()); //แปลงString to date
            if (listSettingNoti.get(i).getStatus().equals("wait")) {
                if (ChronoUnit.HOURS.between(currentTime, inputTime) == 0) {
                    List<UserTable> lstuserbyname_noti = userRepository.findByHeader(listSettingNoti.get(i).getName_noti());
                    for (int j = 0; j < lstuserbyname_noti.size(); j++) {
                        ///ส่งไปที่ Email///
                        smtpMailSenders.send(lstuserbyname_noti.get(j).getEmail().toString(), listSettingNoti.get(i).getHeader(), listSettingNoti.get(i).getBody());
                        ///ส่งไปที่ line///
                        if (lstuserbyname_noti.get(j).getLine_uid() != null) {
                            System.out.print("ทดสอบ"+lstuserbyname_noti.get(j).getLine_uid());
                            final LineMessagingClient client = LineMessagingClient
                                    .builder("vfzFSzKN6OVit3qSsxSpPXXw93J5H/x7NnlHF170FqZV1lVEril/pt+1MBaKoctMi3DbSIFC2yZPNEHZ7MPVz/zIv/CKpAlP8HdyLns7VFLq/LAQlHlAFq4BOP96LKg2j2diKWZMkzlX8xlxArgbuAdB04t89/1O/w1cDnyilFU=")
                                    .build();
                            final TextMessage textMessage = new TextMessage(listSettingNoti.get(i).getBody());
                            final PushMessage pushMessage = new PushMessage(lstuserbyname_noti.get(j).getLine_uid().toString(), textMessage);
                            final BotApiResponse botApiResponse;
                            try {
                                botApiResponse = client.pushMessage(pushMessage).get();
                                System.out.println(botApiResponse);

                            } catch (InterruptedException | ExecutionException e) {
                                e.printStackTrace();
                            }
                        }
                        settingNotiRepository.updateStatus("sent", listSettingNoti.get(i).getSetting_noti_id());
                    }
                }else if(ChronoUnit.HOURS.between(currentTime, inputTime) < 0){
                    settingNotiRepository.updateStatus("sent", listSettingNoti.get(i).getSetting_noti_id());
                }
            }
        }
    }

}
