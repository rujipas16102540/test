package project.isns.model.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.isns.model.table.QueueTable;
import project.isns.model.table.SettingNotiTable;
import project.isns.model.table.UserTable;

import javax.transaction.Transactional;
import java.util.List;

public interface SettingNotiRepository extends JpaRepository<SettingNotiTable,Integer> {

    @Query(value = "SELECT * FROM settingnoti WHERE header = :header",nativeQuery = true)
    public List<SettingNotiTable> findByHeader(@Param("header") String header);

    @Query(value = "select * from settingnoti",nativeQuery = true)
    public List<SettingNotiTable> listSettingNoti();

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE settingnoti SET status = :status WHERE setting_noti_id = :setting_noti_id")
    public Integer updateStatus(@Param("status")String status,
                                @Param("setting_noti_id")int setting_noti_id);

    @Transactional
    @Modifying
    @Query("delete from settingnoti where name_noti = :name_noti")
    public Integer deleteSettingNotiByHeader(@Param("name_noti") String name_noti);
}
