-- ============================================================
-- BLUEMOON APARTMENT MANAGEMENT — Triggers MSSQL
-- ============================================================
-- File này chứa các trigger bổ sung cho schema
-- ============================================================

USE CNPM;
GO

-- ================================================================
-- SECTION 1: SOFT-DELETE TRIGGERS (cho các bảng còn thiếu)
-- ================================================================

-- 1.1 TRIGGER: Soft-delete cho bảng APARTMENT
-- Khi xóa apartment, đặt delete_at = SYSDATETIMEOFFSET()
CREATE OR ALTER TRIGGER trg_soft_delete_apartment
ON dbo.apartment
INSTEAD OF DELETE
AS
BEGIN
    SET NOCOUNT ON;
UPDATE dbo.apartment
SET    delete_at = SYSDATETIMEOFFSET()
WHERE  apart_id IN (SELECT apart_id FROM DELETED);

PRINT 'Soft-deleted ' + CAST(@@ROWCOUNT AS NVARCHAR(10)) + ' apartment(s)';
END;
GO

-- ================================================================
-- SECTION 2: VALIDATION & BUSINESS LOGIC TRIGGERS
-- ================================================================

-- 2.1 TRIGGER: Kiểm tra pay_deadline phải lớn hơn ngày hôm nay khi INSERT/UPDATE
CREATE OR ALTER TRIGGER trg_validate_house_recei_deadline
ON dbo.house_recei
AFTER INSERT, UPDATE
                                  AS
BEGIN
    SET NOCOUNT ON;

    IF EXISTS (
        SELECT 1 FROM INSERTED
        WHERE CAST(pay_deadline AS DATE) < CAST(SYSDATETIMEOFFSET() AS DATE)
    )
BEGIN
        RAISERROR('Lỗi: Ngày thanh toán không thể là ngày trong quá khứ!', 16, 1);
ROLLBACK TRANSACTION;
RETURN;
END
END;
GO

-- 2.2 TRIGGER: Kiểm tra status payment - nếu status = 1 thì pay_date phải được set
CREATE OR ALTER TRIGGER trg_validate_house_recei_payment
ON dbo.house_recei
AFTER INSERT, UPDATE
                                  AS
BEGIN
    SET NOCOUNT ON;

    IF EXISTS (
        SELECT 1 FROM INSERTED
        WHERE status = 1 AND pay_date IS NULL
    )
BEGIN
        RAISERROR('Lỗi: Khi status=1 (đã thanh toán), pay_date phải được xác định!', 16, 1);
ROLLBACK TRANSACTION;
RETURN;
END
END;
GO

-- 2.3 TRIGGER: Kiểm tra apartment area > 0
CREATE OR ALTER TRIGGER trg_validate_apartment_area
ON dbo.apartment
AFTER INSERT, UPDATE
                                  AS
BEGIN
    SET NOCOUNT ON;

    IF EXISTS (
        SELECT 1 FROM INSERTED
        WHERE area <= 0
    )
BEGIN
        RAISERROR('Lỗi: Diện tích apartment phải lớn hơn 0!', 16, 1);
ROLLBACK TRANSACTION;
RETURN;
END
END;
GO

-- 2.4 TRIGGER: Kiểm tra receivable price >= 0
CREATE OR ALTER TRIGGER trg_validate_receivable_price
ON dbo.receivable
AFTER INSERT, UPDATE
                                  AS
BEGIN
    SET NOCOUNT ON;

    IF EXISTS (
        SELECT 1 FROM INSERTED
        WHERE price < 0
    )
BEGIN
        RAISERROR('Lỗi: Giá tiền phải >= 0!', 16, 1);
ROLLBACK TRANSACTION;
RETURN;
END
END;
GO

-- ================================================================
-- SECTION 3: AUDIT & TRACKING TRIGGERS
-- ================================================================

-- 3.1 Tạo bảng AUDIT log (nếu chưa có)
IF OBJECT_ID('dbo.audit_log', 'U') IS NULL
CREATE TABLE dbo.audit_log (
                               audit_id       BIGINT IDENTITY(1,1) PRIMARY KEY,
                               table_name     NVARCHAR(128) NOT NULL,
                               operation      NVARCHAR(10) NOT NULL,    -- INSERT, UPDATE, DELETE
                               old_values     NVARCHAR(MAX),             -- JSON format
                               new_values     NVARCHAR(MAX),             -- JSON format
                               changed_by     NVARCHAR(128),             -- USER_NAME()
                               changed_at     DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET(),
                               record_id      NVARCHAR(20)               -- Primary key value
);
GO

-- 3.2 TRIGGER: Audit log cho bảng receivable
CREATE OR ALTER TRIGGER trg_audit_receivable
ON dbo.receivable
AFTER INSERT, UPDATE, DELETE
    AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @operation NVARCHAR(10);

    IF EXISTS (SELECT 1 FROM INSERTED) AND NOT EXISTS (SELECT 1 FROM DELETED)
        SET @operation = 'INSERT';
ELSE IF EXISTS (SELECT 1 FROM INSERTED) AND EXISTS (SELECT 1 FROM DELETED)
        SET @operation = 'UPDATE';
ELSE
        SET @operation = 'DELETE';

INSERT INTO dbo.audit_log (table_name, operation, new_values, old_values, changed_by, record_id)
SELECT
    'receivable',
    @operation,
    CASE WHEN i.recei_id IS NOT NULL THEN
             '{"recei_id":"' + ISNULL(i.recei_id, '') +
             '","recei_name":"' + ISNULL(i.recei_name, '') +
             '","price":' + CAST(ISNULL(i.price, 0) AS NVARCHAR(20)) + '}'
        END,
    CASE WHEN d.recei_id IS NOT NULL THEN
             '{"recei_id":"' + ISNULL(d.recei_id, '') +
             '","recei_name":"' + ISNULL(d.recei_name, '') +
             '","price":' + CAST(ISNULL(d.price, 0) AS NVARCHAR(20)) + '}'
        END,
    SYSTEM_USER,
    ISNULL(i.recei_id, d.recei_id)
FROM INSERTED i
         FULL OUTER JOIN DELETED d ON i.recei_id = d.recei_id;
END;
GO

-- 3.3 TRIGGER: Audit log cho bảng house_recei
CREATE OR ALTER TRIGGER trg_audit_house_recei
ON dbo.house_recei
AFTER INSERT, UPDATE, DELETE
    AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @operation NVARCHAR(10);

    IF EXISTS (SELECT 1 FROM INSERTED) AND NOT EXISTS (SELECT 1 FROM DELETED)
        SET @operation = 'INSERT';
ELSE IF EXISTS (SELECT 1 FROM INSERTED) AND EXISTS (SELECT 1 FROM DELETED)
        SET @operation = 'UPDATE';
ELSE
        SET @operation = 'DELETE';

INSERT INTO dbo.audit_log (table_name, operation, new_values, old_values, changed_by, record_id)
SELECT
    'house_recei',
    @operation,
    CASE WHEN i.house_id IS NOT NULL THEN
             '{"house_id":"' + ISNULL(i.house_id, '') +
             '","recei_id":"' + ISNULL(i.recei_id, '') +
             '","status":' + CAST(i.status AS NVARCHAR(1)) +
             ',"quantity":' + CAST(i.quantity AS NVARCHAR(20)) + '}'
        END,
    CASE WHEN d.house_id IS NOT NULL THEN
             '{"house_id":"' + ISNULL(d.house_id, '') +
             '","recei_id":"' + ISNULL(d.recei_id, '') +
             '","status":' + CAST(d.status AS NVARCHAR(1)) +
             ',"quantity":' + CAST(d.quantity AS NVARCHAR(20)) + '}'
        END,
    SYSTEM_USER,
    ISNULL(i.house_id, d.house_id)
FROM INSERTED i
         FULL OUTER JOIN DELETED d ON i.house_id = d.house_id AND i.recei_id = d.recei_id;
END;
GO

-- ================================================================
-- SECTION 4: REFERENTIAL INTEGRITY & CASCADE TRIGGERS
-- ================================================================

-- 4.1 TRIGGER: Khi house_reg bị soft-delete, xóa mềm các receivable liên quan
CREATE OR ALTER TRIGGER trg_cascade_delete_house_reg
ON dbo.house_reg
AFTER UPDATE
                          AS
BEGIN
    SET NOCOUNT ON;

    -- Nếu delete_at được set (soft-delete)
    IF UPDATE(delete_at)
BEGIN
UPDATE dbo.house_recei
SET delete_at = SYSDATETIMEOFFSET()
WHERE house_id IN (SELECT house_id FROM INSERTED WHERE delete_at IS NOT NULL)
  AND delete_at IS NULL;

UPDATE dbo.resident_house
SET delete_at = SYSDATETIMEOFFSET()
WHERE house_id IN (SELECT house_id FROM INSERTED WHERE delete_at IS NOT NULL)
  AND CAST(DELETE_at AS NVARCHAR(1)) = ''; -- Nếu bảng có delete_at
END
END;
GO

-- 4.2 TRIGGER: Kiểm tra resident tối thiểu 1 người ở mỗi house
CREATE OR ALTER TRIGGER trg_validate_house_has_resident
ON dbo.resident_house
AFTER DELETE
AS
BEGIN
    SET NOCOUNT ON;

    IF EXISTS (
        SELECT 1 FROM dbo.house_reg hr
        WHERE NOT EXISTS (
            SELECT 1 FROM dbo.resident_house rh
            WHERE rh.house_id = hr.house_id
        )
    )
BEGIN
        RAISERROR('Lỗi: Mỗi house phải có ít nhất 1 resident!', 16, 1);
ROLLBACK TRANSACTION;
RETURN;
END
END;
GO

-- ================================================================
-- SECTION 5: UPDATE TIMESTAMP TRIGGERS
-- ================================================================

-- 5.1 Thêm cột updated_at vào các bảng (nếu cần)
-- ALTER TABLE dbo.receivable ADD updated_at DATETIMEOFFSET;
-- GO

-- 5.2 TRIGGER: Auto-update updated_at khi thay đổi receivable
CREATE OR ALTER TRIGGER trg_update_timestamp_receivable
ON dbo.receivable
AFTER UPDATE
                          AS
BEGIN
    SET NOCOUNT ON;

    -- Có thể thêm logic cập nhật updated_at nếu cột tồn tại
    -- UPDATE dbo.receivable SET updated_at = SYSDATETIMEOFFSET() WHERE recei_id IN (SELECT recei_id FROM INSERTED);
END;
GO

-- ================================================================
-- SECTION 6: PREVENT DELETE TRIGGERS (Tùy chọn)
-- ================================================================

-- 6.1 TRIGGER: Ngăn xóa cứng (hard-delete) các bảng có soft-delete
-- Chú thích: Các trigger soft-delete ở trên đã INSTEAD OF DELETE
-- nên trigger này là thêm một lớp bảo vệ

CREATE OR ALTER TRIGGER trg_prevent_hard_delete_resident
ON dbo.resident
INSTEAD OF DELETE
AS
BEGIN
    SET NOCOUNT ON;

    -- Ghi thông báo mềm mà không rollback (tùy theo yêu cầu)
    PRINT 'Thông báo: Hãy sử dụng soft-delete (update delete_at) thay vì delete trực tiếp.';

    -- Thực hiện soft-delete
UPDATE dbo.resident
SET    delete_at = SYSDATETIMEOFFSET()
WHERE  resident_id IN (SELECT resident_id FROM DELETED);
END;
GO

-- ================================================================
-- SECTION 7: PAYMENT STATUS TRIGGERS
-- ================================================================

-- 7.1 TRIGGER: Tự động set pay_date = SYSDATETIMEOFFSET() khi status = 1
CREATE OR ALTER TRIGGER trg_auto_pay_date
ON dbo.house_recei
AFTER UPDATE
                          AS
BEGIN
    SET NOCOUNT ON;

    IF UPDATE(status)
BEGIN
UPDATE dbo.house_recei
SET pay_date = SYSDATETIMEOFFSET()
WHERE (house_id, recei_id) IN (
    SELECT house_id, recei_id FROM INSERTED WHERE status = 1
)
  AND pay_date IS NULL;
END
END;
GO

-- ================================================================
-- SECTION 8: VALIDATION TRIGGER - Số điện thoại resident
-- ================================================================

-- 8.1 TRIGGER: Kiểm tra format số điện thoại
CREATE OR ALTER TRIGGER trg_validate_resident_phone
ON dbo.resident
AFTER INSERT, UPDATE
                                  AS
BEGIN
    SET NOCOUNT ON;

    IF EXISTS (
        SELECT 1 FROM INSERTED
        WHERE telephone IS NOT NULL
          AND (
              LEN(REPLACE(REPLACE(telephone, ' ', ''), '+', '')) < 9
              OR telephone NOT LIKE '%[0-9]%'
          )
    )
BEGIN
        RAISERROR('Lỗi: Số điện thoại không hợp lệ (tối thiểu 9 chữ số)!', 16, 1);
ROLLBACK TRANSACTION;
RETURN;
END
END;
GO

-- ================================================================
-- SECTION 9: UTILITY TRIGGERS - Master User Check
-- ================================================================

-- 9.1 TRIGGER: Ensure mỗi house có ít nhất 1 master resident
CREATE OR ALTER TRIGGER trg_validate_house_master
ON dbo.resident_house
AFTER DELETE, UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    IF UPDATE(isMaster) OR EXISTS (SELECT 1 FROM DELETED)
BEGIN
        IF EXISTS (
            SELECT 1 FROM dbo.house_reg hr
            WHERE NOT EXISTS (
                SELECT 1 FROM dbo.resident_house rh
                WHERE rh.house_id = hr.house_id AND rh.isMaster = 1
            )
            AND hr.delete_at IS NULL
        )
BEGIN
            RAISERROR('Lỗi: Mỗi house phải có ít nhất 1 master resident!', 16, 1);
ROLLBACK TRANSACTION;
RETURN;
END
END
END;
GO

-- ================================================================
-- DISABLE/ENABLE TRIGGERS (for maintenance)
-- ================================================================

/*
-- Tắt tất cả trigger trên bảng house_recei
DISABLE TRIGGER ALL ON dbo.house_recei;

-- Bật lại trigger
ENABLE TRIGGER ALL ON dbo.house_recei;

-- Xem trigger thông tin
SELECT name, object_id, parent_id, type_desc
FROM sys.triggers
WHERE database_id = DB_ID();

-- Xem trigger của bảng cụ thể
SELECT name FROM sys.triggers
WHERE parent_id = OBJECT_ID('dbo.house_recei');
*/

-- ================================================================
-- END OF TRIGGERS
-- ================================================================
PRINT '✓ Tất cả triggers đã được tạo thành công!';
GO