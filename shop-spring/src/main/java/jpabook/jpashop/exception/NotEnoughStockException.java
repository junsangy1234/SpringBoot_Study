package jpabook.jpashop.exception;

public class NotEnoughStockException extends RuntimeException {
    public NotEnoughStockException(){
        super();
    }

    public NotEnoughStockException(String message) {
        super(message);
    }

    public NotEnoughStockException(String msg, Throwable cause){
        super(msg, cause);
    }

    public NotEnoughStockException(Throwable cause){
        super(cause);
    }
}
