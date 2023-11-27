package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.mapper;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.NuocSanXuat;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.create_request.CreateNuocSanXuatRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.update_request.UpdateNuocSanXuatRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.response.NuocSanXuatResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NuocSanXuatMapper {

    NuocSanXuat nuocSanXuatResponseToNuocSanXuatEntity(NuocSanXuatResponse nuocSanXuatResponse);

    NuocSanXuatResponse nuocSanXuatEntityToNuocSanXuatResponse(NuocSanXuat nuocSanXuat);

    NuocSanXuat createNuocSanXuatRequestToNuocSanXuatEntity(CreateNuocSanXuatRequest createNuocSanXuatRequest);

    NuocSanXuat updateNuocSanXuatRequestToNuocSanXuatEntity(UpdateNuocSanXuatRequest updateNuocSanXuatRequest);

    List<NuocSanXuatResponse> listNuocSanXuatEntityToNuocSanXuatResponses(List<NuocSanXuat> nuocSanXuatList);
}
