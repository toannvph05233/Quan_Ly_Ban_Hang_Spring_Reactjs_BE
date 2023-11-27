package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.service;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.create_request.CreateTiLeQuyDoiThuHangRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.update_request.UpdateTiLeQuyDoiThuHangRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.response.TiLeQuyDoiThuHangResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TiLeQuyDoiThuHangService {
    Page<TiLeQuyDoiThuHangResponse> pageTiLeQuyDoiThuHang(Integer pageNo, Integer size);

    List<TiLeQuyDoiThuHangResponse> listTiLeQuyDoiThuHangResponse();

    TiLeQuyDoiThuHangResponse createTiLeQuyDoiThuHang(CreateTiLeQuyDoiThuHangRequest createTiLeQuyDoiThuHangRequest);

    TiLeQuyDoiThuHangResponse updateTiLeQuyDoiThuHang(UpdateTiLeQuyDoiThuHangRequest updateTiLeQuyDoiThuHangRequest, Integer id);

    TiLeQuyDoiThuHangResponse getOneTiLeQuyDoiThuHang(Integer id);

    void removeOrRevertTiLeQuyDoiThuHang(String trangThaiTiLeQuyDoiThuHang,Integer id);
}
