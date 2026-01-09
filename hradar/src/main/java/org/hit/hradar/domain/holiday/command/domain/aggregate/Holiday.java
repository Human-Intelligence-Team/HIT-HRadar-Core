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
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(name = "holiday")
@Getter
public class Holiday extends BaseTimeEntity  {

  //휴일 id
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "holiday_id")
  private Long holidayId;

  //회사 id
  @Column(name ="com_id", nullable = false)
  private Long comId;

  //휴일날짜
  @Column(name = "holiday_date", nullable = false)
  private LocalDate holidayDate;

  //휴일명
  @Column(name = "holiday_name", nullable = false, length = 100)
  private String holidayName;

  //휴일타입
  @Enumerated(EnumType.STRING)
  @Column(name = "holiday_type", nullable = false)
  private HolidayType holidayType = HolidayType.LEGAL;

  //급여 휴일
  @Column(name = "is_paid", nullable = false)
  private boolean paid;

  //비고
  @Column(name = "description", length = 200)
  private String description;

  //삭제여부
  @Column(name = "is_deleted", nullable = false)
  private Character isDeleted = 'N';

}
