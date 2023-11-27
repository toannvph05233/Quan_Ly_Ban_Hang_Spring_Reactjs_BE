package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.response;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.ChiTietSanPham;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TopSellingProductDTO {
    private ChiTietSanPham chiTietSanPham;
    private Long totalSold;
}
