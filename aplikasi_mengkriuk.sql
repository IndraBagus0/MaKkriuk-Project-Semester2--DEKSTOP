-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 18, 2022 at 01:00 AM
-- Server version: 10.4.17-MariaDB
-- PHP Version: 8.0.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `aplikasi_mengkriuk`
--

-- --------------------------------------------------------

--
-- Table structure for table `barang`
--

CREATE TABLE `barang` (
  `kode_dan_barcode` char(30) NOT NULL,
  `nama_barang` varchar(50) NOT NULL,
  `id_satuan` int(11) NOT NULL,
  `id_kategori` int(11) NOT NULL,
  `harga_biasa` int(11) NOT NULL,
  `harga_jual` int(11) NOT NULL,
  `laba` int(11) NOT NULL,
  `harga_grosir` int(11) NOT NULL,
  `minimal_beli_grosir` int(11) NOT NULL,
  `diskon_jual` int(11) NOT NULL,
  `minimal_beliDiskon_jual` int(11) NOT NULL,
  `stok` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `barang`
--

INSERT INTO `barang` (`kode_dan_barcode`, `nama_barang`, `id_satuan`, `id_kategori`, `harga_biasa`, `harga_jual`, `laba`, `harga_grosir`, `minimal_beli_grosir`, `diskon_jual`, `minimal_beliDiskon_jual`, `stok`) VALUES
('8991389241561', 'kripik manis', 2, 1, 5000, 6000, 0, 5500, 10, 0, 0, 60),
('8995227500261', 'kripik tempe pedas', 1, 2, 3000, 4000, 0, 3500, 10, 10, 5, 70);

-- --------------------------------------------------------

--
-- Table structure for table `data_kategori`
--

CREATE TABLE `data_kategori` (
  `id_kategori` int(11) NOT NULL,
  `nama_kategori` varchar(20) NOT NULL,
  `keterangan` varchar(25) NOT NULL,
  `status` char(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `data_kategori`
--

INSERT INTO `data_kategori` (`id_kategori`, `nama_kategori`, `keterangan`, `status`) VALUES
(1, 'Manis', 'pesanya manis', 'Aktive'),
(2, 'Pedas', 'Rasa pedas', 'Aktive');

-- --------------------------------------------------------

--
-- Table structure for table `data_satuan`
--

CREATE TABLE `data_satuan` (
  `id_satuan` int(11) NOT NULL,
  `nama_satuan` varchar(20) NOT NULL,
  `keterangan` varchar(35) NOT NULL,
  `status` char(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `data_satuan`
--

INSERT INTO `data_satuan` (`id_satuan`, `nama_satuan`, `keterangan`, `status`) VALUES
(1, '100gr', 'dalam kemasan 100gr', 'Aktive'),
(2, '200gr', 'kemasan 200gr', 'Aktive');

-- --------------------------------------------------------

--
-- Table structure for table `data_user`
--

CREATE TABLE `data_user` (
  `id_user` int(5) NOT NULL,
  `nama_user` varchar(30) NOT NULL,
  `username` varchar(30) NOT NULL,
  `password` varchar(20) NOT NULL,
  `no_hp` int(13) NOT NULL,
  `level` char(10) NOT NULL,
  `status` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `data_user`
--

INSERT INTO `data_user` (`id_user`, `nama_user`, `username`, `password`, `no_hp`, `level`, `status`) VALUES
(1, 'Muhammad Rhomaedi', 'rhomaedi', '12345', 851235678, 'Admin', 'Aktive');

-- --------------------------------------------------------

--
-- Table structure for table `detail_transaksi_penjualan`
--

CREATE TABLE `detail_transaksi_penjualan` (
  `no_transaksi` char(25) NOT NULL,
  `kode_dan_barcode` char(30) NOT NULL,
  `harga_biasa` int(11) NOT NULL DEFAULT 0,
  `harga_jual` int(10) NOT NULL DEFAULT 0,
  `harga_grosir` int(11) NOT NULL DEFAULT 0,
  `qty` int(10) NOT NULL DEFAULT 0,
  `diskon_persen` int(10) NOT NULL DEFAULT 0,
  `diskon_rupiah` int(10) NOT NULL DEFAULT 0,
  `total_jual` int(10) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Triggers `detail_transaksi_penjualan`
--
DELIMITER $$
CREATE TRIGGER `Delete_stok_barang` AFTER DELETE ON `detail_transaksi_penjualan` FOR EACH ROW BEGIN
	UPDATE barang SET stok = stok+old.qty WHERE kode_dan_barcode = old.kode_dan_barcode; 
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `kurang_stok_barang` AFTER INSERT ON `detail_transaksi_penjualan` FOR EACH ROW BEGIN
	UPDATE barang SET stok = stok-new.qty WHERE kode_dan_barcode = new.kode_dan_barcode;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `update_stok_barang` AFTER UPDATE ON `detail_transaksi_penjualan` FOR EACH ROW BEGIN
	UPDATE barang SET stok = (stok+old.qty)-new.qty WHERE kode_dan_barcode = old.kode_dan_barcode;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `pelanggan`
--

CREATE TABLE `pelanggan` (
  `kode_pelanggan` char(10) NOT NULL,
  `nama_pelanggan` varchar(40) NOT NULL,
  `alamat` varchar(35) NOT NULL,
  `no_hp` char(13) NOT NULL,
  `email` varchar(25) NOT NULL,
  `status` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `pelanggan`
--

INSERT INTO `pelanggan` (`kode_pelanggan`, `nama_pelanggan`, `alamat`, `no_hp`, `email`, `status`) VALUES
('PLG-01', 'Zidan', 'kembang', '085567345234', '', 'Aktive');

-- --------------------------------------------------------

--
-- Table structure for table `transaksi_penjualan`
--

CREATE TABLE `transaksi_penjualan` (
  `no_transaksi` char(25) NOT NULL,
  `tanggal` datetime NOT NULL,
  `kode_pelanggan` char(10) DEFAULT NULL,
  `id_user` int(5) DEFAULT NULL,
  `total_diskon_transaksi` int(11) NOT NULL DEFAULT 0,
  `diskon_persen_pembayaran` int(10) NOT NULL DEFAULT 0,
  `total_diskon_rupiah_pembayaran` int(10) NOT NULL DEFAULT 0,
  `total_seluruh` int(11) NOT NULL DEFAULT 0,
  `bayar` int(10) NOT NULL DEFAULT 0,
  `kembalian` int(10) NOT NULL DEFAULT 0,
  `metode` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `barang`
--
ALTER TABLE `barang`
  ADD PRIMARY KEY (`kode_dan_barcode`),
  ADD UNIQUE KEY `kode_dan_barcode` (`kode_dan_barcode`) USING BTREE,
  ADD KEY `id_satuan` (`id_satuan`),
  ADD KEY `id_kategori` (`id_kategori`);

--
-- Indexes for table `data_kategori`
--
ALTER TABLE `data_kategori`
  ADD PRIMARY KEY (`id_kategori`);

--
-- Indexes for table `data_satuan`
--
ALTER TABLE `data_satuan`
  ADD PRIMARY KEY (`id_satuan`);

--
-- Indexes for table `data_user`
--
ALTER TABLE `data_user`
  ADD PRIMARY KEY (`id_user`);

--
-- Indexes for table `detail_transaksi_penjualan`
--
ALTER TABLE `detail_transaksi_penjualan`
  ADD KEY `no_transaksi` (`no_transaksi`),
  ADD KEY `kode_dan_barcode` (`kode_dan_barcode`);

--
-- Indexes for table `pelanggan`
--
ALTER TABLE `pelanggan`
  ADD PRIMARY KEY (`kode_pelanggan`);

--
-- Indexes for table `transaksi_penjualan`
--
ALTER TABLE `transaksi_penjualan`
  ADD PRIMARY KEY (`no_transaksi`),
  ADD KEY `kode_pelanggan` (`kode_pelanggan`),
  ADD KEY `id_user` (`id_user`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `data_kategori`
--
ALTER TABLE `data_kategori`
  MODIFY `id_kategori` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `data_satuan`
--
ALTER TABLE `data_satuan`
  MODIFY `id_satuan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `data_user`
--
ALTER TABLE `data_user`
  MODIFY `id_user` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `barang`
--
ALTER TABLE `barang`
  ADD CONSTRAINT `barang_ibfk_1` FOREIGN KEY (`id_satuan`) REFERENCES `data_satuan` (`id_satuan`),
  ADD CONSTRAINT `barang_ibfk_2` FOREIGN KEY (`id_kategori`) REFERENCES `data_kategori` (`id_kategori`);

--
-- Constraints for table `detail_transaksi_penjualan`
--
ALTER TABLE `detail_transaksi_penjualan`
  ADD CONSTRAINT `detail_transaksi_penjualan_ibfk_4` FOREIGN KEY (`no_transaksi`) REFERENCES `transaksi_penjualan` (`no_transaksi`),
  ADD CONSTRAINT `detail_transaksi_penjualan_ibfk_5` FOREIGN KEY (`kode_dan_barcode`) REFERENCES `barang` (`kode_dan_barcode`);

--
-- Constraints for table `transaksi_penjualan`
--
ALTER TABLE `transaksi_penjualan`
  ADD CONSTRAINT `transaksi_penjualan_ibfk_1` FOREIGN KEY (`kode_pelanggan`) REFERENCES `pelanggan` (`kode_pelanggan`),
  ADD CONSTRAINT `transaksi_penjualan_ibfk_2` FOREIGN KEY (`id_user`) REFERENCES `data_user` (`id_user`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
