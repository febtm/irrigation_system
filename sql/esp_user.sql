-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Apr 18, 2017 at 02:48 PM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `a6431137_febulou`
--

-- --------------------------------------------------------

--
-- Table structure for table `esp_user`
--

CREATE TABLE IF NOT EXISTS `esp_user` (
  `entry_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_val` int(11) NOT NULL,
  PRIMARY KEY (`entry_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=35 ;

--
-- Dumping data for table `esp_user`
--

INSERT INTO `esp_user` (`entry_id`, `user_val`) VALUES
(1, 2412),
(2, 2512),
(3, 2412),
(4, 2512),
(5, 2412),
(6, 2412),
(7, 2412),
(8, 2512),
(9, 2412),
(10, 2512),
(11, 2412),
(12, 2412),
(13, 2512),
(14, 2412),
(15, 2512),
(16, 2412),
(17, 2412),
(18, 2412),
(19, 2412),
(20, 2512),
(21, 2412),
(22, 2512),
(23, 2412),
(24, 2512),
(25, 2512),
(26, 2412),
(27, 2512),
(28, 2412),
(29, 2512),
(30, 2412),
(31, 2512),
(32, 2412),
(33, 2412),
(34, 2412);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
