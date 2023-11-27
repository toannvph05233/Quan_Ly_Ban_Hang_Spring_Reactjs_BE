package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.service;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.create_request.CreateLoaiSanPhamRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.update_request.UpdateLoaiSanPhamRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.response.LoaiSanPhamResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LoaiSanPhamService {
    Page<LoaiSanPhamResponse> pageLoaiSanPham(Integer pageNo, Integer size);

    Page<LoaiSanPhamResponse> pageSearchLoaiSanPham(Integer pageNo,Integer size,String search);

    List<LoaiSanPhamResponse> listLoaiSanPhamResponse();

    LoaiSanPhamResponse createLoaiSanPham(CreateLoaiSanPhamRequest createLoaiSanPhamRequest);

    LoaiSanPhamResponse updateLoaiSanPham(UpdateLoaiSanPhamRequest updateLoaiSanPhamRequest, Integer id);

    LoaiSanPhamResponse getOneLoaiSanPham(Integer id);

    void removeOrRevertLoaiSanPham(String trangThaiLoaiSanPham,Integer id);
}
