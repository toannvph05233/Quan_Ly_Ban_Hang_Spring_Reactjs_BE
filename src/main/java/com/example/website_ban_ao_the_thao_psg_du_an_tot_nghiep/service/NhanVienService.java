package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.service;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.dto.KhachHangDto;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.dto.LoginMesage;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.dto.NhanVienDto;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.NhanVien;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.create_request.CreateNhanVienRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.update_request.UpdateNhanVienRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.response.NhanVienResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface NhanVienService {
    Page<NhanVienResponse>  pageNhanVien(Integer pageNo, Integer size);

    Page<NhanVienResponse> pageSearchNhanVien(Integer pageNo, Integer size, String search, ApplicationConstant.TrangThaiTaiKhoan trangThai, Integer vaiTroId);

    List<NhanVienResponse> listNhanVienResponse();
    NhanVien findByEmail(String email);

    byte[] exportExcelNhanVien() throws IOException;

    NhanVienResponse create(CreateNhanVienRequest createNhanVienRequest);

    NhanVienResponse update(UpdateNhanVienRequest updateNhanVienRequest, Integer id);

    NhanVienResponse getOne(Integer id);

    void removeOrRevert(String trangThaiNhanVien, Integer id);

    LoginMesage loginNhanVien(NhanVienDto nhanVienDto);

    boolean changeEmail(NhanVienDto nhanVienDto, String newEmailNv);

    void sendForgotPasswordEmailForNhanVien(String email, String soCanCuocCongDan);

}
