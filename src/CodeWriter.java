//package vmtranslator;

import java.io.IOException;
import java.io.Writer;

/**
 * Output Hack ASM for VM code.
 *
 */
public class CodeWriter {
    // Temporary registers for intermediate calculations,
    // should they be required by the translator.
    private static final String R13 = "R13", R14 = "R14", R15 = "R15";

    // Where the translation is written.
    private final Writer writer;
    // Current file being processed.
    private String currentFilename;

    //we have a jump counter here, so our label doesn't
    //overlap with other labels.
    //So when we create new labels it'll have a value on it to distinct itself with others.
    int JumpCounter = 0;
    int SecondJumpCounter = 0;

    /**
     * Create a CodeWriter to output to the given file.
     * @param writer Where to write the code.
     */
    public CodeWriter(Writer writer)
    {
        this.writer = writer;
    }

    /**
     * Translation of a new file.
     * @param filename The input file name.
     */
    public void setFilename(String filename)
    {
        this.currentFilename = filename;
    }

    /**
     * Translate the given arithmetic command.
     * @param command The command to be translated.
     * @throws java.io.IOException
     */
    public void writeArithmetic(String command)
            throws IOException
    {
        switch(command) {
            // Binary arithmetic operators.
            case "add":
                //pop value add their sum
                output(popDToStack(command, 0) + "M=M+D\n" + "@SP\n" + "M=M-1\n");
                //we have index 0 here because we can't call method otherwise, we won't use the index here,
                //but we need it for other cases such as doing push local...
                break;
            case "and":
                //pop value and their sum
                output(popDToStack(command, 0) + "M=D&M\n" + "@SP\n" + "M=M-1\n");
                break;
            case "or":
                //pop value or their sum
                output(popDToStack(command, 0) + "M=D|M\n" + "@SP\n" + "M=M-1\n");
                break;
            case "sub":
                //pop value sub their sum
                output(popDToStack(command, 0) + "M=M-D\n" + "@SP\n" + "M=M-1\n");
                break;

            // Unary operators.
            case "neg":
                //get the negation by subtracting 0 and main value so minus would be plus and plus would be minus.
                output("@SP\n" + "A=M-1\n" + "M=-M\n");
                break;
            case "not":
                //not
                output("@SP\n" + "A=M-1\n" + "M=!M\n");
                break;

            // Relational operators.
            case "eq":
                output(popDToStack(command, 0));
                break;
            case "lt":
                output(popDToStack(command, 0));
                break;
            case "gt":
                output(popDToStack(command, 0));
                break;
            default:
                throw new IllegalStateException("Unrecognised arithmetic command: " + command);
        }
    }

    /**
     * Translate the given push or pop command.
     * @param command The command to be translated.
     * @param segment The segment to be accessed.
     * @param index   The index within segment.
     * @throws java.io.IOException
     */
    public void writePushPop(CommandType command, String segment, int index)
            throws IOException
    {
        if(null == command) {
            throw new IllegalStateException("Invalid command in writePushPop: " +
                    command);
        }
        else {
            switch (command) {
                case C_PUSH:
                    switch(segment){//another switch case here for segment when push
                        case "constant"://when segment is case#
                            output("@" + index + "\n" + "D=A\n" + pushDToStack(segment, index));
                            break;
                        case "local":
                            output(pushDToStack("LCL", index));
                            break;
                        case "this":
                            output(pushDToStack("THIS", index));
                            break;
                        case "that":
                            output(pushDToStack("THAT", index));
                            break;
                        case "temp":
                            //R14 as our temp variable
                            output(pushDToStack(R14, index + 5));
                            //without +5 on index, test will fail because value won't be on RAM11.
                            break;
                        case "argument":
                            output(pushDToStack("ARG", index));
                            break;
                        case "static":
                            output(pushDToStack( currentFilename + "." + index, index));
                            break;
                        case "pointer":
                            if(index==0){output(pushDToStack("THIS", index));}
                            else if(index==1){output(pushDToStack("THAT", index));}
                            break;
                    }
                    break;
                case C_POP:
                    //another switch case here for segment when pop
                    switch (segment){
                        //we use keywords as our segment
                        //so instead of local, we'd use LCL, arguments as ARG etc...
                        case "local":
                            output(popDToStack("LCL", index));
                            break;
                        case "this":
                            output(popDToStack("THIS", index));
                            break;
                        case "that":
                            output(popDToStack("THAT", index));
                            break;
                        case "temp":
                            //without +5 on index, test will fail because value won't be on RAM11.
                            output(popDToStack(R14, index + 5));
                            break;
                        case "argument":
                            output(popDToStack("ARG", index));
                            break;
                        case "static":
                            output(popDToStack( currentFilename + "." + index, index));
                            break;
                        case "pointer":
                            //index is either 1 or 0
                            if(index==0){output(popDToStack("THIS", index));}
                            else if(index==1){output(popDToStack("THAT", index));}
                            break;
                    }
                    break;
                default:
                    throw new IllegalStateException("Invalid command in writePushPop: " +
                            command);
            }
        }
    }

    /**
     * sequence to push D to the top
     * of the stack.
     */
    public String pushDToStack(String segment, int index){

        //split Instruction into different parts for push D and different segments.

        String instructions = "@SP\n" + "A=M\n" + "M=D\n" + "@SP\n" + "M=M+1\n";

        //return a different sequence for other segments
        if(segment.equals("LCL") || segment.equals("THIS")  &&  !(index== 0)|| segment.equals("THAT")  &&  !(index==1) || segment.equals("R14") || segment.equals("ARG") ){
            instructions = "@" + segment + "\nD=M\n" + "@" + index + "\nA=D+A\n" +
                    "D=M\n" + "@SP\n" + "A=M\n" + "M=D\n" + "@SP\n" + "M=M+1\n";}

        //index equals 0 or 1 for pointer, so we have to make sure that it can reach the last else if,
        //so it can execute pointer instructions.

        else if(segment.equals("THIS") &&  (index == 0)|| segment.equals("THAT") &&  (index == 1) || segment.equals(currentFilename + "." + String.valueOf(index))){
            instructions = "@" + segment + "\nD=M\n" + "@SP\n"
                    + "A=M\n" + "M=D\n" + "@SP\n" + "M=M+1\n";}

        return instructions;
    }

    public String popDToStack(String segment, int index){

        //split Instruction into different parts for operators and different segments.

        String instructions = null;

        if(segment.equals("add") || segment.equals("sub") || segment.equals("and")|| segment.equals("or")){
            instructions = "@SP\n" + "A=M-1\n" + "D=M\n" + "A=A-1\n";}//pop top of the stack into register D


        else if(segment.equals("eq") || segment.equals("lt") || segment.equals("gt")){
            instructions = "@SP\n" + "A=M-1\n" + "D=M\n" + "A=A-1\n" + "D=M-D\n"
                    + "@" + segment + "Label" + JumpCounter + "\n" + "D;" + "J" + segment.toUpperCase() + "\n"
                    + "D=0\n" + "@" + segment+ "LabelTwo" +  SecondJumpCounter +"\n" + "0;JMP\n" +
                    "(" + segment+ "Label" + JumpCounter +  ")\n" + "D=-1\n" + "(" + segment+ "LabelTwo" + SecondJumpCounter + ")\n" +
                     "@SP\n" + "A=M-1\n" + "A=A-1\n" + "M=D\n" + "@SP\n" + "M=M-1\n";

        }//pop top of the stack into register D
        //return a different sequence if its eq, lt, gt
        //return a different sequence for other segments

        else if(segment.equals("LCL") || segment.equals("THIS") &&  !(index == 0)|| segment.equals("THAT")  &&  !(index== 1)|| segment.equals("R14") || segment.equals("ARG") ){
            instructions = "@" + segment + "\nD=M\n" + "@" + index + "\nD=D+A\n" + "@R13\n" + "M=D\n" +
                    "@SP\n" + "AM=M-1\n" +  "D=M\n" + "@R13\n" + "A=M\n" + "M=D\n";}

        //index equals 0 or 1 for pointer, so we have to make sure that it can reach the last else if,
        //so it can execute pointer instructions.

        else if(segment.equals("THIS") &&  (index== 0)|| segment.equals("THAT") &&  (index== 1)|| segment.equals(currentFilename + "." + String.valueOf(index))){
                instructions = "@" + segment + "\nD=A\n" + "@R13\n" + "M=D\n" +
                             "@SP\n" + "AM=M-1\n" +  "D=M\n" + "@R13\n" + "A=M\n" + "M=D\n"; }

        //increase jump counter
        //so we won't get the same label again.
        JumpCounter++;
        SecondJumpCounter++;

        return instructions;
    }


    /**
     * Write the given text as a comment.
     * @param text the text to output.
     * @throws IOException
     */
    public void writeComment(String text)
            throws IOException
    {
        output("// " + text);
    }

    /**
     * Close the output file.
     * @throws IOException
     */
    public void close()
            throws IOException
    {
        try (writer) {
            String endOfProgram = "THATS_ALL_FOLKS";
            output("@" + endOfProgram);
            output(String.format("(%s)", endOfProgram));
            output("0;JMP");
        }
    }

    /**
     * Output the given string with an indent and
     * terminate the current line.
     * @param s The string to output.
     * @throws IOException
     */
    private void output(String s)
            throws IOException
    {
        writer.write("    ");
        writer.write(s);
        writer.write('\n');
    }

}
