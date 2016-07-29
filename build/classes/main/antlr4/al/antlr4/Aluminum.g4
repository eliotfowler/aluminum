grammar Aluminum;

program : statements;

statements : (statement? terminator)* (Return expression)?;

statement : assignment
          | ifStatement
          | whileStatement
          | functionCall
          | functionDecl
;

terminator : Newline | SColon;

// Statements
assignment : Identifier '=' expression;

ifStatement : 'if' expression block;

whileStatement : While expression block;

functionCall : Identifier arguments;
arguments : '(' exprList? ')';

functionDecl : Def Identifier parameters block;
parameters : '(' idList? ')';

// Expressions
expression : '-' expression                           #unaryMinusExpression
           | '!' expression                           #notExpression
           | expression '^' expression                #powerExpression
           | expression '*' expression                #multiplyExpression
           | expression '/' expression                #divideExpression
           | expression '%' expression                #modulusExpression
           | expression '+' expression                #addExpression
           | expression '-' expression                #subtractExpression
           | expression '>=' expression               #gtEqExpression
           | expression '<=' expression               #ltEqExpression
           | expression '>' expression                #gtExpression
           | expression '<' expression                #ltExpression
           | expression '==' expression               #eqExpression
           | expression '!=' expression               #notEqExpression
           | expression '&&' expression               #andExpression
           | expression '||' expression               #orExpression
           | expression '?' expression ':' expression #ternaryExpression
           | Number                                   #numberExpression
           | Boolean                                  #booleanExpression
           | Nil                                      #nilExpression
           | functionCall                             #functionCallExpression
           | Identifier                               #identifierExpression
           | String                                   #stringExpression
           | '(' expression ')'                       #expressionExpression
;


// Utilities
exprList : expression (',' expression)*;
idList : Identifier (',' Identifier)*;
block : Do Newline statements End;


// Operators
Or       : '||';
And      : '&&';
Equals   : '==';
NEquals  : '!=';
GTEquals : '>=';
LTEquals : '<=';
Pow      : '^';
Excl     : '!';
GT       : '>';
LT       : '<';
Add      : '+';
Subtract : '-';
Multiply : '*';
Divide   : '/';
Modulus  : '%';
OBrace   : '{';
CBrace   : '}';
OBracket : '[';
CBracket : ']';
OParen   : '(';
CParen   : ')';
SColon   : ';';
Assign   : '=';
Comma    : ',';
QMark    : '?';
Colon    : ':';
Newline  : '\n';

Nil      : 'nil';
Def      : 'def';
If       : 'if';
While    : 'while';
End      : 'end';
Do       : 'do';
Return   : 'return';

// Lexer regex
Boolean : 'true' | 'false';
Number : [0-9]+;
Identifier : [a-zA-Z_] [a-zA-Z_0-9]*;
String : ["] (~["\r\n] | '\\\\' | '\\"')* ["]
       | ['] (~['\r\n] | '\\\\' | '\\\'')* [']
;

// Whitespace
Comment : ('//' ~[\r\n]* | '/*' .*? '*/') -> skip;
Space : [ \t\r\n\u000C] -> skip;
