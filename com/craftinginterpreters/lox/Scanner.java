package com.craftinginterpreters.lox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.craftinginterpreters.lox.TokenType.*;

class Scanner { 
    private final String source;
    private final List <Token> tokens = new ArrayList<>();
    private int start = 0;  // index to first char of current token in source[]
    private int current = 0; // index to current char in source[]
    private int line = 1;    // current line

    Scanner (String source){ 
        this.source = source;
    }

    List<Token> scanTokens() {
        while (!isAtEnd()) {
            start = current;
            scanTokens();
        }

        tokens.add(new Token(EOF, "", null, line));
        return tokens;
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private void scanToken() {
        char c = advance();
        switch (c) {
        case '(': addToken(LEFT_PAREN); break;
        case ')': addToken(RIGHT_PAREN); break;
        case '{': addToken(LEFT_BRACE); break;
        case '}': addToken(RIGHT_BRACE); break;
        case ',': addToken(COMMA); break;
        case '.': addToken(DOT); break;
        case '-': addToken(MINUS); break;
        case '+': addToken(PLUS); break;
        case ';': addToken(SEMICOLON); break;
        case '*': addToken(STAR); break; 
        case '!': 
            addToken(match('=') ? BANG_EQUAL : BANG);
            break;
        case '=':
            addToken(match('=') ? EQUAL_EQUAL : EQUAL);
            break;
        case '<':
            addToken(match('=') ? LESS_EQUAL : LESS);
            break;
        case '>':
            addToken(match('=') ? GREATER_EQUAL : GREATER);
            break;
        case '/':
            if (match('/')) {
                while (peek() != '\n' && !isAtEnd()) advance();
            } else {
                addToken(SLASH);
            }
            break;
        case ' ':
        case '\r':
        case '\t':
            break;
        
        case '\n':
            line++;
            break;

        default:
            Lox.error(line, "Unexpected character.");
            break;

            /* 
        case '"': 
            string(); 
            break;

        default: 
            if (isDigit(c)) {
                number();
            } else {
                Lox.error(line, "Unexpected character.");
            }
            */
        }
    }

    /*
    private string() {
        while (peek()!= '"' && !isAtEnd()) {
            if (peek == '\n') return line++;
            advance();
        }
        if (isAtEnd()) {
            Lox.error(line, "Unterminated string.");
            return;
        }
        
        advance();

        String value = source.substring(start + 1, current - 1);
        addToken(STRING, value);
    }

    private void number() {
        while (isDigit)
    }
    */

    private boolean match(char expected){
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;
        // this is a match. consume the next char.
        current++;
        return true;
    }

    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }

    // I get that it's checking if the character is between 0 and 9 
    // but why is it written this way? shouldn't it be using an if statment 
    // like 'if c>=0 && c <= 9 then return c?'
    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    // Consumes the next character in the source file and returns it
    private char advance() {
        return source.charAt(current++);
    }
    // grabs new the text of the current lexeme and creates a new token for it. For Output
    // How can there be two functions calling itself? 
    private void addToken(TokenType type) {
        addToken(type, null);
    }
    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }
}
