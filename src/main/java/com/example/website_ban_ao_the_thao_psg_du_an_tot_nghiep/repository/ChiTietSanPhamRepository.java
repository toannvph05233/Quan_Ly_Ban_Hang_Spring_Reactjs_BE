package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.repository;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.ChiTietSanPham;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChiTietSanPhamRepository extends JpaRepository<ChiTietSanPham,Integer> {
    @Query("SELECT ctsp FROM ChiTietSanPham ctsp " +
            "LEFT JOIN ctsp.kichThuoc kt " +
            "LEFT JOIN ctsp.sanPham.cauThu ct " +
            "LEFT JOIN ctsp.sanPham.mauSac ms " +
            "LEFT JOIN ctsp.sanPham.thuongHieu th " +
            "LEFT JOIN ctsp.sanPham.congNghe cn " +
            "WHERE (:search is null or ctsp.sanPham.ten like %:search% " +
            "or ctsp.sanPham.ma like %:search% " +
            "or ctsp.sku like %:search%) " +
            "and (:gioiTinh is null or ctsp.sanPham.gioiTinh = :gioiTinh) " +
            "and (:kichThuocId is null or kt.id = :kichThuocId) " +
            "and (:cauThuId is null or ct.id = :cauThuId) " +
            "and (:mauSacId is null or ms.id = :mauSacId) " +
            "and (:thuongHieuId is null or th.id = :thuongHieuId) " +
            "and (:congNgheId is null or cn.id = :congNgheId) " +
            "and (:trangThai is null or ctsp.sanPham.trangThai = :trangThai)")
    Page<ChiTietSanPham> pageSearchChiTietSanPhamAdmin(Pageable pageable, @Param("search") String search, @Param("gioiTinh") Boolean gioiTinh,
                                                       @Param("kichThuocId") Integer kichThuocId,
                                                       @Param("cauThuId") Integer cauThuId,
                                  @Param("mauSacId") Integer mauSacId, @Param("thuongHieuId") Integer thuongHieuId, @Param("congNgheId") Integer congNgheId,
                                  @Param("trangThai") ApplicationConstant.TrangThaiSanPham trangThai);

    Optional<ChiTietSanPham> getChiTietSanPhamBySku(String sku);

    List<ChiTietSanPham> findChiTietSanPhamsByTrangThai(ApplicationConstant.TrangThaiChiTietSanPham trangThai);
}
