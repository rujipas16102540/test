package project.isns.controller;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.isns.SmtpMailSender;
import project.isns.model.bean.APIResponse;
//import project.isns.model.service.MessageRepository;
import project.isns.model.service.UserRepository;
//import project.isns.model.table.MessageTable;
import project.isns.model.table.QueueTable;
import project.isns.model.table.UserTable;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")

public class UserAPIController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SmtpMailSender smtpMailSenders;
//    @Autowired
//    private MessageRepository messageRepository;


    @PostMapping("/register")
    public Object register(UserTable user) {
        APIResponse res = new APIResponse();
        try {
            UserTable username = userRepository.findByUsername(user.getUsername());
            UserTable email = userRepository.findByEmail(user.getEmail());
            if (username == null && email == null) {
                userRepository.save(user);
                res.setStatus(1);
                res.setMessage("Success");
                res.setData(user);
            } else {
                res.setStatus(0);
                res.setMessage("Failed");
            }
        } catch (Exception err) {
            res.setStatus(-1);
            res.setMessage("ERROR: " + err.toString());
        }

        return res;
    }

    @PostMapping("/login")
    public Object login(UserTable user) {
        APIResponse res = new APIResponse();
        try {
            UserTable dbUser = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
            if (dbUser == null) {
                res.setStatus(0);
                res.setMessage("Login Failed");
            } else {
                res.setStatus(1);
                res.setMessage("Login Success");
                res.setData(dbUser);
            }
        } catch (Exception err) {
            res.setStatus(-1);
            res.setMessage("ERROR: " + err.toString());
        }
        return res;
    }

    @PostMapping("/list_user_by_id")
    public Object ListUserByID(UserTable user) {
        APIResponse res = new APIResponse();
        try {
            UserTable dbUser = userRepository.findByUserId(user.getUser_id());
            res.setStatus(1);
            res.setMessage("Success");
            res.setData(dbUser);

        } catch (Exception err) {
            res.setStatus(0);
            res.setMessage("ERROR: " + err.toString());
        }
        return res;
    }

    @PostMapping("/lst_user_by_header")
    public Object ListUserByHeader(UserTable userTable) {
        APIResponse res = new APIResponse();
        try {
            List<UserTable> test = userRepository.findByHeader(userTable.getHeader());
            res.setStatus(1);
            res.setMessage("Success");
            res.setData(test);

        } catch (Exception err) {
            res.setStatus(0);
            res.setMessage("ERROR: " + err.toString());
        }
        return res;
    }

    @PostMapping("/update_user")
    public Object updateUser(UserTable user) {
        APIResponse res = new APIResponse();
        try {
            UserTable email = userRepository.findByEmail(user.getEmail());
//            if ( email == null) {
            Integer status = userRepository.updateUser(
                    user.getFirst_name(),
                    user.getLast_name(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getAddress(),
                    user.getPhone_number(),
                    user.getUsername(),
                    user.getUser_id());
            if (status == 1) {
                res.setStatus(1);
                res.setMessage("Update Success");
                res.setData(userRepository.findByUserId(user.getUser_id()));
            } else {
                res.setStatus(0);
                res.setMessage("Not Update");
            }
//            }else{
//                res.setStatus(-1);
//                res.setMessage("email");
//            }
        } catch (Exception err) {
            res.setStatus(0);
            res.setMessage("ERROR: " + err.toString());
        }
        return res;
    }

    @GetMapping("/list_user")
    public Object listUser() {
        APIResponse res = new APIResponse();
        try {
            List dbUser = userRepository.findAll();
            res.setStatus(1);
            res.setMessage("Success");
            res.setData(dbUser);
        } catch (Exception err) {
            res.setStatus(0);
            res.setMessage("ERROR: " + err.toString());
        }
        return res;
    }

    @PostMapping("/delete_user")
    public Object deleteUser(UserTable user) {
        APIResponse res = new APIResponse();
        try {
            userRepository.deleteById(user.getUser_id());
            res.setStatus(1);
            res.setMessage("Success");
            res.setData(user);
        } catch (Exception err) {
            res.setStatus(0);
            res.setMessage("ERROR: " + err.toString());
        }
        return res;
    }

    @PostMapping("/update_line")
    public String updateLine(@RequestParam String username, @RequestParam String line_uid) {
        try {
            Integer result = userRepository.updateLineUId(username, line_uid);
            if (result != 0) {
                return "Success";
            } else {
                return "Failed";
            }
        } catch (Exception err) {
            return "Error";
        }
    }

    @PostMapping("/update_type_noti")
    public String updateTypeNoti(UserTable userTable) {
        try {
            Integer result = userRepository.updateTypeNoti(
                    userTable.getUsername(),
                    userTable.getHeader(),
                    userTable.getType_noti());
            if (result != 0) {
                return "Success";
            } else {
                return "Failed";
            }
        } catch (Exception err) {
            return "Error";
        }
    }

    @PostMapping("/update_status")
    public String updateStatus(UserTable userTable) {
        try {
            Integer result = userRepository.updateStatus(
                    userTable.getUsername(),
                    userTable.getStatus(),
                    userTable.getHeader());
            if (result != 0) {
                return "Success";
            } else {
                return "Failed";
            }
        } catch (Exception err) {
            return "Error";
        }
    }

    @PostMapping("/re_user")
    public String reTypeAndHeader(UserTable userTable) {
        try {
            System.out.println(userTable.getHeader());

            String strEmail = userTable.getEmail();
            List<String> lstEmail = List.of(strEmail.split(","));
            for (int i = 0; i < lstEmail.size(); i++) {
                Integer result = userRepository.reUser(
                        lstEmail.get(i),
                        userTable.getType_noti(),
                        userTable.getHeader(),
                        userTable.getStatus());
            }
            return "Success";
        } catch (Exception err) {
            return "Error";
        }
    }


}
