package main.java.al.antlr4;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import al.antlr4.AluminumLexer;
import al.antlr4.AluminumParser;
public class AluminumRunner 
{
    public static void main( String[] args) throws Exception 
    {
        AluminumLexer lexer = new AluminumLexer(new ANTLRFileStream("test/samples/if.al"));
        AluminumParser parser = new AluminumParser(new CommonTokenStream(lexer));
        ParseTree tree = parser.program();
        
        System.out.println(tree.toStringTree(parser));
    }
}