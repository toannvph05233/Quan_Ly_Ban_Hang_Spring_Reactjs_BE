package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.repository;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.NhanVien;
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
public interface NhanVienRepository extends JpaRepository<NhanVien, Integer> {
    @Query("SELECT nv FROM NhanVien nv LEFT JOIN nv.vaiTro vt WHERE " +
            "(:search is null or nv.ma LIKE %:search% or nv.ten LIKE %:search% or nv.email like %:search% or nv.sdt like %:search%) " +
            "and (:trangThai is null or nv.trangThai = :trangThai)" +
            "and (:vaiTroId is null or vt.id = :vaiTroId)")
    Page<NhanVien> pageSearch(Pageable pageable, @Param("search") String search,
                              @Param("trangThai") ApplicationConstant.TrangThaiTaiKhoan trangThai,
                              @Param("vaiTroId") Integer vaiTroId);

    List<NhanVien> getNhanVienByTrangThai(ApplicationConstant.TrangThaiTaiKhoan trangThai);

    @Transactional
    @Modifying
    @Query(value = "UPDATE nhan_vien nv SET nv.trang_thai = :trangThai WHERE nv.id = :id", nativeQuery = true)
    void removeOrRevert(@Param("trangThai") String trangThai, @Param("id") Integer id);

    NhanVien findBySdt(String sdt);

    NhanVien findByEmail(String email);

    NhanVien findBySoCanCuocCongDan(String soCanCuocCongDan);

    Optional<NhanVien> findOneByEmailAndMatKhau(String email, String matKhau);

}
