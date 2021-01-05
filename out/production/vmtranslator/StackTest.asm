    // push constant 17
    @17
D=A
@SP
A=M
M=D
D=A
@SP
M=M+1

    // push constant 17
    @17
D=A
@SP
A=M
M=D
D=A
@SP
M=M+1

    // eq
    @SP
A=M-1
D=M
A=A-1
D=M-D
@eqLabel0
D;JEQ
D=0
@eqLabelTwo0
0;JMP
(eqLabel0)
D=-1
(eqLabelTwo0)
@SP
A=M-1
A=A-1
M=D
@SP
M=M-1

    // push constant 17
    @17
D=A
@SP
A=M
M=D
D=A
@SP
M=M+1

    // push constant 16
    @16
D=A
@SP
A=M
M=D
D=A
@SP
M=M+1

    // eq
    @SP
A=M-1
D=M
A=A-1
D=M-D
@eqLabel1
D;JEQ
D=0
@eqLabelTwo1
0;JMP
(eqLabel1)
D=-1
(eqLabelTwo1)
@SP
A=M-1
A=A-1
M=D
@SP
M=M-1

    // push constant 16
    @16
D=A
@SP
A=M
M=D
D=A
@SP
M=M+1

    // push constant 17
    @17
D=A
@SP
A=M
M=D
D=A
@SP
M=M+1

    // eq
    @SP
A=M-1
D=M
A=A-1
D=M-D
@eqLabel2
D;JEQ
D=0
@eqLabelTwo2
0;JMP
(eqLabel2)
D=-1
(eqLabelTwo2)
@SP
A=M-1
A=A-1
M=D
@SP
M=M-1

    // push constant 892
    @892
D=A
@SP
A=M
M=D
D=A
@SP
M=M+1

    // push constant 891
    @891
D=A
@SP
A=M
M=D
D=A
@SP
M=M+1

    // lt
    @SP
A=M-1
D=M
A=A-1
D=M-D
@ltLabel3
D;JLT
D=0
@ltLabelTwo3
0;JMP
(ltLabel3)
D=-1
(ltLabelTwo3)
@SP
A=M-1
A=A-1
M=D
@SP
M=M-1

    // push constant 891
    @891
D=A
@SP
A=M
M=D
D=A
@SP
M=M+1

    // push constant 892
    @892
D=A
@SP
A=M
M=D
D=A
@SP
M=M+1

    // lt
    @SP
A=M-1
D=M
A=A-1
D=M-D
@ltLabel4
D;JLT
D=0
@ltLabelTwo4
0;JMP
(ltLabel4)
D=-1
(ltLabelTwo4)
@SP
A=M-1
A=A-1
M=D
@SP
M=M-1

    // push constant 891
    @891
D=A
@SP
A=M
M=D
D=A
@SP
M=M+1

    // push constant 891
    @891
D=A
@SP
A=M
M=D
D=A
@SP
M=M+1

    // lt
    @SP
A=M-1
D=M
A=A-1
D=M-D
@ltLabel5
D;JLT
D=0
@ltLabelTwo5
0;JMP
(ltLabel5)
D=-1
(ltLabelTwo5)
@SP
A=M-1
A=A-1
M=D
@SP
M=M-1

    // push constant 32767
    @32767
D=A
@SP
A=M
M=D
D=A
@SP
M=M+1

    // push constant 32766
    @32766
D=A
@SP
A=M
M=D
D=A
@SP
M=M+1

    // gt
    @SP
A=M-1
D=M
A=A-1
D=M-D
@gtLabel6
D;JGT
D=0
@gtLabelTwo6
0;JMP
(gtLabel6)
D=-1
(gtLabelTwo6)
@SP
A=M-1
A=A-1
M=D
@SP
M=M-1

    // push constant 32766
    @32766
D=A
@SP
A=M
M=D
D=A
@SP
M=M+1

    // push constant 32767
    @32767
D=A
@SP
A=M
M=D
D=A
@SP
M=M+1

    // gt
    @SP
A=M-1
D=M
A=A-1
D=M-D
@gtLabel7
D;JGT
D=0
@gtLabelTwo7
0;JMP
(gtLabel7)
D=-1
(gtLabelTwo7)
@SP
A=M-1
A=A-1
M=D
@SP
M=M-1

    // push constant 32766
    @32766
D=A
@SP
A=M
M=D
D=A
@SP
M=M+1

    // push constant 32766
    @32766
D=A
@SP
A=M
M=D
D=A
@SP
M=M+1

    // gt
    @SP
A=M-1
D=M
A=A-1
D=M-D
@gtLabel8
D;JGT
D=0
@gtLabelTwo8
0;JMP
(gtLabel8)
D=-1
(gtLabelTwo8)
@SP
A=M-1
A=A-1
M=D
@SP
M=M-1

    // push constant 57
    @57
D=A
@SP
A=M
M=D
D=A
@SP
M=M+1

    // push constant 31
    @31
D=A
@SP
A=M
M=D
D=A
@SP
M=M+1

    // push constant 53
    @53
D=A
@SP
A=M
M=D
D=A
@SP
M=M+1

    // add
    @SP
A=M-1
D=M
A=A-1
M=M+D
@SP
M=M-1

    // push constant 112
    @112
D=A
@SP
A=M
M=D
D=A
@SP
M=M+1

    // sub
    @SP
A=M-1
D=M
A=A-1
M=M-D
@SP
M=M-1

    // neg
    @SP
A=M-1
M=-M

    // and
    @SP
A=M-1
D=M
A=A-1
M=D&M
@SP
M=M-1

    // push constant 82
    @82
D=A
@SP
A=M
M=D
D=A
@SP
M=M+1

    // or
    @SP
A=M-1
D=M
A=A-1
M=D|M
@SP
M=M-1

    // not
    @SP
A=M-1
M=!M

    @THATS_ALL_FOLKS
    (THATS_ALL_FOLKS)
    0;JMP
