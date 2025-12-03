package com.pooii.exception;

public class AutenticacaoException extends Exception {
    
    public AutenticacaoException(String mensagem) {
        super(mensagem);
    }

    public AutenticacaoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

