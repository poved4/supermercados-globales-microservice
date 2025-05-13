package com.ucentral.microservice.inventory.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "inventory")
public class Inventory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "branch_id", nullable = false)
  private Long branchId;

  // @ManyToOne
  // @JoinColumn(name = "product_id", nullable = false)
  @Column(name = "product_id", nullable = false)
  private Long productId;

  @Column(nullable = false)
  private Integer stock;

  @Column(name = "stock_min", nullable = false)
  private Integer stockMin;

  @Column(name = "stock_max", nullable = false)
  private Integer stockMax;

  @Column(name = "sales_price", nullable = false, precision = 10, scale = 2)
  private BigDecimal salesPrice;

  @Column(nullable = false)
  private BigDecimal discount;

}
