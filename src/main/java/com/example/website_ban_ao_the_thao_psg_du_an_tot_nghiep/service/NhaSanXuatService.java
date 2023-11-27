package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.service;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.create_request.CreateNhaSanXuatRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.update_request.UpdateNhaSanXuatRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.response.NhaSanXuatResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NhaSanXuatService {
    Page<NhaSanXuatResponse> pageNhaSanXuat(Integer pageNo, Integer size);

    Page<NhaSanXuatResponse> pageSearchNhaSanXuat(Integer pageNo,Integer size,String search);

    List<NhaSanXuatResponse> listNhaSanXuat();

    NhaSanXuatResponse createNhaSanXuat(CreateNhaSanXuatRequest createNhaSanXuatRequest);

    NhaSanXuatResponse updateNhaSanXuat(UpdateNhaSanXuatRequest updateNhaSanXuatRequest, Integer id);

    NhaSanXuatResponse getOneNhaSanXuat(Integer id);

    void removeOrRevertNhaSanXuat(String trangThaiNhaSanXuat,Integer id);
}
