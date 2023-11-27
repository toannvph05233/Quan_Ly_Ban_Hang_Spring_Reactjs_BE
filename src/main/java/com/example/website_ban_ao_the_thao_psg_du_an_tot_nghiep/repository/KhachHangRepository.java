package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.repository;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.KhachHang;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {
    @Query("SELECT kh FROM KhachHang kh LEFT JOIN kh.thuHang th WHERE " +
            "(:search is null or kh.ma LIKE %:search% or kh.ten LIKE %:search% or kh.email like %:search% or kh.sdt like %:search%) " +
            "and (:trangThai is null or kh.trangThai = :trangThai)" +
            "and (:thuHangId is null or th.id = :thuHangId)")
    Page<KhachHang> pageSearch(Pageable pageable, @Param("search") String search,
                               @Param("trangThai") ApplicationConstant.TrangThaiTaiKhoan trangThai,
                               @Param("thuHangId") Integer thuHangId);

    List<KhachHang> getKhachHangByTrangThai(ApplicationConstant.TrangThaiTaiKhoan trangThai);
    @Transactional
    @Modifying
    @Query(value = "UPDATE khach_hang kh SET kh.trang_thai = :trangThai WHERE kh.id = :id", nativeQuery = true)
    void removeOrRevert(@Param("trangThai") String trangThai, @Param("id") Integer id);

    KhachHang findBySdt(String sdt);

    KhachHang findByEmail(String email);
    @Query(value = "select kh from KhachHang kh where kh.email =:email and kh.matKhau =:matKhau")
    KhachHang findByEmailAndMatKhau(@Param("email") String email,@Param("matKhau") String matKhau);

    Optional<KhachHang> findById(Integer id);

    @Transactional
    @Modifying
    @Query(value = "update KhachHang kh set kh.soTienDaChiTieu = 0, kh.soLuongDonHangThanhCong = 0")
    void resetSoLuongDonHangThanhCongAndSoTienDaChiTieuVeKhong();

Optional<KhachHang>findOneByEmailAndMatKhau(String email, String matKhau);
    @Query("SELECT kh FROM KhachHang kh ORDER BY kh.soDiem DESC")
    List<KhachHang> findAllByOrderBySoDiemDesc(Pageable pageable);
}
