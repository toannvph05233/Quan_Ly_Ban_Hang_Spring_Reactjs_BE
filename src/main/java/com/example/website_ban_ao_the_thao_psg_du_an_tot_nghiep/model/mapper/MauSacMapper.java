package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.mapper;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.MauSac;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.create_request.CreateMauSacRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.update_request.UpdateMauSacRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.response.MauSacResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MauSacMapper {

    MauSac mauSacResponseToMauSacEntity(MauSacResponse mauSacResponse);

    MauSacResponse mauSacEntityToMauSacResponse(MauSac mauSac);

    MauSac createMauSacRequestToMauSacEntity(CreateMauSacRequest createMauSacRequest);

    MauSac updateMauSacRequestToMauSacEntity(UpdateMauSacRequest updateMauSacRequest);

    List<MauSacResponse> listMauSacEntityToMauSacResponse(List<MauSac> mauSacList);
}
