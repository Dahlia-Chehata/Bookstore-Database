create schema if not exists book_store;
use book_store;
create table if not exists book_store.Categories(
	category_id	int primary key Not Null auto_increment,			
	Category_Name   varchar (100) unique,
    FULLTEXT Category_Name_idx(Category_Name)
);


create table if not exists book_store.Publishers(
	Publisher_id		int primary key Not Null auto_increment,	
	Publisher_Name      varchar (100)  not null,
	Publisher_Address   varchar(100) null,
	Publisher_Telephone   varchar(100) null,
	FULLTEXT Publisher_Name_idx (Publisher_Name),
    INDEX (publisher_name)
);


create table if not exists book_store.books(
	Book_id		int primary key Not Null auto_increment,	
	Title       varchar(100) not null,
	category_id  int not null,
    FULLTEXT title_idx (Title),
    index (title),
    index(category_id),
    constraint books_category_fk foreign key (category_id) references Categories(category_id)
);

create table if not exists book_store.Books_ISBNs(
	ISBN		varchar (100)Primary Key Not Null,
	Book_id      int not null,
	Publisher_id  int not null, 	
	Publication_Year int not null,   
	Selling_price   double not null,
	Available_Quantity int not null, 
	Threshold int not null,
    index (ISBN),
    index(book_id),
    index(publisher_id),
    index(publication_year),
    index(selling_price),
    index(available_quantity),
    index(threshold),
    FULLTEXT ISBN_idx (ISBN),
    constraint books_isbns_fk foreign key (Book_id) references books(book_id),
	constraint isbns_publishers_fk foreign key (Publisher_id) references Publishers(Publisher_id)
);

create table if not exists book_store.Authors(
	Book_id	 int not null,				
	Author_Name	 varchar (100) not null ,
    FULLTEXT Author_Name_idx (Author_Name),
    index(book_id),
    index(author_name),
    constraint authors_pk primary key (Book_id,Author_Name),
	constraint books_authors_fk foreign key (Book_id) references books(book_id)
);
create table if not exists book_store.Status_Menu (
	Status_id int primary key not null auto_increment,      -- <1 - 0> 		
	Permission  varchar (100) unique not null                -- <manager - customer>
);	

create table if not exists book_store.Users(
	User_id  int primary key Not Null auto_increment,	
	User_Name varchar (100) not null,
	Password  varchar(100) not null,
	First_Name varchar(100) not null,
	last_Name varchar(100) not null,
	Email     varchar (100) unique  null,
	Phone_Number varchar (100),
	Status_id   int not null,				
	Default_Shipping_Address varchar (100) not null,
	index(user_name),
    index(email),
    constraint users_status_fk foreign key (status_id) references Status_Menu (status_id)
);
	
create table if not exists book_store.orders(

  Id int primary key Not Null auto_increment,	
  User_id int not null,
  Order_Time Timestamp,
  credit_card_no varchar (100) null,
  Credit_Expiry_Date date not null,
  index(user_id),
  index(order_time),
  index(credit_card_no),
  constraint orders_users_fk foreign key (user_id) references Users (user_id)
);
create table if not exists book_store.purchases(

	Order_id   int not null,
	ISBN    varchar (100) not null,
    no_of_copies int,
	TotalPrice double,
    index(ISBN),
    constraint purchases_pk primary key (Order_id,ISBN),
	constraint purchases_Order_id_fk foreign key (Order_id) references orders (Id),
	constraint purchases_isbn_fk foreign key (ISBN) references books_ISBNs(ISBN)

);

create table if not exists book_store.manager_order(
    mgr_order_id  int not null auto_increment,		
	ISBN    varchar (100) unique not null,
	no_of_copies int,
  constraint manager_order_pk primary key (mgr_order_id),
  constraint manager_order_isbn_fk foreign key (ISBN) references books_ISBNs(ISBN)
);

-- check negativity of book count
DROP trigger if exists negative_constraint;
DELIMITER $$
CREATE TRIGGER negative_constraint BEFORE update ON Books_ISBNs
FOR EACH ROW BEGIN
   IF (NEW.Available_Quantity < 0) THEN
         CALL raise_error;
   END IF;
END$$
DELIMITER ;

-- check if quantity is less than a threshold after update
DROP trigger if exists threshold_after_update;
DELIMITER $$
CREATE TRIGGER threshold_after_update AFTER update ON Books_ISBNs
FOR EACH ROW BEGIN
 declare ordered_quantity int;
 set ordered_quantity=0;
 select no_of_copies into ordered_quantity
 from manager_order natural join books_ISBNs 
 where ISBN = NEW.ISBN;
   IF (NEW.Available_Quantity < OLD.Threshold+ordered_quantity) THEN
      if ordered_quantity=0 then
	  insert into book_store.manager_order (ISBN,no_of_copies) values(new.ISBN,(OLD.Available_Quantity-NEW.Available_Quantity)*1.5);
      else
       update book_store.manager_order
       set no_of_copies = no_of_copies+(OLD.Available_Quantity-NEW.Available_Quantity)*1.5
       where manager_order.ISBN = NEW.ISBN;
       END IF;
   END IF;
END$$
DELIMITER ;

-- check if quantity is less than a threshold after insertion
DROP trigger if exists threshold_after_insert;
DELIMITER $$
CREATE TRIGGER threshold_after_insert AFTER insert ON Books_ISBNs
FOR EACH ROW BEGIN
   IF (NEW.Available_Quantity < NEW.Threshold) THEN
	  insert into book_store.manager_order (ISBN,no_of_copies) values(NEW.ISBN,(NEW.Threshold - NEW.Available_Quantity)*1.5);
   END IF;
END$$
DELIMITER ;


-- check if the order is confirmed before deletion
DROP trigger if exists delete_manager_order ;
DELIMITER $$
CREATE TRIGGER delete_manager_order AFTER DELETE ON manager_order
FOR EACH ROW BEGIN
   UPDATE Books_ISBNs SET Books_ISBNs.Available_Quantity = (Books_ISBNs.Available_Quantity + OLD.no_of_copies) WHERE 
   Books_ISBNs.ISBN = OLD.ISBN;
END$$
DELIMITER ;

-- check if email is in a valid format before update
DROP trigger if exists valid_email_before_update ;
DELIMITER $$
CREATE TRIGGER valid_email_before_update BEFORE UPDATE ON Users
FOR EACH ROW BEGIN
   IF (NEW.Email NOT LIKE '%_@_%._%' ) THEN
             CALL raise_error;
   END IF;
END$$
DELIMITER ;

-- check if email is in a valid format before insertion
DROP trigger if exists valid_email_before_insert ;
DELIMITER $$
CREATE TRIGGER valid_email_before_insert BEFORE UPDATE ON Users
FOR EACH ROW BEGIN
   IF (NEW.Email NOT LIKE '%_@_%._%' ) THEN
             CALL raise_error;
   END IF;
END$$
DELIMITER ;

-- check if the category type is valid before insertion
DROP trigger if exists valid_category_before_insert;
DELIMITER $$
CREATE TRIGGER  valid_category_before_insert BEFORE insert ON categories
FOR EACH ROW BEGIN
   IF (NEW.Category_Name !='Science' and NEW.Category_Name !='Art' and NEW.Category_Name !='Religion' and 
   NEW.Category_Name != 'History' and NEW.Category_Name != 'Geography') THEN
             CALL raise_error;
   END IF;
END$$
DELIMITER ;


-- check if the category type is valid before update
DROP trigger if exists valid_category_before_update;
DELIMITER $$
CREATE TRIGGER valid_category_before_update BEFORE UPDATE ON categories
FOR EACH ROW BEGIN
   IF (NEW.Category_Name!='Science' and NEW.Category_Name !='Art' and NEW.Category_Name !='Religion' and 
   NEW.Category_Name != 'History' and NEW.Category_Name != 'Geography') THEN
             CALL raise_error;
   END IF;
END$$
DELIMITER ;

--  Query 1: Total sales for books in the previous month 
Drop table if exists book_store.Total_sales_previous_month;
create table if not exists book_store.Total_sales_previous_month As 
SELECT SUM(purchases.TotalPrice) FROM purchases
INNER JOIN orders ON orders.Id = purchases.Order_id
WHERE month(orders.Order_Time) = (((month(NOW())+10) mod 12)+1)
AND ((year(orders.Order_Time) = year(NOW()) AND MONTH(NOW()) != 1) OR (year(orders.Order_Time) = year(NOW())-1 AND MONTH(NOW()) = 1));

-- Query 2: The top 5 customers who purchase the most purchase amount
-- in descending order for the last three months
Drop table if exists top_five_customers;
create table if not exists book_store.top_five_customers As 
SELECT Users.User_Name,Users.first_name,users.last_Name FROM Users
INNER JOIN orders on orders.User_id = Users.User_id
INNER JOIN purchases ON orders.Id = purchases.Order_id
WHERE month(orders.Order_Time) >= (((month(NOW())+8) mod 12)+1) AND month(orders.Order_Time) <= (((month(NOW())+10) mod 12)+1)
AND ((year(orders.Order_Time) = year(NOW()) AND MONTH(NOW()) != 1) OR (year(orders.Order_Time) = year(NOW())-1 AND MONTH(NOW()) = 1))
GROUP BY Users.User_id
ORDER BY SUM(purchases.TotalPrice) DESC
LIMIT 5;

-- Query 3: top 10 selling books for the last 3 months
Drop table if exists top_selling_books;
create table if not exists book_store.top_selling_books As
SELECT Books_ISBNs.ISBN,books.Title FROM Books_ISBNs
inner join books on books.Book_id = books_isbns.Book_id
INNER JOIN purchases ON purchases.ISBN = Books_ISBNs.ISBN
INNER JOIN orders ON orders.Id = purchases.Order_id
WHERE month(orders.Order_Time) >= (((month(NOW())+8) mod 12)+1) AND month(orders.Order_Time) <= (((month(NOW())+10) mod 12)+1)
AND ((year(orders.Order_Time) = year(NOW()) AND MONTH(NOW()) != 1) OR (year(orders.Order_Time) = year(NOW())-1 AND MONTH(NOW()) = 1))
GROUP BY Books_ISBNs.ISBN
ORDER BY SUM(purchases.no_of_copies) DESC
LIMIT 10;


LOCK TABLES `Categories` WRITE;
INSERT INTO `Categories` VALUES (4,'Art'),(5,'Geography'),(2,'History'),(3,'Religion'),(1,'Science');
UNLOCK TABLES;

LOCK TABLES `Publishers` WRITE;
INSERT INTO `Publishers` VALUES (1,'Clark R. Madden','8185 Luctus. Avenue','07624 746004'),(2,'Melinda Z. Chavez','5855 Magna. Road','(01014) 392293'),(3,'Austin Mcknight','P.O. Box 203, 5497 Congue. Avenue','0871 619 1376'),(4,'Brian L. Brock','Ap #726-6794 Eu Rd.','(016977) 3424'),(5,'Dane Owens','P.O. Box 555, 7302 Lacinia. Av.','07872 604802'),(6,'Chloe X. Herring','5018 Quam Avenue','0845 46 41'),(7,'James Kane','344-1132 Libero. Road','0928 678 3514'),(8,'Georgia P. Whitfield','Ap #748-9055 Leo, Rd.','056 5757 1902'),(9,'Iliana N. Hernandez','823-7169 Ornare, Road','0395 860 2763'),(10,'Drake S. Franks','721-9606 Mi Avenue','0880 767 3336'),(11,'Kiara Morgan','Ap #869-4299 Sed St.','07624 221627'),(12,'Kareem Serrano','315 Vitae, St.','0316 240 1814'),(13,'Caryn L. Wong','354-987 Nibh Rd.','0845 46 45'),(14,'Eugenia J. Ellis','P.O. Box 714, 4665 Sodales Rd.','07206 035227'),(15,'Nevada Thornton','P.O. Box 590, 1192 Consectetuer, Rd.','0815 227 2385'),(16,'Stewart Woodward','P.O. Box 631, 5064 Metus Av.','070 3665 0011'),(17,'Carlos Norton','614-5237 Gravida Avenue','076 0562 7754'),(18,'Galvin X. Reed','373-8464 Curabitur Ave','(0111) 937 9603'),(19,'Naomi Roth','Ap #892-9098 Consectetuer Road','(01536) 269810'),(20,'MacKensie T. Sims','Ap #564-7713 Purus. Avenue','0845 46 45'),(21,'Ronan Simmons','696-9863 Sapien, Av.','07624 450770'),(22,'Wanda I. Watkins','4848 Ut, St.','(016977) 0184'),(23,'Mona Spencer','290-4996 Odio, Ave','0800 133 9631'),(24,'Davis Sargent','520-1197 Mauris St.','(01802) 98427'),(25,'Kalia E. Herrera','329-8252 Orci St.','(014446) 07199'),(26,'Quyn Love','743-6330 Ultricies Street','(01775) 746603'),(27,'Kenneth O. Sutton','Ap #757-9438 Purus Rd.','07624 211588'),(28,'Rebecca Downs','1476 Integer Avenue','056 0835 4991'),(29,'Keaton Pruitt','P.O. Box 692, 9079 Sociis St.','(0151) 243 5844'),(30,'Colette I. Webster','472-8805 Gravida Road','07491 338527'),(31,'Devin Bentley','8220 Turpis. Street','0339 787 2061'),(32,'Octavia B. Nieves','5718 Consequat Avenue','0977 406 4816'),(33,'Rafael A. Fry','396-1575 Purus Road','07784 046172'),(34,'Jameson Gutierrez','P.O. Box 103, 2798 Eget Rd.','0845 46 46'),(35,'Thaddeus A. Zamora','P.O. Box 502, 8885 Tempor St.','0861 130 7764'),(36,'Cairo I. Serrano','750-2362 Ipsum Ave','(01607) 66478'),(37,'Fallon Porter','Ap #764-9452 Quisque Rd.','(016977) 4589'),(38,'Stone D. Benton','933 Ipsum. Rd.','(016977) 3345'),(39,'Dorian Greene','P.O. Box 782, 6817 Metus. Avenue','07624 504728'),(40,'Raya G. Joseph','Ap #532-6002 Integer Avenue','055 9381 6061'),(41,'Derek Lowe','9796 Risus, St.','(014111) 13985'),(42,'Thomas J. Mccarthy','270 Nam Rd.','0800 795200'),(43,'Sawyer Q. Lawrence','Ap #577-2100 Eget Ave','07624 339972'),(44,'Leonard Y. Lang','Ap #622-1650 Magna. Ave','0309 312 7598'),(45,'Zorita Banks','3045 Rhoncus. Ave','076 5007 8354'),(46,'Alden Beasley','864 Ligula Street','(014780) 08785'),(47,'Jennifer Hammond','Ap #877-9584 Ultrices Av.','(0101) 689 0712'),(48,'Karleigh R. Mathis','6928 Non Av.','07806 204119'),(49,'Marny L. Padilla','Ap #777-1100 A, Street','0378 973 3303'),(50,'Abbot Gregory','876-6548 Sit Av.','055 1170 0112'),(51,'Gabriel Mcintosh','Ap #910-8089 Hendrerit Ave','056 3813 3585'),(52,'Talon Callahan','114-7721 Lacus Ave','0993 542 0466'),(53,'Raven N. Ashley','P.O. Box 459, 4838 Nisi Avenue','0800 1111'),(54,'Keelie O. Oconnor','P.O. Box 162, 7033 A, Avenue','(0114) 632 1675'),(55,'Lillith Mills','P.O. Box 421, 6429 Sit Rd.','0845 46 48'),(56,'Ferdinand Frank','2611 Dictum Rd.','076 8227 1276'),(57,'Garrett Martin','227-7771 Consequat St.','(0112) 461 0700'),(58,'Orli D. Wallace','693-3417 Eget St.','0800 944858'),(59,'Amal Q. Carrillo','Ap #707-7737 Quis St.','0800 363 5496'),(60,'Rosalyn Dillard','Ap #901-7530 Enim Ave','0500 159723'),(61,'Caldwell S. Cervantes','P.O. Box 594, 3366 Etiam Avenue','0346 343 8067'),(62,'Rebecca R. Winters','P.O. Box 996, 9251 Risus. Street','(0151) 494 6671'),(63,'Merrill Montoya','P.O. Box 504, 9814 Nibh St.','076 7076 2588'),(64,'Melodie Hubbard','P.O. Box 396, 4259 Pede Rd.','(016977) 8872'),(65,'Meghan V. Fox','P.O. Box 708, 6655 Malesuada Avenue','(0111) 689 1606'),(66,'Lance Y. Mosley','587-1117 Integer Ave','055 1648 4824'),(67,'Piper Whitney','Ap #535-6477 Diam Rd.','0892 313 5619'),(68,'Alisa Howe','606-1733 Aliquam Road','(016977) 1587'),(69,'Adara Harrell','P.O. Box 228, 2646 Dui St.','(01968) 344464'),(70,'Calvin Moss','1538 Ac Av.','070 1002 4804'),(71,'Quon K. Decker','Ap #979-5890 Aliquam St.','0800 062455'),(72,'Doris E. Harrell','Ap #640-2257 Lorem, Street','0974 090 0784'),(73,'Acton B. Morrow','Ap #161-935 Arcu Ave','(029) 9723 7735'),(74,'Hermione H. Pate','P.O. Box 651, 2623 Phasellus Road','076 6805 1295'),(75,'Benedict Stafford','Ap #436-808 Massa Rd.','07603 466046'),(76,'Cynthia Y. Roach','671 Ornare Rd.','0800 1111'),(77,'Cheyenne Rowe','P.O. Box 712, 2027 Vehicula Rd.','07624 693107'),(78,'Wallace G. Boyer','P.O. Box 723, 1358 Urna Rd.','(028) 9988 2556'),(79,'Jermaine House','P.O. Box 889, 6069 Enim Rd.','07307 945810'),(80,'Chelsea S. Ramos','Ap #716-6364 Purus Road','(016977) 2252'),(81,'Regina Boyer','9103 Proin Rd.','0800 546 5455'),(82,'Veda Boone','P.O. Box 962, 6569 Vitae, Ave','07198 321657'),(83,'Micah Snow','901-3361 Porttitor Ave','0800 649 2163'),(84,'Tashya Wallace','962-7920 Accumsan St.','(0121) 849 6462'),(85,'Mari Holden','Ap #262-3102 Elementum Av.','070 6146 5297'),(86,'Maile Johnston','P.O. Box 113, 9082 Interdum Ave','0800 176 7489'),(87,'Abbot Pruitt','Ap #168-3894 Cras St.','076 4132 3299'),(88,'Summer Horne','350-7915 Amet Rd.','0845 46 47'),(89,'Mannix Le','5337 Tempor, Av.','0958 784 2554'),(90,'Baker Z. Cooper','577-1992 Sed Av.','(0101) 106 9802'),(91,'Minerva Morrow','P.O. Box 614, 2772 Donec St.','07880 168133'),(92,'Elvis Mclean','P.O. Box 115, 5937 Phasellus St.','(01924) 670773'),(93,'Ariana Marsh','545-3332 Dictum Rd.','0800 1111'),(94,'Donna Ochoa','P.O. Box 753, 337 Varius Rd.','0334 302 1635'),(95,'Rudyard Copeland','289 Sed Av.','055 9709 3676'),(96,'Reece Wyatt','854 Consectetuer Avenue','0908 823 6451'),(97,'Nicole L. Blankenship','P.O. Box 586, 6837 Rutrum St.','0800 771082'),(98,'Sybil Ford','2979 Mollis St.','0800 904 4759'),(99,'Dora Joseph','1707 Metus. Ave','076 8965 0839'),(100,'Tamara H. Sims','118 Ultrices Rd.','0800 715 8938');
UNLOCK TABLES;

LOCK TABLES `Status_Menu` WRITE;
INSERT INTO `Status_Menu` VALUES (0,'customer'),(1,'manager');
UNLOCK TABLES;

LOCK TABLES `Users` WRITE;
INSERT INTO `Users` VALUES (1,'Benedict Villarreal','144bc9671d41e613a94e6aca0d9497d43d7472b7','Gordon','Kirk','a.neque.Nullam@dictumeu.co.uk','(0110) 363 9323',0,'669-2853 Dignissim. Ave'),(2,'Erasmus Beard','144bc9671d41e613a94e6aca0d9497d43d7472b7','Velazquez','Goodman','malesuada@nisiMauris.co.uk','0845 46 46',1,'P.O. Box 827, 5466 Eleifend Av.'),(3,'Jonas Foreman','144bc9671d41e613a94e6aca0d9497d43d7472b7','Madden','Whitley','rutrum@egestas.edu','(0121) 222 6188',1,'2736 Faucibus St.'),(4,'Whitney Phillips','144bc9671d41e613a94e6aca0d9497d43d7472b7','Barrett','Wilkinson','ipsum.sodales@nibh.co.uk','07624 160887',0,'512-9988 Non Rd.'),(5,'Lars White','144bc9671d41e613a94e6aca0d9497d43d7472b7','Ruiz','Torres','ac@pharetraQuisqueac.com','(028) 6469 7665',0,'Ap #737-4513 Donec St.'),(6,'Aspen Meyers','144bc9671d41e613a94e6aca0d9497d43d7472b7','Copeland','Goodman','Suspendisse.tristique@Nam.co.uk','0800 1111',1,'597-4392 Sed St.'),(7,'Tobias Lawrence','144bc9671d41e613a94e6aca0d9497d43d7472b7','Villarreal','Horne','vel.convallis.in@ametultriciessem.co.uk','055 8097 4911',1,'9180 Ad Street'),(8,'Briar Campbell','144bc9671d41e613a94e6aca0d9497d43d7472b7','Jacobson','Parsons','a.felis.ullamcorper@atarcuVestibulum.edu','055 6676 4348',1,'P.O. Box 492, 5826 Est Av.'),(9,'Jelani Wilkins','144bc9671d41e613a94e6aca0d9497d43d7472b7','Nelson','Guerrero','lectus.sit.amet@eutelluseu.ca','(020) 0377 1541',0,'P.O. Box 932, 3000 Pede Street'),(10,'Alma Hayden','144bc9671d41e613a94e6aca0d9497d43d7472b7','Griffith','Barber','Donec.tincidunt.Donec@et.co.uk','(016977) 7847',0,'P.O. Box 472, 5126 Purus, Ave'),(11,'Brooke Mcguire','144bc9671d41e613a94e6aca0d9497d43d7472b7','Bishop','Blevins','Duis@euplacerateget.org','(016977) 6800',0,'514-197 Dui Avenue'),(12,'Shea Riddle','144bc9671d41e613a94e6aca0d9497d43d7472b7','Jacobs','Munoz','Donec.est@dolornonummy.org','07566 964652',0,'222-6439 A, Road'),(13,'Rooney Drake','144bc9671d41e613a94e6aca0d9497d43d7472b7','Buckley','Levy','sodales.at@nuncsedpede.edu','(01991) 52419',0,'P.O. Box 849, 170 Sodales St.'),(14,'Alec Bowen','144bc9671d41e613a94e6aca0d9497d43d7472b7','Frost','Rivers','eget@Maurisutquam.ca','(0131) 656 6858',0,'322-3778 At Avenue'),(15,'Perry Clayton','144bc9671d41e613a94e6aca0d9497d43d7472b7','Mason','Green','varius.Nam.porttitor@Sed.net','0376 351 0783',1,'5191 Et Av.'),(16,'Rudyard Pacheco','144bc9671d41e613a94e6aca0d9497d43d7472b7','Freeman','Delaney','Sed@fringilla.com','07624 633510',0,'5226 Luctus St.'),(17,'Trevor Carlson','144bc9671d41e613a94e6aca0d9497d43d7472b7','Mcgee','Pierce','sed.dui.Fusce@Quisqueimperdiet.edu','0800 873 0409',1,'686-7892 Parturient Rd.'),(18,'Holmes Bennett','144bc9671d41e613a94e6aca0d9497d43d7472b7','Lara','Howe','dignissim.tempor.arcu@nonummyut.net','0800 1111',0,'8662 Faucibus Av.'),(19,'Desiree Stewart','144bc9671d41e613a94e6aca0d9497d43d7472b7','Valdez','Cunningham','facilisis.eget@orciUtsemper.edu','(023) 9518 4773',0,'Ap #863-646 Dictum St.'),(20,'Avram Adams','144bc9671d41e613a94e6aca0d9497d43d7472b7','Sexton','Mitchell','lorem.vitae@ut.com','(01284) 636183',1,'597-8270 Nec, St.'),(21,'Venus Dominguez','144bc9671d41e613a94e6aca0d9497d43d7472b7','Booker','Middleton','Nam@Aliquam.co.uk','0800 283 5261',1,'Ap #394-1823 Donec Av.'),(22,'Katell Flores','144bc9671d41e613a94e6aca0d9497d43d7472b7','Irwin','Becker','est@malesuadavel.net','(0114) 068 5935',0,'Ap #566-694 Eros Ave'),(23,'Patience Johnston','144bc9671d41e613a94e6aca0d9497d43d7472b7','Vaughan','Hinton','Curae.Donec.tincidunt@lobortisauguescelerisque.org','0845 46 42',0,'3878 Volutpat Rd.'),(24,'Ariana Murray','144bc9671d41e613a94e6aca0d9497d43d7472b7','Waller','Rich','risus.Duis@odiotristiquepharetra.org','070 6412 4143',0,'P.O. Box 828, 5654 Facilisis St.'),(25,'Farrah Nunez','144bc9671d41e613a94e6aca0d9497d43d7472b7','Kinney','Frye','cursus.vestibulum@cursusin.com','056 6669 4887',1,'P.O. Box 883, 7761 Tempor Rd.'),(26,'Reece Murray','144bc9671d41e613a94e6aca0d9497d43d7472b7','Ramirez','Rodriguez','elit.pretium@cursusvestibulum.net','07624 455362',0,'283-4481 Cursus Avenue'),(27,'Wade Pena','144bc9671d41e613a94e6aca0d9497d43d7472b7','Navarro','Gamble','Aliquam.erat@sociisnatoque.ca','0500 383793',1,'595-310 Sed Street'),(28,'Nash Mercer','144bc9671d41e613a94e6aca0d9497d43d7472b7','Mullen','Taylor','nulla@neque.co.uk','056 7213 1261',0,'P.O. Box 990, 2735 Porttitor Road'),(29,'Kane Ballard','144bc9671d41e613a94e6aca0d9497d43d7472b7','Poole','Mcconnell','non@nuncinterdum.com','0381 233 0690',0,'2884 Magna Avenue'),(30,'Keefe Gentry','144bc9671d41e613a94e6aca0d9497d43d7472b7','Pearson','Sweeney','lorem@senectusetnetus.net','0800 605123',1,'209-1913 Non, Road'),(31,'Michael Berry','144bc9671d41e613a94e6aca0d9497d43d7472b7','Fitzgerald','Battle','eu@portaelita.edu','076 9939 7636',0,'9669 Erat St.'),(32,'Elaine Pitts','144bc9671d41e613a94e6aca0d9497d43d7472b7','Romero','Hughes','vel.sapien@bibendumfermentummetus.org','(0110) 895 4643',0,'717-1395 Elit, Rd.'),(33,'Nina Franks','144bc9671d41e613a94e6aca0d9497d43d7472b7','Ochoa','Reilly','et@natoquepenatibuset.edu','070 2912 0861',0,'6629 Sit Rd.'),(34,'Aristotle Dickerson','144bc9671d41e613a94e6aca0d9497d43d7472b7','Fox','Abbott','Duis@eu.net','0372 355 4855',1,'Ap #552-9390 Orci Street'),(35,'Elvis Burks','144bc9671d41e613a94e6aca0d9497d43d7472b7','Walker','Hogan','auctor.vitae.aliquet@volutpatNulla.co.uk','056 1980 7151',1,'P.O. Box 778, 7720 Tempus Ave'),(36,'Akeem Blair','144bc9671d41e613a94e6aca0d9497d43d7472b7','Howe','Bell','ut@variusNam.edu','(028) 7460 9906',1,'6243 Ligula Avenue'),(37,'Uta Mcclain','144bc9671d41e613a94e6aca0d9497d43d7472b7','Bauer','Wallace','eu@aliquameu.com','0815 229 9288',0,'Ap #642-2728 Leo St.'),(38,'Sandra Mccormick','144bc9671d41e613a94e6aca0d9497d43d7472b7','Dunn','Buckley','dictum@enim.co.uk','076 1028 0765',0,'835-5728 Eros. Rd.'),(39,'Aimee Douglas','144bc9671d41e613a94e6aca0d9497d43d7472b7','Morton','Paul','non.dapibus.rutrum@semperauctorMauris.net','056 5893 4437',0,'267-2776 Iaculis St.'),(40,'Tana Sutton','144bc9671d41e613a94e6aca0d9497d43d7472b7','Pennington','Baldwin','Cum.sociis@Nullaeget.net','0981 217 1407',0,'826-1913 Risus Ave'),(41,'Robin Bright','144bc9671d41e613a94e6aca0d9497d43d7472b7','Bullock','Avery','Etiam.bibendum@Quisqueornare.ca','(011473) 82057',1,'4394 Sed St.'),(42,'Odette Goodman','144bc9671d41e613a94e6aca0d9497d43d7472b7','Lynn','Dudley','condimentum@Phasellusnulla.org','0848 398 2984',0,'Ap #271-1833 Sed St.'),(43,'Beverly Moses','144bc9671d41e613a94e6aca0d9497d43d7472b7','Cantu','Neal','amet@arcu.org','(01611) 742293',0,'600-259 Auctor Av.'),(44,'Brett Kane','144bc9671d41e613a94e6aca0d9497d43d7472b7','Langley','Jarvis','non@tellusjusto.ca','076 4266 1232',1,'752-8960 Quam St.'),(45,'Quentin Hale','144bc9671d41e613a94e6aca0d9497d43d7472b7','Garcia','Higgins','gravida.molestie@eget.net','07624 306970',0,'207-8578 Vivamus St.'),(46,'Imogene Holden','144bc9671d41e613a94e6aca0d9497d43d7472b7','Barton','Thomas','eu@sempereratin.com','(01554) 979036',0,'845-5271 Mi St.'),(47,'Forrest Willis','144bc9671d41e613a94e6aca0d9497d43d7472b7','Welch','Ewing','odio.tristique.pharetra@tristique.edu','(0111) 040 2039',0,'Ap #437-901 Lobortis Avenue'),(48,'Winifred Walsh','144bc9671d41e613a94e6aca0d9497d43d7472b7','Green','Hayes','condimentum@lectusa.com','0800 1111',0,'Ap #295-4199 Sociis Ave'),(49,'Herrod Hess','144bc9671d41e613a94e6aca0d9497d43d7472b7','Tucker','Lee','malesuada.vel@risus.edu','0800 1111',0,'4692 Magnis Avenue'),(50,'Dahlia Sexton','144bc9671d41e613a94e6aca0d9497d43d7472b7','Craig','Ayers','at.pretium@consequatnecmollis.co.uk','(0117) 418 2361',1,'Ap #633-3699 Eu Road'),(51,'Veronica Lawrence','144bc9671d41e613a94e6aca0d9497d43d7472b7','Matthews','Levy','mollis.dui@elementumduiquis.com','0978 267 0622',0,'9939 Nulla. Road'),(52,'Ursula Decker','144bc9671d41e613a94e6aca0d9497d43d7472b7','Holder','Gilliam','consectetuer.mauris.id@ligula.edu','0845 46 43',1,'601-200 Dui. Ave'),(53,'Katell Smith','144bc9671d41e613a94e6aca0d9497d43d7472b7','Miranda','Moran','convallis.convallis.dolor@Cum.co.uk','0819 451 5253',0,'Ap #444-3235 Mauris Avenue'),(54,'Virginia Fulton','144bc9671d41e613a94e6aca0d9497d43d7472b7','Kirkland','Fox','pharetra.Quisque@vitae.ca','0332 897 9588',0,'3154 Massa. Road'),(55,'Carson Franco','144bc9671d41e613a94e6aca0d9497d43d7472b7','Stewart','Barrett','Duis.gravida.Praesent@quis.net','07951 894365',0,'P.O. Box 269, 6304 Sed St.'),(56,'Octavius Hess','144bc9671d41e613a94e6aca0d9497d43d7472b7','Burns','Galloway','non@Phasellus.org','0331 077 3067',0,'593-5327 Suspendisse Ave'),(57,'Isadora Robinson','144bc9671d41e613a94e6aca0d9497d43d7472b7','York','Taylor','dolor@blandit.co.uk','0800 458 3186',0,'7096 Enim. Street'),(58,'Kevyn Webb','144bc9671d41e613a94e6aca0d9497d43d7472b7','Simon','Lyons','et@Proinnislsem.org','0379 332 8240',0,'Ap #291-4538 Augue. Street'),(59,'Norman Pace','144bc9671d41e613a94e6aca0d9497d43d7472b7','Booker','Mills','pharetra.Quisque@nec.net','07624 130330',1,'201-8322 Quis, Ave'),(60,'Briar Richardson','144bc9671d41e613a94e6aca0d9497d43d7472b7','Henson','Levine','pretium@dictum.co.uk','0940 173 9569',0,'418-7460 Sed Road'),(61,'Vanna Talley','144bc9671d41e613a94e6aca0d9497d43d7472b7','Pruitt','Trevino','nec@magnis.org','0800 1111',0,'888-3722 Dictum Rd.'),(62,'Ross Stephenson','144bc9671d41e613a94e6aca0d9497d43d7472b7','Vang','Webster','posuere@sodaleselit.net','(011427) 36218',0,'563-7980 Cursus. Rd.'),(63,'Wade Butler','144bc9671d41e613a94e6aca0d9497d43d7472b7','Graves','Decker','Morbi.non@acmattis.co.uk','070 5581 8501',1,'850-1874 Egestas Rd.'),(64,'Adrienne Graham','144bc9671d41e613a94e6aca0d9497d43d7472b7','Shepherd','Ewing','velit@ligulatortordictum.co.uk','(01598) 618032',1,'Ap #751-6744 Eget Street'),(65,'Scarlett Nash','144bc9671d41e613a94e6aca0d9497d43d7472b7','Mann','Mcintosh','nonummy@duiSuspendisse.com','(0131) 182 6966',0,'Ap #455-5467 Lorem Street'),(66,'Akeem Holcomb','144bc9671d41e613a94e6aca0d9497d43d7472b7','Moore','Castro','mollis.Integer@pretium.ca','056 9208 3007',0,'398-3262 Mauris Rd.'),(67,'Slade Jenkins','144bc9671d41e613a94e6aca0d9497d43d7472b7','Pittman','Hanson','massa.Integer@eros.com','(013125) 59806',1,'Ap #525-6218 Egestas Street'),(68,'Clementine Stephenson','144bc9671d41e613a94e6aca0d9497d43d7472b7','Adkins','Witt','natoque.penatibus.et@duilectusrutrum.ca','(016977) 6304',0,'Ap #884-5589 Purus Street'),(69,'Serina Vasquez','144bc9671d41e613a94e6aca0d9497d43d7472b7','Delacruz','Kelly','mollis.Phasellus@sociisnatoque.net','070 5853 1092',1,'P.O. Box 817, 9939 Auctor Rd.'),(70,'Magee Bowers','144bc9671d41e613a94e6aca0d9497d43d7472b7','Small','Espinoza','ac.mattis.semper@milacinia.ca','0355 891 6963',0,'9912 Egestas. Road'),(71,'Priscilla Bass','144bc9671d41e613a94e6aca0d9497d43d7472b7','Delaney','Combs','sit@metusfacilisis.co.uk','(016977) 0815',1,'646-3659 Suscipit, Ave'),(72,'Troy Hopkins','144bc9671d41e613a94e6aca0d9497d43d7472b7','Soto','Marquez','Nunc.mauris.elit@ullamcorpermagna.edu','(016401) 16948',0,'P.O. Box 614, 7519 Neque Av.'),(73,'Ivory Maynard','144bc9671d41e613a94e6aca0d9497d43d7472b7','Young','Levy','Cras@rhoncusProinnisl.edu','(0118) 033 2330',1,'541-2325 Ipsum. Rd.'),(74,'Dorothy Riddle','144bc9671d41e613a94e6aca0d9497d43d7472b7','Hoffman','Oconnor','imperdiet@porttitorinterdumSed.net','07244 613590',0,'P.O. Box 568, 3320 Pede Rd.'),(75,'Sage Benson','144bc9671d41e613a94e6aca0d9497d43d7472b7','Reyes','Acevedo','arcu@Phaselluselit.edu','07878 948907',1,'Ap #649-3792 A, St.'),(76,'Kermit Wynn','144bc9671d41e613a94e6aca0d9497d43d7472b7','Carey','Morales','tristique@auctorMauris.com','0889 731 2063',0,'4975 Vel Street'),(77,'Sydnee Dillon','144bc9671d41e613a94e6aca0d9497d43d7472b7','Patton','Coffey','vestibulum@molestie.net','0845 46 48',0,'P.O. Box 377, 2821 Donec Avenue'),(78,'Ima Sanchez','144bc9671d41e613a94e6aca0d9497d43d7472b7','Knox','Hickman','eu.eleifend.nec@semmolestiesodales.org','(016977) 6006',1,'P.O. Box 290, 4101 Lorem Street'),(79,'Prescott Salas','144bc9671d41e613a94e6aca0d9497d43d7472b7','Marshall','Mclean','et.magnis@ornareIn.edu','0969 829 6823',0,'438 Ornare, St.'),(80,'Molly Mosley','144bc9671d41e613a94e6aca0d9497d43d7472b7','Hendrix','Roach','leo.Cras@Donecfelis.co.uk','(027) 0735 5046',0,'2152 Urna St.'),(81,'Shelby Henry','144bc9671d41e613a94e6aca0d9497d43d7472b7','Stevens','Huber','purus.sapien@vehicula.co.uk','0845 46 43',1,'Ap #221-7589 Ac Rd.'),(82,'Summer Blankenship','144bc9671d41e613a94e6aca0d9497d43d7472b7','Livingston','Lane','nec@velconvallisin.net','(01500) 417939',1,'Ap #936-5375 Eget St.'),(83,'Rhonda Jones','144bc9671d41e613a94e6aca0d9497d43d7472b7','Pate','Holcomb','pede.Praesent.eu@ac.org','(01704) 885625',1,'P.O. Box 459, 2661 Tortor. Rd.'),(84,'Mona Olsen','144bc9671d41e613a94e6aca0d9497d43d7472b7','Acosta','Sampson','Quisque@vel.ca','055 6987 1089',1,'Ap #999-9406 Cras Avenue'),(85,'Callum Duke','144bc9671d41e613a94e6aca0d9497d43d7472b7','Jackson','Weeks','Donec@risus.com','0500 460875',1,'P.O. Box 512, 8542 Neque. St.'),(86,'Jonah Emerson','144bc9671d41e613a94e6aca0d9497d43d7472b7','Parrish','Hayden','ultrices@consectetuercursuset.ca','0500 156209',0,'4911 Ultrices, Rd.'),(87,'Harper Rojas','144bc9671d41e613a94e6aca0d9497d43d7472b7','Wooten','Odom','elit.a@dui.edu','0500 047434',1,'Ap #649-7203 Sociis Ave'),(88,'Zane Bridges','144bc9671d41e613a94e6aca0d9497d43d7472b7','Bernard','Case','ac.arcu.Nunc@pedePraesenteu.edu','0800 700927',1,'Ap #214-6346 Risus. Road'),(89,'Olympia England','144bc9671d41e613a94e6aca0d9497d43d7472b7','Russell','James','et@Morbi.org','(016977) 2028',1,'216-3323 Lacinia Av.'),(90,'Armando Avery','144bc9671d41e613a94e6aca0d9497d43d7472b7','Floyd','Nash','facilisis@purusgravidasagittis.net','07658 164728',0,'Ap #536-9976 Enim Avenue'),(91,'Barrett Skinner','144bc9671d41e613a94e6aca0d9497d43d7472b7','Whitfield','Cross','volutpat.Nulla.facilisis@facilisis.net','055 3429 3115',1,'Ap #727-1455 Lorem Street'),(92,'Malik Barrett','144bc9671d41e613a94e6aca0d9497d43d7472b7','Snider','Mccarthy','Aenean.gravida@Duiscursusdiam.net','(012666) 41102',1,'P.O. Box 606, 3860 Luctus Ave'),(93,'Todd Sheppard','144bc9671d41e613a94e6aca0d9497d43d7472b7','Tillman','Lynn','ultrices.sit.amet@natoquepenatibus.ca','(01313) 62245',1,'Ap #300-8603 Ac Av.'),(94,'Cameron Pollard','144bc9671d41e613a94e6aca0d9497d43d7472b7','Arnold','Roach','lectus@commodohendrerit.org','0320 178 3285',0,'P.O. Box 918, 8352 Aliquam Avenue'),(95,'Chava Leach','144bc9671d41e613a94e6aca0d9497d43d7472b7','Henson','Bauer','est@enimnectempus.net','055 3595 2447',1,'8639 A, St.'),(96,'Cheyenne Goodman','144bc9671d41e613a94e6aca0d9497d43d7472b7','Joseph','Phillips','Phasellus.ornare@euismodurna.com','0354 093 6031',1,'P.O. Box 947, 2624 Suspendisse Road'),(97,'Mollie Copeland','144bc9671d41e613a94e6aca0d9497d43d7472b7','Little','Roach','nisl.Quisque.fringilla@risusMorbimetus.com','(0161) 312 8298',0,'P.O. Box 133, 1099 At St.'),(98,'Faith Whitley','144bc9671d41e613a94e6aca0d9497d43d7472b7','Mitchell','Kirby','pellentesque.tellus@eu.com','(0171) 349 4654',1,'P.O. Box 779, 3408 Et, Avenue'),(99,'Kermit Bean','144bc9671d41e613a94e6aca0d9497d43d7472b7','Wells','Ramsey','diam@risusNunc.org','(016977) 6571',0,'2449 Eu Ave'),(100,'Jameson Mccullough','144bc9671d41e613a94e6aca0d9497d43d7472b7','Woodward','Campbell','mauris.Morbi.non@velvulputate.co.uk','076 4407 6416',1,'P.O. Box 825, 2371 Consequat, St.');
UNLOCK TABLES;

LOCK TABLES `books` WRITE;
INSERT INTO `books` VALUES (1,'eros turpis non enim. Mauris',4),(2,'sem mollis dui, in sodales',4),(3,'amet luctus vulputate, nisi sem',2),(4,'sem. Nulla interdum. Curabitur dictum.',2),(5,'est ac mattis semper, dui',5),(6,'dolor. Quisque tincidunt pede ac',5),(7,'gravida sagittis. Duis gravida. Praesent',4),(8,'Lorem ipsum dolor sit amet,',5),(9,'Vivamus rhoncus. Donec est. Nunc',2),(10,'sed dictum eleifend, nunc risus',5),(11,'pede. Nunc sed orci lobortis',4),(12,'tristique senectus et netus et',2),(13,'sapien molestie orci tincidunt adipiscing.',5),(14,'odio sagittis semper. Nam tempor',4),(15,'Vestibulum accumsan neque et nunc.',2),(16,'dignissim tempor arcu. Vestibulum ut',5),(17,'eu neque pellentesque massa lobortis',2),(18,'imperdiet, erat nonummy ultricies ornare,',4),(19,'Maecenas iaculis aliquet diam. Sed',1),(20,'tortor nibh sit amet orci.',1),(21,'malesuada vel, venenatis vel, faucibus',3),(22,'id, mollis nec, cursus a,',4),(23,'malesuada ut, sem. Nulla interdum.',5),(24,'et malesuada fames ac turpis',2),(25,'dapibus ligula. Aliquam erat volutpat.',1),(26,'adipiscing. Mauris molestie pharetra nibh.',4),(27,'turpis. Aliquam adipiscing lobortis risus.',2),(28,'felis purus ac tellus. Suspendisse',5),(29,'ante. Vivamus non lorem vitae',3),(30,'mollis lectus pede et risus.',1),(31,'pharetra. Quisque ac libero nec',1),(32,'cursus. Nunc mauris elit, dictum',2),(33,'ornare, elit elit fermentum risus,',4),(34,'fames ac turpis egestas. Aliquam',1),(35,'adipiscing lacus. Ut nec urna',1),(36,'turpis. Aliquam adipiscing lobortis risus.',4),(37,'risus. Morbi metus. Vivamus euismod',2),(38,'magnis dis parturient montes, nascetur',1),(39,'mollis vitae, posuere at, velit.',2),(40,'ultrices. Duis volutpat nunc sit',5),(41,'nec ante. Maecenas mi felis,',2),(42,'dictum sapien. Aenean massa. Integer',2),(43,'et magnis dis parturient montes,',5),(44,'turpis egestas. Aliquam fringilla cursus',1),(45,'amet, risus. Donec nibh enim,',5),(46,'fermentum risus, at fringilla purus',1),(47,'Maecenas ornare egestas ligula. Nullam',2),(48,'sem. Nulla interdum. Curabitur dictum.',5),(49,'sed sem egestas blandit. Nam',3),(50,'quis diam luctus lobortis. Class',4),(51,'felis. Nulla tempor augue ac',5),(52,'nisl sem, consequat nec, mollis',1),(53,'dui. Fusce aliquam, enim nec',2),(54,'Mauris eu turpis. Nulla aliquet.',2),(55,'Nunc quis arcu vel quam',2),(56,'rhoncus id, mollis nec, cursus',4),(57,'lectus pede et risus. Quisque',1),(58,'penatibus et magnis dis parturient',3),(59,'amet orci. Ut sagittis lobortis',3),(60,'eget nisi dictum augue malesuada',3),(61,'est tempor bibendum. Donec felis',4),(62,'ridiculus mus. Aenean eget magna.',3),(63,'Nunc sollicitudin commodo ipsum. Suspendisse',1),(64,'sapien. Cras dolor dolor, tempus',3),(65,'tempus risus. Donec egestas. Duis',5),(66,'Ut tincidunt orci quis lectus.',5),(67,'nascetur ridiculus mus. Donec dignissim',4),(68,'tincidunt orci quis lectus. Nullam',4),(69,'nisl elementum purus, accumsan interdum',1),(70,'sem semper erat, in consectetuer',3),(71,'risus. Morbi metus. Vivamus euismod',4),(72,'sapien, cursus in, hendrerit consectetuer,',5),(73,'ornare placerat, orci lacus vestibulum',5),(74,'adipiscing. Mauris molestie pharetra nibh.',2),(75,'semper pretium neque. Morbi quis',3),(76,'in consectetuer ipsum nunc id',2),(77,'ac, feugiat non, lobortis quis,',3),(78,'Etiam vestibulum massa rutrum magna.',5),(79,'tristique neque venenatis lacus. Etiam',4),(80,'sit amet risus. Donec egestas.',4),(81,'Fusce mi lorem, vehicula et,',2),(82,'magna. Sed eu eros. Nam',5),(83,'ac mattis ornare, lectus ante',2),(84,'Nunc mauris sapien, cursus in,',3),(85,'porta elit, a feugiat tellus',5),(86,'Maecenas malesuada fringilla est. Mauris',5),(87,'vulputate mauris sagittis placerat. Cras',1),(88,'feugiat nec, diam. Duis mi',3),(89,'risus a ultricies adipiscing, enim',5),(90,'Duis sit amet diam eu',1),(91,'Curabitur consequat, lectus sit amet',1),(92,'egestas rhoncus. Proin nisl sem,',1),(93,'magna. Sed eu eros. Nam',4),(94,'erat semper rutrum. Fusce dolor',2),(95,'laoreet posuere, enim nisl elementum',2),(96,'vitae erat vel pede blandit',3),(97,'placerat. Cras dictum ultricies ligula.',3),(98,'vitae, aliquet nec, imperdiet nec,',2),(99,'Nunc mauris sapien, cursus in,',2),(100,'ullamcorper. Duis cursus, diam at',2);
UNLOCK TABLES;

LOCK TABLES `Books_ISBNs` WRITE;
INSERT INTO `Books_ISBNs` VALUES ('1',1,72,1987,471,75,17),('10',10,3,1968,105,50,30),('100',100,89,1958,150,90,23),('11',11,78,1951,94,77,25),('12',12,59,2001,181,97,21),('13',13,32,1995,372,56,11),('14',14,37,1984,479,60,38),('15',15,63,1965,54,91,25),('16',16,35,1974,271,53,36),('17',17,81,1961,347,68,20),('18',18,97,1986,305,78,16),('19',19,97,1975,413,53,36),('2',2,27,2012,276,61,35),('20',20,29,1978,227,85,22),('21',21,14,1975,281,60,34),('22',22,47,1974,89,51,28),('23',23,3,2010,55,51,12),('24',24,2,1992,446,84,18),('25',25,46,1966,426,100,33),('26',26,66,2011,56,98,18),('27',27,42,1973,78,84,19),('28',28,84,1953,377,60,30),('29',29,64,1981,421,57,28),('3',3,76,2013,55,67,13),('30',30,50,1971,327,64,22),('31',31,29,2003,335,86,19),('32',32,93,1978,132,90,33),('33',33,37,1961,63,91,25),('34',34,1,2017,423,51,13),('35',35,28,1997,436,82,18),('36',36,5,1985,203,78,40),('37',37,59,1952,165,89,35),('38',38,82,1968,424,81,22),('39',39,3,2009,212,60,19),('4',4,17,1993,462,75,12),('40',40,92,1994,399,65,14),('41',41,71,1984,107,85,10),('42',42,77,2013,318,86,29),('43',43,86,1959,255,89,28),('44',44,62,1967,129,84,17),('45',45,39,1955,334,88,13),('46',46,19,1960,381,73,10),('47',47,72,2000,432,76,19),('48',48,42,1979,374,89,12),('49',49,7,1976,148,66,11),('5',5,78,1968,311,96,28),('50',50,74,1980,439,100,13),('51',51,12,1964,254,76,34),('52',52,63,1979,182,91,39),('53',53,90,1951,101,84,30),('54',54,50,2017,172,55,26),('55',55,39,1993,276,50,20),('56',56,64,2002,319,95,12),('57',57,7,1956,129,90,38),('58',58,53,2004,313,74,32),('59',59,86,1995,494,75,18),('6',6,4,1998,397,69,17),('60',60,39,1972,475,58,20),('61',61,10,1983,149,60,12),('62',62,70,1989,302,85,30),('63',63,81,1993,383,88,40),('64',64,61,1978,325,97,27),('65',65,47,1993,396,53,28),('66',66,78,1993,354,89,33),('67',67,68,1999,87,56,22),('68',68,70,1961,451,77,31),('69',69,38,2000,103,66,11),('7',7,29,1995,464,73,36),('70',70,56,1971,126,95,17),('71',71,47,1958,349,89,11),('72',72,17,1977,323,100,22),('73',73,95,2000,311,96,26),('74',74,69,1958,410,85,17),('75',75,37,1981,335,71,35),('76',76,76,1982,472,77,22),('77',77,38,1965,441,64,14),('78',78,80,1954,192,86,36),('79',79,46,2005,309,90,40),('8',8,71,1988,61,99,24),('80',80,36,1950,224,69,36),('81',81,47,2005,74,52,25),('82',82,22,1960,189,90,19),('83',83,34,1961,100,51,25),('84',84,64,2011,492,58,16),('85',85,21,1951,411,88,19),('86',86,33,2015,141,66,20),('87',87,40,2010,458,52,12),('88',88,84,1965,201,85,29),('89',89,71,1960,337,59,14),('9',9,69,1997,69,56,30),('90',90,65,1989,290,81,34),('91',91,84,2014,359,93,13),('92',92,90,1993,458,79,34),('93',93,64,1996,369,54,33),('94',94,81,1989,486,56,12),('95',95,16,1959,271,85,31),('96',96,78,1965,353,91,15),('97',97,3,1963,313,80,38),('98',98,8,1961,461,81,33),('99',99,62,2005,414,89,27);
UNLOCK TABLES;

LOCK TABLES `Authors` WRITE;
INSERT INTO `Authors` VALUES (1,'Keegan J. Hunt'),(1,'Lucy Z. Kelly'),(1,'Madaline R. Petty'),(2,'Josiah F. Murray'),(2,'Leo D. Calderon'),(3,'Kato H. Larsen'),(4,'Graham U. Lindsay'),(5,'Alvin G. Holder'),(5,'Lee L. Avila'),(5,'Sarah F. Owen'),(6,'Grady G. Kline'),(6,'Simone F. Graves'),(6,'Vaughan U. Guerra'),(7,'Ainsley T. Key'),(8,'Eleanor X. Hanson'),(9,'Chiquita Q. Alexander'),(10,'Keelie E. Stephens'),(11,'Abbot P. Bonner'),(11,'Axel Z. Huber'),(11,'Chase X. Hickman'),(11,'Troy N. Huber'),(12,'Callie Z. Peck'),(12,'Gray X. Lowery'),(13,'Hanae X. Rivera'),(13,'Nyssa O. Workman'),(14,'Derek V. Sanford'),(14,'Finn E. Santos'),(14,'Reese N. Singleton'),(14,'Reuben O. Briggs'),(15,'Philip L. Frank'),(15,'Vaughan D. Carrillo'),(16,'Melodie P. Burton'),(17,'Quail T. Blanchard'),(18,'Hammett S. Marquez'),(18,'Olga A. Mckee'),(18,'Walter V. Huff'),(19,'Arthur F. Ochoa'),(19,'Kiayada M. Bird'),(19,'Odessa A. Orr'),(20,'Casey G. Gaines'),(21,'Amanda S. Pierce'),(21,'Daryl C. Dillard'),(21,'Rosalyn Z. Gonzalez'),(21,'Steven S. Blevins'),(22,'Basil L. Russell'),(22,'Constance S. Cash'),(22,'Demetria T. Conner'),(22,'Xandra Y. Mccullough'),(23,'Cathleen C. Hyde'),(23,'Erich K. Gentry'),(24,'Diana X. Black'),(24,'Faith Z. Pickett'),(24,'Laurel Z. Everett'),(25,'Astra B. Smith'),(25,'Gil Y. Mercado'),(26,'Sophia U. Davidson'),(27,'Audrey Q. Huber'),(27,'Fleur J. Hester'),(28,'Addison B. Short'),(28,'Fatima D. Molina'),(28,'Kyle X. Carver'),(28,'Sydnee P. Gallagher'),(28,'Ulla M. Hancock'),(29,'Mia M. Wolf'),(30,'Willa V. Zimmerman'),(31,'Andrew H. Olson'),(31,'Hamish I. Leach'),(31,'Kyle G. Frye'),(32,'Adrian U. Ellison'),(32,'Ayanna K. Joyner'),(32,'Wing X. Powers'),(33,'Tanek U. Fernandez'),(33,'Yael M. Day'),(34,'Harrison J. Mcknight'),(34,'Mara E. Hudson'),(35,'Sharon X. Hale'),(35,'Zephania B. Merritt'),(36,'Lysandra W. Hampton'),(37,'Justine E. Bray'),(37,'Kyla O. Slater'),(38,'John W. Kidd'),(38,'Tamekah P. Hanson'),(38,'Tanya I. Mooney'),(39,'Clare S. Martin'),(39,'Claudia R. Coffey'),(40,'Dahlia X. Lowe'),(41,'Charlotte U. Adams'),(41,'Melvin P. Carroll'),(41,'Mohammad C. Pugh'),(42,'Chloe B. Garrison'),(42,'Hilda T. Carver'),(42,'Juliet B. Griffith'),(43,'Brennan S. Pratt'),(43,'Sigourney J. Gardner'),(44,'Darius N. Camacho'),(45,'Sybil Z. Gardner'),(46,'Cherokee C. Daugherty'),(46,'Hammett U. Calhoun'),(46,'Kevyn M. Valenzuela'),(47,'Joelle W. Fuller'),(48,'Eaton O. Rivas'),(48,'Geraldine Q. Combs'),(49,'Joel K. Sears'),(49,'Kaitlin T. Key'),(49,'Timon O. Porter'),(50,'Harriet G. Wallace'),(51,'Penelope W. Rivas'),(51,'Summer D. Nguyen'),(52,'Catherine W. Dejesus'),(52,'Troy V. William'),(53,'Blaze Z. Gonzalez'),(53,'Jasper R. Tanner'),(53,'Mannix M. Oneal'),(54,'Dorothy M. Anthony'),(54,'Reece S. Rowe'),(55,'Kristen P. Adams'),(55,'TaShya G. Leon'),(56,'Dora D. Wilcox'),(57,'Rahim G. Lester'),(58,'Amery T. Lara'),(58,'Hedda A. Hurley'),(59,'Dane G. Heath'),(60,'Wesley L. Massey'),(61,'Darrel K. Watkins'),(61,'Madison O. Gonzales'),(61,'Yoshi M. Dodson'),(62,'Armand J. Maddox'),(62,'Eric H. Hobbs'),(62,'Myles Z. Stephens'),(62,'Steven R. Cooke'),(63,'Adria T. Moody'),(63,'Xyla P. Rodriquez'),(64,'Octavius F. Hickman'),(65,'Latifah B. Figueroa'),(66,'Risa F. Todd'),(67,'Heidi Q. Todd'),(68,'Eden Q. Alvarez'),(68,'Jack V. Carlson'),(68,'Yolanda P. Reed'),(69,'Elliott W. Tran'),(69,'Winter Q. Avery'),(70,'Barry B. Vincent'),(70,'Ezekiel E. Boyle'),(71,'August F. Delgado'),(72,'McKenzie I. Roth'),(73,'Sebastian O. England'),(74,'Jakeem U. Le'),(74,'Kathleen W. Mejia'),(75,'Veda O. Rosales'),(76,'Justina A. Stevens'),(77,'Keiko G. Thomas'),(77,'Kellie K. Beard'),(78,'Calvin J. Jenkins'),(79,'Cecilia O. Bird'),(79,'Dakota M. Holloway'),(80,'Clayton L. Webster'),(80,'Rafael M. Hull'),(81,'Vaughan C. Mccoy'),(82,'Belle B. Mcpherson'),(82,'Channing H. Campbell'),(82,'Mariam C. Padilla'),(82,'Maxwell C. Donaldson'),(83,'Alfonso V. William'),(83,'Anjolie C. Craig'),(84,'Armando F. Raymond'),(84,'Macon N. Cummings'),(85,'Hammett C. Gibson'),(86,'Regina D. Greer'),(87,'Amanda C. Mcneil'),(87,'Charles J. Russo'),(87,'Denton N. Randall'),(88,'Jeremy X. Battle'),(88,'Katelyn X. Washington'),(88,'Nora D. Willis'),(89,'Hedda D. Ward'),(89,'Ryder C. Garrison'),(90,'Joelle C. Evans'),(91,'Jacob R. Durham'),(91,'Kevin D. Howell'),(92,'Ayanna U. Pena'),(92,'Hilda B. Kidd'),(92,'Nora W. White'),(92,'Shaeleigh R. Blankenship'),(93,'Iola B. Rutledge'),(94,'Clarke J. Munoz'),(95,'Daria K. Randall'),(95,'Quincy C. Gaines'),(95,'Quyn P. Gill'),(96,'Bevis U. Sargent'),(96,'Walker Z. Holmes'),(96,'Zane F. Castro'),(97,'Laith N. Poole'),(97,'Lillith W. Blevins'),(98,'Kendall G. Miller'),(99,'Blaze L. Frederick'),(99,'Celeste L. Schroeder'),(99,'Maryam P. Cruz'),(99,'Phoebe W. Neal'),(100,'Conan P. Curry'),(100,'Dalton K. Suarez');
UNLOCK TABLES;

LOCK TABLES `orders` WRITE;
INSERT INTO `orders` VALUES (1,7,'2017-11-18 19:08:30','378282246310005','2018-04-30'),(2,35,'2017-12-30 07:11:15',' 371449635398431','2018-05-18'),(3,22,'2018-04-23 01:25:16',' 378734493671000','2017-10-02'),(4,32,'2017-11-25 16:13:12',' 5610591081018250','2017-12-24'),(5,30,'2017-12-26 16:58:34',' 30569309025904','2018-01-28'),(6,21,'2017-07-04 12:39:25',' 38520000023237','2018-02-22'),(7,13,'2017-12-04 15:04:10',' 5555555555554444','2017-09-22'),(8,7,'2017-11-06 17:42:15',' 5105105105105100','2017-07-31'),(9,43,'2017-11-11 04:44:22',' 5019717010103742','2017-09-04'),(10,20,'2017-11-29 21:34:56',' 6331101999990016','2018-05-31'),(11,13,'2018-01-05 03:17:31','378282246310005','2018-01-14'),(12,12,'2018-02-21 19:57:04',' 371449635398431','2018-04-29'),(13,23,'2018-04-23 12:00:20',' 378734493671000','2018-03-25'),(14,48,'2018-03-02 10:25:48',' 5610591081018250','2017-09-06'),(15,1,'2017-06-11 16:04:24',' 30569309025904','2018-03-08'),(16,12,'2017-08-16 06:12:10',' 38520000023237','2018-04-20'),(17,15,'2017-06-26 20:43:17',' 5555555555554444','2017-08-17'),(18,31,'2017-12-04 00:00:52',' 5105105105105100','2018-06-02'),(19,3,'2017-09-08 04:56:32',' 5019717010103742','2017-06-13'),(20,33,'2018-05-05 13:06:53',' 6331101999990016','2018-01-17'),(21,36,'2018-02-25 21:50:07','378282246310005','2017-10-09'),(22,28,'2017-06-29 02:44:42',' 371449635398431','2017-12-26'),(23,30,'2018-01-21 14:14:38',' 378734493671000','2018-04-12'),(24,30,'2017-11-27 12:10:45',' 5610591081018250','2017-09-11'),(25,28,'2017-11-16 07:46:50',' 30569309025904','2017-09-21'),(26,26,'2017-09-01 07:17:59',' 38520000023237','2017-10-29'),(27,40,'2017-06-24 17:08:50',' 5555555555554444','2017-07-09'),(28,22,'2018-01-04 14:52:12',' 5105105105105100','2017-12-31'),(29,27,'2018-05-22 08:29:58',' 5019717010103742','2017-11-15'),(30,7,'2017-12-11 05:05:10',' 6331101999990016','2017-06-11'),(31,19,'2017-10-08 06:46:30','378282246310005','2017-12-31'),(32,32,'2018-04-28 10:45:14',' 371449635398431','2018-02-04'),(33,42,'2018-04-20 22:13:45',' 378734493671000','2018-03-18'),(34,39,'2018-05-21 07:28:47',' 5610591081018250','2018-04-11'),(35,27,'2017-08-28 14:29:02',' 30569309025904','2017-07-14'),(36,2,'2018-04-01 11:54:31',' 38520000023237','2017-08-23'),(37,16,'2017-12-02 16:28:58',' 5555555555554444','2018-01-11'),(38,47,'2018-01-13 01:23:17',' 5105105105105100','2017-07-01'),(39,45,'2018-01-09 14:37:45',' 5019717010103742','2018-03-28'),(40,43,'2018-02-03 00:37:54',' 6331101999990016','2018-05-11'),(41,23,'2018-01-24 23:39:29','378282246310005','2018-05-08'),(42,11,'2017-11-09 22:14:45',' 371449635398431','2017-12-28'),(43,11,'2017-11-04 23:52:13',' 378734493671000','2017-11-25'),(44,22,'2018-03-24 13:16:59',' 5610591081018250','2018-04-03'),(45,23,'2018-02-07 21:15:39',' 30569309025904','2017-07-11'),(46,12,'2018-04-24 07:37:57',' 38520000023237','2018-03-30'),(47,42,'2017-08-27 07:50:47',' 5555555555554444','2018-02-20'),(48,43,'2018-01-13 09:26:52',' 5105105105105100','2017-11-23'),(49,30,'2017-11-07 07:17:37',' 5019717010103742','2017-06-30'),(50,13,'2017-08-17 10:47:37',' 6331101999990016','2017-06-22'),(51,34,'2017-12-04 21:06:14','378282246310005','2018-04-25'),(52,36,'2017-08-23 08:05:47',' 371449635398431','2017-10-14'),(53,38,'2017-09-21 17:57:52',' 378734493671000','2017-07-19'),(54,5,'2018-05-03 17:43:50',' 5610591081018250','2018-02-03'),(55,11,'2017-07-09 11:01:33',' 30569309025904','2017-09-17'),(56,13,'2018-01-21 14:37:20',' 38520000023237','2017-11-08'),(57,15,'2018-05-30 09:05:11',' 5555555555554444','2018-02-04'),(58,6,'2018-05-04 10:39:36',' 5105105105105100','2017-06-15'),(59,24,'2018-06-06 03:54:48',' 5019717010103742','2018-03-25'),(60,11,'2017-07-28 01:45:28',' 6331101999990016','2017-09-19'),(61,12,'2017-12-06 23:57:54','378282246310005','2017-11-10'),(62,50,'2018-02-27 13:20:01',' 371449635398431','2018-01-28'),(63,41,'2017-08-21 10:07:26',' 378734493671000','2018-03-13'),(64,33,'2017-12-19 03:25:16',' 5610591081018250','2018-05-03'),(65,7,'2018-03-03 02:55:50',' 30569309025904','2017-09-13'),(66,33,'2017-07-25 15:53:52',' 38520000023237','2018-02-26'),(67,43,'2018-02-21 04:06:01',' 5555555555554444','2017-10-19'),(68,22,'2017-12-23 19:56:15',' 5105105105105100','2018-04-30'),(69,31,'2017-08-07 21:30:36',' 5019717010103742','2018-05-14'),(70,34,'2017-12-06 20:40:11',' 6331101999990016','2018-04-01'),(71,50,'2017-09-22 09:51:17','378282246310005','2017-11-16'),(72,37,'2018-01-20 03:00:03',' 371449635398431','2017-10-09'),(73,12,'2018-02-05 17:41:57',' 378734493671000','2017-09-05'),(74,42,'2017-06-23 10:35:40',' 5610591081018250','2018-02-17'),(75,8,'2018-01-26 18:07:23',' 30569309025904','2017-06-22'),(76,21,'2017-10-11 00:21:06',' 38520000023237','2017-08-28'),(77,39,'2018-01-02 06:13:58',' 5555555555554444','2018-03-17'),(78,45,'2017-11-16 18:00:38',' 5105105105105100','2018-05-20'),(79,19,'2017-10-21 21:29:39',' 5019717010103742','2018-04-26'),(80,27,'2017-10-04 11:34:40',' 6331101999990016','2018-04-03'),(81,4,'2018-02-27 20:17:04','378282246310005','2017-11-25'),(82,43,'2017-07-31 23:26:00',' 371449635398431','2018-02-02'),(83,44,'2017-06-21 07:33:51',' 378734493671000','2017-12-13'),(84,22,'2017-06-17 14:34:14',' 5610591081018250','2018-02-07'),(85,21,'2017-12-23 14:02:40',' 30569309025904','2018-05-19'),(86,26,'2017-12-29 17:11:37',' 38520000023237','2018-03-18'),(87,22,'2017-12-06 02:49:48',' 5555555555554444','2017-11-09'),(88,33,'2017-08-12 08:39:12',' 5105105105105100','2018-04-22'),(89,48,'2017-06-11 01:55:00',' 5019717010103742','2017-11-16'),(90,41,'2018-01-28 20:40:14',' 6331101999990016','2018-02-23'),(91,8,'2018-01-28 09:56:22','378282246310005','2018-06-09'),(92,50,'2018-04-18 14:25:42',' 371449635398431','2017-10-21'),(93,43,'2017-12-25 17:56:10',' 378734493671000','2017-08-10'),(94,8,'2017-08-27 08:51:43',' 5610591081018250','2017-08-28'),(95,40,'2017-07-09 19:24:57',' 30569309025904','2017-08-08'),(96,45,'2017-11-11 00:32:30',' 38520000023237','2018-05-21'),(97,45,'2017-09-12 01:20:15',' 5555555555554444','2018-05-30'),(98,24,'2018-03-22 15:53:50',' 5105105105105100','2017-08-04'),(99,50,'2017-06-29 13:34:55',' 5019717010103742','2017-12-15'),(100,26,'2018-06-08 03:54:46',' 6331101999990016','2017-09-21');
UNLOCK TABLES;

LOCK TABLES `purchases` WRITE;
INSERT INTO `purchases` VALUES (1,'43',2,1066),(2,'31',8,4056),(3,'61',9,322),(4,'25',7,2899),(5,'36',9,4454),(6,'63',1,2078),(7,'91',5,3691),(8,'50',8,1143),(9,'34',5,2905),(10,'33',10,3360),(11,'79',8,1372),(12,'93',3,1124),(13,'88',4,1403),(14,'86',4,1954),(15,'71',5,4810),(16,'24',9,4989),(17,'82',6,4590),(18,'21',7,3564),(19,'77',4,3002),(20,'83',10,2099),(21,'56',10,1294),(22,'1',6,4270),(23,'46',6,1246),(24,'23',3,3694),(25,'95',9,3269),(26,'20',2,1802),(27,'82',8,4077),(28,'50',2,635),(29,'21',6,849),(30,'81',9,673),(31,'69',6,4326),(32,'53',6,2476),(33,'5',1,656),(34,'68',4,91),(35,'3',9,3084),(36,'76',8,4495),(37,'49',7,4140),(38,'11',9,3991),(39,'55',1,2048),(40,'97',3,158),(41,'53',5,422),(42,'70',3,3968),(43,'24',1,2644),(44,'70',7,938),(45,'69',10,4057),(46,'48',8,1470),(47,'67',2,3632),(48,'82',7,3447),(49,'30',4,1651),(50,'80',6,2227),(51,'99',10,439),(52,'47',5,3719),(53,'100',6,2116),(54,'62',6,4632),(55,'9',3,2758),(56,'87',5,2022),(57,'7',7,3677),(58,'10',10,4717),(59,'34',2,1848),(60,'63',6,3669),(61,'29',1,1341),(62,'52',10,3556),(63,'90',7,736),(64,'19',3,140),(65,'82',2,351),(66,'42',1,4251),(67,'66',8,3337),(68,'3',6,4930),(69,'7',5,593),(70,'18',10,3671),(71,'13',1,2771),(72,'73',9,3114),(73,'27',5,1190),(74,'81',8,1268),(75,'75',1,2462),(76,'8',6,3447),(77,'52',7,3226),(78,'33',6,3958),(79,'55',1,3694),(80,'48',6,293),(81,'11',10,256),(82,'47',10,1956),(83,'25',9,2601),(84,'13',10,234),(85,'48',10,1788),(86,'84',1,571),(87,'56',7,4240),(88,'53',7,2845),(89,'81',3,1547),(90,'69',6,2191),(91,'57',1,4881),(92,'11',4,811),(93,'17',4,4331),(94,'20',4,3819),(95,'1',5,1793),(96,'48',5,133),(97,'44',2,989),(98,'80',10,3355),(99,'8',2,3297),(100,'62',1,3605);
UNLOCK TABLES;

INSERT INTO `book_store`.`users` (`User_id`, `User_Name`, `Password`, `First_Name`, `last_Name`, `Email`, `Phone_Number`, `Status_id`, `Default_Shipping_Address`) VALUES ('114', 'd', 'd', 'd', 'd', 'd@k.com', '123', '1', 'd');


-- insert into categories values (1,'Science');
-- insert into categories values (2,'History');
-- insert into categories values (3,'Religion');
-- insert into categories values (4,'Art');
-- insert into categories values (5,'Geography');
-- 
-- insert into publishers values (1,'LEquipe','180 street1 st','012013');
-- insert into publishers values (2,'gallimard','180 street2 st','012413');
-- insert into publishers values (3,'Larousse','180 street3 st','012513');
-- 
-- INSERT INTO `books` (`Book_id`,`Title`,`category_id`) VALUES (8,'newTitle2',1);
-- INSERT INTO `books` (`Book_id`,`Title`,`category_id`) VALUES (10,'newTitle',1);
-- INSERT INTO `books` (`Book_id`,`Title`,`category_id`) VALUES (11,'title2',1);
-- 
-- INSERT INTO `Books_ISBNs` (`ISBN`,`Book_id`,`Publisher_id`,`Publication_Year`,`Selling_price`,`Available_Quantity`,`Threshold`) VALUES ('lm1235',10,1,2016,50.5,45,3);
-- INSERT INTO `Books_ISBNs` (`ISBN`,`Book_id`,`Publisher_id`,`Publication_Year`,`Selling_price`,`Available_Quantity`,`Threshold`) VALUES ('po5321',11,1,1999,2,30,4);
-- INSERT INTO `Books_ISBNs` (`ISBN`,`Book_id`,`Publisher_id`,`Publication_Year`,`Selling_price`,`Available_Quantity`,`Threshold`) VALUES ('xxxxx',8,1,2018,50.7,85,3);
-- 
-- insert into status_menu values (0,'manager');
-- insert into status_menu values (1,'customer');
-- 
-- INSERT INTO `Users` (`User_id`,`User_Name`,`Password`,`First_Name`,`last_Name`,`Email`,`Phone_Number`,`Status_id`,`Default_Shipping_Address`) VALUES (1,'salma H','7ea53dce4fa0f0abbe8eaa63717e2fed5f193d31','newFName','newLName','newEmail@newEmail.com','newPhone',1,'changeDefaultShippingAddress');
-- INSERT INTO `Users` (`User_id`,`User_Name`,`Password`,`First_Name`,`last_Name`,`Email`,`Phone_Number`,`Status_id`,`Default_Shipping_Address`) VALUES (2,'fares3','144bc9671d41e613a94e6aca0d9497d43d7472b7','aaaaaa','bbbbbbb','fares3@fares.com','93999',1,'fares3');
-- INSERT INTO `Users` (`User_id`,`User_Name`,`Password`,`First_Name`,`last_Name`,`Email`,`Phone_Number`,`Status_id`,`Default_Shipping_Address`) VALUES (3,'dal_CH','144bc9671d41e613125655880d9497d43d7472b7','aasaaa','bbmbbbb','dol3@mail.com','93999',1,'default');
-- 
-- INSERT INTO `orders` (`Id`,`User_id`,`Order_Time`,`credit_card_no`,`Credit_Expiry_Date`) VALUES (3,1,'2018-05-14 16:03:19','545335','2018-05-14');
-- INSERT INTO `orders` (`Id`,`User_id`,`Order_Time`,`credit_card_no`,`Credit_Expiry_Date`) VALUES (2,2,'2018-05-14 16:03:19','548335','2018-05-14');
-- INSERT INTO `orders` (`Id`,`User_id`,`Order_Time`,`credit_card_no`,`Credit_Expiry_Date`) VALUES (4,3,'2018-05-14 16:03:19','545035','2018-05-14');
-- 
-- 
-- INSERT INTO `purchases` (`Order_id`,`ISBN`,`no_of_copies`,`TotalPrice`) VALUES (3,'lm1235',5,250);
-- INSERT INTO `purchases` (`Order_id`,`ISBN`,`no_of_copies`,`TotalPrice`) VALUES (3,'po5321',6,250);
-- 
-- INSERT INTO `purchases` (`Order_id`,`ISBN`,`no_of_copies`,`TotalPrice`) VALUES (2,'lm1235',5,250);
-- INSERT INTO `purchases` (`Order_id`,`ISBN`,`no_of_copies`,`TotalPrice`) VALUES (2,'po5321',6,250);
-- 
-- 
-- INSERT INTO `purchases` (`Order_id`,`ISBN`,`no_of_copies`,`TotalPrice`) VALUES (4,'lm1235',5,250);
-- INSERT INTO `purchases` (`Order_id`,`ISBN`,`no_of_copies`,`TotalPrice`) VALUES (4,'po5321',6,250);
-- 


-- select* from authors where Author_Name='John F. Kennedy';


-- SET FOREIGN_KEY_CHECKS =0;
-- INSERT INTO `book_store`.`books_isbns` (`ISBN`, `Book_id`, `Publisher_id`, `Publication_Year`, `Selling_price`, `Available_Quantity`, `Threshold`) VALUES ('1560', '1', '1', '2016', '500', '12', '3');
-- insert into manager_order values (1,'1560',10);
-- insert into manager_order values (2,'1560',12);
-- 
-- SET FOREIGN_KEY_CHECKS =1;

-- SELECT * FROM books_isbns
-- WHERE MATCH(ISBN) AGAINST ('1560');

