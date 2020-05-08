-- Categories.
INSERT INTO `category` (`id`,`name`) VALUES ('1','Hlavní chod');
INSERT INTO `category` (`id`,`name`) VALUES ('2','Polévka');
INSERT INTO `category` (`id`,`name`) VALUES ('3','Předkrm');
INSERT INTO `category` (`id`,`name`) VALUES ('4','Dezert');
INSERT INTO `category` (`id`,`name`) VALUES ('5','Večeře');
INSERT INTO `category` (`id`,`name`) VALUES ('6','Snídaně');
INSERT INTO `category` (`id`,`name`) VALUES ('7','Buchty');
INSERT INTO `category` (`id`,`name`) VALUES ('8','Bábovky');

-- Ingredients.
INSERT INTO `ingredient` (`id`,`name`) VALUES ('1','Maso');
INSERT INTO `ingredient` (`id`,`name`) VALUES ('2','Sůl');
INSERT INTO `ingredient` (`id`,`name`) VALUES ('3','Hladká mouka');
INSERT INTO `ingredient` (`id`,`name`) VALUES ('4','Polohrubá mouka');
INSERT INTO `ingredient` (`id`,`name`) VALUES ('5','Paprika');
INSERT INTO `ingredient` (`id`,`name`) VALUES ('6','Kukuřice');
INSERT INTO `ingredient` (`id`,`name`) VALUES ('7','Hořčice');
INSERT INTO `ingredient` (`id`,`name`) VALUES ('8','Kvasnice');
INSERT INTO `ingredient` (`id`,`name`) VALUES ('9','Cukr krupice');
INSERT INTO `ingredient` (`id`,`name`) VALUES ('10','Pepř');
INSERT INTO `ingredient` (`id`,`name`) VALUES ('11','Kypřící prášek');
INSERT INTO `ingredient` (`id`,`name`) VALUES ('12','Tymián');
INSERT INTO `ingredient` (`id`,`name`) VALUES ('13','Rozmarýn');
INSERT INTO `ingredient` (`id`,`name`) VALUES ('14','Oregáno');
INSERT INTO `ingredient` (`id`,`name`) VALUES ('15','Sýr');

-- Users.
INSERT INTO `user` (`id`, `username`, `password`, `display_name`) VALUES ('1', 'test', '$2y$12$e7IMUyDEHK53m6wmT9z7Hu84jN.M4IdFEs751tKooPPWlWDt.CAsO', 'Testing User');
INSERT INTO `user` (`id`, `username`, `password`, `display_name`) VALUES ('2', 'tomas', '$2y$12$e7IMUyDEHK53m6wmT9z7Hu84jN.M4IdFEs751tKooPPWlWDt.CAsO', 'Tomáš Křičenský');
INSERT INTO `user` (`id`, `username`, `password`, `display_name`) VALUES ('3', 'eva', '$2y$12$e7IMUyDEHK53m6wmT9z7Hu84jN.M4IdFEs751tKooPPWlWDt.CAsO', null);

-- Recipes.
INSERT INTO `recipe` (`id`, `name`, `description`, `created_at`, `author_id`, `preparation_time`) VALUES ('1', 'České buchty', 'Nadýchané a voňavé kynuté buchty naplněné povidly ovoněnými kapkou rumu jsou nejlepší bohatě poprášené moučkovým cukrem.', now(), '1', '40 minut');
INSERT INTO `recipe` (`id`, `name`, `description`, `created_at`, `author_id`, `preparation_time`) VALUES ('2', 'Hrachová polévka s domácími houstičkami', 'Popis', now(), '1', '120 minut');

INSERT INTO `recipe` (`id`, `name`, `description`, `created_at`, `author_id`, `preparation_time`) VALUES ('3', 'České buchty 2', 'Nadýchané a voňavé kynuté buchty naplněné povidly ovoněnými kapkou rumu jsou nejlepší bohatě poprášené moučkovým cukrem.', now(), '1', '40 minut');
INSERT INTO `recipe` (`id`, `name`, `description`, `created_at`, `author_id`, `preparation_time`) VALUES ('4', 'Hrachová polévka s domácími houstičkami 2', 'Popis', now(), '1', '120 minut');
INSERT INTO `recipe` (`id`, `name`, `description`, `created_at`, `author_id`, `preparation_time`) VALUES ('5', 'České buchty 3', 'Nadýchané a voňavé kynuté buchty naplněné povidly ovoněnými kapkou rumu jsou nejlepší bohatě poprášené moučkovým cukrem.', now(), '1', '40 minut');
INSERT INTO `recipe` (`id`, `name`, `description`, `created_at`, `author_id`, `preparation_time`) VALUES ('6', 'Hrachová polévka s domácími houstičkami 3', 'Popis', now(), '1', '120 minut');
INSERT INTO `recipe` (`id`, `name`, `description`, `created_at`, `author_id`, `preparation_time`) VALUES ('7', 'České buchty 4', 'Nadýchané a voňavé kynuté buchty naplněné povidly ovoněnými kapkou rumu jsou nejlepší bohatě poprášené moučkovým cukrem.', now(), '1', '40 minut');
INSERT INTO `recipe` (`id`, `name`, `description`, `created_at`, `author_id`, `preparation_time`) VALUES ('8', 'Hrachová polévka s domácími houstičkami 4', 'Popis', now(), '1', '120 minut');
INSERT INTO `recipe` (`id`, `name`, `description`, `created_at`, `author_id`, `preparation_time`) VALUES ('9', 'České buchty 5', 'Nadýchané a voňavé kynuté buchty naplněné povidly ovoněnými kapkou rumu jsou nejlepší bohatě poprášené moučkovým cukrem.', now(), '1', '40 minut');
INSERT INTO `recipe` (`id`, `name`, `description`, `created_at`, `author_id`, `preparation_time`) VALUES ('10', 'Hrachová polévka s domácími houstičkami 5', 'Popis', now(), '1', '120 minut');

-- Recipes + categories.
INSERT INTO `recipe_category` (`recipe_id`, `category_id`) VALUES ('1', '7');
INSERT INTO `recipe_category` (`recipe_id`, `category_id`) VALUES ('2', '2');
INSERT INTO `recipe_category` (`recipe_id`, `category_id`) VALUES ('3', '2');
INSERT INTO `recipe_category` (`recipe_id`, `category_id`) VALUES ('3', '3');
INSERT INTO `recipe_category` (`recipe_id`, `category_id`) VALUES ('3', '4');
INSERT INTO `recipe_category` (`recipe_id`, `category_id`) VALUES ('4', '1');
INSERT INTO `recipe_category` (`recipe_id`, `category_id`) VALUES ('5', '2');
INSERT INTO `recipe_category` (`recipe_id`, `category_id`) VALUES ('6', '3');
INSERT INTO `recipe_category` (`recipe_id`, `category_id`) VALUES ('7', '1');
INSERT INTO `recipe_category` (`recipe_id`, `category_id`) VALUES ('8', '5');
INSERT INTO `recipe_category` (`recipe_id`, `category_id`) VALUES ('9', '6');
INSERT INTO `recipe_category` (`recipe_id`, `category_id`) VALUES ('10', '8');

-- Recipes + ingredients.
INSERT INTO `recipe_ingredient` (`recipe_id`, `ingredient_id`, `amount`) VALUES ('1', '1', '1 kus');
INSERT INTO `recipe_ingredient` (`recipe_id`, `ingredient_id`, `amount`) VALUES ('1', '2', '1 kg');
INSERT INTO `recipe_ingredient` (`recipe_id`, `ingredient_id`, `amount`) VALUES ('1', '3', '1 litr');
INSERT INTO `recipe_ingredient` (`recipe_id`, `ingredient_id`, `amount`) VALUES ('1', '4', '1 hrst');
INSERT INTO `recipe_ingredient` (`recipe_id`, `ingredient_id`, `amount`) VALUES ('2', '12', '1 hrnek');
INSERT INTO `recipe_ingredient` (`recipe_id`, `ingredient_id`, `amount`) VALUES ('2', '13', '1 kg');
INSERT INTO `recipe_ingredient` (`recipe_id`, `ingredient_id`, `amount`) VALUES ('2', '8', '1 deci-litr');
INSERT INTO `recipe_ingredient` (`recipe_id`, `ingredient_id`, `amount`) VALUES ('2', '14', '1 špetka');
INSERT INTO `recipe_ingredient` (`recipe_id`, `ingredient_id`, `amount`) VALUES ('3', '1', '1 hrnek');
INSERT INTO `recipe_ingredient` (`recipe_id`, `ingredient_id`, `amount`) VALUES ('3', '15', '1 kg');
INSERT INTO `recipe_ingredient` (`recipe_id`, `ingredient_id`, `amount`) VALUES ('4', '11', '1 hrnek');
INSERT INTO `recipe_ingredient` (`recipe_id`, `ingredient_id`, `amount`) VALUES ('4', '2', '1 kg');
INSERT INTO `recipe_ingredient` (`recipe_id`, `ingredient_id`, `amount`) VALUES ('5', '4', '1 hrnek');
INSERT INTO `recipe_ingredient` (`recipe_id`, `ingredient_id`, `amount`) VALUES ('5', '6', '1 kg');
INSERT INTO `recipe_ingredient` (`recipe_id`, `ingredient_id`, `amount`) VALUES ('6', '8', '1 hrnek');
INSERT INTO `recipe_ingredient` (`recipe_id`, `ingredient_id`, `amount`) VALUES ('6', '7', '1 kg');
INSERT INTO `recipe_ingredient` (`recipe_id`, `ingredient_id`, `amount`) VALUES ('7', '9', '1 hrnek');
INSERT INTO `recipe_ingredient` (`recipe_id`, `ingredient_id`, `amount`) VALUES ('7', '10', '1 kg');
INSERT INTO `recipe_ingredient` (`recipe_id`, `ingredient_id`, `amount`) VALUES ('8', '10', '1 hrnek');
INSERT INTO `recipe_ingredient` (`recipe_id`, `ingredient_id`, `amount`) VALUES ('8', '4', '1 kg');
INSERT INTO `recipe_ingredient` (`recipe_id`, `ingredient_id`, `amount`) VALUES ('9', '1', '1 hrnek');
INSERT INTO `recipe_ingredient` (`recipe_id`, `ingredient_id`, `amount`) VALUES ('9', '2', '1 kg');
INSERT INTO `recipe_ingredient` (`recipe_id`, `ingredient_id`, `amount`) VALUES ('10', '2', '1 hrnek');
INSERT INTO `recipe_ingredient` (`recipe_id`, `ingredient_id`, `amount`) VALUES ('10', '4', '1 kg');
