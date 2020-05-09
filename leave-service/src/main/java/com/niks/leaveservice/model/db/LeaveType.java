package com.niks.leaveservice.model.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "leave_type")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LeaveType extends BaseModel {

  @Column(name = "type", nullable = false, unique = true, length = 200)
  private String type;
}