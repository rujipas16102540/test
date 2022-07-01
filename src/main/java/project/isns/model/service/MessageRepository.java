//package project.isns.model.service;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import project.isns.model.table.MessageTable;
//
//import javax.transaction.Transactional;
//
//public interface MessageRepository extends JpaRepository<MessageTable,Integer> {
//    @Query(value = "SELECT * FROM message WHERE message_id = :message_id",nativeQuery = true)
//    public MessageTable findByMessageId(@Param("message_id") Integer messageId);
//
//    @Transactional
//    @Modifying(clearAutomatically = true)
//    @Query(value = "UPDATE message SET header = :header, body = :body WHERE message_id = :message_id")
//    public Integer updateMessage(@Param("header")String header,
//                                 @Param("body")String body,
//                                 @Param("message_id")Integer messageId);
//
//    @Query(value = "select * from message where message_id = '1' ",nativeQuery = true)
//    public MessageTable register();
//
//    @Query(value = "select * from message where message_id = '2' ",nativeQuery = true)
//    public MessageTable detail();
//
//    @Query(value = "select * from message where message_id = '3' ",nativeQuery = true)
//    public MessageTable message1Day();
//
//    @Query(value = "select * from message where message_id = '4' ",nativeQuery = true)
//    public MessageTable message3Day();
//
//    @Query(value = "select * from message where message_id = '5' ",nativeQuery = true)
//    public MessageTable message30Day();
//
//}
//
