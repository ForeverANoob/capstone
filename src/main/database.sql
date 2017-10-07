-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.2.8-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for assessments
CREATE DATABASE IF NOT EXISTS `assessments` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `assessments`;

-- Dumping structure for table assessments.assessments
CREATE TABLE IF NOT EXISTS `assessments` (
  `ass_id` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `course_code` varchar(50) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  `uploaded` int(11) DEFAULT NULL,
  `published` int(11) DEFAULT NULL,
  `mark_cap` int(10) DEFAULT NULL,
  `calculation` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping database structure for courses
CREATE DATABASE IF NOT EXISTS `courses` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `courses`;

-- Dumping structure for table courses.2016_csc1015f
CREATE TABLE IF NOT EXISTS `2016_csc1015f` (
  `id` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `ass1` int(11) DEFAULT NULL,
  `test1` int(11) DEFAULT NULL,
  `test2` int(11) DEFAULT NULL,
  `classmark` int(11) DEFAULT NULL,
  `final` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table courses.2016_csc1016s
CREATE TABLE IF NOT EXISTS `2016_csc1016s` (
  `id` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `ass1` int(11) DEFAULT NULL,
  `ass2` int(11) DEFAULT NULL,
  `test1` int(11) DEFAULT NULL,
  `test2` int(11) DEFAULT NULL,
  `classmark` int(11) DEFAULT NULL,
  `final` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table courses.2017_csc1015
CREATE TABLE IF NOT EXISTS `2017_csc1015` (
  `id` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `vqvr` int(11) DEFAULT NULL,
  `vqe` int(11) DEFAULT NULL,
  `rtv` int(11) DEFAULT NULL,
  `pie` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table courses.2017_csc1015f
CREATE TABLE IF NOT EXISTS `2017_csc1015f` (
  `id` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `ass1` int(11) DEFAULT NULL,
  `ass2` int(11) DEFAULT NULL,
  `test1` int(11) DEFAULT NULL,
  `test2` int(11) DEFAULT NULL,
  `classmark` int(11) DEFAULT NULL,
  `final` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table courses.2017_csc1016s
CREATE TABLE IF NOT EXISTS `2017_csc1016s` (
  `id` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `ass1` int(11) DEFAULT NULL,
  `ass2` int(11) DEFAULT NULL,
  `test1` int(11) DEFAULT NULL,
  `test2` int(11) DEFAULT NULL,
  `classmark` int(11) DEFAULT NULL,
  `final` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table courses.courses_info
CREATE TABLE IF NOT EXISTS `courses_info` (
  `course_code` varchar(12) DEFAULT NULL,
  `year` int(10) DEFAULT NULL,
  `num_ass` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table courses.example
CREATE TABLE IF NOT EXISTS `example` (
  `id` varchar(50) NOT NULL,
  `ass_1` int(11) DEFAULT NULL,
  `ass_2` int(11) DEFAULT NULL,
  `test_1` int(11) DEFAULT NULL,
  `test_2` int(11) DEFAULT NULL,
  `final` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping database structure for users
CREATE DATABASE IF NOT EXISTS `users` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `users`;

-- Dumping structure for table users.user_courses
CREATE TABLE IF NOT EXISTS `user_courses` (
  `user_id` varchar(10) NOT NULL,
  `course_id` varchar(10) NOT NULL,
  `year` int(5) NOT NULL,
  `role` varchar(12) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
-- Dumping structure for table users.user_info
CREATE TABLE IF NOT EXISTS `user_info` (
  `id` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `role` varchar(10) NOT NULL,
  `degree` varchar(10) DEFAULT NULL,
  `department` varchar(64) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
