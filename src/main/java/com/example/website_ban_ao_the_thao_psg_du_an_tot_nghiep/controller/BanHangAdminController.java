package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.controller;


import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.dto.RequestPayment;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.service.BanHangTaiQuayService;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.time.LocalDate;

@RequestMapping("/api/ban-hang-tai-quay")
@RestController
@CrossOrigin(origins = "*", maxAge = -1)
public class BanHangAdminController {


    @Autowired
    BanHangTaiQuayService banHangTaiQuayService;

    @GetMapping("/hoa-don-cho")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(banHangTaiQuayService.getAllHoaDonCho());
    }

    @GetMapping("/hoa-don-cho/{id}")
    public ResponseEntity<?> getOneHoaDon(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(banHangTaiQuayService.getOneHoaDon(id));
    }

    @GetMapping("/hoa-don-pending")
    public ResponseEntity<?> getAllHoaDonPending() {
        return ResponseEntity.ok(banHangTaiQuayService.getAllListHoaDonResponseStatusPending(ApplicationConstant.TrangThaiHoaDon.PENDING));
    }

    @GetMapping("/delete-hoa-don-chi-tiet/{id}")
    public ResponseEntity<?> deleteHoaDonChiTiet(@PathVariable("id") Integer id) {
        banHangTaiQuayService.deleteHoaDonChiTiet(id);
        return ResponseEntity.ok("Delete hdct success");
    }

    @PostMapping("/create-hoa-don-cho")
    public ResponseEntity<?> createHoaDonCho(@RequestParam(defaultValue = "") String email) {
        return ResponseEntity.ok(banHangTaiQuayService.createHoaDonCho(email));
    }

    @PostMapping("/create-hoa-don-chi-tiet/{idHoaDon}")
    public ResponseEntity<?> createHoaDonChiTiet(@PathVariable("idHoaDon") Integer idHoaDon, @RequestBody JsonNode data) {
        banHangTaiQuayService.createHoaDonChiTiet(idHoaDon, data.get("idCtsp").asInt());
        return ResponseEntity.ok("Thêm sản phẩm thành công");
    }

    @PutMapping("/tru-so-luong-hoa-don-chi-tiet/{idHdct}")
    public ResponseEntity<?> truSoLuongHoaDonChiTiet(@PathVariable("idHdct") Integer idHdct) {
        return ResponseEntity.ok(banHangTaiQuayService.truSoLuongHoaDonChiTiet(idHdct));
    }

    @PutMapping("/cong-so-luong-hoa-don-chi-tiet/{idHdct}")
    public ResponseEntity<?> congSoLuongHoaDonChiTiet(@PathVariable("idHdct") Integer idHdct) {
        return ResponseEntity.ok(banHangTaiQuayService.congSoLuongHoaDonChiTiet(idHdct));
    }

    @PutMapping("/update-so-luong-hoa-don-chi-tiet/{idHdct}")
    public ResponseEntity<?> updateSoLuongHoaDonChiTiet(@PathVariable("idHdct") Integer idHdct, @RequestBody JsonNode data) {
        return ResponseEntity.ok(banHangTaiQuayService.updateSoLuongAndDonGiaHoaDonChiTiet(idHdct, data.get("soLuong").asInt()));
    }

    @PutMapping("/update-khach-hang-hoa-don/{idHoaDon}")
    public ResponseEntity<?> updateKhachHangHoaDon(@PathVariable("idHoaDon") Integer idHd, @RequestBody JsonNode data) {
        return ResponseEntity.ok(banHangTaiQuayService.updateKhachHangHoaDon(idHd, data.get("idKhachHang").asInt()));
    }

    @PutMapping("/update-trang-thai-hoa-don/{idHoaDon}")
    public ResponseEntity<?> updateTrangThaiHoaDon(@PathVariable("idHoaDon") Integer idHd, @RequestBody JsonNode data) {
        banHangTaiQuayService.updateTrangThaiHoaDon(idHd, ApplicationConstant.TrangThaiHoaDon.valueOf(data.get("trangThaiHoaDon").asText()));
        return ResponseEntity.ok("Cập nhập trạng thái hoá đơn thành công");
    }

    @PutMapping("/tra-hang-hoa-don-chi-tiet/{idHdct}")
    public ResponseEntity<?> traHangHoaDonChiTiet(@PathVariable("idHdct") Integer idHdct,@RequestBody JsonNode data) {
        banHangTaiQuayService.updateTrangThaiHoaDonChiTiet(idHdct,data.get("soLuong").asInt());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/thanh-toan-hoa-don/{idHoaDon}")
    public ResponseEntity<?> thanhToanHoaDon(@PathVariable("idHoaDon") Integer idHd, @RequestBody JsonNode data) {
        try {
            String moTa = data.has("moTa") ? data.get("moTa").asText() : "";

            // Check if ngayMuonNhan is not empty before parsing
            String ngayMuonNhanText = data.has("ngayMuonNhan") ? data.get("ngayMuonNhan").asText() : "";
            LocalDate ngayMuonNhan = !ngayMuonNhanText.isEmpty() ? LocalDate.parse(ngayMuonNhanText) : null;

            Integer phanTramGiamGia = data.has("phanTramGiamGia") ? data.get("phanTramGiamGia").asInt() : 0;
            BigDecimal soTienVanChuyen = data.has("soTienVanChuyen") ? BigDecimal.valueOf(Long.parseLong(data.get("soTienVanChuyen").asText())) : BigDecimal.ZERO;
            ApplicationConstant.HinhThucThanhToan phuongThucThanhToan = ApplicationConstant.HinhThucThanhToan.valueOf(data.get("phuongThucThanhToan").asText());
            BigDecimal thanhTien = data.has("thanhTien") ? BigDecimal.valueOf(Long.parseLong(data.get("thanhTien").asText())) : BigDecimal.ZERO;
            String diaChi = data.has("diaChi") ? data.get("diaChi").asText() : "";
            String tenNguoiNhan = data.has("tenNguoiNhan") ? data.get("tenNguoiNhan").asText() : "";
            String sdtNguoiNhan = data.has("sdtNguoiNhan") ? data.get("sdtNguoiNhan").asText() : "";
            String sdtNguoiShip = data.has("sdtNguoiShip") ? data.get("sdtNguoiShip").asText() : "";
            ApplicationConstant.HinhThucBanHang hinhThucBanHang = ApplicationConstant.HinhThucBanHang.valueOf(data.get("hinhThucBanHang").asText());

            banHangTaiQuayService.thanhToanHoaDon(idHd, moTa, ngayMuonNhan, phanTramGiamGia, soTienVanChuyen, phuongThucThanhToan, thanhTien, diaChi, tenNguoiNhan, sdtNguoiNhan, sdtNguoiShip, hinhThucBanHang);

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @PutMapping("/cap-nhat-dia-chi-hoa-don/{idHoaDon}")
    public ResponseEntity<?> capNhatDiaChiHoaDon(@PathVariable("idHoaDon") Integer idHd, @RequestBody JsonNode data) {
        try {
            LocalDate ngayMuonNhan = LocalDate.parse(data.get("ngayMuonNhan").asText());
            String diaChi = data.get("diaChi").asText();
            String tenNguoiNhan = data.get("tenNguoiNhan").asText();
            String sdtNguoiNhan = data.get("sdtNguoiNhan").asText();
            String sdtNguoiShip = data.get("sdtNguoiShip").asText();
            banHangTaiQuayService.updateDiaChiHoaDon(idHd,ngayMuonNhan,diaChi, tenNguoiNhan, sdtNguoiNhan, sdtNguoiShip);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/payment")
    public ResponseEntity<?> createPayment(@RequestBody RequestPayment requestPayment) throws UnsupportedEncodingException {
        return new ResponseEntity<>(banHangTaiQuayService.payment(requestPayment), HttpStatus.OK);
    }
}
