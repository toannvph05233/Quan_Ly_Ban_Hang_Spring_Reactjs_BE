package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.response;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common.ApplicationConstant;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString

public class CauThuResponse {

    private Integer id;

    private String ma;


    private String ten;


    private String soAo;


    private LocalDate ngayTao;


    private LocalDate ngayCapNhat;


    @Enumerated(EnumType.STRING)
    private ApplicationConstant.TrangThaiSanPham trangThai;
}
