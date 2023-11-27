package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.service.impl;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common.GenCode;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.NuocSanXuat;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.exception.DuplicateRecordException;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.exception.InternalServerException;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.exception.NotFoundException;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.mapper.NuocSanXuatMapper;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.create_request.CreateNuocSanXuatRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.update_request.UpdateNuocSanXuatRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.response.NuocSanXuatResponse;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.repository.NuocSanXuatRepository;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.service.NuocSanXuatService;
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
public class NuocSanXuatServiceImpl implements NuocSanXuatService {
    @Autowired
    NuocSanXuatRepository nuocSanXuatRepository;

    @Autowired
    NuocSanXuatMapper nuocSanXuatMapper;

    @Override
    public Page<NuocSanXuatResponse> pageNuocSanXuat(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<NuocSanXuat> nuocSanXuatPage = nuocSanXuatRepository.findAll(pageable);
        return nuocSanXuatPage.map(nuocSanXuatMapper::nuocSanXuatEntityToNuocSanXuatResponse);
    }

    @Override
    public Page<NuocSanXuatResponse> pageSearchNuocSanXuat(Integer pageNo, Integer size, String search) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<NuocSanXuat> nuocSanXuatPage = nuocSanXuatRepository.pageSearch(pageable, search);
        return nuocSanXuatPage.map(nuocSanXuatMapper::nuocSanXuatEntityToNuocSanXuatResponse);
    }

    @Override
    public List<NuocSanXuatResponse> listNuocSanXuatResponse() {
        return nuocSanXuatMapper.listNuocSanXuatEntityToNuocSanXuatResponses(nuocSanXuatRepository.getNuocSanXuatByTrangThai(ApplicationConstant.TrangThaiSanPham.ACTIVE));
    }

    @Override
    public NuocSanXuatResponse createNuocSanXuat(CreateNuocSanXuatRequest createNuocSanXuatRequest) {
        try {
            NuocSanXuat nuocSanXuat = nuocSanXuatMapper.createNuocSanXuatRequestToNuocSanXuatEntity(createNuocSanXuatRequest);
            nuocSanXuat.setMa(GenCode.generateNuocSanXuatCode());
            nuocSanXuat.setNgayTao(LocalDate.now());
            nuocSanXuat.setTrangThai(ApplicationConstant.TrangThaiSanPham.ACTIVE);
            return nuocSanXuatMapper.nuocSanXuatEntityToNuocSanXuatResponse(nuocSanXuatRepository.save(nuocSanXuat));
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException("Failed to create nuoc san xuat. Possibly duplicate record." + ex);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to create nuoc san xuat due to an unexpected error." + ex);
        }
    }

    @Override
    @Transactional
    public NuocSanXuatResponse updateNuocSanXuat(UpdateNuocSanXuatRequest updateNuocSanXuatRequest, Integer id) {
        try {
            NuocSanXuat nuocSanXuat = nuocSanXuatRepository.findById(id).orElseThrow(() -> new NotFoundException("NuocSanXuat not found with id " + id));
            NuocSanXuat updateNuocSanXuat = nuocSanXuatMapper.updateNuocSanXuatRequestToNuocSanXuatEntity(updateNuocSanXuatRequest);
            nuocSanXuat.setTen(updateNuocSanXuat.getTen());
            nuocSanXuat.setNgayCapNhat(LocalDate.now());
            return nuocSanXuatMapper.nuocSanXuatEntityToNuocSanXuatResponse(nuocSanXuatRepository.save(nuocSanXuat));
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException("Failed to update nuoc san xuat. Possibly duplicate record." + ex);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to update nuoc san xuat due to an unexpected error." + ex);
        }
    }

    @Override
    public NuocSanXuatResponse getOneNuocSanXuat(Integer id) {
        NuocSanXuat nuocSanXuat = nuocSanXuatRepository.findById(id).orElseThrow(() -> new NotFoundException("NuocSanXuat not found with id " + id));
        return nuocSanXuatMapper.nuocSanXuatEntityToNuocSanXuatResponse(nuocSanXuat);
    }

    @Override
    public void removeOrRevertNuocSanXuat(String trangThaiNuocSanXuat, Integer id) {
        nuocSanXuatRepository.removeOrRevert(trangThaiNuocSanXuat, id);
    }
}
