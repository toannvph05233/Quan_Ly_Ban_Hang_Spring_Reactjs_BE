package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.service;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.create_request.CreateDiaChiRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.update_request.UpdateDiaChiRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.response.DiaChiResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
public interface DiaChiService {

    Page<DiaChiResponse> pageDiaChi(Integer pageNo, Integer size);

    DiaChiResponse getOneDiaChi(Integer id);

    Page<DiaChiResponse> pageSearchDiaChi(Integer pageNo, Integer size, String search);

    DiaChiResponse add(CreateDiaChiRequest createDiaChiRequest, Integer khachHangID);

    DiaChiResponse update(UpdateDiaChiRequest updateDiaChiRequest , Integer id);

    DiaChiResponse getOne(Integer id);
    Page<DiaChiResponse> searchNameOrMa(String searchName, Integer pageNo, Integer size);

    void delete(Integer id);

    void diaChiTrangThai(String trangThaiDiaChi, Integer id);





    
}
