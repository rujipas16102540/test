//package project.isns.model.service;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import project.isns.model.table.ServiceTable;
//
//import java.util.List;
//
//public interface ServiceRepository extends JpaRepository<ServiceTable,Integer>{
//    @Query(value = "select * from queue",nativeQuery = true)
//    public List<ServiceTable> listService();
//}
