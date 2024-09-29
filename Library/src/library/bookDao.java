package library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
public class bookDao {
	public bookDao(Connection conn) {
		super();
		this.conn = conn;
	}
	private Connection conn;
	
	public boolean addBook(book b)throws Exception {
		boolean f = false;
		try {
			String query = "insert into book(bookId,bookName,bookAuthor,bookGenre,bookPublications) values(?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, b.getBookId());
			ps.setString(2,b.getBookName());
			ps.setString(3,b.getBookAuthor());
			ps.setString(4,b.getBookGenre());
			ps.setString(5,b.getBookPublication());
			
			int i = ps.executeUpdate();
			if(i==1) {
				f = true;
			}
		}
			catch(Exception e) {
				e.printStackTrace();
				
			}
			return f;
			
			
		}
		public boolean editBook(book b) throws Exception {
			boolean f = false;
			try {
				PreparedStatement ps = conn.prepareStatement("update book set bookId=?,bookName=?,bookAuthor=?,bookGenre=?,bookPublications=?");
				ps.setInt(1,b.getBookId());
				ps.setString(2,b.getBookName());
				ps.setString(3,b.getBookAuthor());
				ps.setString(4,b.getBookGenre());
				ps.setString(5,b.getBookPublication());
				
				int i = ps.executeUpdate();
				if(i==1) {
					f = true;
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			return f;
		}
		public boolean deleteBook(int bookId)  throws Exception{
			boolean f = false;
			try {
				PreparedStatement ps = conn.prepareStatement("delete from book where bookId=?");
				ps.setInt(1, bookId);
				int i = ps.executeUpdate();
				if(i>=1) {
					f = true;
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			return f;
		}
		public book viewBook(int bookId) throws Exception {
			book b = null;
			try {
				PreparedStatement ps = conn.prepareStatement("select * from book where bookId=?");
				ps.setInt(1, bookId);;
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					b = new book();
					b.setBookId(rs.getInt(1));
					b.setBookName(rs.getString(2));
					b.setBookAuthor(rs.getString(3));
					b.setBookGenre(rs.getString(4));
					b.setBookPublication(rs.getString(5));
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			return b;
			
		}
		public List<book> getAllBooks() throws Exception{
			List<book> list = new ArrayList<book>();
			book b = null;
			try {
				PreparedStatement ps=conn.prepareStatement("select * from book");
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					b = new book();
					b.setBookId(rs.getInt(1));
					b.setBookName(rs.getString(2));
					b.setBookAuthor(rs.getString(3));
					b.setBookGenre(rs.getString(4));
					b.setBookPublication(rs.getString(5));
					list.add(b);
					
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			return list;
		}
		public static void addBook() throws Exception {
			try (Scanner sc = new Scanner(System.in)) {
				System.out.println("Enter book id");
				int bookId = sc.nextInt();

				System.out.println("Enter book name");
				String bookName = sc.next();

				System.out.println("Enter book Author");
				String bookAuthor = sc.next();

				System.out.println("Enter book Genre");
				String bookGenre = sc.next();

				System.out.println("Enter book publications ");
				String Publications = sc.next();

				
				book b = new book();
				b.setBookId(bookId);
				b.setBookName(bookName);
				b.setBookAuthor(bookAuthor);
				b.setBookGenre(bookGenre);
				b.setBookPublication(Publications);

				
				bookDao dao = new bookDao(DbConnection.getConnection());
				boolean f = dao.addBook(b);
				if (f) {
					System.out.println("Book added successfully");
				} else {
					System.out.println("Something went wrong");
				}
			}
		}

		public static void deleteBook()  throws Exception{
			try (Scanner sc = new Scanner(System.in)) {
				System.out.println("Enter book id to delete");
				int id = sc.nextInt();
				book b = new book();
				b.setBookId(id);
				bookDao dao = new bookDao(DbConnection.getConnection());
				boolean f = dao.deleteBook(id);

				if (f) {
					System.out.println("Book details deleted succesfully");
				} else {
					System.out.println("something went wrong");
				}
			}

		}

		public static void updateBook()  throws Exception{
			try (Scanner sc = new Scanner(System.in)) {
				book b = new book();
				System.out.println("Enter book id");
				int bookId = sc.nextInt();
				System.out.println("Enter book name");
				String bookName = sc.next();
				System.out.println("Enter book Author");
				String bookAuthor = sc.next();
				System.out.println("Enter book category");
				String bookCategory = sc.next();
				System.out.println("Enter book publications ");
				String Publications = sc.next();
				b.setBookId(bookId);
				b.setBookName(bookName);
				b.setBookAuthor(bookAuthor);
				b.setBookGenre(bookCategory);
				b.setBookPublication(Publications);
				bookDao dao = new bookDao(DbConnection.getConnection());

				boolean f = dao.editBook(b);
				if (f) {
					System.out.println("book details updated");
				} else {
					System.out.println("something went wrong");
				}
			}
		}

		public static void searchBook()  throws Exception{
			bookDao dao = new bookDao(DbConnection.getConnection());
			try (Scanner sc = new Scanner(System.in)) {
				int id = sc.nextInt();
				book b = dao.viewBook(id);

				if (b == null) {
					System.out.println("No book with the given ID");

				}
			}
		}

		public static void viewAllBooks()  throws Exception{
			bookDao dao = new bookDao(DbConnection.getConnection());
			List<book> list = dao.getAllBooks();
			for (book b : list) {
				System.out.println("Book Details------------->");
				System.out.println("---------------------------");
				System.out.println("Id = " + b.getBookId());
				System.out.println("Book Name = " + b.getBookName());
				System.out.println("Author = " + b.getBookAuthor());
				System.out.println("Category = " + b.getBookGenre());
				System.out.println("Publications =" + b.getBookPublication());
				System.out.println("-----------------------------");
			}
		}

		public boolean borrowBook(int bookId, String studentRegNo) {
			try {
				String query = "UPDATE book SET available = false, studentRegNo = ? WHERE bookId = ? AND available = true";
				try (PreparedStatement pst = conn.prepareStatement(query)) {
					pst.setString(1, studentRegNo);
					pst.setInt(2, bookId);

					int rowsAffected = pst.executeUpdate();

					return rowsAffected > 0;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}

		public boolean returnBook(int bookId) throws Exception {
			try {
				String query = "UPDATE book SET available = true, studentRegNo = null WHERE bookId = ?";
				try (PreparedStatement pst = conn.prepareStatement(query)) {
					pst.setInt(1, bookId);

					int rowsAffected = pst.executeUpdate();

					return rowsAffected > 0;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}

		public boolean updateLateFee(int bookId, double lateFee) throws Exception {
			try {
				String query = "UPDATE book SET lateFee = ? WHERE bookId = ?";
				try (PreparedStatement pst = conn.prepareStatement(query)) {
					pst.setDouble(1, lateFee);
					pst.setInt(2, bookId);

					int rowsAffected = pst.executeUpdate();

					return rowsAffected > 0;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}

		public boolean setDueDate(int bookId, LocalDate dueDate) throws Exception {
			try {
				String query = "UPDATE book SET dueDate = ? WHERE bookId = ?";
				try (PreparedStatement pst = conn.prepareStatement(query)) {
					pst.setObject(1, dueDate);
					pst.setInt(2, bookId);

					int rowsAffected = pst.executeUpdate();

					return rowsAffected > 0;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}

		public double calculateLateFee(int bookId)  throws Exception{
			try {
				String query = "SELECT dueDate, reminderSent FROM books WHERE bookId = ?";
				try (PreparedStatement pst = conn.prepareStatement(query)) {
					pst.setInt(1, bookId);

					try (ResultSet rs = pst.executeQuery()) {
						if (rs.next()) {
							LocalDate dueDate = rs.getObject("dueDate", LocalDate.class);
							boolean reminderSent = rs.getBoolean("reminderSent");

							if (dueDate != null && !reminderSent && LocalDate.now().isAfter(dueDate)) {
								long daysLate = LocalDate.now().until(dueDate).getDays();
								return daysLate * 2.5; 
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return 0.0;
		}

		public void sendReminderForBook(int bookId) throws Exception {
			book book = viewBook(bookId);
			if (book != null) {
				book.sendReminder();
			} else {
				System.out.println("Book with ID " + bookId + " not found.");
			}
		}

		public void checkDueDatesAndReminders() {
			try {
				String query = "SELECT bookId, dueDate FROM books WHERE available = false AND dueDate IS NOT NULL AND reminderSent = false";
				try (PreparedStatement pst = conn.prepareStatement(query)) {
					try (ResultSet rs = pst.executeQuery()) {
						while (rs.next()) {
							int bookId = rs.getInt("bookId");
							LocalDate dueDate = rs.getObject("dueDate", LocalDate.class);

							book book = new book();
							book.setBookId(bookId);
							book.setDueDate(dueDate);
							book.sendReminder();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		}


