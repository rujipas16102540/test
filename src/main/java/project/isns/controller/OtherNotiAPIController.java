package project.isns.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.isns.model.bean.APIResponse;
import project.isns.model.service.OtherNotiRepository;
import project.isns.model.table.OtherNotiTable;
import project.isns.model.table.QueueTable;
import project.isns.model.table.UserTable;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/other_noti")
public class OtherNotiAPIController {
    @Autowired
    private OtherNotiRepository otherNotiRepository;

    @PostMapping("/add_other_noti")
    public Object addOtherNoti(OtherNotiTable otherNotiTable){
        APIResponse res = new APIResponse();
        try {
            OtherNotiTable header = otherNotiRepository.findByHeader(otherNotiTable.getHeader());
            if (header == null) {
            otherNotiRepository.save(otherNotiTable);
                res.setStatus(1);
                res.setMessage("Success");
                res.setData(otherNotiTable);
            } else {
                res.setStatus(0);
                res.setMessage("Failed");
            }


        }catch (Exception err){
            res.setStatus(0);
            res.setMessage("ERROR: " + err.toString());
        }
        return res;
    }

//    @PostMapping("/delete_other_noti")
//    public Object deleteOtherNoti (OtherNotiTable otherNotiTable){
//        APIResponse res = new APIResponse();
//        try{
//            otherNotiRepository.deleteById(otherNotiTable.getOther_noti_id());
//            res.setStatus(1);
//            res.setMessage("Success");
//            res.setData(otherNotiTable);
//        }catch (Exception err){
//            res.setStatus(0);
//            res.setMessage("ERROR: " + err.toString());
//        }
//        return res;
//    }

    @GetMapping("/list_other_noti")
    public Object listOtherNoti (){
        APIResponse res = new APIResponse();
        try{
            List dbOtherNoti = otherNotiRepository.findAll();
            res.setStatus(1);
            res.setMessage("Success");
            res.setData(dbOtherNoti);
        }catch (Exception err){
            res.setStatus(0);
            res.setMessage("ERROR: " + err.toString());
        }
        return res;
    }

    @PostMapping("/list_noti_by_header")
    public Object ListNotiByHeader(OtherNotiTable otherNotiTable) {
        APIResponse res = new APIResponse();
        try {
            OtherNotiTable dbNoti = otherNotiRepository.findByHeader(otherNotiTable.getHeader());
            res.setStatus(1);
            res.setMessage("Success");
            res.setData(dbNoti);
        } catch (Exception err) {
            res.setStatus(0);
            res.setMessage("ERROR: " + err.toString());
        }
        return res;
    }

    @PostMapping("/delete_noti")
    public Object deletnoti (OtherNotiTable otherNotiTable){
        APIResponse res = new APIResponse();
        try{
            otherNotiRepository.deleteById(otherNotiTable.getOther_noti_id());
            res.setStatus(1);
            res.setMessage("Success");
            res.setData(otherNotiTable);
        }catch (Exception err){
            res.setStatus(0);
            res.setMessage("ERROR: " + err.toString());
        }
        return res;
    }
}
