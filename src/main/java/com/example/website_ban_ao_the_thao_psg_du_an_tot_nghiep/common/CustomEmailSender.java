/**
 * Đây là một lớp được sử dụng để gửi email trong ứng dụng. Lớp này chứa các phương thức để gửi email cho khách hàng và nhân viên.
 * Các phương thức trong lớp này sử dụng JavaMailSender để gửi email thông qua giao thức SMTP.
 * Lớp này được sử dụng để gửi email cho việc thăng hạng, đăng ký tài khoản và thông báo về việc đặt lại thứ hạng.
 */
package com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.common;

import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.KhachHang;
import com.example.website_ban_ao_the_thao_psg_du_an_tot_nghiep.entity.NhanVien;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class CustomEmailSender {

    private final JavaMailSender sender;

    @Autowired
    public CustomEmailSender(JavaMailSender sender) {
        this.sender = sender;
    }

    /**
     * Phương thức này được sử dụng để gửi email thông báo cho khách hàng khi họ được thăng hạng.
     * Email chứa các thông tin về thứ hạng mới và các ưu đãi đặc biệt cho khách hàng.
     *
     * @param khachHang Đối tượng KhachHang chứa thông tin về khách hàng.
     */
    public void updateThuHangkhachHangSendEmail(KhachHang khachHang){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(khachHang.getEmail());
        message.setSubject("Chúc mừng! Bạn đã thăng hạng trong Hội viên Ưu đãi PSG Fashion");
        message.setText("Chào bạn "+khachHang.getTen()+" thân mến,\n" +
                "\n" +
                "Chúc mừng bạn đã chính thức thăng hạng lên "+khachHang.getThuHang().getTen()+" trong Hội viên Ưu đãi PSG Fashion! Đây là một thành quả đáng tự hào mà bạn đã đạt được và chúng tôi xin gửi lời chúc mừng chân thành nhất đến bạn.\n" +
                "\n" +
                "Chúng tôi biết rằng bạn đã luôn ủng hộ chúng tôi trong suốt thời gian qua và chúng tôi xin cảm ơn bạn vô cùng. Thăng hạng của bạn không chỉ là một sự công nhận về sự hỗ trợ của bạn, mà còn là sự thể hiện về sự kiên nhẫn và sự cam kết của bạn đối với thương hiệu PSG Fashion.\n" +
                "\n" +
                "Với thăng hạng này, bạn sẽ được hưởng nhiều ưu đãi độc quyền hơn, bao gồm:\n" +
                "\n" +
                "Giảm giá đặc biệt cho các sản phẩm mới nhất của PSG Fashion.\n" +
                "Quyền truy cập trước vào các sự kiện và chương trình khuyến mãi sắp tới.\n" +
                "Đặc quyền tham gia vào các buổi triển lãm thời trang và hậu trường.\n" +
                "Quà tặng độc đáo và các ưu đãi đặc biệt khác dành riêng cho Hội viên Ưu đãi.\n" +
                "Chúng tôi rất mong muốn tiếp tục chia sẻ những trải nghiệm thú vị và mới mẻ với bạn trong tương lai. Hãy tiếp tục ủng hộ PSG Fashion và chúng tôi cam kết sẽ không ngừng phấn đấu để mang đến cho bạn những sản phẩm thời trang tốt nhất và những trải nghiệm mua sắm đáng nhớ.\n" +
                "\n" +
                "Nếu bạn có bất kỳ câu hỏi hoặc góp ý nào, xin vui lòng liên hệ với chúng tôi qua số điện thoại [số điện thoại] hoặc email [địa chỉ email]. Chúng tôi luôn sẵn sàng hỗ trợ bạn.\n" +
                "\n" +
                "Một lần nữa, chúc mừng bạn với sự thăng hạng đầy ý nghĩa này. Cảm ơn bạn vì đã là một phần quan trọng của cộng đồng PSG Fashion.\n" +
                "\n" +
                "Trân trọng,\n" +
                "Nguyễn Trọng Tùng Anh\n" +
                "Giám đốc\n" +
                "PSG Fashion");
        sender.send(message);
    }

    /**
     * Phương thức này được sử dụng để gửi email thông báo cho khách hàng khi họ đăng ký tài khoản thành công.
     * Email chứa thông tin đăng nhập của khách hàng.
     *
     * @param khachHang Đối tượng KhachHang chứa thông tin về khách hàng.
     */
    public void signupKhachHangSendEmail(KhachHang khachHang, String plainTextPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(khachHang.getEmail());
        message.setSubject("Đăng ký tài khoản thành công");
        message.setText("Chào anh/chị,\n" +
                "Dưới đây là thông tin tài khoản của bạn:\n" +
                "Tên đăng nhập (Email): " + khachHang.getEmail() + "\n" +
                "Mật khẩu: " + plainTextPassword + "\n" +
                "Vui lòng đăng nhập bằng thông tin này để sử dụng tài khoản của bạn.\n" +
                "\n" +
                "Trân trọng,\n" +
                "Cửa hàng bán áo thể thao PSG");
        sender.send(message);
    }

    /**
     * Phương thức này được sử dụng để gửi email thông báo cho nhân viên khi họ đăng ký tài khoản thành công.
     * Email chứa thông tin đăng nhập của nhân viên.
     *
     * @param nhanVien Đối tượng NhanVien chứa thông tin về nhân viên.
     */

    public void sendForgotPasswordEmail(KhachHang khachHang, String newPlainTextPassword){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(khachHang.getEmail());
        message.setSubject("Thay đổi mật khẩu của bạn");
        message.setText("Chào anh/chị, " +
                "Dưới đậy là mật khẩu mới của bạn" +
                "Mật khẩu: " + newPlainTextPassword + "\n" +
                "Vui lòng dùng mật khẩu này để đăng nhập lại tài khoản của bạn" + "\n"+
                "Trân trọng" + "\n" +
                "Của hàng bán áo đấu thể thao PSG");
        sender.send(message);
    }

    public void sendForgotPasswordEmailForNhanVien(NhanVien nhanVien, String newPlainTextPassword){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(nhanVien.getEmail());
        message.setSubject("Thay đổi mật khẩu của bạn");
        message.setText("Chào anh/chị, " +
                "Dưới đậy là mật khẩu mới của bạn" +
                "Mật khẩu: " + newPlainTextPassword + "\n" +
                "Vui lòng dùng mật khẩu này để đăng nhập lại tài khoản của bạn" + "\n"+
                "Trân trọng" + "\n" +
                "Của hàng bán áo đấu thể thao PSG");
        sender.send(message);
    }
    public void signupNhanVienSendEmail(NhanVien nhanVien, String genPassWord){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(nhanVien.getEmail());
        message.setSubject("Đăng ký tài khoản thành công");
        message.setText("Xin chào" +nhanVien.getTen()+",\n" +
                "\n" +
                "Chúng tôi rất vui mừng chào đón bạn gia nhập đội ngũ của cửa hàng bán áo PSG! Dưới đây là thông tin đăng nhập cần thiết để bạn có thể truy cập vào hệ thống của chúng tôi:\n" +
                "\n" +
                "Tài khoản (email): "+nhanVien.getEmail()+"\n" +
                "Mật khẩu: "+genPassWord+"\n" +
                "Chức vụ: "+nhanVien.getVaiTro().getTen()+"\n" +
                "\n" +
                "Vui lòng lưu ý rằng đây là mật khẩu tạm thời, và chúng tôi khuyến nghị bạn nên thay đổi mật khẩu ngay sau khi đăng nhập lần đầu. Hãy đảm bảo rằng bạn giữ thông tin đăng nhập này một cách cẩn thận để tránh rủi ro bất kỳ việc lạm dụng tài khoản nào.\n" +
                "\n" +
                "Nếu bạn gặp bất kỳ vấn đề gì trong quá trình đăng nhập hoặc sử dụng hệ thống, vui lòng liên hệ với bộ phận hỗ trợ của chúng tôi qua địa chỉ [địa chỉ email hỗ trợ] hoặc số điện thoại [số điện thoại hỗ trợ]. Chúng tôi luôn sẵn sàng hỗ trợ bạn.\n" +
                "\n" +
                "Chúc bạn một ngày làm việc hiệu quả và thú vị tại cửa hàng bán áo PSG! Chúng tôi hy vọng bạn sẽ đóng góp mạnh mẽ vào sự phát triển của đội ngũ và sự thành công của cửa hàng.\n" +
                "\n" +
                "Trân trọng,\n" +
                "Nguyễn Trọng Tùng Anh\n" +
                "Giám đốc Cửa hàng bán áo PSG");
        sender.send(message);
    }

    /**
     * Phương thức này được sử dụng để gửi email thông báo cho khách hàng khi thứ hạng của họ được đặt lại về mặc định.
     *
     * @param khachHang Đối tượng KhachHang chứa thông tin về khách hàng.
     */
    public void resetRankAlert(KhachHang khachHang){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(khachHang.getEmail());
        message.setSubject("Thông báo đặt lại thứ hạng");
        message.setText("Xin chào " + khachHang.getTen() + ",\n\nĐã đến ngày reset lại thứ hạng của bạn về mặc định. " +
                "Cảm ơn vì bạn đã đông hành cùng chúng tôi.\n\nTrân trọng,\nWebsite bán áo thể thao PSG");
        sender.send(message);
    }

    /**
     * Phương thức này được sử dụng để gửi email thông báo cho khách hàng trước khi thứ hạng của họ được đặt lại về mặc định.
     *
     * @param khachHang Đối tượng KhachHang chứa thông tin về khách hàng.
     */
    public void willResetRank(KhachHang khachHang){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(khachHang.getEmail());
        message.setSubject("Thông báo đặt lại thứ hạng");
        message.setText("Xin chào " + khachHang.getTen() + ",\n\nĐến trước 1 tháng nữa, chúng ta sẽ đặt lại thứ hạng." +
                " Vui lòng chuẩn bị cho điều này.\n" +
                "\nTrân trọng," +
                "\nWebsite bán áo thể thao PSG");
        sender.send(message);
    }
}
