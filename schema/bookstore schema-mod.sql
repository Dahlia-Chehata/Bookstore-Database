create schema if not exists book_store;
use book_store;
create table if not exists book_store.Categories(
	category_id	int primary key Not Null auto_increment,			
	Category_Name   varchar (40) unique
);
create table if not exists book_store.Publishers(
	Publisher_id		int primary key Not Null auto_increment,	
	Publisher_Name      varchar (40) not null,
	Publisher_Address   varchar(40) null,
	Publisher_Telephone   varchar(40) null
);

create table if not exists book_store.books(
	Book_id		int primary key Not Null auto_increment,	
	Title       varchar(40) not null,
	category_id  int not null,
    constraint books_category_fk foreign key (category_id) references Categories(category_id)
);

create table if not exists book_store.Books_ISBNs(
	ISBN		varchar (40)Primary Key Not Null,
	Book_id      int not null,
	Publisher_id  int not null, 	
	Publication_Year int not null,   
	Selling_price   double not null,
	Available_Quantity int not null, 
	Threshold int not null,
    
    constraint books_isbns_fk foreign key (Book_id) references books(book_id),
	constraint isbns_publishers_fk foreign key (Publisher_id) references Publishers(Publisher_id)
);

create table if not exists book_store.Authors(
	Book_id	 int not null,				
	Author_Name	 varchar (40) not null ,			
    constraint authors_pk primary key (Book_id,Author_Name),
	constraint books_authors_fk foreign key (Book_id) references books(book_id)
);
create table if not exists book_store.Status_Menu (
	Status_id int primary key not null auto_increment,      -- <1 - 0> 		
	Permission  varchar (40) unique not null                -- <manager - customer>
);	

create table if not exists book_store.Users(
	User_id  int primary key Not Null auto_increment,	
	User_Name varchar (40) not null,
	Password  varchar(40) not null,
	First_Name varchar(40) not null,
	last_Name varchar(40) not null,
	Email     varchar (40) unique  null,
	Phone_Number varchar (40),
	Status_id   int not null,				
	Default_Shipping_Address varchar (40) not null,

    constraint users_status_fk foreign key (status_id) references Status_Menu (status_id)
);
	
create table if not exists book_store.orders(

  Id int primary key Not Null auto_increment,	
  User_id int not null,
  Order_Time Timestamp,
  credit_card_no varchar (40) not null,
  Credit_Expiry_Date date not null,

  constraint orders_users_fk foreign key (user_id) references Users (user_id)
);
create table if not exists book_store.purchases(

	Order_id   int not null,
	ISBN    varchar (40) not null,
    no_of_copies int,
	TotalPrice double,

    constraint purchases_pk primary key (Order_id,ISBN),
	constraint purchases_Order_id_fk foreign key (Order_id) references orders (Id),
	constraint purchases_isbn_fk foreign key (ISBN) references books_ISBNs(ISBN)

);

create table if not exists book_store.manager_order(
    mgr_order_id  int not null auto_increment,		
	ISBN    varchar (40) not null,
	no_of_copies int,
    confirmed boolean not null,
  constraint manager_order_pk primary key (mgr_order_id,ISBN),
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
	  insert into book_store.manager_order (ISBN,no_of_copies,confirmed) values(new.ISBN,20,false);
      else
       update book_store.manager_order
       set no_of_copies = no_of_copies+20
       where ISBN= NEW.ISBN;
       END IF;
   END IF;
END$$
DELIMITER ;

-- check if quantity is less than a threshold after insertion
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
	  insert into book_store.manager_order (ISBN,no_of_copies,confirmed) values(new.ISBN,20,false);
      else
       update book_store.manager_order
       set no_of_copies = no_of_copies+20
       where ISBN= NEW.ISBN;
       END IF;
   END IF;
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
