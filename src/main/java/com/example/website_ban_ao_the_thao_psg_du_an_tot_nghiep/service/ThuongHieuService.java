package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.service;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.create_request.CreateThuongHieuRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.update_request.UpdateThuongHieuRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.response.ThuongHieuResponse;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.response.ThuongHieuResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ThuongHieuService {
    Page<ThuongHieuResponse> pageThuongHieu(Integer pageNo, Integer size);

    Page<ThuongHieuResponse> pageSearchThuongHieu(Integer pageNo,Integer size,String search);

    List<ThuongHieuResponse> listThuongHieuResponse();

    ThuongHieuResponse createThuongHieu(CreateThuongHieuRequest createThuongHieuRequest);

    ThuongHieuResponse updateThuongHieu(UpdateThuongHieuRequest updateThuongHieuRequest, Integer id);

    ThuongHieuResponse getOneThuongHieu(Integer id);

    void removeOrRevertThuongHieu(String trangThaiThuongHieu,Integer id);
}
