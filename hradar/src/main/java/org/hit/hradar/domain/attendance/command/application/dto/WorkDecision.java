package org.hit.hradar.domain.attendance.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hit.hradar.domain.attendance.command.domain.aggregate.WorkType;

@Getter
@AllArgsConstructor
//근무결정 dto(service <> policy 내부 전달용)
public class WorkDecision {

  //당일 근무유형(내근/외근/출장/재택)
  private WorkType workType;

  //당일 근무장소(OFFICE/HOME/외근,출장 지명 등 기타장소)
  private String location;

}
