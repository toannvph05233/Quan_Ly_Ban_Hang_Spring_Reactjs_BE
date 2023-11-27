package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.mapper;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.GioHangChiTiet;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.create_request.CreateGioHangChiTietRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.update_request.UpdateGioHangChiTietRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.response.GioHangChiTietResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GioHangChiTietMapper {
    GioHangChiTiet gioHangChiTietResponseToGioHangChiTietEntity(GioHangChiTietResponse gioHangChiTietResponse);

    GioHangChiTietResponse gioHangChiTietEntityToGioHangChiTietResponse(GioHangChiTiet gioHangChiTiet);

    GioHangChiTiet createGioHangChiTietRequestToGioHangChiTietEntity(CreateGioHangChiTietRequest createGioHangChiTietRequest);

    GioHangChiTiet updateGioHangChiTietRequestToGioHangChiTietEntity(UpdateGioHangChiTietRequest updateGioHangChiTietRequest);

    List<GioHangChiTietResponse> listGioHangChiTietEntityToGioHangChiTietResponse(List<GioHangChiTiet> listGioHangChiTiet);
}
