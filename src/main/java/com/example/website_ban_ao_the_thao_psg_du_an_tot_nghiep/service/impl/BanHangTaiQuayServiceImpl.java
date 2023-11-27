package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.service.impl;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common.GenCode;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.config.Config;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.dto.RequestPayment;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.dto.ResponsePayment;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.*;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.exception.NotFoundException;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.mapper.HoaDonChiTietMapper;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.mapper.HoaDonMapper;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.response.HoaDonChiTietResponse;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.response.HoaDonResponse;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.model.response.KhachHangResponse;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.repository.*;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.service.BanHangTaiQuayService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.Inet4Address;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common.ApplicationConstant.HinhThucBanHang.DELIVERY;
import static com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common.ApplicationConstant.HinhThucBanHang.STORE;
import static com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common.ApplicationConstant.TrangThaiHoaDon.*;
import static com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common.ApplicationConstant.TrangThaiHoaDonChiTiet.APPROVED;
import static com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common.ApplicationConstant.TrangThaiHoaDonChiTiet.REVERSE;

@Component
public class BanHangTaiQuayServiceImpl implements BanHangTaiQuayService {

    @Autowired
    HoaDonRepository hoaDonRepository;

    @Autowired
    ChiTietSanPhamRepository chiTietSanPhamRepository;

    @Autowired
    HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    KhachHangRepository khachHangRepository;

    @Autowired
    NhanVienRepository nhanVienRepository;

    @Autowired
    HttpSession session;

    @Autowired
    LichSuHoaDonRepository lichSuHoaDonRepository;

    @Autowired
    HoaDonMapper hoaDonMapper;

    @Autowired
    HoaDonChiTietMapper hoaDonChiTietMapper;

    @Override
    public List<HoaDonResponse> getAllHoaDonCho() {
        return hoaDonMapper.listHoaDonEntityToHoaDonResponse(hoaDonRepository.getHoaDonByTrangThai(CONFIRMED));
    }

    @Override
    public HoaDonResponse getOneHoaDon(Integer id) {
        HoaDon hoaDon = hoaDonRepository.findById(id).orElseThrow(() -> new NotFoundException("HoaDon not found with id " + id));
        return hoaDonMapper.hoaDonEntityToHoaDonResponse(hoaDon);
    }

    @Override
    public void deleteHoaDonChiTiet(Integer id) {
        hoaDonChiTietRepository.deleteById(id);
    }


    @Override
    public HoaDonResponse createHoaDonCho(String email) {
        NhanVien nhanVien = nhanVienRepository.findByEmail(email);
        if (nhanVien != null) {
            HoaDon hoaDon = new HoaDon();
            hoaDon.setMa(GenCode.generateHoaDonCode());
            hoaDon.setNgayTao(LocalDate.now());
            hoaDon.setTrangThai(CONFIRMED);
            hoaDon.setNhanVien(nhanVien);
            HoaDon hd = hoaDonRepository.save(hoaDon);
            LichSuHoaDon lichSuHoaDon = new LichSuHoaDon();
            lichSuHoaDon.setHoaDon(hd);
            lichSuHoaDon.setNhanVien(nhanVien);
            lichSuHoaDon.setMoTa("Tạo hoá đơn cho khách");
            lichSuHoaDon.setNgayTao(LocalDateTime.now());
            lichSuHoaDon.setLoaiLichSuHoaDon(ApplicationConstant.LoaiLichSuHoaDon.CREATED);
            lichSuHoaDonRepository.save(lichSuHoaDon);

            return hoaDonMapper.hoaDonEntityToHoaDonResponse(hd);
        }
        return null;
    }

    @Override
    public void createHoaDonChiTiet(Integer idHoaDon, Integer idCtsp) {
        HoaDon hoaDon = hoaDonRepository.findById(idHoaDon).orElseThrow(() -> new NotFoundException("HoaDon not found with id " + idHoaDon));
        ChiTietSanPham ctsp = chiTietSanPhamRepository.findById(idCtsp).orElseThrow(() -> new NotFoundException("ChiTietSanPham not found with id " + idCtsp));

        // Tìm xem có HoaDonChiTiet nào có cùng id ChiTietSanPham không

        Optional<HoaDonChiTiet> hoaDonChiTietOptional = hoaDon.getHoaDonChiTietList().stream()
                .filter(hdct -> hdct.getChiTietSanPham().getId().equals(ctsp.getId()))
                .findFirst();
        if (hoaDonChiTietOptional.isEmpty()) {
            HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
            hoaDonChiTiet.setHoaDon(hoaDon);
            hoaDonChiTiet.setChiTietSanPham(ctsp);
            hoaDonChiTiet.setSoLuong(1);
            hoaDonChiTiet.setGiaBan(ctsp.getSanPham().getGia());
            hoaDonChiTiet.setDonGia(ctsp.getSanPham().getGia());
            hoaDonChiTiet.setNgayTao(LocalDate.now());
            hoaDonChiTiet.setTrangThai(APPROVED);
            hoaDonChiTietRepository.save(hoaDonChiTiet);
        } else {
            HoaDonChiTiet hdct = hoaDonChiTietOptional.get();
            hdct.setSoLuong(hdct.getSoLuong() + 1);
            hdct.setDonGia(hdct.getDonGia().add(hdct.getGiaBan()));
            hdct.setNgayCapNhat(hdct.getNgayCapNhat());
            hoaDonChiTietRepository.save(hdct);
        }
    }

    @Override
    public HoaDonChiTietResponse truSoLuongHoaDonChiTiet(Integer idHdct) {
        HoaDonChiTiet hdct = hoaDonChiTietRepository.findById(idHdct).orElseThrow(() -> new NotFoundException("HoaDonChiTiet not found with id " + idHdct));
        int soLuongMoi = hdct.getSoLuong() - 1;
        if (soLuongMoi <= 0) {
            hoaDonChiTietRepository.deleteById(idHdct);
            return null;
        } else {
            hdct.setSoLuong(soLuongMoi);
            hdct.setDonGia(hdct.getDonGia().subtract(hdct.getGiaBan()));
            return hoaDonChiTietMapper.hoaDonChiTietEntityToHoaDonChiTietResponse(hoaDonChiTietRepository.save(hdct));
        }
    }


    @Override
    public HoaDonChiTietResponse congSoLuongHoaDonChiTiet(Integer idHdct) {
        HoaDonChiTiet hdct = hoaDonChiTietRepository.findById(idHdct).orElseThrow(() -> new NotFoundException("HoaDonChiTiet not found with id " + idHdct));

        int soLuongHienTai = hdct.getSoLuong();
        int soLuongTonKho = hdct.getChiTietSanPham().getSoLuong();
        if (soLuongHienTai + 1 > soLuongTonKho) {
            throw new NotFoundException("Số lượng vượt quá số lượng tồn kho");
        }
        hdct.setSoLuong(soLuongHienTai + 1);
        hdct.setDonGia(hdct.getDonGia().add(hdct.getGiaBan()));
        return hoaDonChiTietMapper.hoaDonChiTietEntityToHoaDonChiTietResponse(hoaDonChiTietRepository.save(hdct));
    }

    @Override
    public HoaDonChiTietResponse updateSoLuongAndDonGiaHoaDonChiTiet(Integer idHdct, Integer soLuong) {
        HoaDonChiTiet hdct = hoaDonChiTietRepository.findById(idHdct).orElseThrow(() -> new NotFoundException("HoaDonChiTiet not found with id " + idHdct));

        int soLuongTonKho = hdct.getChiTietSanPham().getSoLuong();
        if (soLuong > soLuongTonKho) {
            throw new NotFoundException("Số lượng vượt quá số lượng tồn kho");
        }

        if (soLuong == 0) {
            hoaDonChiTietRepository.deleteById(idHdct);
            return null;
        } else {
            hdct.setSoLuong(soLuong);
            hdct.setDonGia(hdct.getGiaBan().multiply(BigDecimal.valueOf(soLuong)));
            return hoaDonChiTietMapper.hoaDonChiTietEntityToHoaDonChiTietResponse(hoaDonChiTietRepository.save(hdct));
        }
    }


    @Override
    public HoaDonResponse updateKhachHangHoaDon(Integer idHd, Integer idKhachHang) {
        HoaDon hoaDon = hoaDonRepository.findById(idHd)
                .orElseThrow(() -> new NotFoundException("HoaDon không tồn tại với id " + idHd));

        KhachHang khachHang = khachHangRepository.findById(idKhachHang)
                .orElse(null);

        if (khachHang != null) {
            hoaDon.setKhachHang(khachHang);
        } else {
            hoaDon.setKhachHang(null);
        }

        return hoaDonMapper.hoaDonEntityToHoaDonResponse(hoaDonRepository.save(hoaDon));
    }

    @Override
    public HoaDonResponse updateDiaChiHoaDon(Integer idHd, LocalDate ngayMuonNhan, String diaChi, String tenNguoiNhan, String sdtNguoiNhan, String sdtNguoiShip) {
        HoaDon hoaDon = hoaDonRepository.findById(idHd).orElseThrow(() -> new NotFoundException("HoaDon not found with id " + idHd));
        hoaDon.setDiaChi(diaChi);
        hoaDon.setTenNguoiNhan(tenNguoiNhan);
        hoaDon.setSdtNguoiNhan(sdtNguoiNhan);
        hoaDon.setSdtNguoiShip(sdtNguoiShip);
        return hoaDonMapper.hoaDonEntityToHoaDonResponse(hoaDonRepository.save(hoaDon));
    }


    @Override
    public void updateTrangThaiHoaDon(Integer idHoaDon, ApplicationConstant.TrangThaiHoaDon trangThaiHoaDon) {
        HoaDon hoaDon = hoaDonRepository.findById(idHoaDon).orElseThrow(() -> new NotFoundException("HoaDon not found with id " + idHoaDon));
        LichSuHoaDon lichSuHoaDon = new LichSuHoaDon();
        lichSuHoaDon.setHoaDon(hoaDon);
        lichSuHoaDon.setNhanVien(null);
        lichSuHoaDon.setNgayTao(LocalDateTime.now());
        switch (trangThaiHoaDon) {
            case SHIPPING:
                lichSuHoaDon.setLoaiLichSuHoaDon(ApplicationConstant.LoaiLichSuHoaDon.SHIPPING);
                lichSuHoaDon.setMoTa("Đã giao cho đơn vị vận chuyển");
                break;
            case CONFIRMED:
                lichSuHoaDon.setLoaiLichSuHoaDon(ApplicationConstant.LoaiLichSuHoaDon.CONFIRMED);
                lichSuHoaDon.setMoTa("Đã xác nhận thông tin thanh toán");
                List<HoaDonChiTiet> hoaDonChiTietList = hoaDonChiTietRepository.getHoaDonChiTietByHoaDon(hoaDon);
                for (HoaDonChiTiet hdct : hoaDonChiTietList) {
                    ChiTietSanPham ctsp = chiTietSanPhamRepository.findById(hdct.getChiTietSanPham().getId()).get();
                    ctsp.setSoLuong(ctsp.getSoLuong() - hdct.getSoLuong());
                    chiTietSanPhamRepository.save(ctsp);
                }
                break;
            case APPROVED:
                lichSuHoaDon.setLoaiLichSuHoaDon(ApplicationConstant.LoaiLichSuHoaDon.APPROVED);
                lichSuHoaDon.setMoTa("Đơn hàng thành công");
                break;
            case CANCELLED:
                lichSuHoaDon.setLoaiLichSuHoaDon(ApplicationConstant.LoaiLichSuHoaDon.CANCELLED);
                lichSuHoaDon.setMoTa("Huỷ đơn hàng thành công");
                List<HoaDonChiTiet> hdctList = hoaDonChiTietRepository.getHoaDonChiTietByHoaDon(hoaDon);
                for (HoaDonChiTiet hdct : hdctList) {
                    ChiTietSanPham ctsp = chiTietSanPhamRepository.findById(hdct.getChiTietSanPham().getId()).get();
                    ctsp.setSoLuong(ctsp.getSoLuong() + hdct.getSoLuong());
                    chiTietSanPhamRepository.save(ctsp);
                }
                break;
            case REVERSE:
                lichSuHoaDon.setLoaiLichSuHoaDon(ApplicationConstant.LoaiLichSuHoaDon.REVERSED);
                lichSuHoaDon.setMoTa("Trả hàng");
                break;
            default:
                break;
        }
        lichSuHoaDonRepository.save(lichSuHoaDon);
        System.out.println(trangThaiHoaDon);
        hoaDonRepository.updateTrangThaiHoaDon(String.valueOf(trangThaiHoaDon), idHoaDon);
    }

    @Override
    public void updateTrangThaiHoaDonChiTiet(Integer idhdct, Integer soLuong) {
        HoaDonChiTiet hdct = hoaDonChiTietRepository.findById(idhdct).orElseThrow(() -> new NotFoundException("HoaDonChiTiet not found with id " + idhdct));
        ChiTietSanPham ctsp = hdct.getChiTietSanPham();

        if (soLuong < 0) {
            throw new IllegalArgumentException("Số lượng không hợp lệ.");
        }
        if (soLuong > ctsp.getSoLuong()) {
            throw new IllegalArgumentException("Số lượng vượt quá số lượng trong hóa đơn chi tiết.");
        }

        ctsp.setSoLuong(ctsp.getSoLuong() + soLuong);
        chiTietSanPhamRepository.save(ctsp);

        HoaDon hoaDon = hdct.getHoaDon();
        BigDecimal sanPhamGia = ctsp.getSanPham().getGia();
        BigDecimal giamTien = sanPhamGia.multiply(BigDecimal.valueOf(soLuong));
        hoaDon.setThanhTien(hoaDon.getThanhTien().subtract(giamTien));

        if (hoaDon.getHoaDonChiTietList().isEmpty()) {
            hoaDon.setTrangThai(ApplicationConstant.TrangThaiHoaDon.REVERSE);
        }
        hoaDonRepository.save(hoaDon);
        hoaDonChiTietRepository.updateTrangThaiHoaDonChiTiet(REVERSE, idhdct);
    }

    @Override
    public void thanhToanHoaDon(Integer idHoaDon,
                                String moTa,
                                LocalDate ngayMuonNhan,
                                Integer phanTramGiamGia,
                                BigDecimal soTienVanChuyen,
                                ApplicationConstant.HinhThucThanhToan phuongThucThanhToan,
                                BigDecimal thanhTien,
                                String diaChi,
                                String tenNguoiNhan,
                                String sdtNguoiNhan,
                                String sdtNguoiShip,
                                ApplicationConstant.HinhThucBanHang hinhThucBanHang) {
        HoaDon hoaDon = hoaDonRepository.findById(idHoaDon).orElseThrow(() -> new NotFoundException("HoaDon not found with id " + idHoaDon));
        hoaDon.setMoTa(moTa);
        hoaDon.setNgayMuonNhan(ngayMuonNhan);
        hoaDon.setPhanTramGiamGia(phanTramGiamGia);
        hoaDon.setSoTienVanChuyen(soTienVanChuyen);
        hoaDon.setPhuongThucThanhToan(phuongThucThanhToan);
        hoaDon.setThanhTien(thanhTien);
        hoaDon.setDiaChi(diaChi);
        hoaDon.setTenNguoiNhan(tenNguoiNhan);
        hoaDon.setSdtNguoiNhan(sdtNguoiNhan);
        hoaDon.setSdtNguoiShip(sdtNguoiShip);
        hoaDon.setHinhThucBanHang(hinhThucBanHang);
        HoaDon hd = hoaDonRepository.save(hoaDon);
        LichSuHoaDon lichSuHoaDon = new LichSuHoaDon();
        lichSuHoaDon.setHoaDon(hoaDon);
        lichSuHoaDon.setNhanVien(null);
        lichSuHoaDon.setNgayTao(LocalDateTime.now());

        if (hd.getPhuongThucThanhToan() == ApplicationConstant.HinhThucThanhToan.CASH) {
            hd.setMaGiaoDichTienMat(GenCode.generateGiaoDichCode());
            hd.setSoTienMat(hoaDon.getThanhTien());
            hd.setTrangThai(ApplicationConstant.TrangThaiHoaDon.APPROVED);
            lichSuHoaDon.setLoaiLichSuHoaDon(ApplicationConstant.LoaiLichSuHoaDon.APPROVED);
            lichSuHoaDon.setMoTa("Đơn hàng thành thông");
            List<HoaDonChiTiet> hoaDonChiTietList = hoaDonChiTietRepository.getHoaDonChiTietByHoaDon(hoaDon);
            for (HoaDonChiTiet hdct : hoaDonChiTietList) {
                ChiTietSanPham ctsp = chiTietSanPhamRepository.findById(hdct.getChiTietSanPham().getId()).get();
                ctsp.setSoLuong(ctsp.getSoLuong() - hdct.getSoLuong());
                chiTietSanPhamRepository.save(ctsp);
            }
        }

        if (hd.getPhuongThucThanhToan() == ApplicationConstant.HinhThucThanhToan.BANKING) {
            hd.setTrangThai(PENDING_CHECKOUT);
        }

        hoaDonRepository.save(hd);
        lichSuHoaDonRepository.save(lichSuHoaDon);
    }

    @Override
    public List<HoaDonResponse> getAllListHoaDonResponseStatusPending(ApplicationConstant.TrangThaiHoaDon trangThai) {
        return hoaDonMapper.listHoaDonEntityToHoaDonResponse(hoaDonRepository.getHoaDonsByTrangThai(trangThai));
    }

    @Override
    public ResponsePayment payment(RequestPayment requestPayment) throws UnsupportedEncodingException {
        Inet4Address ip;
        try {
            ip = (Inet4Address) Inet4Address.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_TxnRef = Config.getRandomNumber(8);
        String vnp_TmnCode = Config.vnp_TmnCode;
        long amount = (long) (requestPayment.getTotalPrice() * 100);
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
//        vnp_Params.put("vnp_Amount", "1000000");
        vnp_Params.put("vnp_CurrCode", "VND");
//        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang thoi gian:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", "topup");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_Returnurl);
        vnp_Params.put("vnp_IpAddr", String.valueOf(ip));
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
        ResponsePayment responsePayment = new ResponsePayment();
        responsePayment.setMessage("Successfully");
        responsePayment.setStatus("Ok");
        responsePayment.setURL(paymentUrl);
        if ("Ok".equalsIgnoreCase(responsePayment.getStatus())) {
            HoaDon hoaDon = hoaDonRepository.findById(requestPayment.getIdHoaDon()).orElseThrow(() -> new NotFoundException("HoaDon not found with id " + requestPayment.getIdHoaDon()));
            hoaDon.setSoTienChuyenKhoan(BigDecimal.valueOf(requestPayment.getTotalPrice()));
         if(hoaDon.getHinhThucBanHang() == STORE){
             hoaDon.setTrangThai(ApplicationConstant.TrangThaiHoaDon.APPROVED);
             LichSuHoaDon lichSuHoaDon = new LichSuHoaDon();
             lichSuHoaDon.setHoaDon(hoaDon);
             lichSuHoaDon.setNhanVien(null);
             lichSuHoaDon.setNgayTao(LocalDateTime.now());
             lichSuHoaDon.setLoaiLichSuHoaDon(ApplicationConstant.LoaiLichSuHoaDon.APPROVED);
             lichSuHoaDon.setMoTa("Đơn hàng thành thông");
             List<HoaDonChiTiet> hoaDonChiTietList = hoaDonChiTietRepository.getHoaDonChiTietByHoaDon(hoaDon);
             for (HoaDonChiTiet hdct : hoaDonChiTietList) {
                 ChiTietSanPham ctsp = chiTietSanPhamRepository.findById(hdct.getChiTietSanPham().getId()).get();
                 ctsp.setSoLuong(ctsp.getSoLuong() - hdct.getSoLuong());
                 chiTietSanPhamRepository.save(ctsp);
             }
         }
         if(hoaDon.getHinhThucBanHang() == DELIVERY){
             hoaDon.setTrangThai(PAYMENT_SUCCESS);
             LichSuHoaDon lichSuHoaDon = new LichSuHoaDon();
             lichSuHoaDon.setHoaDon(hoaDon);
             lichSuHoaDon.setNhanVien(null);
             lichSuHoaDon.setNgayTao(LocalDateTime.now());
             lichSuHoaDon.setLoaiLichSuHoaDon(ApplicationConstant.LoaiLichSuHoaDon.CONFIRMED);
             lichSuHoaDon.setMoTa("Chờ giao cho đơn vị vận chuyển");
             List<HoaDonChiTiet> hoaDonChiTietList = hoaDonChiTietRepository.getHoaDonChiTietByHoaDon(hoaDon);
             for (HoaDonChiTiet hdct : hoaDonChiTietList) {
                 ChiTietSanPham ctsp = chiTietSanPhamRepository.findById(hdct.getChiTietSanPham().getId()).get();
                 ctsp.setSoLuong(ctsp.getSoLuong() - hdct.getSoLuong());
                 chiTietSanPhamRepository.save(ctsp);
             }
         }
            hoaDonRepository.save(hoaDon);
        }
        return responsePayment;
    }

}
