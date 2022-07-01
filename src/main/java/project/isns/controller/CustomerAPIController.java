//package project.isns.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import project.isns.model.bean.APIResponse;
//import project.isns.model.service.CustomerRepository;
//import project.isns.model.table.CustomerTable;
//
//import java.util.List;
//
//@CrossOrigin(origins = "*")
//@RestController
//@RequestMapping("/customer")
//public class CustomerAPIController {
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    @PostMapping("/save_customer")
//    public Object saveCustomer (CustomerTable customer){
//        APIResponse res = new APIResponse();
//
//        try {
//            customerRepository.save(customer);
//            res.setStatus(1);
//            res.setMessage("Success");
//            res.setData(customer);
//        }catch (Exception err){
//            res.setStatus(0);
//            res.setMessage("ERROR: " + err.toString());
//        }
//        return res;
//    }
//    //
//    @GetMapping("/list_customer")
//    public Object listCustomer (){
//        APIResponse res = new APIResponse();
//        try{
//            List dbCustomer = customerRepository.findAll();
//            res.setStatus(1);
//            res.setMessage("Success");
//            res.setData(dbCustomer);
//        }catch (Exception err){
//            res.setStatus(0);
//            res.setMessage("ERROR: " + err.toString());
//        }
//        return res;
//    }
//
//    @PostMapping("/list_customer_by_id")
//    public Object ListCustomerByID (CustomerTable customer){
//        APIResponse res = new APIResponse();
//        try {
////            System.out.println(customer.getCustomer_id());
//            CustomerTable dbCustomer = customerRepository.findByCustomerId(customer.getCustomer_id());
//            res.setStatus(1);
//            res.setMessage("Success");
//            res.setData(dbCustomer);
//
//        }catch (Exception err){
//            res.setStatus(0);
//            res.setMessage("ERROR: " + err.toString());
//        }
//        return res;
//    }
//
//    @PostMapping("/update_customer")
//    public  Object updateCustomer (CustomerTable customer){
//        APIResponse res = new APIResponse();
//        try {
//            Integer status = customerRepository.updateCustomer(
//                    customer.getFirst_name(),
//                    customer.getLast_name(),
//                    customer.getEmail(),
////                    customer.getLine_id(),
//                    customer.getAddress(),
//                    customer.getPhone_number(),
//                    customer.getCustomer_id());
//            if (status == 1){
//                res.setStatus(1);
//                res.setMessage("Update Success");
//                res.setData(customerRepository.findByCustomerId(customer.getCustomer_id()));
//            }else{
//                res.setStatus(0);
//                res.setMessage("Not Update");
//            }
//        }catch (Exception err){
//            res.setStatus(0);
//            res.setMessage("ERROR: " + err.toString());
//        }
//        return res;
//    }
//
//    @PostMapping("/delete_customer")
//    public Object deleteCustomer (CustomerTable customer){
//        APIResponse res = new APIResponse();
//        try{
//            customerRepository.deleteById(customer.getCustomer_id());
//            res.setStatus(1);
//            res.setMessage("Success");
//            res.setData(customer);
//        }catch (Exception err){
//            res.setStatus(0);
//            res.setMessage("ERROR: " + err.toString());
//        }
//        return res;
//    }
//}
