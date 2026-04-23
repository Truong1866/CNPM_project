-- =====================================================
-- CNPM PROJECT - DATABASE SCHEMA
-- Hệ thống quản lý thu phí chung cư BlueMoon
-- Created: 2026-04-23
-- =====================================================

-- =====================================================
-- 1. CÁC BẢNG CƠ BẢN (Existing - Reference)
-- =====================================================

CREATE TABLE IF NOT EXISTS canho (
    MaCanHo VARCHAR(50) PRIMARY KEY,
    SoPhong VARCHAR(50) NOT NULL UNIQUE,
    DienTich INT NOT NULL,
    MoTa VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS khoanthu (
    MaKhoanThu VARCHAR(50) PRIMARY KEY,
    TenKhoanThu VARCHAR(100) NOT NULL,
    BatBuoc BOOLEAN DEFAULT TRUE,
    CoDinh BOOLEAN DEFAULT TRUE,
    DonGia INT NOT NULL,
    MoTa VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS hokhau (
    MaHo VARCHAR(50) PRIMARY KEY,
    MaCanHo VARCHAR(50) NOT NULL,
    FOREIGN KEY (MaCanHo) REFERENCES canho(MaCanHo)
);

CREATE TABLE IF NOT EXISTS nguoidung (
    MaNguoiDung VARCHAR(50) PRIMARY KEY,
    TenNguoiDung VARCHAR(100) NOT NULL,
    MatKhau VARCHAR(100) NOT NULL,
    VaiTro VARCHAR(50),
    DienThoai VARCHAR(20),
    CCCD VARCHAR(20) UNIQUE
);

-- =====================================================
-- 2. UPDATE BẢNG hokhau_khoanthu - ADD CÁC CỘT MỚI
-- =====================================================

CREATE TABLE IF NOT EXISTS hokhau_khoanthu (
    MaHo VARCHAR(50),
    MaKhoanThu VARCHAR(50),
    TrangThai BOOLEAN,
    SoLuong INT,
    NgayNop DATE,
    HanNop DATE,
    MoTa VARCHAR(255),
    -- CÁC CỘT MỚI
    SoTienThuc INT DEFAULT 0,           -- Số tiền thực nộp
    TienCo INT DEFAULT 0,               -- Tiền dư/công nợ
    TienThieu INT DEFAULT 0,            -- Tiền còn nợ
    TrangThaiChiTiet VARCHAR(50) DEFAULT 'Chưa nộp',  -- Chưa nộp / Nộp 1 phần / Nộp đủ / Quá hạn
    PRIMARY KEY (MaHo, MaKhoanThu),
    FOREIGN KEY (MaHo) REFERENCES hokhau(MaHo),
    FOREIGN KEY (MaKhoanThu) REFERENCES khoanthu(MaKhoanThu)
);

-- =====================================================
-- 3. BẢNG MỚI: phiBienLai - LƯU TRỮ GIAO DỊCH THU TIỀN
-- =====================================================

CREATE TABLE IF NOT EXISTS phiBienLai (
    MaPhiBienLai VARCHAR(50) PRIMARY KEY,    -- RCP-YYYY-MMDD-0001
    MaHo VARCHAR(50) NOT NULL,
    MaKhoanThu VARCHAR(50) NOT NULL,
    SoTienThuc INT NOT NULL,
    NgayThu DATE NOT NULL,
    TrangThaiThu VARCHAR(20) DEFAULT 'Đã thu',  -- Đã thu / Hủy
    NguoiThu VARCHAR(50),
    GhiChu VARCHAR(255),
    NgayTao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (MaPhiBienLai),
    FOREIGN KEY (MaHo) REFERENCES hokhau(MaHo),
    FOREIGN KEY (MaKhoanThu) REFERENCES khoanthu(MaKhoanThu),
    -- Tránh duplicate: cùng ngày, cùng hộ, cùng khoản
    UNIQUE KEY unique_payment (MaHo, MaKhoanThu, NgayThu)
);

-- =====================================================
-- 4. BẢNG MỚI: nguoidung_hokhau - QUAN HỆ NGƯỜI DÙNG & HỘ KHẨU
-- =====================================================

CREATE TABLE IF NOT EXISTS nguoidung_hokhau (
    MaNguoiDung VARCHAR(50),
    MaHo VARCHAR(50),
    ChuHo BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (MaNguoiDung, MaHo),
    FOREIGN KEY (MaNguoiDung) REFERENCES nguoidung(MaNguoiDung),
    FOREIGN KEY (MaHo) REFERENCES hokhau(MaHo)
);

-- =====================================================
-- 5. VIEW - THỐNG KÊ TỔNG HỢP THEO KHOẢN THU
-- =====================================================

CREATE VIEW IF NOT EXISTS v_tongKeThongKe AS
SELECT
    kt.MaKhoanThu,
    kt.TenKhoanThu,
    COUNT(DISTINCT hk.MaHo) AS TongHoCoKhoan,
    COALESCE(SUM(hk.SoTienThuc), 0) AS TongTienThu,
    COALESCE(SUM(hk.TienThieu), 0) AS TongTienThieu,
    COUNT(CASE WHEN hk.TrangThaiChiTiet = 'Nộp đủ' THEN 1 END) AS HoNopDu,
    ROUND(COUNT(CASE WHEN hk.TrangThaiChiTiet = 'Nộp đủ' THEN 1 END) * 100.0 / COUNT(DISTINCT hk.MaHo), 2) AS TiLeNopDu
FROM hokhau_khoanthu hk
JOIN khoanthu kt ON hk.MaKhoanThu = kt.MaKhoanThu
GROUP BY kt.MaKhoanThu, kt.TenKhoanThu;

-- =====================================================
-- 6. VIEW - THỐNG KÊ CHI TIẾT THEO HỘ KHẨU
-- =====================================================

CREATE VIEW IF NOT EXISTS v_chiTietHoKhau AS
SELECT
    hk.MaHo,
    COALESCE(SUM(hk.SoTienThuc), 0) AS TongTienDaNop,
    COALESCE(SUM(kt.DonGia * c.DienTich), 0) AS TongTienPhaiNop,
    COALESCE(SUM(hk.TienThieu), 0) AS TongTienConNo,
    COUNT(CASE WHEN hk.TrangThaiChiTiet = 'Quá hạn' THEN 1 END) AS SoKhoanQuaHan
FROM hokhau hk
LEFT JOIN hokhau_khoanthu hk_kt ON hk.MaHo = hk_kt.MaHo
LEFT JOIN khoanthu kt ON hk_kt.MaKhoanThu = kt.MaKhoanThu
LEFT JOIN canho c ON hk.MaCanHo = c.MaCanHo
GROUP BY hk.MaHo;

-- =====================================================
-- 7. INDEX - TỐI ƯU HÓA QUERY
-- =====================================================

CREATE INDEX IF NOT EXISTS idx_phiBienLai_MaHo ON phiBienLai(MaHo);
CREATE INDEX IF NOT EXISTS idx_phiBienLai_NgayThu ON phiBienLai(NgayThu);
CREATE INDEX IF NOT EXISTS idx_hokhau_khoanthu_TrangThai ON hokhau_khoanthu(TrangThaiChiTiet);
CREATE INDEX IF NOT EXISTS idx_hokhau_khoanthu_MaHo ON hokhau_khoanthu(MaHo);
CREATE INDEX IF NOT EXISTS idx_hokhau_MaCanHo ON hokhau(MaCanHo);

-- =====================================================
-- 8. SAMPLE DATA (Optional - for testing)
-- =====================================================

-- INSERT INTO canho VALUES ('101', '101', 45, 'Căn hộ tiêu chuẩn');
-- INSERT INTO khoanthu VALUES ('A01', 'Dịch vụ chung', TRUE, TRUE, 5000, 'Dịch vụ hàng tháng');
-- INSERT INTO khoanthu VALUES ('A02', 'Quản lý', TRUE, TRUE, 7000, 'Phí quản lý hàng tháng');
-- INSERT INTO hokhau VALUES ('101A', '101');
-- INSERT INTO nguoidung VALUES ('ADMIN01', 'Trần Tuấn Nam', 'password123', 'Quản Trị', '0912345678', '036212345678');
-- INSERT INTO hokhau_khoanthu VALUES ('101A', 'A01', FALSE, 45, NULL, '2026-05-31', NULL, 0, 0, 225000, 'Chưa nộp');

-- =====================================================
-- END OF SCHEMA
-- =====================================================

