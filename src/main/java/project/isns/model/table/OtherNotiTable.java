package project.isns.model.table;


import lombok.Data;

import javax.persistence.*;

@Entity(name = "othernoti")
@Data
public class OtherNotiTable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private int other_noti_id;

    @Column
    private String header;

    @Column
    private String drescription;

    @Column
    private String name;

    @Column
    private String type_noti;

    @Column
    private String send_message;

    @Column
    private String comment;

    @Column
    private String setting_noti;

}
