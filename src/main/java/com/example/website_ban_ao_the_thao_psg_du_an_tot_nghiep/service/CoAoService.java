package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.service;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.create_request.CreateCoAoRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.update_request.UpdateCoAoRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.response.CoAoResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CoAoService {
    Page<CoAoResponse> pageCoAo(Integer pageNo, Integer size);

    Page<CoAoResponse> pageSearchCoAo(Integer pageNo,Integer size,String search);

    List<CoAoResponse> listCoAoResponse();

    CoAoResponse createCoAo(CreateCoAoRequest createCoAoRequest);

    CoAoResponse updateCoAo(UpdateCoAoRequest updateCoAoRequest, Integer id);

    CoAoResponse getOneCoAo(Integer id);

    void removeOrRevertCoAo(String trangThaiCoAo,Integer id);
}
