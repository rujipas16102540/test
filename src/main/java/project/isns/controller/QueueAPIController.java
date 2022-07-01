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
//import project.isns.model.service.MessageRepository;
import project.isns.model.service.QueueRepository;
//import project.isns.model.table.MessageTable;
//import project.isns.model.table.PaymentTable;
import project.isns.model.table.QueueTable;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.ExecutionException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/queue")
public class QueueAPIController {
    @Autowired
    private QueueRepository queueRepository;
    @Autowired
    private SmtpMailSender smtpMailSenders;


    @PostMapping("/save_queue")
    public Object saveQueue(QueueTable queue) {
        APIResponse res = new APIResponse();
        System.out.println("queue   " + queue.getDrescription());
        System.out.println("queue   " + queue.getDrescription());

        try {
            queueRepository.save(queue);
            res.setStatus(1);
            res.setMessage("Success");
            res.setData(queue);

        } catch (Exception err) {
            res.setStatus(-1);
            res.setMessage("ERROR: " + err.toString());
        }
        return res;
    }

    @PostMapping("/list_queue_by_email")
    public Object ListQueueByEmail(QueueTable queue) {
        APIResponse res = new APIResponse();
        try {
//            System.out.println("ทดสอบ"+queue.getEmail());
            List<QueueTable> dbQueue = queueRepository.findByEmail(queue.getEmail());
            res.setStatus(1);
            res.setMessage("Success");
            res.setData(dbQueue);
        } catch (Exception err) {
            res.setStatus(0);
            res.setMessage("ERROR: " + err.toString());
        }
        return res;
    }

    @GetMapping("/list_queue")
    public Object listQueue() {
        APIResponse res = new APIResponse();
        try {
            List<QueueTable> dbQueue = queueRepository.findAll();
            res.setStatus(1);
            res.setMessage("Success");
            res.setData(dbQueue);
        } catch (Exception err) {
            res.setStatus(0);
            res.setMessage("ERROR: " + err.toString());
        }
        return res;
    }

    @PostMapping("/cancel_queue")
    public Object cancelQueue(QueueTable queue) {
        APIResponse res = new APIResponse();
        try {
            queueRepository.deleteById(queue.getQueue_id());
            res.setStatus(1);
            res.setMessage("Success");
            res.setData(queue);
        } catch (Exception err) {
            res.setStatus(0);
            res.setMessage("ERROR: " + err.toString());
        }
        return res;
    }

    @PostMapping("/update_line")
    public String updateLine(@RequestParam String email, @RequestParam String line_uid) {
        try {
            Integer result = queueRepository.updateLineUId(email, line_uid);
            if (result != 0) {
                return "Success";
            } else {
                return "Failed";
            }
        } catch (Exception err) {
            return "Error";
        }
    }

    @PostMapping("/approve_send") ///send line และ email
    public String approveSend(QueueTable queueTable) {
        QueueTable dbQueue = queueRepository.findByQueueId(queueTable.getQueue_id()); //เอา line_id ของ queue_id นั้นๆ
//        System.out.println(queueTable.getLine_uid());
//        System.out.println(queueTable.getEmail());
//
//        LocalDateTime startTime = LocalDateTime.parse(queueTable.getDate()); //เปลี่ยนรูปเป็น LocalDateTime YY-MM-DD TT
//        String strLocalDateTime = startTime.toString(); //แปลง LocalDateTime เป็น String
//        String[] arrSplit = strLocalDateTime.split("T"); //แยกเดือนกับเวลาเป็น arr
//        String strYear = arrSplit[0].toString(); //เอาวันมาทำเป็น String
//        String[] sptYear = strYear.split("-"); //เพื่อมาแยก DD MM YY
//        int intEngYear = Integer.parseInt(sptYear[0].toString()); //นำ YY มาเป็น int เพื่อการคำนวน
//        int thaYear = intEngYear + 543; //แปลงมาเป็น พ.ศ.ไทย
//        String newDateShow = sptYear[2] + "/" + sptYear[1] + "/" + thaYear; //นำมารวมกัน วัน/เดือน/ปี เพื่อไปแสดง

        ///ส่งไปที่ line && email///
        try {
//            smtpMailSenders.send(queueTable.getEmail(), "แจ้งรายละเอียด", "อนุมัติการจองคิวสำเร็จ คุณ" + queueTable.getFirst_name() + " " + queueTable.getLast_name() + " สามารถนำรถมาใช้บริการในเวลา " + arrSplit[1] + " น. " + newDateShow + " ค่าใช้จ่ายเบื้องต้น " + queueTable.getPrice() + " บาท");
            smtpMailSenders.send(queueTable.getEmail(), "การอนุมัติ", "การขอรับบริการของ คุณ" + queueTable.getFirst_name() + " " + queueTable.getLast_name() + "อนุมัติสำเร็จ สอบถามเพิ่มเติมโทร 080-4002004");
            if (dbQueue.getLine_uid() != null) {
                final LineMessagingClient client = LineMessagingClient
                        .builder("vfzFSzKN6OVit3qSsxSpPXXw93J5H/x7NnlHF170FqZV1lVEril/pt+1MBaKoctMi3DbSIFC2yZPNEHZ7MPVz/zIv/CKpAlP8HdyLns7VFLq/LAQlHlAFq4BOP96LKg2j2diKWZMkzlX8xlxArgbuAdB04t89/1O/w1cDnyilFU=")
                        .build();
//            final TextMessage textMessage = new TextMessage("อนุมัติการจองคิวสำเร็จ คุณ" + queueTable.getFirst_name() + " " + queueTable.getLast_name() + " สามารถนำรถมาใช้บริการในเวลา " + arrSplit[1] + " น. " + newDateShow + " ค่าใช้จ่ายเบื้องต้น " + queueTable.getPrice() + " บาท");
                final TextMessage textMessage = new TextMessage("การขอรับบริการของ คุณ" + queueTable.getFirst_name() + " " + queueTable.getLast_name() + "อนุมัติสำเร็จ สอบถามเพิ่มเติมโทร 080-4002004");
                final PushMessage pushMessage = new PushMessage(dbQueue.getLine_uid().toString(), textMessage);
                final BotApiResponse botApiResponse;
                try {
                    botApiResponse = client.pushMessage(pushMessage).get();
                    System.out.println(botApiResponse);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            return "Success";
        } catch (Exception err) {
            return "Error";
        }
    }

    @PostMapping("/disapprove_send") ///send line และ email
    public String disapproveSend(@RequestParam String email, @RequestParam String line_uid, @RequestParam String first_name, @RequestParam String last_name, @RequestParam String comment) {
        ///ส่งไปที่ line && email///
        try {
            smtpMailSenders.send(email, "อนุมัติไม่สำเร็จ", "การขอรับบริการของ คุณ" + first_name + " " + last_name + "ไม่ได้รับการอนุมัติ เนื่องจาก" + comment);
            if (!line_uid.equals("null")) {
                final LineMessagingClient client = LineMessagingClient
                        .builder("vfzFSzKN6OVit3qSsxSpPXXw93J5H/x7NnlHF170FqZV1lVEril/pt+1MBaKoctMi3DbSIFC2yZPNEHZ7MPVz/zIv/CKpAlP8HdyLns7VFLq/LAQlHlAFq4BOP96LKg2j2diKWZMkzlX8xlxArgbuAdB04t89/1O/w1cDnyilFU=")
                        .build();
                final TextMessage textMessage = new TextMessage("การขอรับบริการของ คุณ" + first_name + " " + last_name + "ไม่ได้รับการอนุมัติ เนื่องจาก" + comment);
                final PushMessage pushMessage = new PushMessage(line_uid, textMessage);
                final BotApiResponse botApiResponse;
                try {
                    botApiResponse = client.pushMessage(pushMessage).get();
                    System.out.println(botApiResponse);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            return "Success";
        } catch (Exception err) {
            return "Error";
        }

    }

    @PostMapping("/list_queue_by_id")
    public Object ListQueueByID(QueueTable queue) {
        APIResponse res = new APIResponse();
        try {
            System.out.println(queue.getQueue_id());
            QueueTable dbQueue = queueRepository.findByQueueId(queue.getQueue_id());
            res.setStatus(1);
            res.setMessage("Success");
            res.setData(dbQueue);

        } catch (Exception err) {
            res.setStatus(0);
            res.setMessage("ERROR: " + err.toString());
        }
        return res;
    }

    @PostMapping("/update_approve")
    public Object updateApprove(QueueTable queue) {
        APIResponse res = new APIResponse();
        try {
            Integer result = queueRepository.updateApprove(
                    queue.getApprove(),
                    queue.getQueue_id());
            System.out.println(result);
            if (result == 1) {
                res.setStatus(1);
                res.setMessage("Update Success");
                res.setData(queueRepository.findByQueueId(queue.getQueue_id()));
            } else {
                res.setStatus(0);
                res.setMessage("Not Update");
            }
        } catch (Exception err) {
            res.setStatus(0);
            res.setMessage("ERROR: " + err.toString());
        }
        return res;
    }

    @PostMapping("/update_approve_by_email")
    public Object updateApproveByEmail(QueueTable queue) {
        APIResponse res = new APIResponse();
        try {
            Integer result = queueRepository.updateApprovePayment(
                    queue.getApprove(),
                    queue.getEmail());
            res.setStatus(1);
            res.setMessage("Success");
            res.setData(queueRepository.findByQueueId(queue.getQueue_id()));
        } catch (Exception err) {
            res.setStatus(0);
            res.setMessage("Faild");
        }
        return res;
    }

    @PostMapping("/del_by_header")
    public String delQueueByHeader(QueueTable queueTable) {
        try {
            queueRepository.deleteQueueByHeader(queueTable.getHeader());
            return "Success";
        } catch (Exception err) {
            return err.toString();
        }
    }


}
