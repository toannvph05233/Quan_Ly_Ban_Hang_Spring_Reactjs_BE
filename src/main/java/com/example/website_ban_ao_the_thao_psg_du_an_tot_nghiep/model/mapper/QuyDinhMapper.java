package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.mapper;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.QuyDinh;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.create_request.CreateQuyDinhRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.update_request.UpdateQuyDinhRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.response.QuyDinhResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuyDinhMapper {

    QuyDinh quyDinhResponseToQuyDinhEntity(QuyDinhResponse quyDinhResponse);

    QuyDinhResponse quyDinhEntityToQuyDinhResponse(QuyDinh quyDinh);

    QuyDinh createQuyDinhRequestToQuyDinhEntity(CreateQuyDinhRequest createQuyDinhRequest);

    QuyDinh updateQuyDinhRequestToQuyDinhEntity(UpdateQuyDinhRequest updateQuyDinhRequestQuyDinhRequest);

    List<QuyDinhResponse> listQuyDinhEntityToQuyDinhResponse(List<QuyDinh> quyDinhList);
}
