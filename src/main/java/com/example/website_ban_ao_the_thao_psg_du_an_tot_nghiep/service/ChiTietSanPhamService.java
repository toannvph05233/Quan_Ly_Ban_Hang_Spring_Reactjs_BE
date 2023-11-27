package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.service;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.update_request.UpdateChiTietSanPhamRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.response.ChiTietSanPhamResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface ChiTietSanPhamService {
    Page<ChiTietSanPhamResponse> pageChiTietSanPham(Integer pageNo, Integer size);
    Page<ChiTietSanPhamResponse> pageSearchChiTietSanPhamAdmin(Integer pageNo, Integer size, String search, Boolean gioiTinh,   Integer kichThuocId, Integer cauThuId,   Integer mauSacId,   Integer thuongHieuId,   Integer congNgheId, ApplicationConstant.TrangThaiSanPham trangThai);
    ChiTietSanPhamResponse getOneChiTietSanPham(Integer id);
    ChiTietSanPhamResponse getOneChiTietSanPhamBySku(String sku);

    List<ChiTietSanPhamResponse> findByTrangThai();

    List<ChiTietSanPhamResponse> updateSoLuongSanPhamPending(UpdateChiTietSanPhamRequest chiTietSanPhamRequest);
}
