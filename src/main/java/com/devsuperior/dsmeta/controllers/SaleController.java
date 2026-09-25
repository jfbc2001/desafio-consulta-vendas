package com.devsuperior.dsmeta.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.services.SaleService;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

    @Autowired
    private SaleService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
        SaleMinDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/report")
    public ResponseEntity<List<SaleMinDTO>> getReport(
            @RequestParam(name = "minDate", defaultValue = "") String minDate,
            @RequestParam(name = "maxDate", defaultValue = "") String maxDate,
            @RequestParam(name = "name", defaultValue = "") String name) {

        LocalDate today = LocalDate.now();

        LocalDate min = minDate.equals("")
                ? today.minusYears(1)
                : LocalDate.parse(minDate);

        LocalDate max = maxDate.equals("")
                ? today
                : LocalDate.parse(maxDate);

        List<SaleMinDTO> result = service.searchSales(min, max, name);

        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/summary")
    public ResponseEntity<List<SaleSummaryDTO>> getSummary(
            @RequestParam(name = "minDate", defaultValue = "") String minDate,
            @RequestParam(name = "maxDate", defaultValue = "") String maxDate) {

        LocalDate today = LocalDate.now();

        LocalDate min = minDate.equals("")
                ? today.minusYears(1)
                : LocalDate.parse(minDate);

        LocalDate max = maxDate.equals("")
                ? today
                : LocalDate.parse(maxDate);

        List<SaleSummaryDTO> result = service.summarySales(min, max);

        return ResponseEntity.ok(result);
    }
}