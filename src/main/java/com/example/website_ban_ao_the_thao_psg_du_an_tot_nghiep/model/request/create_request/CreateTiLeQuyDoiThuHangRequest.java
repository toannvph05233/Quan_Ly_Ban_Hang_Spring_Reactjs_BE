package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.create_request;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.ThuHang;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.ChuongTrinhTichDiem;
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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class CreateTiLeQuyDoiThuHangRequest {
    private Integer id;
    private ChuongTrinhTichDiem chuongTrinhTichDiem;
    private ThuHang thuHang;
    private Integer heSoNhan;
    private BigDecimal nguongSoTien;
    private Integer tiLeChuyendoi;
    private LocalDate ngayCapNhat;
    private LocalDate ngayTao;
    @Enumerated(EnumType.STRING)
    private ApplicationConstant.TrangThaiTiLeQuyDoiThuHang trangThai;
}
