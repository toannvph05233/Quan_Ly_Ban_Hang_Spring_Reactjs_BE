package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.create_request;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common.ApplicationConstant.TrangThaiSanPham;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class CreateKichThuocRequest {
    private Integer id;
    private String ma;
    @NotBlank(message = "Tên không để trống")
    @Size(min = 1, max = 255, message = "Tên không vượt quá 255 ký tự")
    private String ten;
    private LocalDate ngayTao;
    private LocalDate ngayCapNhat;
    @Enumerated(EnumType.STRING)
    private TrangThaiSanPham trangThai;
}
