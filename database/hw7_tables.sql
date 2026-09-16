-- Script du phong: chi can chay TAY trong SSMS neu Hibernate (ddl-auto=update)
-- khong tu tao duoc 2 bang nay khi chay ung dung lan dau (truong hop nay tung
-- xay ra o cac bai tap truoc, xem course_context).
-- Chay tren database "webst2" da co san.

USE webst2;
GO

IF OBJECT_ID('dbo.HW7Categories', 'U') IS NULL
BEGIN
	CREATE TABLE dbo.HW7Categories (
		categoryId BIGINT IDENTITY(1,1) PRIMARY KEY,
		categoryName NVARCHAR(500) NOT NULL,
		icon NVARCHAR(200) NULL
	);
END
GO

IF OBJECT_ID('dbo.HW7Products', 'U') IS NULL
BEGIN
	CREATE TABLE dbo.HW7Products (
		productId BIGINT IDENTITY(1,1) PRIMARY KEY,
		productName NVARCHAR(500) NOT NULL,
		quantity INT NOT NULL,
		unitPrice FLOAT NOT NULL,
		images NVARCHAR(200) NULL,
		description NVARCHAR(500) NOT NULL,
		discount FLOAT NOT NULL,
		createDate DATETIME NOT NULL,
		status SMALLINT NOT NULL,
		categoryId BIGINT NULL,
		CONSTRAINT FK_HW7Products_Category FOREIGN KEY (categoryId) REFERENCES dbo.HW7Categories(categoryId)
	);
END
GO
