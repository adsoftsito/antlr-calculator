package com.adsoft.antlr;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.adsoft.antlr.calc.CalculatorLexer;
import com.adsoft.antlr.calc.CalculatorParser;
import com.adsoft.antlr.calc.Listener;
import com.adsoft.antlr.calc.Result;
import com.adsoft.antlr.calc.Statement;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

@RestController
public class HelloController {

   @GetMapping("/")
   public String index() {
        HelloLexer lexer = new HelloLexer(CharStreams.fromString("hello adsoft"));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        HelloParser parser = new HelloParser(tokens);
        return "Hello  : "  + parser.r().toStringTree();
   }

   @PostMapping("/calc")
   @ResponseBody
   public Result calc(@RequestBody Statement myStatements) {
        
      CalculatorLexer lexer = new CalculatorLexer(CharStreams.fromString(myStatements.getStatement()));
      CommonTokenStream tokens = new CommonTokenStream(lexer);

        CalculatorParser parser = new CalculatorParser(tokens);
        ParseTree tree = parser.expr(); // parse

        ParseTreeWalker walker = new ParseTreeWalker();
        Listener listener = new Listener();
        walker.walk(listener, tree);
        System.out.println(listener.getResult());

        Result myResult = new Result();
        myResult.setResult(listener.getResult());

        return (myResult);
   }

}
