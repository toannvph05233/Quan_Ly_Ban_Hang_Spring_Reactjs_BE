package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class  RequestPayment {
    private Integer idHoaDon;
    private Long totalPrice;
}
