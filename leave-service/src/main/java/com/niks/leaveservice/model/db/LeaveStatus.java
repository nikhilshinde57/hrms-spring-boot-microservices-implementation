package com.niks.leaveservice.model.db;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@ToString(callSuper = true)
@Table(name = "leave_status")
public class LeaveStatus extends BaseModel {

  @Column(name = "status", nullable = false, unique = true, length = 200)
  private String status;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LeaveStatus that = (LeaveStatus) o;
    return this.getId().equals(that.getId()) &&
        this.getGuid().equals(that.getGuid()) &&
        status.equals(that.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getId(), this.getGuid(), status);
  }
}
