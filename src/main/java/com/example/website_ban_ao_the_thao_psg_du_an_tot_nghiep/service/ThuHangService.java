package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.service;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.create_request.CreateThuHangRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.update_request.UpdateThuHangRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.response.ThuHangResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface ThuHangService {
    Page<ThuHangResponse> pageThuHang(Integer pageNo, Integer size);

    Page<ThuHangResponse> pageSearchThuHang(Integer pageNo, Integer size, String search, String trangThaiThuHang);

    List<ThuHangResponse> listThuHangResponse();

    byte[] exportExcelThuHang() throws IOException;

    ThuHangResponse createThuHang(CreateThuHangRequest createThuHangRequest);

    ThuHangResponse updateThuHang(UpdateThuHangRequest updateThuHangRequest, Integer id);

    ThuHangResponse getOneThuHang(Integer id);

    void removeOrRevertThuHang(String trangThaiThuHang,Integer id);
}
