package com.nexters.phoneletter.domain.user;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor
@Getter
@Entity
public class Users {
  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String phoneNumber;

  @Builder
  public Users(String email, String phoneNumber) {
    this.email = email;
    this.phoneNumber = phoneNumber;
  }
}
