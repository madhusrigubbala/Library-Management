create database librarymanagement;
use librarymanagement;
create table student(
       id int auto_increment primary key,
       regno varchar(20),
       name varchar(20),
       dept varchar(20),
       password varchar(20));
create table book(
      bookId integer(20),
      bookName varchar(20),
      bookAuthor varchar(20),
      bookGenre varchar(20),
      bookPublications varchar(20));

insert into student values(1,"20A21A04C9","Madhu","ECE","root");
insert into student values(2,"20A21A0416","Harun","ECE","root2");
insert into student values(3,"20A21A0486","munni","ECE","root3");
insert into student values(4,"20A21A0487","Devi","ECE","root4");
select * from student;


insert into book values(1,"Ramayanam","valmiki","mythology","nethaji");
insert into book values(2,"The Quran","Muhammad","Religious literature","nethaji");
insert into book values(3,"My Days","R.K.Narayan","Autobiography","nethaji");
insert into book values(4,"Harry Potter","J. K. Rowling","Fantasy literature","nethaji");

select * from book;

drop table book;


set sql_safe_updates=0;
ALTER TABLE book
ADD COLUMN dueDate DATE,
ADD COLUMN reminderSent BOOLEAN DEFAULT FALSE;
update book set dueDate="2023-08-02" where bookId=1;
ALTER TABLE book
ADD COLUMN latefee double;
SELECT CURDATE();
update book  set dueDate=curdate()  ;
 SELECT CURDATE() 'start date',
 DATE_ADD(CURDATE(),INTERVAL 1 WEEK) 'one week later';


update book set bookId=3 where latefee=null;
delete from book where bookId=2;
ALTER TABLE book
ADD COLUMN available boolean default true;
select * from book;
select * from student;


update book set bookName="Aca",bookAuthor="acac",bookGenre="myth",bookPublication="nethaji" where bookId=2;