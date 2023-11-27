package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.service;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.response.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface ThongKeService {
    HoaDonDTO findByNgayTaoTruoc(Integer ngayTruoc);
    HoaDonDTO findByThangTaoTruoc(Integer thangTruoc);
    List<KhachHangResponse> findTopByOrderBySoDiemDesc(Integer limit);
    List<TopSellingProductDTO> getTopSellingProducts(Integer limit);
    List<HoaDonDTO> countAndSumByNgayTao(LocalDate startDate, LocalDate endDate);
    List<HoaDonDTO2> countAndSumByNgayTaoMoney(LocalDate startDate, LocalDate endDate);
    List<HoaDonDTO3> countAndSumByTrangThai();
    HoaDonDTO sumHoaDon();
}
