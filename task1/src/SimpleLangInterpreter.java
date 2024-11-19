import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.lang.reflect.Array;
import java.util.*;

public class SimpleLangInterpreter extends AbstractParseTreeVisitor<Integer> implements SimpleLangVisitor<Integer> {

    private final Map<String, SimpleLangParser.DecContext> global_funcs = new HashMap<>();
    private final Stack<Map<String, Integer>> frames = new Stack<>();

    private final Map<String, ArrayList<Map<String, Integer>>> local_variables = new HashMap<>();
    private String current_scope;

    public Integer visitProgram(SimpleLangParser.ProgContext ctx, String[] args)
    {

        for (int i = 0; i < ctx.dec().size(); ++i) {

            SimpleLangParser.DecContext dec = ctx.dec(i);
            SimpleLangParser.Typed_idfrContext typedIdfr = dec.typed_idfr();
            global_funcs.put(typedIdfr.Idfr().getText(), dec);

        }

        SimpleLangParser.DecContext main = global_funcs.get("main");

        Map<String, Integer> newFrame = new HashMap<>();

        for (int i = 0; i < args.length; ++i) {
            if (args[i].equals("true")) {
                newFrame.put(main.vardec().typed_idfr().get(i).Idfr().getText(), 1);
            } else if (args[i].equals("false")) {
                newFrame.put(main.vardec().typed_idfr().get(i).Idfr().getText(), 0);
            } else {
                newFrame.put(main.vardec().typed_idfr().get(i).Idfr().getText(), Integer.parseInt(args[i]));
            }
        }

        frames.push(newFrame);
        return visit(main);

    }

    @Override public Integer visitProg(SimpleLangParser.ProgContext ctx)
    {

        throw new RuntimeException("Should not be here!");

    }

    @Override public Integer visitDec(SimpleLangParser.DecContext ctx)
    {
//        System.out.printf("@visitDec%n");
        Integer returnValue = visit(ctx.body());

        String function_name = ctx.typed_idfr().Idfr().getText();
        current_scope = function_name;

        // Check if scope exists
        ArrayList<Map<String, Integer>> scope_exists = local_variables.get(ctx);
//        System.out.printf("ctx.typed_idfr().Idfr().getText() : %s%n",ctx.typed_idfr().Idfr().getText());
        // If not, create new scope
        if (Objects.isNull(scope_exists))
        {
            ArrayList<Map<String, Integer>> var_list = new ArrayList<>();
            local_variables.put(function_name, var_list);
        }

        Map<String, Integer> popped = frames.pop();

        return returnValue;

    }

    @Override public Integer visitVardec(SimpleLangParser.VardecContext ctx)
    {
        SimpleLangParser.Typed_idfrContext first_vardec_context = ctx.typed_idfr(0);
        Map<String, Integer> new_frames = new HashMap<>(frames.peek());
        Integer returnValue = Integer.parseInt(first_vardec_context.Idfr().getText());

        new_frames.put(first_vardec_context.getText(),  Integer.parseInt(first_vardec_context.Idfr().getText()));

        // Iterate over declarations if more than one exists
        if (ctx.typed_idfr().size() > 1)
        {
            for (SimpleLangParser.Typed_idfrContext current_context: ctx.typed_idfr())
            {
                new_frames.put(current_context.getText(), Integer.parseInt(current_context.Idfr().getText()));
            }
        }

        frames.push(new_frames);
        return returnValue;
    }

    @Override public Integer visitTyped_idfr(SimpleLangParser.Typed_idfrContext ctx)
    {
        throw new RuntimeException("Should not be here!");
    }

    @Override public Integer visitType(SimpleLangParser.TypeContext ctx)
    {
        throw new RuntimeException("Should not be here!");
    }

    @Override public Integer visitBody(SimpleLangParser.BodyContext ctx) {

        Integer returnValue = null;
        for (int i = 0; i < ctx.ene.size(); ++i) {
            SimpleLangParser.ExpContext exp = ctx.ene.get(i);
            returnValue = visit(exp);
        }
        return returnValue;

    }

    @Override public Integer visitBlock(SimpleLangParser.BlockContext ctx)
    {
        Integer returnValue = null;
        for (int i = 0; i < ctx.ene.size(); ++i) {
            SimpleLangParser.ExpContext exp = ctx.ene.get(i);
            returnValue = visit(exp);
        }
        return returnValue;
    }

    @Override public Integer visitAssignExpr(SimpleLangParser.AssignExprContext ctx) {
        SimpleLangParser.ExpContext rhs = ctx.exp();
        Integer visitation = visit(rhs);
        frames.peek().replace(ctx.Idfr().getText(), visitation);

        // Update local variables for current scope
        if (Objects.isNull(local_variables.get(current_scope)))
            local_variables.put(current_scope, new ArrayList<>());

        local_variables.get(current_scope).clear();
        local_variables.get(current_scope).add(frames.peek());

//        System.out.printf("Local variables for %s are: %s%n", current_scope, local_variables.get(current_scope).get(0).get("n").toString());
        return null;
    }
    @Override public Integer visitAssignTypedExpr(SimpleLangParser.AssignTypedExprContext ctx) {
        SimpleLangParser.Typed_idfrContext type_identifier = ctx.typed_idfr();
        SimpleLangParser.ExpContext right_hand_side = ctx.exp();

//        Map<String, Integer> new_frame = new HashMap<>(frames.peek()); // Construct new frame with all tokens from previous frame
//        new_frame.put(type_identifier.Idfr().getText(), visit(right_hand_side));
//        frames.push(new_frame);                                        // Declare identifier
        frames.peek().put(type_identifier.Idfr().getText(), visit(right_hand_side));
        // Update local variables for current scope
        if (Objects.isNull(local_variables.get(current_scope)))
            local_variables.put(current_scope, new ArrayList<>());

        local_variables.get(current_scope).clear();
        local_variables.get(current_scope).add(frames.peek());

//        System.out.printf("Local variables for %s are: %s%n", current_scope, local_variables.get(current_scope).get(0).get("n").toString());
        return null;
    }

    @Override
    public Integer visitNewLineExpr(SimpleLangParser.NewLineExprContext ctx) {
        return null;
    }

    @Override
    public Integer visitSkipExpr(SimpleLangParser.SkipExprContext ctx) { return null; }

    @Override
    public Integer visitRepeatUntilExpr(SimpleLangParser.RepeatUntilExprContext ctx) {
        SimpleLangParser.ExpContext exp = ctx.exp();
        SimpleLangParser.BlockContext block = ctx.block();

        do
        {
            visit(block);
        }
        while(visit(exp) != 0);

        return null;
    }

    @Override public Integer visitBinOpExpr(SimpleLangParser.BinOpExprContext ctx) {


        SimpleLangParser.ExpContext operand1 = ctx.exp(0);
//        System.out.println(frames);
        Integer oprnd1 = visit(operand1);
        SimpleLangParser.ExpContext operand2 = ctx.exp(1);
        Integer oprnd2 = visit(operand2);

        switch (((TerminalNode) (ctx.binop().getChild(0))).getSymbol().getType()) {

            case SimpleLangParser.Eq ->  {

                return ((Objects.equals(oprnd1, oprnd2)) ? 1 : 0);

            }
            case SimpleLangParser.Less -> {

                return ((oprnd1 < oprnd2) ? 1 : 0);

            }
            case SimpleLangParser.LessEq -> {

                return ((oprnd1 <= oprnd2) ? 1 : 0);

            }
            case SimpleLangParser.Plus -> {

                return oprnd1 + oprnd2;

            }
            case SimpleLangParser.Minus -> {

                return oprnd1 - oprnd2;

            }
            case SimpleLangParser.Times -> {

                return oprnd1 * oprnd2;

            }
            default -> {
                throw new RuntimeException("Shouldn't be here - wrong binary operator.");
            }

        }

    }

    /**
     * @param ctx the parse tree
     * @return the result of operand1 OPERATOR operand2
     */
    @Override
    public Integer visitBoolOpExpr(SimpleLangParser.BoolOpExprContext ctx) {
        SimpleLangParser.ExpContext operand1 = ctx.exp(0);
        Integer oprnd1 = visit(operand1);
        boolean casted_operand_1 = (oprnd1 != 0);
        SimpleLangParser.ExpContext operand2 = ctx.exp(1);
        Integer oprnd2 = visit(operand2);
        boolean casted_operand_2 = (oprnd2 != 0);

        switch (((TerminalNode) (ctx.boolop().getChild(0))).getSymbol().getType()) {

            case SimpleLangParser.BinAnd -> {
                return (casted_operand_1 & casted_operand_2 ? 1 : 0);
            }
            case SimpleLangParser.BinOr -> {
                return (casted_operand_1 | casted_operand_2 ? 1 : 0);
            }
            case SimpleLangParser.BinXor -> {
                return (casted_operand_1 ^ casted_operand_2 ? 1 : 0);
            }
            default -> {
                throw new RuntimeException("Invalid Boolean Operation %s OPERATOR %s".formatted(casted_operand_1 ? "true" : "false", casted_operand_2 ? "true" : "false"));
            }
        }
    }

    @Override public Integer visitInvokeExpr(SimpleLangParser.InvokeExprContext ctx)
    {

        SimpleLangParser.DecContext dec = global_funcs.get(ctx.Idfr().getText());
        Map<String, Integer> newFrame = new HashMap<>();

        for (int i = 0; i < ctx.args.size(); ++i)
        {
            SimpleLangParser.Typed_idfrContext param = dec.vardec().typed_idfr().get(i);
            SimpleLangParser.ExpContext exp = ctx.args.get(i);
            newFrame.put(param.Idfr().getText(), visit(exp));
            frames.push(newFrame);
        }
        return visit(dec);

    }

    @Override public Integer visitBlockExpr(SimpleLangParser.BlockExprContext ctx) {
        return visit(ctx.block());
    }

    @Override public Integer visitIfExpr(SimpleLangParser.IfExprContext ctx)
    {

        SimpleLangParser.ExpContext cond = ctx.exp();
        Integer condValue = visit(cond);
        if (condValue > 0) {

            SimpleLangParser.BlockContext thenBlock = ctx.block(0);
            return visit(thenBlock);

        } else {

            SimpleLangParser.BlockContext elseBlock = ctx.block(1);
            return visit(elseBlock);

        }

    }

    @Override public Integer visitWhileExpr(SimpleLangParser.WhileExprContext ctx)
    {
        SimpleLangParser.ExpContext exp = ctx.exp();
        SimpleLangParser.BlockContext block = ctx.block();

        while (visit(exp) != 0)
        {
            visit(block);
        }

        return null;
    }

    @Override public Integer visitPrintExpr(SimpleLangParser.PrintExprContext ctx) {

        SimpleLangParser.ExpContext exp = ctx.exp();

        if (((TerminalNode) exp.getChild(0)).getSymbol().getType() == SimpleLangParser.Space) {

            System.out.print(" ");

        } else if (((TerminalNode) exp.getChild(0)).getSymbol().getType() == SimpleLangParser.NewLine) {

            System.out.println();

        } else {

            System.out.print(visit(exp));

        }

        return null;

    }

    @Override public Integer visitSpaceExpr(SimpleLangParser.SpaceExprContext ctx) {
        return null;
    }

    @Override public Integer visitIdExpr(SimpleLangParser.IdExprContext ctx)
    {
        // Must grab from local scope
        try {
            return local_variables.get(current_scope).get(0).get(ctx.Idfr().getText());
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            return frames.peek().get(ctx.Idfr().getText());
        }
    }

    @Override public Integer visitIntExpr(SimpleLangParser.IntExprContext ctx)
    {

        return Integer.parseInt(ctx.IntLit().getText());

    }
    @Override public Integer visitEqBinop(SimpleLangParser.EqBinopContext ctx) {
        throw new RuntimeException("Should not be here!");
    }
    @Override public Integer visitLessBinop(SimpleLangParser.LessBinopContext ctx) {
        throw new RuntimeException("Should not be here!");
    }
    @Override public Integer visitLessEqBinop(SimpleLangParser.LessEqBinopContext ctx) {
        throw new RuntimeException("Should not be here!");
    }

    /**
     * @param ctx the parse tree
     * @return None
     */
    @Override
    public Integer visitGreatBinop(SimpleLangParser.GreatBinopContext ctx) {
        throw new RuntimeException("Should not be here!");
    }

    /**
     * @param ctx the parse tree
     * @return None
     */
    @Override
    public Integer visitGreatEqBinop(SimpleLangParser.GreatEqBinopContext ctx) {
        throw new RuntimeException("Should not be here!");
    }

    @Override public Integer visitPlusBinop(SimpleLangParser.PlusBinopContext ctx) {
        throw new RuntimeException("Should not be here!");
    }
    @Override public Integer visitMinusBinop(SimpleLangParser.MinusBinopContext ctx) {
        throw new RuntimeException("Should not be here!");
    }
    @Override public Integer visitTimesBinop(SimpleLangParser.TimesBinopContext ctx) {
        throw new RuntimeException("Should not be here!");
    }

    /**
     * @param ctx the parse tree
     * @return None
     */
    @Override
    public Integer visitDivideBinop(SimpleLangParser.DivideBinopContext ctx) {
        throw new RuntimeException("Should not be here!");
    }

    /**
     * @param ctx the parse tree
     * @return None
     */
    @Override
    public Integer visitAndBoolOp(SimpleLangParser.AndBoolOpContext ctx) {
        throw new RuntimeException("Should not be here!");
    }

    /**
     * @param ctx the parse tree
     * @return None
     */
    @Override
    public Integer visitOrBoolOp(SimpleLangParser.OrBoolOpContext ctx) {
        throw new RuntimeException("Should not be here!");
    }

    /**
     * @param ctx the parse tree
     * @return None
     */
    @Override
    public Integer visitXorBoolOp(SimpleLangParser.XorBoolOpContext ctx) {
        throw new RuntimeException("Should not be here!");
    }

}
