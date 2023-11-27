package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.service;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.response.HoaDonResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public interface HoaDonService {
    Page<HoaDonResponse> pageHoaDon(Integer pageNo, Integer size);

    Page<HoaDonResponse> searchHoaDon(Integer pageNo, Integer pageSize, String search, ApplicationConstant.HinhThucBanHang hinhThucBanHang, LocalDate ngayTaoMin, LocalDate ngayTaoMax, ApplicationConstant.TrangThaiHoaDon trangThai);

    Page<HoaDonResponse> searchGiaoDich(Integer pageNo, Integer pageSize,
                                        String searchTenOrMa,
                                        String maGiaoDich, LocalDate ngayTaoMin, LocalDate ngayTaoMax,
                                        ApplicationConstant.HinhThucThanhToan hinhThucThanhToan);

    HoaDonResponse getOneHoaDon(Integer id);

    byte[] exportExcelHoaDon() throws IOException;
}
