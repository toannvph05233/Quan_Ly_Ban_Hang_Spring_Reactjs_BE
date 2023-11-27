package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.service.impl;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common.GenCode;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.*;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.exception.DuplicateRecordException;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.exception.InternalServerException;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.exception.NotFoundException;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.mapper.ChiTietSanPhamMapper;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.mapper.SanPhamMapper;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.create_request.CreateSanPhamRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.update_request.UpdateSanPhamRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.response.SanPhamResponse;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.repository.AnhSanPhamRepository;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.repository.ChiTietSanPhamRepository;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.repository.KichThuocRepository;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.repository.SanPhamRepository;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.service.SanPhamService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class SanPhamServiceImpl implements SanPhamService {

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepository;

    @Autowired
    private AnhSanPhamRepository anhSanPhamRepository;

    @Autowired
    private SanPhamMapper sanPhamMapper;

    @Autowired
    private ChiTietSanPhamMapper chiTietSanPhamMapper;
    @Autowired
    private KichThuocRepository kichThuocRepository;

    @Override
    public Page<SanPhamResponse> pageSanPham(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<SanPham> sanPhamPage = sanPhamRepository.findAll(pageable);
        return sanPhamPage.map(sanPhamMapper::sanPhamEntityToSanPhamResponse);
    }

    @Override
    public Page<SanPhamResponse> pageSearchSanPhamAdmin(Integer pageNo, Integer size, String search, Boolean gioiTinh,
                                                        Integer cauThuId, Integer coAoId, Integer mauSacId, Integer loaiSanPhamId,
                                                        Integer chatLieuId, Integer dongSanPhamId, Integer nhaSanXuatId, Integer thuongHieuId,
                                                        Integer nuocSanXuatId, Integer congNgheId, ApplicationConstant.TrangThaiSanPham trangThai,
                                                        BigDecimal giaMin, BigDecimal giaMax) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<SanPham> sanPhamPage = sanPhamRepository.pageSearchAdmin(pageable, search, gioiTinh, cauThuId,
                coAoId, mauSacId, loaiSanPhamId, chatLieuId, dongSanPhamId, nhaSanXuatId, thuongHieuId, nuocSanXuatId,
                congNgheId, trangThai, giaMin, giaMax);
        return sanPhamPage.map(sanPhamMapper::sanPhamEntityToSanPhamResponse);
    }

    @Override
    public SanPhamResponse createSanPham(CreateSanPhamRequest createSanPhamRequest) {
        try {
            SanPham sanPham = this.sanPhamMapper.createSanPhamRequestToSanPhamEntity(createSanPhamRequest);
            sanPham.setMa(GenCode.generateSanPhamCode());
            sanPham.setNgayTao(LocalDate.now());
            sanPham.setTrangThai(ApplicationConstant.TrangThaiSanPham.ACTIVE);
            this.sanPhamRepository.save(sanPham);

            List<ChiTietSanPham> chiTietSanPhamList = new CopyOnWriteArrayList<>(createSanPhamRequest.getChiTietSanPhamList());
//            Repository.saveAll(anhSanPhamList);List<AnhSanPham> anhSanPhamList = new CopyOnWriteArrayList<>(createSanPhamRequest.getAnhSanPhamList());
////            AnhSanPham anhSanPham = new AnhSanPham();
////            for (AnhSanPham anh : anhSanPhamList) {
////                anhSanPham.setSanPham(anh.getSanPham());
////                anhSanPham.setAnh(anh.getAnh());
////                anhSanPham.setNgayTao(LocalDate.now());
////                anhSanPham.setTrangThai(ApplicationConstant.TrangThaiSanPham.ACTIVE);
////                anhSanPhamList.add(anhSanPham);
////                System.out.println("jjjjj");
////            }
////            this.anhSanPham

            for (ChiTietSanPham ct : chiTietSanPhamList) {
                ChiTietSanPham ctsp = new ChiTietSanPham();
                ctsp.setSanPham(sanPham);
                ctsp.setKichThuoc(ct.getKichThuoc());
                ctsp.setSoLuong(1);
                ctsp.setSku(ct.getSku());
                ctsp.setTrangThai(ApplicationConstant.TrangThaiChiTietSanPham.PENDING);
                chiTietSanPhamList.add(ctsp);
            }
            chiTietSanPhamRepository.saveAll(chiTietSanPhamList);
            return this.sanPhamMapper.sanPhamEntityToSanPhamResponse(sanPham);
        }catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException("Không thể tạo sản phẩm. Có thể bản ghi bị trùng lặp." + ex);
        } catch (Exception ex) {
            throw new InternalServerException("Không thể tạo sản phẩm do một lỗi không mong muốn xảy ra." + ex);
        }
    }

    @Override
    public SanPhamResponse updateSanPham(UpdateSanPhamRequest updateSanPhamRequest, Integer id) {
        try{
            SanPham sanPham = this.sanPhamRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm với ID: " + id));

            List<ChiTietSanPham> chiTietSanPhamList = sanPham.getChiTietSanPhamList();
            Map<Integer, ChiTietSanPham> existingChiTietMap = chiTietSanPhamList.stream()
                    .collect(Collectors.toMap(ChiTietSanPham::getId, Function.identity()));

            List<ChiTietSanPham> updatedChiTietSanPham = new ArrayList<>();

            for (ChiTietSanPham updatedChiTiet : updateSanPhamRequest.getChiTietSanPhamList()) {
                ChiTietSanPham existingChiTiet = existingChiTietMap.get(updatedChiTiet.getId());

                if (existingChiTiet != null) {
                    existingChiTiet.setKichThuoc(updatedChiTiet.getKichThuoc());
                    existingChiTiet.setSku(updatedChiTiet.getSku());
                    existingChiTiet.setSoLuong(updatedChiTiet.getSoLuong());
                    updatedChiTietSanPham.add(existingChiTiet);
                } else {
                    ChiTietSanPham newChiTiet = new ChiTietSanPham();
                    newChiTiet.setSanPham(sanPham);
                    newChiTiet.setKichThuoc(updatedChiTiet.getKichThuoc());
                    newChiTiet.setSku(updatedChiTiet.getSku());
                    newChiTiet.setSoLuong(updatedChiTiet.getSoLuong());
                    chiTietSanPhamRepository.save(newChiTiet);
                    updatedChiTietSanPham.add(newChiTiet);
                }
            }

            sanPham.setDongSanPham(updateSanPhamRequest.getDongSanPham());
            sanPham.setLoaiSanPham(updateSanPhamRequest.getLoaiSanPham());
            sanPham.setCauThu(updateSanPhamRequest.getCauThu());
            sanPham.setChatLieu(updateSanPhamRequest.getChatLieu());
            sanPham.setCoAo(updateSanPhamRequest.getCoAo());
            sanPham.setMauSac(updateSanPhamRequest.getMauSac());
            sanPham.setCongNghe(updateSanPhamRequest.getCongNghe());
            sanPham.setThuongHieu(updateSanPhamRequest.getThuongHieu());
            sanPham.setNuocSanXuat(updateSanPhamRequest.getNuocSanXuat());
            sanPham.setNhaSanXuat(updateSanPhamRequest.getNhaSanXuat());
            sanPham.setNamSanXuat(updateSanPhamRequest.getNamSanXuat());
            sanPham.setTen(updateSanPhamRequest.getTen());
            sanPham.setGia(updateSanPhamRequest.getGia());
            sanPham.setMoTa(updateSanPhamRequest.getMoTa());
            sanPham.setGioiTinh(updateSanPhamRequest.getGioiTinh());
            sanPham.setNgayCapNhat(LocalDate.now());

            this.sanPhamRepository.save(sanPham);

            return this.sanPhamMapper.sanPhamEntityToSanPhamResponse(sanPham);
        }catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException("Không thể sửa sản phẩm. Có thể bản ghi bị trùng lặp." + ex);
        } catch (Exception ex) {
            throw new InternalServerException("Không thể sửa sản phẩm do một lỗi không mong muốn xảy ra." + ex);
        }

    }
    @Override
    public List<SanPhamResponse> listSanPhamResponse() {
        return sanPhamMapper.listSanPhamEntityToSanPhamResponse(sanPhamRepository.getSanPhamByTrangThai(ApplicationConstant.TrangThaiSanPham.ACTIVE));
    }

    @Override
    public byte[] exportExcelSanPham() throws IOException {
        List<SanPham> data = sanPhamRepository.findAll();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("SanPham");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("id");
        headerRow.createCell(1).setCellValue("mauSac");
        headerRow.createCell(2).setCellValue("loaiSanPham");
        headerRow.createCell(3).setCellValue("chatLieu");
        headerRow.createCell(4).setCellValue("dongSanPham");
        headerRow.createCell(5).setCellValue("nhaSanXuat");
        headerRow.createCell(6).setCellValue("thuongHieu");
        headerRow.createCell(7).setCellValue("nuocSanXuat");
        headerRow.createCell(8).setCellValue("congNghe");
        headerRow.createCell(9).setCellValue("coAo");
        headerRow.createCell(10).setCellValue("cauThu");
        headerRow.createCell(11).setCellValue("namSanXuat");
        headerRow.createCell(12).setCellValue("ma");
        headerRow.createCell(13).setCellValue("ten");
        headerRow.createCell(14).setCellValue("gia");
        headerRow.createCell(15).setCellValue("moTa");
        headerRow.createCell(16).setCellValue("gioiTinh");
        headerRow.createCell(17).setCellValue("ngayTao");
        headerRow.createCell(18).setCellValue("ngayCapNhat");
        headerRow.createCell(19).setCellValue("trangThai");

        int rowNum = 1;
        for (SanPham sanPham : data) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(sanPham.getId());
            row.createCell(1).setCellValue(sanPham.getMauSac().getId());
            row.createCell(2).setCellValue(sanPham.getLoaiSanPham().getId());
            row.createCell(3).setCellValue(sanPham.getChatLieu().getId());
            row.createCell(4).setCellValue(sanPham.getDongSanPham().getId());
            row.createCell(5).setCellValue(sanPham.getNhaSanXuat().getId());
            row.createCell(6).setCellValue(sanPham.getThuongHieu().getId());
            row.createCell(7).setCellValue(sanPham.getNuocSanXuat().getId());
            row.createCell(8).setCellValue(sanPham.getCongNghe().getId());
            row.createCell(9).setCellValue(sanPham.getCoAo().getId());
            row.createCell(10).setCellValue(sanPham.getCauThu().getId());
            row.createCell(11).setCellValue(String.valueOf(sanPham.getNamSanXuat()));
            row.createCell(12).setCellValue(sanPham.getMa());
            row.createCell(13).setCellValue(sanPham.getTen());
            row.createCell(14).setCellValue(String.valueOf(sanPham.getGia()));
            row.createCell(15).setCellValue(sanPham.getMoTa());
            row.createCell(16).setCellValue(sanPham.getGioiTinh() != null ? sanPham.getGioiTinh().booleanValue() : false);
            row.createCell(17).setCellValue(String.valueOf(sanPham.getNgayTao()));
            row.createCell(18).setCellValue(String.valueOf(sanPham.getNgayCapNhat()));
            row.createCell(19).setCellValue(String.valueOf(sanPham.getTrangThai()));
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream.toByteArray();
    }

    @Override
    public SanPhamResponse getOneSanPham(Integer id) {
        SanPham sanPham = sanPhamRepository.findById(id).orElseThrow(() -> new NotFoundException("SanPham not found with id" + id));
        return sanPhamMapper.sanPhamEntityToSanPhamResponse(sanPham);
    }

    @Override
    public void removeOrRevertSanPham(String trangThaiSanPham, Integer id) {
        sanPhamRepository.removeOrRevertSanPham(trangThaiSanPham, id);
    }
}
