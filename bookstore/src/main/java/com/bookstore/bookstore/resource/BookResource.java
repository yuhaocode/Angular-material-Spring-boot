package com.bookstore.bookstore.resource;

import com.bookstore.bookstore.domain.Book;
import com.bookstore.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/book")
public class BookResource {

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Book addBookPost(@RequestBody Book book) {
        return bookService.save(book);
    }
    @RequestMapping(value = "/add/image", method = RequestMethod.POST)
    public ResponseEntity upload (
            @RequestParam("id") Long id,
            HttpServletResponse response, HttpServletRequest request
    ){
        try {
            Book book = bookService.findOne(id);
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            Iterator<String> it = multipartRequest.getFileNames();
            MultipartFile multipartFile = multipartRequest.getFile(it.next());
            String fileName = id + ".png";


            byte[] bytes = multipartFile.getBytes();
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("src/main/resources/static/image/book/" + fileName)));
            stream.write(bytes);

            stream = new BufferedOutputStream(new FileOutputStream(new File("target/classes/static/image/book/" + fileName)));
            stream.write(bytes);

            stream.close();

            return new ResponseEntity("Upload Success!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("Upload failed!", HttpStatus.BAD_REQUEST);
        }
    }


    @RequestMapping("/bookList")
    public List<Book> getBookList(){
        return bookService.findAll();
    }

    @RequestMapping("/{id}")
    public Book getBook(@PathVariable ("id") Long id){
        Book book = bookService.findOne(id);
        return book;
    }

    @RequestMapping(value = "/searchBook", method = RequestMethod.POST)
    public List<Book> searchBook(@RequestBody String keyword){
        List<Book> bookList = bookService.blurrySearch(keyword);

        return bookList;
    }

    @RequestMapping(value= "/update" , method = RequestMethod.POST)
    public Book update(@RequestBody Book book){
        return bookService.save(book);
    }

    @RequestMapping(value = "/remove" , method = RequestMethod.POST)
    public ResponseEntity remove (
            @RequestBody String id
    ) throws IOException{
        bookService.removeOne(Long.parseLong(id));
        String fileName = id + ".png";
        Files.delete(Paths.get("src/main/resources/static/image/book/", fileName));
        Files.delete(Paths.get("target/classes/static/image/book/", fileName));
        return new ResponseEntity("Remove Success", HttpStatus.OK);
    }

    @RequestMapping(value = "/update/image", method = RequestMethod.POST)
    public ResponseEntity updateImagePost (
            @RequestParam("id") Long id,
            HttpServletResponse response, HttpServletRequest request
    ){
        try {
            Book book = bookService.findOne(id);
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            Iterator<String> it = multipartRequest.getFileNames();
            MultipartFile multipartFile = multipartRequest.getFile(it.next());
            String fileName = id + ".png";
            Files.delete(Paths.get("src/main/resources/static/image/book/", fileName));
            Files.delete(Paths.get("target/classes/static/image/book/", fileName));

            byte[] bytes = multipartFile.getBytes();
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("src/main/resources/static/image/book/" + fileName)));
            stream.write(bytes);

            stream = new BufferedOutputStream(new FileOutputStream(new File("target/classes/static/image/book/" + fileName)));
            stream.write(bytes);
            stream.close();

            return new ResponseEntity("Upload Success!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("Upload failed!", HttpStatus.BAD_REQUEST);
        }
    }

}

