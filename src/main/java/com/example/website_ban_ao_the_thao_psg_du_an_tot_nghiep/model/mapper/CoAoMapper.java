package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.mapper;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.CoAo;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.create_request.CreateCoAoRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.update_request.UpdateCoAoRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.response.CoAoResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CoAoMapper {

    CoAo coAoResponseToCoAoEntity(CoAoResponse coAoResponse);

    CoAoResponse coAoEntityToCoAoResponse(CoAo coAo);

    CoAo createCoAoRequestToCoAoEntity(CreateCoAoRequest createCoAoRequest);

    CoAo updateCoAoRequestToCoAoEntity(UpdateCoAoRequest updateCoAoRequest);

    List<CoAoResponse> listCoAoEntityToCoAoResponse(List<CoAo> coAoList);
}
