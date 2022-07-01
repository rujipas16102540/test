package project.isns.model.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.isns.model.table.OtherNotiTable;
import project.isns.model.table.QueueTable;
import project.isns.model.table.UserTable;

import java.util.List;

public interface OtherNotiRepository extends JpaRepository<OtherNotiTable,Integer> {

    @Query(value = "select * from othernoti",nativeQuery = true)
    public List<OtherNotiTable> listOtherNoti();

    @Query(value = "SELECT * FROM othernoti WHERE header = :header",nativeQuery = true)
    public OtherNotiTable findByHeader(@Param("header") String header);
}
