package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.service.impl;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common.GenCode;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.NhaSanXuat;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.exception.DuplicateRecordException;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.exception.InternalServerException;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.exception.NotFoundException;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.mapper.NhaSanXuatMapper;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.create_request.CreateNhaSanXuatRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.update_request.UpdateNhaSanXuatRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.response.NhaSanXuatResponse;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.repository.NhaSanXuatRepository;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.service.NhaSanXuatService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class NhaSanXuatServiceImpl implements NhaSanXuatService {
    @Autowired
    NhaSanXuatRepository nhaSanXuatRepository;

    @Autowired
    NhaSanXuatMapper nhaSanXuatMapper;

    @Override
    public Page<NhaSanXuatResponse> pageNhaSanXuat(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<NhaSanXuat> nhaSanXuatPage = nhaSanXuatRepository.findAll(pageable);
        return nhaSanXuatPage.map(nhaSanXuatMapper::nhaSanXuatEntityToNhaSanXuatResponse);
    }

    @Override
    public Page<NhaSanXuatResponse> pageSearchNhaSanXuat(Integer pageNo, Integer size, String search) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<NhaSanXuat> nhaSanXuatPage = nhaSanXuatRepository.pageSearch(pageable, search);
        return nhaSanXuatPage.map(nhaSanXuatMapper::nhaSanXuatEntityToNhaSanXuatResponse);
    }

    @Override
    public List<NhaSanXuatResponse> listNhaSanXuat() {
        return nhaSanXuatMapper.listNhaSanXuatEntityToNhaSanXuatRespnse(nhaSanXuatRepository.getNhaSanXuatByTrangThai(ApplicationConstant.TrangThaiSanPham.ACTIVE));
    }

    @Override
    public NhaSanXuatResponse createNhaSanXuat(CreateNhaSanXuatRequest createNhaSanXuatRequest) {
        try {
            NhaSanXuat nhaSanXuat = nhaSanXuatMapper.createNhaSanXuatRequestToNhaSanXuatEntity(createNhaSanXuatRequest);
            nhaSanXuat.setMa(GenCode.generateNhaSanXuatCode());
            nhaSanXuat.setNgayTao(LocalDate.now());
            nhaSanXuat.setTrangThai(ApplicationConstant.TrangThaiSanPham.ACTIVE);
            return nhaSanXuatMapper.nhaSanXuatEntityToNhaSanXuatResponse(nhaSanXuatRepository.save(nhaSanXuat));
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException("Failed to create nha san xuat. Possibly duplicate record." + ex);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to create nha san xuat due to an unexpected error." + ex);
        }
    }

    @Override
    @Transactional
    public NhaSanXuatResponse updateNhaSanXuat(UpdateNhaSanXuatRequest updateNhaSanXuatRequest, Integer id) {
        try {
            NhaSanXuat nhaSanXuat = nhaSanXuatRepository.findById(id).orElseThrow(() -> new NotFoundException("NhaSanXuat not found with id " + id));
            NhaSanXuat updateNhaSanXuat = nhaSanXuatMapper.updateNhaSanXuatRequestToNhaSanXuatEntity(updateNhaSanXuatRequest);
            nhaSanXuat.setTen(updateNhaSanXuat.getTen());
            nhaSanXuat.setNgayCapNhat(LocalDate.now());
            return nhaSanXuatMapper.nhaSanXuatEntityToNhaSanXuatResponse(nhaSanXuatRepository.save(nhaSanXuat));
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException("Failed to update nha san xuat. Possibly duplicate record." + ex);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to update nha san xuat due to an unexpected error." + ex);
        }
    }

    @Override
    public NhaSanXuatResponse getOneNhaSanXuat(Integer id) {
        NhaSanXuat nhaSanXuat = nhaSanXuatRepository.findById(id).orElseThrow(() -> new NotFoundException("NhaSanXuat not found with id " + id));
        return nhaSanXuatMapper.nhaSanXuatEntityToNhaSanXuatResponse(nhaSanXuat);
    }

    @Override
    public void removeOrRevertNhaSanXuat(String trangThaiNhaSanXuat, Integer id) {
        nhaSanXuatRepository.removeOrRevert(trangThaiNhaSanXuat, id);
    }
}
