package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.controller;


import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.create_request.CreateDiaChiRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.request.update_request.UpdateDiaChiRequest;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.response.DiaChiResponse;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.repository.KhachHangRepository;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.service.DiaChiService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common.ApplicationConstant.TrangThaiDiaChi.DEFAULT;
import static com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common.ApplicationConstant.TrangThaiDiaChi.EXTRA;

@RequestMapping("/api/dia-chi")
@RestController
@CrossOrigin(origins = "*", maxAge = -1)

public class DiaChiController {

    @Autowired
    private DiaChiService diaChiService;

    @GetMapping("")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "3", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(diaChiService.pageDiaChi(pageNo, pageSize));
    }


    @PostMapping(path= "/create")
    public ResponseEntity<?> createDiaChi(@RequestBody CreateDiaChiRequest createDiaChiRequest, BindingResult bindingResult, HttpSession session, @RequestHeader("Authorization") String authorization) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            return ResponseEntity.ok(list);
        }
        Integer khachHangID = Integer.parseInt(authorization.substring(7));
        System.out.println(khachHangID);
        try {
            if (khachHangID != null) {
                DiaChiResponse diaChiResponse = diaChiService.add(createDiaChiRequest, khachHangID);
                return ResponseEntity.ok(diaChiResponse);
            } else {
                return ResponseEntity.badRequest().body("Không tìm thấy ID khách hàng trong session");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(name = "search", required = false) String search,
                                    @RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "3", name = "pageNo", required = false) Integer pageSize) {
        return ResponseEntity.ok(diaChiService.pageSearchDiaChi(pageSize, pageNo, search));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDiaChi(@PathVariable("id") Integer id) {
        diaChiService.delete(id);
        return ResponseEntity.ok("Xóa thành công");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneDiaCHi(@PathVariable("id") Integer id){
        return ResponseEntity.ok(diaChiService.getOneDiaChi(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateDiaChi(@Valid @RequestBody UpdateDiaChiRequest updateDiaChiRequest, BindingResult result, @PathVariable("id") Integer id) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(diaChiService.update(updateDiaChiRequest, id));
    }

    @PutMapping("/default/{id}")
    public ResponseEntity<?> updateDefault(@PathVariable("id") Integer id){
        diaChiService.diaChiTrangThai(String.valueOf(DEFAULT), id);
        return ResponseEntity.ok("Cập nhật thành công");
    }

    @PutMapping("/extra/{id}")
    public ResponseEntity<?> updateExtra(@PathVariable("id") Integer id){
        diaChiService.diaChiTrangThai(String.valueOf(EXTRA), id);
        return ResponseEntity.ok("Cập nhật thành công");
    }
}
