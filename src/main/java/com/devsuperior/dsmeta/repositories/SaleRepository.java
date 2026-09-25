package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("""
            SELECT new com.devsuperior.dsmeta.dto.SaleMinDTO(
                obj.id,
                obj.amount,
                obj.date,
                obj.seller.name
            )
            FROM Sale obj
            WHERE obj.date BETWEEN :minDate AND :maxDate
            AND UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name, '%'))
            ORDER BY obj.date DESC, obj.amount DESC
            """)
    List<SaleMinDTO> searchSales(LocalDate minDate, LocalDate maxDate, String name);

    @Query("""
            SELECT new com.devsuperior.dsmeta.dto.SaleSummaryDTO(
                obj.seller.name,
                SUM(obj.amount)
            )
            FROM Sale obj
            WHERE obj.date BETWEEN :minDate AND :maxDate
            GROUP BY obj.seller.name
            ORDER BY SUM(obj.amount) DESC
            """)
    List<SaleSummaryDTO> summarySales(LocalDate minDate, LocalDate maxDate);
}