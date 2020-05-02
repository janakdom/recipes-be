-- Categories.
INSERT INTO `category` (`name`) VALUES ('Hlavní chod');
INSERT INTO `category` (`name`) VALUES ('Polévka');
INSERT INTO `category` (`name`) VALUES ('Předkrm');
INSERT INTO `category` (`name`) VALUES ('Dezert');
INSERT INTO `category` (`name`) VALUES ('Večeře');
INSERT INTO `category` (`name`) VALUES ('Snídaně');

-- Ingredients.
INSERT INTO `ingredient` (`name`) VALUES ('Maso');
INSERT INTO `ingredient` (`name`) VALUES ('Sůl');
INSERT INTO `ingredient` (`name`) VALUES ('Hladká mouka');
INSERT INTO `ingredient` (`name`) VALUES ('Polohrubá mouka');
INSERT INTO `ingredient` (`name`) VALUES ('Paprika');
INSERT INTO `ingredient` (`name`) VALUES ('Kukuřice');
INSERT INTO `ingredient` (`name`) VALUES ('Hořčice');
INSERT INTO `ingredient` (`name`) VALUES ('Kvasnice');
INSERT INTO `ingredient` (`name`) VALUES ('Cukr krupice');
INSERT INTO `ingredient` (`name`) VALUES ('Pepř');
INSERT INTO `ingredient` (`name`) VALUES ('Kypřící prášek');
INSERT INTO `ingredient` (`name`) VALUES ('Tymián');
INSERT INTO `ingredient` (`name`) VALUES ('Rozmarýn');
INSERT INTO `ingredient` (`name`) VALUES ('Oregáno');
INSERT INTO `ingredient` (`name`) VALUES ('Sýr');

-- Users.
INSERT INTO `user` (`id`, `username`, `password`, `display_name`) VALUES ('1', 'test', '$2y$12$e7IMUyDEHK53m6wmT9z7Hu84jN.M4IdFEs751tKooPPWlWDt.CAsO', 'Testing User');
INSERT INTO `user` (`id`, `username`, `password`, `display_name`) VALUES ('2', 'tomas', '$2y$12$e7IMUyDEHK53m6wmT9z7Hu84jN.M4IdFEs751tKooPPWlWDt.CAsO', 'Tomáš Křičenský');
INSERT INTO `user` (`id`, `username`, `password`, `display_name`) VALUES ('3', 'eva', '$2y$12$e7IMUyDEHK53m6wmT9z7Hu84jN.M4IdFEs751tKooPPWlWDt.CAsO', null);

-- Recipes.
-- TODO.

-- Recipes + categories.
-- TODO.

-- Recipes + ingredients.
-- TODO.