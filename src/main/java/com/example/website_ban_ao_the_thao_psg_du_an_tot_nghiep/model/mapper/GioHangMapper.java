package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.mapper;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.GioHang;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.create_request.CreateGioHangRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.update_request.UpdateGioHangRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.response.GioHangResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GioHangMapper {
    GioHang gioHangResponseToGioHangEntity(GioHangResponse gioHangResponse);

    GioHangResponse gioHangEntityToGioHangResponse(GioHang gioHang);

    GioHang createGioHangRequestToGioHangEntity(CreateGioHangRequest createGioHangRequest);

    GioHang updateGioHangRequestToGioHangEntity(UpdateGioHangRequest updateGioHangRequest);

    List<GioHangResponse> listGioHangEntityToGioHangResponse(List<GioHang> listGioHang);
}
