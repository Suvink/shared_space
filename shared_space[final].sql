-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jul 25, 2020 at 06:35 PM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.2.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `shared_space`
--

-- --------------------------------------------------------

--
-- Table structure for table `posts`
--

CREATE TABLE `posts` (
  `post_id` int(11) NOT NULL,
  `author` varchar(30) NOT NULL,
  `title` varchar(255) NOT NULL,
  `body` text NOT NULL,
  `img_url` text NOT NULL,
  `timestamp` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `posts`
--

INSERT INTO `posts` (`post_id`, `author`, `title`, `body`, `img_url`, `timestamp`) VALUES
(502, 'John Doe', 'Hong Kong street at night', 'Whilst I’m a true lover of street food, it is fair to say that the street food of the world knows many different forms. In fact interesting can often trump quality and indeed cleanliness, but not where Macau is concerned, in Macau street food and fine dining are one and the same.Macau street foods are so good that for the first time ever in 2016 the Michelin Food and Dining Guide (yes that Michelin) included 12 street food vendors for the first time ever in 2016, all from Hong Kong and Macao.', 'https://www.abc.net.au/news/image/11535754-3x2-940x627.jpg', 'Saturday, 25-Jul-20 14:17:26 UTC'),
(503, 'iJustine', 'Generate staging app.json for Expo like flavor', 'In some case, we need change app.json variable for staging channel (like bundleIdentifer, icon , etc...)\r\nBut expo hasn\'t flavor function.\r\nI try to generate app.json per publish.\r\nbuild app.json\r\nFirst, we write JSON override config.\r\napp-staging-override.json', 'https://res.cloudinary.com/practicaldev/image/fetch/s--12mRvRHD--/c_imagga_scale,f_auto,fl_progressive,h_420,q_auto,w_1000/https://dev-to-uploads.s3.amazonaws.com/i/hk1eaootda7evq3wpaf3.jpg', 'Jul 25'),
(504, 'Sonny Side', 'Best Street Foods of Macau', 'Whilst I’m a true lover of street food, it is fair to say that the street food of the world knows many different forms. In fact interesting can often trump quality and indeed cleanliness, but not where Macau is concerned, in Macau street food and fine dining are one and the same.Macau street foods are so good that for the first time ever in 2016 the Michelin Food and Dining Guide (yes that Michelin) included 12 street food vendors for the first time ever in 2016, all from Hong Kong and Macao.', 'https://media-cdn.tripadvisor.com/media/photo-s/07/59/da/3e/dong-hua-men-night-market.jpg', '2, May');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(30) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `profile_pic` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `first_name`, `last_name`, `email`, `password`, `profile_pic`) VALUES
(1001, 'Suvin', 'Nimnaka', 'hello@suvink.me', '123@ucsc', 'https://avatars0.githubusercontent.com/u/10671497?s=460&u=2688fb382fbc91bc1874b4e233e6e58191d017c9&v=4'),
(1002, 'Maithreepala', 'Sirisena', 'my3@gmail.com', 'my3', 'https://imagevars.gulfnews.com/2019/06/09/190609-Maithripala-Sirisena_16b3af95e8b_medium.jpg'),
(1006, 'Mahinda', 'Rajapaksha', 'rajapaksha@gmail.com', '123', 'https://cdn.iconscout.com/icon/free/png-512/avatar-370-456322.png'),
(1007, 'fwef', 'wef', 'wef', '123', 'https://cdn.iconscout.com/icon/free/png-512/avatar-370-456322.png');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `posts`
--
ALTER TABLE `posts`
  ADD PRIMARY KEY (`post_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(30) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1008;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
