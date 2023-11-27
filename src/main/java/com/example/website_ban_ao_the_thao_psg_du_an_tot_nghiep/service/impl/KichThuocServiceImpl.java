package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.service.impl;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common.GenCode;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.KichThuoc;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.exception.DuplicateRecordException;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.exception.InternalServerException;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.exception.NotFoundException;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.mapper.KichThuocMapper;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.create_request.CreateKichThuocRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.update_request.UpdateKichThuocRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.response.KichThuocResponse;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.response.LoaiSanPhamResponse;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.repository.KichThuocRepository;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.service.KichThuocService;
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
public class KichThuocServiceImpl implements KichThuocService {

    @Autowired
    KichThuocRepository kichThuocRepository;

    @Autowired
    KichThuocMapper kichThuocMapper;

    @Override
    public Page<KichThuocResponse> pageKichThuoc(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<KichThuoc> kichThuocPage = kichThuocRepository.findAll(pageable);
        return kichThuocPage.map(kichThuocMapper::kichThuocEntityToKichThuocResponse);
    }
    @Override
    public List<KichThuocResponse> listKichThuocResponse() {
        return kichThuocMapper.listKichThuocEntityToKichThuocResponses(kichThuocRepository.getKichThuocByTrangThai(ApplicationConstant.TrangThaiSanPham.ACTIVE));
    }

    @Override
    public Page<KichThuocResponse> pageSearchKichThuoc(Integer pageNo, Integer size, String search) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<KichThuoc> kichThuocPage = kichThuocRepository.pageSearch(pageable, search);
        return kichThuocPage.map(kichThuocMapper::kichThuocEntityToKichThuocResponse);
    }

    @Override
    public KichThuocResponse createKichThuoc(CreateKichThuocRequest createKichThuocRequest) {

        try {
            KichThuoc kichThuoc = kichThuocMapper.createKichThuocRequestToKichThuocEntity(createKichThuocRequest);
            kichThuoc.setMa(GenCode.generateKichThuocCode());
            kichThuoc.setNgayTao(LocalDate.now());
            kichThuoc.setTrangThai(ApplicationConstant.TrangThaiSanPham.ACTIVE);
            if(kichThuocRepository.findByTen(kichThuoc.getTen()) != null){
                throw new InternalServerException("Tên này đã tồn tại " + kichThuoc.getTen());
            }
            return kichThuocMapper.kichThuocEntityToKichThuocResponse(kichThuocRepository.save(kichThuoc));
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException("Failed to create kich thuoc. Possibly duplicate record." + ex);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to create kich thuoc due to an unexpected error." + ex);
        }
    }

    @Override
    @Transactional
    public KichThuocResponse updateKichThuoc(UpdateKichThuocRequest updateKichThuocRequest, Integer id) {
        try {
            KichThuoc kichThuoc = kichThuocRepository.findById(id).orElseThrow(() -> new NotFoundException("KichThuoc not found with id " + id));
            KichThuoc updateKichThuoc = kichThuocMapper.updateKichThuocRequestToKichThuocEntity(updateKichThuocRequest);

            for (KichThuoc kt : kichThuocRepository.findAll()){
                if(kt.getTen().equals(updateKichThuoc.getTen()) && kt.getId() != kichThuoc.getId()){
                    throw new InternalServerException("Tên này đã tồn tại " + kt.getTen());
                }
            }
            kichThuoc.setTen(updateKichThuoc.getTen());
            kichThuoc.setNgayCapNhat(LocalDate.now());
            return kichThuocMapper.kichThuocEntityToKichThuocResponse(kichThuocRepository.save(kichThuoc));
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException("Failed to update kich thuoc. Possibly duplicate record." + ex);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to update kich thuoc due to an unexpected error." + ex);
        }
    }

    @Override
    public KichThuocResponse getOneKichThuoc(Integer id) {
        KichThuoc kichThuoc = kichThuocRepository.findById(id).orElseThrow(() -> new NotFoundException("KichThuoc not found with id " + id));
        return kichThuocMapper.kichThuocEntityToKichThuocResponse(kichThuoc);
    }

    @Override
    public void removeOrRevertKichThuoc(String trangThaiKichThuoc, Integer id) {
        kichThuocRepository.removeOrRevert(trangThaiKichThuoc, id);
    }

}
