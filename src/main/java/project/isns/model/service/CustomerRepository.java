//package project.isns.model.service;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import project.isns.model.table.CustomerTable;
//
//import javax.transaction.Transactional;
//
//public interface CustomerRepository extends JpaRepository<CustomerTable,Integer> {
//    @Query(value = "SELECT * FROM customer WHERE customer_id = :customer_id",nativeQuery = true)
//    public CustomerTable findByCustomerId(@Param("customer_id") Integer customerId);
//
//    @Transactional
//    @Modifying(clearAutomatically = true)
//    @Query(value = "UPDATE customer SET first_name = :first_name, last_name = :last_name, email =:email," +
//            " address = :address, phone_number = :phone_number WHERE customer_id = :customer_id")
//    public Integer updateCustomer(@Param("first_name")String firstName,
//                                  @Param("last_name")String lastName,
//                                  @Param("email")String email,
////                                  @Param("line_id")String lineId,
//                                  @Param("address")String address,
//                                  @Param("phone_number")String phoneNumber,
//                                  @Param("customer_id")Integer customerId);
//}
