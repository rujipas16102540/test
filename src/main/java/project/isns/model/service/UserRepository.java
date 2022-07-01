package project.isns.model.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.isns.model.table.QueueTable;
import project.isns.model.table.UserTable;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends JpaRepository<UserTable, Integer> {
    public UserTable findByUsername(String username);

    public UserTable findByEmail(String email);

    public UserTable findByUsernameAndPassword(String username, String password);

    @Query(value = "select email from users where user_type = 2")
    public List<String> findEmailByUserType();

    @Query(value = "select line_uid from users where user_type = 2")
    public List<String> findLineUidByUserId();

    @Query(value = "select * from users", nativeQuery = true)
    public List<UserTable> listUser();

    @Query(value = "SELECT * FROM users WHERE user_id = :user_id", nativeQuery = true)
    public UserTable findByUserId(@Param("user_id") Integer userId);

    @Query(value = "SELECT * FROM users WHERE header = :header", nativeQuery = true)
    public List<UserTable> findByHeader(@Param("header") String header);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE users SET first_name = :first_name, last_name = :last_name, email =:email, password =:password," +
            " address = :address, phone_number = :phone_number,username =:username WHERE user_id = :user_id")
    public Integer updateUser(@Param("first_name") String firstName,
                              @Param("last_name") String lastName,
                              @Param("email") String email,
                              @Param("password") String password,
                              @Param("address") String address,
                              @Param("phone_number") String phoneNumber,
                              @Param("username") String username,
                              @Param("user_id") Integer userId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE users SET line_uid = :line_uid WHERE username = :username")
    public Integer updateLineUId(@Param("username") String username, @Param("line_uid") String line_uid);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE users SET status = :status,header=:header WHERE username = :username")
    public Integer updateStatus(@Param("username") String username,
                                @Param("status") String status,
                                @Param("header") String header);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE users SET type_noti = :type_noti,header = :header WHERE username = :username")
    public Integer updateTypeNoti(@Param("username") String username,
                                  @Param("header") String header,
                                  @Param("type_noti") String type_noti);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE users SET type_noti = :type_noti,header = :header,status =:status WHERE email = :email")
    public Integer reUser(@Param("email") String email,
                          @Param("header") String header,
                          @Param("type_noti") String type_noti,
                          @Param("status") String status);


//    @Transactional
//    @Modifying(clearAutomatically = true)
//    @Query(value = "UPDATE users SET line_uid = :line_uid WHERE username = :username")
//    public Integer updateLineUserId(@Param("username")String username,@Param("line_uid")String line_uid);

//    @Query(value = "SELECT * FROM users WHERE username = :username",nativeQuery = true)
//    public List<UserTable> findline_uidByUsername(@Param("username") String username);

}
