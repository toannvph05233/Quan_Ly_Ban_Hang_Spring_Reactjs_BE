package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.create_request;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.GioHangChiTiet;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.KhachHang;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class CreateGioHangRequest {
    private Integer id;
    private KhachHang khachHang;
    private LocalDate ngayTao;
    private LocalDate ngayCapNhat;
    @Enumerated(EnumType.STRING)
    private ApplicationConstant.TrangThaiGioHang trangThai;
    private List<GioHangChiTiet> gioHangChiTietList;
}
