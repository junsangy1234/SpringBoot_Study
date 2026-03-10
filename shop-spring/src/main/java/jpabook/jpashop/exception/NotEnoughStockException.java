package jpabook.jpashop.exception;

public class NotEnoughStockException extends RuntimeException {
    public NotEnoughStockException() {
        super();
    }

    public NotEnoughStockException(String msg){
      super(msg);
    }

    public NotEnoughStockException(String msg, Throwable cause){
      super(msg, cause);
    }

    public NotEnoughStockException(Throwable cause){
      super(cause);
    }
}
