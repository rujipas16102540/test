package project.isns.model.table;

import lombok.Data;

import javax.persistence.*;


@Entity(name = "settingnoti")
@Data
public class SettingNotiTable {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private int setting_noti_id;

        @Column
        private String date;

        @Column
        private String body;

        @Column
        private String header;

        @Column
        private String status;

        @Column
        private String name_noti;

}
