package project.isns.model.table;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "news")
@Data
public class NewsTable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int news_id;

    @Column
    private String header;

    @Column
    private String body;
}

