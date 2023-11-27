package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.service;


import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.dto.RequestPayment;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.dto.ResponsePayment;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.HoaDon;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.response.HoaDonChiTietResponse;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.response.HoaDonResponse;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public interface BanHangTaiQuayService {

    HoaDonResponse createHoaDonCho(String email);

    List<HoaDonResponse> getAllHoaDonCho();

    void deleteHoaDonChiTiet(Integer id);

    HoaDonResponse updateKhachHangHoaDon(Integer idHd,Integer idKhachHang);

    HoaDonResponse updateDiaChiHoaDon(Integer idHd,LocalDate ngayMuonNhan,String diaChi,
                                      String tenNguoiNhan,
                                      String sdtNguoiNhan,
                                      String sdtNguoiShip);

    HoaDonResponse getOneHoaDon(Integer id);

    void createHoaDonChiTiet(Integer idHoaDon,Integer idCtsp);

    HoaDonChiTietResponse truSoLuongHoaDonChiTiet(Integer idHdct);

    HoaDonChiTietResponse congSoLuongHoaDonChiTiet(Integer idHdct);

    HoaDonChiTietResponse updateSoLuongAndDonGiaHoaDonChiTiet(Integer idHdct, Integer soLuong);

    void updateTrangThaiHoaDon(Integer idHoaDon, ApplicationConstant.TrangThaiHoaDon trangThaiHoaDon);

    void updateTrangThaiHoaDonChiTiet(Integer id,Integer soLuong);

    void thanhToanHoaDon(Integer idHoaDon,
                    String moTa,
                    LocalDate ngayMuonNhan,
                    Integer phanTramGiamGia,
                    BigDecimal soTienVanChuyen,
                    ApplicationConstant.HinhThucThanhToan phuongThucThanhToan,
                    BigDecimal thanhTien,
                    String diaChi,
                    String tenNguoiNhan,
                    String sdtNguoiNhan,
                    String sdtNguoiShip,
                    ApplicationConstant.HinhThucBanHang hinhThucBanHang);

    List<HoaDonResponse> getAllListHoaDonResponseStatusPending(ApplicationConstant.TrangThaiHoaDon trangThai);

    ResponsePayment payment(RequestPayment requestPayment) throws UnsupportedEncodingException;
}
