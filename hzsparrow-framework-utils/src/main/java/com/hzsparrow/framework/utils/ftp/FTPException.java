/*
 * $Id:$
 * Copyright 2017 ecarpo.com All rights reserved.
 */
package com.hzsparrow.framework.utils.ftp;

/**
 * ServiceException
 * 
 * @author HellerZhang
 * @since 2017.07.17
 */
public class FTPException extends RuntimeException {

    /**
     * generated serial version ID
     */
    private static final long serialVersionUID = 6014713135438417458L;

    private Object[] args;

    public FTPException() {
      super();
    }

    public FTPException(String message) {
      super(message);
    }

    public FTPException(String message, Object... args) {
      super(message);
      this.args = args;
    }

    public FTPException(Throwable cause) {
      super(cause);
    }

    public FTPException(Throwable cause, Object... args) {
      super(cause);
      this.args = args;
    }

    public FTPException(String message, Throwable cause) {
      super(message, cause);
    }

    public FTPException(String message, Throwable cause, Object... args) {
      super(message, cause);
      this.args = args;
    }

    /**
     * @return the args
     */
    public Object[] getArgs() {
      return args;
    }
}
