-- phpMyAdmin SQL Dump
-- version 3.5.1
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le: Mar 25 Mars 2014 à 16:46
-- Version du serveur: 5.5.25
-- Version de PHP: 5.4.4

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Base de données: `tp5_java`
--

-- --------------------------------------------------------

--
-- Structure de la table `movies`
--

CREATE TABLE `movies` (
  `name` varchar(50) NOT NULL,
  `places` int(2) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Contenu de la table `movies`
--

INSERT INTO `movies` (`name`, `places`, `id`) VALUES
('transformes', 30, 1),
('Avengers', 10, 2);

-- --------------------------------------------------------

--
-- Structure de la table `reservations`
--

CREATE TABLE `reservations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `movieId` varchar(50) NOT NULL,
  `name` varchar(255) NOT NULL,
  `places` int(11) NOT NULL,
  `reservationId` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=27 ;

--
-- Contenu de la table `reservations`
--

INSERT INTO `reservations` (`id`, `movieId`, `name`, `places`, `reservationId`) VALUES
(1, '2', 'Thomas', 2, 37400),
(2, '2', 'Thomas', 2, 36169),
(3, '2', 'Jess', 4, 42129),
(4, '1', 'Jess', 1, 56996),
(5, '1', 'Jess', 20, 1515),
(6, '1', 'Jessica', 25, 6681),
(7, '2', 'Thomas', 2, 78096),
(8, '1', 'jTextField1', 3, 60290),
(9, '2', 'Thomas', 12, 12057),
(10, '1', 'Thomas', 16, 61927),
(11, '2', 'Jess', 2, 62974),
(12, '2', 'jTextField1', 1, 75471),
(13, '2', 'jTextField1', 1, 61419),
(14, '1', 'Thomas', 24, 87967),
(15, '2', 'Thomas', 3, 93687),
(16, '2', 'Tom', 5, 67851),
(17, '1', 'Louis', 5, 35597),
(18, '1', 'Louis', 1, 1213),
(19, '2', 'Louis', 10, 38419),
(20, '2', 'Toto', 4, 76962),
(21, '1', 'Jess', 16, 2000),
(22, '1', 'Jess', 2, 30140),
(23, '2', 'Jess', 16, 50523),
(24, '1', 'Jess', 2, 75920),
(25, '2', 'Thomas', 9, 17091),
(26, '2', 'Thomas', 1, 56749);
