package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.create_request;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.KichThuoc;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.SanPham;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString

public class CreateChiTietSanPhamRequest {
    private Integer id;
    private KichThuoc kichThuoc;
    private SanPham sanPham;
    private Integer soLuong;
    private String sku;
    private LocalDate ngayTao;
    private LocalDate ngayCapNhat;
    @Enumerated(EnumType.STRING)
    private ApplicationConstant.TrangThaiChiTietSanPham trangThai;
}
