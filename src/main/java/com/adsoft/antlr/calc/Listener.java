package com.adsoft.antlr.calc;

import java.util.Stack;

public  class Listener extends CalculatorBaseListener {
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

    
