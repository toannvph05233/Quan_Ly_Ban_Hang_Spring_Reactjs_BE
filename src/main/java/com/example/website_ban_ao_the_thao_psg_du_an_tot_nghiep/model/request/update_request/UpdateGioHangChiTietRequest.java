package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.update_request;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.ChiTietSanPham;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.GioHang;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.GioHangChiTiet;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UpdateGioHangChiTietRequest {
    private Integer id;
    private GioHang gioHang;
    private ChiTietSanPham chiTietSanPham;
    private Integer soLuong;
    private BigDecimal donGia;
    private BigDecimal giaBan;
    private LocalDate ngayTao;
    private LocalDate ngayCapNhat;
    @Enumerated(EnumType.STRING)
    private ApplicationConstant.TrangThaiGioHang trangThai;
   // private List<GioHangChiTiet> gioHangChiTietList;
}
