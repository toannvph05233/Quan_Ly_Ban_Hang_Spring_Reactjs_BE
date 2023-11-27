package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.mapper;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.VaiTro;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.create_request.CreateVaiTroRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.update_request.UpdateVaiTroRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.response.VaiTroResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VaiTroMapper {

    VaiTro vaiTroResponseToVaiTroEntity(VaiTroResponse vaiTroResponse);

    VaiTroResponse vaiTroEntityToVaiTroResponse(VaiTro vaiTro);

    VaiTro createVaiTroRequestToVaiTroEntity(CreateVaiTroRequest createVaiTroRequest);

    VaiTro updateVaiTroRequestToVaiTroEntity(UpdateVaiTroRequest updateVaiTroRequest);

    List<VaiTroResponse> listVaiTroEntityToVaiTroResponse(List<VaiTro> vaiTroList);
}
