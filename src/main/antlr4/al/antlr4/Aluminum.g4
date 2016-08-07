grammar Aluminum;

program : statements;

statements : (statement? terminator)* (Return expression terminator)?;

statement : assignment
          | ifStatement
          | whileStatement
          | functionCall
          | functionDecl
          | classStatement
;

terminator : Newline | SColon;

// Statements
assignment : Identifier Assign expression;

ifStatement : If expression block;

whileStatement : While expression block;

classStatement : Class Identifier (LT Identifier)? block;

functionCall : (Identifier Period)? Identifier arguments;
arguments : Parens | OParen exprList CParen;

functionDecl : Def Identifier parameters? block;
parameters : Parens | OParen idList CParen;

// Expressions
expression : Subtract expression                           		#unaryMinusExpression
           | Excl expression                           			#notExpression
           | expression Pow expression                			#powerExpression
           | expression Multiply expression               		#multiplyExpression
           | expression Divide expression                		#divideExpression
           | expression Modulus expression                		#modulusExpression
           | expression Add expression                			#addExpression
           | expression Subtract expression                		#subtractExpression
           | expression GTEquals expression               		#gtEqExpression
           | expression LTEquals expression               		#ltEqExpression
           | expression GT expression                			#gtExpression
           | expression LT expression                			#ltExpression
           | expression EqEq expression              			#eqExpression
           | expression NEquals expression               		#notEqExpression
           | expression And expression               			#andExpression
           | expression Or expression               			#orExpression
           | expression QMark expression Colon expression 		#ternaryExpression
           | Number                                   			#numberExpression
           | Boolean                                  			#booleanExpression
       	   | Nil                           				        #nilExpression
           | functionCall                             			#functionCallExpression
           | Identifier                               			#identifierExpression
           | String                                   			#stringExpression
           | OParen expression CParen                  			#expressionExpression
;


// Utilities
exprList : expression (',' expression)*;
idList : Identifier (',' Identifier)*;
block : Do Newline statements End;

// Operators
Or       : 'or' | '||';
And      : 'and' | '&&';
EqEq     : '==';
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
Period   : '.';
QMark    : '?';
Colon    : ':';
Newline  : '\n';
Parens   : '()';

Nil      : 'nil';
Def      : 'def';
If       : 'if';
While    : 'while';
End      : 'end';
Do       : 'do';
Return   : 'return';
Class    : 'class';

// Lexer regex
Boolean : 'true' | 'false';
Number : [1-9] Digit* | '0';

Identifier : [a-zA-Z_] [a-zA-Z_0-9]*;
String : ["] (~["\r\n] | '\\\\' | '\\"')* ["]
       | ['] (~['\r\n] | '\\\\' | '\\\'')* [']
;

// Whitespace
Comment : ('//' ~[\r\n]* | '/*' .*? '*/') -> skip;
Space : [ \t\r\n\u000C] -> skip;

fragment Digit : [0-9];
