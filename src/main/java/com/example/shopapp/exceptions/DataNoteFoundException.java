package com.example.shopapp.exceptions;

public class DataNoteFoundException extends Exception {
    public DataNoteFoundException(String message) {
        super(message);
    }//thực thi lại phương thức của lớp cha
    // DataNoteFoundException kiểm tra xem nó trả về loại exeption nào để phân loại
}
