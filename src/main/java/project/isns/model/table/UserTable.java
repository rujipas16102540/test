package project.isns.model.table;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "users")
@Data
public class UserTable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int user_id;

    @Column
    private String first_name;

    @Column
    private String last_name;

    @Column
    private int user_type;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String address;

    @Column
    private String prefix;

    @Column
    private String email;

    @Column
    private String phone_number;

    @Column
    private String line_uid;

    @Column
    private String header;

    @Column
    private String type_noti;

    @Column
    private String status;


}
