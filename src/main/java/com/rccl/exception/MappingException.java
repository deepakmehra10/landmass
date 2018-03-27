package com.rccl.exception;

    public class MappingException extends RuntimeException {

        public MappingException(String message, Throwable throwable) {
            super(message, throwable);
        }

        public MappingException(String message) {
            super(message);
        }

    }


