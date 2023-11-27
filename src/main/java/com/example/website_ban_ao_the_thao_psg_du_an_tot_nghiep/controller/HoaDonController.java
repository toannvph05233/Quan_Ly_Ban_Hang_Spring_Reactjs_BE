package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.controller;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.service.HoaDonService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

@RequestMapping("/api/hoa-don")
@RestController
@CrossOrigin(origins = "*", maxAge = -1)
public class HoaDonController {

    @Autowired
    HoaDonService hoaDonService;

    @GetMapping("")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(hoaDonService.pageHoaDon(pageNo, pageSize));
    }

    @GetMapping("/keywords-admin")
    public ResponseEntity<?> searchAdmin(@RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize,
                                    @RequestParam(name = "search", required = false) String search, @RequestParam(name = "hinhThucBanHang", required = false) ApplicationConstant.HinhThucBanHang hinhThucBanHang,
                                    @RequestParam(name = "ngayTaoMin", required = false) LocalDate ngayTaoMin,
                                    @RequestParam(name = "ngayTaoMax", required = false) LocalDate ngayTaoMax,
                                    @RequestParam(name = "trangThai", required = false) ApplicationConstant.TrangThaiHoaDon trangThai) {
        System.out.println(hinhThucBanHang);
        return ResponseEntity.ok(hoaDonService.searchHoaDon(pageNo, pageSize, search, hinhThucBanHang, ngayTaoMin, ngayTaoMax, trangThai));

    }

    @GetMapping("/excel")
    public void exportExcel(HttpServletResponse response) throws IOException {
        byte[] excelFile = hoaDonService.exportExcelHoaDon();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=employees.xlsx");
        response.getOutputStream().write(excelFile);
    }

    @GetMapping("/giao-dich/keywords")
    public ResponseEntity<?> searchAdmin(@RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                         @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize,
                                         @RequestParam(name = "searchTenOrMa", required = false) String searchTenOrMa,
                                         @RequestParam(name = "maGiaoDich", required = false) String maGiaoDich,
                                         @RequestParam(name = "phuongThucThanhToan", required = false) ApplicationConstant.HinhThucThanhToan phuongThucThanhToan,
                                         @RequestParam(name = "ngayTaoMin", required = false) LocalDate ngayTaoMin,
                                         @RequestParam(name = "ngayTaoMax", required = false) LocalDate ngayTaoMax) {
        return ResponseEntity.ok(hoaDonService.searchGiaoDich(pageNo, pageSize, searchTenOrMa, maGiaoDich, ngayTaoMin, ngayTaoMax, phuongThucThanhToan));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHoaDonId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(hoaDonService.getOneHoaDon(id));
    }

//    @PutMapping("/revert/{id}")
//    public ResponseEntity<?> revertHoaDon(@PathVariable("id") Integer id) {
//        hoaDonService.removeOrRevertHoaDon(String.valueOf(ACTIVE), id);
//        return ResponseEntity.ok("Revert Success");
//    }
//
//
//    @PutMapping("/remove/{id}")
//    public ResponseEntity<?> removeHoaDon(@PathVariable("id") Integer id) {
//        hoaDonService.removeOrRevertHoaDon(String.valueOf(INACTIVE), id);
//        return ResponseEntity.ok("Remove Success");
//    }
}
