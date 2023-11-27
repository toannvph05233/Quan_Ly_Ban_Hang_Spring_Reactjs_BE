package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.service.impl;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.HoaDon;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.HoaDon;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.NhanVien;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.exception.NotFoundException;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.mapper.HoaDonMapper;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.response.HoaDonResponse;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.repository.HoaDonRepository;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.service.HoaDonService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class HoaDonServiceImpl implements HoaDonService {

    @Autowired
    HoaDonRepository hoaDonRepository;
    @Autowired
    HoaDonMapper hoaDonMapper;
    @Override
    public Page<HoaDonResponse> pageHoaDon(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<HoaDon> hoaDonPage = hoaDonRepository.findAll(pageable);
        return hoaDonPage.map(hoaDonMapper::hoaDonEntityToHoaDonResponse);
    }

    @Override
    public Page<HoaDonResponse> searchHoaDon(Integer pageNo, Integer pageSize, String search, ApplicationConstant.HinhThucBanHang hinhThucBanHang, LocalDate ngayTaoMin, LocalDate ngayTaoMax, ApplicationConstant.TrangThaiHoaDon trangThai) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<HoaDon> hoaDonPage = hoaDonRepository.searchHoaDon(pageable, search,hinhThucBanHang,ngayTaoMin,ngayTaoMax,trangThai);
        return hoaDonPage.map(hoaDonMapper::hoaDonEntityToHoaDonResponse);
    }

    @Override
    public Page<HoaDonResponse> searchGiaoDich(Integer pageNo, Integer pageSize, String searchTenOrMa, String maGiaoDich, LocalDate ngayTaoMin, LocalDate ngayTaoMax, ApplicationConstant.HinhThucThanhToan hinhThucThanhToan) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<HoaDon> hoaDonPage = hoaDonRepository.searchGiaoDich(pageable,searchTenOrMa, maGiaoDich,ngayTaoMin,ngayTaoMax,hinhThucThanhToan);
        return hoaDonPage.map(hoaDonMapper::hoaDonEntityToHoaDonResponse);
    }

    @Override
    public HoaDonResponse getOneHoaDon(Integer id) {
        HoaDon hoaDon = hoaDonRepository.findById(id).orElseThrow(() -> new NotFoundException("HoaDon not found with id" + id));
        return hoaDonMapper.hoaDonEntityToHoaDonResponse(hoaDon);
    }

    @Override
    public byte[] exportExcelHoaDon() throws IOException {
        List<HoaDon> data = hoaDonRepository.findAll();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("NhanVien");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("id");
        headerRow.createCell(1).setCellValue("khachHang");
        headerRow.createCell(2).setCellValue("nhanVien");
        headerRow.createCell(3).setCellValue("ma");
        headerRow.createCell(4).setCellValue("ngayTao");
        headerRow.createCell(5).setCellValue("ngayMuonNhan");
        headerRow.createCell(6).setCellValue("phanTramGiamGia");
        headerRow.createCell(7).setCellValue("soDiemCong");
        headerRow.createCell(8).setCellValue("soDiemTru");
        headerRow.createCell(9).setCellValue("soTienChuyenKhoan");
        headerRow.createCell(10).setCellValue("soTienMat");
        headerRow.createCell(11).setCellValue("soTienVanChuyen");
        headerRow.createCell(12).setCellValue("maGiaoDichTienMat");
        headerRow.createCell(13).setCellValue("maGiaoDichChuyenKhoan");
        headerRow.createCell(14).setCellValue("phuongThucThanhToan");
        headerRow.createCell(15).setCellValue("thanhTien");
        headerRow.createCell(16).setCellValue("diaChi");
        headerRow.createCell(17).setCellValue("tenNguoiNhan");
        headerRow.createCell(18).setCellValue("sdtNguoiNhan");
        headerRow.createCell(19).setCellValue("sdtNguoiShip");
        headerRow.createCell(20).setCellValue("hinhThucBanHang");
        headerRow.createCell(21).setCellValue("trangThai");

        int rowNum = 1;
        for (HoaDon hoaDon : data) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(hoaDon.getId());
            if (hoaDon.getKhachHang() != null) {
                row.createCell(1).setCellValue(hoaDon.getKhachHang().getId());
            } else {
                row.createCell(1).setCellValue((String) null);
            }
            if (hoaDon.getNhanVien() != null) {
                row.createCell(2).setCellValue(hoaDon.getNhanVien().getId());
            } else {
                row.createCell(2).setCellValue((String) null);
            }
            row.createCell(3).setCellValue(hoaDon.getMa());
            row.createCell(4).setCellValue(String.valueOf(hoaDon.getNgayTao()));
            row.createCell(5).setCellValue(String.valueOf(hoaDon.getNgayMuonNhan()));
            row.createCell(6).setCellValue(hoaDon.getPhanTramGiamGia());
            row.createCell(7).setCellValue(hoaDon.getSoDiemCong());
            row.createCell(8).setCellValue(hoaDon.getSoDiemTru());
            row.createCell(9).setCellValue(String.valueOf(hoaDon.getSoTienChuyenKhoan()));
            row.createCell(10).setCellValue(String.valueOf(hoaDon.getSoTienMat()));
            row.createCell(11).setCellValue(String.valueOf(hoaDon.getSoTienVanChuyen()));
            row.createCell(12).setCellValue(hoaDon.getMaGiaoDichTienMat());
            row.createCell(13).setCellValue(hoaDon.getMaGiaoDichChuyenKhoan());
            row.createCell(14).setCellValue(String.valueOf(hoaDon.getPhuongThucThanhToan()));
            row.createCell(15).setCellValue(String.valueOf(hoaDon.getThanhTien()));
            row.createCell(16).setCellValue(hoaDon.getDiaChi());
            row.createCell(17).setCellValue(hoaDon.getTenNguoiNhan());
            row.createCell(18).setCellValue(hoaDon.getSdtNguoiNhan());
            row.createCell(19).setCellValue(hoaDon.getSdtNguoiShip());
            row.createCell(20).setCellValue(String.valueOf(hoaDon.getHinhThucBanHang()));
            row.createCell(21).setCellValue(String.valueOf(hoaDon.getTrangThai()));
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream.toByteArray();
    }
}
