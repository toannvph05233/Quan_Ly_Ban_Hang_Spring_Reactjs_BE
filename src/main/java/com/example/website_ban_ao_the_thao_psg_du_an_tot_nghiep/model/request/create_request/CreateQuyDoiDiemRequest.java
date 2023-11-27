package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.create_request;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common.ApplicationConstant;
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
public class CreateQuyDoiDiemRequest {
    private Integer id;
    private Integer diem;
    private BigDecimal soTienQuyDoi;
    private LocalDate ngayTao;
    private LocalDate ngayCapNhat;
    @Enumerated(EnumType.STRING)
    private ApplicationConstant.TrangThaiQuyDoiDiem trangThai;
}
