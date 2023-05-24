package com.example.btl_mobile.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import android.content.Context;

import com.example.btl_mobile.model.Bill;
import com.example.btl_mobile.model.Book;
import com.example.btl_mobile.model.Cart;
import com.example.btl_mobile.model.Favourite;
import com.example.btl_mobile.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class DB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "bookstoreeee.db";
    private static  int DATABASE_VERSION = 1;

    public DB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE books("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "title TEXT,"+
                "content TEXT,"+
                "type TEXT," +
                "price TEXT," +
                "creation TEXT,"+
                "author TEXT,"+
                "img TEXT)";
        String sql1 = "CREATE TABLE users("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "username TEXT,"+
                "password TEXT,"+
                "role INTEGER)";
        String sql2 = "CREATE TABLE favourites("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "userid INTEGER,"+
                "bookid INTEGER)";
        String sql3 = "CREATE TABLE carts("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "userid INTEGER,"+
                "bookid INTEGER,"+
                "amount INTEGER,"+
                "sold INTEGER)";
        String sql4 = "CREATE TABLE bills("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "cartid TEXT,"+
                "creation TEXT,"+
                "total DOUBLE)";
        String sql5 =  "CREATE TABLE stores("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "bookid INTEGER,"+
                "amount INTEGER)";
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.execSQL(sql1);
        sqLiteDatabase.execSQL(sql2);
        sqLiteDatabase.execSQL(sql3);
        sqLiteDatabase.execSQL(sql4);
        sqLiteDatabase.execSQL(sql5);
    }

    public long addBill(Bill bill){
        ContentValues values = new ContentValues();
        String listcartid = "";
        for(Cart cart:bill.getCarts()){
            listcartid += cart.getId()+" ";
        }
        values.put("cartid",listcartid.trim());
        values.put("creation",bill.getCreation());
        values.put("total",bill.getTotal());
        SQLiteDatabase db = getWritableDatabase();
        return db.insert("bills",null,values);
    }
    public List<Bill> getAllBill(){
        List<Bill> bills = new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("bills",null,null,null,null,null,null);
        while (rs!=null && rs.moveToNext()){
            Bill bill = new Bill();
            bill.setId(rs.getInt(0));
            String idcart = rs.getString(1);
            StringTokenizer tokenizer = new StringTokenizer(idcart);
            List<Cart> carts = new ArrayList<>();
            while (tokenizer.hasMoreTokens()){
                String s = tokenizer.nextToken();
                Cart cart = getCartByid(Integer.parseInt(s));
                if(cart.getId()!=0) {carts.add(cart);}
            }
            bill.setCarts(carts);
            bill.setCreation(rs.getString(2));
            bill.setTotal(rs.getDouble(3));
            bills.add(bill);
            if (bill.getCarts().size()==0) deleteBill(bill);
        }
        return bills;
    }
    public List<Bill> getBillByMonth(int month){
        List<Bill> bills = new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("bills",null,null,null,null,null,null);
        while (rs!=null && rs.moveToNext()) {
            Bill bill = new Bill();
            bill.setId(rs.getInt(0));
            String idcart = rs.getString(1);
            StringTokenizer tokenizer = new StringTokenizer(idcart);
            List<Cart> carts = new ArrayList<>();
            while (tokenizer.hasMoreTokens()) {
                String s = tokenizer.nextToken();
                Cart cart = getCartByid(Integer.parseInt(s));
                if(cart.getId()!=0) {carts.add(cart);}

            }
            bill.setCarts(carts);
            bill.setCreation(rs.getString(2));
            bill.setTotal(rs.getDouble(3));
            int dem = 1;
            String s = "";
            StringTokenizer tokenizer1 = new StringTokenizer(bill.getCreation(),"/");
            while (tokenizer1.hasMoreTokens()){
                s = tokenizer1.nextToken();
                if(dem==2) break;
                dem = dem + 1;
            }

            if(Integer.parseInt(s)==month) {bills.add(bill);if (bill.getCarts().size()==0) deleteBill(bill);}
        }
        rs.close();
        return bills;
    }

    public List<Cart> getListCartByBillId(int billid){
        Bill bill = new Bill();
        String whereClause = "id= ?";
        String[] wherearg = {String.valueOf(billid)};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("bills",null,whereClause,wherearg,null,null,null);
        while (rs!=null && rs.moveToNext()){

            bill.setId(rs.getInt(0));
            String idcart = rs.getString(1);
            StringTokenizer tokenizer = new StringTokenizer(idcart);
            List<Cart> carts = new ArrayList<>();
            while (tokenizer.hasMoreTokens()) {
                String s = tokenizer.nextToken();
                Cart cart = getCartByid(Integer.parseInt(s));
                if(cart.getId()!=0) {carts.add(cart);}

            }
            bill.setCarts(carts);
            bill.setCreation(rs.getString(2));
            bill.setTotal(rs.getDouble(3));
        }
        return bill.getCarts();
    }



    public long deleteBill(Bill bill){
        for(Cart cart:bill.getCarts()){
            deletecart(cart.getId());
        }
        String whereClause = "id= ?";
        String[] wherearg = {String.valueOf(bill.getId())};
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("bills",whereClause,wherearg);
    }
    public Cart getCartByid(int id){
        Cart cart = new Cart();
        String whereClause = "id LIKE ?";
        String[] wherearg = {String.valueOf(id)};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("carts", null, whereClause, wherearg, null, null, null);
        while (rs!=null && rs.moveToNext()){
            cart.setId(rs.getInt(0));
            cart.setUser(getUserById(rs.getInt(1)));
            cart.setBook(getBookByKey(rs.getInt(2)));
            cart.setAmount(rs.getInt(3));
            cart.setSold(rs.getInt(4));
        }
        rs.close();
        return cart;
    }
    public boolean checkCardExist(int userid, int sold, int bookid){
        Cart cart = new Cart();
        cart.setId(-1);
        String whereClause = "userid LIKE ? AND sold LIKE ? AND bookid LIKE ?";
        String[] wherearg = {String.valueOf(userid), String.valueOf(sold),String.valueOf(bookid)};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("carts", null, whereClause, wherearg, null, null, null);
        while (rs!=null && rs.moveToNext()){
            cart.setId(rs.getInt(0));
            cart.setUser(getUserById(rs.getInt(1)));
            cart.setBook(getBookByKey(rs.getInt(2)));
            cart.setAmount(rs.getInt(3));
            cart.setSold(rs.getInt(4));
        }
        rs.close();
        if(cart.getId()==-1) return false;
        else return true;
    }
    public long addCart(Cart cart){
        ContentValues values = new ContentValues();
        values.put("userid",cart.getUser().getId());
        values.put("bookid",cart.getBook().getId());
        values.put("amount",cart.getAmount());
        values.put("sold",cart.getSold());
        SQLiteDatabase db = getWritableDatabase();
        return db.insert("carts",null,values);
    }
    public Cart getCart(int userid, int bookid,int sold){
        Cart cart = new Cart();
        String whereClause = "userid LIKE ? AND sold LIKE ? AND bookid LIKE ?";
        String[] wherearg = {String.valueOf(userid), String.valueOf(sold),String.valueOf(bookid)};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("carts", null, whereClause, wherearg, null, null, null);
        while (rs!=null && rs.moveToNext()){
            cart.setId(rs.getInt(0));
            cart.setUser(getUserById(rs.getInt(1)));
            cart.setBook(getBookByKey(rs.getInt(2)));
            cart.setAmount(rs.getInt(3));
            cart.setSold(rs.getInt(4));
        }
        rs.close();
        return cart;
    }
    public void deletecart(int id){
        String whereClause = "id= ?";
        String[] wherearg = {String.valueOf(id)};
        SQLiteDatabase db = getWritableDatabase();
        db.delete("carts",whereClause,wherearg);
    }
    public void deleteCartByBookId(int id){
        String whereClause = "bookid= ?";
        String[] wherearg = {String.valueOf(id)};
        SQLiteDatabase db = getWritableDatabase();
        db.delete("carts",whereClause,wherearg);
    }
    public long deleteCart(Cart cart){
        String whereClause = "id= ?";
        String[] wherearg = {String.valueOf(cart.getId())};
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("carts",whereClause,wherearg);
    }
    public List<Cart> getListCartByUserId(int userid, int sold){
        List<Cart> carts = new ArrayList<>();
        String whereClause = "userid LIKE ? AND sold LIKE ?";
        String[] wherearg = {String.valueOf(userid), String.valueOf(sold)};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("carts", null, whereClause, wherearg, null, null, null);
        while (rs!=null && rs.moveToNext()){
            Cart cart = new Cart();
            cart.setId(rs.getInt(0));
            cart.setUser(getUserById(rs.getInt(1)));
            cart.setBook(getBookByKey(rs.getInt(2)));
            cart.setAmount(rs.getInt(3));
            cart.setSold(rs.getInt(4));
            carts.add(cart);
        }
        rs.close();
        return carts;
    }

    public long updateCart(Cart cart){
        ContentValues values = new ContentValues();
        values.put("userid",cart.getUser().getId());
        values.put("bookid",cart.getBook().getId());
        values.put("amount",cart.getAmount());
        values.put("sold",cart.getSold());SQLiteDatabase db = getWritableDatabase();
        String whereClause = "id= ?";
        String[] wherearg = {String.valueOf(cart.getId())};
        return db.update("carts",values,whereClause,wherearg);
    }

    public User getUserById(int userid){
        User user = new User();
        String whereClause = "id LIKE ?";
        String[] wherearg = {String.valueOf(userid)};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("users",null,whereClause,wherearg,null,null,null);
        while (rs!=null && rs.moveToNext()){
            int id = rs.getInt(0);
            String name = rs.getString(1);
            String password = rs.getString(2);
            int role = rs.getInt(3);
            user.setUsername(name);
            user.setPassword(password);
            user.setRole(role);
            user.setId(id);
        }
        rs.close();
        return user;
    }

    public long addFavourite(int userid,int bookid){
        if(checkExistFavourite(userid,bookid)){
            return 0;
        }else {
            ContentValues values = new ContentValues();
            values.put("userid",userid);
            values.put("bookid",bookid);
            SQLiteDatabase db = getWritableDatabase();
            return  db.insert("favourites",null,values);
        }
    }
    public boolean checkExistFavourite(int userid,int bookid){
        Favourite favourite = new Favourite();
        favourite.setId(-1);
        String whereClause = "userid LIKE ? AND bookid LIKE ?";
        String[] wherearg = {String.valueOf(userid), String.valueOf(bookid)};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("favourites", null, whereClause, wherearg, null, null, null);
        while (rs != null && rs.moveToNext()) {
            favourite.setId(rs.getInt(0));
            favourite.setUserid(rs.getInt(1));
            favourite.setBookid(rs.getInt(2));
        }
        rs.close();
        if(favourite.getId()!=-1) return true;
        return false;
    }

    public List<Favourite> getFavouriteByUserId(int id){
        List<Favourite> list = new ArrayList<>();
        String whereClause = "userid LIKE ?";
        String[] wherearg = {String.valueOf(id)};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("favourites", null, whereClause, wherearg, null, null, null);
        while (rs.moveToNext() && rs!=null){
            Favourite favourite = new Favourite();
            favourite.setId(rs.getInt(0));
            favourite.setUserid(rs.getInt(1));
            favourite.setBookid(rs.getInt(2));
            list.add(favourite);
        }
        rs.close();
        return list;
    }


    public Book getBookByKey(int id){
        Book book = new Book();
        String whereClause = "id LIKE ?";
        String[] wherearg = {String.valueOf(id)};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("books", null, whereClause, wherearg, null, null, null);
        while (rs != null && rs.moveToNext()) {

            book.setId(rs.getInt(0));
            book.setTitle(rs.getString(1));
            book.setContent(rs.getString(2));
            book.setType(rs.getString(3));
            book.setPrice(rs.getString(4));
            book.setCreation(rs.getString(5));
            book.setAuthor(rs.getString(6));
            book.setImg(rs.getString(7));

        }
        rs.close();
        return book;

    }
    public long addUser(User user){
        ContentValues values = new ContentValues();
        values.put("username",user.getUsername());
        values.put("password",user.getPassword());
        values.put("role",user.getRole());
        SQLiteDatabase db = getWritableDatabase();
        return db.insert("users",null,values);
    }
    public long addBook(Book book){
        ContentValues values = new ContentValues();
        values.put("title",book.getTitle());
        values.put("content",book.getContent());
        values.put("type",book.getType());
        values.put("price",book.getPrice());
        values.put("creation",book.getCreation());
        values.put("author",book.getAuthor());
        values.put("img",book.getImg());
        SQLiteDatabase database = getWritableDatabase();
        return database.insert("books",null,values);

    }
    public User getUserByUsername(String username){
        User user = new User();
        String whereClause = "username LIKE ?";
        String[] wherearg = {username};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("users",null,whereClause,wherearg,null,null,null);
        while (rs!=null && rs.moveToNext()){
            int id = rs.getInt(0);
            String name = rs.getString(1);
            String password = rs.getString(2);
            int role = rs.getInt(3);
            user.setUsername(name);
            user.setPassword(password);
            user.setRole(role);
            user.setId(id);
        }
        rs.close();
        return user;
    }
    public List<Book> getAllBook(){
        List<Book> list = new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("books",null,null,null,null,null,null);
        while (rs.moveToNext() && rs!=null){
            Book book = new Book();
            book.setId(rs.getInt(0));
            book.setTitle(rs.getString(1));
            book.setContent(rs.getString(2));
            book.setType(rs.getString(3));
            book.setPrice(rs.getString(4));
            book.setCreation(rs.getString(5));
            book.setAuthor(rs.getString(6));
            book.setImg(rs.getString(7));
            list.add(book);
        }
        return list;
    }
    public int deleteBook(int id){
        String whereClause = "id= ?";
        deleteCartByBookId(id);
        deleteFavouriteByBookId(id);
        String[] wherearg = {String.valueOf(id)};
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("books",whereClause,wherearg);
    }
    public int updateBook(Book book){
        ContentValues values = new ContentValues();
        values.put("title",book.getTitle());
        values.put("content",book.getContent());
        values.put("type",book.getType());
        values.put("price",book.getPrice());
        values.put("creation",book.getCreation());
        values.put("author",book.getAuthor());
        values.put("img",book.getImg());
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "id= ?";
        String[] wherearg = {String.valueOf(book.getId())};
        return db.update("books",values,whereClause,wherearg);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public List<Book> getBookByType(String typer) {
        List<Book> list = new ArrayList<>();
        String whereClause = "type LIKE ?";
        String[] wherearg = {typer};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("books", null, whereClause, wherearg, null, null, null);
        while (rs != null && rs.moveToNext()) {
            Book book = new Book();
            book.setId(rs.getInt(0));
            book.setTitle(rs.getString(1));
            book.setContent(rs.getString(2));
            book.setType(rs.getString(3));
            book.setPrice(rs.getString(4));
            book.setCreation(rs.getString(5));
            book.setAuthor(rs.getString(6));
            book.setImg(rs.getString(7));
            list.add(book);
        }
        rs.close();
        return list;
    }

    public List<Book> getListBookByTitle(String title) {
        List<Book> list = new ArrayList<>();
        String whereClause = "title LIKE ?";
        String[] wherearg = {"%" + title + "%"};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("books", null, whereClause, wherearg, null, null, null);
        while (rs!=null && rs.moveToNext()){
            Book book = new Book();
            book.setId(rs.getInt(0));
            book.setTitle(rs.getString(1));
            book.setContent(rs.getString(2));
            book.setType(rs.getString(3));
            book.setPrice(rs.getString(4));
            book.setCreation(rs.getString(5));
            book.setAuthor(rs.getString(6));
            book.setImg(rs.getString(7));
            list.add(book);
        }
        rs.close();
        return list;
    }
    public List<Book> getListBookByDateFromTo(String from, String to) {
        List<Book> list = new ArrayList<>();
        String whereClause = "creation BETWEEN ? AND ?";
        String[] wherearg = {from.trim(), to.trim()};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("books", null, whereClause, wherearg, null, null, null);
        while (rs != null && rs.moveToNext()) {
            Book book = new Book();
            book.setId(rs.getInt(0));
            book.setTitle(rs.getString(1));
            book.setContent(rs.getString(2));
            book.setType(rs.getString(3));
            book.setPrice(rs.getString(4));
            book.setCreation(rs.getString(5));
            book.setAuthor(rs.getString(6));
            book.setImg(rs.getString(7));
            list.add(book);
        }
        rs.close();
        return list;
    }
    public List<Favourite> getAllFavourite(){
        List<Favourite> favourites = new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("favourites",null,null,null,null,null,null);
        while (rs!=null && rs.moveToNext()){
            Favourite favourite = new Favourite();
            favourite.setId(rs.getInt(0));
            favourite.setUserid(rs.getInt(1));
            favourite.setBookid(rs.getInt(2));
            favourites.add(favourite);
        }
        rs.close();
        return favourites;
    }

    public long deleteFavouriteByUserIdAndBookId(int userid, int bookid){
        String whereClause = "userid= ? AND bookid= ?";
        String[] wherearg = {String.valueOf(userid),String.valueOf(bookid)};
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("favourites",whereClause,wherearg);
    }
    public void deleteFavouriteByBookId(int id){
        String whereClause = "bookid= ?";
        String[] wherearg = {String.valueOf(id)};
        SQLiteDatabase db = getWritableDatabase();
        db.delete("favourites",whereClause,wherearg);
    }
    public void addStored(int bookid,int amount){
        ContentValues values = new ContentValues();
        values.put("bookid",bookid);
        values.put("amount",amount);
        SQLiteDatabase database = getWritableDatabase();
        database.insert("stores",null,values);
    }
    public int getAmountByBookId(int bookid){
        String whereClause = "bookid= ?";
        String[] wherearg = {String.valueOf(bookid)};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("stores", null, whereClause, wherearg, null, null, null);
        while (rs.moveToNext() && rs!=null){
            return rs.getInt(2);
        }
        return 0;
    }
}
