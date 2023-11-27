package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.service;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.create_request.CreateChuongTrinhTichDiemRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.update_request.UpdateChuongTrinhTichDiemRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.response.ChuongTrinhTichDiemResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface ChuongTrinhTichDiemService {

    Page<ChuongTrinhTichDiemResponse> pageChuongTrinhTichDiem(Integer pageNo, Integer size);

    Page<ChuongTrinhTichDiemResponse> pageSearchChuongTrinhTichDiem(Integer pageNo,Integer size,String search, String trangThai);

    List<ChuongTrinhTichDiemResponse> listChuongTrinhTichDiemResponse();

    ChuongTrinhTichDiemResponse createChuongTrinhTichDiem(CreateChuongTrinhTichDiemRequest createChuongTrinhTichDiemRequest);

    ChuongTrinhTichDiemResponse updateChuongTrinhTichDiem(UpdateChuongTrinhTichDiemRequest updateChuongTrinhTichDiemRequest, Integer id);

    ChuongTrinhTichDiemResponse getOneChuongTrinhTichDiem(Integer id);

    void removeOrRevertChuongTrinhTichDiem(String trangThaiChuongTrinhTichDiem,Integer id);

    byte[] exportExcelTichDiem() throws IOException;
}
