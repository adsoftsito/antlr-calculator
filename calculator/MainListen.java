import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Stack;

public class MainListen {
    private static class Listener extends CalculatorBaseListener {
        private Stack<Integer> stack = new Stack<Integer>();
        public int getResult() {
            return stack.peek();
        }
        
       @Override
        public void exitMulDiv(CalculatorParser.MulDivContext ctx) {
            int right = stack.pop();
            int left = stack.pop();
            int result;
            if (ctx.op.getType() == CalculatorParser.MUL) {
                result = left * right;
            } else {
                result = left / right;
            }
            stack.push(result);
        }
        @Override
        public void exitAddSub(CalculatorParser.AddSubContext ctx) {
            int right = stack.pop();
            int left = stack.pop();
            int result;
            if (ctx.op.getType() == CalculatorParser.ADD) {
                result = left + right;
            } else {
                result = left - right;
            }
            stack.push(result);
        }

	 @Override
        public void exitInt(CalculatorParser.IntContext ctx) {
            stack.push(Integer.valueOf(ctx.INT().getText()));
        }
    }
    public static void main(String[] args) throws Exception {

        String inputFile = null;
        if ( args.length>0 ) inputFile = args[0];
        InputStream is = System.in;
        if ( inputFile!=null ) is = new FileInputStream(inputFile);
        ANTLRInputStream input = new ANTLRInputStream(is);
        CalculatorLexer lexer = new CalculatorLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        //System.out.println(tokens.getText());
        CalculatorParser parser = new CalculatorParser(tokens);
        ParseTree tree = parser.expr(); // parse
        ParseTreeWalker walker = new ParseTreeWalker();
        Listener listener = new Listener();
        walker.walk(listener, tree);
        System.out.println(listener.getResult());

    }
}

