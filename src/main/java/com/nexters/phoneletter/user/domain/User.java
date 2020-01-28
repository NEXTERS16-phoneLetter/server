package com.nexters.phoneletter.user.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String phone1;

  @Column(nullable = false)
  private String phone2;

  @Column(nullable = false)
  private String phone3;

  @Builder
  public User(String email, String phone1, String phone2, String phone3) {
    this.email = email;
    this.phone1 = phone1;
    this.phone2 = phone2;
    this.phone3 = phone3;
  }
}