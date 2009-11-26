// $ANTLR 3.2 Sep 23, 2009 12:02:23 /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g 2009-11-26 01:09:19
package queryopt.parser;

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


import org.antlr.runtime.tree.*;

public class SQL_grammarParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "QUERY", "SELECT", "NAME", "STAR", "COUNT", "SUM", "AVG", "MIN", "MAX", "FROM", "WHERE", "LITERAL", "IN", "EQ", "LS", "GT", "LS_EQ", "GT_EQ", "WS", "DIFF", "';'", "','", "'('", "')'", "'AND'"
    };
    public static final int WHERE=14;
    public static final int STAR=7;
    public static final int T__28=28;
    public static final int T__27=27;
    public static final int T__26=26;
    public static final int T__25=25;
    public static final int T__24=24;
    public static final int LS=18;
    public static final int MAX=12;
    public static final int LITERAL=15;
    public static final int COUNT=8;
    public static final int MIN=11;
    public static final int SUM=9;
    public static final int EOF=-1;
    public static final int NAME=6;
    public static final int WS=22;
    public static final int IN=16;
    public static final int DIFF=23;
    public static final int AVG=10;
    public static final int GT=19;
    public static final int QUERY=4;
    public static final int EQ=17;
    public static final int FROM=13;
    public static final int LS_EQ=20;
    public static final int GT_EQ=21;
    public static final int SELECT=5;

    // delegates
    // delegators


        public SQL_grammarParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public SQL_grammarParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return SQL_grammarParser.tokenNames; }
    public String getGrammarFileName() { return "/home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g"; }


    List<RecognitionException> exceptions = new ArrayList<RecognitionException>();

    public  void reportError(RecognitionException e)
    {
        exceptions.add(e);
    }

    public boolean hasError()
    {
     return exceptions.size() > 0; 
    }

    public String getErrorMessage()
    {
    	if (exceptions.size() > 0)
    		return this.getErrorMessage(exceptions.get(0), this.getTokenNames());
    	else
    		return "No Errors";
    }

    public String getErrorPosition()
    {
    	return this.getErrorHeader(exceptions.get(0));
    }


    public static class query_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "query"
    // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:43:1: query : query1 ';' EOF ;
    public final SQL_grammarParser.query_return query() throws RecognitionException {
        SQL_grammarParser.query_return retval = new SQL_grammarParser.query_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal2=null;
        Token EOF3=null;
        SQL_grammarParser.query1_return query11 = null;


        CommonTree char_literal2_tree=null;
        CommonTree EOF3_tree=null;

        try {
            // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:44:2: ( query1 ';' EOF )
            // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:44:4: query1 ';' EOF
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_query1_in_query55);
            query11=query1();

            state._fsp--;

            adaptor.addChild(root_0, query11.getTree());
            char_literal2=(Token)match(input,24,FOLLOW_24_in_query57); 
            EOF3=(Token)match(input,EOF,FOLLOW_EOF_in_query60); 

            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "query"

    public static class query1_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "query1"
    // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:46:1: query1 : select from ( where )? -> ^( QUERY from select ( where )? ) ;
    public final SQL_grammarParser.query1_return query1() throws RecognitionException {
        SQL_grammarParser.query1_return retval = new SQL_grammarParser.query1_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        SQL_grammarParser.select_return select4 = null;

        SQL_grammarParser.from_return from5 = null;

        SQL_grammarParser.where_return where6 = null;


        RewriteRuleSubtreeStream stream_select=new RewriteRuleSubtreeStream(adaptor,"rule select");
        RewriteRuleSubtreeStream stream_from=new RewriteRuleSubtreeStream(adaptor,"rule from");
        RewriteRuleSubtreeStream stream_where=new RewriteRuleSubtreeStream(adaptor,"rule where");
        try {
            // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:47:2: ( select from ( where )? -> ^( QUERY from select ( where )? ) )
            // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:47:4: select from ( where )?
            {
            pushFollow(FOLLOW_select_in_query173);
            select4=select();

            state._fsp--;

            stream_select.add(select4.getTree());
            pushFollow(FOLLOW_from_in_query175);
            from5=from();

            state._fsp--;

            stream_from.add(from5.getTree());
            // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:47:16: ( where )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==WHERE) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:47:16: where
                    {
                    pushFollow(FOLLOW_where_in_query177);
                    where6=where();

                    state._fsp--;

                    stream_where.add(where6.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: select, where, from
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 47:23: -> ^( QUERY from select ( where )? )
            {
                // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:47:26: ^( QUERY from select ( where )? )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(QUERY, "QUERY"), root_1);

                adaptor.addChild(root_1, stream_from.nextTree());
                adaptor.addChild(root_1, stream_select.nextTree());
                // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:47:46: ( where )?
                if ( stream_where.hasNext() ) {
                    adaptor.addChild(root_1, stream_where.nextTree());

                }
                stream_where.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "query1"

    public static class select_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "select"
    // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:49:1: select : SELECT ( ( NAME | aggregate_funct ) ( ',' ( NAME | aggregate_funct ) )* | STAR ) ;
    public final SQL_grammarParser.select_return select() throws RecognitionException {
        SQL_grammarParser.select_return retval = new SQL_grammarParser.select_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token SELECT7=null;
        Token NAME8=null;
        Token char_literal10=null;
        Token NAME11=null;
        Token STAR13=null;
        SQL_grammarParser.aggregate_funct_return aggregate_funct9 = null;

        SQL_grammarParser.aggregate_funct_return aggregate_funct12 = null;


        CommonTree SELECT7_tree=null;
        CommonTree NAME8_tree=null;
        CommonTree char_literal10_tree=null;
        CommonTree NAME11_tree=null;
        CommonTree STAR13_tree=null;

        try {
            // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:49:8: ( SELECT ( ( NAME | aggregate_funct ) ( ',' ( NAME | aggregate_funct ) )* | STAR ) )
            // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:49:11: SELECT ( ( NAME | aggregate_funct ) ( ',' ( NAME | aggregate_funct ) )* | STAR )
            {
            root_0 = (CommonTree)adaptor.nil();

            SELECT7=(Token)match(input,SELECT,FOLLOW_SELECT_in_select106); 
            SELECT7_tree = (CommonTree)adaptor.create(SELECT7);
            root_0 = (CommonTree)adaptor.becomeRoot(SELECT7_tree, root_0);

            // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:49:20: ( ( NAME | aggregate_funct ) ( ',' ( NAME | aggregate_funct ) )* | STAR )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==NAME||(LA5_0>=COUNT && LA5_0<=MAX)) ) {
                alt5=1;
            }
            else if ( (LA5_0==STAR) ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:49:22: ( NAME | aggregate_funct ) ( ',' ( NAME | aggregate_funct ) )*
                    {
                    // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:49:22: ( NAME | aggregate_funct )
                    int alt2=2;
                    int LA2_0 = input.LA(1);

                    if ( (LA2_0==NAME) ) {
                        alt2=1;
                    }
                    else if ( ((LA2_0>=COUNT && LA2_0<=MAX)) ) {
                        alt2=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 2, 0, input);

                        throw nvae;
                    }
                    switch (alt2) {
                        case 1 :
                            // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:49:23: NAME
                            {
                            NAME8=(Token)match(input,NAME,FOLLOW_NAME_in_select113); 
                            NAME8_tree = (CommonTree)adaptor.create(NAME8);
                            adaptor.addChild(root_0, NAME8_tree);


                            }
                            break;
                        case 2 :
                            // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:49:30: aggregate_funct
                            {
                            pushFollow(FOLLOW_aggregate_funct_in_select117);
                            aggregate_funct9=aggregate_funct();

                            state._fsp--;

                            adaptor.addChild(root_0, aggregate_funct9.getTree());

                            }
                            break;

                    }

                    // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:49:47: ( ',' ( NAME | aggregate_funct ) )*
                    loop4:
                    do {
                        int alt4=2;
                        int LA4_0 = input.LA(1);

                        if ( (LA4_0==25) ) {
                            alt4=1;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:49:48: ',' ( NAME | aggregate_funct )
                    	    {
                    	    char_literal10=(Token)match(input,25,FOLLOW_25_in_select121); 
                    	    // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:49:53: ( NAME | aggregate_funct )
                    	    int alt3=2;
                    	    int LA3_0 = input.LA(1);

                    	    if ( (LA3_0==NAME) ) {
                    	        alt3=1;
                    	    }
                    	    else if ( ((LA3_0>=COUNT && LA3_0<=MAX)) ) {
                    	        alt3=2;
                    	    }
                    	    else {
                    	        NoViableAltException nvae =
                    	            new NoViableAltException("", 3, 0, input);

                    	        throw nvae;
                    	    }
                    	    switch (alt3) {
                    	        case 1 :
                    	            // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:49:54: NAME
                    	            {
                    	            NAME11=(Token)match(input,NAME,FOLLOW_NAME_in_select125); 
                    	            NAME11_tree = (CommonTree)adaptor.create(NAME11);
                    	            adaptor.addChild(root_0, NAME11_tree);


                    	            }
                    	            break;
                    	        case 2 :
                    	            // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:49:61: aggregate_funct
                    	            {
                    	            pushFollow(FOLLOW_aggregate_funct_in_select129);
                    	            aggregate_funct12=aggregate_funct();

                    	            state._fsp--;

                    	            adaptor.addChild(root_0, aggregate_funct12.getTree());

                    	            }
                    	            break;

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop4;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:49:83: STAR
                    {
                    STAR13=(Token)match(input,STAR,FOLLOW_STAR_in_select137); 
                    STAR13_tree = (CommonTree)adaptor.create(STAR13);
                    adaptor.addChild(root_0, STAR13_tree);


                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "select"

    public static class aggregate_funct_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "aggregate_funct"
    // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:51:1: aggregate_funct : ( COUNT | SUM | AVG | MIN | MAX ) '(' NAME ')' ;
    public final SQL_grammarParser.aggregate_funct_return aggregate_funct() throws RecognitionException {
        SQL_grammarParser.aggregate_funct_return retval = new SQL_grammarParser.aggregate_funct_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token set14=null;
        Token char_literal15=null;
        Token NAME16=null;
        Token char_literal17=null;

        CommonTree set14_tree=null;
        CommonTree char_literal15_tree=null;
        CommonTree NAME16_tree=null;
        CommonTree char_literal17_tree=null;

        try {
            // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:52:2: ( ( COUNT | SUM | AVG | MIN | MAX ) '(' NAME ')' )
            // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:52:4: ( COUNT | SUM | AVG | MIN | MAX ) '(' NAME ')'
            {
            root_0 = (CommonTree)adaptor.nil();

            set14=(Token)input.LT(1);
            set14=(Token)input.LT(1);
            if ( (input.LA(1)>=COUNT && input.LA(1)<=MAX) ) {
                input.consume();
                root_0 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(set14), root_0);
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            char_literal15=(Token)match(input,26,FOLLOW_26_in_aggregate_funct173); 
            NAME16=(Token)match(input,NAME,FOLLOW_NAME_in_aggregate_funct177); 
            NAME16_tree = (CommonTree)adaptor.create(NAME16);
            adaptor.addChild(root_0, NAME16_tree);

            char_literal17=(Token)match(input,27,FOLLOW_27_in_aggregate_funct179); 

            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "aggregate_funct"

    public static class from_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "from"
    // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:55:1: from : FROM NAME ( ',' NAME )* ;
    public final SQL_grammarParser.from_return from() throws RecognitionException {
        SQL_grammarParser.from_return retval = new SQL_grammarParser.from_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token FROM18=null;
        Token NAME19=null;
        Token char_literal20=null;
        Token NAME21=null;

        CommonTree FROM18_tree=null;
        CommonTree NAME19_tree=null;
        CommonTree char_literal20_tree=null;
        CommonTree NAME21_tree=null;

        try {
            // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:55:6: ( FROM NAME ( ',' NAME )* )
            // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:55:8: FROM NAME ( ',' NAME )*
            {
            root_0 = (CommonTree)adaptor.nil();

            FROM18=(Token)match(input,FROM,FOLLOW_FROM_in_from191); 
            FROM18_tree = (CommonTree)adaptor.create(FROM18);
            root_0 = (CommonTree)adaptor.becomeRoot(FROM18_tree, root_0);

            NAME19=(Token)match(input,NAME,FOLLOW_NAME_in_from194); 
            NAME19_tree = (CommonTree)adaptor.create(NAME19);
            adaptor.addChild(root_0, NAME19_tree);

            // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:55:19: ( ',' NAME )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==25) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:55:20: ',' NAME
            	    {
            	    char_literal20=(Token)match(input,25,FOLLOW_25_in_from197); 
            	    NAME21=(Token)match(input,NAME,FOLLOW_NAME_in_from200); 
            	    NAME21_tree = (CommonTree)adaptor.create(NAME21);
            	    adaptor.addChild(root_0, NAME21_tree);


            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "from"

    public static class where_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "where"
    // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:58:1: where : WHERE and ;
    public final SQL_grammarParser.where_return where() throws RecognitionException {
        SQL_grammarParser.where_return retval = new SQL_grammarParser.where_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token WHERE22=null;
        SQL_grammarParser.and_return and23 = null;


        CommonTree WHERE22_tree=null;

        try {
            // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:58:7: ( WHERE and )
            // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:58:9: WHERE and
            {
            root_0 = (CommonTree)adaptor.nil();

            WHERE22=(Token)match(input,WHERE,FOLLOW_WHERE_in_where213); 
            WHERE22_tree = (CommonTree)adaptor.create(WHERE22);
            root_0 = (CommonTree)adaptor.becomeRoot(WHERE22_tree, root_0);

            pushFollow(FOLLOW_and_in_where216);
            and23=and();

            state._fsp--;

            adaptor.addChild(root_0, and23.getTree());

            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "where"

    public static class and_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "and"
    // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:61:1: and : clause ( 'AND' clause )* ;
    public final SQL_grammarParser.and_return and() throws RecognitionException {
        SQL_grammarParser.and_return retval = new SQL_grammarParser.and_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token string_literal25=null;
        SQL_grammarParser.clause_return clause24 = null;

        SQL_grammarParser.clause_return clause26 = null;


        CommonTree string_literal25_tree=null;

        try {
            // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:61:5: ( clause ( 'AND' clause )* )
            // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:61:7: clause ( 'AND' clause )*
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_clause_in_and226);
            clause24=clause();

            state._fsp--;

            adaptor.addChild(root_0, clause24.getTree());
            // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:61:14: ( 'AND' clause )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==28) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:61:16: 'AND' clause
            	    {
            	    string_literal25=(Token)match(input,28,FOLLOW_28_in_and230); 
            	    pushFollow(FOLLOW_clause_in_and233);
            	    clause26=clause();

            	    state._fsp--;

            	    adaptor.addChild(root_0, clause26.getTree());

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "and"

    public static class clause_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "clause"
    // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:64:1: clause : ( ( NAME | LITERAL ) op ( NAME | LITERAL ) | ( NAME | LITERAL ) IN '(' query1 ')' );
    public final SQL_grammarParser.clause_return clause() throws RecognitionException {
        SQL_grammarParser.clause_return retval = new SQL_grammarParser.clause_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token set27=null;
        Token set29=null;
        Token set30=null;
        Token IN31=null;
        Token char_literal32=null;
        Token char_literal34=null;
        SQL_grammarParser.op_return op28 = null;

        SQL_grammarParser.query1_return query133 = null;


        CommonTree set27_tree=null;
        CommonTree set29_tree=null;
        CommonTree set30_tree=null;
        CommonTree IN31_tree=null;
        CommonTree char_literal32_tree=null;
        CommonTree char_literal34_tree=null;

        try {
            // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:64:8: ( ( NAME | LITERAL ) op ( NAME | LITERAL ) | ( NAME | LITERAL ) IN '(' query1 ')' )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==NAME||LA8_0==LITERAL) ) {
                int LA8_1 = input.LA(2);

                if ( (LA8_1==IN) ) {
                    alt8=2;
                }
                else if ( ((LA8_1>=EQ && LA8_1<=GT_EQ)) ) {
                    alt8=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:64:10: ( NAME | LITERAL ) op ( NAME | LITERAL )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    set27=(Token)input.LT(1);
                    if ( input.LA(1)==NAME||input.LA(1)==LITERAL ) {
                        input.consume();
                        adaptor.addChild(root_0, (CommonTree)adaptor.create(set27));
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_op_in_clause256);
                    op28=op();

                    state._fsp--;

                    root_0 = (CommonTree)adaptor.becomeRoot(op28.getTree(), root_0);
                    set29=(Token)input.LT(1);
                    if ( input.LA(1)==NAME||input.LA(1)==LITERAL ) {
                        input.consume();
                        adaptor.addChild(root_0, (CommonTree)adaptor.create(set29));
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }


                    }
                    break;
                case 2 :
                    // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:65:5: ( NAME | LITERAL ) IN '(' query1 ')'
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    set30=(Token)input.LT(1);
                    if ( input.LA(1)==NAME||input.LA(1)==LITERAL ) {
                        input.consume();
                        adaptor.addChild(root_0, (CommonTree)adaptor.create(set30));
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    IN31=(Token)match(input,IN,FOLLOW_IN_in_clause281); 
                    IN31_tree = (CommonTree)adaptor.create(IN31);
                    root_0 = (CommonTree)adaptor.becomeRoot(IN31_tree, root_0);

                    char_literal32=(Token)match(input,26,FOLLOW_26_in_clause284); 
                    pushFollow(FOLLOW_query1_in_clause287);
                    query133=query1();

                    state._fsp--;

                    adaptor.addChild(root_0, query133.getTree());
                    char_literal34=(Token)match(input,27,FOLLOW_27_in_clause289); 

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "clause"

    public static class op_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "op"
    // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:68:1: op : ( EQ | LS | GT | LS_EQ | GT_EQ ) ;
    public final SQL_grammarParser.op_return op() throws RecognitionException {
        SQL_grammarParser.op_return retval = new SQL_grammarParser.op_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token set35=null;

        CommonTree set35_tree=null;

        try {
            // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:68:4: ( ( EQ | LS | GT | LS_EQ | GT_EQ ) )
            // /home/dragan/Dragan/workspaces/eclipse_workspace/query_optimizer/src/main/java/queryopt/parser/SQL_grammar.g:68:6: ( EQ | LS | GT | LS_EQ | GT_EQ )
            {
            root_0 = (CommonTree)adaptor.nil();

            set35=(Token)input.LT(1);
            if ( (input.LA(1)>=EQ && input.LA(1)<=GT_EQ) ) {
                input.consume();
                adaptor.addChild(root_0, (CommonTree)adaptor.create(set35));
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "op"

    // Delegated rules


 

    public static final BitSet FOLLOW_query1_in_query55 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_24_in_query57 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_query60 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_select_in_query173 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_from_in_query175 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_where_in_query177 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SELECT_in_select106 = new BitSet(new long[]{0x0000000000001FC0L});
    public static final BitSet FOLLOW_NAME_in_select113 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_aggregate_funct_in_select117 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_25_in_select121 = new BitSet(new long[]{0x0000000000001F40L});
    public static final BitSet FOLLOW_NAME_in_select125 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_aggregate_funct_in_select129 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_STAR_in_select137 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_aggregate_funct150 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_26_in_aggregate_funct173 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_NAME_in_aggregate_funct177 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_27_in_aggregate_funct179 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FROM_in_from191 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_NAME_in_from194 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_25_in_from197 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_NAME_in_from200 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_WHERE_in_where213 = new BitSet(new long[]{0x0000000000008040L});
    public static final BitSet FOLLOW_and_in_where216 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_clause_in_and226 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_28_in_and230 = new BitSet(new long[]{0x0000000000008040L});
    public static final BitSet FOLLOW_clause_in_and233 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_set_in_clause246 = new BitSet(new long[]{0x00000000003E0000L});
    public static final BitSet FOLLOW_op_in_clause256 = new BitSet(new long[]{0x0000000000008040L});
    public static final BitSet FOLLOW_set_in_clause259 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_clause273 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_IN_in_clause281 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_26_in_clause284 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_query1_in_clause287 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_27_in_clause289 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_op303 = new BitSet(new long[]{0x0000000000000002L});

}