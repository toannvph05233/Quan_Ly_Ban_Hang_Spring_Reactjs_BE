package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.mapper;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.ChuongTrinhTichDiem;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.create_request.CreateChuongTrinhTichDiemRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.update_request.UpdateChuongTrinhTichDiemRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.response.ChuongTrinhTichDiemResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChuongTrinhTichDiemMapper {

    ChuongTrinhTichDiem chuongTrinhTichDiemResponseToChuongTrinhTichDiemEntity(ChuongTrinhTichDiemResponse chuongTrinhTichDiemResponse);

    ChuongTrinhTichDiemResponse chuongTrinhTichDiemEntityToChuongTrinhTichDiemResponse(ChuongTrinhTichDiem chuongTrinhTichDiem);

    ChuongTrinhTichDiem createChuongTrinhTichDiemRequestToChuongTrinhTichDiemEntity(CreateChuongTrinhTichDiemRequest createChuongTrinhTichDiemRequest);

    ChuongTrinhTichDiem updateChuongTrinhTichDiemRequestToChuongTrinhTichDiemEntity(UpdateChuongTrinhTichDiemRequest updateChuongTrinhTichDiemRequest);

    List<ChuongTrinhTichDiemResponse> listChuongTrinhTichDiemEntityToChuongTrinhTichDiemResponse(List<ChuongTrinhTichDiem> chuongTrinhTichDiemList);
}
