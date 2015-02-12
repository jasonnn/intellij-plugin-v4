grammar FormattingExamples;

        //erlang
        tryCatch : 'catch' tryClauses               'end'
                           | 'catch' tryClauses 'after' exprs 'end'
                           | 'after' exprs 'end';

        binary : '<<'             '>>'
                         | '<<' binElements '>>';

        binaryType : '<<'                             '>>'
                             | '<<' binBaseType                 '>>'
                             | '<<'                 binUnitType '>>'
                             | '<<' binBaseType ',' binUnitType '>>'
                ;

        binBaseType : tokVar ':'            type;

        binUnitType : tokVar ':' tokVar '*' type;

                //antlr

        PUBLIC : 'public';
        PRIVATE : 'private';
        RETURNS : 'returns';
        LOCALS : 'locals';
        THROWS : 'throws';
        CATCH : 'catch';
        FINALLY : 'finally';
        MODE : 'mode';

        mode LexerCharSet;

                LEXER_CHAR_SET_BODY
                        : (    ~[\]\\]
                                       | '\\' .
                                  )+
                                  -> more
                        ;