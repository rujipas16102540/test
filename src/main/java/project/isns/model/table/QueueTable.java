package project.isns.model.table;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name="queue")
public class QueueTable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int queue_id;

    @Column
    private String prefix;

    @Column
    private String first_name;

    @Column
    private String last_name;

    @Column
    private String email;

    @Column
    private String phone_number;

    @Column
    private String status;

    @Column
    private String line_uid;

    @Column
    private String approve;

    @Column
    private String date;

    @Column
    private String drescription;

    @Column
    private String header;
}
