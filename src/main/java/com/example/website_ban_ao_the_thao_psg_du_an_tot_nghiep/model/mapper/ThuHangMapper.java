package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.mapper;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.ThuHang;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.create_request.CreateThuHangRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.update_request.UpdateThuHangRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.response.ThuHangResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ThuHangMapper {

    ThuHang thuHangResponseToThuHangEntity(ThuHangResponse thuHangResponse);

    ThuHangResponse thuHangEntiyToThuHangResponse(ThuHang thuHang);

    ThuHang createThuHangRequestToThuHangEntity(CreateThuHangRequest createThuHangRequest);

    ThuHang updateThuHangRequestToThuHangEntity(UpdateThuHangRequest updateThuHangRequest);

    List<ThuHangResponse> listThuHangEntityToThuHangResponse(List<ThuHang> thuHangMapperList);
}
