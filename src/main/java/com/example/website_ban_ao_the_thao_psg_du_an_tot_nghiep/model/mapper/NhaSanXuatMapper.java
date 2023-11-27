package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.mapper;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.NhaSanXuat;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.create_request.CreateNhaSanXuatRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.update_request.UpdateNhaSanXuatRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.response.NhaSanXuatResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NhaSanXuatMapper {

    NhaSanXuat nhaSanXuatResponseToNhaSanXuatEntity(NhaSanXuatResponse nhaSanXuatResponse);

    NhaSanXuatResponse nhaSanXuatEntityToNhaSanXuatResponse(NhaSanXuat nhaSanXuat);

    NhaSanXuat createNhaSanXuatRequestToNhaSanXuatEntity(CreateNhaSanXuatRequest createNhaSanXuatRequest);

    NhaSanXuat updateNhaSanXuatRequestToNhaSanXuatEntity(UpdateNhaSanXuatRequest updateNhaSanXuatRequest);

    List<NhaSanXuatResponse> listNhaSanXuatEntityToNhaSanXuatRespnse(List<NhaSanXuat> nhaSanXuatList);
}
