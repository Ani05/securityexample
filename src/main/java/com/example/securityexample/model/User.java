package com.example.securityexample.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String password;
    @Column
    private String email;
    @Column (name = "pic_url")
    private String picUrl;
    @Column(name = "user_type")
    @Enumerated(EnumType.STRING)
    private UserType userType= UserType.USER;

}
