package org.hit.hradar.domain.holiday.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;

@Entity
@Table(name = "HOLIDAY")
@Getter
public class Holiday {

  //휴일 id
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "holiday_id")
  private Long holidayId;

  //날짜
  @Column(name = "holiday_date", nullable = false)
  private LocalDate holidayDate;

  //휴일명
  @Column(name = "holiday_name", nullable = false)
  private String holidayName;

  //휴일타입
  @Enumerated(EnumType.STRING)
  @Column(name = "holiday_type", nullable = false)
  private HolidayType holidayType;

  //급여 휴일
  @Column(name = "is_paid", nullable = false)
  private boolean paid;

  //비고
  @Column(name = "description")
  private String description;

  //회사 id
  @Column(name ="com_id", nullable = false)
  private Long comId;

}
