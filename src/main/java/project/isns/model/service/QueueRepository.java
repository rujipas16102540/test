package project.isns.model.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
//import project.isns.model.table.CustomerTable;
import project.isns.model.table.QueueTable;
import project.isns.model.table.UserTable;

import javax.transaction.Transactional;
import java.util.List;

public interface QueueRepository extends JpaRepository<QueueTable,Integer> {
    public QueueTable findByDate(String date);
//    public QueueTable findByEndTime(String date);

    @Query(value = "select * from queue",nativeQuery = true)
    public List<QueueTable> listQueue();

    @Query(value = "SELECT * FROM queue WHERE queue_id = :queue_id",nativeQuery = true)
    public QueueTable findByQueueId(@Param("queue_id") Integer queueId);

    @Query(value = "SELECT email FROM queue WHERE header = :header" ,nativeQuery = true)
    public List<String> findEmailByHeader(@Param("header") String topic);

    @Query(value = "SELECT line_uid FROM queue WHERE header = :header" ,nativeQuery = true)
    public List<String> findLineByHeader(@Param("header") String topic);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE queue SET status = :status WHERE queue_id = :queue_id")
    public Integer updateStatus(@Param("status")String status,
                                @Param("queue_id")int queue_id);

    @Query(value = "SELECT * FROM queue WHERE email = :email",nativeQuery = true)
    public List<QueueTable> findByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM queue WHERE end_time = :end_time" ,nativeQuery = true)
    public List<QueueTable> findEndTime(@Param("end_time") String end_time);

    @Query(value = "SELECT * FROM queue WHERE date = :date" ,nativeQuery = true)
    public List<QueueTable> findDate(@Param("date") String date);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE queue SET line_uid = :line_uid WHERE email = :email")
    public Integer updateLineUId(@Param("email")String email,@Param("line_uid")String line_uid);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE queue SET approve=:approve WHERE queue_id = :queue_id")
    public Integer updateApprove(@Param("approve")String approve,
                                 @Param("queue_id")Integer queueId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE queue SET details=:details, price=:price, end_time=:end_time, approve=:approve WHERE queue_id = :queue_id")
    public Integer updateDetails(@Param("details")String details,
                                 @Param("price")String price,
                                 @Param("end_time")String end_time,
                                 @Param("approve")String approve,
                                 @Param("queue_id")Integer queueId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE queue SET approve=:approve WHERE email = :email")
    public Integer updateApprovePayment(@Param("approve")String approve,
                                        @Param("email")String email);

    @Transactional
    @Modifying
    @Query("delete from queue where header = :header")
    public Integer deleteQueueByHeader(@Param("header") String header);

}
