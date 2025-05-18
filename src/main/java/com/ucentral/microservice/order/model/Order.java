package com.ucentral.microservice.order.model;

import java.time.LocalDateTime;

import com.ucentral.microservice.orderStatus.model.OrderStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`order`")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "status_id", referencedColumnName = "id")
  private OrderStatus status;

  @Column(name = "branch_id", nullable = false)
  private Long branchId;

  @Column(name = "delivery_date", nullable = false)
  private LocalDateTime deliveryDate;

}
