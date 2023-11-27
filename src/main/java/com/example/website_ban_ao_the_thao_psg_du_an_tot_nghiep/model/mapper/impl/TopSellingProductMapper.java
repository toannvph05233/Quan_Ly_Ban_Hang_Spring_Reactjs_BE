package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.mapper.impl;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.ChiTietSanPham;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.response.TopSellingProductDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TopSellingProductMapper {
    public List<TopSellingProductDTO> mapToObjectArrayList(List<Object[]> result) {
        List<TopSellingProductDTO> dtoList = new ArrayList<>();
        for (Object[] row : result) {
            ChiTietSanPham chiTietSanPham = (ChiTietSanPham) row[0];
            Long totalSold = (Long) row[1];
            TopSellingProductDTO dto = new TopSellingProductDTO(chiTietSanPham, totalSold);
            dtoList.add(dto);
        }
        return dtoList;
    }
}
