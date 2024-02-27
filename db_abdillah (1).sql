-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 27, 2024 at 05:17 PM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_abdillah`
--

-- --------------------------------------------------------

--
-- Table structure for table `detailpenjualan`
--

CREATE TABLE `detailpenjualan` (
  `tanggal_transaksi` date NOT NULL,
  `id_detail` int(11) NOT NULL,
  `id_produk` int(11) NOT NULL,
  `id_penjualan` int(11) NOT NULL,
  `nama_pelanggan` varchar(255) NOT NULL,
  `nama_produk` varchar(255) NOT NULL,
  `jml_produk` int(11) NOT NULL,
  `subtotal` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `detailpenjualan`
--

INSERT INTO `detailpenjualan` (`tanggal_transaksi`, `id_detail`, `id_produk`, `id_penjualan`, `nama_pelanggan`, `nama_produk`, `jml_produk`, `subtotal`) VALUES
('2024-02-27', 10001, 1, 10001, 'abi', 'Mie', 3, '30000.00');

--
-- Triggers `detailpenjualan`
--
DELIMITER $$
CREATE TRIGGER `TambahTotal` AFTER INSERT ON `detailpenjualan` FOR EACH ROW update penjualan set total = total + new.subtotal where id_penjualan=new.id_penjualan
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `kurangTotal` AFTER DELETE ON `detailpenjualan` FOR EACH ROW update penjualan set total = total - old.subtotal where old.id_penjualan
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `kurangstok` AFTER INSERT ON `detailpenjualan` FOR EACH ROW update produk set stok = stok - new.jml_produk where id_produk=new.id_produk
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `tambahstok` AFTER DELETE ON `detailpenjualan` FOR EACH ROW update produk set stok = stok + old.jml_produk where id_produk=old.id_produk
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `pelanggan`
--

CREATE TABLE `pelanggan` (
  `id_pelanggan` int(11) NOT NULL,
  `nama_pelanggan` varchar(255) NOT NULL,
  `alamat` text NOT NULL,
  `nomor` varchar(13) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `pelanggan`
--

INSERT INTO `pelanggan` (`id_pelanggan`, `nama_pelanggan`, `alamat`, `nomor`) VALUES
(1, 'Abrilah', 'Kuningan', '0897575858474');

-- --------------------------------------------------------

--
-- Table structure for table `penjualan`
--

CREATE TABLE `penjualan` (
  `id_penjualan` int(11) NOT NULL,
  `tanggal_penjualan` date NOT NULL,
  `total` decimal(10,2) NOT NULL,
  `id_pelanggan` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `produk`
--

CREATE TABLE `produk` (
  `id_produk` int(11) NOT NULL,
  `nama_produk` varchar(255) NOT NULL,
  `harga` decimal(10,2) NOT NULL,
  `stok` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `produk`
--

INSERT INTO `produk` (`id_produk`, `nama_produk`, `harga`, `stok`) VALUES
(1, 'Mie', '10000.00', 22),
(2, 'Aqua', '1000.00', 10);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `username` varchar(110) NOT NULL,
  `password` varchar(150) NOT NULL,
  `level` enum('Admin','Kasir') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`username`, `password`, `level`) VALUES
('abdillah', '123', 'Admin'),
('Abi', '81dc9bdb52d04dc20036dbd8313ed055', 'Admin'),
('kay', '123', 'Kasir'),
('putri', '123', 'Admin'),
('Yarra', '202cb962ac59075b964b07152d234b70', 'Kasir');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `detailpenjualan`
--
ALTER TABLE `detailpenjualan`
  ADD PRIMARY KEY (`id_detail`),
  ADD KEY `id_produk` (`id_produk`),
  ADD KEY `id_penjualan` (`id_penjualan`),
  ADD KEY `nama_pelanggan` (`nama_pelanggan`),
  ADD KEY `nama_produk` (`nama_produk`);

--
-- Indexes for table `pelanggan`
--
ALTER TABLE `pelanggan`
  ADD PRIMARY KEY (`id_pelanggan`),
  ADD UNIQUE KEY `nomor` (`nomor`),
  ADD KEY `nama` (`nama_pelanggan`);

--
-- Indexes for table `penjualan`
--
ALTER TABLE `penjualan`
  ADD PRIMARY KEY (`id_penjualan`),
  ADD KEY `id_pelanggan` (`id_pelanggan`);

--
-- Indexes for table `produk`
--
ALTER TABLE `produk`
  ADD PRIMARY KEY (`id_produk`),
  ADD UNIQUE KEY `nama_produk` (`nama_produk`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`username`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `detailpenjualan`
--
ALTER TABLE `detailpenjualan`
  ADD CONSTRAINT `detailpenjualan_ibfk_1` FOREIGN KEY (`id_produk`) REFERENCES `produk` (`id_produk`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `penjualan`
--
ALTER TABLE `penjualan`
  ADD CONSTRAINT `penjualan_ibfk_1` FOREIGN KEY (`id_pelanggan`) REFERENCES `pelanggan` (`id_pelanggan`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
