grammar SimpleLang;

prog : dec+ EOF;

dec
    : typed_idfr LParen vardec RParen body
;

vardec
    : (typed_idfr (Comma typed_idfr)*)?
;

typed_idfr
    : type Idfr
;

type
    : IntType | BoolType | UnitType
;

body
    : LBrace ene+=exp (Semicolon ene+=exp)* RBrace
;

block
    : LBrace ene+=exp (Semicolon ene+=exp)* RBrace
;

exp
    : Idfr Assign exp                                       #AssignExpr
    | LParen exp binop exp RParen                           #BinOpExpr
    | LParen exp boolop exp RParen                          #BoolOpExpr
    | Idfr LParen (args+=exp (Comma args+=exp)*)? RParen    #InvokeExpr
    | block                                                 #BlockExpr
    | If exp Then block Else block                          #IfExpr
    | Print (exp | NewLine)                                 #PrintExpr
    | Space                                                 #SpaceExpr
    | Idfr                                                  #IdExpr
    | IntLit                                                #IntExpr
    | While exp Do block                                    #WhileExpr
    | typed_idfr (Assign exp)?                              #AssignTypedExpr
    | NewLine                                               #NewLineExpr
    | Space                                                 #SpaceExpr
;

binop
    : Eq              #EqBinop
    | Less            #LessBinop
    | LessEq          #LessEqBinop
    | Great           #GreatBinop
    | GreatEq         #GreatEqBinop
    | Plus            #PlusBinop
    | Minus           #MinusBinop
    | Times           #TimesBinop
    | Divide          #DivideBinop
;

boolop
    : BinAnd          #AndBoolOp
    | BinOr           #OrBoolOp
    | BinXor          #XorBoolOp
;

LParen : '(' ;
Comma : ',' ;
RParen : ')' ;
LBrace : '{' ;
Semicolon : ';' ;
RBrace : '}' ;

Eq : '==' ;
Less : '<' ;
LessEq : '<=' ;
Great: '>';
GreatEq: '>=';

Plus : '+' ;
Times : '*' ;
Minus : '-' ;
Divide: '/';

BinAnd: '&';
BinOr: '|';
BinXor: '^';

Assign : ':=' ;

Print : 'print' ;
Space : 'space' ;
NewLine : 'newline' ;
If : 'if' ;
Then : 'then' ;
Else : 'else' ;
While : 'while';
Do : 'do';

IntType : 'int' ;
BoolType : 'bool' ;
UnitType : 'unit' ;

BoolLit : 'true' | 'false' ;
IntLit : '0' | ('-'? [1-9][0-9]*) ;
Idfr : [a-z][A-Za-z0-9_]* ;
WS : [ \n\r\t]+ -> skip ;