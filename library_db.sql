-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3308
-- Generation Time: Aug 24, 2024 at 06:32 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `library_db`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_borrowings_by_user_id` (IN `userID` INT)   BEGIN
    DELETE FROM borrowings WHERE userID = userID;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_user_by_id` (IN `userID` INT)   BEGIN
    DELETE FROM users WHERE id = userId;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `get_all_users` ()   BEGIN
    SELECT * FROM users;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `books`
--

CREATE TABLE `books` (
  `id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `author` varchar(255) NOT NULL,
  `genre` varchar(255) NOT NULL,
  `copies` int(11) NOT NULL,
  `availability` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `books`
--

INSERT INTO `books` (`id`, `title`, `author`, `genre`, `copies`, `availability`) VALUES
(1, 'The White Tiger', 'Aravind Adiga', 'Fiction', 5, 1),
(2, 'Midnight’s Children', 'Salman Rushdie', 'Historical Fiction', 3, 1),
(3, 'The God of Small Things', 'Arundhati Roy', 'Drama', 4, 1),
(4, 'Pride and Prejudice', 'Jane Austen', 'Classic', 2, 1),
(5, 'One Hundred Years of Solitude', 'Gabriel García Márquez', 'Magical Realism', 1, 1),
(6, 'The Catcher in the Rye', 'J.D. Salinger', 'Fiction', 6, 1),
(7, 'Rich Dad Poor Dad', 'Robert Kiyosaki', 'finance', 10, 1);

-- --------------------------------------------------------

--
-- Table structure for table `borrowings`
--

CREATE TABLE `borrowings` (
  `userID` int(11) NOT NULL,
  `bookID` int(11) NOT NULL,
  `borrowDate` varchar(255) NOT NULL,
  `dueDate` varchar(255) NOT NULL,
  `returned` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `borrowings`
--

INSERT INTO `borrowings` (`userID`, `bookID`, `borrowDate`, `dueDate`, `returned`) VALUES
(1, 7, '2024-08-24', '2024-08-31', 0);

-- --------------------------------------------------------

--
-- Table structure for table `reservations`
--

CREATE TABLE `reservations` (
  `userID` int(11) NOT NULL,
  `bookID` int(11) NOT NULL,
  `reservationDate` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `reservations`
--

INSERT INTO `reservations` (`userID`, `bookID`, `reservationDate`) VALUES
(2, 1, '2024-08-10'),
(6, 5, '2024-08-15'),
(1, 6, '2024-08-23');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `mobile` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `password`, `mobile`, `role`) VALUES
(1, 'Arjun Patel', 'arjun.patel@example.com', 'password123', '987-654-3210', 'member'),
(2, 'Priya Sharma', 'priya.sharma@example.com', 'password456', '912-345-6789', 'member'),
(3, 'Sita Rani', 'sita.rani@example.com', 'password789', '998-877-6655', 'admin'),
(4, 'John Smith', 'john.smith@example.com', 'password123', '123-456-7890', 'member'),
(6, 'Yuki Tanaka', 'yuki.tanaka@example.com', 'password789', '555-666-7777', 'admin');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `books`
--
ALTER TABLE `books`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `borrowings`
--
ALTER TABLE `borrowings`
  ADD KEY `userID` (`userID`),
  ADD KEY `bookID` (`bookID`);

--
-- Indexes for table `reservations`
--
ALTER TABLE `reservations`
  ADD KEY `userID` (`userID`),
  ADD KEY `bookID` (`bookID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `books`
--
ALTER TABLE `books`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `borrowings`
--
ALTER TABLE `borrowings`
  ADD CONSTRAINT `borrowings_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `borrowings_ibfk_2` FOREIGN KEY (`bookID`) REFERENCES `books` (`id`);

--
-- Constraints for table `reservations`
--
ALTER TABLE `reservations`
  ADD CONSTRAINT `reservations_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `reservations_ibfk_2` FOREIGN KEY (`bookID`) REFERENCES `books` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
