// Generated from C:/Users/ep500/Documents/ep500/task1/src/SimpleLang.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SimpleLangParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface SimpleLangVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link SimpleLangParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(SimpleLangParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLangParser#dec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDec(SimpleLangParser.DecContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLangParser#vardec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVardec(SimpleLangParser.VardecContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLangParser#typed_idfr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTyped_idfr(SimpleLangParser.Typed_idfrContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLangParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(SimpleLangParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLangParser#body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBody(SimpleLangParser.BodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleLangParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(SimpleLangParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AssignExpr}
	 * labeled alternative in {@link SimpleLangParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignExpr(SimpleLangParser.AssignExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BinOpExpr}
	 * labeled alternative in {@link SimpleLangParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinOpExpr(SimpleLangParser.BinOpExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BoolOpExpr}
	 * labeled alternative in {@link SimpleLangParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolOpExpr(SimpleLangParser.BoolOpExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InvokeExpr}
	 * labeled alternative in {@link SimpleLangParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInvokeExpr(SimpleLangParser.InvokeExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BlockExpr}
	 * labeled alternative in {@link SimpleLangParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockExpr(SimpleLangParser.BlockExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IfExpr}
	 * labeled alternative in {@link SimpleLangParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfExpr(SimpleLangParser.IfExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PrintExpr}
	 * labeled alternative in {@link SimpleLangParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintExpr(SimpleLangParser.PrintExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SpaceExpr}
	 * labeled alternative in {@link SimpleLangParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSpaceExpr(SimpleLangParser.SpaceExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IdExpr}
	 * labeled alternative in {@link SimpleLangParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdExpr(SimpleLangParser.IdExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IntExpr}
	 * labeled alternative in {@link SimpleLangParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntExpr(SimpleLangParser.IntExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code WhileExpr}
	 * labeled alternative in {@link SimpleLangParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileExpr(SimpleLangParser.WhileExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AssignTypedExpr}
	 * labeled alternative in {@link SimpleLangParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignTypedExpr(SimpleLangParser.AssignTypedExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NewLineExpr}
	 * labeled alternative in {@link SimpleLangParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewLineExpr(SimpleLangParser.NewLineExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EqBinop}
	 * labeled alternative in {@link SimpleLangParser#binop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqBinop(SimpleLangParser.EqBinopContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LessBinop}
	 * labeled alternative in {@link SimpleLangParser#binop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLessBinop(SimpleLangParser.LessBinopContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LessEqBinop}
	 * labeled alternative in {@link SimpleLangParser#binop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLessEqBinop(SimpleLangParser.LessEqBinopContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GreatBinop}
	 * labeled alternative in {@link SimpleLangParser#binop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGreatBinop(SimpleLangParser.GreatBinopContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GreatEqBinop}
	 * labeled alternative in {@link SimpleLangParser#binop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGreatEqBinop(SimpleLangParser.GreatEqBinopContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PlusBinop}
	 * labeled alternative in {@link SimpleLangParser#binop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlusBinop(SimpleLangParser.PlusBinopContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MinusBinop}
	 * labeled alternative in {@link SimpleLangParser#binop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMinusBinop(SimpleLangParser.MinusBinopContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TimesBinop}
	 * labeled alternative in {@link SimpleLangParser#binop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTimesBinop(SimpleLangParser.TimesBinopContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DivideBinop}
	 * labeled alternative in {@link SimpleLangParser#binop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDivideBinop(SimpleLangParser.DivideBinopContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AndBoolOp}
	 * labeled alternative in {@link SimpleLangParser#boolop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndBoolOp(SimpleLangParser.AndBoolOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OrBoolOp}
	 * labeled alternative in {@link SimpleLangParser#boolop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrBoolOp(SimpleLangParser.OrBoolOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code XorBoolOp}
	 * labeled alternative in {@link SimpleLangParser#boolop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXorBoolOp(SimpleLangParser.XorBoolOpContext ctx);
}