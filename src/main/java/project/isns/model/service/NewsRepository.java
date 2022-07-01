package project.isns.model.service;

import org.springframework.data.jpa.repository.JpaRepository;
import project.isns.model.table.NewsTable;

public interface NewsRepository extends JpaRepository<NewsTable,Integer> {
}
